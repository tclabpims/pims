package com.pims.model;

import com.smart.Constants;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by king on 2016/10/21.
 */
@Entity
@Table(name = "PIMS_PATHOLOGY_SAMPLE")
public class PimsPathologySample {
    private long sampleid;
    private String saminspectionid;
    private String sampathologycode;
    private long samcustomerid;
    private long sampathologyid;
    private int samsource;
    private String samotherid;
    private String samrequistionid;
    private String sampatientid;
    private String saminpatientid;
    private String saminpatientno;
    private String sampatientnumber;
    private long sampatienttype;
    private String sampatientname;
    private int sampatientsex;
    private String sampatientage;
    private Long sampatientagetype;
    private String sampatientbed;
    private String samsampleclass;
    private String samsamplename;
    private String sampopuser;
    private int samisemergency;
    private int samisdecacified;
    private int samissamplingall;
    private long samsamplestatus;
    private Date samreqtime;
    private String samreqdocid;
    private String samreqdocname;
    private Date samsendtime;
    private String samsenddoctorid;
    private String samsenddoctorname;
    private String samsendhospital;
    private String samsendphone;
    private String samdigcode;
    private String samdeptcode;
    private String samdeptname;
    private String samwardcode;
    private String samwardname;
    private String sampatientdignoses;
    private String sampatientidcardno;
    private String sampatientaddress;
    private String sampatientphoneno;
    private String sampatientcompany;
    private int samismenopause;
    private Date reqlastmenstruation;
    private int samischarged;
    private Date samreceivertime;
    private String samreceiverid;
    private String samreceivername;
    private Date samregisttime;
    private String samregisterid;
    private String samregistername;
    private Date saminitiallytime;
    private String saminitiallyuserid;
    private String saminitiallyusername;
    private Date samauditedtime;
    private String samauditerid;
    private String samauditer;
    private Date samreportedtime;
    private String samreportorid;
    private String samreportor;
    private int samisdeleted;
    private String samremark;
    private String samtaskid;
    private String samfollowupid;
    private int sampdfupload;
    private int samissentresult;
    private String samfirstv;
    private String samsecondv;
    private String samthirdv;
    private Date samfirstd;
    private Date samsecondd;
    private int samfirstn;
    private Date samcreatetime;
    private String samcreateuser;

    private Date samplesectionfrom;

    private Date samplesectionto;

    private String samjjsj;
    //病理状态 1:未报告 2：已签发 3：已审核 4：已打印
    private int sampathologystatus;

    private String samjcxm;

    //病种分类
    private String patclass;

    @Transient
    public String getPatclass() {
        return patclass;
    }

    public void setPatclass(String patclass) {
        this.patclass = patclass;
    }


    private String sampiecedoctorid;

    private String sampiecedoctorname;

    @Basic
    @Column(name = "SAMPIECEDOCTORID")
    public String getSampiecedoctorid() {
        return sampiecedoctorid;
    }

    public void setSampiecedoctorid(String sampiecedoctorid) {
        this.sampiecedoctorid = sampiecedoctorid;
    }
    @Basic
    @Column(name = "SAMPIECEDOCTORNAME")
    public String getSampiecedoctorname() {
        return sampiecedoctorname;
    }

    public void setSampiecedoctorname(String sampiecedoctorname) {
        this.sampiecedoctorname = sampiecedoctorname;
    }



    private String sampiecedoctorid;

    private String sampiecedoctorname;

    @Basic
    @Column(name = "SAMPIECEDOCTORID")
    public String getSampiecedoctorid() {
        return sampiecedoctorid;
    }

    public void setSampiecedoctorid(String sampiecedoctorid) {
        this.sampiecedoctorid = sampiecedoctorid;
    }
    @Basic
    @Column(name = "SAMPIECEDOCTORNAME")
    public String getSampiecedoctorname() {
        return sampiecedoctorname;
    }

    public void setSampiecedoctorname(String sampiecedoctorname) {
        this.sampiecedoctorname = sampiecedoctorname;
    }

    @Basic
    @Column(name = "SAMJCXM")
    public String getSamjcxm() {
        return samjcxm;
    }

    public void setSamjcxm(String samjcxm) {
        this.samjcxm = samjcxm;
    }

    @Transient
    public int getSampathologystatus() {
        return sampathologystatus;
    }

    public void setSampathologystatus(int sampathologystatus) {
        this.sampathologystatus = sampathologystatus;
    }

    public int pathologyStatus() {
        if(samreportorid != null) return Constants.PATHOLOGY_STATUS_PRINTED;
        else if(samauditerid != null && samreportorid == null) return Constants.PATHOLOGY_STATUS_CHECKED;
        else if(saminitiallyuserid != null && samauditerid == null) return Constants.PATHOLOGY_STATUS_REPORTED;
        else return Constants.PATHOLOGY_STATUS_NOT_REPORTED;
    }

