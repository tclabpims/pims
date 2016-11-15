package com.pims.dao.hibernate.pimsPathologySample;

import com.pims.dao.pimspathologysample.PimsConsultationDetailDao;
import com.pims.dao.pimspathologysample.PimsPathologyConsultationDao;
import com.pims.model.PimsBaseModel;
import com.pims.model.PimsConsultationDetail;
import com.pims.model.PimsPathologyConsultation;
import com.pims.model.PimsSampleResult;
import com.smart.dao.hibernate.GenericDaoHibernate;
import org.apache.commons.lang.StringUtils;
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

    public  StringBuffer getSql(StringBuffer sb,PimsBaseModel map){
        if (map.getReq_bf_time() != null) {
            sb.append(" and consponsoredtime >= :req_bf_time");
        }
        if (map.getReq_af_time() != null) {
            sb.append(" and  consponsoredtime < :req_af_time");
        }
        if(map.getPatient_name() != null){//受邀医生
            sb.append(" and detdoctorid = '"+ map.getPatient_name()+"'");
        }
        if(!StringUtils.isEmpty(map.getReq_sts())){
            sb.append(" and conconsultationstate =" + map.getReq_sts());
        }
        return  sb;
    }

    @Override
    public List getConList(PimsBaseModel map) {
        StringBuffer sb = new StringBuffer();
        sb.append(" from PimsPathologyConsultation,PimsConsultationDetail where consultationid = detconsultationid ");
        getSql(sb,map);
        return pagingList(sb.toString(),map.getStart(),map.getEnd(),map.getReq_bf_time(),map.getReq_af_time());
    }

    @Override
    public int getReqListNum(PimsBaseModel map) {
        StringBuffer sb = new StringBuffer();
        sb.append(" select count(1) from Pims_Pathology_Consultation,Pims_Consultation_Detail where consultationid = detconsultationid ");
        getSql(sb,map);
        return countTotal(sb.toString(),map.getReq_bf_time(),map.getReq_af_time());
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
