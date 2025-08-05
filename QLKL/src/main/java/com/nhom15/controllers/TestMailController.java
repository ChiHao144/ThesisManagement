/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom15.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author ACER
 */
@Controller
public class TestMailController {

    @Autowired
    private JavaMailSender mailSender;

    @RequestMapping("/test-mail")
    @ResponseBody
    public String testMail() {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("nguyenvanhieua97@gmail.com");
            message.setTo("nguyenvanbuu1104@gmail.com"); // đổi thành email thật
            message.setSubject("Test gửi mail");
            message.setText("Đây là mail test từ QLKL");

            mailSender.send(message);
            return "✅ Gửi thành công!";
        } catch (Exception e) {
            e.printStackTrace();
            return "❌ Lỗi gửi: " + e.getMessage();
        }
    }
}

