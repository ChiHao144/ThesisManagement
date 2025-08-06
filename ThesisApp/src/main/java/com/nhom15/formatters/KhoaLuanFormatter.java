/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom15.formatters;

import com.nhom15.pojo.KhoaLuan;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 *
 * @author ACER
 */
public class KhoaLuanFormatter implements Formatter<KhoaLuan> {
    @Override
    public String print(KhoaLuan kl, Locale locale) {
        return String.valueOf(kl.getId());
    }

    @Override
    public KhoaLuan parse(String klId, Locale locale) throws ParseException {
        KhoaLuan kl = new KhoaLuan(Integer.valueOf(klId)); 
        return kl;
    }
}
