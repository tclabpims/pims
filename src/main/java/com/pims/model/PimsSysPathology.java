package com.pims.model;

import com.smart.model.BaseObject;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by king on 2016/9/28.
 * 病种类别POJO
 */
@Entity
@Table(name = "pims_sys_pathology")
public class PimsSysPathology extends BaseObject {
    private long pathologyid;
    private String patnamech;
    private String patnameen;
    private long patuseflag;
    private String patsort;
    private String patreporttitle;
    private String patcoddingprechar;
    private String patcoddinglength;
    private Long patstartcodding;
    private String patclass;
    private String patdefaultdiagnosis;
    private String patreportremark;
    private Long patissampling;
    private Long patisspecialcheck;
    private String patfirstv;
    private String patsecondv;
    private String patthirdv;
    private Long patfirstn;
    private Date patfirstd;
    private Date patcreatetime;
    private String patcreateuser;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PIMSSYSPATHOLOGY")
    @SequenceGenerator(name = "SEQ_PIMSSYSPATHOLOGY", sequenceName = "pimssyspathology", allocationSize = 1)
    public long getPathologyid() {
        return pathologyid;
    }

    public void setPathologyid(long pathologyid) {
        this.pathologyid = pathologyid;
    }

    @Basic
    @Column(name = "PATNAMECH")
    public String getPatnamech() {
        return patnamech;
    }

    public void setPatnamech(String patnamech) {
        this.patnamech = patnamech;
    }

    @Basic
    @Column(name = "PATNAMEEN")
    public String getPatnameen() {
        return patnameen;
    }

    public void setPatnameen(String patnameen) {
        this.patnameen = patnameen;
    }

    @Basic
    @Column(name = "PATUSEFLAG")
    public long getPatuseflag() {
        return patuseflag;
    }

    public void setPatuseflag(long patuseflag) {
        this.patuseflag = patuseflag;
    }

    @Basic
    @Column(name = "PATSORT")
    public String getPatsort() {
        return patsort;
    }

    public void setPatsort(String patsort) {
        this.patsort = patsort;
    }

    @Basic
    @Column(name = "PATREPORTTITLE")
    public String getPatreporttitle() {
        return patreporttitle;
    }

    public void setPatreporttitle(String patreporttitle) {
        this.patreporttitle = patreporttitle;
    }

    @Basic
    @Column(name = "PATCODDINGPRECHAR")
    public String getPatcoddingprechar() {
        return patcoddingprechar;
    }

    public void setPatcoddingprechar(String patcoddingprechar) {
        this.patcoddingprechar = patcoddingprechar;
    }

    @Basic
    @Column(name = "PATCODDINGLENGTH")
    public String getPatcoddinglength() {
        return patcoddinglength;
    }

    public void setPatcoddinglength(String patcoddinglength) {
        this.patcoddinglength = patcoddinglength;
    }

    @Basic
    @Column(name = "PATSTARTCODDING")
    public Long getPatstartcodding() {
        return patstartcodding;
    }

    public void setPatstartcodding(Long patstartcodding) {
        this.patstartcodding = patstartcodding;
    }

    @Basic
    @Column(name = "PATCLASS")
    public String getPatclass() {
        return patclass;
    }

    public void setPatclass(String patclass) {
        this.patclass = patclass;
    }

    @Basic
    @Column(name = "PATDEFAULTDIAGNOSIS")
    public String getPatdefaultdiagnosis() {
        return patdefaultdiagnosis;
    }

    public void setPatdefaultdiagnosis(String patdefaultdiagnosis) {
        this.patdefaultdiagnosis = patdefaultdiagnosis;
    }

    @Basic
    @Column(name = "PATREPORTREMARK")
    public String getPatreportremark() {
        return patreportremark;
    }

    public void setPatreportremark(String patreportremark) {
        this.patreportremark = patreportremark;
    }

    @Basic
    @Column(name = "PATISSAMPLING")
    public Long getPatissampling() {
        return patissampling;
    }

    public void setPatissampling(Long patissampling) {
        this.patissampling = patissampling;
    }

    @Basic
    @Column(name = "PATISSPECIALCHECK")
    public Long getPatisspecialcheck() {
        return patisspecialcheck;
    }

    public void setPatisspecialcheck(Long patisspecialcheck) {
        this.patisspecialcheck = patisspecialcheck;
    }

    @Basic
    @Column(name = "PATFIRSTV")
    public String getPatfirstv() {
        return patfirstv;
    }

    public void setPatfirstv(String patfirstv) {
        this.patfirstv = patfirstv;
    }

    @Basic
    @Column(name = "PATSECONDV")
    public String getPatsecondv() {
        return patsecondv;
    }

    public void setPatsecondv(String patsecondv) {
        this.patsecondv = patsecondv;
    }

    @Basic
    @Column(name = "PATTHIRDV")
    public String getPatthirdv() {
        return patthirdv;
    }

    public void setPatthirdv(String patthirdv) {
        this.patthirdv = patthirdv;
    }

    @Basic
    @Column(name = "PATFIRSTN")
    public Long getPatfirstn() {
        return patfirstn;
    }

