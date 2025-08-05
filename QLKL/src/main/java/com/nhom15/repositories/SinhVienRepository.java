/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nhom15.repositories;

import com.nhom15.pojo.SinhVien;
import java.util.List;

/**
 *
 * @author ACER
 */
public interface SinhVienRepository {
    List<SinhVien> getSinhViens();
    SinhVien getSinhVienById(int id);
    
}
