/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom15.repositories.impl;

import com.nhom15.pojo.HoiDong;
import com.nhom15.pojo.KhoaLuan;
import com.nhom15.pojo.ThanhVien;
import com.nhom15.repositories.HoiDongRepository;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
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
public class HoiDongRepositoryImpl implements HoiDongRepository {

    @Autowired
    private LocalSessionFactoryBean factory;
    private static final int PAGE_SIZE = 6;

    @Override
    public HoiDong getHoiDongById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(HoiDong.class, id);
    }

    @Override
    public List<HoiDong> getHoiDongs(Map<String, String> params) {
        Session s = factory.getObject().getCurrentSession();
        CriteriaBuilder cb = s.getCriteriaBuilder();
        CriteriaQuery<HoiDong> cq = cb.createQuery(HoiDong.class);
        Root root = cq.from(HoiDong.class);
        root.fetch("thanhVienSet", JoinType.LEFT);
        cq.select(root).distinct(true);

        if (params != null) {
            // Loc du lieu
            List<Predicate> predicates = new ArrayList<>();

            String kw = params.get("kw");
            if (kw != null && !kw.isEmpty()) {
                predicates.add(cb.like(root.get("noiDungBaoVe"), String.format("%%%s%%", kw)));
            }

            // Lọc theo "daKhoa" (true/false)
            String daKhoa = params.get("daKhoa");
            if (daKhoa != null && !daKhoa.isEmpty()) {
                // parse Boolean từ chuỗi
                boolean isLocked = Boolean.parseBoolean(daKhoa);
                predicates.add(cb.equal(root.get("daKhoa"), isLocked));
            }

            cq.where(predicates.toArray(Predicate[]::new));

            // Sap xep du lieu
            cq.orderBy(cb.desc(root.get(params.getOrDefault("sortBy", "id"))));
        }

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
    public void addOrUpdateHoiDong(HoiDong hd, List<ThanhVien> thanhVienList, List<Integer> khoaLuanIds) {
        Session session = this.factory.getObject().getCurrentSession();

        if (hd.getId() == null) {
            session.persist(hd);
        } else {
            session.merge(hd);

            // 1. Xoá các liên kết cũ của ThanhVien
            CriteriaBuilder cb = session.getCriteriaBuilder();

            CriteriaQuery<ThanhVien> cqTV = cb.createQuery(ThanhVien.class);
            Root<ThanhVien> rootTV = cqTV.from(ThanhVien.class);
            cqTV.select(rootTV).where(cb.equal(rootTV.get("hoiDongId").get("id"), hd.getId()));
            List<ThanhVien> tvList = session.createQuery(cqTV).getResultList();

            for (ThanhVien tv : tvList) {
                tv.setHoiDongId(null);
                session.merge(tv);
            }

            // 2. Xoá các liên kết cũ của KhoaLuan
            CriteriaQuery<KhoaLuan> cqKL = cb.createQuery(KhoaLuan.class);
            Root<KhoaLuan> rootKL = cqKL.from(KhoaLuan.class);
            cqKL.select(rootKL).where(cb.equal(rootKL.get("hoidongId").get("id"), hd.getId()));
            List<KhoaLuan> klList = session.createQuery(cqKL).getResultList();

            for (KhoaLuan kl : klList) {
                kl.setHoidongId(null);
                session.merge(kl);
            }
        }

        for (ThanhVien tv : thanhVienList) {
            tv.setHoiDongId(hd);
            session.persist(tv);
        }

        for (Integer klId : khoaLuanIds) {
            KhoaLuan kl = session.get(KhoaLuan.class, klId);
            if (kl != null) {
                kl.setHoidongId(hd);
                session.merge(kl);
            }
        }
    }

    @Override
    public void deleteHoiDong(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        HoiDong kl = this.getHoiDongById(id);
        if (kl != null) {
            s.remove(kl);
        }
    }
}
