package com.pims.dao.hibernate.pimssyspathology;

import com.pims.dao.pimssyspathology.PimsPathologyOrderDao;
import com.pims.model.PimsBaseModel;
import com.pims.model.PimsPathologyOrder;
import com.pims.webapp.controller.GridQuery;
import com.smart.Constants;
import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.model.user.User;
import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by 909436637@qq.com on 2016/11/4.
 * Description:
 */
@Repository("pimsPathologyOrderDao")
public class PimsPathologyOrderDaoHibernate extends GenericDaoHibernate<PimsPathologyOrder, Long> implements PimsPathologyOrderDao {
    /**
     * Constructor that takes in a class to see which type of entity to persist.
     * Use this constructor when subclassing.
     */
    public PimsPathologyOrderDaoHibernate() {
        super(PimsPathologyOrder.class);
    }

    @Override
    public List getOrders(GridQuery gridQuery, String specialCheck, String pathologyCode, String startDate, String endDate, String patientName, String orderState, String ingore) {
        StringBuilder builder = new StringBuilder("select o.OrderId,o.OrderCode,o.OrdSampleId,o.OrdCustomerId,o.OrdPathologyCode,o.OrdOrderUser,t.teschinesename,c.ChiOrderState,s.SamPathologyId,t.tesenglishname ");
        builder.append("from PIMS_PATHOLOGY_ORDER o, PIMS_PATHOLOGY_ORDER_CHILD c, Pims_Pathology_Sample s,PIMS_SYS_REQ_TESTITEM t where ");
        builder.append("O.Orderid=C.Chiorderid and c.ChiSampleId=s.sampleid and t.testitemid=c.testItemId ");
        if(ingore != null ) {
            builder.append("and t.tesenglishname !='").append(ingore).append("' ");
        }
        if(StringUtils.isNotEmpty(specialCheck)) {
            builder.append(" and c.ChiOrderType=:specialCheck");
        }
        if(StringUtils.isNotEmpty(pathologyCode)) {
            builder.append(" and c.ChiPathologyCode=:pathologyCode");
        }

        if(StringUtils.isEmpty(startDate)) {
            startDate = "1980-01-01";
        }

        if(StringUtils.isEmpty(endDate)) {
            endDate = Constants.DF2.format(new Date());
        }
        builder.append(" and c.ChiReqTime  between to_date(:startDate,'yyyy-mm-dd') and to_date(:endDate,'yyyy-mm-dd') ");

        if(StringUtils.isNotEmpty(patientName)) {
            builder.append(" and s.SamPatientName like :patientName ");
        }
        if(StringUtils.isNotEmpty(orderState)) {
            builder.append(" and c.ChiOrderState =:orderState ");
        }
        builder.append(" group by o.OrderId,o.OrderCode,o.OrdSampleId,o.OrdCustomerId,o.OrdPathologyCode,o.OrdOrderUser,t.teschinesename,c.ChiOrderState,s.SamPathologyId,t.tesenglishname");
        builder.append(" order by o.orderid desc");

        SQLQuery query = getSession().createSQLQuery(builder.toString());
        if(StringUtils.isNotEmpty(specialCheck)) {
            query.setParameter("specialCheck", Long.valueOf(specialCheck.trim()));
        }
        if(StringUtils.isNotEmpty(pathologyCode)) {
            query.setParameter("pathologyCode", pathologyCode.trim());
        }
        if(StringUtils.isNotEmpty(patientName)) {
            query.setParameter("patientName", "%"+patientName.trim()+"%");
        }
        if(StringUtils.isNotEmpty(orderState)) {
            query.setParameter("orderState", Long.valueOf(orderState.trim()));
        }
        query.setParameter("startDate", startDate.trim());
        query.setParameter("endDate", endDate.trim());
        query.setFirstResult(gridQuery.getStart());
        query.setMaxResults(gridQuery.getEnd());
        return query.list();
    }

