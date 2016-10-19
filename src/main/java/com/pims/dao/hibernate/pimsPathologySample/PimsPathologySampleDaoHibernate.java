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
     * 组装查询语句
     * @param sb
     * @param map
     * @return
     */
    public  StringBuffer getSearchSql(StringBuffer sb,PimsBaseModel map){
        if(!StringUtils.isEmpty(map.getLogyid())){
            sb.append(" and sampathologyid = " + map.getLogyid());
        }
        if(map.getReq_bf_time() != null){
            sb.append(" and samregisttime >= :req_bf_time");
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
        if(map.getReq_af_time()  != null){
            sb.append(" and  samregisttime < :req_af_time");
        }
        if(!StringUtils.isEmpty(map.getReq_code())){
            sb.append(" and saminspectionid = " + map.getReq_code());
        }
        return sb;
    }

    /**
     * 查询标本列表
     * @param map
     * @return
     */
    @Override
    public List<PimsPathologySample> getSampleList(PimsBaseModel map) {
        StringBuffer sb = new StringBuffer();
        sb.append(" from PimsPathologySample where samisdeleted = 0 ");
        getSearchSql(sb,map);
        String orderby = (map.getSidx()==null|| map.getSidx().trim().equals(""))?"saminspectionid":map.getSidx();
        sb.append(" order by " + orderby + " " +map.getSord());
        System.out.println(sb.toString());
        return pagingList(sb.toString(),map.getStart(),map.getEnd(),map.getReq_bf_time(),map.getReq_af_time());
    }

    /**
     * 查询标本数量
     * @param map
     * @return
     */
    @Override
    public int getReqListNum(PimsBaseModel map) {
        StringBuffer sb = new StringBuffer();
        sb.append(" select count(1) from PIMS_PATHOLOGY_SAMPLE where samisdeleted = 0 ");
        getSearchSql(sb,map);
        return countTotal(sb.toString(),map.getReq_bf_time(),map.getReq_af_time()).intValue();
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
            //更改申请单为可用状态
            getSession().createSQLQuery("update pims_pathology_requisition set reqstate = 0,reqsampleid= null where reqsampleid = '"+id+"'");
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
            String sql =  "select count(1) from PIMS_PATHOLOGY_SAMPLE where samsamplestatus in (0,1,2,3,4) and sampleid = "+ id;
            if(countTotal(sql).intValue() == 1){
                return true;
            }
        }else if(sts.equals("2")){
            String sql =  "select count(1) from PIMS_PATHOLOGY_SAMPLE where samsamplestatus = 0 and sampleid = "+ id;
            if(countTotal(sql).intValue() == 1){
                return true;
            }
        }
        return false;
    }
}
