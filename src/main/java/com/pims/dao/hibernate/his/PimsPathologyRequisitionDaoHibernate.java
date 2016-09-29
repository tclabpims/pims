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
import java.util.Map;

/**
 * Created by king on 2016/9/28.
 */
@Repository("pimsPathologyRequisitionDao")
public class PimsPathologyRequisitionDaoHibernate extends GenericDaoHibernate<PimsPathologyRequisition,Long>
        implements PimsPathologyRequisitionDao{
    public PimsPathologyRequisitionDaoHibernate(){super(PimsPathologyRequisition.class);}

    /**
     * 查询申请单列表
     * @param map
     * @return
     */
    public List<PimsPathologyRequisition> getRequisitionInfo(Map map){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyymmdd");
        StringBuffer buffer = new StringBuffer();
        buffer.append(" from Pims_Pathology_Requisition where 1 = 1 ");
        if(!StringUtils.isEmpty(map.get("req_code"))){
            buffer.append("and RequisitionNo = " +  map.get("req_code"));
        }
        if(!StringUtils.isEmpty(map.get("patient_name"))){
            buffer.append(" and ReqPatientName  = " + map.get("patient_name"));
        }
        if(!StringUtils.isEmpty(map.get("send_hosptail"))){
            buffer.append(" and  ReqSendHospital = " + map.get("send_hosptail"));
        }
        if(!StringUtils.isEmpty(map.get("req_bf_time"))){
            buffer.append(" and ReqDate >= to_date('" + map.get("req_bf_time")+"','YYYYMMDD')");
        }
        if(!StringUtils.isEmpty(map.get("req_af_time"))){
            buffer.append(" and  ReqDate < to_date('" + map.get("req_af_time")+"','YYYYMMDD')+1");
        }
        if(!StringUtils.isEmpty(map.get("send_dept"))){
            buffer.append(" and  ReqDeptName = " + map.get("send_dept"));
        }
        if(!StringUtils.isEmpty(map.get("send_doctor"))){
            buffer.append(" and ReqDoctorName = " + map.get("send_doctor"));
        }
        if(!StringUtils.isEmpty(map.get("req_sts"))){
            buffer.append(" and  ReqState = " + map.get("req_sts"));
        }
        //Query query = getSession().createQuery(buffer.toString());
        StringBuffer buff = new StringBuffer();
        buff.append( "from Pims_Pathology_Requisition where 1=1 ");
        Query query = getSession().createQuery(buff.toString());
        System.out.println(query.getQueryString());
        List<PimsPathologyRequisition> list = query.list();
        if(list == null){
           return null;
        }
        return list;
    }
}
