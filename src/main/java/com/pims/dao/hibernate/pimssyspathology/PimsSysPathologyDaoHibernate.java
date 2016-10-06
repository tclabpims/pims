package com.pims.dao.hibernate.pimssyspathology;

import com.pims.dao.pimssyspathology.PimsSysPathologyDao;
import com.pims.model.PimsSysPathology;
import com.smart.dao.hibernate.GenericDaoHibernate;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by 909436637@qq.com on 2016/9/28.
 * Description:病种维护DAO
 */
@Repository("pimsSysPathologyDao")
public class PimsSysPathologyDaoHibernate extends GenericDaoHibernate<PimsSysPathology, Long> implements PimsSysPathologyDao {

    public  PimsSysPathologyDaoHibernate() {
        super(PimsSysPathology.class);
    }



    @Override
    public List<PimsSysPathology> getPimsSysPathology(String hql, int offset, int pageSize, String sortColumn, String direct) {
        DetachedCriteria dc = DetachedCriteria.forClass(PimsSysPathology.class, "psp");
        dc.add((Criterion) Property.forName("")).equals(hql);
        return null;
    }

    @Override
    public List<PimsSysPathology> getPimsSysPathologyList(PimsSysPathology pimsSysPathology, int offset, int pageSize, String sortColumn, String direct) {
        Criteria criteria = getSession().createCriteria(PimsSysPathology.class);
        return null;
    }

    @Override
    public List<PimsSysPathology> getPimsSysPathologyList(String s, int start, int end) {

        return pagingList(s, start, end);
    }

    @Override
    public Integer getPimsSysPathologyTotal(String s) {

        return countTotal(s);
    }
}
