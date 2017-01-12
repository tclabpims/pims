package com.pims.webapp.controller.pimssyspathology;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.pims.model.*;
import com.pims.service.QueryHisDataService;
import com.pims.service.UpdateReportDataService;
import com.pims.service.pimspathologysample.PimsPathologyParaffinManager;
import com.pims.service.pimspathologysample.PimsPathologyReportPdfManager;
import com.pims.service.pimspathologysample.PimsPathologySampleManager;
import com.pims.service.pimspathologysample.PimsPathologySlideManager;
import com.pims.service.pimssyspathology.*;
import com.pims.util.PDFWebService;
import com.pims.webapp.controller.GridQuery;
import com.pims.webapp.controller.PIMSBaseController;
import com.pims.webapp.controller.WebControllerUtil;
import com.pims.webapp.util.HtmlGenerator;
import com.smart.Constants;
import com.smart.model.lis.Hospital;
import com.smart.model.user.User;
import com.smart.service.lis.HospitalManager;
import com.smart.util.Config;
import com.smart.util.GenericPdfUtil;
import com.smart.webapp.util.DataResponse;
import com.smart.webapp.util.PrintwriterUtil;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
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

    @Autowired
    private PimsPathologyOrderChildManager pimsPathologyOrderChildManager;

    @Autowired
    private PimsPathologyFavoriteManager pimsPathologyFavoriteManager;

    @Autowired
    private PimsPathologyFollowupManager pimsPathologyFollowupManager;

    @RequestMapping(value = "/addFollowup", method = RequestMethod.GET)
    @ResponseBody
    public void addFollowup(HttpServletRequest request, HttpServletResponse response) throws Exception {

    }

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

    @RequestMapping(value = "/addFavorite", method = RequestMethod.GET)
    @ResponseBody
    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String favtitle = request.getParameter("favtitle");
        String favdescription = request.getParameter("favdescription");
        String pathologyItems = request.getParameter("pathologyItems");
        String num = request.getParameter("num");
        JSONArray array = JSONArray.parseArray(pathologyItems);
        User user = WebControllerUtil.getAuthUser();
        for(Object obj : array) {
            JSONObject jsonObject = (JSONObject)obj;
            PimsPathologyFavorite favorite = new PimsPathologyFavorite();
            favorite.setFavowner(user.getUsername());
            favorite.setFavsampleid(jsonObject.getLongValue("sampleid"));
            favorite.setFavpathologycode(jsonObject.getString("sampathologycode"));
            favorite.setFavcustomercode(jsonObject.getLongValue("samcustomerid"));
            favorite.setFavtype(0);
            favorite.setFavtitle(favtitle);
            favorite.setFavdescription(favdescription);
            favorite.setFavstate(0L);
            favorite.setFavtime(new Date());
            favorite.setFavfirstn(Long.valueOf(num));
            pimsPathologyFavoriteManager.save(favorite);
        }
    }

    @RequestMapping(value = "/camera", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView camera(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.addObject("sampleId", request.getParameter("sampleId"));
        mv.addObject("customerId", request.getParameter("customerId"));
        mv.addObject("nowshow",request.getParameter("nowshowrow"));
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

    @Autowired
    private PimsPathologyReportPdfManager pimsPathologyReportPdfManager;

    @RequestMapping(value = "/report/print", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> printReport(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long sampleId = Long.valueOf(request.getParameter("sampleid"));
        String writerString = getReportHtml(request,null);
        String rootDir = request.getSession().getServletContext().getRealPath("/pdf");
        String fileName = sampleId + ".html";
        String outputFile = rootDir + File.separator + fileName;
//        generateHtml(outputFile, writer.toString());
        generateHtml(outputFile, writerString);
        response.setContentType(super.contentType);
        Map<String, String> map = new HashMap<>();
        map.put("url", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/pdf/" + fileName);
//            GenericPdfUtil.html2Pdf(sampleId+".pdf",writer.toString());
        map.put("writerString",writerString);
        map.put("sampleid",String.valueOf(sampleId));
        return map;
    }


    @RequestMapping(value = "/report/printList", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> printReportList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String tasks = request.getParameter("tasks");
        com.alibaba.fastjson.JSONArray taskList = JSON.parseArray(tasks);
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        if(taskList != null && taskList.size() > 0){
            for(int i = 0;i< taskList.size();i++){
                Map map = (Map) taskList.get(i);
                PimsPathologySample sample = (PimsPathologySample) setBeanProperty(map,PimsPathologySample.class);
                sample = pimsPathologySampleManager.getBySampleNo(sample.getSampleid());
                String writerString = getReportHtml(request,sample);
                Map<String, Object> map1 = new HashMap<String, Object>();
                map1.put("sampleid",sample.getSampleid());
                map1.put("writerString", writerString);
                mapList.add(map1);
            }
        }
        Map map = new HashMap<>();
        map.put("writerString",mapList);
        return map;
    }

    @RequestMapping(value = "/updateprintStates", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> updateprintStates(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, String> map = new HashMap<>();
        Long sampleId = Long.valueOf(request.getParameter("sampleid"));
        PimsPathologySample sample = pimsPathologySampleManager.getBySampleNo(sampleId);
        if(sample.getSamsamplestatus() < 8  && sample.getSamsamplestatus() > 4){
            updateReportDataService.updateSts(sample);
        }
        if(sample.getSamsamplestatus() > 4){
            sample.setSamsamplestatus(8);
            pimsPathologySampleManager.save(sample);
        }
        map.put("result","true");
        return map;
    }

    /**
     * 创建静态页
     * @param request
     * @return
     * @throws Exception
     */
    private String getReportHtml(HttpServletRequest request,PimsPathologySample sample) throws Exception{
        Long sampleId = Long.valueOf(StringUtils.isEmpty(request.getParameter("sampleid"))?0:Long.valueOf(request.getParameter("sampleid")));
//        PimsPathologyReportPdf pprpdf = pimsPathologyReportPdfManager.getPdfBySampleId(sampleId);
        String picNum = request.getParameter("picNum");
        Long pictureClass = Long.valueOf(StringUtils.isEmpty(request.getParameter("picpictureclass"))?0:Long.valueOf(request.getParameter("picpictureclass")));
        int picNumInt = (picNum == null || "".equals(picNum)) ? 0 : Integer.valueOf(picNum);
        String templateUrl = request.getParameter("templateUrl");
        int operateType = StringUtils.isEmpty(request.getParameter("type"))?0:Integer.valueOf(request.getParameter("type"));
        String pclass = request.getParameter("patClass");
        int patClass = StringUtils.isEmpty(pclass)?0:Integer.valueOf(pclass);

        if(sample != null){
            sampleId = sample.getSampleid();
            picNumInt = 0;
            pictureClass = Long.valueOf(2);
            templateUrl = "";
            PimsSysPathology pathology = pimsSysPathologyManager.get(sample.getSampathologyid());
            List<PimsSysReportFormate> lis = pimsSysReportFormatManager.getReportFormatByPathologyId(pathology.getPathologyid());
            if(lis == null || lis.size() == 0){
                return null;
            }else{
                PimsSysReportFormate psf = lis.get(0);
                picNumInt = Integer.valueOf(psf.getFormpicturenum());
                templateUrl = psf.getFormweburl();
            }
            pclass = pathology.getPatclass();
            patClass = pclass == null?0:Integer.valueOf(pclass);
        }


        PimsPathologySample pimsPathologySample = pimsPathologySampleManager.get(sampleId);
        PimsSysPathology pathology = pimsSysPathologyManager.get(pimsPathologySample.getSampathologyid());
        List<PimsPathologyPictures> pictures = pimsPathologyPicturesManager.getSamplePicture(sampleId, pictureClass);
        PimsSampleResult result = null;
        Map<String, String> resultMap = null;
        VelocityContext context = getVelocityContext(pimsPathologySample, pathology);
        if (picNumInt > pictures.size()) picNumInt = pictures.size();
        if(patClass == 2) {
            resultMap = pimsSampleResultManager.getYjxbDiagnosisResult(sampleId);
            context.put("diagnosisResult", resultMap.get("diagnosisResult"));
            context.put("advice", resultMap.get("advice"));
            context.put("dnaResult", resultMap.get("dnaResult"));
            context.put("checkedItemsStr", resultMap.get("checkedItemsStr"));
            context.put("degree", resultMap.get("degree"));
        } else if(patClass == 7) {
            resultMap = pimsSampleResultManager.getHPVTestResult(sampleId);
            context.put("sampleAmount", resultMap.get("sampleAmount"));
            context.put("hpvResult", resultMap.get("hpvResult"));
            context.put("diagnosisResult", resultMap.get("diagnosisResult"));
        }
        else {
            result = pimsSampleResultManager.getSampleResultForPrint(sampleId);
            context.put("diagnosisResult", result == null ? "" : result.getRestestresult());
        }
        context.put("picNum", picNumInt);
//            context.put("hospitalLogo", getHospitalLogo(request, pimsPathologySample.getSamcustomerid()));
        context.put("hospitalLogo", "data:image/jpg" + ";base64," + getHospitalLogo(request, pimsPathologySample.getSamcustomerid()));//医院logo

        StringBuilder logoFileRoot2 = new StringBuilder();
        logoFileRoot2.append(Config.getString("img.hospital","E:\\img\\hospital") + File.separator+ pimsPathologySample.getSamcustomerid() + File.separator + "R-Logo.jpg");
        FileInputStream fileInputStream2 = new FileInputStream(logoFileRoot2.toString().replace("/","\\"));
        byte[] buffer2 = null;
        buffer2 = new byte[fileInputStream2.available()];
        fileInputStream2.read(buffer2);
        fileInputStream2.close();
        context.put("RhospitalLogo","data:image/jpg" + ";base64," + new String(org.apache.commons.codec.binary.Base64.encodeBase64(buffer2)));//条形码

        if (picNumInt > 0) {
            Map<String, String> map1 = new HashMap<>();
            for (int i = 0; i < picNumInt; i++) {
                PimsPathologyPictures pic = pictures.get(i);
                String realPath = pic.getPicsavepath();
                if (realPath != null && realPath.length() > 0) {
//                    realPath = realPath.substring(realPath.indexOf("\\images"));
//                    realPath = realPath.replaceAll("\\\\", "/");
                    if(picNumInt == 1) {
                        StringBuilder logoFileRoot1 = new StringBuilder();
//                        logoFileRoot1.append(request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + realPath);
                        logoFileRoot1.append(realPath);
                        FileInputStream fileInputStream1 = new FileInputStream(logoFileRoot1.toString().replace("/","\\"));
                        byte[] buffer1 = null;
                        buffer1 = new byte[fileInputStream1.available()];
                        fileInputStream1.read(buffer1);
                        fileInputStream1.close();
                        String imgstyle = realPath.substring(realPath.lastIndexOf(".")+1);
                        System.out.println(imgstyle);
                        context.put("imgsrc","data:image/"+imgstyle + ";base64," + new String(org.apache.commons.codec.binary.Base64.encodeBase64(buffer1)));//条形码
//                        context.put("imgsrc", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + realPath);
                    }
                    else{
                        StringBuilder logoFileRoot1 = new StringBuilder();
                        logoFileRoot1.append(realPath);
                        FileInputStream fileInputStream1 = new FileInputStream(logoFileRoot1.toString().replace("/","\\"));
                        byte[] buffer1 = null;
                        buffer1 = new byte[fileInputStream1.available()];
                        fileInputStream1.read(buffer1);
                        fileInputStream1.close();
                        String imgstyle = realPath.substring(realPath.lastIndexOf(".")+1);

//                        context.put("imgsrc","data:image/jpg" + ";base64," + new String(org.apache.commons.codec.binary.Base64.encodeBase64(buffer1)));//条形码
                        map1.put("imgsrc" + (i + 1), "data:image/"+imgstyle + ";base64," + new String(org.apache.commons.codec.binary.Base64.encodeBase64(buffer1)));
                    }
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

        return writer.toString();
    }

    /**
     * 创建报告单PDF
     * @return
     * @throws Exception
     */
    public void createReportPdf(HttpServletRequest request) throws Exception {
        String html = getReportHtml(request,null);
        Long sampleId = Long.valueOf(request.getParameter("sampleid"));
        GenericPdfUtil.html2Pdf(sampleId+".pdf",html);
    }

    private String getHospitalLogo(HttpServletRequest request, Long hospitalId) throws  Exception{
        Hospital hospital = hospitalManager.get(hospitalId);
//        StringBuilder logoFileRoot = new StringBuilder(request.getScheme());
//        logoFileRoot.append("://").append(request.getServerName())
//                .append(":").append(request.getServerPort()).append("/images/hospital/");
//        logoFileRoot.append(hospitalId).append("/").append(hospital.getLogo());
//        return logoFileRoot.toString();
        StringBuilder logoFileRoot = new StringBuilder();
        logoFileRoot.append(Config.getString("img.hospital","E:\\img\\hospital") + File.separator+ hospitalId + File.separator + hospital.getLogo());
        FileInputStream fileInputStream = new FileInputStream(logoFileRoot.toString().replace("/","\\"));
        byte[] buffer = null;
        buffer = new byte[fileInputStream.available()];
        fileInputStream.read(buffer);
        fileInputStream.close();
        return new String(org.apache.commons.codec.binary.Base64.encodeBase64(buffer));
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

    private VelocityContext getVelocityContext(PimsPathologySample sample, PimsSysPathology pathology) throws Exception {
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
        context.put("samtestresult", pimsPathologyOrderChildManager.getTestItemResult(sample.getSampleid()));
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
        if(StringUtils.isEmpty(sample.getSaminitiallyusername())){
            context.put("samreportor","");//条形码
        }
//        if(StringUtils.isEmpty(sample.getSamreportor())){
//            context.put("samreportor","");//条形码
//        }
        else{
            StringBuilder logoFileRoot1 = new StringBuilder();
//            logoFileRoot1.append(Config.getString("dzqm.path","E:\\img\\dzqm") + File.separator + sample.getSamreportor()+".jpg");
            logoFileRoot1.append(Config.getString("dzqm.path","E:\\img\\dzqm") + File.separator + sample.getSaminitiallyusername()+".jpg");

            FileInputStream fileInputStream1 = new FileInputStream(logoFileRoot1.toString().replace("/","\\"));
            byte[] buffer1 = null;
            buffer1 = new byte[fileInputStream1.available()];
            fileInputStream1.read(buffer1);
            fileInputStream1.close();
            context.put("samreportor","data:image/jpg" + ";base64," + new String(org.apache.commons.codec.binary.Base64.encodeBase64(buffer1)));//条形码
        }


//        context.put("samreportor", sample.getSamreportor());
        if(StringUtils.isEmpty(sample.getSamauditer())){
            context.put("samauditer","");//条形码

        }else{
            StringBuilder logoFileRoot = new StringBuilder();
            logoFileRoot.append(Config.getString("dzqm.path","E:\\img\\dzqm") + File.separator + sample.getSamauditer()+".jpg");
            FileInputStream fileInputStream = new FileInputStream(logoFileRoot.toString().replace("/","\\"));
            byte[] buffer = null;
            buffer = new byte[fileInputStream.available()];
            fileInputStream.read(buffer);
            fileInputStream.close();
            context.put("samauditer","data:image/jpg" + ";base64," + new String(org.apache.commons.codec.binary.Base64.encodeBase64(buffer)));//条形码
        }
//        context.put("samauditer", sample.getSamauditer());
//        if(sample.getSamreportedtime() != null)
//        context.put("samreportedtime", Constants.DF2.format(sample.getSamreportedtime()));
//        else
//        context.put("samreportedtime", "");
        if(sample.getSamauditedtime() != null)
            context.put("samreportedtime", Constants.DF2.format(sample.getSamauditedtime()));
        else
            context.put("samreportedtime", "");

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

//    @RequestMapping(method = {RequestMethod.POST}, value = "/upload")
//    @ResponseBody
//    public Map<String, Object> upload(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        String sampleId = request.getParameter("sampleid");
//        String samCustomerId = request.getParameter("samcustomerid");
//        String continuous = request.getParameter("continuous");
//        Long picPictureClass = Long.valueOf(request.getParameter("picpictureclass"));
//        ServletInputStream inputStream = request.getInputStream();
//        BufferedImage bufferedImage = ImageIO.read(inputStream);
//        String customerFileDir = "/images/jpg" + "/" + samCustomerId + "/" + sampleId;
//        // the directory to upload to
//        String uploadDir = request.getSession().getServletContext().getRealPath(customerFileDir);
//
//        //图片序号
//        int picIndex = 0;
//
//        // The following seems to happen when running jetty:run
//        if (uploadDir == null) {
//            picIndex = 1;
//            uploadDir = new File("src/main/webapp" + customerFileDir).getAbsolutePath();
//        } else {
//            File exsitFile = new File(uploadDir);
//            File[] files = exsitFile.listFiles();
//            //如果不是连拍
//            if ("false".equals(continuous)) {
//                if (files != null && files.length > 0) {
//                    for (File f : files) {
//                        if (f.isFile()) {
//                            delPathologyPictures(f.getName(), Long.valueOf(sampleId));
//                            f.delete();
//                        }
//                    }
//                }
//            } else {
//                if (files != null && files.length > 0) {
//                    for (File f : files) {
//                        if (f.isFile()) {
//                            picIndex = picIndex + 1;
//                        }
//                    }
//                }
//            }
//        }
//        String fileName = sampleId + "_" + new Date().getTime() + "_" + picIndex + "." + Constants.PIC_TYPE_JPG;
//        uploadDir += "/" + fileName;
//
//        // Create the directory if it doesn't exist
//        File dirPath = new File(uploadDir);
//
//        boolean success;
//        success = dirPath.mkdirs();
//        if (success) {
//            ImageIO.write(bufferedImage, "jpeg", dirPath);
//            Thumbnails.of(dirPath).size(480,360).toFile(dirPath);
//        }
//
//        PimsPathologyPictures pp = saveImageFile(Long.valueOf(sampleId), dirPath, picIndex, request, picPictureClass);
//
//        Map<String, Object> map = new HashMap<>();
//        map.put("name", pp.getPicpicturename());
//        map.put("pictureid", pp.getPictureid());
//        map.put("src", customerFileDir + "/" + fileName);
//        map.put("continuous", continuous);
//        response.setContentType(contentType);
//        return map;
//    }

    @RequestMapping(method = {RequestMethod.POST}, value = "/upload")
    @ResponseBody
    public Map<String, Object> upload(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String sampleId = request.getParameter("sampleid");
        String samCustomerId = request.getParameter("samcustomerid");
        String continuous = request.getParameter("continuous");
        Long picPictureClass = Long.valueOf(request.getParameter("picpictureclass"));
        String imgString = request.getParameter("img");
//        ServletInputStream inputStream = request.getInputStream();
//        BufferedImage bufferedImage = ImageIO.read(inputStream);
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
            //使用BASE64对图片文件数据进行解码操作
            BASE64Decoder decoder = new BASE64Decoder();
            //通过Base64解密，将图片数据解密成字节数组
            byte[] bytes = decoder.decodeBuffer(imgString);
            //构造字节数组输入流
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            //读取输入流的数据
            BufferedImage bi = ImageIO.read(bais);
            ImageIO.write(bi, "jpeg", dirPath);
            bais.close();
//            ImageIO.write(bufferedImage, "jpeg", dirPath);
            Thumbnails.of(dirPath).size(480,360).toFile(dirPath);
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
                Thumbnails.of(file).size(600,400).toFile(file);
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
        String f = request.getParameter("states");
        if(f.equals("4")){
            PimsPathologyReportPdf rpdf = pimsPathologyReportPdfManager.getPdfBySampleId(sample.getSampleid());
            //删除PDF存储路径
            pimsPathologyReportPdfManager.deletePDF(sample.getSampleid());
            //删除37数据库上的记录
            updateReportDataService.delete(sample);
            PDFWebService pdfWebService = new PDFWebService();
            pdfWebService.deletePdf(Config.getString("pdf.key",""),Config.getString("pdf.upload.path",""),rpdf);//删除PDF
            queryHisDataService.delete(sample);//删除HIS数据库
        }
        pimsPathologySampleManager.sign(sample);
    }


    @RequestMapping(method = {RequestMethod.POST}, value = "/saveResult")
    @ResponseBody
    public Map<String, Long> saveResult(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String result = request.getParameter("result");
        String pclass = request.getParameter("patClass");
        int patClass = pclass == null?0:Integer.valueOf(pclass);
        User user = WebControllerUtil.getAuthUser();
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
            sampleResult.setRescustomerid(((JSONObject) obj).getLongValue("customerId"));
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
        if (set.size() > 0) map = pimsSampleResultManager.save(set, patClass);
        response.setContentType(contentType);
        return map;
    }

    @RequestMapping(method = {RequestMethod.GET}, value = "/sampleresult")
    @ResponseBody
    public Map<String, PimsSampleResult> getSampleResult(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long sampleId = Long.valueOf(request.getParameter("sampleid"));
        String pclass = request.getParameter("patClass");
        int patClass = pclass == null?0:Integer.valueOf(pclass);
        response.setContentType(contentType);
        return pimsSampleResultManager.getSampleResult(sampleId, patClass);
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
//        String options = getPathologySelectOption(request);
        ModelAndView mv = getmodelView(request);
//        mv.addObject("options", options);
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
//            options = getPathologySelectOption(request);
//            mv.addObject("options", options);

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


    /**
     * 获取未接收未审核单据
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/getpathnum*", method = RequestMethod.GET)
    public void getPathNum(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PimsPathologySample sample = (PimsPathologySample) setBeanProperty(request, PimsPathologySample.class);
        DataResponse dr = new DataResponse();
        //待接收
        sample.setSampiecedoctorid("");
        sample.setSamfirstv("1");
        Integer noreceivetotal  = pimsPathologySampleManager.querySampleNum(sample);
        sample.setSamfirstv("2");
        Integer noaudittotal = pimsPathologySampleManager.querySampleNum(sample);
        JSONObject o = new JSONObject();
        o.put("success",true);
        o.put("noreceiveid",noreceivetotal);
        o.put("noauditid",noaudittotal);
        PrintwriterUtil.print(response, o.toString());
    }

    @Autowired
    private UpdateReportDataService updateReportDataService;
    @Autowired
    private QueryHisDataService queryHisDataService;
    /**
     * 取消签发
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/resetqianfa*", method = RequestMethod.POST)
    public String resetqianfa(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        JSONObject o = new JSONObject();
        String tasks = request.getParameter("tasks");
        com.alibaba.fastjson.JSONArray taskList = JSON.parseArray(tasks);
        for(int i=0;i<taskList.size();i++){
            Map map = (Map) taskList.get(i);
            PimsPathologySample sample = (PimsPathologySample) setBeanProperty(map,PimsPathologySample.class);
            sample = pimsPathologySampleManager.getBySampleNo(sample.getSampleid());
            if(sample.getSamsamplestatus() > 5){//已签发才允许撤销签发
                sample.setSamsamplestatus(5);
                sample.setSamreportedtime(null);
                sample.setSamreportorid(null);
                sample.setSamreportor(null);
                sample = pimsPathologySampleManager.save(sample);
                PimsPathologyReportPdf rpdf = pimsPathologyReportPdfManager.getPdfBySampleId(sample.getSampleid());
                //删除PDF存储路径
                pimsPathologyReportPdfManager.deletePDF(sample.getSampleid());
                //删除37数据库上的记录
                updateReportDataService.delete(sample);
                PDFWebService pdfWebService = new PDFWebService();
                pdfWebService.deletePdf(Config.getString("pdf.key",""),Config.getString("pdf.upload.path",""),rpdf);//删除PDF
                queryHisDataService.delete(sample);//删除HIS数据库
            }
        }
        o.put("message", "取消签发成功！");
        o.put("success", true);
        PrintwriterUtil.print(response, o.toString());
        return  null;
    }

    /**
     * 签发
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/qianfa*", method = RequestMethod.POST)
    public String qianfa(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        JSONObject o = new JSONObject();
        String tasks = request.getParameter("tasks");
        com.alibaba.fastjson.JSONArray taskList = JSON.parseArray(tasks);
        for(int i=0;i<taskList.size();i++){
            Map map = (Map) taskList.get(i);
            PimsPathologySample sample = (PimsPathologySample) setBeanProperty(map,PimsPathologySample.class);
            sample = pimsPathologySampleManager.getBySampleNo(sample.getSampleid());
            if(sample.getSamsamplestatus() > 4){//已审核才允许签发
                if(sample.getSamsamplestatus() == 5){
                    sample.setSamsamplestatus(6);
                }
                sample.setSamreportedtime(new Date());
                sample.setSamreportorid(String.valueOf(user.getId()));
                sample.setSamreportor(user.getName());
                sample = pimsPathologySampleManager.save(sample);
                String html = getReportHtml(request,sample);
                GenericPdfUtil.html2Pdf(sample.getSampleid()+".pdf",html);
                //删除PDF存储路径
                pimsPathologyReportPdfManager.deletePDF(sample.getSampleid());
                //重新生成PDF
                PimsPathologyReportPdf rpdf = new PimsPathologyReportPdf();
                rpdf.setPdffileid(sample.getSampleid());//Pdf文件Id
                rpdf.setPdfsampleid(sample.getSampleid());//标本Id
                rpdf.setPdfpathologycode(sample.getSampathologycode());//病理编号
                rpdf.setPdffilename(sample.getSampleid()+".pdf");//Pdf文件名称
                rpdf.setPdffilesize("142k");//Pdf文件大小
                rpdf.setPdffilesavepath(Config.getString("pdf.path","E:\\pims\\pdf"));//存储路径
                rpdf.setPdfuploadtime(new Date());//上传时间
                rpdf.setPdfuploaduser(user.getName());//上传用户
//                rpdf.setPdfuploadip();//上传终端Ip
                rpdf.setPdfuploadtimes(Long.valueOf(1));//上传次数
                rpdf.setPdflastuploadtime(new Date());//最近一次上传时间
                rpdf.setPdflastuploaduser(user.getName());//最近一次上传人员
                rpdf.setPdfcreatetime(new Date());//创建时间
                rpdf.setPdfcreateuser(String.valueOf(user.getId()));//创建人员
                rpdf = pimsPathologyReportPdfManager.save(rpdf);
                PimsSysPathology psp = pimsSysPathologyManager.get(sample.getSampathologyid());
                //删除37数据库上的记录
                updateReportDataService.delete(sample);
                updateReportDataService.insert(sample,rpdf,psp);//插入37数据库
                PDFWebService pdfWebService = new PDFWebService();
                pdfWebService.deletePdf(Config.getString("pdf.key",""),Config.getString("pdf.upload.path",""),rpdf);//删除PDF
                pdfWebService.uploadPdf(Config.getString("pdf.key",""),Config.getString("pdf.upload.path",""),rpdf);//上传PDF
//                pdfWebService.saveHisResult(sample,psp);
                queryHisDataService.delete(sample);//删除HIS数据库
                queryHisDataService.insert(sample,psp);//插入HIS数据库
            }
        }
        o.put("message", "签发成功！");
        o.put("success", true);
        PrintwriterUtil.print(response, o.toString());
        return  null;
    }



    /**
     * 创建静态页
     * @param request
     * @return
     * @throws Exception
     */
//    private String getReportHtml(HttpServletRequest request,PimsPathologySample sample) throws Exception{
//        Long sampleId = sample.getSampleid();
//        int picNumInt = 0;
//        Long pictureClass = Long.valueOf(2);
//        String templateUrl = "";
//        PimsSysPathology pathology = pimsSysPathologyManager.get(sample.getSampathologyid());
//        List<PimsSysReportFormate> lis = pimsSysReportFormatManager.getReportFormatByPathologyId(pathology.getPathologyid());
//        if(lis == null || lis.size() == 0){
//            return null;
//        }else{
//            PimsSysReportFormate psf = lis.get(0);
//            picNumInt = Integer.valueOf(psf.getFormpicturenum());
//            templateUrl = psf.getFormweburl();
//        }
//        String pclass = pathology.getPatclass();
//        int patClass = pclass == null?0:Integer.valueOf(pclass);
//        PimsPathologySample pimsPathologySample = pimsPathologySampleManager.get(sampleId);
//        List<PimsPathologyPictures> pictures = pimsPathologyPicturesManager.getSamplePicture(sampleId, pictureClass);
//        PimsSampleResult result = null;
//        Map<String, String> resultMap = null;
//        VelocityContext context = getVelocityContext(pimsPathologySample, pathology);
//        if (picNumInt > pictures.size()) picNumInt = pictures.size();
//        if(patClass == 2) {
//            resultMap = pimsSampleResultManager.getYjxbDiagnosisResult(sampleId);
//            context.put("diagnosisResult", resultMap.get("diagnosisResult"));
//            context.put("advice", resultMap.get("advice"));
//            context.put("dnaResult", resultMap.get("dnaResult"));
//            context.put("checkedItemsStr", resultMap.get("checkedItemsStr"));
//            context.put("degree", resultMap.get("degree"));
//        } else if(patClass == 7) {
//            resultMap = pimsSampleResultManager.getHPVTestResult(sampleId);
//            context.put("sampleAmount", resultMap.get("sampleAmount"));
//            context.put("hpvResult", resultMap.get("hpvResult"));
//            context.put("diagnosisResult", resultMap.get("diagnosisResult"));
//        }
//        else {
//            result = pimsSampleResultManager.getSampleResultForPrint(sampleId);
//            context.put("diagnosisResult", result == null ? "" : result.getRestestresult());
//        }
//        context.put("picNum", picNumInt);
////            context.put("hospitalLogo", getHospitalLogo(request, pimsPathologySample.getSamcustomerid()));
//        context.put("hospitalLogo", "data:image/png" + ";base64," + getHospitalLogo(request, pimsPathologySample.getSamcustomerid()));//医院logo
//
//        if (picNumInt > 0) {
//            Map<String, String> map1 = new HashMap<>();
//            for (int i = 0; i < picNumInt; i++) {
//                PimsPathologyPictures pic = pictures.get(i);
//                String realPath = pic.getPicsavepath();
//                if (realPath != null && realPath.length() > 0) {
//                    realPath = realPath.substring(realPath.indexOf("\\images"));
//                    realPath = realPath.replaceAll("\\\\", "/");
//                    if(picNumInt == 1) {
//                        context.put("imgsrc", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + realPath);
//                    }
//                    else
//                        map1.put("imgsrc" + (i + 1), request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + realPath);
//                }
//            }
//            context.put("multiSrc", map1);
//        }
//
//        VelocityEngine engine = new VelocityEngine();
//        engine.setProperty(Velocity.RESOURCE_LOADER, "class");
//        engine.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
//        engine.init();
//
//        Template template = engine.getTemplate(templateUrl, "UTF-8");
//        StringWriter writer = new StringWriter();
//        template.merge(context, writer);
//
//        return writer.toString();
//    }

}
