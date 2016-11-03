package com.pims.service.pimssyspathology;

import com.pims.model.PimsSysReqField;
import com.pims.webapp.controller.GridQuery;
import com.smart.service.GenericManager;

import java.util.List;

/**
 * Created by 909436637@qq.com on 2016/10/8.
 * Description:
 */
public interface PimsSysReqFieldManager extends GenericManager<PimsSysReqField, Long> {
    List<PimsSysReqField> getReqFieldList(GridQuery gridQuery);

    Integer countReqField(String query);

    void deleteFields(String mid);

    List<PimsSysReqField> getReqFieldList(Long hospitalId, Long pathologyId);
}
