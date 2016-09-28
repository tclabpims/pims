package com.pims.model;

import javax.persistence.*;
import java.sql.Time;

/**
 * Created by king on 2016/9/28.
 */
@Entity
@Table(name = "PIMS_PATHOLOGY_MESSAGE", schema = "KFTEST", catalog = "")
public class PimsPathologyMessage {
    private long messageid;
    private long meslevel;
    private String mescontent;
    private String messenderid;
    private String messendername;
    private long mesvaliddays;
    private Time mesvalidtime;
    private String mesreceivestation;
    private String mesreceiverid;
    private String mesreceivername;
    private Time meshandletime;
    private String meshandleuser;
    private String mesremark;
    private String mesfirstv;
    private Long mesfirstn;
    private Time mesfirstd;
    private Time mescreatetime;
    private String mescreateuser;

    @Id
    @Column(name = "MESSAGEID")
    public long getMessageid() {
        return messageid;
    }

    public void setMessageid(long messageid) {
        this.messageid = messageid;
    }

    @Basic
    @Column(name = "MESLEVEL")
    public long getMeslevel() {
        return meslevel;
    }

    public void setMeslevel(long meslevel) {
        this.meslevel = meslevel;
    }

    @Basic
    @Column(name = "MESCONTENT")
    public String getMescontent() {
        return mescontent;
    }

    public void setMescontent(String mescontent) {
        this.mescontent = mescontent;
    }

    @Basic
    @Column(name = "MESSENDERID")
    public String getMessenderid() {
        return messenderid;
    }

    public void setMessenderid(String messenderid) {
        this.messenderid = messenderid;
    }

    @Basic
    @Column(name = "MESSENDERNAME")
    public String getMessendername() {
        return messendername;
    }

    public void setMessendername(String messendername) {
        this.messendername = messendername;
    }

    @Basic
    @Column(name = "MESVALIDDAYS")
    public long getMesvaliddays() {
        return mesvaliddays;
    }

    public void setMesvaliddays(long mesvaliddays) {
        this.mesvaliddays = mesvaliddays;
    }

    @Basic
    @Column(name = "MESVALIDTIME")
    public Time getMesvalidtime() {
        return mesvalidtime;
    }

    public void setMesvalidtime(Time mesvalidtime) {
        this.mesvalidtime = mesvalidtime;
    }

    @Basic
    @Column(name = "MESRECEIVESTATION")
    public String getMesreceivestation() {
        return mesreceivestation;
    }

    public void setMesreceivestation(String mesreceivestation) {
        this.mesreceivestation = mesreceivestation;
    }

    @Basic
    @Column(name = "MESRECEIVERID")
    public String getMesreceiverid() {
        return mesreceiverid;
    }

    public void setMesreceiverid(String mesreceiverid) {
        this.mesreceiverid = mesreceiverid;
    }

    @Basic
    @Column(name = "MESRECEIVERNAME")
    public String getMesreceivername() {
        return mesreceivername;
    }

    public void setMesreceivername(String mesreceivername) {
        this.mesreceivername = mesreceivername;
    }

    @Basic
    @Column(name = "MESHANDLETIME")
    public Time getMeshandletime() {
        return meshandletime;
    }

    public void setMeshandletime(Time meshandletime) {
        this.meshandletime = meshandletime;
    }

    @Basic
    @Column(name = "MESHANDLEUSER")
    public String getMeshandleuser() {
        return meshandleuser;
    }

    public void setMeshandleuser(String meshandleuser) {
        this.meshandleuser = meshandleuser;
    }

    @Basic
    @Column(name = "MESREMARK")
    public String getMesremark() {
        return mesremark;
    }

    public void setMesremark(String mesremark) {
        this.mesremark = mesremark;
    }

    @Basic
    @Column(name = "MESFIRSTV")
    public String getMesfirstv() {
        return mesfirstv;
    }

    public void setMesfirstv(String mesfirstv) {
        this.mesfirstv = mesfirstv;
    }

    @Basic
    @Column(name = "MESFIRSTN")
    public Long getMesfirstn() {
        return mesfirstn;
    }

    public void setMesfirstn(Long mesfirstn) {
        this.mesfirstn = mesfirstn;
    }

    @Basic
    @Column(name = "MESFIRSTD")
    public Time getMesfirstd() {
        return mesfirstd;
    }

