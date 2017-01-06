//package com.pims.model;
//
//import javax.persistence.*;
//import java.util.Date;
///**
// * Created by zp on 2016/12/23.
// */
//@Entity
//@Table(name = "VIEW_CONSULTATION_QUERY")
//public class ViewConsultationQuery {
//    private long sampleid;//样本id
//    private long sampathologyid;//病种类别ID
//    private long samcustomerid;//患者id
//    private long sampatientsex;//患者性别
//    private long samsamplestatus;//病理状态
//    private long consultationid;//会诊id
//    private long conconsultationstate;//会诊状态
//    private String sampathologycode;//病理编号
//    private String sampatientname;//患者名称
//    private String sampatientage;//患者年龄
//    private String samsenddoctorname;//送检医生
//    private String samsendhospital;//送检单位
//    private String samdeptname;//送检科室
//    private String consponsoreduserid;//发起人id
//    private String consponsoredusername;//发起人姓名
//    private Date consponsoredtime;//发起时间
//    private String confinisheduserid;//完成人id
//    private String confinishedusername;//完成者姓名
//    private Date confinishedtime;//完成时间
//
//    @Id
//    @Column(name="SAMPLEID")
//    public long getSampleid() {
//        return sampleid;
//    }
//
//    public void setSampleid(long sampleid) {
//        this.sampleid = sampleid;
//    }
//
//    @Basic
//    @Column(name = "SAMPATHOLOGYID")
//    public long getSampathologyid() {
//        return sampathologyid;
//    }
//
//    public void setSampathologyid(long sampathologyid) {
//        this.sampathologyid = sampathologyid;
//    }
//
//    @Basic
//    @Column(name = "SAMCUSTOMERID")
//    public long getSamcustomerid() {
//        return samcustomerid;
//    }
//
//    public void setSamcustomerid(long samcustomerid) {
//        this.samcustomerid = samcustomerid;
//    }
//
//    @Basic
//    @Column(name = "SAMPATIENTSEX")
//    public long getSampatientsex() {
//        return sampatientsex;
//    }
//
//    public void setSampatientsex(long sampatientsex) {
//        this.sampatientsex = sampatientsex;
//    }
//
//    @Basic
//    @Column(name = "SAMSAMPLESTATUS")
//    public long getSamsamplestatus() {
//        return samsamplestatus;
//    }
//
//    public void setSamsamplestatus(long samsamplestatus) {
//        this.samsamplestatus = samsamplestatus;
//    }
//
//    @Basic
//    @Column(name = "CONSULTATIONID")
//    public long getConsultationid() {
//        return consultationid;
//    }
//
//    public void setConsultationid(long consultationid) {
//        this.consultationid = consultationid;
//    }
//
//    @Basic
//    @Column(name = "CONCONSULTATIONSTATE")
//    public long getConconsultationstate() {
//        return conconsultationstate;
//    }
//
//    public void setConconsultationstate(long conconsultationstate) {
//        this.conconsultationstate = conconsultationstate;
//    }
//
//    @Basic
//    @Column(name = "SAMPATHOLOGYCODE")
//    public String getSampathologycode() {
//        return sampathologycode;
//    }
//
//    public void setSampathologycode(String sampathologycode) {
//        this.sampathologycode = sampathologycode;
//    }
//
//    @Basic
//    @Column(name = "SAMPATIENTNAME")
//    public String getSampatientname() {
//        return sampatientname;
//    }
//
//    public void setSampatientname(String sampatientname) {
//        this.sampatientname = sampatientname;
//    }
//
//    @Basic
//    @Column(name = "SAMPATIENTAGE")
//    public String getSampatientage() {
//        return sampatientage;
//    }
//
//    public void setSampatientage(String sampatientage) {
//        this.sampatientage = sampatientage;
//    }
//
//    @Basic
//    @Column(name = "SAMSENDDOCTORNAME")
//    public String getSamsenddoctorname() {
//        return samsenddoctorname;
//    }
//
//    public void setSamsenddoctorname(String samsenddoctorname) {
//        this.samsenddoctorname = samsenddoctorname;
//    }
//
//    @Basic
//    @Column(name = "SAMSENDHOSPITAL")
//    public String getSamsendhospital() {
//        return samsendhospital;
//    }
//
//    public void setSamsendhospital(String samsendhospital) {
//        this.samsendhospital = samsendhospital;
//    }
//
//    @Basic
//    @Column(name = "SAMDEPTNAME")
//    public String getSamdeptname() {
//        return samdeptname;
//    }
//
//    public void setSamdeptname(String samdeptname) {
//        this.samdeptname = samdeptname;
//    }
//
//    @Basic
//    @Column(name = "CONSPONSOREDUSERID")
//    public String getConsponsoreduserid() {
//        return consponsoreduserid;
//    }
//
//    public void setConsponsoreduserid(String consponsoreduserid) {
//        this.consponsoreduserid = consponsoreduserid;
//    }
//
//    @Basic
//    @Column(name = "CONSPONSOREDUSERNAME")
//    public String getConsponsoredusername() {
//        return consponsoredusername;
//    }
//
//    public void setConsponsoredusername(String consponsoredusername) {
//        this.consponsoredusername = consponsoredusername;
//    }
//
//    @Basic
//    @Column(name = "CONSPONSOREDTIME")
//    public Date getConsponsoredtime() {
//        return consponsoredtime;
//    }
//
//    public void setConsponsoredtime(Date consponsoredtime) {
//        this.consponsoredtime = consponsoredtime;
//    }
//
//    @Basic
//    @Column(name = "CONFINISHEDUSERID")
//    public String getConfinisheduserid() {
//        return confinisheduserid;
//    }
//
//    public void setConfinisheduserid(String confinisheduserid) {
//        this.confinisheduserid = confinisheduserid;
//    }
//
//    @Basic
//    @Column(name = "CONFINISHEDUSERNAME")
//    public String getConfinishedusername() {
//        return confinishedusername;
//    }
//
//    public void setConfinishedusername(String confinishedusername) {
//        this.confinishedusername = confinishedusername;
//    }
//
//    @Basic
//    @Column(name = "CONFINISHEDTIME")
//    public Date getConfinishedtime() {
//        return confinishedtime;
//    }
//
//    public void setConfinishedtime(Date confinishedtime) {
//        this.confinishedtime = confinishedtime;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        ViewConsultationQuery that = (ViewConsultationQuery) o;
//
//        if (sampleid != that.sampleid) return false;
//        if (sampathologyid != that.sampathologyid) return false;
//        if (samcustomerid != that.samcustomerid) return false;
//        if (sampatientsex != that.sampatientsex) return false;
//        if (samsamplestatus != that.samsamplestatus) return false;
//        if (consultationid != that.consultationid) return false;
//        if (conconsultationstate != that.conconsultationstate) return false;
//        if (sampathologycode != null ? !sampathologycode.equals(that.sampathologycode) : that.sampathologycode != null)
//            return false;
//        if (sampatientname != null ? !sampatientname.equals(that.sampatientname) : that.sampatientname != null)
//            return false;
//        if (sampatientage != null ? !sampatientage.equals(that.sampatientage) : that.sampatientage != null)
//            return false;
//        if (samsenddoctorname != null ? !samsenddoctorname.equals(that.samsenddoctorname) : that.samsenddoctorname != null)
//            return false;
//        if (samsendhospital != null ? !samsendhospital.equals(that.samsendhospital) : that.samsendhospital != null)
//            return false;
//        if (samdeptname != null ? !samdeptname.equals(that.samdeptname) : that.samdeptname != null) return false;
//        if (consponsoreduserid != null ? !consponsoreduserid.equals(that.consponsoreduserid) : that.consponsoreduserid != null)
//            return false;
//        if (consponsoredusername != null ? !consponsoredusername.equals(that.consponsoredusername) : that.consponsoredusername != null)
//            return false;
//        if (consponsoredtime != null ? !consponsoredtime.equals(that.consponsoredtime) : that.consponsoredtime != null)
//            return false;
//        if (confinisheduserid != null ? !confinisheduserid.equals(that.confinisheduserid) : that.confinisheduserid != null)
//            return false;
//        if (confinishedusername != null ? !confinishedusername.equals(that.confinishedusername) : that.confinishedusername != null)
//            return false;
//        return confinishedtime != null ? confinishedtime.equals(that.confinishedtime) : that.confinishedtime == null;
//
//    }
//
//    @Override
//    public int hashCode() {
//        int result = (int) (sampleid ^ (sampleid >>> 32));
//        result = 31 * result + (int) (sampathologyid ^ (sampathologyid >>> 32));
//        result = 31 * result + (int) (samcustomerid ^ (samcustomerid >>> 32));
//        result = 31 * result + (int) (sampatientsex ^ (sampatientsex >>> 32));
//        result = 31 * result + (int) (samsamplestatus ^ (samsamplestatus >>> 32));
//        result = 31 * result + (int) (consultationid ^ (consultationid >>> 32));
//        result = 31 * result + (int) (conconsultationstate ^ (conconsultationstate >>> 32));
//        result = 31 * result + (sampathologycode != null ? sampathologycode.hashCode() : 0);
//        result = 31 * result + (sampatientname != null ? sampatientname.hashCode() : 0);
//        result = 31 * result + (sampatientage != null ? sampatientage.hashCode() : 0);
//        result = 31 * result + (samsenddoctorname != null ? samsenddoctorname.hashCode() : 0);
//        result = 31 * result + (samsendhospital != null ? samsendhospital.hashCode() : 0);
//        result = 31 * result + (samdeptname != null ? samdeptname.hashCode() : 0);
//        result = 31 * result + (consponsoreduserid != null ? consponsoreduserid.hashCode() : 0);
//        result = 31 * result + (consponsoredusername != null ? consponsoredusername.hashCode() : 0);
//        result = 31 * result + (consponsoredtime != null ? consponsoredtime.hashCode() : 0);
//        result = 31 * result + (confinisheduserid != null ? confinisheduserid.hashCode() : 0);
//        result = 31 * result + (confinishedusername != null ? confinishedusername.hashCode() : 0);
//        result = 31 * result + (confinishedtime != null ? confinishedtime.hashCode() : 0);
//        return result;
//    }
//}
