package com.pims.dao.hibernate.pimssyspathology;

import com.pims.dao.pimssyspathology.PimsSampleResultDao;
import com.pims.model.PimsSampleResult;
import com.smart.Constants;
import com.smart.dao.hibernate.GenericDaoHibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by 909436637@qq.com on 2016/10/25.
 * Description:
 */
@Service("pimsSampleResultDao")
public class PimsSampleResultDaoHibernate extends GenericDaoHibernate<PimsSampleResult, Long> implements PimsSampleResultDao {
    /**
     * Constructor that takes in a class to see which type of entity to persist.
     * Use this constructor when subclassing.
     */
    public PimsSampleResultDaoHibernate() {
        super(PimsSampleResult.class);
    }

    @Override
    public Map<String, Long> save(List<PimsSampleResult> set, int patClass) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        Map<String, Long> result = new HashMap<>();
        int i = 0;
        for (PimsSampleResult aSet : set) {
            PimsSampleResult sampleResult = (PimsSampleResult) session.merge(aSet);
            if(patClass == 2) {
                result.put(Constants.YJXB_INPUT_PREFIX+i, sampleResult.getResultid());
                i++;
            } else
            result.put(String.valueOf(aSet.getRestestitemid()), sampleResult.getResultid());
        }
        transaction.commit();
        session.flush();
        session.close();
        return result;
    }

    @Override
    public List<PimsSampleResult> getSampleResult(Long sampleId) {
        return getSession().createQuery("from PimsSampleResult where ressampleid=:sampleId").setParameter("sampleId", sampleId).list();
    }

    @Override
    public PimsSampleResult getSampleResultForPrint(Long sampleId) {
        String hql = "select r from com.pims.model.PimsSysCustomerBasedata d, com.pims.model.PimsSampleResult r where d.basrptItemSort=1 and d.basrefdataid=r.restestitemid and r.ressampleid=:sampleId";
        List lis = getSession().createQuery(hql).setParameter("sampleId", sampleId).list();
        if(lis.size() > 0 ) return (PimsSampleResult) lis.get(0);
        return null;
    }
}
