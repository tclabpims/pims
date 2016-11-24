package com.pims.service.impl;

import com.pims.dao.PimsUserDao;
import com.pims.service.PimsUserManager;
import com.pims.webapp.controller.GridQuery;
import com.smart.model.user.User;
import com.smart.service.impl.GenericManagerImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by 909436637@qq.com on 2016/10/6.
 * Description:
 */
@Service("pimsUserManager")
public class PimsUserManagerImpl extends GenericManagerImpl<User,Long> implements PimsUserManager {

    private PimsUserDao pimsUserDao;

    @Autowired
    public void setPimsUserDao(PimsUserDao pimsUserDao) {
        this.dao = pimsUserDao;
        this.pimsUserDao = pimsUserDao;
    }

    @Override
    public List<User> getUserList(GridQuery gridQuery,String states) {
        StringBuilder hql = new StringBuilder("from User psp where 1=1");
        if(!StringUtils.isEmpty(states)){
            hql.append(" and id <>"+ states);
        }
        String sidx = gridQuery.getSidx();
        sidx = (sidx == null || sidx.trim().equals(""))?"psp.id ":sidx;
        hql.append(" order by  ").append(sidx).append(gridQuery.getSord());
        return pimsUserDao.pagingList(hql.toString(), gridQuery.getStart(),gridQuery.getEnd());
    }

    @Override
    public Integer countUser(String query,String states) {
        StringBuilder hql = new StringBuilder("select count(1) cnt from lab_user psp");
        if(!StringUtils.isEmpty(states)){
            hql.append(" where id <>"+ states);
        }
        return pimsUserDao.countTotal(hql.toString());
    }

    @Override
    public List<User> getDataList(Map map) {
        return pimsUserDao.getDataList(map);
    }
}
