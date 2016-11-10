package com.pims.service.impl.pimssyspathology;

import com.pims.dao.pimssyspathology.PimsPathologyOrderCheckDao;
import com.pims.model.PimsPathologyOrderCheck;
import com.pims.service.pimssyspathology.PimsPathologyOrderCheckManager;
import com.smart.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 909436637@qq.com on 2016/11/4.
 * Description:
 */
@Service("pimsPathologyOrderCheckManager")
public class PimsPathologyOrderCheckManagerImpl extends GenericManagerImpl<PimsPathologyOrderCheck, Long> implements PimsPathologyOrderCheckManager {

    private PimsPathologyOrderCheckDao pathologyOrderCheckDao;

    @Autowired
    public void setPathologyOrderCheckDao(PimsPathologyOrderCheckDao pathologyOrderCheckDao) {
        this.dao = pathologyOrderCheckDao;
        this.pathologyOrderCheckDao = pathologyOrderCheckDao;
    }

    @Override
    public void batchSave(List<PimsPathologyOrderCheck> checkItems) {
        pathologyOrderCheckDao.batchSave(checkItems);
    }

    @Override
    public List<PimsPathologyOrderCheck> getOrderCheckByOrderId(long orderId) {
        return pathologyOrderCheckDao.getOrderCheckByOrderId(orderId);
    }
}
