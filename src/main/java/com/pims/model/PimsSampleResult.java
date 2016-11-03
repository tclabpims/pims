package com.pims.model;

import com.smart.model.BaseObject;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

/**
 * Created by king on 2016/9/28.
 */
@Entity
@Table(name = "PIMS_SAMPLE_RESULT")
public class PimsSampleResult extends BaseObject {
    private long resultid;
    private long ressampleid;
    private long rescustomerid;
    private long restestitemid;
    private String resviewtitle;
    private String resviewsort;
    private String resinputsort;
    private String resviewtype;
    private String restestresult;
    private String resresultp;
    private String resavgvaluep;
    private String resstandardvaluep;
    private String resresultn;
    private String resavgvaluen;
    private String resstandardvaluen;
    private Date resinputtime;
    private String resinputuser;
    private Date resmodifytime;
    private String resmodifyuser;
    private String resremark;
    private String resfirstv;
    private String ressecondv;
    private String resthirdv;
    private Long resfirstn;
    private Long ressecondn;
    private Date resfirstd;
    private Date ressecondd;
    private Date rescreatetime;
    private String rescreateuser;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_RESULTID")
    @SequenceGenerator(name = "SEQ_RESULTID", sequenceName = "SEQ_RESULTID", allocationSize = 1)
    public long getResultid() {
        return resultid;
    }

    public void setResultid(long resultid) {
        this.resultid = resultid;
    }

    @Basic
    @Column(name = "RESSAMPLEID")
    public long getRessampleid() {
        return ressampleid;
    }

    public void setRessampleid(long ressampleid) {
        this.ressampleid = ressampleid;
    }

    @Basic
    @Column(name = "RESCUSTOMERID")
    public long getRescustomerid() {
        return rescustomerid;
    }

    public void setRescustomerid(long rescustomerid) {
        this.rescustomerid = rescustomerid;
    }

    @Basic
    @Column(name = "RESTESTITEMID")
    public long getRestestitemid() {
        return restestitemid;
    }

    public void setRestestitemid(long restestitemid) {
        this.restestitemid = restestitemid;
    }

    @Basic
    @Column(name = "RESVIEWTITLE")
    public String getResviewtitle() {
        return resviewtitle;
    }

    public void setResviewtitle(String resviewtitle) {
        this.resviewtitle = resviewtitle;
    }

    @Basic
    @Column(name = "RESVIEWSORT")
    public String getResviewsort() {
        return resviewsort;
    }

    public void setResviewsort(String resviewsort) {
        this.resviewsort = resviewsort;
    }

    @Basic
    @Column(name = "RESINPUTSORT")
    public String getResinputsort() {
        return resinputsort;
    }

    public void setResinputsort(String resinputsort) {
        this.resinputsort = resinputsort;
    }

    @Basic
    @Column(name = "RESVIEWTYPE")
    public String getResviewtype() {
        return resviewtype;
    }

    public void setResviewtype(String resviewtype) {
        this.resviewtype = resviewtype;
    }

    @Basic
    @Column(name = "RESTESTRESULT")
    public String getRestestresult() {
        return restestresult;
    }

    public void setRestestresult(String restestresult) {
        this.restestresult = restestresult;
    }

    @Basic
    @Column(name = "RESRESULTP")
    public String getResresultp() {
        return resresultp;
    }

    public void setResresultp(String resresultp) {
        this.resresultp = resresultp;
    }

    @Basic
    @Column(name = "RESAVGVALUEP")
    public String getResavgvaluep() {
        return resavgvaluep;
    }

    public void setResavgvaluep(String resavgvaluep) {
        this.resavgvaluep = resavgvaluep;
    }

    @Basic
    @Column(name = "RESSTANDARDVALUEP")
    public String getResstandardvaluep() {
        return resstandardvaluep;
    }

    public void setResstandardvaluep(String resstandardvaluep) {
        this.resstandardvaluep = resstandardvaluep;
    }

    @Basic
    @Column(name = "RESRESULTN")
    public String getResresultn() {
        return resresultn;
    }

    public void setResresultn(String resresultn) {
        this.resresultn = resresultn;
    }

    @Basic
    @Column(name = "RESAVGVALUEN")
    public String getResavgvaluen() {
        return resavgvaluen;
    }

    public void setResavgvaluen(String resavgvaluen) {
        this.resavgvaluen = resavgvaluen;
    }

    @Basic
    @Column(name = "RESSTANDARDVALUEN")
    public String getResstandardvaluen() {
        return resstandardvaluen;
    }

    public void setResstandardvaluen(String resstandardvaluen) {
        this.resstandardvaluen = resstandardvaluen;
    }

    @Basic
    @Column(name = "RESINPUTTIME")
    public Date getResinputtime() {
        return resinputtime;
    }

    public void setResinputtime(Date resinputtime) {
        this.resinputtime = resinputtime;
    }

    @Basic
    @Column(name = "RESINPUTUSER")
    public String getResinputuser() {
        return resinputuser;
    }

    public void setResinputuser(String resinputuser) {
        this.resinputuser = resinputuser;
    }

    @Basic
    @Column(name = "RESMODIFYTIME")
    public Date getResmodifytime() {
        return resmodifytime;
    }

    public void setResmodifytime(Date resmodifytime) {
        this.resmodifytime = resmodifytime;
    }

    @Basic
    @Column(name = "RESMODIFYUSER")
    public String getResmodifyuser() {
        return resmodifyuser;
    }

    public void setResmodifyuser(String resmodifyuser) {
        this.resmodifyuser = resmodifyuser;
    }

    @Basic
    @Column(name = "RESREMARK")
    public String getResremark() {
        return resremark;
    }

