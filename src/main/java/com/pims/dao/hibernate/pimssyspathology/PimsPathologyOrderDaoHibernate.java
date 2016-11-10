package com.pims.dao.hibernate.pimssyspathology;

import com.pims.dao.pimssyspathology.PimsPathologyOrderDao;
import com.pims.model.PimsPathologyOrder;
import com.pims.webapp.controller.GridQuery;
import com.smart.Constants;
import com.smart.dao.hibernate.GenericDaoHibernate;
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
    public List getOrders(GridQuery gridQuery, String specialCheck, String pathologyCode, String startDate, String endDate, String patientName, String orderState) {
        StringBuilder builder = new StringBuilder("select o.OrderId,o.OrderCode,o.OrdSampleId,o.OrdCustomerId,o.OrdPathologyCode,o.OrdOrderUser,t.teschinesename,c.ChiOrderState,s.SamPathologyId ");
        builder.append("from PIMS_PATHOLOGY_ORDER o, PIMS_PATHOLOGY_ORDER_CHILD c, Pims_Pathology_Sample s,PIMS_SYS_REQ_TESTITEM t where ");
        builder.append("O.Orderid=C.Chiorderid and c.ChiSampleId=s.sampleid and t.testitemid=c.testItemId ");
        if(specialCheck != null && !"".equals(specialCheck.trim())) {
            builder.append(" and c.ChiOrderType=:specialCheck");
        }
        if(pathologyCode != null && !"".equals(pathologyCode.trim())) {
            builder.append(" and c.ChiPathologyCode=:pathologyCode");
        }

        if(startDate == null || "".equals(startDate.trim())) {
            startDate = "1980-01-01";
        }

        if(endDate == null || "".equals(endDate.trim())) {
            endDate = Constants.DF2.format(new Date());
        }
        builder.append(" and c.ChiReqTime  between to_date(:startDate,'yyyy-mm-dd') and to_date(:endDate,'yyyy-mm-dd') ");

        if(patientName != null && !"".equals(patientName.trim())) {
            builder.append(" and s.SamPatientName like '%:patientName%' ");
        }
        if(orderState != null && !"".equals(orderState.trim())) {
            builder.append(" and c.ChiOrderState =:orderState ");
        }
        builder.append(" order by o.orderid desc");

        SQLQuery query = getSession().createSQLQuery(builder.toString());
        if(specialCheck != null && !"".equals(specialCheck.trim())) {
            query.setParameter("specialCheck", Long.valueOf(specialCheck));
        }
        if(pathologyCode != null && !"".equals(pathologyCode.trim())) {
            query.setParameter("pathologyCode", pathologyCode);
        }
        if(patientName != null && !"".equals(patientName.trim())) {
            query.setParameter("patientName", patientName);
        }
        if(orderState != null && !"".equals(orderState.trim())) {
            query.setParameter("orderState", Long.valueOf(orderState));
        }
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        query.setFirstResult(gridQuery.getStart());
        query.setMaxResults(gridQuery.getEnd());
        return query.list();
    }

    @Override
    public Integer countOrders(String specialCheck, String pathologyCode, String startDate, String endDate, String patientName, String orderState) {
        StringBuilder builder = new StringBuilder("select count(1) cnt ");
        builder.append("from PIMS_PATHOLOGY_ORDER o, PIMS_PATHOLOGY_ORDER_CHILD c, Pims_Pathology_Sample s,PIMS_SYS_REQ_TESTITEM t where ");
        builder.append("O.Orderid=C.Chiorderid and c.ChiSampleId=s.sampleid and t.testitemid=c.testItemId ");
        if(specialCheck != null && !"".equals(specialCheck.trim())) {
            builder.append(" and c.ChiOrderType=:specialCheck");
        }
        if(pathologyCode != null && !"".equals(pathologyCode.trim())) {
            builder.append(" and c.ChiPathologyCode=:pathologyCode");
        }

        if(startDate == null || "".equals(startDate.trim())) {
            startDate = "1980-01-01";
        }

        if(endDate == null || "".equals(endDate.trim())) {
            endDate = Constants.DF2.format(new Date());
        }
        builder.append(" and c.ChiReqTime  between to_date(:startDate,'yyyy-mm-dd') and to_date(:endDate,'yyyy-mm-dd') ");

        if(patientName != null && !"".equals(patientName.trim())) {
            builder.append(" and s.SamPatientName like '%:patientName%' ");
        }

        if(orderState != null && !"".equals(orderState.trim())) {
            builder.append(" and c.ChiOrderState =:orderState ");
        }

        SQLQuery query = getSession().createSQLQuery(builder.toString());
        if(specialCheck != null && !"".equals(specialCheck.trim())) {
            query.setParameter("specialCheck", Long.valueOf(specialCheck));
        }
        if(orderState != null && !"".equals(orderState.trim())) {
            query.setParameter("orderState", Long.valueOf(orderState));
        }
        if(pathologyCode != null && !"".equals(pathologyCode.trim())) {
            query.setParameter("pathologyCode", pathologyCode);
        }
        if(patientName != null && !"".equals(patientName.trim())) {
            query.setParameter("patientName", patientName);
        }
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        return ((BigDecimal)query.uniqueResult()).intValue();
    }
}
