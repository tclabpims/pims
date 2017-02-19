package com.pims.service.impl.pimspathologysample;

import com.pims.dao.pimspathologysample.PimsPathologyReportPdfDao;
import com.pims.model.PimsPathologyReportPdf;
import com.pims.service.pimspathologysample.PimsPathologyReportPdfManager;
import com.smart.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * Created by king on 2016/10/10.
 */
@Service("pimsPathologyReportPdfManager")
public class PimsPathologyReportPdfManagerImpl extends GenericManagerImpl<PimsPathologyReportPdf,Long> implements PimsPathologyReportPdfManager {
    private PimsPathologyReportPdfDao pimsPathologyReportPdfDao;
    @Autowired
    public void setPimsPathologyReportPdfDao(PimsPathologyReportPdfDao pimsPathologyReportPdfDao) {
        this.pimsPathologyReportPdfDao = pimsPathologyReportPdfDao;
        this.dao = pimsPathologyReportPdfDao;
    }

    @Override
    public PimsPathologyReportPdf getPdfBySampleId(Long sampleid) {
        return pimsPathologyReportPdfDao.getPdfBySampleId(sampleid);
    }

    @Override
    public boolean deletePDF(Long sampleid) {
        return pimsPathologyReportPdfDao.deletePDF(sampleid);
    }

    @Override
    public Map<Long,PimsPathologyReportPdf> getPDFList(String sampleids) {
        return pimsPathologyReportPdfDao.getPDFList(sampleids);
    }
}
