/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom15.repositories.impl;

import com.nhom15.pojo.HoiDong;
import com.nhom15.pojo.ThanhVien;
import com.nhom15.repositories.ThanhVienRepository;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;
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

    @Override
    public void deleteByHoiDongId(int hoiDongId) {
        Session s = this.factory.getObject().getCurrentSession();

        CriteriaBuilder tv = s.getCriteriaBuilder();
        CriteriaQuery<ThanhVien> q = tv.createQuery(ThanhVien.class);
        Root root = q.from(ThanhVien.class);
        q.select(root).where(tv.equal(root.get("hoiDong").get("id"), hoiDongId));
        Query query = s.createQuery(q);

        List<ThanhVien> thanhViens = query.getResultList();

        for (ThanhVien tvien : thanhViens) {
            s.remove(tvien);
        }
    }

    @Override
    public void addThanhVienToHoiDong(ThanhVien tv, HoiDong hoiDong) {
        Session session = factory.getObject().getCurrentSession();
        tv.setHoiDongId(hoiDong);
        session.persist(tv);
    }

    @Override
    public void clearHoiDongOfThanhVien(Integer hoiDongId) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();

        CriteriaQuery<ThanhVien> cq = cb.createQuery(ThanhVien.class);
        Root<ThanhVien> root = cq.from(ThanhVien.class);
        cq.select(root).where(cb.equal(root.get("hoiDongId").get("id"), hoiDongId));

        List<ThanhVien> list = session.createQuery(cq).getResultList();
        for (ThanhVien tv : list) {
            tv.setHoiDongId(null);
            session.merge(tv);
        }
    }
}
