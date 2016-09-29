package com.pims.dao.his;

import com.pims.dao.GenericDao;
import com.pims.model.PimsPathologyRequisition;

import java.util.List;
import java.util.Map;

/**
 * Created by king on 2016/9/28.
 */
public interface PimsPathologyRequisitionDao extends GenericDao<PimsPathologyRequisition,Long> {
    /**
     *查询申请单列表
     * @param map
     * @return
     */
    List<PimsPathologyRequisition> getRequisitionInfo(Map map);

}

