package com.pims.dao.pimspathologysample;

import com.pims.model.PimsBaseModel;
import com.pims.model.PimsPathologySample;
import com.smart.dao.GenericDao;
import java.util.List;
import java.util.Map;

/**
 * Created by king on 2016/10/10.
 */
public interface PimsPathologySampleDao extends GenericDao<PimsPathologySample,Long> {

    /**
     * 查询标本列表
     * @param map
     * @return
     */
    List<PimsPathologySample> getSampleList(PimsBaseModel map);

    /**
     * 查询标本数量
     * @param map
     * @return
     */
    int getReqListNum(PimsBaseModel map);

    /**
     * 查询标本内容
     * @param id
     * @return
     */
    PimsPathologySample getBySampleNo(Long id);

    /**
     * 逻辑删除申请单
     * @param id
     * @return
     */
    boolean delete(Long id);
    /**
     * 查询单据是否可被修改
     * @param id,sts(1修改2删除)
     * @return
     */
    boolean canChange(Long id,String sts);
}
