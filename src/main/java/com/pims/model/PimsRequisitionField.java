package com.pims.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by king on 2016/10/21.
 */
@Entity
@Table(name = "PIMS_REQUISITION_FIELD")
public class PimsRequisitionField {
    private long requisitionid;
    private long fieldid;
    private long reqfcustomerid;
    private String reqfelementid;
    private String reqfelementname;
    private String reqfelementtype;
    private String reqfshowlevel;
    private String reqfpelementid;
    private String reqfdefaultvalue;
    private String reqfsort;
    private String reqfvalue;
    private String reqfremark;
    private String reqffirstv;
    private String reqfsecondv;
    private Long reqffirstn;
    private String reqfcreateuser;
    private Date reqfcreatetime;
    private long reqfid;

    @Basic
    @Column(name = "REQUISITIONID")
    public long getRequisitionid() {
        return requisitionid;
    }

    public void setRequisitionid(long requisitionid) {
        this.requisitionid = requisitionid;
    }

    @Basic
    @Column(name = "FIELDID")
    public long getFieldid() {
        return fieldid;
    }

    public void setFieldid(long fieldid) {
        this.fieldid = fieldid;
    }

    @Basic
    @Column(name = "REQFCUSTOMERID")
    public long getReqfcustomerid() {
        return reqfcustomerid;
    }

    public void setReqfcustomerid(long reqfcustomerid) {
        this.reqfcustomerid = reqfcustomerid;
    }

    @Basic
    @Column(name = "REQFELEMENTID")
    public String getReqfelementid() {
        return reqfelementid;
    }

    public void setReqfelementid(String reqfelementid) {
        this.reqfelementid = reqfelementid;
    }

    @Basic
    @Column(name = "REQFELEMENTNAME")
    public String getReqfelementname() {
        return reqfelementname;
    }

    public void setReqfelementname(String reqfelementname) {
        this.reqfelementname = reqfelementname;
    }

    @Basic
    @Column(name = "REQFELEMENTTYPE")
    public String getReqfelementtype() {
        return reqfelementtype;
    }

    public void setReqfelementtype(String reqfelementtype) {
        this.reqfelementtype = reqfelementtype;
    }

    @Basic
    @Column(name = "REQFSHOWLEVEL")
    public String getReqfshowlevel() {
        return reqfshowlevel;
    }

    public void setReqfshowlevel(String reqfshowlevel) {
        this.reqfshowlevel = reqfshowlevel;
    }

    @Basic
    @Column(name = "REQFPELEMENTID")
    public String getReqfpelementid() {
        return reqfpelementid;
    }

    public void setReqfpelementid(String reqfpelementid) {
        this.reqfpelementid = reqfpelementid;
    }

    @Basic
    @Column(name = "REQFDEFAULTVALUE")
    public String getReqfdefaultvalue() {
        return reqfdefaultvalue;
    }

    public void setReqfdefaultvalue(String reqfdefaultvalue) {
        this.reqfdefaultvalue = reqfdefaultvalue;
    }

    @Basic
    @Column(name = "REQFSORT")
    public String getReqfsort() {
        return reqfsort;
    }

    public void setReqfsort(String reqfsort) {
        this.reqfsort = reqfsort;
    }

    @Basic
    @Column(name = "REQFVALUE")
    public String getReqfvalue() {
        return reqfvalue;
    }

    public void setReqfvalue(String reqfvalue) {
        this.reqfvalue = reqfvalue;
    }

    @Basic
    @Column(name = "REQFREMARK")
    public String getReqfremark() {
        return reqfremark;
    }

    public void setReqfremark(String reqfremark) {
        this.reqfremark = reqfremark;
    }

    @Basic
    @Column(name = "REQFFIRSTV")
    public String getReqffirstv() {
        return reqffirstv;
    }

    public void setReqffirstv(String reqffirstv) {
        this.reqffirstv = reqffirstv;
    }

    @Basic
    @Column(name = "REQFSECONDV")
    public String getReqfsecondv() {
        return reqfsecondv;
    }

    public void setReqfsecondv(String reqfsecondv) {
        this.reqfsecondv = reqfsecondv;
    }

    @Basic
    @Column(name = "REQFFIRSTN")
    public Long getReqffirstn() {
        return reqffirstn;
    }

    public void setReqffirstn(Long reqffirstn) {
        this.reqffirstn = reqffirstn;
    }

    @Basic
    @Column(name = "REQFCREATEUSER")
    public String getReqfcreateuser() {
        return reqfcreateuser;
    }

