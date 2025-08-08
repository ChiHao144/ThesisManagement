package com.nhom15.controller;

import com.nhom15.services.ThongKeDiemService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Chi Hao
 */
@Controller
public class ThongKeDiemController {

    @Autowired
    private ThongKeDiemService thongKeDiemService;

    @GetMapping("/thongke/diem/{khoaLuanId}")
    public String thongKeDiem(@PathVariable("khoaLuanId") int khoaLuanId, Model model) {
        List<Object[]> data = thongKeDiemService.getTongDiemTheoGiangVien(khoaLuanId);
        Double avgScore = thongKeDiemService.getDiemTrungBinh(khoaLuanId);

        List<String> labels = new ArrayList<>();
        List<Double> scores = new ArrayList<>();

        for (Object[] row : data) {
            labels.add((String) row[1]); // tên giảng viên
            scores.add(((Number) row[2]).doubleValue()); // tổng điểm
        }

        model.addAttribute("labels", labels);
        model.addAttribute("scores", scores);
        model.addAttribute("avgScore", avgScore);

        return "thongkediem"; // view name
    }

    @GetMapping("/thongkediem")
    public String redirectThongKe() {
        int defaultKhoaLuanId = 1; // hoặc lấy ID mới nhất từ DB
        return "redirect:/thongke/diem/" + defaultKhoaLuanId;
    }

    @GetMapping("/diem-khoaluan-nam")
    public String thongKeDiemTheoNam(Model model) {
        // 1. Gọi service để lấy dữ liệu thô từ cơ sở dữ liệu
        List<Object[]> rawData = thongKeDiemService.thongKeDiemKhoaLuanTheoNam();

        // 2. Chuẩn bị 2 danh sách riêng biệt cho biểu đồ
        List<Integer> namLabels = new ArrayList<>();
        List<Double> diemScores = new ArrayList<>();

        // 3. Tách dữ liệu thô vào 2 danh sách trên
        if (rawData != null) {
            for (Object[] row : rawData) {
                // Cột 0 là năm, Cột 1 là điểm
                namLabels.add(((Number) row[0]).intValue());
                diemScores.add(((Number) row[1]).doubleValue());
            }
        }

        // 4. Đưa 2 danh sách vào "model" để Thymeleaf có thể dùng
        // Tên "labels" và "scores" phải khớp với tên trong file HTML
        model.addAttribute("labels", namLabels);
        model.addAttribute("scores", diemScores);

        // 5. Trả về tên của file view (ví dụ: "thongke-diem-nam-html.html")
        // Spring MVC và Thymeleaf sẽ tự động tìm và xử lý file này.
        System.out.println("Labels: " + namLabels);
        System.out.println("Scores: " + diemScores);

        return "thongkediem";
    }

    @GetMapping("/thong-ke/tan-suat-khoa")
    public String thongKeTanSuatKhoa(Model model) {
        List<Object[]> stats = thongKeDiemService.thongKeTanSuatKhoaLuanTheoKhoa();

        List<String> khoaLabels = new ArrayList<>();
        List<Long> soLuongData = new ArrayList<>();

        for (Object[] row : stats) {
            khoaLabels.add(String.valueOf(row[0]));
            soLuongData.add(((Number) row[1]).longValue());
        }

        model.addAttribute("khoaLabels", khoaLabels);
        model.addAttribute("soLuongData", soLuongData);

        return "thongkekhoa";
    }

}
