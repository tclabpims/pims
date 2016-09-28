package com.pims.model;

import javax.persistence.*;
import java.sql.Time;

/**
 * Created by king on 2016/9/28.
 */
@Entity
@Table(name = "PIMS_SYS_REQ_TESTITEM", schema = "KFTEST", catalog = "")
public class PimsSysReqTestitem {
    private long testitemid;
    private String teschinesename;
    private String tesenglishname;
    private String tesitemsort;
    private String tespinyincode;
    private String tesfivestroke;
    private long tesitemtype;
    private Long tespathologyid;
    private Long tesitemhandle;
    private long tesischarge;
    private long tesuseflag;
    private String tesfirstv;
    private String tessecondv;
    private Long tesfirstn;
    private String tesreamrk;
    private String tescreateuser;
    private Time tescreatetime;

    @Id
    @Column(name = "TESTITEMID")
    public long getTestitemid() {
        return testitemid;
    }

    public void setTestitemid(long testitemid) {
        this.testitemid = testitemid;
    }

    @Basic
    @Column(name = "TESCHINESENAME")
    public String getTeschinesename() {
        return teschinesename;
    }

    public void setTeschinesename(String teschinesename) {
        this.teschinesename = teschinesename;
    }

    @Basic
    @Column(name = "TESENGLISHNAME")
    public String getTesenglishname() {
        return tesenglishname;
    }

    public void setTesenglishname(String tesenglishname) {
        this.tesenglishname = tesenglishname;
    }

    @Basic
    @Column(name = "TESITEMSORT")
    public String getTesitemsort() {
        return tesitemsort;
    }

    public void setTesitemsort(String tesitemsort) {
        this.tesitemsort = tesitemsort;
    }

    @Basic
    @Column(name = "TESPINYINCODE")
    public String getTespinyincode() {
        return tespinyincode;
    }

    public void setTespinyincode(String tespinyincode) {
        this.tespinyincode = tespinyincode;
    }

    @Basic
    @Column(name = "TESFIVESTROKE")
    public String getTesfivestroke() {
        return tesfivestroke;
    }

    public void setTesfivestroke(String tesfivestroke) {
        this.tesfivestroke = tesfivestroke;
    }

    @Basic
    @Column(name = "TESITEMTYPE")
    public long getTesitemtype() {
        return tesitemtype;
    }

    public void setTesitemtype(long tesitemtype) {
        this.tesitemtype = tesitemtype;
    }

    @Basic
    @Column(name = "TESPATHOLOGYID")
    public Long getTespathologyid() {
        return tespathologyid;
    }

    public void setTespathologyid(Long tespathologyid) {
        this.tespathologyid = tespathologyid;
    }

    @Basic
    @Column(name = "TESITEMHANDLE")
    public Long getTesitemhandle() {
        return tesitemhandle;
    }

    public void setTesitemhandle(Long tesitemhandle) {
        this.tesitemhandle = tesitemhandle;
    }

    @Basic
    @Column(name = "TESISCHARGE")
    public long getTesischarge() {
        return tesischarge;
    }

    public void setTesischarge(long tesischarge) {
        this.tesischarge = tesischarge;
    }

    @Basic
    @Column(name = "TESUSEFLAG")
    public long getTesuseflag() {
        return tesuseflag;
    }

    public void setTesuseflag(long tesuseflag) {
        this.tesuseflag = tesuseflag;
    }

    @Basic
    @Column(name = "TESFIRSTV")
    public String getTesfirstv() {
        return tesfirstv;
    }

    public void setTesfirstv(String tesfirstv) {
        this.tesfirstv = tesfirstv;
    }

    @Basic
    @Column(name = "TESSECONDV")
    public String getTessecondv() {
        return tessecondv;
    }

    public void setTessecondv(String tessecondv) {
        this.tessecondv = tessecondv;
    }

    @Basic
    @Column(name = "TESFIRSTN")
    public Long getTesfirstn() {
        return tesfirstn;
    }

    public void setTesfirstn(Long tesfirstn) {
        this.tesfirstn = tesfirstn;
    }

