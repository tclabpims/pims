package com.pims.dao.hibernate.pimsPathologySample;

import com.pims.dao.pimspathologysample.PimsPathologyPiecesDao;
import com.pims.dao.pimspathologysample.PimsPathologySampleDao;
import com.pims.model.PimsBaseModel;
import com.pims.model.PimsPathologyPieces;
import com.pims.model.PimsPathologySample;
import com.smart.dao.hibernate.GenericDaoHibernate;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by king on 2016/10/10.
 */
@Repository("pimsPathologyPiecesDao")
public class PimsPathologyPiecesDaoHibernate extends GenericDaoHibernate<PimsPathologyPieces,Long> implements PimsPathologyPiecesDao {
    public PimsPathologyPiecesDaoHibernate(){super(PimsPathologyPieces.class);}
    /**
     * 查询材块列表不分页
     * @param code
     * @return
     */
    @Override
    public List<PimsPathologyPieces> getSampleListNoPage(String code) {
        StringBuffer sb = new StringBuffer();
        sb.append(" from PimsPathologyPieces where piesampleid = "+ code);
        return getSession().createQuery(sb.toString()).list();
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
        if(!StringUtils.isEmpty(map.getLogyid())){
            sb.append(" and sampathologyid = " + map.getLogyid());
        }
        if(map.getReq_bf_time() != null){
            sb.append(" and samregisttime >= :req_bf_time");
        }
        if(!StringUtils.isEmpty(map.getReq_sts())){
            if(map.getReq_sts().equals("1")){
                sb.append(" and samsamplestatus = " + map.getReq_sts());
            }else{
                sb.append(" and samsamplestatus =  0");
            }
        }
        if(!StringUtils.isEmpty(map.getSend_doctor())){
            //sb.append(" and samsenddoctorid = " +  map.getSend_doctor());
        }
        if(!StringUtils.isEmpty(map.getSend_dept())){
            sb.append(" and sampathologycode = " + map.getSend_dept());
        }
        if(!StringUtils.isEmpty(map.getSend_hosptail())){
            //sb.append(" and samsendhospital = " + map.getSend_hosptail());
        }
        if(!StringUtils.isEmpty(map.getPatient_name())){
            sb.append(" and sampatientname = " + map.getPatient_name());
        }
        if(map.getReq_af_time() != null){
            sb.append(" and  samregisttime < :req_af_time");
        }
        if(!StringUtils.isEmpty(map.getReq_code())){
            //sb.append(" and saminspectionid = " + map.getReq_code());
        }
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
        sb.append(" select count(1) from pims_pathology_sample where samisdeleted = 0 ");
        if(!StringUtils.isEmpty(map.getLogyid())){
            sb.append(" and sampathologyid = " + map.getLogyid());
        }
        if(map.getReq_bf_time() != null){
            sb.append(" and samregisttime >= :req_bf_time");
        }
        if(!StringUtils.isEmpty(map.getReq_sts())){
            if(map.getReq_sts().equals("1")){
                sb.append(" and samsamplestatus = " + map.getReq_sts());
            }else{
                sb.append(" and samsamplestatus =  0");
            }
        }
        if(!StringUtils.isEmpty(map.getSend_doctor())){
            //sb.append(" and samsenddoctorid = " +  map.getSend_doctor());
        }
        if(!StringUtils.isEmpty(map.getSend_dept())){
            sb.append(" and sampathologycode = " + map.getSend_dept());
        }
        if(!StringUtils.isEmpty(map.getSend_hosptail())){
            //sb.append(" and samsendhospital = " + map.getSend_hosptail());
        }
        if(!StringUtils.isEmpty(map.getPatient_name())){
            sb.append(" and sampatientname = " + map.getPatient_name());
        }
        if(map.getReq_af_time() != null){
            sb.append(" and  samregisttime < :req_af_time");
        }
        if(!StringUtils.isEmpty(map.getReq_code())){
            //sb.append(" and saminspectionid = " + map.getReq_code());
        }
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
     * 更新标本信息
     * @param map
     * @return
     */
    @Override
    public boolean updateSample(PimsPathologySample map) {
        if(map == null || StringUtils.isEmpty(String.valueOf(map.getSampleid()))){
            return false;
        }else{
            StringBuffer sb = new StringBuffer();
            long samissamplingall = StringUtils.isEmpty(String.valueOf(map.getSamissamplingall()))?0:map.getSamissamplingall();
            long samisdecacified = StringUtils.isEmpty(String.valueOf(map.getSamisdecacified()))?0:map.getSamisdecacified();
            sb.append("update pims_pathology_sample set samsamplestatus = 1, samissamplingall = "+ samissamplingall + ", samisdecacified = " +
                    samisdecacified + "  where sampleid = "+map.getSampleid());
            getSession().createSQLQuery(sb.toString()).executeUpdate();
            return true;
        }
    }
    /**
     * 更新标本信息
     * @param map,sts
     * @return
     */
    @Override
    public boolean updateSampleSts(PimsPathologySample map,int sts) {
        if(map == null || StringUtils.isEmpty(String.valueOf(map.getSampleid()))){
            return false;
        }else{
            StringBuffer sb = new StringBuffer();
            sb.append("update pims_pathology_sample set samsamplestatus = "+ sts +"  where sampleid = "+map.getSampleid());
            getSession().createSQLQuery(sb.toString()).executeUpdate();
            return true;
        }
    }
    /**
     * 删除材块单
     * @param id
     * @return
     */
    @Override
    public boolean delete(Long id) {
        if(id == null){
            return false;
        }else{
            Query query = getSession().createSQLQuery(" delete from  pims_pathology_pieces where pieceid = "+ id);
            query.executeUpdate();
            return true;
        }
    }
    /**
     * 查询材块单据是否可以被修改或删除
     * @param id
     * @return
     */
    @Override
    public boolean canChange(Long id,String sts) {
        if(id == null || StringUtils.isEmpty(sts)){
            return false;
        }else if(sts.equals("1")){
            String sql =  "select count(1) from pims_pathology_pieces a,pims_pathology_sample b where b.sampleid = a.piesampleid and " +
                    "b.samsamplestatus < 5 and a.pieceid = "+ id;
            if(countTotal(sql).intValue() == 1){
                return true;
            }
        }else if(sts.equals("2")){
            String sql =  "select count(1) from pims_pathology_pieces a,pims_pathology_sample b where b.sampleid = a.piesampleid and " +
                    " b.samsamplestatus < 2 and a.pieceid = "+ id;
            if(countTotal(sql).intValue() == 1){
                return true;
            }
        }
        return false;
    }
}
