package com.pims.model;

import javax.persistence.*;
import java.sql.Time;

/**
 * Created by king on 2016/11/3.
 */
@Entity
@Table(name = "PIMS_SYS_TEST_FEE")
public class PimsSysTestFee {
    private long tfid;
    private long tftestid;
    private long tffeeid;
    private String tfsort;
    private int tfflag;
    private String tfcreateuser;
    private Time tfcreatetime;

    @Id
    @Column(name = "TFID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="SEQ_TESTFEE")
    @SequenceGenerator(name = "SEQ_TESTFEE", sequenceName = "SEQ_TESTFEE", allocationSize=1)
    public long getTfid() {
        return tfid;
    }

    public void setTfid(long tfid) {
        this.tfid = tfid;
    }

    @Basic
    @Column(name = "TFTESTID")
    public long getTftestid() {
        return tftestid;
    }

    public void setTftestid(long tftestid) {
        this.tftestid = tftestid;
    }

    @Basic
    @Column(name = "TFFEEID")
    public long getTffeeid() {
        return tffeeid;
    }

    public void setTffeeid(long tffeeid) {
        this.tffeeid = tffeeid;
    }

    @Basic
    @Column(name = "TFSORT")
    public String getTfsort() {
        return tfsort;
    }

    public void setTfsort(String tfsort) {
        this.tfsort = tfsort;
    }

    @Basic
    @Column(name = "TFFLAG")
    public int getTfflag() {
        return tfflag;
    }

    public void setTfflag(int tfflag) {
        this.tfflag = tfflag;
    }

    @Basic
    @Column(name = "TFCREATEUSER")
    public String getTfcreateuser() {
        return tfcreateuser;
    }

    public void setTfcreateuser(String tfcreateuser) {
        this.tfcreateuser = tfcreateuser;
    }

    @Basic
    @Column(name = "TFCREATETIME")
    public Time getTfcreatetime() {
        return tfcreatetime;
    }

    public void setTfcreatetime(Time tfcreatetime) {
        this.tfcreatetime = tfcreatetime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PimsSysTestFee that = (PimsSysTestFee) o;

        if (tfid != that.tfid) return false;
        if (tftestid != that.tftestid) return false;
        if (tffeeid != that.tffeeid) return false;
        if (tfsort != null ? !tfsort.equals(that.tfsort) : that.tfsort != null) return false;
        if (tfflag != that.tfflag) return false;
        if (tfcreateuser != null ? !tfcreateuser.equals(that.tfcreateuser) : that.tfcreateuser != null) return false;
        if (tfcreatetime != null ? !tfcreatetime.equals(that.tfcreatetime) : that.tfcreatetime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (tfid ^ (tfid >>> 32));
        result = 31 * result + (int) (tftestid ^ (tftestid >>> 32));
        result = 31 * result + (int) (tffeeid ^ (tffeeid >>> 32));
        result = 31 * result + (tfsort != null ? tfsort.hashCode() : 0);
        result = 31 * result + (tfflag ^ (tfflag >>> 32));
        result = 31 * result + (tfcreateuser != null ? tfcreateuser.hashCode() : 0);
        result = 31 * result + (tfcreatetime != null ? tfcreatetime.hashCode() : 0);
        return result;
    }
}
