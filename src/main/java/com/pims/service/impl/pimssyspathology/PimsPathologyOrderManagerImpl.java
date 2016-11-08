package com.pims.service.impl.pimssyspathology;

import com.pims.dao.pimssyspathology.PimsPathologyOrderDao;
import com.pims.model.PimsPathologyOrder;
import com.pims.service.pimssyspathology.PimsPathologyOrderManager;
import com.smart.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 909436637@qq.com on 2016/11/4.
 * Description:
 */
@Service("pimsPathologyOrderManager")
public class PimsPathologyOrderManagerImpl extends GenericManagerImpl<PimsPathologyOrder, Long> implements PimsPathologyOrderManager {

    private PimsPathologyOrderDao pimsPathologyOrderDao;

    @Autowired
    public void setPimsPathologyOrderDao(PimsPathologyOrderDao pimsPathologyOrderDao) {
        this.dao = pimsPathologyOrderDao;
        this.pimsPathologyOrderDao = pimsPathologyOrderDao;
    }

}
