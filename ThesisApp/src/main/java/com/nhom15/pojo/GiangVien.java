/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom15.pojo;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
@Table(name = "giang_vien")
@NamedQueries({
    @NamedQuery(name = "GiangVien.findAll", query = "SELECT g FROM GiangVien g"),
    @NamedQuery(name = "GiangVien.findById", query = "SELECT g FROM GiangVien g WHERE g.id = :id"),
    @NamedQuery(name = "GiangVien.findByHocHam", query = "SELECT g FROM GiangVien g WHERE g.hocHam = :hocHam")})
public class GiangVien implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Size(max = 100)
    @Column(name = "hoc_ham")
    private String hocHam;
    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private User user;
    @OneToMany(mappedBy = "gvId")
    private Set<ThanhVien> thanhVienSet;
    @OneToMany(mappedBy = "gvhd1")
    private Set<KhoaLuan> khoaLuanSet;
    @OneToMany(mappedBy = "gvhd2")
    private Set<KhoaLuan> khoaLuanSet1;
    @OneToMany(mappedBy = "gvpb")
    private Set<KhoaLuan> khoaLuanSet2;

    public GiangVien() {
    }

    public GiangVien(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHocHam() {
        return hocHam;
    }

    public void setHocHam(String hocHam) {
        this.hocHam = hocHam;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public Set<KhoaLuan> getKhoaLuanSet1() {
        return khoaLuanSet1;
    }

    public void setKhoaLuanSet1(Set<KhoaLuan> khoaLuanSet1) {
        this.khoaLuanSet1 = khoaLuanSet1;
    }

    public Set<KhoaLuan> getKhoaLuanSet2() {
        return khoaLuanSet2;
    }

    public void setKhoaLuanSet2(Set<KhoaLuan> khoaLuanSet2) {
        this.khoaLuanSet2 = khoaLuanSet2;
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
        if (!(object instanceof GiangVien)) {
            return false;
        }
        GiangVien other = (GiangVien) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nhom15.pojo.GiangVien[ id=" + id + " ]";
    }
    
}
