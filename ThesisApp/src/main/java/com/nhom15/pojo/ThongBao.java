/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom15.pojo;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author ACER
 */
@Entity
@Table(name = "thong_bao")
@NamedQueries({
    @NamedQuery(name = "ThongBao.findAll", query = "SELECT t FROM ThongBao t"),
    @NamedQuery(name = "ThongBao.findById", query = "SELECT t FROM ThongBao t WHERE t.id = :id"),
    @NamedQuery(name = "ThongBao.findByLoaiThongBao", query = "SELECT t FROM ThongBao t WHERE t.loaiThongBao = :loaiThongBao"),
    @NamedQuery(name = "ThongBao.findByTieuDe", query = "SELECT t FROM ThongBao t WHERE t.tieuDe = :tieuDe"),
    @NamedQuery(name = "ThongBao.findByTrangThai", query = "SELECT t FROM ThongBao t WHERE t.trangThai = :trangThai"),
    @NamedQuery(name = "ThongBao.findByNgayGui", query = "SELECT t FROM ThongBao t WHERE t.ngayGui = :ngayGui")})
public class ThongBao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "loai_thong_bao")
    private String loaiThongBao;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "tieu_de")
    private String tieuDe;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "noi_dung")
    private String noiDung;
    @Size(max = 8)
    @Column(name = "trang_thai")
    private String trangThai;
    @Column(name = "ngay_gui")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ngayGui;
    @JoinColumn(name = "khoa_luan_id", referencedColumnName = "id")
    @ManyToOne
    private KhoaLuan khoaLuanId;
    @JoinColumn(name = "nguoi_nhan_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User nguoiNhanId;

    public ThongBao() {
    }

    public ThongBao(Integer id) {
        this.id = id;
    }

    public ThongBao(Integer id, String loaiThongBao, String tieuDe, String noiDung) {
        this.id = id;
        this.loaiThongBao = loaiThongBao;
        this.tieuDe = tieuDe;
        this.noiDung = noiDung;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoaiThongBao() {
        return loaiThongBao;
    }

    public void setLoaiThongBao(String loaiThongBao) {
        this.loaiThongBao = loaiThongBao;
    }

    public String getTieuDe() {
        return tieuDe;
    }

    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public Date getNgayGui() {
        return ngayGui;
    }

    public void setNgayGui(Date ngayGui) {
        this.ngayGui = ngayGui;
    }

    public KhoaLuan getKhoaLuanId() {
        return khoaLuanId;
    }

    public void setKhoaLuanId(KhoaLuan khoaLuanId) {
        this.khoaLuanId = khoaLuanId;
    }

    public User getNguoiNhanId() {
        return nguoiNhanId;
    }

    public void setNguoiNhanId(User nguoiNhanId) {
        this.nguoiNhanId = nguoiNhanId;
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
        if (!(object instanceof ThongBao)) {
            return false;
        }
        ThongBao other = (ThongBao) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nhom15.pojo.ThongBao[ id=" + id + " ]";
    }
    
}
