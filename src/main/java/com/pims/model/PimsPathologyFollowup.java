package com.pims.model;

import javax.persistence.*;
import java.sql.Time;

/**
 * Created by king on 2016/9/28.
 */
@Entity
@Table(name = "PIMS_PATHOLOGY_FOLLOWUP", schema = "KFTEST", catalog = "")
public class PimsPathologyFollowup {
    private long followupid;
    private long folsampleid;
    private String folpathologycode;
    private String folcustomercode;
    private long folstate;
    private String foluserid;
    private String folusername;
    private Time folplantime;
    private Time folstarttime;
    private Time folendtime;
    private String folcontent;
    private String folfirstv;
    private String folsecondv;
    private String folthirdv;
    private Time folfirstd;
    private Time folsecondd;
    private Long folfirstn;
    private Long folsecondn;
    private String folremark;
    private String folcreateuser;
    private Time folcreatetime;

    @Id
    @Column(name = "FOLLOWUPID")
    public long getFollowupid() {
        return followupid;
    }

    public void setFollowupid(long followupid) {
        this.followupid = followupid;
    }

    @Basic
    @Column(name = "FOLSAMPLEID")
    public long getFolsampleid() {
        return folsampleid;
    }

    public void setFolsampleid(long folsampleid) {
        this.folsampleid = folsampleid;
    }

    @Basic
    @Column(name = "FOLPATHOLOGYCODE")
    public String getFolpathologycode() {
        return folpathologycode;
    }

    public void setFolpathologycode(String folpathologycode) {
        this.folpathologycode = folpathologycode;
    }

    @Basic
    @Column(name = "FOLCUSTOMERCODE")
    public String getFolcustomercode() {
        return folcustomercode;
    }

    public void setFolcustomercode(String folcustomercode) {
        this.folcustomercode = folcustomercode;
    }

    @Basic
    @Column(name = "FOLSTATE")
    public long getFolstate() {
        return folstate;
    }

    public void setFolstate(long folstate) {
        this.folstate = folstate;
    }

    @Basic
    @Column(name = "FOLUSERID")
    public String getFoluserid() {
        return foluserid;
    }

    public void setFoluserid(String foluserid) {
        this.foluserid = foluserid;
    }

    @Basic
    @Column(name = "FOLUSERNAME")
    public String getFolusername() {
        return folusername;
    }

    public void setFolusername(String folusername) {
        this.folusername = folusername;
    }

    @Basic
    @Column(name = "FOLPLANTIME")
    public Time getFolplantime() {
        return folplantime;
    }

    public void setFolplantime(Time folplantime) {
        this.folplantime = folplantime;
    }

    @Basic
    @Column(name = "FOLSTARTTIME")
    public Time getFolstarttime() {
        return folstarttime;
    }

    public void setFolstarttime(Time folstarttime) {
        this.folstarttime = folstarttime;
    }

    @Basic
    @Column(name = "FOLENDTIME")
    public Time getFolendtime() {
        return folendtime;
    }

    public void setFolendtime(Time folendtime) {
        this.folendtime = folendtime;
    }

    @Basic
    @Column(name = "FOLCONTENT")
    public String getFolcontent() {
        return folcontent;
    }

    public void setFolcontent(String folcontent) {
        this.folcontent = folcontent;
    }

    @Basic
    @Column(name = "FOLFIRSTV")
    public String getFolfirstv() {
        return folfirstv;
    }

    public void setFolfirstv(String folfirstv) {
        this.folfirstv = folfirstv;
    }

    @Basic
    @Column(name = "FOLSECONDV")
    public String getFolsecondv() {
        return folsecondv;
    }

    public void setFolsecondv(String folsecondv) {
        this.folsecondv = folsecondv;
    }

    @Basic
    @Column(name = "FOLTHIRDV")
    public String getFolthirdv() {
        return folthirdv;
    }

    public void setFolthirdv(String folthirdv) {
        this.folthirdv = folthirdv;
    }

    @Basic
    @Column(name = "FOLFIRSTD")
    public Time getFolfirstd() {
        return folfirstd;
    }

    public void setFolfirstd(Time folfirstd) {
        this.folfirstd = folfirstd;
    }

    @Basic
    @Column(name = "FOLSECONDD")
    public Time getFolsecondd() {
        return folsecondd;
    }

    public void setFolsecondd(Time folsecondd) {
        this.folsecondd = folsecondd;
    }

    @Basic
    @Column(name = "FOLFIRSTN")
    public Long getFolfirstn() {
        return folfirstn;
    }

    public void setFolfirstn(Long folfirstn) {
        this.folfirstn = folfirstn;
    }

    @Basic
    @Column(name = "FOLSECONDN")
    public Long getFolsecondn() {
        return folsecondn;
    }

