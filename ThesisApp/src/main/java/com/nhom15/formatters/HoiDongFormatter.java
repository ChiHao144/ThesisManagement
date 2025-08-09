/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom15.formatters;

import com.nhom15.pojo.HoiDong;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 *
 * @author ACER
 */
public class HoiDongFormatter implements Formatter<HoiDong> {

    @Override
    public String print(HoiDong hd, Locale locale) {
        return String.valueOf(hd.getId());
    }

    @Override
    public HoiDong parse(String hoiDongId, Locale locale) throws ParseException {
        HoiDong hd = new HoiDong(Integer.valueOf(hoiDongId));
        return hd;
    }
}
