package com.pims.service.pimspathologysample;

import com.pims.model.SamplePdfTask;
import com.smart.service.GenericManager;
import java.util.List;

/**
 * Created by king on 2016/10/25.
 */
public interface SamplePdfTaskManager extends GenericManager<SamplePdfTask,Long> {

    /**
     * 查询需要上传报告库的列表
     * @return
     */
    List getTaskList();
}
