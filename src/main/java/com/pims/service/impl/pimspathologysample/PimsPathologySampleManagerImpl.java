package com.pims.service.impl.pimspathologysample;

import com.pims.dao.pimspathologysample.PimsPathologySampleDao;
import com.pims.model.PimsBaseModel;
import com.pims.model.PimsPathologySample;
import com.pims.service.pimspathologysample.PimsPathologySampleManager;
import com.smart.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by king on 2016/10/10.
 */
@Service("pimsPathologySampleManager")
public class PimsPathologySampleManagerImpl extends GenericManagerImpl<PimsPathologySample,Long> implements PimsPathologySampleManager {
    private PimsPathologySampleDao pimsPathologySampleDao;
    @Autowired
    public void setPimsPathologySampleDao(PimsPathologySampleDao pimsPathologySampleDao) {
        this.pimsPathologySampleDao = pimsPathologySampleDao;
        this.dao = pimsPathologySampleDao;
    }

    /**
     * 查询标本列表
     * @param map
     * @return
     */
    @Override
    public List<PimsPathologySample> getSampleList(PimsBaseModel map) {
        return pimsPathologySampleDao.getSampleList(map);
    }

    /**
     * 查询标本数量
     * @param map
     * @return
     */
    @Override
    public int getReqListNum(PimsBaseModel map) {
        return pimsPathologySampleDao.getReqListNum(map);
    }

    /**
     * 查询标本内容
     * @param id
     * @return
     */
    @Override
    public PimsPathologySample getBySampleNo(Long id) {
        return pimsPathologySampleDao.getBySampleNo(id);
    }

    /**
     * 逻辑删除申请单
     * @param id
     * @return
     */
    @Override
    public boolean delete(Long id) {
        return pimsPathologySampleDao.delete(id);
    }

    /**
     * 查询单据是否可修改
     * @param id,sts
     * @return
     */
    @Override
    public boolean canChange(Long id,String sts) {
        return pimsPathologySampleDao.canChange(id,sts);
    }
}
