package com.pims.service.impl.pimssyspathology;

import com.pims.dao.pimssyspathology.PimsPathologyFavoriteDao;
import com.pims.model.PimsPathologyFavorite;
import com.pims.service.pimssyspathology.PimsPathologyFavoriteManager;
import com.smart.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
