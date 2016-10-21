package com.pims.dao.basedata;

import com.pims.model.PimsCommonBaseData;
import com.smart.dao.GenericDao;
import java.util.List;
import java.util.Map;

/**
 * Created by king on 2016/10/21.
 */
public interface PimsCommonBaseDataDao extends GenericDao<PimsCommonBaseData,Long> {

    /**
     * 查询基础数据列表
     * @param map
     * @return
     */
    List<PimsCommonBaseData> getDataList(Map map);

}
