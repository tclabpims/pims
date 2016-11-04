package com.pims.dao.hibernate.pimssyspathology;

import com.pims.dao.pimssyspathology.PimsTestItemPackageDao;
import com.pims.model.PimsSysPackageDetail;
import com.pims.model.PimsTestItemPackage;
import com.smart.dao.hibernate.GenericDaoHibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 909436637@qq.com on 2016/11/3.
 * Description:
 */
@Repository("packageDao")
public class PimsTestItemPackageDaoHibernate extends GenericDaoHibernate<PimsTestItemPackage, Long> implements PimsTestItemPackageDao {
    /**
     * Constructor that takes in a class to see which type of entity to persist.
     * Use this constructor when subclassing.
     */
    public PimsTestItemPackageDaoHibernate() {
        super(PimsTestItemPackage.class);
    }


    @Override
    public List<PimsTestItemPackage> getPackageItems(Long aLong) {
        String hql = "from PimsTestItemPackage where pathologyId=:pathologyId";
        Query query = getSession().createQuery(hql);
        query.setParameter("pathologyId", aLong);
        return query.list();
    }
}
