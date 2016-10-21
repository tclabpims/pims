package com.pims.service.impl.basedata;

import com.pims.dao.basedata.PimsCommonBaseDataDao;
import com.pims.dao.pimssysreqtestitem.PimsSysReqTestitemDao;
import com.pims.model.PimsCommonBaseData;
import com.pims.model.PimsSysReqTestitem;
import com.pims.service.basedata.PimsCommonBaseDataManager;
import com.pims.service.pimssysreqtestitem.PimsSysReqTestitemManager;
import com.pims.webapp.controller.GridQuery;
import com.smart.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by king on 2016/10/21
 */
@Service("pimsCommonBaseDataManager")
public class PimsCommonBaseDataManagerImpl extends GenericManagerImpl<PimsCommonBaseData,Long>
        implements PimsCommonBaseDataManager {
    private PimsCommonBaseDataDao pimsCommonBaseDataDao;

    @Override
    public List<PimsCommonBaseData> getDataList(Map map) {
        return pimsCommonBaseDataDao.getDataList(map);
    }
}
