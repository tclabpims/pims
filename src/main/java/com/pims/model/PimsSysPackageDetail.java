package com.pims.model;

import com.smart.model.BaseObject;

import javax.persistence.*;

/**
 * Created by 909436637@qq.com on 2016/11/2.
 * Description:
 */
@Entity
@Table(name = "PIMS_SYS_PACKAGE_DETAIL")
@IdClass(PimsSysPackageDetail.class)
public class PimsSysPackageDetail extends BaseObject {

    private long packageId;

    private long testItemId;

    public PimsSysPackageDetail() {
    }

    public PimsSysPackageDetail(long packageId, long testItemId) {
        this.packageId = packageId;
        this.testItemId = testItemId;
    }

    @Id
    @Column(name = "PACKAGEID")
    public long getPackageId() {
        return packageId;
    }

    public void setPackageId(long packageId) {
        this.packageId = packageId;
    }

    @Id
    @Column(name = "TESTITEMID")
    public long getTestItemId() {
        return testItemId;
    }

    public void setTestItemId(long testItemId) {
        this.testItemId = testItemId;
    }

    /**
     * Returns a multi-line String with key=value pairs.
     *
     * @return a String representation of this class.
     */
    @Override
    public String toString() {
        return "{packageId:"+packageId+",testItemId:"+testItemId+"}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PimsSysPackageDetail that = (PimsSysPackageDetail) o;

        if (packageId != that.packageId) return false;
        return testItemId == that.testItemId;

    }

    @Override
    public int hashCode() {
        int result = (int) (packageId ^ (packageId >>> 32));
        result = 31 * result + (int) (testItemId ^ (testItemId >>> 32));
        return result;
    }
}
