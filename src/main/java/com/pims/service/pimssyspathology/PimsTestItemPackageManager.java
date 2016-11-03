package com.pims.service.pimssyspathology;

import com.pims.model.PimsSysPackageDetail;
import com.pims.model.PimsTestItemPackage;
import com.pims.webapp.controller.GridQuery;
import com.smart.service.GenericManager;

import java.util.List;

/**
 * Created by 909436637@qq.com on 2016/11/3.
 * Description:
 */
public interface PimsTestItemPackageManager extends GenericManager<PimsTestItemPackage, Long> {

    List<PimsTestItemPackage> getPackageList(GridQuery gridQuery);

    Integer countPackage(String query);
}
