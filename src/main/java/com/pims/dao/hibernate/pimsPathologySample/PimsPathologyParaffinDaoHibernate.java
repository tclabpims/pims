package com.pims.dao.hibernate.pimsPathologySample;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pims.dao.pimspathologysample.PimsPathologyParaffinDao;
import com.pims.model.*;
import com.pims.service.pimspathologysample.PimsPathologyParaffinManager;
import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.model.user.User;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by king on 2016/10/10.
 */
@Repository("pimsPathologyParaffinDao")
public class PimsPathologyParaffinDaoHibernate extends GenericDaoHibernate<PimsPathologyParaffin,Long> implements PimsPathologyParaffinDao {
    public PimsPathologyParaffinDaoHibernate(){super(PimsPathologyParaffin.class);}
    @Autowired
    private PimsPathologyParaffinManager pimsPathologyParaffinManager;
    /**
     * 查询包埋信息
     * @param code
     * @return
     */
    @Override
    public List getSampleListNoPage(String code) {
        StringBuffer sb = new StringBuffer();
        sb.append(" from PimsPathologyPieces , PimsPathologyParaffin  where pieparaffinid = paraffinid and pieceid in ( "+ code + ")");
        return getSession().createQuery(sb.toString()).list();
    }

    /**
     * 组装sql
     * @param sb
     * @param map
     * @return
     */
    public StringBuffer getSearchSql(StringBuffer sb,PimsBaseModel map){
        if(!StringUtils.isEmpty(map.getLogyid())){
            sb.append(" and sampathologyid = " + map.getLogyid());//病种类别
        }
        if(map.getReq_bf_time() != null){
            sb.append(" and piesamplingtime >= :req_bf_time");//开始时间
        }
        if(!StringUtils.isEmpty(map.getReq_sts())){
            sb.append(" and pieisembed = " + map.getReq_sts());//包埋状态
        }
        if(!StringUtils.isEmpty(map.getSend_hosptail())){
            //sb.append(" and samsenddoctorid = " +  map.getSend_doctor());
        }
        if(!StringUtils.isEmpty(map.getSend_dept())){
            sb.append(" and piepathologycode like '%" +  map.getSend_dept().trim()+"%'");//病理编号
        }
        if(!StringUtils.isEmpty(map.getSend_doctor())){
            sb.append(" and piefirstn is not null");//内部医嘱
        }
        if(!StringUtils.isEmpty(map.getPatient_name())){
            sb.append(" and sampatientname like '%" + map.getPatient_name().trim()+"%'");//病人姓名
        }
        if(map.getReq_af_time() != null){
            sb.append(" and  piesamplingtime < :req_af_time");//结束时间
        }
        if(!StringUtils.isEmpty(map.getReq_code())){
            //sb.append(" and saminspectionid = " + map.getReq_code());
        }
        return sb;
    }
    /**
     * 查询材块列表
     * @param map
     * @return
     */
    @Override
    public List getSampleList(PimsBaseModel map) {
        StringBuffer sb = new StringBuffer();
        sb.append(" from PimsPathologyPieces,PimsPathologySample where piesampleid = sampleid and piestate > 0 ");
        getSearchSql(sb,map);
//        if(!StringUtils.isEmpty(map.getReq_sts()) && map.getReq_sts().equals("1")){
//            sb.append(" and exists (select 1 from PimsPathologyParaffin where pieparaffinid = paraffinid and parissectioned = 0) ");
//        }
        String orderby = (map.getSidx()==null|| map.getSidx().trim().equals(""))?"piecode":map.getSidx();
        sb.append(" order by  length(piecode) asc ," + orderby + " " +map.getSord());
        return pagingList(sb.toString(),map.getStart(),map.getEnd(),map.getReq_bf_time(),map.getReq_af_time());
    }

    /**
     * 查询材块数量
     * @param map
     * @return
     */
    @Override
    public int getReqListNum(PimsBaseModel map) {
        StringBuffer sb = new StringBuffer();
        sb.append(" select count(1) from pims_pathology_pieces,pims_pathology_sample where piesampleid = sampleid and piestate > 0  ");
        getSearchSql(sb,map);
//        if(!StringUtils.isEmpty(map.getReq_sts()) && map.getReq_sts().equals("1")){
//            sb.append(" and exists (select 1 from Pims_Pathology_Paraffin where pieparaffinid = paraffinid and parissectioned = 0) ");
//        }
        return countTotal(sb.toString(),map.getReq_bf_time(),map.getReq_af_time()).intValue();
    }

