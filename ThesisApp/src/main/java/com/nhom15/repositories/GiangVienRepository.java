package com.nhom15.repositories;

import com.nhom15.pojo.GiangVien;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Chi Hao
 */
public interface GiangVienRepository {
    List<GiangVien> getGiangViens(Map<String, String> params);
    void addOrUpdateGiangVien(GiangVien gv);

    public void importGiangVienFromUser();
}
