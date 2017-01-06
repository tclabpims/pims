//package com.pims.dao.hibernate.report;
//
//import com.pims.dao.report.ConsultationDao;
//import com.pims.model.PimsConsultationDetail;
//import com.pims.model.ViewConsultationQuery;
//import com.smart.dao.hibernate.GenericDaoHibernate;
//import org.apache.commons.lang.StringUtils;
//import org.hibernate.Query;
//import org.hibernate.SQLQuery;
//import org.springframework.stereotype.Repository;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by zp on 2016/12/23.
// */
//@Repository("consultationDao")
//public class ConsultationDaoHibernate extends GenericDaoHibernate<ViewConsultationQuery,Long> implements ConsultationDao{
//    public ConsultationDaoHibernate(){
//        super(ViewConsultationQuery.class);
//    }
//
//    //public List getDoctor(Map map){
//    //    String sql = "select p.name from PimsConsultationDetail as p where detdoctorname=:detdoctorname";
//    //    Query query = getSession().createSQLQuery(sql);
//    //    query.setParameter("detdoctorname",(String)map.get("detdoctorname"));
//    //    System.out.print(query.list());
//    //    List result = query.list()
//    //    return result;
//    //}
//
//    public StringBuffer getSearchSql(StringBuffer sb, Map map) {
//        if (!StringUtils.isEmpty((String) map.get("conconsultationstate"))) {
//            sb.append(" and conconsultationstate ="+ (long) map.get("conconsultationstate"));//会诊状态
//        }
//        if (!StringUtils.isEmpty((String) map.get("sampathologyid"))) {
//            sb.append(" and sampathologyid = " + (long) map.get("sampathologyid"));//病种类别ID
//        }
//        if (!StringUtils.isEmpty((String) map.get("sampathologycode"))) {
//            sb.append(" and sampathologycode like '%" + (String) map.get("sampathologycode")+"%'");//病人
//        }
//        if (!StringUtils.isEmpty((String) map.get("sampatientname"))) {
//            sb.append(" and sampatientname like '%" + (String) map.get("sampatientname")+"%'");//病人
//        }
//        if (!StringUtils.isEmpty((String) map.get("samsendhospital"))) {
//            sb.append(" and samsendhospital = '" + (String) map.get("samsendhospital")+"'");//病理编号
//        }
//        if (!StringUtils.isEmpty((String) map.get("samdeptname"))) {
//            sb.append(" and samdeptname = '" + (String) map.get("samdeptname")+"'");//在库状态
//        }
//        if (!StringUtils.isEmpty((String) map.get("samsenddoctorname"))) {
//            sb.append(" and samsenddoctorname = '" + (String) map.get("samsenddoctorname")+"'");//病理编号
//        }
//        if (!StringUtils.isEmpty((String) map.get("consponsoredusername"))) {
//            sb.append(" and consponsoredusername = '" + (String) map.get("consponsoredusername")+"'");//病理编号
//        }
//        if (!StringUtils.isEmpty((String) map.get("detdoctorname"))) {
//            sb.append(" and detdoctorname in (select p.detsampleid from PIMS_CONSULTATION_DETAIL as p where detdoctorname='"+map.get("detdoctorname")+"')");//病理编号
//        }
//        //if (!StringUtils.isEmpty((String) map.get("samre"))) {
//        //    sb.append(" and samre like '%" + (String) map.get("samre")+"%'");//病人
//        //}
//        if ((Date) map.get("") != null) {
//            sb.append(" and samregisttime >=:req_bf_time");//开始时间
//        }
//        if ((Date) map.get("") != null) {
//            sb.append(" and samregisttime <=:req_af_time");//开始时间
//        }
//        return sb;
//    }
//
//
//    /**
//     * 查询标本列表
//     *
//     * @param map
//     * @return
//     */
//    @Override
//    public List<ViewConsultationQuery> getConsultationList(Map map) {
//        StringBuffer sb = new StringBuffer();
//        sb.append("from ViewConsultationQuery where 1=1");
//        getSearchSql(sb, map);
//        sb.append(" order by conconsultationstate");
//        System.out.println(sb.toString());
//        Query query = getSession().createQuery(sb.toString());
//        return query.list();
//    }
//
//    @Override
//    public int getReqListNum(Map map) {
//        StringBuffer sb = new StringBuffer();
//        sb.append(" select count(1) from VIEW_CONSULTATION_QUERY where 1=1 ");
//        getSearchSql(sb, map);
//        return countTotal(sb.toString()).intValue();
//    }
//}
