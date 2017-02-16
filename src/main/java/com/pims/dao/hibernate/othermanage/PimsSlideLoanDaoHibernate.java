package com.pims.dao.hibernate.othermanage;//package com.pims.dao.hibernate.othermanage;

import com.pims.dao.othermanage.PimsSlideLoanDao;
import com.pims.model.PimsBaseModel;
import com.pims.model.PimsPathologySlide;
import com.pims.webapp.controller.WebControllerUtil;
import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.model.user.User;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository("pimsSlideLoanDao")
public class PimsSlideLoanDaoHibernate extends GenericDaoHibernate<PimsPathologySlide,Long>
        implements PimsSlideLoanDao {
    public PimsSlideLoanDaoHibernate() {
        super(PimsPathologySlide.class);
    }
    Date t = new Date();
    public StringBuffer getSearchSql(StringBuffer sb, PimsBaseModel map) {
        if (!StringUtils.isEmpty(map.getSlipathologyid())) {
            sb.append(" and P.sampathologyid = '" + map.getSlipathologyid()+"'");//条形码
        }
        if (!StringUtils.isEmpty(map.getSliid())) {
            sb.append(" and s.slislidebarcode ='" + map.getSliid()+"'");//玻片id
        }
        if (!StringUtils.isEmpty(map.getPatient_name())) {
            sb.append(" and upper(P.sampatientname) like '%" + map.getPatient_name().toUpperCase()+"%'");//病人
        }
        if (!StringUtils.isEmpty(map.getLogyid())) {
            sb.append(" and S.slipathologycode = '" + map.getLogyid().toUpperCase()+"'");//病理编号
        }
        if (!StringUtils.isEmpty(map.getCurrent())) {
            sb.append(" and S.slistockin = '" + map.getCurrent()+"'");//在库状态
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
    public List getLoanList(PimsBaseModel map) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT\n" +
                "\ts.slistockin,\n" +
                "\ts.slipathologycode,\n" +
                "\ts.slislidebarcode,\n" +
                "\ts.slicreatetime,\n" +
                "\tP .samsamplename,\n" +
                "\tP .sampathologyid,\n" +
                "\tP .sampatientname,\n" +
                "\tP .sampatientsex,\n" +
                "\tP .sampatientage,\n" +
                "\tP .sampatientagetype\n" +
                "from PIMS_PATHOLOGY_SLIDE s,PIMS_PATHOLOGY_SAMPLE P \n" +
                "WHERE s.slipathologycode=P.SAMPATHOLOGYCODE ");
        getSearchSql(sb, map);
        String orderby = (map.getSidx() == null || map.getSidx().trim().equals("")) ? "P.sampatientname" : map.getSidx();
        sb.append(" order by " + orderby + " " + map.getSord());
        System.out.println(sb.toString());
        SQLQuery query = getSession().createSQLQuery(sb.toString());
        query.setFirstResult(map.getStart());
        query.setMaxResults(map.getEnd());
        return query.list();
    }

    @Override
    public List getLoanList2(PimsBaseModel map){
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT s.slislidebarcode,r.slitime,r.SLICUSTOMERNAME,r.slicustomerid from PIMS_PATHOLOGY_SLIDE s,PIMS_SLIDE_RECORD r WHERE r.sliid=s.slislidebarcode and r.SLICURRENT='0' and r.slicustomerid='"+map.getSliid()+"'");
        return getSession().createSQLQuery(sb.toString()).list();
    }

    @Override
    public boolean loan(Map map){
        Date sliintime = (Date)map.get("sli_in_time");
        User user = WebControllerUtil.getAuthUser();
        if(map == null){
            return false;
        }else{

            String sql = "update PIMS_PATHOLOGY_SLIDE set slistockin='0' where slislidebarcode = '" + map.get("sliid")+"'";
            Query query= getSession().createSQLQuery(sql);
            query.executeUpdate();
            return  true;
        }
    }

    @Override
    public boolean returnSlide3(PimsBaseModel map){
        if(map == null){
            return false;
        }else{
            String sql3 = "update PIMS_PATHOLOGY_SLIDE set slistockin = '1' where slislidebarcode='"+map.getSliid()+"'";
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
        sb.append(" select count(1) from PIMS_PATHOLOGY_SLIDE s,PIMS_PATHOLOGY_SAMPLE P where s.slipathologycode=P.SAMPATHOLOGYCODE ");
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
    public PimsPathologySlide getByLoanNo(Long id) {
        if (id == null) {
            return null;
        } else {
            Query query = getSession().createQuery("from PimsSlideLoan where sliid = " + id);
            if (query.list() == null || query.list().size() != 1) {
                return null;
            } else {
                return (PimsPathologySlide) query.list().get(0);
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
