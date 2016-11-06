package com.pims.dao.hibernate.pimssyspathology;

import com.pims.dao.pimssyspathology.PimsPathologyOrderCheckDao;
import com.pims.model.PimsPathologyOrderCheck;
import com.smart.dao.hibernate.GenericDaoHibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 909436637@qq.com on 2016/11/4.
 * Description:
 */
@Repository("pathologyOrderCheckDao")
public class PimsPathologyOrderCheckDaoHibernate extends GenericDaoHibernate<PimsPathologyOrderCheck, Long> implements PimsPathologyOrderCheckDao {
    /**
     * Constructor that takes in a class to see which type of entity to persist.
     * Use this constructor when subclassing.
     */
    public PimsPathologyOrderCheckDaoHibernate() {
        super(PimsPathologyOrderCheck.class);
    }

    @Override
    public void batchSave(List<PimsPathologyOrderCheck> checkItems) {
        Session session = getSession();
        //Transaction transaction = session.beginTransaction();
        for(PimsPathologyOrderCheck ci : checkItems) {
            save(ci);
        }
        //transaction.commit();
        //session.flush();
       // session.close();
    }
}
