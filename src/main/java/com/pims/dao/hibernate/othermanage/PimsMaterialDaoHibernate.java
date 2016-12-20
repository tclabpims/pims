package com.pims.dao.hibernate.othermanage;

import com.pims.dao.othermanage.PimsMaterialDao;
import com.pims.model.PimsBaseModel;
import com.pims.model.PimsDisposableMaterial;
import com.smart.dao.hibernate.GenericDaoHibernate;
import org.apache.commons.lang.StringUtils;
import com.pims.webapp.controller.WebControllerUtil;
import org.springframework.stereotype.Repository;
import org.hibernate.Query;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zp on 2016/12/16.
 */
@Repository("pimsMaterialDao")
public class PimsMaterialDaoHibernate extends GenericDaoHibernate<PimsDisposableMaterial,Long> implements PimsMaterialDao{
    public PimsMaterialDaoHibernate() {super(PimsDisposableMaterial.class);}

    Date t = new Date();
    public StringBuffer getSearchSql(StringBuffer sb, PimsBaseModel map) {
        if (!StringUtils.isEmpty(map.getMarname())) {
            sb.append(" and marname = '" + map.getMarname() + "'");//条形码
        }
        if (!StringUtils.isEmpty(map.getCurrent())) {
            sb.append(" and marishas = '" + map.getCurrent() + "'");//玻片id
        }
        return sb;
    }

    @Override
    public  List<PimsDisposableMaterial> getMarList(PimsBaseModel map){
        StringBuffer sb = new StringBuffer();
        sb.append("from PimsDisposableMaterial where 1=1");
        getSearchSql(sb, map);
        String orderby = (map.getSidx() == null || map.getSidx().trim().equals("")) ? "marname" : map.getSidx();
        sb.append(" order by " + orderby + " " + map.getSord());
        System.out.println(sb.toString());
        return pagingList(sb.toString());
    }

    @Override
    public boolean deleteMar(Map map){
        if(map == null){
            return false;
        }else {
            String sql = "delete from PIMS_DISPOSABLE_MATERIAL where marid='"+map.get("marid")+"'";
            Query query = getSession().createSQLQuery(sql);
            query.executeUpdate();
            return true;
        }
    }

    @Override
    public int getReqListNum(PimsBaseModel map) {
        StringBuffer sb = new StringBuffer();
        sb.append(" select count(1) from PIMS_DISPOSABLE_MATERIAL where 1=1 ");
        getSearchSql(sb, map);
        return countTotal(sb.toString()).intValue();
    }

    @Override
    public String sampleCode() {
        String sql = ("select max(marid) from PIMS_DISPOSABLE_MATERIAL");
        Object o = getSession().createSQLQuery(sql).uniqueResult();
        if(o==null) return null;
        return o.toString();
    }
}

