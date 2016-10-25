package com.pims.dao.hibernate.pimssyspathology;

import com.pims.dao.pimssyspathology.PimsSysReqMaterialDao;
import com.pims.model.PimsSysReqMaterial;
import com.smart.dao.hibernate.GenericDaoHibernate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 909436637@qq.com on 2016/10/9.
 * Description:
 */
@Repository("pimsSysReqMaterialDao")
public class PimsSysReqMaterialDaoHibernate extends GenericDaoHibernate<PimsSysReqMaterial, Long> implements PimsSysReqMaterialDao {
    /**
     * Constructor that takes in a class to see which type of entity to persist.
     * Use this constructor when subclassing.
     */
    public PimsSysReqMaterialDaoHibernate() {
        super(PimsSysReqMaterial.class);
    }

    @Override
    public List<PimsSysReqMaterial> getAllInfo() {
        StringBuffer sb = new StringBuffer();
        sb.append("from PimsSysReqMaterial where matuseflag = 1 order by matsort");
        return getSession().createQuery(sb.toString()).list();
    }
}
