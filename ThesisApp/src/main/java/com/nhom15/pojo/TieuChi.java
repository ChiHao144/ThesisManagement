package com.nhom15.pojo;

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
import java.io.Serializable;
import java.util.Set;

/**
 *
 * @author Chi Hao
 */
@Entity
@Table(name = "tieu_chi")
@NamedQueries({
    @NamedQuery(name = "TieuChi.findAll", query = "SELECT t FROM TieuChi t"),
    @NamedQuery(name = "TieuChi.findById", query = "SELECT t FROM TieuChi t WHERE t.id = :id"),
    @NamedQuery(name = "TieuChi.findByTenTc", query = "SELECT t FROM TieuChi t WHERE t.tenTc = :tenTc"),
    @NamedQuery(name = "TieuChi.findByNoiDung", query = "SELECT t FROM TieuChi t WHERE t.noiDung = :noiDung"),
    @NamedQuery(name = "TieuChi.findByDiemToiDa", query = "SELECT t FROM TieuChi t WHERE t.diemToiDa = :diemToiDa")})
public class TieuChi implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "ten_tc")
    private String tenTc;
    @Column(name = "noi_dung")
    private String noiDung;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "diem_toi_da")
    private Double diemToiDa;
    @OneToMany(mappedBy = "tieuChiId")
    private Set<Diem> diemSet;

    public TieuChi() {
    }

    public TieuChi(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTenTc() {
        return tenTc;
    }

    public void setTenTc(String tenTc) {
        this.tenTc = tenTc;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public Double getDiemToiDa() {
        return diemToiDa;
    }

    public void setDiemToiDa(Double diemToiDa) {
        this.diemToiDa = diemToiDa;
    }

    public Set<Diem> getDiemSet() {
        return diemSet;
    }

    public void setDiemSet(Set<Diem> diemSet) {
        this.diemSet = diemSet;
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
        if (!(object instanceof TieuChi)) {
            return false;
        }
        TieuChi other = (TieuChi) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nhom15.pojo.TieuChi[ id=" + id + " ]";
    }
    
}
