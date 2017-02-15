package com.pims.dao.hibernate.report;

import com.pims.dao.report.ConsultationDao;
import com.pims.model.*;
import com.smart.dao.hibernate.GenericDaoHibernate;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zp on 2016/12/23.
 */
@Repository("consultationDao")
public class ConsultationDaoHibernate extends GenericDaoHibernate implements ConsultationDao{
    public ConsultationDaoHibernate(){
        super(PimsBaseModel.class);
    }

    //public List getDoctor(Map map){
    //    String sql = "select p.name from PimsConsultationDetail as p where detdoctorname=:detdoctorname";
    //    Query query = getSession().createSQLQuery(sql);
    //    query.setParameter("detdoctorname",(String)map.get("detdoctorname"));
    //    System.out.print(query.list());
    //    List result = query.list()
    //    return result;
    //}

    public StringBuffer getSearchSql(StringBuffer sb, Map map) {
        if (map.get("consponsoredtime")!=null) {
            sb.append(" and c.consponsoredtime >=:consponsoredtime");//会诊状态
        }
        if (map.get("confinishedtime")!=null) {
            sb.append(" and c.confinishedtime >=:confinishedtime");//病种类别ID
        }
        if (!StringUtils.isEmpty((String) map.get("logyids"))) {
            sb.append(" and s.sampathologyid ='" + (String) map.get("logyids")+"'");//病人
        }
        if (!StringUtils.isEmpty((String) map.get("sampathologycode"))) {
            sb.append(" and s.sampathologycode ='" + (String) map.get("sampathologycode")+"'");//病人
        }
        if (!StringUtils.isEmpty((String) map.get("sampatientname"))) {
            sb.append(" and s.sampatientname like '%" + (String) map.get("sampatientname")+"%'");//病理编号
        }
        if (!StringUtils.isEmpty((String) map.get("samsendhospital"))) {
            sb.append(" and s.samsendhospital = '" + (String) map.get("samsendhospital")+"'");//在库状态
        }
        if (!StringUtils.isEmpty((String) map.get("samdeptcode"))) {
            sb.append(" and s.samdeptname = '" + (String) map.get("samdeptcode")+"'");//病理编号
        }
        if (!StringUtils.isEmpty((String) map.get("samsenddoctorname"))) {
            sb.append(" and s.samsenddoctorname = '" + (String) map.get("samsenddoctorname")+"'");//病理编号
        }
        if (!StringUtils.isEmpty((String) map.get("conconsultationstate"))) {
            sb.append(" and c.conconsultationstate='"+(String) map.get("conconsultationstate")+"'");//病理编号
        }
        if (!StringUtils.isEmpty((String) map.get("confinisheduserid"))) {
            sb.append(" and c.confinisheduserid='"+(String) map.get("confinisheduserid")+"'");//病理编号
        }
        //if (!StringUtils.isEmpty((String) map.get("samre"))) {
        //    sb.append(" and samre like '%" + (String) map.get("samre")+"%'");//病人
        //} consponsoredusername
        if (!StringUtils.isEmpty((String) map.get("consponsoredusername"))) {
            sb.append(" and c.consponsoredusername='"+(String) map.get("consponsoredusername")+"'");//病理编号
        }
        //if (!StringUtils.isEmpty((String) map.get("remarks"))) {
        //    sb.append(" and ='"+(String) map.get("remarks")+"'");//病理编号
        //}
        if (!StringUtils.isEmpty((String) map.get("remarks"))) {
            sb.append(" and s.sampleid in(select ressampleid from PIMS_SAMPLE_RESULT WHERE RESVIEWTITLE='病理诊断' and restestresult like '%"+(String) map.get("remarks")+"%')");//病理编号
        }
        return sb;
    }


