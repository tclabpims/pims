package com.pims.dao.pimspathologysample;

import com.pims.model.SamplePdfTask;
import com.smart.dao.GenericDao;
import java.util.List;

/**
 * Created by king on 2016/10/25.
 */
public interface SamplePdfTaskDao extends GenericDao<SamplePdfTask,Long> {

    /**
     * 查询需要上传报告库的列表
     * @return
     */
    List getTaskList();
}
