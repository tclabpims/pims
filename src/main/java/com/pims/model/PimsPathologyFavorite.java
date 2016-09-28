package com.pims.model;

import javax.persistence.*;
import java.sql.Time;

/**
 * Created by king on 2016/9/28.
 */
@Entity
@Table(name = "PIMS_PATHOLOGY_FAVORITE", schema = "KFTEST", catalog = "")
public class PimsPathologyFavorite {
    private long favoriteid;
    private long favsampleid;
    private String favpathologycode;
    private String favcustomercode;
    private long favtype;
    private String favowner;
    private String favtitle;
    private String favdescription;
    private Time favpreviewtime;
    private Long favstate;
    private String favbrowsetimes;
    private String favnum;
    private Time favtime;
    private String favfirstv;
    private String favsecondv;
    private String favthirdv;
    private Long favfirstn;
    private Time favfirstd;

    @Id
    @Column(name = "FAVORITEID")
    public long getFavoriteid() {
        return favoriteid;
    }

    public void setFavoriteid(long favoriteid) {
        this.favoriteid = favoriteid;
    }

    @Basic
    @Column(name = "FAVSAMPLEID")
    public long getFavsampleid() {
        return favsampleid;
    }

    public void setFavsampleid(long favsampleid) {
        this.favsampleid = favsampleid;
    }

    @Basic
    @Column(name = "FAVPATHOLOGYCODE")
    public String getFavpathologycode() {
        return favpathologycode;
    }

    public void setFavpathologycode(String favpathologycode) {
        this.favpathologycode = favpathologycode;
    }

    @Basic
    @Column(name = "FAVCUSTOMERCODE")
    public String getFavcustomercode() {
        return favcustomercode;
    }

    public void setFavcustomercode(String favcustomercode) {
        this.favcustomercode = favcustomercode;
    }

    @Basic
    @Column(name = "FAVTYPE")
    public long getFavtype() {
        return favtype;
    }

    public void setFavtype(long favtype) {
        this.favtype = favtype;
    }

    @Basic
    @Column(name = "FAVOWNER")
    public String getFavowner() {
        return favowner;
    }

    public void setFavowner(String favowner) {
        this.favowner = favowner;
    }

    @Basic
    @Column(name = "FAVTITLE")
    public String getFavtitle() {
        return favtitle;
    }

    public void setFavtitle(String favtitle) {
        this.favtitle = favtitle;
    }

    @Basic
    @Column(name = "FAVDESCRIPTION")
    public String getFavdescription() {
        return favdescription;
    }

    public void setFavdescription(String favdescription) {
        this.favdescription = favdescription;
    }

    @Basic
    @Column(name = "FAVPREVIEWTIME")
    public Time getFavpreviewtime() {
        return favpreviewtime;
    }

    public void setFavpreviewtime(Time favpreviewtime) {
        this.favpreviewtime = favpreviewtime;
    }

    @Basic
    @Column(name = "FAVSTATE")
    public Long getFavstate() {
        return favstate;
    }

    public void setFavstate(Long favstate) {
        this.favstate = favstate;
    }

    @Basic
    @Column(name = "FAVBROWSETIMES")
    public String getFavbrowsetimes() {
        return favbrowsetimes;
    }

    public void setFavbrowsetimes(String favbrowsetimes) {
        this.favbrowsetimes = favbrowsetimes;
    }

    @Basic
    @Column(name = "FAVNUM")
    public String getFavnum() {
        return favnum;
    }

    public void setFavnum(String favnum) {
        this.favnum = favnum;
    }

    @Basic
    @Column(name = "FAVTIME")
    public Time getFavtime() {
        return favtime;
    }

    public void setFavtime(Time favtime) {
        this.favtime = favtime;
    }

    @Basic
    @Column(name = "FAVFIRSTV")
    public String getFavfirstv() {
        return favfirstv;
    }

    public void setFavfirstv(String favfirstv) {
        this.favfirstv = favfirstv;
    }

    @Basic
    @Column(name = "FAVSECONDV")
    public String getFavsecondv() {
        return favsecondv;
    }

    public void setFavsecondv(String favsecondv) {
        this.favsecondv = favsecondv;
    }

