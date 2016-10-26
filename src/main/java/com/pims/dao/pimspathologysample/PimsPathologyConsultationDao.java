package com.pims.dao.pimspathologysample;

import com.pims.model.*;
import com.smart.dao.GenericDao;

import java.util.List;

/**
 * Created by king on 2016/10/25.
 */
public interface PimsPathologyConsultationDao extends GenericDao<PimsPathologyConsultation,Long> {

    /**
     * 查询会诊列表
     * @param map
     * @return
     */
    List<PimsPathologyConsultation> getConList(PimsBaseModel map);

    /**
     * 查询会诊数量
     * @param map
     * @return
     */
    int getReqListNum(PimsBaseModel map);

    /**
     * 查询蜡块数量
     * @param id
     * @return
     */
    int getParaNums(Long id);

    /**
     * 查询病理结果列表
     * @param id
     * @return
     */
    List<PimsSampleResult> getSampleResultList(Long id);

    /**
     * 更新会诊状态
     * @param id
     */
    void updateConStates(int states,Long id);

    /**
     * 查询会诊结果列表
     * @param id
     * @return
     */
    List<PimsConsultationDetail> getConDets(Long id);

    /**
     * 查询会诊详细信息
     * @param id
     * @return
     */
    PimsPathologyConsultation getConsInfo(Long id);

    boolean canChang(Long id);

}
