package com.pims.dao.pimssyspathology;

import com.pims.model.PimsSampleResult;
import com.smart.dao.GenericDao;

import java.util.List;
import java.util.Map;

/**
 * Created by 909436637@qq.com on 2016/10/25.
 * Description:
 */
public interface PimsSampleResultDao extends GenericDao<PimsSampleResult, Long> {
    Map<String, Long> save(List<PimsSampleResult> set);

    List<PimsSampleResult> getSampleResult(Long sampleId);

    PimsSampleResult getSampleResultForPrint(Long sampleId);
}
