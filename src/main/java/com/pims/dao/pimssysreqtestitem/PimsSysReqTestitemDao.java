package com.pims.dao.pimssysreqtestitem;

import com.pims.model.PimsSysReqTestitem;
import com.smart.dao.GenericDao;

import java.util.List;
import java.util.Map;

/**
 * Created by king on 2016/10/8
 */
public interface PimsSysReqTestitemDao extends GenericDao<PimsSysReqTestitem,Long> {
    /**
     *查询所有可用项目列表
     * @param
     * @return
     */
    List<PimsSysReqTestitem> getTestitemInfo(Map name);

    List<PimsSysReqTestitem> allTestItem();

    List<PimsSysReqTestitem> getTestItems(Long aLong);

    /**
     * 按照病种编号、取材要求、特检要求取医嘱项目
     *
     * @param pathologyId 医嘱ID
     * @param specialCheck 是否特检
     * @param patIsSampling 是否取材
     * @return 申请检查项目
     */
    List<PimsSysReqTestitem> orderTreatmentItem(Long pathologyId, Long specialCheck, Long patIsSampling);

    /**
     *
     * @return 取系统 申请检查项目 中设置过的医嘱项目
     */
    List<PimsSysReqTestitem> allValidOrderItem();
}

