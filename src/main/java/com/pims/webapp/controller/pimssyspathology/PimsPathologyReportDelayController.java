package com.pims.webapp.controller.pimssyspathology;

import com.pims.model.PimsPathologyReportDelay;
import com.pims.service.pimssyspathology.PimsPathologyReportDelayManager;
import com.pims.webapp.controller.PIMSBaseController;
import com.pims.webapp.controller.WebControllerUtil;
import com.smart.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by 909436637@qq.com on 2016/11/21.
 * Description:
 */
@Controller
@RequestMapping(value = "/reportdelay")
public class PimsPathologyReportDelayController extends PIMSBaseController {

    @Autowired
    private PimsPathologyReportDelayManager pimsPathologyReportDelayManager;

    @RequestMapping(value="/save", method = RequestMethod.GET)
    @ResponseBody
    public void save(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PimsPathologyReportDelay reportDelay = (PimsPathologyReportDelay)setBeanProperty(request, PimsPathologyReportDelay.class);
        User user = WebControllerUtil.getAuthUser();
        reportDelay.setDelcreatetime(new Date());
        reportDelay.setDelcreateuser(user.getUsername());
        reportDelay.setDelfirstn(user.getId());
        reportDelay.setDeldoctor(user.getName());
        pimsPathologyReportDelayManager.save(reportDelay);
    }

}
