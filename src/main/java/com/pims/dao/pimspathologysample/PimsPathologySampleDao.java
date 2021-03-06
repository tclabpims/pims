package com.pims.dao.pimspathologysample;

import com.pims.model.Pdfinfo;
import com.pims.model.PimsBaseModel;
import com.pims.model.PimsPathologySample;
import com.pims.model.PimsSysColor;
import com.pims.webapp.controller.GridQuery;
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
     * 首页查询标本信息
     * @param map
     * @return
     */
    List getSList(PimsBaseModel map);

    /**
     * 首页查询标本数量
     * @param map
     * @return
     */
    int getSNum(PimsBaseModel map);

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

    List<PimsPathologySample> querySample(PimsPathologySample sample, GridQuery gridQuery, String sql);

    Integer totalNum(PimsPathologySample sample, String s);

    void sign(PimsPathologySample sample);

    /**
     * 获取最大条码号
     * @return
     */
    String sampleCode();

    int getSamStaNum();
    /**
     * 查询报告列表
     * @param map
     * @return
     */
    List getReportList(PimsBaseModel map);

    /**
     * 查询报告数量
     * @param map
     * @return
     */
    int getReportNum(PimsBaseModel map);

    /**
     * 查询报告列表
     * @param map
     * @return
     */
    List getReportDelayList(PimsBaseModel map);

    /**
     * 查询报告数量
     * @param map
     * @return
     */
    int getReportDelayNum(PimsBaseModel map);
    /**
     * 日志统计总列表
     * @param map
     * @return
     */
    List getRztj(PimsBaseModel map);

    /**
     * 日志统计详情
     * @param map
     * @return
     */
    List getRztjInfo(PimsBaseModel map);

    /**
     * 标本来源统计
     * @param map
     * @return
     */
    List getBbly(PimsBaseModel map);
    /**
     * 收费统计报告
     * @param map
     * @return
     */
    List getSftj(PimsBaseModel map);

    List<PimsSysColor> getColor(PimsSysColor psc);
    //List<PimsSysColor> getColor2();


    boolean updatebgjStates(Pdfinfo pi);

    /**
     * 反推标本状态
     * @param sample
     * @return
     */
    int agoStates(PimsPathologySample sample);


    List<PimsPathologySample> queryHisSample(Long sampleid, GridQuery gridQuery);

    Integer queryHisSampleNum(Long sampleid);

    /**
     * 医生工作量
     */
    List getQcgzl(PimsBaseModel map);

    /**
     * 科室工作量
     */
    List getKsgzl(PimsBaseModel map);

    /**
     * 初查工作量
     */
    List getCcgzl(PimsBaseModel map);

    /**
     * 审查工作量
     */
    List getScgzl(PimsBaseModel map);

    /**
     * 包埋工作量
     */
    List getBmgzl(PimsBaseModel map);

    /**
     * 切片工作量
     */
    List getQpgzl(PimsBaseModel map);

    /**
     * 术中冰冻结果表
     */
    List getTworesults(PimsBaseModel map);

    Map<Long,PimsPathologySample> getSampleMap(String sampleids);
}
