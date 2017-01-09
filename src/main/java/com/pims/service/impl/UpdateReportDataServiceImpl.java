package com.pims.service.impl;

import com.pims.model.*;
import com.pims.service.QueryHisDataService;
import com.pims.service.UpdateReportDataService;
import com.pims.service.pimspathologysample.PimsPathologySampleManager;
import com.pims.service.pimssyspathology.PimsSampleResultManager;
import com.smart.Constants;
import com.smart.model.lis.Sample;
import com.smart.util.Config;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class UpdateReportDataServiceImpl implements UpdateReportDataService {

    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    private PimsSampleResultManager pimsSampleResultManager;
    @Override
    public void insert(PimsPathologySample sample,PimsPathologyReportPdf rpdf, PimsSysPathology psp) {
        String samstatues = sample.getSamsamplestatus()==8?"已打印":"已审核";
        int patClass = Integer.parseInt(psp.getPatclass());
        Map<String, String> resultMap = null;
        String  zdjg = "";
        if(patClass == 2) {
            resultMap = pimsSampleResultManager.getYjxbDiagnosisResult(sample.getSampleid());
//            context.put("diagnosisResult", resultMap.get("diagnosisResult"));
//            context.put("advice", resultMap.get("advice"));
//            context.put("dnaResult", resultMap.get("dnaResult"));
//            context.put("checkedItemsStr", resultMap.get("checkedItemsStr"));
//            context.put("degree", resultMap.get("degree"));
            zdjg = resultMap.get("diagnosisResult");
        } else if(patClass == 7) {
            resultMap = pimsSampleResultManager.getHPVTestResult(sample.getSampleid());
//            context.put("sampleAmount", resultMap.get("sampleAmount"));
//            context.put("hpvResult", resultMap.get("hpvResult"));
//            context.put("diagnosisResult", resultMap.get("diagnosisResult"));
            zdjg = resultMap.get("diagnosisResult");
        }
        else {
            PimsSampleResult result = pimsSampleResultManager.getSampleResultForPrint(sample.getSampleid());
//            context.put("diagnosisResult", result == null ? "" : result.getRestestresult());
            zdjg = result == null ? "" : result.getRestestresult();
        }
        StringBuffer sb = new StringBuffer();
        sb.append(" insert into Report_Result_NewSystem(" +
                "check_id," +//病理编号
                "sample_id," +//条码号
                "patient_id," +//住院号，门诊号
                "patient_name," +//患者姓名
                "patient_sex," +//性别
                "patient_age," +//年龄
                "sample_class," +//标本种类病种名称
                "sample_name," +//送检物
                "report_doctor," +//报告医生
                "report_dtime," +//报告时间
                "autided_doctor," +//审核医生
                "autided_dtime," +//审核时间
                "report_status," +//报告状态（已审核，已打印）
                "diagnosis," +//诊断结果
                "pdf_name," +//pdf名称带后缀
                "pdf_path," +//路径
                "PatinetFileId," +//病案号
                "PatientType," +//病理类别 1 住院 2门诊
                "InPatientNo," +//就诊ID
                "bzlbid," +//病种ID
                "sjbb," +//送检标本
                "lczd," +//临床诊断
                "sjdoctor," +//送检医生
                "sjks," +//送检科室
                "cjtime," +//采集时间
                "blsj," +//病理所见
                "cwh," +//床位号
                "lrsj," +//录入时间
                "jxsj" +//
                ") values " +
                "('"+sample.getSampathologycode()+"'," +//病理编号
                "'"+sample.getSaminspectionid()+"'," +//条码号
                "'"+sample.getSampatientnumber()+"'," +//住院号，门诊号
                "'"+sample.getSampatientname()+"'," +//患者姓名
                ""+sample.getSampatientsex()+"," +//性别
                ""+sample.getSampatientage()+"," +//年龄
                "'"+psp.getPatnamech()+"'," +//标本种类病种名称
                "'"+sample.getSamsamplename()+"'," +//送检物
                "'"+sample.getSamreportor()+"'," +//报告医生
                "'"+Constants.SDF.format(sample.getSamreportedtime())+"'," +//报告时间
                "'"+sample.getSamauditer()+"'," +//审核医生
                "'"+Constants.SDF.format(sample.getSamauditedtime())+"'," +//审核时间
                "'"+samstatues+"'," +//报告状态（已审核，已打印）
                "'"+zdjg+"'," +//诊断结果
                "'"+rpdf.getPdffilename()+"'," +//pdf名称带后缀
                "'"+ Config.getString("pdf.upload.path","")+"'," +//路径
                "'"+sample.getSampatientid()+"'," +//病案号
                "'"+sample.getSampatienttype()+"'," +//病理类别 1 住院 2门诊
                "'"+sample.getSaminpatientid()+"'," +//就诊ID
                ""+sample.getSampathologyid()+"," +//病种ID
                "'"+sample.getSamsamplename()+"'," +//送检标本
                "'"+sample.getSampatientdignoses()+"'," +//临床诊断
                "'"+sample.getSamsenddoctorname()+"'," +//送检医生
                "'"+sample.getSamdeptname()+"'," +//送检科室
                "'"+Constants.SDF.format(sample.getSamsendtime())+"'," +//采集时间
                "'"+""+"'," +//病理所见
                "'"+sample.getSampatientbed()+"'," +//床位号
                "'"+Constants.SDF.format(sample.getSamreceivertime())+"'," +//录入时间
                "'"+Constants.SDF.format(new Date())+"')");

        jdbcTemplate.execute(sb.toString());
    }
}
