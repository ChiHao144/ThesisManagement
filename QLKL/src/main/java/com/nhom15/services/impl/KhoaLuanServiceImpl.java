/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom15.services.impl;

import com.nhom15.pojo.KhoaLuan;
import com.nhom15.repositories.KhoaLuanRepository;
import com.nhom15.services.KhoaLuanService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ACER
 */
@Service
public class KhoaLuanServiceImpl implements KhoaLuanService {
    @Autowired
    private KhoaLuanRepository khoaLuanRepo;
    
    @Override
    public List<KhoaLuan> getKhoaLuans(Map<String, String> params) {
        return this.khoaLuanRepo.getKhoaLuans();
    }
    
}
