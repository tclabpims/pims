package com.pims.service.impl.pimspathologysample;

import com.pims.dao.pimspathologysample.PimsPathologySampleDao;
import com.pims.model.PimsBaseModel;
import com.pims.model.PimsPathologySample;
import com.pims.service.pimspathologysample.PimsPathologySampleManager;
import com.pims.webapp.controller.GridQuery;
import com.smart.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by king on 2016/10/10.
 */
@Service("pimsPathologySampleManager")
public class PimsPathologySampleManagerImpl extends GenericManagerImpl<PimsPathologySample, Long> implements PimsPathologySampleManager {
    private PimsPathologySampleDao pimsPathologySampleDao;

    @Autowired
    public void setPimsPathologySampleDao(PimsPathologySampleDao pimsPathologySampleDao) {
        this.pimsPathologySampleDao = pimsPathologySampleDao;
        this.dao = pimsPathologySampleDao;
    }

    @Override
    public List<PimsPathologySample> querySample(PimsPathologySample sample, GridQuery gridQuery) {
        StringBuffer sql = new StringBuffer("from PimsPathologySample as p where 1=1 ");
        setParameter(sql, sample);
        sql.append(" order by p.sampleid desc");
        return pimsPathologySampleDao.querySample(sample, gridQuery, sql.toString());
    }

    private void setParameter(StringBuffer sql, PimsPathologySample sample) {
        long pathologyId = sample.getSampathologyid();
        long sampleStatus = sample.getSamsamplestatus();
        Date from = sample.getSamplesectionfrom();
        Date to = sample.getSamplesectionto();
        String inspectionId = sample.getSaminspectionid();
        String pathologyCode = sample.getSampathologycode();
        String patientName = sample.getSampatientname();
        if (sampleStatus > 0) {
            sql.append("and p.samsamplestatus=:SamSampleStatus ");
        }
        if (pathologyId > 0) {
            sql.append("and p.sampathologyid=:SamPathologyId ");
        }
        if (inspectionId != null && !"".equals(inspectionId.trim())) {
            sql.append("and p.saminspectionid=:SamInspectionId ");
        }
        if (pathologyCode != null && !"".equals(pathologyCode.trim())) {
            sql.append("and p.sampathologycode=:SamPathologyCode ");
        }
        if (patientName != null && !"".equals(patientName.trim())) {
            sql.append("and p.sampatientname=:SamPatientName ");
        }
        if (from != null && to != null)
            sql.append("and p.sampleid in (select pp.parsampleid from PimsPathologyParaffin as pp where pp.parsectionedtime between :samplesectionfrom and  :samplesectionto)");
    }

    @Override
    public Integer querySampleNum(PimsPathologySample sample) {
        StringBuffer sql = new StringBuffer("select count(*) from PimsPathologySample as p where 1=1 ");
        setParameter(sql, sample);
        return pimsPathologySampleDao.totalNum(sample, sql.toString());
    }

    /**
     * 查询标本列表
     *
     * @param map
     * @return
     */
    @Override
    public List<PimsPathologySample> getSampleList(PimsBaseModel map) {
        return pimsPathologySampleDao.getSampleList(map);
    }

    /**
     * 查询标本数量
     *
     * @param map
     * @return
     */
    @Override
    public int getReqListNum(PimsBaseModel map) {
        return pimsPathologySampleDao.getReqListNum(map);
    }

    /**
     * 查询标本内容
     *
     * @param id
     * @return
     */
    @Override
    public PimsPathologySample getBySampleNo(Long id) {
        return pimsPathologySampleDao.getBySampleNo(id);
    }

    /**
     * 逻辑删除申请单
     *
     * @param id
     * @return
     */
    @Override
    public boolean delete(Long id) {
        return pimsPathologySampleDao.delete(id);
    }

    /**
     * 查询单据是否可修改
     *
     * @param id,sts
     * @return
     */
    @Override
    public boolean canChange(Long id, String sts) {
        return pimsPathologySampleDao.canChange(id, sts);
    }

    @Override
    public void sign(PimsPathologySample sample) {
        pimsPathologySampleDao.sign(sample);
    }

    /**
     * 获取最大条码号
     * @return
     */
    @Override
    public String sampleCode() {
        return pimsPathologySampleDao.sampleCode();
    }

    @Override
    public int getSamStaNum() {
        return pimsPathologySampleDao.getSamStaNum();
    }

    @Override
    public List getSList(PimsBaseModel map) {
        return pimsPathologySampleDao.getSList(map);
    }

    @Override
    public int getSNum(PimsBaseModel map) {
        return pimsPathologySampleDao.getSNum(map);
    }
}
