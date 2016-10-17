package com.pims.service.impl.pimspathologysample;

import com.pims.dao.pimspathologysample.PimsPathologyPiecesDao;
import com.pims.model.PimsBaseModel;
import com.pims.model.PimsPathologyPieces;
import com.pims.model.PimsPathologySample;
import com.pims.service.pimspathologysample.PimsPathologyPiecesManager;
import com.smart.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by king on 2016/10/10.
 */
@Service("pimsPathologyPiecesManager")
public class PimsPathologyPiecesManagerImpl extends GenericManagerImpl<PimsPathologyPieces,Long> implements PimsPathologyPiecesManager {
    private PimsPathologyPiecesDao pimsPathologyPiecesDao;
    @Autowired
    public void setPimsPathologySampleDao(PimsPathologyPiecesDao pimsPathologyPiecesDao) {
        this.pimsPathologyPiecesDao = pimsPathologyPiecesDao;
        this.dao = pimsPathologyPiecesDao;
    }

    /**
     * 查询材块列表不分页
     * @param code
     * @return
     */
    @Override
    public List<PimsPathologyPieces> getSampleListNoPage(String code) {
        return pimsPathologyPiecesDao.getSampleListNoPage(code);
    }

    /**
     * 查询标本列表
     * @param map
     * @return
     */
    @Override
    public List<PimsPathologySample> getSampleList(PimsBaseModel map) {
        return pimsPathologyPiecesDao.getSampleList(map);
    }

    /**
     * 查询标本数量
     * @param map
     * @return
     */
    @Override
    public int getReqListNum(PimsBaseModel map) {
        return pimsPathologyPiecesDao.getReqListNum(map);
    }

    /**
     * 查询标本内容
     * @param id
     * @return
     */
    @Override
    public PimsPathologySample getBySampleNo(Long id) {
        return pimsPathologyPiecesDao.getBySampleNo(id);
    }

    /**
     * 更新标本信息
     * @param map
     * @return
     */
    @Override
    public boolean updateSample(PimsPathologySample map) {
        return pimsPathologyPiecesDao.updateSample(map);
    }
    /**
     * 更新标本信息
     * @param map,sts
     * @return
     */
    @Override
    public boolean updateSampleSts(PimsPathologySample map,int sts) {
        return pimsPathologyPiecesDao.updateSampleSts(map,sts);
    }

    /**
     * 逻辑删除申请单
     * @param id
     * @return
     */
    @Override
    public boolean delete(Long id) {
        return pimsPathologyPiecesDao.delete(id);
    }

    /**
     * 查询单据是否可修改
     * @param id,sts
     * @return
     */
    @Override
    public boolean canChange(Long id,String sts) {
        return pimsPathologyPiecesDao.canChange(id,sts);
    }
}
