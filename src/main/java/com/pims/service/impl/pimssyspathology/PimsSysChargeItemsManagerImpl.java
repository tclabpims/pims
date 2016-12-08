package com.pims.service.impl.pimssyspathology;

import com.pims.dao.pimssyspathology.PimsSysChargeItemsDao;
import com.pims.model.PimsSysChargeItems;
import com.pims.service.pimssyspathology.PimsSysChargeItemsManager;
import com.pims.webapp.controller.GridQuery;
import com.smart.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 909436637@qq.com on 2016/10/11.
 * Description:
 */
@Service("pimsSysChargeItemsManager")
public class PimsSysChargeItemsManagerImpl extends GenericManagerImpl<PimsSysChargeItems, Long> implements PimsSysChargeItemsManager {

    private PimsSysChargeItemsDao pimsSysChargeItemsDao;

    @Autowired
    public void setPimsSysChargeItemsDao(PimsSysChargeItemsDao pimsSysChargeItemsDao) {
        this.dao = pimsSysChargeItemsDao;
        this.pimsSysChargeItemsDao = pimsSysChargeItemsDao;
    }

    @Override
    public List<PimsSysChargeItems> getChargeItemsList(GridQuery gridQuery) {
        StringBuilder qstr = new StringBuilder("from PimsSysChargeItems p");
        String query = gridQuery.getQuery();
        String sidx = gridQuery.getSidx();
        if(query != null && !"".equals(query.trim())) {
            qstr.append(" where p.chinesename||p.chienglishname  like '%").append(query).append("%'");
        }
        sidx = (sidx == null || sidx.trim().equals(""))?"p.chargeitemid ":sidx;
        qstr.append(" order by  ").append(sidx).append(gridQuery.getSord());
        return pimsSysChargeItemsDao.pagingList(qstr.toString(), gridQuery.getStart(), gridQuery.getEnd());
    }

    @Override
    public Integer countChargeItems(String query) {
        StringBuilder qstr = new StringBuilder("select count(1) cnt from PIMS_SYS_CHARGE_ITEMS p");
        if(query != null && !"".equals(query.trim())) {
            qstr.append(" where p.chinesename||p.chienglishname  like '%").append(query).append("%'");
        }
        return pimsSysChargeItemsDao.countTotal(qstr.toString());
    }

    @Override
    public List getfeeAll() {
        return pimsSysChargeItemsDao.getfeeAll();
    }

    @Override
    public List getFeeByName(String name) {
        return pimsSysChargeItemsDao.getFeeByName(name);
    }
}
