/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom15.formatters;

import com.nhom15.pojo.SinhVien;
import com.nhom15.services.SinhVienService;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;

/**
 *
 * @author ACER
 */
public class SinhVienFormatter implements Formatter<SinhVien> {

    @Override
    public String print(SinhVien sv, Locale locale) {
        return String.valueOf(sv.getId());
    }

    @Override
    public SinhVien parse(String sinhVienId, Locale locale) throws ParseException {
        SinhVien sv = new SinhVien(Integer.valueOf(sinhVienId));
        return sv;
    }
}
