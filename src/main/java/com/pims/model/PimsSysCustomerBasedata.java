package com.pims.model;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

/**
 * Created by king on 2016/9/28.
 */
@Entity
@Table(name = "PIMS_SYS_CUSTOMER_BASEDATA")
public class PimsSysCustomerBasedata {
    private long basedataid;
    private String bascustomercode;
    private long baspathologyid;
    private long bastype;
    private long basrefdataid;
    private long basuseflag;
    //报告项排序号
    private int basrptItemSort;
    //别名
    private String basrefdataalias;
    private String basfirstv;
    private String bassecondv;
    private String basthirdv;
    private String basfourthv;
    private Long basfirstn;
    private Long bassecondn;
    private Long basthirdn;
    private Long basfourthn;
    private String bascreateuser;
    private Date bascreatetime;

    private String baspathologyname;

    private String bascustomername;

    private String basrefdataname;

    @Basic
    @Column(name="BASRPTITEMSORT")
    public int getBasrptItemSort() {
        return basrptItemSort;
    }

    public void setBasrptItemSort(int basrptItemSort) {
        this.basrptItemSort = basrptItemSort;
    }

    @Basic
    @Column(name="BASREFDATAALIAS")
    public String getBasrefdataalias() {
        return basrefdataalias;
    }

    public void setBasrefdataalias(String basrefdataalias) {
        this.basrefdataalias = basrefdataalias;
    }

    @Transient
    public String getBasrefdataname() {
        return basrefdataname;
    }

    public void setBasrefdataname(String basrefdataname) {
        this.basrefdataname = basrefdataname;
    }

    @Transient
    public String getBaspathologyname() {
        return baspathologyname;
    }

    public void setBaspathologyname(String baspathologyname) {
        this.baspathologyname = baspathologyname;
    }

    @Transient
    public String getBascustomername() {
        return bascustomername;
    }

    public void setBascustomername(String bascustomername) {
        this.bascustomername = bascustomername;
    }

    @Basic
    @Column(name = "BASUSEFLAG")
    public long getBasuseflag() {
        return basuseflag;
    }

    public void setBasuseflag(long basuseflag) {
        this.basuseflag = basuseflag;
    }

