package com.pims.dao.hibernate.pimsPathologySample;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pims.dao.pimspathologysample.PimsPathologyConsultationDao;
import com.pims.dao.pimspathologysample.PimsPathologyTaskDao;
import com.pims.model.*;
import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.model.user.User;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by king on 2016/10/25.
 */
@Repository("pimsPathologyTaskDao")
public class PimsPathologyTaskDaoHibernate extends GenericDaoHibernate<PimsPathologyTask, Long> implements PimsPathologyTaskDao {

    public PimsPathologyTaskDaoHibernate() {
        super(PimsPathologyTask.class);
    }

    /**
     * 组装sql
     *
     * @param sb
     * @param map
     * @return
     */
    public StringBuffer getsql(StringBuffer sb, PimsBaseModel map) {
//        if (!StringUtils.isEmpty(map.getLogyid())) {
//            sb.append(" and sampathologyid= " + map.getLogyid() );
//        }
        if (!StringUtils.isEmpty(map.getReq_code())) {
            sb.append(" and taspathologycode like '%" + map.getReq_code() + "%'");
        }
        if (map.getReq_bf_time() != null) {
            sb.append(" and tasfirstd >= :req_bf_time");
        }
        if (map.getReq_af_time() != null) {
            sb.append(" and  tasfirstd < :req_af_time");
        }
        return sb;
    }

    /**
     * 抄送列表
     *
     * @param map
     * @return
     */
    @Override
    public List getTaskList(PimsBaseModel map) {
        StringBuffer sb = new StringBuffer();
        sb.append(" select a.sampleid,a.saminspectionid,a.sampathologycode,a.samregisttime,a.samregistername," +
                "a.saminitiallyusername,a.sampathologyid,tasrecivername,max(piedoctorname) as piedoctorname,max(pieembeddoctorname) " +
                "as pieembeddoctorname,max(parsectioneddoctor) as parsectioneddoctor from pims_pathology_sample a," +
                "pims_pathology_pieces b,pims_pathology_paraffin c,pims_pathology_task d  where a.sampleid = b.piesampleid and " +
                "a.sampleid = c.parsampleid and a.sampleid = d.tassampleid and tastasktype = 0  ");
        getsql(sb, map);
        sb.append(" group by a.sampleid,a.saminspectionid,a.sampathologycode," +
                "a.samregisttime,a.samregistername,a.saminitiallyusername,a.sampathologyid,tasrecivername ");
        Query query = getSession().createSQLQuery(sb.toString());
        if(map.getReq_bf_time() != null){
            query.setDate("req_bf_time",map.getReq_bf_time());
        }
        if(map.getReq_af_time() != null){
            query.setDate("req_af_time",map.getReq_af_time());
        }
        query.setFirstResult(map.getStart());
        query.setMaxResults(map.getEnd());
        System.out.println(query.toString());
        return query.list();
    }

    /**
     * 抄送列表数量
     *
     * @param map
     * @return
     */
    @Override
    public int getTaskListNum(PimsBaseModel map) {
        StringBuffer sb = new StringBuffer();
        sb.append(" select count(1) from pims_pathology_sample a," +
                "pims_pathology_pieces b,pims_pathology_paraffin c,pims_pathology_task d where a.sampleid = b.piesampleid and " +
                "a.sampleid = c.parsampleid and a.sampleid = d.tassampleid and  tastasktype = 0  ");
        getsql(sb, map);
        sb.append(" group by a.sampleid,a.saminspectionid,a.sampathologycode," +
                "a.samregisttime,a.samregistername,a.saminitiallyusername,a.sampathologyid,tasrecivername ");
        return countTotal(sb.toString(), map.getReq_bf_time(), map.getReq_af_time());
    }

    /**
     * 更新任务状态
     *
     * @param states（0   取消抄送 1 抄送接收）
     * @param sampleList
     * @return Map
     */
    @Override
    public JSONObject updateConStates(int states, JSONArray sampleList) {
        JSONObject map = new JSONObject();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < sampleList.size(); i++) {
            Map map1 = (Map) sampleList.get(i);
            PimsPathologySample sample = (PimsPathologySample) setBeanProperty(map1, PimsPathologySample.class);
            sb = new StringBuffer();
            sb.append(" from PimsPathologyTask where tastasktype = 0 and tassampleid = " + sample.getSampleid());
            Object oo = getSession().createQuery(sb.toString()).uniqueResult();
            if (oo == null) {
                map.put("success", false);
                map.put("message", "查不到抄送信息!");
                return map;
            } else {
                PimsPathologyTask task = (PimsPathologyTask) oo;
                if(task.getTastaskstate() == 0){
                    if(states == 0){//取消抄送
                        sb = new StringBuffer();
                        sb.append(" delete from pims_pathology_task where taskid = "+ task.getTaskid());
                        getSession().createSQLQuery(sb.toString()).executeUpdate();
                    }else{//接收抄送
                        sb = new StringBuffer();
                        sb.append(" update  pims_pathology_task set tastaskstate = 1,tasreciverid= :tasreciverid," +
                                "tasrecivername = :tasrecivername,tassecondd = :tassecondd where taskid = "+ task.getTaskid());
                        Query query = getSession().createSQLQuery(sb.toString());
                        query.setString("tasreciverid",String.valueOf(user.getId()));
                        query.setString("tasrecivername",user.getName());
                        query.setTimestamp("tassecondd",new Date());
                        query.executeUpdate();
                    }
                }else{
                    map.put("success", false);
                    map.put("message", "已接收的抄送信息无法取消或再次接收!");
                    return map;
                }
            }
        }
        map.put("success", true);
        map.put("message", "操作成功!");
        return map;
    }

    /**
     * 判断抄送是否已存在
     * @param states
     * @param id
     * @return
     */
    @Override
    public boolean isExistsTask(int states, Long id) {
        StringBuffer  sb = new StringBuffer();
        sb.append(" select count(1) from Pims_Pathology_Task where tastasktype ="+states+"  and tassampleid = " + id);
        int nums = countTotal(sb.toString());
        if(nums == 0){
            return  true;
        }
        return false;
    }
}
