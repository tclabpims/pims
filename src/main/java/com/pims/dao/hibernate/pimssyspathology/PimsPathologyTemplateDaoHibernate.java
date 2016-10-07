package com.pims.dao.hibernate.pimssyspathology;

import com.pims.dao.pimssyspathology.PimsPathologyTemplateDao;
import com.pims.model.PimsPathologyTemplate;
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
}
