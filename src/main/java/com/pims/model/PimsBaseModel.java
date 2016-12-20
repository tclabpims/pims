package com.pims.model;

import com.smart.Constants;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Calendar;
import java.sql.Date;

/**
 * Created by king on 2016/10/6.
 */
public class PimsBaseModel {

    private HttpServletRequest request;
    private String logyid;//病种类别
    private String req_code;//病理号
    private String patient_name;//病人
    private String send_hosptail;//送检医院
    private Date req_bf_time;//起始时间
    private Date req_af_time;//结束时间
    private String send_dept;//送检科室
    private String send_doctor;//送检医生
    private String req_sts;//状态
    private int page;
    private int row;
    private int start;
    private int end;
    private String sidx;
    private String sord;
    private String sampatientnumber;//住院号
    private String sampatientbed;//床号
    private String sampatientsex;//性别
    private String piedoctorname;//取材医生
    private String parsectioneddoctor;//切片医生
    private String saminitiallyusername;//诊断医生
    private String myzh;//免疫组化
    private String tsrs;//特殊染色
    private String fzbl;//分子病理
    private String blzd;//病理诊断
    private String qcbw;//取材部位
    private String slipathologyid;
    private String sliid;
    private String current;
    private Date sli_in_time;
    private String slidept;
    private String sliresult;
    private String slicustomerid;
    private String marname;
    public PimsBaseModel(){

    }
    public  PimsBaseModel(HttpServletRequest request) throws UnsupportedEncodingException, ParseException {
        Calendar cal = Calendar.getInstance();
        request.setCharacterEncoding("UTF-8");
        this.request = request;
        String pages = request.getParameter("page")==null?"1":request.getParameter("page");
        String rows = request.getParameter("rows")==null?"10":request.getParameter("rows");
        this.req_code = request.getParameter("req_code");
        this.patient_name = request.getParameter("patient_name");
        this.send_hosptail = request.getParameter("send_hosptail");
        this.req_bf_time = (request.getParameter("req_bf_time")== null|| request.getParameter("req_bf_time").equals(""))?null:new Date(Constants.DF2.parse(request.getParameter("req_bf_time")).getTime());
        this.req_af_time = (request.getParameter("req_bf_time")== null|| request.getParameter("req_bf_time").equals(""))?null:new Date(Constants.DF2.parse(request.getParameter("req_af_time")).getTime());
        if(this.req_af_time != null){
            cal.setTime(this.req_af_time);
            cal.add(Calendar.DAY_OF_YEAR, +1);
            this.req_af_time = new Date(cal.getTime().getTime());
        }
        this.send_dept = request.getParameter("send_dept");
        this.send_doctor = request.getParameter("send_doctor");
        this.req_sts = request.getParameter("req_sts");
        this.sampatientnumber = request.getParameter("sampatientnumber");//住院号
        this.sampatientbed = request.getParameter("sampatientbed");//床号
        this.sampatientsex = request.getParameter("sampatientsex");//性别
        this.piedoctorname = request.getParameter("piedoctorname");//取材医生
        this.parsectioneddoctor = request.getParameter("parsectioneddoctor");//切片医生
        this.saminitiallyusername = request.getParameter("saminitiallyusername");//诊断医生
        this.myzh = request.getParameter("myzh");//免疫组化
        this.tsrs = request.getParameter("tsrs");//特殊染色
        this.fzbl = request.getParameter("fzbl");//分子病理
        this.blzd = request.getParameter("blzd");//病理诊断
        this.qcbw = request.getParameter("qcbw");//取材部位
        this.sidx = request.getParameter("sidx");
        this.sord = request.getParameter("sord");
        this.logyid = request.getParameter("logyid");
        this.page = Integer.parseInt(pages);
        this.row = Integer.parseInt(rows);
        this.start = row * (page - 1);
        this.end = row * page;
        this.sliid = request.getParameter("sliid");
        this.current = request.getParameter("current");
        this.patient_name = request.getParameter("patient_name");
        this.sli_in_time = (request.getParameter("sli_in_time")== null|| request.getParameter("sli_in_time").equals(""))?null:new Date(Constants.DF2.parse(request.getParameter("sli_in_time")).getTime());
        this.slidept = request.getParameter("slidept");
        this.sliresult = request.getParameter("sliresult");
        this.slicustomerid = request.getParameter("slicustomerid");
        this.slipathologyid = request.getParameter("slipathologyid");
        this.marname = request.getParameter("marname");

    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public String getReq_code() {
        return req_code;
    }

    public void setReq_code(String req_code) {
        this.req_code = req_code;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public String getSend_hosptail() {
        return send_hosptail;
    }

    public void setSend_hosptail(String send_hosptail) {
        this.send_hosptail = send_hosptail;
    }

    public Date getReq_bf_time() {
        return req_bf_time;
    }

    public void setReq_bf_time(Date req_bf_time) {
        this.req_bf_time = req_bf_time;
    }

    public Date getReq_af_time() {
        return req_af_time;
    }

    public void setReq_af_time(Date req_af_time) {
        this.req_af_time = req_af_time;
    }

    public String getSend_dept() {
        return send_dept;
    }

    public void setSend_dept(String send_dept) {
        this.send_dept = send_dept;
    }

    public String getSend_doctor() {
        return send_doctor;
    }

    public void setSend_doctor(String send_doctor) {
        this.send_doctor = send_doctor;
    }

    public String getReq_sts() {
        return req_sts;
    }

    public void setReq_sts(String req_sts) {
        this.req_sts = req_sts;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public String getSidx() {
        return sidx;
    }

    public void setSidx(String sidx) {
        this.sidx = sidx;
    }

    public String getSord() {
        return sord;
    }

    public void setSord(String sord) {
        this.sord = sord;
    }

    public String getLogyid() {
        return logyid;
    }

    public void setLogyid(String logyid) {
        this.logyid = logyid;
    }

    public String getSampatientnumber() {
        return sampatientnumber;
    }

    public void setSampatientnumber(String sampatientnumber) {
        this.sampatientnumber = sampatientnumber;
    }

    public String getSampatientbed() {
        return sampatientbed;
    }

    public void setSampatientbed(String sampatientbed) {
        this.sampatientbed = sampatientbed;
    }

    public String getSampatientsex() {
        return sampatientsex;
    }

    public void setSampatientsex(String sampatientsex) {
        this.sampatientsex = sampatientsex;
    }

    public String getPiedoctorname() {
        return piedoctorname;
    }

    public void setPiedoctorname(String piedoctorname) {
        this.piedoctorname = piedoctorname;
    }

    public String getParsectioneddoctor() {
        return parsectioneddoctor;
    }

    public void setParsectioneddoctor(String parsectioneddoctor) {
        this.parsectioneddoctor = parsectioneddoctor;
    }

    public String getSaminitiallyusername() {
        return saminitiallyusername;
    }

    public void setSaminitiallyusername(String saminitiallyusername) {
        this.saminitiallyusername = saminitiallyusername;
    }

    public String getMyzh() {
        return myzh;
    }

    public void setMyzh(String myzh) {
        this.myzh = myzh;
    }

    public String getTsrs() {
        return tsrs;
    }

    public void setTsrs(String tsrs) {
        this.tsrs = tsrs;
    }

    public String getFzbl() {
        return fzbl;
    }

    public void setFzbl(String fzbl) {
        this.fzbl = fzbl;
    }

    public String getBlzd() {
        return blzd;
    }

    public void setBlzd(String blzd) {
        this.blzd = blzd;
    }

    public String getQcbw() {
        return qcbw;
    }

    public void setQcbw(String qcbw) {
        this.qcbw = qcbw;
    }

    public String getSlipathologyid() {
        return slipathologyid;
    }

    public void setSlipathologyid(String slipathologyid) {
        this.slipathologyid = slipathologyid;
    }

    public String getSliid() {
        return sliid;
    }

    public void setSliid(String sliid) {
        this.sliid = sliid;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public Date getSli_in_time() {
        return sli_in_time;
    }

    public void setSli_in_time(Date sli_in_time) {
        this.sli_in_time = sli_in_time;
    }

    public String getSlidept() {
        return slidept;
    }

    public void setSlidept(String slidept) {
        this.slidept = slidept;
    }

    public String getSliresult() {
        return sliresult;
    }

    public void setSliresult(String sliresult) {
        this.sliresult = sliresult;
    }

    public String getSlicustomerid() {
        return slicustomerid;
    }

    public void setSlicustomerid(String slicustomerid) {
        this.slicustomerid = slicustomerid;
    }

    public String getMarname() {
        return marname;
    }

    public void setMarname(String marname) {
        this.marname = marname;
    }
}
