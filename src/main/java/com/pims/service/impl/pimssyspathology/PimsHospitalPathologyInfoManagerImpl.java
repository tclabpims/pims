package com.pims.service.impl.pimssyspathology;

import com.pims.dao.pimssyspathology.PimsHospitalPathologyInfoDao;
import com.pims.model.PimsHospitalPathologyInfo;
import com.pims.model.PimsPathologySample;
import com.pims.model.PimsSysPathology;
import com.pims.service.pimssyspathology.PimsHospitalPathologyInfoManager;
import com.pims.webapp.controller.GridQuery;
import com.smart.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 909436637@qq.com on 2016/10/20.
 * Description:
 */
@Service("pimsHospitalPathologyInfoManager")
public class PimsHospitalPathologyInfoManagerImpl extends GenericManagerImpl<PimsHospitalPathologyInfo, Long> implements PimsHospitalPathologyInfoManager {

    private PimsHospitalPathologyInfoDao hospitalPathologyInfoDao;

    @Autowired
    public void setHospitalPathologyInfoDao(PimsHospitalPathologyInfoDao hospitalPathologyInfoDao) {
        this.dao = hospitalPathologyInfoDao;
        this.hospitalPathologyInfoDao = hospitalPathologyInfoDao;
    }

    @Override
    public List<PimsHospitalPathologyInfo> getInfoList(GridQuery gridQuery) {
        StringBuffer sql = new StringBuffer("SELECT hpi.*,hs.name,sp.patnamech from Pims_Hospital_Pathology_Info hpi, lab_hospital hs, Pims_Sys_Pathology sp ")
                .append("where Hpi.Hospitalid=Hs.Id and Hpi.Pathologyid=Sp.Pathologyid ");
        String query = gridQuery.getQuery();
        String sidx = gridQuery.getSidx();
        if(query != null && !"".equals(query.trim())) {
            sql.append("and hs.name like '%").append(query).append("%'");
        }
        sidx = (sidx == null || sidx.trim().equals(""))?"hpi.sortNo ":sidx;
        sql.append(" order by  ").append(sidx).append(gridQuery.getSord());
        List<Object[]> data = hospitalPathologyInfoDao.sqlPagingQuery(sql.toString(), gridQuery.getStart(), gridQuery.getEnd());
        List<PimsHospitalPathologyInfo> result = new ArrayList<>();
        if(data.size() > 0) {
            for(Object[] obj : data) {
                PimsHospitalPathologyInfo phpi = new PimsHospitalPathologyInfo();
                phpi.setId(((BigDecimal)obj[0]).longValue());
                phpi.setHospitalId(((BigDecimal)obj[1]).longValue());
                phpi.setPathologyId(((BigDecimal)obj[2]).longValue());
                phpi.setNumberPrefix(obj[3] == null?null:String.valueOf(obj[3]));
                phpi.setRegularExpression(obj[4] == null?null:String.valueOf(obj[4]));
                phpi.setUseFlag(((BigDecimal)obj[5]).intValue());
                phpi.setNextNumber(((BigDecimal)obj[6]).longValue());
                phpi.setSortNo(obj[9] == null?null:String.valueOf(obj[9]));
                phpi.setTheAlias(obj[11] == null?null:String.valueOf(obj[11]));
                phpi.setHospitalName(String.valueOf(obj[12]));
                phpi.setPathologyName(String.valueOf(obj[13]));
                result.add(phpi);
            }
        }
        return result;
    }



    @Override
    public Integer countInfo(String query) {
        StringBuffer sql = new StringBuffer("SELECT count(*) cnt from Pims_Hospital_Pathology_Info hpi, lab_hospital hs, Pims_Sys_Pathology sp ")
                .append("where Hpi.Hospitalid=Hs.Id and Hpi.Pathologyid=Sp.Pathologyid ");
        if(query != null && !"".equals(query.trim())) {
            sql.append("and hs.name like '%").append(query).append("%'");
        }
        return hospitalPathologyInfoDao.countTotal(sql.toString());
    }

    @Override
    public List<PimsSysPathology> getPathologyByUserId(long userId) {
        StringBuilder hql = new StringBuilder("from PimsSysPathology as pathology where pathology.patuseflag=0 and pathology.pathologyid ")
                .append("in(select hpi.pathologyId from PimsHospitalPathologyInfo as hpi, User as us where hpi.useFlag=1 and us.hospitalId=hpi.hospitalId and us.id=:id)");
        return hospitalPathologyInfoDao.getPathologyByUserId(hql.toString(), userId);
    }

    @Override
    public PimsHospitalPathologyInfo gethinfo(PimsPathologySample sample) {
        return hospitalPathologyInfoDao.gethinfo(sample);
    }
}
