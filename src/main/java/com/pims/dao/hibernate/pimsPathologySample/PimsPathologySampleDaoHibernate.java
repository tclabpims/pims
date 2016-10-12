package com.pims.dao.hibernate.pimsPathologySample;

import com.pims.dao.pimspathologysample.PimsPathologySampleDao;
import com.pims.model.PimsBaseModel;
import com.pims.model.PimsPathologySample;
import com.smart.dao.hibernate.GenericDaoHibernate;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Created by king on 2016/10/10.
 */
@Repository("pimsPathologySampleDao")
public class PimsPathologySampleDaoHibernate extends GenericDaoHibernate<PimsPathologySample,Long> implements PimsPathologySampleDao {

    public PimsPathologySampleDaoHibernate(){super(PimsPathologySample.class);}

    /**
     * 查询标本列表
     * @param map
     * @return
     */
    @Override
    public List<PimsPathologySample> getSampleList(PimsBaseModel map) {
        StringBuffer sb = new StringBuffer();
        sb.append(" from PimsPathologySample where samisdeleted = 0 ");
        if(!StringUtils.isEmpty(map.getLogyid())){
            sb.append(" and sampathologyid = " + map.getLogyid());
        }
        if(!StringUtils.isEmpty(map.getReq_bf_time())){
            sb.append(" and samregisttime >= to_date('" + map.getReq_bf_time()+"','YYYY-MM-DD')");
        }
        if(!StringUtils.isEmpty(map.getReq_sts())){
            sb.append(" and samsecondv = " + map.getReq_sts());
        }
        if(!StringUtils.isEmpty(map.getSend_doctor())){
            sb.append(" and samsenddoctorid = " +  map.getSend_doctor());
        }
        if(!StringUtils.isEmpty(map.getSend_dept())){
            sb.append(" and sampathologycode = " + map.getSend_dept());
        }
        if(!StringUtils.isEmpty(map.getSend_hosptail())){
            sb.append(" and samsendhospital = " + map.getSend_hosptail());
        }
        if(!StringUtils.isEmpty(map.getPatient_name())){
            sb.append(" and sampatientname = " + map.getPatient_name());
        }
        if(!StringUtils.isEmpty(map.getReq_af_time())){
            sb.append(" and  samregisttime < to_date('" + map.getReq_af_time()+"','YYYY-MM-DD')+1");
        }
        if(!StringUtils.isEmpty(map.getReq_code())){
            sb.append(" and saminspectionid = " + map.getReq_code());
        }
        String orderby = (map.getSidx()==null|| map.getSidx().trim().equals(""))?"saminspectionid":map.getSidx();
        sb.append(" order by " + orderby + " " +map.getSord());
        System.out.println(sb.toString());
        return pagingList(sb.toString(),map.getStart(),map.getEnd());
    }

    /**
     * 查询标本数量
     * @param map
     * @return
     */
    @Override
    public int getReqListNum(PimsBaseModel map) {
        StringBuffer sb = new StringBuffer();
        sb.append(" from PimsPathologySample where samisdeleted = 0 ");
        if(!StringUtils.isEmpty(map.getLogyid())){
            sb.append(" and sampathologyid = " + map.getLogyid());
        }
        if(!StringUtils.isEmpty(map.getReq_bf_time())){
            sb.append(" and samregisttime >= to_date('" + map.getReq_bf_time()+"','YYYY-MM-DD')");
        }
        if(!StringUtils.isEmpty(map.getReq_sts())){
            sb.append(" and samsecondv = " + map.getReq_sts());
        }
        if(!StringUtils.isEmpty(map.getSend_doctor())){
            sb.append(" and samsenddoctorid = " +  map.getSend_doctor());
        }
        if(!StringUtils.isEmpty(map.getSend_dept())){
            sb.append(" and sampathologycode = " + map.getSend_dept());
        }
        if(!StringUtils.isEmpty(map.getSend_hosptail())){
            sb.append(" and samsendhospital = " + map.getSend_hosptail());
        }
        if(!StringUtils.isEmpty(map.getPatient_name())){
            sb.append(" and sampatientname = " + map.getPatient_name());
        }
        if(!StringUtils.isEmpty(map.getReq_af_time())){
            sb.append(" and  samregisttime < to_date('" + map.getReq_af_time()+"','YYYY-MM-DD')+1");
        }
        if(!StringUtils.isEmpty(map.getReq_code())){
            sb.append(" and saminspectionid = " + map.getReq_code());
        }
        return countTotal(sb.toString()).intValue();
    }

    /**
     * 查询标本内容
     * @param id
     * @return
     */
    @Override
    public PimsPathologySample getBySampleNo(Long id) {
        if(id == null){
            return null;
        }else{
            Query query = getSession().createQuery("from PimsPathologySample where sampleid = "+ id);
            if(query.list() == null || query.list().size() != 1){
                return null;
            }else{
                return (PimsPathologySample)query.list().get(0);
            }
        }
    }

    /**
     * 逻辑删除标本单
     * @param id
     * @return
     */
    @Override
    public boolean delete(Long id) {
        if(id == null){
            return false;
        }else{
            Query query = getSession().createSQLQuery(" update PIMS_PATHOLOGY_SAMPLE set samisdeleted = 1 where sampleid = "+ id);
            query.executeUpdate();
            return true;
        }
    }

    /**
     * 查询单据是否可以被修改或删除
     * @param id
     * @return
     */
    @Override
    public boolean canChange(Long id,String sts) {
        if(id == null || StringUtils.isEmpty(sts)){
            return false;
        }else if(sts.equals("1")){
            String sql =  "select count(1) PIMS_PATHOLOGY_SAMPLE where sampleid in (0,1,2,3,4) and sampleid = "+ id;
            if(countTotal(sql).intValue() == 1){
                return true;
            }
        }else if(sts.equals("2")){
            String sql =  "select count(1) PIMS_PATHOLOGY_SAMPLE where sampleid = 0 and sampleid = "+ id;
            if(countTotal(sql).intValue() == 1){
                return true;
            }
        }
        return false;
    }
}