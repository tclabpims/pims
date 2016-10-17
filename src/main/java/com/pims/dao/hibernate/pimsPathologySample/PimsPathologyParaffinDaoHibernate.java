package com.pims.dao.hibernate.pimsPathologySample;

import com.pims.dao.pimspathologysample.PimsPathologyParaffinDao;
import com.pims.dao.pimspathologysample.PimsPathologyPiecesDao;
import com.pims.model.PimsBaseModel;
import com.pims.model.PimsPathologyParaffin;
import com.pims.model.PimsPathologyPieces;
import com.pims.model.PimsPathologySample;
import com.smart.Constants;
import com.smart.dao.hibernate.GenericDaoHibernate;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by king on 2016/10/10.
 */
@Repository("pimsPathologyParaffinDao")
public class PimsPathologyParaffinDaoHibernate extends GenericDaoHibernate<PimsPathologyParaffin,Long> implements PimsPathologyParaffinDao {
    public PimsPathologyParaffinDaoHibernate(){super(PimsPathologyParaffin.class);}
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
//        sb.append(" select parsampleid,parpieceids,paraffinid,parpathologycode,parname,parparaffinno,piecode," +
//                "parpiececount,parnullslidenum,parpieceparts,piespecial,piesamplingtime,piedoctorname,pieembedtime," +
//                "pieembeddoctorname,parfirstn from pims_pathology_pieces a, pims_pathology_paraffin b where a.pieparaffinid = b.paraffinid and a.pieceid in ( "+ code + ")");
//        return getSession().createSQLQuery(sb.toString()).list();
    }
    /**
     * 查询材块列表
     * @param map
     * @return
     */
    @Override
    public List<PimsPathologyPieces> getSampleList(PimsBaseModel map) {
        StringBuffer sb = new StringBuffer();
//        sb.append(" select a.piesamplingtime from pims_pathology_pieces a, pims_pathology_sample b where a.piesampleid = b.sampleid and a.piestate > 0 ");
        sb.append(" from PimsPathologyPieces a where  a.piestate > 0 ");
        if(!StringUtils.isEmpty(map.getLogyid())){
            sb.append(" and b.sampathologyid = " + map.getLogyid());
        }
        if(!StringUtils.isEmpty(map.getReq_bf_time())){
            sb.append(" and a.piesamplingtime >= to_date('" + map.getReq_bf_time()+"','YYYY-MM-DD')");
        }
        if(!StringUtils.isEmpty(map.getReq_sts())){
                sb.append(" and a.piestate = " + map.getReq_sts());
        }
        if(!StringUtils.isEmpty(map.getSend_doctor())){
            //sb.append(" and samsenddoctorid = " +  map.getSend_doctor());
        }
        if(!StringUtils.isEmpty(map.getSend_dept())){
            sb.append(" and a.piepathologycode = " + map.getSend_dept());
        }
        if(!StringUtils.isEmpty(map.getSend_hosptail())){
            //sb.append(" and samsendhospital = " + map.getSend_hosptail());
        }
//        if(!StringUtils.isEmpty(map.getPatient_name())){
//            sb.append(" and b.sampatientname = " + map.getPatient_name());
//        }
        if(!StringUtils.isEmpty(map.getReq_af_time())){
            sb.append(" and  a.piesamplingtime < to_date('" + map.getReq_af_time()+"','YYYY-MM-DD')+1");
        }
        if(!StringUtils.isEmpty(map.getReq_code())){
            //sb.append(" and saminspectionid = " + map.getReq_code());
        }
        String orderby = (map.getSidx()==null|| map.getSidx().trim().equals(""))?"a.piesampleid":map.getSidx();
        sb.append(" order by " + orderby + " " +map.getSord());
        System.out.println(sb.toString());
//        Query query = getSession().createSQLQuery(sb.toString());
//        query.setFirstResult(map.getStart());
//        query.setMaxResults(map.getEnd());
//        return query.list();
        return pagingList(sb.toString(),map.getStart(),map.getEnd());
    }

    /**
     * 查询材块数量
     * @param map
     * @return
     */
    @Override
    public int getReqListNum(PimsBaseModel map) {
        StringBuffer sb = new StringBuffer();
        sb.append(" select count(1) from pims_pathology_pieces a, pims_pathology_sample b where a.piesampleid = b.sampleid and a.piestate > 0 ");
        if(!StringUtils.isEmpty(map.getLogyid())){
            sb.append(" and b.sampathologyid = " + map.getLogyid());
        }
        if(!StringUtils.isEmpty(map.getReq_bf_time())){
            sb.append(" and a.piesamplingtime >= to_date('" + map.getReq_bf_time()+"','YYYY-MM-DD')");
        }
        if(!StringUtils.isEmpty(map.getReq_sts())){
            sb.append(" and a.piestate = " + map.getReq_sts());
        }
        if(!StringUtils.isEmpty(map.getSend_doctor())){
            //sb.append(" and samsenddoctorid = " +  map.getSend_doctor());
        }
        if(!StringUtils.isEmpty(map.getSend_dept())){
            sb.append(" and a.piepathologycode = " + map.getSend_dept());
        }
        if(!StringUtils.isEmpty(map.getSend_hosptail())){
            //sb.append(" and samsendhospital = " + map.getSend_hosptail());
        }
        if(!StringUtils.isEmpty(map.getPatient_name())){
            sb.append(" and b.sampatientname = " + map.getPatient_name());
        }
        if(!StringUtils.isEmpty(map.getReq_af_time())){
            sb.append(" and  a.piesamplingtime < to_date('" + map.getReq_af_time()+"','YYYY-MM-DD')+1");
        }
        if(!StringUtils.isEmpty(map.getReq_code())){
            //sb.append(" and saminspectionid = " + map.getReq_code());
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
     * 更新标本状态
     * @param sampleid,sts
     * @return
     */
    @Override
    public boolean updateSample(long sampleid,int sts) {
        PimsPathologySample sample = (PimsPathologySample) getSession().createQuery("from PimsPathologySample where sampleid = "+ sampleid).list().get(0);//查询标本信息
        if(sts == 3){//切片
            if(sts - sample.getSamsamplestatus() == 1){//样本状态为包埋
                int minnum = countTotal("select min(parfirstn) from pims_pathology_paraffin a where a.parsampleid =" + sampleid);//包埋全部已切片
                if(minnum == sts){
                    getSession().createSQLQuery("update pims_pathology_sample set samsamplestatus = "+sts+" where sampleid =  "+ sampleid).executeUpdate();
                }
            }
        }else if(sts == 2){//取消切片 或进行包埋
            if(sample.getSamsamplestatus() - sts == 1){//样本状态为已切片
                getSession().createSQLQuery("update pims_pathology_sample set samsamplestatus = "+sts+" where sampleid =  "+ sampleid).executeUpdate();
            }else if(sts - sample.getSamsamplestatus() == 1){//样本状态为已取材
                int minnum = countTotal("select min(piestate) from pims_pathology_pieces a where a.piesampleid =" + sampleid);//材块是否已全包埋
                if(minnum == sts){
                    getSession().createSQLQuery("update pims_pathology_sample set samsamplestatus = "+sts+" where sampleid =  "+ sampleid).executeUpdate();
                }
            }
        }else if(sts == 1){//取消包埋
            if(sample.getSamsamplestatus() - sts == 1){//样本状态为已包埋
                getSession().createSQLQuery("update pims_pathology_sample set samsamplestatus = "+sts+" where sampleid =  "+ sampleid).executeUpdate();
            }
        }
        return true;
    }
    /**
     * 更新材块信息
     * @param piece,sts
     * @return
     */
    @Override
    public boolean updateSampleSts(PimsPathologyPieces piece,int sts) {
        if(piece== null ){
            return false;
        }else{
            if(sts == 2){//包埋
                StringBuffer sb = new StringBuffer();
                sb.append("update pims_pathology_pieces set piestate = "+ sts +",pieembedtime = TO_DATE('"+ Constants.SDF.format(piece.getPieembedtime())+"','YYYY-MM-DD HH24:MI:SS')"
                        +",pieembeddoctorid = "+ piece.getPieembeddoctorid()+",pieembeddoctorname = '"+piece.getPieembeddoctorname()+
                        "',pieparaffinid = "+piece.getPieparaffinid()+"  where piestate = 1 and  pieceid = "+piece.getPieceid());
                System.out.println(sb.toString());
                getSession().createSQLQuery(sb.toString()).executeUpdate();
                return true;
            }else if(sts == 1){//取消包埋
                StringBuffer sb = new StringBuffer();
                sb.append("update pims_pathology_pieces set piestate = "+ sts +",pieembedtime = null,pieembeddoctorid = null ,pieembeddoctorname = null,"+
                        "pieparaffinid = null where piestate = 2 and  pieceid = "+piece.getPieceid());
                System.out.println(sb.toString());
                getSession().createSQLQuery(sb.toString()).executeUpdate();
                return true;
            }
            return false;
        }
    }
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
            String sql =  "select count(1) from pims_pathology_pieces  where pieceid = "+ id +" and piestate = 1 and  pieparaffinid is null";
            if(countTotal(sql).intValue() == 1){
                return true;
            }
        }else if(sts.equals("1")){
            String sql =  "select count(1) from pims_pathology_pieces  where pieceid = "+ id +" and piestate = 2 and  pieparaffinid is not null";;
            if(countTotal(sql).intValue() == 1){
                return true;
            }
        }
        return false;
    }
}
