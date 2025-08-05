package com.nhom15.services.impl;

import com.nhom15.pojo.SinhVien;
import com.nhom15.pojo.User;
import com.nhom15.repositories.SinhVienRepository;
import com.nhom15.repositories.UserRepository;
import com.nhom15.services.SinhVienService;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Chi Hao
 */
@Service
public class SinhVienServiceImpl implements SinhVienService {

    @Autowired
    private SinhVienRepository sinhVienRepo;

    @Override
    public List<SinhVien> getSinhViens(Map<String, String> params) {
        return this.sinhVienRepo.getSinhViens(params);
    }

    @Override
    public void addOrUpdateSinhVien(SinhVien sv) {
        this.sinhVienRepo.addOrUpdateSinhVien(sv);
    }

    @Override
    public void importSinhVienFromUser() {
        sinhVienRepo.importSinhVienFromUser();
    }

}
