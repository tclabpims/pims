package com.pims.webapp.controller;

import com.pims.model.PimsBaseModel;
import com.pims.model.PimsSysPathology;
import com.pims.service.pimspathologysample.*;
import com.pims.service.pimssyspathology.*;
import com.smart.Constants;
import com.smart.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by king on 2016/11/10.
 */
@Controller
@RequestMapping("/home")
public class HomeController extends PIMSBaseController{
    @Autowired
    private PimsPathologySampleManager pimsPathologySampleManager;//标本
    @Autowired
    private PimsPathologyConsultationManager pimsPathologyConsultationManager;//病理会诊
    @Autowired
    private PimsPathologyMessageManager pimsPathologyMessageManager;//发送的消息
    @Autowired
    private PimsPathologyReceivemessageManager pimsPathologyReceivemessageManager;//我的消息
    @Autowired
    private PimsConsultationDetailManager pimsConsultationDetailManager;//受邀会诊
    @Autowired
    private PimsPathologyPiecesManager pimsPathologyPiecesManager;//取材
    @Autowired
    private PimsPathologyParaffinManager pimsPathologyParaffinManager;//包埋
    @Autowired
    private PimsPathologySlideManager pimsPathologySlideManager;//切片
    @Autowired
    private PimsPathologyOrderManager pimsPathologyOrderManager;//医嘱
    @Autowired
    private PimsPathologyFavoriteManager pimsPathologyFavoriteManager;//收藏
    /**
     * 渲染视图
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView handleRequest(HttpServletRequest request) throws Exception {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, - 7);
        Date monday1 = c.getTime();
        String sevenDay1 = Constants.DF2.format(monday1);
        String today1 = Constants.DF2.format(new Date());
        PimsBaseModel map = new PimsBaseModel();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, - 7);
        Date monday = cal.getTime();
        String sevenDay = Constants.DF2.format(monday);
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_YEAR, +1);
        String today = Constants.DF2.format(cal.getTime());
        int noworkList = pimsPathologySampleManager.getSamStaNum();//工作未处理
        map.setReq_sts("3");
        map.setPatient_name(String.valueOf(user.getId()));
        int nocc = pimsPathologySampleManager.getSNum(map);//未初查
        map.setReq_sts("4");
        map.setPatient_name(String.valueOf(user.getId()));
        int nosh = pimsPathologySampleManager.getSNum(map);//未审核
        map.setReq_sts("6");
        map.setPatient_name(String.valueOf(user.getId()));
        int nody = pimsPathologySampleManager.getSNum(map);//未打印
        map.setReq_sts("5");
        map.setPatient_name(String.valueOf(user.getId()));
        int nofs = pimsPathologySampleManager.getSNum(map);//未发送
        map = new PimsBaseModel();
        map.setReq_sts("2");
        map.setPatient_name(String.valueOf(user.getId()));
        int noqs = pimsPathologyOrderManager.getOrderNum(map);//未签收
        map = new PimsBaseModel();
        map.setReq_sts("0");
        map.setReq_code("1");
        map.setPatient_name(String.valueOf(user.getId()));
        int nobq = pimsPathologyOrderManager.getOrderNum(map);//未补取
        map = new PimsBaseModel();
        map.setReq_sts("0");
        map.setReq_bf_time(new java.sql.Date((Constants.DF2.parse(sevenDay).getTime())));
        map.setReq_af_time(new java.sql.Date((Constants.DF2.parse(today)).getTime()));
        map.setPatient_name(String.valueOf(user.getId()));
        int mycons = pimsConsultationDetailManager.getReqListNum(map);//我的会诊
        map = new PimsBaseModel();
        map.setReq_sts("0");
        map.setReq_bf_time(new java.sql.Date((Constants.DF2.parse(sevenDay).getTime())));
        map.setReq_af_time(new java.sql.Date((Constants.DF2.parse(today)).getTime()));
        map.setPatient_name(String.valueOf(user.getId()));
        int mymessage = pimsPathologyReceivemessageManager.getTaskListNum(map);//我的留言
        map = new PimsBaseModel();
        map.setReq_sts("0");
        map.setReq_bf_time(new java.sql.Date((Constants.DF2.parse(sevenDay).getTime())));
        map.setReq_af_time(new java.sql.Date((Constants.DF2.parse(today)).getTime()));
        map.setPatient_name(String.valueOf(user.getId()));
        int mysendcons = pimsPathologyConsultationManager.getReqListNum(map);//发起的会诊
        map = new PimsBaseModel();
        map.setReq_bf_time(new java.sql.Date((Constants.DF2.parse(sevenDay).getTime())));
        map.setReq_af_time(new java.sql.Date((Constants.DF2.parse(today)).getTime()));
        map.setPatient_name(String.valueOf(user.getId()));
        int mysendmessage = pimsPathologyMessageManager.getTaskListNum(map);//发起的留言
        map = new PimsBaseModel();
        map.setReq_code("0");
        int nosyscq = pimsPathologySampleManager.getReqListNum(map);//系统未取材
        map = new PimsBaseModel();
        map.setReq_sts("0");
        int nosysbm = pimsPathologyParaffinManager.getReqListNum(map);;//系统未包埋
        map = new PimsBaseModel();
        map.setReq_sts("0");
        int nosysqp = pimsPathologySlideManager.getReqListNum(map);//系统未切片
        map = new PimsBaseModel();
        map.setReq_sts("2");
        int nosyszp = pimsPathologySlideManager.getProducerSampleListNum(map);//系统未制片
        map = new PimsBaseModel();
        map.setReq_sts("3");
        int nosyscc = pimsPathologySampleManager.getSNum(map);//系统未初查
        map.setReq_sts("4");
        int nosyssh = pimsPathologySampleManager.getSNum(map);//系统未审核
        map.setReq_sts("6");
        int nosysdy = pimsPathologySampleManager.getSNum(map);//系统未打印
        map.setReq_sts("5");
        int nosysfs = pimsPathologySampleManager.getSNum(map);//系统未发送
        map.setReq_sts("2");
        int nosyswc = pimsPathologyOrderManager.getOrderNum(map);//系统未完成
        map.setReq_sts("2");
        int nosysqs = pimsPathologyOrderManager.getOrderNum(map);//系统未签收
        map.setReq_sts("0");
        map.setReq_code("0");
        int nosysjs = pimsPathologyOrderManager.getOrderNum(map);//系统未接收
        map.setReq_sts("0");
        map.setReq_code("1");
        int nosysbq = pimsPathologyOrderManager.getOrderNum(map);//系统未补取



        ModelAndView view = new ModelAndView();
        view.addObject("sevenday", sevenDay1);//7天前
        view.addObject("receivetime", today1);//当前时间
        view.addObject("local_username",user.getName());//当前登录用户名
        view.addObject("local_userid",user.getId());//当前登录用户id
        view.addObject("send_hosptail",user.getHospitalId());//账号所属医院
        view.addObject("noworkList",noworkList);//工作未处理
        view.addObject("nocc",nocc);//未初查
        view.addObject("nosh",nosh);//未审核
        view.addObject("nody",nody);//未打印
        view.addObject("nofs",nofs);//未发送
        view.addObject("noqs",noqs);//未签收
        view.addObject("noqc",nobq);//未取材
        view.addObject("mycons",mycons);//我的会诊
        view.addObject("mymessage",mymessage);//我的消息
        view.addObject("mysendcons",mysendcons);//发起的会诊
        view.addObject("mysendmessage",mysendmessage);//发起的消息

        view.addObject("nosyscq",nosyscq);//系统未取材
        view.addObject("nosysbm",nosysbm);//系统未包埋
        view.addObject("nosysqp",nosysqp);//系统未切片
        view.addObject("nosyszp",nosyszp);//系统未制片
        view.addObject("nosyscc",nosyscc);//系统未初查
        view.addObject("nosyssh",nosyssh);//系统未审核
        view.addObject("nosysdy",nosysdy);//系统未打印
        view.addObject("nosysfs",nosysfs);//系统未发送
        view.addObject("nosyswc",nosyswc);//系统未完成
        view.addObject("nosysqs",nosysqs); //系统未签收
        view.addObject("nosysjs",nosysjs); //系统未接收
        view.addObject("nosysbq",nosysbq); //系统未补取

        view.addObject("myFavorite", pimsPathologyFavoriteManager.myFavorite("0"));
        view.addObject("myFollow", pimsPathologyFavoriteManager.myFavorite("1"));
        return view;
    }

}
