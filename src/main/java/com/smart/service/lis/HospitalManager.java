package com.smart.service.lis;

import com.pims.webapp.controller.GridQuery;
import com.smart.model.lis.Hospital;
import com.smart.service.GenericManager;

import java.util.List;

public interface HospitalManager extends GenericManager<Hospital, Long> {

    List<Hospital> getHospitalList(GridQuery gridQuery);

    Integer getHospital(String query);

}
