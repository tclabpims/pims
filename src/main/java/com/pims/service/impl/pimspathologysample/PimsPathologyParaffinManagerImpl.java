package com.pims.service.impl.pimspathologysample;

import com.alibaba.fastjson.JSONArray;
import com.pims.dao.pimspathologysample.PimsPathologyParaffinDao;
import com.pims.model.PimsBaseModel;
import com.pims.model.PimsPathologyParaffin;
import com.pims.model.PimsPathologySample;
import com.pims.service.pimspathologysample.PimsPathologyParaffinManager;
import com.smart.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by king on 2016/10/10.
 */
@Service("pimsPathologyParaffinManager")
public class PimsPathologyParaffinManagerImpl extends GenericManagerImpl<PimsPathologyParaffin,Long> implements PimsPathologyParaffinManager {
    private PimsPathologyParaffinDao pimsPathologyParaffinDao;
    @Autowired
    public void setPimsPathologySampleDao(PimsPathologyParaffinDao pimsPathologyParaffinDao) {
        this.pimsPathologyParaffinDao = pimsPathologyParaffinDao;
        this.dao = pimsPathologyParaffinDao;
    }

    /**
     * 查询包埋信息
     * @param code
     * @return
     */
    @Override
    public List getSampleListNoPage(String code) {
        return pimsPathologyParaffinDao.getSampleListNoPage(code);
    }

    /**
     * 查询材块列表
     * @param map
     * @return
     */
    @Override
    public List getSampleList(PimsBaseModel map) {
        return pimsPathologyParaffinDao.getSampleList(map);
    }

    /**
     * 查询材块数量
     * @param map
     * @return
     */
    @Override
    public int getReqListNum(PimsBaseModel map) {
        return pimsPathologyParaffinDao.getReqListNum(map);
    }

    /**
     * 查询标本内容
     * @param id
     * @return
     */
    @Override
    public PimsPathologySample getBySampleNo(Long id) {
        return pimsPathologyParaffinDao.getBySampleNo(id);
    }

    /**
     *
     * @param slideList
     * @param paraList
     * @param sampleList
     * @param sts
     * @param state
     * @return
     */
    @Override
    public boolean updateSampleSts(JSONArray slideList, JSONArray paraList, JSONArray sampleList, int sts, int state) {
        return pimsPathologyParaffinDao.updateSampleSts(slideList,paraList,sampleList,sts,state);
    }

    /**
     * 查询单据是否可修改
     * @param id,sts
     * @return
     */
    @Override
    public boolean canChange(Long id,String sts) {
        return pimsPathologyParaffinDao.canChange(id,sts);
    }

    @Override
    public List<PimsPathologyParaffin> getParaffinBySampleId(long sampleId, Long orderId) {
        return pimsPathologyParaffinDao.getParaffinBySampleId(sampleId, orderId);
    }

    /**
     * 按照标本编号和蜡块编号取蜡块信息
     *
     * @param sampleId     标本编号
     * @param paraffinCode 蜡块编号
     * @return 蜡块信息
     */
    @Override
    public PimsPathologyParaffin getPimsPathologyParaffin(long sampleId, String paraffinCode) {
        return pimsPathologyParaffinDao.getPimsPathologyParaffin(sampleId, paraffinCode);
    }

    @Override
    public List<Map<String, Object>> getParaffinMaterial(long paraffinId) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        PimsPathologyParaffin paraffin = this.get(paraffinId);
        List lis = pimsPathologyParaffinDao.getParaffinMaterial(paraffin);
        if(lis.size() > 0) {
            for(Object obj : lis) {
                Map<String, Object> map = new HashMap<>();
                Object[] objects = (Object[])obj;
                map.put("pieceid", objects[0]);
                map.put("pieparts", objects[1]);
                map.put("piedoctorname", objects[2]);
                map.put("parparaffincode", paraffin.getParparaffincode());
                mapList.add(map);
            }
        }
        return mapList;
    }

    @Override
    public List<Map<String, Object>> getParaffinFromOrder(long oderId) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        List lis = pimsPathologyParaffinDao.getParaffinFromOrder(oderId);
        if(lis.size() > 0) {
            for(Object obj : lis) {
                Map<String, Object> map = new HashMap<>();
                Object[] objects = (Object[])obj;
                map.put("childorderid", objects[0]);
                map.put("chiparaffinid", objects[1]);
                map.put("chiparaffincode", objects[2]);
                map.put("chislidenum", objects[3]);
                map.put("chinullslidenum", objects[4]);
                mapList.add(map);
            }
        }
        return mapList;
    }
}
