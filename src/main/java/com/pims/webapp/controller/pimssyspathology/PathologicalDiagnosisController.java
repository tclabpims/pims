package com.pims.webapp.controller.pimssyspathology;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.pims.model.*;
import com.pims.service.pimspathologysample.PimsPathologyParaffinManager;
import com.pims.service.pimspathologysample.PimsPathologySampleManager;
import com.pims.service.pimspathologysample.PimsPathologySlideManager;
import com.pims.service.pimssyspathology.*;
import com.pims.webapp.controller.GridQuery;
import com.pims.webapp.controller.PIMSBaseController;
import com.pims.webapp.controller.WebControllerUtil;
import com.pims.webapp.util.HtmlGenerator;
import com.smart.Constants;
import com.smart.model.lis.Hospital;
import com.smart.model.user.User;
import com.smart.service.lis.HospitalManager;
import com.smart.webapp.util.DataResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by 909436637@qq.com on 2016/10/18.
 * Description:
 */
@Controller
@RequestMapping("/diagnosis/*")
public class PathologicalDiagnosisController extends PIMSBaseController {

    @Autowired
    private PimsPathologySampleManager pimsPathologySampleManager;

    @Autowired
    private PimsPathologyTemplateManager pptm;

    @Autowired
    private PimsSysReqFieldManager psrm;

    @Autowired
    private PimsSysReportItemsManager pimsSysReportItemsManager;

    @Autowired
    private PimsSysCustomerBasedataManager pimsSysCustomerBasedataManager;

    @Autowired
    private PimsSampleResultManager pimsSampleResultManager;

    @Autowired
    private PimsPathologyPicturesManager pimsPathologyPicturesManager;

    @Autowired
    private PimsSysPathologyManager pimsSysPathologyManager;

    @Autowired
    private PimsSysReportFormatManager pimsSysReportFormatManager;

    @Autowired
    private HospitalManager hospitalManager;

    @Autowired
    private PimsPathologySlideManager pimsPathologySlideManager;

    @Autowired
    private PimsPathologyParaffinManager pimsPathologyParaffinManager;

