package com.pims.service.pimssyspathology;

import com.pims.model.PimsSysReportItems;
import com.pims.webapp.controller.GridQuery;
import com.smart.service.GenericManager;

import java.util.List;

/**
 * Created by 909436637@qq.com on 2016/10/10.
 * Description:
 */
public interface PimsSysReportItemsManager extends GenericManager<PimsSysReportItems, Long> {
    List<PimsSysReportItems> getReportItemList(GridQuery gridQuery);

    Integer countReportItem(String query);
}
