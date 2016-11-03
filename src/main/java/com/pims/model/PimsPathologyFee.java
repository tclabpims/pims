package com.pims.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by king on 2016/11/2.
 */
@Entity
@Table(name = "PIMS_PATHOLOGY_FEE")
public class PimsPathologyFee {
    private long feeid;
    private long feecustomerid;
    private long feesampleid;
    private long feepathologyid;
    private String feepathologycode;
    private long feesource;
    private long feestate;
    private String feecategory;
    private long feeitemid;
    private String feenamech;
    private String feenameen;
    private long feeprince;
    private String feehisitemid;
    private String feehisname;
    private long feehisprice;
    private long feeamount;
    private Long feecost;
    private String feeuserid;
    private String feeusername;
    private Date feetime;
    private String feesenduserid;
    private String feesendusername;
    private Date feesendtime;
    private String feefailerror;
    private Date feeinputtime;
    private String feeinputuser;
    private String feefirstv;
    private String feesecondv;
    private String feethirdv;
    private Long feefirstn;
    private Date feefirstd;
    private String feeremark;

    @Id
    @Column(name = "FEEID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="SEQ_FEEID")
    @SequenceGenerator(name = "SEQ_FEEID", sequenceName = "SEQ_FEEID", allocationSize=1)
    public long getFeeid() {
        return feeid;
    }

    public void setFeeid(long feeid) {
        this.feeid = feeid;
    }

    @Basic
    @Column(name = "FEECUSTOMERID")
    public long getFeecustomerid() {
        return feecustomerid;
    }

    public void setFeecustomerid(long feecustomerid) {
        this.feecustomerid = feecustomerid;
    }

    @Basic
    @Column(name = "FEESAMPLEID")
    public long getFeesampleid() {
        return feesampleid;
    }

    public void setFeesampleid(long feesampleid) {
        this.feesampleid = feesampleid;
    }

    @Basic
    @Column(name = "FEEPATHOLOGYID")
    public long getFeepathologyid() {
        return feepathologyid;
    }

    public void setFeepathologyid(long feepathologyid) {
        this.feepathologyid = feepathologyid;
    }

    @Basic
    @Column(name = "FEEPATHOLOGYCODE")
    public String getFeepathologycode() {
        return feepathologycode;
    }

    public void setFeepathologycode(String feepathologycode) {
        this.feepathologycode = feepathologycode;
    }

    @Basic
    @Column(name = "FEESOURCE")
    public long getFeesource() {
        return feesource;
    }

    public void setFeesource(long feesource) {
        this.feesource = feesource;
    }

    @Basic
    @Column(name = "FEESTATE")
    public long getFeestate() {
        return feestate;
    }

    public void setFeestate(long feestate) {
        this.feestate = feestate;
    }

    @Basic
    @Column(name = "FEECATEGORY")
    public String getFeecategory() {
        return feecategory;
    }

    public void setFeecategory(String feecategory) {
        this.feecategory = feecategory;
    }

    @Basic
    @Column(name = "FEEITEMID")
    public long getFeeitemid() {
        return feeitemid;
    }

    public void setFeeitemid(long feeitemid) {
        this.feeitemid = feeitemid;
    }

    @Basic
    @Column(name = "FEENAMECH")
    public String getFeenamech() {
        return feenamech;
    }

    public void setFeenamech(String feenamech) {
        this.feenamech = feenamech;
    }

    @Basic
    @Column(name = "FEENAMEEN")
    public String getFeenameen() {
        return feenameen;
    }

    public void setFeenameen(String feenameen) {
        this.feenameen = feenameen;
    }

    @Basic
    @Column(name = "FEEPRINCE")
    public long getFeeprince() {
        return feeprince;
    }

    public void setFeeprince(long feeprince) {
        this.feeprince = feeprince;
    }

    @Basic
    @Column(name = "FEEHISITEMID")
    public String getFeehisitemid() {
        return feehisitemid;
    }

    public void setFeehisitemid(String feehisitemid) {
        this.feehisitemid = feehisitemid;
    }

    @Basic
    @Column(name = "FEEHISNAME")
    public String getFeehisname() {
        return feehisname;
    }

    public void setFeehisname(String feehisname) {
        this.feehisname = feehisname;
    }

    @Basic
    @Column(name = "FEEHISPRICE")
    public long getFeehisprice() {
        return feehisprice;
    }

    public void setFeehisprice(long feehisprice) {
        this.feehisprice = feehisprice;
    }

    @Basic
    @Column(name = "FEEAMOUNT")
    public long getFeeamount() {
        return feeamount;
    }

    public void setFeeamount(long feeamount) {
        this.feeamount = feeamount;
    }

    @Basic
    @Column(name = "FEECOST")
    public Long getFeecost() {
        return feecost;
    }

    public void setFeecost(Long feecost) {
        this.feecost = feecost;
    }

    @Basic
    @Column(name = "FEEUSERID")
    public String getFeeuserid() {
        return feeuserid;
    }

    public void setFeeuserid(String feeuserid) {
        this.feeuserid = feeuserid;
    }

    @Basic
    @Column(name = "FEEUSERNAME")
    public String getFeeusername() {
        return feeusername;
    }

    public void setFeeusername(String feeusername) {
        this.feeusername = feeusername;
    }

    @Basic
    @Column(name = "FEETIME")
    public Date getFeetime() {
        return feetime;
    }

    public void setFeetime(Date feetime) {
        this.feetime = feetime;
    }

    @Basic
    @Column(name = "FEESENDUSERID")
    public String getFeesenduserid() {
        return feesenduserid;
    }

    public void setFeesenduserid(String feesenduserid) {
        this.feesenduserid = feesenduserid;
    }

    @Basic
    @Column(name = "FEESENDUSERNAME")
    public String getFeesendusername() {
        return feesendusername;
    }

    public void setFeesendusername(String feesendusername) {
        this.feesendusername = feesendusername;
    }

    @Basic
    @Column(name = "FEESENDTIME")
    public Date getFeesendtime() {
        return feesendtime;
    }

    public void setFeesendtime(Date feesendtime) {
        this.feesendtime = feesendtime;
    }

    @Basic
    @Column(name = "FEEFAILERROR")
    public String getFeefailerror() {
        return feefailerror;
    }

    public void setFeefailerror(String feefailerror) {
        this.feefailerror = feefailerror;
    }

    @Basic
    @Column(name = "FEEINPUTTIME")
    public Date getFeeinputtime() {
        return feeinputtime;
    }

    public void setFeeinputtime(Date feeinputtime) {
        this.feeinputtime = feeinputtime;
    }

    @Basic
    @Column(name = "FEEINPUTUSER")
    public String getFeeinputuser() {
        return feeinputuser;
    }

    public void setFeeinputuser(String feeinputuser) {
        this.feeinputuser = feeinputuser;
    }

    @Basic
    @Column(name = "FEEFIRSTV")
    public String getFeefirstv() {
        return feefirstv;
    }

    public void setFeefirstv(String feefirstv) {
        this.feefirstv = feefirstv;
    }

    @Basic
    @Column(name = "FEESECONDV")
    public String getFeesecondv() {
        return feesecondv;
    }

    public void setFeesecondv(String feesecondv) {
        this.feesecondv = feesecondv;
    }

    @Basic
    @Column(name = "FEETHIRDV")
    public String getFeethirdv() {
        return feethirdv;
    }

    public void setFeethirdv(String feethirdv) {
        this.feethirdv = feethirdv;
    }

    @Basic
    @Column(name = "FEEFIRSTN")
    public Long getFeefirstn() {
        return feefirstn;
    }

    public void setFeefirstn(Long feefirstn) {
        this.feefirstn = feefirstn;
    }

    @Basic
    @Column(name = "FEEFIRSTD")
    public Date getFeefirstd() {
        return feefirstd;
    }

    public void setFeefirstd(Date feefirstd) {
        this.feefirstd = feefirstd;
    }

    @Basic
    @Column(name = "FEEREMARK")
    public String getFeeremark() {
        return feeremark;
    }

    public void setFeeremark(String feeremark) {
        this.feeremark = feeremark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PimsPathologyFee that = (PimsPathologyFee) o;

        if (feeid != that.feeid) return false;
        if (feecustomerid != that.feecustomerid) return false;
        if (feesampleid != that.feesampleid) return false;
        if (feepathologyid != that.feepathologyid) return false;
        if (feesource != that.feesource) return false;
        if (feestate != that.feestate) return false;
        if (feeitemid != that.feeitemid) return false;
        if (feeprince != that.feeprince) return false;
        if (feehisprice != that.feehisprice) return false;
        if (feeamount != that.feeamount) return false;
        if (feepathologycode != null ? !feepathologycode.equals(that.feepathologycode) : that.feepathologycode != null)
            return false;
        if (feecategory != null ? !feecategory.equals(that.feecategory) : that.feecategory != null) return false;
        if (feenamech != null ? !feenamech.equals(that.feenamech) : that.feenamech != null) return false;
        if (feenameen != null ? !feenameen.equals(that.feenameen) : that.feenameen != null) return false;
        if (feehisitemid != null ? !feehisitemid.equals(that.feehisitemid) : that.feehisitemid != null) return false;
        if (feehisname != null ? !feehisname.equals(that.feehisname) : that.feehisname != null) return false;
        if (feecost != null ? !feecost.equals(that.feecost) : that.feecost != null) return false;
        if (feeuserid != null ? !feeuserid.equals(that.feeuserid) : that.feeuserid != null) return false;
        if (feeusername != null ? !feeusername.equals(that.feeusername) : that.feeusername != null) return false;
        if (feetime != null ? !feetime.equals(that.feetime) : that.feetime != null) return false;
        if (feesenduserid != null ? !feesenduserid.equals(that.feesenduserid) : that.feesenduserid != null)
            return false;
        if (feesendusername != null ? !feesendusername.equals(that.feesendusername) : that.feesendusername != null)
            return false;
        if (feesendtime != null ? !feesendtime.equals(that.feesendtime) : that.feesendtime != null) return false;
        if (feefailerror != null ? !feefailerror.equals(that.feefailerror) : that.feefailerror != null) return false;
        if (feeinputtime != null ? !feeinputtime.equals(that.feeinputtime) : that.feeinputtime != null) return false;
        if (feeinputuser != null ? !feeinputuser.equals(that.feeinputuser) : that.feeinputuser != null) return false;
        if (feefirstv != null ? !feefirstv.equals(that.feefirstv) : that.feefirstv != null) return false;
        if (feesecondv != null ? !feesecondv.equals(that.feesecondv) : that.feesecondv != null) return false;
        if (feethirdv != null ? !feethirdv.equals(that.feethirdv) : that.feethirdv != null) return false;
        if (feefirstn != null ? !feefirstn.equals(that.feefirstn) : that.feefirstn != null) return false;
        if (feefirstd != null ? !feefirstd.equals(that.feefirstd) : that.feefirstd != null) return false;
        if (feeremark != null ? !feeremark.equals(that.feeremark) : that.feeremark != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (feeid ^ (feeid >>> 32));
        result = 31 * result + (int) (feecustomerid ^ (feecustomerid >>> 32));
        result = 31 * result + (int) (feesampleid ^ (feesampleid >>> 32));
        result = 31 * result + (int) (feepathologyid ^ (feepathologyid >>> 32));
        result = 31 * result + (feepathologycode != null ? feepathologycode.hashCode() : 0);
        result = 31 * result + (int) (feesource ^ (feesource >>> 32));
        result = 31 * result + (int) (feestate ^ (feestate >>> 32));
        result = 31 * result + (feecategory != null ? feecategory.hashCode() : 0);
        result = 31 * result + (int) (feeitemid ^ (feeitemid >>> 32));
        result = 31 * result + (feenamech != null ? feenamech.hashCode() : 0);
        result = 31 * result + (feenameen != null ? feenameen.hashCode() : 0);
        result = 31 * result + (int) (feeprince ^ (feeprince >>> 32));
        result = 31 * result + (feehisitemid != null ? feehisitemid.hashCode() : 0);
        result = 31 * result + (feehisname != null ? feehisname.hashCode() : 0);
        result = 31 * result + (int) (feehisprice ^ (feehisprice >>> 32));
        result = 31 * result + (int) (feeamount ^ (feeamount >>> 32));
        result = 31 * result + (feecost != null ? feecost.hashCode() : 0);
        result = 31 * result + (feeuserid != null ? feeuserid.hashCode() : 0);
        result = 31 * result + (feeusername != null ? feeusername.hashCode() : 0);
        result = 31 * result + (feetime != null ? feetime.hashCode() : 0);
        result = 31 * result + (feesenduserid != null ? feesenduserid.hashCode() : 0);
        result = 31 * result + (feesendusername != null ? feesendusername.hashCode() : 0);
        result = 31 * result + (feesendtime != null ? feesendtime.hashCode() : 0);
        result = 31 * result + (feefailerror != null ? feefailerror.hashCode() : 0);
        result = 31 * result + (feeinputtime != null ? feeinputtime.hashCode() : 0);
        result = 31 * result + (feeinputuser != null ? feeinputuser.hashCode() : 0);
        result = 31 * result + (feefirstv != null ? feefirstv.hashCode() : 0);
        result = 31 * result + (feesecondv != null ? feesecondv.hashCode() : 0);
        result = 31 * result + (feethirdv != null ? feethirdv.hashCode() : 0);
        result = 31 * result + (feefirstn != null ? feefirstn.hashCode() : 0);
        result = 31 * result + (feefirstd != null ? feefirstd.hashCode() : 0);
        result = 31 * result + (feeremark != null ? feeremark.hashCode() : 0);
        return result;
    }
}
