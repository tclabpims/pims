package com.pims.service.othermanagement;

import com.pims.model.PimsBaseModel;
import com.pims.model.PimsDisposableMaterial;
import com.smart.service.GenericManager;

import java.util.List;
import java.util.Map;

/**
 * Created by zp on 2016/12/16.
 */
public interface PimsMaterialManager extends GenericManager<PimsDisposableMaterial,Long> {
    List<PimsDisposableMaterial> getMarList(PimsBaseModel map);

    boolean deleteMar(Map map);

    int getReqListNum(PimsBaseModel map);

    String sampleCode();
}
