package com.pims.service.pimssyspathology;

import com.pims.model.PimsSampleResult;
import com.smart.service.GenericManager;

import java.util.List;
import java.util.Map;

/**
 * Created by 909436637@qq.com on 2016/10/25.
 * Description:
 */
public interface PimsSampleResultManager extends GenericManager<PimsSampleResult, Long> {
    Map<String, Long> save(List<PimsSampleResult> set);

    Map<String,PimsSampleResult> getSampleResult(Long sampleId);

    PimsSampleResult getSampleResultForPrint(Long sampleId);
}
