/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nhom15.services;

import com.nhom15.pojo.SinhVien;
import java.util.List;

/**
 *
 * @author ACER
 */
public interface SinhVienService {
    List<SinhVien> getSinhViens();
    SinhVien getSinhVienById(int id);
}
