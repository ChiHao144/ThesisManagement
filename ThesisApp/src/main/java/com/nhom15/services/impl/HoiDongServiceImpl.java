/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom15.services.impl;

import com.nhom15.pojo.GiangVien;
import com.nhom15.pojo.HoiDong;
import com.nhom15.pojo.KhoaLuan;
import com.nhom15.pojo.ThanhVien;
import com.nhom15.repositories.GiangVienRepository;
import com.nhom15.repositories.HoiDongRepository;
import com.nhom15.repositories.KhoaLuanRepository;
import com.nhom15.repositories.ThanhVienRepository;
import com.nhom15.services.HoiDongService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ACER
 */
@Service
public class HoiDongServiceImpl implements HoiDongService {

    @Autowired
    private HoiDongRepository hoiDongRepo;

    @Autowired
    private KhoaLuanRepository khoaLuanRepo;

    @Autowired
    private GiangVienRepository giangVienRepo;

    @Autowired
    private ThanhVienRepository thanhVienRepo;

    @Override
    public HoiDong getHoiDongById(int id) {
        return this.hoiDongRepo.getHoiDongById(id);
    }

    @Override
    public List<HoiDong> getHoiDongs(Map<String, String> params) {
        return this.hoiDongRepo.getHoiDongs(params);
    }

    @Override
    public void addOrUpdateHoiDong(HoiDong hd, List<Integer> giangVienIds, List<String> vaiTros, List<Integer> khoaLuanIds) {
        if (hd.getNgayBaoVe() == null) {
            throw new IllegalArgumentException("Ngày bảo vệ là bắt buộc!");
        }

        if (hd.getNoiDungBaoVe() == null || hd.getNoiDungBaoVe().trim().isEmpty()) {
            throw new IllegalArgumentException("Nội dung bảo vệ là bắt buộc!");
        }

        if (giangVienIds == null || giangVienIds.isEmpty()) {
            throw new IllegalArgumentException("Hội đồng phải có ít nhất 3 thành viên!");
        }

        if (giangVienIds.size() < 3 || giangVienIds.size() > 5) {
            throw new IllegalArgumentException("Hội đồng phải có từ 3 đến 5 giảng viên!");
        }

        Set<Integer> uniqueGvIds = new HashSet<>(giangVienIds);
        if (uniqueGvIds.size() != giangVienIds.size()) {
            throw new IllegalArgumentException("Một giảng viên không được tham gia nhiều vai trò trong cùng một hội đồng!");
        }

        boolean hasChuTich = vaiTros.contains("chu_tich");
        boolean hasThuKy = vaiTros.contains("thu_ky");
        boolean hasPhanBien = vaiTros.contains("phan_bien");

        if (!hasChuTich || !hasThuKy || !hasPhanBien) {
            throw new IllegalArgumentException("Hội đồng phải có đủ 1 Chủ tịch, 1 Thư ký và 1 Phản biện!");
        }

        List<Integer> finalKhoaLuanIds = (khoaLuanIds != null) ? khoaLuanIds : new ArrayList<>();

        if (!finalKhoaLuanIds.isEmpty()) {
            if (finalKhoaLuanIds.size() > 5) {
                throw new IllegalArgumentException("Một hội đồng chỉ được chấm tối đa 5 khóa luận!");
            }

            for (Integer klId : finalKhoaLuanIds) {
                KhoaLuan kl = khoaLuanRepo.getKhoaLuanById(klId);
                if (kl == null) {
                    throw new IllegalArgumentException("Không tìm thấy khoá luận ID: " + klId);
                }

                if (kl.getHoidongId() != null && !kl.getHoidongId().getId().equals(hd.getId())) {
                    throw new IllegalArgumentException(
                            "Khoá luận '" + kl.getChuDe() + "' (ID: " + kl.getId()
                            + ") đã thuộc hội đồng ID: " + kl.getHoidongId().getId()
                    );
                }

                for (int i = 0; i < giangVienIds.size(); i++) {
                    Integer gvId = giangVienIds.get(i);
                    GiangVien gv = giangVienRepo.getGiangVienById(gvId); // Lấy đối tượng GiangVien để có fullname

                    if (kl.getGvhd1() != null && gvId.equals(kl.getGvhd1().getId())) {
                        throw new IllegalArgumentException(
                                "Giảng viên " + gv.getUser().getFullname()
                                + " trong hội đồng trùng với Giảng viên hướng dẫn 1 của khoá luận '" + kl.getChuDe() + "'"
                        );
                    }

                    if (kl.getGvhd2() != null && gvId.equals(kl.getGvhd2().getId())) {
                        throw new IllegalArgumentException(
                                "Giảng viên " + gv.getUser().getFullname()
                                + " trong hội đồng trùng với Giảng viên hướng dẫn 2 của khoá luận '" + kl.getChuDe() + "'"
                        );
                    }

                    if (kl.getGvpb() != null && gvId.equals(kl.getGvpb().getId())) {
                        throw new IllegalArgumentException(
                                "Giảng viên " + gv.getUser().getFullname()
                                + " trong hội đồng trùng với Giảng viên phản biện của khoá luận '" + kl.getChuDe() + "'"
                        );
                    }
                }
            }
        }

        this.hoiDongRepo.addOrUpdateHoiDong(hd, giangVienIds, vaiTros, finalKhoaLuanIds);
    }

    @Override
    public void deleteHoiDong(int id) {
        this.hoiDongRepo.deleteHoiDong(id);
    }

    @Override
    public List<HoiDong> getHoiDongsByGiangVien(int giangVienId, Map<String, String> params) {
        return this.hoiDongRepo.getHoiDongsByGiangVien(giangVienId, params);
    }
}
