package com.pims.dao.hibernate.pimssyspathology;

import com.pims.dao.pimssyspathology.PimsPathologyOrderDao;
import com.pims.model.PimsPathologyOrder;
import com.smart.dao.hibernate.GenericDaoHibernate;
import org.springframework.stereotype.Repository;

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
}
