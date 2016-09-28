package com.pims.model;

import javax.persistence.*;
import java.sql.Time;

/**
 * Created by king on 2016/9/28.
 */
@Entity
@Table(name = "PIMS_PATHOLOGY_SIGNATURE", schema = "KFTEST", catalog = "")
public class PimsPathologySignature {
    private long signatureid;
    private String sigloginid;
    private String sigloginname;
    private String sigsavepath;
    private long siguseflag;
    private Time sigcreatetime;
    private String sigcreateuser;
    private String sigfirstv;
    private Long sigfirstn;
    private Time sigfirstd;

    @Id
    @Column(name = "SIGNATUREID")
    public long getSignatureid() {
        return signatureid;
    }

    public void setSignatureid(long signatureid) {
        this.signatureid = signatureid;
    }

    @Basic
    @Column(name = "SIGLOGINID")
    public String getSigloginid() {
        return sigloginid;
    }

    public void setSigloginid(String sigloginid) {
        this.sigloginid = sigloginid;
    }

    @Basic
    @Column(name = "SIGLOGINNAME")
    public String getSigloginname() {
        return sigloginname;
    }

    public void setSigloginname(String sigloginname) {
        this.sigloginname = sigloginname;
    }

    @Basic
    @Column(name = "SIGSAVEPATH")
    public String getSigsavepath() {
        return sigsavepath;
    }

    public void setSigsavepath(String sigsavepath) {
        this.sigsavepath = sigsavepath;
    }

    @Basic
    @Column(name = "SIGUSEFLAG")
    public long getSiguseflag() {
        return siguseflag;
    }

    public void setSiguseflag(long siguseflag) {
        this.siguseflag = siguseflag;
    }

    @Basic
    @Column(name = "SIGCREATETIME")
    public Time getSigcreatetime() {
        return sigcreatetime;
    }

    public void setSigcreatetime(Time sigcreatetime) {
        this.sigcreatetime = sigcreatetime;
    }

    @Basic
    @Column(name = "SIGCREATEUSER")
    public String getSigcreateuser() {
        return sigcreateuser;
    }

    public void setSigcreateuser(String sigcreateuser) {
        this.sigcreateuser = sigcreateuser;
    }

    @Basic
    @Column(name = "SIGFIRSTV")
    public String getSigfirstv() {
        return sigfirstv;
    }

    public void setSigfirstv(String sigfirstv) {
        this.sigfirstv = sigfirstv;
    }

    @Basic
    @Column(name = "SIGFIRSTN")
    public Long getSigfirstn() {
        return sigfirstn;
    }

    public void setSigfirstn(Long sigfirstn) {
        this.sigfirstn = sigfirstn;
    }

    @Basic
    @Column(name = "SIGFIRSTD")
    public Time getSigfirstd() {
        return sigfirstd;
    }

    public void setSigfirstd(Time sigfirstd) {
        this.sigfirstd = sigfirstd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PimsPathologySignature that = (PimsPathologySignature) o;

        if (signatureid != that.signatureid) return false;
        if (siguseflag != that.siguseflag) return false;
        if (sigloginid != null ? !sigloginid.equals(that.sigloginid) : that.sigloginid != null) return false;
        if (sigloginname != null ? !sigloginname.equals(that.sigloginname) : that.sigloginname != null) return false;
        if (sigsavepath != null ? !sigsavepath.equals(that.sigsavepath) : that.sigsavepath != null) return false;
        if (sigcreatetime != null ? !sigcreatetime.equals(that.sigcreatetime) : that.sigcreatetime != null)
            return false;
        if (sigcreateuser != null ? !sigcreateuser.equals(that.sigcreateuser) : that.sigcreateuser != null)
            return false;
        if (sigfirstv != null ? !sigfirstv.equals(that.sigfirstv) : that.sigfirstv != null) return false;
        if (sigfirstn != null ? !sigfirstn.equals(that.sigfirstn) : that.sigfirstn != null) return false;
        if (sigfirstd != null ? !sigfirstd.equals(that.sigfirstd) : that.sigfirstd != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (signatureid ^ (signatureid >>> 32));
        result = 31 * result + (sigloginid != null ? sigloginid.hashCode() : 0);
        result = 31 * result + (sigloginname != null ? sigloginname.hashCode() : 0);
        result = 31 * result + (sigsavepath != null ? sigsavepath.hashCode() : 0);
        result = 31 * result + (int) (siguseflag ^ (siguseflag >>> 32));
        result = 31 * result + (sigcreatetime != null ? sigcreatetime.hashCode() : 0);
        result = 31 * result + (sigcreateuser != null ? sigcreateuser.hashCode() : 0);
        result = 31 * result + (sigfirstv != null ? sigfirstv.hashCode() : 0);
        result = 31 * result + (sigfirstn != null ? sigfirstn.hashCode() : 0);
        result = 31 * result + (sigfirstd != null ? sigfirstd.hashCode() : 0);
        return result;
    }
}
