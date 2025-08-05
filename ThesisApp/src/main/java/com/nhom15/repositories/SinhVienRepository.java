package com.nhom15.repositories;

import com.nhom15.pojo.SinhVien;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Chi Hao
 */
public interface SinhVienRepository {
    List<SinhVien> getSinhViens(Map<String, String> params);
    void addOrUpdateSinhVien(SinhVien sv);
    
    SinhVien getSinhVienById(int id);
    void importSinhVienFromUser();
}
