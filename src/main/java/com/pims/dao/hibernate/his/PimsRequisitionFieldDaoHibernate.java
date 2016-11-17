package com.pims.dao.hibernate.his;

import com.pims.dao.his.PimsRequisitionFieldDao;
import com.pims.model.*;
import com.smart.dao.hibernate.GenericDaoHibernate;
import org.springframework.stereotype.Repository;

/**
 * Created by king on 2016/9/28.
 */
@Repository("pimsRequisitionFieldDao")
public class PimsRequisitionFieldDaoHibernate extends GenericDaoHibernate<PimsRequisitionField,Long>
        implements PimsRequisitionFieldDao{
    public PimsRequisitionFieldDaoHibernate(){super(PimsRequisitionField.class);}

}
