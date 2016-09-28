package com.pims.model;

import javax.persistence.*;
import java.sql.Time;

/**
 * Created by king on 2016/9/28.
 */
@Entity
@Table(name = "PIMS_SYS_MODULE_INFO", schema = "KFTEST", catalog = "")
public class PimsSysModuleInfo {
    private String moduleid;
    private String modulecode;
    private String modulename;
    private String modulelevel;
    private String parentid;
    private String levelsort;
    private String showico;
    private String linkurl;
    private String useflag;
    private Time createtime;
    private String createuser;

    @Id
    @Column(name = "MODULEID")
    public String getModuleid() {
        return moduleid;
    }

    public void setModuleid(String moduleid) {
        this.moduleid = moduleid;
    }

    @Basic
    @Column(name = "MODULECODE")
    public String getModulecode() {
        return modulecode;
    }

    public void setModulecode(String modulecode) {
        this.modulecode = modulecode;
    }

    @Basic
    @Column(name = "MODULENAME")
    public String getModulename() {
        return modulename;
    }

    public void setModulename(String modulename) {
        this.modulename = modulename;
    }

    @Basic
    @Column(name = "MODULELEVEL")
    public String getModulelevel() {
        return modulelevel;
    }

    public void setModulelevel(String modulelevel) {
        this.modulelevel = modulelevel;
    }

    @Basic
    @Column(name = "PARENTID")
    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    @Basic
    @Column(name = "LEVELSORT")
    public String getLevelsort() {
        return levelsort;
    }

    public void setLevelsort(String levelsort) {
        this.levelsort = levelsort;
    }

    @Basic
    @Column(name = "SHOWICO")
    public String getShowico() {
        return showico;
    }

    public void setShowico(String showico) {
        this.showico = showico;
    }

    @Basic
    @Column(name = "LINKURL")
    public String getLinkurl() {
        return linkurl;
    }

    public void setLinkurl(String linkurl) {
        this.linkurl = linkurl;
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

        PimsSysModuleInfo that = (PimsSysModuleInfo) o;

        if (moduleid != null ? !moduleid.equals(that.moduleid) : that.moduleid != null) return false;
        if (modulecode != null ? !modulecode.equals(that.modulecode) : that.modulecode != null) return false;
        if (modulename != null ? !modulename.equals(that.modulename) : that.modulename != null) return false;
        if (modulelevel != null ? !modulelevel.equals(that.modulelevel) : that.modulelevel != null) return false;
        if (parentid != null ? !parentid.equals(that.parentid) : that.parentid != null) return false;
        if (levelsort != null ? !levelsort.equals(that.levelsort) : that.levelsort != null) return false;
        if (showico != null ? !showico.equals(that.showico) : that.showico != null) return false;
        if (linkurl != null ? !linkurl.equals(that.linkurl) : that.linkurl != null) return false;
        if (useflag != null ? !useflag.equals(that.useflag) : that.useflag != null) return false;
        if (createtime != null ? !createtime.equals(that.createtime) : that.createtime != null) return false;
        if (createuser != null ? !createuser.equals(that.createuser) : that.createuser != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = moduleid != null ? moduleid.hashCode() : 0;
        result = 31 * result + (modulecode != null ? modulecode.hashCode() : 0);
        result = 31 * result + (modulename != null ? modulename.hashCode() : 0);
        result = 31 * result + (modulelevel != null ? modulelevel.hashCode() : 0);
        result = 31 * result + (parentid != null ? parentid.hashCode() : 0);
        result = 31 * result + (levelsort != null ? levelsort.hashCode() : 0);
        result = 31 * result + (showico != null ? showico.hashCode() : 0);
        result = 31 * result + (linkurl != null ? linkurl.hashCode() : 0);
        result = 31 * result + (useflag != null ? useflag.hashCode() : 0);
        result = 31 * result + (createtime != null ? createtime.hashCode() : 0);
        result = 31 * result + (createuser != null ? createuser.hashCode() : 0);
        return result;
    }
}
