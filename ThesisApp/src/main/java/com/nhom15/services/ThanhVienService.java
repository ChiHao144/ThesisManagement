/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nhom15.services;

import com.nhom15.pojo.ThanhVien;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ACER
 */
public interface ThanhVienService {
    ThanhVien getByGiangVienAndHoiDong(int giangVienId, int hoiDongId);
    List<ThanhVien> getThanhViens(Map<String, String> params);
    ThanhVien getThanhVienById(int id);
    void addOrUpdateThanhVien(ThanhVien tv);
    void deleteThanhVien(int id);
}
