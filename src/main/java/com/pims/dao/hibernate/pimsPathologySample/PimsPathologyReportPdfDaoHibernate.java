package com.pims.dao.hibernate.pimsPathologySample;

import com.pims.dao.pimspathologysample.PimsPathologyReportPdfDao;
import com.pims.model.PimsPathologyReportPdf;
import com.smart.dao.hibernate.GenericDaoHibernate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by king on 2016/10/10.
 */
@Repository("pimsPathologyReportPdfDao")
public class PimsPathologyReportPdfDaoHibernate extends GenericDaoHibernate<PimsPathologyReportPdf,Long> implements PimsPathologyReportPdfDao {
    public PimsPathologyReportPdfDaoHibernate(){super(PimsPathologyReportPdf.class);}

    @Override
    public PimsPathologyReportPdf getPdfBySampleId(Long sampleid) {
        StringBuffer sb = new StringBuffer();
        sb.append(" from PimsPathologyReportPdf where pdfsampleid=" + sampleid);
        Object o = getSession().createQuery(sb.toString()).uniqueResult();
        if(o == null){
            return  null;
        }else{
            return (PimsPathologyReportPdf)o;
        }
    }

    @Override
    public boolean deletePDF(Long sampleid) {
        StringBuffer sb = new StringBuffer();
        sb.append(" delete from pims_pathology_report_pdf where pdfsampleid ="+ sampleid);
        getSession().createSQLQuery(sb.toString()).executeUpdate();
        return true;
    }

    @Override
    public Map<Long,PimsPathologyReportPdf> getPDFList(String sampleids) {
        Map<Long,PimsPathologyReportPdf> map = new HashMap();
        StringBuffer sb = new StringBuffer();
        sb.append(" from PimsPathologyReportPdf where pdfsampleid in ("+sampleids+")");
        List<PimsPathologyReportPdf> pdflist = getSession().createQuery(sb.toString()).list();
        for(PimsPathologyReportPdf pdf:pdflist){
            map.put(pdf.getPdfsampleid(),pdf);
        }
        return  map;
    }
}
