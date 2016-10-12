package com.pims.model;

/**
 * Created by 909436637@qq.com on 2016/10/12.
 * Description: 来自HIS的收费项目字典
 */
public class HisChargePrice {

    //收费项目代码
    private long sfxmid;

    //收费项目名称
    private String sfxmmc;

    //拼音码
    private String hzsrm1;

    //五笔码
    private String hzsrm2;

    //收费项目价格
    private Double sfxmdj;

    //备注
    private String bzsysm;

    //组织机构代码
    private String zzjgdm;

    //收入项目编号
    private Long srxmid;

    //收费别名编号
    private Long sfbmid;

    //执行价格编码
    private Long zxjgid;

    public HisChargePrice(){}

    public HisChargePrice(long sfxmid, String sfxmmc, Double sfxmdj) {
        this.sfxmid = sfxmid;
        this.sfxmmc = sfxmmc;
        this.sfxmdj = sfxmdj;
    }

    public long getSfxmid() {
        return sfxmid;
    }

    public void setSfxmid(long sfxmid) {
        this.sfxmid = sfxmid;
    }

    public String getSfxmmc() {
        return sfxmmc;
    }

    public void setSfxmmc(String sfxmmc) {
        this.sfxmmc = sfxmmc;
    }

    public String getHzsrm1() {
        return hzsrm1;
    }

    public void setHzsrm1(String hzsrm1) {
        this.hzsrm1 = hzsrm1;
    }

    public String getHzsrm2() {
        return hzsrm2;
    }

    public void setHzsrm2(String hzsrm2) {
        this.hzsrm2 = hzsrm2;
    }

    public Double getSfxmdj() {
        return sfxmdj;
    }

    public void setSfxmdj(Double sfxmdj) {
        this.sfxmdj = sfxmdj;
    }

    public String getBzsysm() {
        return bzsysm;
    }

    public void setBzsysm(String bzsysm) {
        this.bzsysm = bzsysm;
    }

    public String getZzjgdm() {
        return zzjgdm;
    }

    public void setZzjgdm(String zzjgdm) {
        this.zzjgdm = zzjgdm;
    }

    public Long getSrxmid() {
        return srxmid;
    }

    public void setSrxmid(Long srxmid) {
        this.srxmid = srxmid;
    }

    public Long getSfbmid() {
        return sfbmid;
    }

    public void setSfbmid(Long sfbmid) {
        this.sfbmid = sfbmid;
    }

    public Long getZxjgid() {
        return zxjgid;
    }

    public void setZxjgid(Long zxjgid) {
        this.zxjgid = zxjgid;
    }
}
