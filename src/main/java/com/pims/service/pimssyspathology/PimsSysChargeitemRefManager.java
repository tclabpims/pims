package com.pims.service.pimssyspathology;

import com.pims.model.PimsSysChargeitemRef;
import com.pims.webapp.controller.GridQuery;
import com.smart.service.GenericManager;

import java.util.List;

/**
 * Created by 909436637@qq.com on 2016/10/11.
 * Description:
 */
public interface PimsSysChargeitemRefManager extends GenericManager<PimsSysChargeitemRef, Long> {

    List<PimsSysChargeitemRef> getChargeitemRefList(GridQuery gridQuery);

    Integer countChargeitemRef(String query);
}
