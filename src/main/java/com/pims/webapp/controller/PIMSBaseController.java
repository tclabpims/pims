package com.pims.webapp.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
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
import org.apache.commons.lang3.StringEscapeUtils;
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

    protected final String contentType = "application/json; charset=UTF-8";

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
//        User user = WebControllerUtil.getAuthUser();
        UserBussinessRelate ubr = user.getUserBussinessRelate();
        ApplicationContext ctx= WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
        PimsSysPathologyManager pimsSysPathologyManager = (PimsSysPathologyManager) ctx.getBean("pimsSysPathologyManager");
        com.alibaba.fastjson.JSONArray items = pimsSysPathologyManager.getPathologyType();
        StringBuilder builder = new StringBuilder();
        for(Object obj : items) {
            com.alibaba.fastjson.JSONObject o = (com.alibaba.fastjson.JSONObject) obj;
            builder.append("<option value='").append(o.get("pathologyLibId")).append("' ");
            if(ubr.getPathologyLibId().equals(String.valueOf(o.get("pathologyLibId")))) {
                builder.append(" selected = 'selected' ");
            }
            builder.append(">").append(o.get("pathologyLib")).append("</option>");
        }
        ModelAndView view = new ModelAndView();
        view.addObject("logyid",logylibid);//当前用户选择的病例库
        view.addObject("sevenday", sevenDay);//7天前
        view.addObject("receivetime", today);//当前时间
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
            if (ubr.getPathologyLibId().equals(String.valueOf(o.get("pathologyLibId")))) {
                builder.append(" selected ");
            }
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
                                try {
                                    if(value.length() == 19){
                                        pd.getWriteMethod().invoke(ins, Constants.SDF.parse(value));
                                    }else if(value.length() == 10){
                                        pd.getWriteMethod().invoke(ins,Constants.DF2.parse(value));
                                    }else if(value.length() == 8){
                                        pd.getWriteMethod().invoke(ins, Constants.DF3.parse(value));
                                    }
                                } catch (ParseException e) {
                                    e.printStackTrace();
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

}
