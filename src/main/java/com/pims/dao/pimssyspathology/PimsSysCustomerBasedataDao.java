package com.pims.dao.pimssyspathology;

import com.pims.model.PimsSysCustomerBasedata;
import com.smart.dao.GenericDao;

import java.util.List;

/**
 * Created by 909436637@qq.com on 2016/10/12.
 * Description:
 */
public interface PimsSysCustomerBasedataDao extends GenericDao<PimsSysCustomerBasedata, Long> {
    List<PimsSysCustomerBasedata> getCustomerDataList(String sql, Long hospitalId, Long pathologyId);
}
