package com.pims.service.his;

import com.pims.model.PimsPathologyRequisition;
import com.pims.service.GenericManager;

import java.util.List;
import java.util.Map;

/**
 * Created by king on 2016/9/28.
 */
public interface PimsPathologyRequisitionManager extends GenericManager<PimsPathologyRequisition,Long> {
    /**
     * 获取申请单列表
     * @param map
     * @return
     */
    List<PimsPathologyRequisition> getRequisitionInfo(Map map);

}
