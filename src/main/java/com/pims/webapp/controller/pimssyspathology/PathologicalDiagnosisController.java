package com.pims.webapp.controller.pimssyspathology;

import com.pims.model.PimsPathologySample;
import com.pims.service.pimspathologysample.PimsPathologySampleManager;
import com.pims.webapp.controller.GridQuery;
import com.pims.webapp.controller.PIMSBaseController;
import com.smart.webapp.util.DataResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @RequestMapping(method= RequestMethod.GET)
    public ModelAndView diagnosis() throws Exception {
        return new ModelAndView();
    }

    //查询符合条件的标本信息
    @RequestMapping(method = {RequestMethod.GET}, value = "/query")
    @ResponseBody
    public DataResponse query(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PimsPathologySample sample = (PimsPathologySample)setBeanProperty(request, PimsPathologySample.class);
        DataResponse dr = new DataResponse();
        GridQuery gridQuery = new GridQuery(request);
        List<PimsPathologySample> result = pimsPathologySampleManager.querySample(sample,gridQuery);
        Integer total = pimsPathologySampleManager.querySampleNum(sample);
        dr.setRecords(total);
        dr.setPage(gridQuery.getPage());
        dr.setTotal(getTotalPage(total, gridQuery.getRow(), gridQuery.getPage()));
        dr.setRows(getResultMap(result));
        response.setContentType(contentType);
        return dr;
    }
}
