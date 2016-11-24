package com.pims.service.pimssyspathology;

import com.pims.model.PimsPathologyFavorite;
import com.pims.webapp.controller.GridQuery;
import com.smart.service.GenericManager;

import java.util.List;

/**
 * Created by 909436637@qq.com on 2016/11/24.
 * Description:
 */
public interface PimsPathologyFavoriteManager extends GenericManager<PimsPathologyFavorite,Long> {

    Integer myFavorite();

    List<PimsPathologyFavorite> queryMyFavorite(GridQuery query);

}
