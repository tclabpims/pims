package com.pims.service.pimssyspathology;

import com.pims.model.PimsPathologyOrderCheck;
import com.smart.service.GenericManager;

import java.util.List;

/**
 * Created by 909436637@qq.com on 2016/11/4.
 * Description:
 */
public interface PimsPathologyOrderCheckManager extends GenericManager<PimsPathologyOrderCheck, Long> {
    void batchSave(List<PimsPathologyOrderCheck> checkItems);

    List<PimsPathologyOrderCheck> getOrderCheckByOrderId(long orderId);
}
