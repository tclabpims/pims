package com.pims.dao.hibernate.pimssysreqtestitem;
import com.pims.dao.pimssysreqtestitem.PimsSysReqTestitemDao;
import com.pims.model.PimsSysReqTestitem;
import com.smart.dao.hibernate.GenericDaoHibernate;
import org.apache.commons.lang.StringUtils;
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
        sb.append(" from PimsSysReqTestitem where tesuseflag = 1 ");
        if(map == null){
            return  null;
        }
        if(!StringUtils.isEmpty(String.valueOf(map.get("tesitemtype")))){
            sb.append(" and tesitemtype = " + map.get("tesitemtype"));
        }
        if(!StringUtils.isEmpty(String.valueOf(map.get("name")))){
            String name = map.get("name").toString().toUpperCase();
            sb.append(" and ( upper(teschinesename) like '%");
            sb.append(name);
            sb.append("%' or upper(tesenglishname) like '%");
            sb.append(name);
            sb.append("%' or upper(tespinyincode) like '%");
            sb.append(name);
            sb.append("%' or upper(tesfivestroke) like '%");
            sb.append(name);
            sb.append("%')");
        }
        return getSession().createQuery(sb.toString()).list();
    }
}
