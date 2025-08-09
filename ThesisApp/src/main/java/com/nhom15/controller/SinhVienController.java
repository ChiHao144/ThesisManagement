package com.nhom15.controller;

import com.nhom15.pojo.SinhVien;
import com.nhom15.services.SinhVienService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Chi Hao
 */
@Controller
public class SinhVienController {

    @Autowired
    private SinhVienService sinhVienService;

    @GetMapping("/sinhvien")
    public String listView(Model model, @RequestParam Map<String, String> params) {
        model.addAttribute("sinhvien", sinhVienService.getSinhViens(params));
        return "sinhvien";
    }

    @PostMapping("/sinhvien")
    public String addSinhVien(@ModelAttribute(value = "sinhvien") SinhVien sv) {
        this.sinhVienService.addOrUpdateSinhVien(sv);
        return "redirect:/sinhvien";
    }


    @GetMapping("/importsinhvien")
    public String importSinhViens() {
        this.sinhVienService.importSinhVienFromUser();
        return "redirect:/sinhvien";
    }
    
    @GetMapping("/sinhvien/{sinhVienId}")
    public String updateUser(Model model, @PathVariable(value = "sinhVienId") int id) {
         model.addAttribute("sinhvien", this.sinhVienService.getSinhVienById(id));
         return "sinhvienupdate";
    }
}
