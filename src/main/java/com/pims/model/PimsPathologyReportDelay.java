package com.pims.model;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

/**
 * Created by king on 2016/9/28.
 */
@Entity
@Table(name = "PIMS_PATHOLOGY_REPORT_DELAY")
public class PimsPathologyReportDelay {
    private long delayid;
    private long delsampleid;
    private String delpathologycode;
    private long delreasonid;
    private String delreason;
    private long deldays;
    private Date delreporttime;
    private String deldiagnosis;
    private String deldoctor;
    private String delfirstv;
    private Long delfirstn;
    private Date delfirstd;
    private Date delcreatetime;
    private String delcreateuser;
    private long delcustomerid;

    @Column(name="delcustomerid")
    public long getDelcustomerid() {
        return delcustomerid;
    }

    public void setDelcustomerid(long delcustomerid) {
        this.delcustomerid = delcustomerid;
    }

    @Id
    @Column(name = "DELAYID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="SEQ_DELAYID")
    @SequenceGenerator(name = "SEQ_DELAYID", sequenceName = "SEQ_DELAYID", allocationSize=1)
    public long getDelayid() {
        return delayid;
    }

    public void setDelayid(long delayid) {
        this.delayid = delayid;
    }

    @Basic
    @Column(name = "DELSAMPLEID")
    public long getDelsampleid() {
        return delsampleid;
    }

    public void setDelsampleid(long delsampleid) {
        this.delsampleid = delsampleid;
    }

    @Basic
    @Column(name = "DELPATHOLOGYCODE")
    public String getDelpathologycode() {
        return delpathologycode;
    }

    public void setDelpathologycode(String delpathologycode) {
        this.delpathologycode = delpathologycode;
    }

    @Basic
    @Column(name = "DELREASONID")
    public long getDelreasonid() {
        return delreasonid;
    }

    public void setDelreasonid(long delreasonid) {
        this.delreasonid = delreasonid;
    }

    @Basic
    @Column(name = "DELREASON")
    public String getDelreason() {
        return delreason;
    }

    public void setDelreason(String delreason) {
        this.delreason = delreason;
    }

    @Basic
    @Column(name = "DELDAYS")
    public long getDeldays() {
        return deldays;
    }

    public void setDeldays(long deldays) {
        this.deldays = deldays;
    }

    @Basic
    @Column(name = "DELREPORTTIME")
    public Date getDelreporttime() {
        return delreporttime;
    }

    public void setDelreporttime(Date delreporttime) {
        this.delreporttime = delreporttime;
    }

    @Basic
    @Column(name = "DELDIAGNOSIS")
    public String getDeldiagnosis() {
        return deldiagnosis;
    }

    public void setDeldiagnosis(String deldiagnosis) {
        this.deldiagnosis = deldiagnosis;
    }

    @Basic
    @Column(name = "DELDOCTOR")
    public String getDeldoctor() {
        return deldoctor;
    }

    public void setDeldoctor(String deldoctor) {
        this.deldoctor = deldoctor;
    }

    @Basic
    @Column(name = "DELFIRSTV")
    public String getDelfirstv() {
        return delfirstv;
    }

    public void setDelfirstv(String delfirstv) {
        this.delfirstv = delfirstv;
    }

    @Basic
    @Column(name = "DELFIRSTN")
    public Long getDelfirstn() {
        return delfirstn;
    }

    public void setDelfirstn(Long delfirstn) {
        this.delfirstn = delfirstn;
    }

    @Basic
    @Column(name = "DELFIRSTD")
    public Date getDelfirstd() {
        return delfirstd;
    }

    public void setDelfirstd(Time delfirstd) {
        this.delfirstd = delfirstd;
    }

    @Basic
    @Column(name = "DELCREATETIME")
    public Date getDelcreatetime() {
        return delcreatetime;
    }

    public void setDelcreatetime(Date delcreatetime) {
        this.delcreatetime = delcreatetime;
    }

    @Basic
    @Column(name = "DELCREATEUSER")
    public String getDelcreateuser() {
        return delcreateuser;
    }

    public void setDelcreateuser(String delcreateuser) {
        this.delcreateuser = delcreateuser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PimsPathologyReportDelay that = (PimsPathologyReportDelay) o;

        if (delayid != that.delayid) return false;
        if (delsampleid != that.delsampleid) return false;
        if (delreasonid != that.delreasonid) return false;
        if (deldays != that.deldays) return false;
        if (delpathologycode != null ? !delpathologycode.equals(that.delpathologycode) : that.delpathologycode != null)
            return false;
        if (delreason != null ? !delreason.equals(that.delreason) : that.delreason != null) return false;
        if (delreporttime != null ? !delreporttime.equals(that.delreporttime) : that.delreporttime != null)
            return false;
        if (deldiagnosis != null ? !deldiagnosis.equals(that.deldiagnosis) : that.deldiagnosis != null) return false;
        if (deldoctor != null ? !deldoctor.equals(that.deldoctor) : that.deldoctor != null) return false;
        if (delfirstv != null ? !delfirstv.equals(that.delfirstv) : that.delfirstv != null) return false;
        if (delfirstn != null ? !delfirstn.equals(that.delfirstn) : that.delfirstn != null) return false;
        if (delfirstd != null ? !delfirstd.equals(that.delfirstd) : that.delfirstd != null) return false;
        if (delcreatetime != null ? !delcreatetime.equals(that.delcreatetime) : that.delcreatetime != null)
            return false;
        if (delcreateuser != null ? !delcreateuser.equals(that.delcreateuser) : that.delcreateuser != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (delayid ^ (delayid >>> 32));
        result = 31 * result + (int) (delsampleid ^ (delsampleid >>> 32));
        result = 31 * result + (delpathologycode != null ? delpathologycode.hashCode() : 0);
        result = 31 * result + (int) (delreasonid ^ (delreasonid >>> 32));
        result = 31 * result + (delreason != null ? delreason.hashCode() : 0);
        result = 31 * result + (int) (deldays ^ (deldays >>> 32));
        result = 31 * result + (delreporttime != null ? delreporttime.hashCode() : 0);
        result = 31 * result + (deldiagnosis != null ? deldiagnosis.hashCode() : 0);
        result = 31 * result + (deldoctor != null ? deldoctor.hashCode() : 0);
        result = 31 * result + (delfirstv != null ? delfirstv.hashCode() : 0);
        result = 31 * result + (delfirstn != null ? delfirstn.hashCode() : 0);
        result = 31 * result + (delfirstd != null ? delfirstd.hashCode() : 0);
        result = 31 * result + (delcreatetime != null ? delcreatetime.hashCode() : 0);
        result = 31 * result + (delcreateuser != null ? delcreateuser.hashCode() : 0);
        return result;
    }
}
