package com.pims.service.impl.pimspathologysample;

import com.pims.dao.pimspathologysample.PimsPathologyReceivemessageDao;
import com.pims.model.PimsBaseModel;
import com.pims.model.PimsPathologyReceivemessage;
import com.pims.service.pimspathologysample.PimsPathologyReceivemessageManager;
import com.smart.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by king on 2016/10/25.
 */
@Service("pimsPathologyReceivemessageManager")
public class PimsPathologyReceivemessageManagerImpl extends GenericManagerImpl<PimsPathologyReceivemessage, Long>
        implements PimsPathologyReceivemessageManager {
    private PimsPathologyReceivemessageDao pimsPathologyReceivemessageDao;

    @Autowired
    public void setPimsPathologyReceivemessageDao(PimsPathologyReceivemessageDao pimsPathologyReceivemessageDao) {
        this.pimsPathologyReceivemessageDao = pimsPathologyReceivemessageDao;
        this.dao = pimsPathologyReceivemessageDao;
    }

    /**
     * 查询接收消息列表
     * @param map
     * @return
     */
    @Override
    public List getTaskList(PimsBaseModel map) {
        return pimsPathologyReceivemessageDao.getTaskList(map);
    }

    /**
     * 查询接收消息数量
     * @param map
     * @return
     */
    @Override
    public int getTaskListNum(PimsBaseModel map) {
        return pimsPathologyReceivemessageDao.getTaskListNum(map);
    }

    /**
     * 更新消息为已接收
     * @param id
     * @return
     */
    @Override
    public boolean updateConStates(Long id) {
        return pimsPathologyReceivemessageDao.updateConStates(id);
    }
}
