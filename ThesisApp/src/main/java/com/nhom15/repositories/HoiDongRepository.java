/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nhom15.repositories;

import com.nhom15.pojo.HoiDong;
import com.nhom15.pojo.ThanhVien;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ACER
 */
public interface HoiDongRepository {
    List<HoiDong> getHoiDongs(Map<String, String> params);
    HoiDong getHoiDongById(int id);
    void addOrUpdateHoiDong(HoiDong hd, List<Integer> giangVienIds, List<String> vaiTros, List<Integer> khoaLuanIds);
    void deleteHoiDong(int id);
    boolean isHoiDongDaKhoa(int hoiDongId);
    List<HoiDong> getHoiDongsByGiangVien(int giangVienId, Map<String, String> params);
    void saveHoiDong(HoiDong hd);
}
