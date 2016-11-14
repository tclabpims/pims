package com.pims.dao.hibernate.pimsPathologySample;

import com.alibaba.fastjson.JSONArray;
import com.pims.dao.pimspathologysample.PimsPathologySlideDao;
import com.pims.model.*;
import com.pims.service.pimspathologysample.PimsPathologySlideManager;
import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.model.user.User;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
            sb.append(" and parissectioned= " + map.getReq_sts());//切片状态
        }
        if(!StringUtils.isEmpty(map.getLogyid())){
            sb.append(" and sampathologyid = " + map.getLogyid());//病种类别
        }
        if(map.getReq_bf_time() != null){
            sb.append(" and pieembedtime >= :req_bf_time");//开始时间
        }
        if(!StringUtils.isEmpty(map.getSend_doctor())){
            //sb.append(" and parfirstn = " +  map.getSend_doctor());
        }
        if(!StringUtils.isEmpty(map.getSend_dept())){
            sb.append(" and parpathologycode like '%" + map.getSend_dept().toUpperCase()+"%'");//病理编号
        }
        if(!StringUtils.isEmpty(map.getPatient_name())){
            sb.append(" and sampatientname like '%" + map.getPatient_name().toUpperCase()+"%'");//病人姓名
        }
        if(map.getReq_af_time() != null){
            sb.append(" and  pieembedtime < :req_af_time + 1");//结束时间
        }
        if(!StringUtils.isEmpty(map.getReq_code())){
            sb.append(" and parnullslidenum  > 0");//是否预留白片
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
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        StringBuffer sb = new StringBuffer();
        if(sts == 3){//切片
            for(int i=0;i<paraList.size();i++){//更新蜡块为已切片
                Map map = (Map) paraList.get(i);
                PimsPathologyParaffin para = (PimsPathologyParaffin) setBeanProperty(map,PimsPathologyParaffin.class);
                sb = new StringBuffer();
                sb.append(" from PimsPathologyParaffin where paraffinid = " + para.getParaffinid());
                para = (PimsPathologyParaffin)getSession().createQuery(sb.toString()).uniqueResult();
                //切片
                //先切正常片
                PimsPathologySlide slide = new PimsPathologySlide();
                slide.setSlisampleid(para.getParsampleid());//样本号
                slide.setSlicustomerid(user.getHospitalId());//客户代码
                slide.setSlislidebarcode("");//玻片条码号
                slide.setSlipathologycode(para.getParpathologycode());//病理编号
                slide.setSlislidetype(0);//玻片类型(0常规 1白片)
                slide.setSlislidesource(new Long(0));//玻片类型(0来自正常取材 1来自内部医嘱,3直接登记（如Tct）)
                slide.setSliuseflag(new Long(0));//玻片使用状态(0未使用,1已使用)
                slide.setSlislideno("1");//玻片序号
                slide.setSlislidecode(para.getParparaffincode()+"-1");//玻片号
                slide.setSliparaffinid(para.getParaffinid());//蜡块Id
                slide.setSliparaffinno(para.getParparaffinno());//蜡块序号
                slide.setSliparaffincode(para.getParparaffincode());//蜡块编号
                slide.setSliparaffinname(para.getParname());//蜡块名称
                slide.setSlisamplingparts(para.getParpieceparts());//取材部位
                slide.setSliifprint(0);//是否已打印(0未打印,1已打印)
                slide.setSlicreateuser(String.valueOf(user.getId()));//创建用户
                slide.setSlicreatetime(new Date());//创建时间
                pimsPathologySlideManager.save(slide);
                //再切白片
                int bnum = para.getParnullslidenum().intValue();
                if(bnum > 0){
                    for(int j=0;j<bnum;j++){
                        int xh = j+2;
                        slide = new PimsPathologySlide();
                        slide.setSlisampleid(para.getParsampleid());//样本号
                        slide.setSlicustomerid(user.getHospitalId());//客户代码
                        slide.setSlislidebarcode("");//玻片条码号
                        slide.setSlipathologycode(para.getParpathologycode());//病理编号
                        slide.setSlislidetype(1);//玻片类型(0常规 1白片)
                        slide.setSlislidesource(new Long(0));//玻片类型(0来自正常取材 1来自内部医嘱,3直接登记（如Tct）)
                        slide.setSliuseflag(new Long(0));//玻片使用状态(0未使用,1已使用)
                        slide.setSlislideno(String.valueOf(xh));//玻片序号
                        slide.setSlislidecode(para.getParparaffincode()+"-"+xh);//玻片号
                        slide.setSliparaffinid(para.getParaffinid());//蜡块Id
                        slide.setSliparaffinno(para.getParparaffinno());//蜡块序号
                        slide.setSliparaffincode(para.getParparaffincode());//蜡块编号
                        slide.setSliparaffinname(para.getParname());//蜡块名称
                        slide.setSlisamplingparts(para.getParpieceparts());//取材部位
                        slide.setSliifprint(0);//是否已打印(0未打印,1已打印)
                        slide.setSlicreateuser(String.valueOf(user.getId()));//创建用户
                        slide.setSlicreatetime(new Date());//创建时间
                        pimsPathologySlideManager.save(slide);
                    }
                }
                sb = new StringBuffer();
                sb.append("update pims_pathology_paraffin set parissectioned =  1 ,parsectionedtime = :parsectionedtime"+
                        ",parsectioneddoctor = '"+ user.getName()+"' where parissectioned = 0 and  paraffinid = "+para.getParaffinid());
                System.out.println(sb.toString());
                Query query = getSession().createSQLQuery(sb.toString());
                query.setTimestamp("parsectionedtime",new Date());
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
                sb.append(" from PimsPathologyParaffin where paraffinid = " + para.getParaffinid());
                para = (PimsPathologyParaffin)getSession().createQuery(sb.toString()).uniqueResult();//蜡块
                sb = new StringBuffer();
                sb.append(" from PimsPathologySlide where sliparaffinid = " + para.getParaffinid());
                List<PimsPathologySlide> slides = getSession().createQuery(sb.toString()).list();//玻片列表
                if(slides != null && slides.size()>0){
                    for(int j=0;j<slides.size();j++){
                        PimsPathologySlide slide = slides.get(j);
                        if(slide.getSliuseflag() == 1){
                            throw  new RuntimeException("该玻片已被使用无法取消切片！");
                        }else{
                            pimsPathologySlideManager.remove(slide);
                        }
                    }
                }
                sb = new StringBuffer();
                sb.append("select count(1) from pims_pathology_slide where  sliparaffinid = "+ para.getParaffinid());
                if(countTotal(sb.toString()).intValue() == 0){
                    sb = new StringBuffer();
                    sb.append("update pims_pathology_paraffin set parissectioned =  0 ,parsectionedtime = null"+
                            ",parsectioneddoctor = null where parissectioned = 1 and  paraffinid = "+para.getParaffinid());
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

    /**
     * 查询白片的数量
     *
     * @param paraffincode
     * @param sampleId
     * @return
     */
    @Override
    public List<PimsPathologySlide> getWhitePiece(String paraffincode, Long sampleId) {
        String sql = " from PimsPathologySlide where slisampleid=:sampleId and sliparaffincode=:paraffincode and slislidetype=1 and sliuseflag=0";
        Query query = getSession().createQuery(sql);
        query.setParameter("sampleId", sampleId);
        query.setParameter("paraffincode", paraffincode);
        return query.list();
    }

    @Override
    public void updateWhitePieceUsedFlag(String paraffincode, Long sampleId, Long num) {
        String hql = " update PimsPathologySlide set sliuseflag=1 where slisampleid=:sampleId and sliparaffincode=:paraffincode and rownum<=:num";
        Query query = getSession().createQuery(hql);
        query.setParameter("sampleId", sampleId);
        query.setParameter("paraffincode", paraffincode);
        query.setParameter("num", num);
        query.executeUpdate();
    }

    /**
     * 切片或取消片，更新蜡块信息，并更新标本信息
     * @param slideList 切片列表,paraList 蜡块列表,sts 状态,state 逻辑更新标志,sampleList 标本列表
     * @return
     */
//    @Override
//    public boolean updateSampleSts(JSONArray slideList, JSONArray paraList, JSONArray sampleList, int sts, int state) {
//        StringBuffer sb = new StringBuffer();
//        if(sts == 3){//切片
//            for(int i=0;i<slideList.size();i++){//切片
//                Map map = (Map) slideList.get(i);
//                PimsPathologySlide slide = (PimsPathologySlide) setBeanProperty(map,PimsPathologySlide.class);
//                pimsPathologySlideManager.save(slide);
//            }
//            for(int i=0;i<paraList.size();i++){//更新蜡块为已切片
//                Map map = (Map) paraList.get(i);
//                PimsPathologyParaffin para = (PimsPathologyParaffin) setBeanProperty(map,PimsPathologyParaffin.class);
//                sb = new StringBuffer();
//                sb.append("update pims_pathology_paraffin set parissectioned =  1 ,parsectionedtime = :parsectionedtime"+
//                        ",parsectioneddoctor = '"+ para.getParsectioneddoctor()+"' where parissectioned = 0 and  paraffinid = "+para.getParaffinid());
//                System.out.println(sb.toString());
//                Query query = getSession().createSQLQuery(sb.toString());
//                query.setTimestamp("parsectionedtime",para.getParsectionedtime());
//                query.executeUpdate();
//            }
//            if(state == 1){//按照全部完成才更新的原则
//                for(int i=0;i<sampleList.size();i++){
//                    Map map = (Map) sampleList.get(i);
//                    PimsPathologySample sample = (PimsPathologySample) setBeanProperty(map,PimsPathologySample.class);
//                    if(sample.getSamsamplestatus() == 2){//标本状态(0已登记,1已取材,2包埋,3已切片,4已初诊,5已审核,6已发送,7会诊中,8报告已打印)
//                        sb = new StringBuffer();
//                        sb.append("select count(1) from pims_pathology_paraffin where parissectioned = 0 and parsampleid = " + sample.getSampleid());
//                        if(countTotal(sb.toString()).intValue() == 0){//全部已切片
//                            sb = new StringBuffer();
//                            sb.append("update pims_pathology_sample set samsamplestatus = 3 where sampleid = "+ sample.getSampleid());
//                            getSession().createSQLQuery(sb.toString()).executeUpdate();
//                        }
//                    }
//                }
//            }else{//部分更新就更新的原则
//                for(int i=0;i<sampleList.size();i++){
//                    Map map = (Map) sampleList.get(i);
//                    PimsPathologySample sample = (PimsPathologySample) setBeanProperty(map,PimsPathologySample.class);
//                    if(sample.getSamsamplestatus() == 2){//标本状态(0已登记,1已取材,2包埋,3已切片,4已初诊,5已审核,6已发送,7会诊中,8报告已打印)
//                        sb = new StringBuffer();
//                        sb.append("update pims_pathology_sample set samsamplestatus = 3 where sampleid = "+ sample.getSampleid());
//                        getSession().createSQLQuery(sb.toString()).executeUpdate();
//                    }
//                }
//            }
//            return true;
//        }else if(sts == 2){
//            for(int i=0;i<slideList.size();i++){
//                Map map = (Map) slideList.get(i);
//                PimsPathologySlide  slide = (PimsPathologySlide) setBeanProperty(map,PimsPathologySlide.class);
//                if(slide.getSliuseflag() == 1){
//                    throw  new RuntimeException("该玻片已被使用无法取消切片！");
//                }else{
//                    pimsPathologySlideManager.remove(slide);
//                }
//            }
//            for(int i=0;i<paraList.size();i++){//更新蜡块是否切片状态
//                Map map = (Map) paraList.get(i);
//                PimsPathologyParaffin para = (PimsPathologyParaffin) setBeanProperty(map,PimsPathologyParaffin.class);
//                sb = new StringBuffer();
//                sb.append("select count(1) from pims_pathology_slide where  sliparaffinid = "+ para.getParaffinid());
//                if(countTotal(sb.toString()).intValue() == 0){
//                    sb = new StringBuffer();
//                    sb.append("update pims_pathology_paraffin set parissectioned =  0 ,parsectionedtime = null"+
//                            ",parsectioneddoctor = null where parissectioned = 1 and  paraffinid = "+para.getParaffinid());
//                    System.out.println(sb.toString());
//                    getSession().createSQLQuery(sb.toString()).executeUpdate();
//                }
//            }
//            if(state == 1){//按照全部完成才更新的原则
//                for(int i=0;i<sampleList.size();i++){
//                    Map map = (Map) sampleList.get(i);
//                    PimsPathologySample sample = (PimsPathologySample) setBeanProperty(map,PimsPathologySample.class);
//                    if(sample.getSamsamplestatus() == 3){//标本状态(0已登记,1已取材,2包埋,3已切片,4已初诊,5已审核,6已发送,7会诊中,8报告已打印)
//                        sb = new StringBuffer();
//                        sb.append("select count(1) from pims_pathology_paraffin where parissectioned = 0 and parsampleid = " + sample.getSampleid());
//                        if(countTotal(sb.toString()).intValue() > 0){//部分未切片
//                            sb = new StringBuffer();
//                            sb.append("update pims_pathology_sample set samsamplestatus = 2 where sampleid = "+ sample.getSampleid());
//                            getSession().createSQLQuery(sb.toString()).executeUpdate();
//                        }
//                    }
//                }
//            }else{//部分更新就更新的原则
//                for(int i=0;i<sampleList.size();i++){
//                    Map map = (Map) sampleList.get(i);
//                    PimsPathologySample sample = (PimsPathologySample)setBeanProperty(map,PimsPathologySample.class);
//                    if(sample.getSamsamplestatus() == 3){//标本状态(0已登记,1已取材,2包埋,3已切片,4已初诊,5已审核,6已发送,7会诊中,8报告已打印)
//                        sb = new StringBuffer();
//                        sb.append("select count(1) from pims_pathology_paraffin where parissectioned = 1 and parsampleid = " + sample.getSampleid());
//                        if(countTotal(sb.toString()).intValue() == 0){//全部未切片
//                            sb = new StringBuffer();
//                            sb.append("update pims_pathology_sample set samsamplestatus = 2 where sampleid = "+ sample.getSampleid());
//                            getSession().createSQLQuery(sb.toString()).executeUpdate();
//                        }
//                    }
//                }
//            }
//            return true;
//        }
//        return false;
//    }
}
