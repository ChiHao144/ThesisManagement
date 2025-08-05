/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nhom15.services;

import com.nhom15.pojo.GiangVien;
import java.util.List;

/**
 *
 * @author ACER
 */
public interface GiangVienService {
    List<GiangVien> getGiangViens();
    GiangVien getGiangVienById(int id);
}
