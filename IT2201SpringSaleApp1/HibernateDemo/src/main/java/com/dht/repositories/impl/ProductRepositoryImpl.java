/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dht.repositories.impl;

import com.dht.hibernatedemo.HibernateConfigs;
import com.dht.pojo.Product;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;

/**
 *
 * @author admin
 */
public class ProductRepositoryImpl {
    private static final int PAGE_SIZE = 6;
    
    public List<Product> getProducts(Map<String, String> params) {
        try (Session s = HibernateConfigs.getFACTORY().openSession()) {
            CriteriaBuilder b = s.getCriteriaBuilder();
            CriteriaQuery<Product> q = b.createQuery(Product.class);
            Root root = q.from(Product.class);
            q.select(root);
            
           
            if (params != null) {
                 // Loc du lieu
                List<Predicate> predcates = new ArrayList<>();
                
                String kw = params.get("kw");
                if (kw != null && !kw.isEmpty()) 
                    predcates.add(b.like(root.get("name"), String.format("%%%s%%", kw)));
                
                String fromPrice = params.get("fromPrice");
                if (fromPrice != null && !fromPrice.isEmpty())
                    predcates.add(b.greaterThanOrEqualTo(root.get("price"), fromPrice));
                
                String toPrice = params.get("toPrice");
                if (toPrice != null && !toPrice.isEmpty())
                    predcates.add(b.lessThanOrEqualTo(root.get("price"), toPrice));
                
                String cateId = params.get("cateId");
                if (cateId != null && !cateId.isEmpty())
                    predcates.add(b.equal(root.get("category").as(Integer.class), cateId));
                
                q.where(predcates.toArray(Predicate[]::new ));
                
                // Sap xep du lieu
                q.orderBy(b.desc(root.get(params.getOrDefault("sortBy", "id"))));
            }
            
            Query query = s.createQuery(q);
            
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
    }
    
    public Product getProductById(int id) {
        try (Session s = HibernateConfigs.getFACTORY().openSession()) {
            return s.get(Product.class, id);
        }
    }
    
    public void addOrUpdateProduct(Product p) {
        try (Session s = HibernateConfigs.getFACTORY().openSession()) {
            if (p.getId() == null) {
                s.persist(p);
            } else {
                s.merge(p);
            }
        }
    }
    
    public void deleteProduct(int id) {
        try (Session s = HibernateConfigs.getFACTORY().openSession()) {
            Product p = this.getProductById(id);
            s.remove(p);
        }
    }
}
