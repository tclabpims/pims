package com.pims.dao.report;

import com.pims.model.*;
import com.smart.dao.GenericDao;
import java.util.List;
import java.util.Map;
/**
 * Created by zp on 2016/12/23.
 */
public interface ConsultationDao extends GenericDao {
    List getConsultationList(Map map,PimsBaseModel ppr);

    int getReqListNum(Map map);

    List<PimsPathologyPieces> getSampleListNoPage(String pathologyid);

    List<PimsConsultationDetail> getConDets(String code);

    String getResult(Long id);

    List<PimsPathologyOrderCheck> getItem(String id,String sampleid);
}
