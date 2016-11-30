package com.pims.webapp.controller.his;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pims.model.PimsBaseModel;
import com.pims.model.PimsPathologyRequisition;
import com.pims.model.PimsRequisitionMaterial;
import com.pims.model.PimsSysReqTestitem;
import com.pims.service.QueryHisDataService;
import com.pims.service.his.PimsPathologyRequisitionManager;
import com.pims.service.his.PimsRequisitionMaterialManager;
import com.pims.service.pimssysreqtestitem.PimsSysReqTestitemManager;
import com.pims.webapp.controller.PIMSBaseController;
import com.smart.Constants;
import com.smart.model.user.User;
import com.smart.webapp.util.DataResponse;
import com.smart.webapp.util.PrintwriterUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
		List list = pimsPathologyRequisitionManager.searchViews(Long.parseLong(id));
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
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String materiallist = request.getParameter("material");
		String fieldlist = request.getParameter("arrs");
		JSONArray materials = JSON.parseArray(materiallist);
		JSONArray fields = JSON.parseArray(fieldlist);
		JSONObject o = new JSONObject();
		ppr = pimsPathologyRequisitionManager.insertOrUpdate(materials,ppr,fields);
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
}
