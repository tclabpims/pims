package com.pims.webapp.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.pims.model.PimsCommonBaseData;
import com.pims.model.PimsHospitalPathologyInfo;
import com.pims.model.PimsPathologySample;
import com.pims.model.PimsSysPathology;
import com.pims.service.pimspathologysample.PimsPathologySampleManager;
import com.pims.service.pimssyspathology.PimsHospitalPathologyInfoManager;
import com.pims.service.pimssyspathology.PimsSysPathologyManager;
import com.pims.webapp.util.VerificaDate;
import com.smart.Constants;
import com.smart.model.BaseObject;
import com.smart.model.user.User;
import com.smart.model.user.UserBussinessRelate;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.beans.PropertyDescriptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.apache.commons.beanutils.PropertyUtils.getPropertyDescriptors;

/**
 * Created by 909436637@qq.com on 2016/9/30.
 * Description: This class is used to do something about controller,e.g assemble parameters .
 */
public class PIMSBaseController {
    @Autowired
    private PimsHospitalPathologyInfoManager pimsHospitalPathologyInfoManager;
    @Autowired
    private PimsPathologySampleManager pimsPathologySampleManager;

    protected final String contentType = "application/json; charset=UTF-8";

    private static Log log = LogFactory.getLog(PIMSBaseController.class);

    private Date start;

    private Date end;

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public String getOptions(List<PimsCommonBaseData> listbase){
        StringBuilder builder = new StringBuilder();
        builder.append("<option value=''></option>");
        for(PimsCommonBaseData obj : listbase) {
            builder.append("<option value='").append(obj.getDataid()).append("' ");
            builder.append(">").append(obj.getIsself()+":"+obj.getBddatanamech()).append("</option>");
        }
        return builder.toString();
    }

    public String getOptions(List<PimsCommonBaseData> listbase,ModelAndView view){
        StringBuilder builder = new StringBuilder();
        builder.append("<option value=''></option>");
        for(PimsCommonBaseData obj : listbase) {
            builder.append("<option value='").append(obj.getDataid()).append("' ");
            if(obj.getIsself() == 0) {
                builder.append(" selected = 'selected' ");
                if(obj.getBddatatype() == 1){//病区
                    view.addObject("reqwardname1",obj.getBddatanamech());
                    view.addObject("reqwardcode1",obj.getDataid());
                }else if(obj.getBddatatype() == 2){//送检科室
                    view.addObject("samdeptname1",obj.getBddatanamech());
                    view.addObject("samdeptcode1",obj.getDataid());
                }else if(obj.getBddatatype() == 3){//送检医生
                    view.addObject("samsenddoctorname1",obj.getBddatanamech());
                    view.addObject("samsenddoctorcode1",obj.getDataid());
                }else if(obj.getBddatatype() == 4){//送检医院
                    view.addObject("samsendhospital1",obj.getBddatanamech());
                    view.addObject("samsendhospitalcode1",obj.getDataid());
                }
            }
            builder.append(">").append(obj.getIsself()+":"+obj.getBddatanamech()).append("</option>");
        }
        return builder.toString();
    }

    protected List<File> multifileUpload(MultipartFile[] imgFile, String path, String sampleId) throws IOException {
        List<File> ret = new ArrayList<>();
        File savePath = new File(path);
        if(!savePath.exists())savePath.mkdirs();
        for(MultipartFile file : imgFile) {
            if(!file.isEmpty()) {
                String fileName = file.getOriginalFilename();
                String name = fileName.substring(0, fileName.lastIndexOf(".")-1);
                String type = fileName.substring(fileName.lastIndexOf("."));
                File out = new File(path, sampleId + "_" + name + "_" + (savePath.list().length+1)+type);
                FileUtils.copyInputStreamToFile(file.getInputStream(), out);
                ret.add(out);
            }
        }
        return ret;
    }

