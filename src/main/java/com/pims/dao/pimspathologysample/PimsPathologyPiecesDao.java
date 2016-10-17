package com.pims.dao.pimspathologysample;

import com.pims.model.PimsBaseModel;
import com.pims.model.PimsPathologyPieces;
import com.pims.model.PimsPathologySample;
import com.smart.dao.GenericDao;

import java.util.List;

/**
 * Created by king on 2016/10/10.
 */
public interface PimsPathologyPiecesDao extends GenericDao<PimsPathologyPieces,Long> {
    /**
     *
     * 查询材块列表不分页
     * @param code
     * @return
     */
    List<PimsPathologyPieces> getSampleListNoPage(String code);

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
     * 更新标本信息
     * @param map
     * @return
     */
    boolean updateSample(PimsPathologySample map);
    /**
     * 更新标本信息
     * @param map,sts
     * @return
     */
    boolean updateSampleSts(PimsPathologySample map,int sts);
    /**
     * 删除材块单
     * @param id
     * @return
     */
    boolean delete(Long id);
    /**
     * 查询单据是否可被修改
     * @param id,sts(1修改2删除)
     * @return
     */
    boolean canChange(Long id, String sts);
}
