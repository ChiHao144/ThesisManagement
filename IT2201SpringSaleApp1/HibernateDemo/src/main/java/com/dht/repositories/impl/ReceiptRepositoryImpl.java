/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dht.repositories.impl;

import com.dht.hibernatedemo.HibernateConfigs;
import com.dht.pojo.Cart;
import com.dht.pojo.OrderDetail;
import com.dht.pojo.SaleOrder;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author admin
 */
public class ReceiptRepositoryImpl {
    public void addReceipt(List<Cart> carts) {
        if (carts != null) {
            try (Session s = HibernateConfigs.getFACTORY().openSession()) {
                SaleOrder order = new SaleOrder();
                order.setUserId(new UserRepositoryImpl().getUserByUsername("dhthanh"));
                order.setCreatedDate(new Date());
                s.persist(order);
                
                for (var x: carts) {
                    OrderDetail d = new OrderDetail();
                    d.setQuantity(x.getQuantity());
                    d.setUnitPrice(x.getPrice());
                    d.setProductId(new ProductRepositoryImpl().getProductById(x.getId()));
                    d.setOrderId(order);
                    
                    s.persist(d);
                }
            }
            
        }
    }
}
