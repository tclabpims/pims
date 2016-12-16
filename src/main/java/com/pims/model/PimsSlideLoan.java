package com.pims.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by zp on 2016/11/15.
 */
@Entity
@Table(name="PIMS_SLIDE_LOAN")
public class PimsSlideLoan {
    private String sliid;
    private String slipathologyid;
    private String pathologyid;
    private String slipatientname;
    private String slipatientage;
    private String slipatientsex;
    private String  slicustomerid;
    private Date slimadetime;
    private Date sliintime;
    private String slicurrent;
    private Date sliouttime;
    private long slino;
    private String slicustomername;
    public PimsSlideLoan() {
        super();
    }



    @Id
    @Column(name = "SLINO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="employ_autoinc")
    @SequenceGenerator(name = "employ_autoinc", sequenceName = "employ_autoinc", allocationSize=1)
    public long getSlino() {
        return slino;
    }

    public void setSlino(long slino) {
        this.slino = slino;
    }

    @Basic
    @Column(name = "SLIID")
    public String getSliid() {
        return sliid;
    }

    public void setSliid(String sliid) {
        this.sliid = sliid;
    }

    @Basic
    @Column(name = "SLIPATHOLOGYID")
    public String getSlipathologyid() {
        return slipathologyid;
    }

    public void setSlipathologyid(String slipathologyid) {
        this.slipathologyid = slipathologyid;
    }

    @Basic
    @Column(name = "PATHOLOGYID")
    public String getPathologyid() {
        return pathologyid;
    }

    public void setPathologyid(String pathologyid) {
        this.pathologyid = pathologyid;
    }

    @Basic
    @Column(name = "SLIPATIENTNAME")
    public String getSlipatientname() {
        return slipatientname;
    }

    public void setSlipatientname(String slipatientname) {
        this.slipatientname = slipatientname;
    }

    @Basic
    @Column(name = "SLIPATIENTAGE")
    public String getSlipatientage() {
        return slipatientage;
    }

    public void setSlipatientage(String slipatientage) {
        this.slipatientage = slipatientage;
    }

    @Basic
    @Column(name = "SLIPATIENTSEX")
    public String getSlipatientsex() {
        return slipatientsex;
    }

    public void setSlipatientsex(String slipatientsex) {
        this.slipatientsex = slipatientsex;
    }

    @Basic
    @Column(name = "SLICUSTOMERID")
    public String getSlicustomerid() {
        return slicustomerid;
    }

    public void setSlicustomerid(String slicustomerid) {
        this.slicustomerid = slicustomerid;
    }

    @Basic
    @Column(name = "SLIMADETIME")
    public Date getSlimadetime() {
        return slimadetime;
    }

    public void setSlimadetime(Date slimadetime) {
        this.slimadetime = slimadetime;
    }

    @Basic
    @Column(name = "SLIINTIME")
    public Date getSliintime() {
        return sliintime;
    }

    public void setSliintime(Date sliintime) {
        this.sliintime = sliintime;
    }

    @Basic
    @Column(name = "SLICURRENT")
    public String getSlicurrent() {
        return slicurrent;
    }

    public void setSlicurrent(String slicurrent) {
        this.slicurrent = slicurrent;
    }

    @Basic
    @Column(name = "SLIOUTTIME")
    public Date getSliouttime() {
        return sliouttime;
    }

    public void setSliouttime(Date sliouttime) {
        this.sliouttime = sliouttime;
    }

    @Basic
    @Column(name = "SLICUSTOMERNAME")
    public String getSlicustomername() {
        return slicustomername;
    }

    public void setSlicustomername(String slicustomername) {
        this.slicustomername = slicustomername;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PimsSlideLoan that = (PimsSlideLoan) o;

        if (slino != that.slino) return false;
        if (sliid != null ? !sliid.equals(that.sliid) : that.sliid != null) return false;
        if (slipathologyid != null ? !slipathologyid.equals(that.slipathologyid) : that.slipathologyid != null)
            return false;
        if (pathologyid != null ? !pathologyid.equals(that.pathologyid) : that.pathologyid != null) return false;
        if (slipatientname != null ? !slipatientname.equals(that.slipatientname) : that.slipatientname != null)
            return false;
        if (slipatientage != null ? !slipatientage.equals(that.slipatientage) : that.slipatientage != null)
            return false;
        if (slipatientsex != null ? !slipatientsex.equals(that.slipatientsex) : that.slipatientsex != null)
            return false;
        if (slicustomerid != null ? !slicustomerid.equals(that.slicustomerid) : that.slicustomerid != null)
            return false;
        if (slimadetime != null ? !slimadetime.equals(that.slimadetime) : that.slimadetime != null) return false;
        if (sliintime != null ? !sliintime.equals(that.sliintime) : that.sliintime != null) return false;
        if (slicurrent != null ? !slicurrent.equals(that.slicurrent) : that.slicurrent != null) return false;
        if (sliouttime != null ? !sliouttime.equals(that.sliouttime) : that.sliouttime != null) return false;
        if (slicustomername != null ? !slicustomername.equals(that.slicustomername) : that.slicustomername != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = sliid != null ? sliid.hashCode() : 0;
        result = 31 * result + (slipathologyid != null ? slipathologyid.hashCode() : 0);
        result = 31 * result + (pathologyid != null ? pathologyid.hashCode() : 0);
        result = 31 * result + (slipatientname != null ? slipatientname.hashCode() : 0);
        result = 31 * result + (slipatientage != null ? slipatientage.hashCode() : 0);
        result = 31 * result + (slipatientsex != null ? slipatientsex.hashCode() : 0);
        result = 31 * result + (slicustomerid != null ? slicustomerid.hashCode() : 0);
        result = 31 * result + (slimadetime != null ? slimadetime.hashCode() : 0);
        result = 31 * result + (sliintime != null ? sliintime.hashCode() : 0);
        result = 31 * result + (slicurrent != null ? slicurrent.hashCode() : 0);
        result = 31 * result + (sliouttime != null ? sliouttime.hashCode() : 0);
        result = 31 * result + (int) (slino ^ (slino >>> 32));
        result = 31 * result + (slicustomername != null ? slicustomername.hashCode() : 0);
        return result;
    }
}

