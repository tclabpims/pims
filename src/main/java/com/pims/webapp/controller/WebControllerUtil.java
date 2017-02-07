package com.pims.webapp.controller;

import com.smart.model.user.User;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by 909436637@qq.com on 2016/9/30.
 * Description:
 */
public class WebControllerUtil {

    public static User getAuthUser() {
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(user != null && !user.equals("anonymousUser")) {
            return (User)user;
        }
        return null;
    }

}
