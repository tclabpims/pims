package com.pims.model;

import javax.persistence.*;
import java.sql.Time;

/**
 * Created by king on 2016/9/28.
 */
@Entity
@Table(name = "PIMS_PATHOLOGY_CONSULTATION", schema = "KFTEST", catalog = "")
public class PimsPathologyConsultation {
    private long consultationid;
    private long consampleid;
    private String conpathologycode;
    private String concustomercode;
    private String consponsoreduserid;
    private String consponsoredusername;
    private Time consponsoredtime;
    private long conconsultationstate;
    private String confinisheduserid;
    private String confinishedusername;
    private Time confinishedtime;
    private String confinishedremark;
    private String confirstv;
    private String consecondv;
    private String conthirdv;
    private Long confirstn;
    private Long consecondn;
    private Time confirstd;
    private Time consecondd;

    @Id
    @Column(name = "CONSULTATIONID")
    public long getConsultationid() {
        return consultationid;
    }

    public void setConsultationid(long consultationid) {
        this.consultationid = consultationid;
    }

    @Basic
    @Column(name = "CONSAMPLEID")
    public long getConsampleid() {
        return consampleid;
    }

    public void setConsampleid(long consampleid) {
        this.consampleid = consampleid;
    }

    @Basic
    @Column(name = "CONPATHOLOGYCODE")
    public String getConpathologycode() {
        return conpathologycode;
    }

    public void setConpathologycode(String conpathologycode) {
        this.conpathologycode = conpathologycode;
    }

    @Basic
    @Column(name = "CONCUSTOMERCODE")
    public String getConcustomercode() {
        return concustomercode;
    }

    public void setConcustomercode(String concustomercode) {
        this.concustomercode = concustomercode;
    }

    @Basic
    @Column(name = "CONSPONSOREDUSERID")
    public String getConsponsoreduserid() {
        return consponsoreduserid;
    }

    public void setConsponsoreduserid(String consponsoreduserid) {
        this.consponsoreduserid = consponsoreduserid;
    }

    @Basic
    @Column(name = "CONSPONSOREDUSERNAME")
    public String getConsponsoredusername() {
        return consponsoredusername;
    }

    public void setConsponsoredusername(String consponsoredusername) {
        this.consponsoredusername = consponsoredusername;
    }

    @Basic
    @Column(name = "CONSPONSOREDTIME")
    public Time getConsponsoredtime() {
        return consponsoredtime;
    }

    public void setConsponsoredtime(Time consponsoredtime) {
        this.consponsoredtime = consponsoredtime;
    }

    @Basic
    @Column(name = "CONCONSULTATIONSTATE")
    public long getConconsultationstate() {
        return conconsultationstate;
    }

    public void setConconsultationstate(long conconsultationstate) {
        this.conconsultationstate = conconsultationstate;
    }

    @Basic
    @Column(name = "CONFINISHEDUSERID")
    public String getConfinisheduserid() {
        return confinisheduserid;
    }

    public void setConfinisheduserid(String confinisheduserid) {
        this.confinisheduserid = confinisheduserid;
    }

    @Basic
    @Column(name = "CONFINISHEDUSERNAME")
    public String getConfinishedusername() {
        return confinishedusername;
    }

    public void setConfinishedusername(String confinishedusername) {
        this.confinishedusername = confinishedusername;
    }

    @Basic
    @Column(name = "CONFINISHEDTIME")
    public Time getConfinishedtime() {
        return confinishedtime;
    }

    public void setConfinishedtime(Time confinishedtime) {
        this.confinishedtime = confinishedtime;
    }

    @Basic
    @Column(name = "CONFINISHEDREMARK")
    public String getConfinishedremark() {
        return confinishedremark;
    }

    public void setConfinishedremark(String confinishedremark) {
        this.confinishedremark = confinishedremark;
    }

    @Basic
    @Column(name = "CONFIRSTV")
    public String getConfirstv() {
        return confirstv;
    }

    public void setConfirstv(String confirstv) {
        this.confirstv = confirstv;
    }

    @Basic
    @Column(name = "CONSECONDV")
    public String getConsecondv() {
        return consecondv;
    }

    public void setConsecondv(String consecondv) {
        this.consecondv = consecondv;
    }

    @Basic
    @Column(name = "CONTHIRDV")
    public String getConthirdv() {
        return conthirdv;
    }

    public void setConthirdv(String conthirdv) {
        this.conthirdv = conthirdv;
    }

    @Basic
    @Column(name = "CONFIRSTN")
    public Long getConfirstn() {
        return confirstn;
    }

