package com.pims.dao.pimssyspathology;

import com.pims.model.PimsSysColor;
import com.smart.dao.GenericDao;

/**
 * Created by 909436637@qq.com on 2016/10/10.
 * Description:
 */
public interface PimsSysColorDao extends GenericDao<PimsSysColor, Long> {
    boolean isExisted(PimsSysColor psc);
}
