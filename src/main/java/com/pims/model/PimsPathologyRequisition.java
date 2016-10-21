package com.pims.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by king on 2016/10/21.
 */
@Entity
@Table(name = "PIMS_PATHOLOGY_REQUISITION")
public class PimsPathologyRequisition {
    private long requisitionid;
    private long reqcustomerid;
    private long reqpathologyid;
    private String requisitionno;
    private boolean reqsource;
    private long reqtype;
    private Date reqdate;
    private String reqinspectionid;
    private String reqdatechar;
    private String reqdeptcode;
    private String reqdeptname;
    private String reqwardcode;
    private String reqwardname;
    private String reqdoctorid;
    private String reqdoctorname;
    private Date reqplanexectime;
    private String reqdigcode;
    private String reqchargestatus;
    private String reqsendhospital;
    private String reqsendphone;
    private long reqstate;
    private String reqitemids;
    private String reqitemnames;
    private String reqpatientid;
    private int reqisemergency;
    private String reqinpatientid;
    private Long reqinpatientno;
    private long reqpatienttype;
    private String reqpatientnumber;
    private String reqpatientname;
    private boolean reqpatientsex;
    private String reqpatientage;
    private Long reqpatagetype;
    private Date reqpatbirthday;
    private String reqpatidcard;
    private String reqpattelephone;
    private String reqpataddress;
    private String reqpatdiagnosis;
    private int reqismenopause;
    private Date reqlastmenstruation;
    private String reqpatcompany;
    private int reqsendhisorder;
    private int reqsampleid;
    private int reqisdeleted;
    private String reqprintuser;
    private String reqprintusername;
    private Date reqprinttime;
    private Date reqsendtime;
    private String reqremark;
    private String reqfirstv;
    private String reqsecondv;
    private String reqthirdv;
    private Date reqfirstd;
    private Date reqsecondd;
    private Long reqfirstn;
    private String reqcreateuser;
    private Date reqcreatetime;

