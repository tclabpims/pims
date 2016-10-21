package com.pims.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by king on 2016/10/21.
 */
@Entity
@Table(name = "PIMS_PATHOLOGY_PIECES")
public class PimsPathologyPieces {
    private long pieceid;
    private long piesampleid;
    private String piepathologycode;
    private String piesamplingno;
    private String pieparts;
    private Long piecounts;
    private String pieunit;
    private String piecode;
    private String piesign;
    private String piespecial;
    private Long pienullslidenum;
    private Long piestate;
    private Date piesamplingtime;
    private String piedoctorid;
    private String piedoctorname;
    private String pierecorderid;
    private String pierecordername;
    private String pieisdeprivation;
    private Date piedeprivationtime;
    private String pieisembed;
    private Date pieembedtime;
    private String pieembeddoctorid;
    private String pieembeddoctorname;
    private String pieparaffinid;
    private String piefirstv;
    private String piesecondv;
    private Long piefirstn;
    private Date piefirstd;

    @Id
    @Column(name = "PIECEID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="SEQ_PIECEID")
    @SequenceGenerator(name = "SEQ_PIECEID", sequenceName = "SEQ_PIECEID", allocationSize=1)
    public long getPieceid() {
        return pieceid;
    }

    public void setPieceid(long pieceid) {
        this.pieceid = pieceid;
    }

    @Basic
    @Column(name = "PIESAMPLEID")
    public long getPiesampleid() {
        return piesampleid;
    }

    public void setPiesampleid(long piesampleid) {
        this.piesampleid = piesampleid;
    }

    @Basic
    @Column(name = "PIEPATHOLOGYCODE")
    public String getPiepathologycode() {
        return piepathologycode;
    }

    public void setPiepathologycode(String piepathologycode) {
        this.piepathologycode = piepathologycode;
    }

    @Basic
    @Column(name = "PIESAMPLINGNO")
    public String getPiesamplingno() {
        return piesamplingno;
    }

    public void setPiesamplingno(String piesamplingno) {
        this.piesamplingno = piesamplingno;
    }

    @Basic
    @Column(name = "PIEPARTS")
    public String getPieparts() {
        return pieparts;
    }

    public void setPieparts(String pieparts) {
        this.pieparts = pieparts;
    }

    @Basic
    @Column(name = "PIECOUNTS")
    public Long getPiecounts() {
        return piecounts;
    }

    public void setPiecounts(Long piecounts) {
        this.piecounts = piecounts;
    }

    @Basic
    @Column(name = "PIEUNIT")
    public String getPieunit() {
        return pieunit;
    }

    public void setPieunit(String pieunit) {
        this.pieunit = pieunit;
    }

    @Basic
    @Column(name = "PIECODE")
    public String getPiecode() {
        return piecode;
    }

    public void setPiecode(String piecode) {
        this.piecode = piecode;
    }

    @Basic
    @Column(name = "PIESIGN")
    public String getPiesign() {
        return piesign;
    }

    public void setPiesign(String piesign) {
        this.piesign = piesign;
    }

    @Basic
    @Column(name = "PIESPECIAL")
    public String getPiespecial() {
        return piespecial;
    }

    public void setPiespecial(String piespecial) {
        this.piespecial = piespecial;
    }

    @Basic
    @Column(name = "PIENULLSLIDENUM")
    public Long getPienullslidenum() {
        return pienullslidenum;
    }

    public void setPienullslidenum(Long pienullslidenum) {
        this.pienullslidenum = pienullslidenum;
    }

    @Basic
    @Column(name = "PIESTATE")
    public Long getPiestate() {
        return piestate;
    }

    public void setPiestate(Long piestate) {
        this.piestate = piestate;
    }

    @Basic
    @Column(name = "PIESAMPLINGTIME")
    public Date getPiesamplingtime() {
        return piesamplingtime;
    }

    public void setPiesamplingtime(Date piesamplingtime) {
        this.piesamplingtime = piesamplingtime;
    }

    @Basic
    @Column(name = "PIEDOCTORID")
    public String getPiedoctorid() {
        return piedoctorid;
    }

    public void setPiedoctorid(String piedoctorid) {
        this.piedoctorid = piedoctorid;
    }

    @Basic
    @Column(name = "PIEDOCTORNAME")
    public String getPiedoctorname() {
        return piedoctorname;
    }

    public void setPiedoctorname(String piedoctorname) {
        this.piedoctorname = piedoctorname;
    }

    @Basic
    @Column(name = "PIERECORDERID")
    public String getPierecorderid() {
        return pierecorderid;
    }

    public void setPierecorderid(String pierecorderid) {
        this.pierecorderid = pierecorderid;
    }

    @Basic
    @Column(name = "PIERECORDERNAME")
    public String getPierecordername() {
        return pierecordername;
    }

    public void setPierecordername(String pierecordername) {
        this.pierecordername = pierecordername;
    }

    @Basic
    @Column(name = "PIEISDEPRIVATION")
    public String getPieisdeprivation() {
        return pieisdeprivation;
    }

    public void setPieisdeprivation(String pieisdeprivation) {
        this.pieisdeprivation = pieisdeprivation;
    }

    @Basic
    @Column(name = "PIEDEPRIVATIONTIME")
    public Date getPiedeprivationtime() {
        return piedeprivationtime;
    }

    public void setPiedeprivationtime(Date piedeprivationtime) {
        this.piedeprivationtime = piedeprivationtime;
    }

    @Basic
    @Column(name = "PIEISEMBED")
    public String getPieisembed() {
        return pieisembed;
    }

    public void setPieisembed(String pieisembed) {
        this.pieisembed = pieisembed;
    }

    @Basic
    @Column(name = "PIEEMBEDTIME")
    public Date getPieembedtime() {
        return pieembedtime;
    }

    public void setPieembedtime(Date pieembedtime) {
        this.pieembedtime = pieembedtime;
    }

    @Basic
    @Column(name = "PIEEMBEDDOCTORID")
    public String getPieembeddoctorid() {
        return pieembeddoctorid;
    }

    public void setPieembeddoctorid(String pieembeddoctorid) {
        this.pieembeddoctorid = pieembeddoctorid;
    }

    @Basic
    @Column(name = "PIEEMBEDDOCTORNAME")
    public String getPieembeddoctorname() {
        return pieembeddoctorname;
    }

    public void setPieembeddoctorname(String pieembeddoctorname) {
        this.pieembeddoctorname = pieembeddoctorname;
    }

    @Basic
    @Column(name = "PIEPARAFFINID")
    public String getPieparaffinid() {
        return pieparaffinid;
    }

    public void setPieparaffinid(String pieparaffinid) {
        this.pieparaffinid = pieparaffinid;
    }

    @Basic
    @Column(name = "PIEFIRSTV")
    public String getPiefirstv() {
        return piefirstv;
    }

    public void setPiefirstv(String piefirstv) {
        this.piefirstv = piefirstv;
    }

    @Basic
    @Column(name = "PIESECONDV")
    public String getPiesecondv() {
        return piesecondv;
    }

    public void setPiesecondv(String piesecondv) {
        this.piesecondv = piesecondv;
    }

    @Basic
    @Column(name = "PIEFIRSTN")
    public Long getPiefirstn() {
        return piefirstn;
    }

    public void setPiefirstn(Long piefirstn) {
        this.piefirstn = piefirstn;
    }

    @Basic
    @Column(name = "PIEFIRSTD")
    public Date getPiefirstd() {
        return piefirstd;
    }

    public void setPiefirstd(Date piefirstd) {
        this.piefirstd = piefirstd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PimsPathologyPieces that = (PimsPathologyPieces) o;

        if (pieceid != that.pieceid) return false;
        if (piesampleid != that.piesampleid) return false;
        if (piepathologycode != null ? !piepathologycode.equals(that.piepathologycode) : that.piepathologycode != null)
            return false;
        if (piesamplingno != null ? !piesamplingno.equals(that.piesamplingno) : that.piesamplingno != null)
            return false;
        if (pieparts != null ? !pieparts.equals(that.pieparts) : that.pieparts != null) return false;
        if (piecounts != null ? !piecounts.equals(that.piecounts) : that.piecounts != null) return false;
        if (pieunit != null ? !pieunit.equals(that.pieunit) : that.pieunit != null) return false;
        if (piecode != null ? !piecode.equals(that.piecode) : that.piecode != null) return false;
        if (piesign != null ? !piesign.equals(that.piesign) : that.piesign != null) return false;
        if (piespecial != null ? !piespecial.equals(that.piespecial) : that.piespecial != null) return false;
        if (pienullslidenum != null ? !pienullslidenum.equals(that.pienullslidenum) : that.pienullslidenum != null)
            return false;
        if (piestate != null ? !piestate.equals(that.piestate) : that.piestate != null) return false;
        if (piesamplingtime != null ? !piesamplingtime.equals(that.piesamplingtime) : that.piesamplingtime != null)
            return false;
        if (piedoctorid != null ? !piedoctorid.equals(that.piedoctorid) : that.piedoctorid != null) return false;
        if (piedoctorname != null ? !piedoctorname.equals(that.piedoctorname) : that.piedoctorname != null)
            return false;
        if (pierecorderid != null ? !pierecorderid.equals(that.pierecorderid) : that.pierecorderid != null)
            return false;
        if (pierecordername != null ? !pierecordername.equals(that.pierecordername) : that.pierecordername != null)
            return false;
        if (pieisdeprivation != null ? !pieisdeprivation.equals(that.pieisdeprivation) : that.pieisdeprivation != null)
            return false;
        if (piedeprivationtime != null ? !piedeprivationtime.equals(that.piedeprivationtime) : that.piedeprivationtime != null)
            return false;
        if (pieisembed != null ? !pieisembed.equals(that.pieisembed) : that.pieisembed != null) return false;
        if (pieembedtime != null ? !pieembedtime.equals(that.pieembedtime) : that.pieembedtime != null) return false;
        if (pieembeddoctorid != null ? !pieembeddoctorid.equals(that.pieembeddoctorid) : that.pieembeddoctorid != null)
            return false;
        if (pieembeddoctorname != null ? !pieembeddoctorname.equals(that.pieembeddoctorname) : that.pieembeddoctorname != null)
            return false;
        if (pieparaffinid != null ? !pieparaffinid.equals(that.pieparaffinid) : that.pieparaffinid != null)
            return false;
        if (piefirstv != null ? !piefirstv.equals(that.piefirstv) : that.piefirstv != null) return false;
        if (piesecondv != null ? !piesecondv.equals(that.piesecondv) : that.piesecondv != null) return false;
        if (piefirstn != null ? !piefirstn.equals(that.piefirstn) : that.piefirstn != null) return false;
        if (piefirstd != null ? !piefirstd.equals(that.piefirstd) : that.piefirstd != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (pieceid ^ (pieceid >>> 32));
        result = 31 * result + (int) (piesampleid ^ (piesampleid >>> 32));
        result = 31 * result + (piepathologycode != null ? piepathologycode.hashCode() : 0);
        result = 31 * result + (piesamplingno != null ? piesamplingno.hashCode() : 0);
        result = 31 * result + (pieparts != null ? pieparts.hashCode() : 0);
        result = 31 * result + (piecounts != null ? piecounts.hashCode() : 0);
        result = 31 * result + (pieunit != null ? pieunit.hashCode() : 0);
        result = 31 * result + (piecode != null ? piecode.hashCode() : 0);
        result = 31 * result + (piesign != null ? piesign.hashCode() : 0);
        result = 31 * result + (piespecial != null ? piespecial.hashCode() : 0);
        result = 31 * result + (pienullslidenum != null ? pienullslidenum.hashCode() : 0);
        result = 31 * result + (piestate != null ? piestate.hashCode() : 0);
        result = 31 * result + (piesamplingtime != null ? piesamplingtime.hashCode() : 0);
        result = 31 * result + (piedoctorid != null ? piedoctorid.hashCode() : 0);
        result = 31 * result + (piedoctorname != null ? piedoctorname.hashCode() : 0);
        result = 31 * result + (pierecorderid != null ? pierecorderid.hashCode() : 0);
        result = 31 * result + (pierecordername != null ? pierecordername.hashCode() : 0);
        result = 31 * result + (pieisdeprivation != null ? pieisdeprivation.hashCode() : 0);
        result = 31 * result + (piedeprivationtime != null ? piedeprivationtime.hashCode() : 0);
        result = 31 * result + (pieisembed != null ? pieisembed.hashCode() : 0);
        result = 31 * result + (pieembedtime != null ? pieembedtime.hashCode() : 0);
        result = 31 * result + (pieembeddoctorid != null ? pieembeddoctorid.hashCode() : 0);
        result = 31 * result + (pieembeddoctorname != null ? pieembeddoctorname.hashCode() : 0);
        result = 31 * result + (pieparaffinid != null ? pieparaffinid.hashCode() : 0);
        result = 31 * result + (piefirstv != null ? piefirstv.hashCode() : 0);
        result = 31 * result + (piesecondv != null ? piesecondv.hashCode() : 0);
        result = 31 * result + (piefirstn != null ? piefirstn.hashCode() : 0);
        result = 31 * result + (piefirstd != null ? piefirstd.hashCode() : 0);
        return result;
    }
}
