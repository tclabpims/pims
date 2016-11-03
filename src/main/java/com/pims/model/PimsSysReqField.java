package com.pims.model;

import com.smart.model.BaseObject;

import javax.persistence.*;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by king on 2016/9/28.
 */
@Entity
@Table(name = "PIMS_SYS_REQ_FIELD")
public class PimsSysReqField extends BaseObject {
    private long fieldid;
    private String fieelementid;
    private String fieelementname;
    private String fieelementtype;
    private String fieshowlevel;
    private Long fiepelementid;
    private String fiedefaultvalue;
    private String fieshoworder;
    private Long fieuseflag;
    private String fiefisrtv;
    private String fiesecondv;
    private Long fiefisrtn;
    private String fieremark;
    private String fiecreateuser;
    private Date fiecreatetime;

    private String invokefunc;

    private String invokefuncbody;

    private String fieldcss;

    private List<PimsSysReqField> children = new ArrayList<>();

    @Transient
    public List<PimsSysReqField> getChildren() {
        return children;
    }

    public void setChildren(List<PimsSysReqField> children) {
        this.children = children;
    }

    @Basic
    @Column(name="FIELDCSS")
    public String getFieldcss() {
        return fieldcss;
    }

    public void setFieldcss(String fieldcss) {
        this.fieldcss = fieldcss;
    }

    @Basic
    @Column(name="INVOKEFUNC")
    public String getInvokefunc() {
        return invokefunc;
    }

    public void setInvokefunc(String invokefunc) {
        this.invokefunc = invokefunc;
    }

    @Basic
    @Column(name="INVOKEFUNCBODY")
    public String getInvokefuncbody() {
        return invokefuncbody;
    }

