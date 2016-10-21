package com.pims.model;

import com.smart.model.BaseObject;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by 909436637@qq.com on 2016/10/20.
 * Description:
 */
@Entity
@Table(name = "PIMS_HOSPITAL_PATHOLOGY_INFO")
public class PimsHospitalPathologyInfo extends BaseObject {

    private long id;

    private long hospitalId;

    private long pathologyId;

    private String numberPrefix;

    private String regularExpression;

    private int useFlag;

    private long nextNumber;

    private Date createTime;

    private long createUser;

    private String sortNo;

    private long latestUser;

    private String hospitalName;

    private String pathologyName;

    private String theAlias;

    @Transient
    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    @Transient
    public String getPathologyName() {
        return pathologyName;
    }

    public void setPathologyName(String pathologyName) {
        this.pathologyName = pathologyName;
    }

    @Basic
    @Column(name = "THEALIAS")
    public String getTheAlias() {
        return theAlias;
    }

    public void setTheAlias(String theAlias) {
        this.theAlias = theAlias;
    }

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="SEQ_HOS_PATHOLOGY")
    @SequenceGenerator(name = "SEQ_HOS_PATHOLOGY", sequenceName = "SEQ_HOS_PATHOLOGY", allocationSize=1)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "HOSPITALID")
    public long getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(long hospitalId) {
        this.hospitalId = hospitalId;
    }

    @Basic
    @Column(name = "PATHOLOGYID")
    public long getPathologyId() {
        return pathologyId;
    }

    public void setPathologyId(long pathologyId) {
        this.pathologyId = pathologyId;
    }

    @Basic
    @Column(name = "NUMBERPREFIX")
    public String getNumberPrefix() {
        return numberPrefix;
    }

    public void setNumberPrefix(String numberPrefix) {
        this.numberPrefix = numberPrefix;
    }

    @Basic
    @Column(name = "REGULAREXPRESSION")
    public String getRegularExpression() {
        return regularExpression;
    }

    public void setRegularExpression(String regularExpression) {
        this.regularExpression = regularExpression;
    }

    @Basic
    @Column(name = "USEFLAG")
    public int getUseFlag() {
        return useFlag;
    }

    public void setUseFlag(int useFlag) {
        this.useFlag = useFlag;
    }

    @Basic
    @Column(name = "NEXTNUMBER")
    public long getNextNumber() {
        return nextNumber;
    }

    public void setNextNumber(long nextNumber) {
        this.nextNumber = nextNumber;
    }

    @Basic
    @Column(name = "CREATETIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "CREATEUSER")
    public long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(long createUser) {
        this.createUser = createUser;
    }

    @Basic
    @Column(name = "SORTNO")
    public String getSortNo() {
        return sortNo;
    }

    public void setSortNo(String sortNo) {
        this.sortNo = sortNo;
    }

    @Basic
    @Column(name = "LASTESTUSER")
    public long getLatestUser() {
        return latestUser;
    }

    public void setLatestUser(long latestUser) {
        this.latestUser = latestUser;
    }

    /**
     * Returns a multi-line String with key=value pairs.
     *
     * @return a String representation of this class.
     */
    @Override
    public String toString() {
        return null;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PimsHospitalPathologyInfo that = (PimsHospitalPathologyInfo) o;

        if (id != that.id) return false;
        if (hospitalId != that.hospitalId) return false;
        if (pathologyId != that.pathologyId) return false;
        if (useFlag != that.useFlag) return false;
        if (nextNumber != that.nextNumber) return false;
        if (createUser != that.createUser) return false;
        if (latestUser != that.latestUser) return false;
        if (!numberPrefix.equals(that.numberPrefix)) return false;
        if (!regularExpression.equals(that.regularExpression)) return false;
        if (!createTime.equals(that.createTime)) return false;
        if (!sortNo.equals(that.sortNo)) return false;
        if (!hospitalName.equals(that.hospitalName)) return false;
        if (!pathologyName.equals(that.pathologyName)) return false;
        return theAlias.equals(that.theAlias);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (hospitalId ^ (hospitalId >>> 32));
        result = 31 * result + (int) (pathologyId ^ (pathologyId >>> 32));
        result = 31 * result + numberPrefix.hashCode();
        result = 31 * result + regularExpression.hashCode();
        result = 31 * result + useFlag;
        result = 31 * result + (int) (nextNumber ^ (nextNumber >>> 32));
        result = 31 * result + createTime.hashCode();
        result = 31 * result + (int) (createUser ^ (createUser >>> 32));
        result = 31 * result + sortNo.hashCode();
        result = 31 * result + (int) (latestUser ^ (latestUser >>> 32));
        result = 31 * result + hospitalName.hashCode();
        result = 31 * result + pathologyName.hashCode();
        result = 31 * result + theAlias.hashCode();
        return result;
    }
}
