package com.nhom15.services.impl;

import com.nhom15.repositories.ThongBaoRepository;
import com.nhom15.repositories.ThongKeDiemRepository;
import com.nhom15.services.ThongKeDiemService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Chi Hao
 */
@Service
public class ThongKeDiemServiceImpl implements ThongKeDiemService{
    @Autowired
    private ThongKeDiemRepository thongKeDiemRepo;
    
    @Override
    public List<Object[]> thongKeDiemKhoaLuanTheoNam() {
        return this.thongKeDiemRepo.thongKeDiemKhoaLuanTheoNam();
    }
    
    @Override
    public List<Object[]> thongKeTanSuatKhoaLuanTheoKhoa() {
        return this.thongKeDiemRepo.thongKeTanSuatKhoaLuanTheoKhoa();
    }
}
