package com.pims.dao.hibernate.pimsPathologySample;

import com.pims.dao.pimspathologysample.PimsPathologyReceivemessageDao;
import com.pims.model.PimsBaseModel;
import com.pims.model.PimsPathologyReceivemessage;
import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.model.user.User;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by king on 2016/10/25.
 */
@Repository("pimsPathologyReceivemessageDao")
public class PimsPathologyReceivemessageDaoHibernate extends GenericDaoHibernate<PimsPathologyReceivemessage, Long>
        implements PimsPathologyReceivemessageDao {

    public PimsPathologyReceivemessageDaoHibernate() {
        super(PimsPathologyReceivemessage.class);
    }

    /**
     * 组装sql
     *
     * @param sb
     * @param map
     * @return
     */
    public StringBuffer getsql(StringBuffer sb, PimsBaseModel map) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        sb.append(" and receiveruserid ="+ user.getId());
        if(!StringUtils.isEmpty(map.getReq_sts())){
            sb.append(" and receivests = "+ map.getReq_sts());
        }
        if (map.getReq_bf_time() != null) {
            sb.append(" and meshandletime >= :req_bf_time");
        }
        if (map.getReq_af_time() != null) {
            sb.append(" and  meshandletime < :req_af_time");
        }
        return sb;
    }

    /**
     * 查询接收消息列表
     * @param map
     * @return
     */
    @Override
    public List getTaskList(PimsBaseModel map) {
        StringBuffer sb = new StringBuffer();
        sb.append(" from PimsPathologyMessage,PimsPathologyReceivemessage where recmessageid = messageid ");
        getsql(sb,map);
        return pagingList(sb.toString(),map.getStart(),map.getEnd(),map.getReq_bf_time(),map.getReq_af_time());
    }

    /**
     * 查询接收数量
     * @param map
     * @return
     */
    @Override
    public int getTaskListNum(PimsBaseModel map) {
        StringBuffer sb = new StringBuffer();
        sb.append(" select count(1) from pims_pathology_message,pims_pathology_receivemessage where recmessageid = messageid ");
        getsql(sb,map);
        return countTotal(sb.toString(),map.getReq_bf_time(),map.getReq_af_time());
    }

    /**
     * 更新状态已接收
     * @param id
     * @return
     */
    @Override
    public boolean updateConStates(Long id) {
        StringBuffer sb = new StringBuffer();
        sb.append(" update  pims_pathology_receivemessage set receivests = 1 ,receivedate=:receivedate " +
                "where receivemessageid = "+ id);
        Query query = getSession().createSQLQuery(sb.toString());
        query.setTimestamp("receivedate",new Date());
        query.executeUpdate();
        return true;
    }
}
