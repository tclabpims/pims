package com.pims.dao.his;

import com.alibaba.fastjson.JSONArray;
import com.pims.model.PimsBaseModel;
import com.pims.model.PimsPathologyRequisition;
import com.pims.model.PimsPathologySample;
import com.pims.model.PimsRequisitionField;
import com.smart.dao.GenericDao;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by king on 2016/9/28.
 */
public interface PimsRequisitionFieldDao extends GenericDao<PimsRequisitionField,Long> {
}

