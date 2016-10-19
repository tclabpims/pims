package com.pims.dao.hibernate.pimsPathologySample;

import com.alibaba.fastjson.JSONArray;
import com.pims.dao.pimspathologysample.PimsPathologySlideDao;
import com.pims.model.*;
import com.pims.service.pimspathologysample.PimsPathologySlideManager;
import com.smart.Constants;
import com.smart.dao.hibernate.GenericDaoHibernate;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by king on 2016/10/10.
 */
@Repository("pimsPathologySlideDao")
public class PimsPathologySlideDaoHibernate extends GenericDaoHibernate<PimsPathologySlide,Long> implements PimsPathologySlideDao {
    public PimsPathologySlideDaoHibernate(){super(PimsPathologySlide.class);}
    @Autowired
    private PimsPathologySlideManager pimsPathologySlideManager;
    /**
     * 查询切片信息
     * @param code
     * @return
     */
    @Override
    public List getSampleListNoPage(String code) {
        StringBuffer sb = new StringBuffer();
        sb.append(" from PimsPathologySlide,PimsPathologyParaffin,PimsPathologyPieces  where sliparaffinid = paraffinid and  " +
                "paraffinid = pieparaffinid  and paraffinid in ( "+ code + ")");
        return getSession().createQuery(sb.toString()).list();
    }

    public StringBuffer getSearchSql(StringBuffer sb,PimsBaseModel map){
        sb.append("where  sampleid = parsampleid and sampleid = piesampleid and pieparaffinid = paraffinid ");
        if(!StringUtils.isEmpty(map.getReq_sts())){
            sb.append(" and parissectioned= " + map.getReq_sts());
//            if(map.getReq_sts().equals("1")){//已切片
//                if(!StringUtils.isEmpty(map.getSend_hosptail())){//打印状态
//                    sb.append(",PimsPathologySlide where  sampleid = parsampleid and sampleid = piesampleid and pieparaffinid = paraffinid and sliparaffinid = paraffinid");
//                    sb.append(" and sliifprint = " + map.getSend_hosptail());
//                    sb.append(" and parissectioned = " + map.getReq_sts());
//                }else{
//                    sb.append("where  sampleid = parsampleid and sampleid = piesampleid and pieparaffinid = paraffinid ");
//                    sb.append(" and parissectioned= " + map.getReq_sts());
//                }
//            }else{
//                sb.append("where  sampleid = parsampleid and sampleid = piesampleid and pieparaffinid = paraffinid ");
//                sb.append(" and parissectioned= " + map.getReq_sts());
//            }
//        }else{
//            sb.append("where  sampleid = parsampleid and sampleid = piesampleid and pieparaffinid = paraffinid ");
        }
        if(!StringUtils.isEmpty(map.getLogyid())){
            sb.append(" and sampathologyid = " + map.getLogyid());
        }
        if(map.getReq_bf_time() != null){
            sb.append(" and pieembedtime >= :req_bf_time");
        }
        if(!StringUtils.isEmpty(map.getSend_doctor())){
            //sb.append(" and parfirstn = " +  map.getSend_doctor());
        }
        if(!StringUtils.isEmpty(map.getSend_dept())){
            sb.append(" and parpathologycode = " + map.getSend_dept());
        }
        if(!StringUtils.isEmpty(map.getPatient_name())){
            sb.append(" and sampatientname = " + map.getPatient_name());
        }
        if(map.getReq_af_time() != null){
            sb.append(" and  pieembedtime < :req_af_time");
        }
        if(!StringUtils.isEmpty(map.getReq_code())){
            sb.append(" and parnullslidenum  > 0");
        }
        return sb;
    }
    /**
     * 查询蜡块列表
     * @param map
     * @return
     */
    @Override
    public List<PimsPathologyParaffin> getSampleList(PimsBaseModel map) {
        StringBuffer sb = new StringBuffer();
        sb.append(" from PimsPathologyParaffin,PimsPathologySample,PimsPathologyPieces ");
//        if(!StringUtils.isEmpty(map.getReq_sts())){
//            if(map.getReq_sts().equals("1")){//已切片
//                if(!StringUtils.isEmpty(map.getSend_hosptail())){//打印状态
//                    sb.append(",PimsPathologySlide where  sampleid = parsampleid and sampleid = piesampleid and pieparaffinid = paraffinid and sliparaffinid = paraffinid");
//                    sb.append(" and sliifprint = " + map.getSend_hosptail());
//                    sb.append(" and parissectioned = " + map.getReq_sts());
//                }else{
//                    sb.append("where  sampleid = parsampleid and sampleid = piesampleid and pieparaffinid = paraffinid ");
//                    sb.append(" and parissectioned= " + map.getReq_sts());
//                }
//            }else{
//                sb.append("where  sampleid = parsampleid and sampleid = piesampleid and pieparaffinid = paraffinid ");
//                sb.append(" and parissectioned= " + map.getReq_sts());
//            }
//        }else{
//            sb.append("where  sampleid = parsampleid and sampleid = piesampleid and pieparaffinid = paraffinid ");
//        }
        getSearchSql(sb,map);
        String orderby = (map.getSidx()==null|| map.getSidx().trim().equals(""))?"sampathologycode":map.getSidx();
        sb.append(" order by " + orderby + " " +map.getSord());
        System.out.println(sb.toString());
        return pagingList(sb.toString(),map.getStart(),map.getEnd(),map.getReq_bf_time(),map.getReq_af_time());
    }

