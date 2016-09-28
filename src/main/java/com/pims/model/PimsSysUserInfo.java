package com.pims.model;

import javax.persistence.*;
import java.sql.Time;

/**
 * Created by king on 2016/9/28.
 */
@Entity
@Table(name = "PIMS_SYS_USER_INFO", schema = "KFTEST", catalog = "")
public class PimsSysUserInfo {
    private String userId;
    private String customercode;
    private String username;
    private String usersex;
    private String usersort;
    private String pinyincode;
    private String userdept;
    private String workstation;
    private String hisId;
    private String hischargedept;
    private String useflag;
    private String telephone;
    private String password;
    private Time createtime;
    private Time expiredtime;

    @Id
    @Column(name = "USER_ID")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
    @Column(name = "USERNAME")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "USERSEX")
    public String getUsersex() {
        return usersex;
    }

    public void setUsersex(String usersex) {
        this.usersex = usersex;
    }

    @Basic
    @Column(name = "USERSORT")
    public String getUsersort() {
        return usersort;
    }

    public void setUsersort(String usersort) {
        this.usersort = usersort;
    }

    @Basic
    @Column(name = "PINYINCODE")
    public String getPinyincode() {
        return pinyincode;
    }

    public void setPinyincode(String pinyincode) {
        this.pinyincode = pinyincode;
    }

    @Basic
    @Column(name = "USERDEPT")
    public String getUserdept() {
        return userdept;
    }

    public void setUserdept(String userdept) {
        this.userdept = userdept;
    }

    @Basic
    @Column(name = "WORKSTATION")
    public String getWorkstation() {
        return workstation;
    }

    public void setWorkstation(String workstation) {
        this.workstation = workstation;
    }

    @Basic
    @Column(name = "HIS_ID")
    public String getHisId() {
        return hisId;
    }

    public void setHisId(String hisId) {
        this.hisId = hisId;
    }

    @Basic
    @Column(name = "HISCHARGEDEPT")
    public String getHischargedept() {
        return hischargedept;
    }

    public void setHischargedept(String hischargedept) {
        this.hischargedept = hischargedept;
    }

    @Basic
    @Column(name = "USEFLAG")
    public String getUseflag() {
        return useflag;
    }

    public void setUseflag(String useflag) {
        this.useflag = useflag;
    }

    @Basic
    @Column(name = "TELEPHONE")
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Basic
    @Column(name = "PASSWORD")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "CREATETIME")
    public Time getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Time createtime) {
        this.createtime = createtime;
    }

    @Basic
    @Column(name = "EXPIREDTIME")
    public Time getExpiredtime() {
        return expiredtime;
    }

    public void setExpiredtime(Time expiredtime) {
        this.expiredtime = expiredtime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PimsSysUserInfo that = (PimsSysUserInfo) o;

        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (customercode != null ? !customercode.equals(that.customercode) : that.customercode != null) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (usersex != null ? !usersex.equals(that.usersex) : that.usersex != null) return false;
        if (usersort != null ? !usersort.equals(that.usersort) : that.usersort != null) return false;
        if (pinyincode != null ? !pinyincode.equals(that.pinyincode) : that.pinyincode != null) return false;
        if (userdept != null ? !userdept.equals(that.userdept) : that.userdept != null) return false;
        if (workstation != null ? !workstation.equals(that.workstation) : that.workstation != null) return false;
        if (hisId != null ? !hisId.equals(that.hisId) : that.hisId != null) return false;
        if (hischargedept != null ? !hischargedept.equals(that.hischargedept) : that.hischargedept != null)
            return false;
        if (useflag != null ? !useflag.equals(that.useflag) : that.useflag != null) return false;
        if (telephone != null ? !telephone.equals(that.telephone) : that.telephone != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (createtime != null ? !createtime.equals(that.createtime) : that.createtime != null) return false;
        if (expiredtime != null ? !expiredtime.equals(that.expiredtime) : that.expiredtime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (customercode != null ? customercode.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (usersex != null ? usersex.hashCode() : 0);
        result = 31 * result + (usersort != null ? usersort.hashCode() : 0);
        result = 31 * result + (pinyincode != null ? pinyincode.hashCode() : 0);
        result = 31 * result + (userdept != null ? userdept.hashCode() : 0);
        result = 31 * result + (workstation != null ? workstation.hashCode() : 0);
        result = 31 * result + (hisId != null ? hisId.hashCode() : 0);
        result = 31 * result + (hischargedept != null ? hischargedept.hashCode() : 0);
        result = 31 * result + (useflag != null ? useflag.hashCode() : 0);
        result = 31 * result + (telephone != null ? telephone.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (createtime != null ? createtime.hashCode() : 0);
        result = 31 * result + (expiredtime != null ? expiredtime.hashCode() : 0);
        return result;
    }
}
