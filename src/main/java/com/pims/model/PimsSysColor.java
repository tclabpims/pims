package com.pims.model;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

/**
 * Created by king on 2016/9/28.
 */
@Entity
@Table(name = "PIMS_SYS_COLOR")
public class PimsSysColor {
    private long colorid;
    private long colcustomercode;
    private String colcustomername;
    private String coltype;
    private String colowner;
    private String colownername;
    private String colobject;
    private String colmodule;
    private String colobjectstate;
    private String colvalue;
    private String colfirstv;
    private String colsecondv;
    private Long colfirstn;
    private String colcreateuser;
    private Date colcreatetime;
    private String colchinese;

    @Transient
    public String getColcustomername() {
        return colcustomername;
    }

    public void setColcustomername(String colcustomername) {
        this.colcustomername = colcustomername;
    }

    @Transient
    public String getColownername() {
        return colownername;
    }

    public void setColownername(String colownername) {
        this.colownername = colownername;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_REQMATERIAL")
    @SequenceGenerator(name = "SEQ_REQMATERIAL", sequenceName = "seq_syscolor", allocationSize = 1)
    public long getColorid() {
        return colorid;
    }

    public void setColorid(long colorid) {
        this.colorid = colorid;
    }

    @Basic
    @Column(name = "COLCUSTOMERID")
    public long getColcustomercode() {
        return colcustomercode;
    }

    public void setColcustomercode(long colcustomercode) {
        this.colcustomercode = colcustomercode;
    }

    @Basic
    @Column(name = "COLTYPE")
    public String getColtype() {
        return coltype;
    }

    public void setColtype(String coltype) {
        this.coltype = coltype;
    }

    @Basic
    @Column(name = "COLOWNER")
    public String getColowner() {
        return colowner;
    }

    public void setColowner(String colowner) {
        this.colowner = colowner;
    }

    @Basic
    @Column(name = "COLOBJECT")
    public String getColobject() {
        return colobject;
    }

    public void setColobject(String colobject) {
        this.colobject = colobject;
    }

    @Basic
    @Column(name = "COLMODULE")
    public String getColmodule() {
        return colmodule;
    }

    public void setColmodule(String colmodule) {
        this.colmodule = colmodule;
    }

    @Basic
    @Column(name = "COLOBJECTSTATE")
    public String getColobjectstate() {
        return colobjectstate;
    }

    public void setColobjectstate(String colobjectstate) {
        this.colobjectstate = colobjectstate;
    }

    @Basic
    @Column(name = "COLVALUE")
    public String getColvalue() {
        return colvalue;
    }

    public void setColvalue(String colvalue) {
        this.colvalue = colvalue;
    }

    @Basic
    @Column(name = "COLFIRSTV")
    public String getColfirstv() {
        return colfirstv;
    }

    public void setColfirstv(String colfirstv) {
        this.colfirstv = colfirstv;
    }

    @Basic
    @Column(name = "COLSECONDV")
    public String getColsecondv() {
        return colsecondv;
    }

    public void setColsecondv(String colsecondv) {
        this.colsecondv = colsecondv;
    }

    @Basic
    @Column(name = "COLFIRSTN")
    public Long getColfirstn() {
        return colfirstn;
    }

    public void setColfirstn(Long colfirstn) {
        this.colfirstn = colfirstn;
    }

    @Basic
    @Column(name = "COLCREATEUSER")
    public String getColcreateuser() {
        return colcreateuser;
    }

    public void setColcreateuser(String colcreateuser) {
        this.colcreateuser = colcreateuser;
    }

    @Basic
    @Column(name = "COLCREATETIME")
    public Date getColcreatetime() {
        return colcreatetime;
    }

    public void setColcreatetime(Date colcreatetime) {
        this.colcreatetime = colcreatetime;
    }

    @Basic
    @Column(name = "COLCHINESE")
    public String getColchinese() {
        return colchinese;
    }

    public void setColchinese(String colchinese) {
        this.colchinese = colchinese;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PimsSysColor that = (PimsSysColor) o;

        if (colorid != that.colorid) return false;
        if (colcustomercode != that.colcustomercode) return false;
        if (colcustomername != null ? !colcustomername.equals(that.colcustomername) : that.colcustomername != null)
            return false;
        if (coltype != null ? !coltype.equals(that.coltype) : that.coltype != null) return false;
        if (colowner != null ? !colowner.equals(that.colowner) : that.colowner != null) return false;
        if (colownername != null ? !colownername.equals(that.colownername) : that.colownername != null) return false;
        if (colobject != null ? !colobject.equals(that.colobject) : that.colobject != null) return false;
        if (colmodule != null ? !colmodule.equals(that.colmodule) : that.colmodule != null) return false;
        if (colobjectstate != null ? !colobjectstate.equals(that.colobjectstate) : that.colobjectstate != null)
            return false;
        if (colvalue != null ? !colvalue.equals(that.colvalue) : that.colvalue != null) return false;
        if (colfirstv != null ? !colfirstv.equals(that.colfirstv) : that.colfirstv != null) return false;
        if (colsecondv != null ? !colsecondv.equals(that.colsecondv) : that.colsecondv != null) return false;
        if (colfirstn != null ? !colfirstn.equals(that.colfirstn) : that.colfirstn != null) return false;
        if (colcreateuser != null ? !colcreateuser.equals(that.colcreateuser) : that.colcreateuser != null)
            return false;
        if (colcreatetime != null ? !colcreatetime.equals(that.colcreatetime) : that.colcreatetime != null)
            return false;
        return colchinese != null ? colchinese.equals(that.colchinese) : that.colchinese == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (colorid ^ (colorid >>> 32));
        result = 31 * result + (int) (colcustomercode ^ (colcustomercode >>> 32));
        result = 31 * result + (colcustomername != null ? colcustomername.hashCode() : 0);
        result = 31 * result + (coltype != null ? coltype.hashCode() : 0);
        result = 31 * result + (colowner != null ? colowner.hashCode() : 0);
        result = 31 * result + (colownername != null ? colownername.hashCode() : 0);
        result = 31 * result + (colobject != null ? colobject.hashCode() : 0);
        result = 31 * result + (colmodule != null ? colmodule.hashCode() : 0);
        result = 31 * result + (colobjectstate != null ? colobjectstate.hashCode() : 0);
        result = 31 * result + (colvalue != null ? colvalue.hashCode() : 0);
        result = 31 * result + (colfirstv != null ? colfirstv.hashCode() : 0);
        result = 31 * result + (colsecondv != null ? colsecondv.hashCode() : 0);
        result = 31 * result + (colfirstn != null ? colfirstn.hashCode() : 0);
        result = 31 * result + (colcreateuser != null ? colcreateuser.hashCode() : 0);
        result = 31 * result + (colcreatetime != null ? colcreatetime.hashCode() : 0);
        result = 31 * result + (colchinese != null ? colchinese.hashCode() : 0);
        return result;
    }

    @Transient
    String num1 = null;
    String num2 = null;
    public void setA() {
    num1 =getColmodule();
    num2 =getColobjectstate();
        switch(Integer.parseInt(num1)){
            case 0:
                switch (Integer.parseInt(num2)){
                    case 0:setColchinese("已登记");
                        break;
                    case 1:setColchinese("已取材");
                        break;
                    case 2:setColchinese("已包埋");
                        break;
                    case 3:setColchinese("已切片");
                        break;
                    case 4:setColchinese("已初诊");
                        break;
                    case 5:setColchinese("已审核");
                        break;
                    case 6:setColchinese("已发送");
                        break;
                    case 7:setColchinese("会诊中");
                        break;
                    case 8:setColchinese("已打印");
                        break;

                }
                break;
            case 1:
                switch (Integer.parseInt(num2)){
                    case 0:setColchinese("未取材");
                        break;
                    case 1:setColchinese("已取材");
                        break;
                }
                break;
            case 2:
                switch (Integer.parseInt(num2)){
                    case 0:setColchinese("待包埋");
                        break;
                    case 1:setColchinese("已包埋");
                        break;
                }
                break;
            case 3:
                switch (Integer.parseInt(num2)){
                    case 0:setColchinese("待切片");
                        break;
                    case 1:setColchinese("已切片");
                        break;
                }
                break;
            case 4:
                switch (Integer.parseInt(num2)){
                    case 0:setColchinese("未制作");
                        break;
                    case 1:setColchinese("已制作");
                        break;
                }
                break;
            case 5:
                switch (Integer.parseInt(num2)){
                    case 1:setColchinese("未报告");
                        break;
                    case 2:setColchinese("已初诊");
                        break;
                    case 3:setColchinese("已审核");
                        break;
                    case 4:setColchinese("已签发");
                        break;
                    case 5:setColchinese("已打印");
                        break;
                }
                break;
        }
    }
}
