package com.pims.service.impl.pimssyspathology;

import com.pims.dao.pimssyspathology.PimsPathologyPicturesDao;
import com.pims.model.PimsPathologyPictures;
import com.pims.service.pimssyspathology.PimsPathologyPicturesManager;
import com.smart.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 909436637@qq.com on 2016/10/28.
 * Description:
 */
@Service("pimsPathologyPicturesManager")
public class PimsPathologyPicturesManagerImpl extends GenericManagerImpl<PimsPathologyPictures, Long> implements PimsPathologyPicturesManager {

    private PimsPathologyPicturesDao pathologyPicturesDao;

    @Autowired
    public void setPathologyPicturesDao(PimsPathologyPicturesDao pathologyPicturesDao) {
        this.dao = pathologyPicturesDao;
        this.pathologyPicturesDao = pathologyPicturesDao;
    }

    @Override
    public void removeByName(String picName, Long sampleId) {
        pathologyPicturesDao.removeByName(picName, sampleId);
    }

    @Override
    public List<PimsPathologyPictures> getSamplePicture(Long sampleId, Long pictureClass) {
        return pathologyPicturesDao.getSamplePicture(sampleId, pictureClass);
    }
}
