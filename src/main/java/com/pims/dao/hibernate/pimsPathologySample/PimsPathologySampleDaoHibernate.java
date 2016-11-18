package com.pims.dao.hibernate.pimsPathologySample;

import com.pims.dao.pimspathologysample.PimsPathologySampleDao;
import com.pims.model.PimsBaseModel;
import com.pims.model.PimsPathologySample;
import com.pims.webapp.controller.GridQuery;
import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.model.user.User;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
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
        if (id == null) {
            return null;
        } else {
            Query query = getSession().createQuery("from PimsPathologySample where sampleid = " + id);
            if (query.list() == null || query.list().size() != 1) {
                return null;
            } else {
                return (PimsPathologySample) query.list().get(0);
            }
        }
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
            String sql = "select count(1) from PIMS_PATHOLOGY_SAMPLE where samsamplestatus = 0 and sampleid = " + id;
            if (countTotal(sql).intValue() == 1) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<PimsPathologySample> querySample(PimsPathologySample sample, GridQuery gridQuery, String sql) {
        Query query = getSession().createQuery(sql);
        setParameter(sample, query);
        query.setFirstResult(gridQuery.getStart());
        query.setMaxResults(gridQuery.getEnd());
        List<PimsPathologySample> lis = query.list();
        if(lis.size() > 0) {
            for(PimsPathologySample sp : lis) {
                sp.setSampathologystatus(sp.pathologyStatus());
            }
        }
        return lis;
    }

    private void setParameter(PimsPathologySample sample, Query query) {
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
        if(inspectionId != null && !"".equals(inspectionId.trim()))
            query.setString("SamInspectionId", sample.getSaminspectionid());
        if(pathologyCode != null && !"".equals(pathologyCode.trim()))
            query.setString("SamPathologyCode", sample.getSampathologycode());
        if(patientName != null && !"".equals(patientName.trim()))
            query.setString("SamPatientName", sample.getSampatientname());
        if( from != null && to == null) {
            query.setDate("samplesectionfrom", from);
            query.setDate("samplesectionto", new Date());
        }
        if(from != null && to != null) {
            query.setDate("samplesectionfrom", from);
            query.setDate("samplesectionto", to);
        }
    }

    @Override
    public Integer totalNum(PimsPathologySample sample, String s) {
        Query query = getSession().createQuery(s);
        setParameter(sample, query);
        return ((Long)query.uniqueResult()).intValue();
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
}
