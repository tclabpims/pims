package com.pims.model;

import javax.persistence.*;
import java.sql.Time;

/**
 * Created by king on 2016/9/28.
 */
@Entity
@Table(name = "PIMS_SYS_ROLE_INFO", schema = "KFTEST", catalog = "")
public class PimsSysRoleInfo {
    private String roleid;
    private String rolename;
    private String rolesort;
    private String pinyincode;
    private Time createtime;
    private String createuser;

    @Id
    @Column(name = "ROLEID")
    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }

    @Basic
    @Column(name = "ROLENAME")
    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    @Basic
    @Column(name = "ROLESORT")
    public String getRolesort() {
        return rolesort;
    }

    public void setRolesort(String rolesort) {
        this.rolesort = rolesort;
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
    @Column(name = "CREATETIME")
    public Time getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Time createtime) {
        this.createtime = createtime;
    }

    @Basic
    @Column(name = "CREATEUSER")
    public String getCreateuser() {
        return createuser;
    }

    public void setCreateuser(String createuser) {
        this.createuser = createuser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PimsSysRoleInfo that = (PimsSysRoleInfo) o;

        if (roleid != null ? !roleid.equals(that.roleid) : that.roleid != null) return false;
        if (rolename != null ? !rolename.equals(that.rolename) : that.rolename != null) return false;
        if (rolesort != null ? !rolesort.equals(that.rolesort) : that.rolesort != null) return false;
        if (pinyincode != null ? !pinyincode.equals(that.pinyincode) : that.pinyincode != null) return false;
        if (createtime != null ? !createtime.equals(that.createtime) : that.createtime != null) return false;
        if (createuser != null ? !createuser.equals(that.createuser) : that.createuser != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = roleid != null ? roleid.hashCode() : 0;
        result = 31 * result + (rolename != null ? rolename.hashCode() : 0);
        result = 31 * result + (rolesort != null ? rolesort.hashCode() : 0);
        result = 31 * result + (pinyincode != null ? pinyincode.hashCode() : 0);
        result = 31 * result + (createtime != null ? createtime.hashCode() : 0);
        result = 31 * result + (createuser != null ? createuser.hashCode() : 0);
        return result;
    }
}
