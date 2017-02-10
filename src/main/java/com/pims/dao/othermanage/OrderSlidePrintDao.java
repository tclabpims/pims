package com.pims.dao.othermanage;

import com.pims.model.PimsBaseModel;
import com.pims.model.PimsPathologyOrderCheck;
import com.smart.dao.GenericDao;
import java.util.Map;
import java.util.List;

/**
 * Created by zp on 2017/1/9.
 */
public interface OrderSlidePrintDao extends GenericDao{
    List getLoanList(Map map, PimsBaseModel ppr);

    int getReqListNum(Map map);

    String getTotal(Map map);
}
