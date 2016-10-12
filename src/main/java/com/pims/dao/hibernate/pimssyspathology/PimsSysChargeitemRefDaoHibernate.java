package com.pims.dao.hibernate.pimssyspathology;

import com.pims.dao.pimssyspathology.PimsSysChargeitemRefDao;
import com.pims.model.PimsSysChargeitemRef;
import com.smart.dao.hibernate.GenericDaoHibernate;
import org.springframework.stereotype.Repository;

/**
 * Created by 909436637@qq.com on 2016/10/11.
 * Description:
 */
@Repository("pimsSysChargeitemRefDao")
public class PimsSysChargeitemRefDaoHibernate extends GenericDaoHibernate<PimsSysChargeitemRef, Long> implements PimsSysChargeitemRefDao {
    /**
     * Constructor that takes in a class to see which type of entity to persist.
     * Use this constructor when subclassing.
     */
    public PimsSysChargeitemRefDaoHibernate() {
        super(PimsSysChargeitemRef.class);
    }
}
