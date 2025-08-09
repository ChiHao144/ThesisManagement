/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom15.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 *
 * @author ACER
 */
@Entity
@Table(name = "khoa_luan")
@NamedQueries({
    @NamedQuery(name = "KhoaLuan.findAll", query = "SELECT k FROM KhoaLuan k"),
    @NamedQuery(name = "KhoaLuan.findById", query = "SELECT k FROM KhoaLuan k WHERE k.id = :id"),
    @NamedQuery(name = "KhoaLuan.findByChuDe", query = "SELECT k FROM KhoaLuan k WHERE k.chuDe = :chuDe"),
    @NamedQuery(name = "KhoaLuan.findByNgayTao", query = "SELECT k FROM KhoaLuan k WHERE k.ngayTao = :ngayTao")})
public class KhoaLuan implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 255)
    @Column(name = "chu_de")
    private String chuDe;
    @Column(name = "ngay_tao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ngayTao;
    @JsonIgnore
    @OneToMany(mappedBy = "khoaLuanId")
    private Set<Diem> diemSet;
    @JsonIgnore
    @OneToMany(mappedBy = "khoaLuanId")
    private Set<ThongBao> thongBaoSet;
    @JoinColumn(name = "gvhd1", referencedColumnName = "id")
    @ManyToOne
    private GiangVien gvhd1;
    @JoinColumn(name = "gvhd2", referencedColumnName = "id", nullable = true)
    @ManyToOne
    private GiangVien gvhd2;
    @JoinColumn(name = "gvpb", referencedColumnName = "id", nullable = true)
    @ManyToOne
    private GiangVien gvpb;
    @JoinColumn(name = "hoidong_id", referencedColumnName = "id", nullable = true)
    @ManyToOne
    private HoiDong hoidongId;
    @JoinColumn(name = "sinh_vien_id", referencedColumnName = "id")
    @OneToOne
    private SinhVien sinhVienId;

    public KhoaLuan() {
    }

    public KhoaLuan(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getChuDe() {
        return chuDe;
    }

    public void setChuDe(String chuDe) {
        this.chuDe = chuDe;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public Set<Diem> getDiemSet() {
        return diemSet;
    }

    public void setDiemSet(Set<Diem> diemSet) {
        this.diemSet = diemSet;
    }

    public Set<ThongBao> getThongBaoSet() {
        return thongBaoSet;
    }

    public void setThongBaoSet(Set<ThongBao> thongBaoSet) {
        this.thongBaoSet = thongBaoSet;
    }

    public GiangVien getGvhd1() {
        return gvhd1;
    }

    public void setGvhd1(GiangVien gvhd1) {
        this.gvhd1 = gvhd1;
    }

    public GiangVien getGvhd2() {
        return gvhd2;
    }

    public void setGvhd2(GiangVien gvhd2) {
        this.gvhd2 = gvhd2;
    }

    public GiangVien getGvpb() {
        return gvpb;
    }

    public void setGvpb(GiangVien gvpb) {
        this.gvpb = gvpb;
    }

    public HoiDong getHoidongId() {
        return hoidongId;
    }

    public void setHoidongId(HoiDong hoidongId) {
        this.hoidongId = hoidongId;
    }

    public SinhVien getSinhVienId() {
        return sinhVienId;
    }

    public void setSinhVienId(SinhVien sinhVienId) {
        this.sinhVienId = sinhVienId;
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
        if (!(object instanceof KhoaLuan)) {
            return false;
        }
        KhoaLuan other = (KhoaLuan) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nhom15.pojo.KhoaLuan[ id=" + id + " ]";
    }
    
}
