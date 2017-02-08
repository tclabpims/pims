package com.pims.service.impl.pimspathologysample;

import com.pims.dao.pimspathologysample.PimsPathologyConsultationDao;
import com.pims.dao.pimspathologysample.PimsPathologySampleDao;
import com.pims.model.*;
import com.pims.service.pimspathologysample.PimsPathologyConsultationManager;
import com.pims.webapp.controller.GridQuery;
import com.smart.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by king on 2016/10/25.
 */
@Service("pimsPathologyConsultationManager")
public class PimsPathologyConsultationManagerImpl extends GenericManagerImpl<PimsPathologyConsultation, Long> implements PimsPathologyConsultationManager {
    private PimsPathologyConsultationDao pimsPathologyConsultationDao;

    @Autowired
    public void setPimsPathologyConsultationDao(PimsPathologyConsultationDao pimsPathologyConsultationDao) {
        this.pimsPathologyConsultationDao = pimsPathologyConsultationDao;
        this.dao = pimsPathologyConsultationDao;
    }

    /**
     * 查询会诊列表
     * @param map
     * @return
     */
    @Override
    public List<PimsPathologyConsultation> getConList(PimsBaseModel map) {
        return pimsPathologyConsultationDao.getConList(map);
    }

    /**
     * 查询会诊数量
     * @param map
     * @return
     */
    @Override
    public int getReqListNum(PimsBaseModel map) {
        return pimsPathologyConsultationDao.getReqListNum(map);
    }

    /**
     * 查询蜡块数量
     * @param id
     * @return
     */
    @Override
    public int getParaNums(Long id) {
        return pimsPathologyConsultationDao.getParaNums(id);
    }

    /**
     * 查询标本结果
     * @param id
     * @return
     */
    @Override
    public List<PimsSampleResult> getSampleResultList(Long id) {
        return pimsPathologyConsultationDao.getSampleResultList(id);
    }

    /**
     * 跟新标本状态
     * @param states
     * @param id
     */
    @Override
    public void updatePictureStates(int states, Long id) {
        pimsPathologyConsultationDao.updateConStates(states,id);
    }

    /**
     * 查询会诊结果列表
     * @param id
     * @return
     */
    @Override
    public List<PimsConsultationDetail> getConDets(Long id) {
        return pimsPathologyConsultationDao.getConDets(id);
    }

    /**
     * 查询会诊详细信息
     * @param id
     * @return
     */
    @Override
    public PimsPathologyConsultation getConsInfo(Long id) {
        return pimsPathologyConsultationDao.getConsInfo(id);
    }

    @Override
    public boolean canChang(Long id) {
        return pimsPathologyConsultationDao.canChang(id);
    }

    @Override
    public boolean conIsFinish(Long id) {
        return pimsPathologyConsultationDao.conIsFinish(id);
    }
}
