package com.pims.dao.hibernate.pimsPathologySample;

import com.pims.dao.pimspathologysample.PimsPathologySampleDao;
import com.pims.model.Pdfinfo;
import com.pims.model.PimsBaseModel;
import com.pims.model.PimsPathologySample;
import com.pims.model.PimsSysColor;
import com.pims.webapp.controller.GridQuery;
import com.pims.webapp.controller.WebControllerUtil;
import com.smart.Constants;
import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.model.user.User;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by king on 2016/10/10.
 */
@Repository("pimsPathologySampleDao")
public class PimsPathologySampleDaoHibernate extends GenericDaoHibernate<PimsPathologySample, Long> implements PimsPathologySampleDao {

    public PimsPathologySampleDaoHibernate() {
        super(PimsPathologySample.class);
    }

    /**
     * 组装查询语句
     *
     * @param sb
     * @param map
     * @return
     */
    public StringBuffer getSearchSql(StringBuffer sb, PimsBaseModel map) {
        if (!StringUtils.isEmpty(map.getLogyid())) {
            sb.append(" and sampathologyid = " + map.getLogyid());//病种类别
        }
        if (map.getReq_bf_time() != null) {
            sb.append(" and samregisttime >= :req_bf_time");//开始时间
        }
        if (!StringUtils.isEmpty(map.getReq_sts())) {
            sb.append(" and samsecondv = " + map.getReq_sts());//标本状态合格不合格
        }
        if (!StringUtils.isEmpty(map.getSend_doctor())) {
            sb.append(" and samsenddoctorname like '%" + map.getSend_doctor().toUpperCase()+"%'");//送检医生
        }
        if (!StringUtils.isEmpty(map.getSend_dept())) {
            sb.append(" and sampathologycode like '%" + map.getSend_dept().toUpperCase()+"%'");//病理编号
        }
        if (!StringUtils.isEmpty(map.getSend_hosptail())) {
            sb.append(" and samsendhospital like '%" + map.getSend_hosptail().toUpperCase()+"%'");//送检医院
        }
        if (!StringUtils.isEmpty(map.getPatient_name())) {
            sb.append(" and sampatientname like '%" + map.getPatient_name().toUpperCase()+"%'");//病人姓名
        }
        if (map.getReq_af_time() != null) {
            sb.append(" and  samregisttime < :req_af_time");//结束时间
        }
        if (!StringUtils.isEmpty(map.getReq_code())) {
            sb.append(" and samsamplestatus = " + map.getReq_code());//病理状态
        }
        return sb;
    }

    /**
     * 查询标本列表
     *
     * @param map
     * @return
     */
    @Override
    public List<PimsPathologySample> getSampleList(PimsBaseModel map) {
        StringBuffer sb = new StringBuffer();
        sb.append(" from PimsPathologySample where samisdeleted = 0 ");
        getSearchSql(sb, map);
        String orderby = (map.getSidx() == null || map.getSidx().trim().equals("")) ? "saminspectionid" : map.getSidx();
        sb.append(" order by " + orderby + " " + map.getSord());
        System.out.println(sb.toString());
        return pagingList(sb.toString(), map.getStart(), map.getEnd(), map.getReq_bf_time(), map.getReq_af_time());
    }

    /**
     * 查询标本数量
     *
     * @param map
     * @return
     */
    @Override
    public int getReqListNum(PimsBaseModel map) {
        StringBuffer sb = new StringBuffer();
        sb.append(" select count(1) from PIMS_PATHOLOGY_SAMPLE where samisdeleted = 0 ");
        getSearchSql(sb, map);
        return countTotal(sb.toString(), map.getReq_bf_time(), map.getReq_af_time()).intValue();
    }

    /**
     * 查询标本内容
     *
     * @param id
     * @return
     */
    @Override
    public PimsPathologySample getBySampleNo(Long id) {
        StringBuffer sb = new StringBuffer();
        sb.append("from PimsPathologySample where sampleid = " + id);
        Object o = getSession().createQuery(sb.toString()).uniqueResult();
        if (o == null) return null;
        return (PimsPathologySample)o;
    }

    /**
     * 逻辑删除标本单
     *
     * @param id
     * @return
     */
    @Override
    public boolean delete(Long id) {
        if (id == null) {
            return false;
        } else {
            Query query = getSession().createSQLQuery(" update PIMS_PATHOLOGY_SAMPLE set samisdeleted = 1 where sampleid = " + id);
            query.executeUpdate();
            //更改申请单为可用状态
            getSession().createSQLQuery("update pims_pathology_requisition set reqstate = 0,reqsampleid= 0 where reqsampleid = '" + id + "'").executeUpdate();
            return true;
        }
    }

