package com.pims.dao.hibernate.pimssyspathology;

import com.pims.dao.pimssyspathology.PimsHospitalPathologyInfoDao;
import com.pims.model.PimsHospitalPathologyInfo;
import com.smart.dao.hibernate.GenericDaoHibernate;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 909436637@qq.com on 2016/10/20.
 * Description:
 */
@Repository("hospitalPathologyInfoDao")
public class PimsHospitalPathologyInfoDaoHibernate extends GenericDaoHibernate<PimsHospitalPathologyInfo, Long> implements PimsHospitalPathologyInfoDao {
    /**
     * Constructor that takes in a class to see which type of entity to persist.
     * Use this constructor when subclassing.
     */
    public PimsHospitalPathologyInfoDaoHibernate() {
        super(PimsHospitalPathologyInfo.class);
    }

    @Override
    public List<PimsHospitalPathologyInfo> getPathologyByUserId(String s, long userId) {

        return getSession().createQuery(s).setLong("id", userId).list();
    }
}
