package com.pims.dao.pimspathologysample;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pims.model.PimsBaseModel;
import com.pims.model.PimsPathologyParaffin;
import com.pims.model.PimsPathologySample;
import com.smart.dao.GenericDao;

import java.util.List;

/**
 * Created by king on 2016/10/10.
 */
public interface PimsPathologyParaffinDao extends GenericDao<PimsPathologyParaffin,Long> {
    /**
     *
     * 查询包埋信息不分页
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
    JSONObject updateSampleSts(JSONArray slideList, JSONArray paraList, JSONArray sampleList, int sts, int state);
    /**
     * 查询单据是否可被修改
     * @param id,sts(1修改2删除)
     * @return
     */
    boolean canChange(Long id, String sts);

    List<PimsPathologyParaffin> getParaffinBySampleId(long sampleId, Long orderId);

    PimsPathologyParaffin getPimsPathologyParaffin(long sampleId, String paraffinCode);

    List getParaffinMaterial(PimsPathologyParaffin paraffin);

    List getParaffinFromOrder(long oderId);
}
