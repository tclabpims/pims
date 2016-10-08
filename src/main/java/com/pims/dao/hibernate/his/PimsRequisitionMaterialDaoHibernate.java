package com.pims.dao.hibernate.his;

import com.pims.dao.his.PimsRequisitionMaterialDao;
import com.pims.model.PimsRequisitionMaterial;
import com.smart.dao.hibernate.GenericDaoHibernate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by king on 2016/10/7
 */
@Repository("pimsRequisitionMaterialDao")
public class PimsRequisitionMaterialDaoHibernate extends GenericDaoHibernate<PimsRequisitionMaterial,Long>
        implements PimsRequisitionMaterialDao{
    public PimsRequisitionMaterialDaoHibernate(){super(PimsRequisitionMaterial.class);}

    /**
     * 查询申请材料
     * @param reqId
     * @return
     */
    @Override
    public List<PimsRequisitionMaterial> getListByReqId(long reqId) {
        return getSession().createQuery(" from PimsRequisitionMaterial where requisitionid = " + reqId).list();
    }

    /**
     * 删除申请材料
     * @param id
     * @return
     */
    @Override
    public boolean delete(long id) {
        getSession().createSQLQuery("delete from  Pims_Requisition_Material where requisitionid =  " + id).executeUpdate();
        return true;
    }
}
