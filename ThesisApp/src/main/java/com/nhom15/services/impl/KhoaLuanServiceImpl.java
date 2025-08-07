/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom15.services.impl;

import com.nhom15.pojo.GiangVien;
import com.nhom15.pojo.HoiDong;
import com.nhom15.pojo.KhoaLuan;
import com.nhom15.pojo.SinhVien;
import com.nhom15.pojo.ThanhVien;
import com.nhom15.pojo.ThongBao;
import com.nhom15.pojo.User;
import com.nhom15.repositories.GiangVienRepository;
import com.nhom15.repositories.HoiDongRepository;
import com.nhom15.repositories.KhoaLuanRepository;
import com.nhom15.repositories.SinhVienRepository;
import com.nhom15.repositories.ThongBaoRepository;
import com.nhom15.services.KhoaLuanService;
import com.nhom15.services.MailService;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ACER
 */
@Service
public class KhoaLuanServiceImpl implements KhoaLuanService {

    @Autowired
    private KhoaLuanRepository khoaLuanRepo;

    @Autowired
    private GiangVienRepository giangVienRepo;

    @Autowired
    private ThongBaoRepository thongBaoRepo;

    @Autowired
    private SinhVienRepository sinhVienRepo;

    @Autowired
    private HoiDongRepository hoiDongRepo;

    @Autowired
    private MailService mailService;

    @Override
    public List<KhoaLuan> getKhoaLuans(Map<String, String> params) {
        return this.khoaLuanRepo.getKhoaLuans(params);
    }

    @Override
    public KhoaLuan getKhoaLuanById(int id) {
        return this.khoaLuanRepo.getKhoaLuanById(id);
    }

