//package com.pch.repositories.impl;
//
//import com.pch.hibernatethesismanagement.HibernateConfigs;
//import com.pch.pojo.KhoaLuan;
//import jakarta.persistence.Query;
//import java.util.List;
//import org.hibernate.Session;
//
///**
// *
// * @author Chi Hao
//// *
//public class KhoaLuanRepositoryImpl {
//    public List<KhoaLuan> getKhoaLuans() {
//        try(Session s = HibernateConfigs.getFACTORY().openSession()){
//            Query q = s.createQuery("FROM KhoaLuan", KhoaLuan.class);
//            return q.getResultList();
//        }
//    }
//}
