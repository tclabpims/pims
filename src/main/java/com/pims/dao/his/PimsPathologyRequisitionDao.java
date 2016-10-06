package com.pims.dao.his;

import com.pims.model.PimsPathologyRequisition;
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
     * @param map
     * @return
     */
    List<PimsPathologyRequisition> getRequisitionInfo(Map map);

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
     * 查询最大ID号
     */
    Long getMaxId();


}

