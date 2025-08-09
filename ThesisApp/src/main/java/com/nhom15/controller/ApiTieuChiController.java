
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom15.controller;

import com.nhom15.pojo.TieuChi;
import com.nhom15.services.ThanhVienService;
import com.nhom15.services.TieuChiService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Chi Hao
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiTieuChiController {

    @Autowired
    private TieuChiService tieuChiService;

    @GetMapping("/tieuchis")
    public ResponseEntity<List<TieuChi>> list() {

        List<TieuChi> tieuChis = this.tieuChiService.getAllTieuChi();

        return new ResponseEntity<>(tieuChis, HttpStatus.OK);

    }

    @DeleteMapping("/tieuchis/{tieuchiId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTieuChi(@PathVariable(value = "tieuchiId") int id) {
        this.tieuChiService.deleteTieuChi(id);

    }
}
