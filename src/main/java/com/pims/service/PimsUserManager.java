package com.pims.service;

import com.pims.webapp.controller.GridQuery;
import com.smart.model.user.User;
import com.smart.service.GenericManager;

import java.util.List;
import java.util.Map;

/**
 * Created by 909436637@qq.com on 2016/10/6.
 * Description:
 */
public interface PimsUserManager extends GenericManager<User,Long> {


    List getUserList(GridQuery gridQuery,String states);

    Integer countUser(String query,String states);

    List<User> getDataList(Map map);
}
