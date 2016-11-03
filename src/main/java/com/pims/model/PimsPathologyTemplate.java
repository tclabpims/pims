package com.pims.model;

import com.smart.model.BaseObject;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

/**
 * Created by king on 2016/9/28.
 */
@Entity
@Table(name = "PIMS_PATHOLOGY_TEMPLATE")
public class PimsPathologyTemplate extends BaseObject {
    private long templateid;
    private long temcustomerid;
    private long tempathologyid;
    private long temtype;
    private long temclass;
    private String temownerid;
    private String temownername;
    private String temcontent;
    private String temkey;
    private String tempinyin;
    private String temfivestroke;
    private String temspellcode;
    private String temsort;
    private String temusetimes;
    private String temfirstv;
    private String temsecondv;
    private String temthirdv;
    private Long temfirstn;
    private Date temfirstd;
    private Date temcreatetime;
    private String temcreateuser;

    private String tempathologyname;

    private String temcustomername;

    @Transient
    public String getTempathologyname() {
        return tempathologyname;
    }

    public void setTempathologyname(String tempathologyname) {
        this.tempathologyname = tempathologyname;
    }

    @Transient
    public String getTemcustomername() {
        return temcustomername;
    }

    public void setTemcustomername(String temcustomername) {
        this.temcustomername = temcustomername;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PIMSSYSTEMPLATE")
    @SequenceGenerator(name = "SEQ_PIMSSYSTEMPLATE", sequenceName = "seq_templateid", allocationSize = 1)
    public long getTemplateid() {
        return templateid;
    }

    public void setTemplateid(long templateid) {
        this.templateid = templateid;
    }

    @Basic
    @Column(name = "TEMCUSTOMERID")
    public long getTemcustomerid() {
        return temcustomerid;
    }

    public void setTemcustomerid(long temcustomerid) {
        this.temcustomerid = temcustomerid;
    }

    @Basic
    @Column(name = "TEMPATHOLOGYID")
    public long getTempathologyid() {
        return tempathologyid;
    }

    public void setTempathologyid(long tempathologyid) {
        this.tempathologyid = tempathologyid;
    }

    @Basic
    @Column(name = "TEMTYPE")
    public long getTemtype() {
        return temtype;
    }

    public void setTemtype(long temtype) {
        this.temtype = temtype;
    }

    @Basic
    @Column(name = "TEMCLASS")
    public long getTemclass() {
        return temclass;
    }

    public void setTemclass(long temclass) {
        this.temclass = temclass;
    }

    @Basic
    @Column(name = "TEMOWNERID")
    public String getTemownerid() {
        return temownerid;
    }

    public void setTemownerid(String temownerid) {
        this.temownerid = temownerid;
    }

    @Basic
    @Column(name = "TEMOWNERNAME")
    public String getTemownername() {
        return temownername;
    }

    public void setTemownername(String temownername) {
        this.temownername = temownername;
    }

    @Basic
    @Column(name = "TEMCONTENT")
    public String getTemcontent() {
        return temcontent;
    }

    public void setTemcontent(String temcontent) {
        this.temcontent = temcontent;
    }

    @Basic
    @Column(name = "TEMKEY")
    public String getTemkey() {
        return temkey;
    }

    public void setTemkey(String temkey) {
        this.temkey = temkey;
    }

    @Basic
    @Column(name = "TEMPINYIN")
    public String getTempinyin() {
        return tempinyin;
    }

    public void setTempinyin(String tempinyin) {
        this.tempinyin = tempinyin;
    }

    @Basic
    @Column(name = "TEMFIVESTROKE")
    public String getTemfivestroke() {
        return temfivestroke;
    }

    public void setTemfivestroke(String temfivestroke) {
        this.temfivestroke = temfivestroke;
    }

    @Basic
    @Column(name = "TEMSPELLCODE")
    public String getTemspellcode() {
        return temspellcode;
    }

    public void setTemspellcode(String temspellcode) {
        this.temspellcode = temspellcode;
    }

    @Basic
    @Column(name = "TEMSORT")
    public String getTemsort() {
        return temsort;
    }

    public void setTemsort(String temsort) {
        this.temsort = temsort;
    }

    @Basic
    @Column(name = "TEMUSETIMES")
    public String getTemusetimes() {
        return temusetimes;
    }

    public void setTemusetimes(String temusetimes) {
        this.temusetimes = temusetimes;
    }

    @Basic
    @Column(name = "TEMFIRSTV")
    public String getTemfirstv() {
        return temfirstv;
    }

    public void setTemfirstv(String temfirstv) {
        this.temfirstv = temfirstv;
    }

    @Basic
    @Column(name = "TEMSECONDV")
    public String getTemsecondv() {
        return temsecondv;
    }

    public void setTemsecondv(String temsecondv) {
        this.temsecondv = temsecondv;
    }

    @Basic
    @Column(name = "TEMTHIRDV")
    public String getTemthirdv() {
        return temthirdv;
    }

    public void setTemthirdv(String temthirdv) {
        this.temthirdv = temthirdv;
    }

    @Basic
    @Column(name = "TEMFIRSTN")
    public Long getTemfirstn() {
        return temfirstn;
    }

    public void setTemfirstn(Long temfirstn) {
        this.temfirstn = temfirstn;
    }

    @Basic
    @Column(name = "TEMFIRSTD")
    public Date getTemfirstd() {
        return temfirstd;
    }

    public void setTemfirstd(Date temfirstd) {
        this.temfirstd = temfirstd;
    }

    @Basic
    @Column(name = "TEMCREATETIME")
    public Date getTemcreatetime() {
        return temcreatetime;
    }

    public void setTemcreatetime(Date temcreatetime) {
        this.temcreatetime = temcreatetime;
    }

    @Basic
    @Column(name = "TEMCREATEUSER")
    public String getTemcreateuser() {
        return temcreateuser;
    }

    public void setTemcreateuser(String temcreateuser) {
        this.temcreateuser = temcreateuser;
    }

    /**
     * Returns a multi-line String with key=value pairs.
     *
     * @return a String representation of this class.
     */
    @Override
    public String toString() {
        return getClass().getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PimsPathologyTemplate that = (PimsPathologyTemplate) o;

        if (templateid != that.templateid) return false;
        if (tempathologyid != that.tempathologyid) return false;
        if (temtype != that.temtype) return false;
        if (temclass != that.temclass) return false;
        if (temcustomerid !=that.temcustomerid)
            return false;
        if (temownerid != null ? !temownerid.equals(that.temownerid) : that.temownerid != null) return false;
        if (temownername != null ? !temownername.equals(that.temownername) : that.temownername != null) return false;
        if (temcontent != null ? !temcontent.equals(that.temcontent) : that.temcontent != null) return false;
        if (temkey != null ? !temkey.equals(that.temkey) : that.temkey != null) return false;
        if (tempinyin != null ? !tempinyin.equals(that.tempinyin) : that.tempinyin != null) return false;
        if (temfivestroke != null ? !temfivestroke.equals(that.temfivestroke) : that.temfivestroke != null)
            return false;
        if (temspellcode != null ? !temspellcode.equals(that.temspellcode) : that.temspellcode != null) return false;
        if (temsort != null ? !temsort.equals(that.temsort) : that.temsort != null) return false;
        if (temusetimes != null ? !temusetimes.equals(that.temusetimes) : that.temusetimes != null) return false;
        if (temfirstv != null ? !temfirstv.equals(that.temfirstv) : that.temfirstv != null) return false;
        if (temsecondv != null ? !temsecondv.equals(that.temsecondv) : that.temsecondv != null) return false;
        if (temthirdv != null ? !temthirdv.equals(that.temthirdv) : that.temthirdv != null) return false;
        if (temfirstn != null ? !temfirstn.equals(that.temfirstn) : that.temfirstn != null) return false;
        if (temfirstd != null ? !temfirstd.equals(that.temfirstd) : that.temfirstd != null) return false;
        if (temcreatetime != null ? !temcreatetime.equals(that.temcreatetime) : that.temcreatetime != null)
            return false;
        if (temcreateuser != null ? !temcreateuser.equals(that.temcreateuser) : that.temcreateuser != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (templateid ^ (templateid >>> 32));
        result = 31 * result + (int)(temcustomerid ^ (temcustomerid >>> 32));
        result = 31 * result + (int) (tempathologyid ^ (tempathologyid >>> 32));
        result = 31 * result + (int) (temtype ^ (temtype >>> 32));
        result = 31 * result + (int) (temclass ^ (temclass >>> 32));
        result = 31 * result + (temownerid != null ? temownerid.hashCode() : 0);
        result = 31 * result + (temownername != null ? temownername.hashCode() : 0);
        result = 31 * result + (temcontent != null ? temcontent.hashCode() : 0);
        result = 31 * result + (temkey != null ? temkey.hashCode() : 0);
        result = 31 * result + (tempinyin != null ? tempinyin.hashCode() : 0);
        result = 31 * result + (temfivestroke != null ? temfivestroke.hashCode() : 0);
        result = 31 * result + (temspellcode != null ? temspellcode.hashCode() : 0);
        result = 31 * result + (temsort != null ? temsort.hashCode() : 0);
        result = 31 * result + (temusetimes != null ? temusetimes.hashCode() : 0);
        result = 31 * result + (temfirstv != null ? temfirstv.hashCode() : 0);
        result = 31 * result + (temsecondv != null ? temsecondv.hashCode() : 0);
        result = 31 * result + (temthirdv != null ? temthirdv.hashCode() : 0);
        result = 31 * result + (temfirstn != null ? temfirstn.hashCode() : 0);
        result = 31 * result + (temfirstd != null ? temfirstd.hashCode() : 0);
        result = 31 * result + (temcreatetime != null ? temcreatetime.hashCode() : 0);
        result = 31 * result + (temcreateuser != null ? temcreateuser.hashCode() : 0);
        return result;
    }
}
