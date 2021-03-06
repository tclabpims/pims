package com.pims.dao.pimssyspathology;

import com.pims.model.PimsPathologyOrderChild;
import com.smart.dao.GenericDao;

import java.util.List;
import java.util.Set;

/**
 * Created by 909436637@qq.com on 2016/11/4.
 * Description:
 */
public interface PimsPathologyOrderChildDao extends GenericDao<PimsPathologyOrderChild, Long> {
    PimsPathologyOrderChild getChildByOrderId(long orderId);

    List getOrderChildChargeItem(long testItemId, long ordcustomercode);

    void updateWhitePiece(Long orderChildId, Long inventory);

    List<PimsPathologyOrderChild> getChildList(long oderId);

    void updateChildItemStatus(Set<Long> s);

    Integer getMaxPieceNo(long sampleId);

    List getTestItemResult(long sampleId);
}
