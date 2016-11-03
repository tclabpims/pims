package com.pims.service.pimssyspathology;

import com.pims.model.PimsPathologyPictures;
import com.smart.service.GenericManager;

import java.util.List;

/**
 * Created by 909436637@qq.com on 2016/10/28.
 * Description:
 */
public interface PimsPathologyPicturesManager extends GenericManager<PimsPathologyPictures, Long> {
    void removeByName(String picName, Long sampleId);
    List<PimsPathologyPictures> getSamplePicture(Long sampleId);

}
