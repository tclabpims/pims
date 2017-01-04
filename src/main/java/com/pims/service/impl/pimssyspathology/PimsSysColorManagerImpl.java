package com.pims.service.impl.pimssyspathology;

import com.pims.dao.pimssyspathology.PimsSysColorDao;
import com.pims.model.PimsSysColor;
import com.pims.service.pimssyspathology.PimsSysColorManager;
import com.pims.webapp.controller.GridQuery;
import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.service.impl.GenericManagerImpl;
import com.smart.dao.hibernate.GenericDaoHibernate;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.hibernate.search.FullTextSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by 909436637@qq.com on 2016/10/10.
 * Description:
 */
@Service("pimsSysColorManager")
public class PimsSysColorManagerImpl extends GenericManagerImpl<PimsSysColor, Long> implements PimsSysColorManager {

    private PimsSysColorDao pimsSysColorDao;

    @Autowired
    public void setPimsSysColorDao(PimsSysColorDao pimsSysColorDao) {
        this.dao = pimsSysColorDao;
        this.pimsSysColorDao = pimsSysColorDao;
    }

    @Override
    public List<PimsSysColor> getSysColorList(GridQuery gridQuery) {
        StringBuilder qstr = new StringBuilder().append("select a.colorid,A.COLCUSTOMERID,a.coltype,A.Colowner,A.Colobject,")
                .append("A.Colobjectstate,a.colvalue,B.Name,(select (case when (select c.name from lab_user c where to_number(A.Colowner)=c.id) is not null then (select c.name from lab_user c where to_number(A.Colowner)=c.id) else 'System' end) from dual) as uname,a.colmodule ")
                .append("from Pims_Sys_Color a,Lab_hospital b ")
                .append("where a.COLCUSTOMERID=B.Id");
        String query = gridQuery.getQuery();
        String sidx = gridQuery.getSidx();
        if (query != null && !"".equals(query.trim())) {
            qstr.append(" and B.Name like '%").append(query).append("%'");
        }

        sidx = (sidx == null || sidx.trim().equals("")) ? "a.colorid " : sidx;
        qstr.append(" order by  ").append(sidx).append(gridQuery.getSord());
        List<Object[]> data = pimsSysColorDao.sqlPagingQuery(qstr.toString(), gridQuery.getStart(), gridQuery.getEnd());
        List<PimsSysColor> result = new ArrayList<>();
        if(data.size() > 0) {
            for(Object[] obj : data) {
                PimsSysColor psc = new PimsSysColor();
                psc.setColorid(((BigDecimal)obj[0]).longValue());
                psc.setColcustomercode(((BigDecimal)obj[1]).longValue());
                psc.setColtype(String.valueOf(obj[2]));
                psc.setColowner(obj[3]==null?"":String.valueOf(obj[3]));
                psc.setColobject(String.valueOf(obj[4]));
                psc.setColobjectstate(String.valueOf(obj[5]));
                psc.setColvalue(obj[6]==null?"":String.valueOf(obj[6]));
                psc.setColcustomername(obj[7]==null?"":String.valueOf(obj[7]));
                psc.setColownername(obj[8]==null?"":String.valueOf(obj[8]));
                psc.setColmodule(obj[9]==null?"":String.valueOf(obj[9]));
                result.add(psc);
            }
        }
        return result;
    }

    @Override
    public Integer countSysColor(String query) {
        StringBuilder qstr = new StringBuilder().append("select count(1) cnt ")
                .append("from Pims_Sys_Color a,Lab_hospital b ")
                .append("where a.COLCUSTOMERID=B.Id");

        if (query != null && !"".equals(query.trim())) {
            qstr.append(" and B.Name like '%").append(query).append("%'");
        }
        return pimsSysColorDao.countTotal(qstr.toString());
    }

    @Override
    public boolean isExisted(PimsSysColor psc){
       return pimsSysColorDao.isExisted(psc);
    }
}
