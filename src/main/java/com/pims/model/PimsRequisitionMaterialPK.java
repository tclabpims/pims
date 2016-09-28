package com.pims.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by king on 2016/9/28.
 */
public class PimsRequisitionMaterialPK implements Serializable {
    private long requisitionid;
    private long materialid;

    @Column(name = "REQUISITIONID")
    @Id
    public long getRequisitionid() {
        return requisitionid;
    }

    public void setRequisitionid(long requisitionid) {
        this.requisitionid = requisitionid;
    }

    @Column(name = "MATERIALID")
    @Id
    public long getMaterialid() {
        return materialid;
    }

    public void setMaterialid(long materialid) {
        this.materialid = materialid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PimsRequisitionMaterialPK that = (PimsRequisitionMaterialPK) o;

        if (requisitionid != that.requisitionid) return false;
        if (materialid != that.materialid) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (requisitionid ^ (requisitionid >>> 32));
        result = 31 * result + (int) (materialid ^ (materialid >>> 32));
        return result;
    }
}