    protected String getRemoteHost(HttpServletRequest request){
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getRemoteAddr();
        }
        return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
    }

    public ModelAndView getmodelView(HttpServletRequest request) throws  Exception{
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
        ModelAndView view = new ModelAndView();
        view.addObject("logyid",logylibid);//当前用户选择的病例库
        String id = request.getParameter("id");
        for(PimsSysPathology obj : items) {
            builder.append("<option value='").append(obj.getPathologyid()).append("' ");
            if(StringUtils.isEmpty(id)){
                if((String.valueOf(user.getUserBussinessRelate().getPathologyLibId())).equals(String.valueOf(obj.getPathologyid()))) {
                    builder.append(" selected = 'selected' ");
                }
            }
            builder.append(">").append(obj.getPatnamech()).append("</option>");
        }
        if(!StringUtils.isEmpty(id)){
            PimsPathologySample pathology = pimsPathologySampleManager.get(Long.parseLong(id));
            view.addObject("sevenday", "");//7天前
            view.addObject("receivetime", "");//当前时间
            view.addObject("code", pathology.getSampathologycode());//病理号
        }else{
            view.addObject("sevenday", sevenDay);//7天前
            view.addObject("receivetime", today);//当前时间
        }
        view.addObject("local_username",user.getName());//当前登录用户名
        view.addObject("local_userid",user.getId());//当前登录用户id
        view.addObject("send_hosptail",user.getHospitalId());//账号所属医院
        view.addObject("logyids",builder.toString());
        return view;
    }

    public String getPathologySelectOption(HttpServletRequest request) {
        User user = WebControllerUtil.getAuthUser();
        UserBussinessRelate ubr = user.getUserBussinessRelate();
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
        PimsSysPathologyManager pimsSysPathologyManager = (PimsSysPathologyManager) ctx.getBean("pimsSysPathologyManager");
        JSONArray items = pimsSysPathologyManager.getPathologyType();
        StringBuilder result = new StringBuilder();
        for (Object obj : items) {
            JSONObject o = (JSONObject) obj;
            StringBuilder builder = new StringBuilder();
            builder.append("<option value=\"")
                    .append(o.get("pathologyLibId"))
                    .append("\" ");
            if (ubr.getPathologyLibId()!= null && ubr.getPathologyLibId().equals((o.get("pathologyLibId")))) {
                builder.append(" selected ");
            }
            builder.append(">")
                    .append(o.get("pathologyLib"))
                    .append("</option>\n");
            result.append(builder);
        }
        return StringEscapeUtils.escapeHtml4(result.toString());
    }


    public String getPathologyOption(HttpServletRequest request) {
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
        PimsSysPathologyManager pimsSysPathologyManager = (PimsSysPathologyManager) ctx.getBean("pimsSysPathologyManager");
        JSONArray items = pimsSysPathologyManager.getPathologyType();
        StringBuilder result = new StringBuilder();
        for (Object obj : items) {
            JSONObject o = (JSONObject) obj;
            StringBuilder builder = new StringBuilder();
            builder.append("<option value=\"")
                    .append(o.get("pathologyLibId"))
                    .append("\" ");
            builder.append(">")
                    .append(o.get("pathologyLib"))
                    .append("</option>\n");
            result.append(builder);
        }
        return StringEscapeUtils.escapeHtml4(result.toString());
    }

    protected List<Map<String, Object>> getResultMap(List<?> result) throws Exception {
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        if(result == null || result.size() == 0) return mapList;
        for(Object bean : result) {
            Map<String, Object> map = new HashMap<String, Object>();
            PropertyDescriptor[] pd = PropertyUtils.getPropertyDescriptors(bean);
            for(PropertyDescriptor pdp : pd) {
                if("class".equals(pdp.getName()))continue;
                map.put(pdp.getName(), pdp.getReadMethod().invoke(bean, new Object[0]));
            }
            mapList.add(map);
        }
        return mapList;
    }

    protected String getResultJsons(List<?> result) throws Exception {
        org.codehaus.jettison.json.JSONArray mapList = new org.codehaus.jettison.json.JSONArray();
        if(result == null || result.size() == 0) return "";
        for(Object o : result) {
            org.codehaus.jettison.json.JSONObject map = new org.codehaus.jettison.json.JSONObject();
            Object[] oo = (Object[]) o;
            for(Object bean : oo) {
                PropertyDescriptor[] pd = PropertyUtils.getPropertyDescriptors(bean);
                for(PropertyDescriptor pdp : pd) {
                    if("class".equals(pdp.getName()))continue;
                    map.put(pdp.getName(), pdp.getReadMethod().invoke(bean, new Object[0]));
                }
            }
            mapList.put(map);
        }
        return mapList.toString();
    }

    protected String getResultJson(List<?> result) throws Exception {
        org.codehaus.jettison.json.JSONArray mapList = new org.codehaus.jettison.json.JSONArray();
        if(result == null || result.size() == 0) return "";
        for(Object bean : result) {
            org.codehaus.jettison.json.JSONObject map = new org.codehaus.jettison.json.JSONObject();
            PropertyDescriptor[] pd = PropertyUtils.getPropertyDescriptors(bean);
            for(PropertyDescriptor pdp : pd) {
                if("class".equals(pdp.getName()))continue;
                map.put(pdp.getName(), pdp.getReadMethod().invoke(bean, new Object[0]));
            }
            mapList.put(map);
        }
        return mapList.toString();
    }

    protected Map<String, Object> getResultMap(BaseObject bean) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        PropertyDescriptor[] pd = PropertyUtils.getPropertyDescriptors(bean);
        for(PropertyDescriptor pdp : pd) {
            if("class".equals(pdp.getName()))continue;
            map.put(pdp.getName(), pdp.getReadMethod().invoke(bean, new Object[0]));
        }
        return map;
    }

    protected List<Map<String, Object>> getResultMaps(List<?> result) throws Exception {
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        if(result == null || result.size() == 0) return mapList;
        for(Object o:result){
            Object[] oo = (Object[]) o;
            Map<String, Object> map = new HashMap<String, Object>();
            for(Object bean : oo) {
                PropertyDescriptor[] pd = PropertyUtils.getPropertyDescriptors(bean);
                for(PropertyDescriptor pdp : pd) {
                    if("class".equals(pdp.getName()))continue;
                    map.put(pdp.getName(), pdp.getReadMethod().invoke(bean, new Object[0]));
                }
            }
            mapList.add(map);
        }
        return mapList;
    }

    public JSONObject getJSONObject(Object o) throws InvocationTargetException, IllegalAccessException {
        JSONObject jo = new JSONObject();
        PropertyDescriptor[] pd = PropertyUtils.getPropertyDescriptors(o);
        for(PropertyDescriptor pdp : pd) {
            if("class".equals(pdp.getName()))continue;
            try {
                jo.put(pdp.getName(), pdp.getReadMethod().invoke(o, new Object[0]));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jo;
    }

    protected int getTotalPage(int size, int row, int page) {
        int x = size % (row == 0 ? size : row);
        if (x != 0) {
            x = row - x;
        }
        return (size + x) / (row == 0 ? size : row);
    }

    /**
     * Set request parameter value to pojo,avoid some repetitive work
     * @param request HttpServletRequest obj
     * @param clazz simple pojo class
     * @return pojo instance with property value
     */
    protected Object setBeanProperty(HttpServletRequest request, Class clazz) {
        Object ins = null;
        try {
            ins = clazz.newInstance();
            Map<String, String[]> parameterEntry = request.getParameterMap();
            try {
                if (parameterEntry.size() > 0) {
                    Set<String> paramsNames = parameterEntry.keySet();
                    for (String name : paramsNames) {
                        PropertyDescriptor pd = PropertyUtils.getPropertyDescriptor(ins, name);
                        if(pd == null){
                            continue;
                        }
                        String propertyTypeName = pd.getPropertyType().getName();
                        String value = parameterEntry.get(name)[0];
                        if(value != null && !value.trim().equals("")){
                            if (propertyTypeName.equals(int.class.getName())
                                    || propertyTypeName.equals(Integer.class.getName())) {
                                pd.getWriteMethod().invoke(ins, Integer.valueOf(value));
                            } else if (propertyTypeName.equals(long.class.getName())
                                    || propertyTypeName.equals(Long.class.getName())) {
                                pd.getWriteMethod().invoke(ins, Long.valueOf(value));
                            } else if (propertyTypeName.equals(double.class.getName())
                                    || propertyTypeName.equals(Double.class.getName())) {
                                pd.getWriteMethod().invoke(ins, Double.valueOf(value));
                            } else if (propertyTypeName.equals(float.class.getName())
                                    || propertyTypeName.equals(Float.class.getName())) {
                                pd.getWriteMethod().invoke(ins, Float.valueOf(value));
                            } else if (propertyTypeName.equals(byte.class.getName())
                                    || propertyTypeName.equals(Byte.class.getName())) {
                                pd.getWriteMethod().invoke(ins, Byte.valueOf(value));
                            } else if (propertyTypeName.equals(short.class.getName())
                                    || propertyTypeName.equals(Short.class.getName())) {
                                pd.getWriteMethod().invoke(ins, Short.valueOf(value));
                            } else if (propertyTypeName.equals(char[].class.getName())
                                    || propertyTypeName.equals(Character[].class.getName())) {
                                pd.getWriteMethod().invoke(ins, (Object) value.toCharArray());
                            } else if (propertyTypeName.equals(boolean.class.getName())
                                    || propertyTypeName.equals(Boolean.class.getName())) {
                                pd.getWriteMethod().invoke(ins, Boolean.valueOf(value));
                            } else if (propertyTypeName.equals(String.class.getName())) {
                                pd.getWriteMethod().invoke(ins, value);
                            } else if (propertyTypeName.equals(java.util.Date.class.getName())) {
                                if(!VerificaDate.verificationOfDateIsCorrect(value)) continue;
                                Set<String> patternSet = Constants.patternSet;
                                for(String p : patternSet) {
                                    if(value.length()==p.length()) {
                                        SimpleDateFormat sdf = new SimpleDateFormat(p);
                                        try {
                                            pd.getWriteMethod().invoke(ins, sdf.parse(value));
                                        } catch (ParseException e) {
                                            log.error(e.getCause().getMessage());
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }

                    }
                }
            } catch (InvocationTargetException | NoSuchMethodException e) {
                log.error(e.getCause().getMessage());
                e.printStackTrace();
            }
        } catch (InstantiationException | IllegalAccessException e) {
            log.error(e.getCause().getMessage());
            e.printStackTrace();
        }
        return ins;
    }

    protected Object setBeanProperty(Map map, Class clazz) {
        Object ins = null;
        try {
            ins = clazz.newInstance();
            try {
                if (map.size() > 0) {
                    Set<String> paramsNames = map.keySet();
                    for (String name : paramsNames) {
                        PropertyDescriptor pd = PropertyUtils.getPropertyDescriptor(ins, name);
                        if(pd == null){
                            continue;
                        }
                        String propertyTypeName = pd.getPropertyType().getName();
                        String value = (String) map.get(name);
                        if(value != null && !value.trim().equals("")){
                            if (propertyTypeName.equals(int.class.getName())
                                    || propertyTypeName.equals(Integer.class.getName())) {
                                pd.getWriteMethod().invoke(ins, Integer.valueOf(value));
                            } else if (propertyTypeName.equals(long.class.getName())
                                    || propertyTypeName.equals(Long.class.getName())) {
                                pd.getWriteMethod().invoke(ins, Long.valueOf(value));
                            } else if (propertyTypeName.equals(double.class.getName())
                                    || propertyTypeName.equals(Double.class.getName())) {
                                pd.getWriteMethod().invoke(ins, Double.valueOf(value));
                            } else if (propertyTypeName.equals(float.class.getName())
                                    || propertyTypeName.equals(Float.class.getName())) {
                                pd.getWriteMethod().invoke(ins, Float.valueOf(value));
                            } else if (propertyTypeName.equals(byte.class.getName())
                                    || propertyTypeName.equals(Byte.class.getName())) {
                                pd.getWriteMethod().invoke(ins, Byte.valueOf(value));
                            } else if (propertyTypeName.equals(short.class.getName())
                                    || propertyTypeName.equals(Short.class.getName())) {
                                pd.getWriteMethod().invoke(ins, Short.valueOf(value));
                            } else if (propertyTypeName.equals(char[].class.getName())
                                    || propertyTypeName.equals(Character[].class.getName())) {
                                pd.getWriteMethod().invoke(ins, (Object) value.toCharArray());
                            } else if (propertyTypeName.equals(boolean.class.getName())
                                    || propertyTypeName.equals(Boolean.class.getName())) {
                                pd.getWriteMethod().invoke(ins, Boolean.valueOf(value));
                            } else if (propertyTypeName.equals(String.class.getName())) {
                                pd.getWriteMethod().invoke(ins, value);
                            } else if (propertyTypeName.equals(java.util.Date.class.getName())) {
                                if(!VerificaDate.verificationOfDateIsCorrect(value)) continue;
                                Set<String> patternSet = Constants.patternSet;
                                for(String p : patternSet) {
                                    if(value.length()==p.length()) {
                                        SimpleDateFormat sdf = new SimpleDateFormat(p);
                                        try {
                                            pd.getWriteMethod().invoke(ins, sdf.parse(value));
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }

                    }
                }
            } catch (InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return ins;
    }

    public PimsHospitalPathologyInfo searchCodeValue(PimsHospitalPathologyInfo phi){
        String rexpre =  phi.getRegularExpression();//病理编号的生成规则正则
        long nextnumber = phi.getNextNumber();//当前值
        String numberprefix = phi.getNumberPrefix()==null?"":phi.getNumberPrefix();//前缀
        phi.setNumberPrefix(numberprefix);
        if(rexpre == null || rexpre.equals("")){
            nextnumber += 1;
        }else{
            String[] rexpres = rexpre.split("\\|D");
            if(rexpres.length == 1 || rexpres[0].equals("")){
                nextnumber += 1;
            }else{
                SimpleDateFormat sdf = new SimpleDateFormat(rexpres[0]);
                String newlong = sdf.format(new Date());
                String oldlong = Long.toString(nextnumber).substring(0,rexpres[0].length());
                if(newlong.equals(oldlong)){
                    nextnumber += 1;
                }else{
                    int num = Integer.parseInt(rexpres[1]);
                    nextnumber = (long) (Long.parseLong(newlong)*(Math.pow(10,num)) + 1);
                }
            }
        }
        phi.setNextNumber(nextnumber);
        return phi;
    }
}
