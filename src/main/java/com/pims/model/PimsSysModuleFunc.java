package com.pims.model;

import javax.persistence.*;
import java.sql.Time;

/**
 * Created by king on 2016/9/28.
 */
@Entity
@Table(name = "PIMS_SYS_MODULE_FUNC", schema = "KFTEST", catalog = "")
public class PimsSysModuleFunc {
    private String functionid;
    private String functioncode;
    private String functionname;
    private String moduleid;
    private String showsort;
    private String showico;
    private String linkurl;
    private Time createtime;
    private String createuser;

    @Id
    @Column(name = "FUNCTIONID")
    public String getFunctionid() {
        return functionid;
    }

    public void setFunctionid(String functionid) {
        this.functionid = functionid;
    }

    @Basic
    @Column(name = "FUNCTIONCODE")
    public String getFunctioncode() {
        return functioncode;
    }

    public void setFunctioncode(String functioncode) {
        this.functioncode = functioncode;
    }

    @Basic
    @Column(name = "FUNCTIONNAME")
    public String getFunctionname() {
        return functionname;
    }

    public void setFunctionname(String functionname) {
        this.functionname = functionname;
    }

    @Basic
    @Column(name = "MODULEID")
    public String getModuleid() {
        return moduleid;
    }

    public void setModuleid(String moduleid) {
        this.moduleid = moduleid;
    }

    @Basic
    @Column(name = "SHOWSORT")
    public String getShowsort() {
        return showsort;
    }

    public void setShowsort(String showsort) {
        this.showsort = showsort;
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

        PimsSysModuleFunc that = (PimsSysModuleFunc) o;

        if (functionid != null ? !functionid.equals(that.functionid) : that.functionid != null) return false;
        if (functioncode != null ? !functioncode.equals(that.functioncode) : that.functioncode != null) return false;
        if (functionname != null ? !functionname.equals(that.functionname) : that.functionname != null) return false;
        if (moduleid != null ? !moduleid.equals(that.moduleid) : that.moduleid != null) return false;
        if (showsort != null ? !showsort.equals(that.showsort) : that.showsort != null) return false;
        if (showico != null ? !showico.equals(that.showico) : that.showico != null) return false;
        if (linkurl != null ? !linkurl.equals(that.linkurl) : that.linkurl != null) return false;
        if (createtime != null ? !createtime.equals(that.createtime) : that.createtime != null) return false;
        if (createuser != null ? !createuser.equals(that.createuser) : that.createuser != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = functionid != null ? functionid.hashCode() : 0;
        result = 31 * result + (functioncode != null ? functioncode.hashCode() : 0);
        result = 31 * result + (functionname != null ? functionname.hashCode() : 0);
        result = 31 * result + (moduleid != null ? moduleid.hashCode() : 0);
        result = 31 * result + (showsort != null ? showsort.hashCode() : 0);
        result = 31 * result + (showico != null ? showico.hashCode() : 0);
        result = 31 * result + (linkurl != null ? linkurl.hashCode() : 0);
        result = 31 * result + (createtime != null ? createtime.hashCode() : 0);
        result = 31 * result + (createuser != null ? createuser.hashCode() : 0);
        return result;
    }
}
