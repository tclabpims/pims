package com.pims.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by king on 2016/10/25.
 */
@Entity
@Table(name = "PIMS_CONSULTATION_DETAIL")
public class PimsConsultationDetail {
    private long detailid;
    private long detconsultationid;
    private long detsampleid;
    private String detpathologycode;
    private long detcustomerid;
    private String detdoctorid;
    private String detdoctorname;
    private long detstate;
    private Date detconsultationtime;
    private String detadvice;
    private String detremark;
    private String detfirstv;
    private String detsecondv;
    private String detthirdv;
    private Long detfirstn;
    private Long detsecondn;
    private Date detfirstd;
    private Date detsecondd;
    private String detcreateuserid;
    private Date detcreatetime;

    @Id
    @Column(name = "DETAILID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="SEQ_DETAILID")
    @SequenceGenerator(name = "SEQ_DETAILID", sequenceName = "SEQ_DETAILID", allocationSize=1)
    public long getDetailid() {
        return detailid;
    }

    public void setDetailid(long detailid) {
        this.detailid = detailid;
    }

    @Basic
    @Column(name = "DETCONSULTATIONID")
    public long getDetconsultationid() {
        return detconsultationid;
    }

    public void setDetconsultationid(long detconsultationid) {
        this.detconsultationid = detconsultationid;
    }

    @Basic
    @Column(name = "DETSAMPLEID")
    public long getDetsampleid() {
        return detsampleid;
    }

    public void setDetsampleid(long detsampleid) {
        this.detsampleid = detsampleid;
    }

    @Basic
    @Column(name = "DETPATHOLOGYCODE")
    public String getDetpathologycode() {
        return detpathologycode;
    }

    public void setDetpathologycode(String detpathologycode) {
        this.detpathologycode = detpathologycode;
    }

    @Basic
    @Column(name = "DETCUSTOMERID")
    public long getDetcustomerid() {
        return detcustomerid;
    }

    public void setDetcustomerid(long detcustomerid) {
        this.detcustomerid = detcustomerid;
    }

    @Basic
    @Column(name = "DETDOCTORID")
    public String getDetdoctorid() {
        return detdoctorid;
    }

    public void setDetdoctorid(String detdoctorid) {
        this.detdoctorid = detdoctorid;
    }

    @Basic
    @Column(name = "DETDOCTORNAME")
    public String getDetdoctorname() {
        return detdoctorname;
    }

    public void setDetdoctorname(String detdoctorname) {
        this.detdoctorname = detdoctorname;
    }

    @Basic
    @Column(name = "DETSTATE")
    public long getDetstate() {
        return detstate;
    }

    public void setDetstate(long detstate) {
        this.detstate = detstate;
    }

    @Basic
    @Column(name = "DETCONSULTATIONTIME")
    public Date getDetconsultationtime() {
        return detconsultationtime;
    }

    public void setDetconsultationtime(Date detconsultationtime) {
        this.detconsultationtime = detconsultationtime;
    }

    @Basic
    @Column(name = "DETADVICE")
    public String getDetadvice() {
        return detadvice;
    }

    public void setDetadvice(String detadvice) {
        this.detadvice = detadvice;
    }

    @Basic
    @Column(name = "DETREMARK")
    public String getDetremark() {
        return detremark;
    }

    public void setDetremark(String detremark) {
        this.detremark = detremark;
    }

    @Basic
    @Column(name = "DETFIRSTV")
    public String getDetfirstv() {
        return detfirstv;
    }

    public void setDetfirstv(String detfirstv) {
        this.detfirstv = detfirstv;
    }

    @Basic
    @Column(name = "DETSECONDV")
    public String getDetsecondv() {
        return detsecondv;
    }

    public void setDetsecondv(String detsecondv) {
        this.detsecondv = detsecondv;
    }

    @Basic
    @Column(name = "DETTHIRDV")
    public String getDetthirdv() {
        return detthirdv;
    }

    public void setDetthirdv(String detthirdv) {
        this.detthirdv = detthirdv;
    }

    @Basic
    @Column(name = "DETFIRSTN")
    public Long getDetfirstn() {
        return detfirstn;
    }

    public void setDetfirstn(Long detfirstn) {
        this.detfirstn = detfirstn;
    }

    @Basic
    @Column(name = "DETSECONDN")
    public Long getDetsecondn() {
        return detsecondn;
    }

    public void setDetsecondn(Long detsecondn) {
        this.detsecondn = detsecondn;
    }

    @Basic
    @Column(name = "DETFIRSTD")
    public Date getDetfirstd() {
        return detfirstd;
    }

    public void setDetfirstd(Date detfirstd) {
        this.detfirstd = detfirstd;
    }

    @Basic
    @Column(name = "DETSECONDD")
    public Date getDetsecondd() {
        return detsecondd;
    }

    public void setDetsecondd(Date detsecondd) {
        this.detsecondd = detsecondd;
    }

    @Basic
    @Column(name = "DETCREATEUSERID")
    public String getDetcreateuserid() {
        return detcreateuserid;
    }

    public void setDetcreateuserid(String detcreateuserid) {
        this.detcreateuserid = detcreateuserid;
    }

    @Basic
    @Column(name = "DETCREATETIME")
    public Date getDetcreatetime() {
        return detcreatetime;
    }

    public void setDetcreatetime(Date detcreatetime) {
        this.detcreatetime = detcreatetime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PimsConsultationDetail that = (PimsConsultationDetail) o;

        if (detailid != that.detailid) return false;
        if (detconsultationid != that.detconsultationid) return false;
        if (detsampleid != that.detsampleid) return false;
        if (detcustomerid != that.detcustomerid) return false;
        if (detstate != that.detstate) return false;
        if (detpathologycode != null ? !detpathologycode.equals(that.detpathologycode) : that.detpathologycode != null)
            return false;
        if (detdoctorid != null ? !detdoctorid.equals(that.detdoctorid) : that.detdoctorid != null) return false;
        if (detdoctorname != null ? !detdoctorname.equals(that.detdoctorname) : that.detdoctorname != null)
            return false;
        if (detconsultationtime != null ? !detconsultationtime.equals(that.detconsultationtime) : that.detconsultationtime != null)
            return false;
        if (detadvice != null ? !detadvice.equals(that.detadvice) : that.detadvice != null) return false;
        if (detremark != null ? !detremark.equals(that.detremark) : that.detremark != null) return false;
        if (detfirstv != null ? !detfirstv.equals(that.detfirstv) : that.detfirstv != null) return false;
        if (detsecondv != null ? !detsecondv.equals(that.detsecondv) : that.detsecondv != null) return false;
        if (detthirdv != null ? !detthirdv.equals(that.detthirdv) : that.detthirdv != null) return false;
        if (detfirstn != null ? !detfirstn.equals(that.detfirstn) : that.detfirstn != null) return false;
        if (detsecondn != null ? !detsecondn.equals(that.detsecondn) : that.detsecondn != null) return false;
        if (detfirstd != null ? !detfirstd.equals(that.detfirstd) : that.detfirstd != null) return false;
        if (detsecondd != null ? !detsecondd.equals(that.detsecondd) : that.detsecondd != null) return false;
        if (detcreateuserid != null ? !detcreateuserid.equals(that.detcreateuserid) : that.detcreateuserid != null)
            return false;
        if (detcreatetime != null ? !detcreatetime.equals(that.detcreatetime) : that.detcreatetime != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (detailid ^ (detailid >>> 32));
        result = 31 * result + (int) (detconsultationid ^ (detconsultationid >>> 32));
        result = 31 * result + (int) (detsampleid ^ (detsampleid >>> 32));
        result = 31 * result + (detpathologycode != null ? detpathologycode.hashCode() : 0);
        result = 31 * result + (int) (detcustomerid ^ (detcustomerid >>> 32));
        result = 31 * result + (detdoctorid != null ? detdoctorid.hashCode() : 0);
        result = 31 * result + (detdoctorname != null ? detdoctorname.hashCode() : 0);
        result = 31 * result + (int) (detstate ^ (detstate >>> 32));
        result = 31 * result + (detconsultationtime != null ? detconsultationtime.hashCode() : 0);
        result = 31 * result + (detadvice != null ? detadvice.hashCode() : 0);
        result = 31 * result + (detremark != null ? detremark.hashCode() : 0);
        result = 31 * result + (detfirstv != null ? detfirstv.hashCode() : 0);
        result = 31 * result + (detsecondv != null ? detsecondv.hashCode() : 0);
        result = 31 * result + (detthirdv != null ? detthirdv.hashCode() : 0);
        result = 31 * result + (detfirstn != null ? detfirstn.hashCode() : 0);
        result = 31 * result + (detsecondn != null ? detsecondn.hashCode() : 0);
        result = 31 * result + (detfirstd != null ? detfirstd.hashCode() : 0);
        result = 31 * result + (detsecondd != null ? detsecondd.hashCode() : 0);
        result = 31 * result + (detcreateuserid != null ? detcreateuserid.hashCode() : 0);
        result = 31 * result + (detcreatetime != null ? detcreatetime.hashCode() : 0);
        return result;
    }
}
