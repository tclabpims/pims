package com.pims.webapp.controller.his;

import com.pims.model.PimsCommonBaseData;
import com.pims.model.PimsSysPathology;
import com.pims.model.PimsSysReqTestitem;
import com.pims.service.basedata.PimsCommonBaseDataManager;
import com.pims.service.his.PimsPathologyRequisitionManager;
import com.pims.service.pimssyspathology.PimsHospitalPathologyInfoManager;
import com.pims.service.pimssyspathology.PimsSysPathologyManager;
import com.pims.service.pimssysreqtestitem.PimsSysReqTestitemManager;
import com.pims.webapp.controller.PIMSBaseController;
import com.smart.Constants;
import com.smart.model.user.User;
import com.smart.model.user.UserBussinessRelate;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by king on 2016/9/28.
 */
@Controller
//@RequestMapping("/his/his_main*")
@RequestMapping("/his/his_main*")
public class HisMainController extends PIMSBaseController {
    @Autowired
    private PimsSysPathologyManager pimsSysPathologyManager;
    @Autowired
    private PimsHospitalPathologyInfoManager pimsHospitalPathologyInfoManager;
    @Autowired
    private PimsSysReqTestitemManager pimsSysReqTestitemManager;//检查项目
    @Autowired
    private PimsPathologyRequisitionManager pimsPathologyRequisitionManager;
    @Autowired
    private PimsCommonBaseDataManager pimsCommonBaseDataManager;//基础资料
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView handleRequest(HttpServletRequest request) throws Exception {
//        ModelAndView view = new ModelAndView();
//        Calendar c = Calendar.getInstance();
//        c.add(Calendar.DATE, - 7);
//        Date monday = c.getTime();
//        String sevenDay = Constants.DF2.format(monday);
//        String today = Constants.DF2.format(new Date());
//        User user = SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")?new User():
//                (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        Long logylibid = user.getUserBussinessRelate()==null?(StringUtils.isEmpty(request.getParameter("logylibid"))?185:Long.parseLong(request.getParameter("logylibid"))):
//                user.getUserBussinessRelate().getPathologyLibId();//病种库
//        List<PimsSysReqTestitem> list = pimsSysReqTestitemManager.getTestitemInfo(null);//查询申请项目列表
//        int nowyear = Calendar.getInstance().get(Calendar.YEAR);//获取当前年份
//        String requisitionno = "";
//        String maxCode = pimsPathologyRequisitionManager.getMaxCode(null);
//        if(maxCode == null || !maxCode.contains(String.valueOf(nowyear))){
//            requisitionno = nowyear + "00000001";
//        }else if(maxCode.contains(String.valueOf(nowyear))){
//            long a = Long.valueOf(maxCode).longValue() + 1;
//            requisitionno =  String.valueOf(a);
//        }
//        List<PimsSysPathology> items = pimsHospitalPathologyInfoManager.getPathologyByUserId(user.getId()==null?124:user.getId());
//        StringBuilder builder = new StringBuilder();
//        for(PimsSysPathology obj : items) {
//            builder.append("<option value='").append(obj.getPathologyid()).append("' ");
//            if((String.valueOf(logylibid)).equals(String.valueOf(obj.getPathologyid()))) {
//                builder.append(" selected = 'selected' ");
//            }
//            builder.append(">").append(obj.getPatnamech()).append("</option>");
//        }
//        view.addObject("logyids",builder.toString());
//        long hosptail = user.getHospitalId()==0?1:user.getHospitalId();
//        //检查项目
//        Map map = new HashMap();
//        map.put("tesitemtype","4");
//        List<PimsSysReqTestitem> list1 = pimsSysReqTestitemManager.getTestitemInfo(map);
//        builder = new StringBuilder();
//        builder.append("<option value=''></option>");
//        for(PimsSysReqTestitem obj : list1) {
//            builder.append("<option value='").append(obj.getTestitemid()).append("' ");
//            if((String.valueOf(logylibid)).equals(String.valueOf(obj.getTespathologyid()))) {
//                builder.append(" selected = 'selected' ");
//                view.addObject("reqitemids",obj.getTestitemid());
//                view.addObject("reqitemnames",obj.getTeschinesename());
//            }
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
//        //病区
//        map.put("bddatatype",1);
//        listbase = pimsCommonBaseDataManager.getDataList(map);
//        view.addObject("reqwardname",getOptions(listbase).toString());
//        view.addObject("requisitionno",requisitionno);//申请单号
//        view.addObject("sevenday", sevenDay);//7天前
//        view.addObject("receivetime", today);//当前时间
//        view.addObject("reqcustomerid",hosptail);//账号所属医院
//        view.addObject("reqpathologyid",logylibid);//当前用户选择的病例库
//        view.addObject("reqsource",0);//申请单来源
//        view.addObject("testList",getResultMap(list));//申请项目列表
//
//        view.addObject("local_user",user.getName());//用户姓名
//        view.addObject("local_userid",user.getId());//用户ID
//
//        return view;
        String hosptail = request.getParameter("hosptail");//申请医院
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
        User user = SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")?new User():
        (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long logylibid = user.getUserBussinessRelate()==null?(StringUtils.isEmpty(request.getParameter("logylibid"))?185:Long.parseLong(request.getParameter("logylibid"))):
                user.getUserBussinessRelate().getPathologyLibId();//病种库
//        List<PimsSysPathology> items = pimsSysPathologyManager.getAll();
        List<PimsSysPathology> items = pimsHospitalPathologyInfoManager.getPathologyByUserId(user.getId()==null?124:user.getId());
        StringBuilder builder = new StringBuilder();
        for(PimsSysPathology obj : items) {
            builder.append("<option value='").append(obj.getPathologyid()).append("' ");
            if((String.valueOf(logylibid)).equals(String.valueOf(obj.getPathologyid()))) {
                builder.append(" selected = 'selected' ");
            }
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
            if((String.valueOf(logylibid)).equals(String.valueOf(obj.getTespathologyid()))) {
                builder.append(" selected = 'selected' ");
                view.addObject("reqitemids",obj.getTestitemid());
                view.addObject("reqitemnames",obj.getTeschinesename());
            }
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

        String brjzxh = request.getParameter("brjzxh");//门诊唯一号
        String patient_type = request.getParameter("patient_type");//患者类型:1 住院 2 门诊
        String lczdmc = request.getParameter("lczdmc");//临床诊断
        String reqdoctorid = request.getParameter("reqdoctorid");//申请医生ID
        String reqdoctorname = request.getParameter("reqdoctorname");//申请医生姓名
        String reqwardcode = request.getParameter("reqwardcode");//申请病区ID
        String reqwardname = request.getParameter("reqwardname");//申请病区名称
        String reqdeptcode = request.getParameter("reqdeptcode");//申请科室ID
        String reqdeptname = request.getParameter("reqdeptname");//申请科室名称
        String reqsendhospital = request.getParameter("reqsendhospital");//送检医院
        String reqcreateuser = request.getParameter("reqcreateuser");//申请人ID
        String reqcreateusername = request.getParameter("reqcreateusername");//申请人姓名

        if(!(StringUtils.isEmpty(reqdeptcode) || StringUtils.isEmpty(reqdeptname))){
            //送检科室
            map.put("bddatatype",2);
            map.put("bdhisid",reqdeptcode);//HIS ID
            map.put("bddatanamech",reqdeptname);//名称
            listbase = pimsCommonBaseDataManager.getDataList(map);
            if(listbase != null && listbase.size() > 0){
                view.addObject("reqdeptcode1",listbase.get(0).getDataid());
            }
            view.addObject("reqdeptname1",reqdeptname);
        }
        //送检医生
        if(!(StringUtils.isEmpty(reqdoctorid) || StringUtils.isEmpty(reqdoctorname))) {
            map.put("bddatatype", 3);
            map.put("bdhisid", reqdoctorid);//HIS ID
            map.put("bddatanamech", reqdoctorname);//名称
            listbase = pimsCommonBaseDataManager.getDataList(map);
            if (listbase != null && listbase.size() > 0) {
                view.addObject("reqdoctorid1", listbase.get(0).getDataid());
            }
            view.addObject("reqdoctorname1", reqdoctorname);
        }
        //病区
        if(!(StringUtils.isEmpty(reqwardcode) || StringUtils.isEmpty(reqwardname))) {
            map.put("bddatatype", 1);
            map.put("bdhisid", reqwardcode);//HIS ID
            map.put("bddatanamech", reqwardname);//名称
            listbase = pimsCommonBaseDataManager.getDataList(map);
            if (listbase != null && listbase.size() > 0) {
                view.addObject("reqwardcode1", listbase.get(0).getDataid());
            }
            view.addObject("reqwardname1", reqwardname);
        }
        view.addObject("requisitionno",requisitionno);//申请单号
        view.addObject("reqpathologyid",logylibid);//当前用户选择的病例库
        view.addObject("reqcustomerid",hosptail);//账号所属医院
        view.addObject("reqsource",0);//申请单来源
        view.addObject("testList",getResultMap(list));//申请项目列表
        view.addObject("local_user",reqcreateuser+"_"+hosptail);//用户姓名
        view.addObject("local_userid",reqcreateuser+"_"+hosptail);//用户ID

        view.addObject("lczdmc",lczdmc);//临床诊断
        view.addObject("reqsendhospital1",reqsendhospital);//送检医院
        view.addObject("brjzxh",brjzxh);//门诊唯一号
        view.addObject("patient_type",patient_type);//患者类型:1 住院 2 门诊
        return view;
    }
}
