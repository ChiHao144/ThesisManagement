/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dht.repositories.impl;

import com.dht.hibernatedemo.HibernateConfigs;
import com.dht.pojo.OrderDetail;
import com.dht.pojo.Product;
import com.dht.pojo.SaleOrder;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author admin
 */
public class StatsRepositoryImpl {

    public List<Object[]> statsRevenueByProduct() {
        try ( Session s = HibernateConfigs.getFACTORY().openSession()) {
            CriteriaBuilder b = s.getCriteriaBuilder();
            CriteriaQuery<Object[]> q = b.createQuery(Object[].class);
            Root r = q.from(OrderDetail.class);
            Join<OrderDetail, Product> join = r.join("productId");

            q.multiselect(join.get("id"), join.get("name"),
                    b.sum(b.prod(r.get("quantity"), r.get("unitPrice"))));
            q.groupBy(join.get("id"));

            Query query = s.createQuery(q);
            return query.getResultList();
        }
    }

    public List<Object[]> statsRevenueByTime(String time, int year) {
        try ( Session s = HibernateConfigs.getFACTORY().openSession()) {
            CriteriaBuilder b = s.getCriteriaBuilder();
            CriteriaQuery<Object[]> q = b.createQuery(Object[].class);
            Root r = q.from(OrderDetail.class);
            Join<OrderDetail, SaleOrder> join = r.join("orderId");

            q.multiselect(b.function(time, Integer.class, join.get("createdDate")),
                    b.sum(b.prod(r.get("quantity"), r.get("unitPrice"))));
            q.where(b.equal(b.function("YEAR", Integer.class, join.get("createdDate")), year));
            q.groupBy(b.function(time, Integer.class, join.get("createdDate")));

            Query query = s.createQuery(q);
            return query.getResultList();
        }
    }
}
