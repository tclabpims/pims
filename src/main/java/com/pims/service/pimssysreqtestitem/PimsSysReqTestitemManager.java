package com.pims.service.pimssysreqtestitem;

import com.pims.model.PimsSysReqTestitem;
import com.smart.service.GenericManager;

import java.util.List;

/**
 * Created by king on 2016/10/8
 */
public interface PimsSysReqTestitemManager extends GenericManager<PimsSysReqTestitem,Long> {
    /**
     * 获取可用的申请项目
     * @param
     * @return
     */
    List<PimsSysReqTestitem> getTestitemInfo();
}
