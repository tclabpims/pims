package com.pims.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by king on 2016/10/21.
 */
@Entity
@Table(name = "PIMS_COMMON_BASE_DATA")
public class PimsCommonBaseData {
    private long dataid;
    private long bdcustomerid;
    private boolean bddatatype;
    private String bddatanamech;
    private String bddatanameen;
    private String bddatasort;
    private boolean bduseflag;
    private String bdpinyincode;
    private String bdfivestroke;
    private String bdhisid;
    private String bdfirstv;
    private String bdsecondv;
    private Long bdfirstn;
    private String bdcreateuser;
    private Date bdcreatetime;

    @Id
    @Column(name = "DATAID")
    public long getDataid() {
        return dataid;
    }

    public void setDataid(long dataid) {
        this.dataid = dataid;
    }

    @Basic
    @Column(name = "BDCUSTOMERID")
    public long getBdcustomerid() {
        return bdcustomerid;
    }

    public void setBdcustomerid(long bdcustomerid) {
        this.bdcustomerid = bdcustomerid;
    }

    @Basic
    @Column(name = "BDDATATYPE")
    public boolean isBddatatype() {
        return bddatatype;
    }

    public void setBddatatype(boolean bddatatype) {
        this.bddatatype = bddatatype;
    }

    @Basic
    @Column(name = "BDDATANAMECH")
    public String getBddatanamech() {
        return bddatanamech;
    }

    public void setBddatanamech(String bddatanamech) {
        this.bddatanamech = bddatanamech;
    }

    @Basic
    @Column(name = "BDDATANAMEEN")
    public String getBddatanameen() {
        return bddatanameen;
    }

    public void setBddatanameen(String bddatanameen) {
        this.bddatanameen = bddatanameen;
    }

    @Basic
    @Column(name = "BDDATASORT")
    public String getBddatasort() {
        return bddatasort;
    }

    public void setBddatasort(String bddatasort) {
        this.bddatasort = bddatasort;
    }

    @Basic
    @Column(name = "BDUSEFLAG")
    public boolean isBduseflag() {
        return bduseflag;
    }

    public void setBduseflag(boolean bduseflag) {
        this.bduseflag = bduseflag;
    }

    @Basic
    @Column(name = "BDPINYINCODE")
    public String getBdpinyincode() {
        return bdpinyincode;
    }

    public void setBdpinyincode(String bdpinyincode) {
        this.bdpinyincode = bdpinyincode;
    }

    @Basic
    @Column(name = "BDFIVESTROKE")
    public String getBdfivestroke() {
        return bdfivestroke;
    }

    public void setBdfivestroke(String bdfivestroke) {
        this.bdfivestroke = bdfivestroke;
    }

    @Basic
    @Column(name = "BDHISID")
    public String getBdhisid() {
        return bdhisid;
    }

    public void setBdhisid(String bdhisid) {
        this.bdhisid = bdhisid;
    }

    @Basic
    @Column(name = "BDFIRSTV")
    public String getBdfirstv() {
        return bdfirstv;
    }

    public void setBdfirstv(String bdfirstv) {
        this.bdfirstv = bdfirstv;
    }

    @Basic
    @Column(name = "BDSECONDV")
    public String getBdsecondv() {
        return bdsecondv;
    }

    public void setBdsecondv(String bdsecondv) {
        this.bdsecondv = bdsecondv;
    }

    @Basic
    @Column(name = "BDFIRSTN")
    public Long getBdfirstn() {
        return bdfirstn;
    }

    public void setBdfirstn(Long bdfirstn) {
        this.bdfirstn = bdfirstn;
    }

    @Basic
    @Column(name = "BDCREATEUSER")
    public String getBdcreateuser() {
        return bdcreateuser;
    }

    public void setBdcreateuser(String bdcreateuser) {
        this.bdcreateuser = bdcreateuser;
    }

    @Basic
    @Column(name = "BDCREATETIME")
    public Date getBdcreatetime() {
        return bdcreatetime;
    }

    public void setBdcreatetime(Date bdcreatetime) {
        this.bdcreatetime = bdcreatetime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PimsCommonBaseData that = (PimsCommonBaseData) o;

        if (dataid != that.dataid) return false;
        if (bdcustomerid != that.bdcustomerid) return false;
        if (bddatatype != that.bddatatype) return false;
        if (bduseflag != that.bduseflag) return false;
        if (bddatanamech != null ? !bddatanamech.equals(that.bddatanamech) : that.bddatanamech != null) return false;
        if (bddatanameen != null ? !bddatanameen.equals(that.bddatanameen) : that.bddatanameen != null) return false;
        if (bddatasort != null ? !bddatasort.equals(that.bddatasort) : that.bddatasort != null) return false;
        if (bdpinyincode != null ? !bdpinyincode.equals(that.bdpinyincode) : that.bdpinyincode != null) return false;
        if (bdfivestroke != null ? !bdfivestroke.equals(that.bdfivestroke) : that.bdfivestroke != null) return false;
        if (bdhisid != null ? !bdhisid.equals(that.bdhisid) : that.bdhisid != null) return false;
        if (bdfirstv != null ? !bdfirstv.equals(that.bdfirstv) : that.bdfirstv != null) return false;
        if (bdsecondv != null ? !bdsecondv.equals(that.bdsecondv) : that.bdsecondv != null) return false;
        if (bdfirstn != null ? !bdfirstn.equals(that.bdfirstn) : that.bdfirstn != null) return false;
        if (bdcreateuser != null ? !bdcreateuser.equals(that.bdcreateuser) : that.bdcreateuser != null) return false;
        if (bdcreatetime != null ? !bdcreatetime.equals(that.bdcreatetime) : that.bdcreatetime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (dataid ^ (dataid >>> 32));
        result = 31 * result + (int) (bdcustomerid ^ (bdcustomerid >>> 32));
        result = 31 * result + (bddatatype ? 1 : 0);
        result = 31 * result + (bddatanamech != null ? bddatanamech.hashCode() : 0);
        result = 31 * result + (bddatanameen != null ? bddatanameen.hashCode() : 0);
        result = 31 * result + (bddatasort != null ? bddatasort.hashCode() : 0);
        result = 31 * result + (bduseflag ? 1 : 0);
        result = 31 * result + (bdpinyincode != null ? bdpinyincode.hashCode() : 0);
        result = 31 * result + (bdfivestroke != null ? bdfivestroke.hashCode() : 0);
        result = 31 * result + (bdhisid != null ? bdhisid.hashCode() : 0);
        result = 31 * result + (bdfirstv != null ? bdfirstv.hashCode() : 0);
        result = 31 * result + (bdsecondv != null ? bdsecondv.hashCode() : 0);
        result = 31 * result + (bdfirstn != null ? bdfirstn.hashCode() : 0);
        result = 31 * result + (bdcreateuser != null ? bdcreateuser.hashCode() : 0);
        result = 31 * result + (bdcreatetime != null ? bdcreatetime.hashCode() : 0);
        return result;
    }
}
