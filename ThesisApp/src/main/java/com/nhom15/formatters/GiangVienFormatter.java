/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom15.formatters;

import com.nhom15.pojo.GiangVien;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 *
 * @author ACER
 */
public class GiangVienFormatter implements Formatter<GiangVien>{
    @Override
    public String print(GiangVien gv, Locale locale) {
        return String.valueOf(gv.getId());
    }

    @Override
    public GiangVien parse(String giangVienId, Locale locale) throws ParseException {
        GiangVien c = new GiangVien(Integer.valueOf(giangVienId));
        return c;
    }
}
