package com.pims.dao.hibernate.othermanage;

import com.pims.dao.othermanage.PimsSlideLoanRecordDao;
import com.pims.model.PimsBaseModel;
import com.pims.model.PimsSlideRecord;
import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.model.qc.QcBatch;
import org.springframework.stereotype.Repository;
import org.hibernate.Query;
import java.util.List;

/**
 * Created by zp on 2016/11/21.
 */

@Repository("pimsSlideLoanRecordDao")
public class PimsSlideLoanRecordDaoHibernate extends GenericDaoHibernate<PimsSlideRecord,Long> implements PimsSlideLoanRecordDao{
    public PimsSlideLoanRecordDaoHibernate() {
    super(PimsSlideRecord.class);
}

    public List<PimsSlideRecord> getRecordList(PimsSlideRecord map, PimsBaseModel ppr) {
    StringBuffer sb = new StringBuffer();
    sb.append(" from PimsSlideRecord where sliid = '" + map.getSliid()+"'");
    String orderby = (ppr.getSidx() == null || ((String) ppr.getSidx()).trim().equals("")) ? "sliid" :ppr.getSidx();
    sb.append(" order by " + orderby + " " + ppr.getSord());
    Query query = getSession().createQuery(sb.toString());
    query.setFirstResult(ppr.getStart());
    query.setMaxResults(ppr.getEnd());
    return query.list();
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
