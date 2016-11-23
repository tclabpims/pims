package com.pims.service.pimssyspathology;

import com.pims.model.PimsPathologyOrderChild;
import com.smart.service.GenericManager;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by 909436637@qq.com on 2016/11/4.
 * Description:
 */
public interface PimsPathologyOrderChildManager extends GenericManager<PimsPathologyOrderChild, Long> {
    PimsPathologyOrderChild getChildByOrderId(long orderId);

    List<Map<String,Object>> getOrderChildChargeItem(long testItemId, long ordcustomercode);

    void updateWhitePiece(Long orderChildId, Long inventory);

    List<PimsPathologyOrderChild> getChildList(long oderId);

    void updateChildItemStatus(Set<Long> s);

    Integer getMaxPieceNo(long sampleId);

    String getTestItemResult(long sampleId);
}
