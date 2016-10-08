package com.pims.dao.hibernate.pimssysreqtestitem;
import com.pims.dao.pimssysreqtestitem.PimsSysReqTestitemDao;
import com.pims.model.PimsSysReqTestitem;
import com.smart.dao.hibernate.GenericDaoHibernate;
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
    public List<PimsSysReqTestitem> getTestitemInfo() {
        return getSession().createQuery(" from PimsSysReqTestitem where tesuseflag = 1").list();
    }
}
