package com.pims.service.impl.pimssyspathology;

import com.pims.dao.pimssyspathology.PimsSysPathologyDao;
import com.pims.model.PimsSysPathology;
import com.pims.service.pimssyspathology.PimsSysPathologyManager;
import com.smart.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 909436637@qq.com on 2016/9/28.
 * Description:病种维护manager
 */
@Service("pimsSysPathologyManager")
public class PimsSysPathologyManagerImpl extends GenericManagerImpl<PimsSysPathology,Long> implements PimsSysPathologyManager {

    private PimsSysPathologyDao pimsSysPathologyDao;

    @Autowired
    public void setPimsSysPathologyDao(PimsSysPathologyDao pimsSysPathologyDao) {
        this.dao = pimsSysPathologyDao;
    }

}
