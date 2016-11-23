package com.pims.webapp.controller.pimssyspathology;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pims.model.*;
import com.pims.service.pimspathologysample.PimsPathologyFeeManager;
import com.pims.service.pimspathologysample.PimsPathologyParaffinManager;
import com.pims.service.pimspathologysample.PimsPathologyPiecesManager;
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

    @Autowired
    private PimsPathologyParaffinManager pimsPathologyParaffinManager;

    @Autowired
    private PimsPathologyPiecesManager pimsPathologyPiecesManager;

    @Autowired
    private PimsPathologyFeeManager pimsPathologyFeeManager;

    private Set<Long> testItem = new HashSet<>();

    @RequestMapping(value = "/getmaxpieceno", method = RequestMethod.GET)
    @ResponseBody
    public DataResponse getMaxPieceNo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DataResponse dr = new DataResponse();
        long sampleId = Long.valueOf(request.getParameter("sampleId"));
        Integer maxPieceNo = pimsPathologyOrderChildManager.getMaxPieceNo(sampleId);
        Map<String, Object> o = new HashMap<>();
        o.put("maxPieceNo", maxPieceNo);
        dr.setUserdata(o);
        return dr;
    }

    @RequestMapping(value = "/getchildlist", method = RequestMethod.GET)
    @ResponseBody
    public DataResponse getChildList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DataResponse dr = new DataResponse();
        long oderId = Long.valueOf(request.getParameter("orderId"));
        List<PimsPathologyOrderChild> list = pimsPathologyOrderChildManager.getChildList(oderId);
        dr.setRows(getResultMap(list));
        return dr;
    }

    @RequestMapping(value = "/getparaffinfromorder", method = RequestMethod.GET)
    @ResponseBody
    public DataResponse getParaffinFromOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DataResponse dr = new DataResponse();
        long oderId = Long.valueOf(request.getParameter("orderId"));
        List<Map<String, Object>> list = pimsPathologyParaffinManager.getParaffinFromOrder(oderId);
        dr.setRows(list);
        return dr;
    }

    @RequestMapping(value = "/getparaffinmaterial", method = RequestMethod.GET)
    @ResponseBody
    public DataResponse getParaffinMaterial(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DataResponse dr = new DataResponse();
        long paraffinId = Long.valueOf(request.getParameter("paraffinId"));
        List<Map<String, Object>> list = pimsPathologyParaffinManager.getParaffinMaterial(paraffinId);
        dr.setRows(list);
        return dr;
    }

    @RequestMapping(value = "/getrequestedorder", method = RequestMethod.GET)
    @ResponseBody
    public DataResponse getRequestedOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DataResponse dr = new DataResponse();
        long sampleId = Long.valueOf(request.getParameter("sampleId"));
        List<Map<String, Object>> lis = pimsPathologyOrderManager.getSampleOrder(sampleId);
        dr.setRows(lis);
        return dr;
    }


    @RequestMapping(value = "/getcheckitems", method = RequestMethod.GET)
    @ResponseBody
    public DataResponse getCheckItems(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DataResponse dr = new DataResponse();
        long sampleId = Long.valueOf(request.getParameter("sampleId"));
        long testItemId = Long.valueOf(request.getParameter("testItemId"));
        List<Map<String, Object>> lis = pimsPathologyOrderManager.getCheckItems(sampleId, testItemId);
        dr.setRows(lis);
        return dr;
    }

    @RequestMapping(value = "/saveresult", method = RequestMethod.GET)
    @ResponseBody
    public void saveResult(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String items = request.getParameter("items");
        JSONArray array = JSONArray.parseArray(items);
        pimsPathologyOrderCheckManager.saveResult(array);
    }

    @RequestMapping(value = "/updateitemstatus", method = RequestMethod.GET)
    @ResponseBody
    public void updateItemStatus(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String orderType = request.getParameter("orderType");
        String items = request.getParameter("items");
        Long orderId = Long.valueOf(request.getParameter("orderId"));
        String[] itemArray = items.split(",");
        Set<Long> s = new HashSet<>();
        for (String str : itemArray) {
            s.add(Long.valueOf(str));
        }
        if (orderType.equals(Constants.ORDER_TYPE_MYZH) || orderType.equals(Constants.ORDER_TYPE_FZBL)
                || orderType.equals(Constants.ORDER_TYPE_TSRS))
            pimsPathologyOrderCheckManager.updateItemStatus(s);
        if (orderType.equals(Constants.ORDER_TYPE_SHENQIE) || orderType.equals(Constants.ORDER_TYPE_CHONGQIE)) {
            pimsPathologyOrderChildManager.updateChildItemStatus(s);
        }
        saveSlide(orderId);
    }

    private void saveSlide(Long orderId) {
        List<PimsPathologyOrderChild> child = pimsPathologyOrderChildManager.getChildList(orderId);
        User user = WebControllerUtil.getAuthUser();
        for (PimsPathologyOrderChild orderChild : child) {
            PimsPathologySlide slide = pimsPathologySlideManager.getSlideByParaffinId(orderChild.getChiparaffinid());
            int totalSlide = orderChild.getChinullslidenum().intValue();
            //常规片
            int commonSlide = orderChild.getChislidenum().intValue();
            //预留白片
            int obligateSlide = totalSlide - commonSlide;
            int slideNo = slide == null ? 1 : Integer.valueOf(slide.getSlislideno());
            String slideCode = slide == null ? orderChild.getChiparaffincode() + "-0" : slide.getSlislidecode();
            int slideCodeStart = Integer.valueOf(slideCode.substring(slideCode.lastIndexOf("-") + 1));
            setSlide(slideNo, slideCodeStart, 0, commonSlide, orderChild, user.getId());
            setSlide((slideNo + commonSlide), (slideCodeStart + commonSlide), 1, obligateSlide, orderChild, user.getId());
        }

    }

    private void setSlide(int slideNo, int slideCodeStart, int type, int number, PimsPathologyOrderChild orderChild, Long userId) {
        for (int i = 0; i < number; i++) {
            slideNo++;
            slideCodeStart++;
            PimsPathologySlide newSlide = new PimsPathologySlide();
            newSlide.setSlicreatetime(new Date());
            newSlide.setSlicreateuser(String.valueOf(userId));
            newSlide.setSlicustomerid(orderChild.getChicustomercode());
            newSlide.setSlifirstn(orderChild.getChiorderid());
            newSlide.setSliparaffincode(orderChild.getChiparaffincode());
            newSlide.setSlipathologycode(orderChild.getChipathologycode());
            //newSlide.setSliparaffinno(slide.getSliparaffinno());
            newSlide.setSliifprint(0);
            newSlide.setSlisampleid(orderChild.getChisampleid());
            newSlide.setSlislidesource(2L);
            newSlide.setSliuseflag(1L);
            newSlide.setSlitestitemid(orderChild.getTestItemId());
            newSlide.setSlislideno(String.valueOf(slideNo));
            newSlide.setSlislidecode(orderChild.getChiparaffincode() + "-" + String.valueOf(slideCodeStart));
            newSlide.setSlislidetype(type);
            newSlide.setSliparaffinname(orderChild.getChiparaffincode());
            newSlide.setSliparaffinid(orderChild.getChiparaffinid());
            pimsPathologySlideManager.save(newSlide);
        }
    }

    @RequestMapping(value = "/updatecheckitem", method = RequestMethod.GET)
    @ResponseBody
    public void updateCheckItem(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String testItems = request.getParameter("testItems");
        long orderId = Long.valueOf(request.getParameter("orderId"));
        long pathologyId = Long.valueOf(request.getParameter("pathologyId"));
        JSONArray array = JSONArray.parseArray(testItems);
        JSONArray paraffinArray = JSONArray.parseArray(request.getParameter("paraffinItems"));
        List<PimsPathologyOrderCheck> newItems = new ArrayList<>();
        for (Object item : array) {
            JSONObject o = (JSONObject) item;
            boolean newAppend = o.getBooleanValue("newAppend");
            if (newAppend) {
                PimsPathologyOrderCheck pathologyOrderCheck = new PimsPathologyOrderCheck();
                pathologyOrderCheck.setCheorderid(o.getLongValue("orderId"));
                pathologyOrderCheck.setChechildorderid(o.getLongValue("childorderid"));
                pathologyOrderCheck.setCheischarge(o.getLongValue("cheischarge"));
                pathologyOrderCheck.setParaffincode(o.getString("paraffincode"));
                pathologyOrderCheck.setChenameen(o.getString("chenameen"));
                pathologyOrderCheck.setChenamech(o.getString("chenamech"));
                pathologyOrderCheck.setChepathologycode(o.getString("ordPathologyCode"));
                pathologyOrderCheck.setChecreateuser(o.getString("reqDoctor"));
                pathologyOrderCheck.setChecreatetime(new Date());
                pathologyOrderCheck.setCheorderitemid(o.getString("cheorderitemid"));
                pathologyOrderCheck.setChesampleid(o.getLongValue("ordSampleId"));
                pathologyOrderCheck.setCustomercode(o.getLongValue("ordCustomerId"));
                pathologyOrderCheck.setCheitemtype(1L);
                pathologyOrderCheck.setChechargestate(0L);
                pathologyOrderCheck.setFinishStatus(0L);
                newItems.add(pathologyOrderCheck);
                getTestItem().add(Long.valueOf(o.getString("cheorderitemid")));
            }
        }

        if (newItems.size() > 0) {
            for (PimsPathologyOrderCheck oc : newItems) {
                PimsPathologyOrderCheck o = pimsPathologyOrderCheckManager.save(oc);
            }
        }
        PimsPathologyOrder pathologyOrder = pimsPathologyOrderManager.get(orderId);

        pimsPathologyFeeManager.saveFee(pathologyOrder, pathologyId, getTestItem());
        //pimsPathologyOrderCheckManager.removeItems(keepItems, orderId);
        //pimsPathologyOrderChildManager.updateWhitePiece(orderChildId, inventory);

        if(paraffinArray.size() > 0) {
            List<PimsPathologyOrderChild> lis = pimsPathologyOrderChildManager.getChildList(orderId);
            for(Object o : paraffinArray) {
                JSONObject jsonObject = (JSONObject)o;
                long obligateSlide = jsonObject.getLongValue("yuliu");
                long commonSlide = jsonObject.getLongValue("totalitem");
                long paraffinId = jsonObject.getLongValue("chiparaffinid");
                for(PimsPathologyOrderChild child : lis) {
                    if(child.getChiparaffinid() == paraffinId) {
                        child.setChislidenum(commonSlide);
                        child.setChinullslidenum(obligateSlide);
                        pimsPathologyOrderChildManager.save(child);
                    }
                }
            }
        }
    }

    @RequestMapping(value = "/updatetestresult", method = RequestMethod.GET)
    @ResponseBody
    public void updateTestResult(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String result = request.getParameter("result");
        JSONArray json = JSONArray.parseArray(result);
        User user = WebControllerUtil.getAuthUser();
        pimsPathologyOrderCheckManager.updateTestResult(json, user.getName());
    }

    @RequestMapping(value = "/getparaffin", method = RequestMethod.GET)
    @ResponseBody
    public DataResponse getParaffin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DataResponse dr = new DataResponse();
        String paraffinCode = request.getParameter("paraffinCode");
        long sampleId = Long.valueOf(request.getParameter("sampleId"));
        PimsPathologyParaffin p3 = pimsPathologyParaffinManager.getPimsPathologyParaffin(sampleId, paraffinCode);
        Map<String, Object> result = new HashMap<>();
        result.put("paraffinId", p3.getParaffinid());
        result.put("parnullslidenum", p3.getParnullslidenum());
        result.put("parPieceParts", p3.getParpieceparts());
        dr.setUserdata(result);
        return dr;
    }

    @RequestMapping(value = "/updateorderstate", method = RequestMethod.GET)
    @ResponseBody
    public void updateOrderState(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
        String orderType = request.getParameter("orderType");
        PimsPathologyOrder order = pimsPathologyOrderManager.get(orderId);
        PimsPathologyOrderChild child = pimsPathologyOrderChildManager.getChildByOrderId(orderId);
        PimsSysReqTestitem testitem = pimsSysReqTestitemManager.get(child.getTestItemId());
        child.setTestItemChName(testitem.getTeschinesename());

        if (orderType.equals(Constants.ORDER_TYPE_BUQU)) {
            List<PimsPathologyPieces> pieces = pimsPathologyPiecesManager.getPiecesByOrderId(orderId);
            Map<String, Object> userData = new HashMap<>();
            userData.put("orderChild", JSONObject.toJSON(child).toString());
            dr.setUserdata(userData);
            dr.setRows(getResultMap(pieces));
            return dr;
        }

        //技术检查项
        List<PimsPathologyOrderCheck> orderChecks = pimsPathologyOrderCheckManager.getOrderCheckByOrderId(orderId);

        //取医嘱项目的计费信息
        List<Map<String, Object>> orderChildChargeItem = pimsPathologyOrderChildManager.getOrderChildChargeItem(child.getTestItemId(), order.getOrdcustomercode());

        JSONArray childChargeJson = new JSONArray();

        if (orderChildChargeItem.size() > 0) {
            for (Map<String, Object> map : orderChildChargeItem) {
                JSONObject jsonObject = new JSONObject(map);
                childChargeJson.add(jsonObject);
            }
        }

        //取检验项目的计费信息
        String checkChargeJson = "{}";
        Set<Long> checkItemId = new HashSet<>();
        if (orderChecks.size() > 0) {
            for (PimsPathologyOrderCheck ck : orderChecks) {
                checkItemId.add(Long.valueOf(ck.getCheorderitemid()));
            }
        }

        if (checkItemId.size() > 0) {
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
        String ingore = request.getParameter("ingore");
        List result = pimsPathologyOrderManager.getOrders(gridQuery, specialCheck, pathologyCode, startDate, endDate, patientName, orderState, ingore);
        Integer total = pimsPathologyOrderManager.countOrders(specialCheck, pathologyCode, startDate, endDate, patientName, orderState, ingore);
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

    @RequestMapping(value = "/orderdoctor", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView doctor(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView();
    }

    @RequestMapping(value = "/save")
    @ResponseBody
    public void save(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PimsPathologyOrder pathologyOrder = new PimsPathologyOrder();

        String orderType = request.getParameter("orderType");//医嘱类型
        String orderCode = request.getParameter("orderCode");//医嘱号
        long sampleId = Long.valueOf(request.getParameter("sampleId"));//标本号
        long customerId = Long.valueOf(request.getParameter("customerId"));//客户号
        long pathologyId = Long.valueOf(request.getParameter("pathologyId"));//客户号
        String pathologyCode = request.getParameter("pathologyCode");//病理号
        String reqDoctor = request.getParameter("reqDoctor");//申请医生
        String reqDoctorId = request.getParameter("reqDoctorId");//申请医生ID
        String reqDate = request.getParameter("reqDate");//申请日期

        long testItemId = Long.valueOf(request.getParameter("testItemId"));//医嘱申请的开单项目ID
        String items = request.getParameter("items");//医嘱项目
        String paraffinItems = request.getParameter("paraffinItems");//蜡块库存和预留信息

        JSONArray paraffinArray = JSONArray.parseArray(paraffinItems);
        JSONArray array = JSONArray.parseArray(items);

        pathologyOrder.setOrdsampleid(sampleId);
        pathologyOrder.setOrdpathologycode(pathologyCode);
        pathologyOrder.setOrdcustomercode(customerId);
        pathologyOrder.setOrdcreatetime(Constants.DF2.parse(reqDate));
        pathologyOrder.setOrdorderuser(reqDoctor);
        pathologyOrder.setOrdorderuserid(reqDoctorId);
        pathologyOrder.setOrdorderstate(Constants.ORDER_STATE_REQUEST);//医嘱状态为：申请
        pathologyOrder.setOrdisdelete(0L); //状态正常
        pathologyOrder.setOrdercode(orderCode);

        //保存医嘱基本信息
        pathologyOrder = pimsPathologyOrderManager.save(pathologyOrder);

        //保存医嘱子项信息
        if (orderType.equals(Constants.ORDER_TYPE_BUQU)) {
            int nullSlide = 0;
            for (Object obj : array) {
                nullSlide += Integer.valueOf(String.valueOf(((JSONObject) obj).get("pienullslidenum")));
            }
            PimsPathologyOrderChild orderChild = saveOrderChild(pathologyOrder, testItemId, nullSlide);
            for (Object obj : array) {
                ((JSONObject) obj).put("piefirstn", orderChild.getChiorderid());
            }
            pimsPathologyPiecesManager.saveOrderMaterial(array);
        } else {
            List<PimsPathologyOrderChild> orderChild = saveOrderChild(pathologyOrder, paraffinArray, testItemId, orderType);
            if (orderType.equals(Constants.ORDER_TYPE_FZBL) || orderType.equals(Constants.ORDER_TYPE_MYZH) || orderType.equals(Constants.ORDER_TYPE_TSRS))
                //保存检验项目信息
                saveOrderCheckItem(pathologyOrder, orderChild, array);
        }

        pimsPathologyFeeManager.saveFee(pathologyOrder, pathologyId, getTestItem());

    }

    private PimsPathologyOrderChild saveOrderChild(PimsPathologyOrder pathologyOrder, long testItemId, int nullSlide) {
        PimsPathologyOrderChild orderChild = new PimsPathologyOrderChild();
        //医嘱类型设置为检查项目的ID
        orderChild.setChiordertype(testItemId);
        orderChild.setChicreatetime(new Date());
        orderChild.setTestItemId(testItemId);
        orderChild.setChicreateuser(pathologyOrder.getOrdorderuser());
        orderChild.setChicustomercode(pathologyOrder.getOrdcustomercode());
        orderChild.setChireqtime(pathologyOrder.getOrdcreatetime());
        orderChild.setChihandletype(1L);
        orderChild.setChinullslidenum((long) nullSlide);
        orderChild.setChirequserid(pathologyOrder.getOrdorderuserid());
        orderChild.setChirequsername(pathologyOrder.getOrdorderuser());
        orderChild.setChisampleid(pathologyOrder.getOrdsampleid());
        orderChild.setChiordercode(pathologyOrder.getOrdercode());
        orderChild.setChiorderid(pathologyOrder.getOrderid());
        orderChild.setChiorderstate(pathologyOrder.getOrdorderstate());
        orderChild.setChiisdelete(pathologyOrder.getOrdisdelete());
        orderChild.setChipathologycode(pathologyOrder.getOrdpathologycode());
        orderChild.setFinishStatus(0L);
        orderChild.setChiparaffincode(" ");
        orderChild.setChiparaffinno(0L);
        orderChild.setChiparaffinid(0L);
        //orderChild.setChinullslidenum(0L);
        orderChild = pimsPathologyOrderChildManager.save(orderChild);
        getTestItem().add(testItemId);
        return orderChild;
    }

    private List<PimsPathologyOrderChild> saveOrderChild(PimsPathologyOrder pathologyOrder, JSONArray paraffinArray, long testItemId, String orderType) {
        List<PimsPathologyOrderChild> result = new ArrayList<>();
        for (Object obj : paraffinArray) {
            JSONObject jsonObject = (JSONObject) obj;
            Long paraffinTotalItem = jsonObject.getLongValue("totalItem");
            Long inventory = jsonObject.getLongValue("kucun");
            Long obligate = jsonObject.getLongValue("yuliu");
            long nullPiece = 0;
            if ((paraffinTotalItem + obligate) > inventory) nullPiece = (paraffinTotalItem + obligate) - inventory;
            PimsPathologyOrderChild orderChild = new PimsPathologyOrderChild();
            //医嘱类型设置为检查项目的ID
            orderChild.setChiordertype(testItemId);
            orderChild.setChiparaffincode(jsonObject.getString("lkno"));
            orderChild.setChiparaffinid(jsonObject.getLongValue("lkid"));
            if (orderType.equals("CHONGQIE") || orderType.equals("SHENQIE"))
                orderChild.setChihandletype(2L);
            orderChild.setChicreatetime(new Date());
            orderChild.setTestItemId(testItemId);
            orderChild.setChislidenum(paraffinTotalItem);
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
            orderChild.setChinullslidenum(nullPiece);
            orderChild.setChicontent(jsonObject.getString("chicontent"));
            orderChild.setFinishStatus(0L);
            orderChild = pimsPathologyOrderChildManager.save(orderChild);
            result.add(orderChild);
            pimsPathologySlideManager.updateWhitePieceUsedFlag(jsonObject.getString("lkno"), pathologyOrder.getOrdsampleid(), paraffinTotalItem);
            getTestItem().add(testItemId);
        }
        return result;
    }

    private void saveOrderCheckItem(PimsPathologyOrder pathologyOrder, List<PimsPathologyOrderChild> orderChild, JSONArray array) {
        for (Object obj : array) {
            PimsPathologyOrderCheck checkItem = new PimsPathologyOrderCheck();
            String paraffinCode = ((JSONObject) obj).getString("lkno");
            for (PimsPathologyOrderChild c : orderChild) {
                if (c.getChiparaffincode().equals(paraffinCode)) {
                    checkItem.setCheitemtype(c.getChiordertype());
                    checkItem.setChechildorderid(c.getChildorderid());
                    break;
                }
            }
            checkItem.setParaffincode(paraffinCode);
            checkItem.setChechargestate(0L);
            checkItem.setChecreatetime(pathologyOrder.getOrdcreatetime());
            checkItem.setChecreateuser(pathologyOrder.getOrdorderuser());
            checkItem.setChesampleid(pathologyOrder.getOrdsampleid());
            checkItem.setCheorderid(pathologyOrder.getOrderid());
            checkItem.setChepathologycode(pathologyOrder.getOrdpathologycode());
            checkItem.setCustomercode(pathologyOrder.getOrdcustomercode());
            checkItem.setCheorderitemid(((JSONObject) obj).getString("testitemid"));
            checkItem.setChenamech(((JSONObject) obj).getString("teschinesename"));
            checkItem.setChenameen(((JSONObject) obj).getString("tesenglishname"));
            checkItem.setCheischarge(((JSONObject) obj).getLong("tesischarge"));
            pimsPathologyOrderCheckManager.save(checkItem);
            getTestItem().add(Long.valueOf(((JSONObject) obj).getString("testitemid")));
        }
    }

    @RequestMapping(value = "/orderlist", method = RequestMethod.GET)
    @ResponseBody
    public DataResponse OrderList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DataResponse dr = new DataResponse();
        PimsBaseModel model = new PimsBaseModel(request);
        List result = pimsPathologyOrderManager.getOrderList(model);
        Integer total = pimsPathologyOrderManager.getOrderNum(model);
        dr.setRecords(total);
        dr.setPage(model.getPage());
        dr.setTotal(getTotalPage(total, model.getRow(), model.getPage()));
        dr.setRows(getResultMaps(result));
        response.setContentType(contentType);
        return dr;
    }

    public Set<Long> getTestItem() {
        return testItem;
    }

    public void setTestItem(Set<Long> testItem) {
        this.testItem = testItem;
    }
}