    public void setPatfirstn(Long patfirstn) {
        this.patfirstn = patfirstn;
    }

    @Basic
    @Column(name = "PATFIRSTD")
    public Date getPatfirstd() {
        return patfirstd;
    }

    public void setPatfirstd(Date patfirstd) {
        this.patfirstd = patfirstd;
    }

    @Basic
    @Column(name = "PATCREATETIME")
    public Date getPatcreatetime() {
        return patcreatetime;
    }

    public void setPatcreatetime(Date patcreatetime) {
        this.patcreatetime = patcreatetime;
    }

    @Basic
    @Column(name = "PATCREATEUSER")
    public String getPatcreateuser() {
        return patcreateuser;
    }

    public void setPatcreateuser(String patcreateuser) {
        this.patcreateuser = patcreateuser;
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

        PimsSysPathology that = (PimsSysPathology) o;

        if (pathologyid != that.pathologyid) return false;
        if (patuseflag != that.patuseflag) return false;
        if (patnamech != null ? !patnamech.equals(that.patnamech) : that.patnamech != null) return false;
        if (patnameen != null ? !patnameen.equals(that.patnameen) : that.patnameen != null) return false;
        if (patsort != null ? !patsort.equals(that.patsort) : that.patsort != null) return false;
        if (patreporttitle != null ? !patreporttitle.equals(that.patreporttitle) : that.patreporttitle != null)
            return false;
        if (patcoddingprechar != null ? !patcoddingprechar.equals(that.patcoddingprechar) : that.patcoddingprechar != null)
            return false;
        if (patcoddinglength != null ? !patcoddinglength.equals(that.patcoddinglength) : that.patcoddinglength != null)
            return false;
        if (patstartcodding != null ? !patstartcodding.equals(that.patstartcodding) : that.patstartcodding != null)
            return false;
        if (patclass != null ? !patclass.equals(that.patclass) : that.patclass != null) return false;
        if (patdefaultdiagnosis != null ? !patdefaultdiagnosis.equals(that.patdefaultdiagnosis) : that.patdefaultdiagnosis != null)
            return false;
        if (patreportremark != null ? !patreportremark.equals(that.patreportremark) : that.patreportremark != null)
            return false;
        if (patissampling != null ? !patissampling.equals(that.patissampling) : that.patissampling != null)
            return false;
        if (patisspecialcheck != null ? !patisspecialcheck.equals(that.patisspecialcheck) : that.patisspecialcheck != null)
            return false;
        if (patfirstv != null ? !patfirstv.equals(that.patfirstv) : that.patfirstv != null) return false;
        if (patsecondv != null ? !patsecondv.equals(that.patsecondv) : that.patsecondv != null) return false;
        if (patthirdv != null ? !patthirdv.equals(that.patthirdv) : that.patthirdv != null) return false;
        if (patfirstn != null ? !patfirstn.equals(that.patfirstn) : that.patfirstn != null) return false;
        if (patfirstd != null ? !patfirstd.equals(that.patfirstd) : that.patfirstd != null) return false;
        if (patcreatetime != null ? !patcreatetime.equals(that.patcreatetime) : that.patcreatetime != null)
            return false;
        if (patcreateuser != null ? !patcreateuser.equals(that.patcreateuser) : that.patcreateuser != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (pathologyid ^ (pathologyid >>> 32));
        result = 31 * result + (patnamech != null ? patnamech.hashCode() : 0);
        result = 31 * result + (patnameen != null ? patnameen.hashCode() : 0);
        result = 31 * result + (int) (patuseflag ^ (patuseflag >>> 32));
        result = 31 * result + (patsort != null ? patsort.hashCode() : 0);
        result = 31 * result + (patreporttitle != null ? patreporttitle.hashCode() : 0);
        result = 31 * result + (patcoddingprechar != null ? patcoddingprechar.hashCode() : 0);
        result = 31 * result + (patcoddinglength != null ? patcoddinglength.hashCode() : 0);
        result = 31 * result + (patstartcodding != null ? patstartcodding.hashCode() : 0);
        result = 31 * result + (patclass != null ? patclass.hashCode() : 0);
        result = 31 * result + (patdefaultdiagnosis != null ? patdefaultdiagnosis.hashCode() : 0);
        result = 31 * result + (patreportremark != null ? patreportremark.hashCode() : 0);
        result = 31 * result + (patissampling != null ? patissampling.hashCode() : 0);
        result = 31 * result + (patisspecialcheck != null ? patisspecialcheck.hashCode() : 0);
        result = 31 * result + (patfirstv != null ? patfirstv.hashCode() : 0);
        result = 31 * result + (patsecondv != null ? patsecondv.hashCode() : 0);
        result = 31 * result + (patthirdv != null ? patthirdv.hashCode() : 0);
        result = 31 * result + (patfirstn != null ? patfirstn.hashCode() : 0);
        result = 31 * result + (patfirstd != null ? patfirstd.hashCode() : 0);
        result = 31 * result + (patcreatetime != null ? patcreatetime.hashCode() : 0);
        result = 31 * result + (patcreateuser != null ? patcreateuser.hashCode() : 0);
        return result;
    }
}
