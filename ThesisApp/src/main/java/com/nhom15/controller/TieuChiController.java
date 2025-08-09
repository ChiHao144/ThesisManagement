/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom15.controller;

import com.nhom15.pojo.TieuChi;
import com.nhom15.services.TieuChiService;
import java.util.List;
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
public class TieuChiController {
    @Autowired
    private TieuChiService tieuChiService;
    
    @GetMapping("/tieuchis")
    public String listTieuChis(Model model) { 
        List<TieuChi> tieuChis = this.tieuChiService.getAllTieuChi();
        model.addAttribute("tieuChis", tieuChis);
        return "tieuchis";
    }
    
    @GetMapping("/tieuchis/add")
    public String addTieuChi(Model model) {
        model.addAttribute("tieuchi", new TieuChi());
        return "tieuchiaddandupdate";
    }
    
    @PostMapping("/tieuchis/save")
    public String saveTieuChi(@ModelAttribute(value = "tieuchi") TieuChi tc) {
        this.tieuChiService.addOrUpdateTieuChi(tc);
        return "redirect:/tieuchis";
    }
    
    @GetMapping("/tieuchis/edit/{tieuchiId}")
    public String updateTieuChi(Model model, @PathVariable(value = "tieuchiId") int id) {
         model.addAttribute("tieuchi", this.tieuChiService.getTieuChiById(id));
         return "tieuchiaddandupdate";
    }
}
