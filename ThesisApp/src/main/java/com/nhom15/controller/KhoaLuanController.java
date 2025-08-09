/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom15.controller;

import com.nhom15.pojo.KhoaLuan;
import com.nhom15.services.DiemService;
import com.nhom15.services.KhoaLuanService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author ACER
 */
@Controller
public class KhoaLuanController {

    @Autowired
    private KhoaLuanService khoaLuanService;
    
    @Autowired
    private DiemService diemService;

    @GetMapping("/khoaluans")
    public String listKhoaLuans(Model model, @RequestParam Map<String, String> params) {
        List<KhoaLuan> khoaluans = this.khoaLuanService.getKhoaLuans(params);
        model.addAttribute("khoaluans", khoaluans);
        return "khoaluans";
    }
    
    @GetMapping("/khoaluans/add")
    public String formKhoaLuans(Model model) {
        model.addAttribute("khoaluan", new KhoaLuan());
        return "khoaluanaddandupdate";
    }

    @PostMapping("/khoaluans/save")
    public String addKhoaLuan(@ModelAttribute("khoaluan") KhoaLuan k, Model model) {
        try {
            this.khoaLuanService.addOrUpdateKhoaLuan(k);
            return "redirect:/khoaluans";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("khoaluan", k);
            model.addAttribute("errorMessage", ex.getMessage());
            return "khoaluanaddandupdate";
        }
    }

    @GetMapping("/khoaluans/edit/{khoaLuanId}")
    public String updateKhoaLuan(Model model, @PathVariable(value = "khoaLuanId") int id) {
        model.addAttribute("khoaluan", this.khoaLuanService.getKhoaLuanById(id));

        return "khoaluanaddandupdate";
    }

    @GetMapping("/khoaluans/bangdiem/{id}")
    public String xemDiemKhoaLuan(@PathVariable("id") int khoaLuanId, Model model) {
        try {
            Map<String, Object> data = diemService.getDiemKhoaLuan(khoaLuanId);

            model.addAttribute("khoaLuan", data.get("khoaLuan"));
            model.addAttribute("diemThanhVien", data.get("diemTrungBinhThanhVien"));
            model.addAttribute("diemTrungBinhKhoaLuan", data.get("diemTrungBinhKhoaLuan"));

            return "bangdiem";
        } catch (IllegalStateException ex) {
            model.addAttribute("error", ex.getMessage());
            return "khoaluans";
        }
    }
}
