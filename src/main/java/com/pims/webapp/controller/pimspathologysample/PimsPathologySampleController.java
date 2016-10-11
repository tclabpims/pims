package com.pims.webapp.controller.pimspathologysample;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.pims.model.*;
import com.pims.service.his.PimsPathologyRequisitionManager;
import com.pims.service.pimspathologysample.PimsPathologySampleManager;
import com.pims.webapp.controller.PIMSBaseController;
import com.smart.Constants;
import com.smart.model.user.User;
import com.smart.webapp.util.DataResponse;
import com.smart.webapp.util.PrintwriterUtil;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by king on 2016/10/10.
 */
@Controller
@RequestMapping("/pathologysample/sample")
public class PimsPathologySampleController extends PIMSBaseController{
    @Autowired
    private PimsPathologyRequisitionManager pimsPathologyRequisitionManager;
    @Autowired
    private PimsPathologySampleManager pimsPathologySampleManager;

    /**
     * 渲染视图
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView handleRequest(HttpServletRequest request) throws Exception {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, - 7);
        Date monday = c.getTime();
        String sevenDay = Constants.DF2.format(monday);
        String today = Constants.DF2.format(new Date());
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String logylibid = user.getUserBussinessRelate().getPathologyLibId();//病种库
        ModelAndView view = new ModelAndView();
        view.addObject("logyid",logylibid);//当前用户选择的病例库
        view.addObject("sevenday", sevenDay);//7天前
        view.addObject("receivetime", today);//当前时间
        view.addObject("send_hosptail",user.getHospitalId());//账号所属医院
        return view;
    }

    /**
     * 获取单据详细信息
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/get*", method = RequestMethod.GET)
    public void getsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String code = request.getParameter("id");
        PimsPathologySample pathology = pimsPathologySampleManager.getBySampleNo(Long.parseLong(code));
        JSONObject pathMap = getJSONObject(pathology);
        PrintwriterUtil.print(response, pathMap.toString());
    }

    /**
     * 获取标本列表
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
        List<PimsPathologySample> list = pimsPathologySampleManager.getSampleList(ppr);
        int num = pimsPathologySampleManager.getReqListNum(ppr);
        if(list == null || list.size() == 0) {
            return null;
        }
        dataResponse.setRecords(num);
        dataResponse.setPage(ppr.getPage());
        dataResponse.setTotal(getTotalPage(num, ppr.getRow(), ppr.getPage()));
        dataResponse.setRows(getResultMap(list));
        response.setContentType("text/html; charset=UTF-8");
        return dataResponse;
    }

    /**
     * 获取申请单列表
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/ajax/getreqinfo*", method = RequestMethod.GET)
    @ResponseBody
    public DataResponse getListByReqId(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DataResponse dataResponse = new DataResponse();
        String code = request.getParameter("reqId");
        if(StringUtils.isEmpty(code)){
            return null;
        }
        List<PimsPathologyRequisition> list = pimsPathologyRequisitionManager.getRequisitionInfo(null);
        if(list == null || list.size() == 0) {
            return null;
        }
        //dataResponse.setRecords(list.size());
        dataResponse.setRows(getResultMap(list));
        response.setContentType("text/html; charset=UTF-8");
        return dataResponse;
    }

    /**
     * 查看单据是否可修改
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/canchange*", method = RequestMethod.POST)
    public String canChange(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String code = request.getParameter("id");
        String sts = request.getParameter("sts");
        JSONObject o = new JSONObject();
        if(pimsPathologySampleManager.canChange(Long.parseLong(code),sts)){
            o.put("success", true);
        }else{
            o.put("success", false);
        }
        response.setContentType("text/html; charset=UTF-8");
        response.getWriter().write(o.toString());
        return null;
    }

    /**
     * 保存单据
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/editSample*", method = RequestMethod.POST)
    public String editSample(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PimsPathologySample ppr = (PimsPathologySample)setBeanProperty(request,PimsPathologySample.class);
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        JSONObject o = new JSONObject();
        if(StringUtils.isEmpty(String.valueOf(ppr.getSampleid())) || String.valueOf(ppr.getSampleid()).equals("0")){
            if(StringUtils.isEmpty(String.valueOf(ppr.getSamsource()))){
                ppr.setSamsource(0);
            }
            ppr.setSampatientid("0");
            ppr.setSaminpatientid("0");
            ppr.setSampatienttype(1);
            ppr.setSamsampleclass("0");
            ppr.setSamsamplename("0");
            ppr.setSampopuser("0");
            ppr.setSamsamplestatus(0);
            ppr.setSamregisttime(new Date());
            ppr.setSamregisterid(String.valueOf(user.getId()));
            ppr = pimsPathologySampleManager.save(ppr);
            o.put("message", "标本添加成功！");
            o.put("success", true);
        } else{
            if(StringUtils.isEmpty(String.valueOf(ppr.getSamsource()))){
                ppr.setSamsource(0);
            }
            ppr.setSampatientid("0");
            ppr.setSaminpatientid("0");
            ppr.setSampatienttype(1);
            ppr.setSamsampleclass("0");
            ppr.setSamsamplename("0");
            ppr.setSampopuser("0");
            ppr.setSamsamplestatus(0);
            ppr.setSamregisttime(new Date());
            pimsPathologySampleManager.save(ppr);
            o.put("message", "标本编辑成功！");
            o.put("success", true);
        }
        response.setContentType("text/html; charset=UTF-8");
        response.getWriter().write(o.toString());
        return null;
    }

    /**
     * 删除单据
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/deleteSample*", method = RequestMethod.POST)
    public void deleteSample(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PimsPathologySample ppr = (PimsPathologySample)setBeanProperty(request,PimsPathologySample.class);
        JSONObject o = new JSONObject();
        if(StringUtils.isEmpty(String.valueOf(ppr.getSampleid()))){
            o.put("message", "查不到该标本的信息！");
            o.put("success", false);
        }else{
            pimsPathologySampleManager.delete((long) ppr.getSampleid());
            o.put("message", "标本删除成功！");
            o.put("success", true);
        }
        response.setContentType("text/html; charset=UTF-8");
        response.getWriter().write(o.toString());
    }

}
