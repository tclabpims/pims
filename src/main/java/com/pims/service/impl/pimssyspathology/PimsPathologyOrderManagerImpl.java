package com.pims.service.impl.pimssyspathology;

import com.pims.dao.pimssyspathology.PimsPathologyOrderDao;
import com.pims.model.PimsPathologyOrder;
import com.pims.service.pimssyspathology.PimsPathologyOrderManager;
import com.pims.webapp.controller.GridQuery;
import com.smart.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public List getOrders(GridQuery gridQuery, String specialCheck, String pathologyCode, String startDate, String endDate, String patientName, String orderState) {
        List<Map<String, Object>> lis = new ArrayList<>();
        List data = pimsPathologyOrderDao.getOrders(gridQuery, specialCheck, pathologyCode, startDate, endDate, patientName, orderState);
        if (data.size() > 0) {
            for (Object obj : data) {
                Object[] row = (Object[]) obj;
                Map<String, Object> map = new HashMap<>();
                map.put("orderId", row[0]);
                map.put("orderCode", row[1]);
                map.put("ordSampleId", row[2]);
                map.put("ordCustomerId", row[3]);
                map.put("ordPathologyCode", row[4]);
                map.put("ordOrderUser", row[5]);
                map.put("chiOrderType", row[6]);
                map.put("chiOrderState", row[7]);
                map.put("samPathologyId", row[8]);
                lis.add(map);
            }
        }
        return lis;
    }

    @Override
    public Integer countOrders(String specialCheck, String pathologyCode, String startDate, String endDate, String patientName, String orderState) {
        return pimsPathologyOrderDao.countOrders(specialCheck, pathologyCode, startDate, endDate, patientName, orderState);
    }
}
