package com.pims.model;

import javax.persistence.*;
import javax.xml.crypto.Data;
import java.util.Date;

/**
 * Created by zp on 2016/12/16.
 */
@Entity
@Table(name="PIMS_DISPOSABLE_MATERIAL")
public class PimsDisposableMaterial {
    private String marishas;
    private long marno;
    private String marname;
    private String manufacter;
    private String loginuser;
    private Date loginintime;
    private String remarks;
    private String marid;

    @Basic
    @Column(name="MARISHAS")
    public String getMarishas() {
        return marishas;
    }

    public void setMarishas(String marishas) {
        this.marishas = marishas;
    }

    @Id
    @Column(name = "MARNO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="employ_autoinc")
    @SequenceGenerator(name = "employ_autoinc", sequenceName = "employ_autoinc", allocationSize=1)
    public long getMarno() {
        return marno;
    }

    public void setMarno(long marno) {
        this.marno = marno;
    }

    @Basic
    @Column(name="MARNAME")
    public String getMarname() {
        return marname;
    }

    public void setMarname(String marname) {
        this.marname = marname;
    }

    @Basic
    @Column(name="MANUFACTER")
    public String getManufacter() {
        return manufacter;
    }

    public void setManufacter(String manufacter) {
        this.manufacter = manufacter;
    }

    @Basic
    @Column(name="LOGINUSER")
    public String getLoginuser() {
        return loginuser;
    }

    public void setLoginuser(String loginuser) {
        this.loginuser = loginuser;
    }

    @Basic
    @Column(name="LOGININTIME")
    public Date getLoginintime() {
        return loginintime;
    }

    public void setLoginintime(Date loginintime) {
        this.loginintime = loginintime;
    }

    @Basic
    @Column(name="REMARKS")
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getMarid() {
        return marid;
    }

    public void setMarid(String marid) {
        this.marid = marid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PimsDisposableMaterial that = (PimsDisposableMaterial) o;

        if (marno != that.marno) return false;
        if (marishas != null ? !marishas.equals(that.marishas) : that.marishas != null) return false;
        if (marname != null ? !marname.equals(that.marname) : that.marname != null) return false;
        if (manufacter != null ? !manufacter.equals(that.manufacter) : that.manufacter != null) return false;
        if (loginuser != null ? !loginuser.equals(that.loginuser) : that.loginuser != null) return false;
        if (loginintime != null ? !loginintime.equals(that.loginintime) : that.loginintime != null) return false;
        if (remarks != null ? !remarks.equals(that.remarks) : that.remarks != null) return false;
        return marid != null ? marid.equals(that.marid) : that.marid == null;

    }

    @Override
    public int hashCode() {
        int result = marishas != null ? marishas.hashCode() : 0;
        result = 31 * result + (int) (marno ^ (marno >>> 32));
        result = 31 * result + (marname != null ? marname.hashCode() : 0);
        result = 31 * result + (manufacter != null ? manufacter.hashCode() : 0);
        result = 31 * result + (loginuser != null ? loginuser.hashCode() : 0);
        result = 31 * result + (loginintime != null ? loginintime.hashCode() : 0);
        result = 31 * result + (remarks != null ? remarks.hashCode() : 0);
        result = 31 * result + (marid != null ? marid.hashCode() : 0);
        return result;
    }
}
