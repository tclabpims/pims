package com.pims.webapp.controller.pimspathologysample;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pims.model.*;
import com.pims.service.his.PimsPathologyRequisitionManager;
import com.pims.service.pimspathologysample.PimsConsultationDetailManager;
import com.pims.service.pimspathologysample.PimsPathologyConsultationManager;
import com.pims.service.pimspathologysample.PimsPathologyPiecesManager;
import com.pims.service.pimspathologysample.PimsPathologySampleManager;
import com.pims.webapp.controller.PIMSBaseController;
import com.smart.model.user.User;
import com.smart.webapp.util.DataResponse;
import com.smart.webapp.util.PrintwriterUtil;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by king on 2016/10/25.
 */
@Controller
@RequestMapping("/consultation/cons")
public class PimsPathologyConsultationController extends PIMSBaseController{
    @Autowired
    private PimsPathologyPiecesManager pimsPathologyPiecesManager;
    @Autowired
    private PimsPathologySampleManager pimsPathologySampleManager;
    @Autowired
    private PimsPathologyConsultationManager pimsPathologyConsultationManager;
    @Autowired
    private PimsConsultationDetailManager pimsConsultationDetailManager;

    /**
     * 渲染视图
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView handleRequest(HttpServletRequest request) throws Exception {
        String sampleId = request.getParameter("id");
        //获取会诊详细信息
        PimsPathologyConsultation cons = pimsPathologyConsultationManager.getConsInfo(Long.parseLong(sampleId));
        ModelAndView  view =  getmodelView(request);
        view.addObject("sampleId",sampleId);
        view.addObject("consultationid",cons.getConsultationid());
        view.addObject("consponsoreduserid",cons.getConsponsoreduserid());
        view.addObject("conconsultationstate",cons.getConconsultationstate());
        return view;
    }

    /**
     * 获取医生列表
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/ajax/doctor*", method = RequestMethod.GET)
    @ResponseBody
    public DataResponse getDoctorList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DataResponse dataResponse = new DataResponse();
        String code = request.getParameter("id");
        PimsPathologyConsultation con = pimsPathologyConsultationManager.getConsInfo(Long.parseLong(code));
        List<PimsConsultationDetail> list = pimsPathologyConsultationManager.getConDets(con.getConsultationid());
        if(list == null || list.size() == 0) {
            return null;
        }
        dataResponse.setRows(getResultMap(list));
        response.setContentType("text/html; charset=UTF-8");
        return dataResponse;
    }

    /**
     * 获取会诊列表
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
        List<PimsPathologyConsultation> list = pimsPathologyConsultationManager.getConList(ppr);
        int num = pimsPathologyConsultationManager.getReqListNum(ppr);
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
     * 获取材块列表
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/ajax/peice*", method = RequestMethod.GET)
    @ResponseBody
    public DataResponse getPeices(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DataResponse dataResponse = new DataResponse();
        String code = request.getParameter("id");
        List<PimsPathologyPieces> list = pimsPathologyPiecesManager.getSampleListNoPage(code);
        if(list == null || list.size() == 0) {
            return null;
        }
        dataResponse.setRecords(list.size());
        dataResponse.setRows(getResultMap(list));
        response.setContentType("text/html; charset=UTF-8");
        return dataResponse;
    }

    /**
     * 获取标本单据详细信息
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/get*", method = RequestMethod.GET)
    public void getsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String code = request.getParameter("id");
        //标本信息
        PimsPathologySample pathology = pimsPathologySampleManager.getBySampleNo(Long.parseLong(code));
        JSONObject pathMap = getJSONObject(pathology);
        //蜡块个数
        int nums = pimsPathologyConsultationManager.getParaNums(Long.parseLong(code));
        pathMap.put("paranums",nums);
        //病理诊断
        //免组染色
        //HPV结果
        PrintwriterUtil.print(response, pathMap.toString());
    }

    /**
     * 获取会诊意见列表
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/ajax/getreqinfo*", method = RequestMethod.POST)
    @ResponseBody
    public void getListByReqId(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String code = request.getParameter("id");
        List<PimsConsultationDetail> list = pimsPathologyConsultationManager.getConDets(Long.parseLong(code));
        JSONArray array = new JSONArray();
        for(PimsConsultationDetail s:list){
            JSONObject o = getJSONObject(s);
            array.put(o);
        }
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(array.toString());
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
        JSONObject o = new JSONObject();
        PimsPathologyConsultation ppr = (PimsPathologyConsultation)setBeanProperty(request,PimsPathologyConsultation.class);
        //查询会诊详细信息
        PimsPathologyConsultation con = pimsPathologyConsultationManager.getConsInfo(ppr.getConsampleid());
        if(con.getConconsultationstate() != 0){
            o.put("message", "会诊已结束！");
            o.put("success", false);
            PrintwriterUtil.print(response, o.toString());
            return null;
        }
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(con.getConsponsoreduserid() != null && con.getConsponsoreduserid().equals(String.valueOf(user.getId()))){
            pimsPathologyConsultationManager.updatePictureStates((int)ppr.getConconsultationstate(),con.getConsultationid());
        }
        //更改会诊结果信息
        if(con.getConsponsoreduserid() != null && !con.getConsponsoreduserid().equals(String.valueOf(user.getId()))){
            PimsConsultationDetail condet = (PimsConsultationDetail)setBeanProperty(request,PimsConsultationDetail.class);
            pimsConsultationDetailManager.updateDetil(condet);
        }
        //更新单据信息

        o.put("message", "保存成功！");
        o.put("success", true);
        PrintwriterUtil.print(response, o.toString());
        return  null;
    }

    /**
     * 发起会诊信息
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/addCons*", method = RequestMethod.POST)
    public String addCons(HttpServletRequest request, HttpServletResponse response) throws Exception {
        JSONObject o = new JSONObject();
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PimsPathologySample sample = (PimsPathologySample)setBeanProperty(request,PimsPathologySample.class);
        String  userList = request.getParameter("userlist");
        com.alibaba.fastjson.JSONArray users = JSON.parseArray(userList);
        PimsPathologyConsultation con = new PimsPathologyConsultation();
        con.setConsampleid(sample.getSampleid());//样本ID
        con.setConpathologycode(sample.getSampathologycode());//病理编号
        con.setConcustomerid(sample.getSamcustomerid());//客户Id
        con.setConsponsoreduserid(String.valueOf(user.getId()));//发起人
        con.setConsponsoredusername(user.getName());//发起人姓名
        con.setConsponsoredtime(new Date());//发起时间
        con.setConconsultationstate(0);//会诊状态,0 会诊中,1已完成
        con = pimsPathologyConsultationManager.save(con);
        for(int i=0;i<users.size();i++){
            Map map = (Map)users.get(i);
            PimsConsultationDetail condet = new PimsConsultationDetail();
            User u = (User)setBeanProperty(map,User.class);
            condet.setDetconsultationid(con.getConsultationid());//会诊Id
            condet.setDetsampleid(con.getConsampleid());//样本Id
            condet.setDetpathologycode(con.getConpathologycode());//病理编号
            condet.setDetcustomerid(con.getConcustomerid());//客户Id
            condet.setDetdoctorid(String.valueOf(u.getId()));//医生工号
            condet.setDetdoctorname(u.getName());//医生姓名
            condet.setDetstate(0);//状态
            pimsConsultationDetailManager.save(condet);
        }
        o.put("message", "标本编辑成功！");
        o.put("success", true);
        PrintwriterUtil.print(response, o.toString());
        return  null;
    }

    /**
     * 查询病理是否已经发起了会诊
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/isexist*", method = RequestMethod.POST)
    public String isexist(HttpServletRequest request, HttpServletResponse response) throws Exception {
        JSONObject o = new JSONObject();
        String id = request.getParameter("id");
        //查询会诊详细信息
        PimsPathologyConsultation con = pimsPathologyConsultationManager.getConsInfo(Long.parseLong(id));
        if(con == null){
            o.put("success", false);
            PrintwriterUtil.print(response, o.toString());
        }else{
            o.put("success", true);
            PrintwriterUtil.print(response, o.toString());
        }
        return  null;
    }
}
