package com.pims.webapp.controller.pimssyspathology;

import com.pims.model.PimsPathologyTemplate;
import com.pims.model.PimsSysReportItems;
import com.pims.service.pimssyspathology.PimsSysReportItemsManager;
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
@RequestMapping("/reportitem/*")
public class PimsSysReportItemsController extends PIMSBaseController {

    @Autowired
    private PimsSysReportItemsManager pimsSysReportItemsManager;

    @RequestMapping(method = {RequestMethod.GET})
    public ModelAndView reportitem(HttpServletRequest request) throws java.lang.Exception {
        return new ModelAndView();
    }

    @RequestMapping(method = {RequestMethod.POST}, value = "/edit")
    @ResponseBody
    public void saveOrUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PimsSysReportItems pimsSysReportItems = (PimsSysReportItems)setBeanProperty(request, PimsSysReportItems.class);
        if(pimsSysReportItems.getReportitemid() == 0) {
            pimsSysReportItems.setRptcreatetime(new Date());
            pimsSysReportItems.setRptcreateuser(String.valueOf(WebControllerUtil.getAuthUser().getId()));
        } else {
            PimsSysReportItems hisPimsSysReportItems = pimsSysReportItemsManager.get(Long.valueOf(request.getParameter("reportitemid")));
            pimsSysReportItems.setRptcreateuser(hisPimsSysReportItems.getRptcreateuser());
            pimsSysReportItems.setRptcreatetime(hisPimsSysReportItems.getRptcreatetime());
        }
        pimsSysReportItemsManager.save(pimsSysReportItems);
    }

    @RequestMapping(method = {RequestMethod.GET}, value = "/query")
    @ResponseBody
    public DataResponse query(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DataResponse dr = new DataResponse();
        GridQuery gridQuery = new GridQuery(request);
        List<PimsSysReportItems> result = pimsSysReportItemsManager.getReportItemList(gridQuery);
        Integer total = pimsSysReportItemsManager.countReportItem(gridQuery.getQuery());
        dr.setRecords(total);
        dr.setPage(gridQuery.getPage());
        dr.setTotal(getTotalPage(total, gridQuery.getRow(), gridQuery.getPage()));
        dr.setRows(getResultMap(result));
        response.setContentType(contentType);
        return dr;
    }

    @RequestMapping(method = {RequestMethod.POST}, value = "/remove")
    public void remove(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long templateId = Long.valueOf(request.getParameter("reportitemid"));
        pimsSysReportItemsManager.remove(templateId);
    }

    @RequestMapping(method = {RequestMethod.GET}, value = "/data")
    @ResponseBody
    public void getReportItemById(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PimsSysReportItems template = pimsSysReportItemsManager.get(Long.valueOf(request.getParameter("reportitemid")));
        PrintwriterUtil.print(response, getJSONObject(template).toString());
    }
}
