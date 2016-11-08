package com.pims.webapp.controller.pimssyspathology;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pims.model.PimsPathologyOrder;
import com.pims.model.PimsPathologyOrderCheck;
import com.pims.model.PimsPathologyOrderChild;
import com.pims.service.pimspathologysample.PimsPathologySlideManager;
import com.pims.service.pimssyspathology.PimsPathologyOrderCheckManager;
import com.pims.service.pimssyspathology.PimsPathologyOrderChildManager;
import com.pims.service.pimssyspathology.PimsPathologyOrderManager;
import com.pims.webapp.controller.PIMSBaseController;
import com.smart.Constants;
import org.springframework.beans.factory.annotation.Autowired;
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
        int reqType = Integer.valueOf(request.getParameter("reqType"));//申请类型
        int inventory = Integer.valueOf(request.getParameter("inventory"));//切白片数
        long paraffinId = Long.valueOf(request.getParameter("paraffinId"));//蜡块ID
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
        PimsPathologyOrderChild orderChild = saveOrderChild(pathologyOrder, paraffinCode, paraffinId, inventory, reqType);

        //保存检验项目信息
        int totalItem = saveOrderCheckItem(pathologyOrder, orderChild.getChildorderid(), array, orderChild.getChiordertype());

        //更新白片使用状态
        pimsPathologySlideManager.updateWhitePieceUsedFlag(paraffinCode, sampleId, (long)totalItem);


    }

    private PimsPathologyOrderChild saveOrderChild(PimsPathologyOrder pathologyOrder, String paraffinCode, long paraffinId, int whitePieceNo, int reqType) {
        PimsPathologyOrderChild orderChild = new PimsPathologyOrderChild();
        //if(reqType == 4 || reqType == 5 || reqType == 6) reqType = 2;
        orderChild.setChiordertype((long)reqType);
        orderChild.setChiparaffincode(paraffinCode);
        orderChild.setChiparaffinid(paraffinId);
        orderChild.setChicreatetime(new Date());
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
