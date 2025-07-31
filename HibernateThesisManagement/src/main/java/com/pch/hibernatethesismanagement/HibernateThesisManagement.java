

package com.pch.hibernatethesismanagement;

import com.pch.repositories.impl.KhoaLuanRepositoryImpl;
import com.pch.repositories.impl.SinhVienRepositoryImpl;
import com.pch.repositories.impl.UserRepositoryImpl;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Chi Hao
 */
public class HibernateThesisManagement {

    public static void main(String[] args) {
//        KhoaLuanRepositoryImpl s =  new KhoaLuanRepositoryImpl();
//        s.getKhoaLuans().forEach(k -> System.out.printf("%d - %s\n",k.getId(), k.getChuDe()));
    
//          SinhVieshViens(null).forEach(p -> System.out.printf("%d - %s\n", p.getId(), p.getKhoa()));
//          SinhVienRepositoryImpl s = new SinhVienRepositoryImpl();
//          s.getSinhViens(params)

          Map<String, String> params = new HashMap<>();
//          params.put("kw", "Dương");
            params.put("page", "4");

          UserRepositoryImpl s = new UserRepositoryImpl();
          s.getUsers(params).forEach(p -> System.out.printf("%d - %s - %s - %s\n", p.getId(), p.getUsername(), p.getRole(), p.getFullname()));
    }
}
