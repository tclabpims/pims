package com.pims.service.pimspathologysample;

import com.pims.model.*;
import com.smart.service.GenericManager;
import java.util.List;

/**
 * Created by king on 2016/10/25.
 */
public interface PimsPathologyConsultationManager extends GenericManager<PimsPathologyConsultation,Long> {

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
    void updatePictureStates(int states,Long id);

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

    /**
     * 查询单据是否可以被修改
     */
    boolean canChang(Long id);

    /**
     * 查询会诊是否都发表了意见
     * @param id
     * @return
     */
    boolean  conIsFinish(Long id);

}
