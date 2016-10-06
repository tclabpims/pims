package com.pims.webapp.controller.pimssyspathology;

import com.pims.webapp.controller.PIMSBaseController;
import com.smart.webapp.util.DataResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by 909436637@qq.com on 2016/10/6.
 * Description:
 */
@Controller
@RequestMapping("/template/*")
public class PimsPathologyTemplateController extends PIMSBaseController {

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView template()  throws java.lang.Exception {
        return new ModelAndView();
    }

    @RequestMapping(method = {RequestMethod.POST}, value = "/edit")
    @ResponseBody
    public void saveOrUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {

    }

    @RequestMapping(method = {RequestMethod.GET}, value = "/query")
    @ResponseBody
    public DataResponse query(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DataResponse dr = null;
        return dr;
    }

    @RequestMapping(method = {RequestMethod.POST}, value = "/remove")
    public void remove(HttpServletRequest request, HttpServletResponse response) throws Exception {

    }
}
