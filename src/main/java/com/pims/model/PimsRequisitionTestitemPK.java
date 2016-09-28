package com.pims.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by king on 2016/9/28.
 */
public class PimsRequisitionTestitemPK implements Serializable {
    private long requisitionid;
    private long testitemid;

    @Column(name = "REQUISITIONID")
    @Id
    public long getRequisitionid() {
        return requisitionid;
    }

    public void setRequisitionid(long requisitionid) {
        this.requisitionid = requisitionid;
    }

    @Column(name = "TESTITEMID")
    @Id
    public long getTestitemid() {
        return testitemid;
    }

    public void setTestitemid(long testitemid) {
        this.testitemid = testitemid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PimsRequisitionTestitemPK that = (PimsRequisitionTestitemPK) o;

        if (requisitionid != that.requisitionid) return false;
        if (testitemid != that.testitemid) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (requisitionid ^ (requisitionid >>> 32));
        result = 31 * result + (int) (testitemid ^ (testitemid >>> 32));
        return result;
    }
}
