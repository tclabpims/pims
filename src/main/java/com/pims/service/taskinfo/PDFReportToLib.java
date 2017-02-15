package com.pims.service.taskinfo;

import com.pims.model.*;
import com.pims.service.pimspathologysample.PimsPathologyReportPdfManager;
import com.pims.service.pimspathologysample.PimsPathologySampleManager;
import com.pims.service.pimspathologysample.SamplePdfTaskManager;
import com.pims.service.pimssyspathology.PimsSampleResultManager;
import com.pims.service.pimssyspathology.PimsSysPathologyManager;
import com.pims.util.FastDFSClient;
import com.pims.util.PDFWebService;
import com.smart.model.lis.Hospital;
import com.smart.service.lis.HospitalManager;
import com.smart.webapp.util.TestTubeUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

/**
 * Created by yuzh on 2017/1/14.
 * 每小时向报告库发送已审核报告单
 */
@Service("sendDataToLib")
public class PDFReportToLib {

    private static final Log log = LogFactory.getLog(PDFReportToLib.class);
    @Autowired
    private SamplePdfTaskManager samplePdfTaskManager;
    @Autowired
    private PimsPathologyReportPdfManager pimsPathologyReportPdfManager;
    @Autowired
    private PimsSampleResultManager pimsSampleResultManager;
    @Autowired
    private PimsPathologySampleManager pimsPathologySampleManager;
    @Autowired
    private PimsSysPathologyManager pimsSysPathologyManager;
    @Autowired
    private HospitalManager hospitalManager;
    /**
     * 执行任务
     */
    public void run() {
        final List<SamplePdfTask> sampleList = samplePdfTaskManager.getTaskList();
        final PDFWebService webService = new PDFWebService();
        //由于已审核标本数过多，开启多线程，20*10
        for(int i = 0; i < 20; i++) {
            final int num = i;
            if(num*10 > sampleList.size()) {
                continue;
            }
            try {
                new Thread(new Runnable(){
                    public void run() {
                        int begin  = num*10;
                        int end = (num+1)*10;
                        if(end >= sampleList.size()) {
                            end = sampleList.size();
                        }
                        List<SamplePdfTask> samples = sampleList.subList(begin, end);
                        System.out.println("发送标本数：" + samples.size());
                        for(SamplePdfTask task : samples) {
                            try {
                                FastDFSClient client = new FastDFSClient(TestTubeUtil.class.getClassLoader().getResource("fdfs_client.conf").getFile());
                                PimsPathologyReportPdf pprp = pimsPathologyReportPdfManager.getPdfBySampleId(task.getTasksampleid());
                                if (pprp == null) {
                                    break;
                                }
                                String uploadFile = client.uploadFile(pprp.getPdffilesavepath()+ File.separator + pprp.getPdffilename(), "pdf");
//                                String uploadFile = "1";

                                PimsPathologySample sample = pimsPathologySampleManager.getBySampleNo(task.getTasksampleid());
                                PimsSysPathology psp = pimsSysPathologyManager.getSysPathologyById(sample.getSampathologyid());
                                Hospital hos = hospitalManager.get(sample.getSamcustomerid());
                                int patClass = Integer.parseInt(psp.getPatclass());
                                Map<String, String> resultMap = new HashMap<String, String>();
                                if(patClass == 2) {
                                    resultMap = pimsSampleResultManager.getYjxbDiagnosisResult(sample.getSampleid());
//                                    zdjg = resultMap.get("diagnosisResult");
                                } else if(patClass == 7) {
                                    resultMap = pimsSampleResultManager.getHPVTestResult(sample.getSampleid());
//                                    zdjg = resultMap.get("diagnosisResult");
                                }else {
                                    PimsSampleResult result = pimsSampleResultManager.getSampleResultForPrint(sample.getSampleid());
                                    resultMap.put("diagnosisResult",result == null ? "" : result.getRestestresult());
                                }
                                boolean sendSuccess = webService.reportLib(sample, resultMap, uploadFile,psp,hos);
                                if(sendSuccess == true) {
                                    task.setTaskstates(2);
                                    task.setTaskurl(uploadFile);
                                    task.setTaskfirstd(new Date());
                                    samplePdfTaskManager.save(task);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
            } catch (Exception e) {

            }
        }
    }

}
