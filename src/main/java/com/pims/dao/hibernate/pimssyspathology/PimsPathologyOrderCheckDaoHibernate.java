package com.pims.dao.hibernate.pimssyspathology;

import com.pims.dao.pimssyspathology.PimsPathologyOrderCheckDao;
import com.pims.model.PimsPathologyOrderCheck;
import com.smart.dao.hibernate.GenericDaoHibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

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
        Query query = getSession().createQuery(" from PimsPathologyOrderCheck where cheorderid=:orderId");
        query.setParameter("orderId", orderId);
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
}
