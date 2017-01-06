package com.pims.service.impl.pimspathologysample;

import com.pims.dao.pimspathologysample.PimsPathologySampleDao;
import com.pims.model.PimsBaseModel;
import com.pims.model.PimsPathologySample;
import com.pims.service.pimspathologysample.PimsPathologySampleManager;
import com.pims.webapp.controller.GridQuery;
import com.smart.model.user.User;
import com.smart.service.impl.GenericManagerImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by king on 2016/10/10.
 */
@Service("pimsPathologySampleManager")
public class PimsPathologySampleManagerImpl extends GenericManagerImpl<PimsPathologySample, Long> implements PimsPathologySampleManager {
    private PimsPathologySampleDao pimsPathologySampleDao;

    @Autowired
    public void setPimsPathologySampleDao(PimsPathologySampleDao pimsPathologySampleDao) {
        this.pimsPathologySampleDao = pimsPathologySampleDao;
        this.dao = pimsPathologySampleDao;
    }

    @Override
    public List<PimsPathologySample> querySample(PimsPathologySample sample, GridQuery gridQuery) {
        StringBuffer sql = new StringBuffer("select p.sampleid,p.sampathologycode,p.samcustomerid,p.sampathologyid,p.samsenddoctorname," +
                "p.samreportorid,p.samauditerid,p.saminitiallyuserid,sp.patclass,p.samsamplestatus,p.sampatientname from PIMS_PATHOLOGY_SAMPLE p," +
                "Pims_Sys_Pathology sp where p.SamPathologyId=sp.pathologyid ");
        setParameter(sql, sample);
        sql.append(" order by p.sampathologycode asc");
        return pimsPathologySampleDao.querySample(sample, gridQuery, sql.toString());
    }

