package com.pims.dao.pimspathologysample;

import com.pims.model.PimsBaseModel;
import com.pims.model.PimsPathologyMessage;
import com.pims.model.PimsPathologyReceivemessage;
import com.smart.dao.GenericDao;

import java.util.List;

/**
 * Created by king on 2016/10/25.
 */
public interface PimsPathologyMessageDao extends GenericDao<PimsPathologyMessage,Long> {

    /**
     * 查询发送列表
     * @param map
     * @return
     */
    List getTaskList(PimsBaseModel map);

    /**
     * 查询发送数量
     * @param map
     * @return
     */
    int getTaskListNum(PimsBaseModel map);

    PimsPathologyMessage getBySampleNo(Long id);

}
