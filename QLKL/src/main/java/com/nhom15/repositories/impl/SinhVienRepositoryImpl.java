/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom15.repositories.impl;

import com.nhom15.pojo.SinhVien;
import com.nhom15.repositories.SinhVienRepository;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
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
public class SinhVienRepositoryImpl implements SinhVienRepository {

    @Autowired
    private LocalSessionFactoryBean factory;
    private static final int PAGE_SIZE = 6;
    
    @Override
    public List<SinhVien> getSinhViens() {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM SinhVien", SinhVien.class);
        return q.getResultList();
    }

    @Override
    public SinhVien getSinhVienById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(SinhVien.class, id);
    }

}
