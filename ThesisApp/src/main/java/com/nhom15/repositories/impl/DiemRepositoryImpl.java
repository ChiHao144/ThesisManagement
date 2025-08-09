/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom15.repositories.impl;

import com.nhom15.pojo.Diem;
import com.nhom15.pojo.HoiDong;
import com.nhom15.pojo.KhoaLuan;
import com.nhom15.pojo.ThanhVien;
import com.nhom15.repositories.DiemRepository;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

    public Map<String, Object> getDiemKhoaLuan(int khoaLuanId) {
        Session session = factory.getObject().getCurrentSession();

        KhoaLuan khoaLuan = session.get(KhoaLuan.class, khoaLuanId);
        if (khoaLuan == null) {
            return null;
        }

        HoiDong hoiDong = khoaLuan.getHoidongId();
        if (hoiDong == null) {
            return null;
        }

        Set<ThanhVien> thanhViens = hoiDong.getThanhVienSet();

        Map<ThanhVien, Double> diemTBThanhVienMap = new HashMap<>();
        double tongDiem = 0;
        int count = 0;

        CriteriaBuilder cb = session.getCriteriaBuilder();

        for (ThanhVien tv : thanhViens) {
            // 2. Tạo CriteriaQuery tính điểm trung bình của 1 ThanhVien trên 1 KhoaLuan
            CriteriaQuery<Double> cq = cb.createQuery(Double.class);
            Root<Diem> root = cq.from(Diem.class);

            cq.select(cb.avg(root.get("diem")));
            cq.where(
                    cb.equal(root.get("khoaLuanId").get("id"), khoaLuanId),
                    cb.equal(root.get("thanhvienId").get("id"), tv.getId())
            );

            Double diemTb = session.createQuery(cq).uniqueResult();
            if (diemTb == null) {
                diemTb = 0.0;
            }

            diemTBThanhVienMap.put(tv, diemTb);
            tongDiem += diemTb;
            count++;
        }

        Double diemTBKhoaLuan = count > 0 ? tongDiem / count : 0.0;

        Map<String, Object> result = new HashMap<>();
        result.put("khoaLuan", khoaLuan);
        result.put("diemTrungBinhThanhVien", diemTBThanhVienMap);
        result.put("diemTrungBinhKhoaLuan", diemTBKhoaLuan);

        return result;
    }

}
