package com.pims.model;

import javax.persistence.*;
import java.sql.Time;

/**
 * Created by king on 2016/9/28.
 */
@Entity
@Table(name = "PIMS_SYS_REPORT_ITEMS", schema = "KFTEST", catalog = "")
public class PimsSysReportItems {
    private long reportitemid;
    private String rptname;
    private String rptenglishname;
    private String rptelementid;
    private String rptelementname;
    private String rptitemsort;
    private String rptpinyincode;
    private String rptfivestroke;
    private Long rptitemtype;
    private Long rptuseflag;
    private String rptdefaultvalue;
    private String rptrefvalue1;
    private String rptavgvalue1;
    private String rptrefvalue2;
    private String rptavgvalue2;
    private String rptcreateuser;
    private Time rptcreatetime;

    @Id
    @Column(name = "REPORTITEMID")
    public long getReportitemid() {
        return reportitemid;
    }

    public void setReportitemid(long reportitemid) {
        this.reportitemid = reportitemid;
    }

    @Basic
    @Column(name = "RPTNAME")
    public String getRptname() {
        return rptname;
    }

    public void setRptname(String rptname) {
        this.rptname = rptname;
    }

    @Basic
    @Column(name = "RPTENGLISHNAME")
    public String getRptenglishname() {
        return rptenglishname;
    }

    public void setRptenglishname(String rptenglishname) {
        this.rptenglishname = rptenglishname;
    }

    @Basic
    @Column(name = "RPTELEMENTID")
    public String getRptelementid() {
        return rptelementid;
    }

    public void setRptelementid(String rptelementid) {
        this.rptelementid = rptelementid;
    }

    @Basic
    @Column(name = "RPTELEMENTNAME")
    public String getRptelementname() {
        return rptelementname;
    }

    public void setRptelementname(String rptelementname) {
        this.rptelementname = rptelementname;
    }

    @Basic
    @Column(name = "RPTITEMSORT")
    public String getRptitemsort() {
        return rptitemsort;
    }

    public void setRptitemsort(String rptitemsort) {
        this.rptitemsort = rptitemsort;
    }

    @Basic
    @Column(name = "RPTPINYINCODE")
    public String getRptpinyincode() {
        return rptpinyincode;
    }

    public void setRptpinyincode(String rptpinyincode) {
        this.rptpinyincode = rptpinyincode;
    }

    @Basic
    @Column(name = "RPTFIVESTROKE")
    public String getRptfivestroke() {
        return rptfivestroke;
    }

    public void setRptfivestroke(String rptfivestroke) {
        this.rptfivestroke = rptfivestroke;
    }

    @Basic
    @Column(name = "RPTITEMTYPE")
    public Long getRptitemtype() {
        return rptitemtype;
    }

    public void setRptitemtype(Long rptitemtype) {
        this.rptitemtype = rptitemtype;
    }

    @Basic
    @Column(name = "RPTUSEFLAG")
    public Long getRptuseflag() {
        return rptuseflag;
    }

    public void setRptuseflag(Long rptuseflag) {
        this.rptuseflag = rptuseflag;
    }

    @Basic
    @Column(name = "RPTDEFAULTVALUE")
    public String getRptdefaultvalue() {
        return rptdefaultvalue;
    }

    public void setRptdefaultvalue(String rptdefaultvalue) {
        this.rptdefaultvalue = rptdefaultvalue;
    }

    @Basic
    @Column(name = "RPTREFVALUE1")
    public String getRptrefvalue1() {
        return rptrefvalue1;
    }

    public void setRptrefvalue1(String rptrefvalue1) {
        this.rptrefvalue1 = rptrefvalue1;
    }

    @Basic
    @Column(name = "RPTAVGVALUE1")
    public String getRptavgvalue1() {
        return rptavgvalue1;
    }

    public void setRptavgvalue1(String rptavgvalue1) {
        this.rptavgvalue1 = rptavgvalue1;
    }

    @Basic
    @Column(name = "RPTREFVALUE2")
    public String getRptrefvalue2() {
        return rptrefvalue2;
    }

    public void setRptrefvalue2(String rptrefvalue2) {
        this.rptrefvalue2 = rptrefvalue2;
    }

    @Basic
    @Column(name = "RPTAVGVALUE2")
    public String getRptavgvalue2() {
        return rptavgvalue2;
    }

    public void setRptavgvalue2(String rptavgvalue2) {
        this.rptavgvalue2 = rptavgvalue2;
    }

