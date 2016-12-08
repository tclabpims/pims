package com.pims.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by 909436637@qq.com on 2016/12/8.
 * Description:
 */
@Entity
@Table(name="PIMS_PATHOLOGY_BM_RESULT")
public class PimsPathologyBmResult {

    private long bmResultID;
    private long bmItemId;
    private long pathologyId;
    private long customerId;
    private long sampleId;
    private Float bloodPiecePercent;
    private Float bmPercent;
    private Date resultCreateTime;
    private String resultCreateUser;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Seq_BM_ResultId")
    @SequenceGenerator(name = "Seq_BM_ResultId", sequenceName = "Seq_BM_ResultId", allocationSize = 1)
    public long getBmResultID() {
        return bmResultID;
    }

    public void setBmResultID(long bmResultID) {
        this.bmResultID = bmResultID;
    }

    @Basic
    @Column(name="bmItemId")
    public long getBmItemId() {
        return bmItemId;
    }

    public void setBmItemId(long bmItemId) {
        this.bmItemId = bmItemId;
    }

    @Basic
    @Column(name="pathologyId")
    public long getPathologyId() {
        return pathologyId;
    }

    public void setPathologyId(long pathologyId) {
        this.pathologyId = pathologyId;
    }

    @Basic
    @Column(name="customerId")
    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    @Basic
    @Column(name="sampleId")
    public long getSampleId() {
        return sampleId;
    }

    public void setSampleId(long sampleId) {
        this.sampleId = sampleId;
    }

    @Basic
    @Column(name="bloodPiecePercent")
    public Float getBloodPiecePercent() {
        return bloodPiecePercent;
    }

    public void setBloodPiecePercent(Float bloodPiecePercent) {
        this.bloodPiecePercent = bloodPiecePercent;
    }

    @Basic
    @Column(name="bmPercent")
    public Float getBmPercent() {
        return bmPercent;
    }

    public void setBmPercent(Float bmPercent) {
        this.bmPercent = bmPercent;
    }

    @Basic
    @Column(name="resultCreateTime")
    public Date getResultCreateTime() {
        return resultCreateTime;
    }

    public void setResultCreateTime(Date resultCreateTime) {
        this.resultCreateTime = resultCreateTime;
    }

    @Basic
    @Column(name="resultCreateUser")
    public String getResultCreateUser() {
        return resultCreateUser;
    }

    public void setResultCreateUser(String resultCreateUser) {
        this.resultCreateUser = resultCreateUser;
    }
}
