package com.pims.dao.hibernate.pimssyspathology;

import com.pims.dao.pimssyspathology.PimsSysColorDao;
import com.pims.model.PimsSysColor;
import com.smart.dao.hibernate.GenericDaoHibernate;
import org.springframework.stereotype.Repository;

/**
 * Created by 909436637@qq.com on 2016/10/10.
 * Description:
 */
@Repository("pimsSysColorDao")
public class PimsSysColorDaoHibernate extends GenericDaoHibernate<PimsSysColor, Long> implements PimsSysColorDao {
    /**
     * Constructor that takes in a class to see which type of entity to persist.
     * Use this constructor when subclassing.
     */
    public PimsSysColorDaoHibernate() {
        super(PimsSysColor.class);
    }
}
