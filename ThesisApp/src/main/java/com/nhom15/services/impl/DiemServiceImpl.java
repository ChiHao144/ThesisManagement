/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom15.services.impl;

import com.nhom15.pojo.Diem;
import com.nhom15.pojo.KhoaLuan;
import com.nhom15.pojo.ThanhVien;
import com.nhom15.repositories.DiemRepository;
import com.nhom15.repositories.HoiDongRepository;
import com.nhom15.repositories.KhoaLuanRepository;
import com.nhom15.repositories.ThanhVienRepository;
import com.nhom15.repositories.TieuChiRepository;
import com.nhom15.services.DiemService;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ACER
 */
@Service
public class DiemServiceImpl implements DiemService {

    @Autowired
    private KhoaLuanRepository khoaLuanRepo;
    @Autowired
    private HoiDongRepository hoiDongRepo;
    @Autowired
    private ThanhVienRepository thanhVienRepo;
    @Autowired
    private TieuChiRepository tieuChiRepo;
    @Autowired
    private DiemRepository diemRepo;

    @Override
    public void chamDiem(int giangVienId, int khoaLuanId, Map<Integer, Double> diemTieuChi) {
        KhoaLuan kl = khoaLuanRepo.getKhoaLuanById(khoaLuanId);
        if (kl == null || kl.getHoidongId() == null) {
            throw new IllegalArgumentException("Khóa luận không hợp lệ hoặc chưa có hội đồng.");
        }

        int hoiDongId = kl.getHoidongId().getId();
        if (hoiDongRepo.isHoiDongDaKhoa(hoiDongId)) {
            throw new IllegalStateException("Hội đồng đã bị khóa, không thể chấm điểm.");
        }

        ThanhVien tv = thanhVienRepo.getByGiangVienAndHoiDong(giangVienId, hoiDongId);
        if (tv == null) {
            throw new SecurityException("Bạn không thuộc hội đồng này, không thể chấm điểm.");
        }

        for (Map.Entry<Integer, Double> entry : diemTieuChi.entrySet()) {
            Integer tieuChiId = entry.getKey();
            Double diem = entry.getValue();

            Diem d = diemRepo.getDiem(tv.getId(), khoaLuanId, tieuChiId);
            if (d == null) {
                d = new Diem();
                d.setKhoaLuanId(kl);
                d.setThanhvienId(tv);
                d.setTieuChiId(tieuChiRepo.getTieuChiById(tieuChiId));
                d.setCreatedAt(new Date());
            }

            d.setDiem(diem);
            diemRepo.saveOrUpdate(d);
        }
    }

    @Override
    public List<Diem> getDiemByGiangVienAndKhoaLuan(int thanhVienId, int khoaLuanId) {
        return this.diemRepo.getDiemByGiangVienAndKhoaLuan(thanhVienId, khoaLuanId);
    }

    @Override
    public Map<String, Object> getDiemKhoaLuan(int khoaLuanId) {
        if (!hoiDongRepo.isHoiDongDaKhoa(khoaLuanId)) {
            throw new IllegalStateException("Hội đồng chưa khóa, không thể lấy điểm khóa luận.");
        }
        return this.diemRepo.getDiemKhoaLuan(khoaLuanId);
    }

}
