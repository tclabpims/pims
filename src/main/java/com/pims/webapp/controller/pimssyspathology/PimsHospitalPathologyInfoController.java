package com.pims.webapp.controller.pimssyspathology;

import com.pims.model.PimsHospitalPathologyInfo;
import com.pims.model.PimsSysPathology;
import com.pims.service.pimssyspathology.PimsHospitalPathologyInfoManager;
import com.pims.webapp.controller.GridQuery;
import com.pims.webapp.controller.PIMSBaseController;
import com.pims.webapp.controller.WebControllerUtil;
import com.smart.model.user.User;
import com.smart.model.user.UserBussinessRelate;
import com.smart.util.DateUtil;
import com.smart.webapp.util.DataResponse;
import com.smart.webapp.util.PrintwriterUtil;
import org.apache.commons.lang.StringUtils;
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
import java.util.*;

/**
 * Created by 909436637@qq.com on 2016/10/20.
 * Description:
 */
@Controller
@RequestMapping(value = "/hpinfo/*")
public class PimsHospitalPathologyInfoController extends PIMSBaseController {

    @Autowired
    private PimsHospitalPathologyInfoManager pimsHospitalPathologyInfoManager;

    @RequestMapping(value = "/view")
    @ResponseBody
    public ModelAndView view() throws Exception {
        return new ModelAndView();
    }

    @RequestMapping(method = {RequestMethod.POST}, value = "/edit")
    @ResponseBody
    public void saveOrUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PimsHospitalPathologyInfo pspi = (PimsHospitalPathologyInfo) setBeanProperty(request, PimsHospitalPathologyInfo.class);
        if (pspi.getId() == 0) {
            pspi.setCreateTime(new Date());
            pspi.setCreateUser(WebControllerUtil.getAuthUser().getId());
            String regularExp = pspi.getRegularExpression();
            int split = regularExp.indexOf("|");
            String dateFormat = regularExp.substring(0, split - 1);
            int numLength = Integer.parseInt(regularExp.substring(split + 2));
            StringBuilder no = new StringBuilder(DateUtil.getDateTime(dateFormat, new Date()));
            for (int i = 0; i < numLength; i++) {
                no.append(0);
            }
            pspi.setNextNumber(Long.parseLong(no.toString()));
        } else {
            PimsHospitalPathologyInfo hisPpt = pimsHospitalPathologyInfoManager.get(pspi.getId());
            pspi.setCreateUser(hisPpt.getCreateUser());
            pspi.setCreateTime(hisPpt.getCreateTime());
            pspi.setLatestUser(hisPpt.getLatestUser());
            pspi.setNextNumber(hisPpt.getNextNumber());
        }
        pimsHospitalPathologyInfoManager.save(pspi);
    }

    @RequestMapping(method = {RequestMethod.GET}, value = "/query")
    @ResponseBody
    public DataResponse query(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DataResponse dr = new DataResponse();
        GridQuery gridQuery = new GridQuery(request);
        List<PimsHospitalPathologyInfo> result = pimsHospitalPathologyInfoManager.getInfoList(gridQuery);
        Integer total = pimsHospitalPathologyInfoManager.countInfo(gridQuery.getQuery());
        dr.setRecords(total);
        dr.setPage(gridQuery.getPage());
        dr.setTotal(getTotalPage(total, gridQuery.getRow(), gridQuery.getPage()));
        dr.setRows(getResultMap(result));
        response.setContentType(contentType);
        return dr;
    }

    @RequestMapping(method = {RequestMethod.GET}, value = "/userrelatepathology")
    @ResponseBody
    public DataResponse userRelatePathology(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DataResponse dr = new DataResponse();
        User user = WebControllerUtil.getAuthUser();
        List<PimsSysPathology> result = pimsHospitalPathologyInfoManager.getPathologyByUserId(user.getId());
        dr.setRows(getResultMap(result));
        Map<String, Object> map = new HashMap<>();
        UserBussinessRelate ubr = user.getUserBussinessRelate();
        if (ubr == null) {
            map.put("pathologyLib", "");
            map.put("pathologyLibId", "");
        } else {
            map.put("pathologyLib", user.getUserBussinessRelate().getPathologyLib());
            map.put("pathologyLibId", user.getUserBussinessRelate().getPathologyLibId());
        }
        dr.setUserdata(map);
        response.setContentType(contentType);
        return dr;
    }

    @RequestMapping(method = {RequestMethod.GET}, value = "/userid",produces="text/html;charset=UTF-8")
    @ResponseBody
    public String getPathologyByUserId(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<PimsSysPathology> result = null;
        String id = request.getParameter("id");
        if(StringUtils.isEmpty(id)){
            User user = WebControllerUtil.getAuthUser();
            result = pimsHospitalPathologyInfoManager.getPathologyByUserId(user.getId());
        }else{
            result = pimsHospitalPathologyInfoManager.getPathologyByHosId(Long.parseLong(id));
        }

        JSONArray array = new JSONArray();
        if (result != null) {
            for (PimsSysPathology s : result) {
                JSONObject o = new JSONObject();
                o.put("id", s.getPathologyid());
                o.put("name", s.getPatnamech());
                array.put(o);
            }
        }
//        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(array.toString());
        return null;
    }

    @RequestMapping(method = {RequestMethod.POST}, value = "/remove")
    public void remove(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long id = Long.valueOf(request.getParameter("id"));
        pimsHospitalPathologyInfoManager.remove(id);
    }

    @RequestMapping(method = {RequestMethod.GET}, value = "/data")
    @ResponseBody
    public void getDataById(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PimsHospitalPathologyInfo info = pimsHospitalPathologyInfoManager.get(Long.valueOf(request.getParameter("id")));
        PrintwriterUtil.print(response, getJSONObject(info).toString());
    }

    @RequestMapping(method = {RequestMethod.POST}, value = "/userpathology")
    @ResponseBody
    public void userPathology(HttpServletRequest request, HttpServletResponse response) throws Exception {
        WebControllerUtil.getAuthUser().setUserBussinessRelate((UserBussinessRelate) setBeanProperty(request, UserBussinessRelate.class));
    }
}
