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

    List<PimsSysReqTestitem> getReqTestitemList(GridQuery gridQuery);

    Integer countReqTestitem(String query);
}
