/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nhom15.services;

import com.nhom15.pojo.Diem;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ACER
 */
public interface DiemService {
     void chamDiem(int giangVienId, int khoaLuanId, Map<Integer, Double> diemTieuChi);
     List<Diem> getDiemByGiangVienAndKhoaLuan(int thanhVienId, int khoaLuanId);
     Map<String, Object> getDiemKhoaLuan(int khoaLuanId);
}
