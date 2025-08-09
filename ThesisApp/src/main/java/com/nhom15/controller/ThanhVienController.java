/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom15.controller;

import com.nhom15.pojo.ThanhVien;
import com.nhom15.services.ThanhVienService;
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
public class ThanhVienController {

    @Autowired
    private ThanhVienService thanhVienService;

    @GetMapping("/thanhviens")
    public String listThanhViens(Model model, @RequestParam Map<String, String> params) {
        List<ThanhVien> thanhViens = this.thanhVienService.getThanhViens(params);
        model.addAttribute("thanhViens", thanhViens);
        return "thanhviens";
    }

    @GetMapping("/thanhviens/add")
    public String addThanhVien(Model model) {
        model.addAttribute("thanhvien", new ThanhVien());
        return "thanhvienaddandupdate";
    }

    @PostMapping("/thanhviens/save")
    public String saveThanhVien(@ModelAttribute(value = "thanhvien") ThanhVien tv, Model model) {
        try {
            this.thanhVienService.addOrUpdateThanhVien(tv);
        } catch (IllegalArgumentException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "thanhvienaddandupdate";
        }
        return "redirect:/thanhviens";
    }

    @GetMapping("/thanhviens/edit/{thanhvienId}")
    public String updateTieuChi(Model model, @PathVariable(value = "thanhvienId") int id) {
        model.addAttribute("thanhvien", this.thanhVienService.getThanhVienById(id));
        return "thanhvienaddandupdate";
    }
}
