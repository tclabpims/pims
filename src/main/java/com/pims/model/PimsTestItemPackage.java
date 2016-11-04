package com.pims.model;

import com.smart.model.BaseObject;

import javax.persistence.*;

/**
 * Created by 909436637@qq.com on 2016/11/2.
 * Description:
 */
@Entity
@Table(name="PIMS_TEST_ITEM_PACKAGE")
public class PimsTestItemPackage extends BaseObject {

    private long packageId;

    private String packageName;

    private String packageDiscount;

    private int packageUseTimes;

    private int packageItems;

    private long pathologyId;

    @Column(name="PATHOLOGYID")
    public long getPathologyId() {
        return pathologyId;
    }

    public void setPathologyId(long pathologyId) {
        this.pathologyId = pathologyId;
    }

    @Transient
    public int getPackageItems() {
        return packageItems;
    }

    public void setPackageItems(int packageItems) {
        this.packageItems = packageItems;
    }

    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PACKAGE")
    @SequenceGenerator(name="SEQ_PACKAGE", sequenceName = "SEQ_PACKAGE", allocationSize = 1)
    public long getPackageId() {
        return packageId;
    }

    public void setPackageId(long packageId) {
        this.packageId = packageId;
    }

    @Column(name = "PACKAGENAME")
    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    @Column(name = "PACKAGEDISCOUNT")
    public String getPackageDiscount() {
        return packageDiscount;
    }

    public void setPackageDiscount(String packageDiscount) {
        this.packageDiscount = packageDiscount;
    }

    @Column(name = "PACKAGEUSETIMES")
    public int getPackageUseTimes() {
        return packageUseTimes;
    }

    public void setPackageUseTimes(int packageUseTimes) {
        this.packageUseTimes = packageUseTimes;
    }

    /**
     * Returns a multi-line String with key=value pairs.
     *
     * @return a String representation of this class.
     */
    @Override
    public String toString() {
        return packageName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PimsTestItemPackage that = (PimsTestItemPackage) o;

        if (packageId != that.packageId) return false;
        if (packageUseTimes != that.packageUseTimes) return false;
        if (!packageName.equals(that.packageName)) return false;
        return packageDiscount.equals(that.packageDiscount);

    }

    @Override
    public int hashCode() {
        int result = (int) (packageId ^ (packageId >>> 32));
        result = 31 * result + packageName.hashCode();
        result = 31 * result + packageDiscount.hashCode();
        result = 31 * result + packageUseTimes;
        return result;
    }
}
