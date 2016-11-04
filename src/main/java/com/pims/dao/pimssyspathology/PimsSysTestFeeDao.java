package com.pims.dao.pimssyspathology;

import com.pims.model.PimsBaseModel;
import com.pims.model.PimsPathologySample;
import com.pims.model.PimsSysTestFee;
import com.pims.webapp.controller.GridQuery;
import com.smart.dao.GenericDao;

import java.util.List;

/**
 * Created by king on 2016/10/10.
 */
public interface PimsSysTestFeeDao extends GenericDao<PimsSysTestFee,Long> {
    /**
     * 根据检查项目ID，查询收费列表
     * @param testid
     * @return
     */
   List getTestFeeList(String testid);
}
