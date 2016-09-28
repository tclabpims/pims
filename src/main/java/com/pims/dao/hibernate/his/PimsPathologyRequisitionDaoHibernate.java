package com.pims.dao.hibernate.his;

import com.pims.dao.hibernate.GenericDaoHibernate;
import com.pims.dao.his.PimsPathologyRequisitionDao;
import com.pims.model.PimsPathologyRequisition;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by king on 2016/9/28.
 */
@Repository("pimsPathologyRequisitionDao")
public class PimsPathologyRequisitionDaoHibernate extends GenericDaoHibernate<PimsPathologyRequisition,Long>
        implements PimsPathologyRequisitionDao{
    public PimsPathologyRequisitionDaoHibernate(){super(PimsPathologyRequisition.class);}

    /**
     *
     * @param req_code 申请单号
     * @param patient_name 病人姓名
     * @param send_hosptail 送检医院
     * @param req_bf_time 送检起始时间
     * @param req_af_time  送检截至时间
     * @param send_dept 送检科室
     * @param send_doctor 送检医生
     * @param req_sts  送检状态
     * @return 返回申请列表
     */
    public List<PimsPathologyRequisition> getRequisitionInfo(String req_code, String patient_name, String send_hosptail, String req_bf_time,
                                                      String req_af_time, String send_dept, String send_doctor, String req_sts){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyymmdd");
        StringBuffer buffer = new StringBuffer();
        buffer.append("from Pims_Pathology_Requisition where 1 = 1 ");
        if(!StringUtils.isEmpty(req_code)){
            buffer.append("and RequisitionNo = " +  req_code);
        }
        if(!StringUtils.isEmpty(patient_name)){
            buffer.append(" and ReqPatientName  = " + patient_name);
        }
        if(!StringUtils.isEmpty(send_hosptail)){
            buffer.append(" and  ReqSendHospital = " + send_hosptail);
        }
        if(!StringUtils.isEmpty(req_bf_time)){
            try {
                buffer.append(" and ReqDate >= " + sdf.parse(req_bf_time));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if(!StringUtils.isEmpty(req_af_time)){
            try {
                buffer.append(" and  ReqDate < " + sdf.parse(req_af_time));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if(!StringUtils.isEmpty(send_dept)){
            buffer.append(" and  ReqDeptName = " + send_dept);
        }
        if(!StringUtils.isEmpty(send_doctor)){
            buffer.append(" and ReqDoctorName = " + send_doctor);
        }
        if(!StringUtils.isEmpty(req_sts)){
            buffer.append(" and  ReqState = " + req_sts);
        }
        Query query = getSession().createQuery(buffer.toString());
        return query.list();
    }
}
