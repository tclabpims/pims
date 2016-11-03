package com.pims.dao.hibernate.pimsPathologySample;

import com.pims.dao.pimspathologysample.PimsPathologyFeeDao;
import com.pims.model.PimsPathologyFee;
import com.smart.dao.hibernate.GenericDaoHibernate;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by king on 2016/10/10.
 */
@Repository("pimsPathologyFeeDao")
public class PimsPathologyFeeDaoHibernate extends GenericDaoHibernate<PimsPathologyFee, Long> implements PimsPathologyFeeDao {

    public PimsPathologyFeeDaoHibernate() {
        super(PimsPathologyFee.class);
    }

    /**
     * 根据费用来源查询病理的收费列表
     * @param map
     * @return
     */
    @Override
    public List<PimsPathologyFee> getSampleList(PimsPathologyFee map) {
        StringBuffer sb = new StringBuffer();
        sb.append(" from PimsPathologyFee where feesampleid="+map.getFeesampleid());
        return getSession().createQuery(sb.toString()).list();
    }
}
