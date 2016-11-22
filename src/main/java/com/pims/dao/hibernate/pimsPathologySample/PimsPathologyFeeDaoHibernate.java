package com.pims.dao.hibernate.pimsPathologySample;

import com.pims.dao.pimspathologysample.PimsPathologyFeeDao;
import com.pims.model.PimsPathologyFee;
import com.smart.dao.hibernate.GenericDaoHibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by king on 2016/10/10.
 */
@Repository("pimsPathologyFeeDao")
public class PimsPathologyFeeDaoHibernate extends GenericDaoHibernate<PimsPathologyFee, Long> implements PimsPathologyFeeDao {

    public PimsPathologyFeeDaoHibernate() {
        super(PimsPathologyFee.class);
    }

    /**
     * 根据费用来源查询病理的收费列表
     * @param map
     * @return
     */
    @Override
    public List<PimsPathologyFee> getSampleList(PimsPathologyFee map) {
        StringBuffer sb = new StringBuffer();
        sb.append(" from PimsPathologyFee where feesampleid="+map.getFeesampleid());
        return getSession().createQuery(sb.toString()).list();
    }

    @Override
    public List getChargeItems(long ordcustomercode, Set<Long> testItem) {
        StringBuilder builder = new StringBuilder("select ci.chargeitemid,ci.chichinesename,ci.chienglishname,ci.chiprice,ci.testitemid,cr.refhischargeid,cr.refhischargename,cr.refhisprice");
        builder.append(" from pims_sys_charge_items ci,pims_sys_chargeitem_ref cr where ci.chargeitemid=cr.chargeitemid");
        builder.append(" and cr.customerid=:ordcustomercode and ci.testitemid in(:testItem) and ci.chiuseflag=1");
        SQLQuery query = getSession().createSQLQuery(builder.toString());
        query.setParameter("ordcustomercode", ordcustomercode);
        query.setParameterList("testItem", testItem);
        return query.list();
    }
}
