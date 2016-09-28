package com.pims.model;

import javax.persistence.*;
import java.sql.Time;

/**
 * Created by king on 2016/9/28.
 */
@Entity
@Table(name = "PIMS_PATHOLOGY_PARAFFIN", schema = "KFTEST", catalog = "")
public class PimsPathologyParaffin {
    private long paraffinid;
    private long parsampleid;
    private String parpathologycode;
    private String parname;
    private long parparaffinno;
    private String parparaffincode;
    private String parbarcode;
    private long parpiececount;
    private String parpieceids;
    private Long parnullslidenum;
    private String parpieceparts;
    private Long parissectioned;
    private String parsectioneddoctor;
    private Time parsectionedtime;
    private Long parisprintlabel;
    private String parprintuser;
    private Time parprinttime;
    private String parremaining;
    private String parfirstv;
    private String parsecondv;
    private Long parfirstn;
    private Time parfirstd;
    private Time parcreatetime;
    private String parcreateuse;

    @Id
    @Column(name = "PARAFFINID")
    public long getParaffinid() {
        return paraffinid;
    }

    public void setParaffinid(long paraffinid) {
        this.paraffinid = paraffinid;
    }

    @Basic
    @Column(name = "PARSAMPLEID")
    public long getParsampleid() {
        return parsampleid;
    }

    public void setParsampleid(long parsampleid) {
        this.parsampleid = parsampleid;
    }

    @Basic
    @Column(name = "PARPATHOLOGYCODE")
    public String getParpathologycode() {
        return parpathologycode;
    }

    public void setParpathologycode(String parpathologycode) {
        this.parpathologycode = parpathologycode;
    }

    @Basic
    @Column(name = "PARNAME")
    public String getParname() {
        return parname;
    }

    public void setParname(String parname) {
        this.parname = parname;
    }

    @Basic
    @Column(name = "PARPARAFFINNO")
    public long getParparaffinno() {
        return parparaffinno;
    }

    public void setParparaffinno(long parparaffinno) {
        this.parparaffinno = parparaffinno;
    }

    @Basic
    @Column(name = "PARPARAFFINCODE")
    public String getParparaffincode() {
        return parparaffincode;
    }

    public void setParparaffincode(String parparaffincode) {
        this.parparaffincode = parparaffincode;
    }

    @Basic
    @Column(name = "PARBARCODE")
    public String getParbarcode() {
        return parbarcode;
    }

    public void setParbarcode(String parbarcode) {
        this.parbarcode = parbarcode;
    }

    @Basic
    @Column(name = "PARPIECECOUNT")
    public long getParpiececount() {
        return parpiececount;
    }

    public void setParpiececount(long parpiececount) {
        this.parpiececount = parpiececount;
    }

    @Basic
    @Column(name = "PARPIECEIDS")
    public String getParpieceids() {
        return parpieceids;
    }

    public void setParpieceids(String parpieceids) {
        this.parpieceids = parpieceids;
    }

    @Basic
    @Column(name = "PARNULLSLIDENUM")
    public Long getParnullslidenum() {
        return parnullslidenum;
    }

    public void setParnullslidenum(Long parnullslidenum) {
        this.parnullslidenum = parnullslidenum;
    }

    @Basic
    @Column(name = "PARPIECEPARTS")
    public String getParpieceparts() {
        return parpieceparts;
    }

    public void setParpieceparts(String parpieceparts) {
        this.parpieceparts = parpieceparts;
    }

    @Basic
    @Column(name = "PARISSECTIONED")
    public Long getParissectioned() {
        return parissectioned;
    }

    public void setParissectioned(Long parissectioned) {
        this.parissectioned = parissectioned;
    }

    @Basic
    @Column(name = "PARSECTIONEDDOCTOR")
    public String getParsectioneddoctor() {
        return parsectioneddoctor;
    }

    public void setParsectioneddoctor(String parsectioneddoctor) {
        this.parsectioneddoctor = parsectioneddoctor;
    }

    @Basic
    @Column(name = "PARSECTIONEDTIME")
    public Time getParsectionedtime() {
        return parsectionedtime;
    }

    public void setParsectionedtime(Time parsectionedtime) {
        this.parsectionedtime = parsectionedtime;
    }

    @Basic
    @Column(name = "PARISPRINTLABEL")
    public Long getParisprintlabel() {
        return parisprintlabel;
    }

    public void setParisprintlabel(Long parisprintlabel) {
        this.parisprintlabel = parisprintlabel;
    }

    @Basic
    @Column(name = "PARPRINTUSER")
    public String getParprintuser() {
        return parprintuser;
    }

    public void setParprintuser(String parprintuser) {
        this.parprintuser = parprintuser;
    }

    @Basic
    @Column(name = "PARPRINTTIME")
    public Time getParprinttime() {
        return parprinttime;
    }

    public void setParprinttime(Time parprinttime) {
        this.parprinttime = parprinttime;
    }

    @Basic
    @Column(name = "PARREMAINING")
    public String getParremaining() {
        return parremaining;
    }

    public void setParremaining(String parremaining) {
        this.parremaining = parremaining;
    }

    @Basic
    @Column(name = "PARFIRSTV")
    public String getParfirstv() {
        return parfirstv;
    }

    public void setParfirstv(String parfirstv) {
        this.parfirstv = parfirstv;
    }

    @Basic
    @Column(name = "PARSECONDV")
    public String getParsecondv() {
        return parsecondv;
    }

    public void setParsecondv(String parsecondv) {
        this.parsecondv = parsecondv;
    }

    @Basic
    @Column(name = "PARFIRSTN")
    public Long getParfirstn() {
        return parfirstn;
    }