    public void setMesfirstd(Time mesfirstd) {
        this.mesfirstd = mesfirstd;
    }

    @Basic
    @Column(name = "MESCREATETIME")
    public Time getMescreatetime() {
        return mescreatetime;
    }

    public void setMescreatetime(Time mescreatetime) {
        this.mescreatetime = mescreatetime;
    }

    @Basic
    @Column(name = "MESCREATEUSER")
    public String getMescreateuser() {
        return mescreateuser;
    }

    public void setMescreateuser(String mescreateuser) {
        this.mescreateuser = mescreateuser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PimsPathologyMessage that = (PimsPathologyMessage) o;

        if (messageid != that.messageid) return false;
        if (meslevel != that.meslevel) return false;
        if (mesvaliddays != that.mesvaliddays) return false;
        if (mescontent != null ? !mescontent.equals(that.mescontent) : that.mescontent != null) return false;
        if (messenderid != null ? !messenderid.equals(that.messenderid) : that.messenderid != null) return false;
        if (messendername != null ? !messendername.equals(that.messendername) : that.messendername != null)
            return false;
        if (mesvalidtime != null ? !mesvalidtime.equals(that.mesvalidtime) : that.mesvalidtime != null) return false;
        if (mesreceivestation != null ? !mesreceivestation.equals(that.mesreceivestation) : that.mesreceivestation != null)
            return false;
        if (mesreceiverid != null ? !mesreceiverid.equals(that.mesreceiverid) : that.mesreceiverid != null)
            return false;
        if (mesreceivername != null ? !mesreceivername.equals(that.mesreceivername) : that.mesreceivername != null)
            return false;
        if (meshandletime != null ? !meshandletime.equals(that.meshandletime) : that.meshandletime != null)
            return false;
        if (meshandleuser != null ? !meshandleuser.equals(that.meshandleuser) : that.meshandleuser != null)
            return false;
        if (mesremark != null ? !mesremark.equals(that.mesremark) : that.mesremark != null) return false;
        if (mesfirstv != null ? !mesfirstv.equals(that.mesfirstv) : that.mesfirstv != null) return false;
        if (mesfirstn != null ? !mesfirstn.equals(that.mesfirstn) : that.mesfirstn != null) return false;
        if (mesfirstd != null ? !mesfirstd.equals(that.mesfirstd) : that.mesfirstd != null) return false;
        if (mescreatetime != null ? !mescreatetime.equals(that.mescreatetime) : that.mescreatetime != null)
            return false;
        if (mescreateuser != null ? !mescreateuser.equals(that.mescreateuser) : that.mescreateuser != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (messageid ^ (messageid >>> 32));
        result = 31 * result + (int) (meslevel ^ (meslevel >>> 32));
        result = 31 * result + (mescontent != null ? mescontent.hashCode() : 0);
        result = 31 * result + (messenderid != null ? messenderid.hashCode() : 0);
        result = 31 * result + (messendername != null ? messendername.hashCode() : 0);
        result = 31 * result + (int) (mesvaliddays ^ (mesvaliddays >>> 32));
        result = 31 * result + (mesvalidtime != null ? mesvalidtime.hashCode() : 0);
        result = 31 * result + (mesreceivestation != null ? mesreceivestation.hashCode() : 0);
        result = 31 * result + (mesreceiverid != null ? mesreceiverid.hashCode() : 0);
        result = 31 * result + (mesreceivername != null ? mesreceivername.hashCode() : 0);
        result = 31 * result + (meshandletime != null ? meshandletime.hashCode() : 0);
        result = 31 * result + (meshandleuser != null ? meshandleuser.hashCode() : 0);
        result = 31 * result + (mesremark != null ? mesremark.hashCode() : 0);
        result = 31 * result + (mesfirstv != null ? mesfirstv.hashCode() : 0);
        result = 31 * result + (mesfirstn != null ? mesfirstn.hashCode() : 0);
        result = 31 * result + (mesfirstd != null ? mesfirstd.hashCode() : 0);
        result = 31 * result + (mescreatetime != null ? mescreatetime.hashCode() : 0);
        result = 31 * result + (mescreateuser != null ? mescreateuser.hashCode() : 0);
        return result;
    }
}
