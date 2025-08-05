/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom15.services.impl;

import com.nhom15.pojo.SinhVien;
import com.nhom15.repositories.SinhVienRepository;
import com.nhom15.services.SinhVienService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ACER
 */
@Service
public class SinhVienServiceImpl implements SinhVienService {

    @Autowired
    private SinhVienRepository sinhVienRepo;
    
    @Override
    public SinhVien getSinhVienById(int id) {
        return this.sinhVienRepo.getSinhVienById(id);
    }

    @Override
    public List<SinhVien> getSinhViens() {
        return this.sinhVienRepo.getSinhViens();
    }
    
}
