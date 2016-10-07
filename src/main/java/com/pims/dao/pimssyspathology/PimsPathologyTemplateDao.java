package com.pims.dao.pimssyspathology;

import com.pims.model.PimsPathologyTemplate;
import com.smart.dao.GenericDao;

import java.util.List;

/**
 * Created by 909436637@qq.com on 2016/10/6.
 * Description:
 */
public interface PimsPathologyTemplateDao extends GenericDao<PimsPathologyTemplate, Long> {
    List sqlPagingQuery(String qstr, int start, int end);
}
