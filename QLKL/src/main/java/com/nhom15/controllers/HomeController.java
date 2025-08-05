/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom15.controllers;

import com.nhom15.services.GiangVienService;
import com.nhom15.services.HoiDongService;
import com.nhom15.services.KhoaLuanService;
import com.nhom15.services.SinhVienService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author ACER
 */
@Controller
@ControllerAdvice
public class HomeController {
    @Autowired
    private KhoaLuanService khoaLuanService; 
    
    @Autowired
    private GiangVienService giangVienService;

    @Autowired
    private SinhVienService sinhVienService;

    @Autowired
    private HoiDongService hoiDongService;
    
    @ModelAttribute
    public void commonResponses(Model model, @RequestParam Map<String, String> params) {
        model.addAttribute("giangViens", giangVienService.getGiangViens());
        model.addAttribute("sinhViens", sinhVienService.getSinhViens());
        model.addAttribute("hoiDongs", hoiDongService.getHoiDongs(params));
        model.addAttribute("khoaluans", khoaLuanService.getKhoaLuans(params));
    }
    
    @RequestMapping("/")
    public String index(Model model, @RequestParam Map<String, String> params) {
        
        model.addAttribute("khoaluans", this.khoaLuanService.getKhoaLuans(params));
        
        return "index";
    } 
}

