package com.pims.webapp.controller;

/**
 * Created by king on 2016/12/23.
 */
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/printer*")
public class Printers {

    @RequestMapping(value = "/printlist*", method = RequestMethod.GET)
    @ResponseBody
    public String getPrintList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HashPrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
        DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
        JSONArray array = new JSONArray();
        //查找所有的可用的打印服务
        PrintService[] printService = PrintServiceLookup.lookupPrintServices(flavor, pras);
        for (int i =0; i<printService.length ;i++ ) {
            JSONObject o = new JSONObject();
            o.put("id",printService[i].getName());
            o.put("name", printService[i].getName());
//            System.out.println(printService[i].getName());
            array.put(o);
        }
//        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(array.toString());
        return null;
    }
}
