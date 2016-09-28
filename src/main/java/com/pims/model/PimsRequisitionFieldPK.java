package com.pims.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by king on 2016/9/28.
 */
public class PimsRequisitionFieldPK implements Serializable {
    private long requisitionid;
    private long fieldid;

    @Column(name = "REQUISITIONID")
    @Id
    public long getRequisitionid() {
        return requisitionid;
    }

    public void setRequisitionid(long requisitionid) {
        this.requisitionid = requisitionid;
    }

    @Column(name = "FIELDID")
    @Id
    public long getFieldid() {
        return fieldid;
    }

    public void setFieldid(long fieldid) {
        this.fieldid = fieldid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PimsRequisitionFieldPK that = (PimsRequisitionFieldPK) o;

        if (requisitionid != that.requisitionid) return false;
        if (fieldid != that.fieldid) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (requisitionid ^ (requisitionid >>> 32));
        result = 31 * result + (int) (fieldid ^ (fieldid >>> 32));
        return result;
    }
}
