package com.pims.model;

import javax.persistence.*;
import java.sql.Time;

/**
 * Created by king on 2016/9/28.
 */
@Entity
@Table(name = "PIMS_SYS_ROLE_POWER", schema = "KFTEST", catalog = "")
public class PimsSysRolePower {
    private String rolepowerid;
    private String type;
    private String roleid;
    private String powerid;
    private Time createtime;
    private String createuser;

    @Id
    @Column(name = "ROLEPOWERID")
    public String getRolepowerid() {
        return rolepowerid;
    }

    public void setRolepowerid(String rolepowerid) {
        this.rolepowerid = rolepowerid;
    }

    @Basic
    @Column(name = "TYPE")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "ROLEID")
    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }

    @Basic
    @Column(name = "POWERID")
    public String getPowerid() {
        return powerid;
    }

    public void setPowerid(String powerid) {
        this.powerid = powerid;
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

        PimsSysRolePower that = (PimsSysRolePower) o;

        if (rolepowerid != null ? !rolepowerid.equals(that.rolepowerid) : that.rolepowerid != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (roleid != null ? !roleid.equals(that.roleid) : that.roleid != null) return false;
        if (powerid != null ? !powerid.equals(that.powerid) : that.powerid != null) return false;
        if (createtime != null ? !createtime.equals(that.createtime) : that.createtime != null) return false;
        if (createuser != null ? !createuser.equals(that.createuser) : that.createuser != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = rolepowerid != null ? rolepowerid.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (roleid != null ? roleid.hashCode() : 0);
        result = 31 * result + (powerid != null ? powerid.hashCode() : 0);
        result = 31 * result + (createtime != null ? createtime.hashCode() : 0);
        result = 31 * result + (createuser != null ? createuser.hashCode() : 0);
        return result;
    }
}
