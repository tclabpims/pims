package com.pims.service.impl.pimssyspathology;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pims.dao.pimssyspathology.PimsSysPathologyDao;
import com.pims.model.PimsSysPathology;
import com.pims.service.pimssyspathology.PimsSysPathologyManager;
import com.pims.webapp.controller.GridQuery;
import com.smart.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 909436637@qq.com on 2016/9/28.
 * Description:病种维护manager
 */
@Service("pimsSysPathologyManager")
public class PimsSysPathologyManagerImpl extends GenericManagerImpl<PimsSysPathology,Long> implements PimsSysPathologyManager {

    private PimsSysPathologyDao pimsSysPathologyDao;

    @Autowired
    public void setPimsSysPathologyDao(PimsSysPathologyDao pimsSysPathologyDao) {
        this.dao = pimsSysPathologyDao;
        this.pimsSysPathologyDao = pimsSysPathologyDao;
    }

    @Override
    public List<PimsSysPathology> getPimsSysPathologyList(GridQuery gridQuery) {

        StringBuilder hql = new StringBuilder("from PimsSysPathology psp where 1=1");
        String query = gridQuery.getQuery();
        String sidx = gridQuery.getSidx();
        Long hospitalId = gridQuery.getHospitalId();
        if(query != null && !"".equals(query.trim())) {
            hql.append(" and psp.patnamech||psp.patnameen  like '%" +query +"%'");
        }

        if(hospitalId != null) {
            hql.append(" and psp.pathologyid in(select bd.pathologyId from PimsHospitalPathologyInfo bd where bd.hospitalId=").append(hospitalId).append(") ");
        }

        sidx = (sidx == null || sidx.trim().equals(""))?"psp.patsort ":sidx;
        hql.append(" order by  ").append(sidx).append(gridQuery.getSord());

        return pimsSysPathologyDao.getPimsSysPathologyList(hql.toString(), gridQuery.getStart(), gridQuery.getEnd());
    }

    @Override
    public Integer getPimsSysPathologyTotal(String queryString, Long hospitalId) {
        StringBuilder hql = new StringBuilder("select count(1) cnt from pims_sys_pathology psp where 1=1 ");
        if(queryString != null && !"".equals(queryString.trim())) {
            hql.append(" and psp.patnamech||psp.patnameen  like '%" +queryString +"%'");
        }
        if(hospitalId != null) {
            hql.append(" and psp.pathologyid in(select bd.pathologyid from PIMS_HOSPITAL_PATHOLOGY_INFO bd where bd.hospitalid=").append(hospitalId).append(") ");
        }
        return pimsSysPathologyDao.getPimsSysPathologyTotal(hql.toString());
    }

    @Override
    public JSONArray getPathologyType() {
        JSONArray array = new JSONArray();
        List<PimsSysPathology> data = pimsSysPathologyDao.findEnabledPathology("from PimsSysPathology psp where psp.patuseflag=0 order by psp.patsort");
        for(PimsSysPathology p : data) {
            JSONObject obj = new JSONObject();
            obj.put("pathologyLibId", p.getPathologyid());
            obj.put("pathologyLib", p.getPatnamech());
            array.add(obj);
        }
        return array;
    }
}
