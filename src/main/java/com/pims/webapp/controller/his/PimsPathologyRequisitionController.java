package com.pims.webapp.controller.his;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pims.model.*;
import com.pims.service.QueryHisDataService;
import com.pims.service.his.PimsPathologyRequisitionManager;
import com.pims.service.his.PimsRequisitionMaterialManager;
import com.pims.service.pimssyspathology.PimsSysPathologyManager;
import com.pims.service.pimssysreqtestitem.PimsSysReqTestitemManager;
import com.pims.util.OneBarcodeUtil;
import com.pims.webapp.controller.PIMSBaseController;
import com.pims.webapp.controller.WebControllerUtil;
import com.smart.Constants;
import com.smart.model.lis.Hospital;
import com.smart.model.user.User;
import com.smart.service.lis.HospitalManager;
import com.smart.util.Config;
import com.smart.util.ConvertUtil;
import com.smart.webapp.util.DataResponse;
import com.smart.webapp.util.PrintwriterUtil;
import org.apache.commons.codec.binary.*;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@Controller
@RequestMapping("/pimspathology")
public class PimsPathologyRequisitionController extends PIMSBaseController{
	@Autowired
	private PimsPathologyRequisitionManager pimsPathologyRequisitionManager;
	@Autowired
	private PimsRequisitionMaterialManager pimsRequisitionMaterialManager;
	@Autowired
	private PimsSysReqTestitemManager pimsSysReqTestitemManager;
	@Autowired
	private QueryHisDataService dataService;
	@Autowired
	private PimsSysPathologyManager pimsSysPathologyManager;
	@Autowired
	private HospitalManager hospitalManager;

