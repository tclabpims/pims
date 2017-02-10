package com.pims.service.othermanagement;

import com.pims.model.PimsBaseModel;
import com.pims.model.PimsSlideRecord;
import com.smart.service.GenericManager;

import java.util.List;

/**
 * Created by zp on 2016/11/21.
 */
public interface PimsSlideLoanRecordManager extends GenericManager<PimsSlideRecord,Long> {
    List<PimsSlideRecord>   getRecordList(PimsSlideRecord map, PimsBaseModel ppr);

//    boolean returnSlide(PimsSlideRecord map);
}
