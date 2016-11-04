package com.pims.service.pimssyspathology;

import com.pims.model.PimsSysTestFee;
import com.smart.service.GenericManager;

import java.util.List;

/**
 * Created by king on 2016/10/10.
 */
public interface PimsSysTestFeeManager extends GenericManager<PimsSysTestFee,Long> {

    /**
     * 根据检查项目ID，查询收费列表
     * @param testid
     * @return
     */
    List getTestFeeList(String testid);
}
