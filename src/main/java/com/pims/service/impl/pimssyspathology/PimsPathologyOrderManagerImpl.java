package com.pims.service.impl.pimssyspathology;

import com.pims.dao.pimssyspathology.PimsPathologyOrderDao;
import com.pims.model.PimsBaseModel;
import com.pims.model.PimsPathologyOrder;
import com.pims.service.pimssyspathology.PimsPathologyOrderManager;
import com.pims.webapp.controller.GridQuery;
import com.smart.model.user.User;
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
                map.put("chiParaffinCode", row[9]);
                lis.add(map);
            }
        }
        return lis;
    }

    @Override
    public Integer countOrders(String specialCheck, String pathologyCode, String startDate, String endDate, String patientName, String orderState) {
        return pimsPathologyOrderDao.countOrders(specialCheck, pathologyCode, startDate, endDate, patientName, orderState);
    }

    @Override
    public void updateOrderState(long orderId, long orderState, User user) {
        pimsPathologyOrderDao.updateOrderState(orderId, orderState, user);
    }

    @Override
    public List<Map<String, Object>> getSampleOrder(long sampleId) {
        List result = pimsPathologyOrderDao.getSampleOrder(sampleId);
        List<Map<String, Object>> lis = new ArrayList<>();
        if(result.size() > 0) {
            for(Object obj : result) {
                Object[] order = (Object[])obj;
                Map<String, Object> map = new HashMap<>();
                map.put("teschinesename", order[0]);
                map.put("tesenglishname", order[1]);
                map.put("testitemid", order[2]);
                map.put("tesischarge", order[3]);
                lis.add(map);
            }
        }
        return lis;
    }

    @Override
    public List<Map<String, Object>> getCheckItems(long sampleId, long testItemId) {
        List lis = pimsPathologyOrderDao.getCheckItems(sampleId, testItemId);
        List<Map<String, Object>> result = new ArrayList<>();
        if(lis.size() > 0) {
            for(Object obj : lis) {
                Object[] o = (Object[])obj;
                Map<String, Object> map = new HashMap<>();
                map.put("checkid", o[0]);
                map.put("cheorderid", o[1]);
                map.put("chechildorderid", o[2]);
                map.put("cheorderitemid", o[6]);
                map.put("chenamech", o[7]);
                map.put("chenameen", o[8]);
                map.put("cheischarge", o[11]);
                map.put("chetestresult", o[13]);
                map.put("checreatetime", o[22]);
                map.put("checreateuser", o[23]);
                map.put("finishstatus", o[24]);
                map.put("chiparaffincode", o[25]);
                result.add(map);
            }
        }
        return result;
    }

    @Override
    public List getOrderList(PimsBaseModel map) {
        return pimsPathologyOrderDao.getOrderList(map);
    }

    @Override
    public int getOrderNum(PimsBaseModel map) {
        return pimsPathologyOrderDao.getOrderNum(map);
    }
}
