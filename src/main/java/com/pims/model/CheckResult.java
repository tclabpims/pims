package com.pims.model;

import java.util.Date;

/**
 * Created by king on 2016/12/21.
 */
public class CheckResult {
    private String jcybid;//检测样本号码
    private String zzjgdm;//组织机构代码
    private Long sqmxid;//申请明细编号
    private Long sqjlid;//申请记录序号
    private Integer sjbrlx;//受检病人类型
    private Long brdaid;//病人档案序号
    private String brjzhm;//病人就诊号码
    private String sjbrxm;//受检病人姓名
    private String sjbrch;//受检病人床号
    private String sjbrxb;//受检病人性别
    private Integer sjbrnl;//受检病人年龄
    private String brnldw;//病人年龄单位
    private String yxjclx;//影像检查类型
    private String jcmddm;//检查目的代码
    private String jcmdmc;//检查目的名称
    private String jcxmid;//检查项目序号
    private String jcxmhm;//检查项目号码
    private String jcxmmc;//检查项目名称
    private String jcbwdm;//检查部位代码
    private String jcbwnr;//检查部位内容
    private String yxsjnr;//影像所见内容
    private String yxzdnr;//影像诊断内容
    private String lczddm;//临床诊断代码
    private String lczdmc;//临床诊断名称
    private String bszynr;//病史摘要内容
    private String jcjgzt;//检查结果状态
    private String kdysid;//开单医生序号
    private String kdysxm;//开单医生姓名
    private String kdksid;//开单科室序号
    private String kdksmc;//开单科室名称
    private Date jcdjsj;//检查登记时间
    private String djryid;//登记人员序号
    private String djryxm;//登记人员姓名
    private String djksid;//登记科室序号
    private Date spczsj;//摄片操作时间
    private String spryid;//摄片人员序号
    private String spryxm;//摄片人员姓名
    private String djksmc;//登记科室名称
    private Date shczsj;//审核操作时间
    private String shryid;//审核人员序号
    private String shryxm;//审核人员姓名
    private String jgbggh;//结果报告人员
    private String jgbgxm;//结果报告姓名
    private Date jgwcsj;//结果完成时间
    private Integer sfdypb;//是否打印判别
    private Integer sfyxpb;//是否阳性判别
    private Integer sfjzpb;//是否急诊判别
    private String jgljdz;
    private String ftpljz;
    private String brdabh;
    private Integer jgtxpb;

    public String getJcybid() {
        return jcybid;
    }

    public void setJcybid(String jcybid) {
        this.jcybid = jcybid;
    }

    public String getZzjgdm() {
        return zzjgdm;
    }

    public void setZzjgdm(String zzjgdm) {
        this.zzjgdm = zzjgdm;
    }

    public Long getSqmxid() {
        return sqmxid;
    }

    public void setSqmxid(Long sqmxid) {
        this.sqmxid = sqmxid;
    }

    public Long getSqjlid() {
        return sqjlid;
    }

    public void setSqjlid(Long sqjlid) {
        this.sqjlid = sqjlid;
    }

    public Integer getSjbrlx() {
        return sjbrlx;
    }

    public void setSjbrlx(Integer sjbrlx) {
        this.sjbrlx = sjbrlx;
    }

    public Long getBrdaid() {
        return brdaid;
    }

    public void setBrdaid(Long brdaid) {
        this.brdaid = brdaid;
    }

    public String getBrjzhm() {
        return brjzhm;
    }

    public void setBrjzhm(String brjzhm) {
        this.brjzhm = brjzhm;
    }

    public String getSjbrxm() {
        return sjbrxm;
    }

    public void setSjbrxm(String sjbrxm) {
        this.sjbrxm = sjbrxm;
    }

    public String getSjbrch() {
        return sjbrch;
    }

    public void setSjbrch(String sjbrch) {
        this.sjbrch = sjbrch;
    }

    public String getSjbrxb() {
        return sjbrxb;
    }

    public void setSjbrxb(String sjbrxb) {
        this.sjbrxb = sjbrxb;
    }

    public Integer getSjbrnl() {
        return sjbrnl;
    }

    public void setSjbrnl(Integer sjbrnl) {
        this.sjbrnl = sjbrnl;
    }

    public String getBrnldw() {
        return brnldw;
    }

    public void setBrnldw(String brnldw) {
        this.brnldw = brnldw;
    }

    public String getYxjclx() {
        return yxjclx;
    }

    public void setYxjclx(String yxjclx) {
        this.yxjclx = yxjclx;
    }

    public String getJcmddm() {
        return jcmddm;
    }

    public void setJcmddm(String jcmddm) {
        this.jcmddm = jcmddm;
    }

    public String getJcmdmc() {
        return jcmdmc;
    }

    public void setJcmdmc(String jcmdmc) {
        this.jcmdmc = jcmdmc;
    }

    public String getJcxmid() {
        return jcxmid;
    }

    public void setJcxmid(String jcxmid) {
        this.jcxmid = jcxmid;
    }

    public String getJcxmhm() {
        return jcxmhm;
    }

    public void setJcxmhm(String jcxmhm) {
        this.jcxmhm = jcxmhm;
    }

    public String getJcxmmc() {
        return jcxmmc;
    }