    public void setInvokefuncbody(String invokefuncbody) {
        this.invokefuncbody = invokefuncbody;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_REQFIELD")
    @SequenceGenerator(name = "SEQ_REQFIELD", sequenceName = "seq_field", allocationSize = 1)
    public long getFieldid() {
        return fieldid;
    }

    public void setFieldid(long fieldid) {
        this.fieldid = fieldid;
    }

    @Basic
    @Column(name = "FIEELEMENTID")
    public String getFieelementid() {
        return fieelementid;
    }

    public void setFieelementid(String fieelementid) {
        this.fieelementid = fieelementid;
    }

    @Basic
    @Column(name = "FIEELEMENTNAME")
    public String getFieelementname() {
        return fieelementname;
    }

    public void setFieelementname(String fieelementname) {
        this.fieelementname = fieelementname;
    }

    @Basic
    @Column(name = "FIEELEMENTTYPE")
    public String getFieelementtype() {
        return fieelementtype;
    }

    public void setFieelementtype(String fieelementtype) {
        this.fieelementtype = fieelementtype;
    }

    @Basic
    @Column(name = "FIESHOWLEVEL")
    public String getFieshowlevel() {
        return fieshowlevel;
    }

    public void setFieshowlevel(String fieshowlevel) {
        this.fieshowlevel = fieshowlevel;
    }

    @Basic
    @Column(name = "FIEPELEMENTID")
    public Long getFiepelementid() {
        return fiepelementid;
    }

    public void setFiepelementid(Long fiepelementid) {
        this.fiepelementid = fiepelementid;
    }

    @Basic
    @Column(name = "FIEDEFAULTVALUE")
    public String getFiedefaultvalue() {
        return fiedefaultvalue;
    }

    public void setFiedefaultvalue(String fiedefaultvalue) {
        this.fiedefaultvalue = fiedefaultvalue;
    }

    @Basic
    @Column(name = "FIESHOWORDER")
    public String getFieshoworder() {
        return fieshoworder;
    }

    public void setFieshoworder(String fieshoworder) {
        this.fieshoworder = fieshoworder;
    }

    @Basic
    @Column(name = "FIEUSEFLAG")
    public Long getFieuseflag() {
        return fieuseflag;
    }

    public void setFieuseflag(Long fieuseflag) {
        this.fieuseflag = fieuseflag;
    }

    @Basic
    @Column(name = "FIEFISRTV")
    public String getFiefisrtv() {
        return fiefisrtv;
    }

    public void setFiefisrtv(String fiefisrtv) {
        this.fiefisrtv = fiefisrtv;
    }

    @Basic
    @Column(name = "FIESECONDV")
    public String getFiesecondv() {
        return fiesecondv;
    }

    public void setFiesecondv(String fiesecondv) {
        this.fiesecondv = fiesecondv;
    }

    @Basic
    @Column(name = "FIEFISRTN")
    public Long getFiefisrtn() {
        return fiefisrtn;
    }

    public void setFiefisrtn(Long fiefisrtn) {
        this.fiefisrtn = fiefisrtn;
    }

    @Basic
    @Column(name = "FIEREMARK")
    public String getFieremark() {
        return fieremark;
    }

    public void setFieremark(String fieremark) {
        this.fieremark = fieremark;
    }

    @Basic
    @Column(name = "FIECREATEUSER")
    public String getFiecreateuser() {
        return fiecreateuser;
    }

    public void setFiecreateuser(String fiecreateuser) {
        this.fiecreateuser = fiecreateuser;
    }

    @Basic
    @Column(name = "FIECREATETIME")
    public Date getFiecreatetime() {
        return fiecreatetime;
    }

    public void setFiecreatetime(Date fiecreatetime) {
        this.fiecreatetime = fiecreatetime;
    }

    /**
     * Returns a multi-line String with key=value pairs.
     *
     * @return a String representation of this class.
     */
    @Override
    public String toString() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PimsSysReqField that = (PimsSysReqField) o;

        if (fieldid != that.fieldid) return false;
        if (fieelementid != null ? !fieelementid.equals(that.fieelementid) : that.fieelementid != null) return false;
        if (fieelementname != null ? !fieelementname.equals(that.fieelementname) : that.fieelementname != null)
            return false;
        if (fieelementtype != null ? !fieelementtype.equals(that.fieelementtype) : that.fieelementtype != null)
            return false;
        if (fieshowlevel != null ? !fieshowlevel.equals(that.fieshowlevel) : that.fieshowlevel != null) return false;
        if (fiepelementid != null ? !fiepelementid.equals(that.fiepelementid) : that.fiepelementid != null)
            return false;
        if (fiedefaultvalue != null ? !fiedefaultvalue.equals(that.fiedefaultvalue) : that.fiedefaultvalue != null)
            return false;
        if (fieshoworder != null ? !fieshoworder.equals(that.fieshoworder) : that.fieshoworder != null) return false;
        if (fieuseflag != null ? !fieuseflag.equals(that.fieuseflag) : that.fieuseflag != null) return false;
        if (fiefisrtv != null ? !fiefisrtv.equals(that.fiefisrtv) : that.fiefisrtv != null) return false;
        if (fiesecondv != null ? !fiesecondv.equals(that.fiesecondv) : that.fiesecondv != null) return false;
        if (fiefisrtn != null ? !fiefisrtn.equals(that.fiefisrtn) : that.fiefisrtn != null) return false;
        if (fieremark != null ? !fieremark.equals(that.fieremark) : that.fieremark != null) return false;
        if (fiecreateuser != null ? !fiecreateuser.equals(that.fiecreateuser) : that.fiecreateuser != null)
            return false;
        if (fiecreatetime != null ? !fiecreatetime.equals(that.fiecreatetime) : that.fiecreatetime != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (fieldid ^ (fieldid >>> 32));
        result = 31 * result + (fieelementid != null ? fieelementid.hashCode() : 0);
        result = 31 * result + (fieelementname != null ? fieelementname.hashCode() : 0);
        result = 31 * result + (fieelementtype != null ? fieelementtype.hashCode() : 0);
        result = 31 * result + (fieshowlevel != null ? fieshowlevel.hashCode() : 0);
        result = 31 * result + (fiepelementid != null ? fiepelementid.hashCode() : 0);
        result = 31 * result + (fiedefaultvalue != null ? fiedefaultvalue.hashCode() : 0);
        result = 31 * result + (fieshoworder != null ? fieshoworder.hashCode() : 0);
        result = 31 * result + (fieuseflag != null ? fieuseflag.hashCode() : 0);
        result = 31 * result + (fiefisrtv != null ? fiefisrtv.hashCode() : 0);
        result = 31 * result + (fiesecondv != null ? fiesecondv.hashCode() : 0);
        result = 31 * result + (fiefisrtn != null ? fiefisrtn.hashCode() : 0);
        result = 31 * result + (fieremark != null ? fieremark.hashCode() : 0);
        result = 31 * result + (fiecreateuser != null ? fiecreateuser.hashCode() : 0);
        result = 31 * result + (fiecreatetime != null ? fiecreatetime.hashCode() : 0);
        return result;
    }
}
