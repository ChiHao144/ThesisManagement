package com.nhom15.services;

import com.nhom15.pojo.GiangVien;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Chi Hao
 */
public interface GiangVienService {
    List<GiangVien> getGiangViens(Map<String, String> params);
    void addOrUpdateGiangVien(GiangVien gv);
    GiangVien getGiangVienById(int id);
    void importGiangVienFromUser();
    GiangVien getByUsername(String username);
}
