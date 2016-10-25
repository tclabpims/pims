package com.pims.webapp.controller.pimssyspathology;

import com.pims.model.PimsSysReqMaterial;
import com.pims.service.pimssyspathology.PimsSysReqMaterialManager;
import com.pims.webapp.controller.GridQuery;
import com.pims.webapp.controller.PIMSBaseController;
import com.pims.webapp.controller.WebControllerUtil;
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
import java.util.List;

/**
 * Created by 909436637@qq.com on 2016/10/9.
 * Description:
 */
@Controller
@RequestMapping("/reqmaterial/*")
public class PimsSysReqMaterialController extends PIMSBaseController {

    @Autowired
    private PimsSysReqMaterialManager pimsSysReqMaterialManager;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView reqmaterial() throws java.lang.Exception {

        return new ModelAndView();
    }

    @RequestMapping(method = {RequestMethod.POST}, value = "/edit")
    @ResponseBody
    public void saveOrUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PimsSysReqMaterial pimsSysReqMaterial = (PimsSysReqMaterial)setBeanProperty(request, PimsSysReqMaterial.class);
        if(pimsSysReqMaterial.getMaterialid() == 0) {
            pimsSysReqMaterial.setMatcreatetime(new Date());
            pimsSysReqMaterial.setMatcreateuser(String.valueOf(WebControllerUtil.getAuthUser().getId()));
        } else {
            PimsSysReqMaterial hisPimsSysReqMaterial = pimsSysReqMaterialManager.get(pimsSysReqMaterial.getMaterialid());
            pimsSysReqMaterial.setMatcreateuser(hisPimsSysReqMaterial.getMatcreateuser());
            pimsSysReqMaterial.setMatcreatetime(hisPimsSysReqMaterial.getMatcreatetime());
        }
        pimsSysReqMaterialManager.save(pimsSysReqMaterial);
    }

    @RequestMapping(method = {RequestMethod.GET}, value = "/query")
    @ResponseBody
    public DataResponse query(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DataResponse dr = new DataResponse();
        GridQuery gridQuery = new GridQuery(request);
        List<PimsSysReqMaterial> result = pimsSysReqMaterialManager.getMaterialList(gridQuery);
        Integer total = pimsSysReqMaterialManager.countMaterial(gridQuery.getQuery());
        dr.setRecords(total);
        dr.setPage(gridQuery.getPage());
        dr.setTotal(getTotalPage(total, gridQuery.getRow(), gridQuery.getPage()));
        dr.setRows(getResultMap(result));
        response.setContentType(contentType);
        return dr;
    }

    @RequestMapping(method = {RequestMethod.POST}, value = "/remove")
    public void remove(HttpServletRequest request, HttpServletResponse response) throws Exception {
        pimsSysReqMaterialManager.remove(Long.valueOf(request.getParameter("materialid")));
    }

    @RequestMapping(method = {RequestMethod.GET}, value = "/data")
    @ResponseBody
    public void getReqMaterialById(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PimsSysReqMaterial material = pimsSysReqMaterialManager.get(Long.valueOf(request.getParameter("materialid")));
        PrintwriterUtil.print(response, getJSONObject(material).toString());
    }

    @RequestMapping(value = "/info", method = RequestMethod.POST)
    @ResponseBody
    public String getTestitemInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<PimsSysReqMaterial> list = pimsSysReqMaterialManager.getAllInfo();
        JSONArray array = new JSONArray();
        if (list != null) {
            for (PimsSysReqMaterial s : list) {
                JSONObject o = new JSONObject();
                o.put("id", s.getMaterialid());
                o.put("name", s.getMatname());
                array.put(o);
            }
        }
        response.setCharacterEncoding("UTF-8");
        //response.setContentType("text/html; charset=UTF-8");
        response.getWriter().write(array.toString());
        return null;
    }

}
