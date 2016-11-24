package com.pims.webapp.controller.pimssyspathology;

import com.pims.model.PimsPathologyFavorite;
import com.pims.model.PimsPathologyTemplate;
import com.pims.service.pimssyspathology.PimsPathologyFavoriteManager;
import com.pims.webapp.controller.GridQuery;
import com.pims.webapp.controller.PIMSBaseController;
import com.smart.webapp.util.DataResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by 909436637@qq.com on 2016/11/24.
 * Description:
 */
@Controller
@RequestMapping(value = "/favorite")
public class PimsPathologyFavoriteController extends PIMSBaseController {

    @Autowired
    private PimsPathologyFavoriteManager pimsPathologyFavoriteManager;

    @RequestMapping(method = {RequestMethod.GET}, value = "/query")
    @ResponseBody
    public DataResponse query(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DataResponse dr = new DataResponse();
        GridQuery gridQuery = new GridQuery(request);
        List<PimsPathologyFavorite> result = pimsPathologyFavoriteManager.queryMyFavorite(gridQuery);
        Integer total = pimsPathologyFavoriteManager.myFavorite();
        dr.setRecords(total);
        dr.setPage(gridQuery.getPage());
        dr.setTotal(getTotalPage(total, gridQuery.getRow(), gridQuery.getPage()));
        dr.setRows(getResultMap(result));
        response.setContentType(contentType);
        return dr;
    }

}
