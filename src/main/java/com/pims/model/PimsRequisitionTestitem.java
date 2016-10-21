package com.pims.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by king on 2016/10/21.
 */
@Entity
@Table(name = "PIMS_REQUISITION_TESTITEM")
public class PimsRequisitionTestitem {
    private long requisitionid;
    private long testitemid;
    private long reqicustomerid;
    private String reqiitemname;
    private Long reqisampleid;
    private int reqiischarge;
    private Date reqiprinttime;
    private String reqiremark;
    private String reqifirstv;
    private String reqisecondv;
    private Long reqifirstn;
    private String reqicreateuser;
    private Date reqicreatetime;
    private long reqiid;

    @Basic
    @Column(name = "REQUISITIONID")
    public long getRequisitionid() {
        return requisitionid;
    }

    public void setRequisitionid(long requisitionid) {
        this.requisitionid = requisitionid;
    }

    @Basic
    @Column(name = "TESTITEMID")
    public long getTestitemid() {
        return testitemid;
    }

    public void setTestitemid(long testitemid) {
        this.testitemid = testitemid;
    }

    @Basic
    @Column(name = "REQICUSTOMERID")
    public long getReqicustomerid() {
        return reqicustomerid;
    }

    public void setReqicustomerid(long reqicustomerid) {
        this.reqicustomerid = reqicustomerid;
    }

    @Basic
    @Column(name = "REQIITEMNAME")
    public String getReqiitemname() {
        return reqiitemname;
    }

    public void setReqiitemname(String reqiitemname) {
        this.reqiitemname = reqiitemname;
    }

    @Basic
    @Column(name = "REQISAMPLEID")
    public Long getReqisampleid() {
        return reqisampleid;
    }

    public void setReqisampleid(Long reqisampleid) {
        this.reqisampleid = reqisampleid;
    }

    @Basic
    @Column(name = "REQIISCHARGE")
    public int getReqiischarge() {
        return reqiischarge;
    }

    public void setReqiischarge(int reqiischarge) {
        this.reqiischarge = reqiischarge;
    }

    @Basic
    @Column(name = "REQIPRINTTIME")
    public Date getReqiprinttime() {
        return reqiprinttime;
    }

    public void setReqiprinttime(Date reqiprinttime) {
        this.reqiprinttime = reqiprinttime;
    }

    @Basic
    @Column(name = "REQIREMARK")
    public String getReqiremark() {
        return reqiremark;
    }

    public void setReqiremark(String reqiremark) {
        this.reqiremark = reqiremark;
    }

    @Basic
    @Column(name = "REQIFIRSTV")
    public String getReqifirstv() {
        return reqifirstv;
    }

    public void setReqifirstv(String reqifirstv) {
        this.reqifirstv = reqifirstv;
    }

    @Basic
    @Column(name = "REQISECONDV")
    public String getReqisecondv() {
        return reqisecondv;
    }

    public void setReqisecondv(String reqisecondv) {
        this.reqisecondv = reqisecondv;
    }

    @Basic
    @Column(name = "REQIFIRSTN")
    public Long getReqifirstn() {
        return reqifirstn;
    }

    public void setReqifirstn(Long reqifirstn) {
        this.reqifirstn = reqifirstn;
    }

    @Basic
    @Column(name = "REQICREATEUSER")
    public String getReqicreateuser() {
        return reqicreateuser;
    }

    public void setReqicreateuser(String reqicreateuser) {
        this.reqicreateuser = reqicreateuser;
    }

    @Basic
    @Column(name = "REQICREATETIME")
    public Date getReqicreatetime() {
        return reqicreatetime;
    }

    public void setReqicreatetime(Date reqicreatetime) {
        this.reqicreatetime = reqicreatetime;
    }

    @Id
    @Column(name = "REQIID")
    public long getReqiid() {
        return reqiid;
    }

    public void setReqiid(long reqiid) {
        this.reqiid = reqiid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PimsRequisitionTestitem that = (PimsRequisitionTestitem) o;

        if (requisitionid != that.requisitionid) return false;
        if (testitemid != that.testitemid) return false;
        if (reqicustomerid != that.reqicustomerid) return false;
        if (reqiid != that.reqiid) return false;
        if (reqiitemname != null ? !reqiitemname.equals(that.reqiitemname) : that.reqiitemname != null) return false;
        if (reqisampleid != null ? !reqisampleid.equals(that.reqisampleid) : that.reqisampleid != null) return false;
        if (reqiischarge != that.reqiischarge ) return false;
        if (reqiprinttime != null ? !reqiprinttime.equals(that.reqiprinttime) : that.reqiprinttime != null)
            return false;
        if (reqiremark != null ? !reqiremark.equals(that.reqiremark) : that.reqiremark != null) return false;
        if (reqifirstv != null ? !reqifirstv.equals(that.reqifirstv) : that.reqifirstv != null) return false;
        if (reqisecondv != null ? !reqisecondv.equals(that.reqisecondv) : that.reqisecondv != null) return false;
        if (reqifirstn != null ? !reqifirstn.equals(that.reqifirstn) : that.reqifirstn != null) return false;
        if (reqicreateuser != null ? !reqicreateuser.equals(that.reqicreateuser) : that.reqicreateuser != null)
            return false;
        if (reqicreatetime != null ? !reqicreatetime.equals(that.reqicreatetime) : that.reqicreatetime != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (requisitionid ^ (requisitionid >>> 32));
        result = 31 * result + (int) (testitemid ^ (testitemid >>> 32));
        result = 31 * result + (int) (reqicustomerid ^ (reqicustomerid >>> 32));
        result = 31 * result + (reqiitemname != null ? reqiitemname.hashCode() : 0);
        result = 31 * result + (reqisampleid != null ? reqisampleid.hashCode() : 0);
        result = 31 * result + (reqiischarge ^ (reqiischarge >>> 32));
        result = 31 * result + (reqiprinttime != null ? reqiprinttime.hashCode() : 0);
        result = 31 * result + (reqiremark != null ? reqiremark.hashCode() : 0);
        result = 31 * result + (reqifirstv != null ? reqifirstv.hashCode() : 0);
        result = 31 * result + (reqisecondv != null ? reqisecondv.hashCode() : 0);
        result = 31 * result + (reqifirstn != null ? reqifirstn.hashCode() : 0);
        result = 31 * result + (reqicreateuser != null ? reqicreateuser.hashCode() : 0);
        result = 31 * result + (reqicreatetime != null ? reqicreatetime.hashCode() : 0);
        result = 31 * result + (int) (reqiid ^ (reqiid >>> 32));
        return result;
    }
}
