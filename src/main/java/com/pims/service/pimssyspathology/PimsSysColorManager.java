package com.pims.service.pimssyspathology;

import com.pims.model.PimsSysColor;
import com.pims.webapp.controller.GridQuery;
import com.smart.service.GenericManager;

import java.util.List;

/**
 * Created by 909436637@qq.com on 2016/10/10.
 * Description:
 */
public interface PimsSysColorManager extends GenericManager<PimsSysColor, Long> {
    List<PimsSysColor> getSysColorList(GridQuery gridQuery);

    Integer countSysColor(String query);
}
