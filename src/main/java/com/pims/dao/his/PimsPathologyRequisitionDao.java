package com.pims.dao.his;

import com.pims.dao.GenericDao;
import com.pims.model.PimsPathologyRequisition;

import java.util.List;

/**
 * Created by king on 2016/9/28.
 */
public interface PimsPathologyRequisitionDao extends GenericDao<PimsPathologyRequisition,Long> {
    /**
     * 查询his电子申请信息
     * @param req_code 申请单号
     * @param patient_name 病人姓名
     * @param send_hosptail 送检医院
     * @param req_bf_time 送检起始时间
     * @param req_af_time  送检截至时间
     * @param send_dept 送检科室
     * @param send_doctor 送检医生
     * @param req_sts  送检状态
     * @return 返回his申请实体对象
     */
    List<PimsPathologyRequisition> getRequisitionInfo(String req_code, String patient_name, String send_hosptail, String req_bf_time,
                                                      String req_af_time, String send_dept, String send_doctor, String req_sts);





}

