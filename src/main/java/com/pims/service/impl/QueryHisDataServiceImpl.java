package com.pims.service.impl;

import com.pims.model.HisChargePrice;
import com.pims.model.PatientInfo;
import com.pims.model.PimsPathologyFee;
import com.pims.model.PimsPathologySample;
import com.pims.service.QueryHisDataService;
import com.pims.service.pimspathologysample.PimsPathologySampleManager;
import com.smart.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 909436637@qq.com on 2016/10/12.
 * Description:
 */
public class QueryHisDataServiceImpl implements QueryHisDataService {

    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Integer queryHisChargePrice(String query) {
        StringBuffer buffer = new StringBuffer("select count(1) cnt from V_HSBHJ_CHARGEPRICE h");
        if (query != null && !"".equals(query.trim())) {
            buffer.append(" where h.sfxmmc like '%").append(query).append("%'");
        }
        return jdbcTemplate.queryForObject(buffer.toString(), Integer.class);
    }

    //查询HIS系统中的收费项目信息
    @Override
    public List queryHisChargePrice(final String query, final int start, final int end) {

        StringBuffer sql = new StringBuffer("select * from (select H.Sfxmid,H.Sfxmmc,H.Sfxmdj, rownum as rowno from V_HSBHJ_CHARGEPRICE h");
        if (query != null && !"".equals(query.trim())) {
            sql.append(" where h.sfxmmc like '%").append(query).append("%'");
        }
        sql.append(" ) where rowno between ? and ?");


        return jdbcTemplate.query(sql.toString(), new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setInt(1, start);
                preparedStatement.setInt(2, end);
            }
        }, new ResultSetExtractor<List>() {
            @Override
            public List extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                List result = new ArrayList();
                while (resultSet.next()) {
                    HisChargePrice hcp = new HisChargePrice(resultSet.getLong("sfxmid"), String.valueOf(resultSet.getObject("sfxmmc")), resultSet.getDouble("sfxmdj"));
                    result.add(hcp);
                }
                return result;
            }
        });
    }
    /**
     * 获取住院病人信息
     * @param query
     * @return
     */
    @Override
    public Integer queryPatientNum(String query) {
        StringBuffer buffer = new StringBuffer("SELECT COUNT(1) FROM (SELECT BRJZHM FROM V_HSBCI_TREATINFO WHERE BRJZHM ='"+query+"' UNION ALL  " +
                "SELECT BRJZHM FROM V_HSBBI_RECORDHOME WHERE BRJZHM ='"+query+"')" );
        return jdbcTemplate.queryForObject(buffer.toString(), Integer.class);
    }
    /**
     * 获取住院病人信息
     * @param query
     * @return
     */
    @Override
    public List queryPatientList(String query) {
        StringBuffer sql = new StringBuffer("select b.brjzxh as key_no , b.brjzhm as patient_id,b.brdabh as inpatient_id,b.brdaxm as patient_name," +
                "b.brdaxb as patient_sex,b.brcsrq as patient_birth,regexp_substr(b.brjznl,'[0-9]+') as patient_age," +
                "substr(b.brjznl,-1) as patient_age_type,b.brdamz as patient_nation,'2' as patient_type,b.jzksid as patient_dept, " +
                "b.jzksmc as patient_dept_name,b.jzksid as patient_ward, " +
                "b.jzksmc as patient_ward_name,'' as patient_bed, " +
                "b.lczdmc as lczd,b.brdwmc as commpany,b.brsfzh as id_cardno,b.brjtdz as patient_address,b.brlxdh as phone_no, " +
                "b.brlbid as chargr_type from v_hsbci_treatinfo b where b.brjzhm='"+query+"'" +
                " union all " +
                "select a.brzyid as key_no, a.brjzhm as patient_id,a.brdabh as inpatient_id,a.brdaxm as patient_name, " +
                "a.brdaxb as patient_sex,a.brcsrq as patient_birth,to_char(a.brjznl) as patient_age,a.brnldw as patient_age_type, " +
                "a.brdamz as patient_nation,'1' as patient_type,a.zyksid as patient_dept, " +
                "(select zzksmc from v_hsbhi_deptinfo where zzksid =a.zyksid) as patient_dept_name,a.zybqid as patient_ward, " +
                "(select zzksmc from v_hsbhi_deptinfo where zzksid =a.zybqid) as patient_ward_name,a.zycwhm as patient_bed, " +
                "a.ryzdmc as lczd,a.brdwmc as commpany,a.brsfzh as id_cardno,a.brjtdz as patient_address,a.brlxdh as phone_no, " +
                "a.brlbid as chargr_type from v_hsbbi_recordhome a where  a.brzyzt > 1 and a.brjzhm ='"+query+"'");
        return jdbcTemplate.query(sql.toString(), new ResultSetExtractor<List>() {
            @Override
            public List extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                List result = new ArrayList();
                while (resultSet.next()) {
                    PatientInfo pi = new PatientInfo();
                    pi.setKey_no(resultSet.getLong("key_no"));//Id
                    pi.setPatient_id(resultSet.getString("patient_id"));//住院号
                    pi.setInpatient_id(resultSet.getString("inpatient_id"));//档案号
                    pi.setPatient_name(resultSet.getString("patient_name"));//病人姓名
                    pi.setPatient_sex(resultSet.getString("patient_sex"));//性别
                    pi.setPatient_birth(resultSet.getDate("patient_birth"));//出生日期
                    pi.setPatient_age(resultSet.getString("patient_age"));//年龄
                    pi.setPatient_age_type(resultSet.getString("patient_age_type"));//年龄类型
                    pi.setPatient_nation(resultSet.getString("patient_nation"));//民族
                    pi.setPatient_type(resultSet.getString("patient_type"));//患者类型
                    pi.setPatient_dept(resultSet.getString("patient_dept"));//住院科室Id
                    pi.setPatient_dept_name(resultSet.getString("patient_dept_name"));//住院科室名称
                    pi.setPatient_ward(resultSet.getString("patient_ward"));//住院病区Id
                    pi.setPatient_ward_name(resultSet.getString("patient_ward_name"));//住院病区名称
                    pi.setPatient_bed(resultSet.getString("patient_bed"));//床号
                    pi.setLczd(resultSet.getString("lczd"));//临床诊断
                    pi.setCommpany(resultSet.getString("commpany"));//病人单位名称
                    pi.setId_cardno(resultSet.getString("id_cardno"));//身份证号
                    pi.setPatient_address(resultSet.getString("patient_address"));//家庭地址
                    pi.setPhone_no(resultSet.getString("phone_no"));//联系电话
                    pi.setChargr_type(resultSet.getString("chargr_type"));//收费类别Id
                    result.add(pi);
                }
                return result;
            }
        });
    }

    @Autowired
    private PimsPathologySampleManager pimsPathologySampleManager;
    @Override
    public boolean insert(PimsPathologyFee fee) {
        PimsPathologySample pathology = pimsPathologySampleManager.get(fee.getFeesampleid());
        String sql = " insert into V_HSBDI_CHARGEDETAIL (BRZYID,BRJZHM,FYJLID,FYXMID,FYXMC,FYHJJE,FYFSRQ,SQMXID,FYJLZT) values (" +pathology.getSampatientnumber()+
                ",'"+pathology.getSampatientnumber()+"',"+fee.getFeehisitemid()+","+fee.getFeeitemid()+",'"+fee.getFeehisname()+"',"+fee.getFeehisprice()*fee.getFeeamount()+
                ",to_date('"+ Constants.SDF.format(fee.getFeetime())+"','YYYY-MM-DD HH24:MI:SS'),"+fee.getFeeid()+",1)";
        jdbcTemplate.execute(sql);
        return true;
    }
}
