package com.nhom15.repositories.impl;

import com.nhom15.pojo.GiangVien;
import com.nhom15.pojo.User;
import com.nhom15.repositories.GiangVienRepository;
import com.nhom15.repositories.UserRepository;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
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
public class GiangVienRepositoryImpl implements GiangVienRepository {

    @Autowired
    private LocalSessionFactoryBean factory;
    @Autowired
    private UserRepository userRepo;

    public List<GiangVien> getGiangViens(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<GiangVien> q = b.createQuery(GiangVien.class);
        Root root = q.from(GiangVien.class);
        q.select(root);

        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();
            Join<GiangVien, User> userJoin = root.join("user");

            String kw = params.get("kw");
            if (kw != null && !kw.isEmpty()) {
                String pattern = String.format("%%%s%%", kw); 
                Predicate p1 = b.like(userJoin.get("fullname"), pattern);
                Predicate p2 = b.like(root.get("hocHam"), pattern);
                predicates.add(b.or(p1, p2)); 
            }

            q.where(predicates.toArray(Predicate[]::new));

            //Sap xep du lieu
            q.orderBy(b.desc(root.get(params.getOrDefault("sortBy", "id"))));
        }

        Query query = s.createQuery(q);

        return query.getResultList();
    }
    
    @Override
    public void addOrUpdateGiangVien(GiangVien gv) {
        Session s = this.factory.getObject().getCurrentSession();
        if (gv.getId() == null) {
            s.persist(gv);
        } else {
            s.merge(gv);
        }
    }

    @Override
    public void importGiangVienFromUser() {
        Session session = this.factory.getObject().getCurrentSession();

        List<User> users = this.userRepo.getUsersByRole("giangvien");

        for (User user : users) {
            GiangVien gv = session.get(GiangVien.class, user.getId());
            if (gv == null) {
                GiangVien newGv = new GiangVien();
                newGv.setId(user.getId());
                newGv.setUser(user);
                newGv.setHocHam("Chưa có thông tin");

                session.persist(newGv);
            }
        }
    }
}
