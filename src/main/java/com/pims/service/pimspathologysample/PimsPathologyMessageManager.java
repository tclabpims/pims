package com.pims.service.pimspathologysample;

import com.pims.model.PimsBaseModel;
import com.pims.model.PimsPathologyMessage;
import com.pims.model.PimsPathologyReceivemessage;
import com.smart.service.GenericManager;

import java.util.List;

/**
 * Created by king on 2016/10/25.
 */
public interface PimsPathologyMessageManager extends GenericManager<PimsPathologyMessage,Long> {

    /**
     * 查询消息列表
     * @param map
     * @return
     */
    List getTaskList(PimsBaseModel map);

    /**
     * 查询消息数量
     * @param map
     * @return
     */
    int getTaskListNum(PimsBaseModel map);

    PimsPathologyMessage getBySampleNo(Long id);

}
