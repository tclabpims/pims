package com.pims.webapp.controller.pimssyspathology;

import com.pims.model.PimsPathologyOrder;
import com.pims.service.pimssyspathology.PimsPathologyOrderCheckManager;
import com.pims.service.pimssyspathology.PimsPathologyOrderChildManager;
import com.pims.service.pimssyspathology.PimsPathologyOrderManager;
import com.pims.webapp.controller.PIMSBaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by 909436637@qq.com on 2016/11/4.
 * Description:
 */
@Controller
@RequestMapping(value = "/pathology/order")
public class PimsPathologyOrderController extends PIMSBaseController {

    @Autowired
    private PimsPathologyOrderManager pimsPathologyOrderManager;

    @Autowired
    private PimsPathologyOrderChildManager pimsPathologyOrderChildManager;

    @Autowired
    private PimsPathologyOrderCheckManager pimsPathologyOrderCheckManager;


    @RequestMapping(value = "/save")
    @ResponseBody
    public void save(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PimsPathologyOrder pathologyOrder = (PimsPathologyOrder)setBeanProperty(request, PimsPathologyOrder.class);
        
    }


}
