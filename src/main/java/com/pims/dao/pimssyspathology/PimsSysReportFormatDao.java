package com.pims.dao.pimssyspathology;

import com.pims.dao.GenericDao;
import com.pims.model.PimsSysReportFormate;

/**
 * Created by 909436637@qq.com on 2016/10/1.
 * Description:
 */
public interface PimsSysReportFormatDao  extends GenericDao<PimsSysReportFormate, Long> {
    void removeReportData(String s, Long pathologyid);
}
