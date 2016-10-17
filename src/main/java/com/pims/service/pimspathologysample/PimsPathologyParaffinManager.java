package com.pims.service.pimspathologysample;

import com.pims.model.PimsBaseModel;
import com.pims.model.PimsPathologyParaffin;
import com.pims.model.PimsPathologyPieces;
import com.pims.model.PimsPathologySample;
import com.smart.service.GenericManager;

import java.util.List;

/**
 * Created by king on 2016/10/10.
 */
public interface PimsPathologyParaffinManager extends GenericManager<PimsPathologyParaffin,Long> {
    /**
     *
     * 查询包埋数据
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
     * 更新材块、包埋信息
     * @param piece,sts
     * @return
     */
    boolean updateSampleSts(PimsPathologyPieces piece,int sts);

    /**
     * 判断单据是否可被修改
     * @param id,sts
     * @return
     */
    boolean canChange(Long id, String sts);

}
