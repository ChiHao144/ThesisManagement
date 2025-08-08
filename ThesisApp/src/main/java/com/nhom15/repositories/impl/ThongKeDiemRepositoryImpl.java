package com.nhom15.repositories.impl;

import com.nhom15.pojo.Diem;
import com.nhom15.pojo.GiangVien;
import com.nhom15.pojo.KhoaLuan;
import com.nhom15.pojo.SinhVien;
import com.nhom15.pojo.ThanhVien;
import com.nhom15.pojo.User;
import com.nhom15.repositories.ThongKeDiemRepository;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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
public class ThongKeDiemRepositoryImpl implements ThongKeDiemRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Object[]> getTongDiemTheoGiangVien(int khoaLuanId) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();

        CriteriaQuery<Object[]> q = b.createQuery(Object[].class);
        Root<Diem> r = q.from(Diem.class);

        // Diem -> ThanhVien
        Join<Diem, ThanhVien> tv = r.join("thanhvienId");
        // ThanhVien -> GiangVien
        Join<ThanhVien, GiangVien> gv = tv.join("gvId");
        // GiangVien -> User
        Join<GiangVien, User> u = gv.join("user");
        // Diem -> KhoaLuan
        Join<Diem, KhoaLuan> kl = r.join("khoaLuanId");

        q.multiselect(
                gv.get("id"), // ID giảng viên
                u.get("fullname"), // Lấy tên từ bảng user
                b.sum(r.get("diem")) // Tổng điểm
        );
        q.where(b.equal(kl.get("id"), khoaLuanId));
        q.groupBy(gv.get("id"), u.get("fullname"));

        Query query = s.createQuery(q);
        return query.getResultList();
    }

    @Override
    public Double getDiemTrungBinh(int khoaLuanId) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();

        CriteriaQuery<Double> q = b.createQuery(Double.class);
        Root<Diem> r = q.from(Diem.class);

        Join<Diem, ThanhVien> tv = r.join("thanhvienId");
        Join<ThanhVien, GiangVien> gv = tv.join("gvId");
        Join<GiangVien, User> u = gv.join("user");
        Join<Diem, KhoaLuan> kl = r.join("khoaLuanId");

        // Lấy tổng điểm mỗi giảng viên
        q.select(b.sum(r.get("diem")));
        q.where(b.equal(kl.get("id"), khoaLuanId));
        q.groupBy(u.get("id"));

        List<Double> tongDiemList = s.createQuery(q).getResultList();

        if (tongDiemList.isEmpty()) {
            return 0.0;
        }

        // Tính trung bình trong Java
        double sum = tongDiemList.stream().mapToDouble(Double::doubleValue).sum();
        return sum / tongDiemList.size();
    }

    public List<Object[]> getTongDiemThanhVien() {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();

        CriteriaQuery<Object[]> q = b.createQuery(Object[].class);
        Root<Diem> r = q.from(Diem.class);

        Join<Diem, KhoaLuan> kl = r.join("khoaLuanId");
        Join<Diem, ThanhVien> tv = r.join("thanhvienId");

        q.multiselect(
                kl.get("id"),
                tv.get("id"),
                b.sum(r.get("diem"))
        );

        q.groupBy(kl.get("id"), tv.get("id"));

        return s.createQuery(q).getResultList();
    }

    @Override
    public List<Object[]> thongKeDiemKhoaLuanTheoNam() {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder cb = s.getCriteriaBuilder();

        // Subquery 1: Tổng điểm từng thành viên trong mỗi khóa luận theo năm
        CriteriaQuery<Tuple> q1 = cb.createTupleQuery();
        Root<Diem> r1 = q1.from(Diem.class);
        Join<Diem, KhoaLuan> kl1 = r1.join("khoaLuanId");
        Join<Diem, ThanhVien> tv1 = r1.join("thanhvienId");

        Expression<Integer> nam1 = cb.function("YEAR", Integer.class, kl1.get("ngayTao"));
        Expression<Double> tongDiemThanhVien = cb.sum(r1.get("diem"));

        q1.multiselect(
                kl1.get("id").alias("khoaLuanId"),
                nam1.alias("nam"),
                tv1.get("id").alias("thanhVienId"),
                tongDiemThanhVien.alias("tongDiem")
        )
                .groupBy(kl1.get("id"), nam1, tv1.get("id"));

        List<Tuple> tongDiemList = s.createQuery(q1).getResultList();

        // Xử lý trung gian trên Java
        Map<Integer, List<Double>> khoaLuanTheoNam = tongDiemList.stream()
                .collect(Collectors.groupingBy(
                        t -> (Integer) t.get("nam"),
                        Collectors.groupingBy(
                                t -> (Integer) t.get("khoaLuanId"),
                                Collectors.mapping(
                                        t -> ((Number) t.get("tongDiem")).doubleValue(),
                                        Collectors.toList()
                                )
                        )
                ))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().values().stream()
                                .map(list -> list.stream().mapToDouble(Double::doubleValue).average().orElse(0.0))
                                .collect(Collectors.toList())
                ));

        // Tính điểm trung bình của tất cả khóa luận theo từng năm
        List<Object[]> result = khoaLuanTheoNam.entrySet().stream()
                .map(e -> new Object[]{
            e.getKey(),
            e.getValue().stream().mapToDouble(Double::doubleValue).average().orElse(0.0)
        })
                .sorted(Comparator.comparing(o -> (Integer) o[0]))
                .collect(Collectors.toList());

        return result;
    }

    @Override
    public List<Object[]> thongKeTanSuatKhoaLuanTheoKhoa() {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder cb = s.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);

        Root<KhoaLuan> klRoot = cq.from(KhoaLuan.class);
        Join<KhoaLuan, SinhVien> svJoin = klRoot.join("sinhVienId");  // join đến sinh viên

        // Lấy trường khoa trong SinhVien
        Expression<String> khoaExp = svJoin.get("khoa");
        Expression<Long> countExp = cb.count(klRoot.get("id"));

        cq.multiselect(khoaExp, countExp);

        // Điều kiện: khoa không null và không rỗng
        cq.where(
                cb.isNotNull(khoaExp),
                cb.notEqual(khoaExp, "")
        );

        cq.groupBy(khoaExp);
        cq.orderBy(cb.desc(countExp));

        return s.createQuery(cq).getResultList();
    }
}
