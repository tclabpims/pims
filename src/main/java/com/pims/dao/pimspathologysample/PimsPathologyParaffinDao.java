package com.pims.dao.pimspathologysample;

import com.pims.model.PimsBaseModel;
import com.pims.model.PimsPathologyParaffin;
import com.pims.model.PimsPathologyPieces;
import com.pims.model.PimsPathologySample;
import com.smart.dao.GenericDao;

import java.util.List;

/**
 * Created by king on 2016/10/10.
 */
public interface PimsPathologyParaffinDao extends GenericDao<PimsPathologyParaffin,Long> {
    /**
     *
     * 查询包埋信息不分页
     * @param code
     * @return
     */
    List getSampleListNoPage(String code);

    /**
     * 查询材块列表
     * @param map
     * @return
     */
    List<PimsPathologyPieces> getSampleList(PimsBaseModel map);

    /**
     * 查询材块数量
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
     * @param
     * @return
     */
    boolean updateSample(long sampleid,int sts);
    /**
     * 更新材块信息
     * @param piece,sts
     * @return
     */
    boolean updateSampleSts(PimsPathologyPieces piece, int sts);
    /**
     * 查询单据是否可被修改
     * @param id,sts(1修改2删除)
     * @return
     */
    boolean canChange(Long id, String sts);
}
