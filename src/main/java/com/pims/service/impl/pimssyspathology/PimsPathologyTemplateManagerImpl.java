package com.pims.service.impl.pimssyspathology;

import com.pims.dao.pimssyspathology.PimsPathologyTemplateDao;
import com.pims.model.PimsPathologyTemplate;
import com.pims.service.pimssyspathology.PimsPathologyTemplateManager;
import com.pims.webapp.controller.GridQuery;
import com.smart.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 909436637@qq.com on 2016/10/6.
 * Description:
 */
@Service("pptm")
public class PimsPathologyTemplateManagerImpl extends GenericManagerImpl<PimsPathologyTemplate,Long> implements PimsPathologyTemplateManager {

    private PimsPathologyTemplateDao pimsPathologyTemplateDao;

    @Autowired
    public void setPimsSysPathologyDao(PimsPathologyTemplateDao pimsPathologyTemplateDao) {
        this.dao = pimsPathologyTemplateDao;
        this.pimsPathologyTemplateDao = pimsPathologyTemplateDao;
    }

    @Override
    public List<PimsPathologyTemplate> getTemplateList(GridQuery gridQuery) {
        StringBuilder qstr = new StringBuilder("select a.templateid,a.temcustomerid,a.temownerid,a.tempathologyid,a.temsort,a.temtype,a.temcontent,a.temclass,a.temkey,a.tempinyin,a.temfivestroke,a.temspellcode,a.temownername,b.Patnamech as tempathologyname,c.Name as temcustomername from ");
        qstr.append("Pims_Pathology_Template a,Pims_Sys_Pathology b,Lab_hospital c ")
        .append("where a.Tempathologyid=b.Pathologyid and a.Temcustomerid=c.Id");
        String query = gridQuery.getQuery();
        String sidx = gridQuery.getSidx();
        if(query != null && !"".equals(query.trim())) {
            qstr.append(" and a.temownername||c.name  like '%" +query +"%'");
        }

        sidx = (sidx == null || sidx.trim().equals(""))?"a.templateid ":sidx;
        qstr.append(" order by  ").append(sidx).append(gridQuery.getSord());
        List<Object[]> result = pimsPathologyTemplateDao.sqlPagingQuery(qstr.toString(), gridQuery.getStart(),gridQuery.getEnd());
        List<PimsPathologyTemplate> templateList = new ArrayList<>();
        if(result.size() > 0) {
            for (Object[] obj:result) {
                PimsPathologyTemplate ppt = new PimsPathologyTemplate();
                ppt.setTemplateid(((BigDecimal)obj[0]).longValue());
                ppt.setTemcustomerid(((BigDecimal)obj[1]).longValue());
                ppt.setTemownerid((String)obj[2]);
                ppt.setTempathologyid(((BigDecimal)obj[3]).longValue());
                ppt.setTemsort((String)obj[4]);
                ppt.setTemtype(((BigDecimal)obj[5]).longValue());
                ppt.setTemcontent((String)obj[6]);
                ppt.setTemclass(((BigDecimal)obj[7]).longValue());
                ppt.setTemkey((String)obj[8]);
                ppt.setTempinyin((String)obj[9]);
                ppt.setTemfivestroke((String)obj[10]);
                ppt.setTemspellcode((String)obj[11]);
                ppt.setTemownername((String)obj[12]);
                ppt.setTempathologyname((String)obj[13]);
                ppt.setTemcustomername((String)obj[14]);
                templateList.add(ppt);
            }
        }
        return templateList;
    }

    @Override
    public Integer countTemplate(String query) {
        StringBuilder qstr = new StringBuilder("select count(1) cnt from ");
        qstr.append("Pims_Pathology_Template a,Pims_Sys_Pathology b,Lab_hospital c ")
                .append("where a.Tempathologyid=b.Pathologyid and a.Temcustomerid=c.Id");
        if(query != null && !"".equals(query.trim())) {
            qstr.append(" and a.temownername||c.name  like '%" +query +"%'");
        }
        return pimsPathologyTemplateDao.countTotal(qstr.toString());
    }
}
