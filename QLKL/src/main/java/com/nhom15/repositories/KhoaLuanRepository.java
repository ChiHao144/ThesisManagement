/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nhom15.repositories;

import com.nhom15.pojo.HoiDong;
import com.nhom15.pojo.KhoaLuan;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ACER
 */
public interface KhoaLuanRepository {
    List<KhoaLuan> getKhoaLuans(Map<String, String> params);
    KhoaLuan getKhoaLuanById(int id);
//    KhoaLuan getKhoaLuanChiTiet(int id);
    void addOrUpdateKhoaLuan(KhoaLuan kl);
    void deleteKhoaLuan(int id);
    void unassignHoiDong(int hoiDongId);
    void assignKhoaLuanToHoiDong(int klId, HoiDong hoiDong);
    void clearHoiDongOfKhoaLuan(Integer hoiDongId);
//    List<KhoaLuan> getKhoaLuansByGiangVienId(Map<String, String> params);
//    List<KhoaLuan> getKhoaLuansChuaCoHoiDong(Map<String, String> params);
}
