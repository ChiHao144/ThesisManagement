 
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nhom15.services;

import com.nhom15.pojo.HoiDong;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ACER
 */
public interface HoiDongService {
    List<HoiDong> getHoiDongs(Map<String, String> params);
    HoiDong getHoiDongById(int id);
    void addOrUpdateHoiDong(HoiDong hd, List<Integer> giangVienIds, List<String> vaiTros, List<Integer> khoaLuanIds);
    void deleteHoiDong(int id);
}
