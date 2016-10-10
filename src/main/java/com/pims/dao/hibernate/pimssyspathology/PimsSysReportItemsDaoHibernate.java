package com.pims.dao.hibernate.pimssyspathology;

import com.pims.dao.pimssyspathology.PimsSysReportItemsDao;
import com.pims.model.PimsSysReportItems;
import com.smart.dao.hibernate.GenericDaoHibernate;
import org.springframework.stereotype.Repository;

/**
 * Created by 909436637@qq.com on 2016/10/10.
 * Description:
 */
@Repository("pimsSysReportItemsDao")
public class PimsSysReportItemsDaoHibernate extends GenericDaoHibernate<PimsSysReportItems, Long> implements PimsSysReportItemsDao {
    /**
     * Constructor that takes in a class to see which type of entity to persist.
     * Use this constructor when subclassing.
     */
    public PimsSysReportItemsDaoHibernate() {
        super(PimsSysReportItems.class);
    }
}
