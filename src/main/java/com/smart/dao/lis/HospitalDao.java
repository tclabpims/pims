package com.smart.dao.lis;

import com.smart.dao.GenericDao;
import com.smart.model.lis.Hospital;

import java.util.Map;

public interface HospitalDao extends GenericDao<Hospital, Long> {
    Map<Long,Hospital> getHosMap(String sampleids);

}
