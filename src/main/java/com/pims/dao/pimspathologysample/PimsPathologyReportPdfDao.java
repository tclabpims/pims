package com.pims.dao.pimspathologysample;

import com.pims.model.PimsPathologyReportPdf;
import com.smart.dao.GenericDao;
/**
 * Created by king on 2016/10/10.
 */
public interface PimsPathologyReportPdfDao extends GenericDao<PimsPathologyReportPdf,Long> {
    /**
     * 根据标本ID获取生成的PDF报告
     * @param sampleid
     * @return
     */
    PimsPathologyReportPdf getPdfBySampleId(Long sampleid);
}