    /**
     * 查询标本列表
     *
     * @param map
     * @return
     */
    @Override
    public List getConsultationList(Map map,PimsBaseModel ppr) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT " +
                "s.SAMPLEID, " +
                "s.SAMPATHOLOGYCODE," +
                "s.SAMPATHOLOGYID," +
                "s.SAMCUSTOMERID," +
                "s.SAMPATIENTNAME," +
                "s.SAMPATIENTSEX," +
                "s.SAMPATIENTAGE," +
                "s.SAMSAMPLESTATUS," +
                "s.SAMSENDDOCTORNAME," +
                "s.SAMSENDHOSPITAL," +
                "s.SAMDEPTNAME," +
                "c.CONSULTATIONID," +
                "c.CONSPONSOREDUSERID," +
                "c.CONSPONSOREDUSERNAME," +
                "c.CONSPONSOREDTIME," +
                "c.CONCONSULTATIONSTATE," +
                "c.CONFINISHEDUSERID," +
                "c.CONFINISHEDUSERNAME," +
                "c.CONFINISHEDTIME," +
                "s.SAMSENDDOCTORID," +
                "s.SAMDEPTCODE," +
                "p.PATNAMECH," +
                "s.SAMAUDITER," +
                "s.SAMAUDITEDTIME," +
                "s.samsendtime "+
                "FROM " +
                "PIMS_PATHOLOGY_SAMPLE s," +
                "PIMS_PATHOLOGY_CONSULTATION c," +
                "PATHOLOGY.PIMS_SYS_PATHOLOGY p" +
                " WHERE " +
                "s.SAMPLEID = c.CONSAMPLEID AND " +
                "s.SAMPATHOLOGYID = p.PATHOLOGYID");
        getSearchSql(sb, map);
        sb.append(" order by c.conconsultationstate");

        System.out.println(sb.toString());
        SQLQuery query = getSession().createSQLQuery(sb.toString());
        if(map.get("consponsoredtime")!=null){
        query.setDate("consponsoredtime",(Date) map.get("consponsoredtime"));}
        if(map.get("confinishedtime")!=null){
        query.setDate("confinishedtime",(Date) map.get("confinishedtime"));}
        query.setFirstResult(ppr.getStart());
        query.setMaxResults(ppr.getEnd());
        return query.list();
    }

    @Override
    public int getReqListNum(Map map) {
        StringBuffer sb = new StringBuffer();
        sb.append(" select count(1) from VIEW_CONSULTATION_QUERY where 1=1 ");
        getSearchSql(sb, map);
        //if(map.get("consponsoredtime")!=null){
        //    query.setDate("consponsoredtime",(Date) map.get("consponsoredtime"));}
        //if(map.get("confinishedtime")!=null){
        //    query.setDate("confinishedtime",(Date) map.get("confinishedtime"));}
        return countTotal(sb.toString()).intValue();
    }

    @Override
    public List<PimsPathologyPieces> getSampleListNoPage(String pathologyid) {
        StringBuffer sb = new StringBuffer();
        sb.append(" from PimsPathologyPieces where piepathologycode = '"+ pathologyid + "' order by to_number(piesamplingno)");
        return getSession().createQuery(sb.toString()).list();
    }

    @Override
    public List<PimsConsultationDetail> getConDets(String code){
        StringBuffer sb = new StringBuffer();
        sb.append(" from PimsConsultationDetail where detpathologycode ='"+ code +"' order by detadvice asc,detconsultationtime desc");
        return getSession().createQuery(sb.toString()).list();
    }

    @Override
    public String getResult(Long id){
        String sql = "select restestresult from PIMS_SAMPLE_RESULT where ressampleid='"+id+"' and resviewtitle='病理诊断'";
        SQLQuery query = getSession().createSQLQuery(sql);
        Object t = query.uniqueResult();
        if(t==null){
            return null;
        }
        return t.toString();
    }

    @Override
    public List<PimsPathologyOrderCheck> getItem(String id,String sampleid){

        StringBuffer sb = new StringBuffer();
        sb.append("from PimsPathologyOrderCheck where chesampleid='"+sampleid+"' and cheorderid=(SELECT max(chiorderid) FROM PimsPathologyOrderChild where testItemId='"+id+"' and chisampleid='"+sampleid+"')");
        Query query = getSession().createQuery(sb.toString());
        return query.list();
    }
}