    /**
     * 查询单据是否可以被修改或删除
     *
     * @param id
     * @return
     */
    @Override
    public boolean canChange(Long id, String sts) {
        if (id == null || StringUtils.isEmpty(sts)) {
            return false;
        } else if (sts.equals("1")) {
            String sql = "select count(1) from PIMS_PATHOLOGY_SAMPLE where samsamplestatus in (0,1,2,3,4) and sampleid = " + id;
            if (countTotal(sql).intValue() == 1) {
                return true;
            }
        } else if (sts.equals("2")) {
            StringBuffer sb = new StringBuffer();
            sb.append(" from PimsPathologySample where samsamplestatus = 0 and sampleid = " + id);
            Object o = getSession().createQuery(sb.toString()).uniqueResult();
            if(o == null ){
                return false;
            }else{
                PimsPathologySample sample = (PimsPathologySample) o;
                sb = new StringBuffer();
                sb.append(" select max(sampathologycode) from PIMS_PATHOLOGY_SAMPLE  where samisdeleted=0 and sampathologyid = "+ sample.getSampathologyid() +
                        " and samcustomerid = "+ sample.getSamcustomerid());
                String str = getSession().createSQLQuery(sb.toString()).uniqueResult().toString();
                if(str.equals(sample.getSampathologycode())){
                    return  true;
                }
                return false;
            }
        }
        return false;
    }

