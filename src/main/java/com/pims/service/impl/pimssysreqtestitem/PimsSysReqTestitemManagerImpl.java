package com.pims.service.impl.pimssysreqtestitem;

import com.pims.dao.pimssysreqtestitem.PimsSysReqTestitemDao;
import com.pims.model.PimsSysReqTestitem;
import com.pims.service.pimssysreqtestitem.PimsSysReqTestitemManager;
import com.smart.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by king on 2016/10/8
 */
@Service("pimsSysReqTestitemManager")
public class PimsSysReqTestitemManagerImpl extends GenericManagerImpl<PimsSysReqTestitem,Long>
        implements PimsSysReqTestitemManager {
    private PimsSysReqTestitemDao pimsSysReqTestitemDao;

    @Autowired
    public void setPimsPathologyRequisitionDao(PimsSysReqTestitemDao pimsSysReqTestitemDao) {
        this.dao = pimsSysReqTestitemDao;
        this.pimsSysReqTestitemDao = pimsSysReqTestitemDao;
    }

    /**
     * 获取可用申请项目
     * @return
     */
    @Override
    public List<PimsSysReqTestitem> getTestitemInfo(String name) {
        return pimsSysReqTestitemDao.getTestitemInfo(name);
    }
}
