package com.pims.webapp.controller.pimssysreqtestitem;


import com.pims.model.PimsBaseModel;
import com.pims.model.PimsSysReqTestitem;
import com.pims.service.pimssysreqtestitem.PimsSysReqTestitemManager;
import com.pims.webapp.controller.GridQuery;
import com.pims.webapp.controller.PIMSBaseController;
import com.pims.webapp.controller.WebControllerUtil;
import com.smart.webapp.util.DataResponse;
import com.smart.webapp.util.PrintwriterUtil;
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

@Controller
@RequestMapping("/estitem")
public class PimsSysReqTestitemController extends PIMSBaseController{
	@Autowired
	private PimsSysReqTestitemManager pimsSysReqTestitemManager;

	@RequestMapping(value = "/testitem", method = RequestMethod.GET)
	public ModelAndView reqTestItem() throws Exception {
		return new ModelAndView();
	}

	@RequestMapping(method = {RequestMethod.POST}, value = "/edit")
	@ResponseBody
	public void saveOrUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PimsSysReqTestitem pimsSysReqTestitem = (PimsSysReqTestitem)setBeanProperty(request, PimsSysReqTestitem.class);
		if(pimsSysReqTestitem.getTestitemid() == 0) {
			pimsSysReqTestitem.setTescreatetime(new Date());
			pimsSysReqTestitem.setTescreateuser(String.valueOf(WebControllerUtil.getAuthUser().getId()));
		} else {
			PimsSysReqTestitem hisPimsSysReqTestitem = pimsSysReqTestitemManager.get(pimsSysReqTestitem.getTestitemid());
			pimsSysReqTestitem.setTescreateuser(hisPimsSysReqTestitem.getTescreateuser());
			pimsSysReqTestitem.setTescreatetime(hisPimsSysReqTestitem.getTescreatetime());
		}
		pimsSysReqTestitemManager.save(pimsSysReqTestitem);
	}

	@RequestMapping(method = {RequestMethod.GET}, value = "/query")
	@ResponseBody
	public DataResponse query(HttpServletRequest request, HttpServletResponse response) throws Exception {
		DataResponse dr = new DataResponse();
		GridQuery gridQuery = new GridQuery(request);
		List<PimsSysReqTestitem> result = pimsSysReqTestitemManager.getReqTestitemList(gridQuery);
		Integer total = pimsSysReqTestitemManager.countReqTestitem(gridQuery.getQuery());
		dr.setRecords(total);
		dr.setPage(gridQuery.getPage());
		dr.setTotal(getTotalPage(total, gridQuery.getRow(), gridQuery.getPage()));
		dr.setRows(getResultMap(result));
		response.setContentType(contentType);
		return dr;
	}

	@RequestMapping(method = {RequestMethod.POST}, value = "/remove")
	public void remove(HttpServletRequest request, HttpServletResponse response) throws Exception {
		pimsSysReqTestitemManager.remove(Long.valueOf(request.getParameter("testitemid")));
	}

	@RequestMapping(method = {RequestMethod.GET}, value = "/data")
	@ResponseBody
	public void getReqMaterialById(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PimsSysReqTestitem testitem = pimsSysReqTestitemManager.get(Long.valueOf(request.getParameter("testitemid")));
		PrintwriterUtil.print(response, getJSONObject(testitem).toString());
	}

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