    @Basic
    @Column(name = "FAVTHIRDV")
    public String getFavthirdv() {
        return favthirdv;
    }

    public void setFavthirdv(String favthirdv) {
        this.favthirdv = favthirdv;
    }

    @Basic
    @Column(name = "FAVFIRSTN")
    public Long getFavfirstn() {
        return favfirstn;
    }

    public void setFavfirstn(Long favfirstn) {
        this.favfirstn = favfirstn;
    }

    @Basic
    @Column(name = "FAVFIRSTD")
    public Time getFavfirstd() {
        return favfirstd;
    }

    public void setFavfirstd(Time favfirstd) {
        this.favfirstd = favfirstd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PimsPathologyFavorite that = (PimsPathologyFavorite) o;

        if (favoriteid != that.favoriteid) return false;
        if (favsampleid != that.favsampleid) return false;
        if (favtype != that.favtype) return false;
        if (favpathologycode != null ? !favpathologycode.equals(that.favpathologycode) : that.favpathologycode != null)
            return false;
        if (favcustomercode != null ? !favcustomercode.equals(that.favcustomercode) : that.favcustomercode != null)
            return false;
        if (favowner != null ? !favowner.equals(that.favowner) : that.favowner != null) return false;
        if (favtitle != null ? !favtitle.equals(that.favtitle) : that.favtitle != null) return false;
        if (favdescription != null ? !favdescription.equals(that.favdescription) : that.favdescription != null)
            return false;
        if (favpreviewtime != null ? !favpreviewtime.equals(that.favpreviewtime) : that.favpreviewtime != null)
            return false;
        if (favstate != null ? !favstate.equals(that.favstate) : that.favstate != null) return false;
        if (favbrowsetimes != null ? !favbrowsetimes.equals(that.favbrowsetimes) : that.favbrowsetimes != null)
            return false;
        if (favnum != null ? !favnum.equals(that.favnum) : that.favnum != null) return false;
        if (favtime != null ? !favtime.equals(that.favtime) : that.favtime != null) return false;
        if (favfirstv != null ? !favfirstv.equals(that.favfirstv) : that.favfirstv != null) return false;
        if (favsecondv != null ? !favsecondv.equals(that.favsecondv) : that.favsecondv != null) return false;
        if (favthirdv != null ? !favthirdv.equals(that.favthirdv) : that.favthirdv != null) return false;
        if (favfirstn != null ? !favfirstn.equals(that.favfirstn) : that.favfirstn != null) return false;
        if (favfirstd != null ? !favfirstd.equals(that.favfirstd) : that.favfirstd != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (favoriteid ^ (favoriteid >>> 32));
        result = 31 * result + (int) (favsampleid ^ (favsampleid >>> 32));
        result = 31 * result + (favpathologycode != null ? favpathologycode.hashCode() : 0);
        result = 31 * result + (favcustomercode != null ? favcustomercode.hashCode() : 0);
        result = 31 * result + (int) (favtype ^ (favtype >>> 32));
        result = 31 * result + (favowner != null ? favowner.hashCode() : 0);
        result = 31 * result + (favtitle != null ? favtitle.hashCode() : 0);
        result = 31 * result + (favdescription != null ? favdescription.hashCode() : 0);
        result = 31 * result + (favpreviewtime != null ? favpreviewtime.hashCode() : 0);
        result = 31 * result + (favstate != null ? favstate.hashCode() : 0);
        result = 31 * result + (favbrowsetimes != null ? favbrowsetimes.hashCode() : 0);
        result = 31 * result + (favnum != null ? favnum.hashCode() : 0);
        result = 31 * result + (favtime != null ? favtime.hashCode() : 0);
        result = 31 * result + (favfirstv != null ? favfirstv.hashCode() : 0);
        result = 31 * result + (favsecondv != null ? favsecondv.hashCode() : 0);
        result = 31 * result + (favthirdv != null ? favthirdv.hashCode() : 0);
        result = 31 * result + (favfirstn != null ? favfirstn.hashCode() : 0);
        result = 31 * result + (favfirstd != null ? favfirstd.hashCode() : 0);
        return result;
    }
}
