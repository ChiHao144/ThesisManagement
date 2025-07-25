package com.pch.pojo;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 *
 * @author Chi Hao
 */
@Entity
@Table(name = "hoi_dong")
@NamedQueries({
    @NamedQuery(name = "HoiDong.findAll", query = "SELECT h FROM HoiDong h"),
    @NamedQuery(name = "HoiDong.findById", query = "SELECT h FROM HoiDong h WHERE h.id = :id"),
    @NamedQuery(name = "HoiDong.findByNgayBaoVe", query = "SELECT h FROM HoiDong h WHERE h.ngayBaoVe = :ngayBaoVe"),
    @NamedQuery(name = "HoiDong.findByNoiDungBaoVe", query = "SELECT h FROM HoiDong h WHERE h.noiDungBaoVe = :noiDungBaoVe")})
public class HoiDong implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "ngay_bao_ve")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ngayBaoVe;
    @Column(name = "noi_dung_bao_ve")
    private String noiDungBaoVe;
    @OneToMany(mappedBy = "hoiDongId")
    private Set<ThanhVien> thanhVienSet;
    @OneToMany(mappedBy = "hoidongId")
    private Set<KhoaLuan> khoaLuanSet;

    public HoiDong() {
    }

    public HoiDong(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getNgayBaoVe() {
        return ngayBaoVe;
    }

    public void setNgayBaoVe(Date ngayBaoVe) {
        this.ngayBaoVe = ngayBaoVe;
    }

    public String getNoiDungBaoVe() {
        return noiDungBaoVe;
    }

    public void setNoiDungBaoVe(String noiDungBaoVe) {
        this.noiDungBaoVe = noiDungBaoVe;
    }

    public Set<ThanhVien> getThanhVienSet() {
        return thanhVienSet;
    }

    public void setThanhVienSet(Set<ThanhVien> thanhVienSet) {
        this.thanhVienSet = thanhVienSet;
    }

    public Set<KhoaLuan> getKhoaLuanSet() {
        return khoaLuanSet;
    }

    public void setKhoaLuanSet(Set<KhoaLuan> khoaLuanSet) {
        this.khoaLuanSet = khoaLuanSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HoiDong)) {
            return false;
        }
        HoiDong other = (HoiDong) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pch.pojo.HoiDong[ id=" + id + " ]";
    }
    
}
