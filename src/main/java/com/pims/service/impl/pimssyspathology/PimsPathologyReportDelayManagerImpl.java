package com.pims.service.impl.pimssyspathology;

import com.pims.dao.pimssyspathology.PimsPathologyReportDelayDao;
import com.pims.model.PimsPathologyReportDelay;
import com.pims.service.pimssyspathology.PimsPathologyReportDelayManager;
import com.smart.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 909436637@qq.com on 2016/11/21.
 * Description:
 */
@Service("pimsPathologyReportDelayManager")
public class PimsPathologyReportDelayManagerImpl extends GenericManagerImpl<PimsPathologyReportDelay, Long> implements PimsPathologyReportDelayManager {

    private PimsPathologyReportDelayDao pimsPathologyReportDelayDao;

    @Autowired
    public void setPimsPathologyReportDelayDao(PimsPathologyReportDelayDao pimsPathologyReportDelayDao) {
        this.dao = pimsPathologyReportDelayDao;
        this.pimsPathologyReportDelayDao = pimsPathologyReportDelayDao;
    }

}
