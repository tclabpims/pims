package com.pims.webapp.controller.pimspathologysample;

import com.alibaba.fastjson.JSONObject;
import com.pims.model.PimsBaseModel;
import com.pims.model.PimsPathologyMessage;
import com.pims.model.PimsPathologyReceivemessage;
import com.pims.service.PimsUserManager;
import com.pims.service.pimspathologysample.PimsPathologyMessageManager;
import com.pims.service.pimspathologysample.PimsPathologyReceivemessageManager;
import com.pims.webapp.controller.PIMSBaseController;
import com.smart.model.user.User;
import com.smart.webapp.util.DataResponse;
import com.smart.webapp.util.PrintwriterUtil;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by king on 2016/10/25.
 */
@Controller
@RequestMapping("/message/message")
public class PimsPathologyMessageController extends PIMSBaseController{
    @Autowired
    private PimsPathologyReceivemessageManager pimsPathologyReceivemessageManager;
    @Autowired
    private PimsPathologyMessageManager pimsPathologyMessageManager;
    @Autowired
    private PimsUserManager pimsUserManager;
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
        List list = pimsPathologyMessageManager.getTaskList(ppr);
        int num = pimsPathologyMessageManager.getTaskListNum(ppr);
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
     * 获取单据详细信息
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/get*", method = RequestMethod.GET)
    public void getsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String code = request.getParameter("id");
        PimsPathologyMessage pathology = pimsPathologyMessageManager.getBySampleNo(Long.parseLong(code));
        JSONObject pathMap = getJSONObject(pathology);
        PrintwriterUtil.print(response, pathMap.toString());
    }

    /**
     * 发送消息并创建消息
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/editSample*", method = RequestMethod.POST)
    public String editSample(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        JSONObject o = new JSONObject();
        String states = request.getParameter("states");
        String info = request.getParameter("info");
        if(states == null || states.equals("")){
            return null;
        }
        PimsPathologyMessage message = new PimsPathologyMessage();
        message.setMescustomerid(user.getHospitalId());//客户Id
        message.setMeslevel(Integer.parseInt(states));//消息级别(0系统级,1科室级,2工作站级,3用户级)
        message.setMescontent(info);//消息内容
        message.setMessenderid(String.valueOf(user.getId()));//发布用户Id
        message.setMessendername(user.getName());//发布用户姓名
        message.setMeshandletime(new Date());//处理时间
        message.setMescreatetime(new Date());//创建时间
        message.setMescreateuser(String.valueOf(user.getId()));//创建用户
        message = pimsPathologyMessageManager.save(message);
        if(states.equals("3")){
            String userId = request.getParameter("userid");
            String username = request.getParameter("username");
            PimsPathologyReceivemessage receivemessage = new PimsPathologyReceivemessage();
            receivemessage.setRecmessageid(message.getMessageid());//消息Id
            receivemessage.setReceiveruserid(userId);//接收人Id
            receivemessage.setReceiverusername(username);//接收人姓名
            receivemessage.setReceivests(0);//状态
            receivemessage.setReccreatetime(message.getMescreatetime());//创建时间
            receivemessage.setReccreateuser(message.getMescreateuser());//创建用户
            pimsPathologyReceivemessageManager.save(receivemessage);
        }
        o.put("success",true);
        PrintwriterUtil.print(response, o.toString());
        return  null;
    }

    @RequestMapping(value = "/ajax/item*", method = RequestMethod.GET)
    @ResponseBody
    public String getTestitemInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = request.getParameter("name")==null?"":request.getParameter("name").toString();
        Map map = new HashMap();
        map.put("name",name);
        map.put("hosptial",user.getHospitalId());
        List<User> list = pimsUserManager.getDataList(map);
//        JSONArray array = new JSONArray();
        StringBuilder builder = new StringBuilder();
        if (list != null) {
//            for (User s : list) {
//                org.codehaus.jettison.json.JSONObject o = new org.codehaus.jettison.json.JSONObject();
//                o.put("id", s.getId());
//                o.put("name", s.getName());
//                array.put(o);
//            }
            builder.append("<select id=\"user_id\">");
            for(User obj : list) {
                builder.append("<option value='").append(obj.getId()).append("' ");
                builder.append(">").append(obj.getName()).append("</option>");
            }
            builder.append("</select>");
        }
        response.setContentType("text/html; charset=UTF-8");
//        response.getWriter().write(array.toString());
       // response.getWriter().write(builder.toString());
        JSONObject o = new JSONObject();
        o.put("info",builder.toString());
        PrintwriterUtil.print(response, o.toString());
        return null;
    }
}
