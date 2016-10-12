package com.pims.webapp.controller.pimssyspathology;

import com.pims.model.PimsSysChargeitemRef;
import com.pims.service.QueryHisDataService;
import com.pims.service.pimssyspathology.PimsSysChargeitemRefManager;
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
 * Created by 909436637@qq.com on 2016/10/11.
 * Description:
 */
@Controller
@RequestMapping("/chargeitemref/*")
public class PimsSysChargeitemRefController extends PIMSBaseController {

    @Autowired
    private PimsSysChargeitemRefManager pimsSysChargeitemRefManager;

    @Autowired
    private QueryHisDataService dataService;


    @RequestMapping("/chargeitemref")
    public ModelAndView chargeitemref() throws Exception {
        return new ModelAndView();
    }

    @RequestMapping(method = {RequestMethod.GET}, value = "/query_his_charge")
    @ResponseBody
    public DataResponse queryHisChargePrice(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DataResponse dr = new DataResponse();
        GridQuery gridQuery = new GridQuery(request);
        List result = dataService.queryHisChargePrice(gridQuery.getQuery(), gridQuery.getStart(), gridQuery.getEnd());
        Integer total = dataService.queryHisChargePrice(gridQuery.getQuery());
        dr.setRecords(total);
        dr.setPage(gridQuery.getPage());
        dr.setTotal(getTotalPage(total, gridQuery.getRow(), gridQuery.getPage()));
        dr.setRows(getResultMap(result));
        response.setContentType(contentType);
        return dr;
    }

    @RequestMapping(method = {RequestMethod.POST}, value = "/edit")
    @ResponseBody
    public void saveOrUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PimsSysChargeitemRef pimsSysChargeitemRef = (PimsSysChargeitemRef)setBeanProperty(request, PimsSysChargeitemRef.class);
        if(pimsSysChargeitemRef.getReferenceid() == 0) {
            pimsSysChargeitemRef.setRefcreatetime(new Date());
            pimsSysChargeitemRef.setRefcreateuser(String.valueOf(WebControllerUtil.getAuthUser().getId()));
        } else {
            PimsSysChargeitemRef hisPimsSysChargeitemRef = pimsSysChargeitemRefManager.get(pimsSysChargeitemRef.getReferenceid());
            pimsSysChargeitemRef.setRefcreateuser(hisPimsSysChargeitemRef.getRefcreateuser());
            pimsSysChargeitemRef.setRefcreatetime(hisPimsSysChargeitemRef.getRefcreatetime());
        }
        pimsSysChargeitemRefManager.save(pimsSysChargeitemRef);
    }

    @RequestMapping(method = {RequestMethod.GET}, value = "/query")
    @ResponseBody
    public DataResponse query(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DataResponse dr = new DataResponse();
        GridQuery gridQuery = new GridQuery(request);
        List<PimsSysChargeitemRef> result = pimsSysChargeitemRefManager.getChargeitemRefList(gridQuery);
        Integer total = pimsSysChargeitemRefManager.countChargeitemRef(gridQuery.getQuery());
        dr.setRecords(total);
        dr.setPage(gridQuery.getPage());
        dr.setTotal(getTotalPage(total, gridQuery.getRow(), gridQuery.getPage()));
        dr.setRows(getResultMap(result));
        response.setContentType(contentType);
        return dr;
    }

    @RequestMapping(method = {RequestMethod.POST}, value = "/remove")
    public void remove(HttpServletRequest request, HttpServletResponse response) throws Exception {
        pimsSysChargeitemRefManager.remove(Long.valueOf(request.getParameter("referenceid")));
    }

    @RequestMapping(method = {RequestMethod.GET}, value = "/data")
    @ResponseBody
    public void getChargeItemRefById(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PimsSysChargeitemRef pimsSysChargeitemRef = pimsSysChargeitemRefManager.get(Long.valueOf(request.getParameter("referenceid")));
        PrintwriterUtil.print(response, getJSONObject(pimsSysChargeitemRef).toString());
    }
}
