/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom15.controller;

import com.nhom15.pojo.Diem;
import com.nhom15.pojo.HoiDong;
import com.nhom15.pojo.KhoaLuan;
import com.nhom15.pojo.ThanhVien;
import com.nhom15.services.DiemService;
import com.nhom15.services.HoiDongService;
import com.nhom15.services.KhoaLuanService;
import com.nhom15.services.ThanhVienService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ACER
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiChamDiemController {

    @Autowired
    private DiemService diemService;

    @Autowired
    private HoiDongService hoiDongService;

    @Autowired
    private KhoaLuanService khoaLuanService;
    
    @Autowired
    private ThanhVienService thanhVienService;

    @PostMapping("/secure/diem/cham")
    public ResponseEntity<String> chamDiem(
            @RequestParam("giangVienId") int giangVienId,
            @RequestParam("khoaLuanId") int khoaLuanId,
            @RequestBody Map<Integer, Double> diemTieuChi) {

        try {
            diemService.chamDiem(giangVienId, khoaLuanId, diemTieuChi);
            return new ResponseEntity<>("Chấm điểm thành công", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (SecurityException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/secure/diem/thanhvien/{thanhVienId}/khoaluan/{khoaLuanId}")
    public ResponseEntity<List<Diem>> getDiemByGiangVienAndKhoaLuan(
            @PathVariable("thanhVienId") int thanhVienId,
            @PathVariable("khoaLuanId") int khoaLuanId) {

        List<Diem> list = diemService.getDiemByGiangVienAndKhoaLuan(thanhVienId, khoaLuanId);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/secure/hoidong/giangvien/{giangVienId}")
    public ResponseEntity<List<HoiDong>> getHoiDongsByGiangVien(
            @PathVariable("giangVienId") int giangVienId,
            @RequestParam Map<String, String> params) {

        return new ResponseEntity<>(hoiDongService.getHoiDongsByGiangVien(giangVienId, params), HttpStatus.OK);
    }

    @GetMapping("/secure/khoaluan/hoidong/{hoiDongId}")
    public ResponseEntity<List<KhoaLuan>> getKhoaLuansByHoiDong(
            @PathVariable("hoiDongId") int hoiDongId) {

        return new ResponseEntity<>(khoaLuanService.getKhoaLuansByHoiDong(hoiDongId), HttpStatus.OK);
    }

    @GetMapping("/secure/thanhvien-id")
    public ResponseEntity<?> getThanhVienIdByGiangVienAndKhoaLuan(
            @RequestParam("giangVienId") int giangVienId,
            @RequestParam("khoaLuanId") int khoaLuanId) {
        try {
            KhoaLuan kl = khoaLuanService.getKhoaLuanById(khoaLuanId);
            if (kl == null || kl.getHoidongId() == null) {
                return new ResponseEntity<>("Khóa luận hoặc hội đồng không tồn tại.", HttpStatus.NOT_FOUND);
            }
            ThanhVien tv = thanhVienService.getByGiangVienAndHoiDong(giangVienId, kl.getHoidongId().getId());
            if (tv == null) {
                return new ResponseEntity<>("Giảng viên không phải là thành viên của hội đồng cho khóa luận này.", HttpStatus.NOT_FOUND);
            }
            java.util.Map<String, Integer> response = new java.util.HashMap<>();
            response.put("thanhVienId", tv.getId());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            
            return new ResponseEntity<>("Lỗi máy chủ nội bộ khi lấy ID thành viên.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
