package com.pims.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by king on 2016/9/28.
 */
public class PimsSysUserRolePK implements Serializable {
    private String userid;
    private String roleid;

    @Column(name = "USERID")
    @Id
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Column(name = "ROLEID")
    @Id
    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PimsSysUserRolePK that = (PimsSysUserRolePK) o;

        if (userid != null ? !userid.equals(that.userid) : that.userid != null) return false;
        if (roleid != null ? !roleid.equals(that.roleid) : that.roleid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userid != null ? userid.hashCode() : 0;
        result = 31 * result + (roleid != null ? roleid.hashCode() : 0);
        return result;
    }
}
