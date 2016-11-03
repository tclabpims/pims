package com.pims.dao.hibernate.pimssyspathology;

import com.pims.dao.pimssyspathology.PimsPathologyPicturesDao;
import com.pims.model.PimsPathologyPictures;
import com.smart.dao.hibernate.GenericDaoHibernate;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 909436637@qq.com on 2016/10/28.
 * Description:
 */
@Repository("pathologyPicturesDao")
public class PimsPathologyPicturesDaoHibernate extends GenericDaoHibernate<PimsPathologyPictures, Long> implements PimsPathologyPicturesDao {
    /**
     * Constructor that takes in a class to see which type of entity to persist.
     * Use this constructor when subclassing.
     */
    public PimsPathologyPicturesDaoHibernate() {
        super(PimsPathologyPictures.class);
    }

    @Override
    public void removeByName(String picName, Long sampleId) {
        Query query = getSession().createQuery("delete from PimsPathologyPictures as a  where a.picpicturename=:picpicturename and a.picsampleid=:picsampleid");
        query.setParameter("picpicturename", picName);
        query.setParameter("picsampleid", sampleId);
        query.executeUpdate();
    }

    @Override
    public List<PimsPathologyPictures> getSamplePicture(Long sampleId) {
        Query query = getSession().createQuery("from PimsPathologyPictures as a  where a.picsampleid=:sampleId");
        return query.setParameter("sampleId", sampleId).list();
    }
}
