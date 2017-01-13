package com.pims.service.impl;

import com.pims.model.*;
import com.pims.service.QueryHisDataService;
import com.pims.service.pimspathologysample.PimsPathologySampleManager;
import com.pims.service.pimssyspathology.PimsSampleResultManager;
import com.smart.Constants;
import com.smart.util.Config;
import com.smart.util.ConvertUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by 909436637@qq.com on 2016/10/12.
 * Description:
 */
public class QueryHisDataServiceImpl implements QueryHisDataService {

    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Integer queryHisChargePrice(String query) {
        StringBuffer buffer = new StringBuffer("select count(1) cnt from V_HSBHJ_CHARGEPRICE h");
        if (query != null && !"".equals(query.trim())) {
            buffer.append(" where h.sfxmmc like '%").append(query).append("%'");
        }
        return jdbcTemplate.queryForObject(buffer.toString(), Integer.class);
    }

    //查询HIS系统中的收费项目信息
    @Override
    public List queryHisChargePrice(final String query, final int start, final int end) {

        StringBuffer sql = new StringBuffer("select * from (select H.Sfxmid,H.Sfxmmc,H.Sfxmdj, rownum as rowno from V_HSBHJ_CHARGEPRICE h");
        if (query != null && !"".equals(query.trim())) {
            sql.append(" where h.sfxmmc like '%").append(query).append("%'");
        }
        sql.append(" ) where rowno between ? and ?");


        return jdbcTemplate.query(sql.toString(), new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setInt(1, start);
                preparedStatement.setInt(2, end);
            }
        }, new ResultSetExtractor<List>() {
            @Override
            public List extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                List result = new ArrayList();
                while (resultSet.next()) {
                    HisChargePrice hcp = new HisChargePrice(resultSet.getLong("sfxmid"), String.valueOf(resultSet.getObject("sfxmmc")), resultSet.getDouble("sfxmdj"));
                    result.add(hcp);
                }
                return result;
            }
        });
    }
    /**
     * 获取住院病人信息
     * @param query
     * @return
     */
    @Override
    public Integer queryPatientNum(String query) {
        StringBuffer buffer = new StringBuffer("SELECT COUNT(1) FROM (SELECT BRJZHM FROM V_HSBCI_TREATINFO WHERE BRJZHM ='"+query+"' UNION ALL  " +
                "SELECT BRJZHM FROM V_HSBBI_RECORDHOME WHERE BRJZHM ='"+query+"')" );
        return jdbcTemplate.queryForObject(buffer.toString(), Integer.class);
    }
    /**
     * 获取住院病人信息
     * @param query
     * @return
     */
    @Override
    public List queryPatientList(String query) {
        StringBuffer sql = new StringBuffer("select key_no , patient_id,inpatient_id,patient_name,patient_sex,patient_birth,patient_age,patient_age_type," +
                "patient_nation,patient_type,patient_dept, patient_dept_name,patient_ward,patient_ward_name, patient_bed, " +
                "lczd,commpany,id_cardno,patient_address,phone_no, chargr_type  from( " +
                "select b.brjzxh as key_no , b.brjzhm as patient_id,b.brdabh as inpatient_id,b.brdaxm as patient_name," +
                "b.brdaxb as patient_sex,b.brcsrq as patient_birth,regexp_substr(b.brjznl,'[0-9]+') as patient_age," +
                "substr(b.brjznl,-1) as patient_age_type,b.brdamz as patient_nation,'2' as patient_type,b.jzksid as patient_dept, " +
                "b.jzksmc as patient_dept_name,b.jzksid as patient_ward, " +
                "b.jzksmc as patient_ward_name,'' as patient_bed, " +
                "b.lczdmc as lczd,b.brdwmc as commpany,b.brsfzh as id_cardno,b.brjtdz as patient_address,b.brlxdh as phone_no, " +
                "b.brlbid as chargr_type from v_hsbci_treatinfo b where (b.brdabh = '"+query +"' or b.brjzhm ='"+query+"')" +
                " union all " +
                "select a.brzyid as key_no, a.brjzhm as patient_id,a.brdabh as inpatient_id,a.brdaxm as patient_name, " +
                "a.brdaxb as patient_sex,a.brcsrq as patient_birth,to_char(a.brjznl) as patient_age,a.brnldw as patient_age_type, " +
                "a.brdamz as patient_nation,'1' as patient_type,a.zyksid as patient_dept, " +
                "(select zzksmc from v_hsbhi_deptinfo where zzksid =a.zyksid) as patient_dept_name,a.zybqid as patient_ward, " +
                "(select zzksmc from v_hsbhi_deptinfo where zzksid =a.zybqid) as patient_ward_name,a.zycwhm as patient_bed, " +
                "a.ryzdmc as lczd,a.brdwmc as commpany,a.brsfzh as id_cardno,a.brjtdz as patient_address,a.brlxdh as phone_no, " +
                "a.brlbid as chargr_type from v_hsbbi_recordhome a where  a.brzyzt > 1 and  (a.brdabh = '"+query+"' or a.brjzhm = '"+query+"')"+
                "union all "+
                "select c.SQMXID as key_no, c.BRSQHM as patient_id,sqjlid as inpatient_id,c.XINGMING as patient_name, " +
                " c.XINGBIE as patient_sex,c.CHUSHENGRQ as patient_birth,'' as patient_age,'岁' as patient_age_type, " +
                " '' as patient_nation,'3' as patient_type,c.SONGJIANKSDM as patient_dept,  " +
                " c.SONGJIANKSMC as patient_dept_name,c.SONGJIANKSDM as patient_ward,c.SONGJIANKSMC patient_ward_name, '' as patient_bed, " +
                " '' as lczd,'' as commpany,c.sfzh as id_cardno,'' as patient_address,c.LIANXIDH as phone_no,  " +
                " '' as chargr_type from v_hsbtj_requestinfo c where  c.sqjlid ='"+query +"') order by key_no desc");
        return jdbcTemplate.query(sql.toString(), new ResultSetExtractor<List>() {
            @Override
            public List extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                List result = new ArrayList();
                while (resultSet.next()) {
                    PatientInfo pi = new PatientInfo();
                    pi.setKey_no(resultSet.getLong("key_no"));//Id 就诊ID(患者每一次来院的ID)  SAMINPATIENTID
                    pi.setPatient_id(resultSet.getString("patient_id"));//住院号SAMPATIENTNUMBER
                    pi.setInpatient_id(resultSet.getString("inpatient_id"));//档案号 SAMPATIENTID
                    pi.setPatient_name(resultSet.getString("patient_name"));//病人姓名
                    pi.setPatient_sex(resultSet.getString("patient_sex"));//性别
                    pi.setPatient_birth(resultSet.getDate("patient_birth"));//出生日期
                    pi.setPatient_age(resultSet.getString("patient_age"));//年龄
                    pi.setPatient_age_type(resultSet.getString("patient_age_type"));//年龄类型
                    pi.setPatient_nation(resultSet.getString("patient_nation"));//民族
                    pi.setPatient_type(resultSet.getString("patient_type"));//患者类型
                    pi.setPatient_dept(resultSet.getString("patient_dept"));//住院科室Id
                    pi.setPatient_dept_name(resultSet.getString("patient_dept_name"));//住院科室名称
                    pi.setPatient_ward(resultSet.getString("patient_ward"));//住院病区Id
                    pi.setPatient_ward_name(resultSet.getString("patient_ward_name"));//住院病区名称
                    pi.setPatient_bed(resultSet.getString("patient_bed"));//床号
                    pi.setLczd(resultSet.getString("lczd"));//临床诊断
                    pi.setCommpany(resultSet.getString("commpany"));//病人单位名称
                    pi.setId_cardno(resultSet.getString("id_cardno"));//身份证号
                    pi.setPatient_address(resultSet.getString("patient_address"));//家庭地址
                    pi.setPhone_no(resultSet.getString("phone_no"));//联系电话
                    pi.setChargr_type(resultSet.getString("chargr_type"));//收费类别Id
                    result.add(pi);
                }
                return result;
            }
        });
    }

    @Autowired
    private PimsPathologySampleManager pimsPathologySampleManager;
    @Override
    public boolean insert(PimsPathologyFee fee) {
        PimsPathologySample pathology = pimsPathologySampleManager.get(fee.getFeesampleid());
        String sql = " insert into V_HSBDI_CHARGEDETAIL (BRZYID,BRJZHM,FYJLID,FYXMID,FYXMC,FYHJJE,FYFSRQ,SQMXID,FYJLZT) values (" +pathology.getSampatientnumber()+
                ",'"+pathology.getSampatientnumber()+"',"+fee.getFeehisitemid()+","+fee.getFeeitemid()+",'"+fee.getFeehisname()+"',"+fee.getFeehisprice()*fee.getFeeamount()+
                ",to_date('"+ Constants.SDF.format(fee.getFeetime())+"','YYYY-MM-DD HH24:MI:SS'),"+fee.getFeeid()+",1)";
        jdbcTemplate.execute(sql);
        return true;
    }

    /**
     * 获取住院病人信息
     * @param
     * @return
     */
    @Override
    public List querPatientInfo(String patient_type,String brjzxh) {
        StringBuffer sql = new StringBuffer();
        if(StringUtils.isEmpty(patient_type) || StringUtils.isEmpty(brjzxh)){
            return null;
        }else if(patient_type.equals("1")){
            sql.append("select a.brzyid as key_no, a.brjzhm as patient_id,a.brdabh as inpatient_id,a.brdaxm as patient_name, " +
                    "a.brdaxb as patient_sex,a.brcsrq as patient_birth,to_char(a.brjznl) as patient_age,a.brnldw as patient_age_type, " +
                    "a.brdamz as patient_nation,'1' as patient_type,a.zyksid as patient_dept, " +
                    "(select zzksmc from v_hsbhi_deptinfo where zzksid =a.zyksid) as patient_dept_name,a.zybqid as patient_ward, " +
                    "(select zzksmc from v_hsbhi_deptinfo where zzksid =a.zybqid) as patient_ward_name,a.zycwhm as patient_bed, " +
                    "a.ryzdmc as lczd,a.brdwmc as commpany,a.brsfzh as id_cardno,a.brjtdz as patient_address,a.brlxdh as phone_no, " +
                    "a.brlbid as chargr_type from v_hsbbi_recordhome a where  a.brzyzt > 1 and a.brzyid ="+brjzxh);
        }else if(patient_type.equals("2")){
            sql.append("select b.brjzxh as key_no , b.brjzhm as patient_id,b.brdabh as inpatient_id,b.brdaxm as patient_name," +
                    "b.brdaxb as patient_sex,b.brcsrq as patient_birth,regexp_substr(b.brjznl,'[0-9]+') as patient_age," +
                    "substr(b.brjznl,-1) as patient_age_type,b.brdamz as patient_nation,'2' as patient_type,b.jzksid as patient_dept, " +
                    "b.jzksmc as patient_dept_name,b.jzksid as patient_ward, " +
                    "b.jzksmc as patient_ward_name,'' as patient_bed, " +
                    "b.lczdmc as lczd,b.brdwmc as commpany,b.brsfzh as id_cardno,b.brjtdz as patient_address,b.brlxdh as phone_no, " +
                    "b.brlbid as chargr_type from v_hsbci_treatinfo b where b.brjzxh="+brjzxh);
        }
        return jdbcTemplate.query(sql.toString(), new ResultSetExtractor<List>() {
            @Override
            public List extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                List result = new ArrayList();
                while (resultSet.next()) {
                    PatientInfo pi = new PatientInfo();
                    pi.setKey_no(resultSet.getLong("key_no"));//Id
                    pi.setPatient_id(resultSet.getString("patient_id"));//住院号
                    pi.setInpatient_id(resultSet.getString("inpatient_id"));//档案号
                    pi.setPatient_name(resultSet.getString("patient_name"));//病人姓名
                    pi.setPatient_sex(resultSet.getString("patient_sex"));//性别
                    pi.setPatient_birth(resultSet.getDate("patient_birth"));//出生日期
                    pi.setPatient_age(resultSet.getString("patient_age"));//年龄
                    pi.setPatient_age_type(resultSet.getString("patient_age_type"));//年龄类型
                    pi.setPatient_nation(resultSet.getString("patient_nation"));//民族
                    pi.setPatient_type(resultSet.getString("patient_type"));//患者类型
                    pi.setPatient_dept(resultSet.getString("patient_dept"));//住院科室Id
                    pi.setPatient_dept_name(resultSet.getString("patient_dept_name"));//住院科室名称
                    pi.setPatient_ward(resultSet.getString("patient_ward"));//住院病区Id
                    pi.setPatient_ward_name(resultSet.getString("patient_ward_name"));//住院病区名称
                    pi.setPatient_bed(resultSet.getString("patient_bed"));//床号
                    pi.setLczd(resultSet.getString("lczd"));//临床诊断
                    pi.setCommpany(resultSet.getString("commpany"));//病人单位名称
                    pi.setId_cardno(resultSet.getString("id_cardno"));//身份证号
                    pi.setPatient_address(resultSet.getString("patient_address"));//家庭地址
                    pi.setPhone_no(resultSet.getString("phone_no"));//联系电话
                    pi.setChargr_type(resultSet.getString("chargr_type"));//收费类别Id
                    result.add(pi);
                }
                return result;
            }
        });
    }


    /**
     * 获取影像学信息
     * @param query
     * @return
     */
    @Override
    public List queryyxxList(String query,String yxjclx) {
        StringBuffer sql = new StringBuffer(" select jcybid,zzjgdm,sqmxid,sqjlid,sjbrlx,brdaid,brjzhm,sjbrxm,sjbrch,sjbrxb" +
                ",sjbrnl,brnldw,yxjclx,jcmddm,jcmdmc,jcxmid,jcxmhm,jcxmmc,jcbwdm,jcbwnr" +
                ",yxsjnr,yxzdnr,lczddm,lczdmc,bszynr,jcjgzt,kdysid,kdysxm,kdksid,kdksmc,jcdjsj" +
                ",djryid,djryxm,djksid,spczsj,spryid,spryxm,djksmc,shczsj,shryid,shryxm,jgbggh,jgbgxm" +
                ",jgwcsj,sfdypb,sfyxpb,sfjzpb,jgljdz,ftpljz,brdabh,jgtxpb from DI_CHECKRESULT where BRJZHM = '"+query+"' and yxjclx='"+yxjclx+"' ");
        return jdbcTemplate.query(sql.toString(), new ResultSetExtractor<List>() {
            @Override
            public List extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                List result = new ArrayList();
                while (resultSet.next()) {
                    CheckResult pi = new CheckResult();
                    pi.setJcybid(resultSet.getString("jcybid"));//检测样本号码
                    pi.setZzjgdm(resultSet.getString("zzjgdm"));//组织机构代码
                    pi.setSqmxid(resultSet.getLong("sqmxid"));//申请明细编号
                    pi.setSqjlid(resultSet.getLong("sqjlid"));//申请记录序号
                    pi.setSjbrlx(resultSet.getInt("sjbrlx"));//受检病人类型
                    pi.setBrdaid(resultSet.getLong("brdaid"));//病人档案序号
                    pi.setBrjzhm(resultSet.getString("brjzhm"));//病人就诊号码
                    pi.setSjbrxm(resultSet.getString("sjbrxm"));//受检病人姓名
                    pi.setSjbrch(resultSet.getString("sjbrch"));//受检病人床号
                    pi.setSjbrxb(resultSet.getString("sjbrxb"));//受检病人性别
                    pi.setSjbrnl(resultSet.getInt("sjbrnl"));//受检病人年龄
                    pi.setBrnldw(resultSet.getString("brnldw"));//病人年龄单位
                    pi.setYxjclx(resultSet.getString("yxjclx"));//影像检查类型
                    pi.setJcmddm(resultSet.getString("jcmddm"));//检查目的代码
                    pi.setJcmdmc(resultSet.getString("jcmdmc"));//检查目的名称
                    pi.setJcxmid(resultSet.getString("jcxmid"));//检查项目序号
                    pi.setJcxmhm(resultSet.getString("jcxmhm"));//检查项目号码
                    pi.setJcxmmc(resultSet.getString("jcxmmc"));//检查项目名称
                    pi.setJcbwdm(resultSet.getString("jcbwdm"));//检查部位代码
                    pi.setJcbwnr(resultSet.getString("jcbwnr"));//检查部位内容
                    pi.setYxsjnr(resultSet.getString("yxsjnr"));//影像所见内容
                    pi.setYxzdnr(resultSet.getString("yxzdnr"));//影像诊断内容
                    pi.setLczddm(resultSet.getString("lczddm"));//临床诊断代码
                    pi.setLczdmc(resultSet.getString("lczdmc"));//临床诊断名称
                    pi.setBszynr(resultSet.getString("bszynr"));//病史摘要内容
                    pi.setJcjgzt(resultSet.getString("jcjgzt"));//检查结果状态
                    pi.setKdysid(resultSet.getString("kdysid"));//开单医生序号
                    pi.setKdysxm(resultSet.getString("kdysxm"));//开单医生姓名
                    pi.setKdksid(resultSet.getString("kdksid"));//开单科室序号
                    pi.setKdksmc(resultSet.getString("kdksmc"));//开单科室名称
                    pi.setJcdjsj(resultSet.getDate("jcdjsj"));//检查登记时间
                    pi.setDjryid(resultSet.getString("djryid"));//登记人员序号
                    pi.setDjryxm(resultSet.getString("djryxm"));//登记人员姓名
                    pi.setDjksid(resultSet.getString("djksid"));//登记科室序号
                    pi.setSpczsj(resultSet.getDate("spczsj"));//摄片操作时间
                    pi.setSpryid(resultSet.getString("spryid"));//摄片人员序号
                    pi.setSpryxm(resultSet.getString("spryxm"));//摄片人员姓名
                    pi.setDjksmc(resultSet.getString("djksmc"));//登记科室名称
                    pi.setShczsj(resultSet.getDate("shczsj"));//审核操作时间
                    pi.setShryid(resultSet.getString("shryid"));//审核人员序号
                    pi.setShryxm(resultSet.getString("shryxm"));//审核人员姓名
                    pi.setJgbggh(resultSet.getString("jgbggh"));//结果报告人员
                    pi.setJgbgxm(resultSet.getString("jgbgxm"));//结果报告姓名
                    pi.setJgwcsj(resultSet.getDate("jgwcsj"));//结果完成时间
                    pi.setSfdypb(resultSet.getInt("sfdypb"));//是否打印判别
                    pi.setSfyxpb(resultSet.getInt("sfyxpb"));//是否阳性判别
                    pi.setSfjzpb(resultSet.getInt("sfjzpb"));//是否急诊判别
                    pi.setJgljdz(resultSet.getString("jgljdz"));
                    pi.setFtpljz(resultSet.getString("ftpljz"));
                    pi.setBrdabh(resultSet.getString("brdabh"));
                    pi.setJgtxpb(resultSet.getInt("jgtxpb"));
                    result.add(pi);
                }
                return result;
            }
        });
    }

    @Autowired
    private PimsSampleResultManager pimsSampleResultManager;

    @Override
    public boolean insert(PimsPathologySample sample, PimsSysPathology psp) {
        int patClass = Integer.parseInt(psp.getPatclass());
        Map<String, String> resultMap = null;
        String  zdjg = "";
        if(patClass == 2) {
            resultMap = pimsSampleResultManager.getYjxbDiagnosisResult(sample.getSampleid());
            zdjg = resultMap.get("diagnosisResult");
        } else if(patClass == 7) {
            resultMap = pimsSampleResultManager.getHPVTestResult(sample.getSampleid());
            zdjg = resultMap.get("diagnosisResult");
        }
        else {
            PimsSampleResult result = pimsSampleResultManager.getSampleResultForPrint(sample.getSampleid());
            zdjg = result == null ? "" : result.getRestestresult();
        }
        zdjg = zdjg.replaceAll("<br>"," ");
        /**
         * 住院报告单模式修改 1 门诊 2 住院
         */
        long mode = sample.getSampatienttype();
        if(sample.getSampatienttype()==1)mode =2;
        if(sample.getSampatienttype()==2) mode=1;
        long agetype = sample.getSampatientagetype();
        String ageString = "";
        if(agetype == 1){
            ageString = "岁";
        }else if(agetype == 2){
            ageString = "月";
        }else if(agetype == 4){
            ageString = "周";
        }else if(agetype == 5){
            ageString = "日";
        }else if(agetype == 6){
            ageString = "小时";
        }
        String reportUrl = Config.getString("report.path", "") + "&patienttype=" + mode + "&patientid=" + sample.getSaminpatientid() + "&sampleid=" + sample.getSaminspectionid();
        reportUrl = reportUrl.replaceAll("&","'|| chr(38) ||'");
        StringBuffer sb = new StringBuffer();
        sb.append("insert into DI_LABSAMPLEINFO (" +
                "BRYZID," +//病理编号
                "JCYBID," +//条码号
                "ZZJGDM," +//机构代码
                "SJBRLX," +//受检病人类型
                "SJBRID," +//就诊ID
                "BRJZHM," +//住院卡号
                "SJBRXM," +//患者姓名
                "SJBRXB," +//性别(1男,2女,3未知)
                "SJBRNL," +//年龄
                "BRNLDW," +//年龄类型(1岁、2月、4周、5日、6小时)
                "SJYEPB," +//是否婴儿
                "SJBRCH," +//床号
                "LCZDMC," +//临床诊断
                "YBSJSJ," +//样本送检时间
                "KDYSID," +//开单医生序号
                "KDYSXM," +//开单医生姓名
                "KDKSID," +//开单科室序号
                "KDKSMC," +//开单科室名称
                "YBJSSJ," +//样本接收时间
                "ZXRYXM," +//执行人员姓名
                "ZXKSMC," +//执行科室名称
                "YBZXSJ," +//样本执行时间
                "SHRYXM," +//审核人员姓名
                "YBSHSJ," +//样本审核时间
                "YBLXMC," +//样本类型名称
                "YBJGSJ," +//样本结果时间
                "JCMDDM," +//检查目的代码
                "JCMDMC," +//检查目的名称
                "JGBZSM," +//报告预览地址
                "SFBLPB," +//是否病理判别
                "YBZDNR," +//样本诊断内容
                "BRDABH)" +//病案号
                "values (" +
                "'"+sample.getSampathologycode()+"'," +//病理编号
                "'"+sample.getSaminspectionid()+"'," +//条码号
                "'1001'," +//机构代码
                ""+mode+"," +//受检病人类型 int
                "'"+sample.getSaminpatientid()+"'," +//就诊ID
                "'"+sample.getSampatientnumber()+"'," +//住院卡号
                "'"+sample.getSampatientname()+"'," +//患者姓名
                "'"+sample.getSampatientsex()+"'," +//性别(1男,2女,3未知)
                ""+sample.getSampatientage()+"," +//年龄 int
                "'"+ageString+"'," +//年龄类型(1岁、2月、4周、5日、6小时)
                "0," +//是否婴儿 int
                "'"+sample.getSampatientbed()+"'," +//床号
                "'"+sample.getSampatientdignoses()+"'," +//临床诊断
                "TO_DATE('"+ ConvertUtil.getFormatDateGMT(sample.getSamsendtime(), null)+"','YYYY-MM-DD HH24:MI:SS')," +//样本送检时间
                "'"+sample.getSamsenddoctorid()+"'," +//开单医生序号
                "'"+sample.getSamsenddoctorname()+"'," +//开单医生姓名
                "'"+sample.getSamdeptcode()+"'," +//开单科室序号
                "'"+sample.getSamdeptname()+"'," +//开单科室名称
                "TO_DATE('"+ConvertUtil.getFormatDateGMT(sample.getSamreqtime(), null)+"','YYYY-MM-DD HH24:MI:SS')," +//样本接收时间
                "'"+sample.getSaminitiallyusername()+"'," +//执行人员姓名
                "'病理科'," +//执行科室名称
                "TO_DATE('"+ConvertUtil.getFormatDateGMT(sample.getSamreqtime(), null)+"','YYYY-MM-DD HH24:MI:SS')," +//样本执行时间
                "'"+sample.getSamauditer()+"'," +//审核人员姓名
                "TO_DATE('"+ConvertUtil.getFormatDateGMT(sample.getSamauditedtime(), null)+"','YYYY-MM-DD HH24:MI:SS')," +//样本审核时间
                "'"+sample.getSamsamplename()+"'," +//样本类型名称
                "TO_DATE('"+ConvertUtil.getFormatDateGMT(sample.getSamreportedtime(), null)+"','YYYY-MM-DD HH24:MI:SS')," +//样本结果时间
                "'"+sample.getSampathologyid()+"'," +//检查目的代码
                "'"+psp.getPatnamech()+"'," +//检查目的名称
                "'"+ reportUrl+"'," +//报告预览地址
                "1," +//是否病理判别 int
                "'"+zdjg+"'," +//样本诊断内容
                "'"+sample.getSampatientid()+"')" //病案号
                );
        jdbcTemplate.execute(sb.toString());
        return true;
    }

    @Override
    public boolean delete(PimsPathologySample sample) {
        StringBuffer sb = new StringBuffer();
        sb.append(" delete from DI_LABSAMPLEINFO where BRYZID = '"+sample.getSampathologycode()
                +"' and JCYBID = '"+sample.getSaminspectionid()+"'");
        jdbcTemplate.execute(sb.toString());
        return true;
    }
}
