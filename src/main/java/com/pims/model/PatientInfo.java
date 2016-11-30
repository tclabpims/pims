package com.pims.model;

import java.util.Date;

/**
 * Created by king on 2016/11/30.
 */
public class PatientInfo {
    private long key_no;//id
    private String patient_id;//住院号
    private String inpatient_id;//档案号
    private String patient_name;//病人姓名
    private String patient_sex;//性别
    private Date patient_birth;//出生日期
    private String patient_age;//年龄
    private String patient_age_type;//年龄类型
    private String patient_nation;//民族
    private String patient_type;//患者类型
    private String patient_dept;//住院科室ID
    private String patient_dept_name;//住院科室名称
    private String patient_ward;//住院病区ID
    private String patient_ward_name;//住院病区名称
    private String patient_bed;//床号
    private String lczd;//临床诊断
    private String commpany;//病人单位名称
    private String id_cardno;//身份证号
    private String patient_address;//家庭地址
    private String phone_no;//联系电话
    private String chargr_type;//收费类别ID

    public long getKey_no() {
        return key_no;
    }

    public void setKey_no(long key_no) {
        this.key_no = key_no;
    }

    public String getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(String patient_id) {
        this.patient_id = patient_id;
    }

    public String getInpatient_id() {
        return inpatient_id;
    }

    public void setInpatient_id(String inpatient_id) {
        this.inpatient_id = inpatient_id;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public String getPatient_sex() {
        return patient_sex;
    }

    public void setPatient_sex(String patient_sex) {
        this.patient_sex = patient_sex;
    }

    public Date getPatient_birth() {
        return patient_birth;
    }

    public void setPatient_birth(Date patient_birth) {
        this.patient_birth = patient_birth;
    }

    public String getPatient_age() {
        return patient_age;
    }

    public void setPatient_age(String patient_age) {
        this.patient_age = patient_age;
    }

    public String getPatient_age_type() {
        return patient_age_type;
    }

    public void setPatient_age_type(String patient_age_type) {
        this.patient_age_type = patient_age_type;
    }

    public String getPatient_nation() {
        return patient_nation;
    }

    public void setPatient_nation(String patient_nation) {
        this.patient_nation = patient_nation;
    }

    public String getPatient_type() {
        return patient_type;
    }

    public void setPatient_type(String patient_type) {
        this.patient_type = patient_type;
    }

    public String getPatient_dept() {
        return patient_dept;
    }

    public void setPatient_dept(String patient_dept) {
        this.patient_dept = patient_dept;
    }

    public String getPatient_dept_name() {
        return patient_dept_name;
    }

    public void setPatient_dept_name(String patient_dept_name) {
        this.patient_dept_name = patient_dept_name;
    }

    public String getPatient_ward() {
        return patient_ward;
    }

    public void setPatient_ward(String patient_ward) {
        this.patient_ward = patient_ward;
    }

    public String getPatient_ward_name() {
        return patient_ward_name;
    }

    public void setPatient_ward_name(String patient_ward_name) {
        this.patient_ward_name = patient_ward_name;
    }

    public String getPatient_bed() {
        return patient_bed;
    }

    public void setPatient_bed(String patient_bed) {
        this.patient_bed = patient_bed;
    }

    public String getLczd() {
        return lczd;
    }

    public void setLczd(String lczd) {
        this.lczd = lczd;
    }

    public String getCommpany() {
        return commpany;
    }

    public void setCommpany(String commpany) {
        this.commpany = commpany;
    }

    public String getId_cardno() {
        return id_cardno;
    }

    public void setId_cardno(String id_cardno) {
        this.id_cardno = id_cardno;
    }

    public String getPatient_address() {
        return patient_address;
    }

    public void setPatient_address(String patient_address) {
        this.patient_address = patient_address;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getChargr_type() {
        return chargr_type;
    }

    public void setChargr_type(String chargr_type) {
        this.chargr_type = chargr_type;
    }
}
