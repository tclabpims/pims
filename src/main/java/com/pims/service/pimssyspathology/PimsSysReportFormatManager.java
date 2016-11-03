package com.pims.service.pimssyspathology;

import com.pims.model.PimsSysReportFormate;
import com.pims.webapp.controller.GridQuery;
import com.smart.service.GenericManager;

import java.util.List;

/**
 * Created by 909436637@qq.com on 2016/10/1.
 * Description:
 */
public interface PimsSysReportFormatManager extends GenericManager<PimsSysReportFormate,Long> {

    List<PimsSysReportFormate> getPimsSysReportFormatList(GridQuery gridQuery);

    Integer getPimsSysReportFormat(String query);

    void removeReportData(Long pathologyid);

    List<PimsSysReportFormate> getReportFormatByPathologyId(Long pathologyId);
}