    @Override
    public Integer countOrders(String specialCheck, String pathologyCode, String startDate, String endDate, String patientName, String orderState, String ingore) {
        StringBuilder builder = new StringBuilder("select count(distinct(o.orderid)) cnt ");
        builder.append("from PIMS_PATHOLOGY_ORDER o, PIMS_PATHOLOGY_ORDER_CHILD c, Pims_Pathology_Sample s,PIMS_SYS_REQ_TESTITEM t where ");
        builder.append("O.Orderid=C.Chiorderid and c.ChiSampleId=s.sampleid and t.testitemid=c.testItemId ");
        if(ingore != null ) {
            builder.append("and t.tesenglishname !='").append(ingore).append("' ");
        }
        if(StringUtils.isNotEmpty(specialCheck)) {
            builder.append(" and c.ChiOrderType=:specialCheck");
        }
        if(StringUtils.isNotEmpty(pathologyCode)) {
            builder.append(" and c.ChiPathologyCode=:pathologyCode");
        }

        if((StringUtils.isEmpty(startDate))) {
            startDate = "1980-01-01";
        }

        if((StringUtils.isEmpty(endDate))) {
            endDate = Constants.DF2.format(new Date());
        }
        builder.append(" and c.ChiReqTime  between to_date(:startDate,'yyyy-mm-dd') and to_date(:endDate,'yyyy-mm-dd') ");

        if((StringUtils.isNotEmpty(patientName))) {
            builder.append(" and s.SamPatientName like :patientName ");
        }

        if((StringUtils.isNotEmpty(orderState))) {
            builder.append(" and c.ChiOrderState =:orderState ");
        }

        SQLQuery query = getSession().createSQLQuery(builder.toString());
        if(StringUtils.isNotEmpty(specialCheck)) {
            query.setParameter("specialCheck", Long.valueOf(specialCheck.trim()));
        }
        if(StringUtils.isNotEmpty(orderState)) {
            query.setParameter("orderState", Long.valueOf(orderState.trim()));
        }
        if(StringUtils.isNotEmpty(pathologyCode)) {
            query.setParameter("pathologyCode", pathologyCode.trim());
        }
        if(StringUtils.isNotEmpty(patientName)) {
            query.setParameter("patientName", "%"+patientName.trim()+"%");
        }
        query.setParameter("startDate", startDate.trim());
        query.setParameter("endDate", endDate.trim());
        return ((BigDecimal)query.uniqueResult()).intValue();
    }

    /**
     * 更新医嘱状态 设置操作人员
     * @param orderId 医嘱编号
     * @param orderState 医嘱状态：0已申请 1已接受 2已完成 3已签收 4已取消
     * @param user 当前操作用户
     */
    @Override
    public void updateOrderState(long orderId, long orderState, User user) {
        Long userId = user.getId();
        String name = user.getName();
        String sql;
        String sql1 = "update Pims_Pathology_Order set OrdOrderState=:orderState where OrderId=:orderId";
        String sql2 = "update Pims_Pathology_Order_Child set ChiOrderState=:orderState where ChiOrderId=:orderId";
        if(orderState == Constants.ORDER_STATE_ACCEPT) {
            sql = "update Pims_Pathology_Order set OrdOrderState=:orderState,OrdAcceptTime=:date,OrdAcceptorId=:userId,OrdAcceptorName=:name where OrderId=:orderId";
            executeUpdate(sql, orderState, userId, name, orderId);
            sql = "update Pims_Pathology_Order_Child set ChiOrderState=:orderState,ChiReceiveTime=:date,ChiReceiverId=:userId,ChiReceiverName=:name where ChiOrderId=:orderId";
            executeUpdate(sql, orderState, userId, name, orderId);
        } else if(orderState == Constants.ORDER_STATE_FINISH) {
            sql = "update Pims_Pathology_Order set OrdOrderState=:orderState,OrdFinishedTime=:date,OrdFinishedUserId=:userId,OrdFinishedUserName=:name where OrderId=:orderId";
            executeUpdate(sql, orderState, userId, name, orderId);
            executeUpdate1(sql2, orderState, orderId);
        } else if(orderState == Constants.ORDER_STATE_RECEIVING) {
            executeUpdate1(sql1, orderState, orderId);
            sql = "update Pims_Pathology_Order_Child set ChiOrderState=:orderState, ChiExecTime=:date,ChiExecUserId=:userId,ChiExecUserName=:name where ChiOrderId=:orderId";
            executeUpdate(sql, orderState, userId, name, orderId);
        } else if(orderState == Constants.ORDER_STATE_CANCEL) {
            executeUpdate1(sql1, orderState, orderId);
            executeUpdate1(sql2, orderState, orderId);
        }
    }

