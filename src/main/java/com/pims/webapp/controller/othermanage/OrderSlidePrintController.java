package com.pims.webapp.controller.othermanage;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.pims.model.PimsBaseModel;
import com.pims.model.PimsSlideLoan;
import com.pims.model.PimsSlideRecord;
import com.pims.service.othermanagement.OrderSlidePrintManager;
import com.pims.service.othermanagement.PimsSlideLoanManager;
import com.pims.service.othermanagement.PimsSlideLoanRecordManager;
import com.pims.webapp.controller.PIMSBaseController;
import com.pims.webapp.controller.WebControllerUtil;
import com.smart.Constants;
import com.smart.lisservice.WebService;
import com.smart.model.user.User;
import com.smart.webapp.util.DataResponse;
import com.smart.webapp.util.PrintwriterUtil;
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
import java.util.*;

@Controller
@RequestMapping("/othermanage/sample")
public class OrderSlidePrintController extends PIMSBaseController {
    @Autowired
    private OrderSlidePrintManager orderSlidePrintManager;

    /**
     * 渲染视图
     *
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
//        ModelAndView view = new ModelAndView();
//        view.addObject("logyid",logylibid);//当前用户选择的病例库
//        view.addObject("sevenday", sevenDay);//7天前
//        view.addObject("receivetime", today);//当前时间
//        view.addObject("send_hosptail",user.getHospitalId());//账号所属医院
//        view.addObject("local_userid",user.getId());//用户id
//        view.addObject("local_username",user.getName());//用户姓名
//        return view;
        return getmodelView(request);
    }

    /**
     * 获取标本列表
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/ajax/slide*", method = RequestMethod.GET)
    @ResponseBody
    public DataResponse getRequisitionInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DataResponse dataResponse = new DataResponse();
        Map map =new HashedMap();
        PimsBaseModel ppr = new PimsBaseModel(request);
        map.put("cheitemtype",request.getParameter("cheitemtype"));
        map.put("sliifprint",request.getParameter("sliifprint"));
        map.put("slipathologycode",request.getParameter("slipathologycode"));
        map.put("chirequsername",request.getParameter("chirequsername"));
        map.put("sampatientname",request.getParameter("sampatientname"));
        map.put("applybftime",(request.getParameter("applybftime")== null|| request.getParameter("applybftime").equals(""))?null:new java.sql.Date(Constants.DF2.parse(request.getParameter("applybftime")).getTime()));
        map.put("applyaftime",(request.getParameter("applyaftime")== null|| request.getParameter("applyaftime").equals(""))?null:new java.sql.Date(Constants.DF2.parse(request.getParameter("applyaftime")).getTime()));
        map.put("sidx",ppr.getSidx());
        List list = orderSlidePrintManager.getLoanList(map);
        int num = orderSlidePrintManager.getReqListNum(map);
        if (list == null || list.size() == 0) {
            return null;
        }
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        if(list == null || list.size() == 0) {
            return null;
        }else{
            for(Object bean:list){
                Map<String, Object> map1 = new HashMap<String, Object>();
                Object[] pd = (Object[]) bean;
                String[] st = {"sampatientname","slipathologycode","sliparaffincode","sliifprint",
                        "chireqtime","chirequserid","chirequsername","cheorderitemid","chenamech","cheitemtype"};
                for(int i=0;i<st.length;i++){
                    Object o = pd[i];
                    map1.put(st[i],o);
                }
                mapList.add(map1);
            }
        }
        dataResponse.setRecords(num);
        dataResponse.setPage(ppr.getPage());
        dataResponse.setTotal(getTotalPage(num, ppr.getRow(), ppr.getPage()));
        dataResponse.setRows(mapList);
        response.setContentType("text/html; charset=UTF-8");
        return dataResponse;
    }

    @RequestMapping(value = "/ajax/total*", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getTotal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map map = new HashedMap();
        map.put("sliparaffincode",request.getParameter("sliparaffincode[]"));
        String  num = orderSlidePrintManager.getTotal(map);
        return num;
    }
}