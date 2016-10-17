package com.pims.service;

import java.util.List;

/**
 * Created by 909436637@qq.com on 2016/10/12.
 * Description:
 */
public interface QueryHisDataService {

    Integer queryHisChargePrice(String query);

    List queryHisChargePrice(String query, int start, int end);

}