    @Override
    public List getSampleOrder(long sampleId) {
        SQLQuery query = getSession().createSQLQuery("select T.Teschinesename,T.Tesenglishname,T.Testitemid,T.Tesischarge from pims_pathology_order o,PIMS_PATHOLOGY_ORDER_CHILD c,PIMS_SYS_REQ_TESTITEM t where o.ordsampleid=:sampleId and o.orderid=c.chiorderid and C.Testitemid=T.Testitemid and T.Tesisorder=1 and o.ordorderstate < 4 group by T.Teschinesename,T.Tesenglishname,T.Testitemid,T.Tesischarge");
        query.setParameter("sampleId", sampleId);
        return query.list();
    }

    @Override
    public List getCheckItems(long sampleId, long testItemId) {
        SQLQuery query = getSession().createSQLQuery("select distinct(c.checkid),c.cheorderid,c.chechildorderid,c.cheorderitemid,c.chenamech,c.chenameen,c.cheischarge,chetestresult,c.checreatetime,c.checreateuser,c.finishstatus,c.paraffincode from PIMS_PATHOLOGY_ORDER_CHECK c,pims_pathology_order o,PIMS_PATHOLOGY_ORDER_CHILD oc where o.orderid=c.cheorderid and o.orderid=oc.chiorderid and o.ordsampleid=:sampleId and Oc.Testitemid=:testItemId order by C.Checreatetime desc");
        query.setParameter("sampleId", sampleId);
        query.setParameter("testItemId", testItemId);
        return query.list();
    }

    private void executeUpdate1(String sql, Long orderState, Long orderId) {
        SQLQuery query = getSession().createSQLQuery(sql);
        query.setParameter("orderState", orderState);
        query.setParameter("orderId", orderId);
        query.executeUpdate();
    }

    private void executeUpdate(String sql, Long orderState, Long userId, String name, Long orderId) {
        SQLQuery query = getSession().createSQLQuery(sql);
        query.setParameter("orderState", orderState);
        query.setParameter("date", new Date());
        query.setParameter("userId", userId);
        query.setParameter("name", name);
        query.setParameter("orderId", orderId);
        query.executeUpdate();
    }

    public StringBuffer getsql(StringBuffer sb,PimsBaseModel map){
        if(!StringUtils.isEmpty(map.getReq_sts())){
            sb.append(" and chiorderstate = " + map.getReq_sts());
        }
        if(!StringUtils.isEmpty(map.getReq_code())){
            if(map.getReq_code().equals("0")){
                sb.append(" and (chihandletype = 2 or chihandletype is null)");
            }else{
                sb.append(" and chihandletype = " + map.getReq_code());
            }
        }
        if(!StringUtils.isEmpty(map.getPatient_name())){
            sb.append(" and  ordorderuserid = '" +map.getPatient_name() +  "'");
        }
        return sb;
    }
    @Override
    public List getOrderList(PimsBaseModel map) {
        StringBuffer sb = new StringBuffer();
        sb.append(" from PimsPathologyOrder,PimsPathologyOrderChild,PimsPathologySample " +
                "where orderid = chiorderid and ordsampleid=sampleid and ordisdelete = 0 and chiisdelete = 0 ");
        getsql(sb,map);
        return pagingList(sb.toString(),map.getStart(),map.getEnd());
    }

    @Override
    public int getOrderNum(PimsBaseModel map) {
        StringBuffer sb = new StringBuffer();
        sb.append(" select count(1) from pims_pathology_order,pims_pathology_order_child,pims_pathology_sample " +
                "where orderid = chiorderid and ordsampleid=sampleid and ordisdelete = 0 and chiisdelete = 0 ");
        getsql(sb,map);
        return countTotal(sb.toString());
    }
}
