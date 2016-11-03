package com.smart.webapp.controller;

import com.pims.webapp.controller.PIMSBaseController;
import com.pims.webapp.controller.WebControllerUtil;
import com.smart.Constants;
import com.smart.model.user.User;
import com.smart.service.UserManager;
import com.smart.service.lis.HospitalManager;
import com.smart.service.lis.SectionManager;
import com.smart.webapp.util.PrintwriterUtil;
import com.smart.webapp.util.SectionUtil;
import com.zju.api.service.RMIService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Simple class to retrieve a list of users from the database.
 * <p/>
 * <p>
 * <a href="UserController.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
@Controller
@RequestMapping("/users*")
public class UserController extends PIMSBaseController {

	protected Log log = LogFactory.getLog(getClass());

	@Autowired
    private UserManager userManager = null;
    
	@Autowired
	private HospitalManager hospitalManager = null;
	
	@Autowired
	private RMIService rmiService = null;
	
	@Autowired
	private SectionManager sectionManager = null;

	@RequestMapping(method = RequestMethod.GET)
    public ModelAndView handleRequest(@RequestParam(required = false, value = "q") String query) throws Exception {
        Model model = new ExtendedModelMap();
        if(query == null) {
        	model.addAttribute(userManager.getUsers());
        } else {
        	model.addAttribute(Constants.USER_LIST, userManager.search(query));
        }
        return new ModelAndView("admin/userList", model.asMap());
    }

    @RequestMapping(value="/ajax/pimsuserInfo", method = RequestMethod.GET)
    public String getUserInfo(HttpServletRequest request, HttpServletResponse response){
		try {
			User ud = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("username", ud.getUsername());
			jsonObject.put("name",ud.getName());
			jsonObject.put("userId", String.valueOf(ud.getId()));
			jsonObject.put("pathologyLib", ud.getUserBussinessRelate().getPathologyLib());
			jsonObject.put("pathologyLibId", ud.getUserBussinessRelate().getPathologyLibId());
			PrintwriterUtil.print(response,jsonObject.toString());
		} catch (JSONException e) {
			if(log.isErrorEnabled()) {
				log.error("调用UserController.getUserInfo方法发生错误："+e.getCause().getMessage());
			}
			e.printStackTrace();
		}
		return null;
	}

    @RequestMapping(value="/ajax/hospital", method = RequestMethod.GET)
    public String getHospital(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String ispb = request.getParameter("ispb");
    	
    	JSONObject obj = new JSONObject();
    	SectionUtil sectionUtil = SectionUtil.getInstance(rmiService, sectionManager);
    	User user = userManager.getUserByUsername(request.getRemoteUser());
    	String department = user.getDepartment();
    	if(ispb!=null && ispb.equals("1")){
    		department = user.getPbsection();
    	}
    	String labCode = user.getLastLab();
    	Map<String, String> labMap = new HashMap<String, String>();
    	if(department!=null && !department.isEmpty()){
    		if(labCode.isEmpty()) {
				if(department.indexOf(",") > 0) {
					labCode = department.substring(0, department.indexOf(","));
				} else {
					labCode = department;
				}
			}
    		for(String labcode : department.split(",")) {
//    			System.out.println(sectionUtil.getValue(labcode));
    			labMap.put(labcode, sectionUtil.getLabValue(labcode));
    		}
    	}
    	obj.put("username", user.getName());
    	obj.put("hospital", hospitalManager.get(user.getHospitalId()).getName());
    	obj.put("labCode", labCode);
    	obj.put("lab", sectionUtil.getLabValue(labCode));
    	obj.put("labMap", labMap);
    	response.setContentType("name/html; charset=UTF-8");
		response.getWriter().print(obj.toString());
		return null;
    }
}
