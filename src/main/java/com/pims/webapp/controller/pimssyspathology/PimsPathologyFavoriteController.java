package com.pims.webapp.controller.pimssyspathology;

import com.alibaba.fastjson.JSONObject;
import com.pims.model.PimsPathologyFavorite;
import com.pims.model.PimsPathologyTemplate;
import com.pims.service.pimssyspathology.PimsPathologyFavoriteManager;
import com.pims.webapp.controller.GridQuery;
import com.pims.webapp.controller.PIMSBaseController;
import com.smart.webapp.util.DataResponse;
import com.smart.webapp.util.PrintwriterUtil;
import org.apache.commons.lang.StringUtils;
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
        String num = request.getParameter("num");
        DataResponse dr = new DataResponse();
        GridQuery gridQuery = new GridQuery(request);
        List<PimsPathologyFavorite> result = pimsPathologyFavoriteManager.queryMyFavorite(gridQuery,num);
        Integer total = pimsPathologyFavoriteManager.myFavorite(num);
        dr.setRecords(total);
        dr.setPage(gridQuery.getPage());
        dr.setTotal(getTotalPage(total, gridQuery.getRow(), gridQuery.getPage()));
        dr.setRows(getResultMap(result));
        response.setContentType(contentType);
        return dr;
    }

    @RequestMapping(value = "/deleteinfo*", method = RequestMethod.POST)
    public void deleteSample(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PimsPathologyFavorite ppr = (PimsPathologyFavorite)setBeanProperty(request,PimsPathologyFavorite.class);
        JSONObject o = new JSONObject();
        if(StringUtils.isEmpty(String.valueOf(ppr.getFavoriteid()))){
            o.put("message", "查不到收藏信息！");
            o.put("success", false);
        }else{
            pimsPathologyFavoriteManager.remove(ppr);
                o.put("message", "标本删除成功！");
                o.put("success", true);

        }
        PrintwriterUtil.print(response, o.toString());
    }

}