    @Id
    @Column(name = "REQUISITIONID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="SEQ_REQUISITIONID")
    @SequenceGenerator(name = "SEQ_REQUISITIONID", sequenceName = "SEQ_REQUISITIONID", allocationSize=1)
    public long getRequisitionid() {
        return requisitionid;
    }

    public void setRequisitionid(long requisitionid) {
        this.requisitionid = requisitionid;
    }

    @Basic
    @Column(name = "REQCUSTOMERID")
    public long getReqcustomerid() {
        return reqcustomerid;
    }

    public void setReqcustomerid(long reqcustomerid) {
        this.reqcustomerid = reqcustomerid;
    }

    @Basic
    @Column(name = "REQPATHOLOGYID")
    public long getReqpathologyid() {
        return reqpathologyid;
    }

    public void setReqpathologyid(long reqpathologyid) {
        this.reqpathologyid = reqpathologyid;
    }

    @Basic
    @Column(name = "REQUISITIONNO")
    public String getRequisitionno() {
        return requisitionno;
    }

    public void setRequisitionno(String requisitionno) {
        this.requisitionno = requisitionno;
    }

    @Basic
    @Column(name = "REQSOURCE")
    public boolean isReqsource() {
        return reqsource;
    }

    public void setReqsource(boolean reqsource) {
        this.reqsource = reqsource;
    }

    @Basic
    @Column(name = "REQTYPE")
    public long getReqtype() {
        return reqtype;
    }

    public void setReqtype(long reqtype) {
        this.reqtype = reqtype;
    }

    @Basic
    @Column(name = "REQDATE")
    public Date getReqdate() {
        return reqdate;
    }

    public void setReqdate(Date reqdate) {
        this.reqdate = reqdate;
    }

    @Basic
    @Column(name = "REQINSPECTIONID")
    public String getReqinspectionid() {
        return reqinspectionid;
    }

    public void setReqinspectionid(String reqinspectionid) {
        this.reqinspectionid = reqinspectionid;
    }

    @Basic
    @Column(name = "REQDATECHAR")
    public String getReqdatechar() {
        return reqdatechar;
    }

    public void setReqdatechar(String reqdatechar) {
        this.reqdatechar = reqdatechar;
    }

    @Basic
    @Column(name = "REQDEPTCODE")
    public String getReqdeptcode() {
        return reqdeptcode;
    }

    public void setReqdeptcode(String reqdeptcode) {
        this.reqdeptcode = reqdeptcode;
    }

    @Basic
    @Column(name = "REQDEPTNAME")
    public String getReqdeptname() {
        return reqdeptname;
    }

    public void setReqdeptname(String reqdeptname) {
        this.reqdeptname = reqdeptname;
    }

    @Basic
    @Column(name = "REQWARDCODE")
    public String getReqwardcode() {
        return reqwardcode;
    }

    public void setReqwardcode(String reqwardcode) {
        this.reqwardcode = reqwardcode;
    }

    @Basic
    @Column(name = "REQWARDNAME")
    public String getReqwardname() {
        return reqwardname;
    }

    public void setReqwardname(String reqwardname) {
        this.reqwardname = reqwardname;
    }

    @Basic
    @Column(name = "REQDOCTORID")
    public String getReqdoctorid() {
        return reqdoctorid;
    }

    public void setReqdoctorid(String reqdoctorid) {
        this.reqdoctorid = reqdoctorid;
    }

    @Basic
    @Column(name = "REQDOCTORNAME")
    public String getReqdoctorname() {
        return reqdoctorname;
    }

    public void setReqdoctorname(String reqdoctorname) {
        this.reqdoctorname = reqdoctorname;
    }

    @Basic
    @Column(name = "REQPLANEXECTIME")
    public Date getReqplanexectime() {
        return reqplanexectime;
    }

    public void setReqplanexectime(Date reqplanexectime) {
        this.reqplanexectime = reqplanexectime;
    }

    @Basic
    @Column(name = "REQDIGCODE")
    public String getReqdigcode() {
        return reqdigcode;
    }

    public void setReqdigcode(String reqdigcode) {
        this.reqdigcode = reqdigcode;
    }

    @Basic
    @Column(name = "REQCHARGESTATUS")
    public String getReqchargestatus() {
        return reqchargestatus;
    }

    public void setReqchargestatus(String reqchargestatus) {
        this.reqchargestatus = reqchargestatus;
    }

    @Basic
    @Column(name = "REQSENDHOSPITAL")
    public String getReqsendhospital() {
        return reqsendhospital;
    }

    public void setReqsendhospital(String reqsendhospital) {
        this.reqsendhospital = reqsendhospital;
    }

    @Basic
    @Column(name = "REQSENDPHONE")
    public String getReqsendphone() {
        return reqsendphone;
    }

    public void setReqsendphone(String reqsendphone) {
        this.reqsendphone = reqsendphone;
    }

    @Basic
    @Column(name = "REQSTATE")
    public long getReqstate() {
        return reqstate;
    }

    public void setReqstate(long reqstate) {
        this.reqstate = reqstate;
    }

    @Basic
    @Column(name = "REQITEMIDS")
    public String getReqitemids() {
        return reqitemids;
    }

    public void setReqitemids(String reqitemids) {
        this.reqitemids = reqitemids;
    }

    @Basic
    @Column(name = "REQITEMNAMES")
    public String getReqitemnames() {
        return reqitemnames;
    }

    public void setReqitemnames(String reqitemnames) {
        this.reqitemnames = reqitemnames;
    }

    @Basic
    @Column(name = "REQPATIENTID")
    public String getReqpatientid() {
        return reqpatientid;
    }

    public void setReqpatientid(String reqpatientid) {
        this.reqpatientid = reqpatientid;
    }

    @Basic
    @Column(name = "REQISEMERGENCY")
    public int getReqisemergency() {
        return reqisemergency;
    }

    public void setReqisemergency(int reqisemergency) {
        this.reqisemergency = reqisemergency;
    }

    @Basic
    @Column(name = "REQINPATIENTID")
    public String getReqinpatientid() {
        return reqinpatientid;
    }

    public void setReqinpatientid(String reqinpatientid) {
        this.reqinpatientid = reqinpatientid;
    }

    @Basic
    @Column(name = "REQINPATIENTNO")
    public Long getReqinpatientno() {
        return reqinpatientno;
    }

    public void setReqinpatientno(Long reqinpatientno) {
        this.reqinpatientno = reqinpatientno;
    }

    @Basic
    @Column(name = "REQPATIENTTYPE")
    public long getReqpatienttype() {
        return reqpatienttype;
    }

    public void setReqpatienttype(long reqpatienttype) {
        this.reqpatienttype = reqpatienttype;
    }

    @Basic
    @Column(name = "REQPATIENTNUMBER")
    public String getReqpatientnumber() {
        return reqpatientnumber;
    }

    public void setReqpatientnumber(String reqpatientnumber) {
        this.reqpatientnumber = reqpatientnumber;
    }

    @Basic
    @Column(name = "REQPATIENTNAME")
    public String getReqpatientname() {
        return reqpatientname;
    }

    public void setReqpatientname(String reqpatientname) {
        this.reqpatientname = reqpatientname;
    }

    @Basic
    @Column(name = "REQPATIENTSEX")
    public boolean isReqpatientsex() {
        return reqpatientsex;
    }

    public void setReqpatientsex(boolean reqpatientsex) {
        this.reqpatientsex = reqpatientsex;
    }

    @Basic
    @Column(name = "REQPATIENTAGE")
    public String getReqpatientage() {
        return reqpatientage;
    }

    public void setReqpatientage(String reqpatientage) {
        this.reqpatientage = reqpatientage;
    }

    @Basic
    @Column(name = "REQPATAGETYPE")
    public Long getReqpatagetype() {
        return reqpatagetype;
    }

    public void setReqpatagetype(Long reqpatagetype) {
        this.reqpatagetype = reqpatagetype;
    }

    @Basic
    @Column(name = "REQPATBIRTHDAY")
    public Date getReqpatbirthday() {
        return reqpatbirthday;
    }

    public void setReqpatbirthday(Date reqpatbirthday) {
        this.reqpatbirthday = reqpatbirthday;
    }

    @Basic
    @Column(name = "REQPATIDCARD")
    public String getReqpatidcard() {
        return reqpatidcard;
    }

    public void setReqpatidcard(String reqpatidcard) {
        this.reqpatidcard = reqpatidcard;
    }

    @Basic
    @Column(name = "REQPATTELEPHONE")
    public String getReqpattelephone() {
        return reqpattelephone;
    }

    public void setReqpattelephone(String reqpattelephone) {
        this.reqpattelephone = reqpattelephone;
    }

    @Basic
    @Column(name = "REQPATADDRESS")
    public String getReqpataddress() {
        return reqpataddress;
    }

    public void setReqpataddress(String reqpataddress) {
        this.reqpataddress = reqpataddress;
    }

    @Basic
    @Column(name = "REQPATDIAGNOSIS")
    public String getReqpatdiagnosis() {
        return reqpatdiagnosis;
    }

    public void setReqpatdiagnosis(String reqpatdiagnosis) {
        this.reqpatdiagnosis = reqpatdiagnosis;
    }

    @Basic
    @Column(name = "REQISMENOPAUSE")
    public int getReqismenopause() {
        return reqismenopause;
    }

    public void setReqismenopause(int reqismenopause) {
        this.reqismenopause = reqismenopause;
    }

    @Basic
    @Column(name = "REQLASTMENSTRUATION")
    public Date getReqlastmenstruation() {
        return reqlastmenstruation;
    }

    public void setReqlastmenstruation(Date reqlastmenstruation) {
        this.reqlastmenstruation = reqlastmenstruation;
    }

    @Basic
    @Column(name = "REQPATCOMPANY")
    public String getReqpatcompany() {
        return reqpatcompany;
    }

    public void setReqpatcompany(String reqpatcompany) {
        this.reqpatcompany = reqpatcompany;
    }

    @Basic
    @Column(name = "REQSENDHISORDER")
    public int getReqsendhisorder() {
        return reqsendhisorder;
    }

    public void setReqsendhisorder(int reqsendhisorder) {
        this.reqsendhisorder = reqsendhisorder;
    }

    @Basic
    @Column(name = "REQSAMPLEID")
    public int getReqsampleid() {
        return reqsampleid;
    }

    public void setReqsampleid(int reqsampleid) {
        this.reqsampleid = reqsampleid;
    }

    @Basic
    @Column(name = "REQISDELETED")
    public int getReqisdeleted() {
        return reqisdeleted;
    }

    public void setReqisdeleted(int reqisdeleted) {
        this.reqisdeleted = reqisdeleted;
    }

    @Basic
    @Column(name = "REQPRINTUSER")
    public String getReqprintuser() {
        return reqprintuser;
    }

    public void setReqprintuser(String reqprintuser) {
        this.reqprintuser = reqprintuser;
    }

    @Basic
    @Column(name = "REQPRINTUSERNAME")
    public String getReqprintusername() {
        return reqprintusername;
    }

    public void setReqprintusername(String reqprintusername) {
        this.reqprintusername = reqprintusername;
    }

    @Basic
    @Column(name = "REQPRINTTIME")
    public Date getReqprinttime() {
        return reqprinttime;
    }

    public void setReqprinttime(Date reqprinttime) {
        this.reqprinttime = reqprinttime;
    }

    @Basic
    @Column(name = "REQSENDTIME")
    public Date getReqsendtime() {
        return reqsendtime;
    }

    public void setReqsendtime(Date reqsendtime) {
        this.reqsendtime = reqsendtime;
    }

    @Basic
    @Column(name = "REQREMARK")
    public String getReqremark() {
        return reqremark;
    }

    public void setReqremark(String reqremark) {
        this.reqremark = reqremark;
    }

    @Basic
    @Column(name = "REQFIRSTV")
    public String getReqfirstv() {
        return reqfirstv;
    }

    public void setReqfirstv(String reqfirstv) {
        this.reqfirstv = reqfirstv;
    }

    @Basic
    @Column(name = "REQSECONDV")
    public String getReqsecondv() {
        return reqsecondv;
    }

    public void setReqsecondv(String reqsecondv) {
        this.reqsecondv = reqsecondv;
    }

    @Basic
    @Column(name = "REQTHIRDV")
    public String getReqthirdv() {
        return reqthirdv;
    }

    public void setReqthirdv(String reqthirdv) {
        this.reqthirdv = reqthirdv;
    }

    @Basic
    @Column(name = "REQFIRSTD")
    public Date getReqfirstd() {
        return reqfirstd;
    }

    public void setReqfirstd(Date reqfirstd) {
        this.reqfirstd = reqfirstd;
    }

    @Basic
    @Column(name = "REQSECONDD")
    public Date getReqsecondd() {
        return reqsecondd;
    }

    public void setReqsecondd(Date reqsecondd) {
        this.reqsecondd = reqsecondd;
    }

    @Basic
    @Column(name = "REQFIRSTN")
    public Long getReqfirstn() {
        return reqfirstn;
    }

    public void setReqfirstn(Long reqfirstn) {
        this.reqfirstn = reqfirstn;
    }

    @Basic
    @Column(name = "REQCREATEUSER")
    public String getReqcreateuser() {
        return reqcreateuser;
    }

    public void setReqcreateuser(String reqcreateuser) {
        this.reqcreateuser = reqcreateuser;
    }

    @Basic
    @Column(name = "REQCREATETIME")
    public Date getReqcreatetime() {
        return reqcreatetime;
    }

    public void setReqcreatetime(Date reqcreatetime) {
        this.reqcreatetime = reqcreatetime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PimsPathologyRequisition that = (PimsPathologyRequisition) o;

        if (requisitionid != that.requisitionid) return false;
        if (reqcustomerid != that.reqcustomerid) return false;
        if (reqpathologyid != that.reqpathologyid) return false;
        if (reqsource != that.reqsource) return false;
        if (reqtype != that.reqtype) return false;
        if (reqstate != that.reqstate) return false;
        if (reqpatienttype != that.reqpatienttype) return false;
        if (reqpatientsex != that.reqpatientsex) return false;
        if (requisitionno != null ? !requisitionno.equals(that.requisitionno) : that.requisitionno != null)
            return false;
        if (reqdate != null ? !reqdate.equals(that.reqdate) : that.reqdate != null) return false;
        if (reqinspectionid != null ? !reqinspectionid.equals(that.reqinspectionid) : that.reqinspectionid != null)
            return false;
        if (reqdatechar != null ? !reqdatechar.equals(that.reqdatechar) : that.reqdatechar != null) return false;
        if (reqdeptcode != null ? !reqdeptcode.equals(that.reqdeptcode) : that.reqdeptcode != null) return false;
        if (reqdeptname != null ? !reqdeptname.equals(that.reqdeptname) : that.reqdeptname != null) return false;
        if (reqwardcode != null ? !reqwardcode.equals(that.reqwardcode) : that.reqwardcode != null) return false;
        if (reqwardname != null ? !reqwardname.equals(that.reqwardname) : that.reqwardname != null) return false;
        if (reqdoctorid != null ? !reqdoctorid.equals(that.reqdoctorid) : that.reqdoctorid != null) return false;
        if (reqdoctorname != null ? !reqdoctorname.equals(that.reqdoctorname) : that.reqdoctorname != null)
            return false;
        if (reqplanexectime != null ? !reqplanexectime.equals(that.reqplanexectime) : that.reqplanexectime != null)
            return false;
        if (reqdigcode != null ? !reqdigcode.equals(that.reqdigcode) : that.reqdigcode != null) return false;
        if (reqchargestatus != null ? !reqchargestatus.equals(that.reqchargestatus) : that.reqchargestatus != null)
            return false;
        if (reqsendhospital != null ? !reqsendhospital.equals(that.reqsendhospital) : that.reqsendhospital != null)
            return false;
        if (reqsendphone != null ? !reqsendphone.equals(that.reqsendphone) : that.reqsendphone != null) return false;
        if (reqitemids != null ? !reqitemids.equals(that.reqitemids) : that.reqitemids != null) return false;
        if (reqitemnames != null ? !reqitemnames.equals(that.reqitemnames) : that.reqitemnames != null) return false;
        if (reqpatientid != null ? !reqpatientid.equals(that.reqpatientid) : that.reqpatientid != null) return false;
        if (reqisemergency != that.reqisemergency )
            return false;
        if (reqinpatientid != null ? !reqinpatientid.equals(that.reqinpatientid) : that.reqinpatientid != null)
            return false;
        if (reqinpatientno != null ? !reqinpatientno.equals(that.reqinpatientno) : that.reqinpatientno != null)
            return false;
        if (reqpatientnumber != null ? !reqpatientnumber.equals(that.reqpatientnumber) : that.reqpatientnumber != null)
            return false;
        if (reqpatientname != null ? !reqpatientname.equals(that.reqpatientname) : that.reqpatientname != null)
            return false;
        if (reqpatientage != null ? !reqpatientage.equals(that.reqpatientage) : that.reqpatientage != null)
            return false;
        if (reqpatagetype != null ? !reqpatagetype.equals(that.reqpatagetype) : that.reqpatagetype != null)
            return false;
        if (reqpatbirthday != null ? !reqpatbirthday.equals(that.reqpatbirthday) : that.reqpatbirthday != null)
            return false;
        if (reqpatidcard != null ? !reqpatidcard.equals(that.reqpatidcard) : that.reqpatidcard != null) return false;
        if (reqpattelephone != null ? !reqpattelephone.equals(that.reqpattelephone) : that.reqpattelephone != null)
            return false;
        if (reqpataddress != null ? !reqpataddress.equals(that.reqpataddress) : that.reqpataddress != null)
            return false;
        if (reqpatdiagnosis != null ? !reqpatdiagnosis.equals(that.reqpatdiagnosis) : that.reqpatdiagnosis != null)
            return false;
        if (reqismenopause != that.reqismenopause )
            return false;
        if (reqlastmenstruation != null ? !reqlastmenstruation.equals(that.reqlastmenstruation) : that.reqlastmenstruation != null)
            return false;
        if (reqpatcompany != null ? !reqpatcompany.equals(that.reqpatcompany) : that.reqpatcompany != null)
            return false;
        if (reqsendhisorder != that.reqsendhisorder )
            return false;
        if (reqsampleid != that.reqsampleid ) return false;
        if (reqisdeleted != that.reqisdeleted ) return false;
        if (reqprintuser != null ? !reqprintuser.equals(that.reqprintuser) : that.reqprintuser != null) return false;
        if (reqprintusername != null ? !reqprintusername.equals(that.reqprintusername) : that.reqprintusername != null)
            return false;
        if (reqprinttime != null ? !reqprinttime.equals(that.reqprinttime) : that.reqprinttime != null) return false;
        if (reqsendtime != null ? !reqsendtime.equals(that.reqsendtime) : that.reqsendtime != null) return false;
        if (reqremark != null ? !reqremark.equals(that.reqremark) : that.reqremark != null) return false;
        if (reqfirstv != null ? !reqfirstv.equals(that.reqfirstv) : that.reqfirstv != null) return false;
        if (reqsecondv != null ? !reqsecondv.equals(that.reqsecondv) : that.reqsecondv != null) return false;
        if (reqthirdv != null ? !reqthirdv.equals(that.reqthirdv) : that.reqthirdv != null) return false;
        if (reqfirstd != null ? !reqfirstd.equals(that.reqfirstd) : that.reqfirstd != null) return false;
        if (reqsecondd != null ? !reqsecondd.equals(that.reqsecondd) : that.reqsecondd != null) return false;
        if (reqfirstn != null ? !reqfirstn.equals(that.reqfirstn) : that.reqfirstn != null) return false;
        if (reqcreateuser != null ? !reqcreateuser.equals(that.reqcreateuser) : that.reqcreateuser != null)
            return false;
        if (reqcreatetime != null ? !reqcreatetime.equals(that.reqcreatetime) : that.reqcreatetime != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (requisitionid ^ (requisitionid >>> 32));
        result = 31 * result + (int) (reqcustomerid ^ (reqcustomerid >>> 32));
        result = 31 * result + (int) (reqpathologyid ^ (reqpathologyid >>> 32));
        result = 31 * result + (requisitionno != null ? requisitionno.hashCode() : 0);
        result = 31 * result + (reqsource ? 1 : 0);
        result = 31 * result + (int) (reqtype ^ (reqtype >>> 32));
        result = 31 * result + (reqdate != null ? reqdate.hashCode() : 0);
        result = 31 * result + (reqinspectionid != null ? reqinspectionid.hashCode() : 0);
        result = 31 * result + (reqdatechar != null ? reqdatechar.hashCode() : 0);
        result = 31 * result + (reqdeptcode != null ? reqdeptcode.hashCode() : 0);
        result = 31 * result + (reqdeptname != null ? reqdeptname.hashCode() : 0);
        result = 31 * result + (reqwardcode != null ? reqwardcode.hashCode() : 0);
        result = 31 * result + (reqwardname != null ? reqwardname.hashCode() : 0);
        result = 31 * result + (reqdoctorid != null ? reqdoctorid.hashCode() : 0);
        result = 31 * result + (reqdoctorname != null ? reqdoctorname.hashCode() : 0);
        result = 31 * result + (reqplanexectime != null ? reqplanexectime.hashCode() : 0);
        result = 31 * result + (reqdigcode != null ? reqdigcode.hashCode() : 0);
        result = 31 * result + (reqchargestatus != null ? reqchargestatus.hashCode() : 0);
        result = 31 * result + (reqsendhospital != null ? reqsendhospital.hashCode() : 0);
        result = 31 * result + (reqsendphone != null ? reqsendphone.hashCode() : 0);
        result = 31 * result + (int) (reqstate ^ (reqstate >>> 32));
        result = 31 * result + (reqitemids != null ? reqitemids.hashCode() : 0);
        result = 31 * result + (reqitemnames != null ? reqitemnames.hashCode() : 0);
        result = 31 * result + (reqpatientid != null ? reqpatientid.hashCode() : 0);
        result = 31 * result + (reqisemergency ^ (reqisemergency >>> 32));
        result = 31 * result + (reqinpatientid != null ? reqinpatientid.hashCode() : 0);
        result = 31 * result + (reqinpatientno != null ? reqinpatientno.hashCode() : 0);
        result = 31 * result + (int) (reqpatienttype ^ (reqpatienttype >>> 32));
        result = 31 * result + (reqpatientnumber != null ? reqpatientnumber.hashCode() : 0);
        result = 31 * result + (reqpatientname != null ? reqpatientname.hashCode() : 0);
        result = 31 * result + (reqpatientsex ? 1 : 0);
        result = 31 * result + (reqpatientage != null ? reqpatientage.hashCode() : 0);
        result = 31 * result + (reqpatagetype != null ? reqpatagetype.hashCode() : 0);
        result = 31 * result + (reqpatbirthday != null ? reqpatbirthday.hashCode() : 0);
        result = 31 * result + (reqpatidcard != null ? reqpatidcard.hashCode() : 0);
        result = 31 * result + (reqpattelephone != null ? reqpattelephone.hashCode() : 0);
        result = 31 * result + (reqpataddress != null ? reqpataddress.hashCode() : 0);
        result = 31 * result + (reqpatdiagnosis != null ? reqpatdiagnosis.hashCode() : 0);
        result = 31 * result + (reqismenopause ^ (reqismenopause >>> 32));
        result = 31 * result + (reqlastmenstruation != null ? reqlastmenstruation.hashCode() : 0);
        result = 31 * result + (reqpatcompany != null ? reqpatcompany.hashCode() : 0);
        result = 31 * result + (reqsendhisorder ^ (reqsendhisorder >>> 32));
        result = 31 * result + (reqsampleid ^ (reqsampleid >>> 32));
        result = 31 * result + (reqisdeleted ^ (reqisdeleted >>> 32));
        result = 31 * result + (reqprintuser != null ? reqprintuser.hashCode() : 0);
        result = 31 * result + (reqprintusername != null ? reqprintusername.hashCode() : 0);
        result = 31 * result + (reqprinttime != null ? reqprinttime.hashCode() : 0);
        result = 31 * result + (reqsendtime != null ? reqsendtime.hashCode() : 0);
        result = 31 * result + (reqremark != null ? reqremark.hashCode() : 0);
        result = 31 * result + (reqfirstv != null ? reqfirstv.hashCode() : 0);
        result = 31 * result + (reqsecondv != null ? reqsecondv.hashCode() : 0);
        result = 31 * result + (reqthirdv != null ? reqthirdv.hashCode() : 0);
        result = 31 * result + (reqfirstd != null ? reqfirstd.hashCode() : 0);
        result = 31 * result + (reqsecondd != null ? reqsecondd.hashCode() : 0);
        result = 31 * result + (reqfirstn != null ? reqfirstn.hashCode() : 0);
        result = 31 * result + (reqcreateuser != null ? reqcreateuser.hashCode() : 0);
        result = 31 * result + (reqcreatetime != null ? reqcreatetime.hashCode() : 0);
        return result;
    }
}
