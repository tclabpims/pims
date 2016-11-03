package com.pims.dao.pimssyspathology;

import com.pims.model.PimsSysChargeItems;
import com.smart.dao.GenericDao;

import java.util.List;

/**
 * Created by 909436637@qq.com on 2016/10/11.
 * Description:
 */
public interface PimsSysChargeItemsDao extends GenericDao<PimsSysChargeItems, Long> {

    List getfeeAll();
}