    @Basic
    @Column(name = "RPTCREATEUSER")
    public String getRptcreateuser() {
        return rptcreateuser;
    }

    public void setRptcreateuser(String rptcreateuser) {
        this.rptcreateuser = rptcreateuser;
    }

    @Basic
    @Column(name = "RPTCREATETIME")
    public Time getRptcreatetime() {
        return rptcreatetime;
    }

    public void setRptcreatetime(Time rptcreatetime) {
        this.rptcreatetime = rptcreatetime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PimsSysReportItems that = (PimsSysReportItems) o;

        if (reportitemid != that.reportitemid) return false;
        if (rptname != null ? !rptname.equals(that.rptname) : that.rptname != null) return false;
        if (rptenglishname != null ? !rptenglishname.equals(that.rptenglishname) : that.rptenglishname != null)
            return false;
        if (rptelementid != null ? !rptelementid.equals(that.rptelementid) : that.rptelementid != null) return false;
        if (rptelementname != null ? !rptelementname.equals(that.rptelementname) : that.rptelementname != null)
            return false;
        if (rptitemsort != null ? !rptitemsort.equals(that.rptitemsort) : that.rptitemsort != null) return false;
        if (rptpinyincode != null ? !rptpinyincode.equals(that.rptpinyincode) : that.rptpinyincode != null)
            return false;
        if (rptfivestroke != null ? !rptfivestroke.equals(that.rptfivestroke) : that.rptfivestroke != null)
            return false;
        if (rptitemtype != null ? !rptitemtype.equals(that.rptitemtype) : that.rptitemtype != null) return false;
        if (rptuseflag != null ? !rptuseflag.equals(that.rptuseflag) : that.rptuseflag != null) return false;
        if (rptdefaultvalue != null ? !rptdefaultvalue.equals(that.rptdefaultvalue) : that.rptdefaultvalue != null)
            return false;
        if (rptrefvalue1 != null ? !rptrefvalue1.equals(that.rptrefvalue1) : that.rptrefvalue1 != null) return false;
        if (rptavgvalue1 != null ? !rptavgvalue1.equals(that.rptavgvalue1) : that.rptavgvalue1 != null) return false;
        if (rptrefvalue2 != null ? !rptrefvalue2.equals(that.rptrefvalue2) : that.rptrefvalue2 != null) return false;
        if (rptavgvalue2 != null ? !rptavgvalue2.equals(that.rptavgvalue2) : that.rptavgvalue2 != null) return false;
        if (rptcreateuser != null ? !rptcreateuser.equals(that.rptcreateuser) : that.rptcreateuser != null)
            return false;
        if (rptcreatetime != null ? !rptcreatetime.equals(that.rptcreatetime) : that.rptcreatetime != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (reportitemid ^ (reportitemid >>> 32));
        result = 31 * result + (rptname != null ? rptname.hashCode() : 0);
        result = 31 * result + (rptenglishname != null ? rptenglishname.hashCode() : 0);
        result = 31 * result + (rptelementid != null ? rptelementid.hashCode() : 0);
        result = 31 * result + (rptelementname != null ? rptelementname.hashCode() : 0);
        result = 31 * result + (rptitemsort != null ? rptitemsort.hashCode() : 0);
        result = 31 * result + (rptpinyincode != null ? rptpinyincode.hashCode() : 0);
        result = 31 * result + (rptfivestroke != null ? rptfivestroke.hashCode() : 0);
        result = 31 * result + (rptitemtype != null ? rptitemtype.hashCode() : 0);
        result = 31 * result + (rptuseflag != null ? rptuseflag.hashCode() : 0);
        result = 31 * result + (rptdefaultvalue != null ? rptdefaultvalue.hashCode() : 0);
        result = 31 * result + (rptrefvalue1 != null ? rptrefvalue1.hashCode() : 0);
        result = 31 * result + (rptavgvalue1 != null ? rptavgvalue1.hashCode() : 0);
        result = 31 * result + (rptrefvalue2 != null ? rptrefvalue2.hashCode() : 0);
        result = 31 * result + (rptavgvalue2 != null ? rptavgvalue2.hashCode() : 0);
        result = 31 * result + (rptcreateuser != null ? rptcreateuser.hashCode() : 0);
        result = 31 * result + (rptcreatetime != null ? rptcreatetime.hashCode() : 0);
        return result;
    }
}
