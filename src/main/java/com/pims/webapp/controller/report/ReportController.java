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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
}
