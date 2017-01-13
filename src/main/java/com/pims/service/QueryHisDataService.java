package com.pims.service;

import com.pims.model.PimsPathologyFee;
import com.pims.model.PimsPathologySample;
import com.pims.model.PimsSysPathology;

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

    /**
     * 获取影像学信息
     * @param query
     * @return
     */
    List queryyxxList(String query,String yxjclx);

    /**
     * 根据住院类型及唯一ID获取病人信息
     * @return
     */
    List querPatientInfo(String patient_type,String brjzxh);


    boolean insert(PimsPathologyFee fee);

    boolean insert(PimsPathologySample sample,PimsSysPathology psp);

    boolean delete(PimsPathologySample sample);

}
