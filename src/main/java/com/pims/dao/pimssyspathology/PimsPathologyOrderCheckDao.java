package com.pims.dao.pimssyspathology;

import com.pims.model.PimsPathologyOrderCheck;
import com.smart.dao.GenericDao;

import java.util.List;

/**
 * Created by 909436637@qq.com on 2016/11/4.
 * Description:
 */
public interface PimsPathologyOrderCheckDao extends GenericDao<PimsPathologyOrderCheck, Long> {
    void batchSave(List<PimsPathologyOrderCheck> checkItems);

    List<PimsPathologyOrderCheck> getOrderCheckByOrderId(long orderId);
}
