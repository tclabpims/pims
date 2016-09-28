package com.pims.model;

import javax.persistence.*;
import java.sql.Time;

/**
 * Created by king on 2016/9/28.
 */
@Entity
@Table(name = "PIMS_SYS_OPERATION_LOGS", schema = "KFTEST", catalog = "")
public class PimsSysOperationLogs {
    private long logid;
    private String logcustomer;
    private Long logtype;
    private String logmodulecode;
    private String loguserid;
    private String logusername;
    private String logcontent;
    private Time logtime;
    private String logipaddress;
    private String logmacaddress;
    private String logfirstv;
    private String logsecondv;

    @Id
    @Column(name = "LOGID")
    public long getLogid() {
        return logid;
    }

    public void setLogid(long logid) {
        this.logid = logid;
    }

    @Basic
    @Column(name = "LOGCUSTOMER")
    public String getLogcustomer() {
        return logcustomer;
    }

    public void setLogcustomer(String logcustomer) {
        this.logcustomer = logcustomer;
    }

    @Basic
    @Column(name = "LOGTYPE")
    public Long getLogtype() {
        return logtype;
    }

    public void setLogtype(Long logtype) {
        this.logtype = logtype;
    }

    @Basic
    @Column(name = "LOGMODULECODE")
    public String getLogmodulecode() {
        return logmodulecode;
    }

    public void setLogmodulecode(String logmodulecode) {
        this.logmodulecode = logmodulecode;
    }

    @Basic
    @Column(name = "LOGUSERID")
    public String getLoguserid() {
        return loguserid;
    }

    public void setLoguserid(String loguserid) {
        this.loguserid = loguserid;
    }

    @Basic
    @Column(name = "LOGUSERNAME")
    public String getLogusername() {
        return logusername;
    }

    public void setLogusername(String logusername) {
        this.logusername = logusername;
    }

    @Basic
    @Column(name = "LOGCONTENT")
    public String getLogcontent() {
        return logcontent;
    }

    public void setLogcontent(String logcontent) {
        this.logcontent = logcontent;
    }

    @Basic
    @Column(name = "LOGTIME")
    public Time getLogtime() {
        return logtime;
    }

    public void setLogtime(Time logtime) {
        this.logtime = logtime;
    }

    @Basic
    @Column(name = "LOGIPADDRESS")
    public String getLogipaddress() {
        return logipaddress;
    }

    public void setLogipaddress(String logipaddress) {
        this.logipaddress = logipaddress;
    }

    @Basic
    @Column(name = "LOGMACADDRESS")
    public String getLogmacaddress() {
        return logmacaddress;
    }

    public void setLogmacaddress(String logmacaddress) {
        this.logmacaddress = logmacaddress;
    }

    @Basic
    @Column(name = "LOGFIRSTV")
    public String getLogfirstv() {
        return logfirstv;
    }

    public void setLogfirstv(String logfirstv) {
        this.logfirstv = logfirstv;
    }

    @Basic
    @Column(name = "LOGSECONDV")
    public String getLogsecondv() {
        return logsecondv;
    }

    public void setLogsecondv(String logsecondv) {
        this.logsecondv = logsecondv;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PimsSysOperationLogs that = (PimsSysOperationLogs) o;

        if (logid != that.logid) return false;
        if (logcustomer != null ? !logcustomer.equals(that.logcustomer) : that.logcustomer != null) return false;
        if (logtype != null ? !logtype.equals(that.logtype) : that.logtype != null) return false;
        if (logmodulecode != null ? !logmodulecode.equals(that.logmodulecode) : that.logmodulecode != null)
            return false;
        if (loguserid != null ? !loguserid.equals(that.loguserid) : that.loguserid != null) return false;
        if (logusername != null ? !logusername.equals(that.logusername) : that.logusername != null) return false;
        if (logcontent != null ? !logcontent.equals(that.logcontent) : that.logcontent != null) return false;
        if (logtime != null ? !logtime.equals(that.logtime) : that.logtime != null) return false;
        if (logipaddress != null ? !logipaddress.equals(that.logipaddress) : that.logipaddress != null) return false;
        if (logmacaddress != null ? !logmacaddress.equals(that.logmacaddress) : that.logmacaddress != null)
            return false;
        if (logfirstv != null ? !logfirstv.equals(that.logfirstv) : that.logfirstv != null) return false;
        if (logsecondv != null ? !logsecondv.equals(that.logsecondv) : that.logsecondv != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (logid ^ (logid >>> 32));
        result = 31 * result + (logcustomer != null ? logcustomer.hashCode() : 0);
        result = 31 * result + (logtype != null ? logtype.hashCode() : 0);
        result = 31 * result + (logmodulecode != null ? logmodulecode.hashCode() : 0);
        result = 31 * result + (loguserid != null ? loguserid.hashCode() : 0);
        result = 31 * result + (logusername != null ? logusername.hashCode() : 0);
        result = 31 * result + (logcontent != null ? logcontent.hashCode() : 0);
        result = 31 * result + (logtime != null ? logtime.hashCode() : 0);
        result = 31 * result + (logipaddress != null ? logipaddress.hashCode() : 0);
        result = 31 * result + (logmacaddress != null ? logmacaddress.hashCode() : 0);
        result = 31 * result + (logfirstv != null ? logfirstv.hashCode() : 0);
        result = 31 * result + (logsecondv != null ? logsecondv.hashCode() : 0);
        return result;
    }
}
