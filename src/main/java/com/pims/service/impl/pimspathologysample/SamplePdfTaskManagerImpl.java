package com.pims.service.impl.pimspathologysample;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pims.dao.pimspathologysample.PimsPathologyTaskDao;
import com.pims.dao.pimspathologysample.SamplePdfTaskDao;
import com.pims.model.PimsBaseModel;
import com.pims.model.PimsPathologyTask;
import com.pims.model.SamplePdfTask;
import com.pims.service.pimspathologysample.PimsPathologyTaskManager;
import com.pims.service.pimspathologysample.SamplePdfTaskManager;
import com.smart.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by king on 2016/10/25.
 */
@Service("samplePdfTaskManager")
public class SamplePdfTaskManagerImpl extends GenericManagerImpl<SamplePdfTask, Long>
        implements SamplePdfTaskManager {
    private SamplePdfTaskDao samplePdfTaskDao;

    @Autowired
    public void setSamplePdfTaskDao(SamplePdfTaskDao samplePdfTaskDao) {
        this.samplePdfTaskDao = samplePdfTaskDao;
        this.dao = samplePdfTaskDao;
    }

    @Override
    public List getTaskList() {
        return samplePdfTaskDao.getTaskList();
    }

}
