package com.pims.service.impl.pimspathologysample;

import com.alibaba.fastjson.JSONArray;
import com.pims.dao.pimspathologysample.PimsPathologySlideDao;
import com.pims.model.*;
import com.pims.service.pimspathologysample.PimsPathologySlideManager;
import com.smart.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by king on 2016/10/10.
 */
@Service("pimsPathologySlideManager")
public class PimsPathologySlideManagerImpl extends GenericManagerImpl<PimsPathologySlide,Long> implements PimsPathologySlideManager {
    private PimsPathologySlideDao pimsPathologySlideDao;
    @Autowired
    public void setPimsPathologySampleDao(PimsPathologySlideDao pimsPathologySlideDao) {
        this.pimsPathologySlideDao = pimsPathologySlideDao;
        this.dao = pimsPathologySlideDao;
    }
    /**
     * 查询玻片信息
     * @param code
     * @return
     */
    @Override
    public List getSampleListNoPage(String code) {
        return pimsPathologySlideDao.getSampleListNoPage(code);
    }
    /**
     * 查询蜡块列表
     * @param map
     * @return
     */
    @Override
    public List<PimsPathologyParaffin> getSampleList(PimsBaseModel map) {
        return pimsPathologySlideDao.getSampleList(map);
    }
    /**
     * 查询蜡块数量
     * @param map
     * @return
     */
    @Override
    public int getReqListNum(PimsBaseModel map) {
        return pimsPathologySlideDao.getReqListNum(map);
    }
    /**
     * 查询标本内容
     * @param id
     * @return
     */
    @Override
    public PimsPathologySample getBySampleNo(Long id) {
        return pimsPathologySlideDao.getBySampleNo(id);
    }
    /**
     * 切片或取消片，更新蜡块信息，并更新标本信息
     * @param slideList 切片列表,paraList 蜡块列表,sts 状态,state 逻辑更新标志,sampleList 标本列表
     * @return
     */
    @Override
    public boolean updateSampleSts(JSONArray slideList, JSONArray paraList, JSONArray sampleList, int sts, int state) {
        return pimsPathologySlideDao.updateSampleSts(slideList,paraList,sampleList,sts,state);
    }

    /**
     * 查询白片数量
     * @param paraffincode
     * @param sampleId
     * @return
     */
    @Override
    public List<PimsPathologySlide> getWhitePiece(String paraffincode, Long sampleId) {
        return pimsPathologySlideDao.getWhitePiece(paraffincode, sampleId);
    }

    /**
     * 按照项目数更新白片使用状态
     *  @param paraffincode
     * @param sampleId
     * @param num
     */
    @Override
    public void updateWhitePieceUsedFlag(String paraffincode, Long sampleId, Long num) {
        pimsPathologySlideDao.updateWhitePieceUsedFlag(paraffincode, sampleId, num);
    }

    @Override
    public PimsPathologySlide getSlideByParaffinId(long chiparaffinid) {
        return pimsPathologySlideDao.getSlideByParaffinId(chiparaffinid);
    }

    @Override
    public JSONArray getSlideCode(JSONArray samplesList) {
        return pimsPathologySlideDao.getSlideCode(samplesList);
    }
}
