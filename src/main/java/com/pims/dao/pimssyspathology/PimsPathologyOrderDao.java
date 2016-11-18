package com.pims.dao.pimssyspathology;

import com.pims.model.PimsBaseModel;
import com.pims.model.PimsPathologyOrder;
import com.pims.webapp.controller.GridQuery;
import com.smart.dao.GenericDao;
import com.smart.model.user.User;

import java.util.List;

/**
 * Created by 909436637@qq.com on 2016/11/4.
 * Description:
 */
public interface PimsPathologyOrderDao extends GenericDao<PimsPathologyOrder, Long> {
    List getOrders(GridQuery gridQuery, String specialCheck, String pathologyCode, String startDate, String endDate, String patientName, String orderState, String ingore);

    Integer countOrders(String specialCheck, String pathologyCode, String startDate, String endDate, String patientName, String orderState, String ingore);

    void updateOrderState(long orderId, long orderState, User user);

    List getSampleOrder(long sampleId);

    List getCheckItems(long sampleId, long testItemId);

    List getOrderList(PimsBaseModel map);
    int getOrderNum(PimsBaseModel map);
}
