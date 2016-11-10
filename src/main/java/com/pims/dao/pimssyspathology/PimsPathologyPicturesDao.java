package com.pims.dao.pimssyspathology;

import com.pims.model.PimsPathologyPictures;
import com.smart.dao.GenericDao;

import java.util.List;

/**
 * Created by 909436637@qq.com on 2016/10/28.
 * Description:
 */
public interface PimsPathologyPicturesDao extends GenericDao<PimsPathologyPictures, Long> {
    void removeByName(String picName, Long sampleId);

    List<PimsPathologyPictures> getSamplePicture(Long sampleId, Long pictureClass);
}
