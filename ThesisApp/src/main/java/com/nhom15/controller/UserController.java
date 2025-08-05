package com.nhom15.controller;

import com.nhom15.pojo.User;
import com.nhom15.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author Chi Hao
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;
    
    @GetMapping("/user")
    public String listUser(Model model) {
        model.addAttribute("user", new User());
        return "user";
    }
    
    @PostMapping("/user")
    public String addUser(@ModelAttribute(value = "user") User u) {
        this.userService.addOrUpdateUser(u);
        return "redirect:/";
    }
    
    @GetMapping("/user/{userId}")
    public String updateUser(Model model, @PathVariable(value = "userId") int id) {
         model.addAttribute("user", this.userService.getUserById(id));
         return "user";
    }
    
    @GetMapping("/login")
    public String loginView(){
        return "login";
    }
}
