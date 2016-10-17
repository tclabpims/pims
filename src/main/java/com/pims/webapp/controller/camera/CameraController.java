package com.pims.webapp.controller.camera;

import com.pims.webapp.controller.PIMSBaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by 909436637@qq.com on 2016/10/14.
 * Description:
 */
@Controller
@RequestMapping(value = "/camera/*")
public class CameraController extends PIMSBaseController {

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView camera(HttpServletRequest request, HttpServletResponse response) throws Exception {

        return new ModelAndView();
    }

}
