package com.pims.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by king on 2016/10/28.
 */
@Entity
@Table(name = "PIMS_PATHOLOGY_RECEIVEMESSAGE")
public class PimsPathologyReceivemessage {
    private long receivemessageid;
    private long recmessageid;
    private String receiveruserid;
    private String receiverusername;
    private Date receivedate;
    private int receivests;
    private String recfirstv;
    private Long recfirstn;
    private Date recfirstd;
    private Date reccreatetime;
    private String reccreateuser;

    @Id
    @Column(name = "RECEIVEMESSAGEID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="SEQ_RECEIVEMESSAGEID")
    @SequenceGenerator(name = "SEQ_RECEIVEMESSAGEID", sequenceName = "SEQ_RECEIVEMESSAGEID", allocationSize=1)
    public long getReceivemessageid() {
        return receivemessageid;
    }

    public void setReceivemessageid(long receivemessageid) {
        this.receivemessageid = receivemessageid;
    }

    @Basic
    @Column(name = "RECMESSAGEID")
    public long getRecmessageid() {
        return recmessageid;
    }

    public void setRecmessageid(long recmessageid) {
        this.recmessageid = recmessageid;
    }

    @Basic
    @Column(name = "RECEIVERUSERID")
    public String getReceiveruserid() {
        return receiveruserid;
    }

    public void setReceiveruserid(String receiveruserid) {
        this.receiveruserid = receiveruserid;
    }

    @Basic
    @Column(name = "RECEIVERUSERNAME")
    public String getReceiverusername() {
        return receiverusername;
    }

    public void setReceiverusername(String receiverusername) {
        this.receiverusername = receiverusername;
    }

    @Basic
    @Column(name = "RECEIVEDATE")
    public Date getReceivedate() {
        return receivedate;
    }

    public void setReceivedate(Date receivedate) {
        this.receivedate = receivedate;
    }

    @Basic
    @Column(name = "RECEIVESTS")
    public int getReceivests() {
        return receivests;
    }

    public void setReceivests(int receivests) {
        this.receivests = receivests;
    }

    @Basic
    @Column(name = "RECFIRSTV")
    public String getRecfirstv() {
        return recfirstv;
    }

    public void setRecfirstv(String recfirstv) {
        this.recfirstv = recfirstv;
    }

    @Basic
    @Column(name = "RECFIRSTN")
    public Long getRecfirstn() {
        return recfirstn;
    }

    public void setRecfirstn(Long recfirstn) {
        this.recfirstn = recfirstn;
    }

    @Basic
    @Column(name = "RECFIRSTD")
    public Date getRecfirstd() {
        return recfirstd;
    }

    public void setRecfirstd(Date recfirstd) {
        this.recfirstd = recfirstd;
    }

    @Basic
    @Column(name = "RECCREATETIME")
    public Date getReccreatetime() {
        return reccreatetime;
    }

    public void setReccreatetime(Date reccreatetime) {
        this.reccreatetime = reccreatetime;
    }

    @Basic
    @Column(name = "RECCREATEUSER")
    public String getReccreateuser() {
        return reccreateuser;
    }

    public void setReccreateuser(String reccreateuser) {
        this.reccreateuser = reccreateuser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PimsPathologyReceivemessage that = (PimsPathologyReceivemessage) o;

        if (receivemessageid != that.receivemessageid) return false;
        if (recmessageid != that.recmessageid) return false;
        if (receiveruserid != null ? !receiveruserid.equals(that.receiveruserid) : that.receiveruserid != null)
            return false;
        if (receiverusername != null ? !receiverusername.equals(that.receiverusername) : that.receiverusername != null)
            return false;
        if (receivedate != null ? !receivedate.equals(that.receivedate) : that.receivedate != null) return false;
        if (receivests != that.receivests) return false;
        if (recfirstv != null ? !recfirstv.equals(that.recfirstv) : that.recfirstv != null) return false;
        if (recfirstn != null ? !recfirstn.equals(that.recfirstn) : that.recfirstn != null) return false;
        if (recfirstd != null ? !recfirstd.equals(that.recfirstd) : that.recfirstd != null) return false;
        if (reccreatetime != null ? !reccreatetime.equals(that.reccreatetime) : that.reccreatetime != null)
            return false;
        if (reccreateuser != null ? !reccreateuser.equals(that.reccreateuser) : that.reccreateuser != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (receivemessageid ^ (receivemessageid >>> 32));
        result = 31 * result + (int) (recmessageid ^ (recmessageid >>> 32));
        result = 31 * result + (receiveruserid != null ? receiveruserid.hashCode() : 0);
        result = 31 * result + (receiverusername != null ? receiverusername.hashCode() : 0);
        result = 31 * result + (receivedate != null ? receivedate.hashCode() : 0);
        result = 31 * result + (receivests ^ (receivests >>> 32));
        result = 31 * result + (recfirstv != null ? recfirstv.hashCode() : 0);
        result = 31 * result + (recfirstn != null ? recfirstn.hashCode() : 0);
        result = 31 * result + (recfirstd != null ? recfirstd.hashCode() : 0);
        result = 31 * result + (reccreatetime != null ? reccreatetime.hashCode() : 0);
        result = 31 * result + (reccreateuser != null ? reccreateuser.hashCode() : 0);
        return result;
    }
}
