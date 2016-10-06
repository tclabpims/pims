package com.pims.webapp.controller.his;


import com.pims.model.PimsPathologyRequisition;
import com.pims.service.his.PimsPathologyRequisitionManager;
import com.smart.Constants;
import com.smart.model.user.User;
import com.smart.webapp.util.DataResponse;
import com.smart.webapp.util.SampleUtil;
import com.smart.webapp.util.SectionUtil;
import com.smart.webapp.util.UserUtil;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.sql.*;
import java.util.*;
import java.util.Date;

@Controller
@RequestMapping("/pimspathology")
public class PimsPathologyRequisitionController {
	@Autowired
	private PimsPathologyRequisitionManager pimsPathologyRequisitionManager = null;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView handleRequest(HttpServletRequest request) throws Exception {
		//User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String today = Constants.DF3.format(new Date());
		ModelAndView view = new ModelAndView();
		//view.addObject("receivetime", Constants.SDF.format(new Date()));
		view.addObject("receivetime", today);
		return view;
	}

	@RequestMapping(value = "/get*", method = RequestMethod.GET)
	public String getsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String code = request.getParameter("id");
		JSONObject pathMap = new JSONObject();
		PimsPathologyRequisition pathology = new PimsPathologyRequisition();
		pathology = pimsPathologyRequisitionManager.get(Long.parseLong(code));
		if(pathology == null) {
			return null;
		}
		pathMap.put("requisitionid", pathology.getRequisitionid());
		pathMap.put("reqcustomercode", pathology.getReqcustomercode());
		pathMap.put("reqpathologyid", pathology.getReqcustomercode());
		pathMap.put("requisitionno", pathology.getRequisitionno());
		pathMap.put("reqsource", pathology.getReqsource());
		pathMap.put("reqtype", pathology.getReqtype());
		pathMap.put("reqdate", pathology.getReqdate());
		pathMap.put("reqinspectionid", pathology.getReqinspectionid());
		pathMap.put("reqdatechar", pathology.getReqdatechar());
		pathMap.put("reqdeptcode", pathology.getReqdeptcode());
		pathMap.put("reqdeptname", pathology.getReqdeptname());
		pathMap.put("reqwardcode", pathology.getReqwardcode());
		pathMap.put("reqwardname", pathology.getReqwardname());
		pathMap.put("reqdoctorid", pathology.getReqdoctorid());
		pathMap.put("reqdoctorname", pathology.getReqdoctorname());
		pathMap.put("reqplanexectime", pathology.getReqplanexectime());
		pathMap.put("reqdigcode", pathology.getReqdigcode());
		pathMap.put("reqchargestatus", pathology.getReqchargestatus());
		pathMap.put("reqsendhospital", pathology.getReqsendhospital());
		pathMap.put("reqsendphone", pathology.getReqsendphone());
		pathMap.put("reqstate", pathology.getReqstate());
		pathMap.put("reqitemids", pathology.getReqitemids());
		pathMap.put("reqitemnames", pathology.getReqitemnames());
		pathMap.put("reqpatientid", pathology.getReqpatientid());
		pathMap.put("reqinpatientid", pathology.getReqinpatientid());
		pathMap.put("reqinpatientno", pathology.getReqinpatientno());
		pathMap.put("reqpatienttype", pathology.getReqpatienttype());
		pathMap.put("reqpatientnumber", pathology.getReqpatientnumber());
		pathMap.put("reqpatientname", pathology.getReqpatientname());
		pathMap.put("reqpatientsex", pathology.getReqpatientsex());
		pathMap.put("reqpatientage", pathology.getReqpatientage());
		pathMap.put("reqpatagetype", pathology.getReqpatagetype());
		pathMap.put("reqpatbirthday", pathology.getReqpatbirthday());
		pathMap.put("reqpatidcard", pathology.getReqpatidcard());
		pathMap.put("reqpattelephone", pathology.getReqpattelephone());
		pathMap.put("reqpataddress", pathology.getReqpataddress());
		pathMap.put("reqpatdiagnosis", pathology.getReqpatdiagnosis());
		pathMap.put("reqismenopause", pathology.getReqismenopause());
		pathMap.put("reqlastmenstruation", pathology.getReqlastmenstruation());
		pathMap.put("reqpatcompany", pathology.getReqpatcompany());
		pathMap.put("reqsendhisorder", pathology.getReqsendhisorder());
		pathMap.put("reqsampleid", pathology.getReqsampleid());
		pathMap.put("reqisdeleted", pathology.getReqisdeleted());
		pathMap.put("reqprintuser", pathology.getReqprintuser());
		pathMap.put("reqprintusername", pathology.getReqprintusername());
		pathMap.put("reqprinttime", pathology.getReqprinttime());
		pathMap.put("reqsendtime", pathology.getReqsendtime());
		pathMap.put("reqremark", pathology.getReqremark());
		pathMap.put("reqfirstv", pathology.getReqfirstv());
		pathMap.put("reqsecondv", pathology.getReqsecondv());
		pathMap.put("reqthirdv", pathology.getReqthirdv());
		pathMap.put("reqfirstd", pathology.getReqfirstd());
		pathMap.put("reqsecondd", pathology.getReqsecondd());
		pathMap.put("reqfirstn", pathology.getReqfirstn());
		pathMap.put("reqcreateuser", pathology.getReqcreateuser());
		pathMap.put("reqcreatetime", pathology.getReqcreatetime());
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().write(pathMap.toString());
		return null;
	}
	
	@RequestMapping(value = "/ajax/pathology*", method = RequestMethod.GET)
	@ResponseBody
	public DataResponse getRequisitionInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//获取当前用户选择的病种库
		User ud = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String logylibid = ud.getUserBussinessRelate().getPathologyLibId();
		Map map = new HashMap();
		DataResponse dataResponse = new DataResponse();
		String today = Constants.DF3.format(new Date());
		//String lab = userManager.getUserByUsername(request.getRemoteUser()).getLastLab();
		//List<Sample> list = sampleManager.getReceiveList(today, lab);
		String req_code = request.getParameter("req_code");
		String patient_name = request.getParameter("patient_name");
		String send_hosptail = request.getParameter("send_hosptail");
		String req_bf_time = request.getParameter("req_bf_time")==null?today:request.getParameter("req_bf_time");
		String req_af_time = request.getParameter("req_af_time")==null?today:request.getParameter("req_af_time");
		String send_dept = request.getParameter("send_dept");
		String send_doctor = request.getParameter("send_doctor");
		String req_sts = request.getParameter("req_sts");
		map.put("req_code",req_code);
		map.put("patient_name",patient_name);
		map.put("send_hosptail",send_hosptail);
		map.put("req_bf_time",req_bf_time);
		map.put("req_af_time",req_af_time);
		map.put("send_dept",send_dept);
		map.put("send_doctor",send_doctor);
		map.put("req_sts",req_sts);
		List<PimsPathologyRequisition> list = pimsPathologyRequisitionManager.getRequisitionInfo(map);
		if(list == null || list.size() == 0) {
			return null;
		}
		List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
		dataResponse.setRecords(list.size());
		for(PimsPathologyRequisition pathology : list) {
			Map<String, Object> pathMap = new HashMap<String, Object>();
			pathMap.put("requisitionid", pathology.getRequisitionid());
			pathMap.put("reqcustomercode", pathology.getReqcustomercode());
			pathMap.put("reqpathologyid", pathology.getReqcustomercode());
			pathMap.put("requisitionno", pathology.getRequisitionno());
			pathMap.put("reqsource", pathology.getReqsource());
			pathMap.put("reqtype", pathology.getReqtype());
			pathMap.put("reqdate", pathology.getReqdate());
			pathMap.put("reqinspectionid", pathology.getReqinspectionid());
			pathMap.put("reqdatechar", pathology.getReqdatechar());
			pathMap.put("reqdeptcode", pathology.getReqdeptcode());
			pathMap.put("reqdeptname", pathology.getReqdeptname());
			pathMap.put("reqwardcode", pathology.getReqwardcode());
			pathMap.put("reqwardname", pathology.getReqwardname());
			pathMap.put("reqdoctorid", pathology.getReqdoctorid());
			pathMap.put("reqdoctorname", pathology.getReqdoctorname());
			pathMap.put("reqplanexectime", pathology.getReqplanexectime());
			pathMap.put("reqdigcode", pathology.getReqdigcode());
			pathMap.put("reqchargestatus", pathology.getReqchargestatus());
			pathMap.put("reqsendhospital", pathology.getReqsendhospital());
			pathMap.put("reqsendphone", pathology.getReqsendphone());
			pathMap.put("reqstate", pathology.getReqstate());
			pathMap.put("reqitemids", pathology.getReqitemids());
			pathMap.put("reqitemnames", pathology.getReqitemnames());
			pathMap.put("reqpatientid", pathology.getReqpatientid());
			pathMap.put("reqinpatientid", pathology.getReqinpatientid());
			pathMap.put("reqinpatientno", pathology.getReqinpatientno());
			pathMap.put("reqpatienttype", pathology.getReqpatienttype());
			pathMap.put("reqpatientnumber", pathology.getReqpatientnumber());
			pathMap.put("reqpatientname", pathology.getReqpatientname());
			pathMap.put("reqpatientsex", pathology.getReqpatientsex());
			pathMap.put("reqpatientage", pathology.getReqpatientage());
			pathMap.put("reqpatagetype", pathology.getReqpatagetype());
			pathMap.put("reqpatbirthday", pathology.getReqpatbirthday());
			pathMap.put("reqpatidcard", pathology.getReqpatidcard());
			pathMap.put("reqpattelephone", pathology.getReqpattelephone());
			pathMap.put("reqpataddress", pathology.getReqpataddress());
			pathMap.put("reqpatdiagnosis", pathology.getReqpatdiagnosis());
			pathMap.put("reqismenopause", pathology.getReqismenopause());
			pathMap.put("reqlastmenstruation", pathology.getReqlastmenstruation());
			pathMap.put("reqpatcompany", pathology.getReqpatcompany());
			pathMap.put("reqsendhisorder", pathology.getReqsendhisorder());
			pathMap.put("reqsampleid", pathology.getReqsampleid());
			pathMap.put("reqisdeleted", pathology.getReqisdeleted());
			pathMap.put("reqprintuser", pathology.getReqprintuser());
			pathMap.put("reqprintusername", pathology.getReqprintusername());
			pathMap.put("reqprinttime", pathology.getReqprinttime());
			pathMap.put("reqsendtime", pathology.getReqsendtime());
			pathMap.put("reqremark", pathology.getReqremark());
			pathMap.put("reqfirstv", pathology.getReqfirstv());
			pathMap.put("reqsecondv", pathology.getReqsecondv());
			pathMap.put("reqthirdv", pathology.getReqthirdv());
			pathMap.put("reqfirstd", pathology.getReqfirstd());
			pathMap.put("reqsecondd", pathology.getReqsecondd());
			pathMap.put("reqfirstn", pathology.getReqfirstn());
			pathMap.put("reqcreateuser", pathology.getReqcreateuser());
			pathMap.put("reqcreatetime", pathology.getReqcreatetime());
			dataRows.add(pathMap);
		}
		dataResponse.setRows(dataRows);
		response.setContentType("text/html; charset=UTF-8");
		return dataResponse;
	}

	@RequestMapping(value = "/editSample*", method = RequestMethod.POST)
	public String editSample(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PimsPathologyRequisition ppr = null;
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String requisitionid = request.getParameter("requisitionid");
		String requisitionno = request.getParameter("requisitionno");
		String reqitemnames = request.getParameter("reqitemnames");
		String reqpathologyid = request.getParameter("reqpathologyid");
		String operate = request.getParameter("operate");
		JSONObject o = new JSONObject();
		if(operate.equals("delete")) {
			pimsPathologyRequisitionManager.delete(Long.parseLong(requisitionid));
			o.put("message", "样本号为"+ requisitionno + "的标本删除成功！");
			o.put("success", true);
		} else {
			if(operate.equals("add")) {
				if(StringUtils.isEmpty(requisitionid)){
					ppr = new PimsPathologyRequisition();
					ppr.setRequisitionid(pimsPathologyRequisitionManager.getMaxId().intValue());
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
					ppr.setReqisdeleted(1);
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
					pimsPathologyRequisitionManager.save(ppr);
					o.put("message", "样本号为"+ requisitionid + "的标本添加成功！");
					o.put("success", true);
				} else {
					o.put("message", "样本号为"+ requisitionid + "的标本已存在，不能重复添加！");
					o.put("success", false);
				}

			}else{
				ppr = pimsPathologyRequisitionManager.getBySampleNo(Long.parseLong(requisitionid));
				ppr.setRequisitionno(requisitionno);
				ppr.setReqitemnames(reqitemnames);
				ppr.setReqpathologyid(Integer.parseInt(reqpathologyid));
				pimsPathologyRequisitionManager.save(ppr);
				o.put("message", "样本号为"+ requisitionno + "的标本编辑成功！");
				o.put("success", true);
			}
		}
		o.put("requisitionid", ppr.getRequisitionid());
		o.put("requisitionno", requisitionno);
		o.put("reqitemnames", reqitemnames);
		o.put("reqpathologyid", reqpathologyid);
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().write(o.toString());
		return null;
	}
}
