/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nhom15.repositories;

import com.nhom15.pojo.Diem;
import java.util.List;

/**
 *
 * @author ACER
 */
public interface DiemRepository {
    void saveOrUpdate(Diem diem);
    List<Diem> getDiemByKhoaLuan(int khoaLuanId);
    List<Diem> getDiemByGiangVienAndKhoaLuan(int thanhVienId, int khoaLuanId);
    double getDiemTrungBinhKhoaLuan(int khoaLuanId);
    Diem getDiem(int thanhVienId, int khoaLuanId, int tieuChiId);
}
