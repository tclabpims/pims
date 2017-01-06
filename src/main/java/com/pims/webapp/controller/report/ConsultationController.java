//package com.pims.webapp.controller.report;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.pims.dao.report.ConsultationDao;
//import com.pims.model.PimsBaseModel;
//import com.pims.model.PimsPathologyRequisition;
//import com.pims.model.PimsPathologySample;
//import com.pims.model.PimsSysPathology;
//import com.alibaba.fastjson.serializer.ValueFilter;
//import com.pims.model.*;
//import com.pims.service.QueryHisDataService;
//import com.pims.service.basedata.PimsCommonBaseDataManager;
//import com.pims.service.his.PimsPathologyRequisitionManager;
//import com.pims.service.pimspathologysample.*;
//import com.pims.service.pimssyspathology.PimsSysPathologyManager;
//import com.pims.service.pimssyspathology.PimsHospitalPathologyInfoManager;
//import com.pims.service.pimssyspathology.PimsSysTestFeeManager;
//import com.pims.service.pimssysreqtestitem.PimsSysReqTestitemManager;
//import com.pims.service.report.ConsultationManager;
//import com.pims.webapp.controller.PIMSBaseController;
//import com.smart.Constants;
//import com.smart.lisservice.WebService;
//import com.smart.model.user.User;
//import com.smart.webapp.util.DataResponse;
//import com.smart.webapp.util.PrintwriterUtil;
//import com.smart.webapp.util.UserUtil;
//import org.apache.commons.collections.map.HashedMap;
//import org.apache.commons.lang.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
///**
// * Created by zp on 2016/12/21.
// */
//@Controller
//@RequestMapping("/report/consultation")
//public class ConsultationController extends PIMSBaseController {
//    @Autowired
//    private PimsPathologyRequisitionManager pimsPathologyRequisitionManager;
//    @Autowired
//    private PimsPathologySampleManager pimsPathologySampleManager;
//    @Autowired
//    private PimsSysPathologyManager pimsSysPathologyManager;
//    @Autowired
//    private PimsHospitalPathologyInfoManager pimsHospitalPathologyInfoManager;
//    @Autowired
//    private PimsPathologyFeeManager pimsPathologyFeeManager;
//    @Autowired
//    private PimsSysTestFeeManager pimsSysTestFeeManager;
//    @Autowired
//    private PimsSysReqTestitemManager pimsSysReqTestitemManager;//检查项目
//    @Autowired
//    private PimsCommonBaseDataManager pimsCommonBaseDataManager;//基础资料
//    @Autowired
//    private QueryHisDataService dataService;
//    @Autowired
//    private PimsPathologyPiecesManager pimsPathologyPiecesManager;//取材
//    @Autowired
//    private PimsPathologyParaffinManager pimsPathologyParaffinManager;//包埋
//    @Autowired
//    private PimsPathologySlideManager pimsPathologySlideManager;//制片
//    @Autowired
//    private ConsultationDao consultationDao;
//    @Autowired
//    private ConsultationManager consultationManager;
//    /**
//     * 渲染视图
//     * @param request
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping(method = RequestMethod.GET)
//    public ModelAndView handleRequest(HttpServletRequest request) throws Exception {
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        long hosptail = user.getHospitalId();
//        ModelAndView view = getmodelView(request);
//        //检查项目
//        Map map = new HashMap();
//        map.put("tesitemtype","4");
//        List<PimsSysReqTestitem> list = pimsSysReqTestitemManager.getTestitemInfo(map);
//        StringBuilder builder = new StringBuilder();
//        builder.append("<option value=''></option>");
//        for(PimsSysReqTestitem obj : list) {
//            builder.append("<option value='").append(obj.getTestitemid()).append("' ");
//            builder.append(">").append(obj.getTespathologyid()+":"+obj.getTeschinesename()).append("</option>");
//        }
//        view.addObject("samjcxm",builder.toString());
//        //送检医院
//        map = new HashMap();
//        map.put("bdcustomerid",hosptail);
//        map.put("bddatatype",4);
//        List<PimsCommonBaseData> listbase = pimsCommonBaseDataManager.getDataList(map);
//        view.addObject("samsendhospital",getOptions(listbase).toString());
//        //送检科室
//        map.put("bddatatype",2);
//        listbase = pimsCommonBaseDataManager.getDataList(map);
//        view.addObject("samdeptname",getOptions(listbase).toString());
//        //送检医生
//        map.put("bddatatype",3);
//        listbase = pimsCommonBaseDataManager.getDataList(map);
//        view.addObject("samsenddoctorname",getOptions(listbase).toString());
//        return view;
//    }
//
//    /**
//     * 获取标本列表
//     * @param request
//     * @param response
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping(value = "/ajax/slide*", method = RequestMethod.GET)
//    @ResponseBody
//    public DataResponse getRequisitionInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        DataResponse dataResponse = new DataResponse();
//        PimsBaseModel ppr = new PimsBaseModel(request);
//        Map map = new HashedMap();
//        map.put("conconsultationstate",request.getParameter("conconsultationstate"));
//        map.put("sampathologyid",request.getParameter("sampathologyid"));
//        map.put("sampathologycode",request.getParameter("sampathologycode"));
//        map.put("sampatientname",request.getParameter("sampatientname"));
//        map.put("comsponsoredtime",request.getParameter("comsponsoredtime"));
//        map.put("confinishedtime",request.getParameter("confinishedtime"));
//        map.put("samsendhospital",request.getParameter("samsendhospital"));
//        map.put("samdeptname",request.getParameter("samdeptname"));
//        map.put("samsenddoctorname",request.getParameter("samsenddoctorname"));
//        map.put("consponsoredusername",request.getParameter("consponsoredusername"));
//        map.put("detdoctorname",request.getParameter("detdoctorname"));
//        //map.put("samre",request.getParameter("samre"));
//        List<ViewConsultationQuery> list = consultationManager.getConsultationList(map);
//        int num = consultationManager.getReqListNum(map);
//        if(list == null || list.size() == 0 ) {
//            return null;
//        }
//        dataResponse.setRecords(num);
//        dataResponse.setPage(ppr.getPage());
//        dataResponse.setTotal(getTotalPage(num, ppr.getRow(), ppr.getPage()));
//        dataResponse.setRows(getResultMap(list));
//        response.setContentType("text/html; charset=UTF-8");
//        return dataResponse;
//    }
//}
//
