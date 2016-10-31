package com.pims.service.pimspathologysample;

import com.pims.model.PimsBaseModel;
import com.pims.model.PimsPathologyReceivemessage;
import com.smart.service.GenericManager;

import java.util.List;

/**
 * Created by king on 2016/10/25.
 */
public interface PimsPathologyReceivemessageManager extends GenericManager<PimsPathologyReceivemessage,Long> {

    /**
     * 查询接收消息列表
     * @param map
     * @return
     */
    List getTaskList(PimsBaseModel map);

    /**
     * 查询接收消息数量
     * @param map
     * @return
     */
    int getTaskListNum(PimsBaseModel map);

    /**
     * 更新消息为已读
     * @param id
     * @return
     */
    boolean updateConStates(Long id);

}
