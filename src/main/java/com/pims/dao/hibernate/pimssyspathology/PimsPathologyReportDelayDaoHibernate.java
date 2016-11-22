package com.pims.dao.hibernate.pimssyspathology;

import com.pims.dao.pimssyspathology.PimsPathologyReportDelayDao;
import com.pims.model.PimsPathologyReportDelay;
import com.smart.dao.hibernate.GenericDaoHibernate;
import org.springframework.stereotype.Repository;

/**
 * Created by 909436637@qq.com on 2016/11/21.
 * Description:
 */
@Repository("pimsPathologyReportDelayDao")
public class PimsPathologyReportDelayDaoHibernate extends GenericDaoHibernate<PimsPathologyReportDelay, Long> implements PimsPathologyReportDelayDao {
    /**
     * Constructor that takes in a class to see which type of entity to persist.
     * Use this constructor when subclassing.
     */
    public PimsPathologyReportDelayDaoHibernate() {
        super(PimsPathologyReportDelay.class);
    }
}
