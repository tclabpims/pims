package com.pims.webapp.controller.othermanage;

import com.pims.webapp.controller.PIMSBaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/othermanage/specialglass")
public class SpecialGlassPrintController extends PIMSBaseController {

    /**
     * 渲染视图
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView handleRequest(HttpServletRequest request) throws Exception {
//        Calendar c = Calendar.getInstance();
//        c.add(Calendar.DATE, - 7);
//        Date monday = c.getTime();
//        String sevenDay = Constants.DF2.format(monday);
//        String today = Constants.DF2.format(new Date());
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String logylibid = user.getUserBussinessRelate().getPathologyLibId();//病种库
//        ModelAndView view = new ModelAndView();
//        view.addObject("logyid",logylibid);//当前用户选择的病例库
//        view.addObject("sevenday", sevenDay);//7天前
//        view.addObject("receivetime", today);//当前时间
//        view.addObject("send_hosptail",user.getHospitalId());//账号所属医院
//        view.addObject("local_userid",user.getId());//用户id
//        view.addObject("local_username",user.getName());//用户姓名
//        return view;
        return getmodelView(request);
    }
}


