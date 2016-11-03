package com.pims.dao.pimssyspathology;

import com.pims.model.PimsSysReportItems;
import com.smart.dao.GenericDao;

import java.util.List;

/**
 * Created by 909436637@qq.com on 2016/10/10.
 * Description:
 */
public interface PimsSysReportItemsDao extends GenericDao<PimsSysReportItems, Long> {
    List getRefFieldList(String sql, Long hospitalId, Long pathologyId);
}