    /**
     * 查询标本内容
     * @param id
     * @return
     */
    @Override
    public PimsPathologySample getBySampleNo(Long id) {
        StringBuffer sb = new StringBuffer();
        sb.append("from PimsPathologySample where sampleid = "+ id);
        Query query = getSession().createQuery(sb.toString());
        if(query.list() == null || query.list().size() != 1){
            return null;
        }else{
            return (PimsPathologySample)query.list().get(0);
        }
    }
    /**
     * 包埋或取消包埋，更新材块信息，并更新标本信息
     * @param slideList 蜡块列表,paraList 材块列表,sts 状态,state 逻辑更新标志,sampleList 标本列表
     * @return
     */
    @Override
    public JSONObject updateSampleSts(JSONArray slideList, JSONArray paraList, JSONArray sampleList, int sts, int state) {
        boolean result = true;
        JSONObject o = new JSONObject();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        StringBuffer sb = new StringBuffer();
        if(sts == 2){//包埋
            for(int i=0;i<paraList.size();i++){//包埋、更新材块为已包埋
                Map map = (Map) paraList.get(i);
                PimsPathologyPieces piece = (PimsPathologyPieces) setBeanProperty(map,PimsPathologyPieces.class);
                sb = new StringBuffer();
                sb.append("from PimsPathologyPieces where pieceid = "+piece.getPieceid());
                piece = (PimsPathologyPieces) getSession().createQuery(sb.toString()).uniqueResult();
                if(piece.getPieisembed().equals("1")){
                    result = false;
                    o.put("message", "该材块已包埋无法重复包埋！");
                    o.put("success", false);
                    break;
                }else{
                    PimsPathologyParaffin para = new PimsPathologyParaffin();
                    para.setParsampleid(piece.getPiesampleid());//样本号
                    para.setParpathologycode(piece.getPiepathologycode());//病理编号
                    para.setParname(piece.getPiecode());//蜡块名称
                    para.setParparaffinno(Long.parseLong(piece.getPiesamplingno()));//蜡块序号
                    para.setParparaffincode(piece.getPiecode());//蜡块标签
                    para.setParbarcode(piece.getPiecode());//蜡块条码号
                    para.setParpiececount(piece.getPiecounts().intValue());//材块块数
                    para.setParpieceids(String.valueOf(piece.getPieceid()));//材块Ids(多个之间用逗号隔开)
                    para.setParnullslidenum(piece.getPienullslidenum());//取白片数量
                    para.setParpieceparts(piece.getPieparts());//取材部位(多个之间用逗号隔开)
                    para.setParissectioned(0);//是否已切片(0未切片，1已切片)
                    para.setParisprintlabel(0);//是否已打印标签
                    para.setParcreatetime(new Date());
                    para.setParcreateuse(String.valueOf(user.getId()));
                    para = pimsPathologyParaffinManager.save(para);
                    sb = new StringBuffer();
                    sb.append("update pims_pathology_pieces set piestate = 2, pieisembed =  1 ,pieembedtime = :pieembedtime ,pieembeddoctorid= :pieembeddoctorid"+
                            ",pieembeddoctorname = :pieembeddoctorname,pieparaffinid=:pieparaffinid where pieisembed = 0 and  pieceid = "+piece.getPieceid());
                    Query query = getSession().createSQLQuery(sb.toString());
                    query.setTimestamp("pieembedtime",new Date());
                    query.setString("pieembeddoctorid", String.valueOf(user.getId()));
                    query.setString("pieembeddoctorname",user.getName());
                    query.setString("pieparaffinid", String.valueOf(para.getParaffinid()));
                    query.executeUpdate();
                }
            }
            if(state == 1){//按照全部完成才更新的原则
                for(int i=0;i<sampleList.size();i++){
                    Map map = (Map) sampleList.get(i);
                    PimsPathologySample sample = (PimsPathologySample) setBeanProperty(map,PimsPathologySample.class);
                    if(sample.getSamsamplestatus() == 1){//标本状态(0已登记,1已取材,2包埋,3已切片,4已初诊,5已审核,6已发送,7会诊中,8报告已打印)
                        sb = new StringBuffer();
                        sb.append("select count(1) from pims_pathology_pieces where pieisembed = 0 and piesampleid = " + sample.getSampleid());
                        if(countTotal(sb.toString()).intValue() == 0){//全部已包埋
                            sb = new StringBuffer();
                            sb.append("update pims_pathology_sample set samsamplestatus = 2 where sampleid = "+ sample.getSampleid());
                            getSession().createSQLQuery(sb.toString()).executeUpdate();
                        }
                    }
                }
            }else{//部分更新就更新的原则
                for(int i=0;i<sampleList.size();i++){
                    Map map = (Map) sampleList.get(i);
                    PimsPathologySample sample = (PimsPathologySample) setBeanProperty(map,PimsPathologySample.class);
                    if(sample.getSamsamplestatus() == 1){//标本状态(0已登记,1已取材,2包埋,3已切片,4已初诊,5已审核,6已发送,7会诊中,8报告已打印)
                        sb = new StringBuffer();
                        sb.append("update pims_pathology_sample set samsamplestatus = 2 where sampleid = "+ sample.getSampleid());
                        getSession().createSQLQuery(sb.toString()).executeUpdate();
                    }
                }
            }
        }else if(sts == 1){//取消包埋
            for(int i=0;i<paraList.size();i++){//取消包埋、更新材块为未包埋
                Map map = (Map) paraList.get(i);
                PimsPathologyPieces piece = (PimsPathologyPieces) setBeanProperty(map,PimsPathologyPieces.class);
                sb = new StringBuffer();
                sb.append("from PimsPathologyPieces where pieceid = "+piece.getPieceid());
                piece = (PimsPathologyPieces) getSession().createQuery(sb.toString()).uniqueResult();
                if(!StringUtils.isEmpty(piece.getPieparaffinid())){
                    sb = new StringBuffer();
                    sb.append("from PimsPathologyParaffin where paraffinid = "+piece.getPieparaffinid());
                    PimsPathologyParaffin  para = (PimsPathologyParaffin) getSession().createQuery(sb.toString()).uniqueResult();
                    if(para.getParissectioned() == 1){
                        //throw  new RuntimeException("该蜡块已切片无法取消包埋！");
                        o.put("message", "该蜡块已切片无法取消包埋！");
                        o.put("success", false);
                        result = false;
                        break;
                    }else{
                        pimsPathologyParaffinManager.remove(para);
                    }
                    sb = new StringBuffer();
                    sb.append("update pims_pathology_pieces set piestate = 1, pieisembed =  0 ,pieembedtime = null"+
                            ",pieembeddoctorid = null,pieembeddoctorname=null,pieparaffinid=null where pieisembed = 1 and  pieceid = "+piece.getPieceid());
                    getSession().createSQLQuery(sb.toString()).executeUpdate();
                }
            }
            if(state == 1){//按照全部完成才更新的原则
                for(int i=0;i<sampleList.size();i++){
                    Map map = (Map) sampleList.get(i);
                    PimsPathologySample sample = (PimsPathologySample) setBeanProperty(map,PimsPathologySample.class);
                    if(sample.getSamsamplestatus() == 2){//标本状态(0已登记,1已取材,2包埋,3已切片,4已初诊,5已审核,6已发送,7会诊中,8报告已打印)
                        sb = new StringBuffer();
                        sb.append("update pims_pathology_sample set samsamplestatus = 1 where sampleid = "+ sample.getSampleid());
                        getSession().createSQLQuery(sb.toString()).executeUpdate();
                    }
                }
            }else{//部分更新就更新的原则
                for(int i=0;i<sampleList.size();i++){
                    Map map = (Map) sampleList.get(i);
                    PimsPathologySample sample = (PimsPathologySample)setBeanProperty(map,PimsPathologySample.class);
                    if(sample.getSamsamplestatus() == 2){//标本状态(0已登记,1已取材,2包埋,3已切片,4已初诊,5已审核,6已发送,7会诊中,8报告已打印)
                        sb = new StringBuffer();
                        sb.append("select count(1) from pims_pathology_paraffin where parsampleid = " + sample.getSampleid());
                        if(countTotal(sb.toString()).intValue() == 0){//全部未包埋
                            sb = new StringBuffer();
                            sb.append("update pims_pathology_sample set samsamplestatus = 1 where sampleid = "+ sample.getSampleid());
                            getSession().createSQLQuery(sb.toString()).executeUpdate();
                        }
                    }
                }
            }
        }
        if(result){
            o.put("message", "操作成功！");
            o.put("success", true);
        }
        return o;
    }
    /**
     * 包埋或取消包埋，更新材块信息，并更新标本信息
     * @param slideList 蜡块列表,paraList 材块列表,sts 状态,state 逻辑更新标志,sampleList 标本列表
     * @return
     */
//    @Override
//    public boolean updateSampleSts(JSONArray slideList, JSONArray paraList, JSONArray sampleList, int sts, int state) {
//        StringBuffer sb = new StringBuffer();
//        if(sts == 2){//包埋
//            for(int i=0;i<slideList.size();i++){//包埋
//                Map map = (Map) slideList.get(i);
//                PimsPathologyParaffin para = (PimsPathologyParaffin) setBeanProperty(map,PimsPathologyParaffin.class);
//                pimsPathologyParaffinManager.save(para);
//            }
//            for(int i=0;i<paraList.size();i++){//更新材块为已包埋
//                Map map = (Map) paraList.get(i);
//                PimsPathologyPieces piece = (PimsPathologyPieces) setBeanProperty(map,PimsPathologyPieces.class);
//                sb = new StringBuffer();
//                sb.append("from PimsPathologyParaffin where parpieceids = "+piece.getPieceid());
//                PimsPathologyParaffin para = (PimsPathologyParaffin) (getSession().createQuery(sb.toString()).list().get(0));
//                sb = new StringBuffer();
//                sb.append("update pims_pathology_pieces set piestate = 2, pieisembed =  1 ,pieembedtime = :pieembedtime ,pieembeddoctorid='"+piece.getPieembeddoctorid()+
//                        "',pieembeddoctorname = '"+ piece.getPieembeddoctorname()+ "',pieparaffinid='"+ para.getParaffinid()+
//                        "' where pieisembed = 0 and  pieceid = "+piece.getPieceid());
//                System.out.println(sb.toString());
//                Query query = getSession().createSQLQuery(sb.toString());
//                query.setTimestamp("pieembedtime",piece.getPieembedtime());
//                query.executeUpdate();
//            }
//            if(state == 1){//按照全部完成才更新的原则
//                for(int i=0;i<sampleList.size();i++){
//                    Map map = (Map) sampleList.get(i);
//                    PimsPathologySample sample = (PimsPathologySample) setBeanProperty(map,PimsPathologySample.class);
//                    if(sample.getSamsamplestatus() == 1){//标本状态(0已登记,1已取材,2包埋,3已切片,4已初诊,5已审核,6已发送,7会诊中,8报告已打印)
//                        sb = new StringBuffer();
//                        sb.append("select count(1) from pims_pathology_pieces where pieisembed = 0 and piesampleid = " + sample.getSampleid());
//                        if(countTotal(sb.toString()).intValue() == 0){//全部已包埋
//                            sb = new StringBuffer();
//                            sb.append("update pims_pathology_sample set samsamplestatus = 2 where sampleid = "+ sample.getSampleid());
//                            getSession().createSQLQuery(sb.toString()).executeUpdate();
//                        }
//                    }
//                }
//            }else{//部分更新就更新的原则
//                for(int i=0;i<sampleList.size();i++){
//                    Map map = (Map) sampleList.get(i);
//                    PimsPathologySample sample = (PimsPathologySample) setBeanProperty(map,PimsPathologySample.class);
//                    if(sample.getSamsamplestatus() == 1){//标本状态(0已登记,1已取材,2包埋,3已切片,4已初诊,5已审核,6已发送,7会诊中,8报告已打印)
//                        sb = new StringBuffer();
//                        sb.append("update pims_pathology_sample set samsamplestatus = 2 where sampleid = "+ sample.getSampleid());
//                        getSession().createSQLQuery(sb.toString()).executeUpdate();
//                    }
//                }
//            }
//            return true;
//        }else if(sts == 1){//取消包埋
//            for(int i=0;i<slideList.size();i++){
//                Map map = (Map) slideList.get(i);
//                PimsPathologyParaffin  para = (PimsPathologyParaffin) setBeanProperty(map,PimsPathologyParaffin.class);
//                if(para.getParissectioned() == 1){
//                    throw  new RuntimeException("该蜡块已切片无法取消包埋！");
//                }else{
//                    pimsPathologyParaffinManager.remove(para);
//                }
//            }
//            for(int i=0;i<paraList.size();i++){//更新材块为未包埋
//                Map map = (Map) paraList.get(i);
//                PimsPathologyPieces piece = (PimsPathologyPieces) setBeanProperty(map,PimsPathologyPieces.class);
//                sb = new StringBuffer();
//                sb.append("update pims_pathology_pieces set piestate = 1, pieisembed =  0 ,pieembedtime = null"+
//                        ",pieembeddoctorid = null,pieembeddoctorname=null,pieparaffinid=null where pieisembed = 1 and  pieceid = "+piece.getPieceid());
//                System.out.println(sb.toString());
//                getSession().createSQLQuery(sb.toString()).executeUpdate();
//            }
//            if(state == 1){//按照全部完成才更新的原则
//                for(int i=0;i<sampleList.size();i++){
//                    Map map = (Map) sampleList.get(i);
//                    PimsPathologySample sample = (PimsPathologySample) setBeanProperty(map,PimsPathologySample.class);
//                    if(sample.getSamsamplestatus() == 2){//标本状态(0已登记,1已取材,2包埋,3已切片,4已初诊,5已审核,6已发送,7会诊中,8报告已打印)
//                        sb = new StringBuffer();
//                        sb.append("update pims_pathology_sample set samsamplestatus = 1 where sampleid = "+ sample.getSampleid());
//                        getSession().createSQLQuery(sb.toString()).executeUpdate();
//                    }
//                }
//            }else{//部分更新就更新的原则
//                for(int i=0;i<sampleList.size();i++){
//                    Map map = (Map) sampleList.get(i);
//                    PimsPathologySample sample = (PimsPathologySample)setBeanProperty(map,PimsPathologySample.class);
//                    if(sample.getSamsamplestatus() == 2){//标本状态(0已登记,1已取材,2包埋,3已切片,4已初诊,5已审核,6已发送,7会诊中,8报告已打印)
//                        sb = new StringBuffer();
//                        sb.append("select count(1) from pims_pathology_paraffin where parsampleid = " + sample.getSampleid());
//                        if(countTotal(sb.toString()).intValue() == 0){//全部未包埋
//                            sb = new StringBuffer();
//                            sb.append("update pims_pathology_sample set samsamplestatus = 1 where sampleid = "+ sample.getSampleid());
//                            getSession().createSQLQuery(sb.toString()).executeUpdate();
//                        }
//                    }
//                }
//            }
//            return true;
//        }
//        return false;
//    }
    /**
     * 查询材块单据是否可以包埋或取消包埋
     * @param id
     * @return
     */
    @Override
    public boolean canChange(Long id,String sts) {
        if(id == null || StringUtils.isEmpty(sts)){
            return false;
        }else if(sts.equals("2")){
            String sql =  "select count(1) from pims_pathology_pieces  where pieceid = "+ id +" and pieisembed = 0 and  pieparaffinid is null";
            if(countTotal(sql).intValue() == 1){
                return true;
            }
        }else if(sts.equals("1")){
            String sql =  "select count(1) from pims_pathology_pieces  where pieceid = "+ id +" and pieisembed = 1 and  pieparaffinid is not null";;
            if(countTotal(sql).intValue() == 1){
                return true;
            }
        }
        return false;
    }

