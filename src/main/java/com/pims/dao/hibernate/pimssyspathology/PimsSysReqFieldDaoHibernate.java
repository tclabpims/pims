package com.pims.dao.hibernate.pimssyspathology;

import com.pims.dao.pimssyspathology.PimsSysReqFieldDao;
import com.pims.model.PimsSysReqField;
import com.smart.dao.hibernate.GenericDaoHibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 909436637@qq.com on 2016/10/8.
 * Description:
 */
@Repository("pimsSysReqFieldDao")
public class PimsSysReqFieldDaoHibernate extends GenericDaoHibernate<PimsSysReqField, Long> implements PimsSysReqFieldDao {
    /**
     * Constructor that takes in a class to see which type of entity to persist.
     * Use this constructor when subclassing.
     */
    public PimsSysReqFieldDaoHibernate() {
        super(PimsSysReqField.class);
    }

    @Override
    public void deleteFields(String mid) {
        Query query = getSession().createQuery("delete from PimsSysReqField where fieldid in(:id)");
        String[] str = mid.split(",");
        Long idArray[] = new Long[str.length];
        int i = 0;
        for(String s : str) {
            idArray[i] = Long.valueOf(s);
            i++;
        }
        query.setParameterList("id", idArray).executeUpdate();
    }

    @Override
    public List<PimsSysReqField> getReqFieldList(String sql, Long hospitalId, Long pathologyId) {
        SQLQuery query = getSession().createSQLQuery(sql);
        query.setParameter("hospitalId", hospitalId);
        query.setParameter("pathologyId", pathologyId);
        query.addEntity("srf", PimsSysReqField.class);
        return query.list();
    }

    /**
     * 根据对象ID获取系统申请配置字段信息表
     * @param fieelementid
     * @return
     */
    @Override
    public PimsSysReqField getInfo(String fieelementid) {
        return (PimsSysReqField)getSession().createQuery(" from PimsSysReqField where fieelementid = '"+ fieelementid+"'").uniqueResult();
    }
}
