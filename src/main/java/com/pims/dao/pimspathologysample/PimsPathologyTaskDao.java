package com.pims.dao.pimspathologysample;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pims.model.*;
import com.smart.dao.GenericDao;

import java.util.List;
import java.util.Map;

/**
 * Created by king on 2016/10/25.
 */
public interface PimsPathologyTaskDao extends GenericDao<PimsPathologyTask,Long> {

    /**
     * 查询抄送列表
     * @param map
     * @return
     */
    List getTaskList(PimsBaseModel map);

    /**
     * 查询抄送数量
     * @param map
     * @return
     */
    int getTaskListNum(PimsBaseModel map);

    /**
     * 更新任务状态
     * @param states
     * @param sampleList
     * @return
     */
    JSONObject updateConStates(int states, JSONArray sampleList);

    /**
     * 判断抄送是否已存在
     * @param states,id
     * @return
     */
    boolean isExistsTask(int states,Long id);

    /**
     * 更改任务状态
     * @param taskList
     * @return
     */
    JSONObject changeTask(String states,String taskstates,com.alibaba.fastjson.JSONArray taskList);

}
