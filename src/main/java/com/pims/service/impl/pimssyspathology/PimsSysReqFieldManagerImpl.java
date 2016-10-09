package com.pims.service.impl.pimssyspathology;

import com.pims.dao.pimssyspathology.PimsSysReqFieldDao;
import com.pims.model.PimsSysReqField;
import com.pims.service.impl.GenericManagerImpl;
import com.pims.service.pimssyspathology.PimsSysReqFieldManager;
import com.pims.webapp.controller.GridQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 909436637@qq.com on 2016/10/8.
 * Description:
 */
@Service("psrm")
public class PimsSysReqFieldManagerImpl extends GenericManagerImpl<PimsSysReqField, Long>  implements PimsSysReqFieldManager {

    private PimsSysReqFieldDao pimsSysReqFieldDao;

    @Autowired
    public  void  setPimsSysReqFieldDao(PimsSysReqFieldDao pimsSysReqFieldDao) {
        this.dao = pimsSysReqFieldDao;
        this.pimsSysReqFieldDao = pimsSysReqFieldDao;
    }

    @Override
    public List<PimsSysReqField> getReqFieldList(GridQuery gridQuery) {
        StringBuilder hql = new StringBuilder("from PimsSysReqField psp where 1=1");
        String query = gridQuery.getQuery();
        String sidx = gridQuery.getSidx();
        if(query != null && !"".equals(query.trim())) {
            hql.append(" and psp.fieelementname||psp.fieelementtype  like '%" +query +"%'");
        }

        sidx = (sidx == null || sidx.trim().equals(""))?"psp.fieldid ":sidx;
        hql.append(" order by  ").append(sidx).append(gridQuery.getSord());
        return pimsSysReqFieldDao.pagingList(hql.toString(), gridQuery.getStart(), gridQuery.getEnd());
    }

    @Override
    public Integer countReqField(String query) {
        StringBuilder hql = new StringBuilder("select count(1) cnt from PIMS_SYS_REQ_FIELD psp");
        if(query != null && !"".equals(query.trim())) {
            hql.append(" where psp.fieelementname||psp.fieelementtype  like '%" +query +"%'");
        }
        return pimsSysReqFieldDao.countTotal(hql.toString());
    }

    @Override
    public void deleteFields(String mid) {
        pimsSysReqFieldDao.deleteFields(mid);
    }
}
