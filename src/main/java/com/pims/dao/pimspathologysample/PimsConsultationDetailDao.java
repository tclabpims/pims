package com.pims.dao.pimspathologysample;

import com.pims.model.PimsConsultationDetail;
import com.smart.dao.GenericDao;

/**
 * Created by king on 2016/10/25.
 */
public interface PimsConsultationDetailDao extends GenericDao<PimsConsultationDetail,Long> {
    /**
     * 更新检查结果信息
     * @param model
     */
    void updateDetil(PimsConsultationDetail model);
}
