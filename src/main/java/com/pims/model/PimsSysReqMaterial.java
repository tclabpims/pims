package com.pims.model;

import javax.persistence.*;
import java.sql.Time;

/**
 * Created by king on 2016/9/28.
 */
@Entity
@Table(name = "PIMS_SYS_REQ_MATERIAL", schema = "KFTEST", catalog = "")
public class PimsSysReqMaterial {
    private long materialid;
    private String matname;
    private long mattype;
    private String matspecial;
    private String matsort;
    private Long matuseflag;
    private String matpinyincode;
    private String matfivestrokecode;
    private String matreamrk;
    private String matfirstv;
    private String matfirstn;
    private Time matfirstd;
    private String matcreateuser;
    private Time matcreatetime;

    @Id
    @Column(name = "MATERIALID")
    public long getMaterialid() {
        return materialid;
    }

    public void setMaterialid(long materialid) {
        this.materialid = materialid;
    }

    @Basic
    @Column(name = "MATNAME")
    public String getMatname() {
        return matname;
    }

    public void setMatname(String matname) {
        this.matname = matname;
    }

    @Basic
    @Column(name = "MATTYPE")
    public long getMattype() {
        return mattype;
    }

    public void setMattype(long mattype) {
        this.mattype = mattype;
    }

    @Basic
    @Column(name = "MATSPECIAL")
    public String getMatspecial() {
        return matspecial;
    }

    public void setMatspecial(String matspecial) {
        this.matspecial = matspecial;
    }

    @Basic
    @Column(name = "MATSORT")
    public String getMatsort() {
        return matsort;
    }

    public void setMatsort(String matsort) {
        this.matsort = matsort;
    }

    @Basic
    @Column(name = "MATUSEFLAG")
    public Long getMatuseflag() {
        return matuseflag;
    }

    public void setMatuseflag(Long matuseflag) {
        this.matuseflag = matuseflag;
    }

    @Basic
    @Column(name = "MATPINYINCODE")
    public String getMatpinyincode() {
        return matpinyincode;
    }

    public void setMatpinyincode(String matpinyincode) {
        this.matpinyincode = matpinyincode;
    }

    @Basic
    @Column(name = "MATFIVESTROKECODE")
    public String getMatfivestrokecode() {
        return matfivestrokecode;
    }

    public void setMatfivestrokecode(String matfivestrokecode) {
        this.matfivestrokecode = matfivestrokecode;
    }

    @Basic
    @Column(name = "MATREAMRK")
    public String getMatreamrk() {
        return matreamrk;
    }

    public void setMatreamrk(String matreamrk) {
        this.matreamrk = matreamrk;
    }

    @Basic
    @Column(name = "MATFIRSTV")
    public String getMatfirstv() {
        return matfirstv;
    }

    public void setMatfirstv(String matfirstv) {
        this.matfirstv = matfirstv;
    }

    @Basic
    @Column(name = "MATFIRSTN")
    public String getMatfirstn() {
        return matfirstn;
    }

    public void setMatfirstn(String matfirstn) {
        this.matfirstn = matfirstn;
    }

    @Basic
    @Column(name = "MATFIRSTD")
    public Time getMatfirstd() {
        return matfirstd;
    }

    public void setMatfirstd(Time matfirstd) {
        this.matfirstd = matfirstd;
    }

    @Basic
    @Column(name = "MATCREATEUSER")
    public String getMatcreateuser() {
        return matcreateuser;
    }

    public void setMatcreateuser(String matcreateuser) {
        this.matcreateuser = matcreateuser;
    }

    @Basic
    @Column(name = "MATCREATETIME")
    public Time getMatcreatetime() {
        return matcreatetime;
    }

    public void setMatcreatetime(Time matcreatetime) {
        this.matcreatetime = matcreatetime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PimsSysReqMaterial that = (PimsSysReqMaterial) o;

        if (materialid != that.materialid) return false;
        if (mattype != that.mattype) return false;
        if (matname != null ? !matname.equals(that.matname) : that.matname != null) return false;
        if (matspecial != null ? !matspecial.equals(that.matspecial) : that.matspecial != null) return false;
        if (matsort != null ? !matsort.equals(that.matsort) : that.matsort != null) return false;
        if (matuseflag != null ? !matuseflag.equals(that.matuseflag) : that.matuseflag != null) return false;
        if (matpinyincode != null ? !matpinyincode.equals(that.matpinyincode) : that.matpinyincode != null)
            return false;
        if (matfivestrokecode != null ? !matfivestrokecode.equals(that.matfivestrokecode) : that.matfivestrokecode != null)
            return false;
        if (matreamrk != null ? !matreamrk.equals(that.matreamrk) : that.matreamrk != null) return false;
        if (matfirstv != null ? !matfirstv.equals(that.matfirstv) : that.matfirstv != null) return false;
        if (matfirstn != null ? !matfirstn.equals(that.matfirstn) : that.matfirstn != null) return false;
        if (matfirstd != null ? !matfirstd.equals(that.matfirstd) : that.matfirstd != null) return false;
        if (matcreateuser != null ? !matcreateuser.equals(that.matcreateuser) : that.matcreateuser != null)
            return false;
        if (matcreatetime != null ? !matcreatetime.equals(that.matcreatetime) : that.matcreatetime != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (materialid ^ (materialid >>> 32));
        result = 31 * result + (matname != null ? matname.hashCode() : 0);
        result = 31 * result + (int) (mattype ^ (mattype >>> 32));
        result = 31 * result + (matspecial != null ? matspecial.hashCode() : 0);
        result = 31 * result + (matsort != null ? matsort.hashCode() : 0);
        result = 31 * result + (matuseflag != null ? matuseflag.hashCode() : 0);
        result = 31 * result + (matpinyincode != null ? matpinyincode.hashCode() : 0);
        result = 31 * result + (matfivestrokecode != null ? matfivestrokecode.hashCode() : 0);
        result = 31 * result + (matreamrk != null ? matreamrk.hashCode() : 0);
        result = 31 * result + (matfirstv != null ? matfirstv.hashCode() : 0);
        result = 31 * result + (matfirstn != null ? matfirstn.hashCode() : 0);
        result = 31 * result + (matfirstd != null ? matfirstd.hashCode() : 0);
        result = 31 * result + (matcreateuser != null ? matcreateuser.hashCode() : 0);
        result = 31 * result + (matcreatetime != null ? matcreatetime.hashCode() : 0);
        return result;
    }
}
