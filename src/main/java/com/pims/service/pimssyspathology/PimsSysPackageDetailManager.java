package com.pims.service.pimssyspathology;

import com.pims.model.PimsSysPackageDetail;
import com.smart.service.GenericManager;

import java.util.List;

/**
 * Created by 909436637@qq.com on 2016/11/3.
 * Description:
 */
public interface PimsSysPackageDetailManager extends GenericManager<PimsSysPackageDetail, Long> {

    void  deleteByPackageId(long packageId);

    void batchSave(List<PimsSysPackageDetail> lis);
}
