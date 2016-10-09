package com.pims.service.his;

import com.pims.model.PimsBaseModel;
import com.pims.model.PimsPathologyRequisition;
import com.pims.webapp.controller.GridQuery;
import com.smart.service.GenericManager;

import java.util.List;
import java.util.Map;

/**
 * Created by king on 2016/9/28.
 */
public interface PimsPathologyRequisitionManager extends GenericManager<PimsPathologyRequisition,Long> {
    /**
     * 获取申请单列表
     * @param
     * @return
     */
    List<PimsPathologyRequisition> getRequisitionInfo(PimsBaseModel pims);

    /**
     *
     * @param pims
     * @return
     */
    int getReqListNum(PimsBaseModel pims);
    /**
     * 逻辑删除申请单
     * @param id
     * @returnpims
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

    /**
     * 根据病种类别查询最大单据号
     * @param reqpathologyid
     * @return
     */
    String getMaxCode(int reqpathologyid);


}