    public void setJcxmmc(String jcxmmc) {
        this.jcxmmc = jcxmmc;
    }

    public String getJcbwdm() {
        return jcbwdm;
    }

    public void setJcbwdm(String jcbwdm) {
        this.jcbwdm = jcbwdm;
    }

    public String getJcbwnr() {
        return jcbwnr;
    }

    public void setJcbwnr(String jcbwnr) {
        this.jcbwnr = jcbwnr;
    }

    public String getYxsjnr() {
        return yxsjnr;
    }

    public void setYxsjnr(String yxsjnr) {
        this.yxsjnr = yxsjnr;
    }

    public String getYxzdnr() {
        return yxzdnr;
    }

    public void setYxzdnr(String yxzdnr) {
        this.yxzdnr = yxzdnr;
    }

    public String getLczddm() {
        return lczddm;
    }

    public void setLczddm(String lczddm) {
        this.lczddm = lczddm;
    }

    public String getLczdmc() {
        return lczdmc;
    }

    public void setLczdmc(String lczdmc) {
        this.lczdmc = lczdmc;
    }

    public String getBszynr() {
        return bszynr;
    }

    public void setBszynr(String bszynr) {
        this.bszynr = bszynr;
    }

    public String getJcjgzt() {
        return jcjgzt;
    }

    public void setJcjgzt(String jcjgzt) {
        this.jcjgzt = jcjgzt;
    }

    public String getKdysid() {
        return kdysid;
    }

    public void setKdysid(String kdysid) {
        this.kdysid = kdysid;
    }

    public String getKdysxm() {
        return kdysxm;
    }

    public void setKdysxm(String kdysxm) {
        this.kdysxm = kdysxm;
    }

    public String getKdksid() {
        return kdksid;
    }

    public void setKdksid(String kdksid) {
        this.kdksid = kdksid;
    }

    public String getKdksmc() {
        return kdksmc;
    }

    public void setKdksmc(String kdksmc) {
        this.kdksmc = kdksmc;
    }

    public Date getJcdjsj() {
        return jcdjsj;
    }

    public void setJcdjsj(Date jcdjsj) {
        this.jcdjsj = jcdjsj;
    }

    public String getDjryid() {
        return djryid;
    }

    public void setDjryid(String djryid) {
        this.djryid = djryid;
    }

    public String getDjryxm() {
        return djryxm;
    }

    public void setDjryxm(String djryxm) {
        this.djryxm = djryxm;
    }

    public String getDjksid() {
        return djksid;
    }

    public void setDjksid(String djksid) {
        this.djksid = djksid;
    }

    public Date getSpczsj() {
        return spczsj;
    }

    public void setSpczsj(Date spczsj) {
        this.spczsj = spczsj;
    }

    public String getSpryid() {
        return spryid;
    }

    public void setSpryid(String spryid) {
        this.spryid = spryid;
    }

    public String getSpryxm() {
        return spryxm;
    }

    public void setSpryxm(String spryxm) {
        this.spryxm = spryxm;
    }

    public String getDjksmc() {
        return djksmc;
    }

    public void setDjksmc(String djksmc) {
        this.djksmc = djksmc;
    }

    public Date getShczsj() {
        return shczsj;
    }

    public void setShczsj(Date shczsj) {
        this.shczsj = shczsj;
    }

    public String getShryid() {
        return shryid;
    }

    public void setShryid(String shryid) {
        this.shryid = shryid;
    }

    public String getShryxm() {
        return shryxm;
    }

    public void setShryxm(String shryxm) {
        this.shryxm = shryxm;
    }

    public String getJgbggh() {
        return jgbggh;
    }

    public void setJgbggh(String jgbggh) {
        this.jgbggh = jgbggh;
    }

    public String getJgbgxm() {
        return jgbgxm;
    }

    public void setJgbgxm(String jgbgxm) {
        this.jgbgxm = jgbgxm;
    }

    public Date getJgwcsj() {
        return jgwcsj;
    }

    public void setJgwcsj(Date jgwcsj) {
        this.jgwcsj = jgwcsj;
    }

    public Integer getSfdypb() {
        return sfdypb;
    }

    public void setSfdypb(Integer sfdypb) {
        this.sfdypb = sfdypb;
    }

    public Integer getSfyxpb() {
        return sfyxpb;
    }

    public void setSfyxpb(Integer sfyxpb) {
        this.sfyxpb = sfyxpb;
    }

    public Integer getSfjzpb() {
        return sfjzpb;
    }

    public void setSfjzpb(Integer sfjzpb) {
        this.sfjzpb = sfjzpb;
    }

    public String getJgljdz() {
        return jgljdz;
    }

    public void setJgljdz(String jgljdz) {
        this.jgljdz = jgljdz;
    }

    public String getFtpljz() {
        return ftpljz;
    }

    public void setFtpljz(String ftpljz) {
        this.ftpljz = ftpljz;
    }

    public String getBrdabh() {
        return brdabh;
    }

    public void setBrdabh(String brdabh) {
        this.brdabh = brdabh;
    }

    public Integer getJgtxpb() {
        return jgtxpb;
    }

    public void setJgtxpb(Integer jgtxpb) {
        this.jgtxpb = jgtxpb;
    }
}
