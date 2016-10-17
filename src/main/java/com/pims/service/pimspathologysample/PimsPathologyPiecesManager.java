package com.pims.service.pimspathologysample;

import com.pims.model.PimsBaseModel;
import com.pims.model.PimsPathologyPieces;
import com.pims.model.PimsPathologySample;
import com.smart.service.GenericManager;

import java.util.List;

/**
 * Created by king on 2016/10/10.
 */
public interface PimsPathologyPiecesManager extends GenericManager<PimsPathologyPieces,Long> {
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
    boolean updateSampleSts(PimsPathologySample map ,int sts);
    /**
     * 逻辑删除申请单
     * @param id
     * @returnpims
     */
    boolean delete(Long id);
    /**
     * 判断单据是否可被修改
     * @param id,sts
     * @return
     */
    boolean canChange(Long id, String sts);
}
