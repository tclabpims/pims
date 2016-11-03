package com.pims.service.impl.pimspathologysample;

import com.pims.dao.pimspathologysample.PimsPathologyFeeDao;
import com.pims.model.PimsPathologyFee;
import com.pims.service.pimspathologysample.PimsPathologyFeeManager;
import com.smart.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Created by king on 2016/10/10.
 */
@Service("pimsPathologyFeeManager")
public class PimsPathologyFeeManagerImpl extends GenericManagerImpl<PimsPathologyFee, Long> implements PimsPathologyFeeManager {
    private PimsPathologyFeeDao pimsPathologyFeeDao;

    @Autowired
    public void setPimsPathologyFeeDao(PimsPathologyFeeDao pimsPathologyFeeDao) {
        this.pimsPathologyFeeDao = pimsPathologyFeeDao;
        this.dao = pimsPathologyFeeDao;
    }
    /**
     * 根据费用来源查询病理的收费列表
     * @param map
     * @return
     */
    @Override
    public List<PimsPathologyFee> getSampleList(PimsPathologyFee map) {
        return pimsPathologyFeeDao.getSampleList(map);
    }
}
