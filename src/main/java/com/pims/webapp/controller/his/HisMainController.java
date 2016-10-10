package com.pims.webapp.controller.his;

import com.pims.model.PimsSysReqTestitem;
import com.pims.service.his.PimsPathologyRequisitionManager;
import com.pims.service.pimssysreqtestitem.PimsSysReqTestitemManager;
import com.pims.webapp.controller.PIMSBaseController;
import com.smart.Constants;
import com.smart.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by king on 2016/9/28.
 */
@Controller
@RequestMapping("/his/his_main*")
public class HisMainController extends PIMSBaseController {
    @Autowired
    private PimsSysReqTestitemManager pimsSysReqTestitemManager;
    @Autowired
    private PimsPathologyRequisitionManager pimsPathologyRequisitionManager;
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView handleRequest(HttpServletRequest request) throws Exception {
        String today = Constants.DF2.format(new Date());
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String logylibid = user.getUserBussinessRelate().getPathologyLibId();//病种库
        List<PimsSysReqTestitem> list = pimsSysReqTestitemManager.getTestitemInfo(null);//查询申请项目列表
        int nowyear = Calendar.getInstance().get(Calendar.YEAR);//获取当前年份
        String requisitionno = "";
        String maxCode = pimsPathologyRequisitionManager.getMaxCode(999);
        if(maxCode == null || !maxCode.contains(String.valueOf(nowyear))){
            requisitionno = nowyear + "00000001";
        }else if(maxCode.contains(String.valueOf(nowyear))){
            long a = Long.valueOf(maxCode).longValue() + 1;
            requisitionno =  String.valueOf(a);
        }
        ModelAndView view = new ModelAndView();
        view.addObject("requisitionno",requisitionno);//申请单号
        view.addObject("receivetime", today);//当前时间
        view.addObject("reqcustomerid",user.getHospitalId());//账号所属医院
        view.addObject("reqpathologyid",logylibid);//当前用户选择的病例库
        view.addObject("reqsource",0);//申请单来源
        view.addObject("testList",getResultMap(list));//申请项目列表
        return view;
    }
}
