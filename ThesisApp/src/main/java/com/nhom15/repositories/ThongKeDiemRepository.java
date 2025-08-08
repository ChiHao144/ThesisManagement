package com.nhom15.repositories;

import java.util.List;

/**
 *
 * @author Chi Hao
 */
public interface ThongKeDiemRepository {
    List<Object[]> getTongDiemTheoGiangVien(int khoaLuanId);
    Double getDiemTrungBinh(int khoaLuanId);
    List<Object[]> thongKeDiemKhoaLuanTheoNam();
    List<Object[]> thongKeTanSuatKhoaLuanTheoKhoa();
}
