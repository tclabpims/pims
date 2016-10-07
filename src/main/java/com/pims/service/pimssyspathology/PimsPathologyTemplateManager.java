package com.pims.service.pimssyspathology;

import com.pims.model.PimsPathologyTemplate;
import com.pims.webapp.controller.GridQuery;
import com.smart.model.user.User;
import com.smart.service.GenericManager;

import java.util.List;

/**
 * Created by 909436637@qq.com on 2016/10/6.
 * Description:
 */
public interface PimsPathologyTemplateManager extends GenericManager<PimsPathologyTemplate,Long> {
    List<PimsPathologyTemplate> getTemplateList(GridQuery gridQuery);

    Integer countTemplate(String query);
}
