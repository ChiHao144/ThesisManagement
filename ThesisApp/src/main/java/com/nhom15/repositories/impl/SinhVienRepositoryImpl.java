package com.nhom15.repositories.impl;

import com.nhom15.pojo.SinhVien;
import com.nhom15.pojo.User;
import com.nhom15.repositories.SinhVienRepository;
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
public class SinhVienRepositoryImpl implements SinhVienRepository {

    @Autowired
    private LocalSessionFactoryBean factory;
    @Autowired
    private UserRepository userRepo; // đảm bảo bạn đã inject nó

    public List<SinhVien> getSinhViens(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<SinhVien> q = b.createQuery(SinhVien.class);
        Root root = q.from(SinhVien.class);
        q.select(root);

        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();
            Join<SinhVien, User> userJoin = root.join("user");

            String kw = params.get("kw");
            if (kw != null && !kw.isEmpty()) {
                String pattern = String.format("%%%s%%", kw);
                Predicate p1 = b.like(userJoin.get("fullname"), pattern);
                Predicate p2 = b.like(root.get("khoa"), pattern);
                predicates.add(b.or(p1, p2));
            }

            q.where(predicates.toArray(Predicate[]::new));

            q.orderBy(b.desc(root.get(params.getOrDefault("sortBy", "id"))));
        }

        Query query = s.createQuery(q);

        return query.getResultList();
    }

    @Override
    public void addOrUpdateSinhVien(SinhVien sv) {
        Session s = this.factory.getObject().getCurrentSession();

        if (sv.getId() == null) {
            s.persist(sv);
        } else {
            s.merge(sv);
        }
    }

    public SinhVien getSinhVienById(int id) {
        Session s = factory.getObject().getCurrentSession();
        return s.get(SinhVien.class, id);
    }

    

    @Override
    public void importSinhVienFromUser() {
        Session session = this.factory.getObject().getCurrentSession();

        List<User> users = this.userRepo.getUsersByRole("sinhvien");

        for (User user : users) {
            SinhVien sv = session.get(SinhVien.class, user.getId());
            if (sv == null) {
                SinhVien newSv = new SinhVien();
                newSv.setId(user.getId());
                newSv.setUser(user);
                newSv.setKhoa("Chưa có thông tin");

                session.persist(newSv);
            }
        }
    }

    @Override
    public void updateSinhVien(SinhVien sv) {
        Session s = this.factory.getObject().getCurrentSession();
        if (sv.getId() == null) {
            s.persist(sv);
        } else {
            s.merge(sv);
        }
    }

}
