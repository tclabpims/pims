package com.pims.service.pimssyspathology;

import com.pims.model.PimsSysCustomerBasedata;
import com.pims.webapp.controller.GridQuery;
import com.smart.service.GenericManager;

import java.util.List;

/**
 * Created by 909436637@qq.com on 2016/10/12.
 * Description:
 */
public interface PimsSysCustomerBasedataManager extends GenericManager<PimsSysCustomerBasedata, Long> {
    List<PimsSysCustomerBasedata> getCustomerDataList(GridQuery gridQuery);

    Integer countCustomerData(String query);
}
