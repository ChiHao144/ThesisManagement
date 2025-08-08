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
    public List<Object[]> thongKeDiemKhoaLuanTheoNam() {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder cb = s.getCriteriaBuilder();

        CriteriaQuery<Tuple> q1 = cb.createTupleQuery();
        Root<Diem> r1 = q1.from(Diem.class);
        Join<Diem, KhoaLuan> kl1 = r1.join("khoaLuanId");
        Join<Diem, ThanhVien> tv1 = r1.join("thanhvienId");

        Expression<Integer> nam1 = cb.function("YEAR", Integer.class, kl1.get("ngayTao"));
        Expression<Double> tongDiemThanhVien = cb.sum(r1.get("diem"));

        q1.multiselect(kl1.get("id").alias("khoaLuanId"), nam1.alias("nam"),
                tv1.get("id").alias("thanhVienId"), tongDiemThanhVien.alias("tongDiem")
        ).groupBy(kl1.get("id"), nam1, tv1.get("id"));

        List<Tuple> tongDiemList = s.createQuery(q1).getResultList();

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
        Join<KhoaLuan, SinhVien> svJoin = klRoot.join("sinhVienId"); 

        
        Expression<String> khoaExp = svJoin.get("khoa");
        Expression<Long> countExp = cb.count(klRoot.get("id"));

        cq.multiselect(khoaExp, countExp);

        cq.where(
                cb.isNotNull(khoaExp),
                cb.notEqual(khoaExp, "")
        );

        cq.groupBy(khoaExp);
        cq.orderBy(cb.desc(countExp));

        return s.createQuery(cq).getResultList();
    }
}
