/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nhom15.repositories;

import com.nhom15.pojo.Diem;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ACER
 */
public interface DiemRepository {
    void saveOrUpdate(Diem diem);
    List<Diem> getDiemByGiangVienAndKhoaLuan(int thanhVienId, int khoaLuanId);
    Diem getDiem(int thanhVienId, int khoaLuanId, int tieuChiId);
    Map<String, Object> getDiemKhoaLuan(int khoaLuanId);
}
