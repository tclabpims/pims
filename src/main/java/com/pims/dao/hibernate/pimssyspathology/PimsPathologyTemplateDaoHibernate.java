package com.pims.dao.hibernate.pimssyspathology;

import com.pims.dao.pimssyspathology.PimsPathologyTemplateDao;
import com.pims.model.PimsPathologyTemplate;
import com.pims.webapp.controller.GridQuery;
import com.smart.dao.hibernate.GenericDaoHibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 909436637@qq.com on 2016/10/6.
 * Description:
 */
@Repository("pimsPathologyTemplateDao")
public class PimsPathologyTemplateDaoHibernate  extends GenericDaoHibernate<PimsPathologyTemplate, Long> implements PimsPathologyTemplateDao {
    /**
     * Constructor that takes in a class to see which type of entity to persist.
     * Use this constructor when subclassing.
     */
    public PimsPathologyTemplateDaoHibernate() {
        super(PimsPathologyTemplate.class);
    }

    @Override
    public List sqlPagingQuery(String qstr, int start, int end) {
        SQLQuery query = getSession().createSQLQuery(qstr);
        query.setFirstResult(start);
        query.setMaxResults(end);
        return query.list();
    }

    @Override
    public List<PimsPathologyTemplate> getTemplateList(GridQuery gridQuery, Long tempType, Long pathologyLibId, String hql) {
        Query query = setParameter(hql, gridQuery.getUserId(), gridQuery.getHospitalId(), pathologyLibId, tempType);
        query.setFirstResult(gridQuery.getStart());
        query.setMaxResults(gridQuery.getEnd());
        return query.list();
    }

    @Override
    public Integer countTemplate(Long userId, Long hospitalId, Long tempType, Long pathologyLibId, String s) {
        Query query = setParameter(s, userId, hospitalId, pathologyLibId, tempType);
        return ((Long)query.uniqueResult()).intValue();
    }

    private Query setParameter(String s, Long userId, Long hospitalId, Long pathologyLibId, Long tempType) {
        Query query = getSession().createQuery(s);
        query.setParameter("userId", String.valueOf(userId));
        query.setParameter("hospitalId", hospitalId);
        query.setParameter("tempType", tempType);
        query.setParameter("pathologyLibId", pathologyLibId);
        return query;
    }
}
