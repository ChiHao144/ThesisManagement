/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom15.controller;

import com.nhom15.pojo.Diem;
import com.nhom15.pojo.HoiDong;
import com.nhom15.pojo.KhoaLuan;
import com.nhom15.pojo.ThanhVien;
import com.nhom15.pojo.TieuChi;
import com.nhom15.pojo.User;
import com.nhom15.services.DiemService;
import com.nhom15.services.GiangVienService;
import com.nhom15.services.HoiDongService;
import com.nhom15.services.KhoaLuanService;
import com.nhom15.services.ThanhVienService;
import com.nhom15.services.TieuChiService;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author ACER
 */
@Controller
public class ChamDiemController {

    @Autowired
    private GiangVienService giangVienService;

    @Autowired
    private KhoaLuanService khoaLuanService;

    @Autowired
    private DiemService diemService;

    @Autowired
    private TieuChiService tieuChiService;

    @Autowired
    private ThanhVienService thanhVienService;

    @Autowired
    private HoiDongService hoiDongService;

    @GetMapping("/giangvien/hoidongs")
    public String danhSachHoiDong(Model model, Principal principal, @RequestParam Map<String, String> params) {
        String username = principal.getName();
        int giangVienId = giangVienService.getByUsername(username).getId();
        List<HoiDong> hoiDongs = hoiDongService.getHoiDongsByGiangVien(giangVienId, params);
        model.addAttribute("hoiDongs", hoiDongs);
        return "gvhoidong";
    }

    @GetMapping("/giangvien/hoidong/{id}/khoaluans")
    public String danhSachKhoaLuan(@PathVariable("id") int hoiDongId,
            Model model,
            Principal principal,
            RedirectAttributes redirectAttrs) {
        String username = principal.getName();
        int giangVienId = giangVienService.getByUsername(username).getId();

        ThanhVien tv = thanhVienService.getByGiangVienAndHoiDong(giangVienId, hoiDongId);
        if (tv == null) {
            redirectAttrs.addFlashAttribute("errorMessage", "Bạn không thuộc hội đồng này.");
            return "redirect:/giangvien/hoidongs";
        }

        model.addAttribute("khoaLuans", khoaLuanService.getKhoaLuansByHoiDong(hoiDongId));
        return "gvkhoaluan";
    }

    @PostMapping("/giangvien/chamdiem/save")
    public String luuDiem(@RequestParam("khoaLuanId") int khoaLuanId,
            @RequestParam Map<String, String> diemTieuChiStr,
            Principal principal,
            Model model ) {

        String username = principal.getName();
        int giangVienId = giangVienService.getByUsername(username).getId();

        KhoaLuan kl = khoaLuanService.getKhoaLuanById(khoaLuanId);
        int hoiDongId = kl.getHoidongId().getId();

        Map<Integer, Double> diemTieuChi = new HashMap<>();
        for (String key : diemTieuChiStr.keySet()) {
            if (key.startsWith("diemTieuChi[")) {
                int tcId = Integer.parseInt(key.replaceAll("\\D+", ""));
                double diem = Double.parseDouble(diemTieuChiStr.get(key));
                diemTieuChi.put(tcId, diem);
            }
        }

        try {
            diemService.chamDiem(giangVienId, khoaLuanId, diemTieuChi);
        } catch (IllegalArgumentException  ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }

        return "redirect:/giangvien/hoidong/" + hoiDongId + "/khoaluans";
    }

    @GetMapping("/giangvien/khoaluan/{id}/chamdiem")
    public String formChamDiem(@PathVariable("id") int khoaLuanId, Model model, Principal principal, RedirectAttributes redirectAttrs) {
        String username = principal.getName();
        int giangVienId = giangVienService.getByUsername(username).getId();

        KhoaLuan kl = khoaLuanService.getKhoaLuanById(khoaLuanId);
        List<TieuChi> tieuChiList = tieuChiService.getAllTieuChi();

        ThanhVien tv = thanhVienService.getByGiangVienAndHoiDong(giangVienId, kl.getHoidongId().getId());
        if (tv == null) {
            redirectAttrs.addFlashAttribute("errorMessage", "Bạn không thuộc hội đồng này.");
            return "redirect:/giangvien/hoidong/" + kl.getHoidongId().getId() + "/khoaluans";
        }
        List<Diem> diemList = diemService.getDiemByGiangVienAndKhoaLuan(tv.getId(), kl.getId());

        Map<Integer, Double> mapDiem = diemList.stream()
                .collect(Collectors.toMap(
                        d -> d.getTieuChiId().getId(),
                        Diem::getDiem,
                        (oldVal, newVal) -> newVal
                ));

        model.addAttribute("khoaLuan", kl);
        model.addAttribute("tieuChiList", tieuChiList);
        model.addAttribute("mapDiem", mapDiem);

        return "gvchamdiem";
    }

}
