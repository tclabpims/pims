package com.pims.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by zp on 2016/11/15.
 */
@Entity
@Table(name="PIMS_SLIDE_RECORD")
public class PimsSlideRecord {
    private String sliid;
    private String slicustomername;
    private String slicustomerid;
    private long slino;
    private String slimanagername;
    private String slidept;
    private String sliresult;
    private String slicurrent;
    private Date slitime;



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
    @Column(name = "SLICUSTOMERNAME")
    public String getSlicustomername() {
        return slicustomername;
    }

    public void setSlicustomername(String slicustomername) {
        this.slicustomername = slicustomername;
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
    @Column(name = "SLIMANAGERNAME")
    public String getSlimanagername() {
        return slimanagername;
    }

    public void setSlimanagername(String slimanagername) {
        this.slimanagername = slimanagername;
    }

    @Basic
    @Column(name = "SLIDEPT")
    public String getSlidept() {
        return slidept;
    }

    public void setSlidept(String slidept) {
        this.slidept = slidept;
    }

    @Basic
    @Column(name = "SLIRESULT")
    public String getSliresult() {
        return sliresult;
    }

    public void setSliresult(String sliresult) {
        this.sliresult = sliresult;
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
    @Column(name = "SLITIME")
    public Date getSlitime() {
        return slitime;
    }

    public void setSlitime(Date slitime) {
        this.slitime = slitime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PimsSlideRecord record = (PimsSlideRecord) o;

        if (slino != record.slino) return false;
        if (sliid != null ? !sliid.equals(record.sliid) : record.sliid != null) return false;
        if (slicustomername != null ? !slicustomername.equals(record.slicustomername) : record.slicustomername != null)
            return false;
        if (slicustomerid != null ? !slicustomerid.equals(record.slicustomerid) : record.slicustomerid != null)
            return false;
        if (slimanagername != null ? !slimanagername.equals(record.slimanagername) : record.slimanagername != null)
            return false;
        if (slidept != null ? !slidept.equals(record.slidept) : record.slidept != null) return false;
        if (sliresult != null ? !sliresult.equals(record.sliresult) : record.sliresult != null) return false;
        if (slicurrent != null ? !slicurrent.equals(record.slicurrent) : record.slicurrent != null) return false;
        if (slitime != null ? !slitime.equals(record.slitime) : record.slitime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = sliid != null ? sliid.hashCode() : 0;
        result = 31 * result + (slicustomername != null ? slicustomername.hashCode() : 0);
        result = 31 * result + (slicustomerid != null ? slicustomerid.hashCode() : 0);
        result = 31 * result + (int) (slino ^ (slino >>> 32));
        result = 31 * result + (slimanagername != null ? slimanagername.hashCode() : 0);
        result = 31 * result + (slidept != null ? slidept.hashCode() : 0);
        result = 31 * result + (sliresult != null ? sliresult.hashCode() : 0);
        result = 31 * result + (slicurrent != null ? slicurrent.hashCode() : 0);
        result = 31 * result + (slitime != null ? slitime.hashCode() : 0);
        return result;
    }
}
