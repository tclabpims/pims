package com.pims.dao.hibernate.pimssyspathology;

import com.pims.dao.pimssyspathology.PimsPathologyFollowupDao;
import com.pims.model.PimsPathologyFollowup;
import com.smart.dao.hibernate.GenericDaoHibernate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * Created by 909436637@qq.com on 2016/11/24.
 * Description:
 */
@Repository("pimsPathologyFollowupDao")
public class PimsPathologyFollowupDaoHibernate extends GenericDaoHibernate<PimsPathologyFollowup, Long> implements PimsPathologyFollowupDao {


    /**
     * Constructor that takes in a class to see which type of entity to persist.
     * Use this constructor when subclassing.
     */
    public PimsPathologyFollowupDaoHibernate() {
        super(PimsPathologyFollowup.class);
    }
}
