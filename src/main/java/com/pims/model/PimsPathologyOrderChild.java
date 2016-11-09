package com.pims.model;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

/**
 * Created by king on 2016/9/28.
 */
@Entity
@Table(name = "PIMS_PATHOLOGY_ORDER_CHILD")
public class PimsPathologyOrderChild {
    private long testItemId;
    private long childorderid;
    private long chiorderid;
    private String chiordercode;
    private String chipathologycode;
    private long chicustomercode;
    private long chisampleid;
    private long chiparaffinid;
    private long chiparaffinno;
    private String chiparaffincode;
    private Date chireqtime;
    private String chirequserid;
    private String chirequsername;
    private Long chiordertype;
    private Long chihandletype;
    private Long chiorderstate;
    private String chicontent;
    private Long chiisdelete;
    private Long chislidenum;
    private Long chinullslidenum;
    private Date chireceivetime;
    private String chireceiverid;
    private String chireceivername;
    private Date chiexectime;
    private String chiexecuserid;
    private String chiexecusername;
    private String chifirstv;
    private String chisecondv;
    private String chithirdv;
    private Long chifirstn;
    private Date chifirstd;
    private Date chicreatetime;
    private String chicreateuser;

    @Column(name="TESTITEMID")
    public long getTestItemId() {
        return testItemId;
    }

    public void setTestItemId(long testItemId) {
        this.testItemId = testItemId;
    }

