package com.pch.repositories.impl;

import com.pch.hibernatethesismanagement.HibernateConfigs;
import com.pch.pojo.User;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;

/**
 *
 * @author Chi Hao
 */
public class UserRepositoryImpl {

    private static final int PAGE_SIZE = 3;

    public List<User> getUsers(Map<String, String> params) {
        try (Session s = HibernateConfigs.getFACTORY().openSession()) {
            CriteriaBuilder b = s.getCriteriaBuilder();
            CriteriaQuery<User> q = b.createQuery(User.class);
            Root root = q.from(User.class);
            q.select(root);

            //Loc du lieu
            if (params != null) {
                List<Predicate> predicates = new ArrayList<>();

                String kw = params.get("kw");
                if (kw != null && !kw.isEmpty()) {
                    predicates.add(b.like(root.get("fullname"), String.format("%%%s%%", kw)));
                }
                q.where(predicates.toArray(Predicate[]::new));
            }

            Query query = s.createQuery(q);

            //Phan trang du lieu
            if (params != null) {
                String page = params.get("page");
                if (page != null) {
                    int p = Integer.parseInt(page);
                    int start = (p - 1) * PAGE_SIZE;
                    
                    query.setFirstResult(start);
                    query.setMaxResults(PAGE_SIZE);
                }
            }
            
            return query.getResultList();
        }
    }
}
