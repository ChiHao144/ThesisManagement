package com.nhom15.controller;

import com.nhom15.pojo.User;
import com.nhom15.services.GiangVienService;
import com.nhom15.services.HoiDongService;
import com.nhom15.services.KhoaLuanService;
import com.nhom15.services.SinhVienService;
import com.nhom15.services.UserService;
import jakarta.persistence.Query;
import java.util.Map;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Chi Hao
 */
@Controller
@ControllerAdvice
public class HomeController {
    @Autowired
    private UserService userService;
    
    @Autowired
    private KhoaLuanService khoaLuanService; 
    
    @Autowired
    private GiangVienService giangVienService;

    @Autowired
    private SinhVienService sinhVienService;

    @Autowired
    private HoiDongService hoiDongService;
    
    @ModelAttribute
    public void commonResponses(Model model, @RequestParam Map<String, String> params) {
        model.addAttribute("giangViens", giangVienService.getGiangViens(params));
        model.addAttribute("sinhViens", sinhVienService.getSinhViens(params));
        model.addAttribute("hoiDongs", hoiDongService.getHoiDongs(params));
        model.addAttribute("khoaluans", khoaLuanService.getKhoaLuans(params));
    }
    
    @RequestMapping("/")
    public String index(Model model, @RequestParam Map<String, String> params) {
        model.addAttribute("user", this.userService.getUsers(params));
        return "index";
    }
}
