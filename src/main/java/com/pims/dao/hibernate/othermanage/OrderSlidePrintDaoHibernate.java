package com.pims.dao.hibernate.othermanage;

import com.pims.dao.othermanage.OrderSlidePrintDao;
import com.pims.model.PimsBaseModel;
import com.pims.model.PimsPathologyOrderCheck;
import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.webapp.util.DataResponse;
import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Map;
import java.util.List;

/**
 * Created by zp on 2017/1/9.
 */
@Repository("OrderSlidePrintDao")
public class OrderSlidePrintDaoHibernate extends GenericDaoHibernate implements OrderSlidePrintDao{
    public OrderSlidePrintDaoHibernate(){super(PimsPathologyOrderCheck.class);}

    public StringBuffer getSearchSql(StringBuffer sb, Map map) {
        if (!StringUtils.isEmpty((String)map.get("slitestitemid"))) {
            sb.append(" and s.slitestitemid = '" + map.get("slitestitemid")+"'");//条形码
        }
        if (!StringUtils.isEmpty((String)map.get("sliifprint"))) {
            sb.append(" and s.sliifprint = '" + map.get("sliifprint")+"'");//玻片id
        }
        if (!StringUtils.isEmpty((String)map.get("chirequsername"))) {
            sb.append(" and c.chirequsername like '%" +  (String) map.get("chirequsername")+"%'");//病人
        }
        if (!StringUtils.isEmpty((String)map.get("sampatientname"))) {
            sb.append(" and p.sampatientname like '%" +  (String) map.get("sampatientname")+"%'");//病人
        }
        if (!StringUtils.isEmpty((String)map.get("slipathologycode"))) {
            sb.append(" and s.slipathologycode = '" + ((String) map.get("slipathologycode")).toUpperCase()+"'");//病理编号
        }
        if (map.get("applybftime") != null) {
            sb.append(" and  c.chireqtime >= :applybftime");//开始时间
        }
        if (map.get("applyaftime")  != null) {
            sb.append(" and  c.chireqtime <= :applyaftime");//结束时间
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
    public List getLoanList(Map map, PimsBaseModel ppr) {
        StringBuffer sb = new StringBuffer();
        //蜡块号、病理编号、打印、病人姓名、申请时间、申请人id、申请人姓名、医嘱类型、检查项目类型
//        sb.append("select p.sampatientname,s.slipathologycode,s.sliparaffincode,s.sliifprint,c.chireqtime,c.chirequserid,c.chirequsername,k.cheorderitemid,k.chenamech,t.teschinesename from PIMS_PATHOLOGY_SLIDE s,PIMS_PATHOLOGY_SAMPLE p,PIMS_PATHOLOGY_ORDER_CHECK k,PIMS_PATHOLOGY_ORDER_CHILD c,PIMS_SYS_REQ_TESTITEM t WHERE ");
//        sb.append(" P.SAMPATHOLOGYCODE = K.CHEPATHOLOGYCODE AND K.CHEORDERID = C.CHIORDERID and C.TESTITEMID = T.TESTITEMID");
        sb.append("SELECT s.slitestitemid,s.slipathologycode,s.sliparaffincode,s.slitestitemname,s.sliifprint,p.sampatientname,");
        sb.append("c.chirequserid,c.chirequsername,c.chireqtime,t.teschinesename ");
        sb.append("from PIMS_PATHOLOGY_SLIDE s,PIMS_PATHOLOGY_SAMPLE p,PIMS_PATHOLOGY_ORDER_CHILD c,PIMS_SYS_REQ_TESTITEM t ");
        sb.append("where s.slitestitemid = t.TESTITEMID and s.slipathologycode = p.SAMPATHOLOGYCODE and s.slifirstn = c.chiorderid");
        getSearchSql(sb, map);
        String orderby = (ppr.getSidx() == null || ((String) ppr.getSidx()).trim().equals("")) ? "sliparaffincode" :ppr.getSidx();
        sb.append(" order by " + orderby + " " + ppr.getSord());
        System.out.println(sb.toString());
        SQLQuery query = getSession().createSQLQuery(sb.toString());
        if((Date)map.get("applybftime") != null){
            query.setDate("applybftime",(Date)map.get("applybftime"));
        }
        if((Date)map.get("applyaftime")!= null){
            query.setDate("applyaftime",(Date)map.get("applyaftime"));
        }
        query.setFirstResult(ppr.getStart());
        query.setMaxResults(ppr.getEnd());
        return query.list();
    }

    @Override
    public int getReqListNum(Map map) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT s.slitestitemid,s.slipathologycode,s.sliparaffincode,s.slitestitemname,s.sliifprint,p.sampatientname,");
        sb.append("c.chirequserid,c.chirequsername,c.chireqtime,t.teschinesename ");
        sb.append("from PIMS_PATHOLOGY_SLIDE s,PIMS_PATHOLOGY_SAMPLE p,PIMS_PATHOLOGY_ORDER_CHILD c,PIMS_SYS_REQ_TESTITEM t ");
        sb.append("where s.slitestitemid = t.TESTITEMID and s.slipathologycode = p.SAMPATHOLOGYCODE and s.slifirstn = c.chiorderid");
        getSearchSql(sb, map);
        SQLQuery query = getSession().createSQLQuery(sb.toString());
        if((Date)map.get("applybftime") != null){
            query.setDate("applybftime",(Date)map.get("applybftime"));
        }
        if((Date)map.get("applyaftime")!= null){
            query.setDate("applyaftime",(Date)map.get("applyaftime"));
        }

        if(query.list().size()==0 ) return 0;
        return query.list().size();
    }

    @Override
    public String getTotal(Map map){
        String sql="select MAX(piesamplingno) from PIMS_PATHOLOGY_PIECES WHERE piepathologycode='"+map.get("sliparaffincode")+"'";
        SQLQuery query = getSession().createSQLQuery(sql);
        Object total = query.uniqueResult();
        if(total == null) return "0";
        return (total).toString();
    }
}

