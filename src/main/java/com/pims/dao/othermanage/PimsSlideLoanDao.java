package com.pims.dao.othermanage;

import com.pims.model.PimsBaseModel;
import com.pims.model.PimsSlideLoan;
import com.smart.dao.GenericDao;

import java.util.List;
import java.util.Map;

/**
 * Created by zp on 2016/11/15.
 */
public interface PimsSlideLoanDao extends GenericDao<PimsSlideLoan,Long> {

    /**
     * 查询标本列表
         * @param map
     * @return
     */
    List<PimsSlideLoan> getLoanList(PimsBaseModel map);

    /**
     * 查询标本数量
     * @param map
     * @return
     */
    int getReqListNum(PimsBaseModel map);
    /**
     * 查询标本内容
     * @param id
     * @return
     */
    PimsSlideLoan getByLoanNo(Long id);

    /**
     * 查询单据是否可被修改
     * @param id,sts(1修改2删除)
     * @return
     */
    boolean canChange(Long id, String sts);

    boolean loan(Map map);

//    boolean returnSlide4(PimsBaseModel map);
//
//    boolean returnSlide(PimsBaseModel map);
//
//    boolean returnSlide2(PimsBaseModel map);
//
    boolean returnSlide3(PimsBaseModel map);

}
