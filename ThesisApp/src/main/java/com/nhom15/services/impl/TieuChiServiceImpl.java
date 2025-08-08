/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom15.services.impl;

import com.nhom15.pojo.TieuChi;
import com.nhom15.repositories.TieuChiRepository;
import com.nhom15.services.TieuChiService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ACER
 */
@Service
public class TieuChiServiceImpl implements TieuChiService{
    @Autowired
    private TieuChiRepository tieuChiRepo;

    @Override
    public List<TieuChi> getAllTieuChi() {
        return this.tieuChiRepo.getAllTieuChi();
    }

    @Override
    public TieuChi getTieuChiById(int id) {
        return this.tieuChiRepo.getTieuChiById(id);
    }

    @Override
    public void addOrUpdateTieuChi(TieuChi tc) {
        this.tieuChiRepo.addOrUpdateTieuChi(tc);
    }

    @Override
    public void deleteTieuChi(int id) {
        this.tieuChiRepo.deleteTieuChi(id);
    } 
}
