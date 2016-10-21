package com.pims.dao.pimssyspathology;

import com.pims.model.PimsHospitalPathologyInfo;
import com.smart.dao.GenericDao;

import java.util.List;

/**
 * Created by 909436637@qq.com on 2016/10/20.
 * Description:
 */
public interface PimsHospitalPathologyInfoDao extends GenericDao<PimsHospitalPathologyInfo, Long> {
    List<PimsHospitalPathologyInfo> getPathologyByUserId(String s, long userId);
}
