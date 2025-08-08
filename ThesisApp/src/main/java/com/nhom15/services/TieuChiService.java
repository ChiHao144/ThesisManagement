/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nhom15.services;

import com.nhom15.pojo.TieuChi;
import java.util.List;

/**
 *
 * @author ACER
 */
public interface TieuChiService {
    List<TieuChi> getAllTieuChi();
    TieuChi getTieuChiById(int id);
    void addOrUpdateTieuChi(TieuChi tc);
    void deleteTieuChi(int id);
}
