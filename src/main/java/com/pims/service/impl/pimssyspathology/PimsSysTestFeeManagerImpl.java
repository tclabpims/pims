package com.pims.service.impl.pimssyspathology;

import com.pims.dao.pimspathologysample.PimsPathologySampleDao;
import com.pims.dao.pimssyspathology.PimsSysTestFeeDao;
import com.pims.model.PimsBaseModel;
import com.pims.model.PimsPathologySample;
import com.pims.model.PimsSysTestFee;
import com.pims.service.pimspathologysample.PimsPathologySampleManager;
import com.pims.service.pimssyspathology.PimsSysTestFeeManager;
import com.pims.webapp.controller.GridQuery;
import com.smart.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by king on 2016/10/10.
 */
@Service("pimsSysTestFeeManager")
public class PimsSysTestFeeManagerImpl extends GenericManagerImpl<PimsSysTestFee, Long> implements PimsSysTestFeeManager {
    private PimsSysTestFeeDao pimsSysTestFeeDao;

    @Autowired
    public void setPimsSysTestFeeDao(PimsSysTestFeeDao pimsSysTestFeeDao) {
        this.pimsSysTestFeeDao = pimsSysTestFeeDao;
        this.dao = pimsSysTestFeeDao;
    }

    @Override
    public List getTestFeeList(String testid) {
        return pimsSysTestFeeDao.getTestFeeList(testid);
    }
}