	/**
	 * 渲染视图
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView handleRequest(HttpServletRequest request) throws Exception {
		String today = Constants.DF3.format(new Date());
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long logylibid = user.getUserBussinessRelate().getPathologyLibId();//病种库
		List<PimsSysReqTestitem> list = pimsSysReqTestitemManager.getTestitemInfo(null);//查询申请项目列表
		ModelAndView view = new ModelAndView();
		view.addObject("receivetime", today);//当前时间
		view.addObject("reqcustomerid",user.getHospitalId());//账号所属医院
		view.addObject("reqpathologyid",logylibid);//当前用户选择的病例库
		view.addObject("reqsource",0);//申请单来源
		view.addObject("testList",getResultMap(list));//申请项目列表
		return view;
	}

	/**
	 * 获取申请单详细信息
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/get*", method = RequestMethod.GET)
	public void getsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String code = request.getParameter("id");
		PimsPathologyRequisition pathology = pimsPathologyRequisitionManager.getBySampleNo(Long.parseLong(code));
		String sjcl = pimsPathologyRequisitionManager.getSjcl(Long.parseLong(code));
		JSONObject pathMap = getJSONObject(pathology);
		pathMap.put("sjcl",sjcl);
		PrintwriterUtil.print(response, pathMap.toString());
	}

	/**
	 * 获取最大单号
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getcode*", method = RequestMethod.GET)
	public void getMaxCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int nowyear = Calendar.getInstance().get(Calendar.YEAR);//获取当前年份
		String requisitionno = "";
		String maxCode = pimsPathologyRequisitionManager.getMaxCode(null);
		if(maxCode == null || !maxCode.contains(String.valueOf(nowyear))){
			requisitionno = nowyear + "00000001";
		}else if(maxCode.contains(String.valueOf(nowyear))){
			long a = Long.valueOf(maxCode).longValue() + 1;
			requisitionno =  String.valueOf(a);
		}
		JSONObject o = new JSONObject();
		o.put("success",true);
		o.put("maxcode",requisitionno);
		PrintwriterUtil.print(response, o.toString());
	}

	/**
	 * 获取申请单列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/ajax/pathology*", method = RequestMethod.GET)
	@ResponseBody
	public DataResponse getRequisitionInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		DataResponse dataResponse = new DataResponse();
		PimsBaseModel ppr = new PimsBaseModel(request);
		List<PimsPathologyRequisition> list = pimsPathologyRequisitionManager.getRequisitionInfo(ppr);
        int num = pimsPathologyRequisitionManager.getReqListNum(ppr);
		if(list == null || list.size() == 0) {
			return null;
		}
		dataResponse.setRecords(num);
		dataResponse.setPage(ppr.getPage());
		dataResponse.setTotal(getTotalPage(num, ppr.getRow(), ppr.getPage()));
		dataResponse.setRows(getResultMap(list));
		response.setContentType("text/html; charset=UTF-8");
		return dataResponse;
	}

	/**
	 * 获取申请组织列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/ajax/getmaterial*", method = RequestMethod.GET)
	@ResponseBody
	public DataResponse getListByReqId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		DataResponse dataResponse = new DataResponse();
		String code = request.getParameter("reqId");
		if(StringUtils.isEmpty(code)){
			return null;
		}
		List<PimsRequisitionMaterial> list = pimsRequisitionMaterialManager.getListByReqId(Long.parseLong(code));
		if(list == null || list.size() == 0) {
			return null;
		}
		dataResponse.setRecords(list.size());
		dataResponse.setRows(getResultMap(list));
		response.setContentType("text/html; charset=UTF-8");
		return dataResponse;
	}

	/**
	 * 新增单据时动态生成申请字符串
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/ajax/item*", method = RequestMethod.GET)
	@ResponseBody
	public String getSysData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PimsBaseModel map = new PimsBaseModel();
		map.setReq_code(request.getParameter("req_code"));
		map.setLogyid(request.getParameter("logyid"));
		map.setReq_sts(request.getParameter("req_sts"));
		List list = pimsPathologyRequisitionManager.searchLists(map);
		String resultString = getResultJsons(list);
		//response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(resultString);
		return null;
	}

	/**
	 * 查看单据时动态生成申请字符串
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/ajax/reqdata*", method = RequestMethod.GET)
	@ResponseBody
	public String getReqData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		String reqffirstv = request.getParameter("reqffirstv");
		List list = pimsPathologyRequisitionManager.searchViews(Long.parseLong(id),reqffirstv);
		String resultString = getResultJsons(list);
		//response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(resultString);
		return null;
	}

	/**
	 * 保存单据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/editSample*", method = RequestMethod.POST)
	public String editSample(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PimsPathologyRequisition ppr = (PimsPathologyRequisition)setBeanProperty(request,PimsPathologyRequisition.class);
//		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String materiallist = request.getParameter("material");//取材部位
		String fieldlist = request.getParameter("arrs");//动态字段
		String fieldlist1 = request.getParameter("arrs1");//送检材料
		JSONArray materials = JSON.parseArray(materiallist);
		JSONArray fields = JSON.parseArray(fieldlist);
		JSONArray fields1 = JSON.parseArray(fieldlist1);
		JSONObject o = new JSONObject();
		ppr = pimsPathologyRequisitionManager.insertOrUpdate(materials,ppr,fields,fields1);
		//pimsPathologyRequisitionManager.save(ppr);
		o.put("requisitionid", ppr.getRequisitionid());
		o.put("requisitionno", ppr.getRequisitionno());
		o.put("reqitemnames", ppr.getReqitemnames());
		o.put("reqpathologyid", ppr.getReqpathologyid());
		o.put("message", "单据保存成功！");
		o.put("success", true);
		PrintwriterUtil.print(response, o.toString());
		return null;
	}

	/**
	 * 删除单据
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteSample*", method = RequestMethod.POST)
	public void deleteSample(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PimsPathologyRequisition ppr = (PimsPathologyRequisition)setBeanProperty(request,PimsPathologyRequisition.class);
		JSONObject o = new JSONObject();
		if(StringUtils.isEmpty(String.valueOf(ppr.getRequisitionid()))){
			o.put("message", "查不到该申请单的信息！");
			o.put("success", false);
		}else{
			pimsPathologyRequisitionManager.delete((long) ppr.getRequisitionid());
			o.put("message", "样本号为"+ ppr.getRequisitionid() + "的标本删除成功！");
			o.put("success", true);
		}
		PrintwriterUtil.print(response, o.toString());
	}

	/**
	 * 查看单据是否可修改
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/canchange*", method = RequestMethod.GET)
	public void canChange(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String code = request.getParameter("id");
		JSONObject o = new JSONObject();
		if(pimsPathologyRequisitionManager.canChange(Long.parseLong(code))){
			o.put("success", true);
		}else{
			o.put("success", false);
			o.put("message","无法进行该操作！");
		}
		PrintwriterUtil.print(response, o.toString());
	}

	/**
	 * 查看单据是否可存在
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/codeisexist*", method = RequestMethod.GET)
	public void codeIsExist(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String code = request.getParameter("code");
		JSONObject o = new JSONObject();
		String result = pimsPathologyRequisitionManager.codeIsExist(code);
		if(result == null){
			o.put("success", false);
			o.put("message","无法进行该操作！");
		}else{
			o.put("success", true);
			o.put("message",result);
		}
		PrintwriterUtil.print(response, o.toString());
	}

	/**
	 * 获取病人住院记录
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getpatientlist*", method = RequestMethod.GET)
	@ResponseBody
	public DataResponse getPatientList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		DataResponse dataResponse = new DataResponse();
		String brjzxh = request.getParameter("brjzxh");
		if(StringUtils.isEmpty(brjzxh)){
			return null;
		}
		List list = dataService.queryPatientList(brjzxh);
		if(list == null || list.size() == 0) {
			return null;
		}
		dataResponse.setRecords(list.size());
		dataResponse.setRows(getResultMap(list));
		response.setContentType("text/html; charset=UTF-8");
		return dataResponse;
	}

	/**
	 * 获取病人住院记录
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getpatientinfo*", method = RequestMethod.GET)
	@ResponseBody
	public DataResponse getPatientInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		DataResponse dataResponse = new DataResponse();
		String brjzxh = request.getParameter("brjzxh");
		String patient_type = request.getParameter("patient_type");
		List list = dataService.querPatientInfo(patient_type,brjzxh);
		if(list == null || list.size() == 0) {
			return null;
		}
		dataResponse.setRecords(list.size());
		dataResponse.setRows(getResultMap(list));
		response.setContentType("text/html; charset=UTF-8");
		return dataResponse;
	}

	@RequestMapping(value = "/report/printzqs", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, String> printReportzqs(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String hosptail = request.getParameter("hosptail");
		long hosptailId = 0 ;
		if(StringUtils.isEmpty(hosptail)){
			User user = WebControllerUtil.getAuthUser();
			hosptailId = user.getHospitalId();
		}else{
			hosptailId = Long.parseLong(hosptail);
		}

		PimsSysPathology pathology = pimsSysPathologyManager.get(Long.valueOf(request.getParameter("id")));
		Map<String, String> resultMap = null;
		VelocityContext context = new VelocityContext();
		context.put("formname", pathology.getPatcoddingprechar());
//		context.put("hospitalLogo", getHospitalLogo(request, hosptailId));
		context.put("hospitalLogo", "data:image/png" + ";base64," + getHospitalLogo(request, hosptailId));//医院logo
		VelocityEngine engine = new VelocityEngine();
		engine.setProperty(Velocity.RESOURCE_LOADER, "class");
		engine.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		engine.init();
		Template template = engine.getTemplate(pathology.getPatthirdv(), "UTF-8");
		StringWriter writer = new StringWriter();
		template.merge(context, writer);

		String rootDir = request.getSession().getServletContext().getRealPath("/pdf");
		String fileName = "1.html";
		String outputFile = rootDir + File.separator + fileName;
		generateHtml(outputFile, writer.toString());

		response.setContentType(super.contentType);

		Map<String, String> map = new HashMap<>();
		map.put("url", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/pdf/" + fileName);

		return map;
	}

	@RequestMapping(value = "/report/printreq", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, String> printReportreq(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String hosptail = request.getParameter("hosptail");
		long hosptailId = 0 ;
		if(StringUtils.isEmpty(hosptail)){
			User user = WebControllerUtil.getAuthUser();
			hosptailId = user.getHospitalId();
		}else{
			hosptailId = Long.parseLong(hosptail);
		}
		PimsPathologyRequisition reqPathology = pimsPathologyRequisitionManager.getBySampleNo(Long.parseLong(request.getParameter("id")));
		PimsSysPathology pathology = pimsSysPathologyManager.get(reqPathology.getReqpathologyid());
		List list = pimsPathologyRequisitionManager.searchViews(reqPathology.getRequisitionid(),"1");//送检材料
		VelocityContext context = getVelocityContext(reqPathology);
		String rootDir = request.getSession().getServletContext().getRealPath("/pdf");
		String fileName = reqPathology.getRequisitionid()+".html";
		String outputFile = rootDir + File.separator + fileName;
//		StringBuilder logoFileRoot = new StringBuilder(request.getScheme());
//		logoFileRoot.append("://").append(request.getServerName())
//				.append(":").append(request.getServerPort()).append("/pdf/").append(reqPathology.getRequisitionid()+".png");
//		OneBarcodeUtil.generateFile(reqPathology.getRequisitionno(),rootDir + File.separator + reqPathology.getRequisitionid()+".png");
        StringBuilder logoFileRoot = new StringBuilder();
        logoFileRoot.append(Config.getString("img.path","E:\\img\\pdf") + File.separator + reqPathology.getRequisitionid()+".png");
        OneBarcodeUtil.generateFile(reqPathology.getRequisitionno(),logoFileRoot.toString());
		context.put("formname", pathology.getPatcoddingprechar());//报告单抬头

//		context.put("hospitalLogo", getHospitalLogo(request, user.getHospitalId()));//医院logo
        context.put("hospitalLogo", "data:image/png" + ";base64," + getHospitalLogo(request, hosptailId));//医院logo
		context.put("sjcls",getResultMaps(list));//送检材料

//		List list1 = pimsPathologyRequisitionManager.searchViews(reqPathology.getRequisitionid(),"0");//动态字段
//		context.put("dtzds",getResultMaps(list1));//动态字段

        FileInputStream fileInputStream = new FileInputStream(logoFileRoot.toString().replace("/","\\"));
        byte[] buffer = null;
        buffer = new byte[fileInputStream.available()];
        fileInputStream.read(buffer);
        fileInputStream.close();
        context.put("barcode","data:image/png" + ";base64," + new String(org.apache.commons.codec.binary.Base64.encodeBase64(buffer)));//条形码
//		context.put("barcode",logoFileRoot.toString());//条形码

		List<PimsRequisitionMaterial> materlist = pimsRequisitionMaterialManager.getListByReqId(reqPathology.getRequisitionid());//取材部位
		context.put("materlist",getResultMap(materlist));
		VelocityEngine engine = new VelocityEngine();
		engine.setProperty(Velocity.RESOURCE_LOADER, "class");
		engine.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		engine.init();
		Template template = engine.getTemplate(pathology.getPatsecondv(), "UTF-8");
		StringWriter writer = new StringWriter();
		template.merge(context, writer);
		generateHtml(outputFile, writer.toString());

		response.setContentType(super.contentType);

		Map<String, String> map = new HashMap<>();
		map.put("url", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/pdf/" + fileName);

		return map;
	}

	private VelocityContext getVelocityContext(PimsPathologyRequisition sample) {
		VelocityContext context = new VelocityContext();
		context.put("requisitionno", sample.getRequisitionno());//申请单号
		context.put("reqpatientname", sample.getReqpatientname());//姓名
		String sex = "";
		if(sample.getReqpatientsex() == 1){
			sex = "男";
		}else if(sample.getReqpatientsex() == 2){
			sex = "女";
		}else{
			sex = "未知";
		}
		context.put("reqpatientsex", sex);//性别
		String age = sample.getReqpatientage();
		int agetype = sample.getReqpatagetype().intValue();
		if(agetype == 1){
			age +="岁";
		}else if(agetype == 2){
			age +="月";
		}else if(agetype == 4){
			age +="周";
		}else if(agetype == 5){
			age +="日";
		}else if(agetype == 6){
			age +="小时";
		}
		context.put("reqpatientage", age);//年龄
		context.put("reqpatientjg", "");//籍贯
		String reqprevious = "";
		if(sample.getReqprevious() == null){

		}else if(sample.getReqprevious().intValue() == 0){
			reqprevious="未婚";
		}else if(sample.getReqprevious().intValue() == 1){
			reqprevious="已婚";
		}
		context.put("reqprevious", reqprevious);//婚姻
		context.put("reqpreviouszy", "");//职业
		context.put("reqpataddress", sample.getReqpataddress());//联系地址
		context.put("reqsendhospital", sample.getReqsendhospital());//医院
		context.put("reqpattelephone", sample.getReqpattelephone());//联系电话
		context.put("reqdeptname", sample.getReqdeptname());//门诊/住院科别
		context.put("reqwardname", sample.getReqwardname());//病区
		context.put("reqpatientnumber", sample.getReqpatientnumber());//门诊/住院号
		context.put("reqfirstn", sample.getReqfirstn());//床号

		context.put("reqssmc", "");//手术名称
		context.put("reqsecondv", sample.getReqsecondv());//手术医生
		context.put("reqthirdv", sample.getReqthirdv());//联系电话
		String reqfirstd = "";
		if(sample.getReqfirstd() != null){
			reqfirstd = Constants.DF2.format(sample.getReqfirstd())	;
		}
		context.put("reqfirstd", reqfirstd);//手术日期
		String reqplanexectime = "";
		if(sample.getReqplanexectime() != null){
			reqplanexectime = Constants.DF2.format(sample.getReqplanexectime())	;
		}
		context.put("reqplanexectime", reqplanexectime);//送检日期
		context.put("reqremark", sample.getReqremark());//手术所见
		context.put("reqpatdiagnosis", sample.getReqpatdiagnosis());//临床所见
		context.put("reqdoctorname", sample.getReqdoctorname());//送检医生
		context.put("reqsendphone", sample.getReqsendphone());//联系电话

		context.put("reqmenses", sample.getReqmenses()==null?"":Constants.DF2.format(sample.getReqmenses()));//月经初潮
		context.put("reqcycle", sample.getReqcycle());//周期
		context.put("reqcesarean", sample.getReqcesarean());//产史
		context.put("reqlastmenstruation", sample.getReqlastmenstruation()==null?"":Constants.DF2.format(sample.getReqlastmenstruation()));//末次月经
		context.put("reqismenopause", sample.getReqismenopause());//是否绝经
		context.put("reqxray", sample.getReqxray());//X光
		context.put("reqct", sample.getReqct());//CT
		context.put("reqbultrasonic", sample.getReqbultrasonic());//B超
		return context;
	}

	private String getHospitalLogo(HttpServletRequest request, Long hospitalId) throws Exception {
		Hospital hospital = hospitalManager.get(hospitalId);
//		StringBuilder logoFileRoot = new StringBuilder(request.getScheme());
//		logoFileRoot.append("://").append(request.getServerName())
//				.append(":").append(request.getServerPort()).append("/images/hospital/");
//		logoFileRoot.append(hospitalId).append("/").append(hospital.getLogo());
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

	@Autowired
	private PimsSysPathologyManager pimsSysPathology;
	/**
	 * 查询病种是否需要取材
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getpathologyinfo*", method = RequestMethod.GET)
	public void getPathologyInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String code = request.getParameter("id");
		PimsSysPathology pathology = pimsSysPathology.getSysPathologyById(Long.parseLong(code));
		JSONObject pathMap = getJSONObject(pathology);
		PrintwriterUtil.print(response, pathMap.toString());
	}
}
