package com.pims.service.pimspathologysample;

import com.alibaba.fastjson.JSONArray;
import com.pims.model.PimsBaseModel;
import com.pims.model.PimsPathologyPieces;
import com.pims.model.PimsPathologySample;
import com.smart.service.GenericManager;

import java.text.ParseException;
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

    /**
     * 更新标本信息及材块信息
     * @param piecesList 材块列表,sample 标本信息,sts 状态,state 逻辑更新标志
     * @return
     */
    public boolean updateSampleSts(JSONArray piecesList, PimsPathologySample sample, int sts, int state);

    void saveOrderMaterial(JSONArray array);

    List<PimsPathologyPieces> getPiecesByOrderId(long orderId);
}
