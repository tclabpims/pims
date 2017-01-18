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
import com.pims.service.QueryHisDataService;
import com.pims.service.basedata.PimsCommonBaseDataManager;
import com.pims.service.his.PimsPathologyRequisitionManager;
import com.pims.service.pimspathologysample.*;
import com.pims.service.pimssyspathology.PimsSysColorManager;
import com.pims.service.pimssyspathology.PimsSysPathologyManager;
import com.pims.service.pimssyspathology.PimsHospitalPathologyInfoManager;
import com.pims.service.pimssyspathology.PimsSysTestFeeManager;
import com.pims.service.pimssysreqtestitem.PimsSysReqTestitemManager;
import com.pims.webapp.controller.PIMSBaseController;
import com.pims.webapp.controller.WebControllerUtil;
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
import java.util.*;

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
    @Autowired
    private PimsSysReqTestitemManager pimsSysReqTestitemManager;//检查项目
    @Autowired
    private PimsCommonBaseDataManager pimsCommonBaseDataManager;//基础资料
    @Autowired
    private QueryHisDataService dataService;
    @Autowired
    private PimsPathologyPiecesManager pimsPathologyPiecesManager;//取材
    @Autowired
    private PimsPathologyParaffinManager pimsPathologyParaffinManager;//包埋
    @Autowired
    private PimsPathologySlideManager pimsPathologySlideManager;//制片
    @Autowired
    private PimsSysColorManager pimsSysColorManager;
    /**
     * 渲染视图
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView handleRequest(HttpServletRequest request) throws Exception {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long hosptail = user.getHospitalId();
        ModelAndView view = getmodelView(request);
        //检查项目
        Map map = new HashMap();
        map.put("tesitemtype","4");
        List<PimsSysReqTestitem> list = pimsSysReqTestitemManager.getTestitemInfo(map);
        StringBuilder builder = new StringBuilder();
        builder.append("<option value=''></option>");
        for(PimsSysReqTestitem obj : list) {
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
        PimsPathologySample pathology = pimsPathologySampleManager.get(Long.parseLong(code));
        PimsSysPathology psp = pimsSysPathologyManager.get(pathology.getSampathologyid());
        JSONObject pathMap = getJSONObject(pathology);
        pathMap.put("patIsSampling",psp.getPatissampling());
        pathMap.put("specialCheck",psp.getPatisspecialcheck());
        pathMap.put("pathologyid",psp.getPathologyid());
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
     * 获取首页标本列表
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/ajax/samplelist*", method = RequestMethod.GET)
    @ResponseBody
    public DataResponse getSampleList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DataResponse dataResponse = new DataResponse();
        PimsBaseModel ppr = new PimsBaseModel(request);
        List<PimsPathologySample> list = pimsPathologySampleManager.getSList(ppr);
        int num = pimsPathologySampleManager.getSNum(ppr);
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        if(list == null || list.size() == 0) {
            return null;
        }else{
            for(Object bean:list){
                Map<String, Object> map = new HashMap<String, Object>();
                Object[] pd = (Object[]) bean;
                String[] st = {"sampleid","sampathologyid","sampathologycode","samsenddoctorname",
                        "samsendhospital","sampatientname","piedoctorname","piesamplingtime","samsamplestatus","piedoctorid"};
                for(int i=0;i<st.length;i++){
                    Object o = pd[i];
                    map.put(st[i],o);
                }
                mapList.add(map);
            }
        }
        dataResponse.setRecords(num);
        dataResponse.setPage(ppr.getPage());
        dataResponse.setTotal(getTotalPage(num, ppr.getRow(), ppr.getPage()));
        dataResponse.setRows(mapList);
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
        ppr.setLogyid(request.getParameter("logyid"));
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
     * 获取最大单号
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/getcode*", method = RequestMethod.GET)
    public void getMaxCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String pathologyid = request.getParameter("pathologyid");
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PimsPathologySample ppr = new PimsPathologySample();
        ppr.setSamcustomerid(user.getHospitalId());
        ppr.setSampathologyid(Long.parseLong(pathologyid));
        //查询客户病理编号生成规则
        PimsHospitalPathologyInfo phi = pimsHospitalPathologyInfoManager.gethinfo(ppr);
        phi = searchCodeValue(phi);
        JSONObject o = new JSONObject();
        o.put("success",true);
        o.put("maxcode",phi.getNumberPrefix()+phi.getNextNumber());
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
                samplecode = "A12005030001";
            }else{
                samplecode = samplecode.substring(0,1)+(Long.parseLong(samplecode.substring(1))+1);
            }
            //查询客户病理编号生成规则
            PimsHospitalPathologyInfo phi = pimsHospitalPathologyInfoManager.gethinfo(ppr);
            phi = searchCodeValue(phi);
            ppr.setSampathologycode(phi.getNumberPrefix()+phi.getNextNumber());
            if(StringUtils.isEmpty(ppr.getSaminspectionid())){
                ppr.setSaminspectionid(samplecode);
            }
            PimsSysPathology pathology = pimsSysPathologyManager.get(ppr.getSampathologyid());
            int patissampling = pathology.getPatissampling() == null?0:pathology.getPatissampling().intValue();
            if(patissampling == 0){//常规登记
                ppr = pimsPathologySampleManager.save(ppr);
            }else if(patissampling == 1){//无需取材，细胞学标本登记
                ppr.setSamsamplestatus(2);
                ppr = pimsPathologySampleManager.save(ppr);
                PimsPathologyPieces pieces = new PimsPathologyPieces();
                pieces.setPiesampleid(ppr.getSampleid());//标本Id
                pieces.setPiepathologycode(ppr.getSampathologycode());//病理编号
                pieces.setPiesamplingno("1");//取材序号
                pieces.setPieunit("虚拟");//取材单位
                pieces.setPiecode(ppr.getSampathologycode()+"-1");//材块条码编号
                pieces.setPiestate(Long.getLong("2"));//状态
                pieces.setPieisembed("1");//是否包埋
                pieces = pimsPathologyPiecesManager.save(pieces);//自动生成材块信息
                PimsPathologyParaffin para = new PimsPathologyParaffin();
                para.setParsampleid(ppr.getSampleid());//样本号
                para.setParpathologycode(ppr.getSampathologycode());//病理编号
                para.setParname("虚拟蜡块");//蜡块名称
                para.setParparaffinno(1);//蜡块序号
                para.setParissectioned(0);//是否已切片(0未切片，1已切片
                para.setParpieceids(String.valueOf(pieces.getPieceid()));//材块ID
                pimsPathologyParaffinManager.save(para);//自动生成蜡块信息
            }else if(patissampling == 2){//外院会诊，无需取材也无需制片
                ppr.setSamsamplestatus(3);
                ppr = pimsPathologySampleManager.save(ppr);
                PimsPathologyPieces pieces = new PimsPathologyPieces();
                pieces.setPiesampleid(ppr.getSampleid());//标本Id
                pieces.setPiepathologycode(ppr.getSampathologycode());//病理编号
                pieces.setPiesamplingno("1");//取材序号
                pieces.setPieunit("虚拟");//取材单位
                pieces.setPiecode(ppr.getSampathologycode()+"-1");//材块条码编号
                pieces.setPiestate(Long.getLong("2"));//状态
                pieces.setPieisembed("1");//是否包埋
                pieces = pimsPathologyPiecesManager.save(pieces);//自动生成材块信息
                PimsPathologyParaffin para = new PimsPathologyParaffin();
                para.setParsampleid(ppr.getSampleid());//样本号
                para.setParpathologycode(ppr.getSampathologycode());//病理编号
                para.setParname("虚拟蜡块");//蜡块名称
                para.setParparaffinno(1);//蜡块序号
                para.setParissectioned(0);//是否已切片(0未切片，1已切片
                para.setParpieceids(String.valueOf(pieces.getPieceid()));//材块ID
                para = pimsPathologyParaffinManager.save(para);//自动生成蜡块信息
                PimsPathologySlide slide = new PimsPathologySlide();
                slide.setSlisampleid(ppr.getSampleid());//样本号
                slide.setSlicustomerid(ppr.getSamcustomerid());//客户代码
                slide.setSlislidebarcode(ppr.getSampathologycode()+"-1");//玻片条码号
                slide.setSlipathologycode(ppr.getSampathologycode());//病理编号
                slide.setSlislidetype(0);//玻片类型(0常规 1白片)
                slide.setSlislidesource(Long.getLong("4"));//玻片类型(0来自正常取材 1来自内部医嘱,3直接登记（如tct）,外送会诊)
                slide.setSliuseflag(Long.getLong("0"));//玻片使用状态(0未使用,1已使用)
                slide.setSliparaffinid(para.getParaffinid());//蜡块id
                slide.setSliifprint(0);//是否已打印(0未打印,1已打印)
                slide.setSliprinttimes("0");//打印次数
                pimsPathologySlideManager.save(slide);//自动生成制片信息
                //为标本绑定诊断医师
                ppr.setSampiecedoctorid(String.valueOf(phi.getLatestUser()));//诊断医师ID
                ppr.setSampiecedoctorname(phi.getTheAlias());//诊断医师姓名
                pimsPathologySampleManager.save(ppr);
            }
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

            //查询客户病理编号生成规则
            PimsHospitalPathologyInfo phi = pimsHospitalPathologyInfoManager.gethinfo(pathology);
            String qz =phi.getNumberPrefix()==null?"":phi.getNumberPrefix();
            String code =  qz + phi.getNextNumber();
            if(code.equals(pathology.getSampathologycode())){
                phi.setNextNumber(phi.getNextNumber()-1);
                pimsPathologySampleManager.delete((long) ppr.getSampleid());
                pimsHospitalPathologyInfoManager.save(phi);
                o.put("message", "标本删除成功！");
                o.put("success", true);
            }else{
                o.put("message", "不允许跳号删除！");
                o.put("success", false);
            }
            //更新电子申请单未被使用
            //pimsPathologyRequisitionManager.updateReqState(ppr,0);

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
                if(fee.getFeestate() != 2){
                    if(fee.getFeestate() == -1){
                        fee.setFeestate(0);
                    }
                    pimsPathologyFeeManager.save(fee);
                }
            }
            o.put("message", "计费项目保存成功！");
            o.put("success", true);
        }else if(states.equals("1")){//保存并发送
            for(int i=0;i<feesList.size();i++){
                Map map = (Map) feesList.get(i);
                PimsPathologyFee fee = (PimsPathologyFee) setBeanProperty(map,PimsPathologyFee.class);
                fee.setFeesenduserid(String.valueOf(user.getId()));
                fee.setFeesendusername(user.getName());
                fee.setFeesendtime(new Date());
                if(fee.getFeestate() != 2){
                    boolean result = dataService.insert(fee);
                    if(result){
                        fee.setFeestate(2);
                        pimsPathologyFeeManager.save(fee);
                    }else{
                        fee.setFeestate(3);
                        pimsPathologyFeeManager.save(fee);
                    }

                }
                //// TODO: 2016/11/3 发送到HIS
            }
            o.put("message", "计费项目保存并发送成功！");
            o.put("success", true);
        }
        PrintwriterUtil.print(response, o.toString());
    }
    /**
     * 获取新消息数量
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/ajax/num*", method = RequestMethod.GET)
    @ResponseBody
    public String getReqNum(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int noworkList = pimsPathologySampleManager.getSamStaNum();
        JSONObject o = new JSONObject();
        o.put("nonum",noworkList);
        o.put("success",1);
        PrintwriterUtil.print(response, o.toString());
        return  null;

    }

    @RequestMapping(value = "/ajax/color*", method = RequestMethod.GET ,produces = "application/json; charset=utf-8")
    @ResponseBody
    public DataResponse getColor(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = WebControllerUtil.getAuthUser();
        PimsSysColor psc = (PimsSysColor) setBeanProperty(request,PimsSysColor.class);
        List<PimsSysColor> col = pimsPathologySampleManager.getColor(psc);
        String[] a = new String[9];
        for(int i=0;i<col.size();i++){
            if(col.get(i).getColowner().equals(user.getId().toString())){
                a[i]=col.get(i).getColobjectstate();
            }
        }
        for(int i=0;i<col.size();i++){
            for(int j=0;j<col.size();j++){
                if(col.get(j).getColobjectstate().equals(a[i])&&col.get(j).getColowner().equals("9999999999")){
                    col.remove(j);
                }
            }
        }
        DataResponse dr = new DataResponse();
        dr.setRows(getResultMap(col));
        response.setContentType("text/html; charset=UTF-8");
        return dr;
    }

    //@RequestMapping(value = "/ajax/color2*", method = RequestMethod.GET)
    //@ResponseBody
    //public DataResponse getColor2(HttpServletRequest request, HttpServletResponse response) throws Exception {
    //    User user = WebControllerUtil.getAuthUser();
    //    List<PimsSysColor> col = pimsPathologySampleManager.getColor2();
    //    String[] a = new String[9];
    //    for(int i=0;i<col.size();i++){
    //        if(col.get(i).getColowner().equals(user.getId().toString())){
    //            a[i]=col.get(i).getColobjectstate();
    //        }
    //    }
    //    for(int i=0;i<col.size();i++){
    //        for(int j=0;j<col.size();j++){
    //            if(col.get(j).getColobjectstate().equals(a[i])&&col.get(j).getColowner().equals("9999999999")){
    //                col.remove(j);
    //            }
    //        }
    //    }
    //    DataResponse dr = new DataResponse();
    //    dr.setRows(getResultMap(col));
    //    response.setContentType("text/html; charset=UTF-8");
    //    return dr;
    //}
}
