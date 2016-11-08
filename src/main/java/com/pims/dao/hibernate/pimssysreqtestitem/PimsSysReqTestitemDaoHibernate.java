package com.pims.dao.hibernate.pimssysreqtestitem;
import com.pims.dao.pimssysreqtestitem.PimsSysReqTestitemDao;
import com.pims.model.PimsSysReqTestitem;
import com.smart.dao.hibernate.GenericDaoHibernate;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by king on 2016/10/8
 */
@Repository("pimsSysReqTestitemDao")
public class PimsSysReqTestitemDaoHibernate extends GenericDaoHibernate<PimsSysReqTestitem,Long>
        implements PimsSysReqTestitemDao{
    public PimsSysReqTestitemDaoHibernate(){super(PimsSysReqTestitem.class);}

    /**
     * 获取可用的申请材料
     * @return
     */
    @Override
    public List<PimsSysReqTestitem> getTestitemInfo( Map map) {
        StringBuffer sb = new StringBuffer();
        sb.append(" from PimsSysReqTestitem p where p.tesuseflag = 1 ");
        if(map == null){
            return  null;
        }
        if(!StringUtils.isEmpty((String)(map.get("tesitemtype")))){
            sb.append(" and p.tesitemtype = " + map.get("tesitemtype"));
        }

        if(!StringUtils.isEmpty((String)(map.get("isCharge")))){
            if(((String)map.get("isCharge")).trim().equals("true"))
            sb.append(" and p.tesischarge =1 ");
            else
                sb.append(" and p.tesischarge =0 ");
        }

        if(!StringUtils.isEmpty((String)(map.get("filter")))){
            sb.append(" and not exists (select p2.testitemid from PimsSysChargeItems p2 where p2.testitemid=p.testitemid and p.tesitemtype=2)");
        }

        if(!StringUtils.isEmpty((String)(map.get("name")))){
            String name = map.get("name").toString().toUpperCase();
            sb.append(" and ( upper(p.teschinesename) like '%");
            sb.append(name);
            sb.append("%' or upper(p.tesenglishname) like '%");
            sb.append(name);
            sb.append("%' or upper(p.tespinyincode) like '%");
            sb.append(name);
            sb.append("%' or upper(p.tesfivestroke) like '%");
            sb.append(name);
            sb.append("%')");
        }
        return getSession().createQuery(sb.toString()).list();
    }

    @Override
    public List<PimsSysReqTestitem> allTestItem() {
        String hql = "from PimsSysReqTestitem where tesuseflag = 1 ";
        return getSession().createQuery(hql).list();
    }

    @Override
    public List<PimsSysReqTestitem> getTestItems(Long aLong) {
        String hql = "from PimsSysReqTestitem as t where t.testitemid in(select d.testItemId from PimsSysPackageDetail as d where d.packageId=:packageId)";
        Query query = getSession().createQuery(hql);
        query.setParameter("packageId", aLong);
        return query.list();
    }
}