    public void setReqfcreateuser(String reqfcreateuser) {
        this.reqfcreateuser = reqfcreateuser;
    }

    @Basic
    @Column(name = "REQFCREATETIME")
    public Date getReqfcreatetime() {
        return reqfcreatetime;
    }

    public void setReqfcreatetime(Date reqfcreatetime) {
        this.reqfcreatetime = reqfcreatetime;
    }

    @Id
    @Column(name = "REQFID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="SEQ_FIELD")
    @SequenceGenerator(name = "SEQ_FIELD", sequenceName = "SEQ_FIELD", allocationSize=1)
    public long getReqfid() {
        return reqfid;
    }

    public void setReqfid(long reqfid) {
        this.reqfid = reqfid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PimsRequisitionField that = (PimsRequisitionField) o;

        if (requisitionid != that.requisitionid) return false;
        if (fieldid != that.fieldid) return false;
        if (reqfcustomerid != that.reqfcustomerid) return false;
        if (reqfid != that.reqfid) return false;
        if (reqfelementid != null ? !reqfelementid.equals(that.reqfelementid) : that.reqfelementid != null)
            return false;
        if (reqfelementname != null ? !reqfelementname.equals(that.reqfelementname) : that.reqfelementname != null)
            return false;
        if (reqfelementtype != null ? !reqfelementtype.equals(that.reqfelementtype) : that.reqfelementtype != null)
            return false;
        if (reqfshowlevel != null ? !reqfshowlevel.equals(that.reqfshowlevel) : that.reqfshowlevel != null)
            return false;
        if (reqfpelementid != null ? !reqfpelementid.equals(that.reqfpelementid) : that.reqfpelementid != null)
            return false;
        if (reqfdefaultvalue != null ? !reqfdefaultvalue.equals(that.reqfdefaultvalue) : that.reqfdefaultvalue != null)
            return false;
        if (reqfsort != null ? !reqfsort.equals(that.reqfsort) : that.reqfsort != null) return false;
        if (reqfvalue != null ? !reqfvalue.equals(that.reqfvalue) : that.reqfvalue != null) return false;
        if (reqfremark != null ? !reqfremark.equals(that.reqfremark) : that.reqfremark != null) return false;
        if (reqffirstv != null ? !reqffirstv.equals(that.reqffirstv) : that.reqffirstv != null) return false;
        if (reqfsecondv != null ? !reqfsecondv.equals(that.reqfsecondv) : that.reqfsecondv != null) return false;
        if (reqffirstn != null ? !reqffirstn.equals(that.reqffirstn) : that.reqffirstn != null) return false;
        if (reqfcreateuser != null ? !reqfcreateuser.equals(that.reqfcreateuser) : that.reqfcreateuser != null)
            return false;
        if (reqfcreatetime != null ? !reqfcreatetime.equals(that.reqfcreatetime) : that.reqfcreatetime != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (requisitionid ^ (requisitionid >>> 32));
        result = 31 * result + (int) (fieldid ^ (fieldid >>> 32));
        result = 31 * result + (int) (reqfcustomerid ^ (reqfcustomerid >>> 32));
        result = 31 * result + (reqfelementid != null ? reqfelementid.hashCode() : 0);
        result = 31 * result + (reqfelementname != null ? reqfelementname.hashCode() : 0);
        result = 31 * result + (reqfelementtype != null ? reqfelementtype.hashCode() : 0);
        result = 31 * result + (reqfshowlevel != null ? reqfshowlevel.hashCode() : 0);
        result = 31 * result + (reqfpelementid != null ? reqfpelementid.hashCode() : 0);
        result = 31 * result + (reqfdefaultvalue != null ? reqfdefaultvalue.hashCode() : 0);
        result = 31 * result + (reqfsort != null ? reqfsort.hashCode() : 0);
        result = 31 * result + (reqfvalue != null ? reqfvalue.hashCode() : 0);
        result = 31 * result + (reqfremark != null ? reqfremark.hashCode() : 0);
        result = 31 * result + (reqffirstv != null ? reqffirstv.hashCode() : 0);
        result = 31 * result + (reqfsecondv != null ? reqfsecondv.hashCode() : 0);
        result = 31 * result + (reqffirstn != null ? reqffirstn.hashCode() : 0);
        result = 31 * result + (reqfcreateuser != null ? reqfcreateuser.hashCode() : 0);
        result = 31 * result + (reqfcreatetime != null ? reqfcreatetime.hashCode() : 0);
        result = 31 * result + (int) (reqfid ^ (reqfid >>> 32));
        return result;
    }
}
