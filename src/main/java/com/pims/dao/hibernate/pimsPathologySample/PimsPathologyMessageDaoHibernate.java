package com.pims.dao.hibernate.pimsPathologySample;

import com.pims.dao.pimspathologysample.PimsPathologyMessageDao;
import com.pims.dao.pimspathologysample.PimsPathologyReceivemessageDao;
import com.pims.model.PimsBaseModel;
import com.pims.model.PimsPathologyMessage;
import com.pims.model.PimsPathologyReceivemessage;
import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.model.user.User;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by king on 2016/10/25.
 */
@Repository("pimsPathologyMessageDao")
public class PimsPathologyMessageDaoHibernate extends GenericDaoHibernate<PimsPathologyMessage, Long>
        implements PimsPathologyMessageDao {

    public PimsPathologyMessageDaoHibernate() {
        super(PimsPathologyMessage.class);
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
        sb.append(" and messenderid ="+ user.getId());
        if (map.getReq_bf_time() != null) {
            sb.append(" and meshandletime >= :req_bf_time");
        }
        if (map.getReq_af_time() != null) {
            sb.append(" and  meshandletime < :req_af_time");
        }
        return sb;
    }

    /**
     * 查询消息列表
     * @param map
     * @return
     */
    @Override
    public List getTaskList(PimsBaseModel map) {
        StringBuffer sb = new StringBuffer();
        sb.append(" from PimsPathologyMessage where 1 = 1 ");
        getsql(sb,map);
        return pagingList(sb.toString(),map.getStart(),map.getEnd(),map.getReq_bf_time(),map.getReq_af_time());
    }

    /**
     * 查询数量
     * @param map
     * @return
     */
    @Override
    public int getTaskListNum(PimsBaseModel map) {
        StringBuffer sb = new StringBuffer();
        sb.append(" select count(1) from pims_pathology_message where 1= 1 ");
        getsql(sb,map);
        return countTotal(sb.toString(),map.getReq_bf_time(),map.getReq_af_time());
    }

    @Override
    public PimsPathologyMessage getBySampleNo(Long id) {
        StringBuffer sb = new StringBuffer();
        sb.append(" from PimsPathologyMessage where messageid = "+id);
        Object o = getSession().createQuery(sb.toString()).uniqueResult();
        if(o == null) return null;
        return (PimsPathologyMessage)o;
    }
}
