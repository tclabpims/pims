package com.pims.dao.pimspathologysample;

import com.pims.model.PimsPathologyFee;
import com.smart.dao.GenericDao;
import java.util.List;

/**
 * Created by king on 2016/10/10.
 */
public interface PimsPathologyFeeDao extends GenericDao<PimsPathologyFee,Long> {

    /**
     * 根据费用来源查询病理的收费列表
     * @param map
     * @return
     */
    List<PimsPathologyFee> getSampleList(PimsPathologyFee map);

}
