package com.pims.dao.hibernate.pimssyspathology;

import com.pims.dao.pimssyspathology.PimsPathologyOrderChildDao;
import com.pims.model.PimsPathologyOrderChild;
import com.smart.dao.hibernate.GenericDaoHibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 909436637@qq.com on 2016/11/4.
 * Description:
 */
@Repository("pathologyOrderChildDao")
public class PimsPathologyOrderChildDaoHibernate extends GenericDaoHibernate<PimsPathologyOrderChild, Long> implements PimsPathologyOrderChildDao {
    /**
     * Constructor that takes in a class to see which type of entity to persist.
     * Use this constructor when subclassing.
     */
    public PimsPathologyOrderChildDaoHibernate() {
        super(PimsPathologyOrderChild.class);
    }

    @Override
    public PimsPathologyOrderChild getChildByOrderId(long orderId) {
        Query query = getSession().createQuery("from PimsPathologyOrderChild where chiorderid=:orderId");
        query.setParameter("orderId", orderId);
        return (PimsPathologyOrderChild) query.uniqueResult();
    }

    @Override
    public List getOrderChildChargeItem(long testItemId, long ordcustomercode) {
        SQLQuery query = getSession().createSQLQuery("select Rf.Refhischargename,Rf.Refhisprice,Rf.Referenceid,ps.chargeitemid from PIMS_SYS_CHARGEITEM_REF rf,PIMS_SYS_CHARGE_ITEMS ps where Rf.Chargeitemid=ps.chargeitemid and ps.testitemid=:testItemId and rf.CUSTOMERID=:ordcustomercode");
        query.setParameter("testItemId", testItemId);
        query.setParameter("ordcustomercode", ordcustomercode);
        return query.list();
    }

    @Override
    public void updateWhitePiece(Long orderChildId, Long inventory) {
        SQLQuery query = getSession().createSQLQuery("update PIMS_PATHOLOGY_ORDER_CHILD set CHINULLSLIDENUM=:inventory where CHILDORDERID=:orderChildId");
        query.setParameter("inventory", inventory);
        query.setParameter("orderChildId", orderChildId);
        query.executeUpdate();
    }
}
