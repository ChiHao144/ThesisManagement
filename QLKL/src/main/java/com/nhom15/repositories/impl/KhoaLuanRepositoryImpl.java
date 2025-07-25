/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom15.repositories.impl;

import com.nhom15.configs.HibernateConfigs;
import com.nhom15.pojo.KhoaLuan;
import com.nhom15.repositories.KhoaLuanRepository;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
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
    public List<KhoaLuan> getKhoaLuans(Map<String, String> params){
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder k = s.getCriteriaBuilder();
        CriteriaQuery<KhoaLuan> q = k.createQuery(KhoaLuan.class);
        Root root = q.from(KhoaLuan.class);
        q.select(root);
        
        //Lọc dữ liệu
        
        //Sắp xếp
        
        Query query = s.createQuery(q);
        
        //Phân trang
        return query.getResultList();
    }
}
