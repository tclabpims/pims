package com.pims.dao.pimssyspathology;

import com.pims.model.PimsSysReqMaterial;
import com.smart.dao.GenericDao;

import java.util.List;

/**
 * Created by 909436637@qq.com on 2016/10/9.
 * Description:
 */
public interface PimsSysReqMaterialDao extends GenericDao<PimsSysReqMaterial, Long> {
    List<PimsSysReqMaterial> getAllInfo();
}
