package com.pims.dao.othermanage;


import com.pims.model.PimsBaseModel;
import com.pims.model.PimsSlideRecord;
import com.smart.dao.GenericDao;

import java.util.List;

/**
 * Created by zp on 2016/11/21.
 */
public interface PimsSlideLoanRecordDao extends GenericDao<PimsSlideRecord,Long> {
    /**
     * 查询标本列表
     * @param map
     * @return
     */
    List<PimsSlideRecord> getRecordList(PimsSlideRecord map, PimsBaseModel ppr);

//    boolean returnSlide(PimsSlideRecord map);
}
