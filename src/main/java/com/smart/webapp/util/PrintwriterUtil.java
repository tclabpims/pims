package com.smart.webapp.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by 909436637@qq.com on 2016/9/27.
 */
public class PrintwriterUtil {

    protected static Log log = LogFactory.getLog(PrintwriterUtil.class);

    public static void print(HttpServletResponse response, String content){
        response.setContentType("application/json; charset=UTF-8");
        try {
            PrintWriter writer = response.getWriter();
            writer.print(content);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            if(log.isErrorEnabled()) {
                log.error("HttpServletResponse print 错误："+e.getCause().getMessage());
            }
            e.printStackTrace();
        }
    }

}
