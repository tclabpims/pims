package com.pims.model;

import com.smart.model.BaseObject;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

/**
 * Created by king on 2016/9/28.
 */
@Entity
@Table(name = "PIMS_SYS_REPORT_FORMATE")
public class PimsSysReportFormate extends BaseObject {
    private long formateid;
    private long formpathologyid;
    private String formname;
    private String formsort;
    private Long formuseflag;
    private Long formisdefault;
    private String formweburl;
    private String formpicturenum;
    private String fomfirstv;
    private String fomsecondv;
    private String fomthirdv;
    private Long fomfirstn;
    private String formremark;
    private Long fomsecondn;
    private String formcreateuser;
    private Date formcreatetime;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PIMSSYSREPORTFORMATE")
    @SequenceGenerator(name = "SEQ_PIMSSYSREPORTFORMATE", sequenceName = "pimssysreportformate", allocationSize = 1)
    public long getFormateid() {
        return formateid;
    }

    public void setFormateid(long formateid) {
        this.formateid = formateid;
    }

    @Basic
    @Column(name = "FORMPATHOLOGYID")
    public long getFormpathologyid() {
        return formpathologyid;
    }

    public void setFormpathologyid(long formpathologyid) {
        this.formpathologyid = formpathologyid;
    }

    @Basic
    @Column(name = "FORMNAME")
    public String getFormname() {
        return formname;
    }

    public void setFormname(String formname) {
        this.formname = formname;
    }

    @Basic
    @Column(name = "FORMSORT")
    public String getFormsort() {
        return formsort;
    }

    public void setFormsort(String formsort) {
        this.formsort = formsort;
    }

    @Basic
    @Column(name = "FORMUSEFLAG")
    public Long getFormuseflag() {
        return formuseflag;
    }

    public void setFormuseflag(Long formuseflag) {
        this.formuseflag = formuseflag;
    }

    @Basic
    @Column(name = "FORMISDEFAULT")
    public Long getFormisdefault() {
        return formisdefault;
    }

    public void setFormisdefault(Long formisdefault) {
        this.formisdefault = formisdefault;
    }

    @Basic
    @Column(name = "FORMWEBURL")
    public String getFormweburl() {
        return formweburl;
    }

    public void setFormweburl(String formweburl) {
        this.formweburl = formweburl;
    }

    @Basic
    @Column(name = "FORMPICTURENUM")
    public String getFormpicturenum() {
        return formpicturenum;
    }

    public void setFormpicturenum(String formpicturenum) {
        this.formpicturenum = formpicturenum;
    }

    @Basic
    @Column(name = "FOMFIRSTV")
    public String getFomfirstv() {
        return fomfirstv;
    }

    public void setFomfirstv(String fomfirstv) {
        this.fomfirstv = fomfirstv;
    }

    @Basic
    @Column(name = "FOMSECONDV")
    public String getFomsecondv() {
        return fomsecondv;
    }

    public void setFomsecondv(String fomsecondv) {
        this.fomsecondv = fomsecondv;
    }

    @Basic
    @Column(name = "FOMTHIRDV")
    public String getFomthirdv() {
        return fomthirdv;
    }

    public void setFomthirdv(String fomthirdv) {
        this.fomthirdv = fomthirdv;
    }

    @Basic
    @Column(name = "FOMFIRSTN")
    public Long getFomfirstn() {
        return fomfirstn;
    }

    public void setFomfirstn(Long fomfirstn) {
        this.fomfirstn = fomfirstn;
    }

    @Basic
    @Column(name = "FORMREMARK")
    public String getFormremark() {
        return formremark;
    }

    public void setFormremark(String formremark) {
        this.formremark = formremark;
    }

    @Basic
    @Column(name = "FOMSECONDN")
    public Long getFomsecondn() {
        return fomsecondn;
    }

    public void setFomsecondn(Long fomsecondn) {
        this.fomsecondn = fomsecondn;
    }

    @Basic
    @Column(name = "FORMCREATEUSER")
    public String getFormcreateuser() {
        return formcreateuser;
    }

    public void setFormcreateuser(String formcreateuser) {
        this.formcreateuser = formcreateuser;
    }

    @Basic
    @Column(name = "FORMCREATETIME")
    public Date getFormcreatetime() {
        return formcreatetime;
    }

    public void setFormcreatetime(Date formcreatetime) {
        this.formcreatetime = formcreatetime;
    }

    /**
     * Returns a multi-line String with key=value pairs.
     *
     * @return a String representation of this class.
     */
    @Override
    public String toString() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PimsSysReportFormate that = (PimsSysReportFormate) o;

        if (formateid != that.formateid) return false;
        if (formpathologyid != that.formpathologyid) return false;
        if (formname != null ? !formname.equals(that.formname) : that.formname != null) return false;
        if (formsort != null ? !formsort.equals(that.formsort) : that.formsort != null) return false;
        if (formuseflag != null ? !formuseflag.equals(that.formuseflag) : that.formuseflag != null) return false;
        if (formisdefault != null ? !formisdefault.equals(that.formisdefault) : that.formisdefault != null)
            return false;
        if (formweburl != null ? !formweburl.equals(that.formweburl) : that.formweburl != null) return false;
        if (formpicturenum != null ? !formpicturenum.equals(that.formpicturenum) : that.formpicturenum != null)
            return false;
        if (fomfirstv != null ? !fomfirstv.equals(that.fomfirstv) : that.fomfirstv != null) return false;
        if (fomsecondv != null ? !fomsecondv.equals(that.fomsecondv) : that.fomsecondv != null) return false;
        if (fomthirdv != null ? !fomthirdv.equals(that.fomthirdv) : that.fomthirdv != null) return false;
        if (fomfirstn != null ? !fomfirstn.equals(that.fomfirstn) : that.fomfirstn != null) return false;
        if (formremark != null ? !formremark.equals(that.formremark) : that.formremark != null) return false;
        if (fomsecondn != null ? !fomsecondn.equals(that.fomsecondn) : that.fomsecondn != null) return false;
        if (formcreateuser != null ? !formcreateuser.equals(that.formcreateuser) : that.formcreateuser != null)
            return false;
        if (formcreatetime != null ? !formcreatetime.equals(that.formcreatetime) : that.formcreatetime != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (formateid ^ (formateid >>> 32));
        result = 31 * result + (int) (formpathologyid ^ (formpathologyid >>> 32));
        result = 31 * result + (formname != null ? formname.hashCode() : 0);
        result = 31 * result + (formsort != null ? formsort.hashCode() : 0);
        result = 31 * result + (formuseflag != null ? formuseflag.hashCode() : 0);
        result = 31 * result + (formisdefault != null ? formisdefault.hashCode() : 0);
        result = 31 * result + (formweburl != null ? formweburl.hashCode() : 0);
        result = 31 * result + (formpicturenum != null ? formpicturenum.hashCode() : 0);
        result = 31 * result + (fomfirstv != null ? fomfirstv.hashCode() : 0);
        result = 31 * result + (fomsecondv != null ? fomsecondv.hashCode() : 0);
        result = 31 * result + (fomthirdv != null ? fomthirdv.hashCode() : 0);
        result = 31 * result + (fomfirstn != null ? fomfirstn.hashCode() : 0);
        result = 31 * result + (formremark != null ? formremark.hashCode() : 0);
        result = 31 * result + (fomsecondn != null ? fomsecondn.hashCode() : 0);
        result = 31 * result + (formcreateuser != null ? formcreateuser.hashCode() : 0);
        result = 31 * result + (formcreatetime != null ? formcreatetime.hashCode() : 0);
        return result;
    }
}
