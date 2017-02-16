package com.pims.service.othermanagement;

import com.pims.model.PimsBaseModel;
import com.pims.model.PimsPathologySlide;
import com.pims.model.PimsSlideLoan;
import com.smart.service.GenericManager;

import java.util.List;
import java.util.Map;

/**
 * Created by zp on 2016/11/16.
 */
public interface PimsSlideLoanManager extends GenericManager<PimsPathologySlide,Long> {
    List getLoanList(PimsBaseModel map);

    List getLoanList2(PimsBaseModel map);

    int getReqListNum(PimsBaseModel map);

    PimsPathologySlide getByLoanNo(Long id);
    //判断是否可被借阅
    boolean canChange(Long id, String sts);
    //修改 借阅中0/已归还1

    boolean loan(Map map);

//
//    boolean returnSlide4(PimsBaseModel map);
//
//    boolean returnSlide(PimsBaseModel map);
//
//    boolean returnSlide2(PimsBaseModel map);
//
    boolean returnSlide3(PimsBaseModel map);
}
