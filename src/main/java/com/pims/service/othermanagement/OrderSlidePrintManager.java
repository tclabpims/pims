package com.pims.service.othermanagement;


import com.pims.model.PimsBaseModel;
import com.smart.service.GenericManager;
import java.util.Map;
import java.util.List;

/**
 * Created by zp on 2017/1/10.
 */

public interface OrderSlidePrintManager extends GenericManager{
    List getLoanList(Map map, PimsBaseModel ppr);

    int getReqListNum(Map map);

    String getTotal(Map map);
}
