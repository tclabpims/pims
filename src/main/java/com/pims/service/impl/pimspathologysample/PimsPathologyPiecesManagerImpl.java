package com.pims.service.impl.pimspathologysample;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pims.dao.pimspathologysample.PimsPathologyPiecesDao;
import com.pims.model.PimsBaseModel;
import com.pims.model.PimsPathologyPieces;
import com.pims.model.PimsPathologySample;
import com.pims.service.pimspathologysample.PimsPathologyPiecesManager;
import com.smart.Constants;
import com.smart.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

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

    /**
     * 更新标本信息及材块信息
     * @param piecesList 材块列表,sample 标本信息,sts 状态,state 逻辑更新标志
     * @return
     */
    @Override
    public boolean updateSampleSts(JSONArray piecesList, PimsPathologySample sample, int sts, int state){
        return pimsPathologyPiecesDao.updateSampleSts(piecesList,sample,sts,state);
    }

    @Override
    @Transactional
    public void saveOrderMaterial(JSONArray array) {
        for (Object anArray : array) {//取材
            JSONObject map = (JSONObject) anArray;
            PimsPathologyPieces piece = new PimsPathologyPieces();
            piece.setPiecode((String) map.get("piecode"));
            piece.setPiesampleid(Long.valueOf(String.valueOf(map.get("piesampleid"))));
            piece.setPiefirstn(Long.valueOf(String.valueOf(map.get("piefirstn"))));
            piece.setPieunit(String.valueOf(map.get("pieunit")));
            piece.setPiepathologycode(String.valueOf(map.get("piepathologycode")));
            piece.setPiesamplingno(String.valueOf(map.get("piesamplingno")));
            piece.setPieparts(String.valueOf(map.get("pieparts")));
            piece.setPiecounts(Long.valueOf(String.valueOf((map.get("piecounts")))));
            piece.setPienullslidenum(Long.valueOf(String.valueOf(map.get("pienullslidenum"))));
            piece.setPiestate(Long.valueOf(String.valueOf(map.get("piestate"))));
            piece.setPieisembed(String.valueOf(map.get("pieisembed")));
            piece.setPierecorderid(String.valueOf(map.get("pierecorderid")));
            piece.setPiedoctorid(String.valueOf(map.get("piedoctorid")));
            piece.setPiedoctorname(String.valueOf(map.get("piedoctorname")));
            piece.setPierecordername(String.valueOf(map.get("pierecordername")));
            piece.setPiespecial(String.valueOf(map.get("piespecial")));
            try {
                piece.setPiesamplingtime(Constants.SDF.parse(String.valueOf(map.get("piesamplingtime"))));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            save(piece);
        }
    }

    @Override
    public List<PimsPathologyPieces> getPiecesByOrderId(long orderId) {
        return pimsPathologyPiecesDao.getPiecesByOrderId(orderId);
    }

    @Override
    public PimsPathologyPieces getPieceBySampleId(long ordsampleid) {

        return pimsPathologyPiecesDao.getPieceBySampleId(ordsampleid);
    }
}
