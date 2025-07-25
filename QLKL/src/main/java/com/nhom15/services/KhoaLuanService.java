/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nhom15.services;

import com.nhom15.pojo.KhoaLuan;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ACER
 */
public interface KhoaLuanService {
    List<KhoaLuan> getKhoaLuans(Map<String, String> params);
}
