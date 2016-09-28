package com.pims.dao.hibernate.pimssyspathology;

import com.pims.dao.pimssyspathology.PimsSysPathologyDao;
import com.pims.model.PimsSysPathology;
import com.smart.dao.hibernate.GenericDaoHibernate;
import org.springframework.stereotype.Repository;

/**
 * Created by 909436637@qq.com on 2016/9/28.
 * Description:病种维护DAO
 */
@Repository("pimsSysPathologyDao")
public class PimsSysPathologyDaoHibernate extends GenericDaoHibernate<PimsSysPathology, Long> implements PimsSysPathologyDao {
    /**
     * Constructor that takes in a class to see which type of entity to persist.
     * Use this constructor when subclassing.
     *
     * @param persistentClass the class type you'd like to persist
     */
    public PimsSysPathologyDaoHibernate(Class<PimsSysPathology> persistentClass) {
        super(persistentClass);
    }
}
