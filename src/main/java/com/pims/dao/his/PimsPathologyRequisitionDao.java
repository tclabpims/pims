package com.pims.dao.his;

import com.alibaba.fastjson.JSONArray;
import com.pims.model.PimsBaseModel;
import com.pims.model.PimsPathologyRequisition;
import com.pims.model.PimsPathologySample;
import com.pims.webapp.controller.GridQuery;
import com.smart.dao.GenericDao;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by king on 2016/9/28.
 */
public interface PimsPathologyRequisitionDao extends GenericDao<PimsPathologyRequisition,Long> {
    /**
     *查询申请单列表
     * @param pims
     * @return
     */
    List<PimsPathologyRequisition> getRequisitionInfo(PimsBaseModel pims);

    /**
     * 逻辑删除申请单
     * @param id
     */
    @Transactional
    boolean delete(Long id);

    /**
     * 查询单据号是否存在
     * @param id
     * @return
     */
    @Transactional
    PimsPathologyRequisition getBySampleNo(Long id);
    /**
     * 获取总数量
     * @param pims
     * @return
     */
    int getReqListNum(PimsBaseModel pims);

    /**
     * 根据病种类别查询最大单据号
     * @param reqpathologyid
     * @return
     */
    String getMaxCode(String reqpathologyid);

    /**
     * 更新申请单的可使用状态
     * @param ppr
     * @param state
     * @return
     */
    boolean updateReqState(PimsPathologySample ppr, int state);

    /**
     * 是否可以被修改或删除
     * @param id
     * @return
     */
    boolean canChange(Long id);

    /**
     * 保存申请单据
     * @param materials
     * @param ppr
     * @return
     */
    PimsPathologyRequisition insertOrUpdate(JSONArray materials, PimsPathologyRequisition ppr,JSONArray fields,JSONArray fields1);

    String getSjcl(Long id);

    /**
     * 新增查询申请字段
     * @param map
     * @return
     */
    List searchLists(PimsBaseModel map);

    /**
     * 查看申请字段
     * @param id
     * @return
     */
    List searchViews(long id,String reqffirstv);
    /**
     * 查询单据是否存在
     * @param code
     * @return
     */
    String codeIsExist(String code);
}

