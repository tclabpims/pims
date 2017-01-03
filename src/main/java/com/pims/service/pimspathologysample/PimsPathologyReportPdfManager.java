package com.pims.service.pimspathologysample;

import com.pims.model.PimsPathologyReportPdf;
import com.smart.service.GenericManager;

/**
 * Created by king on 2016/10/10.
 */
public interface PimsPathologyReportPdfManager extends GenericManager<PimsPathologyReportPdf,Long> {
    /**
     * 根据标本ID获取生成的PDF报告
     * @param sampleid
     * @return
     */
    PimsPathologyReportPdf getPdfBySampleId(Long sampleid);
}
