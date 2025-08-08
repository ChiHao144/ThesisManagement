/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom15.services.impl;

import com.nhom15.pojo.ThanhVien;
import com.nhom15.repositories.ThanhVienRepository;
import com.nhom15.services.ThanhVienService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ACER
 */
@Service
public class ThanhVienServiceImpl implements ThanhVienService {
    
    @Autowired
    private ThanhVienRepository thanhVienRepo;
    
    @Override
    public ThanhVien getByGiangVienAndHoiDong(int giangVienId, int hoiDongId) {
        return this.thanhVienRepo.getByGiangVienAndHoiDong(giangVienId, hoiDongId);
    }
    
    @Override
    public List<ThanhVien> getThanhViens(Map<String, String> params) {
        return this.thanhVienRepo.getThanhViens(params);
    }
    
    @Override
    public ThanhVien getThanhVienById(int id) {
        return this.thanhVienRepo.getThanhVienById(id);
    }
    
    @Override
    public void addOrUpdateThanhVien(ThanhVien tv) {
        this.thanhVienRepo.addOrUpdateThanhVien(tv);
    }
    
    @Override
    public void deleteThanhVien(int id) {
        this.thanhVienRepo.deleteThanhVien(id);
    }
    
}
