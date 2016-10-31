package com.pims.dao.hibernate;

import com.pims.dao.PimsUserDao;
import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.model.user.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by 909436637@qq.com on 2016/10/7.
 * Description:
 */
@Repository("pimsUserDao")
public class PimsUserDaoHibernate extends GenericDaoHibernate<User, Long> implements PimsUserDao {
    /**
     * Constructor that takes in a class to see which type of entity to persist.
     * Use this constructor when subclassing.
     */
    public PimsUserDaoHibernate() {
        super(User.class);
    }

    @Override
    public List<User> getDataList(Map map) {
        StringBuffer sb = new StringBuffer();
        sb.append(" from User where hospitalId = '"+map.get("hosptial")+"' and name like '%"+map.get("name")+"%'");
        return getSession().createQuery(sb.toString()).list();
    }
}
