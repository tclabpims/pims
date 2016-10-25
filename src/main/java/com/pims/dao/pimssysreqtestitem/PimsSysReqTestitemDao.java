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

}

