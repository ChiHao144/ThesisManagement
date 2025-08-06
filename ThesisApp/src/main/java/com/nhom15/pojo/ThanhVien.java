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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

/**
 *
 * @author ACER
 */
@Entity
@Table(name = "thanh_vien")
@NamedQueries({
    @NamedQuery(name = "ThanhVien.findAll", query = "SELECT t FROM ThanhVien t"),
    @NamedQuery(name = "ThanhVien.findById", query = "SELECT t FROM ThanhVien t WHERE t.id = :id"),
    @NamedQuery(name = "ThanhVien.findByVaiTro", query = "SELECT t FROM ThanhVien t WHERE t.vaiTro = :vaiTro")})
public class ThanhVien implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 9)
    @Column(name = "vai_tro")
    private String vaiTro;
    @OneToMany(mappedBy = "thanhvienId")
    private Set<Diem> diemSet;
    @JoinColumn(name = "gv_id", referencedColumnName = "id")
    @ManyToOne
    private GiangVien gvId;
    @JoinColumn(name = "hoi_dong_id", referencedColumnName = "id")
    @ManyToOne
    private HoiDong hoiDongId;

    public ThanhVien() {
    }

    public ThanhVien(Integer id) {
        this.id = id;
    }

    public ThanhVien(Integer id, String vaiTro) {
        this.id = id;
        this.vaiTro = vaiTro;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(String vaiTro) {
        this.vaiTro = vaiTro;
    }

    public Set<Diem> getDiemSet() {
        return diemSet;
    }

    public void setDiemSet(Set<Diem> diemSet) {
        this.diemSet = diemSet;
    }

    public GiangVien getGvId() {
        return gvId;
    }

    public void setGvId(GiangVien gvId) {
        this.gvId = gvId;
    }

    public HoiDong getHoiDongId() {
        return hoiDongId;
    }

    public void setHoiDongId(HoiDong hoiDongId) {
        this.hoiDongId = hoiDongId;
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
        if (!(object instanceof ThanhVien)) {
            return false;
        }
        ThanhVien other = (ThanhVien) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nhom15.pojo.ThanhVien[ id=" + id + " ]";
    }
    
}
