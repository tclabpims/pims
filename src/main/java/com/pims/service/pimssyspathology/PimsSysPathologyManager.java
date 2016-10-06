package com.pims.service.pimssyspathology;

import com.pims.model.PimsSysPathology;
import com.pims.webapp.controller.GridQuery;
import com.smart.service.GenericManager;

import java.util.List;

/**
 * Created by 909436637@qq.com on 2016/9/28.
 * Description:
 */
public interface PimsSysPathologyManager extends GenericManager<PimsSysPathology,Long> {

    List<PimsSysPathology> getPimsSysPathologyList(GridQuery gridQuery);

    Integer getPimsSysPathologyTotal(String queryString);

}
