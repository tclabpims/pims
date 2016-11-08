package com.pims.dao.hibernate.pimssyspathology;

import com.pims.dao.pimssyspathology.PimsPathologyOrderChildDao;
import com.pims.model.PimsPathologyOrderChild;
import com.smart.dao.hibernate.GenericDaoHibernate;
import org.springframework.stereotype.Repository;

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
}
