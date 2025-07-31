package com.pch.repositories.impl;

import com.pch.pojo.User;
import com.pch.repositories.UserRepository;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Chi Hao
 */
@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private LocalSessionFactoryBean factory;
    private static final int PAGE_SIZE = 3;

    public List<User> getUsers(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
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

            String role = params.get("role");
            if (role != null && !role.isEmpty()) {
                predicates.add(b.equal(root.get("role"), role));
            }

            q.where(predicates.toArray(Predicate[]::new));

            //Sap xep du lieu
            q.orderBy(b.desc(root.get(params.getOrDefault("sortBy", "id"))));
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

    public void addOrUpdateUser(User u) {
        Session s = this.factory.getObject().getCurrentSession();
        if (u.getId() == null) {
            s.persist(u);
        } else {
            s.merge(u);
        }
    }
    
    public User getUserById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(User.class, id);
    }
    
    public void deleteUser(int id){
        Session s = this.factory.getObject().getCurrentSession();
        User u = this.getUserById(id);
        s.remove(u);
                
    }
}