    @Basic
    @Column(name = "TESREAMRK")
    public String getTesreamrk() {
        return tesreamrk;
    }

    public void setTesreamrk(String tesreamrk) {
        this.tesreamrk = tesreamrk;
    }

    @Basic
    @Column(name = "TESCREATEUSER")
    public String getTescreateuser() {
        return tescreateuser;
    }

    public void setTescreateuser(String tescreateuser) {
        this.tescreateuser = tescreateuser;
    }

    @Basic
    @Column(name = "TESCREATETIME")
    public Time getTescreatetime() {
        return tescreatetime;
    }

    public void setTescreatetime(Time tescreatetime) {
        this.tescreatetime = tescreatetime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PimsSysReqTestitem that = (PimsSysReqTestitem) o;

        if (testitemid != that.testitemid) return false;
        if (tesitemtype != that.tesitemtype) return false;
        if (tesischarge != that.tesischarge) return false;
        if (tesuseflag != that.tesuseflag) return false;
        if (teschinesename != null ? !teschinesename.equals(that.teschinesename) : that.teschinesename != null)
            return false;
        if (tesenglishname != null ? !tesenglishname.equals(that.tesenglishname) : that.tesenglishname != null)
            return false;
        if (tesitemsort != null ? !tesitemsort.equals(that.tesitemsort) : that.tesitemsort != null) return false;
        if (tespinyincode != null ? !tespinyincode.equals(that.tespinyincode) : that.tespinyincode != null)
            return false;
        if (tesfivestroke != null ? !tesfivestroke.equals(that.tesfivestroke) : that.tesfivestroke != null)
            return false;
        if (tespathologyid != null ? !tespathologyid.equals(that.tespathologyid) : that.tespathologyid != null)
            return false;
        if (tesitemhandle != null ? !tesitemhandle.equals(that.tesitemhandle) : that.tesitemhandle != null)
            return false;
        if (tesfirstv != null ? !tesfirstv.equals(that.tesfirstv) : that.tesfirstv != null) return false;
        if (tessecondv != null ? !tessecondv.equals(that.tessecondv) : that.tessecondv != null) return false;
        if (tesfirstn != null ? !tesfirstn.equals(that.tesfirstn) : that.tesfirstn != null) return false;
        if (tesreamrk != null ? !tesreamrk.equals(that.tesreamrk) : that.tesreamrk != null) return false;
        if (tescreateuser != null ? !tescreateuser.equals(that.tescreateuser) : that.tescreateuser != null)
            return false;
        if (tescreatetime != null ? !tescreatetime.equals(that.tescreatetime) : that.tescreatetime != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (testitemid ^ (testitemid >>> 32));
        result = 31 * result + (teschinesename != null ? teschinesename.hashCode() : 0);
        result = 31 * result + (tesenglishname != null ? tesenglishname.hashCode() : 0);
        result = 31 * result + (tesitemsort != null ? tesitemsort.hashCode() : 0);
        result = 31 * result + (tespinyincode != null ? tespinyincode.hashCode() : 0);
        result = 31 * result + (tesfivestroke != null ? tesfivestroke.hashCode() : 0);
        result = 31 * result + (int) (tesitemtype ^ (tesitemtype >>> 32));
        result = 31 * result + (tespathologyid != null ? tespathologyid.hashCode() : 0);
        result = 31 * result + (tesitemhandle != null ? tesitemhandle.hashCode() : 0);
        result = 31 * result + (int) (tesischarge ^ (tesischarge >>> 32));
        result = 31 * result + (int) (tesuseflag ^ (tesuseflag >>> 32));
        result = 31 * result + (tesfirstv != null ? tesfirstv.hashCode() : 0);
        result = 31 * result + (tessecondv != null ? tessecondv.hashCode() : 0);
        result = 31 * result + (tesfirstn != null ? tesfirstn.hashCode() : 0);
        result = 31 * result + (tesreamrk != null ? tesreamrk.hashCode() : 0);
        result = 31 * result + (tescreateuser != null ? tescreateuser.hashCode() : 0);
        result = 31 * result + (tescreatetime != null ? tescreatetime.hashCode() : 0);
        return result;
    }
}
