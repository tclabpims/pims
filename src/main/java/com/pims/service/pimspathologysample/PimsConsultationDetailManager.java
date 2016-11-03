package com.pims.service.pimspathologysample;

import com.pims.model.PimsConsultationDetail;
import com.smart.service.GenericManager;

import java.util.List;

/**
 * Created by king on 2016/10/25.
 */
public interface PimsConsultationDetailManager extends GenericManager<PimsConsultationDetail,Long> {
    /**
     * 更新检查结果信息
     * @param model
     */
    void updateDetil(PimsConsultationDetail model);

}
