/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nhom15.services;

import com.nhom15.pojo.KhoaLuan;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ACER
 */
public interface KhoaLuanService {
    List<KhoaLuan> getKhoaLuans(Map<String, String> params);
    KhoaLuan getKhoaLuanById(int id);
//    KhoaLuan getKhoaLuanChiTiet(int id);
//    double tinhDiemKhoaLuan(KhoaLuan kl);
    void addOrUpdateKhoaLuan(KhoaLuan kl);
    void deleteKhoaLuan(int id);
//    List<KhoaLuan> getKhoaLuansByGiangVienId(Map<String, String> params);
//    List<KhoaLuan> getKhoaLuansChuaCoHoiDong(Map<String, String> params);
}
