package com.pims.dao.hibernate.pimssyspathology;

import com.pims.dao.pimssyspathology.PimsSysColorDao;
import com.pims.model.PimsSysColor;
import com.smart.dao.hibernate.GenericDaoHibernate;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by 909436637@qq.com on 2016/10/10.
 * Description:
 */
@Repository("pimsSysColorDao")
public class PimsSysColorDaoHibernate extends GenericDaoHibernate<PimsSysColor, Long> implements PimsSysColorDao {
    /**
     * Constructor that takes in a class to see which type of entity to persist.
     * Use this constructor when subclassing.
     */
    public PimsSysColorDaoHibernate() {
        super(PimsSysColor.class);
    }

    public StringBuffer getSearchSql(StringBuffer sb, PimsSysColor psc) {
        if (!StringUtils.isEmpty(psc.getColobject())) {
            sb.append(" and colobject = '" + psc.getColobject()+"'");//病种类别
        }
        if (!StringUtils.isEmpty(psc.getColobject())) {
            sb.append(" and colobjectstate = " + psc.getColobjectstate());//病种类别
        }
        if (!StringUtils.isEmpty(psc.getColobject())) {
            sb.append(" and colmodule = " + psc.getColmodule());//病种类别
        }
        if (!StringUtils.isEmpty(psc.getColobject())) {
            sb.append(" and colowner = " + psc.getColowner());//病种类别
        }
        if (!StringUtils.isEmpty(psc.getColobject())) {
            sb.append(" and colcustomerid = " + psc.getColcustomercode());//病种类别
        }
        if (!StringUtils.isEmpty(psc.getColobject())) {
            sb.append(" and coltype = " + psc.getColtype());//病种类别
        }
        return sb;
    }
    @Override
    public boolean isExisted(PimsSysColor psc){
        StringBuffer sb = new StringBuffer();
        sb.append("select colorid from PIMS_SYS_COLOR where 1=1");
        getSearchSql(sb,psc);
        Query query = getSession().createSQLQuery(sb.toString());
        if(query.list().size()>0&&query.list()!=null){
            return true;
        }else {
            return false;
        }
    }
}
