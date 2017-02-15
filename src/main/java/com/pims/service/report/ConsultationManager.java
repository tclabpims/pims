package com.pims.service.report;

import com.pims.model.*;
import com.smart.service.GenericManager;
import java.util.List;
import java.util.Map;

/**
 * Created by zp on 2016/12/26.
 */
public interface ConsultationManager extends GenericManager {
    List getConsultationList(Map map,PimsBaseModel ppr);

    int getReqListNum(Map map);

    List<PimsPathologyPieces> getSampleListNoPage(String pathologyid);

    List<PimsConsultationDetail> getConDets(String code);

    String getResult(Long id);

    List<PimsPathologyOrderCheck> getItem(String id,String sampleid);
}
