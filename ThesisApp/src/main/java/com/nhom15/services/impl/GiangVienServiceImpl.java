package com.nhom15.services.impl;

import com.nhom15.pojo.GiangVien;
import com.nhom15.repositories.GiangVienRepository;
import com.nhom15.services.GiangVienService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Chi Hao
 */
@Service
public class GiangVienServiceImpl implements GiangVienService{
    @Autowired
    private GiangVienRepository giangVienRepo;
    
    @Override
    public List<GiangVien> getGiangViens(Map<String, String> params) {
        return this.giangVienRepo.getGiangViens(params);
    }

    @Override
    public void addOrUpdateGiangVien(GiangVien gv) {
        this.giangVienRepo.addOrUpdateGiangVien(gv);
    }

    @Override
    public void importGiangVienFromUser() {
        giangVienRepo.importGiangVienFromUser();
    }
    
    @Override
    public GiangVien getGiangVienById(int id) {
        return this.giangVienRepo.getGiangVienById(id);
    }
}
