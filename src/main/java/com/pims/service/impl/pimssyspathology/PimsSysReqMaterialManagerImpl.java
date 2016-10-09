package com.pims.service.impl.pimssyspathology;

import com.pims.dao.pimssyspathology.PimsSysReqMaterialDao;
import com.pims.model.PimsSysReqMaterial;
import com.pims.service.pimssyspathology.PimsSysReqMaterialManager;
import com.pims.webapp.controller.GridQuery;
import com.smart.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 909436637@qq.com on 2016/10/9.
 * Description:
 */
@Service("pimsSysReqMaterialManager")
public class PimsSysReqMaterialManagerImpl extends GenericManagerImpl<PimsSysReqMaterial, Long> implements PimsSysReqMaterialManager {

    private PimsSysReqMaterialDao pimsSysReqMaterialDao;

    @Autowired
    public void setPimsSysReqMaterialDao(PimsSysReqMaterialDao pimsSysReqMaterialDao) {
        this.dao = pimsSysReqMaterialDao;
        this.pimsSysReqMaterialDao = pimsSysReqMaterialDao;
    }

    @Override
    public List<PimsSysReqMaterial> getMaterialList(GridQuery gridQuery) {
        StringBuilder builder = new StringBuilder();
        builder.append("select a.materialid,a.matname,a.mattype,a.matsort,a.matuseflag,A.Matpinyincode,A.Matfivestrokecode,A.Matspecial,B.Value from Pims_Sys_Req_Material a, Lab_Dictionary b where a.mattype=B.Id");
        String query = gridQuery.getQuery();
        String sidx = gridQuery.getSidx();
        if (query != null && !"".equals(query.trim())) {
            builder.append(" and a.matname||b.value  like '%").append(query).append("%'");
        }
        sidx = (sidx == null || sidx.trim().equals("")) ? "a.materialid " : sidx;
        builder.append(" order by  ").append(sidx).append(gridQuery.getSord());
        List<Object[]> result = pimsSysReqMaterialDao.sqlPagingQuery(builder.toString(), gridQuery.getStart(), gridQuery.getEnd());
        List<PimsSysReqMaterial> pimsSysReqMaterialList = new ArrayList<>();
        if (result.size() > 0) {
            for (Object[] obj : result) {
                PimsSysReqMaterial psrm = new PimsSysReqMaterial();
                psrm.setMaterialid(((BigDecimal) obj[0]).longValue());
                psrm.setMatname(String.valueOf(obj[1]==null?"":obj[1]));
                psrm.setMattype(((BigDecimal) obj[2]).longValue());
                psrm.setMatsort(String.valueOf(obj[3]));
                psrm.setMatuseflag(((BigDecimal) obj[4]).longValue());
                psrm.setMatpinyincode(String.valueOf(obj[5]==null?"":obj[5]));
                psrm.setMatfivestrokecode(String.valueOf(obj[6]==null?"":obj[6]));
                psrm.setMatspecial(String.valueOf(obj[7]==null?"":obj[7]));
                psrm.setMattypename(String.valueOf(obj[8]==null?"":obj[8]));
                pimsSysReqMaterialList.add(psrm);
            }
        }
        return pimsSysReqMaterialList;
    }

    @Override
    public Integer countMaterial(String query) {
        StringBuilder builder = new StringBuilder();
        builder.append("select count(1) cnt from Pims_Sys_Req_Material a, Lab_Dictionary b where a.mattype=B.Id");
        if (query != null && !"".equals(query.trim())) {
            builder.append(" and a.matname||b.value  like '%").append(query).append("%'");
        }
        return pimsSysReqMaterialDao.countTotal(builder.toString());
    }
}
