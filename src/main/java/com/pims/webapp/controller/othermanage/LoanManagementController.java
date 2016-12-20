package com.pims.webapp.controller.othermanage;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.pims.model.PimsBaseModel;
import com.pims.model.PimsSlideLoan;
import com.pims.model.PimsSlideRecord;
import com.pims.service.othermanagement.PimsSlideLoanManager;
import com.pims.service.othermanagement.PimsSlideLoanRecordManager;
import com.pims.webapp.controller.PIMSBaseController;
import com.pims.webapp.controller.WebControllerUtil;
import com.smart.Constants;
import com.smart.lisservice.WebService;
import com.smart.model.user.User;
import com.smart.webapp.util.DataResponse;
import com.smart.webapp.util.PrintwriterUtil;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/othermanage/loanmanagement")
public class LoanManagementController extends PIMSBaseController {
    @Autowired
    private PimsSlideLoanManager pimsSlideLoanManager;
    @Autowired
    private PimsSlideLoanRecordManager pimsSlideLoanRecordManager;
        /**
         * 渲染视图
         * @param request
         * @return
         * @throws Exception
         */
        @RequestMapping(method = RequestMethod.GET)
        public ModelAndView handleRequest(HttpServletRequest request) throws Exception {
//        Calendar c = Calendar.getInstance();
//        c.add(Calendar.DATE, - 7);
//        Date monday = c.getTime();
//        String sevenDay = Constants.DF2.format(monday);
//        String today = Constants.DF2.format(new Date());
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String logylibid = user.getUserBussinessRelate().getPathologyLibId();//病种库
//        ModelAndView view = new ModelAndView();
//        view.addObject("logyid",logylibid);//当前用户选择的病例库
//        view.addObject("sevenday", sevenDay);//7天前
//        view.addObject("receivetime", today);//当前时间
//        view.addObject("send_hosptail",user.getHospitalId());//账号所属医院
//        view.addObject("local_userid",user.getId());//用户id
//        view.addObject("local_username",user.getName());//用户姓名
//        return view;
            return getmodelView(request);
        }



