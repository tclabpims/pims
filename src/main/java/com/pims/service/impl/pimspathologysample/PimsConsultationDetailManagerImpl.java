package com.pims.service.impl.pimspathologysample;

import com.pims.dao.pimspathologysample.PimsConsultationDetailDao;
import com.pims.dao.pimspathologysample.PimsPathologyConsultationDao;
import com.pims.model.PimsBaseModel;
import com.pims.model.PimsConsultationDetail;
import com.pims.model.PimsPathologyConsultation;
import com.pims.model.PimsSampleResult;
import com.pims.service.pimspathologysample.PimsConsultationDetailManager;
import com.pims.service.pimspathologysample.PimsPathologyConsultationManager;
import com.smart.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by king on 2016/10/25.
 */
@Service("pimsConsultationDetailManager")
public class PimsConsultationDetailManagerImpl extends GenericManagerImpl<PimsConsultationDetail, Long>
        implements PimsConsultationDetailManager {
    private PimsConsultationDetailDao pimsConsultationDetailDao;

    @Autowired
    public void setPimsConsultationDetailDao(PimsConsultationDetailDao pimsConsultationDetailDao) {
        this.pimsConsultationDetailDao = pimsConsultationDetailDao;
        this.dao = pimsConsultationDetailDao;
    }

    /**
     * 查询受邀请的会诊列表
     * @param map
     * @return
     */
    @Override
    public List getConList(PimsBaseModel map) {
        return pimsConsultationDetailDao.getConList(map);
    }

    @Override
    public int getReqListNum(PimsBaseModel map) {
        return pimsConsultationDetailDao.getReqListNum(map);
    }

    /**
     * 更新会诊结果信息
     * @param model
     */
    @Override
    public void updateDetil(PimsConsultationDetail model) {
        pimsConsultationDetailDao.updateDetil(model);
    }
}
