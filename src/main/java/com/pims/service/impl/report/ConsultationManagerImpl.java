package com.pims.service.impl.report;

import com.pims.dao.report.ConsultationDao;
import com.pims.model.*;
import com.pims.service.report.ConsultationManager;
import com.smart.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

/**
 * Created by zp on 2016/12/26.
 */
@Service("comsultationManager")
public class ConsultationManagerImpl extends GenericManagerImpl implements ConsultationManager{


    private ConsultationDao consultationDao;

    @Autowired
    public void setConsultationDao(ConsultationDao consultationDao){
        this.consultationDao = consultationDao;
        this.dao = consultationDao;
    }

    @Override
    public List getConsultationList(Map map,PimsBaseModel ppr){
        return consultationDao.getConsultationList(map,ppr);
    }

    @Override
    public int getReqListNum(Map map){
        return consultationDao.getReqListNum(map);
    }

    @Override
    public List<PimsPathologyPieces> getSampleListNoPage(String pathologyid){
        return consultationDao.getSampleListNoPage(pathologyid);
    }

    @Override
    public List<PimsConsultationDetail> getConDets(String code){
        return consultationDao.getConDets(code);
    }

    @Override
    public String getResult(Long id){
        return consultationDao.getResult(id);
    }

    @Override
    public List<PimsPathologyOrderCheck> getItem(String id,String sampleid){ return consultationDao.getItem(id,sampleid);}
}
