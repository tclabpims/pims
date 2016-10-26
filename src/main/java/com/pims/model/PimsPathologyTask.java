package com.pims.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by king on 2016/10/26.
 */
@Entity
@Table(name = "PIMS_PATHOLOGY_TASK")
public class PimsPathologyTask {
    private long taskid;
    private long tascustomerid;
    private long tassampleid;
    private String taspathologycode;
    private String tastaskname;
    private String tastaskdescribe;
    private long tastasktype;
    private int tastaskstate;
    private String taspromoterid;
    private String taspromotername;
    private String tasreciverid;
    private String tasrecivername;
    private String tasfirstv;
    private String tassecondv;
    private String tasthirdv;
    private Long tasfirstn;
    private Date tasfirstd;
    private Long tassecondn;
    private Date tassecondd;
    private Date tascreatetime;
    private String tascreateuser;

    @Id
    @Column(name = "TASKID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="SEQ_TASKID")
    @SequenceGenerator(name = "SEQ_TASKID", sequenceName = "SEQ_TASKID", allocationSize=1)
    public long getTaskid() {
        return taskid;
    }

    public void setTaskid(long taskid) {
        this.taskid = taskid;
    }

    @Basic
    @Column(name = "TASCUSTOMERID")
    public long getTascustomerid() {
        return tascustomerid;
    }

    public void setTascustomerid(long tascustomerid) {
        this.tascustomerid = tascustomerid;
    }

    @Basic
    @Column(name = "TASSAMPLEID")
    public long getTassampleid() {
        return tassampleid;
    }

    public void setTassampleid(long tassampleid) {
        this.tassampleid = tassampleid;
    }

    @Basic
    @Column(name = "TASPATHOLOGYCODE")
    public String getTaspathologycode() {
        return taspathologycode;
    }

    public void setTaspathologycode(String taspathologycode) {
        this.taspathologycode = taspathologycode;
    }

    @Basic
    @Column(name = "TASTASKNAME")
    public String getTastaskname() {
        return tastaskname;
    }

    public void setTastaskname(String tastaskname) {
        this.tastaskname = tastaskname;
    }

    @Basic
    @Column(name = "TASTASKDESCRIBE")
    public String getTastaskdescribe() {
        return tastaskdescribe;
    }

    public void setTastaskdescribe(String tastaskdescribe) {
        this.tastaskdescribe = tastaskdescribe;
    }

    @Basic
    @Column(name = "TASTASKTYPE")
    public long getTastasktype() {
        return tastasktype;
    }

    public void setTastasktype(long tastasktype) {
        this.tastasktype = tastasktype;
    }

    @Basic
    @Column(name = "TASTASKSTATE")
    public int getTastaskstate() {
        return tastaskstate;
    }

    public void setTastaskstate(int tastaskstate) {
        this.tastaskstate = tastaskstate;
    }

    @Basic
    @Column(name = "TASPROMOTERID")
    public String getTaspromoterid() {
        return taspromoterid;
    }

    public void setTaspromoterid(String taspromoterid) {
        this.taspromoterid = taspromoterid;
    }

    @Basic
    @Column(name = "TASPROMOTERNAME")
    public String getTaspromotername() {
        return taspromotername;
    }

    public void setTaspromotername(String taspromotername) {
        this.taspromotername = taspromotername;
    }

    @Basic
    @Column(name = "TASRECIVERID")
    public String getTasreciverid() {
        return tasreciverid;
    }

    public void setTasreciverid(String tasreciverid) {
        this.tasreciverid = tasreciverid;
    }

    @Basic
    @Column(name = "TASRECIVERNAME")
    public String getTasrecivername() {
        return tasrecivername;
    }

    public void setTasrecivername(String tasrecivername) {
        this.tasrecivername = tasrecivername;
    }

    @Basic
    @Column(name = "TASFIRSTV")
    public String getTasfirstv() {
        return tasfirstv;
    }

    public void setTasfirstv(String tasfirstv) {
        this.tasfirstv = tasfirstv;
    }

    @Basic
    @Column(name = "TASSECONDV")
    public String getTassecondv() {
        return tassecondv;
    }

    public void setTassecondv(String tassecondv) {
        this.tassecondv = tassecondv;
    }

    @Basic
    @Column(name = "TASTHIRDV")
    public String getTasthirdv() {
        return tasthirdv;
    }

    public void setTasthirdv(String tasthirdv) {
        this.tasthirdv = tasthirdv;
    }

    @Basic
    @Column(name = "TASFIRSTN")
    public Long getTasfirstn() {
        return tasfirstn;
    }

    public void setTasfirstn(Long tasfirstn) {
        this.tasfirstn = tasfirstn;
    }

    @Basic
    @Column(name = "TASFIRSTD")
    public Date getTasfirstd() {
        return tasfirstd;
    }

    public void setTasfirstd(Date tasfirstd) {
        this.tasfirstd = tasfirstd;
    }

    @Basic
    @Column(name = "TASSECONDN")
    public Long getTassecondn() {
        return tassecondn;
    }

    public void setTassecondn(Long tassecondn) {
        this.tassecondn = tassecondn;
    }

    @Basic
    @Column(name = "TASSECONDD")
    public Date getTassecondd() {
        return tassecondd;
    }

    public void setTassecondd(Date tassecondd) {
        this.tassecondd = tassecondd;
    }

    @Basic
    @Column(name = "TASCREATETIME")
    public Date getTascreatetime() {
        return tascreatetime;
    }

    public void setTascreatetime(Date tascreatetime) {
        this.tascreatetime = tascreatetime;
    }

    @Basic
    @Column(name = "TASCREATEUSER")
    public String getTascreateuser() {
        return tascreateuser;
    }

    public void setTascreateuser(String tascreateuser) {
        this.tascreateuser = tascreateuser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PimsPathologyTask that = (PimsPathologyTask) o;

        if (taskid != that.taskid) return false;
        if (tascustomerid != that.tascustomerid) return false;
        if (tassampleid != that.tassampleid) return false;
        if (tastasktype != that.tastasktype) return false;
        if (tastaskstate != that.tastaskstate) return false;
        if (taspathologycode != null ? !taspathologycode.equals(that.taspathologycode) : that.taspathologycode != null)
            return false;
        if (tastaskname != null ? !tastaskname.equals(that.tastaskname) : that.tastaskname != null) return false;
        if (tastaskdescribe != null ? !tastaskdescribe.equals(that.tastaskdescribe) : that.tastaskdescribe != null)
            return false;
        if (taspromoterid != null ? !taspromoterid.equals(that.taspromoterid) : that.taspromoterid != null)
            return false;
        if (taspromotername != null ? !taspromotername.equals(that.taspromotername) : that.taspromotername != null)
            return false;
        if (tasreciverid != null ? !tasreciverid.equals(that.tasreciverid) : that.tasreciverid != null) return false;
        if (tasrecivername != null ? !tasrecivername.equals(that.tasrecivername) : that.tasrecivername != null)
            return false;
        if (tasfirstv != null ? !tasfirstv.equals(that.tasfirstv) : that.tasfirstv != null) return false;
        if (tassecondv != null ? !tassecondv.equals(that.tassecondv) : that.tassecondv != null) return false;
        if (tasthirdv != null ? !tasthirdv.equals(that.tasthirdv) : that.tasthirdv != null) return false;
        if (tasfirstn != null ? !tasfirstn.equals(that.tasfirstn) : that.tasfirstn != null) return false;
        if (tasfirstd != null ? !tasfirstd.equals(that.tasfirstd) : that.tasfirstd != null) return false;
        if (tassecondn != null ? !tassecondn.equals(that.tassecondn) : that.tassecondn != null) return false;
        if (tassecondd != null ? !tassecondd.equals(that.tassecondd) : that.tassecondd != null) return false;
        if (tascreatetime != null ? !tascreatetime.equals(that.tascreatetime) : that.tascreatetime != null)
            return false;
        if (tascreateuser != null ? !tascreateuser.equals(that.tascreateuser) : that.tascreateuser != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (taskid ^ (taskid >>> 32));
        result = 31 * result + (int) (tascustomerid ^ (tascustomerid >>> 32));
        result = 31 * result + (int) (tassampleid ^ (tassampleid >>> 32));
        result = 31 * result + (taspathologycode != null ? taspathologycode.hashCode() : 0);
        result = 31 * result + (tastaskname != null ? tastaskname.hashCode() : 0);
        result = 31 * result + (tastaskdescribe != null ? tastaskdescribe.hashCode() : 0);
        result = 31 * result + (int) (tastasktype ^ (tastasktype >>> 32));
        result = 31 * result + (tastaskstate ^ (tastaskstate >>> 32));
        result = 31 * result + (taspromoterid != null ? taspromoterid.hashCode() : 0);
        result = 31 * result + (taspromotername != null ? taspromotername.hashCode() : 0);
        result = 31 * result + (tasreciverid != null ? tasreciverid.hashCode() : 0);
        result = 31 * result + (tasrecivername != null ? tasrecivername.hashCode() : 0);
        result = 31 * result + (tasfirstv != null ? tasfirstv.hashCode() : 0);
        result = 31 * result + (tassecondv != null ? tassecondv.hashCode() : 0);
        result = 31 * result + (tasthirdv != null ? tasthirdv.hashCode() : 0);
        result = 31 * result + (tasfirstn != null ? tasfirstn.hashCode() : 0);
        result = 31 * result + (tasfirstd != null ? tasfirstd.hashCode() : 0);
        result = 31 * result + (tassecondn != null ? tassecondn.hashCode() : 0);
        result = 31 * result + (tassecondd != null ? tassecondd.hashCode() : 0);
        result = 31 * result + (tascreatetime != null ? tascreatetime.hashCode() : 0);
        result = 31 * result + (tascreateuser != null ? tascreateuser.hashCode() : 0);
        return result;
    }
}
