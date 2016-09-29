package com.pims.webapp.controller.his;

import com.smart.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by king on 2016/9/28.
 */
@Controller
@RequestMapping("/his/his_main*")
public class HisMainController {
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView handleRequest(HttpServletRequest request) throws Exception {
        //User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String today = Constants.DF3.format(new Date());
        ModelAndView view = new ModelAndView();
        //view.addObject("receivetime", Constants.SDF.format(new Date()));
        view.addObject("receivetime", today);
        return view;
    }
}
