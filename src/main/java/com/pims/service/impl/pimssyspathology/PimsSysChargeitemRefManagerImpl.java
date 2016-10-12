package com.pims.service.impl.pimssyspathology;

import com.pims.dao.pimssyspathology.PimsSysChargeitemRefDao;
import com.pims.model.PimsSysChargeitemRef;
import com.pims.service.pimssyspathology.PimsSysChargeitemRefManager;
import com.pims.webapp.controller.GridQuery;
import com.smart.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 909436637@qq.com on 2016/10/11.
 * Description:
 */
@Service("pimsSysChargeitemRefManager")
public class PimsSysChargeitemRefManagerImpl extends GenericManagerImpl<PimsSysChargeitemRef, Long> implements PimsSysChargeitemRefManager {

    private PimsSysChargeitemRefDao pimsSysChargeitemRefDao;

    @Autowired
    public void setPimsSysChargeitemRefDao(PimsSysChargeitemRefDao pimsSysChargeitemRefDao) {
        this.dao = pimsSysChargeitemRefDao;
        this.pimsSysChargeitemRefDao = pimsSysChargeitemRefDao;
    }

    @Override
    public List<PimsSysChargeitemRef> getChargeitemRefList(GridQuery gridQuery) {
        StringBuilder builder = new StringBuilder();
        builder.append("select A.Referenceid,A.Chargeitemid,A.Customercode,A.Refhischargeid,A.Refhischargename,")
                .append("A.Refhisprice,A.Refsendhis,A.Refremark,b.name,C.Chinesename ")
                .append("from PIMS_SYS_CHARGEITEM_REF a, lab_hospital b, Pims_Sys_Charge_Items c ")
                .append("where a.chargeitemid=C.Chargeitemid and A.Customercode=B.Id");
        String query = gridQuery.getQuery();
        String sidx = gridQuery.getSidx();
        if (query != null && !"".equals(query.trim())) {
            builder.append(" and A.Refhischargename||C.Chinesename like '%").append(query).append("%'");
        }
        sidx = (sidx == null || sidx.trim().equals("")) ? "a.Referenceid " : sidx;
        builder.append(" order by  ").append(sidx).append(gridQuery.getSord());
        List<Object[]> data = pimsSysChargeitemRefDao.sqlPagingQuery(builder.toString(), gridQuery.getStart(), gridQuery.getEnd());
        List<PimsSysChargeitemRef> result = new ArrayList<>();
        if (data.size() > 0) {
            for (Object[] obj : data) {
                PimsSysChargeitemRef ref = new PimsSysChargeitemRef();
                ref.setReferenceid(((BigDecimal) obj[0]).longValue());
                ref.setChargeitemid(((BigDecimal) obj[1]).longValue());
                ref.setCustomercode(String.valueOf(obj[2]));
                ref.setRefhischargeid(String.valueOf(obj[3]));
                ref.setRefhischargename(String.valueOf(obj[4]));
                ref.setRefhisprice(((BigDecimal) obj[5]).doubleValue());
                ref.setRefsendhis(((BigDecimal) obj[6]).longValue());
                ref.setRefremark(obj[7] == null ? "" : String.valueOf(obj[7]));
                ref.setCustomerName(String.valueOf(obj[8]));
                ref.setChargeItemName(String.valueOf(obj[9]));
                result.add(ref);
            }
        }
        return result;
    }

    @Override
    public Integer countChargeitemRef(String query) {
        StringBuilder builder = new StringBuilder();
        builder.append("select count(1) cnt ")
                .append("from PIMS_SYS_CHARGEITEM_REF a, lab_hospital b, Pims_Sys_Charge_Items c ")
                .append("where a.chargeitemid=C.Chargeitemid and A.Customercode=B.Id");
        if (query != null && !"".equals(query.trim())) {
            builder.append(" and A.Refhischargename||C.Chinesename like '%").append(query).append("%'");
        }
        return pimsSysChargeitemRefDao.countTotal(builder.toString());
    }
}
