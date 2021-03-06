package com.pims.service.pimssyspathology;

import com.alibaba.fastjson.JSONArray;
import com.pims.model.PimsPathologyOrderCheck;
import com.smart.service.GenericManager;

import java.util.List;
import java.util.Set;

/**
 * Created by 909436637@qq.com on 2016/11/4.
 * Description:
 */
public interface PimsPathologyOrderCheckManager extends GenericManager<PimsPathologyOrderCheck, Long> {
    void batchSave(List<PimsPathologyOrderCheck> checkItems);

    List<PimsPathologyOrderCheck> getOrderCheckByOrderId(long orderId);

    List<PimsPathologyOrderCheck> getOrderCheckByOrderChildId(long orderId);

    String calCheckItemCharge(Set<Long> checkItemId, long ordcustomercode);

    /**
     * 获取医嘱收费信息
     * @param orderId
     * @return
     */
    List  orderFeeList(long orderId);

    void updateTestResult(JSONArray json, String name);

    void removeItems(Set<Long> oldItems, long orderId);

    void removeByOrderId(long orderId);

    void updateItemStatus(Set<Long> s);

    void saveResult(JSONArray array);
}
