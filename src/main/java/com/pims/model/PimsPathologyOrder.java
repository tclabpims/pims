package com.pims.model;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

/**
 * Created by king on 2016/9/28.
 */
@Entity
@Table(name = "PIMS_PATHOLOGY_ORDER")
public class PimsPathologyOrder {
    private long orderid;
    private String ordercode;
    private long ordsampleid;
    private String ordcustomercode;
    private String ordpathologycode;
    private Date ordcreatetime;
    private String ordorderuserid;
    private String ordorderuser;
    private Date ordaccepttime;
    private String ordacceptorid;
    private String ordacceptorname;
    private Date ordfinishedtime;
    private String ordfinisheduserid;
    private String ordfinishedusername;
    private long ordorderstate;
    private Long ordisdelete;


    @Id
    @Column(name = "ORDERID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="Seq_OrderId")
    @SequenceGenerator(name = "Seq_OrderId", sequenceName = "Seq_OrderId", allocationSize=1)
    public long getOrderid() {
        return orderid;
    }

    public void setOrderid(long orderid) {
        this.orderid = orderid;
    }

    @Basic
    @Column(name = "ORDERCODE")
    public String getOrdercode() {
        return ordercode;
    }

    public void setOrdercode(String ordercode) {
        this.ordercode = ordercode;
    }

    @Basic
    @Column(name = "ORDSAMPLEID")
    public long getOrdsampleid() {
        return ordsampleid;
    }

    public void setOrdsampleid(long ordsampleid) {
        this.ordsampleid = ordsampleid;
    }

    @Basic
    @Column(name = "ORDCUSTOMERCODE")
    public String getOrdcustomercode() {
        return ordcustomercode;
    }

    public void setOrdcustomercode(String ordcustomercode) {
        this.ordcustomercode = ordcustomercode;
    }

    @Basic
    @Column(name = "ORDPATHOLOGYCODE")
    public String getOrdpathologycode() {
        return ordpathologycode;
    }

    public void setOrdpathologycode(String ordpathologycode) {
        this.ordpathologycode = ordpathologycode;
    }

    @Basic
    @Column(name = "ORDCREATETIME")
    public Date getOrdcreatetime() {
        return ordcreatetime;
    }

    public void setOrdcreatetime(Date ordcreatetime) {
        this.ordcreatetime = ordcreatetime;
    }

    @Basic
    @Column(name = "ORDORDERUSERID")
    public String getOrdorderuserid() {
        return ordorderuserid;
    }

    public void setOrdorderuserid(String ordorderuserid) {
        this.ordorderuserid = ordorderuserid;
    }

    @Basic
    @Column(name = "ORDORDERUSER")
    public String getOrdorderuser() {
        return ordorderuser;
    }

    public void setOrdorderuser(String ordorderuser) {
        this.ordorderuser = ordorderuser;
    }

    @Basic
    @Column(name = "ORDACCEPTTIME")
    public Date getOrdaccepttime() {
        return ordaccepttime;
    }

    public void setOrdaccepttime(Date ordaccepttime) {
        this.ordaccepttime = ordaccepttime;
    }

    @Basic
    @Column(name = "ORDACCEPTORID")
    public String getOrdacceptorid() {
        return ordacceptorid;
    }

    public void setOrdacceptorid(String ordacceptorid) {
        this.ordacceptorid = ordacceptorid;
    }

    @Basic
    @Column(name = "ORDACCEPTORNAME")
    public String getOrdacceptorname() {
        return ordacceptorname;
    }

    public void setOrdacceptorname(String ordacceptorname) {
        this.ordacceptorname = ordacceptorname;
    }

    @Basic
    @Column(name = "ORDFINISHEDTIME")
    public Date getOrdfinishedtime() {
        return ordfinishedtime;
    }

    public void setOrdfinishedtime(Date ordfinishedtime) {
        this.ordfinishedtime = ordfinishedtime;
    }

    @Basic
    @Column(name = "ORDFINISHEDUSERID")
    public String getOrdfinisheduserid() {
        return ordfinisheduserid;
    }

    public void setOrdfinisheduserid(String ordfinisheduserid) {
        this.ordfinisheduserid = ordfinisheduserid;
    }

    @Basic
    @Column(name = "ORDFINISHEDUSERNAME")
    public String getOrdfinishedusername() {
        return ordfinishedusername;
    }

    public void setOrdfinishedusername(String ordfinishedusername) {
        this.ordfinishedusername = ordfinishedusername;
    }

    @Basic
    @Column(name = "ORDORDERSTATE")
    public long getOrdorderstate() {
        return ordorderstate;
    }

    public void setOrdorderstate(long ordorderstate) {
        this.ordorderstate = ordorderstate;
    }

    @Basic
    @Column(name = "ORDISDELETE")
    public Long getOrdisdelete() {
        return ordisdelete;
    }

    public void setOrdisdelete(Long ordisdelete) {
        this.ordisdelete = ordisdelete;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PimsPathologyOrder that = (PimsPathologyOrder) o;

        if (orderid != that.orderid) return false;
        if (ordsampleid != that.ordsampleid) return false;
        if (ordorderstate != that.ordorderstate) return false;
        if (ordercode != null ? !ordercode.equals(that.ordercode) : that.ordercode != null) return false;
        if (ordcustomercode != null ? !ordcustomercode.equals(that.ordcustomercode) : that.ordcustomercode != null)
            return false;
        if (ordpathologycode != null ? !ordpathologycode.equals(that.ordpathologycode) : that.ordpathologycode != null)
            return false;
        if (ordcreatetime != null ? !ordcreatetime.equals(that.ordcreatetime) : that.ordcreatetime != null)
            return false;
        if (ordorderuserid != null ? !ordorderuserid.equals(that.ordorderuserid) : that.ordorderuserid != null)
            return false;
        if (ordorderuser != null ? !ordorderuser.equals(that.ordorderuser) : that.ordorderuser != null) return false;
        if (ordaccepttime != null ? !ordaccepttime.equals(that.ordaccepttime) : that.ordaccepttime != null)
            return false;
        if (ordacceptorid != null ? !ordacceptorid.equals(that.ordacceptorid) : that.ordacceptorid != null)
            return false;
        if (ordacceptorname != null ? !ordacceptorname.equals(that.ordacceptorname) : that.ordacceptorname != null)
            return false;
        if (ordfinishedtime != null ? !ordfinishedtime.equals(that.ordfinishedtime) : that.ordfinishedtime != null)
            return false;
        if (ordfinisheduserid != null ? !ordfinisheduserid.equals(that.ordfinisheduserid) : that.ordfinisheduserid != null)
            return false;
        if (ordfinishedusername != null ? !ordfinishedusername.equals(that.ordfinishedusername) : that.ordfinishedusername != null)
            return false;
        if (ordisdelete != null ? !ordisdelete.equals(that.ordisdelete) : that.ordisdelete != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (orderid ^ (orderid >>> 32));
        result = 31 * result + (ordercode != null ? ordercode.hashCode() : 0);
        result = 31 * result + (int) (ordsampleid ^ (ordsampleid >>> 32));
        result = 31 * result + (ordcustomercode != null ? ordcustomercode.hashCode() : 0);
        result = 31 * result + (ordpathologycode != null ? ordpathologycode.hashCode() : 0);
        result = 31 * result + (ordcreatetime != null ? ordcreatetime.hashCode() : 0);
        result = 31 * result + (ordorderuserid != null ? ordorderuserid.hashCode() : 0);
        result = 31 * result + (ordorderuser != null ? ordorderuser.hashCode() : 0);
        result = 31 * result + (ordaccepttime != null ? ordaccepttime.hashCode() : 0);
        result = 31 * result + (ordacceptorid != null ? ordacceptorid.hashCode() : 0);
        result = 31 * result + (ordacceptorname != null ? ordacceptorname.hashCode() : 0);
        result = 31 * result + (ordfinishedtime != null ? ordfinishedtime.hashCode() : 0);
        result = 31 * result + (ordfinisheduserid != null ? ordfinisheduserid.hashCode() : 0);
        result = 31 * result + (ordfinishedusername != null ? ordfinishedusername.hashCode() : 0);
        result = 31 * result + (int) (ordorderstate ^ (ordorderstate >>> 32));
        result = 31 * result + (ordisdelete != null ? ordisdelete.hashCode() : 0);
        return result;
    }
}