    @Basic
    @Column(name = "SAMJJSJ")
    public String getSamjjsj() {
        return samjjsj;
    }

    public void setSamjjsj(String samjjsj) {
        this.samjjsj = samjjsj;
    }

    @Transient
    public Date getSamplesectionfrom() {
        return samplesectionfrom;
    }

    public void setSamplesectionfrom(Date samplesectionfrom) {
        this.samplesectionfrom = samplesectionfrom;
    }

    @Transient
    public Date getSamplesectionto() {
        return samplesectionto;
    }

    public void setSamplesectionto(Date samplesectionto) {
        this.samplesectionto = samplesectionto;
    }

    @Id
    @Column(name = "SAMPLEID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="SEQ_SAMPLEID")
    @SequenceGenerator(name = "SEQ_SAMPLEID", sequenceName = "SEQ_SAMPLEID", allocationSize=1)
    public long getSampleid() {
        return sampleid;
    }

    public void setSampleid(long sampleid) {
        this.sampleid = sampleid;
    }

    @Basic
    @Column(name = "SAMINSPECTIONID")
    public String getSaminspectionid() {
        return saminspectionid;
    }

    public void setSaminspectionid(String saminspectionid) {
        this.saminspectionid = saminspectionid;
    }

    @Basic
    @Column(name = "SAMPATHOLOGYCODE")
    public String getSampathologycode() {
        return sampathologycode;
    }

    public void setSampathologycode(String sampathologycode) {
        this.sampathologycode = sampathologycode;
    }

    @Basic
    @Column(name = "SAMCUSTOMERID")
    public long getSamcustomerid() {
        return samcustomerid;
    }

    public void setSamcustomerid(long samcustomerid) {
        this.samcustomerid = samcustomerid;
    }

    @Basic
    @Column(name = "SAMPATHOLOGYID")
    public long getSampathologyid() {
        return sampathologyid;
    }

    public void setSampathologyid(long sampathologyid) {
        this.sampathologyid = sampathologyid;
    }

    @Basic
    @Column(name = "SAMSOURCE")
    public int getSamsource() {
        return samsource;
    }

    public void setSamsource(int samsource) {
        this.samsource = samsource;
    }

    @Basic
    @Column(name = "SAMOTHERID")
    public String getSamotherid() {
        return samotherid;
    }

    public void setSamotherid(String samotherid) {
        this.samotherid = samotherid;
    }

    @Basic
    @Column(name = "SAMREQUISTIONID")
    public String getSamrequistionid() {
        return samrequistionid;
    }

    public void setSamrequistionid(String samrequistionid) {
        this.samrequistionid = samrequistionid;
    }

    @Basic
    @Column(name = "SAMPATIENTID")
    public String getSampatientid() {
        return sampatientid;
    }

    public void setSampatientid(String sampatientid) {
        this.sampatientid = sampatientid;
    }

    @Basic
    @Column(name = "SAMINPATIENTID")
    public String getSaminpatientid() {
        return saminpatientid;
    }

    public void setSaminpatientid(String saminpatientid) {
        this.saminpatientid = saminpatientid;
    }

    @Basic
    @Column(name = "SAMINPATIENTNO")
    public String getSaminpatientno() {
        return saminpatientno;
    }

    public void setSaminpatientno(String saminpatientno) {
        this.saminpatientno = saminpatientno;
    }

    @Basic
    @Column(name = "SAMPATIENTNUMBER")
    public String getSampatientnumber() {
        return sampatientnumber;
    }

    public void setSampatientnumber(String sampatientnumber) {
        this.sampatientnumber = sampatientnumber;
    }

    @Basic
    @Column(name = "SAMPATIENTTYPE")
    public long getSampatienttype() {
        return sampatienttype;
    }

    public void setSampatienttype(long sampatienttype) {
        this.sampatienttype = sampatienttype;
    }

    @Basic
    @Column(name = "SAMPATIENTNAME")
    public String getSampatientname() {
        return sampatientname;
    }

    public void setSampatientname(String sampatientname) {
        this.sampatientname = sampatientname;
    }

    @Basic
    @Column(name = "SAMPATIENTSEX")
    public int getSampatientsex() {
        return sampatientsex;
    }

    public void setSampatientsex(int sampatientsex) {
        this.sampatientsex = sampatientsex;
    }

    @Basic
    @Column(name = "SAMPATIENTAGE")
    public String getSampatientage() {
        return sampatientage;
    }

    public void setSampatientage(String sampatientage) {
        this.sampatientage = sampatientage;
    }

    @Basic
    @Column(name = "SAMPATIENTAGETYPE")
    public Long getSampatientagetype() {
        return sampatientagetype;
    }

    public void setSampatientagetype(Long sampatientagetype) {
        this.sampatientagetype = sampatientagetype;
    }

    @Basic
    @Column(name = "SAMPATIENTBED")
    public String getSampatientbed() {
        return sampatientbed;
    }

    public void setSampatientbed(String sampatientbed) {
        this.sampatientbed = sampatientbed;
    }

    @Basic
    @Column(name = "SAMSAMPLECLASS")
    public String getSamsampleclass() {
        return samsampleclass;
    }

    public void setSamsampleclass(String samsampleclass) {
        this.samsampleclass = samsampleclass;
    }

    @Basic
    @Column(name = "SAMSAMPLENAME")
    public String getSamsamplename() {
        return samsamplename;
    }

    public void setSamsamplename(String samsamplename) {
        this.samsamplename = samsamplename;
    }

    @Basic
    @Column(name = "SAMPOPUSER")
    public String getSampopuser() {
        return sampopuser;
    }

    public void setSampopuser(String sampopuser) {
        this.sampopuser = sampopuser;
    }

    @Basic
    @Column(name = "SAMISEMERGENCY")
    public int getSamisemergency() {
        return samisemergency;
    }

    public void setSamisemergency(int samisemergency) {
        this.samisemergency = samisemergency;
    }

    @Basic
    @Column(name = "SAMISDECACIFIED")
    public int getSamisdecacified() {
        return samisdecacified;
    }

    public void setSamisdecacified(int samisdecacified) {
        this.samisdecacified = samisdecacified;
    }

    @Basic
    @Column(name = "SAMISSAMPLINGALL")
    public int getSamissamplingall() {
        return samissamplingall;
    }

    public void setSamissamplingall(int samissamplingall) {
        this.samissamplingall = samissamplingall;
    }

    @Basic
    @Column(name = "SAMSAMPLESTATUS")
    public long getSamsamplestatus() {
        return samsamplestatus;
    }

    public void setSamsamplestatus(long samsamplestatus) {
        this.samsamplestatus = samsamplestatus;
    }

    @Basic
    @Column(name = "SAMREQTIME")
    public Date getSamreqtime() {
        return samreqtime;
    }

    public void setSamreqtime(Date samreqtime) {
        this.samreqtime = samreqtime;
    }

    @Basic
    @Column(name = "SAMREQDOCID")
    public String getSamreqdocid() {
        return samreqdocid;
    }

    public void setSamreqdocid(String samreqdocid) {
        this.samreqdocid = samreqdocid;
    }

    @Basic
    @Column(name = "SAMREQDOCNAME")
    public String getSamreqdocname() {
        return samreqdocname;
    }

    public void setSamreqdocname(String samreqdocname) {
        this.samreqdocname = samreqdocname;
    }

    @Basic
    @Column(name = "SAMSENDTIME")
    public Date getSamsendtime() {
        return samsendtime;
    }

    public void setSamsendtime(Date samsendtime) {
        this.samsendtime = samsendtime;
    }

    @Basic
    @Column(name = "SAMSENDDOCTORID")
    public String getSamsenddoctorid() {
        return samsenddoctorid;
    }

    public void setSamsenddoctorid(String samsenddoctorid) {
        this.samsenddoctorid = samsenddoctorid;
    }

    @Basic
    @Column(name = "SAMSENDDOCTORNAME")
    public String getSamsenddoctorname() {
        return samsenddoctorname;
    }

    public void setSamsenddoctorname(String samsenddoctorname) {
        this.samsenddoctorname = samsenddoctorname;
    }

    @Basic
    @Column(name = "SAMSENDHOSPITAL")
    public String getSamsendhospital() {
        return samsendhospital;
    }

    public void setSamsendhospital(String samsendhospital) {
        this.samsendhospital = samsendhospital;
    }

    @Basic
    @Column(name = "SAMSENDPHONE")
    public String getSamsendphone() {
        return samsendphone;
    }

    public void setSamsendphone(String samsendphone) {
        this.samsendphone = samsendphone;
    }

    @Basic
    @Column(name = "SAMDIGCODE")
    public String getSamdigcode() {
        return samdigcode;
    }

    public void setSamdigcode(String samdigcode) {
        this.samdigcode = samdigcode;
    }

    @Basic
    @Column(name = "SAMDEPTCODE")
    public String getSamdeptcode() {
        return samdeptcode;
    }

    public void setSamdeptcode(String samdeptcode) {
        this.samdeptcode = samdeptcode;
    }

    @Basic
    @Column(name = "SAMDEPTNAME")
    public String getSamdeptname() {
        return samdeptname;
    }

    public void setSamdeptname(String samdeptname) {
        this.samdeptname = samdeptname;
    }

    @Basic
    @Column(name = "SAMWARDCODE")
    public String getSamwardcode() {
        return samwardcode;
    }

    public void setSamwardcode(String samwardcode) {
        this.samwardcode = samwardcode;
    }

    @Basic
    @Column(name = "SAMWARDNAME")
    public String getSamwardname() {
        return samwardname;
    }

    public void setSamwardname(String samwardname) {
        this.samwardname = samwardname;
    }

    @Basic
    @Column(name = "SAMPATIENTDIGNOSES")
    public String getSampatientdignoses() {
        return sampatientdignoses;
    }

    public void setSampatientdignoses(String sampatientdignoses) {
        this.sampatientdignoses = sampatientdignoses;
    }

    @Basic
    @Column(name = "SAMPATIENTIDCARDNO")
    public String getSampatientidcardno() {
        return sampatientidcardno;
    }

    public void setSampatientidcardno(String sampatientidcardno) {
        this.sampatientidcardno = sampatientidcardno;
    }

    @Basic
    @Column(name = "SAMPATIENTADDRESS")
    public String getSampatientaddress() {
        return sampatientaddress;
    }

    public void setSampatientaddress(String sampatientaddress) {
        this.sampatientaddress = sampatientaddress;
    }

    @Basic
    @Column(name = "SAMPATIENTPHONENO")
    public String getSampatientphoneno() {
        return sampatientphoneno;
    }

    public void setSampatientphoneno(String sampatientphoneno) {
        this.sampatientphoneno = sampatientphoneno;
    }

    @Basic
    @Column(name = "SAMPATIENTCOMPANY")
    public String getSampatientcompany() {
        return sampatientcompany;
    }

    public void setSampatientcompany(String sampatientcompany) {
        this.sampatientcompany = sampatientcompany;
    }

    @Basic
    @Column(name = "SAMISMENOPAUSE")
    public int getSamismenopause() {
        return samismenopause;
    }

    public void setSamismenopause(int samismenopause) {
        this.samismenopause = samismenopause;
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
    @Column(name = "SAMISCHARGED")
    public int getSamischarged() {
        return samischarged;
    }

    public void setSamischarged(int samischarged) {
        this.samischarged = samischarged;
    }

    @Basic
    @Column(name = "SAMRECEIVERTIME")
    public Date getSamreceivertime() {
        return samreceivertime;
    }

    public void setSamreceivertime(Date samreceivertime) {
        this.samreceivertime = samreceivertime;
    }

    @Basic
    @Column(name = "SAMRECEIVERID")
    public String getSamreceiverid() {
        return samreceiverid;
    }

    public void setSamreceiverid(String samreceiverid) {
        this.samreceiverid = samreceiverid;
    }

    @Basic
    @Column(name = "SAMRECEIVERNAME")
    public String getSamreceivername() {
        return samreceivername;
    }

    public void setSamreceivername(String samreceivername) {
        this.samreceivername = samreceivername;
    }

    @Basic
    @Column(name = "SAMREGISTTIME")
    public Date getSamregisttime() {
        return samregisttime;
    }

    public void setSamregisttime(Date samregisttime) {
        this.samregisttime = samregisttime;
    }

    @Basic
    @Column(name = "SAMREGISTERID")
    public String getSamregisterid() {
        return samregisterid;
    }

    public void setSamregisterid(String samregisterid) {
        this.samregisterid = samregisterid;
    }

    @Basic
    @Column(name = "SAMREGISTERNAME")
    public String getSamregistername() {
        return samregistername;
    }

    public void setSamregistername(String samregistername) {
        this.samregistername = samregistername;
    }

    @Basic
    @Column(name = "SAMINITIALLYTIME")
    public Date getSaminitiallytime() {
        return saminitiallytime;
    }

    public void setSaminitiallytime(Date saminitiallytime) {
        this.saminitiallytime = saminitiallytime;
    }

    @Basic
    @Column(name = "SAMINITIALLYUSERID")
    public String getSaminitiallyuserid() {
        return saminitiallyuserid;
    }

    public void setSaminitiallyuserid(String saminitiallyuserid) {
        this.saminitiallyuserid = saminitiallyuserid;
    }

    @Basic
    @Column(name = "SAMINITIALLYUSERNAME")
    public String getSaminitiallyusername() {
        return saminitiallyusername;
    }

    public void setSaminitiallyusername(String saminitiallyusername) {
        this.saminitiallyusername = saminitiallyusername;
    }

    @Basic
    @Column(name = "SAMAUDITEDTIME")
    public Date getSamauditedtime() {
        return samauditedtime;
    }

    public void setSamauditedtime(Date samauditedtime) {
        this.samauditedtime = samauditedtime;
    }

    @Basic
    @Column(name = "SAMAUDITERID")
    public String getSamauditerid() {
        return samauditerid;
    }

    public void setSamauditerid(String samauditerid) {
        this.samauditerid = samauditerid;
    }

    @Basic
    @Column(name = "SAMAUDITER")
    public String getSamauditer() {
        return samauditer;
    }

    public void setSamauditer(String samauditer) {
        this.samauditer = samauditer;
    }

    @Basic
    @Column(name = "SAMREPORTEDTIME")
    public Date getSamreportedtime() {
        return samreportedtime;
    }

    public void setSamreportedtime(Date samreportedtime) {
        this.samreportedtime = samreportedtime;
    }

    @Basic
    @Column(name = "SAMREPORTORID")
    public String getSamreportorid() {
        return samreportorid;
    }

    public void setSamreportorid(String samreportorid) {
        this.samreportorid = samreportorid;
    }

    @Basic
    @Column(name = "SAMREPORTOR")
    public String getSamreportor() {
        return samreportor;
    }

    public void setSamreportor(String samreportor) {
        this.samreportor = samreportor;
    }

    @Basic
    @Column(name = "SAMISDELETED")
    public int getSamisdeleted() {
        return samisdeleted;
    }

    public void setSamisdeleted(int samisdeleted) {
        this.samisdeleted = samisdeleted;
    }

    @Basic
    @Column(name = "SAMREMARK")
    public String getSamremark() {
        return samremark;
    }

    public void setSamremark(String samremark) {
        this.samremark = samremark;
    }

    @Basic
    @Column(name = "SAMTASKID")
    public String getSamtaskid() {
        return samtaskid;
    }

    public void setSamtaskid(String samtaskid) {
        this.samtaskid = samtaskid;
    }

    @Basic
    @Column(name = "SAMFOLLOWUPID")
    public String getSamfollowupid() {
        return samfollowupid;
    }

    public void setSamfollowupid(String samfollowupid) {
        this.samfollowupid = samfollowupid;
    }

    @Basic
    @Column(name = "SAMPDFUPLOAD")
    public int getSampdfupload() {
        return sampdfupload;
    }

    public void setSampdfupload(int sampdfupload) {
        this.sampdfupload = sampdfupload;
    }

    @Basic
    @Column(name = "SAMISSENTRESULT")
    public int getSamissentresult() {
        return samissentresult;
    }

    public void setSamissentresult(int samissentresult) {
        this.samissentresult = samissentresult;
    }

    @Basic
    @Column(name = "SAMFIRSTV")
    public String getSamfirstv() {
        return samfirstv;
    }

    public void setSamfirstv(String samfirstv) {
        this.samfirstv = samfirstv;
    }

    @Basic
    @Column(name = "SAMSECONDV")
    public String getSamsecondv() {
        return samsecondv;
    }

    public void setSamsecondv(String samsecondv) {
        this.samsecondv = samsecondv;
    }

    @Basic
    @Column(name = "SAMTHIRDV")
    public String getSamthirdv() {
        return samthirdv;
    }

    public void setSamthirdv(String samthirdv) {
        this.samthirdv = samthirdv;
    }

    @Basic
    @Column(name = "SAMFIRSTD")
    public Date getSamfirstd() {
        return samfirstd;
    }

    public void setSamfirstd(Date samfirstd) {
        this.samfirstd = samfirstd;
    }

    @Basic
    @Column(name = "SAMSECONDD")
    public Date getSamsecondd() {
        return samsecondd;
    }

    public void setSamsecondd(Date samsecondd) {
        this.samsecondd = samsecondd;
    }

    @Basic
    @Column(name = "SAMFIRSTN")
    public int getSamfirstn() {
        return samfirstn;
    }

    public void setSamfirstn(int samfirstn) {
        this.samfirstn = samfirstn;
    }

    @Basic
    @Column(name = "SAMCREATETIME")
    public Date getSamcreatetime() {
        return samcreatetime;
    }

    public void setSamcreatetime(Date samcreatetime) {
        this.samcreatetime = samcreatetime;
    }

    @Basic
    @Column(name = "SAMCREATEUSER")
    public String getSamcreateuser() {
        return samcreateuser;
    }

    public void setSamcreateuser(String samcreateuser) {
        this.samcreateuser = samcreateuser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PimsPathologySample that = (PimsPathologySample) o;

        if (sampleid != that.sampleid) return false;
        if (samcustomerid != that.samcustomerid) return false;
        if (sampathologyid != that.sampathologyid) return false;
        if (samsource != that.samsource) return false;
        if (sampatienttype != that.sampatienttype) return false;
        if (samsamplestatus != that.samsamplestatus) return false;
        if (saminspectionid != null ? !saminspectionid.equals(that.saminspectionid) : that.saminspectionid != null)
            return false;
        if (sampathologycode != null ? !sampathologycode.equals(that.sampathologycode) : that.sampathologycode != null)
            return false;
        if (samotherid != null ? !samotherid.equals(that.samotherid) : that.samotherid != null) return false;
        if (samrequistionid != null ? !samrequistionid.equals(that.samrequistionid) : that.samrequistionid != null)
            return false;
        if (sampatientid != null ? !sampatientid.equals(that.sampatientid) : that.sampatientid != null) return false;
        if (saminpatientid != null ? !saminpatientid.equals(that.saminpatientid) : that.saminpatientid != null)
            return false;
        if (saminpatientno != null ? !saminpatientno.equals(that.saminpatientno) : that.saminpatientno != null)
            return false;
        if (sampatientnumber != null ? !sampatientnumber.equals(that.sampatientnumber) : that.sampatientnumber != null)
            return false;
        if (sampatientname != null ? !sampatientname.equals(that.sampatientname) : that.sampatientname != null)
            return false;
        if (sampatientsex != that.sampatientsex )
            return false;
        if (sampatientage != null ? !sampatientage.equals(that.sampatientage) : that.sampatientage != null)
            return false;
        if (sampatientagetype != null ? !sampatientagetype.equals(that.sampatientagetype) : that.sampatientagetype != null)
            return false;
        if (sampatientbed != null ? !sampatientbed.equals(that.sampatientbed) : that.sampatientbed != null)
            return false;
        if (samsampleclass != null ? !samsampleclass.equals(that.samsampleclass) : that.samsampleclass != null)
            return false;
        if (samsamplename != null ? !samsamplename.equals(that.samsamplename) : that.samsamplename != null)
            return false;
        if (sampopuser != null ? !sampopuser.equals(that.sampopuser) : that.sampopuser != null) return false;
        if (samisemergency != that.samisemergency )
            return false;
        if (samisdecacified != that.samisdecacified )
            return false;
        if (samissamplingall != that.samissamplingall )
            return false;
        if (samreqtime != null ? !samreqtime.equals(that.samreqtime) : that.samreqtime != null) return false;
        if (samreqdocid != null ? !samreqdocid.equals(that.samreqdocid) : that.samreqdocid != null) return false;
        if (samreqdocname != null ? !samreqdocname.equals(that.samreqdocname) : that.samreqdocname != null)
            return false;
        if (samsendtime != null ? !samsendtime.equals(that.samsendtime) : that.samsendtime != null) return false;
        if (samsenddoctorid != null ? !samsenddoctorid.equals(that.samsenddoctorid) : that.samsenddoctorid != null)
            return false;
        if (samsenddoctorname != null ? !samsenddoctorname.equals(that.samsenddoctorname) : that.samsenddoctorname != null)
            return false;
        if (samsendhospital != null ? !samsendhospital.equals(that.samsendhospital) : that.samsendhospital != null)
            return false;
        if (samsendphone != null ? !samsendphone.equals(that.samsendphone) : that.samsendphone != null) return false;
        if (samdigcode != null ? !samdigcode.equals(that.samdigcode) : that.samdigcode != null) return false;
        if (samdeptcode != null ? !samdeptcode.equals(that.samdeptcode) : that.samdeptcode != null) return false;
        if (samdeptname != null ? !samdeptname.equals(that.samdeptname) : that.samdeptname != null) return false;
        if (samwardcode != null ? !samwardcode.equals(that.samwardcode) : that.samwardcode != null) return false;
        if (samwardname != null ? !samwardname.equals(that.samwardname) : that.samwardname != null) return false;
        if (sampatientdignoses != null ? !sampatientdignoses.equals(that.sampatientdignoses) : that.sampatientdignoses != null)
            return false;
        if (sampatientidcardno != null ? !sampatientidcardno.equals(that.sampatientidcardno) : that.sampatientidcardno != null)
            return false;
        if (sampatientaddress != null ? !sampatientaddress.equals(that.sampatientaddress) : that.sampatientaddress != null)
            return false;
        if (sampatientphoneno != null ? !sampatientphoneno.equals(that.sampatientphoneno) : that.sampatientphoneno != null)
            return false;
        if (sampatientcompany != null ? !sampatientcompany.equals(that.sampatientcompany) : that.sampatientcompany != null)
            return false;
        if (samismenopause != that.samismenopause )
            return false;
        if (reqlastmenstruation != null ? !reqlastmenstruation.equals(that.reqlastmenstruation) : that.reqlastmenstruation != null)
            return false;
        if (samischarged != that.samischarged ) return false;
        if (samreceivertime != null ? !samreceivertime.equals(that.samreceivertime) : that.samreceivertime != null)
            return false;
        if (samreceiverid != null ? !samreceiverid.equals(that.samreceiverid) : that.samreceiverid != null)
            return false;
        if (samreceivername != null ? !samreceivername.equals(that.samreceivername) : that.samreceivername != null)
            return false;
        if (samregisttime != null ? !samregisttime.equals(that.samregisttime) : that.samregisttime != null)
            return false;
        if (samregisterid != null ? !samregisterid.equals(that.samregisterid) : that.samregisterid != null)
            return false;
        if (samregistername != null ? !samregistername.equals(that.samregistername) : that.samregistername != null)
            return false;
        if (saminitiallytime != null ? !saminitiallytime.equals(that.saminitiallytime) : that.saminitiallytime != null)
            return false;
        if (saminitiallyuserid != null ? !saminitiallyuserid.equals(that.saminitiallyuserid) : that.saminitiallyuserid != null)
            return false;
        if (saminitiallyusername != null ? !saminitiallyusername.equals(that.saminitiallyusername) : that.saminitiallyusername != null)
            return false;
        if (samauditedtime != null ? !samauditedtime.equals(that.samauditedtime) : that.samauditedtime != null)
            return false;
        if (samauditerid != null ? !samauditerid.equals(that.samauditerid) : that.samauditerid != null) return false;
        if (samauditer != null ? !samauditer.equals(that.samauditer) : that.samauditer != null) return false;
        if (samreportedtime != null ? !samreportedtime.equals(that.samreportedtime) : that.samreportedtime != null)
            return false;
        if (samreportorid != null ? !samreportorid.equals(that.samreportorid) : that.samreportorid != null)
            return false;
        if (samreportor != null ? !samreportor.equals(that.samreportor) : that.samreportor != null) return false;
        if (samisdeleted != that.samisdeleted ) return false;
        if (samremark != null ? !samremark.equals(that.samremark) : that.samremark != null) return false;
        if (samtaskid != null ? !samtaskid.equals(that.samtaskid) : that.samtaskid != null) return false;
        if (samfollowupid != null ? !samfollowupid.equals(that.samfollowupid) : that.samfollowupid != null)
            return false;
        if (sampdfupload != that.sampdfupload ) return false;
        if (samissentresult != that.samissentresult )
            return false;
        if (samfirstv != null ? !samfirstv.equals(that.samfirstv) : that.samfirstv != null) return false;
        if (samsecondv != null ? !samsecondv.equals(that.samsecondv) : that.samsecondv != null) return false;
        if (samthirdv != null ? !samthirdv.equals(that.samthirdv) : that.samthirdv != null) return false;
        if (samfirstd != null ? !samfirstd.equals(that.samfirstd) : that.samfirstd != null) return false;
        if (samsecondd != null ? !samsecondd.equals(that.samsecondd) : that.samsecondd != null) return false;
        if (samfirstn != that.samfirstn ) return false;
        if (samcreatetime != null ? !samcreatetime.equals(that.samcreatetime) : that.samcreatetime != null)
            return false;
        if (samcreateuser != null ? !samcreateuser.equals(that.samcreateuser) : that.samcreateuser != null)
            return false;
        if (samjjsj != null ? !samjjsj.equals(that.samjjsj) : that.samjjsj != null)
            return false;
        if (samjcxm != null ? !samjcxm.equals(that.samjcxm) : that.samjcxm != null)
            return false;
        if (sampiecedoctorid != null ? !sampiecedoctorid.equals(that.sampiecedoctorid) : that.sampiecedoctorid != null)
            return false;
        if (sampiecedoctorname != null ? !sampiecedoctorname.equals(that.sampiecedoctorname) : that.sampiecedoctorname != null)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (sampleid ^ (sampleid >>> 32));
        result = 31 * result + (saminspectionid != null ? saminspectionid.hashCode() : 0);
        result = 31 * result + (sampathologycode != null ? sampathologycode.hashCode() : 0);
        result = 31 * result + (int) (samcustomerid ^ (samcustomerid >>> 32));
        result = 31 * result + (int) (sampathologyid ^ (sampathologyid >>> 32));
        result = 31 * result + (samsource ^ (samsource >>> 32));
        result = 31 * result + (samotherid != null ? samotherid.hashCode() : 0);
        result = 31 * result + (samrequistionid != null ? samrequistionid.hashCode() : 0);
        result = 31 * result + (sampatientid != null ? sampatientid.hashCode() : 0);
        result = 31 * result + (saminpatientid != null ? saminpatientid.hashCode() : 0);
        result = 31 * result + (saminpatientno != null ? saminpatientno.hashCode() : 0);
        result = 31 * result + (sampatientnumber != null ? sampatientnumber.hashCode() : 0);
        result = 31 * result + (int) (sampatienttype ^ (sampatienttype >>> 32));
        result = 31 * result + (sampatientname != null ? sampatientname.hashCode() : 0);
        result = 31 * result + (sampatientsex ^ (sampatientsex >>> 32));
        result = 31 * result + (sampatientage != null ? sampatientage.hashCode() : 0);
        result = 31 * result + (sampatientagetype != null ? sampatientagetype.hashCode() : 0);
        result = 31 * result + (sampatientbed != null ? sampatientbed.hashCode() : 0);
        result = 31 * result + (samsampleclass != null ? samsampleclass.hashCode() : 0);
        result = 31 * result + (samsamplename != null ? samsamplename.hashCode() : 0);
        result = 31 * result + (sampopuser != null ? sampopuser.hashCode() : 0);
        result = 31 * result + (samisemergency ^ (samisemergency >>> 32));
        result = 31 * result + (samisdecacified ^ (samisdecacified >>> 32));
        result = 31 * result + (samissamplingall ^ (samissamplingall >>> 32));
        result = 31 * result + (int) (samsamplestatus ^ (samsamplestatus >>> 32));
        result = 31 * result + (samreqtime != null ? samreqtime.hashCode() : 0);
        result = 31 * result + (samreqdocid != null ? samreqdocid.hashCode() : 0);
        result = 31 * result + (samreqdocname != null ? samreqdocname.hashCode() : 0);
        result = 31 * result + (samsendtime != null ? samsendtime.hashCode() : 0);
        result = 31 * result + (samsenddoctorid != null ? samsenddoctorid.hashCode() : 0);
        result = 31 * result + (samsenddoctorname != null ? samsenddoctorname.hashCode() : 0);
        result = 31 * result + (samsendhospital != null ? samsendhospital.hashCode() : 0);
        result = 31 * result + (samsendphone != null ? samsendphone.hashCode() : 0);
        result = 31 * result + (samdigcode != null ? samdigcode.hashCode() : 0);
        result = 31 * result + (samdeptcode != null ? samdeptcode.hashCode() : 0);
        result = 31 * result + (samdeptname != null ? samdeptname.hashCode() : 0);
        result = 31 * result + (samwardcode != null ? samwardcode.hashCode() : 0);
        result = 31 * result + (samwardname != null ? samwardname.hashCode() : 0);
        result = 31 * result + (sampatientdignoses != null ? sampatientdignoses.hashCode() : 0);
        result = 31 * result + (sampatientidcardno != null ? sampatientidcardno.hashCode() : 0);
        result = 31 * result + (sampatientaddress != null ? sampatientaddress.hashCode() : 0);
        result = 31 * result + (sampatientphoneno != null ? sampatientphoneno.hashCode() : 0);
        result = 31 * result + (sampatientcompany != null ? sampatientcompany.hashCode() : 0);
        result = 31 * result + (samismenopause ^ (samismenopause >>> 32));
        result = 31 * result + (reqlastmenstruation != null ? reqlastmenstruation.hashCode() : 0);
        result = 31 * result + (samischarged ^ (samischarged >>> 32));
        result = 31 * result + (samreceivertime != null ? samreceivertime.hashCode() : 0);
        result = 31 * result + (samreceiverid != null ? samreceiverid.hashCode() : 0);
        result = 31 * result + (samreceivername != null ? samreceivername.hashCode() : 0);
        result = 31 * result + (samregisttime != null ? samregisttime.hashCode() : 0);
        result = 31 * result + (samregisterid != null ? samregisterid.hashCode() : 0);
        result = 31 * result + (samregistername != null ? samregistername.hashCode() : 0);
        result = 31 * result + (saminitiallytime != null ? saminitiallytime.hashCode() : 0);
        result = 31 * result + (saminitiallyuserid != null ? saminitiallyuserid.hashCode() : 0);
        result = 31 * result + (saminitiallyusername != null ? saminitiallyusername.hashCode() : 0);
        result = 31 * result + (samauditedtime != null ? samauditedtime.hashCode() : 0);
        result = 31 * result + (samauditerid != null ? samauditerid.hashCode() : 0);
        result = 31 * result + (samauditer != null ? samauditer.hashCode() : 0);
        result = 31 * result + (samreportedtime != null ? samreportedtime.hashCode() : 0);
        result = 31 * result + (samreportorid != null ? samreportorid.hashCode() : 0);
        result = 31 * result + (samreportor != null ? samreportor.hashCode() : 0);
        result = 31 * result + (samisdeleted ^ (samisdeleted >>> 32));
        result = 31 * result + (samremark != null ? samremark.hashCode() : 0);
        result = 31 * result + (samtaskid != null ? samtaskid.hashCode() : 0);
        result = 31 * result + (samfollowupid != null ? samfollowupid.hashCode() : 0);
        result = 31 * result + (sampdfupload ^ (sampdfupload >>> 32));
        result = 31 * result + (samissentresult ^ (samissentresult >>> 32));
        result = 31 * result + (samfirstv != null ? samfirstv.hashCode() : 0);
        result = 31 * result + (samsecondv != null ? samsecondv.hashCode() : 0);
        result = 31 * result + (samthirdv != null ? samthirdv.hashCode() : 0);
        result = 31 * result + (samfirstd != null ? samfirstd.hashCode() : 0);
        result = 31 * result + (samsecondd != null ? samsecondd.hashCode() : 0);
        result = 31 * result + (samfirstn ^ (samfirstn >>> 32));
        result = 31 * result + (samcreatetime != null ? samcreatetime.hashCode() : 0);
        result = 31 * result + (samcreateuser != null ? samcreateuser.hashCode() : 0);
        result = 31 * result + (samjjsj != null ? samjjsj.hashCode() : 0);
        result = 31 * result + (samjcxm != null ? samjcxm.hashCode() : 0);
        result = 31 * result + (sampiecedoctorid != null ? sampiecedoctorid.hashCode() : 0);
        result = 31 * result + (sampiecedoctorname != null ? sampiecedoctorname.hashCode() : 0);
        return result;
    }
}
