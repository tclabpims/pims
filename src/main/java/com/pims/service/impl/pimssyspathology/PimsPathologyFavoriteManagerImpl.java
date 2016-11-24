package com.pims.service.impl.pimssyspathology;

import com.pims.dao.pimssyspathology.PimsPathologyFavoriteDao;
import com.pims.model.PimsPathologyFavorite;
import com.pims.service.pimssyspathology.PimsPathologyFavoriteManager;
import com.pims.webapp.controller.GridQuery;
import com.pims.webapp.controller.WebControllerUtil;
import com.smart.model.user.User;
import com.smart.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 909436637@qq.com on 2016/11/24.
 * Description:
 */
@Service("pimsPathologyFavoriteManager")
public class PimsPathologyFavoriteManagerImpl extends GenericManagerImpl<PimsPathologyFavorite, Long> implements PimsPathologyFavoriteManager {

    private PimsPathologyFavoriteDao pimsPathologyFavoriteDao;

    @Autowired
    public void setPimsPathologyFavoriteDao(PimsPathologyFavoriteDao pimsPathologyFavoriteDao) {
        this.dao = pimsPathologyFavoriteDao;
        this.pimsPathologyFavoriteDao = pimsPathologyFavoriteDao;
    }

    @Override
    public Integer myFavorite() {
        User user = WebControllerUtil.getAuthUser();
        return pimsPathologyFavoriteDao.myFavorite(user.getUsername());
    }

    @Override
    public List<PimsPathologyFavorite> queryMyFavorite(GridQuery query) {
        User user = WebControllerUtil.getAuthUser();
        return pimsPathologyFavoriteDao.queryMyFavorite(query, user.getUsername());
    }
}