    public void setParfirstn(Long parfirstn) {
        this.parfirstn = parfirstn;
    }

    @Basic
    @Column(name = "PARFIRSTD")
    public Time getParfirstd() {
        return parfirstd;
    }

    public void setParfirstd(Time parfirstd) {
        this.parfirstd = parfirstd;
    }

    @Basic
    @Column(name = "PARCREATETIME")
    public Time getParcreatetime() {
        return parcreatetime;
    }

    public void setParcreatetime(Time parcreatetime) {
        this.parcreatetime = parcreatetime;
    }

    @Basic
    @Column(name = "PARCREATEUSE")
    public String getParcreateuse() {
        return parcreateuse;
    }

    public void setParcreateuse(String parcreateuse) {
        this.parcreateuse = parcreateuse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PimsPathologyParaffin that = (PimsPathologyParaffin) o;

        if (paraffinid != that.paraffinid) return false;
        if (parsampleid != that.parsampleid) return false;
        if (parparaffinno != that.parparaffinno) return false;
        if (parpiececount != that.parpiececount) return false;
        if (parpathologycode != null ? !parpathologycode.equals(that.parpathologycode) : that.parpathologycode != null)
            return false;
        if (parname != null ? !parname.equals(that.parname) : that.parname != null) return false;
        if (parparaffincode != null ? !parparaffincode.equals(that.parparaffincode) : that.parparaffincode != null)
            return false;
        if (parbarcode != null ? !parbarcode.equals(that.parbarcode) : that.parbarcode != null) return false;
        if (parpieceids != null ? !parpieceids.equals(that.parpieceids) : that.parpieceids != null) return false;
        if (parnullslidenum != null ? !parnullslidenum.equals(that.parnullslidenum) : that.parnullslidenum != null)
            return false;
        if (parpieceparts != null ? !parpieceparts.equals(that.parpieceparts) : that.parpieceparts != null)
            return false;
        if (parissectioned != null ? !parissectioned.equals(that.parissectioned) : that.parissectioned != null)
            return false;
        if (parsectioneddoctor != null ? !parsectioneddoctor.equals(that.parsectioneddoctor) : that.parsectioneddoctor != null)
            return false;
        if (parsectionedtime != null ? !parsectionedtime.equals(that.parsectionedtime) : that.parsectionedtime != null)
            return false;
        if (parisprintlabel != null ? !parisprintlabel.equals(that.parisprintlabel) : that.parisprintlabel != null)
            return false;
        if (parprintuser != null ? !parprintuser.equals(that.parprintuser) : that.parprintuser != null) return false;
        if (parprinttime != null ? !parprinttime.equals(that.parprinttime) : that.parprinttime != null) return false;
        if (parremaining != null ? !parremaining.equals(that.parremaining) : that.parremaining != null) return false;
        if (parfirstv != null ? !parfirstv.equals(that.parfirstv) : that.parfirstv != null) return false;
        if (parsecondv != null ? !parsecondv.equals(that.parsecondv) : that.parsecondv != null) return false;
        if (parfirstn != null ? !parfirstn.equals(that.parfirstn) : that.parfirstn != null) return false;
        if (parfirstd != null ? !parfirstd.equals(that.parfirstd) : that.parfirstd != null) return false;
        if (parcreatetime != null ? !parcreatetime.equals(that.parcreatetime) : that.parcreatetime != null)
            return false;
        if (parcreateuse != null ? !parcreateuse.equals(that.parcreateuse) : that.parcreateuse != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (paraffinid ^ (paraffinid >>> 32));
        result = 31 * result + (int) (parsampleid ^ (parsampleid >>> 32));
        result = 31 * result + (parpathologycode != null ? parpathologycode.hashCode() : 0);
        result = 31 * result + (parname != null ? parname.hashCode() : 0);
        result = 31 * result + (int) (parparaffinno ^ (parparaffinno >>> 32));
        result = 31 * result + (parparaffincode != null ? parparaffincode.hashCode() : 0);
        result = 31 * result + (parbarcode != null ? parbarcode.hashCode() : 0);
        result = 31 * result + (int) (parpiececount ^ (parpiececount >>> 32));
        result = 31 * result + (parpieceids != null ? parpieceids.hashCode() : 0);
        result = 31 * result + (parnullslidenum != null ? parnullslidenum.hashCode() : 0);
        result = 31 * result + (parpieceparts != null ? parpieceparts.hashCode() : 0);
        result = 31 * result + (parissectioned != null ? parissectioned.hashCode() : 0);
        result = 31 * result + (parsectioneddoctor != null ? parsectioneddoctor.hashCode() : 0);
        result = 31 * result + (parsectionedtime != null ? parsectionedtime.hashCode() : 0);
        result = 31 * result + (parisprintlabel != null ? parisprintlabel.hashCode() : 0);
        result = 31 * result + (parprintuser != null ? parprintuser.hashCode() : 0);
        result = 31 * result + (parprinttime != null ? parprinttime.hashCode() : 0);
        result = 31 * result + (parremaining != null ? parremaining.hashCode() : 0);
        result = 31 * result + (parfirstv != null ? parfirstv.hashCode() : 0);
        result = 31 * result + (parsecondv != null ? parsecondv.hashCode() : 0);
        result = 31 * result + (parfirstn != null ? parfirstn.hashCode() : 0);
        result = 31 * result + (parfirstd != null ? parfirstd.hashCode() : 0);
        result = 31 * result + (parcreatetime != null ? parcreatetime.hashCode() : 0);
        result = 31 * result + (parcreateuse != null ? parcreateuse.hashCode() : 0);
        return result;
    }
}
