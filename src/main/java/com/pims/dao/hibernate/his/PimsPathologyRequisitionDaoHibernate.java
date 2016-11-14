package com.pims.dao.hibernate.his;

import com.alibaba.fastjson.JSONArray;
import com.pims.dao.his.PimsPathologyRequisitionDao;
import com.pims.model.PimsBaseModel;
import com.pims.model.PimsPathologyRequisition;
import com.pims.model.PimsPathologySample;
import com.pims.model.PimsRequisitionMaterial;
import com.pims.service.his.PimsPathologyRequisitionManager;
import com.pims.service.his.PimsRequisitionMaterialManager;
import com.smart.dao.hibernate.GenericDaoHibernate;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
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
     * 组装sql
     * @param buffer
     * @param pims
     * @return
     */
    public StringBuffer getStringSql(StringBuffer buffer,PimsBaseModel pims){
        if (!StringUtils.isEmpty(pims.getReq_code())) {
            buffer.append("and RequisitionNo like '%" + pims.getReq_code().toUpperCase()+"%'");//申请单号
        }
        if (!StringUtils.isEmpty(pims.getPatient_name())) {
            buffer.append(" and ReqPatientName  like '%" + pims.getPatient_name().toUpperCase()+"%'");//病人姓名
        }
        if (!StringUtils.isEmpty(pims.getSend_hosptail())) {
            buffer.append(" and  ReqSendHospital like '%" + pims.getSend_hosptail().toUpperCase()+"%'");//送检医院
        }
        if (pims.getReq_bf_time() != null) {
            buffer.append(" and ReqDate >= :req_bf_time");//起始时间
        }
        if (pims.getReq_af_time() != null) {
            buffer.append(" and  ReqDate < :req_af_time");//截至时间
        }
        if (!StringUtils.isEmpty(pims.getSend_dept())) {
            buffer.append(" and  ReqDeptName like '%" + pims.getSend_dept().toUpperCase()+"%'");//送检科室
        }
        if (!StringUtils.isEmpty(pims.getSend_doctor())) {
            buffer.append(" and ReqDoctorName like '%" + pims.getSend_doctor().toUpperCase()+"%'");//送检医生
        }
        if (!StringUtils.isEmpty(pims.getReq_sts())) {
            buffer.append(" and  ReqState = " + pims.getReq_sts());//申请状态
        }
        return buffer;
    }

    /**
     * 查询申请单列表
     * @param pims
     * @return
     */
    public List<PimsPathologyRequisition> getRequisitionInfo(PimsBaseModel pims){
        StringBuffer buffer = new StringBuffer();
        buffer.append(" from PimsPathologyRequisition where ReqIsDeleted = 0 ");//正常单据
        buffer = getStringSql(buffer,pims);
        String orderby = (pims.getSidx()==null|| pims.getSidx().trim().equals(""))?"requisitionno":pims.getSidx();
        buffer.append(" order by " + orderby + " " +pims.getSord());
        System.out.println(buffer.toString());
        return pagingList(buffer.toString(),pims.getStart(),pims.getEnd(),pims.getReq_bf_time(),pims.getReq_af_time());
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
        buffer = getStringSql(buffer,pims);
        System.out.println(buffer.toString());
        return countTotal(buffer.toString(),pims.getReq_bf_time(),pims.getReq_af_time()).intValue();
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
            Query query = getSession().createSQLQuery(" update PIMS_PATHOLOGY_REQUISITION set ReqIsDeleted = 1 where requisitionid = "+ id);//逻辑删除申请单
            query.executeUpdate();
            getSession().createSQLQuery("delete from pims_requisition_material where requisitionid = "+ id).executeUpdate();//删除申请材料表
            getSession().createSQLQuery("delete from pims_requisition_testitem where requisitionid = "+ id).executeUpdate();//删除检查项目表
            return true;
        }
    }
    /**
     * 查询申请单详细信息
     * @param id
     * @return
     */
    @Override
    public PimsPathologyRequisition getBySampleNo(Long id) {
        return (PimsPathologyRequisition)getSession().createQuery("from PimsPathologyRequisition where requisitionid = "+ id).uniqueResult();
    }
    /**
     * 根据病种类别查询最大单据号
     * @param reqpathologyid
     * @return
     */
    @Override
    public String getMaxCode(String reqpathologyid) {
        StringBuffer sb = new StringBuffer();
        sb.append(" select max(requisitionno) from pims_pathology_requisition where reqisdeleted = 0 ");
        if(!StringUtils.isEmpty(reqpathologyid)){
            sb.append(" and reqpathologyid = " + reqpathologyid);
        }
        Query query = getSession().createSQLQuery(sb.toString());
        Object o = query.uniqueResult();
        if(o == null) return null;
        return o.toString();
    }
    /**
     * 更新申请单的可使用状态
     * @param ppr
     * @param state
     * @return
     */
    @Override
    public boolean updateReqState(PimsPathologySample ppr, int state) {
        if(ppr == null){
            return false;
        }else{
            getSession().createSQLQuery("update pims_pathology_requisition set reqstate ="+ state +",reqsampleid ="
                    + ppr.getSampleid()+"  where requisitionno = '" + ppr.getSamrequistionid()+"'").executeUpdate();
            return  true;
        }
    }

    /**
     * 是否可以被修改或删除
     * @param id
     * @return
     */
    @Override
    public boolean canChange(Long id) {
        if(StringUtils.isEmpty(String.valueOf(id))){
            return false;
        }else{
            List list = getSession().createSQLQuery("select 1 from pims_pathology_requisition where reqstate = 0 and requisitionid = " + id).list();
            if(list == null || list.size() == 0){
                return false;
            }
            return true;
        }
    }

    @Autowired
    private PimsRequisitionMaterialManager pimsRequisitionMaterialManager;
    @Autowired
    private PimsPathologyRequisitionManager pimsPathologyRequisitionManager;
    /**
     * 保存申请单据
     * @param materials
     * @param ppr
     * @return
     */
    @Override
    public PimsPathologyRequisition insertOrUpdate(JSONArray materials, PimsPathologyRequisition ppr) {
        if(StringUtils.isEmpty(String.valueOf(ppr.getRequisitionid())) || String.valueOf(ppr.getRequisitionid()).equals("0")){
            String maxCode = getMaxCode(null);
            if(!StringUtils.isEmpty(maxCode) && Long.parseLong(maxCode) >= Long.parseLong(ppr.getRequisitionno()) ){
                ppr.setRequisitionno(String.valueOf(Long.parseLong(maxCode)+1));
            }
        }
        ppr = pimsPathologyRequisitionManager.save(ppr);
        //删除组织信息
        getSession().createSQLQuery("delete from pims_requisition_material where requisitionid = "+ ppr.getRequisitionid()).executeUpdate();//删除申请材料表
        getSession().createSQLQuery("delete from pims_requisition_testitem where requisitionid = "+ ppr.getRequisitionid()).executeUpdate();//删除检查项目表
        for(int i= 0;i<materials.size();i++){
            Map map = (Map) materials.get(i);
            PimsRequisitionMaterial mater = (PimsRequisitionMaterial) setBeanProperty(map,PimsRequisitionMaterial.class);
            if(String.valueOf(mater.getRequisitionid()).equals("0")){
                mater.setRequisitionid(ppr.getRequisitionid());
            }
//            String sql = "insert into pims_requisition_material (requisitionid,materialid,reqmcustomercode,reqmmaterialname,reqmmaterialtype,reqmsamplingparts," +
//                    "reqmspecialrequirements,reqmremark,reqmcreateuser,reqmcreatetime) values (:requisitionid,:materialid,:reqmcustomercode,:reqmmaterialname," +
//                    ":reqmmaterialtype,:reqmsamplingparts,:reqmspecialrequirements,:reqmremark,:reqmcreateuser,:reqmcreatetime)";
//            Query query = getSession().createSQLQuery(sql);
//            query.setLong("requisitionid",mater.getRequisitionid());
//            query.setLong("materialid",mater.getMaterialid());
//            query.setString("reqmcustomercode",mater.getReqmcustomercode());
//            query.setString("reqmmaterialname",mater.getReqmmaterialname());
//            query.setString("reqmmaterialtype",mater.getReqmmaterialtype());
//            query.setString("reqmsamplingparts",mater.getReqmsamplingparts());
//            query.setString("reqmspecialrequirements",mater.getReqmspecialrequirements());
//            query.setString("reqmremark",mater.getReqmremark());
//            query.setString("reqmcreateuser",mater.getReqmcreateuser());
//            query.setTimestamp("reqmcreatetime",mater.getReqmcreatetime());
//            query.executeUpdate();
            pimsRequisitionMaterialManager.save(mater);
        }
        return ppr;
    }

    @Override
    public String getSjcl(Long id) {
        String sql = "select listagg(reqmmaterialname,',') within GROUP (order by requisitionid)   from  " +
                "pims_requisition_material  t where  requisitionid = " + id;
        Object o = getSession().createSQLQuery(sql).uniqueResult();
        if(o == null) return "";
        return o.toString();
    }
}
