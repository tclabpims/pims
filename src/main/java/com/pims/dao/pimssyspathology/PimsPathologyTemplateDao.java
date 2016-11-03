package com.pims.dao.pimssyspathology;

import com.pims.model.PimsPathologyTemplate;
import com.pims.webapp.controller.GridQuery;
import com.smart.dao.GenericDao;

import java.util.List;
import java.util.Map;

/**
 * Created by 909436637@qq.com on 2016/10/6.
 * Description:
 */
public interface PimsPathologyTemplateDao extends GenericDao<PimsPathologyTemplate, Long> {
    List sqlPagingQuery(String qstr, int start, int end);

    List<PimsPathologyTemplate> getTemplateList(GridQuery gridQuery, Long tempType, Long pathologyLibId, String hql);

    Integer countTemplate(Long userId, Long hospitalId, Long tempType, Long pathologyLibId, String s);

    List getDataList(Map map);
}
