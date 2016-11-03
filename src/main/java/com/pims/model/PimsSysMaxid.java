package com.pims.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by king on 2016/10/31.
 */
@Entity
@Table(name = "PIMS_SYS_MAXID")
public class PimsSysMaxid {
    private long maxid;
    private String maxobjectname;
    private String maxobjectproperty;
    private int maxresettype;
    private Long maxcurrentvalue;
    private Date maxlastgettime;

    @Id
    @Column(name = "MAXID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="SEQ_PIMSSYSMAXID")
    @SequenceGenerator(name = "SEQ_PIMSSYSMAXID", sequenceName = "SEQ_PIMSSYSMAXID", allocationSize=1)
    public long getMaxid() {
        return maxid;
    }

    public void setMaxid(long maxid) {
        this.maxid = maxid;
    }

    @Basic
    @Column(name = "MAXOBJECTNAME")
    public String getMaxobjectname() {
        return maxobjectname;
    }

    public void setMaxobjectname(String maxobjectname) {
        this.maxobjectname = maxobjectname;
    }

    @Basic
    @Column(name = "MAXOBJECTPROPERTY")
    public String getMaxobjectproperty() {
        return maxobjectproperty;
    }

    public void setMaxobjectproperty(String maxobjectproperty) {
        this.maxobjectproperty = maxobjectproperty;
    }

    @Basic
    @Column(name = "MAXRESETTYPE")
    public int getMaxresettype() {
        return maxresettype;
    }

    public void setMaxresettype(int maxresettype) {
        this.maxresettype = maxresettype;
    }

    @Basic
    @Column(name = "MAXCURRENTVALUE")
    public Long getMaxcurrentvalue() {
        return maxcurrentvalue;
    }

    public void setMaxcurrentvalue(Long maxcurrentvalue) {
        this.maxcurrentvalue = maxcurrentvalue;
    }

    @Basic
    @Column(name = "MAXLASTGETTIME")
    public Date getMaxlastgettime() {
        return maxlastgettime;
    }

    public void setMaxlastgettime(Date maxlastgettime) {
        this.maxlastgettime = maxlastgettime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PimsSysMaxid that = (PimsSysMaxid) o;

        if (maxid != that.maxid) return false;
        if (maxobjectname != null ? !maxobjectname.equals(that.maxobjectname) : that.maxobjectname != null)
            return false;
        if (maxobjectproperty != null ? !maxobjectproperty.equals(that.maxobjectproperty) : that.maxobjectproperty != null)
            return false;
        if (maxresettype != that.maxresettype ) return false;
        if (maxcurrentvalue != null ? !maxcurrentvalue.equals(that.maxcurrentvalue) : that.maxcurrentvalue != null)
            return false;
        if (maxlastgettime != null ? !maxlastgettime.equals(that.maxlastgettime) : that.maxlastgettime != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (maxid ^ (maxid >>> 32));
        result = 31 * result + (maxobjectname != null ? maxobjectname.hashCode() : 0);
        result = 31 * result + (maxobjectproperty != null ? maxobjectproperty.hashCode() : 0);
        result = 31 * result + (maxresettype ^ (maxresettype >>> 32));
        result = 31 * result + (maxcurrentvalue != null ? maxcurrentvalue.hashCode() : 0);
        result = 31 * result + (maxlastgettime != null ? maxlastgettime.hashCode() : 0);
        return result;
    }
}
