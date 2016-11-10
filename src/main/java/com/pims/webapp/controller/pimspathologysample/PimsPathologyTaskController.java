package com.pims.webapp.controller.pimspathologysample;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pims.model.*;
import com.pims.service.pimspathologysample.*;
import com.pims.webapp.controller.PIMSBaseController;
import com.smart.model.user.User;
import com.smart.webapp.util.DataResponse;
import com.smart.webapp.util.PrintwriterUtil;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyDescriptor;
import java.util.*;

/**
 * Created by king on 2016/10/25.
 */
@Controller
@RequestMapping("/task/task")
public class PimsPathologyTaskController extends PIMSBaseController{
    @Autowired
    private PimsPathologyTaskManager pimsPathologyTaskManager;
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
        ModelAndView  view =  getmodelView(request);
        return view;
    }

    /**
     * 获取任务列表
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
        List list = pimsPathologyTaskManager.getTaskList(ppr);
        int num = pimsPathologyTaskManager.getTaskListNum(ppr);
        if(list == null || list.size() == 0) {
            return null;
        }
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        for(Object bean:list){
            Map<String, Object> map = new HashMap<String, Object>();
            Object[] pd = (Object[]) bean;
            String[] st = {"sampleid","saminspectionid","sampathologycode","samregisttime","samregistername",
                    "saminitiallyusername","sampathologyid","tasrecivername","piedoctorname","pieembeddoctorname","parsectioneddoctor"};
            for(int i=0;i<pd.length;i++){
                Object o = pd[i];
                map.put(st[i],o);
                //System.out.println(o.getClass());
            }
            mapList.add(map);
        }
        dataResponse.setRecords(num);
        dataResponse.setPage(ppr.getPage());
        dataResponse.setTotal(getTotalPage(num, ppr.getRow(), ppr.getPage()));
//        dataResponse.setRows(getResultMap(list));
        dataResponse.setRows(mapList);
        response.setContentType("text/html; charset=UTF-8");
        return dataResponse;
    }
    /**
     * 接收抄送或取消抄送
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/editSample*", method = RequestMethod.POST)
    public String editSample(HttpServletRequest request, HttpServletResponse response) throws Exception {
        JSONObject o = new JSONObject();
        String tasks = request.getParameter("tasks");
        String states = request.getParameter("states");
        com.alibaba.fastjson.JSONArray taskList = JSON.parseArray(tasks);
        o = pimsPathologyTaskManager.updateConStates(Integer.parseInt(states),taskList);
        PrintwriterUtil.print(response, o.toString());
        return  null;
    }

    /**
     * 判断标本是否已抄送
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/isExistsTask*", method = RequestMethod.POST)
    public String isExistsTask(HttpServletRequest request, HttpServletResponse response) throws Exception {
        JSONObject o = new JSONObject();
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String states = request.getParameter("states");
        String tasks = request.getParameter("tasks");
        com.alibaba.fastjson.JSONArray taskList = JSON.parseArray(tasks);
        for(int i=0;i<taskList.size();i++){
            Map map = (Map) taskList.get(i);
            PimsPathologySample sample = (PimsPathologySample) setBeanProperty(map,PimsPathologySample.class);
            if(!pimsPathologyTaskManager.isExistsTask(Integer.parseInt(states),sample.getSampleid())) {
                o.put("message", "标本不允许发起多次抄送！");
                o.put("success", true);
                PrintwriterUtil.print(response, o.toString());
                return null;
            }
        }
        o.put("message", "可以发起抄送！");
        o.put("success", false);
        PrintwriterUtil.print(response, o.toString());
        return  null;
    }




    /**
     * 发起抄送
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/addTasks*", method = RequestMethod.POST)
    public String addTasks(HttpServletRequest request, HttpServletResponse response) throws Exception {
        JSONObject o = new JSONObject();
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String states = request.getParameter("states");
        String tasks = request.getParameter("tasks");
        com.alibaba.fastjson.JSONArray taskList = JSON.parseArray(tasks);
        for(int i=0;i<taskList.size();i++){
            Map map = (Map) taskList.get(i);
            PimsPathologySample sample = (PimsPathologySample) setBeanProperty(map,PimsPathologySample.class);
            sample = pimsPathologySampleManager.getBySampleNo(sample.getSampleid());
            if(pimsPathologyTaskManager.isExistsTask(Integer.parseInt(states),sample.getSampleid())){
                PimsPathologyTask task = new PimsPathologyTask();
                task.setTascustomerid(sample.getSamcustomerid());//客户Id
                task.setTassampleid(sample.getSampleid());//标本Id
                task.setTaspathologycode(sample.getSampathologycode());//病理编号
                task.setTastaskname("病理抄送");//任务名称
                task.setTastasktype(Integer.parseInt(states));//任务类型(0初诊抄送,1初诊转发)
                task.setTastaskstate(0);//任务状态
                task.setTaspromoterid(String.valueOf(user.getId()));//发起人Id
                task.setTaspromotername(user.getName());//发起人姓名
                task.setTasfirstd(new Date());//发起时间
                pimsPathologyTaskManager.save(task);
            }else{
                o.put("message", "标本不允许发起多次抄送！");
                o.put("success", false);
                PrintwriterUtil.print(response, o.toString());
                return  null;
            }
        }
        o.put("message", "发起抄送成功！");
        o.put("success", true);
        PrintwriterUtil.print(response, o.toString());
        return  null;
    }
}
