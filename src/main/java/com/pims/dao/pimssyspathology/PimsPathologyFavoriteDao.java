package com.pims.dao.pimssyspathology;

import com.pims.model.PimsPathologyFavorite;
import com.pims.webapp.controller.GridQuery;
import com.smart.dao.GenericDao;

import java.util.List;

/**
 * Created by 909436637@qq.com on 2016/11/24.
 * Description:
 */
public interface PimsPathologyFavoriteDao extends GenericDao<PimsPathologyFavorite, Long> {
    Integer myFavorite(String username,String num);

    List<PimsPathologyFavorite> queryMyFavorite(GridQuery query, String userName,String num);
}
