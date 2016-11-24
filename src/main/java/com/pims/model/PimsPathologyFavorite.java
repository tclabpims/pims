package com.pims.model;

import com.smart.model.BaseObject;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

/**
 * Created by king on 2016/9/28.
 */
@Entity
@Table(name = "PIMS_PATHOLOGY_FAVORITE")
public class PimsPathologyFavorite extends BaseObject {
    private long favoriteid;
    private long favsampleid;
    private String favpathologycode;
    private long favcustomercode;
    private long favtype;
    private String favowner;
    private String favtitle;
    private String favdescription;
    private Date favpreviewtime;
    private Long favstate;
    private String favbrowsetimes;
    private String favnum;
    private Date favtime;
    private String favfirstv;
    private String favsecondv;
    private String favthirdv;
    private Long favfirstn;
    private Date favfirstd;

    @Id
    @Column(name = "FAVORITEID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="Seq_FavoriteId")
    @SequenceGenerator(name = "Seq_FavoriteId", sequenceName = "Seq_FavoriteId", allocationSize=1)
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
    @Column(name = "FAVCUSTOMERID")
    public long getFavcustomercode() {
        return favcustomercode;
    }

    public void setFavcustomercode(long favcustomercode) {
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
    public Date getFavpreviewtime() {
        return favpreviewtime;
    }

    public void setFavpreviewtime(Date favpreviewtime) {
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
    public Date getFavtime() {
        return favtime;
    }

    public void setFavtime(Date favtime) {
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
    public Date getFavfirstd() {
        return favfirstd;
    }

    public void setFavfirstd(Date favfirstd) {
        this.favfirstd = favfirstd;
    }

    /**
     * Returns a multi-line String with key=value pairs.
     *
     * @return a String representation of this class.
     */
    @Override
    public String toString() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PimsPathologyFavorite favorite = (PimsPathologyFavorite) o;

        if (favoriteid != favorite.favoriteid) return false;
        if (favsampleid != favorite.favsampleid) return false;
        if (favcustomercode != favorite.favcustomercode) return false;
        if (favtype != favorite.favtype) return false;
        if (favpathologycode != null ? !favpathologycode.equals(favorite.favpathologycode) : favorite.favpathologycode != null)
            return false;
        if (favowner != null ? !favowner.equals(favorite.favowner) : favorite.favowner != null) return false;
        if (favtitle != null ? !favtitle.equals(favorite.favtitle) : favorite.favtitle != null) return false;
        if (favdescription != null ? !favdescription.equals(favorite.favdescription) : favorite.favdescription != null)
            return false;
        if (favpreviewtime != null ? !favpreviewtime.equals(favorite.favpreviewtime) : favorite.favpreviewtime != null)
            return false;
        if (favstate != null ? !favstate.equals(favorite.favstate) : favorite.favstate != null) return false;
        if (favbrowsetimes != null ? !favbrowsetimes.equals(favorite.favbrowsetimes) : favorite.favbrowsetimes != null)
            return false;
        if (favnum != null ? !favnum.equals(favorite.favnum) : favorite.favnum != null) return false;
        if (favtime != null ? !favtime.equals(favorite.favtime) : favorite.favtime != null) return false;
        if (favfirstv != null ? !favfirstv.equals(favorite.favfirstv) : favorite.favfirstv != null) return false;
        if (favsecondv != null ? !favsecondv.equals(favorite.favsecondv) : favorite.favsecondv != null) return false;
        if (favthirdv != null ? !favthirdv.equals(favorite.favthirdv) : favorite.favthirdv != null) return false;
        if (favfirstn != null ? !favfirstn.equals(favorite.favfirstn) : favorite.favfirstn != null) return false;
        return favfirstd != null ? favfirstd.equals(favorite.favfirstd) : favorite.favfirstd == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (favoriteid ^ (favoriteid >>> 32));
        result = 31 * result + (int) (favsampleid ^ (favsampleid >>> 32));
        result = 31 * result + (favpathologycode != null ? favpathologycode.hashCode() : 0);
        result = 31 * result + (int) (favcustomercode ^ (favcustomercode >>> 32));
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
