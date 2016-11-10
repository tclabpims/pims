package com.pims.service.impl.pimssyspathology;

import com.pims.dao.pimssyspathology.PimsPathologyOrderChildDao;
import com.pims.model.PimsPathologyOrderChild;
import com.pims.service.pimssyspathology.PimsPathologyOrderChildManager;
import com.smart.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 909436637@qq.com on 2016/11/4.
 * Description:
 */
@Service("pimsPathologyOrderChildManager")
public class PimsPathologyOrderChildManagerImpl extends GenericManagerImpl<PimsPathologyOrderChild, Long> implements PimsPathologyOrderChildManager {

    private PimsPathologyOrderChildDao pathologyOrderChildDao;

    @Autowired
    public void setPathologyOrderChildDao(PimsPathologyOrderChildDao pathologyOrderChildDao) {
        this.dao = pathologyOrderChildDao;
        this.pathologyOrderChildDao = pathologyOrderChildDao;
    }

    @Override
    public PimsPathologyOrderChild getChildByOrderId(long orderId) {
        return pathologyOrderChildDao.getChildByOrderId(orderId);
    }
}
