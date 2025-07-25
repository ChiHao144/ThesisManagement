

package com.pch.hibernatethesismanagement;

import com.pch.repositories.impl.KhoaLuanRepositoryImpl;
import com.pch.repositories.impl.SinhVienRepositoryImpl;

/**
 *
 * @author Chi Hao
 */
public class HibernateThesisManagement {

    public static void main(String[] args) {
//        KhoaLuanRepositoryImpl s =  new KhoaLuanRepositoryImpl();
//        s.getKhoaLuans().forEach(k -> System.out.printf("%d - %s\n",k.getId(), k.getChuDe()));
    
          SinhVieshViens(null).forEach(p -> System.out.printf("%d - %s\n", p.getId(), p.getKhoa()));
    
    }
}
