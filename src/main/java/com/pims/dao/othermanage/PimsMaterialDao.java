package com.pims.dao.othermanage;

import com.pims.model.PimsBaseModel;
import com.pims.model.PimsDisposableMaterial;
import com.smart.dao.GenericDao;
import java.util.Map;
import java.util.List;

/**
 * Created by zp on 2016/12/16.
 */
public interface PimsMaterialDao extends GenericDao<PimsDisposableMaterial,Long> {

    List<PimsDisposableMaterial> getMarList(PimsBaseModel map);

    boolean deleteMar(Map map);

    int getReqListNum(PimsBaseModel map);

    String sampleCode();
}
