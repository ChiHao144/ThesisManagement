package com.nhom15.services;

import com.nhom15.pojo.SinhVien;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Chi Hao
 */
public interface SinhVienService {
    List<SinhVien> getSinhViens(Map<String, String> params);
    void addOrUpdateSinhVien(SinhVien sv);
    void importSinhVienFromUser();

}
