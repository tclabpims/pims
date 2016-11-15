package com.pims.webapp.controller.pimspathologysample;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pims.model.PimsBaseModel;
import com.pims.model.PimsPathologySample;
import com.pims.model.PimsPathologyTask;
import com.pims.service.pimspathologysample.PimsPathologyReceivemessageManager;
import com.pims.service.pimspathologysample.PimsPathologyTaskManager;
import com.pims.webapp.controller.PIMSBaseController;
import com.smart.model.user.User;
import com.smart.webapp.util.DataResponse;
import com.smart.webapp.util.PrintwriterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by king on 2016/10/25.
 */
@Controller
@RequestMapping("/receive/receive")
public class PimsPathologyReceivemessageController extends PIMSBaseController{
    @Autowired
    private PimsPathologyReceivemessageManager pimsPathologyReceivemessageManager;

    /**
     * 渲染视图
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView handleRequest(HttpServletRequest request) throws Exception {
        ModelAndView  view =  getmodelView(request);
        return view;
    }

    /**
     * 获取列表
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/ajax/sample*", method = RequestMethod.GET)
    @ResponseBody
    public DataResponse getRequisitionInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DataResponse dataResponse = new DataResponse();
        PimsBaseModel ppr = new PimsBaseModel(request);
        List list = pimsPathologyReceivemessageManager.getTaskList(ppr);
        int num = pimsPathologyReceivemessageManager.getTaskListNum(ppr);
        if(list == null || list.size() == 0) {
            return null;
        }
        dataResponse.setRecords(num);
        dataResponse.setPage(ppr.getPage());
        dataResponse.setTotal(getTotalPage(num, ppr.getRow(), ppr.getPage()));
        dataResponse.setRows(getResultMaps(list));
        response.setContentType("text/html; charset=UTF-8");
        return dataResponse;
    }

    /**
     * 获取新消息数量
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/ajax/num*", method = RequestMethod.GET)
    @ResponseBody
    public String getReqNum(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PimsBaseModel ppr = new PimsBaseModel();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ppr.setPatient_name(String.valueOf(user.getId()));
        ppr.setReq_sts("0");
        int num = pimsPathologyReceivemessageManager.getTaskListNum(ppr);
        JSONObject o = new JSONObject();
        o.put("num",num);
        o.put("success",1);
        PrintwriterUtil.print(response, o.toString());
        return  null;

    }
    /**
     * 接收消息
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/editSample*", method = RequestMethod.POST)
    public String editSample(HttpServletRequest request, HttpServletResponse response) throws Exception {
        JSONObject o = new JSONObject();
        String id = request.getParameter("id");
        pimsPathologyReceivemessageManager.updateConStates(Long.parseLong(id));
        o.put("success",true);
        PrintwriterUtil.print(response, o.toString());
        return  null;
    }
}
