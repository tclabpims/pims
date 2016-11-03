package com.pims.webapp.controller.pimssyspathology;

import com.pims.model.PimsPathologyTemplate;
import com.pims.service.pimssyspathology.PimsPathologyTemplateManager;
import com.pims.webapp.controller.GridQuery;
import com.pims.webapp.controller.PIMSBaseController;
import com.pims.webapp.controller.WebControllerUtil;
import com.smart.model.user.User;
import com.smart.webapp.util.DataResponse;
import com.smart.webapp.util.PrintwriterUtil;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 909436637@qq.com on 2016/10/6.
 * Description:
 */
@Controller
@RequestMapping("/template/*")
public class PimsPathologyTemplateController extends PIMSBaseController {

    @Autowired
    private PimsPathologyTemplateManager pptm;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView template()  throws java.lang.Exception {
        return new ModelAndView();
    }

    @RequestMapping(value = "/pathologyGrid*",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ModelAndView showPathologyGrid( HttpServletRequest request, HttpServletResponse response) throws Exception{
        return new ModelAndView("template/pathologyGrid");
    }

    @RequestMapping(method = {RequestMethod.POST}, value = "/edit")
    @ResponseBody
    public void saveOrUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PimsPathologyTemplate ppt = (PimsPathologyTemplate)setBeanProperty(request, PimsPathologyTemplate.class);
        if(ppt.getTemplateid() == 0) {
            ppt.setTemcreatetime(new Date());
            ppt.setTemcreateuser(String.valueOf(WebControllerUtil.getAuthUser().getId()));
        } else {
            PimsPathologyTemplate hisPpt = pptm.get(ppt.getTemplateid());
            ppt.setTemcreateuser(hisPpt.getTemcreateuser());
            ppt.setTemcreatetime(hisPpt.getTemcreatetime());
        }
        pptm.save(ppt);
    }

    @RequestMapping(method = {RequestMethod.GET}, value = "/query")
    @ResponseBody
    public DataResponse query(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DataResponse dr = new DataResponse();
        GridQuery gridQuery = new GridQuery(request);
        List<PimsPathologyTemplate> result = pptm.getTemplateList(gridQuery);
        Integer total = pptm.countTemplate(gridQuery.getQuery());
        dr.setRecords(total);
        dr.setPage(gridQuery.getPage());
        dr.setTotal(getTotalPage(total, gridQuery.getRow(), gridQuery.getPage()));
        dr.setRows(getResultMap(result));
        response.setContentType(contentType);
        return dr;
    }

    @RequestMapping(method = {RequestMethod.POST}, value = "/remove")
    public void remove(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long templateId = Long.valueOf(request.getParameter("templateid"));
        pptm.remove(templateId);
    }

    @RequestMapping(method = {RequestMethod.GET}, value = "/data")
    @ResponseBody
    public void getTemplateById(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PimsPathologyTemplate template = pptm.get(Long.valueOf(request.getParameter("templateid")));
        PrintwriterUtil.print(response, getJSONObject(template).toString());
    }

    @RequestMapping(value = "/ajax/item*", method = RequestMethod.GET)
    @ResponseBody
    public String getTestitemInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String name = request.getParameter("name")==null?"":request.getParameter("name").toString();
        String sampathologyid = request.getParameter("sampathologyid")==null?"":request.getParameter("sampathologyid").toString();
        Map map = new HashMap();
        map.put("name",name.toUpperCase());
        map.put("sampathologyid",sampathologyid);
        List<PimsPathologyTemplate> list = pptm.getDataList(map);
        JSONArray array = new JSONArray();
        if (list != null) {
            for (PimsPathologyTemplate s : list) {
                JSONObject o = new JSONObject();
                o.put("id", s.getTemkey());
                o.put("name", s.getTemcontent());
                array.put(o);
            }
        }
        //response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(array.toString());
        return null;
    }
}
