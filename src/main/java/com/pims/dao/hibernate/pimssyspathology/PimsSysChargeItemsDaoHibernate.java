package com.pims.dao.hibernate.pimssyspathology;

import com.pims.dao.pimssyspathology.PimsSysChargeItemsDao;
import com.pims.model.PimsSysChargeItems;
import com.smart.dao.hibernate.GenericDaoHibernate;
import org.springframework.stereotype.Repository;

/**
 * Created by 909436637@qq.com on 2016/10/11.
 * Description:
 */
@Repository("pimsSysChargeItemsDao")
public class PimsSysChargeItemsDaoHibernate extends GenericDaoHibernate<PimsSysChargeItems, Long> implements PimsSysChargeItemsDao {
    /**
     * Constructor that takes in a class to see which type of entity to persist.
     * Use this constructor when subclassing.
     */
    public PimsSysChargeItemsDaoHibernate() {
        super(PimsSysChargeItems.class);
    }
}