    /**
     * 查询蜡块数量
     * @param map
     * @return
     */
    @Override
    public int getReqListNum(PimsBaseModel map) {
        StringBuffer sb = new StringBuffer();
        sb.append(" select count(1) from Pims_Pathology_Paraffin,Pims_Pathology_Sample,Pims_Pathology_Pieces ");
//        if(!StringUtils.isEmpty(map.getReq_sts())){
//            if(map.getReq_sts().equals("1")){
//                if(!StringUtils.isEmpty(map.getSend_hosptail())){
//                    sb.append(",Pims_Pathology_Slide where  sampleid = parsampleid and sampleid = piesampleid and pieparaffinid = paraffinid and sliparaffinid = paraffinid");
//                    sb.append(" and sliifprint = " + map.getSend_hosptail());
//                    sb.append(" and parissectioned = " + map.getReq_sts());
//                }else{
//                    sb.append("where  sampleid = parsampleid and sampleid = piesampleid and pieparaffinid = paraffinid ");
//                    sb.append(" and parissectioned= " + map.getReq_sts());
//                }
//            }else{
//                sb.append("where  sampleid = parsampleid and sampleid = piesampleid and pieparaffinid = paraffinid ");
//                sb.append(" and parissectioned= " + map.getReq_sts());
//            }
//        }else{
//            sb.append("where  sampleid = parsampleid and sampleid = piesampleid and pieparaffinid = paraffinid ");
//        }
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
     * 切片或取消片，更新蜡块信息，并更新标本信息
     * @param slideList 切片列表,paraList 蜡块列表,sts 状态,state 逻辑更新标志,sampleList 标本列表
     * @return
     */
    @Override
    public boolean updateSampleSts(JSONArray slideList, JSONArray paraList, JSONArray sampleList, int sts, int state) {
        StringBuffer sb = new StringBuffer();
        if(sts == 3){//切片
            for(int i=0;i<slideList.size();i++){//切片
                Map map = (Map) slideList.get(i);
                PimsPathologySlide slide = (PimsPathologySlide) setBeanProperty(map,PimsPathologySlide.class);
                pimsPathologySlideManager.save(slide);
            }
            for(int i=0;i<paraList.size();i++){//更新蜡块为已切片
                Map map = (Map) paraList.get(i);
                PimsPathologyParaffin para = (PimsPathologyParaffin) setBeanProperty(map,PimsPathologyParaffin.class);
                sb = new StringBuffer();
                sb.append("update pims_pathology_paraffin set parissectioned =  1 ,parsectionedtime = :parsectionedtime"+
                        ",parsectioneddoctor = '"+ para.getParsectioneddoctor()+"' where parissectioned = 0 and  paraffinid = "+para.getParaffinid());
                System.out.println(sb.toString());
                Query query = getSession().createSQLQuery(sb.toString());
                query.setTimestamp("parsectionedtime",para.getParsectionedtime());
                query.executeUpdate();
            }
            if(state == 1){//按照全部完成才更新的原则
                for(int i=0;i<sampleList.size();i++){
                    Map map = (Map) sampleList.get(i);
                    PimsPathologySample sample = (PimsPathologySample) setBeanProperty(map,PimsPathologySample.class);
                    if(sample.getSamsamplestatus() == 2){//标本状态(0已登记,1已取材,2包埋,3已切片,4已初诊,5已审核,6已发送,7会诊中,8报告已打印)
                        sb = new StringBuffer();
                        sb.append("select count(1) from pims_pathology_paraffin where parissectioned = 0 and parsampleid = " + sample.getSampleid());
                        if(countTotal(sb.toString()).intValue() == 0){//全部已切片
                            sb = new StringBuffer();
                            sb.append("update pims_pathology_sample set samsamplestatus = 3 where sampleid = "+ sample.getSampleid());
                            getSession().createSQLQuery(sb.toString()).executeUpdate();
                        }
                    }
                }
            }else{//部分更新就更新的原则
                for(int i=0;i<sampleList.size();i++){
                    Map map = (Map) sampleList.get(i);
                    PimsPathologySample sample = (PimsPathologySample) setBeanProperty(map,PimsPathologySample.class);
                    if(sample.getSamsamplestatus() == 2){//标本状态(0已登记,1已取材,2包埋,3已切片,4已初诊,5已审核,6已发送,7会诊中,8报告已打印)
                        sb = new StringBuffer();
                        sb.append("update pims_pathology_sample set samsamplestatus = 3 where sampleid = "+ sample.getSampleid());
                        getSession().createSQLQuery(sb.toString()).executeUpdate();
                    }
                }
            }
            return true;
        }else if(sts == 2){
            for(int i=0;i<slideList.size();i++){
                Map map = (Map) slideList.get(i);
                PimsPathologySlide  slide = (PimsPathologySlide) setBeanProperty(map,PimsPathologySlide.class);
                if(slide.getSliuseflag() == 1){
                    throw  new RuntimeException("该玻片已被使用无法取消切片！");
                }else{
                    pimsPathologySlideManager.remove(slide);
                }
            }
            for(int i=0;i<paraList.size();i++){//更新蜡块是否切片状态
                Map map = (Map) paraList.get(i);
                PimsPathologyParaffin para = (PimsPathologyParaffin) setBeanProperty(map,PimsPathologyParaffin.class);
                sb = new StringBuffer();
                sb.append("select count(1) from pims_pathology_slide where  sliparaffinid = "+ para.getParaffinid());
                if(countTotal(sb.toString()).intValue() == 0){
                    sb = new StringBuffer();
                    sb.append("update pims_pathology_paraffin set parissectioned =  0 ,parsectionedtime = null"+
                            ",parsectioneddoctor = null where parissectioned = 1 and  paraffinid = "+para.getParaffinid());
                    System.out.println(sb.toString());
                    getSession().createSQLQuery(sb.toString()).executeUpdate();
                }
            }
            if(state == 1){//按照全部完成才更新的原则
                for(int i=0;i<sampleList.size();i++){
                    Map map = (Map) sampleList.get(i);
                    PimsPathologySample sample = (PimsPathologySample) setBeanProperty(map,PimsPathologySample.class);
                    if(sample.getSamsamplestatus() == 3){//标本状态(0已登记,1已取材,2包埋,3已切片,4已初诊,5已审核,6已发送,7会诊中,8报告已打印)
                        sb = new StringBuffer();
                        sb.append("select count(1) from pims_pathology_paraffin where parissectioned = 0 and parsampleid = " + sample.getSampleid());
                        if(countTotal(sb.toString()).intValue() > 0){//部分未切片
                            sb = new StringBuffer();
                            sb.append("update pims_pathology_sample set samsamplestatus = 2 where sampleid = "+ sample.getSampleid());
                            getSession().createSQLQuery(sb.toString()).executeUpdate();
                        }
                    }
                }
            }else{//部分更新就更新的原则
                for(int i=0;i<sampleList.size();i++){
                    Map map = (Map) sampleList.get(i);
                    PimsPathologySample sample = (PimsPathologySample)setBeanProperty(map,PimsPathologySample.class);
                    if(sample.getSamsamplestatus() == 3){//标本状态(0已登记,1已取材,2包埋,3已切片,4已初诊,5已审核,6已发送,7会诊中,8报告已打印)
                        sb = new StringBuffer();
                        sb.append("select count(1) from pims_pathology_paraffin where parissectioned = 1 and parsampleid = " + sample.getSampleid());
                        if(countTotal(sb.toString()).intValue() == 0){//全部未切片
                            sb = new StringBuffer();
                            sb.append("update pims_pathology_sample set samsamplestatus = 2 where sampleid = "+ sample.getSampleid());
                            getSession().createSQLQuery(sb.toString()).executeUpdate();
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
