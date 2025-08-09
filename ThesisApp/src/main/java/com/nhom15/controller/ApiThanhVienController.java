/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom15.controller;

import com.nhom15.services.ThanhVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ACER
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiThanhVienController {
    @Autowired
    private ThanhVienService thanhVienService;
    
    @DeleteMapping("/thanhviens/{thanhvienId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteThanhVien(@PathVariable(value = "thanhvienId") int id) {
        this.thanhVienService.deleteThanhVien(id);
    }
}
