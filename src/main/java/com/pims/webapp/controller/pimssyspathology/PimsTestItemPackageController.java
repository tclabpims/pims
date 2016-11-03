package com.pims.webapp.controller.pimssyspathology;

import com.pims.model.PimsPathologyTemplate;
import com.pims.model.PimsSysPackageDetail;
import com.pims.model.PimsSysReqTestitem;
import com.pims.model.PimsTestItemPackage;
import com.pims.service.pimssyspathology.PimsSysPackageDetailManager;
import com.pims.service.pimssyspathology.PimsTestItemPackageManager;
import com.pims.service.pimssysreqtestitem.PimsSysReqTestitemManager;
import com.pims.webapp.controller.GridQuery;
import com.pims.webapp.controller.PIMSBaseController;
import com.smart.webapp.util.DataResponse;
import org.apache.commons.collections.map.HashedMap;
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
 * Created by 909436637@qq.com on 2016/11/2.
 * Description:
 */
@Controller
@RequestMapping(value = "/package/*")
public class PimsTestItemPackageController extends PIMSBaseController {

    @Autowired
    private PimsSysPackageDetailManager pimsSysPackageDetailManager;

    @Autowired
    private PimsTestItemPackageManager pimsTestItemPackageManager;

    @Autowired
    private PimsSysReqTestitemManager pimsSysReqTestitemManager;

    @RequestMapping(value = "/main")
    @ResponseBody
    public ModelAndView main() throws Exception {
        return new ModelAndView();
    }

    @RequestMapping(method = {RequestMethod.GET}, value = "/query")
    @ResponseBody
    public DataResponse query(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DataResponse dr = new DataResponse();
        GridQuery gridQuery = new GridQuery(request);
        List<PimsTestItemPackage> result = pimsTestItemPackageManager.getPackageList(gridQuery);
        Integer total = pimsTestItemPackageManager.countPackage(gridQuery.getQuery());
        dr.setRecords(total);
        dr.setPage(gridQuery.getPage());
        dr.setTotal(getTotalPage(total, gridQuery.getRow(), gridQuery.getPage()));
        dr.setRows(getResultMap(result));
        response.setContentType(contentType);
        return dr;
    }

    @RequestMapping(method = {RequestMethod.GET}, value = "/testitems")
    @ResponseBody
    public DataResponse getTestItems(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DataResponse dr = new DataResponse();
        String packageId = request.getParameter("packageId");
        if (packageId != null && !"".equals(packageId.trim())) {
            List<PimsSysReqTestitem> items = pimsSysReqTestitemManager.getTestItems(Long.valueOf(packageId));
            dr.setRows(getResultMap(items));
        }
        response.setContentType(contentType);
        return dr;
    }

    @RequestMapping(method = {RequestMethod.GET}, value = "/packageinfo")
    @ResponseBody
    public Map<String, Object> packageInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String packageId = request.getParameter("packageId");
        PimsTestItemPackage itemPackage = pimsTestItemPackageManager.get(Long.valueOf(packageId));
        return getResultMap(itemPackage);
    }

    @RequestMapping(method = {RequestMethod.GET}, value = "/remove")
    @ResponseBody
    public void remove(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String packageId = request.getParameter("packageId");
        Long id = null;
        if (packageId != null && !"".equals(packageId.trim())) id = Long.valueOf(packageId);
        if (id != null) {
            pimsTestItemPackageManager.remove(id);
            pimsSysPackageDetailManager.deleteByPackageId(id);
        }
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    @ResponseBody
    public void edit(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String packageName = request.getParameter("packageName");
        String packageId = request.getParameter("packageId");
        Long id = null;
        if (packageId != null && !"".equals(packageId.trim())) id = Long.valueOf(packageId);
        String selectedItems = request.getParameter("selectedItems");
        PimsTestItemPackage itemPackage = new PimsTestItemPackage();
        itemPackage.setPackageName(packageName);
        if (id != null) {
            itemPackage.setPackageId(id);
            pimsSysPackageDetailManager.deleteByPackageId(id);
        }
        itemPackage = pimsTestItemPackageManager.save(itemPackage);

        if (selectedItems != null && selectedItems.length() > 0) {
            List<PimsSysPackageDetail> lis = new ArrayList<>();
            String[] detailId = selectedItems.split(",");
            for (String str : detailId) {
                PimsSysPackageDetail detail = new PimsSysPackageDetail();
                detail.setPackageId(itemPackage.getPackageId());
                detail.setTestItemId(Long.valueOf(str));
                lis.add(detail);
            }
            pimsSysPackageDetailManager.batchSave(lis);
        }
    }


}
