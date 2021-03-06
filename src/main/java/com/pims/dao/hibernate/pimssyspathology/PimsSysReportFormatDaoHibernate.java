package com.pims.dao.hibernate.pimssyspathology;

import com.pims.dao.pimssyspathology.PimsSysReportFormatDao;
import com.pims.model.PimsSysReportFormate;
import com.smart.dao.hibernate.GenericDaoHibernate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 909436637@qq.com on 2016/10/2.
 * Description:
 */
@Repository("pimsSysReportFormatDao")
public class PimsSysReportFormatDaoHibernate extends GenericDaoHibernate<PimsSysReportFormate, Long> implements PimsSysReportFormatDao {

    public PimsSysReportFormatDaoHibernate() {
        super(PimsSysReportFormate.class);
    }

    @Override
    public void removeReportData(String s, Long pathologyid) {
        getSession().createQuery(s).setParameter("ID", pathologyid).executeUpdate();
    }

    @Override
    public List<PimsSysReportFormate> getReportFormatByPathologyId(String s, Long pathologyId) {
        return getSession().createQuery(s).setParameter("pathologyId", pathologyId).list();
    }
}
