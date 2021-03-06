package com.pims.model;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

/**
 * Created by king on 2016/9/28.
 */
@Entity
@Table(name = "PIMS_SYS_CHARGEITEM_REF")
public class PimsSysChargeitemRef {
    private long referenceid;
    private long chargeitemid;
    private long customercode;
    private String refhischargeid;
    private String refhischargename;
    private double refhisprice;
    private long refsendhis;
    private String refremark;
    private Date refcreatetime;
    private String refcreateuser;

    private String chargeItemName;

    private String customerName;

    @Transient
    public String getChargeItemName() {
        return chargeItemName;
    }

    public void setChargeItemName(String chargeItemName) {
        this.chargeItemName = chargeItemName;
    }

    @Transient
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Id
    @Column(name = "REFERENCEID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="SEQ_CHARGE_ITEMS_REF")
    @SequenceGenerator(name = "SEQ_CHARGE_ITEMS_REF", sequenceName = "SEQ_CHARGE_ITEMS_REF", allocationSize=1)
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
    @Column(name = "CUSTOMERID")
    public long getCustomercode() {
        return customercode;
    }

    public void setCustomercode(long customercode) {
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
    public long getRefsendhis() {
        return refsendhis;
    }

    public void setRefsendhis(long refsendhis) {
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
    public Date getRefcreatetime() {
        return refcreatetime;
    }

    public void setRefcreatetime(Date refcreatetime) {
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
        if (refhischargeid != null ? !refhischargeid.equals(that.refhischargeid) : that.refhischargeid != null)
            return false;
        if (refhischargename != null ? !refhischargename.equals(that.refhischargename) : that.refhischargename != null)
            return false;
        if (refsendhis != that.refsendhis) return false;
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
        result = 31 * result + (refhischargeid != null ? refhischargeid.hashCode() : 0);
        result = 31 * result + (refhischargename != null ? refhischargename.hashCode() : 0);
        temp = Double.doubleToLongBits(refhisprice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (int)(refsendhis^( refsendhis >>>32));
        result = 31 * result + (refremark != null ? refremark.hashCode() : 0);
        result = 31 * result + (refcreatetime != null ? refcreatetime.hashCode() : 0);
        result = 31 * result + (refcreateuser != null ? refcreateuser.hashCode() : 0);
        return result;
    }
}
