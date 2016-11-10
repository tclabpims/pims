package com.pims.webapp.controller;

import com.pims.model.PimsBaseModel;
import com.pims.service.pimspathologysample.PimsPathologyConsultationManager;
import com.pims.service.pimspathologysample.PimsPathologyFeeManager;
import com.pims.service.pimspathologysample.PimsPathologySampleManager;
import com.pims.service.pimssyspathology.PimsHospitalPathologyInfoManager;
import com.pims.service.pimssyspathology.PimsSysPathologyManager;
import com.pims.service.pimssyspathology.PimsSysTestFeeManager;
import com.smart.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by king on 2016/11/10.
 */
@Controller
@RequestMapping("/home")
public class HomeController extends PIMSBaseController{
    @Autowired
    private PimsPathologySampleManager pimsPathologySampleManager;//标本
    @Autowired
    private PimsPathologyConsultationManager pimsPathologyConsultationManager;//病理会诊
    @Autowired
    private PimsSysPathologyManager pimsSysPathologyManager;
    @Autowired
    private PimsHospitalPathologyInfoManager pimsHospitalPathologyInfoManager;
    @Autowired
    private PimsPathologyFeeManager pimsPathologyFeeManager;
    @Autowired
    private PimsSysTestFeeManager pimsSysTestFeeManager;
    /**
     * 渲染视图
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView handleRequest(HttpServletRequest request) throws Exception {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PimsBaseModel map = new PimsBaseModel();
        ModelAndView view = new ModelAndView();
        int noworkList = pimsPathologySampleManager.getSamStaNum();//工作未处理
        map.setReq_code("3");
        int nocc = pimsPathologySampleManager.getReqListNum(map);//未初查
        map.setReq_code("4");
        int nosh = pimsPathologySampleManager.getReqListNum(map);//未审核
        map.setReq_code("6");
        int nody = pimsPathologySampleManager.getReqListNum(map);//未打印
        map.setReq_code("5");
        int nofs = pimsPathologySampleManager.getReqListNum(map);//未发送
        map.setReq_code("3");
        int nojs = pimsPathologySampleManager.getReqListNum(map);//未接收
        map = new PimsBaseModel();
        int noqs = pimsPathologySampleManager.getReqListNum(map);//未签收
        map = new PimsBaseModel();
        int noqc = pimsPathologySampleManager.getReqListNum(map);//未取材
        map = new PimsBaseModel();
        map.setReq_sts("0");
        map.setSend_hosptail(String.valueOf(user.getId()));
        int mycons = pimsPathologyConsultationManager.getReqListNum(map);//病理会诊
        view.addObject("noworkList",noworkList);//工作未处理
        view.addObject("nocc",nocc);//未初查
        view.addObject("nosh",nosh);//未审核
        view.addObject("nody",nody);//未打印
        view.addObject("nofs",nofs);//未发送
        view.addObject("nojs",nojs);//未接收
        view.addObject("noqs",noqs);//未签收
        view.addObject("noqc",noqc);//未取材
        view.addObject("mycons",mycons);//病理会诊
        return view;
    }

}