    @Override
    public List<PimsPathologySample> querySample(PimsPathologySample sample, GridQuery gridQuery, String sql) {
        SQLQuery query = getSession().createSQLQuery(sql);
        try {
            setParameter(sample, query);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        query.setFirstResult(gridQuery.getStart());
        query.setMaxResults(gridQuery.getEnd());
        List<PimsPathologySample> ret = new ArrayList<>();
        List lis = query.list();
        if(lis.size() > 0) {
            for(Object obj : lis) {
                Object[] objects = (Object[])obj;
                PimsPathologySample pps = new PimsPathologySample();
                pps.setSampleid(((BigDecimal)objects[0]).longValue());
                pps.setSampathologycode((String)objects[1]);
                pps.setSamcustomerid(((BigDecimal)objects[2]).longValue());
                pps.setSampathologyid(((BigDecimal)objects[3]).longValue());
                pps.setSamsenddoctorname((String)objects[4]);
                pps.setSamreportorid((String)objects[5]);
                pps.setSamauditerid((String)objects[6]);
                pps.setSaminitiallyuserid((String)objects[7]);
                pps.setPatclass((String)objects[8]);
                pps.setSamsamplestatus(((BigDecimal) objects[9]).longValue());
                pps.setSampathologystatus(pps.pathologyStatus());
                pps.setSampatientname((String)objects[10]);
                ret.add(pps);
            }
        }
        return ret;
    }

    private void setParameter(PimsPathologySample sample, SQLQuery query) throws ParseException {
        long pathologyId = sample.getSampathologyid();
        long sampleStatus = sample.getSamsamplestatus();
        Date from = sample.getSamplesectionfrom();
        Date to = sample.getSamplesectionto();
        String inspectionId = sample.getSaminspectionid();
        String pathologyCode = sample.getSampathologycode();
        String patientName = sample.getSampatientname();
        if (sampleStatus > 0)
            query.setLong("SamSampleStatus", sample.getSamsamplestatus());
        if(pathologyId > 0)
            query.setLong("SamPathologyId", sample.getSampathologyid());
//        if(StringUtils.isNotEmpty(inspectionId))
//            query.setString("SamInspectionId", sample.getSaminspectionid());
        if(StringUtils.isNotEmpty(pathologyCode))
            query.setString("SamPathologyCode", sample.getSampathologycode());
//        if(StringUtils.isNotEmpty(patientName))
//            query.setString("SamPatientName", sample.getSampatientname());
        if(from == null && to != null) from = Constants.SDF.parse("1980-01-01 00:00:00");
        if(from != null && to == null) to = new Date();
        if(from != null) {
            query.setDate("samplesectionfrom", from);
            query.setDate("samplesectionto", to);
        }

    }

    @Override
    public Integer totalNum(PimsPathologySample sample, String s) {
        SQLQuery query = getSession().createSQLQuery(s);
        try {
            setParameter(sample, query);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ((BigDecimal)query.uniqueResult()).intValue();
    }

    @Override
    public void sign(PimsPathologySample sample) {
        StringBuilder builder = new StringBuilder();
        builder.append("update PimsPathologySample s set s.saminitiallytime=:saminitiallytime,s.saminitiallyuserid=:saminitiallyuserid,s.saminitiallyusername=:saminitiallyusername");
        builder.append(",s.samauditedtime=:samauditedtime,s.samauditerid=:samauditerid,s.samauditer=:samauditer,s.samreportedtime=:samreportedtime, s.samreportorid=:samreportorid");
        builder.append(",s.samreportor=:samreportor,s.samsamplestatus=:samsamplestatus where s.sampleid=:sampleid");
        Query query = getSession().createQuery(builder.toString());
        query.setParameter("saminitiallytime", sample.getSaminitiallytime());
        query.setParameter("saminitiallyuserid", sample.getSaminitiallyuserid());
        query.setParameter("saminitiallyusername", sample.getSaminitiallyusername());
        query.setParameter("samauditedtime", sample.getSamauditedtime());
        query.setParameter("samauditerid", sample.getSamauditerid());
        query.setParameter("samauditer", sample.getSamauditer());
        query.setParameter("samreportedtime", sample.getSamreportedtime());
        query.setParameter("samreportorid",sample.getSamreportorid());
        query.setParameter("samreportor", sample.getSamreportor());
        query.setParameter("sampleid", sample.getSampleid());
        query.setParameter("samsamplestatus", sample.getSamsamplestatus());
        query.executeUpdate();
    }

    /**
     * 获取最大条码号
     * @return
     */
    @Override
    public String sampleCode() {
        String sql = ("select max(saminspectionid) from pims_pathology_sample where samisdeleted=0");
        Object o = getSession().createSQLQuery(sql).uniqueResult();
        if(o==null) return null;
        return o.toString();
    }

    @Override
    public int getSamStaNum() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String sql = "SELECT COUNT(1) FROM " +
                "(SELECT A.SAMPLEID,A.SAMPATHOLOGYID,A.SAMPATHOLOGYCODE,A.SAMSENDDOCTORNAME," +
                "A.SAMSENDHOSPITAL,A.SAMPATIENTNAME,A.SAMSAMPLESTATUS,B.PIEDOCTORNAME,B.PIESAMPLINGTIME,B.PIEDOCTORID," +
                "RANK() OVER (PARTITION BY A.SAMPLEID ORDER BY B.PIESAMPLINGTIME,B.PIECEID ) AS RANK_NUM " +
                "FROM PIMS_PATHOLOGY_SAMPLE A,PIMS_PATHOLOGY_PIECES B " +
                "WHERE A.SAMPLEID = B.PIESAMPLEID) WHERE RANK_NUM = 1 " +
                "AND ((SAMSAMPLESTATUS IN (3,5,6,7)  AND PIEDOCTORID = '"+user.getId()+"' ) " +
                " OR (SAMSAMPLESTATUS = 4 AND SAMPLEID IN (SELECT tassampleid FROM pims_pathology_task WHERE tastasktype = 0 AND tasreciverid = '"+user.getId()+"'))" +
                " OR (SAMSAMPLESTATUS = 4 AND PIEDOCTORID = '"+user.getId()+"' AND SAMPLEID NOT IN (SELECT tassampleid FROM pims_pathology_task WHERE tastasktype = 0)))";
        return countTotal(sql).intValue();
    }

    public StringBuffer getSSql(StringBuffer sb,PimsBaseModel map){
        if(!StringUtils.isEmpty(map.getReq_sts())){
            sb.append(" and SAMSAMPLESTATUS = " + map.getReq_sts());
        }
        if(!StringUtils.isEmpty(map.getPatient_name())){
            sb.append(" and PIEDOCTORID = " + map.getPatient_name());
        }
        return sb;
    }

    @Override
    public List getSList(PimsBaseModel map) {
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT SAMPLEID,SAMPATHOLOGYID,SAMPATHOLOGYCODE,SAMSENDDOCTORNAME," +
                "SAMSENDHOSPITAL,SAMPATIENTNAME,PIEDOCTORNAME,PIESAMPLINGTIME,SAMSAMPLESTATUS,PIEDOCTORID FROM " +
                "(SELECT A.SAMPLEID,A.SAMPATHOLOGYID,A.SAMPATHOLOGYCODE,A.SAMSENDDOCTORNAME," +
                "A.SAMSENDHOSPITAL,A.SAMPATIENTNAME,A.SAMSAMPLESTATUS,B.PIEDOCTORNAME,B.PIESAMPLINGTIME,B.PIEDOCTORID," +
                "RANK() OVER (PARTITION BY A.SAMPLEID ORDER BY B.PIESAMPLINGTIME,B.PIECEID ) AS RANK_NUM " +
                " FROM PIMS_PATHOLOGY_SAMPLE A,PIMS_PATHOLOGY_PIECES B " +
                "WHERE A.SAMPLEID = B.PIESAMPLEID) WHERE RANK_NUM = 1");
        getSSql(sb,map);
        Query query = getSession().createSQLQuery(sb.toString());
        query.setFirstResult(map.getStart());
        query.setMaxResults(map.getEnd());
        return query.list();
    }

