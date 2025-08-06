/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom15.repositories.impl;

import com.nhom15.pojo.ThongBao;
import com.nhom15.repositories.ThongBaoRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ACER
 */
@Repository
public class ThongBaoRepositoryImpl implements ThongBaoRepository {
    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public void addThongBao(ThongBao tb) {
        Session s = this.factory.getObject().getCurrentSession();

        s.persist(tb);
    }

}
