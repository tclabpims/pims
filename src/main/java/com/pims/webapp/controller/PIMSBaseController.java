package com.pims.webapp.controller;

import com.smart.webapp.util.DataResponse;
import org.apache.commons.beanutils.PropertyUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.beans.PropertyDescriptor;

import javax.servlet.http.HttpServletRequest;
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
                        System.out.println("name== "+ name + "propertyTypeName====" + propertyTypeName);
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
                                    pd.getWriteMethod().invoke(ins, new SimpleDateFormat().parse(value));
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