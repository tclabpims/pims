package com.pims.dao.hibernate.his;

import com.alibaba.fastjson.JSONArray;
import com.pims.dao.his.PimsPathologyRequisitionDao;
import com.pims.model.*;
import com.pims.service.his.PimsPathologyRequisitionManager;
import com.pims.service.his.PimsRequisitionFieldManager;
import com.pims.service.his.PimsRequisitionMaterialManager;
import com.pims.service.pimssyspathology.PimsSysReqFieldManager;
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
    @Autowired
    private PimsSysReqFieldManager pimsSysReqFieldManager;
    @Autowired
    private PimsRequisitionFieldManager pimsRequisitionFieldManager;
    /**
     * 保存申请单据
     * @param materials
     * @param ppr
     * @return
     */
    @Override
    public PimsPathologyRequisition insertOrUpdate(JSONArray materials, PimsPathologyRequisition ppr,JSONArray fields) {
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
            pimsRequisitionMaterialManager.save(mater);
        }
        String sql = "delete from pims_requisition_field where requisitionid =" + ppr.getRequisitionid();
        getSession().createSQLQuery(sql).executeUpdate();//删除申请单配置字段
        for(int i= 0;i<fields.size();i++){
            Map map = (Map) fields.get(i);
            PimsSysReqField field = pimsSysReqFieldManager.getInfo((String)map.get("id"));
            PimsRequisitionField rf = new PimsRequisitionField();
            rf.setRequisitionid(ppr.getRequisitionid());//申请单号
            rf.setFieldid(field.getFieldid());//字段id
            rf.setReqfcustomerid(ppr.getReqcustomerid());//客户id
            rf.setReqfelementid(field.getFieelementid());//对象id
            rf.setReqfelementname(field.getFieelementname());//对象名称
            rf.setReqfelementtype(field.getFieelementtype());//对象类型
            rf.setReqfshowlevel(field.getFieshowlevel());//显示级别
            rf.setReqfpelementid(String.valueOf(field.getFiepelementid()));//上级对象id
            rf.setReqfdefaultvalue(field.getFiedefaultvalue());//默认值
            rf.setReqfsort(field.getFieshoworder());//显示顺序
            rf.setReqfvalue((String)map.get("value"));//值
            rf.setReqfcreateuser(ppr.getReqcreateuser());//创建人
            rf.setReqfcreatetime(ppr.getReqcreatetime());//创建时间
            pimsRequisitionFieldManager.save(rf);
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

    /**
     * 查询申请字段
     * @param map
     * @return
     */
    @Override
    public List searchLists(PimsBaseModel map) {
        StringBuffer sb = new StringBuffer();
        sb.append(" from PimsSysCustomerBasedata,PimsSysReqField where bastype = 2 and basrefdataid = fieldid and basuseflag = 1 and fieuseflag = 1 ");
        if(map == null){
            return  null;
        }
        if(!StringUtils.isEmpty(map.getReq_code())){
            sb.append(" and bascustomercode ='" + map.getReq_code() + "'");
        }
        if(!StringUtils.isEmpty(map.getLogyid())){
            sb.append(" and baspathologyid=" + map.getLogyid());
        }
        sb.append(" order by fieshoworder");
        return getSession().createQuery(sb.toString()).list();
    }

    /**
     * 查看申请字段
     * @param id
     * @return
     */
    @Override
    public List searchViews(long id) {
        StringBuffer sb = new StringBuffer();
        sb.append(" from PimsRequisitionField a ,PimsSysReqField b where a.fieldid = b.fieldid and b.fieuseflag = 1 and a.requisitionid = "+ id + " order by b.fieshoworder");
        return getSession().createQuery(sb.toString()).list();
    }
}
