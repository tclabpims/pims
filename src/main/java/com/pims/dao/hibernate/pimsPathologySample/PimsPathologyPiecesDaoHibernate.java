package com.pims.dao.hibernate.pimsPathologySample;

import com.alibaba.fastjson.JSONArray;
import com.pims.dao.pimspathologysample.PimsPathologyPiecesDao;
import com.pims.dao.pimspathologysample.PimsPathologySampleDao;
import com.pims.model.*;
import com.pims.service.pimspathologysample.PimsPathologyPiecesManager;
import com.smart.dao.hibernate.GenericDaoHibernate;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by king on 2016/10/10.
 */
@Repository("pimsPathologyPiecesDao")
public class PimsPathologyPiecesDaoHibernate extends GenericDaoHibernate<PimsPathologyPieces,Long> implements PimsPathologyPiecesDao {
    public PimsPathologyPiecesDaoHibernate(){super(PimsPathologyPieces.class);}
    @Autowired
    private PimsPathologyPiecesManager pimsPathologyPiecesManager;
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
     * 组装查询语句
     * @param sb
     * @param map
     * @return
     */
    public StringBuffer getSearchSql(StringBuffer sb,PimsBaseModel map){
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
        sb.append(" select count(1) from pims_pathology_sample where samisdeleted = 0 ");
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
            sb.append("update pims_pathology_sample set samisdecacified = "+map.getSamisdecacified()+",samissamplingall="+map.getSamissamplingall()+
                    ", samsamplestatus = "+ sts +"  where sampleid = "+map.getSampleid());
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
                    " b.pieisembed = 0 and a.pieceid = "+ id;
            if(countTotal(sql).intValue() == 1){
                return true;
            }
        }
        return false;
    }

    /**
     * 更新标本信息及材块信息
     * @param piecesList 材块列表,sample 标本信息,sts 状态,state 逻辑更新标志
     * @return
     */
    @Override
    public boolean updateSampleSts(JSONArray piecesList, PimsPathologySample sample, int sts, int state) {
        StringBuffer sb = new StringBuffer();
        //查询该标本的材块信息
        List<PimsPathologyPieces> list = getSampleListNoPage(String.valueOf(sample.getSampleid()));
        List list1 = new ArrayList();
        List list2 = new ArrayList();
        if(sts == 1){//取材
            //更新标本信息
            updateSampleSts(sample,sts);
            for(int i = 0;i<piecesList.size();i++){//取材
                Map map = (Map) piecesList.get(i);
                PimsPathologyPieces piece = (PimsPathologyPieces) setBeanProperty(map,PimsPathologyPieces.class);
                if(piece.getPiestate().longValue() == 0){//未取材
                    piece.setPiestate((long) 1);
                }
                piece = pimsPathologyPiecesManager.save(piece);
                list1.add(piece.getPieceid());
            }
            return true;
        }else if(sts == 0){//保存
            for(int i=0;i<piecesList.size();i++){
                Map map = (Map) piecesList.get(i);
                PimsPathologyPieces  piece = (PimsPathologyPieces) setBeanProperty(map,PimsPathologyPieces.class);
                if (!list2.contains(piece.getPiestate().toString())) {
                    list2.add(piece.getPiestate().toString());
                }
                piece = pimsPathologyPiecesManager.save(piece);
                list1.add(piece.getPieceid());
            }
            if(state==1){//完全更新才更新的原则
                if(list2.contains("0")){
                    pimsPathologyPiecesManager.updateSampleSts(sample,0);
                }else{
                    pimsPathologyPiecesManager.updateSampleSts(sample,1);
                }
            }else{//部分更新就更新的原则
                if(list2.contains("1")){
                    pimsPathologyPiecesManager.updateSampleSts(sample,1);
                }else{
                    pimsPathologyPiecesManager.updateSampleSts(sample,0);
                }
            }
            return true;
        }
        //删除不存在的材块
        for(PimsPathologyPieces pps : list){
            if(!list1.contains(pps.getPieceid())){
                boolean result = canChange(pps.getPieceid(),"2");//判断单据是否可以被删除
                if(result){
                    delete(pps.getPieceid());
                }else{
                    throw  new RuntimeException("该材块已被包埋，无法被删除");
                }
            }
        }
        return false;
    }
}