    @Override
    public List<PimsPathologyParaffin> getParaffinBySampleId(long sampleId, Long orderId) {
        StringBuilder builder = new StringBuilder();
        builder.append("select paraffinid,parparaffincode, ");
        if(orderId != null) {
            builder.append("(select NVL(ChiSlideNum,0) from pims_pathology_order_child where chiorderid=:orderId and paraffinid=ChiParaffinId and finishstatus=0) as slidenum, ");
            builder.append("(select NVL(ChiNullSlideNum,0) from pims_pathology_order_child where chiorderid=:orderId and paraffinid=ChiParaffinId and finishstatus=0) as obligateslidenum, ");
            builder.append("(select childorderid from pims_pathology_order_child where chiorderid=:orderId and paraffinid=ChiParaffinId and finishstatus=0) as childorderid, ");
            builder.append("(select count(1) from pims_pathology_order_check where cheorderid=:orderId and paraffincode=parparaffincode and finishstatus=0) as totalitem, ");
        } else{
            builder.append("0 as slidenum, ");
            builder.append("0 as obligateslidenum, ");
            builder.append("0 as childorderid, ");
            builder.append("0 as totalitem, ");
        }
        builder.append("(select count(1) from Pims_Pathology_Slide s where s.slisampleid=:sampleId and s.SliUseFlag=0 and s.slislidetype = 1 and parparaffincode=s.SliParaffinCode) as parnullslidenum ");
        builder.append("from pims_pathology_paraffin where parsampleid=:sampleId ");
        if(orderId != null) {
            builder.append("and paraffinid in(select chiparaffinid from pims_pathology_order_child where chiorderid=:orderId )");
        }
        builder.append("group by paraffinid,parparaffincode order by paraffinid ");
        SQLQuery query = getSession().createSQLQuery(builder.toString());
        query.setParameter("sampleId", sampleId);
        if(orderId != null)
            query.setParameter("orderId", orderId);
        List list = query.list();
        List<PimsPathologyParaffin> ret = new ArrayList<>();
        if(list.size() > 0) {
            for(Object obj : list) {
                PimsPathologyParaffin paraffin = new PimsPathologyParaffin();
                paraffin.setParaffinid(((BigDecimal)((Object[])obj)[0]).longValue());
                paraffin.setParparaffincode((String)((Object[])obj)[1]);
                paraffin.setSlidenum(((BigDecimal)((Object[])obj)[2]).longValue());
                paraffin.setObligateslidenum(((BigDecimal)((Object[])obj)[3]).longValue());
                paraffin.setChildorderid(((BigDecimal)((Object[])obj)[4]).longValue());
                paraffin.setTotalitem(((BigDecimal)((Object[])obj)[5]).longValue());
                paraffin.setParnullslidenum(((BigDecimal)((Object[])obj)[6]).longValue());
                ret.add(paraffin);
            }
        }
        return ret;
    }