        /**
         * 获取标本列表
         * @param request
         * @param response
         * @return
         * @throws Exception
         */
        @RequestMapping(value = "/ajax/slide*", method = RequestMethod.GET)
        @ResponseBody
        public DataResponse getRequisitionInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
            DataResponse dataResponse = new DataResponse();
            PimsBaseModel ppr = new PimsBaseModel(request);
            List<PimsSlideLoan> list = pimsSlideLoanManager.getLoanList(ppr);
            int num = pimsSlideLoanManager.getReqListNum(ppr);
            if(list == null || list.size() == 0 ) {
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
         * 查看单据是否可修改
         * @param request
         * @param response
         * @return
         * @throws Exception
         */
        @RequestMapping(value = "/canchange*", method = RequestMethod.GET)
        public void canChange(HttpServletRequest request, HttpServletResponse response) throws Exception {
            String code = request.getParameter("id");
            String sts = request.getParameter("sts");
            JSONObject o = new JSONObject();
            if(pimsSlideLoanManager.canChange(Long.parseLong(code),sts)){
                o.put("success", true);
            }else{
                o.put("success", false);
                o.put("message","无法借阅该玻片！");
            }
            PrintwriterUtil.print(response, o.toString());
        }


        /**
         * 获取收费列表
         * @param request
         * @param response
         * @return
         * @throws Exception
         */
        @RequestMapping(value = "/ajax/rec*", method = RequestMethod.GET)
        @ResponseBody
        public DataResponse getFeeList(HttpServletRequest request, HttpServletResponse response) throws Exception {
            DataResponse dataResponse = new DataResponse();
            PimsSlideRecord rec = (PimsSlideRecord) setBeanProperty(request,PimsSlideRecord.class);
            if(rec == null){
                return  null;
            }
            List<PimsSlideRecord> list = pimsSlideLoanRecordManager.getRecordList(rec);
            dataResponse.setRows(getResultMap(list));
            response.setContentType("text/html; charset=UTF-8");
            return dataResponse;
        }


        /**
         *
         * 借阅
         *
         */

        @RequestMapping(value = "/ajax/loan*", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json; charset=utf-8")
        @ResponseBody
        public void loan(HttpServletRequest request, HttpServletResponse response) throws Exception {
            Map map = new HashedMap();
            map.put("sliid",request.getParameter("sliid"));
            map.put("slicustomerid",request.getParameter("slicustomerid"));
            map.put("customer_name",request.getParameter("customer_name"));
            map.put("sli_in_time",new java.sql.Date(Constants.DF2.parse(request.getParameter("sliintime")).getTime()));

            User user = WebControllerUtil.getAuthUser();
            PimsSlideRecord psr = new PimsSlideRecord();
            psr.setSliid((String)map.get("sliid"));
            psr.setSlicustomername((String)map.get("customer_name"));
            psr.setSlimanagername(user.getUsername());
            psr.setSlitime(new Date());
            psr.setSlicustomerid((String)map.get("slicustomerid"));
            psr.setSlicurrent("0");
            pimsSlideLoanRecordManager.save(psr);
            pimsSlideLoanManager.loan(map);
            JSONObject jj = new JSONObject();
            jj.put("message","借阅成功！");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(jj.toString());
        }

//        @RequestMapping(value = "/ajax/loanadd*", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json; charset=utf-8")
//        @ResponseBody
//        public void loanadd(HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//    }

        @RequestMapping(value = "/ajax/return0*", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json; charset=utf-8")
        @ResponseBody
        public void returnSlide0(HttpServletRequest request, HttpServletResponse response) throws Exception {
            PimsSlideRecord ppr = (PimsSlideRecord)setBeanProperty(request,PimsSlideRecord.class);
            User user = WebControllerUtil.getAuthUser();
            ppr.setSliid(ppr.getSliid());
            ppr.setSlitime(new Date());
            ppr.setSlicustomername(ppr.getSlicustomername());
            ppr.setSlimanagername(user.getUsername());
            ppr.setSlicustomerid(ppr.getSlicustomerid());
            ppr.setSlidept(ppr.getSlidept());
            ppr.setSliresult(ppr.getSliresult());
            ppr.setSlicurrent("1");

            pimsSlideLoanRecordManager.save(ppr);
            JSONObject jj = new JSONObject();
            jj.put("message","归还成功！");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(jj.toString());
        }

    @RequestMapping(value = "/ajax/return3*", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json; charset=utf-8")
    @ResponseBody
    public void returnSlide3(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PimsBaseModel ppr = (PimsBaseModel)setBeanProperty(request,PimsBaseModel.class);
        pimsSlideLoanManager.returnSlide3(ppr);
    }

        /**
         * 打印条码
         *
         * @param
         * @return
         */
        @RequestMapping(value = "/printList*", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json; charset=utf-8")
        @ResponseBody
        public String printRequestList(HttpServletRequest request, HttpServletResponse response) {

            List<PimsSlideLoan> lists = new ArrayList<PimsSlideLoan>(); //返回JSON打印信息
            User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            WebService webService = new WebService();
            String loan = request.getParameter("loan");
            List<PimsSlideLoan> loanarray = JSON.parseArray(loan,PimsSlideLoan.class);//申请打印记录
            List<PimsSlideLoan> hasPrintLaborder = new ArrayList<PimsSlideLoan>(); //已打印记录
            JSONObject retObj = new JSONObject();
            retObj.put("printOrders", loanarray);
            retObj.put("noPrintOrders", hasPrintLaborder);
            return JSON.toJSONString(retObj, filter);
        }

        private ValueFilter filter = new ValueFilter() {
            @Override
            public Object process(Object obj, String s, Object v) {
                if (v == null)
                    return "";
                return v;
            }
        };



    }


