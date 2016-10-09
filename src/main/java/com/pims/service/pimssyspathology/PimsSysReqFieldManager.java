package com.pims.service.pimssyspathology;

import com.pims.model.PimsSysReqField;
import com.pims.service.GenericManager;
import com.pims.webapp.controller.GridQuery;

import java.util.List;

/**
 * Created by 909436637@qq.com on 2016/10/8.
 * Description:
 */
public interface PimsSysReqFieldManager extends GenericManager<PimsSysReqField, Long> {
    List<PimsSysReqField> getReqFieldList(GridQuery gridQuery);

    Integer countReqField(String query);

    void deleteFields(String mid);
}
