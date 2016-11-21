package com.pims.service.impl.pimssyspathology;

import com.pims.dao.pimssyspathology.PimsPathologyOrderChildDao;
import com.pims.model.PimsPathologyOrderChild;
import com.pims.service.pimssyspathology.PimsPathologyOrderChildManager;
import com.smart.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by 909436637@qq.com on 2016/11/4.
 * Description:
 */
@Service("pimsPathologyOrderChildManager")
public class PimsPathologyOrderChildManagerImpl extends GenericManagerImpl<PimsPathologyOrderChild, Long> implements PimsPathologyOrderChildManager {

    private PimsPathologyOrderChildDao pathologyOrderChildDao;

    @Autowired
    public void setPathologyOrderChildDao(PimsPathologyOrderChildDao pathologyOrderChildDao) {
        this.dao = pathologyOrderChildDao;
        this.pathologyOrderChildDao = pathologyOrderChildDao;
    }

    @Override
    public PimsPathologyOrderChild getChildByOrderId(long orderId) {
        return pathologyOrderChildDao.getChildByOrderId(orderId);
    }

    @Override
    public List<Map<String, Object>> getOrderChildChargeItem(long testItemId, long ordcustomercode) {
        List result = pathologyOrderChildDao.getOrderChildChargeItem(testItemId, ordcustomercode);
        List<Map<String, Object>> lis = new ArrayList<>();
        if(result.size() > 0) {
            for(Object obj : result) {
                Map<String, Object> map = new HashMap<>();
                Object[] charge = (Object[])obj;
                map.put("hisChargeName", charge[0]);
                map.put("hisPrice", charge[1]);
                map.put("referenceId", charge[2]);
                map.put("chargeItemId", charge[3]);
                lis.add(map);
            }
        }
        return lis;
    }

    @Override
    public void updateWhitePiece(Long orderChildId, Long inventory) {
        pathologyOrderChildDao.updateWhitePiece(orderChildId, inventory);
    }

    @Override
    public List<PimsPathologyOrderChild> getChildList(long oderId) {
        return pathologyOrderChildDao.getChildList(oderId);
    }

    @Override
    public void updateChildItemStatus(Set<Long> s) {
        pathologyOrderChildDao.updateChildItemStatus(s);
    }

    @Override
    public Integer getMaxPieceNo(long sampleId) {
        return pathologyOrderChildDao.getMaxPieceNo(sampleId);
    }
}
