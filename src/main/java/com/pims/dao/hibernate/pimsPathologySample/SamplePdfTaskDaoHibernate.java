package com.pims.dao.hibernate.pimsPathologySample;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pims.dao.pimspathologysample.PimsPathologyTaskDao;
import com.pims.dao.pimspathologysample.SamplePdfTaskDao;
import com.pims.model.PimsBaseModel;
import com.pims.model.PimsPathologySample;
import com.pims.model.PimsPathologyTask;
import com.pims.model.SamplePdfTask;
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
@Repository("samplePdfTaskDao")
public class SamplePdfTaskDaoHibernate extends GenericDaoHibernate<SamplePdfTask, Long> implements SamplePdfTaskDao {

    public SamplePdfTaskDaoHibernate() {
        super(SamplePdfTask.class);
    }

    /**
     * 列表
     * @return
     */
    @Override
    public List getTaskList() {
        StringBuffer sb = new StringBuffer();
        sb.append(" from SamplePdfTask where taskstates < 2 order by taskcreatetime");
        Query query = getSession().createQuery(sb.toString());
        return query.list();
    }
}
