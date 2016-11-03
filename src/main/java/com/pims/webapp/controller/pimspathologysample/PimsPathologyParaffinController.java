package com.pims.webapp.controller.pimspathologysample;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pims.model.PimsBaseModel;
import com.pims.model.PimsPathologyParaffin;
import com.pims.model.PimsPathologyPieces;
import com.pims.model.PimsPathologySample;
import com.pims.service.pimspathologysample.PimsPathologyParaffinManager;
import com.pims.service.pimspathologysample.PimsPathologyPiecesManager;
import com.pims.service.pimspathologysample.PimsPathologySampleManager;
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
@RequestMapping("/pathologysample/paraffin")
public class PimsPathologyParaffinController extends PIMSBaseController{
    @Autowired
    private PimsPathologyParaffinManager pimsPathologyParaffinManager;
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
//        view.addObject("local_name_id",user.getId());//当前登录用户id
////        view.addObject("send_hosptail",user.getHospitalId());//账号所属医院
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
        PimsPathologySample pathology = pimsPathologyParaffinManager.getBySampleNo(Long.parseLong(code));
        JSONObject pathMap = getJSONObject(pathology);
        PrintwriterUtil.print(response, pathMap.toString());
    }

    /**
     * 获取材块列表
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
        List list = pimsPathologyParaffinManager.getSampleList(ppr);
        int num = pimsPathologyParaffinManager.getReqListNum(ppr);
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
     * 获取包埋列表
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
        List list = pimsPathologyParaffinManager.getSampleListNoPage(code);
        if(list == null || list.size() == 0) {
            return null;
        }
        dataResponse.setRecords(list.size());
        dataResponse.setRows(getResultMaps(list));
        response.setContentType("text/html; charset=UTF-8");
        return dataResponse;
    }
    /**
     * 查看单据是否可以取消包埋
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/canchange*", method = RequestMethod.GET)
    public void canChange(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String code = request.getParameter("id");
        String sts = request.getParameter("sts");
        JSONObject o = new JSONObject();
        if(pimsPathologyParaffinManager.canChange(Long.parseLong(code),sts)){
            o.put("success", true);
        }else{
            o.put("success", false);
            o.put("message","无法进行该操作！");
        }
        PrintwriterUtil.print(response, o.toString());
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
        PimsPathologySample ppr = (PimsPathologySample)setBeanProperty(request,PimsPathologySample.class);
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String slides = request.getParameter("slides");
        String samples = request.getParameter("samples");
        String savenum = request.getParameter("savenum");
        String states = request.getParameter("states");
        JSONArray samplesList = JSON.parseArray(samples);
        JSONArray slidesList = JSON.parseArray(slides);
        JSONObject o = new JSONObject();
//        pimsPathologyParaffinManager.updateSampleSts(slidesList,slidesList,samplesList,Integer.parseInt(savenum),Integer.parseInt(states));
        pimsPathologyParaffinManager.updateSampleSts(slidesList,samplesList,samplesList,Integer.parseInt(savenum),Integer.parseInt(states));
        o.put("message", "操作成功！");
        o.put("success", true);
        PrintwriterUtil.print(response, o.toString());

    }
}
