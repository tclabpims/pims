package com.pims.webapp.controller.pimssyspathology;

import com.alibaba.fastjson.JSONObject;
import com.pims.model.PimsSysReqField;
import com.pims.service.pimssyspathology.PimsSysReqFieldManager;
import com.pims.webapp.controller.GridQuery;
import com.pims.webapp.controller.PIMSBaseController;
import com.pims.webapp.controller.WebControllerUtil;
import com.smart.webapp.util.DataResponse;
import org.apache.commons.collections.map.HashedMap;
import org.codehaus.jettison.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 909436637@qq.com on 2016/10/8.
 * Description:
 */
@Controller
@RequestMapping("/reqfield/*")
public class PimsSysReqFieldController extends PIMSBaseController {

    @Autowired
    private PimsSysReqFieldManager psrm;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView reqfield() throws java.lang.Exception {

        return new ModelAndView();
    }


    @RequestMapping(method = {RequestMethod.POST}, value = "/edit")
    @ResponseBody
    public Map<String, Object> saveOrUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType(contentType);
        PimsSysReqField pimsSysReqField = (PimsSysReqField) setBeanProperty(request, PimsSysReqField.class);
        if (pimsSysReqField.getFieldid() == 0) {
            pimsSysReqField.setFiecreatetime(new java.util.Date());
            pimsSysReqField.setFiecreateuser(String.valueOf(WebControllerUtil.getAuthUser().getId()));
        } else {
            PimsSysReqField hisPimsSysReqField = psrm.get(pimsSysReqField.getFieldid());
            pimsSysReqField.setFiecreateuser(hisPimsSysReqField.getFiecreateuser());
            pimsSysReqField.setFiecreatetime(hisPimsSysReqField.getFiecreatetime());
        }
        pimsSysReqField = psrm.save(pimsSysReqField);
        Map<String, Object> jsonObject = new HashMap<>();
        jsonObject.put("id", pimsSysReqField.getFieldid());
        jsonObject.put("pId", pimsSysReqField.getFiepelementid());
        jsonObject.put("name", pimsSysReqField.getFieelementname());
        jsonObject.put("open", "true");
        return jsonObject;
    }


    @RequestMapping(method = {RequestMethod.POST}, value = "/fieldconfig")
    @ResponseBody
    public Map<String, Object> fieldConfig(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType(contentType);
        PimsSysReqField pimsSysReqField = psrm.get(Long.valueOf(request.getParameter("fieldid")));
        Map<String, Object> jsonObject = new HashMap<>();
        jsonObject.put("fieelementtype", pimsSysReqField.getFieelementtype());
        jsonObject.put("fieelementid", pimsSysReqField.getFieelementid());
        jsonObject.put("fieelementname", pimsSysReqField.getFieelementname());
        jsonObject.put("fiepelementid", pimsSysReqField.getFiepelementid());
        jsonObject.put("fiedefaultvalue", pimsSysReqField.getFiedefaultvalue());
        jsonObject.put("fieshoworder", pimsSysReqField.getFieshoworder());
        jsonObject.put("fieuseflag", pimsSysReqField.getFieuseflag());
        jsonObject.put("fieremark", pimsSysReqField.getFieremark());
        jsonObject.put("fieshowlevel", pimsSysReqField.getFieshowlevel());

        return jsonObject;
    }

    @RequestMapping(method = {RequestMethod.POST}, value = "/remove")
    public void removePsPathology(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String mid = request.getParameter("id");
        psrm.deleteFields(mid);
    }

    @RequestMapping(method = {RequestMethod.GET}, value = "/query")
    @ResponseBody
    public DataResponse query(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DataResponse dr = new DataResponse();
        GridQuery gridQuery = new GridQuery(request);
        List<PimsSysReqField> result = psrm.getReqFieldList(gridQuery);
        Integer total = psrm.countReqField(gridQuery.getQuery());
        dr.setRecords(total);
        dr.setPage(gridQuery.getPage());
        dr.setTotal(getTotalPage(total, gridQuery.getRow(), gridQuery.getPage()));
        dr.setRows(getResultMap(result));
        response.setContentType(contentType);
        return dr;
    }

    @RequestMapping(method = {RequestMethod.GET}, value = "/treequery")
    @ResponseBody
    public List<Map<String, Object>> treeQuery(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType(contentType);
        List<Map<String, Object>> list = new ArrayList<>();
        List<PimsSysReqField> result = psrm.getAll();
        Map<String, Object> root = new HashedMap();
        root.put("id", 0L);
        root.put("name", "Root(根目录)");
        root.put("open", "true");
        list.add(root);
        for (PimsSysReqField psrf : result) {
            Map<String, Object> jsonObject = new HashMap<>();
            jsonObject.put("id", psrf.getFieldid());
            jsonObject.put("pId", psrf.getFiepelementid());
            jsonObject.put("name", psrf.getFieelementname());
            jsonObject.put("open", "true");
            list.add(jsonObject);
        }
        return list;
    }
}
