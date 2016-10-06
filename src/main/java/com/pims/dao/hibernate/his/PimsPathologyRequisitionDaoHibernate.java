package com.pims.dao.hibernate.his;

import com.pims.dao.his.PimsPathologyRequisitionDao;
import com.pims.model.PimsPathologyRequisition;
import com.smart.dao.hibernate.GenericDaoHibernate;
import org.hibernate.Query;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

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
        StringBuffer buffer = new StringBuffer();
        buffer.append(" from PimsPathologyRequisition where 1 = 1 ");
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
        Query query = getSession().createQuery(buffer.toString());
//        StringBuffer buff = new StringBuffer();
//        buff.append( "from PimsPathologyRequisition where 1=1 ");
//        Query query = getSession().createQuery(buff.toString());
        System.out.println(query.getQueryString());
        List<PimsPathologyRequisition> list = query.list();
        return list;
    }

    /**
     * 逻辑删除单据号
     * @param id
     * @return
     */
    @Override
    public boolean delete(Long id) {
        if(id == null){
            return false;
        }else{
            Query query = getSession().createQuery("from PimsPathologyRequisition set ReqState = 'N' where requisitionid = "+ id);
            query.executeUpdate();
            return true;
        }
    }

    /**
     * 查询单据号是否存在
     * @param id
     * @return
     */
    @Override
    public PimsPathologyRequisition getBySampleNo(Long id) {
        if(id == null){
            return null;
        }else{
            Query query = getSession().createQuery("from PimsPathologyRequisition where requisitionid = "+ id);
            if(query.list() == null || query.list().size() != 1){
                return null;
            }else{
               return (PimsPathologyRequisition)query.list().get(0);
            }
        }
    }

    /**
     * 获取最大ID
     * @return
     */
    @Override
    public Long getMaxId() {
        String sql = "select  Seq_RequisitionId.nextval nextvalue from dual";
        Long maxId = (Long)(getSession().createSQLQuery(sql).addScalar("nextvalue", StandardBasicTypes.LONG) ).uniqueResult();
        System.out.println("申请单最大ID为：" + maxId);
        return maxId;
    }
}
