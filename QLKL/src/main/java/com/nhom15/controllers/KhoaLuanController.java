/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom15.controllers;

import com.nhom15.pojo.KhoaLuan;
import com.nhom15.services.KhoaLuanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author ACER
 */
@Controller
public class KhoaLuanController {

    @Autowired
    private KhoaLuanService khoaLuanService;

    @GetMapping("/khoaluans")
    public String listKhoaLuans(Model model) {
        model.addAttribute("khoaluan", new KhoaLuan());
        return "khoaluans";
    }

    @PostMapping("/khoaluans")
    public String addKhoaLuan(@ModelAttribute("khoaluan") KhoaLuan k, Model model) {
        try {
            this.khoaLuanService.addOrUpdateKhoaLuan(k);
            return "redirect:/";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("khoaluan", k);
            model.addAttribute("errorMessage", ex.getMessage());
            return "khoaluans";
        }
    }

    @GetMapping("/khoaluans/{khoaLuanId}")
    public String updateKhoaLuan(Model model, @PathVariable(value = "khoaLuanId") int id) {
        model.addAttribute("khoaluan", this.khoaLuanService.getKhoaLuanById(id));

        return "khoaluans";
    }
}
