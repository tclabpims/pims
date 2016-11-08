package com.pims.service.impl.pimssyspathology;

import com.pims.dao.pimssyspathology.PimsTestItemPackageDao;
import com.pims.model.PimsSysPackageDetail;
import com.pims.model.PimsTestItemPackage;
import com.pims.service.pimssyspathology.PimsTestItemPackageManager;
import com.pims.webapp.controller.GridQuery;
import com.smart.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 909436637@qq.com on 2016/11/3.
 * Description:
 */
@Service("pimsTestItemPackageManager")
public class PimsTestItemPackageManagerImpl extends GenericManagerImpl<PimsTestItemPackage, Long> implements PimsTestItemPackageManager {

    private PimsTestItemPackageDao packageDao;

    @Autowired
    public void setPackageDao(PimsTestItemPackageDao packageDao) {
        this.dao = packageDao;
        this.packageDao = packageDao;
    }

    @Override
    public List<PimsTestItemPackage> getPackageList(GridQuery gridQuery) {
        StringBuilder qstr = new StringBuilder("select p.*,(select count(*) cnt from PIMS_SYS_PACKAGE_DETAIL d where P.Packageid= D.Packageid) as packageItems from PIMS_TEST_ITEM_PACKAGE p ");
        String query = gridQuery.getQuery();
        String sidx = gridQuery.getSidx();
        if(query != null && !"".equals(query.trim())) {
            qstr.append(" where p.packageName like '%").append(query).append("%'");
        }

        sidx = (sidx == null || sidx.trim().equals(""))?"p.packageId ":sidx;
        qstr.append(" order by  ").append(sidx).append(gridQuery.getSord());
        List<Object[]> res = packageDao.sqlPagingQuery(qstr.toString(), gridQuery.getStart(), gridQuery.getEnd());
        List<PimsTestItemPackage> ret = new ArrayList<>();
        if(res.size() > 0) {
            for(Object[] obj : res) {
                PimsTestItemPackage itemPackage = new PimsTestItemPackage();
                itemPackage.setPackageId(((BigDecimal)obj[0]).longValue());
                itemPackage.setPackageName(String.valueOf(obj[1]));
                itemPackage.setPackageDiscount(String.valueOf(obj[2]));
                itemPackage.setPackageUseTimes(((BigDecimal)obj[3]).intValue());
                itemPackage.setPathologyId(((BigDecimal)obj[4]).longValue());
                itemPackage.setPackageItems(((BigDecimal)obj[5]).intValue());
                ret.add(itemPackage);
            }
        }
        return ret;
    }

    @Override
    public Integer countPackage(String query) {
        StringBuilder qstr = new StringBuilder("select count(1) cnt from PIMS_TEST_ITEM_PACKAGE p ");

        if(query != null && !"".equals(query.trim())) {
            qstr.append(" where p.packageName like '%").append(query).append("%'");
        }
        return packageDao.countTotal(qstr.toString());
    }

    @Override
    public List<PimsTestItemPackage> getPackageItems(Long aLong) {
        return packageDao.getPackageItems(aLong);
    }
}
