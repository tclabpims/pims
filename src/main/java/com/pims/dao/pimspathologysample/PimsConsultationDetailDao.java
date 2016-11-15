package com.pims.dao.pimspathologysample;

import com.pims.model.PimsBaseModel;
import com.pims.model.PimsConsultationDetail;
import com.smart.dao.GenericDao;

import java.util.List;

/**
 * Created by king on 2016/10/25.
 */
public interface PimsConsultationDetailDao extends GenericDao<PimsConsultationDetail,Long> {

    /**
     * 查询会诊列表
     * @param map
     * @return
     */
    List getConList(PimsBaseModel map);

    /**
     * 查询会诊数量
     * @param map
     * @return
     */
    int getReqListNum(PimsBaseModel map);
    /**
     * 更新检查结果信息
     * @param model
     */
    void updateDetil(PimsConsultationDetail model);
}
