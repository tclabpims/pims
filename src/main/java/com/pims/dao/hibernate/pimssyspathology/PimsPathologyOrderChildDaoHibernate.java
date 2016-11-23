package com.pims.dao.hibernate.pimssyspathology;

import com.pims.dao.pimssyspathology.PimsPathologyOrderChildDao;
import com.pims.model.PimsPathologyOrderChild;
import com.smart.dao.hibernate.GenericDaoHibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
        PimsPathologyOrderChild child = new PimsPathologyOrderChild();
        SQLQuery query = getSession().createSQLQuery("select C.Childorderid,c.chiorderid,c.chiordercode,c.chipathologycode,c.chicustomerid,c.chisampleid,c.chiparaffinid,c.chiparaffinno,c.chiparaffincode,c.chireqtime,c.chirequserid,c.chirequsername,c.chiordertype,c.chicreatetime,c.chicreateuser,c.testitemid, (select sum(Oc.Chinullslidenum)+sum(Oc.Chislidenum) from Pims_Pathology_Order_Child oc where oc.Chiorderid=:orderId) as totalnullslidenum from Pims_Pathology_Order_Child c where C.Chiorderid=:orderId and rownum=1");
        query.setParameter("orderId", orderId);
        Object[] data = (Object[])query.uniqueResult();
        child.setChildorderid(((BigDecimal)data[0]).longValue());
        child.setChiorderid(((BigDecimal)data[1]).longValue());
        child.setChiordercode((String)data[2]);
        child.setChipathologycode((String)data[3]);
        child.setChicustomercode(((BigDecimal)data[4]).longValue());
        child.setChisampleid(((BigDecimal)data[5]).longValue());
        child.setChiparaffinid(((BigDecimal)data[6]).longValue());
        child.setChiparaffinno(((BigDecimal)data[7]).longValue());
        child.setChiparaffincode((String)data[8]);
        child.setChireqtime((Date) data[9]);
        child.setChirequserid((String)data[10]);
        child.setChirequsername((String)data[11]);
        child.setChiordertype(((BigDecimal)data[12]).longValue());
        child.setChicreatetime((Date) data[13]);
        child.setChicreateuser((String)data[14]);
        child.setTestItemId(((BigDecimal)data[15]).longValue());
        child.setChinullslidenum(((BigDecimal)data[16]).longValue());
        return child;
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

    @Override
    public List<PimsPathologyOrderChild> getChildList(long oderId) {
        Query query = getSession().createQuery("from PimsPathologyOrderChild where chiorderid=:oderId order by childorderid desc");
        query.setParameter("oderId", oderId);
        return query.list();
    }

    @Override
    public void updateChildItemStatus(Set<Long> s) {
        SQLQuery query = getSession().createSQLQuery("update PIMS_PATHOLOGY_ORDER_CHILD set finishstatus=1 where CHILDORDERID in(:orderChildId)");
        query.setParameterList("orderChildId", s);
        query.executeUpdate();
    }

    @Override
    public Integer getMaxPieceNo(long sampleId) {
        SQLQuery query = getSession().createSQLQuery("select max(to_number(piesamplingno)) from PIMS_PATHOLOGY_PIECES where piesampleid=:sampleId");
        query.setParameter("sampleId", sampleId);
        return ((BigDecimal) query.uniqueResult()).intValue();
    }
}
