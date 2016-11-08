package com.pims.service.impl.pimssysreqtestitem;

import com.pims.dao.pimssysreqtestitem.PimsSysReqTestitemDao;
import com.pims.model.PimsSysReqTestitem;
import com.pims.service.pimssysreqtestitem.PimsSysReqTestitemManager;
import com.pims.webapp.controller.GridQuery;
import com.smart.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by king on 2016/10/8
 */
@Service("pimsSysReqTestitemManager")
public class PimsSysReqTestitemManagerImpl extends GenericManagerImpl<PimsSysReqTestitem,Long>
        implements PimsSysReqTestitemManager {
    private PimsSysReqTestitemDao pimsSysReqTestitemDao;

    @Autowired
    public void setPimsPathologyRequisitionDao(PimsSysReqTestitemDao pimsSysReqTestitemDao) {
        this.dao = pimsSysReqTestitemDao;
        this.pimsSysReqTestitemDao = pimsSysReqTestitemDao;
    }

    /**
     * 获取可用申请项目
     * @return
     */
    @Override
    public List<PimsSysReqTestitem> getTestitemInfo(Map map) {
        return pimsSysReqTestitemDao.getTestitemInfo(map);
    }

    @Override
    public List<PimsSysReqTestitem> getReqTestitemList(GridQuery gridQuery, Long pathologyId) {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT a.testitemid,a.teschinesename,a.tesenglishname,a.tesitemsort,a.tespinyincode,a.tesfivestroke,a.tesitemtype,").append(
                "a.tespathologyid,a.tesitemhandle,a.tesischarge,a.tesuseflag,B.Patnamech,a.tesitemproperty from PIMS_SYS_REQ_TESTITEM a, Pims_Sys_Pathology b ").append(
                "where A.Tespathologyid = B.Pathologyid");
        String query = gridQuery.getQuery();
        String sidx = gridQuery.getSidx();
        if (query != null && !"".equals(query.trim())) {
            builder.append(" and a.teschinesename||b.Patnamech  like '%").append(query).append("%'");
        }
        if(pathologyId !=null ) {
            builder.append(" and a.tespathologyid=").append(pathologyId);
        }
        sidx = (sidx == null || sidx.trim().equals("")) ? "a.testitemid " : sidx;
        builder.append(" order by  ").append(sidx).append(gridQuery.getSord());
        List<Object[]> result = pimsSysReqTestitemDao.sqlPagingQuery(builder.toString(), gridQuery.getStart(), gridQuery.getEnd());
        List<PimsSysReqTestitem> pimsSysReqTestitem = new ArrayList<>();
        if(result.size() > 0) {
            for (Object[] obj : result) {
                PimsSysReqTestitem pimsSysReqTestitem1 = new PimsSysReqTestitem();
                pimsSysReqTestitem1.setTestitemid(((BigDecimal)obj[0]).longValue());
                pimsSysReqTestitem1.setTeschinesename(String.valueOf(obj[1]==null?"":obj[1]));
                pimsSysReqTestitem1.setTesenglishname(String.valueOf(obj[2]==null?"":obj[2]));
                pimsSysReqTestitem1.setTesitemsort(String.valueOf(obj[3]==null?"":obj[3]));
                pimsSysReqTestitem1.setTespinyincode(String.valueOf(obj[4]==null?"":obj[4]));
                pimsSysReqTestitem1.setTesfivestroke(String.valueOf(obj[5]==null?"":obj[5]));
                pimsSysReqTestitem1.setTesitemtype(((BigDecimal)obj[6]).longValue());
                pimsSysReqTestitem1.setTespathologyid(((BigDecimal)obj[7]).longValue());
                if(obj[8] != null) {
                    pimsSysReqTestitem1.setTesitemhandle(((BigDecimal)obj[8]).longValue());
                }
                pimsSysReqTestitem1.setTesischarge(((BigDecimal)obj[9]).longValue());
                pimsSysReqTestitem1.setTesuseflag(((BigDecimal)obj[10]).longValue());
                pimsSysReqTestitem1.setTespathologyname(String.valueOf(obj[11]==null?"":obj[11]));
                pimsSysReqTestitem1.setTesitemproperty(((BigDecimal)obj[12]).longValue());
                pimsSysReqTestitem.add(pimsSysReqTestitem1);
            }
        }
        return pimsSysReqTestitem;
    }

    @Override
    public Integer countReqTestitem(String query, Long pathologyId) {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT count(1) cnt from PIMS_SYS_REQ_TESTITEM a, Pims_Sys_Pathology b ").append(
                "where A.Tespathologyid = B.Pathologyid");
        if (query != null && !"".equals(query.trim())) {
            builder.append(" and a.teschinesename||b.Patnamech  like '%").append(query).append("%'");
        }
        if(pathologyId !=null ) {
            builder.append(" and a.tespathologyid=").append(pathologyId);
        }
        return pimsSysReqTestitemDao.countTotal(builder.toString());
    }

    @Override
    public List<PimsSysReqTestitem> allTestItem() {
        return pimsSysReqTestitemDao.allTestItem();
    }

    @Override
    public List<PimsSysReqTestitem> getTestItems(Long aLong) {
        return pimsSysReqTestitemDao.getTestItems(aLong);
    }
}
