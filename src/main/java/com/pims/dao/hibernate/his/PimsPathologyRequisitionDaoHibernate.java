package com.pims.dao.hibernate.his;

import com.pims.dao.his.PimsPathologyRequisitionDao;
import com.pims.model.PimsBaseModel;
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
     * @param pims
     * @return
     */
    public List<PimsPathologyRequisition> getRequisitionInfo(PimsBaseModel pims){
        StringBuffer buffer = new StringBuffer();
        buffer.append(" from PimsPathologyRequisition where ReqIsDeleted = 0 ");
        if(!StringUtils.isEmpty(pims.getReq_code())){
            buffer.append("and RequisitionNo = " +  pims.getReq_code());
        }
        if(!StringUtils.isEmpty(pims.getPatient_name())){
            buffer.append(" and ReqPatientName  = " + pims.getPatient_name());
        }
        if(!StringUtils.isEmpty(pims.getSend_hosptail())){
            buffer.append(" and  ReqSendHospital = " + pims.getSend_hosptail());
        }
        if(!StringUtils.isEmpty(pims.getReq_bf_time())){
            buffer.append(" and ReqDate >= to_date('" + pims.getReq_bf_time()+"','YYYYMMDD')");
        }
        if(!StringUtils.isEmpty(pims.getReq_af_time())){
            buffer.append(" and  ReqDate < to_date('" + pims.getReq_af_time()+"','YYYYMMDD')+1");
        }
        if(!StringUtils.isEmpty(pims.getSend_dept())){
            buffer.append(" and  ReqDeptName = " + pims.getSend_dept());
        }
        if(!StringUtils.isEmpty(pims.getSend_doctor())){
            buffer.append(" and ReqDoctorName = " + pims.getSend_doctor());
        }
        if(!StringUtils.isEmpty(pims.getReq_sts())){
            buffer.append(" and  ReqState = " + pims.getReq_sts());
        }
        String orderby = (pims.getSidx()==null|| pims.getSidx().trim().equals(""))?"reqcustomercode":pims.getSidx();
        buffer.append(" order by " + orderby + " " +pims.getSord());
        System.out.println(buffer.toString());
        return pagingList(buffer.toString(),pims.getStart(),pims.getEnd());
//        Query query = getSession().createQuery(buffer.toString());
//        System.out.println(query.getQueryString());
//        List<PimsPathologyRequisition> list = query.list();
//        return list;
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
            Query query = getSession().createSQLQuery(" update PIMS_PATHOLOGY_REQUISITION set ReqIsDeleted = 1 where requisitionid = "+ id);
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

    /**
     * 获取总数量
     * @param pims
     * @return
     */
    @Override
    public int getReqListNum(PimsBaseModel pims) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(" select count(1) from PIMS_PATHOLOGY_REQUISITION where ReqIsDeleted = 0 ");
        if(!StringUtils.isEmpty(pims.getReq_code())){
            buffer.append("and RequisitionNo = " +  pims.getReq_code());
        }
        if(!StringUtils.isEmpty(pims.getPatient_name())){
            buffer.append(" and ReqPatientName  = " + pims.getPatient_name());
        }
        if(!StringUtils.isEmpty(pims.getSend_hosptail())){
            buffer.append(" and  ReqSendHospital = " + pims.getSend_hosptail());
        }
        if(!StringUtils.isEmpty(pims.getReq_bf_time())){
            buffer.append(" and ReqDate >= to_date('" + pims.getReq_bf_time()+"','YYYYMMDD')");
        }
        if(!StringUtils.isEmpty(pims.getReq_af_time())){
            buffer.append(" and  ReqDate < to_date('" + pims.getReq_af_time()+"','YYYYMMDD')+1");
        }
        if(!StringUtils.isEmpty(pims.getSend_dept())){
            buffer.append(" and  ReqDeptName = " + pims.getSend_dept());
        }
        if(!StringUtils.isEmpty(pims.getSend_doctor())){
            buffer.append(" and ReqDoctorName = " + pims.getSend_doctor());
        }
        if(!StringUtils.isEmpty(pims.getReq_sts())){
            buffer.append(" and  ReqState = " + pims.getReq_sts());
        }
        System.out.println(buffer.toString());
        return countTotal(buffer.toString()).intValue();
    }

    /**
     * 根据病种类别查询最大单据号
     * @param reqpathologyid
     * @return
     */
    @Override
    public String getMaxCode(int reqpathologyid) {
        StringBuffer sb = new StringBuffer();
        sb.append(" select max(requisitionno) from pims_pathology_requisition where reqisdeleted = 0 ");
        if(reqpathologyid != 999){
            sb.append(" and reqpathologyid = " + reqpathologyid);
        }
        Query query = getSession().createSQLQuery(sb.toString());
        Object o = query.uniqueResult();
        if(o == null) return null;
        return o.toString();
    }
}
