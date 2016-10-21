package com.pims.webapp.controller.basedata;


import com.pims.model.PimsCommonBaseData;
import com.pims.model.PimsSysReqTestitem;
import com.pims.service.basedata.PimsCommonBaseDataManager;
import com.pims.service.pimssysreqtestitem.PimsSysReqTestitemManager;
import com.pims.webapp.controller.GridQuery;
import com.pims.webapp.controller.PIMSBaseController;
import com.pims.webapp.controller.WebControllerUtil;
import com.smart.webapp.util.DataResponse;
import com.smart.webapp.util.PrintwriterUtil;
import org.codehaus.jettison.json.JSONArray;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/basadata")
public class PimsCommonBaseDataController extends PIMSBaseController{
	@Autowired
	private PimsCommonBaseDataManager pimsCommonBaseDataManager;

	@RequestMapping(value = "/ajax/item*", method = RequestMethod.GET)
	@ResponseBody
	public String getTestitemInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String name = request.getParameter("name")==null?"":request.getParameter("name").toString();
		String bdcustomerid = request.getParameter("bdcustomerid")==null?"":request.getParameter("bdcustomerid").toString();
		String bddatatype = request.getParameter("bddatatype")==null?"":request.getParameter("bddatatype").toString();
		Map map = new HashMap();
		map.put("name",name);
		map.put("bdcustomerid",bdcustomerid);
		map.put("bddatatype",bddatatype);
		List<PimsCommonBaseData> list = pimsCommonBaseDataManager.getDataList(map);
		JSONArray array = new JSONArray();
		if (list != null) {
			for (PimsCommonBaseData s : list) {
				JSONObject o = new JSONObject();
				o.put("id", s.getDataid());
				o.put("name", s.getBddatanamech());
				array.put(o);
			}
		}
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().write(array.toString());
		return null;
	}
}
