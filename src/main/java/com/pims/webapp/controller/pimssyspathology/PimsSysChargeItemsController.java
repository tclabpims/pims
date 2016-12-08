package com.pims.webapp.controller.pimssyspathology;

import com.pims.model.PimsSysChargeItems;
import com.pims.model.PimsSysChargeitemRef;
import com.pims.model.PimsSysReqTestitem;
import com.pims.service.pimssyspathology.PimsSysChargeItemsManager;
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

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseBody
    public String getTestitemInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String name = request.getParameter("name");
        List list = pimsSysChargeItemsManager.getFeeByName(name);
//        List list = pimsSysChargeItemsManager.getfeeAll();
        JSONArray array = new JSONArray();
        if (list != null) {
            for(int i=0;i<list.size();i++){
                Object[] oo = (Object[]) list.get(i);
                PimsSysChargeItems s = (PimsSysChargeItems) oo[0];
                PimsSysChargeitemRef f = (PimsSysChargeitemRef) oo[1];
                JSONObject o = new JSONObject();
                o.put("feeitemid", s.getChargeitemid());//收费项目ID
                o.put("feenamech", s.getChinesename());//中文名称
                o.put("feenameen",s.getChienglishname());//英文名称
                o.put("feeprince",s.getChiprice());//单价
                o.put("feecategory",s.getChicategory());//所属统计类
                o.put("feehisitemid",f.getRefhischargeid());//his项目id
                o.put("feehisname",f.getRefhischargename());//his项目名称
                o.put("feehisprice",f.getRefhisprice());//his单价
                array.put(o);
            }
//            for (PimsSysChargeItems s : list) {
//                JSONObject o = new JSONObject();
//                o.put("chargeitemid", s.getChargeitemid());//收费ID
//                o.put("chichinesename", s.getChinesename());//中文名称
//                o.put("chienglishname",s.getChienglishname());//英文名称
//                o.put("chiprice",s.getChiprice());//单价
//                array.put(o);
//            }
        }
        response.setCharacterEncoding("UTF-8");
        //response.setContentType("text/html; charset=UTF-8");
        response.getWriter().write(array.toString());
        return null;
    }

}
