package com.pims.webapp.controller.pimssyspathology;

import com.pims.model.PimsSysChargeItems;
import com.pims.model.PimsSysReqTestitem;
import com.pims.service.pimssyspathology.PimsSysChargeItemsManager;
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
@RequestMapping("/chargeitem/*")
public class PimsSysChargeItemsController extends PIMSBaseController {

    @Autowired
    private PimsSysChargeItemsManager pimsSysChargeItemsManager;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView chargeitem() throws Exception {
        return new ModelAndView();
    }

    @RequestMapping(method = {RequestMethod.POST}, value = "/edit")
    @ResponseBody
    public void saveOrUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PimsSysChargeItems pimsSysChargeItems = (PimsSysChargeItems)setBeanProperty(request, PimsSysChargeItems.class);
        if(pimsSysChargeItems.getChargeitemid() == 0) {
            pimsSysChargeItems.setChicreatetime(new Date());
            pimsSysChargeItems.setChicreateuser(String.valueOf(WebControllerUtil.getAuthUser().getId()));
        } else {
            PimsSysChargeItems hisPimsSysChargeItems = pimsSysChargeItemsManager.get(pimsSysChargeItems.getChargeitemid());
            pimsSysChargeItems.setChicreateuser(hisPimsSysChargeItems.getChicreateuser());
            pimsSysChargeItems.setChicreatetime(hisPimsSysChargeItems.getChicreatetime());
        }
        pimsSysChargeItemsManager.save(pimsSysChargeItems);
    }

    @RequestMapping(method = {RequestMethod.GET}, value = "/query")
    @ResponseBody
    public DataResponse query(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DataResponse dr = new DataResponse();
        GridQuery gridQuery = new GridQuery(request);
        List<PimsSysChargeItems> result = pimsSysChargeItemsManager.getChargeItemsList(gridQuery);
        Integer total = pimsSysChargeItemsManager.countChargeItems(gridQuery.getQuery());
        dr.setRecords(total);
        dr.setPage(gridQuery.getPage());
        dr.setTotal(getTotalPage(total, gridQuery.getRow(), gridQuery.getPage()));
        dr.setRows(getResultMap(result));
        response.setContentType(contentType);
        return dr;
    }

    @RequestMapping(method = {RequestMethod.POST}, value = "/remove")
    public void remove(HttpServletRequest request, HttpServletResponse response) throws Exception {
        pimsSysChargeItemsManager.remove(Long.valueOf(request.getParameter("chargeitemid")));
    }

    @RequestMapping(method = {RequestMethod.GET}, value = "/data")
    @ResponseBody
    public void getChargeItemById(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PimsSysChargeItems pimsSysChargeItems = pimsSysChargeItemsManager.get(Long.valueOf(request.getParameter("chargeitemid")));
        PrintwriterUtil.print(response, getJSONObject(pimsSysChargeItems).toString());
    }

}
