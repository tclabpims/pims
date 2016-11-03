package com.pims.dao.hibernate.pimssyspathology;

import com.pims.dao.pimssyspathology.PimsSysPackageDetailDao;
import com.pims.model.PimsSysPackageDetail;
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
@Repository("packageDetailDao")
public class PimsSysPackageDetailDaoHibernate extends GenericDaoHibernate<PimsSysPackageDetail, Long> implements PimsSysPackageDetailDao {
    /**
     * Constructor that takes in a class to see which type of entity to persist.
     * Use this constructor when subclassing.
     */
    public PimsSysPackageDetailDaoHibernate() {
        super(PimsSysPackageDetail.class);
    }

    @Override
    public void deleteByPackageId(long packageId) {
        Query query = getSession().createQuery("delete from PimsSysPackageDetail where packageId=:packageId");
        query.setParameter("packageId", packageId).executeUpdate();
    }

    @Override
    public void batchSave(List<PimsSysPackageDetail> lis) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        for(PimsSysPackageDetail detail : lis) {
            session.save(detail);
        }
        transaction.commit();
        session.flush();
        session.close();
    }
}
