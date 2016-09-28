package com.pims.model;

import javax.persistence.*;
import java.sql.Time;

/**
 * Created by king on 2016/9/28.
 */
@Entity
@Table(name = "PIMS_REQUISITION_MATERIAL", schema = "KFTEST", catalog = "")
@IdClass(PimsRequisitionMaterialPK.class)
public class PimsRequisitionMaterial {
    private long requisitionid;
    private long materialid;
    private String reqmcustomercode;
    private String reqmmaterialname;
    private String reqmmaterialtype;
    private String reqmsamplingparts;
    private String reqmspecialrequirements;
    private String reqmremark;
    private String reqmfirstv;
    private String reqmsecondv;
    private Long reqmfirstn;
    private Time reqmfirstd;
    private String reqmcreateuser;
    private Time reqmcreatetime;

    @Id
    @Column(name = "REQUISITIONID")
    public long getRequisitionid() {
        return requisitionid;
    }

    public void setRequisitionid(long requisitionid) {
        this.requisitionid = requisitionid;
    }

    @Id
    @Column(name = "MATERIALID")
    public long getMaterialid() {
        return materialid;
    }

    public void setMaterialid(long materialid) {
        this.materialid = materialid;
    }

    @Basic
    @Column(name = "REQMCUSTOMERCODE")
    public String getReqmcustomercode() {
        return reqmcustomercode;
    }

    public void setReqmcustomercode(String reqmcustomercode) {
        this.reqmcustomercode = reqmcustomercode;
    }

    @Basic
    @Column(name = "REQMMATERIALNAME")
    public String getReqmmaterialname() {
        return reqmmaterialname;
    }

    public void setReqmmaterialname(String reqmmaterialname) {
        this.reqmmaterialname = reqmmaterialname;
    }

    @Basic
    @Column(name = "REQMMATERIALTYPE")
    public String getReqmmaterialtype() {
        return reqmmaterialtype;
    }

    public void setReqmmaterialtype(String reqmmaterialtype) {
        this.reqmmaterialtype = reqmmaterialtype;
    }

    @Basic
    @Column(name = "REQMSAMPLINGPARTS")
    public String getReqmsamplingparts() {
        return reqmsamplingparts;
    }

    public void setReqmsamplingparts(String reqmsamplingparts) {
        this.reqmsamplingparts = reqmsamplingparts;
    }

    @Basic
    @Column(name = "REQMSPECIALREQUIREMENTS")
    public String getReqmspecialrequirements() {
        return reqmspecialrequirements;
    }

    public void setReqmspecialrequirements(String reqmspecialrequirements) {
        this.reqmspecialrequirements = reqmspecialrequirements;
    }

    @Basic
    @Column(name = "REQMREMARK")
    public String getReqmremark() {
        return reqmremark;
    }

    public void setReqmremark(String reqmremark) {
        this.reqmremark = reqmremark;
    }

    @Basic
    @Column(name = "REQMFIRSTV")
    public String getReqmfirstv() {
        return reqmfirstv;
    }

    public void setReqmfirstv(String reqmfirstv) {
        this.reqmfirstv = reqmfirstv;
    }

    @Basic
    @Column(name = "REQMSECONDV")
    public String getReqmsecondv() {
        return reqmsecondv;
    }

    public void setReqmsecondv(String reqmsecondv) {
        this.reqmsecondv = reqmsecondv;
    }

    @Basic
    @Column(name = "REQMFIRSTN")
    public Long getReqmfirstn() {
        return reqmfirstn;
    }

    public void setReqmfirstn(Long reqmfirstn) {
        this.reqmfirstn = reqmfirstn;
    }

    @Basic
    @Column(name = "REQMFIRSTD")
    public Time getReqmfirstd() {
        return reqmfirstd;
    }

    public void setReqmfirstd(Time reqmfirstd) {
        this.reqmfirstd = reqmfirstd;
    }

    @Basic
    @Column(name = "REQMCREATEUSER")
    public String getReqmcreateuser() {
        return reqmcreateuser;
    }

    public void setReqmcreateuser(String reqmcreateuser) {
        this.reqmcreateuser = reqmcreateuser;
    }

    @Basic
    @Column(name = "REQMCREATETIME")
    public Time getReqmcreatetime() {
        return reqmcreatetime;
    }

    public void setReqmcreatetime(Time reqmcreatetime) {
        this.reqmcreatetime = reqmcreatetime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PimsRequisitionMaterial that = (PimsRequisitionMaterial) o;

        if (requisitionid != that.requisitionid) return false;
        if (materialid != that.materialid) return false;
        if (reqmcustomercode != null ? !reqmcustomercode.equals(that.reqmcustomercode) : that.reqmcustomercode != null)
            return false;
        if (reqmmaterialname != null ? !reqmmaterialname.equals(that.reqmmaterialname) : that.reqmmaterialname != null)
            return false;
        if (reqmmaterialtype != null ? !reqmmaterialtype.equals(that.reqmmaterialtype) : that.reqmmaterialtype != null)
            return false;
        if (reqmsamplingparts != null ? !reqmsamplingparts.equals(that.reqmsamplingparts) : that.reqmsamplingparts != null)
            return false;
        if (reqmspecialrequirements != null ? !reqmspecialrequirements.equals(that.reqmspecialrequirements) : that.reqmspecialrequirements != null)
            return false;
        if (reqmremark != null ? !reqmremark.equals(that.reqmremark) : that.reqmremark != null) return false;
        if (reqmfirstv != null ? !reqmfirstv.equals(that.reqmfirstv) : that.reqmfirstv != null) return false;
        if (reqmsecondv != null ? !reqmsecondv.equals(that.reqmsecondv) : that.reqmsecondv != null) return false;
        if (reqmfirstn != null ? !reqmfirstn.equals(that.reqmfirstn) : that.reqmfirstn != null) return false;
        if (reqmfirstd != null ? !reqmfirstd.equals(that.reqmfirstd) : that.reqmfirstd != null) return false;
        if (reqmcreateuser != null ? !reqmcreateuser.equals(that.reqmcreateuser) : that.reqmcreateuser != null)
            return false;
        if (reqmcreatetime != null ? !reqmcreatetime.equals(that.reqmcreatetime) : that.reqmcreatetime != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (requisitionid ^ (requisitionid >>> 32));
        result = 31 * result + (int) (materialid ^ (materialid >>> 32));
        result = 31 * result + (reqmcustomercode != null ? reqmcustomercode.hashCode() : 0);
        result = 31 * result + (reqmmaterialname != null ? reqmmaterialname.hashCode() : 0);
        result = 31 * result + (reqmmaterialtype != null ? reqmmaterialtype.hashCode() : 0);
        result = 31 * result + (reqmsamplingparts != null ? reqmsamplingparts.hashCode() : 0);
        result = 31 * result + (reqmspecialrequirements != null ? reqmspecialrequirements.hashCode() : 0);
        result = 31 * result + (reqmremark != null ? reqmremark.hashCode() : 0);
        result = 31 * result + (reqmfirstv != null ? reqmfirstv.hashCode() : 0);
        result = 31 * result + (reqmsecondv != null ? reqmsecondv.hashCode() : 0);
        result = 31 * result + (reqmfirstn != null ? reqmfirstn.hashCode() : 0);
        result = 31 * result + (reqmfirstd != null ? reqmfirstd.hashCode() : 0);
        result = 31 * result + (reqmcreateuser != null ? reqmcreateuser.hashCode() : 0);
        result = 31 * result + (reqmcreatetime != null ? reqmcreatetime.hashCode() : 0);
        return result;
    }
}