    @Id
    @Column(name = "CHILDORDERID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="Seq_Order_ChildId")
    @SequenceGenerator(name = "Seq_Order_ChildId", sequenceName = "Seq_Order_ChildId", allocationSize=1)
    public long getChildorderid() {
        return childorderid;
    }

    public void setChildorderid(long childorderid) {
        this.childorderid = childorderid;
    }

    @Basic
    @Column(name = "CHIORDERID")
    public long getChiorderid() {
        return chiorderid;
    }

    public void setChiorderid(long chiorderid) {
        this.chiorderid = chiorderid;
    }

    @Basic
    @Column(name = "CHIORDERCODE")
    public String getChiordercode() {
        return chiordercode;
    }

    public void setChiordercode(String chiordercode) {
        this.chiordercode = chiordercode;
    }

    @Basic
    @Column(name = "CHIPATHOLOGYCODE")
    public String getChipathologycode() {
        return chipathologycode;
    }

    public void setChipathologycode(String chipathologycode) {
        this.chipathologycode = chipathologycode;
    }

    @Basic
    @Column(name = "CHICUSTOMERID")
    public long getChicustomercode() {
        return chicustomercode;
    }

    public void setChicustomercode(long chicustomercode) {
        this.chicustomercode = chicustomercode;
    }

    @Basic
    @Column(name = "CHISAMPLEID")
    public long getChisampleid() {
        return chisampleid;
    }

    public void setChisampleid(long chisampleid) {
        this.chisampleid = chisampleid;
    }

    @Basic
    @Column(name = "CHIPARAFFINID")
    public long getChiparaffinid() {
        return chiparaffinid;
    }

    public void setChiparaffinid(long chiparaffinid) {
        this.chiparaffinid = chiparaffinid;
    }

    @Basic
    @Column(name = "CHIPARAFFINNO")
    public long getChiparaffinno() {
        return chiparaffinno;
    }

    public void setChiparaffinno(long chiparaffinno) {
        this.chiparaffinno = chiparaffinno;
    }

    @Basic
    @Column(name = "CHIPARAFFINCODE")
    public String getChiparaffincode() {
        return chiparaffincode;
    }

    public void setChiparaffincode(String chiparaffincode) {
        this.chiparaffincode = chiparaffincode;
    }

    @Basic
    @Column(name = "CHIREQTIME")
    public Date getChireqtime() {
        return chireqtime;
    }

    public void setChireqtime(Date chireqtime) {
        this.chireqtime = chireqtime;
    }

    @Basic
    @Column(name = "CHIREQUSERID")
    public String getChirequserid() {
        return chirequserid;
    }

    public void setChirequserid(String chirequserid) {
        this.chirequserid = chirequserid;
    }

    @Basic
    @Column(name = "CHIREQUSERNAME")
    public String getChirequsername() {
        return chirequsername;
    }

    public void setChirequsername(String chirequsername) {
        this.chirequsername = chirequsername;
    }

    @Basic
    @Column(name = "CHIORDERTYPE")
    public Long getChiordertype() {
        return chiordertype;
    }

    public void setChiordertype(Long chiordertype) {
        this.chiordertype = chiordertype;
    }

    @Basic
    @Column(name = "CHIHANDLETYPE")
    public Long getChihandletype() {
        return chihandletype;
    }

    public void setChihandletype(Long chihandletype) {
        this.chihandletype = chihandletype;
    }

    @Basic
    @Column(name = "CHIORDERSTATE")
    public Long getChiorderstate() {
        return chiorderstate;
    }

    public void setChiorderstate(Long chiorderstate) {
        this.chiorderstate = chiorderstate;
    }

    @Basic
    @Column(name = "CHICONTENT")
    public String getChicontent() {
        return chicontent;
    }

    public void setChicontent(String chicontent) {
        this.chicontent = chicontent;
    }

    @Basic
    @Column(name = "CHIISDELETE")
    public Long getChiisdelete() {
        return chiisdelete;
    }

    public void setChiisdelete(Long chiisdelete) {
        this.chiisdelete = chiisdelete;
    }

    @Basic
    @Column(name = "CHISLIDENUM")
    public Long getChislidenum() {
        return chislidenum;
    }

    public void setChislidenum(Long chislidenum) {
        this.chislidenum = chislidenum;
    }

    @Basic
    @Column(name = "CHINULLSLIDENUM")
    public Long getChinullslidenum() {
        return chinullslidenum;
    }

    public void setChinullslidenum(Long chinullslidenum) {
        this.chinullslidenum = chinullslidenum;
    }

    @Basic
    @Column(name = "CHIRECEIVETIME")
    public Date getChireceivetime() {
        return chireceivetime;
    }

    public void setChireceivetime(Date chireceivetime) {
        this.chireceivetime = chireceivetime;
    }

    @Basic
    @Column(name = "CHIRECEIVERID")
    public String getChireceiverid() {
        return chireceiverid;
    }

    public void setChireceiverid(String chireceiverid) {
        this.chireceiverid = chireceiverid;
    }

    @Basic
    @Column(name = "CHIRECEIVERNAME")
    public String getChireceivername() {
        return chireceivername;
    }

    public void setChireceivername(String chireceivername) {
        this.chireceivername = chireceivername;
    }

    @Basic
    @Column(name = "CHIEXECTIME")
    public Date getChiexectime() {
        return chiexectime;
    }

    public void setChiexectime(Date chiexectime) {
        this.chiexectime = chiexectime;
    }

    @Basic
    @Column(name = "CHIEXECUSERID")
    public String getChiexecuserid() {
        return chiexecuserid;
    }

    public void setChiexecuserid(String chiexecuserid) {
        this.chiexecuserid = chiexecuserid;
    }

    @Basic
    @Column(name = "CHIEXECUSERNAME")
    public String getChiexecusername() {
        return chiexecusername;
    }

    public void setChiexecusername(String chiexecusername) {
        this.chiexecusername = chiexecusername;
    }

    @Basic
    @Column(name = "CHIFIRSTV")
    public String getChifirstv() {
        return chifirstv;
    }

    public void setChifirstv(String chifirstv) {
        this.chifirstv = chifirstv;
    }

    @Basic
    @Column(name = "CHISECONDV")
    public String getChisecondv() {
        return chisecondv;
    }

    public void setChisecondv(String chisecondv) {
        this.chisecondv = chisecondv;
    }

    @Basic
    @Column(name = "CHITHIRDV")
    public String getChithirdv() {
        return chithirdv;
    }

    public void setChithirdv(String chithirdv) {
        this.chithirdv = chithirdv;
    }

    @Basic
    @Column(name = "CHIFIRSTN")
    public Long getChifirstn() {
        return chifirstn;
    }

    public void setChifirstn(Long chifirstn) {
        this.chifirstn = chifirstn;
    }

    @Basic
    @Column(name = "CHIFIRSTD")
    public Date getChifirstd() {
        return chifirstd;
    }

    public void setChifirstd(Date chifirstd) {
        this.chifirstd = chifirstd;
    }

    @Basic
    @Column(name = "CHICREATETIME")
    public Date getChicreatetime() {
        return chicreatetime;
    }

    public void setChicreatetime(Date chicreatetime) {
        this.chicreatetime = chicreatetime;
    }

    @Basic
    @Column(name = "CHICREATEUSER")
    public String getChicreateuser() {
        return chicreateuser;
    }

    public void setChicreateuser(String chicreateuser) {
        this.chicreateuser = chicreateuser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PimsPathologyOrderChild that = (PimsPathologyOrderChild) o;

        if (childorderid != that.childorderid) return false;
        if (chiorderid != that.chiorderid) return false;
        if (chicustomercode != that.chicustomercode) return false;
        if (chisampleid != that.chisampleid) return false;
        if (chiparaffinid != that.chiparaffinid) return false;
        if (chiparaffinno != that.chiparaffinno) return false;
        if (chiordercode != null ? !chiordercode.equals(that.chiordercode) : that.chiordercode != null) return false;
        if (chipathologycode != null ? !chipathologycode.equals(that.chipathologycode) : that.chipathologycode != null)
            return false;
        if (chiparaffincode != null ? !chiparaffincode.equals(that.chiparaffincode) : that.chiparaffincode != null)
            return false;
        if (chireqtime != null ? !chireqtime.equals(that.chireqtime) : that.chireqtime != null) return false;
        if (chirequserid != null ? !chirequserid.equals(that.chirequserid) : that.chirequserid != null) return false;
        if (chirequsername != null ? !chirequsername.equals(that.chirequsername) : that.chirequsername != null)
            return false;
        if (chiordertype != null ? !chiordertype.equals(that.chiordertype) : that.chiordertype != null) return false;
        if (chihandletype != null ? !chihandletype.equals(that.chihandletype) : that.chihandletype != null)
            return false;
        if (chiorderstate != null ? !chiorderstate.equals(that.chiorderstate) : that.chiorderstate != null)
            return false;
        if (chicontent != null ? !chicontent.equals(that.chicontent) : that.chicontent != null) return false;
        if (chiisdelete != null ? !chiisdelete.equals(that.chiisdelete) : that.chiisdelete != null) return false;
        if (chislidenum != null ? !chislidenum.equals(that.chislidenum) : that.chislidenum != null) return false;
        if (chinullslidenum != null ? !chinullslidenum.equals(that.chinullslidenum) : that.chinullslidenum != null)
            return false;
        if (chireceivetime != null ? !chireceivetime.equals(that.chireceivetime) : that.chireceivetime != null)
            return false;
        if (chireceiverid != null ? !chireceiverid.equals(that.chireceiverid) : that.chireceiverid != null)
            return false;
        if (chireceivername != null ? !chireceivername.equals(that.chireceivername) : that.chireceivername != null)
            return false;
        if (chiexectime != null ? !chiexectime.equals(that.chiexectime) : that.chiexectime != null) return false;
        if (chiexecuserid != null ? !chiexecuserid.equals(that.chiexecuserid) : that.chiexecuserid != null)
            return false;
        if (chiexecusername != null ? !chiexecusername.equals(that.chiexecusername) : that.chiexecusername != null)
            return false;
        if (chifirstv != null ? !chifirstv.equals(that.chifirstv) : that.chifirstv != null) return false;
        if (chisecondv != null ? !chisecondv.equals(that.chisecondv) : that.chisecondv != null) return false;
        if (chithirdv != null ? !chithirdv.equals(that.chithirdv) : that.chithirdv != null) return false;
        if (chifirstn != null ? !chifirstn.equals(that.chifirstn) : that.chifirstn != null) return false;
        if (chifirstd != null ? !chifirstd.equals(that.chifirstd) : that.chifirstd != null) return false;
        if (chicreatetime != null ? !chicreatetime.equals(that.chicreatetime) : that.chicreatetime != null)
            return false;
        return chicreateuser != null ? chicreateuser.equals(that.chicreateuser) : that.chicreateuser == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (childorderid ^ (childorderid >>> 32));
        result = 31 * result + (int) (chiorderid ^ (chiorderid >>> 32));
        result = 31 * result + (chiordercode != null ? chiordercode.hashCode() : 0);
        result = 31 * result + (chipathologycode != null ? chipathologycode.hashCode() : 0);
        result = 31 * result + (int) (chicustomercode ^ (chicustomercode >>> 32));
        result = 31 * result + (int) (chisampleid ^ (chisampleid >>> 32));
        result = 31 * result + (int) (chiparaffinid ^ (chiparaffinid >>> 32));
        result = 31 * result + (int) (chiparaffinno ^ (chiparaffinno >>> 32));
        result = 31 * result + (chiparaffincode != null ? chiparaffincode.hashCode() : 0);
        result = 31 * result + (chireqtime != null ? chireqtime.hashCode() : 0);
        result = 31 * result + (chirequserid != null ? chirequserid.hashCode() : 0);
        result = 31 * result + (chirequsername != null ? chirequsername.hashCode() : 0);
        result = 31 * result + (chiordertype != null ? chiordertype.hashCode() : 0);
        result = 31 * result + (chihandletype != null ? chihandletype.hashCode() : 0);
        result = 31 * result + (chiorderstate != null ? chiorderstate.hashCode() : 0);
        result = 31 * result + (chicontent != null ? chicontent.hashCode() : 0);
        result = 31 * result + (chiisdelete != null ? chiisdelete.hashCode() : 0);
        result = 31 * result + (chislidenum != null ? chislidenum.hashCode() : 0);
        result = 31 * result + (chinullslidenum != null ? chinullslidenum.hashCode() : 0);
        result = 31 * result + (chireceivetime != null ? chireceivetime.hashCode() : 0);
        result = 31 * result + (chireceiverid != null ? chireceiverid.hashCode() : 0);
        result = 31 * result + (chireceivername != null ? chireceivername.hashCode() : 0);
        result = 31 * result + (chiexectime != null ? chiexectime.hashCode() : 0);
        result = 31 * result + (chiexecuserid != null ? chiexecuserid.hashCode() : 0);
        result = 31 * result + (chiexecusername != null ? chiexecusername.hashCode() : 0);
        result = 31 * result + (chifirstv != null ? chifirstv.hashCode() : 0);
        result = 31 * result + (chisecondv != null ? chisecondv.hashCode() : 0);
        result = 31 * result + (chithirdv != null ? chithirdv.hashCode() : 0);
        result = 31 * result + (chifirstn != null ? chifirstn.hashCode() : 0);
        result = 31 * result + (chifirstd != null ? chifirstd.hashCode() : 0);
        result = 31 * result + (chicreatetime != null ? chicreatetime.hashCode() : 0);
        result = 31 * result + (chicreateuser != null ? chicreateuser.hashCode() : 0);
        return result;
    }
}
