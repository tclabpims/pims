package com.pims.dao.pimssyspathology;

import com.alibaba.fastjson.JSONArray;
import com.pims.model.PimsPathologyOrderCheck;
import com.smart.dao.GenericDao;

import java.util.List;
import java.util.Set;

/**
 * Created by 909436637@qq.com on 2016/11/4.
 * Description:
 */
public interface PimsPathologyOrderCheckDao extends GenericDao<PimsPathologyOrderCheck, Long> {
    void batchSave(List<PimsPathologyOrderCheck> checkItems);

    List<PimsPathologyOrderCheck> getOrderCheckByOrderId(long orderId);

    List calCheckItemCharge(Set<Long> checkItemId, long ordcustomercode);

    void updateTestResult(JSONArray json, String name);

    void removeItems(Set<Long> oldItems, long orderId);

    void removeByOrderId(long orderId);

    void updateItemStatus(Set<Long> s);

    void saveResult(long checkid, String chetestresult);
}
