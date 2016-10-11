package com.pims.model;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

/**
 * Created by king on 2016/9/28.
 */
@Entity
@Table(name = "PIMS_SYS_CHARGE_ITEMS")
public class PimsSysChargeItems {
    private long chargeitemid;
    private String chinesename;
    private String chienglishname;
    private String chiitemsort;
    private String chicategory;
    private Double chiprice;
    private long chiuseflag;
    private String chiremark;
    private String chicreateuser;
    private Date chicreatetime;

    @Id
    @Column(name = "CHARGEITEMID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="SEQ_CHARGE_ITEMS")
    @SequenceGenerator(name = "SEQ_CHARGE_ITEMS", sequenceName = "SEQ_CHARGE_ITEMS", allocationSize=1)
    public long getChargeitemid() {
        return chargeitemid;
    }

    public void setChargeitemid(long chargeitemid) {
        this.chargeitemid = chargeitemid;
    }

    @Basic
    @Column(name = "CHINESENAME")
    public String getChinesename() {
        return chinesename;
    }

    public void setChinesename(String chinesename) {
        this.chinesename = chinesename;
    }

    @Basic
    @Column(name = "CHIENGLISHNAME")
    public String getChienglishname() {
        return chienglishname;
    }

    public void setChienglishname(String chienglishname) {
        this.chienglishname = chienglishname;
    }

    @Basic
    @Column(name = "CHIITEMSORT")
    public String getChiitemsort() {
        return chiitemsort;
    }

    public void setChiitemsort(String chiitemsort) {
        this.chiitemsort = chiitemsort;
    }

    @Basic
    @Column(name = "CHICATEGORY")
    public String getChicategory() {
        return chicategory;
    }

    public void setChicategory(String chicategory) {
        this.chicategory = chicategory;
    }

    @Basic
    @Column(name = "CHIPRICE")
    public Double getChiprice() {
        return chiprice;
    }

    public void setChiprice(Double chiprice) {
        this.chiprice = chiprice;
    }

    @Basic
    @Column(name = "CHIUSEFLAG")
    public long getChiuseflag() {
        return chiuseflag;
    }

    public void setChiuseflag(long chiuseflag) {
        this.chiuseflag = chiuseflag;
    }

    @Basic
    @Column(name = "CHIREMARK")
    public String getChiremark() {
        return chiremark;
    }

    public void setChiremark(String chiremark) {
        this.chiremark = chiremark;
    }

    @Basic
    @Column(name = "CHICREATEUSER")
    public String getChicreateuser() {
        return chicreateuser;
    }

    public void setChicreateuser(String chicreateuser) {
        this.chicreateuser = chicreateuser;
    }

    @Basic
    @Column(name = "CHICREATETIME")
    public Date getChicreatetime() {
        return chicreatetime;
    }

    public void setChicreatetime(Date chicreatetime) {
        this.chicreatetime = chicreatetime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PimsSysChargeItems that = (PimsSysChargeItems) o;

        if (chargeitemid != that.chargeitemid) return false;
        if (chinesename != null ? !chinesename.equals(that.chinesename) : that.chinesename != null) return false;
        if (chienglishname != null ? !chienglishname.equals(that.chienglishname) : that.chienglishname != null)
            return false;
        if (chiitemsort != null ? !chiitemsort.equals(that.chiitemsort) : that.chiitemsort != null) return false;
        if (chicategory != null ? !chicategory.equals(that.chicategory) : that.chicategory != null) return false;
        if (chiprice != null ? !chiprice.equals(that.chiprice) : that.chiprice != null) return false;
        if (chiuseflag != that.chiuseflag) return false;
        if (chiremark != null ? !chiremark.equals(that.chiremark) : that.chiremark != null) return false;
        if (chicreateuser != null ? !chicreateuser.equals(that.chicreateuser) : that.chicreateuser != null)
            return false;
        if (chicreatetime != null ? !chicreatetime.equals(that.chicreatetime) : that.chicreatetime != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (chargeitemid ^ (chargeitemid >>> 32));
        result = 31 * result + (chinesename != null ? chinesename.hashCode() : 0);
        result = 31 * result + (chienglishname != null ? chienglishname.hashCode() : 0);
        result = 31 * result + (chiitemsort != null ? chiitemsort.hashCode() : 0);
        result = 31 * result + (chicategory != null ? chicategory.hashCode() : 0);
        result = 31 * result + (chiprice != null ? chiprice.hashCode() : 0);
        result = 31 * result + (int)(chiuseflag ^ (chiuseflag >>> 32));
        result = 31 * result + (chiremark != null ? chiremark.hashCode() : 0);
        result = 31 * result + (chicreateuser != null ? chicreateuser.hashCode() : 0);
        result = 31 * result + (chicreatetime != null ? chicreatetime.hashCode() : 0);
        return result;
    }
}