    @Id
    @Column(name = "BASEDATAID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="SEQ_CUSTOMER_BASEDATA")
    @SequenceGenerator(name = "SEQ_CUSTOMER_BASEDATA", sequenceName = "SEQ_CUSTOMER_BASEDATA", allocationSize=1)
    public long getBasedataid() {
        return basedataid;
    }

    public void setBasedataid(long basedataid) {
        this.basedataid = basedataid;
    }

    @Basic
    @Column(name = "BASCUSTOMERCODE")
    public String getBascustomercode() {
        return bascustomercode;
    }

    public void setBascustomercode(String bascustomercode) {
        this.bascustomercode = bascustomercode;
    }

    @Basic
    @Column(name = "BASPATHOLOGYID")
    public long getBaspathologyid() {
        return baspathologyid;
    }

    public void setBaspathologyid(long baspathologyid) {
        this.baspathologyid = baspathologyid;
    }

    @Basic
    @Column(name = "BASTYPE")
    public long getBastype() {
        return bastype;
    }

    public void setBastype(long bastype) {
        this.bastype = bastype;
    }

    @Basic
    @Column(name = "BASREFDATAID")
    public long getBasrefdataid() {
        return basrefdataid;
    }

    public void setBasrefdataid(long basrefdataid) {
        this.basrefdataid = basrefdataid;
    }

    @Basic
    @Column(name = "BASFIRSTV")
    public String getBasfirstv() {
        return basfirstv;
    }

    public void setBasfirstv(String basfirstv) {
        this.basfirstv = basfirstv;
    }

    @Basic
    @Column(name = "BASSECONDV")
    public String getBassecondv() {
        return bassecondv;
    }

    public void setBassecondv(String bassecondv) {
        this.bassecondv = bassecondv;
    }

    @Basic
    @Column(name = "BASTHIRDV")
    public String getBasthirdv() {
        return basthirdv;
    }

    public void setBasthirdv(String basthirdv) {
        this.basthirdv = basthirdv;
    }

    @Basic
    @Column(name = "BASFOURTHV")
    public String getBasfourthv() {
        return basfourthv;
    }

    public void setBasfourthv(String basfourthv) {
        this.basfourthv = basfourthv;
    }

    @Basic
    @Column(name = "BASFIRSTN")
    public Long getBasfirstn() {
        return basfirstn;
    }

    public void setBasfirstn(Long basfirstn) {
        this.basfirstn = basfirstn;
    }

    @Basic
    @Column(name = "BASSECONDN")
    public Long getBassecondn() {
        return bassecondn;
    }

    public void setBassecondn(Long bassecondn) {
        this.bassecondn = bassecondn;
    }

    @Basic
    @Column(name = "BASTHIRDN")
    public Long getBasthirdn() {
        return basthirdn;
    }

    public void setBasthirdn(Long basthirdn) {
        this.basthirdn = basthirdn;
    }

    @Basic
    @Column(name = "BASFOURTHN")
    public Long getBasfourthn() {
        return basfourthn;
    }

    public void setBasfourthn(Long basfourthn) {
        this.basfourthn = basfourthn;
    }

    @Basic
    @Column(name = "BASCREATEUSER")
    public String getBascreateuser() {
        return bascreateuser;
    }

    public void setBascreateuser(String bascreateuser) {
        this.bascreateuser = bascreateuser;
    }

    @Basic
    @Column(name = "BASCREATETIME")
    public Date getBascreatetime() {
        return bascreatetime;
    }

    public void setBascreatetime(Date bascreatetime) {
        this.bascreatetime = bascreatetime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PimsSysCustomerBasedata that = (PimsSysCustomerBasedata) o;

        if (basedataid != that.basedataid) return false;
        if (baspathologyid != that.baspathologyid) return false;
        if (bastype != that.bastype) return false;
        if (basrefdataid != that.basrefdataid) return false;
        if (basfirstv != null ? !basfirstv.equals(that.basfirstv) : that.basfirstv != null) return false;
        if (bassecondv != null ? !bassecondv.equals(that.bassecondv) : that.bassecondv != null) return false;
        if (basthirdv != null ? !basthirdv.equals(that.basthirdv) : that.basthirdv != null) return false;
        if (basfourthv != null ? !basfourthv.equals(that.basfourthv) : that.basfourthv != null) return false;
        if (basfirstn != null ? !basfirstn.equals(that.basfirstn) : that.basfirstn != null) return false;
        if (bassecondn != null ? !bassecondn.equals(that.bassecondn) : that.bassecondn != null) return false;
        if (basthirdn != null ? !basthirdn.equals(that.basthirdn) : that.basthirdn != null) return false;
        if (basfourthn != null ? !basfourthn.equals(that.basfourthn) : that.basfourthn != null) return false;
        if (bascreateuser != null ? !bascreateuser.equals(that.bascreateuser) : that.bascreateuser != null)
            return false;
        if (bascreatetime != null ? !bascreatetime.equals(that.bascreatetime) : that.bascreatetime != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (basedataid ^ (basedataid >>> 32));
        result = 31 * result + (bascustomercode != null ? bascustomercode.hashCode() : 0);
        result = 31 * result + (int) (baspathologyid ^ (baspathologyid >>> 32));
        result = 31 * result + (int) (bastype ^ (bastype >>> 32));
        result = 31 * result + (int) (basrefdataid ^ (basrefdataid >>> 32));
        result = 31 * result + (basfirstv != null ? basfirstv.hashCode() : 0);
        result = 31 * result + (bassecondv != null ? bassecondv.hashCode() : 0);
        result = 31 * result + (basthirdv != null ? basthirdv.hashCode() : 0);
        result = 31 * result + (basfourthv != null ? basfourthv.hashCode() : 0);
        result = 31 * result + (basfirstn != null ? basfirstn.hashCode() : 0);
        result = 31 * result + (bassecondn != null ? bassecondn.hashCode() : 0);
        result = 31 * result + (basthirdn != null ? basthirdn.hashCode() : 0);
        result = 31 * result + (basfourthn != null ? basfourthn.hashCode() : 0);
        result = 31 * result + (bascreateuser != null ? bascreateuser.hashCode() : 0);
        result = 31 * result + (bascreatetime != null ? bascreatetime.hashCode() : 0);
        return result;
    }
}
