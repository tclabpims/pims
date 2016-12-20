package com.pims.service.pimssyspathology;

import com.pims.model.PimsHospitalPathologyInfo;
import com.pims.model.PimsPathologySample;
import com.pims.model.PimsSysPathology;
import com.pims.webapp.controller.GridQuery;
import com.smart.service.GenericManager;

import java.util.List;

/**
 * Created by 909436637@qq.com on 2016/10/20.
 * Description:
 */
public interface PimsHospitalPathologyInfoManager extends GenericManager<PimsHospitalPathologyInfo, Long> {
    List<PimsHospitalPathologyInfo> getInfoList(GridQuery gridQuery);

    Integer countInfo(String query);

    List<PimsSysPathology> getPathologyByUserId(long userId);

    PimsHospitalPathologyInfo gethinfo(PimsPathologySample sample);

    List<PimsSysPathology> getPathologyByHosId(long hosptailId);
}