    private void setParameter(StringBuffer sql, PimsPathologySample sample) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long pathologyId = sample.getSampathologyid();
        long sampleStatus = sample.getSamsamplestatus();
        String inspectionId = sample.getSaminspectionid();
        String pathologyCode = sample.getSampathologycode();
        String patientName = sample.getSampatientname();
        String samfirstv = sample.getSamfirstv();
        Date from = sample.getSamplesectionfrom();
        Date to = sample.getSamplesectionto();
        if(!StringUtils.isEmpty(samfirstv)){
            if(samfirstv.equals("0")){
                sql.append(" and p.sampleid in (select tassampleid from PIMS_PATHOLOGY_TASK where tastasktype = 0 and taspromoterid ='"+user.getId()+"')");
            }else{
                samfirstv = String.valueOf(Integer.valueOf(samfirstv)-1);
                sql.append("and p.sampleid in (select tassampleid from PIMS_PATHOLOGY_TASK where tastasktype = 0 and tastaskstate = "+samfirstv+" and tasreciverid ='"+user.getId()+"')");
            }
        }
        if(!StringUtils.isEmpty(sample.getSampiecedoctorid())){
            sql.append(" and sampiecedoctorid = '"+sample.getSampiecedoctorid()+"'");
        }
        if (sampleStatus > 0) {
            sql.append("and p.samsamplestatus=:SamSampleStatus ");
        }
        if (pathologyId > 0) {
            sql.append("and p.sampathologyid=:SamPathologyId ");
        }
        if (StringUtils.isNotEmpty(inspectionId)) {
            sql.append("and p.saminspectionid=:SamInspectionId ");
        }
        if (StringUtils.isNotEmpty(pathologyCode)) {
            sql.append("and p.sampathologycode=:SamPathologyCode ");
        }
        if (StringUtils.isNotEmpty(patientName)) {
            sql.append("and p.sampatientname=:SamPatientName ");
        }
        if(from != null || to != null)
            sql.append("and p.sampleid in (select pp.parsampleid from PIMS_PATHOLOGY_PARAFFIN pp where pp.parsectionedtime between :samplesectionfrom and  :samplesectionto)");
    }

    @Override
    public Integer querySampleNum(PimsPathologySample sample) {
        StringBuffer sql = new StringBuffer("select count(1) cnt from PIMS_PATHOLOGY_SAMPLE p where 1=1 ");
        setParameter(sql, sample);
        return pimsPathologySampleDao.totalNum(sample, sql.toString());
    }

    /**
     * 查询标本列表
     *
     * @param map
     * @return
     */
    @Override
    public List<PimsPathologySample> getSampleList(PimsBaseModel map) {
        return pimsPathologySampleDao.getSampleList(map);
    }

    /**
     * 查询标本数量
     *
     * @param map
     * @return
     */
    @Override
    public int getReqListNum(PimsBaseModel map) {
        return pimsPathologySampleDao.getReqListNum(map);
    }

    /**
     * 查询标本内容
     *
     * @param id
     * @return
     */
    @Override
    public PimsPathologySample getBySampleNo(Long id) {
        return pimsPathologySampleDao.getBySampleNo(id);
    }

    /**
     * 逻辑删除申请单
     *
     * @param id
     * @return
     */
    @Override
    public boolean delete(Long id) {
        return pimsPathologySampleDao.delete(id);
    }

    /**
     * 查询单据是否可修改
     *
     * @param id,sts
     * @return
     */
    @Override
    public boolean canChange(Long id, String sts) {
        return pimsPathologySampleDao.canChange(id, sts);
    }

    @Override
    public void sign(PimsPathologySample sample) {
        pimsPathologySampleDao.sign(sample);
    }

    /**
     * 获取最大条码号
     * @return
     */
    @Override
    public String sampleCode() {
        return pimsPathologySampleDao.sampleCode();
    }

    @Override
    public int getSamStaNum() {
        return pimsPathologySampleDao.getSamStaNum();
    }

    @Override
    public List getSList(PimsBaseModel map) {
        return pimsPathologySampleDao.getSList(map);
    }

    @Override
    public int getSNum(PimsBaseModel map) {
        return pimsPathologySampleDao.getSNum(map);
    }

    @Override
    public List getReportList(PimsBaseModel map) {
        return pimsPathologySampleDao.getReportList(map);
    }

    @Override
    public int getReportNum(PimsBaseModel map) {
        return pimsPathologySampleDao.getReportNum(map);
    }

    @Override
    public List getReportDelayList(PimsBaseModel map) {
        return pimsPathologySampleDao.getReportDelayList(map);
    }

    @Override
    public int getReportDelayNum(PimsBaseModel map) {
        return pimsPathologySampleDao.getReportDelayNum(map);
    }

    private List ChangeList(String[] st,List list){
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        if(list == null || list.size() == 0){
            return mapList;
        }else{

            for(Object bean:list){
                Map<String, Object> map1 = new HashMap<String, Object>();
                Object[] pd = (Object[]) bean;
                for(int i=0;i<st.length;i++){
                    Object o = pd[i];
                    map1.put(st[i],o);
                }
                mapList.add(map1);
            }
            return mapList;
        }
    }

    /**
     * 日志统计总列表
     * @param map
     * @return
     */
    @Override
    public List getRztj(PimsBaseModel map) {
        List list = pimsPathologySampleDao.getRztj(map);
        String[] st = {"samsamplestatus0","samsamplestatus1","samsamplestatus2","samsamplestatus3","samsamplestatus4",
                "samsamplestatus5","samsamplestatus6","samsamplestatus7","samsamplestatus8"};
        return ChangeList(st,list);
    }

    /**
     * 日志统计详细列表
     * @param map
     * @return
     */
    @Override
    public List getRztjInfo(PimsBaseModel map) {
        List list = pimsPathologySampleDao.getRztjInfo(map);
        String[] st = {"sampathologyid","samsamplestatus0","samsamplestatus1","samsamplestatus2","samsamplestatus3","samsamplestatus4",
                "samsamplestatus5","samsamplestatus6","samsamplestatus7","samsamplestatus8"};
        return ChangeList(st,list);
    }

    /**
     * 标本来源统计
     * @param map
     * @return
     */
    @Override
    public List getBbly(PimsBaseModel map) {
        List list = pimsPathologySampleDao.getBbly(map);
        String[] st = {"name","nums"};
        return ChangeList(st,list);
    }

    /**
     * 收费统计报告
     * @param map
     * @return
     */
    @Override
    public List getSftj(PimsBaseModel map) {
        List list = pimsPathologySampleDao.getSftj(map);
        String[] st = {"name","prices"};
        return ChangeList(st,list);
    }
}
