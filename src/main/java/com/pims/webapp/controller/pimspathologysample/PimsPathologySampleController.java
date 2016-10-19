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
        view.addObject("local_userid",user.getId());//用户id
        view.addObject("local_username",user.getName());//用户姓名
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
        PimsBaseModel ppr = new PimsBaseModel();
        int row = Integer.parseInt(request.getParameter("rows"));
        int page = Integer.parseInt(request.getParameter("page"));
        ppr.setPage(page);
        ppr.setRow(row);
        ppr.setStart( row * (page - 1));
        ppr.setEnd( row * page);
        ppr.setSord(request.getParameter("sord"));
        ppr.setReq_sts("0");
        String code = request.getParameter("reqId");
        List<PimsPathologyRequisition> list = pimsPathologyRequisitionManager.getRequisitionInfo(ppr);
        if(list == null || list.size() == 0) {
            return null;
        }
        int num = pimsPathologyRequisitionManager.getReqListNum(ppr);
        dataResponse.setRecords(num);
        dataResponse.setPage(ppr.getPage());
        dataResponse.setTotal(getTotalPage(num, ppr.getRow(), ppr.getPage()));
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
    @RequestMapping(value = "/canchange*", method = RequestMethod.GET)
    public void canChange(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String code = request.getParameter("id");
        String sts = request.getParameter("sts");
        JSONObject o = new JSONObject();
        if(pimsPathologySampleManager.canChange(Long.parseLong(code),sts)){
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
    @RequestMapping(value = "/editSample*", method = RequestMethod.POST)
    public void editSample(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PimsPathologySample ppr = (PimsPathologySample)setBeanProperty(request,PimsPathologySample.class);
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        JSONObject o = new JSONObject();
        if(StringUtils.isEmpty(String.valueOf(ppr.getSampleid())) || String.valueOf(ppr.getSampleid()).equals("0")){
            ppr = pimsPathologySampleManager.save(ppr);
            //更新电子申请单已被使用
            pimsPathologyRequisitionManager.updateReqState(ppr,1);
            o.put("message", "标本添加成功！");
            o.put("success", true);
        } else{
            pimsPathologySampleManager.save(ppr);
            o.put("message", "标本编辑成功！");
            o.put("success", true);
        }
        PrintwriterUtil.print(response, o.toString());
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
            //更新电子申请单未被使用
            //pimsPathologyRequisitionManager.updateReqState(ppr,0);
            o.put("message", "标本删除成功！");
            o.put("success", true);
        }
        PrintwriterUtil.print(response, o.toString());
    }

}
