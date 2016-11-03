package com.pims.dao.pimspathologysample;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pims.model.PimsBaseModel;
import com.pims.model.PimsPathologyReceivemessage;
import com.pims.model.PimsPathologyTask;
import com.smart.dao.GenericDao;

import java.util.List;

/**
 * Created by king on 2016/10/25.
 */
public interface PimsPathologyReceivemessageDao extends GenericDao<PimsPathologyReceivemessage,Long> {

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
