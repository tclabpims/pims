package com.pims.service.impl.pimssyspathology;

import com.pims.dao.pimssyspathology.PimsPathologyFollowupDao;
import com.pims.model.PimsPathologyFollowup;
import com.pims.service.pimssyspathology.PimsPathologyFollowupManager;
import com.smart.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 909436637@qq.com on 2016/11/24.
 * Description:
 */
@Service("pimsPathologyFollowupManager")
public class PimsPathologyFollowupManagerImpl extends GenericManagerImpl<PimsPathologyFollowup, Long> implements PimsPathologyFollowupManager {

    private PimsPathologyFollowupDao pimsPathologyFollowupDao;

    @Autowired
    public void setPimsPathologyFollowupDao(PimsPathologyFollowupDao pimsPathologyFollowupDao) {
        this.pimsPathologyFollowupDao = pimsPathologyFollowupDao;
        this.dao = pimsPathologyFollowupDao;
    }
}
