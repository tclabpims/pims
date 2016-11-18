package com.pims.service.pimspathologysample;

import com.alibaba.fastjson.JSONArray;
import com.pims.model.PimsBaseModel;
import com.pims.model.PimsPathologyParaffin;
import com.pims.model.PimsPathologySample;
import com.smart.service.GenericManager;

import java.util.List;
import java.util.Map;

/**
 * Created by king on 2016/10/10.
 */
public interface PimsPathologyParaffinManager extends GenericManager<PimsPathologyParaffin,Long> {
    /**
     *
     * 查询包埋数据
     * @param code
     * @return
     */
    List getSampleListNoPage(String code);
    /**
     * 查询材块列表
     * @param map
     * @return
     */
    List getSampleList(PimsBaseModel map);

    /**
     * 查询材块数量
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
     *
     * @param slideList
     * @param paraList
     * @param sampleList
     * @param sts
     * @param state
     * @return
     */
    boolean updateSampleSts(JSONArray slideList, JSONArray paraList, JSONArray sampleList, int sts, int state);

    /**
     * 判断单据是否可被修改
     * @param id,sts
     * @return
     */
    boolean canChange(Long id, String sts);

    List<PimsPathologyParaffin> getParaffinBySampleId(long sampleId);

    /**
     * 按照标本编号和蜡块编号取蜡块信息
     * @param sampleId 标本编号
     * @param paraffinCode 蜡块编号
     * @return 蜡块信息
     */
    PimsPathologyParaffin getPimsPathologyParaffin(long sampleId, String paraffinCode);

    List<Map<String, Object>> getParaffinMaterial(long paraffinId);

    List<Map<String,Object>> getParaffinFromOrder(long oderId);
}
