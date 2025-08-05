/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom15.repositories.impl;

import com.nhom15.configs.HibernateConfigs;
import com.nhom15.pojo.Diem;
import com.nhom15.pojo.GiangVien;
import com.nhom15.pojo.HoiDong;
import com.nhom15.pojo.KhoaLuan;
import com.nhom15.pojo.SinhVien;
import com.nhom15.pojo.ThanhVien;
import com.nhom15.pojo.User;
import com.nhom15.repositories.KhoaLuanRepository;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.SetJoin;
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
public class KhoaLuanRepositoryImpl implements KhoaLuanRepository {

    @Autowired
    private LocalSessionFactoryBean factory;
    private static final int PAGE_SIZE = 6;

    @Override
    public List<KhoaLuan> getKhoaLuans(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder k = s.getCriteriaBuilder();
        CriteriaQuery<KhoaLuan> q = k.createQuery(KhoaLuan.class);
        Root root = q.from(KhoaLuan.class);
        q.select(root);

        //Lọc dữ liệu
        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();

            // JOIN các bảng liên quan
            Join<KhoaLuan, SinhVien> svJoin = root.join("sinhVienId", JoinType.LEFT);
            Join<SinhVien, User> svUserJoin = svJoin.join("user", JoinType.LEFT);

            Join<KhoaLuan, GiangVien> gvhd1Join = root.join("gvhd1", JoinType.LEFT);
            Join<GiangVien, User> gvhd1UserJoin = gvhd1Join.join("user", JoinType.LEFT);

            Join<KhoaLuan, GiangVien> gvhd2Join = root.join("gvhd2", JoinType.LEFT);
            Join<GiangVien, User> gvhd2UserJoin = gvhd2Join.join("user", JoinType.LEFT);

            Join<KhoaLuan, GiangVien> gvpbJoin = root.join("gvpb", JoinType.LEFT);
            Join<GiangVien, User> gvpbUserJoin = gvpbJoin.join("user", JoinType.LEFT);

            // Tìm theo từ khóa (chủ đề, tên SV, GVHD, GVPB)
            String kw = params.get("kw");
            if (kw != null && !kw.isEmpty()) {
                String pattern = "%" + kw + "%";

                Predicate byChuDe = k.like(root.get("chuDe"), pattern);
                Predicate bySinhVien = k.like(svUserJoin.get("fullname"), pattern);
                Predicate byGVHD1 = k.like(gvhd1UserJoin.get("fullname"), pattern);
                Predicate byGVHD2 = k.like(gvhd2UserJoin.get("fullname"), pattern);
                Predicate byGVPB = k.like(gvpbUserJoin.get("fullname"), pattern);

                predicates.add(k.or(byChuDe, bySinhVien, byGVHD1, byGVHD2, byGVPB));
            }

            //Lọc theo năm tạo
            String nam = params.get("nam");
            if (nam != null && !nam.isEmpty()) {
                predicates.add(k.equal(k.function("YEAR", Integer.class, root.get("ngayTao")), Integer.parseInt(nam)));
            }

            q.where(predicates.toArray(Predicate[]::new));

            //Sắp xếp theo ngày tạo mới nhất
            q.orderBy(k.desc(root.get(params.getOrDefault("sortBy", "ngayTao"))));
        }

        Query query = s.createQuery(q);

        //Phân trang
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
    public KhoaLuan getKhoaLuanById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(KhoaLuan.class, id);
    }

    @Override
    public void addOrUpdateKhoaLuan(KhoaLuan kl) {
        Session s = this.factory.getObject().getCurrentSession();

        if (kl.getId() == null) {
            s.persist(kl);
        } else {
            s.merge(kl);
        }
    }

    @Override
    public void deleteKhoaLuan(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        KhoaLuan kl = this.getKhoaLuanById(id);
        if (kl != null) {
            s.remove(kl);
        }
    }

