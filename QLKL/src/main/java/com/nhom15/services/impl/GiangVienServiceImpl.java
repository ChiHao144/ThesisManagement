/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom15.services.impl;

import com.nhom15.pojo.GiangVien;
import com.nhom15.repositories.GiangVienRepository;
import com.nhom15.services.GiangVienService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ACER
 */
@Service
public class GiangVienServiceImpl implements GiangVienService{
    @Autowired
    private GiangVienRepository giangVienRepo;

    @Override
    public List<GiangVien> getGiangViens() {
        return this.giangVienRepo.getGiangViens();
    }

    @Override
    public GiangVien getGiangVienById(int id) {
        return this.giangVienRepo.getGiangVienById(id);
    }
    
}