    public void setResremark(String resremark) {
        this.resremark = resremark;
    }

    @Basic
    @Column(name = "RESFIRSTV")
    public String getResfirstv() {
        return resfirstv;
    }

    public void setResfirstv(String resfirstv) {
        this.resfirstv = resfirstv;
    }

    @Basic
    @Column(name = "RESSECONDV")
    public String getRessecondv() {
        return ressecondv;
    }

    public void setRessecondv(String ressecondv) {
        this.ressecondv = ressecondv;
    }

    @Basic
    @Column(name = "RESTHIRDV")
    public String getResthirdv() {
        return resthirdv;
    }

    public void setResthirdv(String resthirdv) {
        this.resthirdv = resthirdv;
    }

    @Basic
    @Column(name = "RESFIRSTN")
    public Long getResfirstn() {
        return resfirstn;
    }

    public void setResfirstn(Long resfirstn) {
        this.resfirstn = resfirstn;
    }

    @Basic
    @Column(name = "RESSECONDN")
    public Long getRessecondn() {
        return ressecondn;
    }

    public void setRessecondn(Long ressecondn) {
        this.ressecondn = ressecondn;
    }

    @Basic
    @Column(name = "RESFIRSTD")
    public Date getResfirstd() {
        return resfirstd;
    }

    public void setResfirstd(Date resfirstd) {
        this.resfirstd = resfirstd;
    }

    @Basic
    @Column(name = "RESSECONDD")
    public Date getRessecondd() {
        return ressecondd;
    }

    public void setRessecondd(Date ressecondd) {
        this.ressecondd = ressecondd;
    }

    @Basic
    @Column(name = "RESCREATETIME")
    public Date getRescreatetime() {
        return rescreatetime;
    }

    public void setRescreatetime(Date rescreatetime) {
        this.rescreatetime = rescreatetime;
    }

    @Basic
    @Column(name = "RESCREATEUSER")
    public String getRescreateuser() {
        return rescreateuser;
    }

    public void setRescreateuser(String rescreateuser) {
        this.rescreateuser = rescreateuser;
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

        PimsSampleResult that = (PimsSampleResult) o;

        if (resultid != that.resultid) return false;
        if (ressampleid != that.ressampleid) return false;
        if (rescustomerid != that.rescustomerid) return false;
        if (restestitemid != that.restestitemid) return false;
        if (!resviewtitle.equals(that.resviewtitle)) return false;
        if (!resviewsort.equals(that.resviewsort)) return false;
        if (!resinputsort.equals(that.resinputsort)) return false;
        if (!resviewtype.equals(that.resviewtype)) return false;
        if (!restestresult.equals(that.restestresult)) return false;
        if (!resresultp.equals(that.resresultp)) return false;
        if (!resavgvaluep.equals(that.resavgvaluep)) return false;
        if (!resstandardvaluep.equals(that.resstandardvaluep)) return false;
        if (!resresultn.equals(that.resresultn)) return false;
        if (!resavgvaluen.equals(that.resavgvaluen)) return false;
        if (!resstandardvaluen.equals(that.resstandardvaluen)) return false;
        if (!resinputtime.equals(that.resinputtime)) return false;
        if (!resinputuser.equals(that.resinputuser)) return false;
        if (!resmodifytime.equals(that.resmodifytime)) return false;
        if (!resmodifyuser.equals(that.resmodifyuser)) return false;
        if (!resremark.equals(that.resremark)) return false;
        if (!resfirstv.equals(that.resfirstv)) return false;
        if (!ressecondv.equals(that.ressecondv)) return false;
        if (!resthirdv.equals(that.resthirdv)) return false;
        if (!resfirstn.equals(that.resfirstn)) return false;
        if (!ressecondn.equals(that.ressecondn)) return false;
        if (!resfirstd.equals(that.resfirstd)) return false;
        if (!ressecondd.equals(that.ressecondd)) return false;
        if (!rescreatetime.equals(that.rescreatetime)) return false;
        return rescreateuser.equals(that.rescreateuser);

    }

    @Override
    public int hashCode() {
        int result = (int) (resultid ^ (resultid >>> 32));
        result = 31 * result + (int) (ressampleid ^ (ressampleid >>> 32));
        result = 31 * result + (int) (rescustomerid ^ (rescustomerid >>> 32));
        result = 31 * result + (int) (restestitemid ^ (restestitemid >>> 32));
        result = 31 * result + resviewtitle.hashCode();
        result = 31 * result + resviewsort.hashCode();
        result = 31 * result + resinputsort.hashCode();
        result = 31 * result + resviewtype.hashCode();
        result = 31 * result + restestresult.hashCode();
        result = 31 * result + resresultp.hashCode();
        result = 31 * result + resavgvaluep.hashCode();
        result = 31 * result + resstandardvaluep.hashCode();
        result = 31 * result + resresultn.hashCode();
        result = 31 * result + resavgvaluen.hashCode();
        result = 31 * result + resstandardvaluen.hashCode();
        result = 31 * result + resinputtime.hashCode();
        result = 31 * result + resinputuser.hashCode();
        result = 31 * result + resmodifytime.hashCode();
        result = 31 * result + resmodifyuser.hashCode();
        result = 31 * result + resremark.hashCode();
        result = 31 * result + resfirstv.hashCode();
        result = 31 * result + ressecondv.hashCode();
        result = 31 * result + resthirdv.hashCode();
        result = 31 * result + resfirstn.hashCode();
        result = 31 * result + ressecondn.hashCode();
        result = 31 * result + resfirstd.hashCode();
        result = 31 * result + ressecondd.hashCode();
        result = 31 * result + rescreatetime.hashCode();
        result = 31 * result + rescreateuser.hashCode();
        return result;
    }
}
