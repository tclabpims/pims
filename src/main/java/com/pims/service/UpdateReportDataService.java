package com.pims.service;

import com.pims.model.PimsPathologyReportPdf;
import com.pims.model.PimsPathologySample;
import com.pims.model.PimsSysPathology;

public interface UpdateReportDataService {

    void insert(PimsPathologySample sample, PimsPathologyReportPdf rpdf, PimsSysPathology psp);

}
