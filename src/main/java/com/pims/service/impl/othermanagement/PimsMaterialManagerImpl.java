package com.pims.service.impl.othermanagement;

import com.pims.dao.othermanage.PimsMaterialDao;
import com.pims.model.PimsBaseModel;
import com.pims.model.PimsDisposableMaterial;
import com.pims.service.othermanagement.PimsMaterialManager;
import com.smart.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
/**
 * Created by zp on 2016/12/16.
 */
@Service("pimsMaterialManager")
public class PimsMaterialManagerImpl extends GenericManagerImpl<PimsDisposableMaterial, Long> implements PimsMaterialManager{
    private PimsMaterialDao pimsMaterialDao;

    @Autowired
    public void setPimsMaterialDao(PimsMaterialDao pimsMaterialDao){
        this.pimsMaterialDao = pimsMaterialDao;
        this.dao = pimsMaterialDao;
    }

    @Override
    public List<PimsDisposableMaterial> getMarList(PimsBaseModel map){
        return pimsMaterialDao.getMarList(map);
    }

    @Override
    public boolean deleteMar(Map map){
        return pimsMaterialDao.deleteMar(map);
    }

    @Override
    public int getReqListNum(PimsBaseModel map){
        return pimsMaterialDao.getReqListNum(map);
    }

    @Override
    public String sampleCode(){
        return pimsMaterialDao.sampleCode();
    }
}
