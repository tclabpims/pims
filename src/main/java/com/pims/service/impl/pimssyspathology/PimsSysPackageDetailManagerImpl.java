package com.pims.service.impl.pimssyspathology;

import com.pims.dao.pimssyspathology.PimsSysPackageDetailDao;
import com.pims.model.PimsSysPackageDetail;
import com.pims.service.pimssyspathology.PimsSysPackageDetailManager;
import com.smart.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 909436637@qq.com on 2016/11/3.
 * Description:
 */
@Service("pimsSysPackageDetailManager")
public class PimsSysPackageDetailManagerImpl extends GenericManagerImpl<PimsSysPackageDetail, Long> implements PimsSysPackageDetailManager {

    private PimsSysPackageDetailDao packageDetailDao;

    @Autowired
    public void setPackageDetailDao(PimsSysPackageDetailDao packageDetailDao) {
        this.dao = packageDetailDao;
        this.packageDetailDao = packageDetailDao;
    }

    @Override
    public void deleteByPackageId(long packageId) {
        packageDetailDao.deleteByPackageId(packageId);
    }

    @Override
    public void batchSave(List<PimsSysPackageDetail> lis) {
        packageDetailDao.batchSave(lis);
    }
}
