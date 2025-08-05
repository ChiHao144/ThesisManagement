/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom15.repositories.impl;

import com.nhom15.pojo.GiangVien;
import com.nhom15.repositories.GiangVienRepository;
import jakarta.persistence.Query;
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
public class GiangVienRepositoryImpl implements GiangVienRepository {

    @Autowired
    private LocalSessionFactoryBean factory;
    private static final int PAGE_SIZE = 6;

    @Override
    public List<GiangVien> getGiangViens() {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM GiangVien", GiangVien.class);
        return q.getResultList();
    }

    @Override
    public GiangVien getGiangVienById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(GiangVien.class, id);
    }
}
