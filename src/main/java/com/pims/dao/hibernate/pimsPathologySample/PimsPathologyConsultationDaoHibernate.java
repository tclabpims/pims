package com.pims.dao.hibernate.pimsPathologySample;

import com.pims.dao.pimspathologysample.PimsPathologyConsultationDao;
import com.pims.model.*;
import com.smart.dao.hibernate.GenericDaoHibernate;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by king on 2016/10/25.
 */
@Repository("pimsPathologyConsultationDao")
public class PimsPathologyConsultationDaoHibernate extends GenericDaoHibernate<PimsPathologyConsultation, Long> implements PimsPathologyConsultationDao {

    public PimsPathologyConsultationDaoHibernate() {
        super(PimsPathologyConsultation.class);
    }
    /**
     * 组装sql
     * @param sb
     * @param map
     * @return
     */
    public StringBuffer getsql(StringBuffer sb,PimsBaseModel map){
        if (map.getReq_bf_time() != null) {
            sb.append(" and consponsoredtime >= :req_bf_time");
        }
        if (map.getReq_af_time() != null) {
            sb.append(" and  consponsoredtime < :req_af_time");
        }
        if(!StringUtils.isEmpty(map.getPatient_name())){//创建人
            sb.append(" and consponsoreduserid = '"+ map.getPatient_name()+"'");
        }
        if(!StringUtils.isEmpty(map.getReq_sts())){
            sb.append(" and conconsultationstate =" + map.getReq_sts());
        }
        return  sb;
    }
    /**
     * 查询会诊列表
     * @param map
     * @return
     */
    @Override
    public List<PimsPathologyConsultation> getConList(PimsBaseModel map) {
        StringBuffer sb = new StringBuffer();
        sb.append(" from PimsPathologyConsultation where 1 = 1 ");
        getsql(sb,map);
        return pagingList(sb.toString(),map.getStart(),map.getEnd(),map.getReq_bf_time(),map.getReq_af_time());
    }
    /**
     * 查询会诊数量
     * @param map
     * @return
     */
    @Override
    public int getReqListNum(PimsBaseModel map) {
        StringBuffer sb = new StringBuffer();
        sb.append(" select count(1) from pims_pathology_consultation where 1 = 1 ");
        getsql(sb,map);
        return countTotal(sb.toString(),map.getReq_bf_time(),map.getReq_af_time()).intValue();
    }
    /**
     * 获取蜡块数量
     * @param id
     * @return
     */
    @Override
    public int getParaNums(Long id) {
        String sql = " select count(1) from pims_pathology_paraffin where parsampleid =" + id;
        return countTotal(sql).intValue();
    }

    /**
     *
     * 获取病理诊断结果列表
     * @param id
     * @return
     */
    @Override
    public List<PimsSampleResult> getSampleResultList(Long id) {
        StringBuffer sb = new StringBuffer();

        sb.append(" from PimsSampleResult where  ressampleid = "+ id);
        return getSession().createQuery(sb.toString()).list();
    }

    /**
     * 更新会诊状态
     * @param id
     */
    @Override
    public void updateConStates(int states,Long id) {
        String sql = "update pims_pathology_consultation set conconsultationstate ="+ states + " where consultationid = "+ id;
        getSession().createSQLQuery(sql).executeUpdate();
    }

    /**
     * 查询会诊结果列表
     * @param id
     * @return
     */
    @Override
    public List<PimsConsultationDetail> getConDets(Long id) {
        StringBuffer sb = new StringBuffer();
        sb.append(" from PimsConsultationDetail where detconsultationid ="+ id+" order by detadvice asc,detconsultationtime desc");
        return getSession().createQuery(sb.toString()).list();
    }

    /**
     * 查询会诊详细信息
     * @param id
     * @return
     */
    @Override
    public PimsPathologyConsultation getConsInfo(Long id) {
        StringBuffer sb = new StringBuffer();
        sb.append(" from  PimsPathologyConsultation where consampleid = "+ id);
        Object o = getSession().createQuery(sb.toString()).uniqueResult();
        if(o==null) return null;
        return (PimsPathologyConsultation)o;
    }

    /**
     * 查询单据是否可以被修改
     * @param id
     * @return
     */
    @Override
    public boolean canChang(Long id) {
        String sql = "select count(1) from pims_pathology_consultation where conconsultationstate = 0" +
                " and consultationid =" + id;
        int num = countTotal(sql).intValue();
        if(num == 1){
            return true;
        }
        return false;
    }
}
