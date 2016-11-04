package com.pims.dao.hibernate.pimssyspathology;

import com.pims.dao.pimspathologysample.PimsPathologySampleDao;
import com.pims.dao.pimssyspathology.PimsSysTestFeeDao;
import com.pims.model.PimsBaseModel;
import com.pims.model.PimsPathologySample;
import com.pims.model.PimsSysTestFee;
import com.pims.webapp.controller.GridQuery;
import com.smart.dao.hibernate.GenericDaoHibernate;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by king on 2016/10/10.
 */
@Repository("pimsSysTestFeeDao")
public class PimsSysTestFeeDaoHibernate extends GenericDaoHibernate<PimsSysTestFee, Long> implements PimsSysTestFeeDao {

    public PimsSysTestFeeDaoHibernate() {
        super(PimsSysTestFee.class);
    }

    @Override
    public List getTestFeeList(String testid) {
        StringBuffer sb = new StringBuffer();
        sb.append(" from PimsSysChargeItems a,PimsSysChargeitemRef b,PimsSysTestFee c where a.chargeitemid = b.chargeitemid " +
                "and c.tffeeid = a.chargeitemid and tfflag = 1 and chiuseflag = 1 and tftestid ="+testid+")");
        return  getSession().createQuery(sb.toString()).list();
    }
}
