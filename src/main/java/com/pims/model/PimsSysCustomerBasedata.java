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
    private Long basparentdataid;
    private String baswebeletname;
    private String basshowlevel;
    private long basuseflag;
    private String basinputsort;
    private String basreportsort;
    private String bascheckboxf;
    private String bascheckboxs;
    private String basinputf;
    private String basinputs;
    private String basdefaultvalue;
    private String basrefvalue1;
    private String basavgvalue1;
    private String basrefvalue2;
    private String basavgvalue2;
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
    @Column(name = "BASPARENTDATAID")
    public Long getBasparentdataid() {
        return basparentdataid;
    }

    public void setBasparentdataid(Long basparentdataid) {
        this.basparentdataid = basparentdataid;
    }

    @Basic
    @Column(name = "BASWEBELETNAME")
    public String getBaswebeletname() {
        return baswebeletname;
    }

    public void setBaswebeletname(String baswebeletname) {
        this.baswebeletname = baswebeletname;
    }

    @Basic
    @Column(name = "BASSHOWLEVEL")
    public String getBasshowlevel() {
        return basshowlevel;
    }

    public void setBasshowlevel(String basshowlevel) {
        this.basshowlevel = basshowlevel;
    }

    @Basic
    @Column(name = "BASUSEFLAG")
    public long getBasuseflag() {
        return basuseflag;
    }

    public void setBasuseflag(long basuseflag) {
        this.basuseflag = basuseflag;
    }

    @Basic
    @Column(name = "BASINPUTSORT")
    public String getBasinputsort() {
        return basinputsort;
    }

    public void setBasinputsort(String basinputsort) {
        this.basinputsort = basinputsort;
    }

    @Basic
    @Column(name = "BASREPORTSORT")
    public String getBasreportsort() {
        return basreportsort;
    }

    public void setBasreportsort(String basreportsort) {
        this.basreportsort = basreportsort;
    }

    @Basic
    @Column(name = "BASCHECKBOXF")
    public String getBascheckboxf() {
        return bascheckboxf;
    }

    public void setBascheckboxf(String bascheckboxf) {
        this.bascheckboxf = bascheckboxf;
    }

    @Basic
    @Column(name = "BASCHECKBOXS")
    public String getBascheckboxs() {
        return bascheckboxs;
    }

    public void setBascheckboxs(String bascheckboxs) {
        this.bascheckboxs = bascheckboxs;
    }

    @Basic
    @Column(name = "BASINPUTF")
    public String getBasinputf() {
        return basinputf;
    }

    public void setBasinputf(String basinputf) {
        this.basinputf = basinputf;
    }

    @Basic
    @Column(name = "BASINPUTS")
    public String getBasinputs() {
        return basinputs;
    }

    public void setBasinputs(String basinputs) {
        this.basinputs = basinputs;
    }

    @Basic
    @Column(name = "BASDEFAULTVALUE")
    public String getBasdefaultvalue() {
        return basdefaultvalue;
    }

    public void setBasdefaultvalue(String basdefaultvalue) {
        this.basdefaultvalue = basdefaultvalue;
    }

    @Basic
    @Column(name = "BASREFVALUE1")
    public String getBasrefvalue1() {
        return basrefvalue1;
    }

    public void setBasrefvalue1(String basrefvalue1) {
        this.basrefvalue1 = basrefvalue1;
    }

    @Basic
    @Column(name = "BASAVGVALUE1")
    public String getBasavgvalue1() {
        return basavgvalue1;
    }

    public void setBasavgvalue1(String basavgvalue1) {
        this.basavgvalue1 = basavgvalue1;
    }

    @Basic
    @Column(name = "BASREFVALUE2")
    public String getBasrefvalue2() {
        return basrefvalue2;
    }

    public void setBasrefvalue2(String basrefvalue2) {
        this.basrefvalue2 = basrefvalue2;
    }

    @Basic
    @Column(name = "BASAVGVALUE2")
    public String getBasavgvalue2() {
        return basavgvalue2;
    }

    public void setBasavgvalue2(String basavgvalue2) {
        this.basavgvalue2 = basavgvalue2;
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
        if (basuseflag != that.basuseflag) return false;
        if (bascustomercode != null ? !bascustomercode.equals(that.bascustomercode) : that.bascustomercode != null)
            return false;
        if (basparentdataid != null ? !basparentdataid.equals(that.basparentdataid) : that.basparentdataid != null)
            return false;
        if (baswebeletname != null ? !baswebeletname.equals(that.baswebeletname) : that.baswebeletname != null)
            return false;
        if (basshowlevel != null ? !basshowlevel.equals(that.basshowlevel) : that.basshowlevel != null) return false;
        if (basinputsort != null ? !basinputsort.equals(that.basinputsort) : that.basinputsort != null) return false;
        if (basreportsort != null ? !basreportsort.equals(that.basreportsort) : that.basreportsort != null)
            return false;
        if (bascheckboxf != null ? !bascheckboxf.equals(that.bascheckboxf) : that.bascheckboxf != null) return false;
        if (bascheckboxs != null ? !bascheckboxs.equals(that.bascheckboxs) : that.bascheckboxs != null) return false;
        if (basinputf != null ? !basinputf.equals(that.basinputf) : that.basinputf != null) return false;
        if (basinputs != null ? !basinputs.equals(that.basinputs) : that.basinputs != null) return false;
        if (basdefaultvalue != null ? !basdefaultvalue.equals(that.basdefaultvalue) : that.basdefaultvalue != null)
            return false;
        if (basrefvalue1 != null ? !basrefvalue1.equals(that.basrefvalue1) : that.basrefvalue1 != null) return false;
        if (basavgvalue1 != null ? !basavgvalue1.equals(that.basavgvalue1) : that.basavgvalue1 != null) return false;
        if (basrefvalue2 != null ? !basrefvalue2.equals(that.basrefvalue2) : that.basrefvalue2 != null) return false;
        if (basavgvalue2 != null ? !basavgvalue2.equals(that.basavgvalue2) : that.basavgvalue2 != null) return false;
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
        result = 31 * result + (basparentdataid != null ? basparentdataid.hashCode() : 0);
        result = 31 * result + (baswebeletname != null ? baswebeletname.hashCode() : 0);
        result = 31 * result + (basshowlevel != null ? basshowlevel.hashCode() : 0);
        result = 31 * result + (int) (basuseflag ^ (basuseflag >>> 32));
        result = 31 * result + (basinputsort != null ? basinputsort.hashCode() : 0);
        result = 31 * result + (basreportsort != null ? basreportsort.hashCode() : 0);
        result = 31 * result + (bascheckboxf != null ? bascheckboxf.hashCode() : 0);
        result = 31 * result + (bascheckboxs != null ? bascheckboxs.hashCode() : 0);
        result = 31 * result + (basinputf != null ? basinputf.hashCode() : 0);
        result = 31 * result + (basinputs != null ? basinputs.hashCode() : 0);
        result = 31 * result + (basdefaultvalue != null ? basdefaultvalue.hashCode() : 0);
        result = 31 * result + (basrefvalue1 != null ? basrefvalue1.hashCode() : 0);
        result = 31 * result + (basavgvalue1 != null ? basavgvalue1.hashCode() : 0);
        result = 31 * result + (basrefvalue2 != null ? basrefvalue2.hashCode() : 0);
        result = 31 * result + (basavgvalue2 != null ? basavgvalue2.hashCode() : 0);
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
