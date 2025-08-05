/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nhom15.repositories;

import com.nhom15.pojo.GiangVien;
import java.util.List;

/**
 *
 * @author ACER
 */
public interface GiangVienRepository {
    List<GiangVien> getGiangViens();
    GiangVien getGiangVienById(int id);
}
