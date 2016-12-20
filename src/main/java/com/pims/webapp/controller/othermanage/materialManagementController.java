package com.pims.webapp.controller.othermanage;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.pims.model.PimsBaseModel;
import com.pims.model.PimsDisposableMaterial;
import com.pims.model.PimsSlideLoan;
import com.pims.service.othermanagement.PimsMaterialManager;
import com.pims.webapp.controller.PIMSBaseController;
import com.pims.webapp.controller.WebControllerUtil;
import com.smart.Constants;
import com.smart.lisservice.WebService;
import com.smart.model.user.User;
import com.smart.webapp.util.DataResponse;
import com.smart.webapp.util.PrintwriterUtil;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * Created by zp on 2016/12/16.
 */
@Controller
@RequestMapping("/othermanage/materialManagement")
public class materialManagementController extends PIMSBaseController{
    @Autowired
    private PimsMaterialManager pimsMaterialManager;

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
//        view.addObject("local_userid",user.getId());//用户id
//        view.addObject("local_username",user.getName());//用户姓名
//        return view;
        User user = WebControllerUtil.getAuthUser();
        Date dd = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String now = sdf.format(new Date());
        view.addObject("logintime",now);
        view.addObject("user",user.getUsername());
        return view;
    }



    /**
     * 获取标本列表
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/ajax/mar*", method = RequestMethod.GET)
    @ResponseBody
    public DataResponse getRequisitionInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DataResponse dataResponse = new DataResponse();
        PimsBaseModel ppr = new PimsBaseModel(request);
        List<PimsDisposableMaterial> list = pimsMaterialManager.getMarList(ppr);
        int num = pimsMaterialManager.getReqListNum(ppr);
        if(list == null || list.size() == 0 ) {
            return null;
        }
        dataResponse.setRecords(num);
        dataResponse.setPage(ppr.getPage());
        dataResponse.setTotal(getTotalPage(num, ppr.getRow(), ppr.getPage()));
        dataResponse.setRows(getResultMap(list));
        response.setContentType("text/html; charset=UTF-8");
        return dataResponse;
    }

    @RequestMapping(value = "/ajax/delete*", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json; charset=utf-8")
    @ResponseBody
    public void deleteMar(HttpServletRequest request,HttpServletResponse response) throws Exception{
        Map map = new HashedMap();
        map.put("marid",request.getParameter("marid"));
        JSONObject jj = new JSONObject();
        pimsMaterialManager.deleteMar(map);
        jj.put("message","删除成功！");
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(jj.toString());
//        JSONObject jj = new JSONObject();
//        jj.put("message","删除成功！");
//        response.setContentType("application/json;charset=utf-8");
//        response.getWriter().write(jj.toString());
    }

    @RequestMapping(value = "/ajax/save*", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json; charset=utf-8")
    @ResponseBody
    public void saveMar(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PimsDisposableMaterial ppr = new PimsDisposableMaterial();
        Map map = new HashedMap();
        map.put("marname",request.getParameter("marname"));
        map.put("loginintime",request.getParameter("loginintime"));
        map.put("manufacter",request.getParameter("manufacter"));
        map.put("marishas",request.getParameter("marishas"));
        map.put("remarks",request.getParameter("remarks"));
        String marid = pimsMaterialManager.sampleCode();
        User user = WebControllerUtil.getAuthUser();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String now = sdf.format(new Date());
        System.out.println("当前时间为:"+now);
        String a = null;
        String b = null;
        if(marid!=null){
             a = marid.substring(0,8);
             b = now.substring(0,8);
            if(a.equals(b)){
                b = b+String.format("%02d",(Long.parseLong(marid.substring(9,10))+1));
                System.out.println("当前时间为:"+b);
            }else {
                b = b+String.format("%02d",1);
                System.out.println("当前时间为:"+b);
            }
        }else {
            b = sdf.format(new Date())+String.format("%02d",1);
            System.out.println("当前时间为:"+b);
        }
        ppr.setMarname((String)map.get("marname"));
        ppr.setLoginintime(new Date());
        ppr.setLoginuser(user.getUsername());
        ppr.setRemarks((String)map.get("remarks"));
        ppr.setMarid(b);
        ppr.setManufacter((String)map.get("manufacter"));
        ppr.setMarishas((String)map.get("marishas"));
        pimsMaterialManager.save(ppr);
        JSONObject jj = new JSONObject();
        jj.put("message","保存成功！");
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(jj.toString());
    }

    private ValueFilter filter = new ValueFilter() {
        @Override
        public Object process(Object obj, String s, Object v) {
            if (v == null)
                return "";
            return v;
        }
    };

}
