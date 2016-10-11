package com.pims.model;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * Created by king on 2016/10/6.
 */
public class PimsBaseModel {

    private HttpServletRequest request;
    private String logyid;
    private String req_code;
    private String patient_name;
    private String send_hosptail;
    private String req_bf_time;
    private String req_af_time;
    private String send_dept;
    private String send_doctor;
    private String req_sts;
    private int page;
    private int row;
    private int start;
    private int end;
    private String sidx;
    private String sord;
    public  PimsBaseModel(HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        this.request = request;
        String pages = request.getParameter("page");
        String rows = request.getParameter("rows");
        this.req_code = request.getParameter("req_code");
        this.patient_name = request.getParameter("patient_name");
        this.send_hosptail = request.getParameter("send_hosptail");
        this.req_bf_time = request.getParameter("req_bf_time");
        this.req_af_time = request.getParameter("req_af_time");
        this.send_dept = request.getParameter("send_dept");
        this.send_doctor = request.getParameter("send_doctor");
        this.req_sts = request.getParameter("req_sts");
        this.sidx = request.getParameter("sidx");
        this.sord = request.getParameter("sord");
        this.logyid = request.getParameter("logyid");
        this.page = Integer.parseInt(pages);
        this.row = Integer.parseInt(rows);
        this.start = row * (page - 1);
        this.end = row * page;
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

    public String getReq_bf_time() {
        return req_bf_time;
    }

    public void setReq_bf_time(String req_bf_time) {
        this.req_bf_time = req_bf_time;
    }

    public String getReq_af_time() {
        return req_af_time;
    }

    public void setReq_af_time(String req_af_time) {
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
}
