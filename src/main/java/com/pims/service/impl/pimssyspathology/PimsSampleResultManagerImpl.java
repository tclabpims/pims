package com.pims.service.impl.pimssyspathology;

import com.pims.dao.pimssyspathology.PimsSampleResultDao;
import com.pims.model.PimsSampleResult;
import com.pims.service.pimssyspathology.PimsSampleResultManager;
import com.smart.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 909436637@qq.com on 2016/10/25.
 * Description:
 */
@Service("pimsSampleResultManager")
public class PimsSampleResultManagerImpl extends GenericManagerImpl<PimsSampleResult, Long> implements PimsSampleResultManager {

    private PimsSampleResultDao pimsSampleResultDao;

    @Autowired
    public void setPimsSampleResultDao(PimsSampleResultDao pimsSampleResultDao) {
        this.dao = pimsSampleResultDao;
        this.pimsSampleResultDao = pimsSampleResultDao;
    }

    @Override
    public Map<String, Long> save(List<PimsSampleResult> set) {
        return pimsSampleResultDao.save(set);
    }

    @Override
    public Map<String, PimsSampleResult> getSampleResult(Long sampleId) {
        List<PimsSampleResult> list = pimsSampleResultDao.getSampleResult(sampleId);
        Map<String, PimsSampleResult> map = new HashMap<>();
        if(list.size() > 0) {
            for(PimsSampleResult result : list) {
                map.put(String.valueOf(result.getRestestitemid()), result);
            }
        }
        return map;
    }

    @Override
    public PimsSampleResult getSampleResultForPrint(Long sampleId) {

        return pimsSampleResultDao.getSampleResultForPrint(sampleId);
    }
}
