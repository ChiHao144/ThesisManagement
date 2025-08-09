/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom15.repositories.impl;

import com.nhom15.pojo.TieuChi;
import com.nhom15.repositories.TieuChiRepository;
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
public class TieuChiRepositoryImpl implements TieuChiRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<TieuChi> getAllTieuChi() {
        Session s = this.factory.getObject().getCurrentSession();
        Query query = s.createNamedQuery("TieuChi.findAll", TieuChi.class);
        return query.getResultList();
    }

    @Override
    public TieuChi getTieuChiById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(TieuChi.class, id);
    }

    @Override
    public void addOrUpdateTieuChi(TieuChi tc) {
        Session s = this.factory.getObject().getCurrentSession();

        if (tc.getId() == null) {
            s.persist(tc);
        } else {
            s.merge(tc);
        }
    }

    @Override
    public void deleteTieuChi(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        TieuChi tc = this.getTieuChiById(id);
        if (tc != null) {
            s.remove(tc);
        }
    }

}
