package com.pims.model;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

/**
 * Created by king on 2016/9/28.
 */
@Entity
@Table(name = "PIMS_PATHOLOGY_ORDER_CHECK")
public class PimsPathologyOrderCheck {
    private long checkid;
    private long cheorderid;
    private long chechildorderid;
    private String chepathologycode;
    private String paraffincode;
    private long chesampleid;
    private long customercode;
    private String cheorderitemid;
    private String chenamech;
    private String chenameen;
    private String cheitemsort;
    private long cheitemtype;
    private long cheischarge;
    private Long chechargestate;
    private String chetestresult;
    private Date cheresulttime;
    private String cheresultuser;
    private String cheremark;
    private String chefirstv;
    private String chesecondv;
    private String chethirdv;
    private Long chefirstn;
    private Date chefirstd;
    private Date checreatetime;
    private String checreateuser;
    private long finishStatus;

    @Id
    @Column(name = "CHECKID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="Seq_CheckId")
    @SequenceGenerator(name = "Seq_CheckId", sequenceName = "Seq_CheckId", allocationSize=1)
    public long getCheckid() {
        return checkid;
    }

    public void setCheckid(long checkid) {
        this.checkid = checkid;
    }

    @Column(name="PARAFFINCODE")
    public String getParaffincode() {
        return paraffincode;
    }

    public void setParaffincode(String paraffincode) {
        this.paraffincode = paraffincode;
    }

    @Column(name="FINISHSTATUS")
    public long getFinishStatus() {
        return finishStatus;
    }

    public void setFinishStatus(long finishStatus) {
        this.finishStatus = finishStatus;
    }

    @Basic
    @Column(name = "CHEORDERID")
    public long getCheorderid() {
        return cheorderid;
    }

    public void setCheorderid(long cheorderid) {
        this.cheorderid = cheorderid;
    }

    @Basic
    @Column(name = "CHECHILDORDERID")
    public long getChechildorderid() {
        return chechildorderid;
    }

    public void setChechildorderid(long chechildorderid) {
        this.chechildorderid = chechildorderid;
    }

    @Basic
    @Column(name = "CHEPATHOLOGYCODE")
    public String getChepathologycode() {
        return chepathologycode;
    }

    public void setChepathologycode(String chepathologycode) {
        this.chepathologycode = chepathologycode;
    }

    @Basic
    @Column(name = "CHESAMPLEID")
    public long getChesampleid() {
        return chesampleid;
    }

    public void setChesampleid(long chesampleid) {
        this.chesampleid = chesampleid;
    }

    @Basic
    @Column(name = "CHECUSTOMERID")
    public long getCustomercode() {
        return customercode;
    }

    public void setCustomercode(long customercode) {
        this.customercode = customercode;
    }

    @Basic
    @Column(name = "CHEORDERITEMID")
    public String getCheorderitemid() {
        return cheorderitemid;
    }

    public void setCheorderitemid(String cheorderitemid) {
        this.cheorderitemid = cheorderitemid;
    }

    @Basic
    @Column(name = "CHENAMECH")
    public String getChenamech() {
        return chenamech;
    }

    public void setChenamech(String chenamech) {
        this.chenamech = chenamech;
    }

    @Basic
    @Column(name = "CHENAMEEN")
    public String getChenameen() {
        return chenameen;
    }

    public void setChenameen(String chenameen) {
        this.chenameen = chenameen;
    }

    @Basic
    @Column(name = "CHEITEMSORT")
    public String getCheitemsort() {
        return cheitemsort;
    }

    public void setCheitemsort(String cheitemsort) {
        this.cheitemsort = cheitemsort;
    }

    @Basic
    @Column(name = "CHEITEMTYPE")
    public long getCheitemtype() {
        return cheitemtype;
    }

    public void setCheitemtype(long cheitemtype) {
        this.cheitemtype = cheitemtype;
    }

    @Basic
    @Column(name = "CHEISCHARGE")
    public long getCheischarge() {
        return cheischarge;
    }

    public void setCheischarge(long cheischarge) {
        this.cheischarge = cheischarge;
    }

    @Basic
    @Column(name = "CHECHARGESTATE")
    public Long getChechargestate() {
        return chechargestate;
    }

    public void setChechargestate(Long chechargestate) {
        this.chechargestate = chechargestate;
    }

    @Basic
    @Column(name = "CHETESTRESULT")
    public String getChetestresult() {
        return chetestresult;
    }

    public void setChetestresult(String chetestresult) {
        this.chetestresult = chetestresult;
    }

    @Basic
    @Column(name = "CHERESULTTIME")
    public Date getCheresulttime() {
        return cheresulttime;
    }

    public void setCheresulttime(Date cheresulttime) {
        this.cheresulttime = cheresulttime;
    }

    @Basic
    @Column(name = "CHERESULTUSER")
    public String getCheresultuser() {
        return cheresultuser;
    }

    public void setCheresultuser(String cheresultuser) {
        this.cheresultuser = cheresultuser;
    }

    @Basic
    @Column(name = "CHEREMARK")
    public String getCheremark() {
        return cheremark;
    }

    public void setCheremark(String cheremark) {
        this.cheremark = cheremark;
    }

    @Basic
    @Column(name = "CHEFIRSTV")
    public String getChefirstv() {
        return chefirstv;
    }

    public void setChefirstv(String chefirstv) {
        this.chefirstv = chefirstv;
    }

    @Basic
    @Column(name = "CHESECONDV")
    public String getChesecondv() {
        return chesecondv;
    }

    public void setChesecondv(String chesecondv) {
        this.chesecondv = chesecondv;
    }

    @Basic
    @Column(name = "CHETHIRDV")
    public String getChethirdv() {
        return chethirdv;
    }

    public void setChethirdv(String chethirdv) {
        this.chethirdv = chethirdv;
    }

    @Basic
    @Column(name = "CHEFIRSTN")
    public Long getChefirstn() {
        return chefirstn;
    }

    public void setChefirstn(Long chefirstn) {
        this.chefirstn = chefirstn;
    }

    @Basic
    @Column(name = "CHEFIRSTD")
    public Date getChefirstd() {
        return chefirstd;
    }

    public void setChefirstd(Date chefirstd) {
        this.chefirstd = chefirstd;
    }

    @Basic
    @Column(name = "CHECREATETIME")
    public Date getChecreatetime() {
        return checreatetime;
    }

    public void setChecreatetime(Date checreatetime) {
        this.checreatetime = checreatetime;
    }

    @Basic
    @Column(name = "CHECREATEUSER")
    public String getChecreateuser() {
        return checreateuser;
    }

    public void setChecreateuser(String checreateuser) {
        this.checreateuser = checreateuser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PimsPathologyOrderCheck that = (PimsPathologyOrderCheck) o;

        if (checkid != that.checkid) return false;
        if (cheorderid != that.cheorderid) return false;
        if (chechildorderid != that.chechildorderid) return false;
        if (chesampleid != that.chesampleid) return false;
        if (customercode != that.customercode) return false;
        if (cheitemtype != that.cheitemtype) return false;
        if (cheischarge != that.cheischarge) return false;
        if (chepathologycode != null ? !chepathologycode.equals(that.chepathologycode) : that.chepathologycode != null)
            return false;
        if (cheorderitemid != null ? !cheorderitemid.equals(that.cheorderitemid) : that.cheorderitemid != null)
            return false;
        if (chenamech != null ? !chenamech.equals(that.chenamech) : that.chenamech != null) return false;
        if (chenameen != null ? !chenameen.equals(that.chenameen) : that.chenameen != null) return false;
        if (cheitemsort != null ? !cheitemsort.equals(that.cheitemsort) : that.cheitemsort != null) return false;
        if (chechargestate != null ? !chechargestate.equals(that.chechargestate) : that.chechargestate != null)
            return false;
        if (chetestresult != null ? !chetestresult.equals(that.chetestresult) : that.chetestresult != null)
            return false;
        if (cheresulttime != null ? !cheresulttime.equals(that.cheresulttime) : that.cheresulttime != null)
            return false;
        if (cheresultuser != null ? !cheresultuser.equals(that.cheresultuser) : that.cheresultuser != null)
            return false;
        if (cheremark != null ? !cheremark.equals(that.cheremark) : that.cheremark != null) return false;
        if (chefirstv != null ? !chefirstv.equals(that.chefirstv) : that.chefirstv != null) return false;
        if (chesecondv != null ? !chesecondv.equals(that.chesecondv) : that.chesecondv != null) return false;
        if (chethirdv != null ? !chethirdv.equals(that.chethirdv) : that.chethirdv != null) return false;
        if (chefirstn != null ? !chefirstn.equals(that.chefirstn) : that.chefirstn != null) return false;
        if (chefirstd != null ? !chefirstd.equals(that.chefirstd) : that.chefirstd != null) return false;
        if (checreatetime != null ? !checreatetime.equals(that.checreatetime) : that.checreatetime != null)
            return false;
        return checreateuser != null ? checreateuser.equals(that.checreateuser) : that.checreateuser == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (checkid ^ (checkid >>> 32));
        result = 31 * result + (int) (cheorderid ^ (cheorderid >>> 32));
        result = 31 * result + (int) (chechildorderid ^ (chechildorderid >>> 32));
        result = 31 * result + (chepathologycode != null ? chepathologycode.hashCode() : 0);
        result = 31 * result + (int) (chesampleid ^ (chesampleid >>> 32));
        result = 31 * result + (int) (customercode ^ (customercode >>> 32));
        result = 31 * result + (cheorderitemid != null ? cheorderitemid.hashCode() : 0);
        result = 31 * result + (chenamech != null ? chenamech.hashCode() : 0);
        result = 31 * result + (chenameen != null ? chenameen.hashCode() : 0);
        result = 31 * result + (cheitemsort != null ? cheitemsort.hashCode() : 0);
        result = 31 * result + (int) (cheitemtype ^ (cheitemtype >>> 32));
        result = 31 * result + (int) (cheischarge ^ (cheischarge >>> 32));
        result = 31 * result + (chechargestate != null ? chechargestate.hashCode() : 0);
        result = 31 * result + (chetestresult != null ? chetestresult.hashCode() : 0);
        result = 31 * result + (cheresulttime != null ? cheresulttime.hashCode() : 0);
        result = 31 * result + (cheresultuser != null ? cheresultuser.hashCode() : 0);
        result = 31 * result + (cheremark != null ? cheremark.hashCode() : 0);
        result = 31 * result + (chefirstv != null ? chefirstv.hashCode() : 0);
        result = 31 * result + (chesecondv != null ? chesecondv.hashCode() : 0);
        result = 31 * result + (chethirdv != null ? chethirdv.hashCode() : 0);
        result = 31 * result + (chefirstn != null ? chefirstn.hashCode() : 0);
        result = 31 * result + (chefirstd != null ? chefirstd.hashCode() : 0);
        result = 31 * result + (checreatetime != null ? checreatetime.hashCode() : 0);
        result = 31 * result + (checreateuser != null ? checreateuser.hashCode() : 0);
        return result;
    }
}
