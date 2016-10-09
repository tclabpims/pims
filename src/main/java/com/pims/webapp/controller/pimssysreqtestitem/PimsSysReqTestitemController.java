package com.pims.webapp.controller.pimssysreqtestitem;


import com.pims.model.PimsBaseModel;
import com.pims.model.PimsSysReqTestitem;
import com.pims.service.pimssysreqtestitem.PimsSysReqTestitemManager;
import com.pims.webapp.controller.PIMSBaseController;
import com.smart.webapp.util.DataResponse;
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
	public DataResponse getTestitemInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		DataResponse dataResponse = new DataResponse();
		PimsBaseModel ppr = new PimsBaseModel(request);
		List<PimsSysReqTestitem> list = pimsSysReqTestitemManager.getTestitemInfo();
		if(list == null || list.size() == 0) {
			return null;
		}
		dataResponse.setRecords(list.size());
		dataResponse.setRows(getResultMap(list));
		response.setContentType("text/html; charset=UTF-8");
		return dataResponse;
	}
}
