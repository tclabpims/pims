package com.pims.dao.hibernate.basedata;

import com.pims.dao.basedata.PimsCommonBaseDataDao;
import com.pims.dao.pimspathologysample.PimsPathologySampleDao;
import com.pims.model.PimsBaseModel;
import com.pims.model.PimsCommonBaseData;
import com.pims.model.PimsPathologySample;
import com.smart.dao.hibernate.GenericDaoHibernate;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by king on 2016/10/21.
 */
@Repository("pimsCommonBaseDataDao")
public class PimsCommonBaseDataDaoHibernate extends GenericDaoHibernate<PimsCommonBaseData,Long> implements PimsCommonBaseDataDao {

    public PimsCommonBaseDataDaoHibernate(){super(PimsCommonBaseData.class);}

    /**
     * 查询基础数据列表
     * @param map
     * @return
     */
    @Override
    public List<PimsCommonBaseData> getDataList(Map map) {
        StringBuffer sb = new StringBuffer();
        sb.append(" from PimsCommonBaseData where bduseflag = 1");
        if(!StringUtils.isEmpty(String.valueOf(map.get("bddatatype")))){//数据类型(1 病区数据,2科室数据，3 his医生，4送检医院)
            sb.append(" and bddatatype = " + map.get("bddatatype"));
        }
        if(!StringUtils.isEmpty(String.valueOf(map.get("bdcustomerid")))){//客户id
            sb.append(" and bdcustomerid = " + map.get("bdcustomerid"));
        }
        if(map.get("bdhisid") != null && !StringUtils.isEmpty(String.valueOf(map.get("bdhisid")))){//HIS ID
            sb.append(" and bdhisid = '" + map.get("bdhisid")+"'");
        }
        if(map.get("name") != null && !StringUtils.isEmpty(String.valueOf(map.get("name"))) && !String.valueOf(map.get("name")).toUpperCase().equals("NULL")){//名称
            String name = String.valueOf(map.get("name")).toUpperCase();
            sb.append(" and ( upper(bddatanamech) like '%"+name+"%' or upper(bddatanameen) like '%"+
                   name+ "%' or upper(bdpinyincode) like '%"+name+"%' or upper(bdfivestroke) like '%"+
                    name+"%' )");
        }
        sb.append(" order by bddatasort");
        return getSession().createQuery(sb.toString()).list();
    }
}
