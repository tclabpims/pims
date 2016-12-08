package com.pims.service;

import com.pims.model.PimsPathologyFee;

import java.util.List;

/**
 * Created by 909436637@qq.com on 2016/10/12.
 * Description:
 */
public interface QueryHisDataService {

    Integer queryHisChargePrice(String query);

    List queryHisChargePrice(String query, int start, int end);

    /**
     * 获取住院病人信息
     * @param query
     * @return
     */
    Integer queryPatientNum(String query);

    /**
     * 获取住院病人信息
     * @param query
     * @return
     */
    List queryPatientList(String query);


    boolean insert(PimsPathologyFee fee);

}
