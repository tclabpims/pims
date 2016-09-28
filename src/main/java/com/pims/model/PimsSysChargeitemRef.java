package com.pims.model;

import javax.persistence.*;
import java.sql.Time;

/**
 * Created by king on 2016/9/28.
 */
@Entity
@Table(name = "PIMS_SYS_CHARGEITEM_REF", schema = "KFTEST", catalog = "")
public class PimsSysChargeitemRef {
    private long referenceid;
    private long chargeitemid;
    private String customercode;
    private String refhischargeid;
    private String refhischargename;
    private double refhisprice;
    private String refsendhis;
    private String refremark;
    private Time refcreatetime;
    private String refcreateuser;

    @Id
    @Column(name = "REFERENCEID")
    public long getReferenceid() {
        return referenceid;
    }

    public void setReferenceid(long referenceid) {
        this.referenceid = referenceid;
    }

    @Basic
    @Column(name = "CHARGEITEMID")
    public long getChargeitemid() {
        return chargeitemid;
    }

    public void setChargeitemid(long chargeitemid) {
        this.chargeitemid = chargeitemid;
    }

    @Basic
    @Column(name = "CUSTOMERCODE")
    public String getCustomercode() {
        return customercode;
    }

    public void setCustomercode(String customercode) {
        this.customercode = customercode;
    }

    @Basic
    @Column(name = "REFHISCHARGEID")
    public String getRefhischargeid() {
        return refhischargeid;
    }

    public void setRefhischargeid(String refhischargeid) {
        this.refhischargeid = refhischargeid;
    }

    @Basic
    @Column(name = "REFHISCHARGENAME")
    public String getRefhischargename() {
        return refhischargename;
    }

    public void setRefhischargename(String refhischargename) {
        this.refhischargename = refhischargename;
    }

    @Basic
    @Column(name = "REFHISPRICE")
    public double getRefhisprice() {
        return refhisprice;
    }

    public void setRefhisprice(double refhisprice) {
        this.refhisprice = refhisprice;
    }

    @Basic
    @Column(name = "REFSENDHIS")
    public String getRefsendhis() {
        return refsendhis;
    }

    public void setRefsendhis(String refsendhis) {
        this.refsendhis = refsendhis;
    }

    @Basic
    @Column(name = "REFREMARK")
    public String getRefremark() {
        return refremark;
    }

    public void setRefremark(String refremark) {
        this.refremark = refremark;
    }

    @Basic
    @Column(name = "REFCREATETIME")
    public Time getRefcreatetime() {
        return refcreatetime;
    }

    public void setRefcreatetime(Time refcreatetime) {
        this.refcreatetime = refcreatetime;
    }

    @Basic
    @Column(name = "REFCREATEUSER")
    public String getRefcreateuser() {
        return refcreateuser;
    }

    public void setRefcreateuser(String refcreateuser) {
        this.refcreateuser = refcreateuser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PimsSysChargeitemRef that = (PimsSysChargeitemRef) o;

        if (referenceid != that.referenceid) return false;
        if (chargeitemid != that.chargeitemid) return false;
        if (Double.compare(that.refhisprice, refhisprice) != 0) return false;
        if (customercode != null ? !customercode.equals(that.customercode) : that.customercode != null) return false;
        if (refhischargeid != null ? !refhischargeid.equals(that.refhischargeid) : that.refhischargeid != null)
            return false;
        if (refhischargename != null ? !refhischargename.equals(that.refhischargename) : that.refhischargename != null)
            return false;
        if (refsendhis != null ? !refsendhis.equals(that.refsendhis) : that.refsendhis != null) return false;
        if (refremark != null ? !refremark.equals(that.refremark) : that.refremark != null) return false;
        if (refcreatetime != null ? !refcreatetime.equals(that.refcreatetime) : that.refcreatetime != null)
            return false;
        if (refcreateuser != null ? !refcreateuser.equals(that.refcreateuser) : that.refcreateuser != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (referenceid ^ (referenceid >>> 32));
        result = 31 * result + (int) (chargeitemid ^ (chargeitemid >>> 32));
        result = 31 * result + (customercode != null ? customercode.hashCode() : 0);
        result = 31 * result + (refhischargeid != null ? refhischargeid.hashCode() : 0);
        result = 31 * result + (refhischargename != null ? refhischargename.hashCode() : 0);
        temp = Double.doubleToLongBits(refhisprice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (refsendhis != null ? refsendhis.hashCode() : 0);
        result = 31 * result + (refremark != null ? refremark.hashCode() : 0);
        result = 31 * result + (refcreatetime != null ? refcreatetime.hashCode() : 0);
        result = 31 * result + (refcreateuser != null ? refcreateuser.hashCode() : 0);
        return result;
    }
}
