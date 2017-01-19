package com.pims.dao.hibernate.pimssyspathology;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pims.dao.pimssyspathology.PimsPathologyOrderCheckDao;
import com.pims.model.PimsPathologyOrderCheck;
import com.smart.dao.hibernate.GenericDaoHibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by 909436637@qq.com on 2016/11/4.
 * Description:
 */
@Repository("pathologyOrderCheckDao")
public class PimsPathologyOrderCheckDaoHibernate extends GenericDaoHibernate<PimsPathologyOrderCheck, Long> implements PimsPathologyOrderCheckDao {
    /**
     * Constructor that takes in a class to see which type of entity to persist.
     * Use this constructor when subclassing.
     */
    public PimsPathologyOrderCheckDaoHibernate() {
        super(PimsPathologyOrderCheck.class);
    }

    @Override
    public void batchSave(List<PimsPathologyOrderCheck> checkItems) {
        Session session = getSession();
        //Transaction transaction = session.beginTransaction();
        for(PimsPathologyOrderCheck ci : checkItems) {
            save(ci);
        }
        //transaction.commit();
        //session.flush();
       // session.close();
    }

    @Override
    public List<PimsPathologyOrderCheck> getOrderCheckByOrderId(long orderId) {
        StringBuffer sb = new StringBuffer();
        sb.append(" from PimsPathologyOrderCheck where cheorderid=:orderId");
        Query query = getSession().createQuery(sb.toString());
        query.setParameter("orderId", orderId);
        return query.list();
    }

    @Override
    public List<PimsPathologyOrderCheck> getOrderCheckByOrderChildId(long orderId) {
        StringBuffer sb = new StringBuffer();
        sb.append(" from PimsPathologyOrderCheck where chechildorderid=:chechildorderid");
        Query query = getSession().createQuery(sb.toString());
        query.setParameter("chechildorderid", orderId);
        return query.list();
    }

    @Override
    public List calCheckItemCharge(Set<Long> checkItemId, long ordcustomercode) {
        String sql = "select Rf.Refhischargename,Rf.Refhisprice,Rf.Referenceid,ps.chargeitemid from PIMS_SYS_CHARGEITEM_REF rf,PIMS_SYS_CHARGE_ITEMS ps where Rf.Chargeitemid=ps.chargeitemid and ps.testitemid in(:checkItemId) and rf.CUSTOMERID=:ordcustomercode";
        SQLQuery query = getSession().createSQLQuery(sql);
        query.setParameterList("checkItemId", checkItemId);
        query.setParameter("ordcustomercode", ordcustomercode);
        return query.list();
    }

    /**
     * 获取医嘱收费列表
     * @param orderId
     * @return
     */
    @Override
    public List orderFeeList(long orderId) {
        StringBuffer sb = new StringBuffer();
        sb.append(" from PimsPathologyFee where feecategory = '"+ orderId + "'");
        return getSession().createQuery(sb.toString()).list();
    }

    @Override
    public void updateTestResult(JSONArray json, String name) {
        String sql = "update Pims_Pathology_Order_Check c set c.CheTestResult=:result,c.CheResultTime=:dateTime,c.CheResultUser=:name where c.CheOrderId=:orderId and c.CheOrderItemId=:testItemId";
        SQLQuery query = getSession().createSQLQuery(sql);
        for(Object obj : json) {
            JSONObject jsonObject = (JSONObject)obj;
            query.setParameter("result", jsonObject.get("chetestresult"));
            query.setParameter("dateTime", new Date());
            query.setParameter("name", name);
            query.setParameter("orderId", jsonObject.get("cheorderid"));
            query.setParameter("testItemId", jsonObject.get("cheorderitemid"));
            query.executeUpdate();
        }
    }

    @Override
    public void removeItems(Set<Long> oldItems, long orderId) {
        String sql = "delete from Pims_Pathology_Order_Check where CHEORDERITEMID not in(:oldItems) and CHEORDERID=:orderId";
        SQLQuery query = getSession().createSQLQuery(sql);
        query.setParameterList("oldItems", oldItems);
        query.setParameter("orderId", orderId);
        query.executeUpdate();
    }

    @Override
    public void removeByOrderId(long orderId) {
        String sql = "delete from Pims_Pathology_Order_Check where CHEORDERID=:orderId";
        SQLQuery query = getSession().createSQLQuery(sql);
        query.setParameter("orderId", orderId);
        query.executeUpdate();
    }

    @Override
    public void updateItemStatus(Set<Long> s) {
        SQLQuery query = getSession().createSQLQuery("update Pims_Pathology_Order_Check set finishStatus=1 where checkid in(:items)");
        query.setParameterList("items", s);
        query.executeUpdate();
    }

    @Override
    public void saveResult(long checkid, String chetestresult) {
        SQLQuery query = getSession().createSQLQuery("update Pims_Pathology_Order_Check set chetestresult=:chetestresult where checkid=:checkid");
        query.setParameter("checkid", checkid);
        query.setParameter("chetestresult", chetestresult);
        query.executeUpdate();
    }
}
