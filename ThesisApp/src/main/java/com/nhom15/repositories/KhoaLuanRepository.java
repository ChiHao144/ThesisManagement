/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nhom15.repositories;

import com.nhom15.pojo.KhoaLuan;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ACER
 */
public interface KhoaLuanRepository {
    List<KhoaLuan> getKhoaLuans(Map<String, String> params);
    KhoaLuan getKhoaLuanById(int id);
    void addOrUpdateKhoaLuan(KhoaLuan kl);
    void deleteKhoaLuan(int id);
}
