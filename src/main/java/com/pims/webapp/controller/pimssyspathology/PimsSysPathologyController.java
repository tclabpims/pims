package com.pims.webapp.controller.pimssyspathology;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pims.model.PimsSysPathology;
import com.pims.model.PimsSysReportFormate;
import com.pims.service.pimssyspathology.PimsSysPathologyManager;
import com.pims.service.pimssyspathology.PimsSysReportFormatManager;
import com.pims.webapp.controller.GridQuery;
import com.pims.webapp.controller.PIMSBaseController;
import com.pims.webapp.controller.WebControllerUtil;
import com.smart.webapp.util.DataResponse;
import com.smart.webapp.util.PrintwriterUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.servlet.cache.Method;
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
import java.util.Map;

/**
 * Created by 909436637@qq.com on 2016/9/28.
 * Description:病种类别维护controller
 */
@Controller
@RequestMapping("/pspathology/dcm*")
public class PimsSysPathologyController extends PIMSBaseController {

    private Log log = LogFactory.getLog(PimsSysPathologyController.class);

    @Autowired
    private PimsSysPathologyManager pimsSysPathologyManager;

    @Autowired
    private PimsSysReportFormatManager pimsSysReportFormatManager;



    /**
     * 显示病种维护主页面
     */
    @RequestMapping(method = {RequestMethod.GET})
    public ModelAndView handleRequest(HttpServletRequest request) throws java.lang.Exception {
        return new ModelAndView();
    }

    /**
     * 查询病种信息
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(method = {RequestMethod.GET}, value = "/query")
    @ResponseBody
    public DataResponse queryPsPathology(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DataResponse dr = new DataResponse();
        GridQuery gridQuery = new GridQuery(request);
        List<PimsSysPathology> result = pimsSysPathologyManager.getPimsSysPathologyList(gridQuery);
        Integer total = pimsSysPathologyManager.getPimsSysPathologyTotal(gridQuery.getQuery());
        dr.setRecords(total);
        dr.setPage(gridQuery.getPage());
        dr.setTotal(getTotalPage(total, gridQuery.getRow(), gridQuery.getPage()));
        dr.setRows(getResultMap(result));
        response.setContentType(contentType);
        return dr;
    }

    @RequestMapping(method = {RequestMethod.GET}, value = "/cpt")
    @ResponseBody
    public void changePathologyType(HttpServletRequest request, HttpServletResponse response) throws Exception {
        WebControllerUtil.getAuthUser().getUserBussinessRelate().setPathologyLibId(request.getParameter("pid"));
    }

    @RequestMapping(method = {RequestMethod.GET}, value = "/querytype")
    @ResponseBody
    public void queryPathologyType(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PrintwriterUtil.print(response, pimsSysPathologyManager.getPathologyType().toJSONString());
    }

    @RequestMapping(method = {RequestMethod.GET}, value = "/queryReportFormat")
    @ResponseBody
    public DataResponse queryReportFormat(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DataResponse dr = new DataResponse();
        GridQuery gridQuery = new GridQuery(request);
        List<PimsSysReportFormate> result = pimsSysReportFormatManager.getPimsSysReportFormatList(gridQuery);
        Integer total = pimsSysReportFormatManager.getPimsSysReportFormat(gridQuery.getQuery());
        dr.setRecords(total);
        dr.setPage(gridQuery.getPage());
        dr.setTotal(getTotalPage(total, gridQuery.getRow(), gridQuery.getPage()));
        dr.setRows(getResultMap(result));
        response.setContentType(contentType);
        return dr;
    }

    /**
     * 新增病种信息
     *
     * @PARAM RESPONSE
     * @PARAM PATHOLOGY
     * @RETURN
     */
    @RequestMapping(method = {RequestMethod.POST}, value = "/edit")
    @ResponseBody
    public void saveOrUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PimsSysPathology pathology = (PimsSysPathology) setBeanProperty(request, PimsSysPathology.class);
        if (pathology.getPathologyid() == 0) {
            pathology.setPatcreatetime(new java.util.Date());
            pathology.setPatcreateuser(String.valueOf(WebControllerUtil.getAuthUser().getId()));
        } else {
            PimsSysPathology hisPathology = pimsSysPathologyManager.get(pathology.getPathologyid());
            pathology.setPatcreateuser(hisPathology.getPatcreateuser());
            pathology.setPatcreatetime(hisPathology.getPatcreatetime());
        }
        pimsSysPathologyManager.save(pathology);
    }

    @RequestMapping(method={RequestMethod.POST}, value="/editFormat")
    @ResponseBody
    public void pathologyFormatOperate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PimsSysReportFormate reportFormate = (PimsSysReportFormate)setBeanProperty(request, PimsSysReportFormate.class);
        if(reportFormate.getFormateid() == 0) {
            reportFormate.setFormcreatetime(new Date());
            reportFormate.setFormcreateuser(String.valueOf(WebControllerUtil.getAuthUser().getId()));
        } else {
            PimsSysReportFormate formate = pimsSysReportFormatManager.get(reportFormate.getFormateid());
            reportFormate.setFormcreateuser(formate.getFormcreateuser());
            reportFormate.setFormcreatetime(formate.getFormcreatetime());
        }
        pimsSysReportFormatManager.save(reportFormate);
    }

    /**
     * 删除病种信息
     *
     * @param request
     * @param response
     */
    @RequestMapping(method = {RequestMethod.POST}, value = "/remove")
    public void removePsPathology(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long pathologyid= Long.valueOf(request.getParameter("pathologyid"));
        pimsSysPathologyManager.remove(pathologyid);
        pimsSysReportFormatManager.removeReportData(pathologyid);
    }

    /**
     * 按照病种编号查询病种
     */
    @RequestMapping(method = {RequestMethod.GET}, value = "/data")
    @ResponseBody
    public void getPsPathologyById(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PimsSysPathology pathology = pimsSysPathologyManager.get(Long.valueOf(request.getParameter("pathologyid")));
        PrintwriterUtil.print(response, getJSONObject(pathology).toString());
    }

    /**
     * 按照病种编号查询病种
     */
    @RequestMapping(method = {RequestMethod.GET}, value = "/reportData")
    @ResponseBody
    public void getReportData(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PimsSysReportFormate formate = pimsSysReportFormatManager.get(Long.valueOf(request.getParameter("formateid")));
        PrintwriterUtil.print(response, getJSONObject(formate).toString());
    }

    @RequestMapping(method = {RequestMethod.POST}, value = "/removeReport")
    public void removeReportFormat(HttpServletRequest request, HttpServletResponse response) throws Exception {
        pimsSysReportFormatManager.remove(Long.valueOf(request.getParameter("formateid")));
    }
}
