package com.pims.dao.hibernate.pimssyspathology;

import com.pims.dao.pimssyspathology.PimsPathologyFavoriteDao;
import com.pims.model.PimsPathologyFavorite;
import com.smart.dao.hibernate.GenericDaoHibernate;
import org.springframework.stereotype.Repository;

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
}
