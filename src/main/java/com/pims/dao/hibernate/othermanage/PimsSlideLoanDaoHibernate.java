package com.pims.dao.hibernate.othermanage;//package com.pims.dao.hibernate.othermanage;

import com.pims.dao.othermanage.PimsSlideLoanDao;
import com.pims.model.PimsBaseModel;
import com.pims.model.PimsSlideLoan;
import com.pims.webapp.controller.WebControllerUtil;
import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.model.user.User;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository("pimsSlideLoanDao")
public class PimsSlideLoanDaoHibernate extends GenericDaoHibernate<PimsSlideLoan,Long>
        implements PimsSlideLoanDao {
    public PimsSlideLoanDaoHibernate() {
        super(PimsSlideLoan.class);
    }
    Date t = new Date();
    public StringBuffer getSearchSql(StringBuffer sb, PimsBaseModel map) {
        if (!StringUtils.isEmpty(map.getSlipathologyid())) {
            sb.append(" and slipathologyid = '" + map.getSlipathologyid()+"'");//条形码
        }
        if (!StringUtils.isEmpty(map.getSliid())) {
            sb.append(" and sliid = '" + map.getSliid()+"'");//玻片id
        }
        if (!StringUtils.isEmpty(map.getPatient_name())) {
            sb.append(" and slipatientname like '%" + map.getPatient_name().toUpperCase()+"%'");//病人
        }
        if (!StringUtils.isEmpty(map.getLogyid())) {
            sb.append(" and pathologyid = '" + map.getLogyid().toUpperCase()+"'");//病理编号
        }
        if (!StringUtils.isEmpty(map.getCurrent())) {
            sb.append(" and slicurrent = '" + map.getCurrent()+"'");//在库状态
        }
        if (!StringUtils.isEmpty(map.getSlicustomerid())) {
            sb.append(" and slicustomerid = '" + map.getSlicustomerid().toUpperCase()+"'");//病理编号
        }
        return sb;
    }

    /**
     * 查询标本列表
     *
     * @param map
     * @return
     */
    @Override
    public List<PimsSlideLoan> getLoanList(PimsBaseModel map) {
        StringBuffer sb = new StringBuffer();
        sb.append("from PimsSlideLoan where 1=1");
        getSearchSql(sb, map);
        String orderby = (map.getSidx() == null || map.getSidx().trim().equals("")) ? "sliid" : map.getSidx();
        sb.append(" order by " + orderby + " " + map.getSord());
        System.out.println(sb.toString());
        return pagingList(sb.toString(),map.getStart(),map.getEnd());
    }

    @Override
    public boolean loan(Map map){
        Date sliintime = (Date)map.get("sli_in_time");
        User user = WebControllerUtil.getAuthUser();
        if(map == null){
            return false;
        }else{

            String sql = "update PIMS_SLIDE_LOAN set slicurrent='0',sliintime=:sliintime,slicustomername='"+map.get("customer_name")+"',slicustomerid='"+map.get("slicustomerid")+"',sliouttime=:sliouttime where sliid = '" + map.get("sliid")+"'";
            Query query= getSession().createSQLQuery(sql);
            query.setDate("sliintime",sliintime);
            query.setDate("sliouttime",new Date());
            query.executeUpdate();
            return  true;
        }
    }

    @Override
        public boolean returnSlide3(PimsBaseModel map){
        if(map == null){
            return false;
        }else{
            String sql3 = "update PIMS_SLIDE_LOAN set slicurrent = 1,sliintime='',slicustomername='',slicustomerid='',sliouttime='' where sliid='"+map.getSliid()+"'";
            Query query3 = getSession().createSQLQuery(sql3);
            query3.executeUpdate();
            return  true;
        }
    }
    /**
     * 查询标本数量
     *
     * @param map
     * @return
     */
    @Override
    public int getReqListNum(PimsBaseModel map) {
        StringBuffer sb = new StringBuffer();
        sb.append(" select count(1) from PIMS_SLIDE_LOAN where 1=1 ");
        getSearchSql(sb, map);
        return countTotal(sb.toString(),map.getSli_in_time()).intValue();
    }

    /**
     * 查询标本内容
     *
     * @param id
     * @return
     */
    @Override
    public PimsSlideLoan getByLoanNo(Long id) {
        if (id == null) {
            return null;
        } else {
            Query query = getSession().createQuery("from PimsSlideLoan where sliid = " + id);
            if (query.list() == null || query.list().size() != 1) {
                return null;
            } else {
                return (PimsSlideLoan) query.list().get(0);
            }
        }
    }


    /**
     * 查询单据是否可以被修改或删除
     *
     * @param id
     * @return
     */
    @Override
    public boolean canChange(Long id, String sts) {
        if (id == null || StringUtils.isEmpty(sts)) {
            return false;
        } else if (sts.equals("1")) {
            String sql = "select count(1) from PIMS_SLIDE_LOAN where slicurrent in (1,2) and sliid = " + id;
            if (countTotal(sql).intValue() == 1) {
                return true;
            }
        } else if (sts.equals("2")) {
            String sql = "select count(1) from PIMS_SLIDE_LOAN where slicurrent = 0 and sliid = " + id;
            if (countTotal(sql).intValue() == 1) {
                return true;
            }
        }
        return false;
    }

}
