package com.pims.webapp.controller.his;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.pims.model.PimsBaseModel;
import com.pims.model.PimsPathologyRequisition;
import com.pims.model.PimsRequisitionMaterial;
import com.pims.service.his.PimsPathologyRequisitionManager;
import com.pims.service.his.PimsRequisitionMaterialManager;
import com.pims.webapp.controller.PIMSBaseController;
import com.smart.Constants;
import com.smart.webapp.util.DataResponse;
import com.smart.webapp.util.PrintwriterUtil;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView handleRequest(HttpServletRequest request) throws Exception {
		String today = Constants.DF3.format(new Date());
		ModelAndView view = new ModelAndView();
		view.addObject("receivetime", today);
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
		//获取当前用户选择的病种库
//		User ud = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		String logylibid = ud.getUserBussinessRelate().getPathologyLibId();
		DataResponse dataResponse = new DataResponse();
//		String today = Constants.DF3.format(new Date());
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
		PimsPathologyRequisition ppr = new PimsPathologyRequisition();
		//PimsPathologyRequisition ppr = (PimsPathologyRequisition)setBeanProperty(request,PimsPathologyRequisition.class);
		//User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String requisitionid = request.getParameter("requisitionid");
		String requisitionno = request.getParameter("requisitionno");
		String reqitemnames = request.getParameter("reqitemnames");
		String reqpathologyid = request.getParameter("reqpathologyid");
		String operate = request.getParameter("operate");
		String materiallist = request.getParameter("reqthirdv");
		JSONArray materials = JSON.parseArray(materiallist);
		JSONObject o = new JSONObject();
		if(StringUtils.isEmpty(requisitionid)){
			ppr.setReqcustomercode("1");
			ppr.setRequisitionno("1");
			ppr.setReqsource("1");
			ppr.setReqtype(1);
			ppr.setReqdate(new Date());
			ppr.setReqinspectionid("1");
			ppr.setReqdatechar("1");
			ppr.setReqdeptcode("1");
			ppr.setReqdeptname("1");
			ppr.setReqwardcode("1");
			ppr.setReqwardname("1");
			ppr.setReqdoctorid("1");
			ppr.setReqdoctorname("1");
			ppr.setReqplanexectime(new Date());
			ppr.setReqdigcode("1");
			ppr.setReqchargestatus("1");
			ppr.setReqsendhospital("1");
			ppr.setReqsendphone("1");
			ppr.setReqstate(1);
			ppr.setReqitemids("1");
			ppr.setReqitemnames("1");
			ppr.setReqpatientid("1");
			ppr.setReqinpatientid("1");
			ppr.setReqinpatientno(1);
			ppr.setReqpatienttype(1);
			ppr.setReqpatientnumber("1");
			ppr.setReqpatientname("1");
			ppr.setReqpatientsex(1);
			ppr.setReqpatientage("1");
			ppr.setReqpatagetype(1);
			ppr.setReqpatbirthday(new Date());
			ppr.setReqpatidcard("1");
			ppr.setReqpattelephone("1");
			ppr.setReqpataddress("1");
			ppr.setReqpatdiagnosis("1");
			ppr.setReqismenopause(1);
			ppr.setReqlastmenstruation(new Date());
			ppr.setReqpatcompany("1");
			ppr.setReqsendhisorder(1);
			ppr.setReqsampleid(1);
			ppr.setReqisdeleted(0);
			ppr.setReqprintuser("1");
			ppr.setReqprintusername("1");
			ppr.setReqprinttime(new Date());
			ppr.setReqsendtime(new Date());
			ppr.setReqremark("1");
			ppr.setReqfirstv("1");
			ppr.setReqsecondv("1");
			ppr.setReqthirdv("1");
			ppr.setReqfirstd(new Date());
			ppr.setReqsecondd(new Date());
			ppr.setReqfirstn(1);
			ppr.setReqcreateuser("1");
			ppr.setReqcreatetime(new Date());
			ppr.setRequisitionno(requisitionno);
			ppr.setReqitemnames(reqitemnames);
			ppr.setReqpathologyid(Integer.parseInt(reqpathologyid));
			ppr = pimsPathologyRequisitionManager.save(ppr);
			o.put("message", "样本号为"+ requisitionid + "的标本添加成功！");
			o.put("success", true);
		} else{
			ppr = pimsPathologyRequisitionManager.getBySampleNo(Long.parseLong(requisitionid));
			ppr.setRequisitionno(requisitionno);
			ppr.setReqitemnames(reqitemnames);
			ppr.setReqpathologyid(Integer.parseInt(reqpathologyid));
			pimsPathologyRequisitionManager.save(ppr);
			o.put("message", "样本号为"+ requisitionno + "的标本编辑成功！");
			o.put("success", true);
		}
		for(int i= 0;i<materials.size();i++){
			PimsRequisitionMaterial mater = new PimsRequisitionMaterial();
			Map map = (Map) materials.get(i);
			mater.setReqmmaterialtype((String) map.get("reqmmaterialtype"));
			mater.setReqmsamplingparts((String)map.get("reqmsamplingparts"));
			mater.setReqmcustomercode("1");
			mater.setReqmmaterialname("1");
			mater.setReqmcustomercode("1");
			mater.setReqmcreatetime(new Date());
			mater.setReqmcreateuser("1");
			mater.setRequisitionid(ppr.getRequisitionid());
			pimsRequisitionMaterialManager.save(mater);
		}
		o.put("requisitionid", ppr.getRequisitionid());
		o.put("requisitionno", requisitionno);
		o.put("reqitemnames", reqitemnames);
		o.put("reqpathologyid", reqpathologyid);
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
