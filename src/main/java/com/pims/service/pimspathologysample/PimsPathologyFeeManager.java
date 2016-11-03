package com.pims.service.pimspathologysample;

import com.pims.model.PimsPathologyFee;
import com.smart.service.GenericManager;
import java.util.List;

/**
 * Created by king on 2016/10/10.
 */
public interface PimsPathologyFeeManager extends GenericManager<PimsPathologyFee,Long> {

    /**
     * 根据费用来源查询病理的收费列表
     * @param map
     * @return
     */
    List<PimsPathologyFee> getSampleList(PimsPathologyFee map);
}
