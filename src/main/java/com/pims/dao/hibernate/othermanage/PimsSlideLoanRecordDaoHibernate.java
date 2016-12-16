package com.pims.dao.hibernate.othermanage;

import com.pims.dao.othermanage.PimsSlideLoanRecordDao;
import com.pims.model.PimsSlideRecord;
import com.smart.dao.hibernate.GenericDaoHibernate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zp on 2016/11/21.
 */

@Repository("pimsSlideLoanRecordDao")
public class PimsSlideLoanRecordDaoHibernate extends GenericDaoHibernate<PimsSlideRecord,Long> implements PimsSlideLoanRecordDao{
    public PimsSlideLoanRecordDaoHibernate() {
    super(PimsSlideRecord.class);
}

    public List<PimsSlideRecord> getRecordList(PimsSlideRecord map) {
    StringBuffer sb = new StringBuffer();
    sb.append(" from PimsSlideRecord where sliid = '" + map.getSliid()+"'");
    return getSession().createQuery(sb.toString()).list();
    }

//    public boolean returnSlide(PimsSlideRecord map) {
//        PimsSlideRecord a = new PimsSlideRecord();
//        a.setSliintime(new Date());
//        a.setSliid(map.getSliid());
//
//        try{
//           save(a);
//        }catch (Exception e){
//            e.printStackTrace();
//            return false;
//        }
//        return true;
//    }
}
