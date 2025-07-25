package com.pch.repositories.impl;

import com.pch.hibernatethesismanagement.HibernateConfigs;
import com.pch.pojo.SinhVien;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;
import org.hibernate.query.QueryLogging;

/**
 *
 * @author Chi Hao
 */
public class SinhVienRepositoryImpl {
    public List<SinhVien> getSinhViens(Map<String, String> params) {
        try (Session s = HibernateConfigs.getFACTORY().openSession()) {
            CriteriaBuilder b = s.getCriteriaBuilder();
            CriteriaQuery<SinhVien> q = b.createQuery(SinhVien.class);
            Root root = q.from(SinhVien.class);
            q.select(root);
            
            // loc du lieu
            
            // sap xep du lieu
            
            Query query = s.createQuery(q);
            
            //phan trang du lieu
            
            return query.getResultList();
        }
    }
}
