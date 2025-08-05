package com.nhom15.controller;

import com.nhom15.pojo.GiangVien;
import com.nhom15.services.GiangVienService;
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
public class GiangVienController {
    @Autowired
    private GiangVienService giangVienService;
    
    @GetMapping("/giangvien")
    public String listView(Model model, @RequestParam Map<String, String> params){
        model.addAttribute("giangvien", giangVienService.getGiangViens(params));
        return "giangvien";
    }
    
    @PostMapping("/giangvien")
    public String addGiangVien(@ModelAttribute(value = "giangvien") GiangVien gv) {
        this.giangVienService.addOrUpdateGiangVien(gv);
        return "redirect:/giangvien";
    }
    
    @GetMapping("/addgiangvien")
    public String showAddGiangVienForm(Model model) {
        model.addAttribute("sinhvien", new GiangVien());
        return "addgiangvien"; 
    }
    
    @GetMapping("/importgiangvien")
    public String importGiangViens() {
        this.giangVienService.importGiangVienFromUser();
        return "redirect:/giangvien";
    }
}
