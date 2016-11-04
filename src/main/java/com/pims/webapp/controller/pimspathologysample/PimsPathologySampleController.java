package com.pims.webapp.controller.pimspathologysample;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pims.model.PimsBaseModel;
import com.pims.model.PimsPathologyRequisition;
import com.pims.model.PimsPathologySample;
import com.pims.model.PimsSysPathology;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.pims.model.*;
import com.pims.service.his.PimsPathologyRequisitionManager;
import com.pims.service.pimspathologysample.PimsPathologyFeeManager;
import com.pims.service.pimspathologysample.PimsPathologySampleManager;
import com.pims.service.pimssyspathology.PimsSysPathologyManager;
import com.pims.service.pimssyspathology.PimsHospitalPathologyInfoManager;
import com.pims.service.pimssyspathology.PimsSysTestFeeManager;
import com.pims.webapp.controller.PIMSBaseController;
import com.smart.Constants;
import com.smart.lisservice.WebService;
import com.smart.model.user.User;
import com.smart.webapp.util.DataResponse;
import com.smart.webapp.util.PrintwriterUtil;
import com.smart.webapp.util.UserUtil;
import org.apache.commons.lang.StringUtils;
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
 * Created by king on 2016/10/10.
 */
@Controller
@RequestMapping("/pathologysample/sample")
public class PimsPathologySampleController extends PIMSBaseController{
    @Autowired
    private PimsPathologyRequisitionManager pimsPathologyRequisitionManager;
    @Autowired
    private PimsPathologySampleManager pimsPathologySampleManager;
    @Autowired
    private PimsSysPathologyManager pimsSysPathologyManager;
    @Autowired
    private PimsHospitalPathologyInfoManager pimsHospitalPathologyInfoManager;
    @Autowired
    private PimsPathologyFeeManager pimsPathologyFeeManager;
    @Autowired
    private PimsSysTestFeeManager pimsSysTestFeeManager;
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
     * 获取单据详细信息
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/get*", method = RequestMethod.GET)
    public void getsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String code = request.getParameter("id");
        PimsPathologySample pathology = pimsPathologySampleManager.get(Long.parseLong(code));
        PimsSysPathology psp = pimsSysPathologyManager.get(pathology.getSampathologyid());
        JSONObject pathMap = getJSONObject(pathology);
        pathMap.put("patIsSampling",psp.getPatissampling());
        pathMap.put("specialCheck",psp.getPatisspecialcheck());
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
        List<PimsPathologySample> list = pimsPathologySampleManager.getSampleList(ppr);
        int num = pimsPathologySampleManager.getReqListNum(ppr);
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
     * 获取申请单列表
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/ajax/getreqinfo*", method = RequestMethod.GET)
    @ResponseBody
    public DataResponse getListByReqId(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DataResponse dataResponse = new DataResponse();
        PimsBaseModel ppr = new PimsBaseModel();
        int row = Integer.parseInt(request.getParameter("rows"));
        int page = Integer.parseInt(request.getParameter("page"));
        ppr.setPage(page);
        ppr.setRow(row);
        ppr.setStart( row * (page - 1));
        ppr.setEnd( row * page);
        ppr.setSord(request.getParameter("sord"));
        ppr.setReq_sts("0");
        String code = request.getParameter("reqId");
        List<PimsPathologyRequisition> list = pimsPathologyRequisitionManager.getRequisitionInfo(ppr);
        if(list == null || list.size() == 0) {
            return null;
        }
        int num = pimsPathologyRequisitionManager.getReqListNum(ppr);
        dataResponse.setRecords(num);
        dataResponse.setPage(ppr.getPage());
        dataResponse.setTotal(getTotalPage(num, ppr.getRow(), ppr.getPage()));
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
        if(pimsPathologySampleManager.canChange(Long.parseLong(code),sts)){
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
    @RequestMapping(value = "/editSample*", method = RequestMethod.POST)
    public void editSample(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PimsPathologySample ppr = (PimsPathologySample)setBeanProperty(request,PimsPathologySample.class);
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        JSONObject o = new JSONObject();
        if(StringUtils.isEmpty(String.valueOf(ppr.getSampleid())) || String.valueOf(ppr.getSampleid()).equals("0")){
            //获取条码信息
            String samplecode = pimsPathologySampleManager.sampleCode();
            if(samplecode == null){
                samplecode = "A12000230001";
            }else{
                samplecode = samplecode.substring(0,1)+(Long.parseLong(samplecode.substring(1))+1);
            }
            //查询客户病理编号生成规则
            PimsHospitalPathologyInfo phi = pimsHospitalPathologyInfoManager.gethinfo(ppr);
            phi = searchCodeValue(phi);
            ppr.setSampathologycode(phi.getNumberPrefix()+phi.getNextNumber());
            ppr.setSaminspectionid(samplecode);
            ppr = pimsPathologySampleManager.save(ppr);
            pimsHospitalPathologyInfoManager.save(phi);//更新病理编号最大值

            //更新电子申请单已被使用
            pimsPathologyRequisitionManager.updateReqState(ppr,1);
            //增加收费项目
            if(ppr.getSampopuser() != null && !ppr.getSampopuser().equals("")){
                List list = pimsSysTestFeeManager.getTestFeeList(ppr.getSampopuser());
                if(list != null && list.size()>0){
                    for(Object oo:list){
                        Object[] ob = (Object[]) oo;
                        PimsSysChargeItems psc = (PimsSysChargeItems) ob[0];
                        PimsSysChargeitemRef pscr = (PimsSysChargeitemRef) ob[1];
                        PimsPathologyFee pp = new PimsPathologyFee();
                        pp.setFeecustomerid(ppr.getSamcustomerid());//客户Id
                        pp.setFeesampleid(ppr.getSampleid());//标本号
                        pp.setFeepathologyid(ppr.getSampathologyid());//病种Id
                        pp.setFeepathologycode(ppr.getSampathologycode());//病理编号
                        pp.setFeesource(0);//费用来源(0登记自动生成,1医嘱生成,2手工录入)
                        pp.setFeestate(0);//费用状态(0已保存,1已计费,2已发送到His,3发送失败）
                        pp.setFeecategory(psc.getChicategory());//统计类别
                        pp.setFeeitemid(psc.getChargeitemid());//收费项目Id
                        pp.setFeenamech(psc.getChinesename());//中文名称
                        pp.setFeenameen(psc.getChienglishname());//英文名称
                        pp.setFeeprince(psc.getChiprice());//单价
                        pp.setFeehisitemid(pscr.getRefhischargeid());//His项目Id
                        pp.setFeehisname(pscr.getRefhischargename());//His项目名称
                        pp.setFeehisprice(pscr.getRefhisprice());//His单价
                        pp.setFeeamount(1);//N	数量
                        pp.setFeecost(psc.getChiprice());//金额
                        pp.setFeeuserid(String.valueOf(user.getId()));//计费人员id
                        pp.setFeeusername(user.getName());//计费人员
                        pp.setFeetime(new Date());//计费时间
                        pimsPathologyFeeManager.save(pp);
                    }
                }
            }
            o.put("message", "标本添加成功！");
            o.put("success", true);
        } else{
            pimsPathologySampleManager.save(ppr);
            o.put("message", "标本编辑成功！");
            o.put("success", true);
        }
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
        PimsPathologySample ppr = (PimsPathologySample)setBeanProperty(request,PimsPathologySample.class);
        JSONObject o = new JSONObject();
        if(StringUtils.isEmpty(String.valueOf(ppr.getSampleid()))){
            o.put("message", "查不到该标本的信息！");
            o.put("success", false);
        }else{
            PimsPathologySample pathology = pimsPathologySampleManager.getBySampleNo(ppr.getSampleid());
            pimsPathologySampleManager.delete((long) ppr.getSampleid());
            //查询客户病理编号生成规则
            PimsHospitalPathologyInfo phi = pimsHospitalPathologyInfoManager.gethinfo(pathology);
            String qz =phi.getNumberPrefix()==null?"":phi.getNumberPrefix();
            String code =  qz + phi.getNextNumber();
            if(code.equals(pathology.getSampathologycode())){
                phi.setNextNumber(phi.getNextNumber()-1);
                pimsHospitalPathologyInfoManager.save(phi);
            }
            //更新电子申请单未被使用
            //pimsPathologyRequisitionManager.updateReqState(ppr,0);
            o.put("message", "标本删除成功！");
            o.put("success", true);
        }
        PrintwriterUtil.print(response, o.toString());
    }

    /**
     * 获取收费列表
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/ajax/fee*", method = RequestMethod.GET)
    @ResponseBody
    public DataResponse getFeeList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DataResponse dataResponse = new DataResponse();
        PimsPathologyFee fee = (PimsPathologyFee) setBeanProperty(request,PimsPathologyFee.class);
        if(fee == null || fee.getFeesampleid() == 0){
            return  null;
        }
        List<PimsPathologyFee> list = pimsPathologyFeeManager.getSampleList(fee);
        dataResponse.setRows(getResultMap(list));
        response.setContentType("text/html; charset=UTF-8");
        return dataResponse;
    }

    /**
     * 打印条码
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/printList*", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String printRequestList(HttpServletRequest request, HttpServletResponse response) {

        List<PimsPathologySample> lists = new ArrayList<PimsPathologySample>(); //返回JSON打印信息
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        WebService webService = new WebService();
        String samples = request.getParameter("samples");
        List<PimsPathologySample> samplearray = JSON.parseArray(samples,PimsPathologySample.class);//申请打印记录
        List<PimsPathologySample> hasPrintLaborder = new ArrayList<PimsPathologySample>(); //已打印记录
        JSONObject retObj = new JSONObject();
        retObj.put("printOrders", samplearray);
        retObj.put("noPrintOrders", hasPrintLaborder);
        return JSON.toJSONString(retObj, filter);
    }

    private ValueFilter filter = new ValueFilter() {
        @Override
        public Object process(Object obj, String s, Object v) {
            if (v == null)
                return "";
            return v;
        }
    };


    /**
     * 保存计费信息
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/savefee*", method = RequestMethod.POST)
    public void savefee(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String fees = request.getParameter("fees");
        String states = request.getParameter("states");
        JSONArray feesList = JSON.parseArray(fees);
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        JSONObject o = new JSONObject();
        if(states.equals("0")){//只保存
            for(int i=0;i<feesList.size();i++){
                Map map = (Map) feesList.get(i);
                PimsPathologyFee fee = (PimsPathologyFee) setBeanProperty(map,PimsPathologyFee.class);
                if(fee.getFeestate() == 0){
                    pimsPathologyFeeManager.save(fee);
                }
            }
            o.put("message", "计费项目保存成功！");
            o.put("success", true);
        }else if(states.equals("1")){//保存并发送
            for(int i=0;i<feesList.size();i++){
                Map map = (Map) feesList.get(i);
                PimsPathologyFee fee = (PimsPathologyFee) setBeanProperty(map,PimsPathologyFee.class);
                fee.setFeestate(2);
                fee.setFeesenduserid(String.valueOf(user.getId()));
                fee.setFeesendusername(user.getName());
                fee.setFeesendtime(new Date());
                if(fee.getFeestate() == 0){
                    pimsPathologyFeeManager.save(fee);
                }
                //// TODO: 2016/11/3 发送到HIS
            }
            o.put("message", "计费项目保存并发送成功！");
            o.put("success", true);
        }
        PrintwriterUtil.print(response, o.toString());
    }


}
