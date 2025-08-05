/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nhom15.repositories;

import com.nhom15.pojo.HoiDong;
import com.nhom15.pojo.ThanhVien;

/**
 *
 * @author ACER
 */
public interface ThanhVienRepository {
    void deleteByHoiDongId(int hoiDongId);
    void addThanhVienToHoiDong(ThanhVien tv, HoiDong hoiDong);
    void clearHoiDongOfThanhVien(Integer hoiDongId);
}