//    @Override
//    public KhoaLuan getKhoaLuanChiTiet(int id) {
//        Session s = this.factory.getObject().getCurrentSession();
//
//        CriteriaBuilder cb = s.getCriteriaBuilder();
//        CriteriaQuery<KhoaLuan> cq = cb.createQuery(KhoaLuan.class);
//        Root<KhoaLuan> root = cq.from(KhoaLuan.class);
//
//        root.fetch("sinhVienId", JoinType.LEFT);
//        root.fetch("gvhd1", JoinType.LEFT);
//        root.fetch("gvhd2", JoinType.LEFT);
//        root.fetch("gvpb", JoinType.LEFT);
//        root.fetch("hoidongId", JoinType.LEFT);
//
//        SetJoin<KhoaLuan, Diem> diemJoin = root.joinSet("diemSet", JoinType.LEFT);
//        diemJoin.fetch("thanhvienId", JoinType.LEFT)
//                .fetch("giangVien", JoinType.LEFT);
//        diemJoin.fetch("tieuChiId", JoinType.LEFT);
//
//        cq.select(root).where(cb.equal(root.get("id"), id));
//
//        return s.createQuery(cq).uniqueResult();
//    }
//    @Override
//    public List<KhoaLuan> getKhoaLuansByGiangVienId(Map<String, String> params) {
//        Session s = this.factory.getObject().getCurrentSession();
//        CriteriaBuilder k = s.getCriteriaBuilder();
//        CriteriaQuery<KhoaLuan> q = k.createQuery(KhoaLuan.class);
//        Root<KhoaLuan> root = q.from(KhoaLuan.class);
//        q.select(root);
//
//        List<Predicate> predicates = new ArrayList<>();
//
//        String gvId = params.get("gvId");
//        if (gvId != null && !gvId.isEmpty()) {
//            Predicate p1 = k.equal(root.get("gvhd1").get("id"), Integer.parseInt(gvId));
//            Predicate p2 = k.equal(root.get("gvhd2").get("id"), Integer.parseInt(gvId));
//            Predicate p3 = k.equal(root.get("gvpb").get("id"), Integer.parseInt(gvId));
//            predicates.add(k.or(p1, p2, p3));
//        }
//
//        String kw = params.get("kw");
//        if (kw != null && !kw.isEmpty()) {
//            predicates.add(k.like(root.get("chuDe"), "%" + kw + "%"));
//        }
//
//        String nam = params.get("nam");
//        if (nam != null && !nam.isEmpty()) {
//            predicates.add(k.equal(k.function("YEAR", Integer.class, root.get("ngayTao")), Integer.parseInt(nam)));
//        }
//
//        q.where(predicates.toArray(Predicate[]::new));
//        q.orderBy(k.desc(root.get(params.getOrDefault("sortBy", "ngayTao"))));
//
//        Query query = s.createQuery(q);
//
//        String page = params.get("page");
//        if (page != null) {
//            int start = (Integer.parseInt(page) - 1) * PAGE_SIZE;
//            query.setFirstResult(start);
//            query.setMaxResults(PAGE_SIZE);
//        }
//
//        return query.getResultList();
//    }
//    @Override
//    public List<KhoaLuan> getKhoaLuansChuaCoHoiDong(Map<String, String> params) {
//        Session s = this.factory.getObject().getCurrentSession();
//        CriteriaBuilder k = s.getCriteriaBuilder();
//        CriteriaQuery<KhoaLuan> q = k.createQuery(KhoaLuan.class);
//        Root<KhoaLuan> root = q.from(KhoaLuan.class);
//        q.select(root);
//
//        List<Predicate> predicates = new ArrayList<>();
//
//        // điều kiện: chưa có hội đồng
//        predicates.add(k.isNull(root.get("hoidongId")));
//
//        // lọc theo từ khóa chủ đề (nếu có)
//        String kw = params.get("kw");
//        if (kw != null && !kw.isEmpty()) {
//            predicates.add(k.like(root.get("chuDe"), "%" + kw + "%"));
//        }
//
//        // lọc theo năm tạo (nếu có)
//        String nam = params.get("nam");
//        if (nam != null && !nam.isEmpty()) {
//            predicates.add(k.equal(k.function("YEAR", Integer.class, root.get("ngayTao")), Integer.parseInt(nam)));
//        }
//
//        q.where(predicates.toArray(Predicate[]::new));
//        q.orderBy(k.desc(root.get(params.getOrDefault("sortBy", "ngayTao"))));
//
//        Query query = s.createQuery(q);
//
//        // phân trang (nếu có)
//        String page = params.get("page");
//        if (page != null) {
//            int start = (Integer.parseInt(page) - 1) * PAGE_SIZE;
//            query.setFirstResult(start);
//            query.setMaxResults(PAGE_SIZE);
//        }
//
//        return query.getResultList();
//    }
    @Override
    public void unassignHoiDong(int hoiDongId) {
        Session s = factory.getObject().getCurrentSession();

        CriteriaBuilder cb = s.getCriteriaBuilder();
        CriteriaUpdate<KhoaLuan> cu = cb.createCriteriaUpdate(KhoaLuan.class);
        Root<KhoaLuan> root = cu.from(KhoaLuan.class);

        cu.set("hoidongId", (HoiDong) null);
        cu.where(cb.equal(root.get("hoidongId").get("id"), hoiDongId));

        s.createMutationQuery(cu).executeUpdate();
    }

    @Override
    public void assignKhoaLuanToHoiDong(int klId, HoiDong hoiDong) {
        Session session = factory.getObject().getCurrentSession();
        KhoaLuan kl = session.get(KhoaLuan.class, klId);
        if (kl != null) {
            kl.setHoidongId(hoiDong);
            session.merge(kl);
        }
    }

    @Override
    public void clearHoiDongOfKhoaLuan(Integer hoiDongId) {
        Session session = factory.getObject().getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();

        CriteriaUpdate<KhoaLuan> update = cb.createCriteriaUpdate(KhoaLuan.class);
        Root<KhoaLuan> root = update.from(KhoaLuan.class);

        update.set("hoidongId", (HoiDong) null); // Xoá liên kết
        update.where(cb.equal(root.get("hoidongId").get("id"), hoiDongId));

        session.createMutationQuery(update).executeUpdate();
    }
}
