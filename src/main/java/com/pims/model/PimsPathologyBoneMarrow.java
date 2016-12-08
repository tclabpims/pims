package com.pims.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by 909436637@qq.com on 2016/12/8.
 * Description:
 */
@Entity
@Table(name="PIMS_PATHOLOGY_BONEMARROW")
public class PimsPathologyBoneMarrow  {

    private long bmItemId;

    private long pathologyId;

    private String itemNameCh;

    private String itemNameEn;

    private String itemClass;

    private long itemClassId;

    private Float bloodPiecePercent;

    private Float bmAverage;

    private String bmStandard;

    private Float bmPercent;

    private Date itemCreateTime;

    private String itemCreateUser;

    private String itemPrintOrder;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BM_ITEM_ID")
    @SequenceGenerator(name = "SEQ_BM_ITEM_ID", sequenceName = "SEQ_BM_ITEM_ID", allocationSize = 1)
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
    @Column(name="itemNameCh")
    public String getItemNameCh() {
        return itemNameCh;
    }

    public void setItemNameCh(String itemNameCh) {
        this.itemNameCh = itemNameCh;
    }

    @Basic
    @Column(name="itemNameEn")
    public String getItemNameEn() {
        return itemNameEn;
    }

    public void setItemNameEn(String itemNameEn) {
        this.itemNameEn = itemNameEn;
    }

    @Basic
    @Column(name="itemClass")
    public String getItemClass() {
        return itemClass;
    }

    public void setItemClass(String itemClass) {
        this.itemClass = itemClass;
    }

    @Basic
    @Column(name="itemClassId")
    public long getItemClassId() {
        return itemClassId;
    }

    public void setItemClassId(long itemClassId) {
        this.itemClassId = itemClassId;
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
    @Column(name="bmAverage")
    public Float getBmAverage() {
        return bmAverage;
    }

    public void setBmAverage(Float bmAverage) {
        this.bmAverage = bmAverage;
    }

    @Basic
    @Column(name="bmStandard")
    public String getBmStandard() {
        return bmStandard;
    }

    public void setBmStandard(String bmStandard) {
        this.bmStandard = bmStandard;
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
    @Column(name="itemCreateTime")
    public Date getItemCreateTime() {
        return itemCreateTime;
    }

    public void setItemCreateTime(Date itemCreateTime) {
        this.itemCreateTime = itemCreateTime;
    }

    @Basic
    @Column(name="itemCreateUser")
    public String getItemCreateUser() {
        return itemCreateUser;
    }

    public void setItemCreateUser(String itemCreateUser) {
        this.itemCreateUser = itemCreateUser;
    }

    @Basic
    @Column(name="itemPrintOrder")
    public String getItemPrintOrder() {
        return itemPrintOrder;
    }

    public void setItemPrintOrder(String itemPrintOrder) {
        this.itemPrintOrder = itemPrintOrder;
    }
}
