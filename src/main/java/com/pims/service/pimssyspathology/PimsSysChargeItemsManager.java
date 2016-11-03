package com.pims.service.pimssyspathology;

import com.pims.model.PimsSysChargeItems;
import com.pims.webapp.controller.GridQuery;
import com.smart.service.GenericManager;

import java.util.List;

/**
 * Created by 909436637@qq.com on 2016/10/11.
 * Description:
 */
public interface PimsSysChargeItemsManager extends GenericManager<PimsSysChargeItems, Long> {
    List<PimsSysChargeItems> getChargeItemsList(GridQuery gridQuery);

    Integer countChargeItems(String query);

    List getfeeAll();
}
