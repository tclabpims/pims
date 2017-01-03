package com.pims.service.pimssysreqtestitem;

import com.pims.model.PimsSysReqTestitem;
import com.pims.webapp.controller.GridQuery;
import com.smart.service.GenericManager;

import java.util.List;
import java.util.Map;

/**
 * Created by king on 2016/10/8
 */
public interface PimsSysReqTestitemManager extends GenericManager<PimsSysReqTestitem,Long> {
    /**
     * 获取可用的申请项目
     * @param
     * @return
     */
    List<PimsSysReqTestitem> getTestitemInfo(Map map);

    List<PimsSysReqTestitem> getReqTestitemList(GridQuery gridQuery, Long pathologyId,String tesitemtype);

    Integer countReqTestitem(String query, Long pathologyId,String tesitemtype);

    List<PimsSysReqTestitem> allTestItem();

    /**
     * 根据套餐ID取检查项目列表
     * @param aLong 套餐ID
     * @return
     */
    List<PimsSysReqTestitem> getTestItems(Long aLong);


    /**
     * 按照病种编号、取材要求、特检要求取医嘱项目
     * @param pathologyId
     * @param specialCheck
     * @param patIsSampling
     * @return
     */
    List<PimsSysReqTestitem> orderTreatmentItem(Long pathologyId, Long specialCheck, Long patIsSampling);

    List<PimsSysReqTestitem> allValidOrderItem();
}
