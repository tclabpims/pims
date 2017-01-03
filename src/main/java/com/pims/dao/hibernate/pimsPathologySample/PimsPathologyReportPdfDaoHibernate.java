package com.pims.dao.hibernate.pimsPathologySample;

import com.pims.dao.pimspathologysample.PimsPathologyReportPdfDao;
import com.pims.model.PimsPathologyReportPdf;
import com.smart.dao.hibernate.GenericDaoHibernate;
import org.springframework.stereotype.Repository;

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
}
