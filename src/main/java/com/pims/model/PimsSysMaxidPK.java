package com.pims.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by king on 2016/9/28.
 */
public class PimsSysMaxidPK implements Serializable {
    private String objectname;
    private String objectclass;

    @Column(name = "OBJECTNAME")
    @Id
    public String getObjectname() {
        return objectname;
    }

    public void setObjectname(String objectname) {
        this.objectname = objectname;
    }

    @Column(name = "OBJECTCLASS")
    @Id
    public String getObjectclass() {
        return objectclass;
    }

    public void setObjectclass(String objectclass) {
        this.objectclass = objectclass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PimsSysMaxidPK that = (PimsSysMaxidPK) o;

        if (objectname != null ? !objectname.equals(that.objectname) : that.objectname != null) return false;
        if (objectclass != null ? !objectclass.equals(that.objectclass) : that.objectclass != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = objectname != null ? objectname.hashCode() : 0;
        result = 31 * result + (objectclass != null ? objectclass.hashCode() : 0);
        return result;
    }
}
