package com.pims.model;

import javax.persistence.*;
import java.sql.Time;

/**
 * Created by king on 2016/9/28.
 */
@Entity
@Table(name = "PIMS_SYS_HOSPITAL_INFOS", schema = "KFTEST", catalog = "")
public class PimsSysHospitalInfos {
    private String customercode;
    private String hospitalnamech;
    private String hospitalnameen;
    private String websiteurl;
    private String phonenumber;
    private String address;
    private String postcode;
    private String province;
    private String city;
    private String countyarea;
    private String township;
    private String street;
    private String categroyarea;
    private String salesman;
    private String useflag;
    private Time registtime;

    @Id
    @Column(name = "CUSTOMERCODE")
    public String getCustomercode() {
        return customercode;
    }

    public void setCustomercode(String customercode) {
        this.customercode = customercode;
    }

    @Basic
    @Column(name = "HOSPITALNAMECH")
    public String getHospitalnamech() {
        return hospitalnamech;
    }

    public void setHospitalnamech(String hospitalnamech) {
        this.hospitalnamech = hospitalnamech;
    }

    @Basic
    @Column(name = "HOSPITALNAMEEN")
    public String getHospitalnameen() {
        return hospitalnameen;
    }

    public void setHospitalnameen(String hospitalnameen) {
        this.hospitalnameen = hospitalnameen;
    }

    @Basic
    @Column(name = "WEBSITEURL")
    public String getWebsiteurl() {
        return websiteurl;
    }

    public void setWebsiteurl(String websiteurl) {
        this.websiteurl = websiteurl;
    }

    @Basic
    @Column(name = "PHONENUMBER")
    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    @Basic
    @Column(name = "ADDRESS")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "POSTCODE")
    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    @Basic
    @Column(name = "PROVINCE")
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Basic
    @Column(name = "CITY")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Basic
    @Column(name = "COUNTYAREA")
    public String getCountyarea() {
        return countyarea;
    }

    public void setCountyarea(String countyarea) {
        this.countyarea = countyarea;
    }

    @Basic
    @Column(name = "TOWNSHIP")
    public String getTownship() {
        return township;
    }

    public void setTownship(String township) {
        this.township = township;
    }

    @Basic
    @Column(name = "STREET")
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Basic
    @Column(name = "CATEGROYAREA")
    public String getCategroyarea() {
        return categroyarea;
    }

    public void setCategroyarea(String categroyarea) {
        this.categroyarea = categroyarea;
    }

    @Basic
    @Column(name = "SALESMAN")
    public String getSalesman() {
        return salesman;
    }

    public void setSalesman(String salesman) {
        this.salesman = salesman;
    }

    @Basic
    @Column(name = "USEFLAG")
    public String getUseflag() {
        return useflag;
    }

    public void setUseflag(String useflag) {
        this.useflag = useflag;
    }

    @Basic
    @Column(name = "REGISTTIME")
    public Time getRegisttime() {
        return registtime;
    }

    public void setRegisttime(Time registtime) {
        this.registtime = registtime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PimsSysHospitalInfos that = (PimsSysHospitalInfos) o;

        if (customercode != null ? !customercode.equals(that.customercode) : that.customercode != null) return false;
        if (hospitalnamech != null ? !hospitalnamech.equals(that.hospitalnamech) : that.hospitalnamech != null)
            return false;
        if (hospitalnameen != null ? !hospitalnameen.equals(that.hospitalnameen) : that.hospitalnameen != null)
            return false;
        if (websiteurl != null ? !websiteurl.equals(that.websiteurl) : that.websiteurl != null) return false;
        if (phonenumber != null ? !phonenumber.equals(that.phonenumber) : that.phonenumber != null) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (postcode != null ? !postcode.equals(that.postcode) : that.postcode != null) return false;
        if (province != null ? !province.equals(that.province) : that.province != null) return false;
        if (city != null ? !city.equals(that.city) : that.city != null) return false;
        if (countyarea != null ? !countyarea.equals(that.countyarea) : that.countyarea != null) return false;
        if (township != null ? !township.equals(that.township) : that.township != null) return false;
        if (street != null ? !street.equals(that.street) : that.street != null) return false;
        if (categroyarea != null ? !categroyarea.equals(that.categroyarea) : that.categroyarea != null) return false;
        if (salesman != null ? !salesman.equals(that.salesman) : that.salesman != null) return false;
        if (useflag != null ? !useflag.equals(that.useflag) : that.useflag != null) return false;
        if (registtime != null ? !registtime.equals(that.registtime) : that.registtime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = customercode != null ? customercode.hashCode() : 0;
        result = 31 * result + (hospitalnamech != null ? hospitalnamech.hashCode() : 0);
        result = 31 * result + (hospitalnameen != null ? hospitalnameen.hashCode() : 0);
        result = 31 * result + (websiteurl != null ? websiteurl.hashCode() : 0);
        result = 31 * result + (phonenumber != null ? phonenumber.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (postcode != null ? postcode.hashCode() : 0);
        result = 31 * result + (province != null ? province.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (countyarea != null ? countyarea.hashCode() : 0);
        result = 31 * result + (township != null ? township.hashCode() : 0);
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + (categroyarea != null ? categroyarea.hashCode() : 0);
        result = 31 * result + (salesman != null ? salesman.hashCode() : 0);
        result = 31 * result + (useflag != null ? useflag.hashCode() : 0);
        result = 31 * result + (registtime != null ? registtime.hashCode() : 0);
        return result;
    }
}
