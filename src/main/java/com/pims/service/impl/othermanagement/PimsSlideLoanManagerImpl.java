package com.pims.service.impl.othermanagement;

import com.pims.dao.othermanage.PimsSlideLoanDao;
import com.pims.model.PimsBaseModel;
import com.pims.model.PimsSlideLoan;
import com.pims.service.othermanagement.PimsSlideLoanManager;
import com.smart.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by zp on 2016/11/16.
 */
@Service("pimsSlideLoanManager")
public class PimsSlideLoanManagerImpl extends GenericManagerImpl<PimsSlideLoan, Long> implements PimsSlideLoanManager{
    private PimsSlideLoanDao pimsSlideLoanDao;

    @Autowired
    public void setPimsSlideLoanDao(PimsSlideLoanDao pimsSlideLoanDao){
        this.pimsSlideLoanDao = pimsSlideLoanDao;
        this.dao = pimsSlideLoanDao;
    }

    @Override
    public List getLoanList(PimsBaseModel map){
        return pimsSlideLoanDao.getLoanList(map);
    }

    @Override
    public int getReqListNum(PimsBaseModel map) {
        return pimsSlideLoanDao.getReqListNum(map);
    }

    @Override
    public PimsSlideLoan getByLoanNo(Long id) {
        return pimsSlideLoanDao.getByLoanNo(id);
    }

    @Override
    public boolean canChange(Long id, String sts) {
        return pimsSlideLoanDao.canChange(id, sts);
    }

    @Override
    public boolean loan(Map map) { return pimsSlideLoanDao.loan(map);
    }


//    @Override
//    public boolean returnSlide4(PimsBaseModel map){return pimsSlideLoanDao.returnSlide4(map);}
//
//    @Override
//    public boolean returnSlide(PimsBaseModel map){return pimsSlideLoanDao.returnSlide(map);}
//
//    @Override
//    public boolean returnSlide2(PimsBaseModel map){return pimsSlideLoanDao.returnSlide2(map);}
//
    @Override
    public boolean returnSlide3(PimsBaseModel map){return pimsSlideLoanDao.returnSlide3(map);}
}