    @Override
    public int getSNum(PimsBaseModel map) {
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT COUNT(1) FROM " +
                "(SELECT A.SAMPLEID,A.SAMPATHOLOGYID,A.SAMPATHOLOGYCODE,A.SAMSENDDOCTORNAME," +
                "A.SAMSENDHOSPITAL,A.SAMPATIENTNAME,A.SAMSAMPLESTATUS,B.PIEDOCTORNAME,B.PIESAMPLINGTIME,B.PIEDOCTORID," +
                "RANK() OVER (PARTITION BY A.SAMPLEID ORDER BY B.PIESAMPLINGTIME,B.PIECEID ) AS RANK_NUM " +
                " FROM PIMS_PATHOLOGY_SAMPLE A,PIMS_PATHOLOGY_PIECES B " +
                "WHERE A.SAMPLEID = B.PIESAMPLEID) WHERE RANK_NUM = 1");
        getSSql(sb,map);
        return countTotal(sb.toString());
    }

    public StringBuffer getReportSql(PimsBaseModel map,StringBuffer sb){
        if(map.getReq_bf_time() != null){
            sb.append(" and samsendtime >= :req_bf_time ");//送检开始时间
        }
        if(map.getReq_af_time() != null){
            sb.append(" and samsendtime < :req_af_time ");//送检结束时间
        }
        if(!StringUtils.isEmpty(map.getLogyid())){
            sb.append(" and sampathologyid = " + map.getLogyid());//病种类别
        }
        if(!StringUtils.isEmpty(map.getReq_code())){
            String[] strings = map.getReq_code().split(",");
            String code = "(";
            for(int i=0;i<strings.length;i++){
                if(i == strings.length-1){
                    code += "'"+ strings[i]+"'";
                }else{
                    code += "'"+ strings[i]+"',";
                }
            }
            code +=")";
            sb.append(" and sampathologycode in "+ code);//病理编号
        }
        if(!StringUtils.isEmpty(map.getPatient_name())){
            sb.append(" and upper(sampatientname) like '%"+map.getPatient_name().toUpperCase()+"%'");//病人姓名
        }
        if(!StringUtils.isEmpty(map.getSampatientnumber())){
            sb.append(" and sampatientnumber ="+ map.getSampatientnumber());//住院号
        }
        if(!StringUtils.isEmpty(map.getSampatientbed())){
            sb.append(" and sampatientbed ="+ map.getSampatientbed());//床号
        }
        if(!StringUtils.isEmpty(map.getSampatientsex())){
            sb.append(" and sampatientsex ="+map.getSampatientsex());//性别
        }
        if(!StringUtils.isEmpty(map.getSend_doctor())){
            sb.append(" and samsenddoctorname ='"+ map.getSend_doctor()+"'");//送检医生
        }
        if(!StringUtils.isEmpty(map.getSend_dept())){
            sb.append(" and samdeptname ='"+ map.getSend_dept()+"'");//送检科室
        }
        if(!StringUtils.isEmpty(map.getSend_hosptail())){
            sb.append(" and samsendhospital = '"+map.getSend_hosptail()+"'");//送检医院
        }
        if(!StringUtils.isEmpty(map.getPiedoctorname())){
            sb.append(" and piedoctorname ='"+map.getPiedoctorname()+"'");//取材医生
        }
        if(!StringUtils.isEmpty(map.getParsectioneddoctor())){
            sb.append(" and parsectioneddoctor='"+map.getParsectioneddoctor()+"'");//切片医生
        }
        if(!StringUtils.isEmpty(map.getSaminitiallyusername())){
            sb.append(" and saminitiallyusername='"+map.getSaminitiallyusername()+"'");//诊断医生
        }
        if(!StringUtils.isEmpty(map.getMyzh())){
            String myzh = map.getMyzh();
            if(myzh.equals("0")){
                sb.append(" and myzhnum = 0 ");//免疫组化
            }else{
                sb.append(" and myzhnum > 0");//免疫组化
            }
        }
        if(!StringUtils.isEmpty(map.getTsrs())){
            String tsrs = map.getTsrs();
            if(tsrs.equals("0")){
                sb.append(" and tsrsnum = 0 ");//特殊染色
            }else{
                sb.append(" and tsrsnum > 0 ");//特殊染色
            }
        }
        if(!StringUtils.isEmpty(map.getFzbl())){
            String fzbl = map.getFzbl();
            if(fzbl.equals("0")){
                sb.append(" and fzblnum = 0 ");//分子病理
            }else{
                sb.append(" and fzblnum > 0");//分子病理
            }
        }
        if(!StringUtils.isEmpty(map.getBlzd())){
            sb.append(" and upper(restestresult) like '"+map.getBlzd().toUpperCase() +"'");//病理诊断
        }
        if(!StringUtils.isEmpty(map.getQcbw())){
            sb.append(" and sampleid in (select piesampleid from  pims_pathology_pieces s where upper(s.pieparts) like '"+map.getQcbw().toUpperCase()+"') ");//取材部位
        }
        return sb;
    }

    /**
     * 查询报告列表
     * @param map
     * @return
     */
    @Override
    public List getReportList(PimsBaseModel map) {
        StringBuffer sb = new StringBuffer();
        sb.append(" select sampleid,saminspectionid,sampathologyid,sampathologycode,sampatientname,sampatientage," +
                " sampatientnumber,sampatientbed,sampatientsex,samsamplestatus,samsendtime,saminitiallytime," +
                " samsenddoctorname,samdeptname,samsendhospital,piedoctorname,parsectioneddoctor,saminitiallyusername," +
                " restestresult,myzhnum,tsrsnum,fzblnum " +
                " from ( select sampleid,saminspectionid,sampathologyid,sampathologycode,sampatientname,sampatientage || sampatientagetype as sampatientage," +
                " sampatientnumber,sampatientbed,sampatientsex,samsamplestatus,samsendtime,saminitiallytime,samsenddoctorname,samdeptname," +
                " samsenddoctorid,samdeptcode,samsendhospital,piedoctorname,parsectioneddoctor,saminitiallyusername," +
                " restestresult,(select count(1) from PIMS_PATHOLOGY_ORDER_CHILD m,PIMS_SYS_REQ_TESTITEM n " +
                " where  m.Testitemid = n.Testitemid and n.Tesisorder = 1 and n.tesenglishname = 'MYZH' and m.chisampleid = sampleid) as myzhnum," +
                " (select count(1) from PIMS_PATHOLOGY_ORDER_CHILD m,PIMS_SYS_REQ_TESTITEM n " +
                " where  m.Testitemid = n.Testitemid and n.Tesisorder = 1 and n.tesenglishname = 'TSRS' and m.chisampleid = sampleid) as tsrsnum," +
                " (select count(1) from PIMS_PATHOLOGY_ORDER_CHILD m,PIMS_SYS_REQ_TESTITEM n" +
                " where  m.Testitemid = n.Testitemid and n.Tesisorder = 1 and n.tesenglishname = 'FZBL' and m.chisampleid = sampleid) as fzblnum," +
                " rank() over (partition by a.sampleid order by b.piesamplingtime,b.pieceid) as tk " +
                " from pims_pathology_sample a, pims_pathology_pieces b,pims_pathology_paraffin c,pims_sample_result d  " +
                " where a.sampleid = b.piesampleid and a.sampleid = b.piesampleid and a.sampleid = c.parsampleid and b.pieparaffinid = c.paraffinid and a.sampleid = d.ressampleid ) where tk = 1 ");
        getReportSql(map,sb);
        Query query = getSession().createSQLQuery(sb.toString());
        if(map.getReq_bf_time() != null){
            query.setDate("req_bf_time",map.getReq_bf_time());//送检开始时间
        }
        if(map.getReq_af_time() != null){
            query.setDate("req_af_time",map.getReq_af_time());//送检结束时间
        }
        query.setFirstResult(map.getStart());
        query.setMaxResults(map.getEnd());
        return query.list();
    }

    /**
     * 查询报表数量
     * @param map
     * @return
     */
    @Override
    public int getReportNum(PimsBaseModel map) {
        StringBuffer sb = new StringBuffer();
        sb.append(" select count(1) " +
                " from ( select sampleid,saminspectionid,sampathologyid,sampathologycode,sampatientname,sampatientage, " +
                " sampatientnumber,sampatientbed,sampatientsex,samsamplestatus,samsendtime,saminitiallytime, " +
                " samsenddoctorid,samdeptcode,samsendhospital,piedoctorname,parsectioneddoctor,saminitiallyusername, " +
                " restestresult,(select count(1) from PIMS_PATHOLOGY_ORDER_CHILD m,PIMS_SYS_REQ_TESTITEM n " +
                " where  m.Testitemid = n.Testitemid and n.Tesisorder = 1 and n.tesenglishname = 'MYZH' and m.chisampleid = sampleid) as myzhnum, " +
                " (select count(1) from PIMS_PATHOLOGY_ORDER_CHILD m,PIMS_SYS_REQ_TESTITEM n " +
                " where  m.Testitemid = n.Testitemid and n.Tesisorder = 1 and n.tesenglishname = 'TSRS' and m.chisampleid = sampleid) as tsrsnum, " +
                " (select count(1) from PIMS_PATHOLOGY_ORDER_CHILD m,PIMS_SYS_REQ_TESTITEM n " +
                " where  m.Testitemid = n.Testitemid and n.Tesisorder = 1 and n.tesenglishname = 'FZBL' and m.chisampleid = sampleid) as fzblnum, " +
                " rank() over (partition by a.sampleid order by b.piesamplingtime,b.pieceid) as tk " +
                " from pims_pathology_sample a, pims_pathology_pieces b,pims_pathology_paraffin c,pims_sample_result d " +
                " where a.sampleid = b.piesampleid  and a.sampleid = b.piesampleid and a.sampleid = c.parsampleid and b.pieparaffinid = c.paraffinid and a.sampleid = d.ressampleid  ) where tk = 1 ");
        getReportSql(map,sb);
        return countTotal(sb.toString(),map.getReq_bf_time(),map.getReq_af_time());
    }

    @Override
    public List getReportDelayList(PimsBaseModel map) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT sampleid,sampathologyid,sampathologycode,sampatientname,sampatientage,sampatientsex, " +
                "samsendtime,delreporttime,deldoctor,delreason,samsamplestatus,samdeptname,samsenddoctorname,samsendhospital,deldiagnosis,restestresult,delcreatetime,deldays FROM " +
                "(SELECT sampleid,sampathologyid,sampathologycode,sampatientname,sampatientage||sampatientagetype as sampatientage,sampatientsex, " +
                "samsendtime,delreporttime,deldoctor,delreason,samsamplestatus,samdeptname,samsenddoctorname,samsendhospital,deldiagnosis,delcreatetime,deldays, " +
                "(select restestresult from pims_sample_result where ressampleid=sampleid and rownum=1 ) as restestresult," +
                "RANK() OVER (PARTITION BY SAMPLEID ORDER BY DELREPORTTIME DESC) AS TK " +
                " FROM PIMS_PATHOLOGY_SAMPLE,PIMS_PATHOLOGY_REPORT_DELAY " +
                "WHERE SAMPLEID = DELSAMPLEID) WHERE TK = 1");
        getReportDelaySql(sb,map);
        Query query = getSession().createSQLQuery(sb.toString());
        if(map.getReq_bf_time() != null){
            query.setDate("req_bf_time",map.getReq_bf_time());//送检开始时间
        }
        if(map.getReq_af_time() != null){
            query.setDate("req_af_time",map.getReq_af_time());//送检结束时间
        }
        query.setFirstResult(map.getStart());
        query.setMaxResults(map.getEnd());
        return query.list();
    }

    @Override
    public int getReportDelayNum(PimsBaseModel map) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT count(1) FROM " +
                "(SELECT sampleid,sampathologyid,sampathologycode,sampatientname,sampatientage||sampatientagetype as sampatientage,sampatientsex, " +
                "samsendtime,delreporttime,deldoctor,delreason,samsamplestatus,samdeptname,samsenddoctorname,samsendhospital, " +
                "RANK() OVER (PARTITION BY SAMPLEID ORDER BY DELREPORTTIME DESC) AS TK " +
                " FROM PIMS_PATHOLOGY_SAMPLE,PIMS_PATHOLOGY_REPORT_DELAY " +
                "WHERE SAMPLEID = DELSAMPLEID) WHERE TK = 1");
        getReportDelaySql(sb,map);
        return countTotal(sb.toString(),map.getReq_bf_time(),map.getReq_af_time());
    }

    private StringBuffer getReportDelaySql(StringBuffer sb,PimsBaseModel map){
        if(map.getReq_bf_time() != null){
            sb.append(" and samsendtime >= :req_bf_time ");//送检开始时间
        }
        if(map.getReq_af_time() != null){
            sb.append(" and samsendtime < :req_af_time ");//送检结束时间
        }
        if(!StringUtils.isEmpty(map.getReq_code())){
            String[] strings = map.getReq_code().split(",");
            String code = "(";
            for(int i=0;i<strings.length;i++){
                if(i == strings.length-1){
                    code += "'"+ strings[i]+"'";
                }else{
                    code += "'"+ strings[i]+"',";
                }
            }
            code +=")";
            sb.append(" and sampathologycode in "+ code);//病理编号
        }
        if(!StringUtils.isEmpty(map.getPatient_name())){
            sb.append(" and upper(sampatientname) like '%"+map.getPatient_name().toUpperCase()+"%'");//病人姓名
        }
        if(!StringUtils.isEmpty(map.getSend_doctor())){
            sb.append(" and samsenddoctorname ='"+ map.getSend_doctor()+"'");//送检医生
        }
        if(!StringUtils.isEmpty(map.getSend_dept())){
            sb.append(" and samdeptname ='"+ map.getSend_dept()+"'");//送检科室
        }
        if(!StringUtils.isEmpty(map.getSend_hosptail())){
            sb.append(" and samsendhospital = '"+map.getSend_hosptail()+"'");//送检医院
        }
        if(!StringUtils.isEmpty(map.getPiedoctorname())){
            sb.append(" and deldoctor ='"+map.getPiedoctorname()+"'");//申请医生
        }
        return sb;
    }

    /**
     * 组装统计报表sql
     * @param map
     * @param sb
     * @return
     */
    private StringBuffer getCountReportSql(PimsBaseModel map,StringBuffer sb){
        if(map.getReq_bf_time() != null){
            sb.append(" and samregisttime >= :req_bf_time");
        }
        if(map.getReq_af_time() != null){
            sb.append(" and samregisttime < :req_af_time");
        }
        return sb;
    }
    /**
     * 填充query
     * @param map
     * @param query
     * @return
     */
    private Query getQueryInfo (PimsBaseModel map,Query query){
        if(map.getReq_bf_time() != null){
            query.setDate("req_bf_time",map.getReq_bf_time());
        }
        if(map.getReq_af_time() != null){
            query.setDate("req_af_time",map.getReq_af_time());
        }
        return query;
    }

    /**
     * 日志统计列表
     * @param map
     * @return
     */
    @Override
    public List getRztj(PimsBaseModel map) {
        StringBuffer sb = new StringBuffer();
        sb.append("select * from (select samsamplestatus  from pims_pathology_sample where samisdeleted =0  ");
        getCountReportSql(map,sb);
        sb.append(" )pivot(count(samsamplestatus) for samsamplestatus in(0,1,2,3,4,5,6,7,8))");
        Query query = getSession().createSQLQuery(sb.toString());
        getQueryInfo(map,query);
        return query.list();
    }

    /**
     * 日志统计详细信息
     * @param map
     * @return
     */
    @Override
    public List getRztjInfo(PimsBaseModel map) {
        StringBuffer sb = new StringBuffer();
        sb.append("select * from (select samsamplestatus,sampathologyid  from pims_pathology_sample where samisdeleted =0  ");
        getCountReportSql(map,sb);
        sb.append(" )pivot(count(samsamplestatus) for samsamplestatus in(0,1,2,3,4,5,6,7,8))");
        Query query = getSession().createSQLQuery(sb.toString());
        getQueryInfo(map,query);
        return query.list();
    }

    /**
     * 标本来源统计
     * @param map
     * @return
     */
    @Override
    public List getBbly(PimsBaseModel map) {
        StringBuffer sb = new StringBuffer();
        String req_code = map.getReq_code();
        if(!StringUtils.isEmpty(req_code)){
            if(req_code.equals("1")){//送检单位
                sb.append("select samsendhospital as name,count(1) as nums  from pims_pathology_sample where samisdeleted =0 ");
                getCountReportSql(map,sb);
                sb.append(" group by samsendhospital");
            }else if(req_code.equals("2")){//送检科室
                sb.append("select samdeptname as name,count(1) as nums  from pims_pathology_sample where samisdeleted =0 ");
                getCountReportSql(map,sb);
                sb.append(" group by samdeptname,samdeptcode");
            }else if(req_code.equals("3")){//送检医生
                sb.append("select samsenddoctorname as name,count(1) as nums  from pims_pathology_sample where samisdeleted = 0 ");
                getCountReportSql(map,sb);
                sb.append(" group by samsenddoctorname,samsenddoctorid");
            }
            Query query = getSession().createSQLQuery(sb.toString());
            getQueryInfo(map,query);
            return query.list();
        }
        return  null;
    }

    /**
     * 收费统计报告
     * @param map
     * @return
     */
    @Override
    public List getSftj(PimsBaseModel map) {
        StringBuffer sb = new StringBuffer();
        String req_code = map.getReq_code();
        if(!StringUtils.isEmpty(req_code)){
            if(req_code.equals("1")){//送检单位
                sb.append("select samsendhospital as name ,sum(feeprince) prices from pims_pathology_fee,pims_pathology_sample where feesampleid = sampleid and  samisdeleted =0 ");
                getCountReportSql(map,sb);
                sb.append(" group by samsendhospital");
            }else if(req_code.equals("2")){//送检科室
                sb.append("select samdeptname as name ,sum(feeprince) prices from pims_pathology_fee,pims_pathology_sample where feesampleid = sampleid and  samisdeleted =0 ");
                getCountReportSql(map,sb);
                sb.append(" group by samdeptname,samdeptcode");
            }else if(req_code.equals("3")){//送检医生
                sb.append("select samsenddoctorname as name ,sum(feeprince) prices from pims_pathology_fee,pims_pathology_sample where feesampleid = sampleid and  samisdeleted =0 ");
                getCountReportSql(map,sb);
                sb.append(" group by samsenddoctorname,samsenddoctorid");
            }else if(req_code.equals("4")){//按费用类别
                sb.append("select feecategory as name ,sum(feeprince) prices from pims_pathology_fee,pims_pathology_sample where feesampleid = sampleid and  samisdeleted =0 ");
                getCountReportSql(map,sb);
                sb.append(" group by feecategory");
            }else if(req_code.equals("5")){//收费明细
                sb.append("select feenamech as name ,sum(feeprince) prices from pims_pathology_fee,pims_pathology_sample where feesampleid = sampleid and  samisdeleted =0 ");
                getCountReportSql(map,sb);
                sb.append(" group by feeitemid,feenamech");
            }else if(req_code.equals("6")){//报告医生
                sb.append("select samreportor as name ,sum(feeprince) prices from pims_pathology_fee,pims_pathology_sample where feesampleid = sampleid and  samisdeleted =0 ");
                getCountReportSql(map,sb);
                sb.append(" group by samreportorid,samreportor");
            }
            Query query = getSession().createSQLQuery(sb.toString());
            getQueryInfo(map,query);
            return query.list();
        }
        return  null;
    }

    @Override
    public List<PimsSysColor> getColor(PimsSysColor psc){
        User user = WebControllerUtil.getAuthUser();
        StringBuffer hql = new StringBuffer();
        hql.append("from PimsSysColor where colmodule='"+psc.getColmodule()+"' and colobject = '"+psc.getColobject()+"'");
        hql.append(" and colowner in('"+user.getId()+"','9999999999')");
        return pagingList(hql.toString());
    }

    //@Override
    //public List<PimsSysColor> getColor2(){
    //    User user = WebControllerUtil.getAuthUser();
    //    StringBuffer hql = new StringBuffer();
    //    hql.append("from PimsSysColor where colmodule='5' and colobject = 'Sample'");
    //    hql.append(" and colowner in('"+user.getId()+"','9999999999')");
    //    return pagingList(hql.toString());
    //}


    @Override
    public boolean updatebgjStates(Pdfinfo pi) {
        StringBuffer sb = new StringBuffer();
        sb.append(" update pims_pathology_sample set samsamplestatus = 8 where saminspectionid=:saminspectionid and" +
                " sampathologycode =:sampathologycode");
        Query query = getSession().createSQLQuery(sb.toString());
        query.setString("saminspectionid", pi.getSample_id());
        query.setString("sampathologycode", pi.getCheck_id());
        query.executeUpdate();
        return true;
    }

    @Override
    public int agoStates(PimsPathologySample sample) {
        int result  = 0;
        StringBuffer sb = new StringBuffer();
        //查询是否有取材
        sb.append(" SELECT COUNT(1) FROM PIMS_PATHOLOGY_PIECES S WHERE S.PIESAMPLEID=" + sample.getSampleid());
        int piecenums = countTotal(sb.toString());
        result = piecenums;
        if(piecenums > 0){
            sb = new StringBuffer();
            //查询是否完全取材
            sb.append(" SELECT COUNT(1) FROM PIMS_PATHOLOGY_PIECES S WHERE S.PIESTATE = 0 AND S.PIESAMPLEID="+ sample.getSampleid());
            piecenums = countTotal(sb.toString());
            if(piecenums == 0){//完全取材
                result += 1;
                sb = new StringBuffer();
                //查询是否包埋
                sb.append(" SELECT COUNT(1) FROM PIMS_PATHOLOGY_PIECES S WHERE S.PIEISEMBED = 0 AND S.PIESAMPLEID="+ sample.getSampleid());
                piecenums = countTotal(sb.toString());
                if(piecenums == 0){//完全包埋
                    result += 1;
                    sb = new StringBuffer();
                    //查询是否完全切片
                    sb.append(" SELECT COUNT(1) FROM PIMS_PATHOLOGY_PARAFFIN S WHERE S.PARISSECTIONED = 0 AND S.PARSAMPLEID="+ sample.getSampleid());
                    piecenums = countTotal(sb.toString());
                    if(piecenums == 0){
                        result += 1;
                    }
                }
            }
        }
        return result;
    }
}

