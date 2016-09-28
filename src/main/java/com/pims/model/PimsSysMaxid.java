package com.pims.model;

import javax.persistence.*;

/**
 * Created by king on 2016/9/28.
 */
@Entity
@Table(name = "PIMS_SYS_MAXID", schema = "KFTEST", catalog = "")
@IdClass(PimsSysMaxidPK.class)
public class PimsSysMaxid {
    private String objectname;
    private String objectclass;
    private String currentvalue;

    @Id
    @Column(name = "OBJECTNAME")
    public String getObjectname() {
        return objectname;
    }

    public void setObjectname(String objectname) {
        this.objectname = objectname;
    }

    @Id
    @Column(name = "OBJECTCLASS")
    public String getObjectclass() {
        return objectclass;
    }

    public void setObjectclass(String objectclass) {
        this.objectclass = objectclass;
    }

    @Basic
    @Column(name = "CURRENTVALUE")
    public String getCurrentvalue() {
        return currentvalue;
    }

    public void setCurrentvalue(String currentvalue) {
        this.currentvalue = currentvalue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PimsSysMaxid that = (PimsSysMaxid) o;

        if (objectname != null ? !objectname.equals(that.objectname) : that.objectname != null) return false;
        if (objectclass != null ? !objectclass.equals(that.objectclass) : that.objectclass != null) return false;
        if (currentvalue != null ? !currentvalue.equals(that.currentvalue) : that.currentvalue != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = objectname != null ? objectname.hashCode() : 0;
        result = 31 * result + (objectclass != null ? objectclass.hashCode() : 0);
        result = 31 * result + (currentvalue != null ? currentvalue.hashCode() : 0);
        return result;
    }
}
