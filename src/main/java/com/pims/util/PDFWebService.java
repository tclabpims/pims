package com.pims.util;
import com.pims.model.*;
import com.pims.service.UpdateReportDataService;
import com.pims.service.pimssyspathology.PimsSampleResultManager;
import com.pims.service.pimssyspathology.PimsSysPathologyManager;
import com.smart.Constants;
import com.smart.lisservice.WebService;
import com.smart.model.lis.Hospital;
import com.smart.service.lis.HospitalManager;
import com.smart.util.Config;
import com.smart.util.ConvertUtil;
import com.smart.util.SpringContextUtil;
import com.smart.webapp.util.UserUtil;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.namespace.QName;
import java.io.*;
import java.net.HttpURLConnection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by king on 2017/1/7.
 */
public class PDFWebService {

    private static final Log log = LogFactory.getLog(WebService.class);

    private String url = Config.getString("pdf.web.path","");
    private HttpURLConnection connection = null;
    private String namespace = "http://tempuri.org/fileservice";

    private String weburl = Config.getString("webservice.path","");


    public String uploadPdf(String key,String path,PimsPathologyReportPdf rpdf) {
        String result = "";
        try {
            JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
            Client client = dcf.createClient(url);
            String type = "upload";
            byte[] fs = toByteArray(rpdf.getPdffilesavepath() + File.separator + rpdf.getPdffilename());
            String errorinfo = "";
            String strfile = new String(org.apache.commons.codec.binary.Base64.encodeBase64(fs), "UTF-8");
            Object[] objects = client.invoke("FileService", key, type, path, rpdf.getPdffilename(), fs, errorinfo);
            if ((Boolean) objects[0] == true) {
                System.out.println(objects[1].toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }

    public String deletePdf(String key,String path,PimsPathologyReportPdf rpdf) {
        String result = "";
        try {
            JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
            Client client = dcf.createClient(url);
            String type = "delete";
            byte[] fs = toByteArray(rpdf.getPdffilesavepath() + File.separator + rpdf.getPdffilename());
            String errorinfo = "";
            String strfile = new String(org.apache.commons.codec.binary.Base64.encodeBase64(fs), "UTF-8");
            Object[] objects = client.invoke("FileService", key, type, path, rpdf.getPdffilename(), fs, errorinfo);
            if ((Boolean) objects[0] == true) {
                System.out.println(objects[1].toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }

    public static byte[] toByteArray(String filename) throws IOException {
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(filename));
        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
        byte[] temp = new byte[1024];
        int size = 0;
        while ((size = in.read(temp)) != -1) {
            out.write(temp, 0, size);
        }
        in.close();
        byte[] content = out.toByteArray();
        return content;
        // System.out.println("Readed bytes count:" + content.length);
    }
    @Autowired
    private PimsSampleResultManager pimsSampleResultManager;
    /**
     * 将检测结果保存至HIS系统
     *
     * @param sample     样本信息
     * @return
     */
    public boolean saveHisResult(PimsPathologySample sample,
                                 PimsSysPathology psp) {
        int patClass = Integer.parseInt(psp.getPatclass());
        Map<String, String> resultMap = null;
        String  zdjg = "";
        if(patClass == 2) {
            resultMap = pimsSampleResultManager.getYjxbDiagnosisResult(sample.getSampleid());
            zdjg = resultMap.get("diagnosisResult");
        } else if(patClass == 7) {
            resultMap = pimsSampleResultManager.getHPVTestResult(sample.getSampleid());
            zdjg = resultMap.get("diagnosisResult");
        }
        else {
            PimsSampleResult result = pimsSampleResultManager.getSampleResultForPrint(sample.getSampleid());
            zdjg = result == null ? "" : result.getRestestresult();
        }
        boolean flag = false;
        try {
            HttpClient httpClient = new HttpClient();
            httpClient.getHostConfiguration().setHost(weburl + "saveHisResult");
            PostMethod method = new PostMethod(weburl + "saveHisResult");
            JSONObject hisSampleInfo = new JSONObject();
            /**
             * 住院报告单模式修改 1 门诊 2 住院
             */
            long mode = sample.getSampatienttype();
            if(sample.getSampatienttype()==1)mode =2;
            if(sample.getSampatienttype()==2) mode=1;
            //样本信息
            hisSampleInfo.put("barCode", sample.getSampathologycode());//病理编号
            hisSampleInfo.put("sampleNo", sample.getSaminspectionid());//条码号
            hisSampleInfo.put("organizationId", 1001);       //机构代码
            hisSampleInfo.put("patientType", mode);//受检病人类型
            hisSampleInfo.put("patientId", sample.getSaminpatientid());//就诊ID
            hisSampleInfo.put("patientNo", sample.getSampatientnumber());//住院卡号
            hisSampleInfo.put("patientName", sample.getSampatientname());//患者姓名
            hisSampleInfo.put("sex", sample.getSampatientsex());//性别(1男,2女,3未知)
            hisSampleInfo.put("age", sample.getSampatientage());//年龄
            long agetype = sample.getSampatientagetype();
            String ageString = "";
            if(agetype == 1){
                ageString = "岁";
            }else if(agetype == 2){
                ageString = "月";
            }else if(agetype == 4){
                ageString = "周";
            }else if(agetype == 5){
                ageString = "日";
            }else if(agetype == 6){
                ageString = "小时";
            }
            hisSampleInfo.put("ageUnit", ageString);//年龄类型(1岁、2月、4周、5日、6小时)
            hisSampleInfo.put("isBaby", 0);//是否婴儿
            hisSampleInfo.put("bedNo", sample.getSampatientbed());//床号
            hisSampleInfo.put("diagnosisId", "");//临床诊断代码
            hisSampleInfo.put("diagnosis", sample.getSampatientdignoses());//临床诊断
            hisSampleInfo.put("part", "");//受检采集部位
            hisSampleInfo.put("cycleId", "");//生理周期序号
            hisSampleInfo.put("executeTime", ConvertUtil.getFormatDateGMT(sample.getSamsendtime(), "yyyy-MM-dd'T'HH:mm:ss'Z'"));//样本送检时间
            hisSampleInfo.put("requesterId", sample.getSamsenddoctorid());//开单医生序号
            hisSampleInfo.put("requesterName", sample.getSamsenddoctorname());//开单医生姓名
            hisSampleInfo.put("departmentId", sample.getSamdeptcode());//开单科室序号
            hisSampleInfo.put("departmentName", sample.getSamdeptname());//开单科室名称
            hisSampleInfo.put("receiveTime", ConvertUtil.getFormatDateGMT(sample.getSamreqtime(), "yyyy-MM-dd'T'HH:mm:ss'Z'"));//样本接收时间
            hisSampleInfo.put("testerId", "");//执行人员序号
            hisSampleInfo.put("testerName", sample.getSaminitiallyusername());//执行人员姓名
            hisSampleInfo.put("testDepartmentId", "");//执行科室序号
            hisSampleInfo.put("testDepartmentName", "病理科");//执行科室名称
            hisSampleInfo.put("testTime", ConvertUtil.getFormatDateGMT(sample.getSamreqtime(), "yyyy-MM-dd'T'HH:mm:ss'Z'"));//样本执行时间
            hisSampleInfo.put("auditerId", "");//审核人员序号
            hisSampleInfo.put("auditerName", sample.getSamauditer());//审核人员姓名
            hisSampleInfo.put("auditTime", ConvertUtil.getFormatDateGMT(sample.getSamauditedtime(), "yyyy-MM-dd'T'HH:mm:ss'Z'"));//样本审核时间
            hisSampleInfo.put("auditNote", "");//样本审核备注
            hisSampleInfo.put("sampleTypeId", "");//样本类型代码
            hisSampleInfo.put("sampleTypeName", sample.getSamsamplename());//样本类型名称
            hisSampleInfo.put("sampleOperateStatus", 0);//样本操作状态
            hisSampleInfo.put("sampleResultTime", ConvertUtil.getFormatDateGMT(sample.getSamreportedtime(), "yyyy-MM-dd'T'HH:mm:ss'Z'"));//样本结果时间
            hisSampleInfo.put("sampleResultStatus", "");//样本结果状态
            hisSampleInfo.put("isPrint", "");//是否打印判别
            hisSampleInfo.put("isEmergency", "");//是否急诊判别
            hisSampleInfo.put("testId", sample.getSampathologyid());//检查目的代码
            hisSampleInfo.put("testName", psp.getPatnamech());//检查目的名称
            hisSampleInfo.put("requesterTime", ConvertUtil.getFormatDateGMT(sample.getSamreqtime(), "yyyy-MM-dd'T'HH:mm:ss'Z'"));//


            String reportUrl = Config.getString("report.path", "") + "&patienttype=" + mode + "&patientid=" + sample.getSaminpatientid() + "&sampleid=" + sample.getSaminspectionid();
            hisSampleInfo.put("reportUrl", reportUrl);//报告预览地址
            hisSampleInfo.put("patientCode", sample.getSampatientid());//病人档案号
            hisSampleInfo.put("sfblpb",1);//是否病理判别
            hisSampleInfo.put("ybzdnr",zdjg);
            hisSampleInfo.put("brdabh",sample.getSampatientid());

            JSONObject hisTestInfo = new JSONObject();
            hisTestInfo.put("sampleInfo", hisSampleInfo);
            RequestEntity requestEntity = new StringRequestEntity(hisTestInfo.toString(), "application/json", "UTF-8");
            method.setRequestEntity(requestEntity);
            method.releaseConnection();

            httpClient.executeMethod(method);
            //System.out.println(method.getResponseBodyAsString());

            if (method.getResponseBodyAsString() != null && !method.getResponseBodyAsString().isEmpty()) {
                JSONObject obj = new JSONObject(method.getResponseBodyAsString());
                if ((Integer) obj.get("State") == 0) {
                    flag = false;
                } else {
                    flag = true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }

        return flag;
    }
    private String reportLibUrl = Config.getString("pdf.report.path","");

    /**
     * 将病理结果上传到报告库
     * @param sample
     * @param resultMap
     * @param uploadFile
     * @param psp
     * @param hos
     * @return
     */
    public boolean reportLib(PimsPathologySample sample, Map<String,String> resultMap, String uploadFile,PimsSysPathology psp,Hospital hos) {
        boolean flag = false;
        try {
            HttpClient httpClient = new HttpClient();
            httpClient.getHostConfiguration().setHost(reportLibUrl + "report/saveReportLibInfo");
            PostMethod method = new PostMethod(reportLibUrl + "report/saveReportLibInfo");
            JSONObject libSampleInfo = new JSONObject();

            //样本信息
            libSampleInfo.put("barcode", sample.getSaminspectionid());//实验室条码信息
            libSampleInfo.put("customid", sample.getSamcustomerid());//客户ID
            libSampleInfo.put("age", sample.getSampatientage());//病人年龄
            libSampleInfo.put("birthday", "");//出生日期
            libSampleInfo.put("tester", sample.getSaminitiallyusername());//检验者
            libSampleInfo.put("cycle", 0);//生理周期
            libSampleInfo.put("departBed", sample.getSampatientbed());//病床号
            libSampleInfo.put("description", "");//描述
            libSampleInfo.put("diagnostic", sample.getSampatientdignoses());//病人诊断
            libSampleInfo.put("fee", 0);//价格
            libSampleInfo.put("hossection", sample.getSamdeptname());//在院科室名称
            libSampleInfo.put("inspectionname", sample.getSamjcxm());//检验目的名称
            libSampleInfo.put("part", sample.getSamsamplename());//采集部位
            libSampleInfo.put("patientid", sample.getSampatientnumber());//病人就诊号
            libSampleInfo.put("patientblh", sample.getSampatientid());//病人唯一号
            libSampleInfo.put("patientname", sample.getSampatientname());//病人姓名
            libSampleInfo.put("sampleno", sample.getSampathologycode());//样本编号
            libSampleInfo.put("samplestatus", sample.getSamsamplestatus());//样本状态
            libSampleInfo.put("sampletype", psp.getPatnamech());//样本类型名称
            libSampleInfo.put("labdepartment", "病理科");//实验室部门名称
            libSampleInfo.put("sex", sample.getSampatientsex()==1?"男":"女");//病人性别
            String hosmode = "";
            if(sample.getSampatienttype()==1){
                hosmode = "住院";
            }else if(sample.getSampatienttype()==2){
                hosmode = "门诊";
            }else if(sample.getSampatienttype()==3){
                hosmode = "体检";
            }else if(sample.getSampatienttype()==4){
                hosmode = "婚检";
            }else if(sample.getSampatienttype()==5){
                hosmode = "科研";
            }else if(sample.getSampatienttype()==6){
                hosmode = "特勤";
            }else if(sample.getSampatienttype()==7){
                hosmode = "其他";
            }
            libSampleInfo.put("stayhospitalmode", hosmode);//在院方式
            libSampleInfo.put("inspectionid", sample.getSampopuser());//检验目的序号
            String agetype = "";
            if(sample.getSampatientagetype() == 1){
                agetype = "岁";
            }else if(sample.getSampatientagetype() == 2){
                agetype = "月";
            }else if(sample.getSampatientagetype() == 4){
                agetype = "周";
            }else if(sample.getSampatientagetype() == 5){
                agetype = "日";
            }else if(sample.getSampatientagetype() == 6){
                agetype = "小时";
            }
            libSampleInfo.put("ageunit", agetype);//年龄类型，年、月、周、天
            libSampleInfo.put("customname", hos.getName());//客户名称
            libSampleInfo.put("reporturl", uploadFile);//
            libSampleInfo.put("preBarcode", sample.getSaminspectionid());//预制条码
            libSampleInfo.put("customBarcode", "");//第三方客户条码
            libSampleInfo.put("patientIdNumber", "");//身份证号
            libSampleInfo.put("patientAddress", sample.getSampatientaddress());//病人地址
            libSampleInfo.put("patientPhone", sample.getSampatientphoneno());//病人联系方式
            libSampleInfo.put("reportType", 2);

            libSampleInfo.put("checkoperator", sample.getSamauditer());//审核医生
            libSampleInfo.put("checktime", ConvertUtil.getFormatDateGMT(sample.getSamreportedtime(), "yyyy-MM-dd'T'HH:mm:ss'Z'"));//审核时间
            libSampleInfo.put("executor", sample.getSamregistername());//采样者
            libSampleInfo.put("executetime", ConvertUtil.getFormatDateGMT(sample.getSamregisttime(), "yyyy-MM-dd'T'HH:mm:ss'Z'"));//采样时间
            libSampleInfo.put("ksreceiver", sample.getSamregistername());//实验室接收者
            libSampleInfo.put("ksreceivetime", ConvertUtil.getFormatDateGMT(sample.getSamregisttime(), "yyyy-MM-dd'T'HH:mm:ss'Z'"));//实验室接收时间
            libSampleInfo.put("receiver", sample.getSamregistername());//接受者
            libSampleInfo.put("receivetime", ConvertUtil.getFormatDateGMT(sample.getSamregisttime(), "yyyy-MM-dd'T'HH:mm:ss'Z'"));//接收时间
            libSampleInfo.put("requester", sample.getSaminitiallyusername());//开单医生
            libSampleInfo.put("requesttime", ConvertUtil.getFormatDateGMT(sample.getSaminitiallytime(), "yyyy-MM-dd'T'HH:mm:ss'Z'"));//开单时间
            libSampleInfo.put("sender", sample.getSaminitiallyusername());//标本送出者
            libSampleInfo.put("sendtime", ConvertUtil.getFormatDateGMT(sample.getSaminitiallytime(), "yyyy-MM-dd'T'HH:mm:ss'Z'"));//标本送出时间

            //结果信息
            JSONArray libTestResult = new JSONArray();
            JSONObject object = new JSONObject();
            object.put("barcode", sample.getSaminspectionid());//条码号
            object.put("customid", String.valueOf(sample.getSamcustomerid()));//客户代码
            object.put("customname", hos.getName());//客户名称
            object.put("sampleno", sample.getSampathologycode());//样本编号(病理编号)
            object.put("viewresult", "");//大体所见
            object.put("diagnosis", resultMap.get("diagnosisResult").replaceAll("<br>"," "));//诊断结果
            object.put("createtime", ConvertUtil.getFormatDateGMT(new Date(), "yyyy-MM-dd'T'HH:mm:ss'Z'"));//上传时间
            object.put("rev1", "");//
            object.put("rev2", "");//
            libTestResult.put(object);
            JSONObject reportLibInfo = new JSONObject();
            reportLibInfo.put("sampleInfo", libSampleInfo);
            reportLibInfo.put("pathologyResultInfoList", libTestResult);
            RequestEntity requestEntity = new StringRequestEntity(reportLibInfo.toString(), "application/json", "UTF-8");
            method.setRequestEntity(requestEntity);
            method.releaseConnection();

            httpClient.executeMethod(method);
            if (method.getResponseBodyAsString() != null && !method.getResponseBodyAsString().isEmpty()) {
                JSONObject obj = new JSONObject(method.getResponseBodyAsString());
                if ((Integer) obj.get("State") == 0) {
                    flag = false;
                } else {
                    flag = true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }

        return flag;

    }

}
