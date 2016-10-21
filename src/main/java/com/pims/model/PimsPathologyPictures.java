package com.pims.model;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by king on 2016/10/21.
 */
@Entity
@Table(name = "PIMS_PATHOLOGY_PICTURES")
public class PimsPathologyPictures {
    private long pictureid;
    private Long piccustomerid;
    private long picsampleid;
    private String picpathologycode;
    private long picpictureclass;
    private long picpictureno;
    private String picdescription;
    private long picpicturetype;
    private String picpicturename;
    private String picpicturesize;
    private Date picpicturetime;
    private String picpictureuser;
    private String picpictureip;
    private String picsavepath;
    private int picisupload;
    private Date picuploadtime;
    private String picuploaduser;
    private String picfirstv;
    private Long picfirstn;
    private Date picfirstd;
    private Date piccreatetime;
    private String piccreateuser;

    @Id
    @Column(name = "PICTUREID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="SEQUENCE SEQ_PICTUREID")
    @SequenceGenerator(name = "SEQUENCE SEQ_PICTUREID", sequenceName = "SEQUENCE SEQ_PICTUREID", allocationSize=1)
    public long getPictureid() {
        return pictureid;
    }

    public void setPictureid(long pictureid) {
        this.pictureid = pictureid;
    }

    @Basic
    @Column(name = "PICCUSTOMERID")
    public Long getPiccustomerid() {
        return piccustomerid;
    }

    public void setPiccustomerid(Long piccustomerid) {
        this.piccustomerid = piccustomerid;
    }

    @Basic
    @Column(name = "PICSAMPLEID")
    public long getPicsampleid() {
        return picsampleid;
    }

    public void setPicsampleid(long picsampleid) {
        this.picsampleid = picsampleid;
    }

    @Basic
    @Column(name = "PICPATHOLOGYCODE")
    public String getPicpathologycode() {
        return picpathologycode;
    }

    public void setPicpathologycode(String picpathologycode) {
        this.picpathologycode = picpathologycode;
    }

    @Basic
    @Column(name = "PICPICTURECLASS")
    public long getPicpictureclass() {
        return picpictureclass;
    }

    public void setPicpictureclass(long picpictureclass) {
        this.picpictureclass = picpictureclass;
    }

    @Basic
    @Column(name = "PICPICTURENO")
    public long getPicpictureno() {
        return picpictureno;
    }

    public void setPicpictureno(long picpictureno) {
        this.picpictureno = picpictureno;
    }

    @Basic
    @Column(name = "PICDESCRIPTION")
    public String getPicdescription() {
        return picdescription;
    }

    public void setPicdescription(String picdescription) {
        this.picdescription = picdescription;
    }

    @Basic
    @Column(name = "PICPICTURETYPE")
    public long getPicpicturetype() {
        return picpicturetype;
    }

    public void setPicpicturetype(long picpicturetype) {
        this.picpicturetype = picpicturetype;
    }

    @Basic
    @Column(name = "PICPICTURENAME")
    public String getPicpicturename() {
        return picpicturename;
    }

    public void setPicpicturename(String picpicturename) {
        this.picpicturename = picpicturename;
    }

    @Basic
    @Column(name = "PICPICTURESIZE")
    public String getPicpicturesize() {
        return picpicturesize;
    }

    public void setPicpicturesize(String picpicturesize) {
        this.picpicturesize = picpicturesize;
    }

    @Basic
    @Column(name = "PICPICTURETIME")
    public Date getPicpicturetime() {
        return picpicturetime;
    }

    public void setPicpicturetime(Date picpicturetime) {
        this.picpicturetime = picpicturetime;
    }

    @Basic
    @Column(name = "PICPICTUREUSER")
    public String getPicpictureuser() {
        return picpictureuser;
    }

    public void setPicpictureuser(String picpictureuser) {
        this.picpictureuser = picpictureuser;
    }

    @Basic
    @Column(name = "PICPICTUREIP")
    public String getPicpictureip() {
        return picpictureip;
    }

    public void setPicpictureip(String picpictureip) {
        this.picpictureip = picpictureip;
    }

    @Basic
    @Column(name = "PICSAVEPATH")
    public String getPicsavepath() {
        return picsavepath;
    }

    public void setPicsavepath(String picsavepath) {
        this.picsavepath = picsavepath;
    }

    @Basic
    @Column(name = "PICISUPLOAD")
    public int getPicisupload() {
        return picisupload;
    }

    public void setPicisupload(int picisupload) {
        this.picisupload = picisupload;
    }

    @Basic
    @Column(name = "PICUPLOADTIME")
    public Date getPicuploadtime() {
        return picuploadtime;
    }

    public void setPicuploadtime(Date picuploadtime) {
        this.picuploadtime = picuploadtime;
    }

    @Basic
    @Column(name = "PICUPLOADUSER")
    public String getPicuploaduser() {
        return picuploaduser;
    }

    public void setPicuploaduser(String picuploaduser) {
        this.picuploaduser = picuploaduser;
    }

    @Basic
    @Column(name = "PICFIRSTV")
    public String getPicfirstv() {
        return picfirstv;
    }

    public void setPicfirstv(String picfirstv) {
        this.picfirstv = picfirstv;
    }

    @Basic
    @Column(name = "PICFIRSTN")
    public Long getPicfirstn() {
        return picfirstn;
    }

    public void setPicfirstn(Long picfirstn) {
        this.picfirstn = picfirstn;
    }

    @Basic
    @Column(name = "PICFIRSTD")
    public Date getPicfirstd() {
        return picfirstd;
    }

    public void setPicfirstd(Date picfirstd) {
        this.picfirstd = picfirstd;
    }

    @Basic
    @Column(name = "PICCREATETIME")
    public Date getPiccreatetime() {
        return piccreatetime;
    }

    public void setPiccreatetime(Date piccreatetime) {
        this.piccreatetime = piccreatetime;
    }

    @Basic
    @Column(name = "PICCREATEUSER")
    public String getPiccreateuser() {
        return piccreateuser;
    }

    public void setPiccreateuser(String piccreateuser) {
        this.piccreateuser = piccreateuser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PimsPathologyPictures that = (PimsPathologyPictures) o;

        if (pictureid != that.pictureid) return false;
        if (picsampleid != that.picsampleid) return false;
        if (picpictureclass != that.picpictureclass) return false;
        if (picpictureno != that.picpictureno) return false;
        if (picpicturetype != that.picpicturetype) return false;
        if (piccustomerid != null ? !piccustomerid.equals(that.piccustomerid) : that.piccustomerid != null)
            return false;
        if (picpathologycode != null ? !picpathologycode.equals(that.picpathologycode) : that.picpathologycode != null)
            return false;
        if (picdescription != null ? !picdescription.equals(that.picdescription) : that.picdescription != null)
            return false;
        if (picpicturename != null ? !picpicturename.equals(that.picpicturename) : that.picpicturename != null)
            return false;
        if (picpicturesize != null ? !picpicturesize.equals(that.picpicturesize) : that.picpicturesize != null)
            return false;
        if (picpicturetime != null ? !picpicturetime.equals(that.picpicturetime) : that.picpicturetime != null)
            return false;
        if (picpictureuser != null ? !picpictureuser.equals(that.picpictureuser) : that.picpictureuser != null)
            return false;
        if (picpictureip != null ? !picpictureip.equals(that.picpictureip) : that.picpictureip != null) return false;
        if (picsavepath != null ? !picsavepath.equals(that.picsavepath) : that.picsavepath != null) return false;
        if (picisupload != that.picisupload ) return false;
        if (picuploadtime != null ? !picuploadtime.equals(that.picuploadtime) : that.picuploadtime != null)
            return false;
        if (picuploaduser != null ? !picuploaduser.equals(that.picuploaduser) : that.picuploaduser != null)
            return false;
        if (picfirstv != null ? !picfirstv.equals(that.picfirstv) : that.picfirstv != null) return false;
        if (picfirstn != null ? !picfirstn.equals(that.picfirstn) : that.picfirstn != null) return false;
        if (picfirstd != null ? !picfirstd.equals(that.picfirstd) : that.picfirstd != null) return false;
        if (piccreatetime != null ? !piccreatetime.equals(that.piccreatetime) : that.piccreatetime != null)
            return false;
        if (piccreateuser != null ? !piccreateuser.equals(that.piccreateuser) : that.piccreateuser != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (pictureid ^ (pictureid >>> 32));
        result = 31 * result + (piccustomerid != null ? piccustomerid.hashCode() : 0);
        result = 31 * result + (int) (picsampleid ^ (picsampleid >>> 32));
        result = 31 * result + (picpathologycode != null ? picpathologycode.hashCode() : 0);
        result = 31 * result + (int) (picpictureclass ^ (picpictureclass >>> 32));
        result = 31 * result + (int) (picpictureno ^ (picpictureno >>> 32));
        result = 31 * result + (picdescription != null ? picdescription.hashCode() : 0);
        result = 31 * result + (int) (picpicturetype ^ (picpicturetype >>> 32));
        result = 31 * result + (picpicturename != null ? picpicturename.hashCode() : 0);
        result = 31 * result + (picpicturesize != null ? picpicturesize.hashCode() : 0);
        result = 31 * result + (picpicturetime != null ? picpicturetime.hashCode() : 0);
        result = 31 * result + (picpictureuser != null ? picpictureuser.hashCode() : 0);
        result = 31 * result + (picpictureip != null ? picpictureip.hashCode() : 0);
        result = 31 * result + (picsavepath != null ? picsavepath.hashCode() : 0);
        result = 31 * result + (picisupload ^ (picisupload >>> 32));
        result = 31 * result + (picuploadtime != null ? picuploadtime.hashCode() : 0);
        result = 31 * result + (picuploaduser != null ? picuploaduser.hashCode() : 0);
        result = 31 * result + (picfirstv != null ? picfirstv.hashCode() : 0);
        result = 31 * result + (picfirstn != null ? picfirstn.hashCode() : 0);
        result = 31 * result + (picfirstd != null ? picfirstd.hashCode() : 0);
        result = 31 * result + (piccreatetime != null ? piccreatetime.hashCode() : 0);
        result = 31 * result + (piccreateuser != null ? piccreateuser.hashCode() : 0);
        return result;
    }
}
