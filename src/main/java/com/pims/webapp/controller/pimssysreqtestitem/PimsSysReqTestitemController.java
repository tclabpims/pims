package com.pims.webapp.controller.pimssysreqtestitem;


import com.pims.model.PimsSysReqTestitem;
import com.pims.service.pimssysreqtestitem.PimsSysReqTestitemManager;
import com.pims.webapp.controller.GridQuery;
import com.pims.webapp.controller.PIMSBaseController;
import com.pims.webapp.controller.WebControllerUtil;
import com.smart.Constants;
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
@RequestMapping("/estitem")
public class PimsSysReqTestitemController extends PIMSBaseController{
	@Autowired
	private PimsSysReqTestitemManager pimsSysReqTestitemManager;

	@RequestMapping(value = "/testitem", method = RequestMethod.GET)
	public ModelAndView reqTestItem() throws Exception {
		return new ModelAndView();
	}

	@RequestMapping(method = {RequestMethod.GET}, value = "/allorderitem")
	@ResponseBody
	public void allValidOrderItem(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<PimsSysReqTestitem> lis = pimsSysReqTestitemManager.allValidOrderItem();
		printResult(lis, response);
	}

	@RequestMapping(method = {RequestMethod.GET}, value = "/orderitem")
	@ResponseBody
	public void orderTreatmentItem(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long patIsSampling = Long.valueOf(request.getParameter("patIsSampling"));
		Long specialCheck = Long.valueOf(request.getParameter("specialCheck"));
		Long pathologyId = Long.valueOf(request.getParameter("pathologyId"));
		List<PimsSysReqTestitem> lis = pimsSysReqTestitemManager.orderTreatmentItem(pathologyId, specialCheck, patIsSampling);
		printResult(lis, response);
	}

	private void printResult(List<PimsSysReqTestitem> lis, HttpServletResponse response) {
		com.alibaba.fastjson.JSONArray result = new com.alibaba.fastjson.JSONArray();
		if(lis.size() > 0) {
			for(PimsSysReqTestitem item : lis) {
				com.alibaba.fastjson.JSONObject obj = new  com.alibaba.fastjson.JSONObject();
				obj.put("testitemid", item.getTestitemid());
				obj.put("teschinesename", item.getTeschinesename());
				obj.put("tesenglishname", item.getTesenglishname());
				obj.put("tesischarge", item.getTesischarge());
				result.add(obj);
			}
		}
		response.setContentType(contentType);
		PrintwriterUtil.print(response, result.toString());
	}

	@RequestMapping(method = {RequestMethod.GET}, value = "/querytestitem")
	@ResponseBody
	public void queryTestItem(HttpServletRequest request, HttpServletResponse response) {
		String query = request.getParameter("query");//项目名称
		Map<String, Object> param = new HashMap<>();
//		param.put("name", query);
		param.put("name1", query);
		param.put("tesitemtype",request.getParameter("tesitemtype"));//项目类型
		param.put("pathologyid",request.getParameter("pathologyid"));//病种ID
		param.put("isCharge", request.getParameter("isCharge"));
		param.put("filter", request.getParameter("filter"));
		List<PimsSysReqTestitem> lis = pimsSysReqTestitemManager.getTestitemInfo(param);
		printResult(lis, response);
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
		String sid = request.getParameter("pathologyId");
		String tesitemtype = request.getParameter("tesitemtype");//项目类型

		Long pathologyId = null;
		if(sid != null && !"".equals(sid)) pathologyId = Long.valueOf(sid);
		List<PimsSysReqTestitem> result = pimsSysReqTestitemManager.getReqTestitemList(gridQuery, pathologyId,tesitemtype);
		Integer total = pimsSysReqTestitemManager.countReqTestitem(gridQuery.getQuery(), pathologyId,tesitemtype);
		dr.setRecords(total);
		dr.setPage(gridQuery.getPage());
		dr.setTotal(getTotalPage(total, gridQuery.getRow(), gridQuery.getPage()));
		dr.setRows(getResultMap(result));
		response.setContentType(contentType);
		return dr;
	}

	@RequestMapping(method = {RequestMethod.GET}, value = "/all")
	@ResponseBody
	public DataResponse allTestItem(HttpServletRequest request, HttpServletResponse response) throws Exception {
		DataResponse dr = new DataResponse();
		List<PimsSysReqTestitem> result = pimsSysReqTestitemManager.allTestItem();
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
	public String getTestitemInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map map = new HashMap();
		String name = request.getParameter("name")==null?"":request.getParameter("name");
		String tesitemtype = request.getParameter("tesitemtype")==null?"":request.getParameter("tesitemtype");
		map.put("name",name);
		map.put("tesitemtype",tesitemtype);
		List<PimsSysReqTestitem> list = pimsSysReqTestitemManager.getTestitemInfo(map);
		JSONArray array = new JSONArray();
		if (list != null) {
			for (PimsSysReqTestitem s : list) {
				JSONObject o = new JSONObject();
				o.put("id", s.getTestitemid());
				o.put("name", s.getTeschinesename());
				o.put("tespathologyid",s.getTespathologyid());
				array.put(o);
			}
		}
		//response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(array.toString());
		return null;
	}
}
