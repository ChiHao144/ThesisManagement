/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom15.repositories.impl;

import com.nhom15.pojo.GiangVien;
import com.nhom15.pojo.HoiDong;
import com.nhom15.pojo.KhoaLuan;
import com.nhom15.pojo.ThanhVien;
import com.nhom15.repositories.HoiDongRepository;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashSet;
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
public class HoiDongRepositoryImpl implements HoiDongRepository {

    @Autowired
    private LocalSessionFactoryBean factory;
    private static final int PAGE_SIZE = 6;

    @Override
    public HoiDong getHoiDongById(int id) {
        Session session = factory.getObject().getCurrentSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<HoiDong> cq = cb.createQuery(HoiDong.class);
        Root root = cq.from(HoiDong.class);

        root.fetch("thanhVienSet", JoinType.LEFT);
        root.fetch("khoaLuanSet", JoinType.LEFT);

        cq.select(root).distinct(true).where(cb.equal(root.get("id"), id));

        Query query = session.createQuery(cq);
        return (HoiDong) query.getSingleResult();
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
            List<Predicate> predicates = new ArrayList<>();

            String kw = params.get("kw");
            if (kw != null && !kw.isEmpty()) {
                predicates.add(cb.like(root.get("noiDungBaoVe"), String.format("%%%s%%", kw)));
            }

            String daKhoa = params.get("daKhoa");
            if (daKhoa != null && !daKhoa.isEmpty()) {
                boolean isLocked = Boolean.parseBoolean(daKhoa);
                predicates.add(cb.equal(root.get("daKhoa"), isLocked));
            }

            cq.where(predicates.toArray(Predicate[]::new));

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
    public void addOrUpdateHoiDong(HoiDong hd, List<Integer> giangVienIds, List<String> vaiTros, List<Integer> khoaLuanIds) {
        Session session = this.factory.getObject().getCurrentSession();

        if (hd.getId() == null) {
            session.persist(hd);
            session.flush();
        } else {
            session.merge(hd);
        }

        CriteriaBuilder cbTv = session.getCriteriaBuilder();
        CriteriaQuery<ThanhVien> cqTv = cbTv.createQuery(ThanhVien.class);
        Root<ThanhVien> rootTv = cqTv.from(ThanhVien.class);
        cqTv.select(rootTv).where(cbTv.equal(rootTv.get("hoiDongId").get("id"), hd.getId()));
        List<ThanhVien> oldThanhViens = session.createQuery(cqTv).getResultList();

        Set<Integer> newGiangVienIdsSet = new HashSet<>(giangVienIds != null ? giangVienIds : List.of());

        for (ThanhVien tv : oldThanhViens) {
            if (newGiangVienIdsSet.contains(tv.getGvId().getId())) {
                int index = giangVienIds.indexOf(tv.getGvId().getId());
                if (index != -1 && index < vaiTros.size()) {
                    tv.setVaiTro(vaiTros.get(index));
                    session.merge(tv);
                }
                newGiangVienIdsSet.remove(tv.getGvId().getId());
            } else {
                tv.setHoiDongId(null);
                session.merge(tv);
            }
        }

        for (int i = 0; i < giangVienIds.size(); i++) {
            Integer gvId = giangVienIds.get(i);
            String vaiTro = vaiTros.get(i);

            if (newGiangVienIdsSet.contains(gvId)) {
                GiangVien gv = session.get(GiangVien.class, gvId);
                if (gv != null) {
                    ThanhVien newTv = new ThanhVien();
                    newTv.setGvId(gv);
                    newTv.setHoiDongId(hd);
                    newTv.setVaiTro(vaiTro);
                    session.persist(newTv);
                }
            }
        }

        CriteriaBuilder cbKl = session.getCriteriaBuilder();
        CriteriaQuery<KhoaLuan> cqKl = cbKl.createQuery(KhoaLuan.class);
        Root<KhoaLuan> rootKl = cqKl.from(KhoaLuan.class);
        cqKl.select(rootKl).where(cbKl.equal(rootKl.get("hoidongId").get("id"), hd.getId()));
        List<KhoaLuan> oldKhoaLuans = session.createQuery(cqKl).getResultList();

        Set<Integer> newKhoaLuanIdsSet = new HashSet<>(khoaLuanIds != null ? khoaLuanIds : List.of());

        for (KhoaLuan kl : oldKhoaLuans) {
            if (!newKhoaLuanIdsSet.contains(kl.getId())) {
                kl.setHoidongId(null);
                session.merge(kl);
            }
        }

        if (khoaLuanIds != null) {
            for (Integer klId : khoaLuanIds) {
                if (oldKhoaLuans.stream().noneMatch(kl -> kl.getId().equals(klId))) {
                    KhoaLuan kl = session.get(KhoaLuan.class, klId);
                    if (kl != null) {
                        kl.setHoidongId(hd);
                        session.merge(kl);
                    }
                }
            }
        }

        session.flush();
    }

    @Override
    public void deleteHoiDong(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        HoiDong kl = this.getHoiDongById(id);
        if (kl != null) {
            s.remove(kl);
        }
    }

    @Override
    public boolean isHoiDongDaKhoa(int hoiDongId) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();

        CriteriaQuery<Boolean> cq = cb.createQuery(Boolean.class);
        Root<HoiDong> root = cq.from(HoiDong.class);

        cq.select(root.get("daKhoa"))
                .where(cb.equal(root.get("id"), hoiDongId));

        Boolean daKhoa = session.createQuery(cq).getSingleResult();
        return Boolean.TRUE.equals(daKhoa);
    }

    @Override
    public List<HoiDong> getHoiDongsByGiangVien(int giangVienId, Map<String, String> params) {
        Session session = factory.getObject().getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();

        CriteriaQuery<HoiDong> cq = cb.createQuery(HoiDong.class);
        Root<ThanhVien> thanhVienRoot = cq.from(ThanhVien.class);

        Join<ThanhVien, HoiDong> hoiDongJoin = thanhVienRoot.join("hoiDongId");
        hoiDongJoin.fetch("thanhVienSet", JoinType.LEFT);

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(cb.equal(thanhVienRoot.get("gvId").get("id"), giangVienId));

        if (params != null) {
            String kw = params.get("kw");
            if (kw != null && !kw.isEmpty()) {
                predicates.add(cb.like(hoiDongJoin.get("noiDungBaoVe"), String.format("%%%s%%", kw)));
            }

            String daKhoa = params.get("daKhoa");
            if (daKhoa != null && !daKhoa.isEmpty()) {
                boolean isLocked = Boolean.parseBoolean(daKhoa);
                predicates.add(cb.equal(hoiDongJoin.get("daKhoa"), isLocked));
            }

            String sortBy = params.getOrDefault("sortBy", "id");
            cq.orderBy(cb.desc(hoiDongJoin.get(sortBy)));
        }

        cq.select(hoiDongJoin).distinct(true).where(predicates.toArray(Predicate[]::new));

        Query query = session.createQuery(cq);

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

}
