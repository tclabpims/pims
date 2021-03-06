package com.pims.service.pimssyspathology;

import com.pims.model.PimsBaseModel;
import com.pims.model.PimsPathologyOrder;
import com.pims.webapp.controller.GridQuery;
import com.smart.model.user.User;
import com.smart.service.GenericManager;

import java.util.List;
import java.util.Map;

/**
 * Created by 909436637@qq.com on 2016/11/4.
 * Description:
 */
public interface PimsPathologyOrderManager extends GenericManager<PimsPathologyOrder, Long> {
    List getOrders(GridQuery gridQuery, String specialCheck, String pathologyCode, String startDate, String endDate, String patientName, String orderState, String ingore);

    Integer countOrders(String specialCheck, String pathologyCode, String startDate, String endDate, String patientName, String orderState, String ingore);

    void updateOrderState(long orderId, long orderState, User user);

    List<Map<String,Object>> getSampleOrder(long sampleId);

    List<Map<String,Object>> getCheckItems(long sampleId, long testItemId);

    List getOrderList(PimsBaseModel map);
    int getOrderNum(PimsBaseModel map);
}
