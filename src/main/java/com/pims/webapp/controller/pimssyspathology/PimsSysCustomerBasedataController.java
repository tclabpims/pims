package com.pims.webapp.controller.pimssyspathology;

import com.pims.model.PimsSysChargeitemRef;
import com.pims.model.PimsSysCustomerBasedata;
import com.pims.service.pimssyspathology.PimsSysCustomerBasedataManager;
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
 * Created by 909436637@qq.com on 2016/10/12.
 * Description:
 */
@Controller
@RequestMapping("/customerdata/*")
public class PimsSysCustomerBasedataController extends PIMSBaseController {

    @Autowired
    private PimsSysCustomerBasedataManager pimsSysCustomerBasedataManager;

    @RequestMapping(method = RequestMethod.GET, value = "/customerdata")
    public ModelAndView customerdata() throws Exception {
        return new ModelAndView();
    }

    @RequestMapping(method = {RequestMethod.POST}, value = "/edit")
    @ResponseBody
    public void saveOrUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PimsSysCustomerBasedata pimsSysCustomerBasedata = (PimsSysCustomerBasedata)setBeanProperty(request, PimsSysCustomerBasedata.class);
        if(pimsSysCustomerBasedata.getBasedataid() == 0) {
            pimsSysCustomerBasedata.setBascreatetime(new Date());
            pimsSysCustomerBasedata.setBascreateuser(String.valueOf(WebControllerUtil.getAuthUser().getId()));
        } else {
            PimsSysCustomerBasedata hisPimsSysCustomerBasedata = pimsSysCustomerBasedataManager.get(pimsSysCustomerBasedata.getBasedataid());
            pimsSysCustomerBasedata.setBascreateuser(hisPimsSysCustomerBasedata.getBascreateuser());
            pimsSysCustomerBasedata.setBascreatetime(hisPimsSysCustomerBasedata.getBascreatetime());
        }
        pimsSysCustomerBasedataManager.save(pimsSysCustomerBasedata);
    }

    @RequestMapping(method = {RequestMethod.GET}, value = "/query")
    @ResponseBody
    public DataResponse query(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DataResponse dr = new DataResponse();
        GridQuery gridQuery = new GridQuery(request);
        List<PimsSysCustomerBasedata> result = pimsSysCustomerBasedataManager.getCustomerDataList(gridQuery);
        Integer total = pimsSysCustomerBasedataManager.countCustomerData(gridQuery.getQuery());
        dr.setRecords(total);
        dr.setPage(gridQuery.getPage());
        dr.setTotal(getTotalPage(total, gridQuery.getRow(), gridQuery.getPage()));
        dr.setRows(getResultMap(result));
        response.setContentType(contentType);
        return dr;
    }

    @RequestMapping(method = {RequestMethod.POST}, value = "/remove")
    public void remove(HttpServletRequest request, HttpServletResponse response) throws Exception {
        pimsSysCustomerBasedataManager.remove(Long.valueOf(request.getParameter("basedataid")));
    }

    @RequestMapping(method = {RequestMethod.GET}, value = "/data")
    @ResponseBody
    public void getCustomerBaseDataById(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PimsSysCustomerBasedata pimsSysCustomerBasedata = pimsSysCustomerBasedataManager.get(Long.valueOf(request.getParameter("basedataid")));
        PrintwriterUtil.print(response, getJSONObject(pimsSysCustomerBasedata).toString());
    }

}
