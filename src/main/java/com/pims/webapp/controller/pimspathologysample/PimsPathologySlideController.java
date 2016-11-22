package com.pims.webapp.controller.pimspathologysample;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pims.model.PimsBaseModel;
import com.pims.model.PimsPathologyParaffin;
import com.pims.model.PimsPathologyPieces;
import com.pims.model.PimsPathologySample;
import com.pims.service.pimspathologysample.PimsPathologyParaffinManager;
import com.pims.service.pimspathologysample.PimsPathologySampleManager;
import com.pims.service.pimspathologysample.PimsPathologySlideManager;
import com.pims.webapp.controller.PIMSBaseController;
import com.smart.Constants;
import com.smart.model.user.User;
import com.smart.webapp.util.DataResponse;
import com.smart.webapp.util.PrintwriterUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by king on 2016/10/10.
 */
@Controller
@RequestMapping("/pathologysample/slide")
public class PimsPathologySlideController extends PIMSBaseController{
    @Autowired
    private PimsPathologySlideManager pimsPathologySlideManager;
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
////        String logylibid = user.getUserBussinessRelate().getPathologyLibId();//病种库
//        ModelAndView view = new ModelAndView();
////        view.addObject("logyid",logylibid);//当前用户选择的病例库
//        view.addObject("sevenday", sevenDay);//7天前
//        view.addObject("receivetime", today);//当前时间
//        view.addObject("local_name",user.getName());//当前登录用户名
//        view.addObject("local_hosptail",user.getHospitalId());//账号所属医院
//        return view;
        return getmodelView(request);
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
        PimsPathologySample pathology = pimsPathologySlideManager.getBySampleNo(Long.parseLong(code));
        JSONObject pathMap = getJSONObject(pathology);
        PrintwriterUtil.print(response, pathMap.toString());
    }

    /**
     * 获取蜡块列表
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
        List<PimsPathologyParaffin> list = pimsPathologySlideManager.getSampleList(ppr);
        int num = pimsPathologySlideManager.getReqListNum(ppr);
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
     * 获取玻片列表
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/ajax/getItem*", method = RequestMethod.GET)
    @ResponseBody
    public DataResponse getListByReqId(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DataResponse dataResponse = new DataResponse();
        String code = request.getParameter("reqId");
        if(StringUtils.isEmpty(code)){
            return null;
        }
        List list = pimsPathologySlideManager.getSampleListNoPage(code);
        if(list == null || list.size() == 0) {
            return null;
        }
        dataResponse.setRecords(list.size());
        dataResponse.setRows(getResultMaps(list));
        response.setContentType("text/html; charset=UTF-8");
        return dataResponse;
    }
    /**
     * 保存单据
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Transactional
    @RequestMapping(value = "/editSample*", method = RequestMethod.POST)
    public void editSample(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String slides = request.getParameter("slides");
        String samples = request.getParameter("samples");
        String savenum = request.getParameter("savenum");
        String states = request.getParameter("states");
        JSONArray samplesList = JSON.parseArray(samples);
        JSONArray slidesList = JSON.parseArray(slides);
        JSONObject o = new JSONObject();
        //pimsPathologySlideManager.updateSampleSts(slidesList,slidesList,samplesList,Integer.parseInt(savenum),Integer.parseInt(states));
        pimsPathologySlideManager.updateSampleSts(slidesList,samplesList,samplesList,Integer.parseInt(savenum),Integer.parseInt(states));
        o.put("message", "操作成功！");
        o.put("success", true);
        PrintwriterUtil.print(response, o.toString());
    }

    @RequestMapping(value = "/printcode*", method = RequestMethod.POST)
    public String getPatient(HttpServletRequest request, HttpServletResponse response) throws Exception{
        org.codehaus.jettison.json.JSONObject o = new org.codehaus.jettison.json.JSONObject();
        String samples = request.getParameter("samples");
        JSONArray samplesList = JSON.parseArray(samples);

        JSONArray array = pimsPathologySlideManager.getSlideCode(samplesList);
        o.put("labOrders", array);
        response.setContentType("name/html; charset=UTF-8");
        response.getWriter().write(o.toString());
        return null;
    }
}
