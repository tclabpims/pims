package com.pims.service.impl.pimspathologysample;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pims.dao.pimspathologysample.PimsPathologyConsultationDao;
import com.pims.dao.pimspathologysample.PimsPathologyTaskDao;
import com.pims.model.*;
import com.pims.service.pimspathologysample.PimsPathologyConsultationManager;
import com.pims.service.pimspathologysample.PimsPathologyTaskManager;
import com.smart.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by king on 2016/10/25.
 */
@Service("pimsPathologyTaskManager")
public class PimsPathologyTaskManagerImpl extends GenericManagerImpl<PimsPathologyTask, Long>
        implements PimsPathologyTaskManager {
    private PimsPathologyTaskDao pimsPathologyTaskDao;

    @Autowired
    public void setPimsPathologyTaskDao(PimsPathologyTaskDao pimsPathologyTaskDao) {
        this.pimsPathologyTaskDao = pimsPathologyTaskDao;
        this.dao = pimsPathologyTaskDao;
    }

    @Override
    public List getTaskList(PimsBaseModel map) {
        return pimsPathologyTaskDao.getTaskList(map);
    }

    @Override
    public int getTaskListNum(PimsBaseModel map) {
        return pimsPathologyTaskDao.getTaskListNum(map);
    }

    @Override
    public JSONObject updateConStates(int states, JSONArray sampleList) {
        return pimsPathologyTaskDao.updateConStates(states,sampleList);
    }

    @Override
    public boolean isExistsTask(int states, Long id) {
        return pimsPathologyTaskDao.isExistsTask(states,id);
    }

    @Override
    public JSONObject changeTask(String states, String taskstates, JSONArray taskList) {
        return pimsPathologyTaskDao.changeTask(states,taskstates,taskList);
    }
}
