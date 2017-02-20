package com.pims.webapp.controller.report;

import com.pims.webapp.controller.PIMSBaseController;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zp on 2017/2/17.
 */
@Controller
@RequestMapping("/report/delayData")
public class DelayDaysSetting extends PIMSBaseController {
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView handleRequest(HttpServletRequest request) throws Exception {
       return getmodelView(request);
    }


}
