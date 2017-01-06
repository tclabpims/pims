package com.pims.service.impl.pimssyspathology;

import com.pims.dao.pimssyspathology.PimsSysReportFormatDao;
import com.pims.model.PimsSysReportFormate;
import com.smart.service.impl.GenericManagerImpl;
import com.pims.service.pimssyspathology.PimsSysReportFormatManager;
import com.pims.webapp.controller.GridQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 909436637@qq.com on 2016/10/1.
 * Description:
 */
@Service("pimsSysReportFormatManager")
public class PimsSysReportFormatManagerImpl  extends GenericManagerImpl<PimsSysReportFormate, Long> implements PimsSysReportFormatManager {

    private PimsSysReportFormatDao pimsSysReportFormatDao;

    @Autowired
    public  void  setPimsSysReportFormatDao(PimsSysReportFormatDao pimsSysReportFormatDao) {
        this.pimsSysReportFormatDao = pimsSysReportFormatDao;
        this.dao = pimsSysReportFormatDao;
    }

    @Override
    public List<PimsSysReportFormate> getPimsSysReportFormatList(GridQuery gridQuery) {
        StringBuilder hql = new StringBuilder("from PimsSysReportFormate psp where 1=1");
        String query = gridQuery.getQuery();
        String sidx = gridQuery.getSidx();
        hql.append(" and psp.formpathologyid= ").append(query);
        sidx = (sidx == null || sidx.trim().equals(""))?"psp.formsort ":sidx;
        hql.append(" order by  ").append(sidx).append(gridQuery.getSord());
        return pimsSysReportFormatDao.pagingList(hql.toString(), gridQuery.getStart(), gridQuery.getEnd());
    }

    @Override
    public Integer getPimsSysReportFormat(String query) {
        StringBuilder hql = new StringBuilder("select count(1) cnt from pims_sys_report_formate psp");
        hql.append(" where psp.formpathologyid= ").append(query);

        return pimsSysReportFormatDao.countTotal(hql.toString());
    }

    @Override
    public void removeReportData(Long pathologyid) {
        StringBuilder hql = new StringBuilder("delete PimsSysReportFormate where formpathologyid=:ID");
        pimsSysReportFormatDao.removeReportData(hql.toString(), pathologyid);
    }

    @Override
    public List<PimsSysReportFormate> getReportFormatByPathologyId(Long pathologyId) {
        StringBuilder hql = new StringBuilder("from PimsSysReportFormate psp");
        hql.append(" where psp.formpathologyid=:pathologyId  order by formisdefault , formpicturenum asc");
        return pimsSysReportFormatDao.getReportFormatByPathologyId(hql.toString(), pathologyId);
    }
}
