package com.pims.service.impl.pimspathologysample;

import com.alibaba.fastjson.JSONArray;
import com.pims.dao.pimspathologysample.PimsPathologyParaffinDao;
import com.pims.dao.pimspathologysample.PimsPathologyPiecesDao;
import com.pims.model.PimsBaseModel;
import com.pims.model.PimsPathologyParaffin;
import com.pims.model.PimsPathologyPieces;
import com.pims.model.PimsPathologySample;
import com.pims.service.pimspathologysample.PimsPathologyParaffinManager;
import com.pims.service.pimspathologysample.PimsPathologyPiecesManager;
import com.smart.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by king on 2016/10/10.
 */
@Service("pimsPathologyParaffinManager")
public class PimsPathologyParaffinManagerImpl extends GenericManagerImpl<PimsPathologyParaffin,Long> implements PimsPathologyParaffinManager {
    private PimsPathologyParaffinDao pimsPathologyParaffinDao;
    @Autowired
    public void setPimsPathologySampleDao(PimsPathologyParaffinDao pimsPathologyParaffinDao) {
        this.pimsPathologyParaffinDao = pimsPathologyParaffinDao;
        this.dao = pimsPathologyParaffinDao;
    }

    /**
     * 查询包埋信息
     * @param code
     * @return
     */
    @Override
    public List getSampleListNoPage(String code) {
        return pimsPathologyParaffinDao.getSampleListNoPage(code);
    }

    /**
     * 查询材块列表
     * @param map
     * @return
     */
    @Override
    public List getSampleList(PimsBaseModel map) {
        return pimsPathologyParaffinDao.getSampleList(map);
    }

    /**
     * 查询材块数量
     * @param map
     * @return
     */
    @Override
    public int getReqListNum(PimsBaseModel map) {
        return pimsPathologyParaffinDao.getReqListNum(map);
    }

    /**
     * 查询标本内容
     * @param id
     * @return
     */
    @Override
    public PimsPathologySample getBySampleNo(Long id) {
        return pimsPathologyParaffinDao.getBySampleNo(id);
    }

    /**
     *
     * @param slideList
     * @param paraList
     * @param sampleList
     * @param sts
     * @param state
     * @return
     */
    @Override
    public boolean updateSampleSts(JSONArray slideList, JSONArray paraList, JSONArray sampleList, int sts, int state) {
        return pimsPathologyParaffinDao.updateSampleSts(slideList,paraList,sampleList,sts,state);
    }

    /**
     * 查询单据是否可修改
     * @param id,sts
     * @return
     */
    @Override
    public boolean canChange(Long id,String sts) {
        return pimsPathologyParaffinDao.canChange(id,sts);
    }
}
