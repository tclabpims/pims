package com.pims.webapp.controller;

import com.pims.service.PimsUserManager;
import com.smart.model.user.User;
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
 * Created by 909436637@qq.com on 2016/10/6.
 * Description:
 */
@Controller
@RequestMapping("/pimsuser*")
public class PimsUserController extends PIMSBaseController {

    @Autowired
    private PimsUserManager pimsUserManager;


    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/userlist")
    public DataResponse getUserList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DataResponse dr = new DataResponse();
        GridQuery gridQuery = new GridQuery(request);
        List<User> result = pimsUserManager.getUserList(gridQuery);
        Integer total = pimsUserManager.countUser(gridQuery.getQuery());
        dr.setRecords(total);
        dr.setPage(gridQuery.getPage());
        dr.setTotal(getTotalPage(total, gridQuery.getRow(), gridQuery.getPage()));
        dr.setRows(getResultMap(result));
        response.setContentType(contentType);
        return dr;
    }

}
