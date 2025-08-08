/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom15.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author ACER
 */
@Entity
@Table(name = "hoi_dong")
@NamedQueries({
    @NamedQuery(name = "HoiDong.findAll", query = "SELECT h FROM HoiDong h"),
    @NamedQuery(name = "HoiDong.findById", query = "SELECT h FROM HoiDong h WHERE h.id = :id"),
    @NamedQuery(name = "HoiDong.findByNgayBaoVe", query = "SELECT h FROM HoiDong h WHERE h.ngayBaoVe = :ngayBaoVe"),
    @NamedQuery(name = "HoiDong.findByNoiDungBaoVe", query = "SELECT h FROM HoiDong h WHERE h.noiDungBaoVe = :noiDungBaoVe"),
    @NamedQuery(name = "HoiDong.findByDaKhoa", query = "SELECT h FROM HoiDong h WHERE h.daKhoa = :daKhoa")})
public class HoiDong implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "ngay_bao_ve")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ngayBaoVe;
    @Size(max = 255)
    @Column(name = "noi_dung_bao_ve")
    private String noiDungBaoVe;
    @Column(name = "da_khoa")
    private Boolean daKhoa;
    @OneToMany(mappedBy = "hoiDongId", fetch = FetchType.EAGER)
    private Set<ThanhVien> thanhVienSet;
    @OneToMany(mappedBy = "hoidongId")
    @JsonIgnore
    private Set<KhoaLuan> khoaLuanSet;

    public HoiDong() {
        this.daKhoa = false;
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

    public Boolean getDaKhoa() {
        return daKhoa;
    }

    public void setDaKhoa(Boolean daKhoa) {
        this.daKhoa = daKhoa;
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
        return "com.nhom15.pojo.HoiDong[ id=" + id + " ]";
    }
    
}
