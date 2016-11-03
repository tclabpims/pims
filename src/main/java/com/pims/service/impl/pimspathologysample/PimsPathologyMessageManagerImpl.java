package com.pims.service.impl.pimspathologysample;

import com.pims.dao.pimspathologysample.PimsPathologyMessageDao;
import com.pims.dao.pimspathologysample.PimsPathologyReceivemessageDao;
import com.pims.model.PimsBaseModel;
import com.pims.model.PimsPathologyMessage;
import com.pims.model.PimsPathologyReceivemessage;
import com.pims.service.pimspathologysample.PimsPathologyMessageManager;
import com.pims.service.pimspathologysample.PimsPathologyReceivemessageManager;
import com.smart.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by king on 2016/10/25.
 */
@Service("pimsPathologyMessageManager")
public class PimsPathologyMessageManagerImpl extends GenericManagerImpl<PimsPathologyMessage, Long>
        implements PimsPathologyMessageManager {
    private PimsPathologyMessageDao pimsPathologyMessageDao;

    @Autowired
    public void setPimsPathologyMessageDao(PimsPathologyMessageDao pimsPathologyMessageDao) {
        this.pimsPathologyMessageDao = pimsPathologyMessageDao;
        this.dao = pimsPathologyMessageDao;
    }

    /**
     * 查询消息列表
     * @param map
     * @return
     */
    @Override
    public List getTaskList(PimsBaseModel map) {
        return pimsPathologyMessageDao.getTaskList(map);
    }

    /**
     * 查询消息数量
     * @param map
     * @return
     */
    @Override
    public int getTaskListNum(PimsBaseModel map) {
        return pimsPathologyMessageDao.getTaskListNum(map);
    }

    @Override
    public PimsPathologyMessage getBySampleNo(Long id) {
        return pimsPathologyMessageDao.getBySampleNo(id);
    }
}
