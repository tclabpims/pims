//package com.pims.service.impl.report;
//
//import com.pims.dao.report.ConsultationDao;
//import com.pims.model.ViewConsultationQuery;
//import com.pims.service.report.ConsultationManager;
//import com.smart.service.impl.GenericManagerImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by zp on 2016/12/26.
// */
//@Service("comsultationManager")
//public class ConsultationManagerImpl extends GenericManagerImpl<ViewConsultationQuery,Long> implements ConsultationManager{
//
//
//    private ConsultationDao consultationDao;
//
//    @Autowired
//    public void setConsultationDao(ConsultationDao consultationDao){
//        this.consultationDao = consultationDao;
//        this.dao = consultationDao;
//    }
//
//    @Override
//    public List<ViewConsultationQuery> getConsultationList(Map map){
//        return consultationDao.getConsultationList(map);
//    }
//
//    @Override
//    public int getReqListNum(Map map){
//        return consultationDao.getReqListNum(map);
//    }
//}
