package com.pims.dao.hibernate.pimssysreqtestitem;
import com.pims.dao.pimssysreqtestitem.PimsSysReqTestitemDao;
import com.pims.model.PimsSysReqTestitem;
import com.smart.dao.hibernate.GenericDaoHibernate;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    public List<PimsSysReqTestitem> getTestitemInfo( String name) {
        if(StringUtils.isEmpty(name)){
            return getSession().createQuery(" from PimsSysReqTestitem where tesuseflag = 1").list();
        }else{
            StringBuffer sb = new StringBuffer();
            name = name.toUpperCase();
            sb.append(" from PimsSysReqTestitem where tesuseflag = 1 and ( teschinesename like '%");
            sb.append(name);
            sb.append("%' or tesenglishname like '%");
            sb.append(name);
            sb.append("%' or tespinyincode like '%");
            sb.append(name);
            sb.append("%' or tesfivestroke like '%");
            sb.append(name);
            sb.append("%')");
            return getSession().createQuery(sb.toString()).list();
        }
    }

    @Override
    public List<PimsSysReqTestitem> allTestItem() {
        String hql = "from PimsSysReqTestitem where tesuseflag = 1 group by ";
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
