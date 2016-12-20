package com.pims.service.impl.his;

import com.alibaba.fastjson.JSONArray;
import com.pims.dao.his.PimsPathologyRequisitionDao;
import com.pims.model.PimsBaseModel;
import com.pims.model.PimsPathologyRequisition;
import com.pims.model.PimsPathologySample;
import com.pims.service.his.PimsPathologyRequisitionManager;
import com.pims.webapp.controller.GridQuery;
import com.smart.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by king on 2016/9/28.
 */
@Service("pimsPathologyRequisitionManager")
public class PimsPathologyRequisitionManagerImpl extends GenericManagerImpl<PimsPathologyRequisition,Long>
        implements PimsPathologyRequisitionManager {
    private PimsPathologyRequisitionDao pimsPathologyRequisitionDao;

    @Autowired
    public void setPimsPathologyRequisitionDao(PimsPathologyRequisitionDao pimsPathologyRequisitionDao) {
        this.dao = pimsPathologyRequisitionDao;
        this.pimsPathologyRequisitionDao = pimsPathologyRequisitionDao;
    }

    /**
     * 获取申请单列表
     * @param pims
     * @return
     */
    @Override
    public List<PimsPathologyRequisition> getRequisitionInfo(PimsBaseModel pims) {
        return pimsPathologyRequisitionDao.getRequisitionInfo(pims);
    }

    /**
     * 逻辑删除单据号
     * @param id
     * @return
     */
    @Override
    public boolean delete(Long id) {
        return pimsPathologyRequisitionDao.delete(id);
    }

    /**
     * 查询单据号是否存在
     * @param id
     * @return
     */
    @Override
    public PimsPathologyRequisition getBySampleNo(Long id) {
        return pimsPathologyRequisitionDao.getBySampleNo(id);
    }

    /**
     * 获取总数量
     * @param pims
     * @return
     */
    @Override
    public int getReqListNum(PimsBaseModel pims) {
        return pimsPathologyRequisitionDao.getReqListNum(pims);
    }

    /**
     * 根据病种类别查询最大单据号
     * @param reqpathologyid
     * @return
     */
    @Override
    public String getMaxCode(String reqpathologyid) {
        return pimsPathologyRequisitionDao.getMaxCode(reqpathologyid);
    }

    /**
     * 更新申请单的可使用状态
     * @param ppr
     * @param state
     * @return
     */
    @Override
    public boolean updateReqState(PimsPathologySample ppr, int state) {
        return pimsPathologyRequisitionDao.updateReqState(ppr,state);
    }

    /**
     * 是否可以被修改或删除
     * @param id
     * @return
     */
    @Override
    public boolean canChange(Long id) {
        return pimsPathologyRequisitionDao.canChange(id);
    }

    /**
     * 保存申请单据
     * @param materials
     * @param ppr
     * @return
     */
    @Override
    public PimsPathologyRequisition insertOrUpdate(JSONArray materials, PimsPathologyRequisition ppr,JSONArray fields,JSONArray fields1) {
        return pimsPathologyRequisitionDao.insertOrUpdate(materials,ppr,fields,fields1);
    }

    @Override
    public String getSjcl(Long id) {
        return pimsPathologyRequisitionDao.getSjcl(id);
    }

    @Override
    public List searchLists(PimsBaseModel map) {
        return pimsPathologyRequisitionDao.searchLists(map);
    }

    @Override
    public List searchViews(long id,String reqffirstv) {
        return pimsPathologyRequisitionDao.searchViews(id,reqffirstv);
    }

    /**
     * 查询单据是否存在
     * @param code
     * @return
     */
    @Override
    public String codeIsExist(String code) {
        return pimsPathologyRequisitionDao.codeIsExist(code);
    }
}
