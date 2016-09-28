package com.pims.model;

import javax.persistence.*;
import java.sql.Time;

/**
 * Created by king on 2016/9/28.
 */
@Entity
@Table(name = "PIMS_SAMPLE_RESULT", schema = "KFTEST", catalog = "")
public class PimsSampleResult {
    private long resultid;
    private long ressampleid;
    private String rescustomercode;
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
    private Time resinputtime;
    private String resinputuser;
    private Time resmodifytime;
    private String resmodifyuser;
    private String resremark;
    private String resfirstv;
    private String ressecondv;
    private String resthirdv;
    private Long resfirstn;
    private Long ressecondn;
    private Time resfirstd;
    private Time ressecondd;
    private Time rescreatetime;
    private String rescreateuser;

    @Id
    @Column(name = "RESULTID")
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
    @Column(name = "RESCUSTOMERCODE")
    public String getRescustomercode() {
        return rescustomercode;
    }

    public void setRescustomercode(String rescustomercode) {
        this.rescustomercode = rescustomercode;
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
    public Time getResinputtime() {
        return resinputtime;
    }

    public void setResinputtime(Time resinputtime) {
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
    public Time getResmodifytime() {
        return resmodifytime;
    }

    public void setResmodifytime(Time resmodifytime) {
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
    public Time getResfirstd() {
        return resfirstd;
    }

    public void setResfirstd(Time resfirstd) {
        this.resfirstd = resfirstd;
    }

    @Basic
    @Column(name = "RESSECONDD")
    public Time getRessecondd() {
        return ressecondd;
    }

    public void setRessecondd(Time ressecondd) {
        this.ressecondd = ressecondd;
    }

    @Basic
    @Column(name = "RESCREATETIME")
    public Time getRescreatetime() {
        return rescreatetime;
    }

    public void setRescreatetime(Time rescreatetime) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PimsSampleResult that = (PimsSampleResult) o;

        if (resultid != that.resultid) return false;
        if (ressampleid != that.ressampleid) return false;
        if (restestitemid != that.restestitemid) return false;
        if (rescustomercode != null ? !rescustomercode.equals(that.rescustomercode) : that.rescustomercode != null)
            return false;
        if (resviewtitle != null ? !resviewtitle.equals(that.resviewtitle) : that.resviewtitle != null) return false;
        if (resviewsort != null ? !resviewsort.equals(that.resviewsort) : that.resviewsort != null) return false;
        if (resinputsort != null ? !resinputsort.equals(that.resinputsort) : that.resinputsort != null) return false;
        if (resviewtype != null ? !resviewtype.equals(that.resviewtype) : that.resviewtype != null) return false;
        if (restestresult != null ? !restestresult.equals(that.restestresult) : that.restestresult != null)
            return false;
        if (resresultp != null ? !resresultp.equals(that.resresultp) : that.resresultp != null) return false;
        if (resavgvaluep != null ? !resavgvaluep.equals(that.resavgvaluep) : that.resavgvaluep != null) return false;
        if (resstandardvaluep != null ? !resstandardvaluep.equals(that.resstandardvaluep) : that.resstandardvaluep != null)
            return false;
        if (resresultn != null ? !resresultn.equals(that.resresultn) : that.resresultn != null) return false;
        if (resavgvaluen != null ? !resavgvaluen.equals(that.resavgvaluen) : that.resavgvaluen != null) return false;
        if (resstandardvaluen != null ? !resstandardvaluen.equals(that.resstandardvaluen) : that.resstandardvaluen != null)
            return false;
        if (resinputtime != null ? !resinputtime.equals(that.resinputtime) : that.resinputtime != null) return false;
        if (resinputuser != null ? !resinputuser.equals(that.resinputuser) : that.resinputuser != null) return false;
        if (resmodifytime != null ? !resmodifytime.equals(that.resmodifytime) : that.resmodifytime != null)
            return false;
        if (resmodifyuser != null ? !resmodifyuser.equals(that.resmodifyuser) : that.resmodifyuser != null)
            return false;
        if (resremark != null ? !resremark.equals(that.resremark) : that.resremark != null) return false;
        if (resfirstv != null ? !resfirstv.equals(that.resfirstv) : that.resfirstv != null) return false;
        if (ressecondv != null ? !ressecondv.equals(that.ressecondv) : that.ressecondv != null) return false;
        if (resthirdv != null ? !resthirdv.equals(that.resthirdv) : that.resthirdv != null) return false;
        if (resfirstn != null ? !resfirstn.equals(that.resfirstn) : that.resfirstn != null) return false;
        if (ressecondn != null ? !ressecondn.equals(that.ressecondn) : that.ressecondn != null) return false;
        if (resfirstd != null ? !resfirstd.equals(that.resfirstd) : that.resfirstd != null) return false;
        if (ressecondd != null ? !ressecondd.equals(that.ressecondd) : that.ressecondd != null) return false;
        if (rescreatetime != null ? !rescreatetime.equals(that.rescreatetime) : that.rescreatetime != null)
            return false;
        if (rescreateuser != null ? !rescreateuser.equals(that.rescreateuser) : that.rescreateuser != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (resultid ^ (resultid >>> 32));
        result = 31 * result + (int) (ressampleid ^ (ressampleid >>> 32));
        result = 31 * result + (rescustomercode != null ? rescustomercode.hashCode() : 0);
        result = 31 * result + (int) (restestitemid ^ (restestitemid >>> 32));
        result = 31 * result + (resviewtitle != null ? resviewtitle.hashCode() : 0);
        result = 31 * result + (resviewsort != null ? resviewsort.hashCode() : 0);
        result = 31 * result + (resinputsort != null ? resinputsort.hashCode() : 0);
        result = 31 * result + (resviewtype != null ? resviewtype.hashCode() : 0);
        result = 31 * result + (restestresult != null ? restestresult.hashCode() : 0);
        result = 31 * result + (resresultp != null ? resresultp.hashCode() : 0);
        result = 31 * result + (resavgvaluep != null ? resavgvaluep.hashCode() : 0);
        result = 31 * result + (resstandardvaluep != null ? resstandardvaluep.hashCode() : 0);
        result = 31 * result + (resresultn != null ? resresultn.hashCode() : 0);
        result = 31 * result + (resavgvaluen != null ? resavgvaluen.hashCode() : 0);
        result = 31 * result + (resstandardvaluen != null ? resstandardvaluen.hashCode() : 0);
        result = 31 * result + (resinputtime != null ? resinputtime.hashCode() : 0);
        result = 31 * result + (resinputuser != null ? resinputuser.hashCode() : 0);
        result = 31 * result + (resmodifytime != null ? resmodifytime.hashCode() : 0);
        result = 31 * result + (resmodifyuser != null ? resmodifyuser.hashCode() : 0);
        result = 31 * result + (resremark != null ? resremark.hashCode() : 0);
        result = 31 * result + (resfirstv != null ? resfirstv.hashCode() : 0);
        result = 31 * result + (ressecondv != null ? ressecondv.hashCode() : 0);
        result = 31 * result + (resthirdv != null ? resthirdv.hashCode() : 0);
        result = 31 * result + (resfirstn != null ? resfirstn.hashCode() : 0);
        result = 31 * result + (ressecondn != null ? ressecondn.hashCode() : 0);
        result = 31 * result + (resfirstd != null ? resfirstd.hashCode() : 0);
        result = 31 * result + (ressecondd != null ? ressecondd.hashCode() : 0);
        result = 31 * result + (rescreatetime != null ? rescreatetime.hashCode() : 0);
        result = 31 * result + (rescreateuser != null ? rescreateuser.hashCode() : 0);
        return result;
    }
}
