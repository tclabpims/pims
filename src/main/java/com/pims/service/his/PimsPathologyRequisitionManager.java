package com.pims.service.his;

import com.alibaba.fastjson.JSONArray;
import com.pims.model.PimsBaseModel;
import com.pims.model.PimsPathologyRequisition;
import com.pims.model.PimsPathologySample;
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

    String getSjcl(Long id);

    /**
     * 根据病种类别查询最大单据号
     * @param reqpathologyid
     * @return
     */
    String getMaxCode(String reqpathologyid);

    /**
     * 更新申请单的可使用状态
     * @param ppr
     * @param state
     * @return
     */
    boolean updateReqState(PimsPathologySample ppr, int state);

    /**
     * 是否可以被修改或删除
     * @param id
     * @return
     */
    boolean canChange(Long id);

    /**
     * 保存申请单据
     * @param materials
     * @param ppr
     * @return
     */
    PimsPathologyRequisition insertOrUpdate(JSONArray materials,PimsPathologyRequisition ppr);


}
