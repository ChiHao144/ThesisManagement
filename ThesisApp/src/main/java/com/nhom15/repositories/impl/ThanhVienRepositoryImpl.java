/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom15.repositories.impl;

import com.nhom15.pojo.GiangVien;
import com.nhom15.pojo.HoiDong;
import com.nhom15.pojo.ThanhVien;
import com.nhom15.pojo.User;
import com.nhom15.repositories.ThanhVienRepository;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
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
 * @author ACER
 */
@Repository
@Transactional
public class ThanhVienRepositoryImpl implements ThanhVienRepository {

    @Autowired
    private LocalSessionFactoryBean factory;
    private static final int PAGE_SIZE = 6;

    @Override
    public boolean isGiangVienTrongHoiDong(int giangVienId, int hoiDongId) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder cb = s.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<ThanhVien> root = cq.from(ThanhVien.class);

        Predicate predicateGv = cb.equal(root.get("gvId").get("id"), giangVienId);
        Predicate predicateHd = cb.equal(root.get("hoiDongId").get("id"), hoiDongId);

        cq.select(cb.count(root)).where(cb.and(predicateGv, predicateHd));

        Long count = s.createQuery(cq).getSingleResult();
        return count > 0;
    }

    @Override
    public ThanhVien getByGiangVienAndHoiDong(int giangVienId, int hoiDongId) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<ThanhVien> cq = cb.createQuery(ThanhVien.class);
        Root<ThanhVien> root = cq.from(ThanhVien.class);

        Predicate p1 = cb.equal(root.get("gvId").get("id"), giangVienId);
        Predicate p2 = cb.equal(root.get("hoiDongId").get("id"), hoiDongId);

        cq.select(root).where(cb.and(p1, p2));

        List<ThanhVien> result = session.createQuery(cq).getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public List<ThanhVien> getThanhViens(Map<String, String> params) {
        Session s = factory.getObject().getCurrentSession();
        CriteriaBuilder cb = s.getCriteriaBuilder();
        CriteriaQuery<ThanhVien> cq = cb.createQuery(ThanhVien.class);
        Root<ThanhVien> root = cq.from(ThanhVien.class);

        Join<ThanhVien, GiangVien> gvJoin = root.join("gvId", JoinType.LEFT);
        Join<GiangVien, User> userJoin = gvJoin.join("user", JoinType.LEFT);
        Join<ThanhVien, HoiDong> hdJoin = root.join("hoiDongId", JoinType.LEFT);

        cq.select(root);

        List<Predicate> predicates = new ArrayList<>();

        if (params != null) {
            String kw = params.get("kw");
            if (kw != null && !kw.trim().isEmpty()) {
                String pattern = "%" + kw.trim().toLowerCase() + "%";
                predicates.add(
                        cb.or(
                                cb.like(cb.lower(userJoin.get("fullname")), pattern),
                                // chỉ lọc noiDungBaoVe khi hội đồng không null
                                cb.and(
                                        cb.isNotNull(root.get("hoiDongId")),
                                        cb.like(cb.lower(hdJoin.get("noiDungBaoVe")), pattern)
                                )
                        )
                );
            }

            String chuaCoHoiDong = params.get("chuaCoHoiDong");
            if ("true".equals(chuaCoHoiDong)) {
                predicates.add(cb.isNull(root.get("hoiDongId")));
            }

            String vaiTro = params.get("vaiTro");
            if (vaiTro != null && !vaiTro.isEmpty()) {
                predicates.add(cb.equal(root.get("vaiTro"), vaiTro));
            }
        }

        if (!predicates.isEmpty()) {
            cq.where(predicates.toArray(new Predicate[0]));
        }

        cq.orderBy(cb.desc(root.get("id")));

        Query query = s.createQuery(cq);

        if (params != null) {
            String page = params.get("page");
            if (page != null) {
                int start = (Integer.parseInt(page) - 1) * PAGE_SIZE;
                query.setFirstResult(start);
                query.setMaxResults(PAGE_SIZE);
            }
        }

        return query.getResultList();
    }

    @Override
    public ThanhVien getThanhVienById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(ThanhVien.class, id);
    }

    @Override
    public void addOrUpdateThanhVien(ThanhVien tv) {
        Session s = this.factory.getObject().getCurrentSession();

        if (tv.getId() == null) {
            s.persist(tv);
        } else {
            s.merge(tv);
        }
    }

    @Override
    public void deleteThanhVien(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        ThanhVien tv = this.getThanhVienById(id);
        if (tv != null) {
            s.remove(tv);
        }
    }
}