    @RequestMapping(value = "/report/paraffin", method = RequestMethod.GET)
    @ResponseBody
    public DataResponse getParaffin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DataResponse dr = new DataResponse();
        String sampleId = request.getParameter("sampleId");
        String orderIdStr = request.getParameter("orderId");
        Long orderId = orderIdStr== null?null:Long.valueOf(orderIdStr);
        List<PimsPathologyParaffin> lis = pimsPathologyParaffinManager.getParaffinBySampleId(Long.valueOf(sampleId), orderId);
        dr.setRows(getResultMap(lis));
        return dr;
    }

    @RequestMapping(value = "/camera", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView camera(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.addObject("sampleId", request.getParameter("sampleId"));
        mv.addObject("customerId", request.getParameter("customerId"));
        return mv;
    }

    @RequestMapping(value = "/report/whitepiece", method = RequestMethod.GET)
    @ResponseBody
    public DataResponse getWhitePiece(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DataResponse dr = new DataResponse();
        String sampleId = request.getParameter("sampleId");
        String paraffinNo = request.getParameter("paraffinNo");
        List<PimsPathologySlide> lis = pimsPathologySlideManager.getWhitePiece(paraffinNo, Long.valueOf(sampleId));
        dr.setRows(getResultMap(lis));
        return dr;
    }

    protected PimsPathologyPictures savePathologyPictures(PimsPathologyPictures pic) {
        return pimsPathologyPicturesManager.save(pic);
    }

    protected void delPathologyPictures(String picName, Long sampleId) {
        pimsPathologyPicturesManager.removeByName(picName, sampleId);
    }

    @RequestMapping(value = "/report/print", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> printReport(HttpServletRequest request, HttpServletResponse response) throws Exception {

        Long sampleId = Long.valueOf(request.getParameter("sampleid"));
        String picNum = request.getParameter("picNum");
        Long pictureClass = Long.valueOf(request.getParameter("picpictureclass"));
        int picNumInt = (picNum == null || "".equals(picNum)) ? 0 : Integer.valueOf(picNum);
        String templateUrl = request.getParameter("templateUrl");
        int operateType = Integer.valueOf(request.getParameter("type"));

        PimsPathologySample pimsPathologySample = pimsPathologySampleManager.get(sampleId);
        PimsSysPathology pathology = pimsSysPathologyManager.get(pimsPathologySample.getSampathologyid());
        PimsSampleResult result = pimsSampleResultManager.getSampleResultForPrint(sampleId);
        List<PimsPathologyPictures> pictures = pimsPathologyPicturesManager.getSamplePicture(sampleId, pictureClass);

        VelocityContext context = getVelocityContext(pimsPathologySample, pathology);
        if (picNumInt > pictures.size()) picNumInt = pictures.size();
        context.put("diagnosisResult", result == null ? "" : result.getRestestresult());
        context.put("picNum", picNumInt);
        context.put("hospitalLogo", getHospitalLogo(request, pimsPathologySample.getSamcustomerid()));

        if (picNumInt > 0) {
            Map<String, String> map1 = new HashMap<>();
                for (int i = 0; i < picNumInt; i++) {
                    PimsPathologyPictures pic = pictures.get(i);
                    String realPath = pic.getPicsavepath();
                    if (realPath != null && realPath.length() > 0) {
                        realPath = realPath.substring(realPath.indexOf("\\images"));
                        realPath = realPath.replaceAll("\\\\", "/");
                        if(picNumInt == 1) {
                            context.put("imgsrc", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + realPath);
                        }
                        else
                            map1.put("imgsrc" + (i + 1), request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + realPath);
                    }
                }
                context.put("multiSrc", map1);
            }

            VelocityEngine engine = new VelocityEngine();
            engine.setProperty(Velocity.RESOURCE_LOADER, "class");
            engine.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
            engine.init();

            Template template = engine.getTemplate(templateUrl, "UTF-8");
            StringWriter writer = new StringWriter();
            template.merge(context, writer);

            String rootDir = request.getSession().getServletContext().getRealPath("/pdf");
            String fileName = sampleId + ".html";
            String outputFile = rootDir + File.separator + fileName;
            generateHtml(outputFile, writer.toString());

            response.setContentType(super.contentType);

            Map<String, String> map = new HashMap<>();
            map.put("url", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/pdf/" + fileName);

            return map;
        }

    private String getHospitalLogo(HttpServletRequest request, Long hospitalId) {
        Hospital hospital = hospitalManager.get(hospitalId);
        StringBuilder logoFileRoot = new StringBuilder(request.getScheme());
        logoFileRoot.append("://").append(request.getServerName())
                .append(":").append(request.getServerPort()).append("/images/hospital/");
        logoFileRoot.append(hospitalId).append("/").append(hospital.getLogo());
        return logoFileRoot.toString();
    }

    private void generateHtml(String fileName, String html) {
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }
        RandomAccessFile mm = null;
        try {
            mm = new RandomAccessFile(fileName, "rw");
            mm.write(html.getBytes("UTF-8"));
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            if (mm != null) {
                try {
                    mm.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    @RequestMapping(value = "/report/getTemplate", method = RequestMethod.GET)
    @ResponseBody
    public DataResponse getReportTemplate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DataResponse dr = new DataResponse();
        String sampleId = request.getParameter("sampleid");
        PimsPathologySample pimsPathologySample = pimsPathologySampleManager.get(Long.valueOf(sampleId));
        PimsSysPathology pathology = pimsSysPathologyManager.get(pimsPathologySample.getSampathologyid());
        List<PimsSysReportFormate> lis = pimsSysReportFormatManager.getReportFormatByPathologyId(pathology.getPathologyid());
        dr.setRows(getResultMap(lis));
        return dr;
    }

    private VelocityContext getVelocityContext(PimsPathologySample sample, PimsSysPathology pathology) {
        VelocityContext context = new VelocityContext();
        context.put("formname", pathology.getPatreporttitle());
        context.put("patreportremark", pathology.getPatreportremark());
        context.put("saminspectionid", sample.getSaminspectionid());
        context.put("samsendhospital", sample.getSamsendhospital());
        context.put("sampathologycode", sample.getSampathologycode());
        context.put("sampatientname", sample.getSampatientname());
        context.put("sampatientsex", sample.getSampatientsex());
        context.put("sampatientage", sample.getSampatientage());
        context.put("sampatientphoneno", sample.getSampatientphoneno());
        context.put("sampatientnumber", sample.getSampatientnumber());
        context.put("sampatientbed", sample.getSampatientbed());
        context.put("samdeptname", sample.getSamdeptname());
        context.put("samsenddoctorname", sample.getSamsenddoctorname());
        context.put("samsamplename", sample.getSamsamplename());
        if(sample.getSamsendtime() != null)
        context.put("samsendtime", Constants.DF2.format(sample.getSamsendtime()));
        else
        context.put("samsendtime", "");
        context.put("sampatientaddress", sample.getSampatientaddress());
        if(sample.getSamreceivertime() != null)
        context.put("samreceivertime", Constants.DF2.format(sample.getSamreceivertime()));
        else
        context.put("samreceivertime", "");
        context.put("sampatientdignoses", sample.getSampatientdignoses());
        context.put("samreportor", sample.getSamreportor());
        context.put("samauditer", sample.getSamauditer());
        if(sample.getSamreportedtime() != null)
        context.put("samreportedtime", Constants.DF2.format(sample.getSamreportedtime()));
        else
        context.put("samreportedtime", "");

        return context;
    }

    @RequestMapping(method = {RequestMethod.GET}, value = "/removePicture")
    public void removePicture(HttpServletRequest request, HttpServletResponse response) {
        delPathologyPictures(request.getParameter("name"), Long.valueOf(request.getParameter("sampleid")));
    }

    @RequestMapping(method = {RequestMethod.POST}, value = "/upload")
    @ResponseBody
    public Map<String, Object> upload(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String sampleId = request.getParameter("sampleid");
        String samCustomerId = request.getParameter("samcustomerid");
        String continuous = request.getParameter("continuous");
        Long picPictureClass = Long.valueOf(request.getParameter("picpictureclass"));
        ServletInputStream inputStream = request.getInputStream();
        BufferedImage bufferedImage = ImageIO.read(inputStream);
        String customerFileDir = "/images/jpg" + "/" + samCustomerId + "/" + sampleId;
        // the directory to upload to
        String uploadDir = request.getSession().getServletContext().getRealPath(customerFileDir);

        //图片序号
        int picIndex = 0;

        // The following seems to happen when running jetty:run
        if (uploadDir == null) {
            picIndex = 1;
            uploadDir = new File("src/main/webapp" + customerFileDir).getAbsolutePath();
        } else {
            File exsitFile = new File(uploadDir);
            File[] files = exsitFile.listFiles();
            //如果不是连拍
            if ("false".equals(continuous)) {
                if (files != null && files.length > 0) {
                    for (File f : files) {
                        if (f.isFile()) {
                            delPathologyPictures(f.getName(), Long.valueOf(sampleId));
                            f.delete();
                        }
                    }
                }
            } else {
                if (files != null && files.length > 0) {
                    for (File f : files) {
                        if (f.isFile()) {
                            picIndex = picIndex + 1;
                        }
                    }
                }
            }
        }
        String fileName = sampleId + "_" + new Date().getTime() + "_" + picIndex + "." + Constants.PIC_TYPE_JPG;
        uploadDir += "/" + fileName;

        // Create the directory if it doesn't exist
        File dirPath = new File(uploadDir);

        boolean success;
        success = dirPath.mkdirs();
        if (success) {
            ImageIO.write(bufferedImage, "jpeg", dirPath);
        }

        PimsPathologyPictures pp = saveImageFile(Long.valueOf(sampleId), dirPath, picIndex, request, picPictureClass);

        Map<String, Object> map = new HashMap<>();
        map.put("name", pp.getPicpicturename());
        map.put("pictureid", pp.getPictureid());
        map.put("src", customerFileDir + "/" + fileName);
        map.put("continuous", continuous);
        response.setContentType(contentType);
        return map;
    }


    private PimsPathologyPictures saveImageFile(Long sampleId, File dirPath, int picIndex, HttpServletRequest request, Long pictureClass) throws IOException {
        User user = WebControllerUtil.getAuthUser();
        PimsPathologySample sample = pimsPathologySampleManager.get(sampleId);
        PimsPathologyPictures pic = new PimsPathologyPictures();

        pic.setPiccreatetime(new Date());
        pic.setPicpicturetime(new Date());
        pic.setPicpictureuser(user.getName());
        pic.setPiccreateuser(String.valueOf(user.getId()));
        pic.setPiccustomerid(sample.getSamcustomerid());
        pic.setPicpathologycode(sample.getSampathologycode());
        pic.setPicpictureclass(pictureClass);
        pic.setPicpicturename(dirPath.getName());
        pic.setPicpicturetype(1);
        pic.setPicpictureno(picIndex);
        pic.setPicsampleid(sample.getSampleid());
        pic.setPicpicturesize(String.valueOf(new FileInputStream(dirPath).available() / 1000) + "k");
        pic.setPicpictureip(getRemoteHost(request));
        pic.setPicisupload(1);
        pic.setPicuploaduser(user.getUsername());
        pic.setPicuploadtime(new Date());
        pic.setPicsavepath(dirPath.getAbsolutePath());

        savePathologyPictures(pic);
        return pic;
    }

    @RequestMapping(method = {RequestMethod.POST}, value = "/uploadimg")
    @ResponseBody
    public DataResponse multiFileUpload(HttpServletRequest request, @RequestParam MultipartFile[] imgFile, HttpServletResponse response) throws Exception {
        List<Map<String, Object>> lis = new ArrayList<>();
        String sampleId = request.getParameter("sampleid");
        String samCustomerId = request.getParameter("samcustomerid");
        Long picPictureClass = Long.valueOf(request.getParameter("picpictureclass"));
        String customerFileDir = "/images/jpg" + "/" + samCustomerId + "/" + sampleId;
        // the directory to upload to
        String uploadDir = request.getSession().getServletContext().getRealPath(customerFileDir);
        if (uploadDir == null) {
            uploadDir = new File("src/main/webapp" + customerFileDir).getAbsolutePath();
        }
        List<File> uploadFiles = super.multifileUpload(imgFile, uploadDir, sampleId);
        File filePath = new File(uploadDir);
        if (uploadFiles.size() > 0) {
            for (File file : uploadFiles) {
                PimsPathologyPictures pp = saveImageFile(Long.valueOf(sampleId), file, (filePath.list().length + 1), request, picPictureClass);
                Map<String, Object> map = new HashMap<>();
                map.put("name", pp.getPicpicturename());
                map.put("src", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()+"/"+customerFileDir + "/" + file.getName());
                lis.add(map);
            }
        }
        response.setContentType(contentType);
        DataResponse dr = new DataResponse();
        dr.setRows(lis);
        return dr;
    }

    @RequestMapping(method = {RequestMethod.POST}, value = "/saveSign")
    @ResponseBody
    public void saveSign(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PimsPathologySample sample = (PimsPathologySample) setBeanProperty(request, PimsPathologySample.class);
        if (sample.getSaminitiallyuserid() != null && !"".equals(sample.getSaminitiallyuserid())) {
            sample.setSamsamplestatus(Constants.SAMPLE_STATUS_INITIAL_DIAGNOSIS);
        }
        if (sample.getSamauditerid() != null && !"".equals(sample.getSamauditerid())) {
            sample.setSamsamplestatus(Constants.SAMPLE_STATUS_AUDIT);
        }
        pimsPathologySampleManager.sign(sample);
    }


    @RequestMapping(method = {RequestMethod.POST}, value = "/saveResult")
    @ResponseBody
    public Map<String, Long> saveResult(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String result = request.getParameter("result");
        User user = WebControllerUtil.getAuthUser();
        Long hospitalId = user.getHospitalId();
        DefaultJSONParser parser = new DefaultJSONParser(result);
        JSONArray jsonArray = (JSONArray) parser.parse();
        List<PimsSampleResult> set = new ArrayList<>();
        for (Object obj : jsonArray) {
            PimsSampleResult sampleResult = new PimsSampleResult();
            Long sampleResultId = null;
            if (!((JSONObject) obj).get("resultid").equals(""))
                sampleResultId = ((JSONObject) obj).getLongValue("resultid");
            if (sampleResultId != null) {
                sampleResult.setResultid(sampleResultId);
                PimsSampleResult his = pimsSampleResultManager.get(sampleResultId);
                sampleResult.setResinputtime(his.getResinputtime());
                sampleResult.setResinputuser(his.getResinputuser());
                sampleResult.setRescreatetime(his.getRescreatetime());
                sampleResult.setRescreateuser(his.getRescreateuser());
                sampleResult.setResmodifytime(new Date());
                sampleResult.setResmodifyuser(String.valueOf(user.getId()));
            } else {
                sampleResult.setResinputtime(new Date());
                sampleResult.setResinputuser(user.getUsername());
                sampleResult.setRescreatetime(new Date());
                sampleResult.setRescreateuser(String.valueOf(user.getId()));
            }
            sampleResult.setRescustomerid(hospitalId);
            sampleResult.setResviewtype((String) ((JSONObject) obj).get("resviewtype"));
            sampleResult.setRestestresult((String) ((JSONObject) obj).get("restestresult"));
            sampleResult.setRestestitemid(((JSONObject) obj).getLongValue("restestitemid"));
            sampleResult.setResviewtitle((String) ((JSONObject) obj).get("resviewtitle"));
            sampleResult.setResviewsort((String) ((JSONObject) obj).get("resviewsort"));
            sampleResult.setResinputsort((String) ((JSONObject) obj).get("resinputsort"));
            sampleResult.setRessampleid(((JSONObject) obj).getLongValue("ressampleid"));

            set.add(sampleResult);
        }
        Map<String, Long> map = new HashMap<>();
        if (set.size() > 0) map = pimsSampleResultManager.save(set);
        response.setContentType(contentType);
        return map;
    }

    @RequestMapping(method = {RequestMethod.GET}, value = "/sampleresult")
    @ResponseBody
    public Map<String, PimsSampleResult> getSampleResult(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long sampleId = Long.valueOf(request.getParameter("sampleid"));
        response.setContentType(contentType);
        return pimsSampleResultManager.getSampleResult(sampleId);
    }

    @RequestMapping(method = {RequestMethod.GET}, value = "/pictures")
    @ResponseBody
    public Map<String, String> samplePictures(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, String> map = new HashMap<>();
        Long sampleId = Long.valueOf(request.getParameter("sampleid"));
        Long pictureClass = Long.valueOf(request.getParameter("picpictureclass"));
        List<PimsPathologyPictures> list = pimsPathologyPicturesManager.getSamplePicture(sampleId, pictureClass);
        if (list.size() > 0) {
            String url = null;
            for (PimsPathologyPictures pic : list) {
                url = pic.getPicsavepath();
                if (url != null && url.length() > 0) {
                    url = url.substring(url.indexOf("\\images"));
                    url = url.replaceAll("\\\\", "/");
                    map.put(pic.getPicpicturename(), url);
                }
            }
        }
        response.setContentType(contentType);
        return map;
    }

    @RequestMapping(method = {RequestMethod.GET}, value = "/signdoctor")
    @ResponseBody
    public Map<String, Object> getSignDoctor(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = WebControllerUtil.getAuthUser();
        Map<String, Object> map = new HashMap<>();
        map.put("name", user.getName());
        map.put("id", user.getId());
        map.put("time", Constants.SDF.format(new Date()));
        response.setContentType(contentType);
        return map;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView diagnosis(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String options = getPathologySelectOption(request);
        ModelAndView mv = getmodelView(request);
        mv.addObject("options", options);
        User user = WebControllerUtil.getAuthUser();
        Long hospitalId = user.getHospitalId();
        Long pathologyId = user.getUserBussinessRelate().getPathologyLibId();
        List<PimsSysReportItems> reportItemsList = pimsSysReportItemsManager.getRefFieldList(hospitalId, pathologyId);
        List<PimsSysReqField> reqFields = psrm.getReqFieldList(hospitalId, pathologyId);
        List<PimsSysCustomerBasedata> customerData = pimsSysCustomerBasedataManager.getCustomerDataList(hospitalId, pathologyId);
        mv.addObject("diagnosisItems", HtmlGenerator.generate(reqFields, reportItemsList, customerData));
        //首页跳转
        String id = request.getParameter("id");
        if(!StringUtils.isEmpty(id)){
            PimsPathologySample pathology = pimsPathologySampleManager.get(Long.parseLong(id));
            mv.addObject("code", pathology.getSampathologycode());//病理号
            options = getPathologyOption(request);
            mv.addObject("options", options);

        }
        return mv;
    }

    @RequestMapping(method = {RequestMethod.POST}, value = "/edit")
    @ResponseBody
    public void saveOrUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = WebControllerUtil.getAuthUser();
        PimsPathologyTemplate ppt = (PimsPathologyTemplate) setBeanProperty(request, PimsPathologyTemplate.class);
        ppt.setTemcreatetime(new Date());
        ppt.setTemcreateuser(String.valueOf(WebControllerUtil.getAuthUser().getId()));
        ppt.setTempathologyid(user.getUserBussinessRelate().getPathologyLibId());
        ppt.setTemcustomerid(user.getHospitalId());
        if (ppt.getTemtype() == 1L) {
            ppt.setTemownername(user.getName());
            ppt.setTemownerid(String.valueOf(user.getId()));
        } else {
            ppt.setTemownername(Constants.TEMPLATE_OWNER_NAME);
            ppt.setTemownerid(Constants.TEMPLATE_OWNER_ID);
        }
        pptm.save(ppt);
    }

    //查询符合条件的模板信息
    @RequestMapping(method = {RequestMethod.GET}, value = "/getpathologytemp")
    @ResponseBody
    public DataResponse getPathologyTemp(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DataResponse dr = new DataResponse();
        GridQuery gridQuery = new GridQuery(request);
        Long tempType = Long.valueOf(request.getParameter("type"));
        User user = WebControllerUtil.getAuthUser();
        Long pathologyId = user.getUserBussinessRelate().getPathologyLibId();
        gridQuery.setUserId(user.getId());
        gridQuery.setHospitalId(user.getHospitalId());
        List<PimsPathologyTemplate> result = pptm.getTemplateList(gridQuery, tempType, pathologyId);
        Integer total = pptm.countTemplate(gridQuery.getUserId(), gridQuery.getHospitalId(), tempType, pathologyId);
        dr.setRecords(total);
        dr.setPage(gridQuery.getPage());
        dr.setTotal(getTotalPage(total, gridQuery.getRow(), gridQuery.getPage()));
        dr.setRows(getResultMap(result));
        response.setContentType(contentType);
        return dr;
    }

    //查询符合条件的标本信息
    @RequestMapping(method = {RequestMethod.GET}, value = "/query")
    @ResponseBody
    public DataResponse query(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PimsPathologySample sample = (PimsPathologySample) setBeanProperty(request, PimsPathologySample.class);
        DataResponse dr = new DataResponse();
        GridQuery gridQuery = new GridQuery(request);
        List<PimsPathologySample> result = pimsPathologySampleManager.querySample(sample, gridQuery);
        Integer total = pimsPathologySampleManager.querySampleNum(sample);
        dr.setRecords(total);
        dr.setPage(gridQuery.getPage());
        dr.setTotal(getTotalPage(total, gridQuery.getRow(), gridQuery.getPage()));
        dr.setRows(getResultMap(result));
        response.setContentType(contentType);
        return dr;
    }
}
