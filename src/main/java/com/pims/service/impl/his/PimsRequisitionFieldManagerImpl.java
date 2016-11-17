package com.pims.service.impl.his;

import com.pims.dao.his.PimsRequisitionFieldDao;
import com.pims.model.PimsRequisitionField;
import com.pims.service.his.PimsRequisitionFieldManager;
import com.smart.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by king on 2016/9/28.
 */
@Service("pimsRequisitionFieldManager")
public class PimsRequisitionFieldManagerImpl extends GenericManagerImpl<PimsRequisitionField,Long>
        implements PimsRequisitionFieldManager {
    private PimsRequisitionFieldDao pimsRequisitionFieldDao;

    @Autowired
    public void setPimsRequisitionFieldDao(PimsRequisitionFieldDao pimsRequisitionFieldDao) {
        this.dao = pimsRequisitionFieldDao;
        this.pimsRequisitionFieldDao = pimsRequisitionFieldDao;
    }
}
