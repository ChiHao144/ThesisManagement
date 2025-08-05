/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom15.controllers;

import com.nhom15.pojo.HoiDong;
import com.nhom15.services.GiangVienService;
import com.nhom15.services.HoiDongService;
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
public class HoiDongController {

    @Autowired
    private HoiDongService hoiDongService;

    @Autowired
    private GiangVienService giangVienService;

    @Autowired
    private KhoaLuanService khoaLuanService;

    @GetMapping("/hoidongs")
    public String listHoiDongs(Model model, @RequestParam Map<String, String> params) {
        List<HoiDong> hoiDongs = this.hoiDongService.getHoiDongs(params);
        model.addAttribute("hoiDongs", hoiDongs);
        return "hoidongs";
    }

    @GetMapping("/create")
    public String createHoiDongForm(Model model) {
        model.addAttribute("hoiDong", new HoiDong());
        model.addAttribute("selectedGiangVienIds", List.of(0));
        model.addAttribute("selectedVaiTros", List.of("chu_tich"));
        model.addAttribute("selectedKhoaLuanIds", List.of());
        return "hdcreateandupdate"; // file hdcreateandupdate.html
    }

    // Thêm hoặc cập nhật hội đồng
    @PostMapping("/save")
    public String addOrUpdateHoiDong(
            @ModelAttribute("hoiDong") HoiDong hd,
            @RequestParam(name = "giangVienIds", required = false) List<Integer> giangVienIds,
            @RequestParam(name = "vaiTros", required = false) List<String> vaiTros,
            @RequestParam(name = "khoaLuanIds", required = false) List<Integer> khoaLuanIds,
            @RequestParam Map<String, String> params,
            Model model
    ) {
        try {
            this.hoiDongService.addOrUpdateHoiDong(hd, giangVienIds, vaiTros, khoaLuanIds);
            return "redirect:/hoidongs";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("errorMessage", ex.getMessage());

            model.addAttribute("hoiDong", hd);
            model.addAttribute("giangViens", this.giangVienService.getGiangViens());
            model.addAttribute("khoaluans", this.khoaLuanService.getKhoaLuans(params));

            model.addAttribute("selectedGiangVienIds", giangVienIds);
            model.addAttribute("selectedVaiTros", vaiTros);
            model.addAttribute("selectedKhoaLuanIds", khoaLuanIds);

            return "hdcreateandupdate";
        }
    }

    @GetMapping("/edit/{id}")
    public String updateHoiDong(Model model, @PathVariable("id") int id) {
        model.addAttribute("hoiDong", this.hoiDongService.getHoiDongById(id));
        return "hdcreateandupdate";
    }

    // Xóa hội đồng (tùy chọn)
    @GetMapping("/hoidongs/delete/{id}")
    public String deleteHoiDong(@PathVariable("id") int id) {
        this.hoiDongService.deleteHoiDong(id);
        return "redirect:/hoidongs";
    }
}
