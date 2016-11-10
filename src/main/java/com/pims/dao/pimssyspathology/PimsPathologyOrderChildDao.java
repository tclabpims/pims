package com.pims.dao.pimssyspathology;

import com.pims.model.PimsPathologyOrderChild;
import com.smart.dao.GenericDao;

/**
 * Created by 909436637@qq.com on 2016/11/4.
 * Description:
 */
public interface PimsPathologyOrderChildDao extends GenericDao<PimsPathologyOrderChild, Long> {
    PimsPathologyOrderChild getChildByOrderId(long orderId);
}
