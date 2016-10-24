package com.pims.webapp.controller.pimssyspathology;

import com.pims.model.PimsPathologySample;
import com.pims.model.PimsPathologyTemplate;
import com.pims.service.pimspathologysample.PimsPathologySampleManager;
import com.pims.service.pimssyspathology.PimsPathologyTemplateManager;
import com.pims.webapp.controller.GridQuery;
import com.pims.webapp.controller.PIMSBaseController;
import com.pims.webapp.controller.WebControllerUtil;
import com.smart.model.user.User;
import com.smart.webapp.util.DataResponse;
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
 * Created by 909436637@qq.com on 2016/10/18.
 * Description:
 */
@Controller
@RequestMapping("/diagnosis/*")
public class PathologicalDiagnosisController extends PIMSBaseController {

    @Autowired
    private PimsPathologySampleManager pimsPathologySampleManager;

    @Autowired
    private PimsPathologyTemplateManager pptm;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView diagnosis(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String options = getPathologySelectOption(request);
        ModelAndView mv = new ModelAndView();
        mv.addObject("options", options);
        return mv;
    }

    @RequestMapping(method = {RequestMethod.POST}, value = "/edit")
    @ResponseBody
    public void saveOrUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = WebControllerUtil.getAuthUser();
        PimsPathologyTemplate ppt = (PimsPathologyTemplate) setBeanProperty(request, PimsPathologyTemplate.class);
        ppt.setTemcreatetime(new Date());
        ppt.setTemcreateuser(String.valueOf(WebControllerUtil.getAuthUser().getId()));
        ppt.setTempathologyid(user.getUserBussinessRelate().getPathologyLibId());
        ppt.setTemcustomerid(user.getHospitalId());
        if (ppt.getTemtype() == 1L) {
            ppt.setTemownername(user.getName());
            ppt.setTemownerid(String.valueOf(user.getId()));
        } else {
            ppt.setTemownername("System");
            ppt.setTemownerid("9999999999");
        }
        pptm.save(ppt);
    }

    //查询符合条件的模板信息
    @RequestMapping(method = {RequestMethod.GET}, value = "/getpathologytemp")
    @ResponseBody
    public DataResponse getPathologyTemp(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DataResponse dr = new DataResponse();
        GridQuery gridQuery = new GridQuery(request);
        Long tempType = Long.valueOf(request.getParameter("type"));
        User user = WebControllerUtil.getAuthUser();
        Long pathologyId = user.getUserBussinessRelate().getPathologyLibId();
        gridQuery.setUserId(user.getId());
        gridQuery.setHospitalId(user.getHospitalId());
        List<PimsPathologyTemplate> result = pptm.getTemplateList(gridQuery, tempType, pathologyId);
        Integer total = pptm.countTemplate(gridQuery.getUserId(), gridQuery.getHospitalId(), tempType, pathologyId);
        dr.setRecords(total);
        dr.setPage(gridQuery.getPage());
        dr.setTotal(getTotalPage(total, gridQuery.getRow(), gridQuery.getPage()));
        dr.setRows(getResultMap(result));
        response.setContentType(contentType);
        return dr;
    }

    //查询符合条件的标本信息
    @RequestMapping(method = {RequestMethod.GET}, value = "/query")
    @ResponseBody
    public DataResponse query(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PimsPathologySample sample = (PimsPathologySample) setBeanProperty(request, PimsPathologySample.class);
        DataResponse dr = new DataResponse();
        GridQuery gridQuery = new GridQuery(request);
        List<PimsPathologySample> result = pimsPathologySampleManager.querySample(sample, gridQuery);
        Integer total = pimsPathologySampleManager.querySampleNum(sample);
        dr.setRecords(total);
        dr.setPage(gridQuery.getPage());
        dr.setTotal(getTotalPage(total, gridQuery.getRow(), gridQuery.getPage()));
        dr.setRows(getResultMap(result));
        response.setContentType(contentType);
        return dr;
    }
}