    @Override
    @Transactional
    public void addOrUpdateKhoaLuan(KhoaLuan kl) {
        if (kl.getChuDe() == null || kl.getChuDe().trim().isEmpty()) {
            throw new IllegalArgumentException("Chủ đề không được để trống.");
        }
        
        if (kl.getSinhVienId() == null || kl.getSinhVienId().getId() == null) {
            throw new IllegalArgumentException("Phải chọn sinh viên thực hiện.");
        }

        if (kl.getGvhd1() == null || kl.getGvhd1().getId() == null) {
            throw new IllegalArgumentException("Phải chọn giagr viên hướng dẫn 1.");
        }

        KhoaLuan oldKhoaLuan = null;
        GiangVien oldGvpb = null;

        if (kl.getId() != null) {
            oldKhoaLuan = this.khoaLuanRepo.getKhoaLuanById(kl.getId());
            oldGvpb = oldKhoaLuan.getGvpb();
            kl.setNgayTao(oldKhoaLuan.getNgayTao());
        } else {
            kl.setNgayTao(new Date());
        }

        if (kl.getGvhd1() != null && kl.getGvhd1().getId() != null) {
            kl.setGvhd1(giangVienRepo.getGiangVienById(kl.getGvhd1().getId()));
        } else {
            kl.setGvhd1(null);
        }

        if (kl.getGvhd2() != null && kl.getGvhd2().getId() != null) {
            kl.setGvhd2(giangVienRepo.getGiangVienById(kl.getGvhd2().getId()));
        } else {
            kl.setGvhd2(null);
        }

        if (kl.getGvpb() != null && kl.getGvpb().getId() != null) {
            kl.setGvpb(giangVienRepo.getGiangVienById(kl.getGvpb().getId()));
        } else {
            kl.setGvpb(null);
        }

        if (kl.getSinhVienId() != null && kl.getSinhVienId().getId() != null) {
            kl.setSinhVienId(sinhVienRepo.getSinhVienById(kl.getSinhVienId().getId()));
        } else {
            kl.setSinhVienId(null);
        }

        if (kl.getHoidongId() != null && kl.getHoidongId().getId() != null) {
            kl.setHoidongId(hoiDongRepo.getHoiDongById(kl.getHoidongId().getId()));
        } else {
            kl.setHoidongId(null);
        }

        SinhVien sv = kl.getSinhVienId();
        if (sv.getKhoaLuan() != null && (kl.getId() == null || !sv.getKhoaLuan().getId().equals(kl.getId()))) {
            throw new IllegalArgumentException("Sinh viên này đã có khóa luận.");
        }

        if (kl.getGvhd2() != null && kl.getGvhd1().getId().equals(kl.getGvhd2().getId())) {
            throw new IllegalArgumentException("GVHD1 không được trùng với GVHD2.");
        }

        if (kl.getGvpb() != null) {
            Integer idPb = kl.getGvpb().getId();

            if (idPb.equals(kl.getGvhd1().getId())) {
                throw new IllegalArgumentException("Giảng viên phản biện không được trùng với giảng viên hướng dẫn 1.");
            }

            if (kl.getGvhd2() != null && idPb.equals(kl.getGvhd2().getId())) {
                throw new IllegalArgumentException("Giảng viên phản biện không được trùng với giảng viên hướng dẫn 2.");
            }
        }

        if (kl.getHoidongId() != null) {
            HoiDong hd = kl.getHoidongId();

            if (Boolean.TRUE.equals(hd.getDaKhoa())) {
                throw new IllegalArgumentException("Hội đồng đã bị khóa.");
            }

            long countKhoaLuan = hd.getKhoaLuanSet().stream()
                    .filter(k -> kl.getId() == null || !k.getId().equals(kl.getId()))
                    .count();

            if (countKhoaLuan >= 5) {
                throw new IllegalArgumentException("Hội đồng đã đạt giới hạn 5 khóa luận.");
            }

            for (ThanhVien tv : hd.getThanhVienSet()) {
                Integer tvGvId = tv.getGvId().getId();

                if (tvGvId.equals(kl.getGvhd1().getId())) {
                    throw new IllegalArgumentException("Thành viên hội đồng không được trùng với giảng viên hướng dẫn 1.");
                }

                if (kl.getGvhd2() != null && tvGvId.equals(kl.getGvhd2().getId())) {
                    throw new IllegalArgumentException("Thành viên hội đồng không được trùng với giảng viên hướng dẫn 2.");
                }

                if (kl.getGvpb() != null && tvGvId.equals(kl.getGvpb().getId())) {
                    throw new IllegalArgumentException("Thành viên hội đồng không được trùng với giảng viên phản biện.");
                }
            }
        }

        if (kl.getGvpb() != null) {
            boolean isNewGvpb = oldGvpb == null
                    || oldGvpb.getId() == null
                    || !Objects.equals(oldGvpb.getId(), kl.getGvpb().getId());

            if (isNewGvpb) {
                GiangVien fullGvpb = kl.getGvpb();
                if (fullGvpb.getUser() != null) {
                    User user = fullGvpb.getUser();

                    String to = user.getEmail();
                    String subject = "Thông báo phân công phản biện khóa luận";
                    String body = String.format(
                            "Chào Thầy/Cô %s,\n\nThầy/Cô đã được phân công phản biện cho khóa luận với chủ đề: \"%s\".\n\nTrân trọng.",
                            user.getFullname(), kl.getChuDe()
                    );

                    mailService.sendEmail(to, subject, body);

                    ThongBao tb = new ThongBao();
                    tb.setLoaiThongBao("email");
                    tb.setTieuDe(subject);
                    tb.setNoiDung(body);
                    tb.setTrangThai("chua_doc");
                    tb.setNgayGui(new Date());
                    tb.setNguoiNhanId(user);
                    tb.setKhoaLuanId(kl);

                    thongBaoRepo.addThongBao(tb);
                }
            }
        }

        this.khoaLuanRepo.addOrUpdateKhoaLuan(kl);
    }

    @Override
    public void deleteKhoaLuan(int id) {
        this.khoaLuanRepo.deleteKhoaLuan(id);
    }
}