    public void setConfirstn(Long confirstn) {
        this.confirstn = confirstn;
    }

    @Basic
    @Column(name = "CONSECONDN")
    public Long getConsecondn() {
        return consecondn;
    }

    public void setConsecondn(Long consecondn) {
        this.consecondn = consecondn;
    }

    @Basic
    @Column(name = "CONFIRSTD")
    public Time getConfirstd() {
        return confirstd;
    }

    public void setConfirstd(Time confirstd) {
        this.confirstd = confirstd;
    }

    @Basic
    @Column(name = "CONSECONDD")
    public Time getConsecondd() {
        return consecondd;
    }

    public void setConsecondd(Time consecondd) {
        this.consecondd = consecondd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PimsPathologyConsultation that = (PimsPathologyConsultation) o;

        if (consultationid != that.consultationid) return false;
        if (consampleid != that.consampleid) return false;
        if (conconsultationstate != that.conconsultationstate) return false;
        if (conpathologycode != null ? !conpathologycode.equals(that.conpathologycode) : that.conpathologycode != null)
            return false;
        if (concustomercode != null ? !concustomercode.equals(that.concustomercode) : that.concustomercode != null)
            return false;
        if (consponsoreduserid != null ? !consponsoreduserid.equals(that.consponsoreduserid) : that.consponsoreduserid != null)
            return false;
        if (consponsoredusername != null ? !consponsoredusername.equals(that.consponsoredusername) : that.consponsoredusername != null)
            return false;
        if (consponsoredtime != null ? !consponsoredtime.equals(that.consponsoredtime) : that.consponsoredtime != null)
            return false;
        if (confinisheduserid != null ? !confinisheduserid.equals(that.confinisheduserid) : that.confinisheduserid != null)
            return false;
        if (confinishedusername != null ? !confinishedusername.equals(that.confinishedusername) : that.confinishedusername != null)
            return false;
        if (confinishedtime != null ? !confinishedtime.equals(that.confinishedtime) : that.confinishedtime != null)
            return false;
        if (confinishedremark != null ? !confinishedremark.equals(that.confinishedremark) : that.confinishedremark != null)
            return false;
        if (confirstv != null ? !confirstv.equals(that.confirstv) : that.confirstv != null) return false;
        if (consecondv != null ? !consecondv.equals(that.consecondv) : that.consecondv != null) return false;
        if (conthirdv != null ? !conthirdv.equals(that.conthirdv) : that.conthirdv != null) return false;
        if (confirstn != null ? !confirstn.equals(that.confirstn) : that.confirstn != null) return false;
        if (consecondn != null ? !consecondn.equals(that.consecondn) : that.consecondn != null) return false;
        if (confirstd != null ? !confirstd.equals(that.confirstd) : that.confirstd != null) return false;
        if (consecondd != null ? !consecondd.equals(that.consecondd) : that.consecondd != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (consultationid ^ (consultationid >>> 32));
        result = 31 * result + (int) (consampleid ^ (consampleid >>> 32));
        result = 31 * result + (conpathologycode != null ? conpathologycode.hashCode() : 0);
        result = 31 * result + (concustomercode != null ? concustomercode.hashCode() : 0);
        result = 31 * result + (consponsoreduserid != null ? consponsoreduserid.hashCode() : 0);
        result = 31 * result + (consponsoredusername != null ? consponsoredusername.hashCode() : 0);
        result = 31 * result + (consponsoredtime != null ? consponsoredtime.hashCode() : 0);
        result = 31 * result + (int) (conconsultationstate ^ (conconsultationstate >>> 32));
        result = 31 * result + (confinisheduserid != null ? confinisheduserid.hashCode() : 0);
        result = 31 * result + (confinishedusername != null ? confinishedusername.hashCode() : 0);
        result = 31 * result + (confinishedtime != null ? confinishedtime.hashCode() : 0);
        result = 31 * result + (confinishedremark != null ? confinishedremark.hashCode() : 0);
        result = 31 * result + (confirstv != null ? confirstv.hashCode() : 0);
        result = 31 * result + (consecondv != null ? consecondv.hashCode() : 0);
        result = 31 * result + (conthirdv != null ? conthirdv.hashCode() : 0);
        result = 31 * result + (confirstn != null ? confirstn.hashCode() : 0);
        result = 31 * result + (consecondn != null ? consecondn.hashCode() : 0);
        result = 31 * result + (confirstd != null ? confirstd.hashCode() : 0);
        result = 31 * result + (consecondd != null ? consecondd.hashCode() : 0);
        return result;
    }
}
