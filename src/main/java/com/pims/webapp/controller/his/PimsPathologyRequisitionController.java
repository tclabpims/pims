package com.pims.webapp.controller.his;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.pims.model.PimsBaseModel;
import com.pims.model.PimsPathologyRequisition;
import com.pims.model.PimsRequisitionMaterial;
import com.pims.model.PimsSysReqTestitem;
import com.pims.service.his.PimsPathologyRequisitionManager;
import com.pims.service.his.PimsRequisitionMaterialManager;
import com.pims.service.pimssysreqtestitem.PimsSysReqTestitemManager;
import com.pims.webapp.controller.PIMSBaseController;
import com.smart.Constants;
import com.smart.model.user.User;
import com.smart.webapp.util.DataResponse;
import com.smart.webapp.util.PrintwriterUtil;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/pimspathology")
public class PimsPathologyRequisitionController extends PIMSBaseController{
	@Autowired
	private PimsPathologyRequisitionManager pimsPathologyRequisitionManager;
	@Autowired
	private PimsRequisitionMaterialManager pimsRequisitionMaterialManager;
	@Autowired
	private PimsSysReqTestitemManager pimsSysReqTestitemManager;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView handleRequest(HttpServletRequest request) throws Exception {
		String today = Constants.DF3.format(new Date());
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String logylibid = user.getUserBussinessRelate().getPathologyLibId();//病种库
		List<PimsSysReqTestitem> list = pimsSysReqTestitemManager.getTestitemInfo();//查询申请项目列表
		ModelAndView view = new ModelAndView();
		view.addObject("receivetime", today);//当前时间
		view.addObject("reqcustomerid",user.getHospitalId());//账号所属医院
		view.addObject("reqpathologyid",logylibid);//当前用户选择的病例库
		view.addObject("reqsource",0);//申请单来源
		view.addObject("testList",getResultMap(list));//申请项目列表
		return view;
	}

	@RequestMapping(value = "/get*", method = RequestMethod.GET)
	public void getsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String code = request.getParameter("id");
		PimsPathologyRequisition pathology = pimsPathologyRequisitionManager.getBySampleNo(Long.parseLong(code));
		JSONObject pathMap = getJSONObject(pathology);
		PrintwriterUtil.print(response, pathMap.toString());
	}
	
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

	@RequestMapping(value = "/editSample*", method = RequestMethod.POST)
	public String editSample(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PimsPathologyRequisition ppr = (PimsPathologyRequisition)setBeanProperty(request,PimsPathologyRequisition.class);
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String materiallist = request.getParameter("reqthirdv");
		JSONArray materials = JSON.parseArray(materiallist);
		JSONObject o = new JSONObject();
		if(StringUtils.isEmpty(String.valueOf(ppr.getRequisitionid())) || String.valueOf(ppr.getRequisitionid()).equals("0")){
			ppr.setReqdate(new Date());
			ppr.setReqdatechar(Constants.DF3.format(new Date()));
			ppr.setReqplanexectime(new Date());
			ppr.setReqstate(0);
			ppr.setReqisdeleted(0);
			ppr.setReqpatientid("1");
			ppr.setReqinpatientid("1");
			ppr.setReqthirdv("");
			ppr.setReqcreateuser(String.valueOf(user.getId()));
			ppr.setReqcreatetime(new Date());
			ppr = pimsPathologyRequisitionManager.save(ppr);
			o.put("message", "申请添加成功！");
			o.put("success", true);
		} else{
			PimsPathologyRequisition ppr1 = pimsPathologyRequisitionManager.getBySampleNo((long) ppr.getRequisitionid());
			ppr.setReqdate(ppr1.getReqdate());
			ppr.setReqplanexectime(ppr1.getReqplanexectime());
			ppr.setReqthirdv("");
			pimsPathologyRequisitionManager.save(ppr);
			o.put("message", "申请编辑成功！");
			o.put("success", true);
		}
		for(int i= 0;i<materials.size();i++){
			PimsRequisitionMaterial mater = new PimsRequisitionMaterial();
			Map map = (Map) materials.get(i);
			mater.setReqmmaterialtype((String) map.get("reqmmaterialtype"));
			mater.setReqmsamplingparts((String)map.get("reqmsamplingparts"));
			mater.setReqmcustomercode(ppr.getReqcustomercode());
			mater.setReqmcreatetime(new Date());
			mater.setReqmcreateuser(String.valueOf(user.getId()));
			mater.setRequisitionid(ppr.getRequisitionid());
			pimsRequisitionMaterialManager.save(mater);
		}
		o.put("requisitionid", ppr.getRequisitionid());
		o.put("requisitionno", ppr.getRequisitionno());
		o.put("reqitemnames", ppr.getReqitemnames());
		o.put("reqpathologyid", ppr.getReqpathologyid());
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().write(o.toString());
		return null;
	}

	@RequestMapping(value = "/deleteSample*", method = RequestMethod.POST)
	public void deleteSample(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PimsPathologyRequisition ppr = (PimsPathologyRequisition)setBeanProperty(request,PimsPathologyRequisition.class);
		//User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		JSONObject o = new JSONObject();
		if(StringUtils.isEmpty(String.valueOf(ppr.getRequisitionid()))){
			o.put("message", "查不到该申请单的信息！");
			o.put("success", false);
		}else{
			pimsPathologyRequisitionManager.delete((long) ppr.getRequisitionid());
			pimsRequisitionMaterialManager.delete((long) ppr.getRequisitionid());
			o.put("message", "样本号为"+ ppr.getRequisitionid() + "的标本删除成功！");
			o.put("success", true);
		}
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().write(o.toString());
	}

}
