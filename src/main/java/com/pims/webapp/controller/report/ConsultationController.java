package com.pims.webapp.controller.report;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pims.dao.report.ConsultationDao;
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
import com.pims.service.pimssyspathology.PimsSysPathologyManager;
import com.pims.service.pimssyspathology.PimsHospitalPathologyInfoManager;
import com.pims.service.pimssyspathology.PimsSysTestFeeManager;
import com.pims.service.pimssysreqtestitem.PimsSysReqTestitemManager;
import com.pims.service.report.ConsultationManager;
import com.pims.webapp.controller.PIMSBaseController;
import com.smart.Constants;
import com.smart.lisservice.WebService;
import com.smart.model.user.User;
import com.smart.webapp.util.DataResponse;
import com.smart.webapp.util.PrintwriterUtil;
import com.smart.webapp.util.UserUtil;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zp on 2016/12/21.
 */
@Controller
@RequestMapping("/report/consultation")
public class ConsultationController extends PIMSBaseController {
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
    private ConsultationDao consultationDao;
    @Autowired
    private ConsultationManager consultationManager;

    private final static String sampleexcelUrl = "/excel";
    /**
     * 渲染视图
     *
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
        map.put("tesitemtype", "4");
        List<PimsSysReqTestitem> list = pimsSysReqTestitemManager.getTestitemInfo(map);
        StringBuilder builder = new StringBuilder();
        builder.append("<option value=''></option>");
        for (PimsSysReqTestitem obj : list) {
            builder.append("<option value='").append(obj.getTestitemid()).append("' ");
            builder.append(">").append(obj.getTespathologyid() + ":" + obj.getTeschinesename()).append("</option>");
        }
        view.addObject("samjcxm", builder.toString());
        //送检医院
        map = new HashMap();
        map.put("bdcustomerid", hosptail);
        map.put("bddatatype", 4);
        List<PimsCommonBaseData> listbase = pimsCommonBaseDataManager.getDataList(map);
        view.addObject("samsendhospital", getOptions(listbase).toString());
        //送检科室
        map.put("bddatatype", 2);
        listbase = pimsCommonBaseDataManager.getDataList(map);
        view.addObject("samdeptname", getOptions(listbase).toString());
        //送检医生
        map.put("bddatatype", 3);
        listbase = pimsCommonBaseDataManager.getDataList(map);
        view.addObject("samsenddoctorname", getOptions(listbase).toString());
        return view;
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
        PimsBaseModel ppr = new PimsBaseModel(request);
        Map map = new HashedMap();
        map.put("consponsoredtime", (request.getParameter("consponsoredtime")== null|| request.getParameter("consponsoredtime").equals(""))?null:new java.sql.Date(Constants.DF2.parse(request.getParameter("consponsoredtime")).getTime()));
        map.put("confinishedtime", (request.getParameter("confinishedtime")== null|| request.getParameter("confinishedtime").equals(""))?null:new java.sql.Date(Constants.DF2.parse(request.getParameter("confinishedtime")).getTime()));
        map.put("logyids", request.getParameter("logyids"));
        map.put("sampathologycode", request.getParameter("sampathologycode"));
        map.put("sampatientname", request.getParameter("sampatientname"));
        map.put("samsendhospital", request.getParameter("samsendhospital"));
        map.put("samdeptcode", request.getParameter("samdeptcode"));
        map.put("samsenddoctorname", request.getParameter("samsenddoctorname"));
        map.put("conconsultationstate", request.getParameter("conconsultationstate"));
        map.put("consponsoredusername", request.getParameter("consponsoredusername"));
        map.put("confinisheduserid", request.getParameter("confinisheduserid"));
        map.put("remarks", request.getParameter("remarks"));
        //map.put("samre",request.getParameter("samre"));
        List<ViewConsultationQuery> list = consultationManager.getConsultationList(map,ppr);
        //int num = consultationManager.getReqListNum(map);
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
                String[] st = {"sampleid","sampathologycode","sampathologyid","samcustomerid",
                        "sampatientname","sampatientsex","sampatientage","samsamplestatus",
                        "samsenddoctorname","samsendhospital","samdeptname","consultationid",
                        "consponsoreduserid","consponsoredusername","consponsoredtime",
                        "conconsultationstate","confinisheduserid","confinishedusername",
                        "confinishedtime","samsenddoctorid","samdeptcode","patnamech","samauditer",
                        "samauditedtime","samsendtime"};
                for(int i=0;i<st.length;i++){
                    Object o = pd[i];
                    map1.put(st[i],o);
                }
                mapList.add(map1);
            }
        }
        dataResponse.setRecords(list.size());
        dataResponse.setPage(ppr.getPage());
        dataResponse.setTotal(getTotalPage(list.size(), ppr.getRow(), ppr.getPage()));
        dataResponse.setRows(mapList);
        response.setContentType("text/html; charset=UTF-8");
        return dataResponse;
    }

    //List<PimsPathologyPieces> getSampleListNoPage(String pathologyid)

    @RequestMapping(value = "/ajax/piece*", method = RequestMethod.POST)
    @ResponseBody
    public DataResponse getPiece(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DataResponse dataResponse = new DataResponse();
        String pathologyid = request.getParameter("pathologyid");
        PimsBaseModel ppr = new PimsBaseModel();
        List<PimsPathologyPieces> list = consultationManager.getSampleListNoPage(pathologyid);
        if(list.size()==0){
            return null;
        }
        dataResponse.setRecords(list.size());
        dataResponse.setPage(ppr.getPage());
        dataResponse.setTotal(getTotalPage(list.size(), ppr.getRow(), ppr.getPage()));
        dataResponse.setRows(getResultMap(list));
        response.setContentType("text/html; charset=UTF-8");
        return dataResponse;
    }

    @RequestMapping(value = "/ajax/getreqinfo*", method = RequestMethod.POST)
    @ResponseBody
    public void getListByReqId(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String code = request.getParameter("pathologycode");
        List<PimsConsultationDetail> list = consultationManager.getConDets(code);
        org.codehaus.jettison.json.JSONArray array = new org.codehaus.jettison.json.JSONArray();
        for (PimsConsultationDetail s : list) {
            JSONObject o = getJSONObject(s);
            array.put(o);
        }
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(array.toString());
    }

    @RequestMapping(value = "/ajax/getResult*", method = RequestMethod.POST,produces = {"application/text;charset=UTF-8"})
    @ResponseBody
    public String getResult(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String id = request.getParameter("id");
        String re = consultationManager.getResult(Long.parseLong(id));
        response.setContentType("text/html;charset=utf-8");
        return re;
    }

    @RequestMapping(value = "/ajax/getItem*", method = RequestMethod.POST)
    @ResponseBody
    public DataResponse getItem(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DataResponse dataResponse = new DataResponse();
        String id = request.getParameter("id");
        String sampleid = request.getParameter("sampleid");
        List<PimsPathologyOrderCheck> list = consultationManager.getItem(id,sampleid);
        if(list.size()==0){
            return null;
        }
        dataResponse.setRows(getResultMap(list));
        response.setContentType("text/html; charset=UTF-8");
        return dataResponse;
    }

    @RequestMapping(value = "/dc*", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView daochu(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PimsBaseModel ppr = new PimsBaseModel(request);
        ppr.setEnd(10000);
        Map map = new HashedMap();
        map.put("consponsoredtime", (request.getParameter("consponsoredtime")== null|| request.getParameter("consponsoredtime").equals(""))?null:new java.sql.Date(Constants.DF2.parse(request.getParameter("consponsoredtime")).getTime()));
        map.put("confinishedtime", (request.getParameter("confinishedtime")== null|| request.getParameter("confinishedtime").equals(""))?null:new java.sql.Date(Constants.DF2.parse(request.getParameter("confinishedtime")).getTime()));
        map.put("logyids", request.getParameter("logyids"));
        map.put("sampathologycode", request.getParameter("sampathologycode"));
        map.put("sampatientname", request.getParameter("sampatientname"));
        map.put("samsendhospital", request.getParameter("samsendhospital"));
        map.put("samdeptcode", request.getParameter("samdeptcode"));
        map.put("samsenddoctorname", request.getParameter("samsenddoctorname"));
        map.put("conconsultationstate", request.getParameter("conconsultationstate"));
        map.put("consponsoredusername", request.getParameter("consponsoredusername"));
        map.put("confinisheduserid", request.getParameter("confinisheduserid"));
        map.put("remarks", request.getParameter("remarks"));
        //map.put("samre",request.getParameter("samre"));
        List<ViewConsultationQuery> list = consultationManager.getConsultationList(map,ppr);
        List mapList = new ArrayList();

        if(list == null || list.size() == 0) {
        }else{
            for(Object bean:list){
                map = new HashMap();
                Object[] pd = (Object[]) bean;
                String[] st = {"sampleid","sampathologycode","sampathologyid","samcustomerid",
                        "sampatientname","sampatientsex","sampatientage","samsamplestatus",
                        "samsenddoctorname","samsendhospital","samdeptname","consultationid",
                        "consponsoreduserid","consponsoredusername","consponsoredtime",
                        "conconsultationstate","confinisheduserid","confinishedusername",
                        "confinishedtime","samsenddoctorid","samdeptcode","patnamech","samauditer",
                        "samauditedtime","samsendtime"};
                for(int i=0;i<st.length;i++){
                    Object o = pd[i];
                    if(o != null){
                        String propertyTypeName = o.getClass().getName();
                        if (propertyTypeName.equals(boolean.class.getName())
                                || propertyTypeName.equals(Boolean.class.getName())) {
                            map.put(st[i],((Boolean)o).booleanValue());
                        } else if (propertyTypeName.equals(java.util.Date.class.getName())) {
                            map.put(st[i],Constants.SDF.format((Date)o));
                        }else if (propertyTypeName.equals(java.sql.Date.class.getName())) {
                            map.put(st[i],Constants.SDF.format(((java.sql.Date)o).getTime()));
                        }else{
                            map.put(st[i],o.toString());
                        }
                    }else{
                        map.put(st[i],"");
                    }
                }
                mapList.add(map);
            }
        }
        long millis = Calendar.getInstance().getTimeInMillis();
        writeExcel(mapList,millis);

        System.out.println("开始导出");

        ServletOutputStream out = response.getOutputStream();
        response.setHeader("Content-disposition","attachment; " + "filename=newpb.xls");
        response.setHeader("Content-Type", "application/vnd.ms-excel");
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            File file = new File(sampleexcelUrl+"/"+millis+".xls");
            FileInputStream fin = new FileInputStream(file);
            bis = new BufferedInputStream(fin);
            bos = new BufferedOutputStream(out);
            byte buff[] = new byte[1024];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length)))
                bos.write(buff, 0, bytesRead);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null)
                bis.close();
            if (bos != null)
                bos.close();
        }
        return new ModelAndView("../report/consultation");
    }

    public boolean writeExcel(List list,long mills) throws FileNotFoundException{
        // win下
//		 OutputStream os = new FileOutputStream("d:\\test.xls");

        //linux下
        File dir = new File(sampleexcelUrl);
        dir.setWritable(true,false);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(sampleexcelUrl+"/"+mills+".xls");
        OutputStream os = new FileOutputStream(file);
        try
        {

            WritableWorkbook wwb = Workbook.createWorkbook(os);
            //创建Excel工作表 指定名称和位置
            WritableSheet ws = wwb.createSheet("病理报告查询",0);

            //**************往工作表中添加数据*****************
            Label label = new Label(0, 0, "");
            for(int i=0;i<list.size();i++){
                label = new Label(0, 0, "会诊状态");
                    ws.addCell(label);
                label = new Label(1, 0, "病种类别");
                    ws.addCell(label);
                label = new Label(2, 0, "病理编号");
                    ws.addCell(label);
                label = new Label(3, 0, "患者姓名");
                    ws.addCell(label);
                label = new Label(4, 0, "年龄");
                    ws.addCell(label);
                label = new Label(5, 0, "性别");
                    ws.addCell(label);
                label = new Label(6, 0, "病理状态");
                    ws.addCell(label);
                label = new Label(7, 0, "送检单位");
                    ws.addCell(label);
                label = new Label(8, 0, "送检科室");
                    ws.addCell(label);
                label = new Label(9, 0, "送检医生");
                    ws.addCell(label);
                label = new Label(10, 0, "送检日期");
                    ws.addCell(label);
                label = new Label(11, 0, "发起医生");
                    ws.addCell(label);
                label = new Label(12, 0, "发起日期");
                    ws.addCell(label);
                label = new Label(13, 0, "结束日期");
                    ws.addCell(label);

            }
            for(int i=0;i<list.size();i++){
                Map map = (Map)list.get(i);
                label = new Label(0, i+1, (String) map.get("conconsultationstate"));//病种类别
                ws.addCell(label);
                label = new Label(1, i+1, (String) map.get("patnamech"));//病理号
                ws.addCell(label);
                label = new Label(2, i+1, (String) map.get("sampathologycode"));//病人姓名
                ws.addCell(label);
                label = new Label(3, i+1, (String) map.get("sampatientname"));//年龄
                ws.addCell(label);
                label = new Label(4, i+1, (String) map.get("sampatientage"));//住院号
                ws.addCell(label);
                label = new Label(5, i+1, (String) map.get("sampatientsex"));//床号
                ws.addCell(label);
                label = new Label(6, i+1, (String) map.get("samsamplestatus"));//性别
                ws.addCell(label);
                label = new Label(7, i+1, (String) map.get("samsendhospital"));//病理状态
                ws.addCell(label);
                label = new Label(8, i+1, (String) map.get("samdeptname"));//送检日期
                ws.addCell(label);
                label = new Label(9, i+1, (String) map.get("samsenddoctorname"));//诊断日期
                ws.addCell(label);
                label = new Label(10, i+1, (String) map.get("samsendtime"));//送检医生
                ws.addCell(label);
                label = new Label(11, i+1, (String) map.get("consponsoredusername"));//送检科室
                ws.addCell(label);
                label = new Label(12, i+1, (String) map.get("consponsoredtime"));//送检医院
                ws.addCell(label);
                label = new Label(13, i+1, (String) map.get("confinishedtime"));//取材医生
                ws.addCell(label);
            }
            //写入工作表
            wwb.write();
            wwb.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }



        return true;
    }
}