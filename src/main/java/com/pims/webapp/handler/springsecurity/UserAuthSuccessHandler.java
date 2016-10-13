package com.pims.webapp.handler.springsecurity;

import com.smart.model.user.User;
import com.smart.model.user.UserBussinessRelate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by 909436637@qq.com on 2016/9/26.
 * 用户登录成功后设置一些相关信息
 */
public class UserAuthSuccessHandler implements AuthenticationSuccessHandler {

    /**
     * request参数名：病理库
     */
    public static final String PATHOLOGY_LIB = "pathologyLib";

    /**
     * 当前用户选择的病种类别ID
     */
    public static final String USER_PATHOLOGY_LIB_ID = "pathologyLibId";

    protected final Log log = LogFactory.getLog(getClass());

    public static final String LOGIN_SUCCESS_REDIRECT = "/home";


    /**
     * @param httpServletRequest
     * @param httpServletResponse
     * @param authentication
     * @throws IOException
     * @throws ServletException
     */

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        Map<String, String[]> paramMap = httpServletRequest.getParameterMap();
        User user = (User) authentication.getPrincipal();
        if(authentication.isAuthenticated() && user != null) {
            String[] pathologyLibName = paramMap.get(PATHOLOGY_LIB);
            String[] pathologyLibNameId = paramMap.get(USER_PATHOLOGY_LIB_ID);
            UserBussinessRelate ubr = new UserBussinessRelate(pathologyLibName[0], pathologyLibNameId[0]);
            user.setUserBussinessRelate(ubr);
            httpServletResponse.sendRedirect(new StringBuilder(httpServletRequest.getContextPath()).append(LOGIN_SUCCESS_REDIRECT).toString());
        }
    }
}
