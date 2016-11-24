package com.pims.dao.hibernate.pimssyspathology;

import com.pims.dao.pimssyspathology.PimsPathologyFavoriteDao;
import com.pims.model.PimsPathologyFavorite;
import com.pims.webapp.controller.GridQuery;
import com.smart.dao.hibernate.GenericDaoHibernate;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by 909436637@qq.com on 2016/11/24.
 * Description:
 */
@Repository("pimsPathologyFavoriteDao")
public class PimsPathologyFavoriteDaoHibernate extends GenericDaoHibernate<PimsPathologyFavorite, Long> implements PimsPathologyFavoriteDao {


    /**
     * Constructor that takes in a class to see which type of entity to persist.
     * Use this constructor when subclassing.
     */
    public PimsPathologyFavoriteDaoHibernate() {
        super(PimsPathologyFavorite.class);
    }

    @Override
    public Integer myFavorite(String username) {
        Query query = getSession().createQuery("select count(*) from PimsPathologyFavorite where favowner=:username");
        query.setParameter("username", username);
        return ((Long)query.uniqueResult()).intValue();
    }

    @Override
    public List<PimsPathologyFavorite> queryMyFavorite(GridQuery query, String userName) {
        Query q = getSession().createQuery("from PimsPathologyFavorite where favowner=:username order by favoriteid desc");
        q.setParameter("username", userName);
        q.setFirstResult(query.getStart());
        q.setMaxResults(query.getEnd());
        return q.list();
    }
}
