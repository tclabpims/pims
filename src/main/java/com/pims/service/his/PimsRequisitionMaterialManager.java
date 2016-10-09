package com.pims.service.his;

import com.pims.model.PimsRequisitionMaterial;
import com.smart.service.GenericManager;

import java.util.List;

/**
 * Created by king on 2016/10/7
 */
public interface PimsRequisitionMaterialManager extends GenericManager<PimsRequisitionMaterial,Long> {
    /**
     * 获取申请材料列表
     * @param
     * @return
     */
    List<PimsRequisitionMaterial> getListByReqId(long reqId);
    /**
     * 逻辑删除申请材料
     * @param id
     * @returnpims
     */
    boolean delete(long id);
}