    @Override
    public PimsPathologyParaffin getPimsPathologyParaffin(long sampleId, String paraffinCode) {
        StringBuffer sb = new StringBuffer();
        sb.append(" from PimsPathologyParaffin where parsampleid=:sampleId and parparaffincode=:paraffinCode");
        Query query = getSession().createQuery(sb.toString());
        query.setParameter("sampleId", sampleId);
        query.setParameter("paraffinCode", paraffinCode);
        return (PimsPathologyParaffin) query.list().get(0);
    }

    @Override
    public List getParaffinMaterial(PimsPathologyParaffin paraffin) {
        String[] pieceIds = paraffin.getParpieceids().split(",");
        Set<Long> set = new HashSet<>();
        for(String  str : pieceIds) {
            set.add(Long.valueOf(str));
        }
        SQLQuery query = getSession().createSQLQuery("select PieceId, PieParts, PieDoctorName from Pims_Pathology_Pieces where PieceId in(:id)");
        query.setParameterList("id", set);
        return query.list();
    }

    @Override
    public List getParaffinFromOrder(long orderId) {
        SQLQuery query = getSession().createSQLQuery("select Childorderid,Chiparaffinid,Chiparaffincode,Chislidenum,Chinullslidenum from Pims_Pathology_Order_Child where Chiorderid=:orderId");
        query.setParameter("orderId", orderId);
        return query.list();
    }
}
