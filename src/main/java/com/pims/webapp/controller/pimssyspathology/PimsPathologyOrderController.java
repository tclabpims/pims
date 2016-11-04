package com.pims.webapp.controller.pimssyspathology;

import com.pims.service.pimssyspathology.PimsPathologyOrderManager;
import com.pims.webapp.controller.PIMSBaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by 909436637@qq.com on 2016/11/4.
 * Description:
 */
@Controller
@RequestMapping(value = "/pathology/order")
public class PimsPathologyOrderController extends PIMSBaseController {

    @Autowired
    private PimsPathologyOrderManager pimsPathologyOrderManager;


}
