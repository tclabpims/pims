package com.pims.service.his;

import com.pims.model.PimsPathologyRequisition;
import com.smart.service.GenericManager;

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
    /**
     * 逻辑删除申请单
     * @param id
     * @return
     */
    boolean delete(Long id);

    /**
     * 查询单据号是否存在
     * @param id
     * @return
     */
    PimsPathologyRequisition getBySampleNo(Long id);

    /**
     * 获取最大ID
     * @return
     */
    Long getMaxId();



}
