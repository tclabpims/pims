package com.pims.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by king on 2016/9/28.
 */
@Entity
@Table(name = "PIMS_PATHOLOGY_SLIDE")
public class PimsPathologySlide {
    private long slideid;
    private long slisampleid;
    private String slicustomercode;
    private String slislidebarcode;
    private String slipathologycode;
    private Long slislidetype;
    private Long slislidesource;
    private Long sliuseflag;
    private String slislideno;
    private String slislidecode;
    private Long sliparaffinid;
    private Long sliparaffinno;
    private String sliparaffincode;
    private String sliparaffinname;
    private String slisamplingparts;
    private Long slitestitemid;
    private String slitestitemname;
    private String slilabelcontent;
    private Long sliifprint;
    private Date sliprinttime;
    private String sliprintuser;
    private String sliprinttimes;
    private String slistockin;
    private String slistockinuser;
    private Date slistockintime;
    private String slifirstv;
    private String slisecondv;
    private Long slifirstn;
    private Date slifirstd;
    private String slicreateuser;
    private Date slicreatetime;

    @Id
    @Column(name = "SLIDEID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="SEQ_SLIDEID")
    @SequenceGenerator(name = "SEQ_SLIDEID", sequenceName = "SEQ_SLIDEID", allocationSize=1)
    public long getSlideid() {
        return slideid;
    }

    public void setSlideid(long slideid) {
        this.slideid = slideid;
    }

    @Basic
    @Column(name = "SLISAMPLEID")
    public long getSlisampleid() {
        return slisampleid;
    }

    public void setSlisampleid(long slisampleid) {
        this.slisampleid = slisampleid;
    }

    @Basic
    @Column(name = "SLICUSTOMERCODE")
    public String getSlicustomercode() {
        return slicustomercode;
    }

    public void setSlicustomercode(String slicustomercode) {
        this.slicustomercode = slicustomercode;
    }

    @Basic
    @Column(name = "SLISLIDEBARCODE")
    public String getSlislidebarcode() {
        return slislidebarcode;
    }

    public void setSlislidebarcode(String slislidebarcode) {
        this.slislidebarcode = slislidebarcode;
    }

    @Basic
    @Column(name = "SLIPATHOLOGYCODE")
    public String getSlipathologycode() {
        return slipathologycode;
    }

    public void setSlipathologycode(String slipathologycode) {
        this.slipathologycode = slipathologycode;
    }

    @Basic
    @Column(name = "SLISLIDETYPE")
    public Long getSlislidetype() {
        return slislidetype;
    }

    public void setSlislidetype(Long slislidetype) {
        this.slislidetype = slislidetype;
    }

    @Basic
    @Column(name = "SLISLIDESOURCE")
    public Long getSlislidesource() {
        return slislidesource;
    }

    public void setSlislidesource(Long slislidesource) {
        this.slislidesource = slislidesource;
    }

    @Basic
    @Column(name = "SLIUSEFLAG")
    public Long getSliuseflag() {
        return sliuseflag;
    }

    public void setSliuseflag(Long sliuseflag) {
        this.sliuseflag = sliuseflag;
    }

    @Basic
    @Column(name = "SLISLIDENO")
    public String getSlislideno() {
        return slislideno;
    }

    public void setSlislideno(String slislideno) {
        this.slislideno = slislideno;
    }

    @Basic
    @Column(name = "SLISLIDECODE")
    public String getSlislidecode() {
        return slislidecode;
    }

    public void setSlislidecode(String slislidecode) {
        this.slislidecode = slislidecode;
    }

    @Basic
    @Column(name = "SLIPARAFFINID")
    public Long getSliparaffinid() {
        return sliparaffinid;
    }

    public void setSliparaffinid(Long sliparaffinid) {
        this.sliparaffinid = sliparaffinid;
    }

    @Basic
    @Column(name = "SLIPARAFFINNO")
    public Long getSliparaffinno() {
        return sliparaffinno;
    }

    public void setSliparaffinno(Long sliparaffinno) {
        this.sliparaffinno = sliparaffinno;
    }

    @Basic
    @Column(name = "SLIPARAFFINCODE")
    public String getSliparaffincode() {
        return sliparaffincode;
    }

    public void setSliparaffincode(String sliparaffincode) {
        this.sliparaffincode = sliparaffincode;
    }

    @Basic
    @Column(name = "SLIPARAFFINNAME")
    public String getSliparaffinname() {
        return sliparaffinname;
    }

    public void setSliparaffinname(String sliparaffinname) {
        this.sliparaffinname = sliparaffinname;
    }

    @Basic
    @Column(name = "SLISAMPLINGPARTS")
    public String getSlisamplingparts() {
        return slisamplingparts;
    }

    public void setSlisamplingparts(String slisamplingparts) {
        this.slisamplingparts = slisamplingparts;
    }

    @Basic
    @Column(name = "SLITESTITEMID")
    public Long getSlitestitemid() {
        return slitestitemid;
    }

    public void setSlitestitemid(Long slitestitemid) {
        this.slitestitemid = slitestitemid;
    }

    @Basic
    @Column(name = "SLITESTITEMNAME")
    public String getSlitestitemname() {
        return slitestitemname;
    }

    public void setSlitestitemname(String slitestitemname) {
        this.slitestitemname = slitestitemname;
    }

    @Basic
    @Column(name = "SLILABELCONTENT")
    public String getSlilabelcontent() {
        return slilabelcontent;
    }

    public void setSlilabelcontent(String slilabelcontent) {
        this.slilabelcontent = slilabelcontent;
    }

    @Basic
    @Column(name = "SLIIFPRINT")
    public Long getSliifprint() {
        return sliifprint;
    }

    public void setSliifprint(Long sliifprint) {
        this.sliifprint = sliifprint;
    }

    @Basic
    @Column(name = "SLIPRINTTIME")
    public Date getSliprinttime() {
        return sliprinttime;
    }

    public void setSliprinttime(Date sliprinttime) {
        this.sliprinttime = sliprinttime;
    }

    @Basic
    @Column(name = "SLIPRINTUSER")
    public String getSliprintuser() {
        return sliprintuser;
    }

    public void setSliprintuser(String sliprintuser) {
        this.sliprintuser = sliprintuser;
    }

    @Basic
    @Column(name = "SLIPRINTTIMES")
    public String getSliprinttimes() {
        return sliprinttimes;
    }

    public void setSliprinttimes(String sliprinttimes) {
        this.sliprinttimes = sliprinttimes;
    }

    @Basic
    @Column(name = "SLISTOCKIN")
    public String getSlistockin() {
        return slistockin;
    }

    public void setSlistockin(String slistockin) {
        this.slistockin = slistockin;
    }

    @Basic
    @Column(name = "SLISTOCKINUSER")
    public String getSlistockinuser() {
        return slistockinuser;
    }

    public void setSlistockinuser(String slistockinuser) {
        this.slistockinuser = slistockinuser;
    }

    @Basic
    @Column(name = "SLISTOCKINTIME")
    public Date getSlistockintime() {
        return slistockintime;
    }

    public void setSlistockintime(Date slistockintime) {
        this.slistockintime = slistockintime;
    }

    @Basic
    @Column(name = "SLIFIRSTV")
    public String getSlifirstv() {
        return slifirstv;
    }

    public void setSlifirstv(String slifirstv) {
        this.slifirstv = slifirstv;
    }

    @Basic
    @Column(name = "SLISECONDV")
    public String getSlisecondv() {
        return slisecondv;
    }

    public void setSlisecondv(String slisecondv) {
        this.slisecondv = slisecondv;
    }

    @Basic
    @Column(name = "SLIFIRSTN")
    public Long getSlifirstn() {
        return slifirstn;
    }

    public void setSlifirstn(Long slifirstn) {
        this.slifirstn = slifirstn;
    }

    @Basic
    @Column(name = "SLIFIRSTD")
    public Date getSlifirstd() {
        return slifirstd;
    }

    public void setSlifirstd(Date slifirstd) {
        this.slifirstd = slifirstd;
    }

    @Basic
    @Column(name = "SLICREATEUSER")
    public String getSlicreateuser() {
        return slicreateuser;
    }

    public void setSlicreateuser(String slicreateuser) {
        this.slicreateuser = slicreateuser;
    }

    @Basic
    @Column(name = "SLICREATETIME")
    public Date getSlicreatetime() {
        return slicreatetime;
    }

    public void setSlicreatetime(Date slicreatetime) {
        this.slicreatetime = slicreatetime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PimsPathologySlide that = (PimsPathologySlide) o;

        if (slideid != that.slideid) return false;
        if (slisampleid != that.slisampleid) return false;
        if (slicustomercode != null ? !slicustomercode.equals(that.slicustomercode) : that.slicustomercode != null)
            return false;
        if (slislidebarcode != null ? !slislidebarcode.equals(that.slislidebarcode) : that.slislidebarcode != null)
            return false;
        if (slipathologycode != null ? !slipathologycode.equals(that.slipathologycode) : that.slipathologycode != null)
            return false;
        if (slislidetype != null ? !slislidetype.equals(that.slislidetype) : that.slislidetype != null) return false;
        if (slislidesource != null ? !slislidesource.equals(that.slislidesource) : that.slislidesource != null)
            return false;
        if (sliuseflag != null ? !sliuseflag.equals(that.sliuseflag) : that.sliuseflag != null) return false;
        if (slislideno != null ? !slislideno.equals(that.slislideno) : that.slislideno != null) return false;
        if (slislidecode != null ? !slislidecode.equals(that.slislidecode) : that.slislidecode != null) return false;
        if (sliparaffinid != null ? !sliparaffinid.equals(that.sliparaffinid) : that.sliparaffinid != null)
            return false;
        if (sliparaffinno != null ? !sliparaffinno.equals(that.sliparaffinno) : that.sliparaffinno != null)
            return false;
        if (sliparaffincode != null ? !sliparaffincode.equals(that.sliparaffincode) : that.sliparaffincode != null)
            return false;
        if (sliparaffinname != null ? !sliparaffinname.equals(that.sliparaffinname) : that.sliparaffinname != null)
            return false;
        if (slisamplingparts != null ? !slisamplingparts.equals(that.slisamplingparts) : that.slisamplingparts != null)
            return false;
        if (slitestitemid != null ? !slitestitemid.equals(that.slitestitemid) : that.slitestitemid != null)
            return false;
        if (slitestitemname != null ? !slitestitemname.equals(that.slitestitemname) : that.slitestitemname != null)
            return false;
        if (slilabelcontent != null ? !slilabelcontent.equals(that.slilabelcontent) : that.slilabelcontent != null)
            return false;
        if (sliifprint != null ? !sliifprint.equals(that.sliifprint) : that.sliifprint != null) return false;
        if (sliprinttime != null ? !sliprinttime.equals(that.sliprinttime) : that.sliprinttime != null) return false;
        if (sliprintuser != null ? !sliprintuser.equals(that.sliprintuser) : that.sliprintuser != null) return false;
        if (sliprinttimes != null ? !sliprinttimes.equals(that.sliprinttimes) : that.sliprinttimes != null)
            return false;
        if (slistockin != null ? !slistockin.equals(that.slistockin) : that.slistockin != null) return false;
        if (slistockinuser != null ? !slistockinuser.equals(that.slistockinuser) : that.slistockinuser != null)
            return false;
        if (slistockintime != null ? !slistockintime.equals(that.slistockintime) : that.slistockintime != null)
            return false;
        if (slifirstv != null ? !slifirstv.equals(that.slifirstv) : that.slifirstv != null) return false;
        if (slisecondv != null ? !slisecondv.equals(that.slisecondv) : that.slisecondv != null) return false;
        if (slifirstn != null ? !slifirstn.equals(that.slifirstn) : that.slifirstn != null) return false;
        if (slifirstd != null ? !slifirstd.equals(that.slifirstd) : that.slifirstd != null) return false;
        if (slicreateuser != null ? !slicreateuser.equals(that.slicreateuser) : that.slicreateuser != null)
            return false;
        if (slicreatetime != null ? !slicreatetime.equals(that.slicreatetime) : that.slicreatetime != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (slideid ^ (slideid >>> 32));
        result = 31 * result + (int) (slisampleid ^ (slisampleid >>> 32));
        result = 31 * result + (slicustomercode != null ? slicustomercode.hashCode() : 0);
        result = 31 * result + (slislidebarcode != null ? slislidebarcode.hashCode() : 0);
        result = 31 * result + (slipathologycode != null ? slipathologycode.hashCode() : 0);
        result = 31 * result + (slislidetype != null ? slislidetype.hashCode() : 0);
        result = 31 * result + (slislidesource != null ? slislidesource.hashCode() : 0);
        result = 31 * result + (sliuseflag != null ? sliuseflag.hashCode() : 0);
        result = 31 * result + (slislideno != null ? slislideno.hashCode() : 0);
        result = 31 * result + (slislidecode != null ? slislidecode.hashCode() : 0);
        result = 31 * result + (sliparaffinid != null ? sliparaffinid.hashCode() : 0);
        result = 31 * result + (sliparaffinno != null ? sliparaffinno.hashCode() : 0);
        result = 31 * result + (sliparaffincode != null ? sliparaffincode.hashCode() : 0);
        result = 31 * result + (sliparaffinname != null ? sliparaffinname.hashCode() : 0);
        result = 31 * result + (slisamplingparts != null ? slisamplingparts.hashCode() : 0);
        result = 31 * result + (slitestitemid != null ? slitestitemid.hashCode() : 0);
        result = 31 * result + (slitestitemname != null ? slitestitemname.hashCode() : 0);
        result = 31 * result + (slilabelcontent != null ? slilabelcontent.hashCode() : 0);
        result = 31 * result + (sliifprint != null ? sliifprint.hashCode() : 0);
        result = 31 * result + (sliprinttime != null ? sliprinttime.hashCode() : 0);
        result = 31 * result + (sliprintuser != null ? sliprintuser.hashCode() : 0);
        result = 31 * result + (sliprinttimes != null ? sliprinttimes.hashCode() : 0);
        result = 31 * result + (slistockin != null ? slistockin.hashCode() : 0);
        result = 31 * result + (slistockinuser != null ? slistockinuser.hashCode() : 0);
        result = 31 * result + (slistockintime != null ? slistockintime.hashCode() : 0);
        result = 31 * result + (slifirstv != null ? slifirstv.hashCode() : 0);
        result = 31 * result + (slisecondv != null ? slisecondv.hashCode() : 0);
        result = 31 * result + (slifirstn != null ? slifirstn.hashCode() : 0);
        result = 31 * result + (slifirstd != null ? slifirstd.hashCode() : 0);
        result = 31 * result + (slicreateuser != null ? slicreateuser.hashCode() : 0);
        result = 31 * result + (slicreatetime != null ? slicreatetime.hashCode() : 0);
        return result;
    }
}
