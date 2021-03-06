package com.pims.webapp.controller.pimssyspathology;

import com.alibaba.fastjson.JSONObject;
import com.pims.model.PimsBaseModel;
import com.pims.model.PimsSysColor;
import com.pims.model.PimsSysReportItems;
import com.pims.service.pimssyspathology.PimsSysColorManager;
import com.pims.webapp.controller.GridQuery;
import com.pims.webapp.controller.PIMSBaseController;
import com.pims.webapp.controller.WebControllerUtil;
import com.smart.webapp.util.DataResponse;
import com.smart.webapp.util.PrintwriterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * Created by 909436637@qq.com on 2016/10/10.
 * Description:
 */
@Controller
@RequestMapping("/syscolor/*")
public class PimsSysColorController extends PIMSBaseController {

    @Autowired
    private PimsSysColorManager pimsSysColorManager;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView syscolor(HttpServletRequest request) throws Exception {
        return  new ModelAndView();
    }

    @RequestMapping(method = {RequestMethod.POST}, value = "/save")
    @ResponseBody
    public void save(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PimsSysColor pimsSysColor = (PimsSysColor)setBeanProperty(request, PimsSysColor.class);
        boolean a = pimsSysColorManager.isExisted(pimsSysColor);
        JSONObject jj = new JSONObject();
        if(a) {
            jj.put("message","该对象已设置颜色！");
        }else {
            if (pimsSysColor.getColorid() == 0) {
                pimsSysColor.setColcreatetime(new Date());
                pimsSysColor.setColcreateuser(String.valueOf(WebControllerUtil.getAuthUser().getId()));
            }
            //else {
            //    PimsSysColor hisPimsSysColor = pimsSysColorManager.get(pimsSysColor.getColorid());
            //    pimsSysColor.setColcreateuser(hisPimsSysColor.getColcreateuser());
            //    pimsSysColor.setColcreatetime(hisPimsSysColor.getColcreatetime());
            //}
            pimsSysColor.setA();
            pimsSysColorManager.save(pimsSysColor);
            jj.put("message","保存成功！");

        }
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(jj.toString());
    }

    @RequestMapping(method = {RequestMethod.POST}, value = "/update")
    @ResponseBody
    public void Update(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PimsSysColor pimsSysColor = (PimsSysColor)setBeanProperty(request, PimsSysColor.class);
        JSONObject jj = new JSONObject();
        PimsSysColor hisPimsSysColor = pimsSysColorManager.get(pimsSysColor.getColorid());
        pimsSysColor.setColcreateuser(hisPimsSysColor.getColcreateuser());
        pimsSysColor.setColcreatetime(hisPimsSysColor.getColcreatetime());
        pimsSysColor.setA();
        pimsSysColorManager.save(pimsSysColor);
        jj.put("message","保存成功！");
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(jj.toString());
    }

    @RequestMapping(method = {RequestMethod.GET}, value = "/query")
    @ResponseBody
    public DataResponse query(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DataResponse dr = new DataResponse();
        GridQuery gridQuery = new GridQuery(request);
        List<PimsSysColor> result = pimsSysColorManager.getSysColorList(gridQuery);
        Integer total = pimsSysColorManager.countSysColor(gridQuery.getQuery());
        dr.setRecords(total);
        dr.setPage(gridQuery.getPage());
        dr.setTotal(getTotalPage(total, gridQuery.getRow(), gridQuery.getPage()));
        dr.setRows(getResultMap(result));
        response.setContentType(contentType);
        return dr;
    }

    @RequestMapping(method = {RequestMethod.POST}, value = "/remove")
    public void remove(HttpServletRequest request, HttpServletResponse response) throws Exception {
        pimsSysColorManager.remove(Long.valueOf(request.getParameter("colorid")));
    }

    @RequestMapping(method = {RequestMethod.GET}, value = "/data")
    @ResponseBody
    public void geSysColorById(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PimsSysColor sysColor = pimsSysColorManager.get(Long.valueOf(request.getParameter("colorid")));
        PrintwriterUtil.print(response, getJSONObject(sysColor).toString());
    }
}
