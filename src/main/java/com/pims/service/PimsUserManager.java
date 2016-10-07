package com.pims.service;

import com.pims.webapp.controller.GridQuery;
import com.smart.model.user.User;

import java.util.List;

/**
 * Created by 909436637@qq.com on 2016/10/6.
 * Description:
 */
public interface PimsUserManager extends GenericManager<User,Long> {


    List getUserList(GridQuery gridQuery);

    Integer countUser(String query);
}
