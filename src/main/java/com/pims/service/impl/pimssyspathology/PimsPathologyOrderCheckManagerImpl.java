package com.pims.service.impl.pimssyspathology;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pims.dao.pimssyspathology.PimsPathologyOrderCheckDao;
import com.pims.model.PimsPathologyOrderCheck;
import com.pims.service.pimssyspathology.PimsPathologyOrderCheckManager;
import com.smart.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

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

    @Override
    public String calCheckItemCharge(Set<Long> checkItemId, long ordcustomercode) {
        List li = pathologyOrderCheckDao.calCheckItemCharge(checkItemId, ordcustomercode);
        JSONObject res = new JSONObject();
        if(li.size() > 0) {
            res.put("reqItem", li.size());
            double totalMoney = 0.0;
            for(Object obj : li) {
                Object[] item = (Object[]) obj;
                BigDecimal bigDecimal = (BigDecimal)item[1];
                totalMoney += bigDecimal.doubleValue();
            }
            res.put("totalMoney", totalMoney);
        }
        return res.toString();
    }

    @Override
    public void updateTestResult(JSONArray json, String name) {
        pathologyOrderCheckDao.updateTestResult(json, name);
    }

    @Override
    public void removeItems(Set<Long> oldItems, long orderId) {
        pathologyOrderCheckDao.removeItems(oldItems, orderId);
    }

    @Override
    public void removeByOrderId(long orderId) {
        pathologyOrderCheckDao.removeByOrderId(orderId);
    }

    @Override
    public void updateItemStatus(Set<Long> s) {
        pathologyOrderCheckDao.updateItemStatus(s);
    }

    @Override
    public void saveResult(JSONArray array) {
        for(Object obj : array) {
            JSONObject jsonObject = (JSONObject)obj;
            pathologyOrderCheckDao.saveResult(jsonObject.getLongValue("checkid"), jsonObject.getString("chetestresult"));
        }
    }
}
