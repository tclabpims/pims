package com.pims.dao.his;

import com.pims.model.PimsRequisitionMaterial;
import com.smart.dao.GenericDao;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by king on 2016/10/7
 */
public interface PimsRequisitionMaterialDao extends GenericDao<PimsRequisitionMaterial,Long> {
    /**
     *查询申请材料列表
     * @param reqId
     * @return
     */
    List<PimsRequisitionMaterial> getListByReqId(long reqId);

    /**
     * 逻辑删除申请材料
     * @param id
     */
    @Transactional
    boolean delete(long id);


}

