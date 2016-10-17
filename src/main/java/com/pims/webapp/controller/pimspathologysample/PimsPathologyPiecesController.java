package com.pims.webapp.controller.pimspathologysample;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.pims.model.PimsBaseModel;
import com.pims.model.PimsPathologyPieces;
import com.pims.model.PimsPathologyRequisition;
import com.pims.model.PimsPathologySample;
import com.pims.service.his.PimsPathologyRequisitionManager;
import com.pims.service.pimspathologysample.PimsPathologyPiecesManager;
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
@RequestMapping("/pathologysample/pieces")
public class PimsPathologyPiecesController extends PIMSBaseController{
    @Autowired
    private PimsPathologyPiecesManager pimsPathologyPiecesManager;
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
        ModelAndView view = new ModelAndView();
//        view.addObject("logyid",logylibid);//当前用户选择的病例库
//        view.addObject("sevenday", sevenDay);//7天前
//        view.addObject("receivetime", today);//当前时间
//        view.addObject("send_hosptail",user.getHospitalId());//账号所属医院
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
        PimsPathologySample pathology = pimsPathologyPiecesManager.getBySampleNo(Long.parseLong(code));
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
        List<PimsPathologySample> list = pimsPathologyPiecesManager.getSampleList(ppr);
        int num = pimsPathologyPiecesManager.getReqListNum(ppr);
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
    @RequestMapping(value = "/ajax/getItem*", method = RequestMethod.GET)
    @ResponseBody
    public DataResponse getListByReqId(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DataResponse dataResponse = new DataResponse();
        String code = request.getParameter("reqId");
        if(StringUtils.isEmpty(code)){
            return null;
        }
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
        if(pimsPathologyPiecesManager.canChange(Long.parseLong(code),sts)){
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
        String materiallist = request.getParameter("samthirdv");
        String savenum = request.getParameter("savenum");
        JSONArray materials = JSON.parseArray(materiallist);
        JSONObject o = new JSONObject();
        //查询该标本的材块信息
        List<PimsPathologyPieces> list = pimsPathologyPiecesManager.getSampleListNoPage(String.valueOf(ppr.getSampleid()));
        List list1 = new ArrayList();
        List list2 = new ArrayList();
        if(savenum.equals("1")) {//取材
            //更新标本状态为已取材
            pimsPathologyPiecesManager.updateSample(ppr);
            for (int i = 0; i < materials.size(); i++) {
                Map map = (Map) materials.get(i);
                PimsPathologyPieces mater = (PimsPathologyPieces) setBeanProperty(map, PimsPathologyPieces.class);
                if (mater.getPiestate().longValue() == 0) {
                    mater.setPiestate(Long.valueOf(1));
                }
                mater = pimsPathologyPiecesManager.save(mater);
                list1.add(mater.getPieceid());
            }
        }else{//保存
            for (int i = 0; i < materials.size(); i++) {
                Map map = (Map) materials.get(i);
                PimsPathologyPieces mater = (PimsPathologyPieces) setBeanProperty(map, PimsPathologyPieces.class);
                if (!list2.contains(mater.getPiestate().toString())) {
                    list2.add(mater.getPiestate().toString());
                }
                mater = pimsPathologyPiecesManager.save(mater);
                list1.add(mater.getPieceid());
            }
            if(list2.contains("0")){
                pimsPathologyPiecesManager.updateSampleSts(ppr,0);
            }else{
                pimsPathologyPiecesManager.updateSampleSts(ppr,1);
            }
        }
        //删除不存在的材块
        for(PimsPathologyPieces pps : list){
            if(!list1.contains(pps.getPieceid())){
                pimsPathologyPiecesManager.delete(pps.getPieceid());
            }
        }
        o.put("message", "取材成功！");
        o.put("success", true);
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
        String id = request.getParameter("pieceid");
        JSONObject o = new JSONObject();
        if(StringUtils.isEmpty(id)){
            o.put("message", "查不到该材块的信息！");
            o.put("success", false);
        }else{
            pimsPathologyPiecesManager.delete(Long.parseLong(id));
            o.put("message", "标本删除成功！");
            o.put("success", true);
        }
        PrintwriterUtil.print(response, o.toString());
    }

}
