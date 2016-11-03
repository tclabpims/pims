package com.pims.dao.hibernate.pimssyspathology;

import com.pims.dao.pimssyspathology.PimsSysCustomerBasedataDao;
import com.pims.model.PimsSysCustomerBasedata;
import com.smart.dao.hibernate.GenericDaoHibernate;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 909436637@qq.com on 2016/10/12.
 * Description:
 */
@Repository("pimsSysCustomerBasedataDao")
public class PimsSysCustomerBasedataDaoHibernate extends GenericDaoHibernate<PimsSysCustomerBasedata, Long> implements PimsSysCustomerBasedataDao {

    /**
     * Constructor that takes in a class to see which type of entity to persist.
     * Use this constructor when subclassing.
     */
    public PimsSysCustomerBasedataDaoHibernate() {
        super(PimsSysCustomerBasedata.class);
    }

    @Override
    public List<PimsSysCustomerBasedata> getCustomerDataList(String sql, Long hospitalId, Long pathologyId) {
        SQLQuery query = getSession().createSQLQuery(sql);
        query.setParameter("hospitalId",hospitalId);
        query.setParameter("pathologyId",pathologyId);
        query.addEntity("pb",PimsSysCustomerBasedata.class);
        return query.list();
    }
}
