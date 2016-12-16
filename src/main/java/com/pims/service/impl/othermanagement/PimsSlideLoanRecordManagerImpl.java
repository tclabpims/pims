package com.pims.service.impl.othermanagement;

import com.pims.dao.othermanage.PimsSlideLoanRecordDao;
import com.pims.model.PimsSlideRecord;
import com.pims.service.othermanagement.PimsSlideLoanRecordManager;
import com.smart.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zp on 2016/11/21.
 */

@Service("pimsSlideLoanRecordManager")
public class PimsSlideLoanRecordManagerImpl extends GenericManagerImpl<PimsSlideRecord, Long> implements PimsSlideLoanRecordManager{
    private PimsSlideLoanRecordDao pimsSlideLoanRecordDao;

    @Autowired
    public void setPimsSlideLoanRecordDao(PimsSlideLoanRecordDao pimsSlideLoanRecordDao){
        this.pimsSlideLoanRecordDao = pimsSlideLoanRecordDao;
        this.dao = pimsSlideLoanRecordDao;
    }

    @Override
    public List getRecordList(PimsSlideRecord map){
        return pimsSlideLoanRecordDao.getRecordList(map);
    }

//    public boolean returnSlide(PimsSlideRecord map){
//        PimsSlideRecord a = new PimsSlideRecord();
//        a.setSliintime(new Date());
//        a.setSliid(map.getSliid());
//
//        try{
//            save(a);
//        }catch (Exception e){
//            e.printStackTrace();
//            return false;
//        }
//        return true;
//    }

}
