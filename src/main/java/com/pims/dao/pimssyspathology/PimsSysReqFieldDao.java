package com.pims.dao.pimssyspathology;

import com.pims.model.PimsSysReqField;
import com.smart.dao.GenericDao;

/**
 * Created by 909436637@qq.com on 2016/10/8.
 * Description:
 */
public interface PimsSysReqFieldDao extends GenericDao<PimsSysReqField, Long> {
    void deleteFields(String mid);
}
