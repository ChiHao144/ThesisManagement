/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom15.controllers;

import com.nhom15.pojo.KhoaLuan;
import com.nhom15.services.KhoaLuanService;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author ACER
 */
@Controller
public class HomeController {
    @Autowired
    private KhoaLuanService khoaLuanService;
    
    @RequestMapping("/")
    @Transactional
    public String index(Model model){
        model.addAttribute("khoa_luans", this.khoaLuanService.getKhoaLuans());
        
        return "index";
    }
}
