package com.pims.webapp.controller.pimssyspathology;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pims.model.PimsPathologyOrder;
import com.pims.model.PimsPathologyOrderCheck;
import com.pims.model.PimsPathologyOrderChild;
import com.pims.model.PimsSysReqTestitem;
import com.pims.service.pimspathologysample.PimsPathologySlideManager;
import com.pims.service.pimssyspathology.PimsPathologyOrderCheckManager;
import com.pims.service.pimssyspathology.PimsPathologyOrderChildManager;
import com.pims.service.pimssyspathology.PimsPathologyOrderManager;
import com.pims.service.pimssysreqtestitem.PimsSysReqTestitemManager;
import com.pims.webapp.controller.GridQuery;
import com.pims.webapp.controller.PIMSBaseController;
import com.pims.webapp.controller.WebControllerUtil;
import com.smart.Constants;
import com.smart.model.user.User;
import com.smart.webapp.util.DataResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by 909436637@qq.com on 2016/11/4.
 * Description:
 */
@Controller
@RequestMapping(value = "/order")
public class PimsPathologyOrderController extends PIMSBaseController {

    @Autowired
    private PimsPathologyOrderManager pimsPathologyOrderManager;

    @Autowired
    private PimsPathologyOrderChildManager pimsPathologyOrderChildManager;

    @Autowired
    private PimsPathologyOrderCheckManager pimsPathologyOrderCheckManager;

    @Autowired
    private PimsPathologySlideManager pimsPathologySlideManager;

    @Autowired
    private PimsSysReqTestitemManager pimsSysReqTestitemManager;

    @RequestMapping(value = "/updateorderstate", method = RequestMethod.GET)
    @ResponseBody
    public void updateOrderState(HttpServletRequest request, HttpServletResponse response) throws Exception  {
        long orderId = Long.valueOf(request.getParameter("orderId"));
        long orderState = Long.valueOf(request.getParameter("orderState"));
        User user = WebControllerUtil.getAuthUser();
        pimsPathologyOrderManager.updateOrderState(orderId, orderState, user);
    }

    @RequestMapping(value = "/orderchildandcheckitem", method = RequestMethod.GET)
    @ResponseBody
    public DataResponse getOrderChildAndCheckItem(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DataResponse dr = new DataResponse();
        long orderId = Long.valueOf(request.getParameter("orderId"));
        PimsPathologyOrder order = pimsPathologyOrderManager.get(orderId);
        PimsPathologyOrderChild child = pimsPathologyOrderChildManager.getChildByOrderId(orderId);
        PimsSysReqTestitem testitem = pimsSysReqTestitemManager.get(child.getTestItemId());
        child.setTestItemChName(testitem.getTeschinesename());
        //技术检查项
        List<PimsPathologyOrderCheck> orderChecks = pimsPathologyOrderCheckManager.getOrderCheckByOrderId(orderId);

        //取医嘱项目的计费信息
        List<Map<String, Object>> orderChildChargeItem = pimsPathologyOrderChildManager.getOrderChildChargeItem(child.getTestItemId(), order.getOrdcustomercode());

        JSONArray childChargeJson = new JSONArray();

        if(orderChildChargeItem.size() > 0) {
            for(Map<String, Object> map : orderChildChargeItem) {
                JSONObject jsonObject = new JSONObject(map);
                childChargeJson.add(jsonObject);
            }
        }

        //取检验项目的计费信息
        String checkChargeJson = "{}";
        Set<Long> checkItemId = new HashSet<>();
        if(orderChecks.size() > 0) {
            for(PimsPathologyOrderCheck ck : orderChecks) {
                checkItemId.add(Long.valueOf(ck.getCheorderitemid()));
            }
        }

        if(checkItemId.size() > 0) {
            checkChargeJson = pimsPathologyOrderCheckManager.calCheckItemCharge(checkItemId, order.getOrdcustomercode());
        }

        Map<String, Object> userData = new HashMap<>();
        userData.put("checkChargeJson", checkChargeJson);
        userData.put("childChargeJson", childChargeJson);
        userData.put("orderChild", JSONObject.toJSON(child).toString());
        dr.setUserdata(userData);
        dr.setRows(getResultMap(orderChecks));
        return dr;
    }

    @RequestMapping(value = "/getorders", method = RequestMethod.GET)
    @ResponseBody
    public DataResponse getOrders(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DataResponse dr = new DataResponse();
        GridQuery gridQuery = new GridQuery(request);
        String specialCheck = request.getParameter("specialCheck");
        String pathologyCode = request.getParameter("pathologyCode");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String patientName = request.getParameter("patientName");
        String orderState = request.getParameter("orderState");
        List result = pimsPathologyOrderManager.getOrders(gridQuery, specialCheck, pathologyCode, startDate, endDate, patientName, orderState);
        Integer total = pimsPathologyOrderManager.countOrders(specialCheck, pathologyCode, startDate, endDate, patientName, orderState);
        dr.setRecords(total);
        dr.setPage(gridQuery.getPage());
        dr.setTotal(getTotalPage(total, gridQuery.getRow(), gridQuery.getPage()));
        dr.setRows(result);
        response.setContentType(contentType);
        return dr;
    }

