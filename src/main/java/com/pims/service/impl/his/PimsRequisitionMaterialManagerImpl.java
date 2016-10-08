package com.pims.service.impl.his;

import com.pims.dao.his.PimsRequisitionMaterialDao;
import com.pims.model.PimsRequisitionMaterial;
import com.pims.service.his.PimsRequisitionMaterialManager;
import com.smart.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by king on 2016/10/7
 */
@Service("pimsRequisitionMaterialManager")
public class PimsRequisitionMaterialManagerImpl extends GenericManagerImpl<PimsRequisitionMaterial,Long>
        implements PimsRequisitionMaterialManager {
    private PimsRequisitionMaterialDao pimsRequisitionMaterialDao;

    @Autowired
    public void setPimsPathologyRequisitionDao(PimsRequisitionMaterialDao pimsRequisitionMaterialDao) {
        this.dao = pimsRequisitionMaterialDao;
        this.pimsRequisitionMaterialDao = pimsRequisitionMaterialDao;
    }

    /**
     * 查询申请材料
     * @param reqId
     * @return
     */
    @Override
    public List<PimsRequisitionMaterial> getListByReqId(long reqId) {
        return pimsRequisitionMaterialDao.getListByReqId(reqId);
    }

    /**
     * 删除申请材料
     * @param id
     * @return
     */
    @Override
    public boolean delete(long id) {
        return pimsRequisitionMaterialDao.delete(id);
    }
}
