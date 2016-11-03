package com.pims.webapp.util;

import org.apache.velocity.Template;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

/**
 * Created by 909436637@qq.com on 2016/10/31.
 * Description:
 */
public class DiagnosisReport {

    private Template getReportTemplate() {
        Template template = Velocity.getTemplate("", "UTF-8");
        return template;
    }

}
