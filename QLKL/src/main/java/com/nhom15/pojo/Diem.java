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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author ACER
 */
@Entity
@Table(name = "diem")
@NamedQueries({
    @NamedQuery(name = "Diem.findAll", query = "SELECT d FROM Diem d"),
    @NamedQuery(name = "Diem.findById", query = "SELECT d FROM Diem d WHERE d.id = :id"),
    @NamedQuery(name = "Diem.findByDiem", query = "SELECT d FROM Diem d WHERE d.diem = :diem"),
    @NamedQuery(name = "Diem.findByCreatedAt", query = "SELECT d FROM Diem d WHERE d.createdAt = :createdAt")})
public class Diem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "diem")
    private Double diem;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @JoinColumn(name = "khoa_luan_id", referencedColumnName = "id")
    @ManyToOne
    private KhoaLuan khoaLuanId;
    @JoinColumn(name = "thanhvien_id", referencedColumnName = "id")
    @ManyToOne
    private ThanhVien thanhvienId;
    @JoinColumn(name = "tieu_chi_id", referencedColumnName = "id")
    @ManyToOne
    private TieuChi tieuChiId;

    public Diem() {
    }

    public Diem(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getDiem() {
        return diem;
    }

    public void setDiem(Double diem) {
        this.diem = diem;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public KhoaLuan getKhoaLuanId() {
        return khoaLuanId;
    }

    public void setKhoaLuanId(KhoaLuan khoaLuanId) {
        this.khoaLuanId = khoaLuanId;
    }

    public ThanhVien getThanhvienId() {
        return thanhvienId;
    }

    public void setThanhvienId(ThanhVien thanhvienId) {
        this.thanhvienId = thanhvienId;
    }

    public TieuChi getTieuChiId() {
        return tieuChiId;
    }

    public void setTieuChiId(TieuChi tieuChiId) {
        this.tieuChiId = tieuChiId;
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
        if (!(object instanceof Diem)) {
            return false;
        }
        Diem other = (Diem) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nhom15.pojo.Diem[ id=" + id + " ]";
    }
    
}