    public void setFolsecondn(Long folsecondn) {
        this.folsecondn = folsecondn;
    }

    @Basic
    @Column(name = "FOLREMARK")
    public String getFolremark() {
        return folremark;
    }

    public void setFolremark(String folremark) {
        this.folremark = folremark;
    }

    @Basic
    @Column(name = "FOLCREATEUSER")
    public String getFolcreateuser() {
        return folcreateuser;
    }

    public void setFolcreateuser(String folcreateuser) {
        this.folcreateuser = folcreateuser;
    }

    @Basic
    @Column(name = "FOLCREATETIME")
    public Time getFolcreatetime() {
        return folcreatetime;
    }

    public void setFolcreatetime(Time folcreatetime) {
        this.folcreatetime = folcreatetime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PimsPathologyFollowup that = (PimsPathologyFollowup) o;

        if (followupid != that.followupid) return false;
        if (folsampleid != that.folsampleid) return false;
        if (folstate != that.folstate) return false;
        if (folpathologycode != null ? !folpathologycode.equals(that.folpathologycode) : that.folpathologycode != null)
            return false;
        if (folcustomercode != null ? !folcustomercode.equals(that.folcustomercode) : that.folcustomercode != null)
            return false;
        if (foluserid != null ? !foluserid.equals(that.foluserid) : that.foluserid != null) return false;
        if (folusername != null ? !folusername.equals(that.folusername) : that.folusername != null) return false;
        if (folplantime != null ? !folplantime.equals(that.folplantime) : that.folplantime != null) return false;
        if (folstarttime != null ? !folstarttime.equals(that.folstarttime) : that.folstarttime != null) return false;
        if (folendtime != null ? !folendtime.equals(that.folendtime) : that.folendtime != null) return false;
        if (folcontent != null ? !folcontent.equals(that.folcontent) : that.folcontent != null) return false;
        if (folfirstv != null ? !folfirstv.equals(that.folfirstv) : that.folfirstv != null) return false;
        if (folsecondv != null ? !folsecondv.equals(that.folsecondv) : that.folsecondv != null) return false;
        if (folthirdv != null ? !folthirdv.equals(that.folthirdv) : that.folthirdv != null) return false;
        if (folfirstd != null ? !folfirstd.equals(that.folfirstd) : that.folfirstd != null) return false;
        if (folsecondd != null ? !folsecondd.equals(that.folsecondd) : that.folsecondd != null) return false;
        if (folfirstn != null ? !folfirstn.equals(that.folfirstn) : that.folfirstn != null) return false;
        if (folsecondn != null ? !folsecondn.equals(that.folsecondn) : that.folsecondn != null) return false;
        if (folremark != null ? !folremark.equals(that.folremark) : that.folremark != null) return false;
        if (folcreateuser != null ? !folcreateuser.equals(that.folcreateuser) : that.folcreateuser != null)
            return false;
        if (folcreatetime != null ? !folcreatetime.equals(that.folcreatetime) : that.folcreatetime != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (followupid ^ (followupid >>> 32));
        result = 31 * result + (int) (folsampleid ^ (folsampleid >>> 32));
        result = 31 * result + (folpathologycode != null ? folpathologycode.hashCode() : 0);
        result = 31 * result + (folcustomercode != null ? folcustomercode.hashCode() : 0);
        result = 31 * result + (int) (folstate ^ (folstate >>> 32));
        result = 31 * result + (foluserid != null ? foluserid.hashCode() : 0);
        result = 31 * result + (folusername != null ? folusername.hashCode() : 0);
        result = 31 * result + (folplantime != null ? folplantime.hashCode() : 0);
        result = 31 * result + (folstarttime != null ? folstarttime.hashCode() : 0);
        result = 31 * result + (folendtime != null ? folendtime.hashCode() : 0);
        result = 31 * result + (folcontent != null ? folcontent.hashCode() : 0);
        result = 31 * result + (folfirstv != null ? folfirstv.hashCode() : 0);
        result = 31 * result + (folsecondv != null ? folsecondv.hashCode() : 0);
        result = 31 * result + (folthirdv != null ? folthirdv.hashCode() : 0);
        result = 31 * result + (folfirstd != null ? folfirstd.hashCode() : 0);
        result = 31 * result + (folsecondd != null ? folsecondd.hashCode() : 0);
        result = 31 * result + (folfirstn != null ? folfirstn.hashCode() : 0);
        result = 31 * result + (folsecondn != null ? folsecondn.hashCode() : 0);
        result = 31 * result + (folremark != null ? folremark.hashCode() : 0);
        result = 31 * result + (folcreateuser != null ? folcreateuser.hashCode() : 0);
        result = 31 * result + (folcreatetime != null ? folcreatetime.hashCode() : 0);
        return result;
    }
}
