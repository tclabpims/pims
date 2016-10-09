package com.pims.service.pimssyspathology;

import com.pims.model.PimsSysReqMaterial;
import com.pims.service.GenericManager;
import com.pims.webapp.controller.GridQuery;

import java.util.List;

/**
 * Created by 909436637@qq.com on 2016/10/9.
 * Description:
 */
public interface PimsSysReqMaterialManager extends GenericManager<PimsSysReqMaterial,Long> {
    List<PimsSysReqMaterial> getMaterialList(GridQuery gridQuery);

    Integer countMaterial(String query);
}
