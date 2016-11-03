package com.pims.service.impl.pimssyspathology;

import com.pims.dao.pimssyspathology.PimsSysReportItemsDao;
import com.pims.model.PimsSysReportItems;
import com.pims.service.pimssyspathology.PimsSysReportItemsManager;
import com.pims.webapp.controller.GridQuery;
import com.smart.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 909436637@qq.com on 2016/10/10.
 * Description:
 */
@Service("pimsSysReportItemsManager")
public class PimsSysReportItemsManagerImpl extends GenericManagerImpl<PimsSysReportItems, Long> implements PimsSysReportItemsManager {

    private PimsSysReportItemsDao pimsSysReportItemsDao;

    @Autowired
    public void setPimsSysReportItemsDao(PimsSysReportItemsDao pimsSysReportItemsDao) {
        this.dao = pimsSysReportItemsDao;
        this.pimsSysReportItemsDao = pimsSysReportItemsDao;
    }

    @Override
    public List<PimsSysReportItems> getReportItemList(GridQuery gridQuery) {
        StringBuilder qstr = new StringBuilder("from PimsSysReportItems p");
        String query = gridQuery.getQuery();
        String sidx = gridQuery.getSidx();
        if(query != null && !"".equals(query.trim())) {
            qstr.append(" where p.rptname||p.rptenglishname  like '%").append(query).append("%'");
        }

        sidx = (sidx == null || sidx.trim().equals(""))?"p.reportitemid ":sidx;
        qstr.append(" order by  ").append(sidx).append(gridQuery.getSord());
        return pimsSysReportItemsDao.pagingList(qstr.toString(), gridQuery.getStart(), gridQuery.getEnd());
    }

    @Override
    public Integer countReportItem(String query) {
        StringBuilder qstr = new StringBuilder("select count(1) cnt from ");
        qstr.append("Pims_Sys_Report_Items a");
        if(query != null && !"".equals(query.trim())) {
            qstr.append(" where a.rptname||a.rptenglishname  like '%").append(query).append("%'");
        }
        return pimsSysReportItemsDao.countTotal(qstr.toString());
    }

    @Override
    public List getRefFieldList(Long hospitalId, Long pathologyId) {
        String sql = "select {pr.*} from Pims_Sys_Report_Items pr,Pims_Sys_Customer_Basedata pb, Pims_Sys_Req_Field rf where Pr.Reportitemid=pb.Basrefdataid and Rf.Fieldid =Pr.Rptelementid and Rf.Fieuseflag=1 and pb.bastype=3 and Pb.Baspathologyid=:pathologyId and Pb.Bascustomercode=:hospitalId";
        return pimsSysReportItemsDao.getRefFieldList(sql, hospitalId, pathologyId);
    }
}
