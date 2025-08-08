/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom15.repositories.impl;

import com.nhom15.pojo.Diem;
import com.nhom15.repositories.DiemRepository;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
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
public class DiemRepositoryImpl implements DiemRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public void saveOrUpdate(Diem diem) {
        Session s = this.factory.getObject().getCurrentSession();

        if (diem.getId() == null) {
            s.persist(diem);
        } else {
            s.merge(diem);
        }
    }

    @Override
    public List<Diem> getDiemByKhoaLuan(int khoaLuanId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Diem> getDiemByGiangVienAndKhoaLuan(int thanhVienId, int khoaLuanId) {
        Session s = factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Diem> q = b.createQuery(Diem.class);
        Root root = q.from(Diem.class);
        q.select(root);

        Predicate khoaLuanPredicate = b.equal(root.get("khoaLuanId").get("id"), khoaLuanId);
        Predicate thanhVienPredicate = b.equal(root.get("thanhvienId").get("id"), thanhVienId);

        q.where(b.and(khoaLuanPredicate, thanhVienPredicate));

        return s.createQuery(q).getResultList();
    }

    @Override
    public double getDiemTrungBinhKhoaLuan(int khoaLuanId) {
        Session s = factory.getObject().getCurrentSession();
        CriteriaBuilder cb = s.getCriteriaBuilder();
        CriteriaQuery<Double> cq = cb.createQuery(Double.class);
        Root root = cq.from(Diem.class);

        Expression avgDiem = cb.avg(root.get("diemSo"));

        cq.select(avgDiem).where(cb.equal(root.get("khoaLuanId").get("id"), khoaLuanId));

        Double result = s.createQuery(cq).getSingleResult();

        return result != null ? result : 0.0;
    }

    @Override
    public Diem getDiem(int thanhVienId, int khoaLuanId, int tieuChiId) {
        Session s = factory.getObject().getCurrentSession();
        CriteriaBuilder cb = s.getCriteriaBuilder();
        CriteriaQuery<Diem> cq = cb.createQuery(Diem.class);
        Root root = cq.from(Diem.class);

        cq.where(
                cb.equal(root.get("thanhvienId").get("id"), thanhVienId),
                cb.equal(root.get("khoaLuanId").get("id"), khoaLuanId),
                cb.equal(root.get("tieuChiId").get("id"), tieuChiId)
        );

        List<Diem> result = s.createQuery(cq).getResultList();

        return result.isEmpty() ? null : result.get(0);
    }
}
