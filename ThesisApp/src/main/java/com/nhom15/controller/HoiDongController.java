/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom15.controller;

import com.nhom15.pojo.HoiDong;
import com.nhom15.pojo.KhoaLuan;
import com.nhom15.pojo.ThanhVien;
import com.nhom15.services.GiangVienService;
import com.nhom15.services.HoiDongService;
import com.nhom15.services.KhoaLuanService;
import java.util.ArrayList;
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
        return "hdcreateandupdate";
    }

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
            model.addAttribute("giangViens", this.giangVienService.getGiangViens(params));
            model.addAttribute("khoaluans", this.khoaLuanService.getKhoaLuans(params));

            model.addAttribute("selectedGiangVienIds", giangVienIds != null ? giangVienIds : new ArrayList<>());
            model.addAttribute("selectedVaiTros", vaiTros != null ? vaiTros : new ArrayList<>());
            model.addAttribute("selectedKhoaLuanIds", khoaLuanIds != null ? khoaLuanIds : new ArrayList<>());

            return "hdcreateandupdate";
        }
    }

    @GetMapping("/edit/{id}")
    public String updateHoiDong(Model model, @PathVariable("id") int id, @RequestParam Map<String, String> params) {
        HoiDong hd = this.hoiDongService.getHoiDongById(id);
        model.addAttribute("hoiDong", hd);

        List<Integer> selectedGiangVienIds = new ArrayList<>();
        List<String> selectedVaiTros = new ArrayList<>();

        for (ThanhVien tv : hd.getThanhVienSet()) {
            selectedGiangVienIds.add(tv.getGvId().getId());
            selectedVaiTros.add(tv.getVaiTro());
        }

        List<Integer> selectedKhoaLuanIds = new ArrayList<>();
        for (KhoaLuan kl : hd.getKhoaLuanSet()) {
            selectedKhoaLuanIds.add(kl.getId());
        }

        model.addAttribute("selectedGiangVienIds", selectedGiangVienIds);
        model.addAttribute("selectedVaiTros", selectedVaiTros);
        model.addAttribute("selectedKhoaLuanIds", selectedKhoaLuanIds);

        model.addAttribute("giangViens", this.giangVienService.getGiangViens(params));
        model.addAttribute("khoaluans", this.khoaLuanService.getKhoaLuans(params));

        return "hdcreateandupdate";
    }

    @GetMapping("/hoidongs/delete/{id}")
    public String deleteHoiDong(@PathVariable("id") int id) {
        this.hoiDongService.deleteHoiDong(id);
        return "redirect:/hoidongs";
    }
}
