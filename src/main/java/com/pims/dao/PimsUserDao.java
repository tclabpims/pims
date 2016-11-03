package com.pims.dao;

import com.smart.model.user.User;

import java.util.List;
import java.util.Map;

/**
 * Created by 909436637@qq.com on 2016/10/6.
 * Description:
 */
public interface PimsUserDao extends com.smart.dao.GenericDao<User,Long> {
    List<User> getDataList(Map map);
}
