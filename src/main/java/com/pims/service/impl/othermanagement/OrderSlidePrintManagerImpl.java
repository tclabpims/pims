package com.pims.service.impl.othermanagement;

import com.pims.dao.othermanage.OrderSlidePrintDao;
import com.pims.model.PimsBaseModel;
import com.pims.service.othermanagement.OrderSlidePrintManager;
import com.smart.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.List;

/**
 * Created by zp on 2017/1/10.
 */
@Service("orderSlidePrintManager")
public class OrderSlidePrintManagerImpl extends GenericManagerImpl implements OrderSlidePrintManager{
    private OrderSlidePrintDao orderSlidePrintDao;

    @Autowired
    public void setOrderSlidePrintDao(OrderSlidePrintDao orderSlidePrintDao) {
        this.orderSlidePrintDao = orderSlidePrintDao;
        this.dao = orderSlidePrintDao;
    }

    @Override
    public List getLoanList(Map map, PimsBaseModel ppr){
        return orderSlidePrintDao.getLoanList(map,ppr);
    }

    @Override
    public int getReqListNum(Map map){
        return orderSlidePrintDao.getReqListNum(map);
    }

    @Override
    public String getTotal(Map map){
        return orderSlidePrintDao.getTotal(map);
    }
}
