package com.pims.webapp.controller.report;

import com.pims.model.*;
import com.pims.service.pimspathologysample.PimsPathologySampleManager;
import com.pims.service.pimssyspathology.PimsHospitalPathologyInfoManager;
import com.pims.webapp.controller.PIMSBaseController;
import com.pims.webapp.util.VerificaDate;
import com.smart.Constants;
import com.smart.model.user.User;
import com.smart.webapp.util.DataResponse;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
/**
 * Created by king on 2016/10/10.
 */
@Controller
@RequestMapping("/report/*")
public class ReportController extends PIMSBaseController{
    @Autowired
    private PimsPathologySampleManager pimsPathologySampleManager;
    @Autowired
    private PimsHospitalPathologyInfoManager pimsHospitalPathologyInfoManager;

    private final static String sampleexcelUrl = "/excel";
    /**
     * 渲染视图(病理报告查询)
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView diagnosisreport(HttpServletRequest request) throws Exception {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, - 7);
        Date monday = c.getTime();
        String sevenDay = Constants.DF2.format(monday);
        String today = Constants.DF2.format(new Date());
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long logylibid = user.getUserBussinessRelate().getPathologyLibId();//病种库
        List<PimsSysPathology> items = pimsHospitalPathologyInfoManager.getPathologyByUserId(user.getId());
        StringBuilder builder = new StringBuilder();
        builder.append("<option value=''>全部</option>");
        for(PimsSysPathology obj : items) {
            builder.append("<option value='").append(obj.getPathologyid()).append("'>").append(obj.getPatnamech()).append("</option>");
        }
        ModelAndView view = new ModelAndView();
        view.addObject("logyid",logylibid);//当前用户选择的病例库
        view.addObject("sevenday", sevenDay);//7天前
        view.addObject("receivetime", today);//当前时间
        view.addObject("send_hosptail",user.getHospitalId());//账号所属医院
        view.addObject("logyids",builder.toString());
        return view;
    }


    /**
     * 获取报告列表
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/diagnosisreport/list*", method = RequestMethod.GET)
    @ResponseBody
    public DataResponse getReportList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DataResponse dataResponse = new DataResponse();
        PimsBaseModel ppr = new PimsBaseModel(request);
        List list = pimsPathologySampleManager.getReportList(ppr);
        int num = pimsPathologySampleManager.getReportNum(ppr);
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        if(list == null || list.size() == 0) {
            return null;
        }else{
            for(Object bean:list){
                Map<String, Object> map = new HashMap<String, Object>();
                Object[] pd = (Object[]) bean;
                String[] st = {"sampleid","saminspectionid","sampathologyid","sampathologycode","sampatientname","sampatientage","sampatientnumber",
                        "sampatientbed","sampatientsex","samsamplestatus","samsendtime","saminitiallytime","samsenddoctorname","samdeptname",
                        "samsendhospital","piedoctorname","parsectioneddoctor","saminitiallyusername","restestresult","myzhnum","tsrsnum","fzblnum"};
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

    @RequestMapping(value = "/daochu*", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView daochu(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PimsBaseModel ppr = new PimsBaseModel(request);
        ppr.setEnd(10000);
        List list = pimsPathologySampleManager.getReportList(ppr);
        List mapList = new ArrayList();
        if(list == null || list.size() == 0) {
        }else{
            for(Object bean:list){
                Map map = new HashMap();
                Object[] pd = (Object[]) bean;
                String[] st = {"sampleid","saminspectionid","sampathologyid","sampathologycode","sampatientname","sampatientage","sampatientnumber",
                        "sampatientbed","sampatientsex","samsamplestatus","samsendtime","saminitiallytime","samsenddoctorname","samdeptname",
                        "samsendhospital","piedoctorname","parsectioneddoctor","saminitiallyusername","restestresult","myzhnum","tsrsnum","fzblnum"};
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
        return new ModelAndView("../report/diagnosisreport");
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
            Label label = new Label(0, 0, "病种类别");
            ws.addCell(label);
            label = new Label(1, 0, "病理号");
            ws.addCell(label);
            label = new Label(2, 0, "病人姓名");
            ws.addCell(label);
            label = new Label(3, 0, "年龄");
            ws.addCell(label);
            label = new Label(4, 0, "住院号");
            ws.addCell(label);
            label = new Label(5, 0, "床号");
            ws.addCell(label);
            label = new Label(6, 0, "性别");
            ws.addCell(label);
            label = new Label(7, 0, "病理状态");
            ws.addCell(label);
            label = new Label(8, 0, "送检日期");
            ws.addCell(label);
            label = new Label(9, 0, "诊断日期");
            ws.addCell(label);
            label = new Label(10, 0, "送检医生");
            ws.addCell(label);
            label = new Label(11, 0, "送检科室");
            ws.addCell(label);
            label = new Label(12, 0, "送检医院");
            ws.addCell(label);
            label = new Label(13, 0, "取材医生");
            ws.addCell(label);
            label = new Label(14, 0, "切片医生");
            ws.addCell(label);
            label = new Label(15, 0, "诊断医生");
            ws.addCell(label);
            label = new Label(16, 0, "病理诊断");
            ws.addCell(label);
            label = new Label(17, 0, "免疫组化");
            ws.addCell(label);
            label = new Label(18, 0, "特殊染色");
            ws.addCell(label);
            label = new Label(19, 0, "分子病理");
            ws.addCell(label);
            for(int i=0;i<list.size();i++){
                Map map = (Map)list.get(i);
                label = new Label(0, i+1, (String) map.get("sampathologyid"));//病种类别
                ws.addCell(label);
                label = new Label(1, i+1, (String) map.get("sampathologycode"));//病理号
                ws.addCell(label);
                label = new Label(2, i+1, (String) map.get("sampatientname"));//病人姓名
                ws.addCell(label);
                label = new Label(3, i+1, (String) map.get("sampatientage"));//年龄
                ws.addCell(label);
                label = new Label(4, i+1, (String) map.get("sampatientnumber"));//住院号
                ws.addCell(label);
                label = new Label(5, i+1, (String) map.get("sampatientbed"));//床号
                ws.addCell(label);
                label = new Label(6, i+1, (String) map.get("sampatientsex"));//性别
                ws.addCell(label);
                label = new Label(7, i+1, (String) map.get("samsamplestatus"));//病理状态
                ws.addCell(label);
                label = new Label(8, i+1, (String) map.get("samsendtime"));//送检日期
                ws.addCell(label);
                label = new Label(9, i+1, (String) map.get("saminitiallytime"));//诊断日期
                ws.addCell(label);
                label = new Label(10, i+1, (String) map.get("samsenddoctorname"));//送检医生
                ws.addCell(label);
                label = new Label(11, i+1, (String) map.get("samdeptname"));//送检科室
                ws.addCell(label);
                label = new Label(12, i+1, (String) map.get("samsendhospital"));//送检医院
                ws.addCell(label);
                label = new Label(13, i+1, (String) map.get("piedoctorname"));//取材医生
                ws.addCell(label);
                label = new Label(14, i+1, (String) map.get("parsectioneddoctor"));//切片医生
                ws.addCell(label);
                label = new Label(15, i+1, (String) map.get("saminitiallyusername"));//诊断医生
                ws.addCell(label);
                label = new Label(16, i+1, (String) map.get("restestresult"));//病理诊断
                ws.addCell(label);
                label = new Label(17, i+1, (String) map.get("myzhnum"));//免疫组化
                ws.addCell(label);
                label = new Label(18, i+1, (String) map.get("tsrsnum"));//特殊染色
                ws.addCell(label);
                label = new Label(19, i+1, (String) map.get("fzblnum"));//分子病理
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

    /**
     * 渲染视图(延迟报告管理)
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/reportdelay", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView reportdelay(HttpServletRequest request) throws Exception {
        return getInitializeView();
    }
    /**
     * 获取报告列表
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/reportdelay/list*", method = RequestMethod.GET)
    @ResponseBody
    public DataResponse getReportDelayList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DataResponse dataResponse = new DataResponse();
        PimsBaseModel ppr = new PimsBaseModel(request);
        List list = pimsPathologySampleManager.getReportDelayList(ppr);
        int num = pimsPathologySampleManager.getReportDelayNum(ppr);
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        if(list == null || list.size() == 0) {
            return null;
        }else{
            for(Object bean:list){
                Map<String, Object> map = new HashMap<String, Object>();
                Object[] pd = (Object[]) bean;
                String[] st = {"sampleid","sampathologyid","sampathologycode","sampatientname","sampatientage","sampatientsex","samsendtime",
                        "delreporttime","deldoctor","delreason","samsamplestatus","samdeptname","samsenddoctorname","samsendhospital",
                        "deldiagnosis","restestresult","delcreatetime","deldays"};
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
     * 渲染视图(病理统计管理)
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/countreport", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView countreport(HttpServletRequest request) throws Exception {
        return getInitializeView();
    }

    private ModelAndView getInitializeView(){
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, - 7);
        Date monday = c.getTime();
        String sevenDay = Constants.DF2.format(monday);
        String today = Constants.DF2.format(new Date());
        ModelAndView view = new ModelAndView();
        view.addObject("sevenday", sevenDay);//7天前
        view.addObject("receivetime", today);//当前时间
        return  view;
    }

    /**
     * 日志统计总列表
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/rztj*", method = RequestMethod.GET)
    @ResponseBody
    public DataResponse getRztj(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DataResponse dataResponse = new DataResponse();
        PimsBaseModel ppr = new PimsBaseModel(request);
        List<Map<String, Object>> list = pimsPathologySampleManager.getRztj(ppr);
        dataResponse.setRows(list);
        response.setContentType("text/html; charset=UTF-8");
        return dataResponse;
    }
    /**
     * 日志统计详细列表
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/rztjinfo*", method = RequestMethod.GET)
    @ResponseBody
    public DataResponse getRztjInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DataResponse dataResponse = new DataResponse();
        PimsBaseModel ppr = new PimsBaseModel(request);
        List<Map<String, Object>> list = pimsPathologySampleManager.getRztjInfo(ppr);
        dataResponse.setRows(list);
        response.setContentType("text/html; charset=UTF-8");
        return dataResponse;
    }

    /**
     * 标本来源统计报告
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/bblytj*", method = RequestMethod.GET)
    @ResponseBody
    public DataResponse getBblytj(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DataResponse dataResponse = new DataResponse();
        PimsBaseModel ppr = new PimsBaseModel(request);
        List<Map<String, Object>> list = pimsPathologySampleManager.getBbly(ppr);
        dataResponse.setRows(list);
        response.setContentType("text/html; charset=UTF-8");
        return dataResponse;
    }

    /**
     * 收费统计报告
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/sftj*", method = RequestMethod.GET)
    @ResponseBody
    public DataResponse getSftj(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DataResponse dataResponse = new DataResponse();
        PimsBaseModel ppr = new PimsBaseModel(request);
        List<Map<String, Object>> list = pimsPathologySampleManager.getSftj(ppr);
        dataResponse.setRows(list);
        response.setContentType("text/html; charset=UTF-8");
        return dataResponse;
    }

    /**
     *excel导出
     *
     */
    @RequestMapping(value = "/dc*", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public ModelAndView daochu2(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PimsBaseModel ppr = new PimsBaseModel();
        ppr.setSord("asc");
        ppr.setSidx(" ");
        if(request.getParameter("req_bf_time")== null|| request.getParameter("req_bf_time").equals("")){
            ppr.setReq_bf_time(null);
        }else {
            ppr.setReq_bf_time(new java.sql.Date(Constants.DF2.parse(request.getParameter("req_bf_time")).getTime()));
        }
        if(request.getParameter("req_af_time")== null|| request.getParameter("req_af_time").equals("")){
            ppr.setReq_af_time(null);
        }else {
            ppr.setReq_af_time(new java.sql.Date(Constants.DF2.parse(request.getParameter("req_af_time")).getTime()));
        }
//        List<Map<String, Object>> list1 = pimsPathologySampleManager.getRztjInfo(ppr);
//        List<Map<String, Object>> list2 = pimsPathologySampleManager.getRztj(ppr);
        long millis = Calendar.getInstance().getTimeInMillis();
        String array = request.getParameter("array");
        int arnum=array.split(",").length;
        String[] ar = new String[arnum];
        for(int i=0;i<arnum;i++){
            ar[i]=array.split(",")[i];
        }
        writeExcel1(ar,ppr,millis);
        System.out.println("开始导出");

        ServletOutputStream out = response.getOutputStream();
        response.setHeader("Content-disposition", "attachment; " + "filename=newpb.xls");
        response.setHeader("Content-Type", "application/vnd.ms-excel");
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            File file = new File(sampleexcelUrl + "/" + millis + ".xls");
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
        return new ModelAndView("../report/countreport");
    }

    public boolean writeExcel1(String[] ar,PimsBaseModel ppr,long mills) throws FileNotFoundException{
        // win下
//		 OutputStream os = new FileOutputStream("d:\\test.xls");
        Map map = new HashMap();
        String sex="男";
        String sts="已登记";
        String hgsts="合格";
        String ageType="岁";
        ppr.setReq_sts("5");
        List list = pimsPathologySampleManager.getReportList(ppr);
        List<Map<String, Object>> list13 = new ArrayList<Map<String, Object>>();
        for(Object bean:list){
            Map<String, Object> map1 = new HashMap<String, Object>();
            Object[] pd = (Object[]) bean;
            String[] st = {"sampleid","saminspectionid","sampathologyid","sampathologycode","sampatientname","sampatientage","sampatientnumber",
                    "sampatientbed","sampatientsex","samsamplestatus","samsendtime","saminitiallytime","samsenddoctorname","samdeptname",
                    "samsendhospital","piedoctorname","parsectioneddoctor","saminitiallyusername","restestresult","myzhnum","tsrsnum","fzblnum"};
            for(int i=0;i<st.length;i++){
                Object o = pd[i];
                map1.put(st[i],o);
            }
            list13.add(map1);
        }
        ppr.setReq_sts("");
        List<Map<String, Object>> list1 = pimsPathologySampleManager.getRztjInfo(ppr);
        List<Map<String, Object>> list2 = pimsPathologySampleManager.getRztj(ppr);
        ppr.setReq_sts("2");
        ppr.setSord("asc");
        List<PimsPathologySample> list12 = pimsPathologySampleManager.getSampleList(ppr);
        ppr.setReq_code("1");
        ppr.setReq_sts("");
        List<Map<String, Object>> list3 = pimsPathologySampleManager.getBbly(ppr);
        List<Map<String, Object>> list6 = pimsPathologySampleManager.getSftj(ppr);
        ppr.setReq_code("2");
        List<Map<String, Object>> list4 = pimsPathologySampleManager.getBbly(ppr);
        List<Map<String, Object>> list7 = pimsPathologySampleManager.getSftj(ppr);
        ppr.setReq_code("3");
        List<Map<String, Object>> list5 = pimsPathologySampleManager.getBbly(ppr);
        List<Map<String, Object>> list8 = pimsPathologySampleManager.getSftj(ppr);
        ppr.setReq_code("4");
        List<Map<String, Object>> list9 = pimsPathologySampleManager.getSftj(ppr);
        ppr.setReq_code("5");
        List<Map<String, Object>> list10 = pimsPathologySampleManager.getSftj(ppr);
        ppr.setReq_code("6");
        List<Map<String, Object>> list11 = pimsPathologySampleManager.getSftj(ppr);
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
            for(int j=0;j<ar.length;j++){
                switch (Integer.parseInt(ar[j])){
                    case 1:WritableSheet ws = wwb.createSheet("每日工作统计表",0);
                        Label label = new Label(0, 0, "病种类别");
                        ws.addCell(label);
                        label = new Label(1, 0, "已登记");
                        ws.addCell(label);
                        label = new Label(2, 0, "已取材");
                        ws.addCell(label);
                        label = new Label(3, 0, "已包埋");
                        ws.addCell(label);
                        label = new Label(4, 0, "已切片");
                        ws.addCell(label);
                        label = new Label(5, 0, "已初诊");
                        ws.addCell(label);
                        label = new Label(6, 0, "已审核");
                        ws.addCell(label);
                        label = new Label(7, 0, "已发送");
                        ws.addCell(label);
                        label = new Label(8, 0, "会诊中");
                        ws.addCell(label);
                        label = new Label(9, 0, "已打印");
                        ws.addCell(label);
                        for(int i=0;i<list1.size();i++){
                            map = (Map)list1.get(i);
                            label = new Label(0, i+1,  String.valueOf(map.get("sampathologyid")));//病种类别
                            ws.addCell(label);
                            label = new Label(1, i+1,  String.valueOf(map.get("samsamplestatus0")));//病理号
                            ws.addCell(label);
                            label = new Label(2, i+1,  String.valueOf(map.get("samsamplestatus1")));//病人姓名
                            ws.addCell(label);
                            label = new Label(3, i+1,  String.valueOf(map.get("samsamplestatus2")));//年龄
                            ws.addCell(label);
                            label = new Label(4, i+1,  String.valueOf(map.get("samsamplestatus3")));//住院号
                            ws.addCell(label);
                            label = new Label(5, i+1,  String.valueOf(map.get("samsamplestatus4")));//床号
                            ws.addCell(label);
                            label = new Label(6, i+1,  String.valueOf(map.get("samsamplestatus5")));//性别
                            ws.addCell(label);
                            label = new Label(7, i+1,  String.valueOf(map.get("samsamplestatus6")));//病理状态
                            ws.addCell(label);
                            label = new Label(8, i+1,  String.valueOf(map.get("samsamplestatus7")));//送检日期
                            ws.addCell(label);
                            label = new Label(9, i+1,  String.valueOf(map.get("samsamplestatus8")));//诊断日期
                            ws.addCell(label);
                        }
                        int fn = list1.size();
                        map = (Map)list2.get(0);
                        label = new Label(0, fn+1, "总计");//病理号
                        ws.addCell(label);
                        label = new Label(1, fn+1,  String.valueOf(map.get("samsamplestatus0")));//病理号
                        ws.addCell(label);
                        label = new Label(2, fn+1,  String.valueOf(map.get("samsamplestatus1")));//病人姓名
                        ws.addCell(label);
                        label = new Label(3, fn+1,  String.valueOf(map.get("samsamplestatus2")));//年龄
                        ws.addCell(label);
                        label = new Label(4, fn+1,  String.valueOf(map.get("samsamplestatus3")));//住院号
                        ws.addCell(label);
                        label = new Label(5, fn+1,  String.valueOf(map.get("samsamplestatus4")));//床号
                        ws.addCell(label);
                        label = new Label(6, fn+1,  String.valueOf(map.get("samsamplestatus5")));//性别
                        ws.addCell(label);
                        label = new Label(7, fn+1,  String.valueOf(map.get("samsamplestatus6")));//病理状态
                        ws.addCell(label);
                        label = new Label(8, fn+1,  String.valueOf(map.get("samsamplestatus7")));//送检日期
                        ws.addCell(label);
                        label = new Label(9, fn+1,  String.valueOf(map.get("samsamplestatus8")));//诊断日期
                        ws.addCell(label);
                    break;
                    case 2:WritableSheet ws1 = wwb.createSheet("标本分类统计",1);
                        Label label1 = new Label(0, 0, "标本来源");
                        ws1.addCell(label1);
                        label1 = new Label(1, 0, "送检单位");
                        ws1.addCell(label1);
                        label1 = new Label(2, 0, "数量");
                        ws1.addCell(label1);
                        label1 = new Label(4, 0, "送检科室");
                        ws1.addCell(label1);
                        label1 = new Label(5, 0, "数量");
                        ws1.addCell(label1);
                        label1 = new Label(7, 0, "送检医生");
                        ws1.addCell(label1);
                        label1 = new Label(8, 0, "数量");
                        ws1.addCell(label1);
                        for(int i=0;i<list3.size();i++) {
                            map = (Map) list3.get(i);
                            label1 = new Label(1, i + 1, String.valueOf(map.get("name")));//病种类别
                            ws1.addCell(label1);
                            label1 = new Label(2, i + 1, String.valueOf(map.get("nums")));//病理号
                            ws1.addCell(label1);
                        }
                        for(int i=0;i<list4.size();i++) {
                            map = (Map) list4.get(i);
                            label1 = new Label(4, i + 1, String.valueOf(map.get("name")));//病人姓名
                            ws1.addCell(label1);
                            label1 = new Label(5, i + 1, String.valueOf(map.get("nums")));//年龄
                            ws1.addCell(label1);
                        }
                        for(int i=0;i<list5.size();i++) {
                            map = (Map) list5.get(i);
                            label1 = new Label(7, i + 1, String.valueOf(map.get("name")));//住院号
                            ws1.addCell(label1);
                            label1 = new Label(8, i + 1, String.valueOf(map.get("nums")));//床号
                            ws1.addCell(label1);
                        }
                    break;
                    case 3:WritableSheet ws2 = wwb.createSheet("收费统计报告",2);
                        Label label2 = new Label(0, 0, "收费来源");
                        ws2.addCell(label2);
                        label2 = new Label(1, 0, "送检医生");
                        ws2.addCell(label2);
                        label2 = new Label(2, 0, "数量");
                        ws2.addCell(label2);
                        label2 = new Label(4, 0, "送检科室");
                        ws2.addCell(label2);
                        label2 = new Label(5, 0, "数量");
                        ws2.addCell(label2);
                        label2 = new Label(7, 0, "送检医生");
                        ws2.addCell(label2);
                        label2 = new Label(8, 0, "数量");
                        ws2.addCell(label2);
                        label2 = new Label(10, 0, "收费列表");
                        ws2.addCell(label2);
                        label2 = new Label(11, 0, "金额");
                        ws2.addCell(label2);
                        label2 = new Label(13, 0, "收费明细项目");
                        ws2.addCell(label2);
                        label2 = new Label(14, 0, "金额");
                        ws2.addCell(label2);
                        label2 = new Label(16, 0, "报告医生");
                        ws2.addCell(label2);
                        label2 = new Label(17, 0, "金额");
                        ws2.addCell(label2);
                        for(int i=0;i<list6.size();i++) {
                            map = (Map) list6.get(i);
                            label2 = new Label(1, i + 1, String.valueOf(map.get("name")));//病种类别
                            ws2.addCell(label2);
                            label2 = new Label(2, i + 1, String.valueOf(map.get("prices")));//病理号
                            ws2.addCell(label2);
                        }
                        for(int i=0;i<list7.size();i++) {
                            map = (Map) list7.get(i);
                            label2 = new Label(4, i + 1, String.valueOf(map.get("name")));//病人姓名
                            ws2.addCell(label2);
                            label2 = new Label(5, i + 1, String.valueOf(map.get("prices")));//年龄
                            ws2.addCell(label2);
                        }
                        for(int i=0;i<list8.size();i++) {
                            map = (Map) list8.get(i);
                            label2 = new Label(7, i + 1, String.valueOf(map.get("name")));//住院号
                            ws2.addCell(label2);
                            label2 = new Label(8, i + 1, String.valueOf(map.get("prices")));//床号
                            ws2.addCell(label2);
                        }
                        for(int i=0;i<list9.size();i++) {
                            map = (Map) list9.get(i);
                            label2 = new Label(10, i + 1, String.valueOf(map.get("name")));//住院号
                            ws2.addCell(label2);
                            label2 = new Label(11, i + 1, String.valueOf(map.get("prices")));//床号
                            ws2.addCell(label2);
                        }
                        for(int i=0;i<list10.size();i++) {
                            map = (Map) list10.get(i);
                            label2 = new Label(13, i + 1, String.valueOf(map.get("name")));//住院号
                            ws2.addCell(label2);
                            label2 = new Label(14, i + 1, String.valueOf(map.get("prices")));//床号
                            ws2.addCell(label2);
                        }
                        for(int i=0;i<list11.size();i++) {
                            map = (Map) list11.get(i);
                            label2 = new Label(16, i + 1,String.valueOf(String.valueOf(map.get("name"))));//住院号
                            ws2.addCell(label2);
                            label2 = new Label(17, i + 1, String.valueOf(map.get("prices")));//床号
                            ws2.addCell(label2);
                        }
                    break;
                    case 4:WritableSheet ws3 = wwb.createSheet("标本不合格表",3);
                        Label label3 = new Label(0, 0, "样本ID");
                        ws3.addCell(label3);
                        label3 = new Label(1, 0, "病理编号");
                        ws3.addCell(label3);
                        label3 = new Label(2, 0, "患者姓名");
                        ws3.addCell(label3);
                        label3 = new Label(3, 0, "送检单位");
                        ws3.addCell(label3);
                        label3 = new Label(4, 0, "送检科室");
                        ws3.addCell(label3);
                        label3 = new Label(5, 0, "送检医生");
                        ws3.addCell(label3);
                        label3 = new Label(6, 0, "申请时间");
                        ws3.addCell(label3);
                        label3 = new Label(7, 0, "合格状态");
                        ws3.addCell(label3);
                        label3 = new Label(8, 0, "病理状态");
                        ws3.addCell(label3);
                        label3 = new Label(9, 0, "性别");
                        ws3.addCell(label3);
                        label3 = new Label(10, 0, "年龄");
                        ws3.addCell(label3);
                        label3 = new Label(11, 0, "年龄类型");
                        ws3.addCell(label3);
                        label3 = new Label(12, 0, "临床诊断");
                        ws3.addCell(label3);
                        label3 = new Label(13, 0, "送检时间");
                        ws3.addCell(label3);
                        label3 = new Label(14, 0, "登记时间");
                        ws3.addCell(label3);


                        for(int i=0;i<list12.size();i++){

                            switch (Integer.parseInt(list12.get(i).getSamsecondv())){
                                case 1:hgsts="合格";
                                case 2:hgsts="不合格";
                            }
                            switch (list12.get(i).getSampatientsex()){
                                case 1:sex="男";
                                case 2:sex="女";
                                case 3:sex="未知";
                            }
                            switch (Integer.parseInt(String.valueOf(list12.get(i).getSamsamplestatus()))){
                                case 0:sts="已登记";
                                case 1:sts="已取材";
                                case 2:sts="已包埋";
                                case 3:sts="已切片";
                                case 4:sts="已初诊";
                                case 5:sts="已审核";
                                case 6:sts="已发送";
                                case 7:sts="会诊中";
                                case 8:sts="报告已打印";
                            }
                            label3 = new Label(0, i+1,  String.valueOf(list12.get(i).getSampleid()));//病种类别
                            ws3.addCell(label3);
                            label3 = new Label(1, i+1,  String.valueOf(list12.get(i).getSampathologycode()));//病理号
                            ws3.addCell(label3);
                            label3 = new Label(2, i+1,  String.valueOf(list12.get(i).getSampatientname()));//病人姓名
                            ws3.addCell(label3);
                            label3 = new Label(3, i+1,  String.valueOf(list12.get(i).getSamsendhospital()));//年龄
                            ws3.addCell(label3);
                            label3 = new Label(4, i+1,  String.valueOf(list12.get(i).getSamdeptname()));//住院号
                            ws3.addCell(label3);
                            label3 = new Label(5, i+1,  String.valueOf(list12.get(i).getSamsenddoctorname()));//床号
                            ws3.addCell(label3);
                            label3 = new Label(6, i+1,  String.valueOf(list12.get(i).getSamreqtime()));//性别
                            ws3.addCell(label3);
                            label3 = new Label(7, i+1,  hgsts);//病理状态
                            ws3.addCell(label3);
                            label3 = new Label(8, i+1,  sts);//送检日期
                            ws3.addCell(label3);
                            label3 = new Label(9, i+1,  sex);//诊断日期
                            ws3.addCell(label3);
                            label3 = new Label(10, i+1,  String.valueOf(list12.get(i).getSampatientage()));//诊断日期
                            ws3.addCell(label3);
                            label3 = new Label(11, i+1,  ageType);//诊断日期
                            ws3.addCell(label3);
                            label3 = new Label(12, i+1,  String.valueOf(list12.get(i).getSampatientdignoses()));//诊断日期
                            ws3.addCell(label3);
                            label3 = new Label(13, i+1,  String.valueOf(list12.get(i).getSamsendtime()));//诊断日期
                            ws3.addCell(label3);
                            label3 = new Label(14, i+1,  String.valueOf(list12.get(i).getSamregisttime()));//诊断日期
                            ws3.addCell(label3);
                        }
                    break;
                    case 5:WritableSheet ws4 = wwb.createSheet("病理登记薄",4);
                        Label label4 = new Label(0, 0, "病种类别");
                        ws4.addCell(label4);
                        label4 = new Label(1, 0, "病理编号");
                        ws4.addCell(label4);
                        label4 = new Label(2, 0, "患者姓名");
                        ws4.addCell(label4);
                        label4 = new Label(3, 0, "年龄");
                        ws4.addCell(label4);
                        label4 = new Label(4, 0, "住院号");
                        ws4.addCell(label4);
                        label4 = new Label(5, 0, "床号");
                        ws4.addCell(label4);
                        label4 = new Label(6, 0, "性别");
                        ws4.addCell(label4);
                        label4 = new Label(7, 0, "病理状态");
                        ws4.addCell(label4);
                        label4 = new Label(8, 0, "送检日期");
                        ws4.addCell(label4);
                        label4 = new Label(9, 0, "诊断日期");
                        ws4.addCell(label4);
                        label4 = new Label(10, 0, "送检医生");
                        ws4.addCell(label4);
                        label4 = new Label(11, 0, "送检科室");
                        ws4.addCell(label4);
                        label4 = new Label(12, 0, "送检医院");
                        ws4.addCell(label4);
                        label4 = new Label(13, 0, "取材医生");
                        ws4.addCell(label4);
                        label4 = new Label(14, 0, "切片医生");
                        ws4.addCell(label4);
                        label4 = new Label(15, 0, "诊断医生");
                        ws4.addCell(label4);
                        label4 = new Label(16, 0, "病理诊断");
                        ws4.addCell(label4);
                        label4 = new Label(17, 0, "免疫组化");
                        ws4.addCell(label4);
                        label4 = new Label(18, 0, "特殊染色");
                        ws4.addCell(label4);
                        label4 = new Label(19, 0, "分子病理");
                        ws4.addCell(label4);
                        String samsamplestatus = "已审核";
                        String myzhnum = "无";
                        String tsrsnum = "无";
                        String fzblnum = "无";
                        for(int i=0;i<list13.size();i++) {
                            String age = "未知";
                            map = list13.get(i);
                            switch (Integer.parseInt(String.valueOf(map.get("sampatientsex")))){
                                case 1:sex="男";
                                case 2:sex="女";
                                case 3:sex="未知";
                            }
                            if(Integer.parseInt(String.valueOf(map.get("myzhnum")))>0){
                                myzhnum = "有";
                            }
                            if(Integer.parseInt(String.valueOf(map.get("tsrsnum")))>0){
                                tsrsnum = "有";
                            }
                            if(Integer.parseInt(String.valueOf(map.get("fzblnum")))>0){
                                fzblnum = "有";
                            }
                            String cell1 = String.valueOf(map.get("sampatientage")).substring(String.valueOf(map.get("sampatientage")).length() - 1);
                            if(cell1.equals("1")){
                                cell1="岁";
                                age = String.valueOf(map.get("sampatientage")).substring(0,String.valueOf(map.get("sampatientage")).length() - 1)+cell1;
                            }else if(cell1.equals("2")){
                                cell1="月";
                                age = String.valueOf(map.get("sampatientage")).substring(0,String.valueOf(map.get("sampatientage")).length() - 1)+cell1;

                            }else if(cell1.equals("4")){
                                cell1="周";
                                age = String.valueOf(map.get("sampatientage")).substring(0,String.valueOf(map.get("sampatientage")).length() - 1)+cell1;

                            }else if(cell1.equals("5")){
                                cell1="日";
                                age = String.valueOf(map.get("sampatientage")).substring(0,String.valueOf(map.get("sampatientage")).length() - 1)+cell1;

                            }else if(cell1.equals("6")){
                                cell1="小时";
                                age = String.valueOf(map.get("sampatientage")).substring(0,String.valueOf(map.get("sampatientage")).length() - 1)+cell1;

                            }
                            label4 = new Label(0, i + 1, String.valueOf(map.get("sampathologyid")));//病种类别
                            ws4.addCell(label4);
                            label4 = new Label(1, i + 1, String.valueOf(map.get("sampathologycode")));//病理号
                            ws4.addCell(label4);
                            label4 = new Label(2, i + 1, String.valueOf(map.get("sampatientname")));//病人姓名
                            ws4.addCell(label4);
                            label4 = new Label(3, i + 1, age);//年龄
                            ws4.addCell(label4);
                            label4 = new Label(4, i + 1, String.valueOf(map.get("sampatientnumber")));//住院号
                            ws4.addCell(label4);
                            label4 = new Label(5, i + 1, String.valueOf(map.get("sampatientbed")));//床号
                            ws4.addCell(label4);
                            label4 = new Label(6, i + 1, sex);//性别
                            ws4.addCell(label4);
                            label4 = new Label(7, i + 1, samsamplestatus);//病理状态
                            ws4.addCell(label4);
                            label4 = new Label(8, i + 1, String.valueOf(map.get("samsendtime")));//送检日期
                            ws4.addCell(label4);
                            label4 = new Label(9, i + 1, String.valueOf(map.get("saminitiallytime")));//诊断日期
                            ws4.addCell(label4);
                            label4 = new Label(10, i + 1, String.valueOf(map.get("samsenddoctorname")));//诊断日期
                            ws4.addCell(label4);
                            label4 = new Label(11, i + 1, String.valueOf(map.get("samdeptname")));//诊断日期
                            ws4.addCell(label4);
                            label4 = new Label(12, i + 1, String.valueOf(map.get("samsendhospital")));//诊断日期
                            ws4.addCell(label4);
                            label4 = new Label(13, i + 1, String.valueOf(map.get("piedoctorname")));//诊断日期
                            ws4.addCell(label4);
                            label4 = new Label(14, i + 1, String.valueOf(map.get("parsectioneddoctor")));//诊断日期
                            ws4.addCell(label4);
                            label4 = new Label(15, i + 1, String.valueOf(map.get("saminitiallyusername")));//诊断日期
                            ws4.addCell(label4);
                            label4 = new Label(16, i + 1, String.valueOf(map.get("restestresult")));//诊断日期
                            ws4.addCell(label4);
                            label4 = new Label(17, i + 1, myzhnum);//诊断日期
                            ws4.addCell(label4);
                            label4 = new Label(18, i + 1, tsrsnum);//诊断日期
                            ws4.addCell(label4);
                            label4 = new Label(19, i + 1, fzblnum);//诊断日期
                            ws4.addCell(label4);
                        }
                        break;
                    case 6:WritableSheet ws5 = wwb.createSheet("每日工作统计表1",5);
                    break;
                    case 7:WritableSheet ws6 = wwb.createSheet("每日工作统计表1",6);
                    break;
                    case 8:WritableSheet ws7 = wwb.createSheet("每日工作统计表1",7);
                    break;
                    case 9:WritableSheet ws8 = wwb.createSheet("每日工作统计表1",8);
                    break;
                    case 10:WritableSheet ws9 = wwb.createSheet("每日工作统计表1",9);
                    break;
                    case 11:WritableSheet ws10 = wwb.createSheet("每日工作统计表1",10);
                    break;
                }
            }
            //**************往工作表中添加数据*****************


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