    @RequestMapping(value = "/technologist", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView technologist(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView();
    }

    @RequestMapping(value = "/save")
    @ResponseBody
    public void save(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PimsPathologyOrder pathologyOrder = new PimsPathologyOrder();

        String orderCode = request.getParameter("orderCode");//医嘱号
        long sampleId = Long.valueOf(request.getParameter("sampleId"));//标本号
        long customerId = Long.valueOf(request.getParameter("customerId"));//客户号
        String pathologyCode = request.getParameter("pathologyCode");//病理号
        String reqDoctor = request.getParameter("reqDoctor");//申请医生
        String reqDoctorId = request.getParameter("reqDoctorId");//申请医生ID
        String reqDate = request.getParameter("reqDate");//申请日期
        //int whitePieceNo = Integer.valueOf(request.getParameter("whitePieceNo"));//白片数量
        String paraffinCode = request.getParameter("paraffinCode");//蜡块编号
        Integer inventory = Integer.valueOf(request.getParameter("inventory"));//切白片数
        long paraffinId = Long.valueOf(request.getParameter("paraffinId"));//蜡块ID
        long testItemId = Long.valueOf(request.getParameter("testItemId"));//医嘱申请的开单项目ID
        String items = request.getParameter("items");//检查项目

        JSONArray array = JSONArray.parseArray(items);

        pathologyOrder.setOrdsampleid(sampleId);
        pathologyOrder.setOrdpathologycode(pathologyCode);
        pathologyOrder.setOrdcustomercode(customerId);
        pathologyOrder.setOrdcreatetime(Constants.DF2.parse(reqDate));
        pathologyOrder.setOrdorderuser(reqDoctor);
        pathologyOrder.setOrdorderuserid(reqDoctorId);
        pathologyOrder.setOrdorderstate(0L);//医嘱状态为：申请
        pathologyOrder.setOrdisdelete(0L); //状态正常
        pathologyOrder.setOrdercode(orderCode);

        //保存医嘱基本信息
        pathologyOrder = pimsPathologyOrderManager.save(pathologyOrder);

        //保存医嘱子项信息
        PimsPathologyOrderChild orderChild = saveOrderChild(pathologyOrder, paraffinCode, paraffinId, inventory, testItemId);

        //保存检验项目信息
        int totalItem = saveOrderCheckItem(pathologyOrder, orderChild.getChildorderid(), array, orderChild.getChiordertype());

        //更新白片使用状态
        pimsPathologySlideManager.updateWhitePieceUsedFlag(paraffinCode, sampleId, (long)totalItem);


    }

    private PimsPathologyOrderChild saveOrderChild(PimsPathologyOrder pathologyOrder, String paraffinCode, long paraffinId, int whitePieceNo, long testItemId) {
        PimsPathologyOrderChild orderChild = new PimsPathologyOrderChild();
        //医嘱类型设置为检查项目的ID
        orderChild.setChiordertype(testItemId);
        orderChild.setChiparaffincode(paraffinCode);
        orderChild.setChiparaffinid(paraffinId);
        orderChild.setChicreatetime(new Date());
        orderChild.setTestItemId(testItemId);
        orderChild.setChicreateuser(pathologyOrder.getOrdorderuser());
        orderChild.setChicustomercode(pathologyOrder.getOrdcustomercode());
        orderChild.setChireqtime(pathologyOrder.getOrdcreatetime());
        orderChild.setChirequserid(pathologyOrder.getOrdorderuserid());
        orderChild.setChirequsername(pathologyOrder.getOrdorderuser());
        orderChild.setChisampleid(pathologyOrder.getOrdsampleid());
        orderChild.setChiordercode(pathologyOrder.getOrdercode());
        orderChild.setChiorderid(pathologyOrder.getOrderid());
        orderChild.setChiorderstate(pathologyOrder.getOrdorderstate());
        orderChild.setChiisdelete(pathologyOrder.getOrdisdelete());
        orderChild.setChipathologycode(pathologyOrder.getOrdpathologycode());
        orderChild.setChinullslidenum((long)whitePieceNo);
        orderChild = pimsPathologyOrderChildManager.save(orderChild);
        return orderChild;
    }

    private int saveOrderCheckItem(PimsPathologyOrder pathologyOrder, long orderChildId, JSONArray array, long oderType) {
        int i = 0;
        for(Object obj : array) {
            PimsPathologyOrderCheck checkItem = new PimsPathologyOrderCheck();
            checkItem.setCheitemtype(oderType);
            checkItem.setChechildorderid(orderChildId);
            checkItem.setChechargestate(0L);
            checkItem.setChecreatetime(pathologyOrder.getOrdcreatetime());
            checkItem.setChecreateuser(pathologyOrder.getOrdorderuser());
            checkItem.setChesampleid(pathologyOrder.getOrdsampleid());
            checkItem.setCheorderid(pathologyOrder.getOrderid());
            checkItem.setChepathologycode(pathologyOrder.getOrdpathologycode());
            checkItem.setCustomercode(pathologyOrder.getOrdcustomercode());
            checkItem.setCheorderitemid(((JSONObject)obj).getString("testitemid"));
            checkItem.setChenamech(((JSONObject)obj).getString("teschinesename"));
            checkItem.setChenameen(((JSONObject)obj).getString("tesenglishname"));
            checkItem.setCheischarge(((JSONObject)obj).getLong("tesischarge"));
            pimsPathologyOrderCheckManager.save(checkItem);
            i++;
        }
        return i;
    }

}
