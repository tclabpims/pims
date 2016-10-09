package com.pims.webapp.controller.pimssysreqtestitem;


import com.pims.model.PimsBaseModel;
import com.pims.model.PimsSysReqTestitem;
import com.pims.service.pimssysreqtestitem.PimsSysReqTestitemManager;
import com.pims.webapp.controller.PIMSBaseController;
import com.smart.webapp.util.DataResponse;
import com.zju.api.model.Ksdm;
import org.apache.cxf.common.util.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/estitem")
public class PimsSysReqTestitemController extends PIMSBaseController{
	@Autowired
	private PimsSysReqTestitemManager pimsSysReqTestitemManager;
	
	@RequestMapping(value = "/ajax/item*", method = RequestMethod.GET)
	@ResponseBody
	public String getTestitemInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String name = request.getParameter("name");
//		if (StringUtils.isEmpty(name)) {
//			return null;
//		}
		List<PimsSysReqTestitem> list = pimsSysReqTestitemManager.getTestitemInfo(name);
		JSONArray array = new JSONArray();
		if (list != null) {
			for (PimsSysReqTestitem s : list) {
				JSONObject o = new JSONObject();
				o.put("id", s.getTestitemid());
				o.put("name", s.getTeschinesename());
				array.put(o);
			}
		}
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().write(array.toString());
		return null;
	}
}
