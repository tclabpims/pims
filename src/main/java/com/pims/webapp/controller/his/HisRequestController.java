package com.pims.webapp.controller.his;

import com.pims.model.PimsCommonBaseData;
import com.pims.model.PimsSysPathology;
import com.pims.model.PimsSysReqTestitem;
import com.pims.service.basedata.PimsCommonBaseDataManager;
import com.pims.service.his.PimsPathologyRequisitionManager;
import com.pims.service.pimssyspathology.PimsHospitalPathologyInfoManager;
import com.pims.service.pimssyspathology.PimsSysPathologyManager;
import com.pims.service.pimssysreqtestitem.PimsSysReqTestitemManager;
import com.pims.webapp.controller.GridQuery;
import com.pims.webapp.controller.PIMSBaseController;
import com.smart.Constants;
import com.smart.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by king on 2016/9/28.
 */
@Controller
//@RequestMapping("/his/his_main*")
@RequestMapping("/his/hisrequest*")
public class HisRequestController extends PIMSBaseController {
    @Autowired
    private PimsSysPathologyManager pimsHospitalPathologyInfoManager;
    @Autowired
    private PimsSysReqTestitemManager pimsSysReqTestitemManager;//检查项目
    @Autowired
    private PimsPathologyRequisitionManager pimsPathologyRequisitionManager;
    @Autowired
    private PimsCommonBaseDataManager pimsCommonBaseDataManager;//基础资料
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView handleRequest(HttpServletRequest request) throws Exception {
        String hosptail = request.getParameter("hosptail");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, - 7);
        Date monday = c.getTime();
        String sevenDay = Constants.DF2.format(monday);
        String today = Constants.DF2.format(new Date());
        List<PimsSysReqTestitem> list = pimsSysReqTestitemManager.getTestitemInfo(null);//查询申请项目列表
        int nowyear = Calendar.getInstance().get(Calendar.YEAR);//获取当前年份
        String requisitionno = "";
        String maxCode = pimsPathologyRequisitionManager.getMaxCode(null);
        if(maxCode == null || !maxCode.contains(String.valueOf(nowyear))){
            requisitionno = nowyear + "00000001";
        }else if(maxCode.contains(String.valueOf(nowyear))){
            long a = Long.valueOf(maxCode).longValue() + 1;
            requisitionno =  String.valueOf(a);
        }
        List<PimsSysPathology> items = pimsHospitalPathologyInfoManager.getAll();
        StringBuilder builder = new StringBuilder();
        for(PimsSysPathology obj : items) {
            builder.append("<option value='").append(obj.getPathologyid()).append("' ");
            builder.append(">").append(obj.getPatnamech()).append("</option>");
        }
        ModelAndView view = new ModelAndView();
        view.addObject("logyids",builder.toString());
        //检查项目
        Map map = new HashMap();
        map.put("tesitemtype","4");
        List<PimsSysReqTestitem> list1 = pimsSysReqTestitemManager.getTestitemInfo(map);
        builder = new StringBuilder();
        builder.append("<option value=''></option>");
        for(PimsSysReqTestitem obj : list1) {
            builder.append("<option value='").append(obj.getTestitemid()).append("' ");
            builder.append(">").append(obj.getTespathologyid()+":"+obj.getTeschinesename()).append("</option>");
        }
        view.addObject("samjcxm",builder.toString());
        //送检医院
        map = new HashMap();
        map.put("bdcustomerid",hosptail);
        map.put("bddatatype",4);
        List<PimsCommonBaseData> listbase = pimsCommonBaseDataManager.getDataList(map);
        view.addObject("samsendhospital",getOptions(listbase).toString());
        //送检科室
        map.put("bddatatype",2);
        listbase = pimsCommonBaseDataManager.getDataList(map);
        view.addObject("samdeptname",getOptions(listbase).toString());
        //送检医生
        map.put("bddatatype",3);
        listbase = pimsCommonBaseDataManager.getDataList(map);
        view.addObject("samsenddoctorname",getOptions(listbase).toString());
        //病区
        map.put("bddatatype",1);
        listbase = pimsCommonBaseDataManager.getDataList(map);
        view.addObject("reqwardname",getOptions(listbase).toString());
        view.addObject("requisitionno",requisitionno);//申请单号
        view.addObject("sevenday", sevenDay);//7天前
        view.addObject("receivetime", today);//当前时间
        view.addObject("reqcustomerid",hosptail);//账号所属医院
        view.addObject("reqsource",0);//申请单来源
        view.addObject("testList",getResultMap(list));//申请项目列表
        view.addObject("local_user",hosptail);//用户姓名
        view.addObject("local_userid",hosptail);//用户ID
        return view;
    }
}
