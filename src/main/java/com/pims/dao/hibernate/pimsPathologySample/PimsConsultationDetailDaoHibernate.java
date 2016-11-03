package com.pims.dao.hibernate.pimsPathologySample;

import com.pims.dao.pimspathologysample.PimsConsultationDetailDao;
import com.pims.dao.pimspathologysample.PimsPathologyConsultationDao;
import com.pims.model.PimsBaseModel;
import com.pims.model.PimsConsultationDetail;
import com.pims.model.PimsPathologyConsultation;
import com.pims.model.PimsSampleResult;
import com.smart.dao.hibernate.GenericDaoHibernate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by king on 2016/10/25.
 */
@Repository("pimsConsultationDetailDao")
public class PimsConsultationDetailDaoHibernate extends GenericDaoHibernate<PimsConsultationDetail, Long>
        implements PimsConsultationDetailDao {

    public PimsConsultationDetailDaoHibernate() {
        super(PimsConsultationDetail.class);
    }

    /**
     * 更新会诊结果信息
     * @param model
     */
    @Override
    public void updateDetil(PimsConsultationDetail model) {
        String sql = " update pims_consultation_detail set detstate = 1, detconsultationtime = sysdate,detadvice = '" + model.getDetadvice()+
                "' where detconsultationid = " + model.getDetconsultationid() + " and detdoctorid = '"+model.getDetdoctorid()+"'";
        getSession().createSQLQuery(sql).executeUpdate();
    }
}
