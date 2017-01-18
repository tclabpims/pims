package com.pims.dao.hibernate.pimsPathologySample;

import com.alibaba.fastjson.JSONArray;
import com.pims.dao.pimspathologysample.PimsPathologyPiecesDao;
import com.pims.dao.pimspathologysample.PimsPathologySampleDao;
import com.pims.model.*;
import com.pims.service.PimsUserManager;
import com.pims.service.pimspathologysample.PimsPathologyParaffinManager;
import com.pims.service.pimspathologysample.PimsPathologyPiecesManager;
import com.pims.service.pimssyspathology.PimsPathologyOrderManager;
import com.pims.service.pimssyspathology.PimsSysPathologyManager;
import com.smart.Constants;
import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.model.user.User;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONException;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
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
    @Autowired
    private PimsPathologyOrderManager pimsPathologyOrderManager;
    /**
     * 查询材块列表不分页
     * @param code
     * @return
     */
    @Override
    public List<PimsPathologyPieces> getSampleListNoPage(String code) {
        StringBuffer sb = new StringBuffer();
        sb.append(" from PimsPathologyPieces where piesampleid = "+ code + "order by to_number(piesamplingno)");
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
            sb.append(" and sampathologyid = " + map.getLogyid());//病种类别
        }
        if(map.getReq_bf_time() != null){
            sb.append(" and samregisttime >= :req_bf_time");//开始时间
        }
        if(!StringUtils.isEmpty(map.getReq_sts())){
            if(map.getReq_sts().equals("1")){
                sb.append(" and samsamplestatus > 0 ");//取材状态
            }else{
                sb.append(" and samsamplestatus = "+ map.getReq_sts() );//取材状态

            }
        }
        if(!StringUtils.isEmpty(map.getSend_doctor())){
            sb.append(" and sampleid in (select chisampleid from pims_pathology_order_child " +
                    "where chihandletype = 1 and chiisdelete = 0)");//补医嘱
        }
        if(!StringUtils.isEmpty(map.getSend_dept())){
            sb.append(" and sampathologycode like '%" + map.getSend_dept().toUpperCase()+"%'");//病理编号
        }
        if(!StringUtils.isEmpty(map.getSend_hosptail())){
            //sb.append(" and samsendhospital = " + map.getSend_hosptail());
        }
        if(!StringUtils.isEmpty(map.getPatient_name())){
            sb.append(" and sampatientname  like '%" + map.getPatient_name()+"%'");//病人姓名
        }
        if(map.getReq_af_time() != null){
            sb.append(" and  samregisttime < :req_af_time");//结束时间
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
            StringBuffer sb = new StringBuffer();
            sb.append("from PimsPathologySample where sampleid = "+ id);
            Query query = getSession().createQuery(sb.toString());
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
            PimsPathologySample sample = pimsPathologyPiecesManager.getBySampleNo(map.getSampleid());

            StringBuffer sb = new StringBuffer();
            if(sample.getSamsamplestatus() > 1){
                sb.append("update pims_pathology_sample set samisdecacified = "+map.getSamisdecacified()+",samissamplingall="+map.getSamissamplingall()+
                        ", samsamplestatus = "+ sample.getSamsamplestatus() +",samjjsj='"+(StringUtils.isEmpty(map.getSamjjsj())?"":map.getSamjjsj())+"'  where sampleid = "+map.getSampleid());
            }else{
                PimsSysPathology psp = pimsSysPathologyManager.getSysPathologyById(sample.getSampathologyid());
                if(psp != null && psp.getPatissampling().intValue() == 0 && psp.getPatfirstn() != null && psp.getPatfirstn().intValue() == 1 && sts == 1){
                    sb.append("update pims_pathology_sample set samisdecacified = "+map.getSamisdecacified()+",samissamplingall="+map.getSamissamplingall()+
                            ", samsamplestatus = 2,samjjsj='"+(StringUtils.isEmpty(map.getSamjjsj())?"":map.getSamjjsj())+"'  where sampleid = "+map.getSampleid());
                }else{
                    sb.append("update pims_pathology_sample set samisdecacified = "+map.getSamisdecacified()+",samissamplingall="+map.getSamissamplingall()+
                            ", samsamplestatus = "+ sts +",samjjsj='"+(StringUtils.isEmpty(map.getSamjjsj())?"":map.getSamjjsj())+"'  where sampleid = "+map.getSampleid());
                }
            }
            getSession().createSQLQuery(sb.toString()).executeUpdate();
            return true;
        }
    }

    /**
     * 更新标本诊断医师信息
     * @param map,sts
     * @return
     */
    public boolean updateSampleDoctor(PimsPathologySample map,PimsPathologyPieces pieces) {
            StringBuffer sb = new StringBuffer();
            sb.append("update pims_pathology_sample set sampiecedoctorid = '"+pieces.getPiedoctorid()+"',sampiecedoctorname='"+pieces.getPiedoctorname()+
                    "' where sampleid = "+map.getSampleid());
            getSession().createSQLQuery(sb.toString()).executeUpdate();
            return true;
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
                    " a.pieisembed = 0 and a.piefirstn is null and  a.pieceid = "+ id;
            if(countTotal(sql).intValue() == 1){
                return true;
            }
        }
        return false;
    }

    @Autowired
    private PimsUserManager userManager;
    @Autowired
    private PimsSysPathologyManager pimsSysPathologyManager;
    @Autowired
    private PimsPathologyParaffinManager pimsPathologyParaffinManager;
    /**
     * 更新标本信息及材块信息
     * @param piecesList 材块列表,sample 标本信息,sts 状态,state 逻辑更新标志
     * @return
     */
    @Override
    public boolean updateSampleSts(JSONArray piecesList, PimsPathologySample sample, int sts, int state) {
        StringBuffer sb = new StringBuffer();
        //查询该标本的材块信息
        List<PimsPathologyPieces> list = pimsPathologyPiecesManager.getSampleListNoPage(String.valueOf(sample.getSampleid()));
        List list1 = new ArrayList();
        List list2 = new ArrayList();
        List list3 = new ArrayList();
        if(sts == 1){//取材
            //更新标本信息
            if(piecesList == null || piecesList.size() == 0){
                updateSampleSts(sample,0);
            }else{
                updateSampleSts(sample,1);
            }
            for(int i = 0;i<piecesList.size();i++){//取材
                Map map = (Map) piecesList.get(i);
                PimsPathologyPieces piece = (PimsPathologyPieces) setBeanProperty(map,PimsPathologyPieces.class);
                if(piece.getPiestate().longValue() == 0){//未取材
                    if(i==0){//第一个材块
                        sample = pimsPathologyPiecesManager.getBySampleNo(sample.getSampleid());
                        if(StringUtils.isEmpty(sample.getSampiecedoctorid())){
                            updateSampleDoctor(sample,piece);//增加初诊医师ID信息
                        }else{
                            if (!list3.contains(piece.getPiedoctorid())) {
                                list3.add(piece.getPiedoctorid());
                            }
                        }
                    }
                    PimsSysPathology psp = pimsSysPathologyManager.getSysPathologyById(sample.getSampathologyid());
                    if(psp != null && psp.getPatissampling().intValue() == 0 && psp.getPatfirstn() != null && psp.getPatfirstn().intValue() == 1){
                        piece.setPiestate((long) 2);
                        piece.setPieisembed("1");
                        piece = pimsPathologyPiecesManager.save(piece);
                        PimsPathologyParaffin ppp = new PimsPathologyParaffin();
//                        ppp.setParaffinid();//蜡块Id
                        ppp.setParsampleid(sample.getSampleid());//标本Id
                        ppp.setParpathologycode(sample.getSampathologycode());//病理编号
                        ppp.setParname(piece.getPiecode());//蜡块名称
                        ppp.setParparaffinno(Integer.parseInt(piece.getPiesamplingno()));//蜡块序号
                        ppp.setParparaffincode(piece.getPiecode());//蜡块编号
                        ppp.setParbarcode(piece.getPiecode());//蜡块条码号
                        ppp.setParpiececount(piece.getPiecounts().intValue());//材块块数
                        ppp.setParpieceids(String.valueOf(piece.getPieceid()));//材块Ids(多个之间用逗号隔开)
                        ppp.setParnullslidenum(piece.getPienullslidenum());//取白片数量
                        ppp.setParpieceparts(piece.getPieparts());//取材部位(多个之间用逗号隔开)
                        ppp.setParissectioned(0);//是否已切片(0未切片，1已切片)
//                        ppp.setParsectioneddoctor();//切片医生
//                        ppp.setParsectionedtime();//切片时间
//                        ppp.setParisprintlabel(0);//是否已打印标签
//                        ppp.setParprintuser();//标签打印人员
//                        ppp.setParprinttime();//标签打印时间
//                        ppp.setParremaining();//剩余处理
//                        ppp.setParfirstv();//预留字段1(Varchar)
//                        ppp.setParsecondv();//预留字段2(Varchar)
//                        ppp.setParfirstn();//预留字段3(Numberic)
//                        ppp.setParfirstd();//预留字段4(Date)
//                        ppp.setParcreatetime();//
//                        ppp.setParcreateuse();//
                        pimsPathologyParaffinManager.save(ppp);
                    }else{
                        piece.setPiestate((long) 1);
                        piece = pimsPathologyPiecesManager.save(piece);

                    }
                    //判断材块是不是补取
                    if(piece.getPiefirstn() != null){
                        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                        pimsPathologyOrderManager.updateOrderState(piece.getPiefirstn(),1,user);
                        pimsPathologyOrderManager.updateOrderState(piece.getPiefirstn(),2,user);
                    }
                }
                if (!list3.contains(piece.getPiedoctorid())) {
                    list3.add(piece.getPiedoctorid());
                }
                list1.add(piece.getPieceid());
            }
            sample = pimsPathologyPiecesManager.getBySampleNo(sample.getSampleid());
            if(list3 != null && list3.size()>0 && !list3.contains(sample.getSampiecedoctorid())){
                User user = userManager.get(Long.parseLong((String)(list3.get(0))));
                PimsPathologyPieces pieces = new PimsPathologyPieces();
                pieces.setPiedoctorid(String.valueOf(user.getId()));
                pieces.setPiedoctorname(user.getName());
                updateSampleDoctor(sample,pieces);//增加初诊医师ID信息
            }
        }else if(sts == 0){//保存
            for(int i=0;i<piecesList.size();i++){
                Map map = (Map) piecesList.get(i);
                PimsPathologyPieces  piece = (PimsPathologyPieces) setBeanProperty(map,PimsPathologyPieces.class);
                if (!list2.contains(piece.getPiestate().toString())) {
                    list2.add(piece.getPiestate().toString());
                }
                if(piece.getPiestate().longValue() == 0) {//未取材
                    piece = pimsPathologyPiecesManager.save(piece);
                }
                list1.add(piece.getPieceid());
            }
            if(state==1){//完全更新才更新的原则
                if(list2 == null || list2.size() ==0 || list2.contains("0")){
                    updateSampleSts(sample,0);
                }else{
                    updateSampleSts(sample,1);
                }
            }else{//部分更新就更新的原则
                if(list2.contains("1")){
                    updateSampleSts(sample,1);
                }else{
                    updateSampleSts(sample,0);
                }
            }
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
        return true;
    }

    @Override
    public List<PimsPathologyPieces> getPiecesByOrderId(long orderId) {
        StringBuffer sb = new StringBuffer();
        sb.append("from PimsPathologyPieces where piefirstn=:orderId");
        Query query = getSession().createQuery(sb.toString());
        query.setParameter("orderId", orderId);
        return query.list();
    }

    @Override
    public PimsPathologyPieces getPieceBySampleId(long ordsampleid) {
        StringBuffer sb = new StringBuffer();
        sb.append("from PimsPathologyPieces where piesampleid=:ordsampleid and rownum=1 and PieDoctorId is not null");
        Query query = getSession().createQuery(sb.toString());
        query.setParameter("ordsampleid", ordsampleid);
        return (PimsPathologyPieces) query.uniqueResult();
    }

    @Override
    public JSONArray getSlideCode(JSONArray samplesList) {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        JSONArray  array = new JSONArray();
        if(samplesList == null || samplesList.size() == 0){
            return array;
        }else{
            StringBuffer sb = new StringBuffer();
            sb.append(" from PimsPathologyPieces,PimsPathologySample where piesampleid = sampleid and piesampleid in (");
            for(int i=0;i<samplesList.size();i++){
                Map map = (Map) samplesList.get(i);
                PimsPathologySample sample = (PimsPathologySample) setBeanProperty(map,PimsPathologySample.class);
                sb.append(sample.getSampleid());
                if(i<samplesList.size()-1){
                    sb.append(",");
                }
            }
            sb.append(") order by piecode");
            List list = getSession().createQuery(sb.toString()).list();
            for(Object o : list){
                org.codehaus.jettison.json.JSONObject object = new org.codehaus.jettison.json.JSONObject();
                Object[] oo = (Object[]) o;
                PimsPathologyPieces piece = (PimsPathologyPieces) oo[0];
                PimsPathologySample sam = (PimsPathologySample) oo[1];
                try {
                    object.put("piepathologycode",piece.getPiepathologycode());//病理编号
                    object.put("samissamplingall",sam.getSamissamplingall()==0?"否":"是");//是否全取
                    object.put("samisdecacified",sam.getSamisdecacified()==0?"否":"是");//是否脱钙
                    object.put("samjjsj",sam.getSamjjsj());//巨检所见
                    object.put("piesamplingno",piece.getPiesamplingno());//取材序号
                    object.put("piecounts",piece.getPiecounts());//材块数
                    object.put("pienullslidenum",piece.getPienullslidenum());//白片数
                    object.put("pieparts",piece.getPieparts());//取材部位
                    object.put("piedoctorname",piece.getPiedoctorname());//取材医生
                    object.put("pierecordername",piece.getPierecordername());//录入员
                    object.put("piesamplingtime", Constants.SDF.format(piece.getPiesamplingtime()));//取材时间
                    object.put("piespecial",piece.getPiespecial());//特殊要求
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                array.add(object);
            }
        }

        return array;
    }

    @Override
    public String getMinTime(Long sampleid) {
        StringBuffer sb = new StringBuffer();
        sb.append("select to_char(min(PIESAMPLINGTIME),'YYYY-MM-DD HH24:MI:SS') AS PIESAMPLINGTIME " +
                "from Pims_Pathology_Pieces where PIESAMPLEID="+sampleid);
        Object o = getSession().createSQLQuery(sb.toString()).uniqueResult();
        if(o == null) return "";
        return o.toString();
    }
}
