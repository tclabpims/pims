package com.pims.webapp.util;

import com.pims.model.PimsSysCustomerBasedata;
import com.pims.model.PimsSysReportItems;
import com.pims.model.PimsSysReqField;
import org.apache.commons.lang3.StringEscapeUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 909436637@qq.com on 2016/10/24.
 * Description:
 */
public class HtmlGenerator {

    public static String generate(List<PimsSysReqField> reqFields, List<PimsSysReportItems> reportItemsList, List<PimsSysCustomerBasedata> customerData) {
        if(reqFields.size() == 0) return null;
        StringBuilder tags = new StringBuilder();
        Map<String, PimsSysReportItems> map = new HashMap<>();
        Map<Long, PimsSysCustomerBasedata> dataMap = new HashMap<>();
        for(PimsSysReportItems item : reportItemsList) {
            map.put(item.getRptelementid(), item);
        }
        for(PimsSysCustomerBasedata data : customerData) {
            dataMap.put(data.getBasrefdataid(), data);
        }
        for(PimsSysReqField field : reqFields) {
            String tag = field2Tag(field);
            PimsSysReportItems item = map.get(String.valueOf(field.getFieldid()));
            PimsSysCustomerBasedata bd = dataMap.get(item.getReportitemid());
            tag = tag.replaceAll("#fieelementid", field.getFieelementid());
            tag = tag.replaceAll("#fieelementname", field.getFieelementname());
            tag = tag.replaceAll("#rpttemplatetype", String.valueOf(item.getRpttemplatetype()));
            tag = tag.replaceAll("#reportitemid", String.valueOf(item.getReportitemid()));
            tag = tag.replaceAll("#showOrder", field.getFieshoworder());
            tag = tag.replaceAll("#printOrder", String.valueOf(bd.getBasrptItemSort()));
            tags.append(StringEscapeUtils.escapeHtml4(tag));
        }
        return tags.toString();
    }


    private static String field2Tag(PimsSysReqField field) {

        return Tag.getTag(field.getFieelementtype());
    }

    public enum Tag{
        INPUT("input","<div class='form-group' style='margin-left:0px;margin-right:0px;'>\n" +
                "<label class='col-xs-3 control-label no-padding-right' for='#fieelementid'>#fieelementname</label>\n" +
                "<div class='col-xs-8'><input type='text' name='#fieelementid' id='#fieelementid' rptItemId='#reportitemid' class='#fieldcss' placeholder='#fieelementname'></div>\n" +
                "                    </div>"),
        BUTTON("button","<button onclick='#invokefunc'>#fieelementname</button>"),
        TEXTAREA("textarea","<div>\n" +
                "<label for='#fieelementid'>#fieelementname</label>\n" +
                "<button onclick=\"showTemplate(#rpttemplatetype,'#fieelementid')\">从模板选择</button>\n" +
                "<button onclick=\"saveAsTemplate(#rpttemplatetype,'#fieelementid')\">存为模板</button>\n<div><textarea name='#fieelementid' id='#fieelementid' cols='65' rptItemId='#reportitemid' rows='2' printOrder='#printOrder' showOrder='#showOrder' hiddenValue='' placeholder='#fieelementname'></textarea></div>\n" +
                "</div>\n"),
        SELECT("select",""),
        RADIO("radio",""),
        CHECKBOX("checkbox",""),
        LABEL("label","<label for='$id' class='#fieldcss'>#fieelementname</label>"),
        FORM("form",""),
        LEGEND("legend",""),
        DIV("div","<div class='#fieldcss' >#children</div>"),
        SPAN("span","");

        private String name;

        private String tagObject;

        private Tag(String name, String tagObject) {
            this.name = name;
            this.tagObject = tagObject;
        }

        public static String getTag(String name) {
            for(Tag tag : Tag.values()) {
                if(tag.getName().equals(name)) return tag.tagObject;
            }
            return null;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTagObject() {
            return tagObject;
        }

        public void setTagObject(String tagObject) {
            this.tagObject = tagObject;
        }
    }

}
