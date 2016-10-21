package com.pims.service.basedata;

import com.pims.model.PimsCommonBaseData;
import com.smart.service.GenericManager;
import java.util.List;
import java.util.Map;

/**
 * Created by king on 2016/10/21
 */
public interface PimsCommonBaseDataManager extends GenericManager<PimsCommonBaseData,Long> {
    /**
     * 获取基础数据列表
     * @param
     * @return
     */
    List<PimsCommonBaseData> getDataList(Map map);
}
