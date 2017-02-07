package com.pims.dao.pimspathologysample;

import com.alibaba.fastjson.JSONArray;
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


    /**
     * 更新标本信息及材块信息
     * @param piecesList 材块列表,sample 标本信息,sts 状态,state 逻辑更新标志
     * @return
     */
    public boolean updateSampleSts(JSONArray piecesList, PimsPathologySample sample, int sts, int state);

    List<PimsPathologyPieces> getPiecesByOrderId(long orderId);

    PimsPathologyPieces getPieceBySampleId(long ordsampleid);
    /**
     * 获取打印列表
     * @param samplesList
     * @return
     */
    JSONArray getSlideCode(JSONArray samplesList);

    String getMinTime(Long sampleid);

    boolean updatePieceStates(Long orderId,int state);// 1 更新 2 删除

}
