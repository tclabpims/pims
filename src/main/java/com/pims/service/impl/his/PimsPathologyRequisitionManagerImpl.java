package com.pims.service.impl.his;

import com.pims.dao.his.PimsPathologyRequisitionDao;
import com.pims.model.PimsPathologyRequisition;
import com.pims.service.his.PimsPathologyRequisitionManager;
import com.smart.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by king on 2016/9/28.
 */
@Service("pimsPathologyRequisitionManager")
public class PimsPathologyRequisitionManagerImpl extends GenericManagerImpl<PimsPathologyRequisition,Long>
        implements PimsPathologyRequisitionManager {
    private PimsPathologyRequisitionDao pimsPathologyRequisitionDao;

    @Autowired
    public void setPimsPathologyRequisitionDao(PimsPathologyRequisitionDao pimsPathologyRequisitionDao) {
        this.dao = pimsPathologyRequisitionDao;
        this.pimsPathologyRequisitionDao = pimsPathologyRequisitionDao;
    }

    /**
     * 获取申请单列表
     * @param map
     * @return
     */
    @Override
    public List<PimsPathologyRequisition> getRequisitionInfo(Map map) {
        return pimsPathologyRequisitionDao.getRequisitionInfo(map);
    }

    /**
     * 逻辑删除单据号
     * @param id
     * @return
     */
    @Override
    public boolean delete(Long id) {
        return pimsPathologyRequisitionDao.delete(id);
    }

    /**
     * 查询单据号是否存在
     * @param id
     * @return
     */
    @Override
    public PimsPathologyRequisition getBySampleNo(Long id) {
        return pimsPathologyRequisitionDao.getBySampleNo(id);
    }

    /**
     * 获取最大ID
     * @return
     */
    @Override
    public Long getMaxId() {
        return pimsPathologyRequisitionDao.getMaxId();
    }

}
