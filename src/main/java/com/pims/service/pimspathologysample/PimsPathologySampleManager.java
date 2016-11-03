package com.pims.service.pimspathologysample;

import com.pims.model.PimsBaseModel;
import com.pims.model.PimsPathologySample;
import com.pims.webapp.controller.GridQuery;
import com.smart.service.GenericManager;

import java.util.List;

/**
 * Created by king on 2016/10/10.
 */
public interface PimsPathologySampleManager extends GenericManager<PimsPathologySample,Long> {

    List<PimsPathologySample> querySample(PimsPathologySample sample, GridQuery gridQuery);

    Integer querySampleNum(PimsPathologySample sample);

    /**
     * 查询标本列表
     * @param map
     * @return
     */
    List<PimsPathologySample> getSampleList(PimsBaseModel map);

    /**
     * 查询标本数量
     * @param map
     * @return
     */
    int getReqListNum(PimsBaseModel map);

    /**
     * 查询标本内容
     * @param id
     * @return
     */
    PimsPathologySample getBySampleNo(Long id);
    /**
     * 逻辑删除申请单
     * @param id
     * @returnpims
     */
    boolean delete(Long id);
    /**
     * 判断单据是否可被修改
     * @param id,sts
     * @return
     */
    boolean canChange(Long id,String sts);

    void sign(PimsPathologySample sample);
}
