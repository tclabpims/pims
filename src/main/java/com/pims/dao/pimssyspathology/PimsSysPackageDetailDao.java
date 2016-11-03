package com.pims.dao.pimssyspathology;

import com.pims.model.PimsSysPackageDetail;
import com.smart.dao.GenericDao;

import java.util.List;

/**
 * Created by 909436637@qq.com on 2016/11/3.
 * Description:
 */
public interface PimsSysPackageDetailDao extends GenericDao<PimsSysPackageDetail, Long> {

    void  deleteByPackageId(long packageId);

    void batchSave(List<PimsSysPackageDetail> lis);
}
