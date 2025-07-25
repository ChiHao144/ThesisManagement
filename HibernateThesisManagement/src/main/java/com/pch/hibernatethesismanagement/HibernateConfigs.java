package com.pch.hibernatethesismanagement;

import com.pch.pojo.Diem;
import com.pch.pojo.GiangVien;
import com.pch.pojo.HoiDong;
import com.pch.pojo.KhoaLuan;
import com.pch.pojo.SinhVien;
import com.pch.pojo.ThanhVien;
import com.pch.pojo.TieuChi;
import com.pch.pojo.User;
import java.util.Properties;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

/**
 *
 * @author Chi Hao
 */
public class HibernateConfigs {
    private static final SessionFactory FACTORY;
    
    static {
        Configuration conf = new Configuration();
        Properties props = new Properties();
        props.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
        props.put(Environment.JAKARTA_JDBC_DRIVER, "com.mysql.cj.jdbc.Driver");
        props.put(Environment.JAKARTA_JDBC_URL, "jdbc:mysql://localhost/quan_ly_khoa_luan");
        props.put(Environment.JAKARTA_JDBC_USER, "root");
        props.put(Environment.JAKARTA_JDBC_PASSWORD, "Admin@123");
        props.put(Environment.SHOW_SQL, "true");
        conf.setProperties(props);
        
        conf.addAnnotatedClass(KhoaLuan.class);
        conf.addAnnotatedClass(GiangVien.class);
        conf.addAnnotatedClass(Diem.class);
        conf.addAnnotatedClass(HoiDong.class);
        conf.addAnnotatedClass(SinhVien.class);
        conf.addAnnotatedClass(ThanhVien.class);
        conf.addAnnotatedClass(TieuChi.class);
        conf.addAnnotatedClass(User.class);
        
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(conf.getProperties()).build();
        FACTORY = conf.buildSessionFactory(serviceRegistry);
    }

    /**
     * @return the FACTORY
     */
    public static SessionFactory getFACTORY() {
        return FACTORY;
    }
    
}
