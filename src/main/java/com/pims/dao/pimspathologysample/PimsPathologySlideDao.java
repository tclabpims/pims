package com.pims.dao.pimspathologysample;

import com.alibaba.fastjson.JSONArray;
import com.pims.model.*;
import com.smart.dao.GenericDao;
import java.util.List;

/**
 * Created by king on 2016/10/10.
 */
public interface PimsPathologySlideDao extends GenericDao<PimsPathologySlide,Long> {
    /**
     *
     * 查询切片信息不分页
     * @param code
     * @return
     */
    List getSampleListNoPage(String code);

    /**
     * 查询蜡块列表
     * @param map
     * @return
     */
    List<PimsPathologyParaffin> getSampleList(PimsBaseModel map);

    /**
     * 查询蜡块数量
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
     * 切片或取消片，更新蜡块信息，并更新标本信息
     * @param slideList 切片列表,paraList 蜡块列表,sts 状态,state 逻辑更新标志,sampleList 标本列表
     * @return
     */
    boolean updateSampleSts(JSONArray slideList, JSONArray paraList, JSONArray sampleList, int sts, int state);

    /**
     * 查询白片的数量
     * @param paraffincode
     * @param sampleId
     * @return
     */
    List<PimsPathologySlide> getWhitePiece(String paraffincode, Long sampleId);

    void updateWhitePieceUsedFlag(String paraffincode, Long sampleId, Long num);
}
