package com.nhom15.controller;

import com.nhom15.pojo.SinhVien;
import com.nhom15.services.SinhVienService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

    //Test thêm sinh viên
    @GetMapping("/addsinhvien")
    public String showAddSinhVienForm(Model model) {
        model.addAttribute("sinhvien", new SinhVien());
        return "addsinhvien";
    }

    @GetMapping("/importsinhvien")
    public String importSinhViens() {
        this.sinhVienService.importSinhVienFromUser();
        return "redirect:/sinhvien";
    }

}
