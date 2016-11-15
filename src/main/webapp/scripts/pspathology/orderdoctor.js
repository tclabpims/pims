Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}
function search() {
    var query = $('#query').val() || '';
    jQuery("#sectionList").jqGrid('setGridParam', {
        url: "../reqfield/query",
        //发送数据
        postData: {"query": query, "sidx": "fieldid"},
        page: 1
    }).trigger('reloadGrid');//重新载入
}

$(function () {
    //keyPress 回车检索
    $("#query").keypress(function (e) {
        if (e.keyCode == 13) {
            search();
        }
    });
});

function getOrderItems() {
    $("#q_specialCheck").empty();
    $.get("../estitem/allorderitem", {}, function (data) {
        var ret = data;
        if (ret != null && ret.length > 0) {
            $("#q_specialCheck").append("<option value=''>请选择</option>");
            for (var i = 0; i < ret.length; i++) {
                $("#q_specialCheck").append("<option value='" + ret[i].testitemid + "'>" + ret[i].teschinesename + "</option>");
            }
        }
    })
}

function getWhitePiece(sampleid,pcode) {

    $.get("../diagnosis/report/whitepiece", {sampleId: sampleid, paraffinNo: pcode}, function (data) {
        var ret = data.rows;
        if (ret != null && ret.length > 0) {
            $.get("../order/getparaffin", {paraffinCode:pcode, sampleId: sampleid}, function(data1){
                var yl = data1.userdata.parnullslidenum;
                $("#lkItemList").jqGrid('addRowData',0, {lkno:pcode,kucun:ret.length,yuliu:yl});
            })
        } else {
            $("#lkItemList").jqGrid('addRowData',0, {lkno:pcode,kucun:0,yuliu:0});
        }
    });
}

function getOrderInfo(orderId) {
    $.get("../order/orderchildandcheckitem", {orderId:orderId}, function (data) {
        var checkItems = data.rows;
        var childInfo = jQuery.parseJSON(data.userdata.orderChild);
        var checkChargeJson = jQuery.parseJSON(data.userdata.checkChargeJson);
        var childChargeJson = data.userdata.childChargeJson;

        $("#chipathologycode").val(childInfo.chipathologycode);
        $("#testItemChName").val(childInfo.testItemChName);
        $("#chireqtime").val(new Date(childInfo.chireqtime).Format("yyyy-MM-dd"));
        $("#chiordercode").val(childInfo.chiordercode);
        $("#chirequsername").val(childInfo.chirequsername);
        $("#chinullslidenum").val(childInfo.chinullslidenum);
        $("#orderType").val(childInfo.chiordertype);
        $("#childItemId").val(childInfo.childorderid);

        jQuery("#checkItemList").jqGrid("clearGridData");
        if(checkItems.length > 0) {
            for(var i = 0; i < checkItems.length; i++) {
                checkItems[i].chiparaffincode = childInfo.chiparaffincode;
                checkItems[i].chirequsername = childInfo.chirequsername;
                checkItems[i].chiorderstate = childInfo.chiorderstate;
            }
        }
        jQuery("#checkItemList")[0].addJSONData(checkItems);

        jQuery("#childChargeList").jqGrid("clearGridData");

        jQuery("#childChargeList")[0].addJSONData(childChargeJson);

        var itemCal = "申请数量：" + checkChargeJson.reqItem + ",应收费用：￥" + checkChargeJson.totalMoney;
        $("#itemCal").html(itemCal);
    })
}

function updateState(state) {
    var id = $('#sectionList').jqGrid('getGridParam', 'selrow');
    if (id == null || id.length == 0) {
        layer.msg('请先选择医嘱', {icon: 2, time: 1000});
        return false;
    }
    var rowData = $("#sectionList").jqGrid('getRowData', id);
    doUpdate(rowData, state);
}

function  saveOrder() {
    var id = $('#sectionList').jqGrid('getGridParam', 'selrow');
    if (id == null || id.length == 0) {
        layer.msg('请先选择医嘱', {icon: 2, time: 1000});
        return false;
    }
    var rowData = $("#sectionList").jqGrid('getRowData', id);

    var itemNo = $("#checkItemList").jqGrid("getDataIDs");

    var orderState = rowData.chiOrderState;
    var orderId = rowData.orderId;
    var array = [];
    var jsonParam;
    //如果遗嘱状态是已完成 就可以保存检验结果了
    if(orderState == 2) {
        for(var i=0; i < itemNo.length; i++) {
            var row = $("#checkItemList").jqGrid("getRowData", itemNo[i]);
            var testItemResult = {};
            testItemResult.cheorderid = orderId;
            testItemResult.cheorderitemid = row.cheorderitemid;
            testItemResult.chetestresult = row.chetestresult;
            array[i] = testItemResult;
        }
        jsonParam = JSON.stringify(array);
        $.get("../order/updatetestresult", {result:jsonParam}, function (data) {
            layer.alert("保存成功！");
        })
    }
    //如果医嘱状态是已申请 那么就保存修改后的检验项目
    if(orderState == 0) {
        if(itemNo.length == 0) {
            layer.alert("检测项目不存在，请先添加项目！");
            return;
        }
        for(var j = 0; j < itemNo.length; j++) {
            var row = $("#checkItemList").jqGrid("getRowData", itemNo[j]);
            if(row.checreatetime == null || row.checreatetime == "") {
                row.newAppend = true;
            } else {
                row.newAppend = false;
            }
            row.orderType = $("#orderType").val();
            row.childItemId = $("#childItemId").val();
            row.ordSampleId = rowData.ordSampleId;
            row.ordCustomerId = rowData.ordCustomerId;
            row.ordPathologyCode = rowData.ordPathologyCode;
            row.samPathologyId = rowData.samPathologyId;
            row.chiParaffinCode = rowData.chiParaffinCode;
            row.checreateuser = $("#reqDoctor").val();
            row.orderId = orderId;
            array[j] = row;
        }
        jsonParam = JSON.stringify(array);

        var d = $("#lkItemList").jqGrid('getRowData',0);
        var inventory = 0;
        var need = array.length + parseInt(d.yuliu);
        if(need > d.kucun) inventory = need - d.kucun;

        alert(jsonParam);
        $.get("../order/updatecheckitem", {testItems:jsonParam,inventory:inventory,orderChildId:$("#childItemId").val(), orderId:orderId}, function (data1) {
            layer.alert("保存成功！");
        })
    }


}

function chargeAdjust() {

}

function doUpdate(rowData, state) {
    $.get("../order/updateorderstate", {orderState:state, orderId:rowData.orderId}, function(data){
        rowData.chiOrderState = state;
        layer.alert("操作成功！");
    });
}

function getSampleData1(id) {
    $.get("../pathologysample/sample/get", {id: id}, function (data) {
        if (data != "") {
            $("#sampleid").val(data.sampleid);//标本id
            $("#saminspectionid").val(data.saminspectionid);//标本条码号 11
            $("#sampathologycode").val(data.sampathologycode);//病理编号 11
            $("#sampatientnumber").val(data.sampatientnumber);//住院卡号/门诊卡号 11
            $("#sampatientname").val(data.sampatientname);//姓名 11
            var tt = data.sampatientsex;
            if (tt == 1)
                $("#sampatientgender").val("男");//患者性别(1男,2女,3未知) 11
            else if (tt == 2)
                $("#sampatientgender").val("女");
            else $("#sampatientgender").val("未知");
            $("#samsamplename").val(data.samsamplename);//标本名称(,多个检查项目名称之间用逗号隔开) 11
            $("#samsenddoctorid").val(data.samsenddoctorid);//送检医生id 11
            $("#samsendhospital").val(data.samsendhospital);//送检单位名称 11
            $("#samdeptname").val(data.samdeptname);//科室名称 11
            $("#sampatientdignoses").val(data.sampatientdignoses);//临床诊断 11
            $("#samismenopause").val(data.samismenopause);//是否绝经 11
            $("#reqlastmenstruation").val(data.reqlastmenstruation);//末次月经时间 11
            $("#sampatientbed").val(data.sampatientbed);//患者床号
            $("#sampatientage").val(data.sampatientage);//患者床号

            //重新加载取材信息列表
            jQuery("#materialList").jqGrid("clearGridData");
            jQuery("#materialList").jqGrid('setGridParam', {
                url: "../pathologysample/pieces/ajax/getItem",
                datatype: 'json',
                postData: {"reqId": data.sampleid}
            }).trigger('reloadGrid');//重新载入
        } else {
            layer.msg("该医嘱申请不存在！", {icon: 0, time: 1000});
        }
    });
}

function query(state) {
    var specialCheck = $("#q_specialCheck").val();
    var startDate = $("#q_startDate").val();
    var endDate = $("#q_endDate").val();
    var pathologyCode = $("#q_pathologyCode").val();
    var patientName = $("#q_patientName").val();
    var param = {
        "specialCheck": specialCheck,
        "startDate": startDate,
        "endDate": endDate,
        "pathologyCode": pathologyCode,
        "patientName": patientName
    };
    if(state != -1) param.orderState = state;
    jQuery("#sectionList").jqGrid('setGridParam', {
        datatype: 'json',
        postData: param,
        page: 1
    }).trigger('reloadGrid');//重新载入
}

var crno = 1;

function setSelect(c) {
    var o = jQuery("#sectionList");
    var total = o.jqGrid('getGridParam', 'reccount'); //获取当前页面的总记录数量
    if (total == 0) return false;
    var id = $('#sectionList').jqGrid('getGridParam', 'selrow');
    c = parseInt(c);
    if (id == null || id.length == 0) {
        $("#sectionList").jqGrid('setSelection', crno);
        return;
    }
    if (c == 0) {
        if (crno > 1) {
            crno = crno - 1;
        }
    } else {
        if (crno + 1 > total) crno = total;
        else crno = crno + 1;
    }
    $("#sectionList").jqGrid('setSelection', crno);
}

function CurentTime(now) {
    //var now = new Date();
    var year = now.getFullYear();       //年
    var month = now.getMonth() + 1;     //月
    var day = now.getDate();            //日
    var hh = now.getHours();            //时
    var mm = now.getMinutes();          //分
    var ss = now.getMilliseconds();    //秒
    var clock = year + "-";
    if (month < 10)
        clock += "0";
    clock += month + "-";
    if (day < 10)
        clock += "0";
    clock += day + " ";
    if (hh < 10)
        clock += "0";
    clock += hh + ":";
    if (mm < 10) clock += '0';
    clock += mm + ":";
    if (ss < 10) clock += '0';
    clock += ss;
    return (clock);
}

function buttonFormat(cellval,options,pos,cnt) {

    return "<button onclick=appendItem('" + cellval + "')>追加</button>";
}

function removeBt(cellval,options,pos,cnt) {
    if(cellval == 0)
        return "<button onclick=removeItem('" + cellval + "')>移除</button>";
    return "";
}

function removeItem(v) {
    var id = $('#checkItemList').jqGrid('getGridParam', 'selrow');
    $("#checkItemList").jqGrid("delRowData", id);
}

function getPackageItems(pid) {
    if(pid != null && pid !="") {
        $.get("../package/packageitems", {pathologyId:pid}, function (data) {
            var ret = data.rows;
            if (ret != null && ret.length > 0) {
                $("#itemPackage").append("<option value=''></option>");
                for (var i = 0; i < ret.length; i++) {
                    $("#itemPackage").append("<option value='" + ret[i].packageId + "'>" + ret[i].packageName + "</option>");
                }
            }
        })
    }
}

function getItemInfo(v){
    if(v == null || v == "") return;
    $.get("../package/testitems", {packageId:v}, function (data) {
        var ret = data.rows;
        if (ret != null && ret.length > 0) {
            for (var i = 0; i < ret.length; i++) {
                $("#ckItemList").jqGrid("addRowData",i,ret[i]);
            }
        }
    })
}

function appendItem(v) {
    var id = $('#sectionList').jqGrid('getGridParam', 'selrow');
    var rowData = $("#sectionList").jqGrid('getRowData', id);
    var lakuai = rowData.chiParaffinCode;
    var selrow = $("#ckItemList").jqGrid('getGridParam', 'selrow');
    if ((selrow == null || selrow.length == 0) && v == null) return;
    var row;
    if((typeof v) == "object") row = v;
    else row = $("#ckItemList").jqGrid('getRowData', selrow);
    var itemNo = $("#checkItemList").jqGrid("getDataIDs").length;
    var insertedId = $("#checkItemList").getCol("cheorderitemid");
    var e = false;
    for(var j =0; j < insertedId.length; j++) {
        if(insertedId[j] == row.testitemid) e = true;
    }
    if(!e) {
        var d = {};
        d.cheorderitemid = row.testitemid;
        d.chiparaffincode = lakuai;
        d.chenamech = row.teschinesename;
        d.chenameen = row.tesenglishname;
        d.chiorderstate  = 0;
        d.cheischarge = row.tesischarge;
        $("#checkItemList").jqGrid("addRowData", (itemNo+1), d);
    }
}

function appendAll() {
    var rows = $("#ckItemList").jqGrid("getDataIDs");
    var id = $('#sectionList').jqGrid('getGridParam', 'selrow');
    var rowData = $("#sectionList").jqGrid('getRowData', id);
    var lakuai = rowData.chiParaffinCode;
    if(rows.length > 0) {
        for(var i = 0; i < rows.length; i++) {
            var row = $("#ckItemList").jqGrid("getRowData", rows[i]);
            row.lkno = lakuai;
            var itemNo = $("#checkItemList").jqGrid("getDataIDs").length;
            var insertedId = $("#checkItemList").getCol("cheorderitemid");
            var e = false;
            for(var j =0; j < insertedId.length; j++) {
                if(insertedId[j] == row.testitemid) e = true;
            }
            if(!e) {
                var d = {};
                d.cheorderitemid = row.testitemid;
                d.chiparaffincode = lakuai;
                d.chenamech = row.teschinesename;
                d.chenameen = row.tesenglishname;
                d.cheischarge = row.tesischarge;
                d.chiorderstate  = 0;
                $("#checkItemList").jqGrid("addRowData", (itemNo+1), d);
            }
        }
    }
}

$(function () {
    $(window).on('resize.jqGrid', function () {
        $('#sectionList').jqGrid('setGridWidth', $(".leftContent").width(), false);
    });
    var clientHeight = $(window).innerHeight();
    var height = clientHeight - $('#head').height() - $('#toolbar').height() - $('.footer-content').height() - 150;

    $("#sectionList").jqGrid({
        caption: "",
        url: "../order/getorders",
        mtype: "GET",
        datatype: "json",
        width: $('.leftContent').width(),
        postData: {
            specialCheck: $("#q_specialCheck").val(),
            pathologyCode: $("#q_pathologyCode").val(),
            startDate: $("#q_startDate").val(),
            endDate: $("#q_endDate").val(),
            patientName: $("#q_patientName").val(),
        },
        colNames: ['特检类型', '医嘱号', '申请医生', 'orderId', 'ordSampleId', 'ordCustomerId', 'ordPathologyCode', 'chiOrderState', 'samPathologyId','chiParaffinCode'],
        colModel: [
            {
                name: 'chiOrderType',
                index: 'chiOrderType',
                width: 30
            },
            {name: 'orderCode', index: 'orderCode', width: 40},
            {name: 'ordOrderUser', index: 'ordOrderUser', width: 40},
            {name: 'orderId', index: 'orderId', hidden: true},
            {name: 'ordSampleId', index: 'ordSampleId', hidden: true},
            {name: 'ordCustomerId', index: 'ordCustomerId', hidden: true},
            {name: 'ordPathologyCode', index: 'ordPathologyCode', hidden: true},
            {name: 'chiOrderState', index: 'chiOrderState', hidden: true},
            {name: 'samPathologyId', index: 'samPathologyId', hidden: true},
            {name: 'chiParaffinCode', index: 'chiParaffinCode', hidden: true}
        ],
        loadComplete: function () {
            var table = this;
            setTimeout(function () {
                updatePagerIcons(table);
            }, 0);
        },
        ondblClickRow: function (id) {
        },
        viewrecords: true,
        shrinkToFit: true,
        altRows: true,
        height: height,
        rowNum: 10,
        rowList: [10, 20, 30],
        rownumbers: true, // 显示行号
        rownumWidth: 35, // the width of the row numbers columns
        pager: "#pager",
        onSelectRow: function (id) {
            var rowData = $("#sectionList").jqGrid('getRowData', id);
            getSampleData1(rowData.ordSampleId);
            getOrderInfo(rowData.orderId);
            $("#lkItemList").jqGrid('clearGridData');
            $("#ckItemList").jqGrid('clearGridData');
            $("#itemPackage").empty();
            $("#itemName").attr("readonly", "readonly");
            if(rowData.chiOrderState == 0) {
                getPackageItems(rowData.samPathologyId);
                getWhitePiece(rowData.ordSampleId, rowData.chiParaffinCode);
                $("#itemName").removeAttr("readonly");
            }
            var state = rowData.chiOrderState;
            if(state == 1 || state == 3) {
                $("#btFinish").attr("disabled", "disabled");
            } else if(state == 0 || state == 2) {
                $("#btFinish").removeAttr("disabled");
            }
            if(state == 0 || state == 1) {
                $("#btCancel").removeAttr("disabled");
            } else {
                $("#btCancel").attr("disabled","disabled");
            }
        }
    });

    jQuery("#sectionList").jqGrid('bindKeys', {
            "onEnter": function (rowid) {
                $("#sectionList").jqGrid('setSelection', rowid);
            }
        }
    );

    $("#checkItemList").jqGrid({
        datatype: "json",
        mtype: "GET",
        cellEdit: true,
        cellsubmit:'clientArray',
        afterEditCell:function(rowid,name,val,iRow,iCol){
            //$("#lkItemList").jqGrid('setSelection',rowid);
            $('#checkItemList').jqGrid('saveCell',$("#checkItemList").jqGrid.editrow,$("#checkItemList").jqGrid.editcol);

        },
        colNames: ['cheorderitemid','蜡块编号', '项目名称','结果', '申请医生', '状态','操作','checreatetime','chenameen','cheischarge'],
        colModel: [
            {name: 'cheorderitemid', index: 'cheorderitemid', hidden:true},
            {name: 'chiparaffincode', index: 'chiparaffincode', width: 100},
            {name: 'chenamech', index: 'chenamech', width: 120},
            {name: 'chetestresult', index: 'chetestresult', width: 120,editable:true,edittype:'text',editrules: {edithidden:true,required:true}},
            {name: 'chirequsername', index: 'chirequsername', width: 80},
            {name: 'finishStatus', index: 'finishStatus', width: 60, formatter:"select",editoptions: {value: "0:未完成;1:已完成;"}},
            {name: 'chiorderstate', index: 'chiorderstate', formatter:removeBt,width:60},
            {name: 'checreatetime', index: 'checreatetime', hidden:true},
            {name: 'chenameen', index: 'chenameen', hidden:true},
            {name: 'cheischarge', index: 'cheischarge', hidden:true}
        ],
        loadComplete: function () {
            /*var table = this;
             setTimeout(function(){
             updatePagerIcons(table);
             }, 0);*/
        },
        multiselect:true,
        shrinkToFit: true,
        scrollOffset: 2,
        rowNum: 10,
        rownumbers: true // 显示行号
    });

    $("#childChargeList").jqGrid({
        datatype: "json",
        mtype: "GET",
        height:150,
        colNames: ['项目名称', '单价', '金额', '数量'],
        colModel: [
            {name: 'hisChargeName', index: 'hisChargeName', width: 100},
            {name: 'hisPrice', index: 'hisPrice', width: 60},
            {name: 'hisPrice', index: 'hisPrice', width: 60},
            {name: 'num', index: 'num', width: 50}
        ],
        loadComplete: function () {
            /*var table = this;
             setTimeout(function(){
             updatePagerIcons(table);
             }, 0);*/
        },
        shrinkToFit: false,
        scrollOffset: 2,
        rowNum: 10,
        rownumbers: true // 显示行号
    });

    $("#lkItemList").jqGrid({
        datatype: "json",
        width: 210,
        cellEdit: true,
        cellsubmit:'clientArray',
        colNames: ['蜡块编号','库存', '预留'],
        afterEditCell:function(rowid,name,val,iRow,iCol){
            //$("#lkItemList").jqGrid('setSelection',rowid);
            $('#lkItemList').jqGrid('saveCell',$("#lkItemList").jqGrid.editrow,$("#lkItemList").jqGrid.editcol);

        },
        colModel: [
            {
                name: 'lkno',
                index: 'lkno',
                width: 30
            },
            {
                name: 'kucun',
                index: 'kucun',
                width: 30
            },
            {
                name: 'yuliu', index: 'yuliu', width: 40,editable:true,edittype:'text',editrules: {edithidden:true,required:true,number:true}
            }
        ],
        shrinkToFit: true,
        altRows: true,
        height: 100,
        rowNum: 5
    });

    $("#ckItemList").jqGrid({
        mtype: "GET",
        datatype: "json",
        width: 210,
        colNames: ['追加','testitemid', '项目名称','英文名称','tesischarge'],
        colModel: [
            {name:'testitemid',index:'testitemid',formatter:buttonFormat,width:35},
            {name: 'testitemid', index: 'testitemid', hidden: true},
            {name: 'teschinesename', index: 'teschinesename', width: 40},
            {name: 'tesenglishname', index: 'tesenglishname', width: 40},
            {name: 'tesischarge', index: 'tesischarge', hidden: true}
        ],
        loadComplete: function () {
            var table = this;
            setTimeout(function () {
                //updatePagerIcons(table);
            }, 0);
        },
        ondblClickRow: function (id) {
        },
        viewrecords: true,
        shrinkToFit: true,
        altRows: true,
        height: 230,
        rowNum: 5,
        onSelectRow: function (id) {

        }
    });

    $("#materialList").jqGrid({
        datatype: "json",
        mtype: "GET",
        height: 'auto',
        width: 640,
        pager: "#pager2",
        colNames: ['病理号', '取材序号', '材块数', '白片数', '取材部位', '取材医生', '录入员', '取材时间', '特殊要求', '取材状态'],
        colModel: [
            {name: 'piepathologycode', index: 'piepathologycode', width: 80},//病理号
            {name: 'piesamplingno', index: 'piesamplingno', width: 60},//取材序号
            {name: 'piecounts', index: 'piecounts', width: 50},//材块数
            {name: 'pienullslidenum', index: 'pienullslidenum', width: 50},//白片数
            {
                name: 'pieparts',
                index: 'pieparts',
                width: 60,
                edittype: "select",
                formatter: "select",
                editoptions: {value: "1:肌腱;2:肺;3:肝脏"}
            },//取材部位
            {name: 'piedoctorname', index: 'piedoctorname', width: 80},//取材医生
            {name: 'pierecordername', index: 'pierecordername', width: 80},//录入员
            {
                name: 'piesamplingtime',
                index: 'piesamplingtime',
                width: 80,
                formatter: function (cellvalue, options, row) {
                    return CurentTime(new Date(cellvalue))
                }
            },//取材时间
            {name: 'piespecial', index: 'piespecial', width: 60},//特殊要求
            {
                name: 'piestate',
                index: 'piestate',
                width: 60,
                formatter: "select",
                editoptions: {value: "0:未取材;1:已取材;2:已包埋;3:已切片;4:已初诊;5:已审核"}
            }//取材状态
        ],
        loadComplete: function () {
            /*var table = this;
             setTimeout(function(){
             updatePagerIcons(table);
             }, 0);*/
        },
        shrinkToFit: false,
        scrollOffset: 2,
        rowNum: 10,
        rowList: [10, 20, 40],
        rownumbers: true // 显示行号
    });

    $("#tabs").tabs(
        {
            activate: function (event, ui) {

            }
        });
    $(document).ready(function () {
        $('ul.tabs li').click(function () {
            var tab_id = $(this).attr('data-tab');

            $('ul.tabs li').removeClass('current');
            $('.tab-content').removeClass('current');

            $(this).addClass('current');
            $("#" + tab_id).addClass('current');
        })

    })
    $("#ctabs").tabs();

    $("#samplesectionfrom").datepicker({
        changeMonth: true,
        dateFormat: "yy-mm-dd",
        monthNamesShort: ['1\u6708', '2\u6708', '3\u6708', '4\u6708', '5\u6708', '6\u6708', '7\u6708', '8\u6708', '9\u6708', '10\u6708', '11\u6708', '12\u6708'],
        dayNamesMin: ['\u65e5', '\u4e00', '\u4e8c', '\u4e09', '\u56db', '\u4e94', '\u516d'],
        onClose: function (selectedDate) {
            $("#samplesectionfrom").datepicker("option", "minDate", selectedDate);
        }
    });

    $("#samplesectionto").datepicker({
        changeMonth: true,
        dateFormat: "yy-mm-dd",
        monthNamesShort: ['1\u6708', '2\u6708', '3\u6708', '4\u6708', '5\u6708', '6\u6708', '7\u6708', '8\u6708', '9\u6708', '10\u6708', '11\u6708', '12\u6708'],
        dayNamesMin: ['\u65e5', '\u4e00', '\u4e8c', '\u4e09', '\u56db', '\u4e94', '\u516d'],
        onClose: function (selectedDate) {
            $("#datepickerf").datepicker("option", "maxDate", selectedDate);
        }
    });
    $(".form_datetime1").datetimepicker({
        //minView: "month", //选择日期后，不会再跳转去选择时分秒
        format: "yyyy-mm-dd hh:ii:ss", //选择日期后，文本框显示的日期格式
        language: 'zh-CN', //汉化
        todayBtn: 1,
        autoclose: true //选择日期后自动关闭
    });

    $('#reqDate').datepicker({
        changeMonth: true,
        dateFormat: "yy-mm-dd",
        monthNamesShort: ['1\u6708', '2\u6708', '3\u6708', '4\u6708', '5\u6708', '6\u6708', '7\u6708', '8\u6708', '9\u6708', '10\u6708', '11\u6708', '12\u6708'],
        dayNamesMin: ['\u65e5', '\u4e00', '\u4e8c', '\u4e09', '\u56db', '\u4e94', '\u516d'],
        autoclose: true //选择日期后自动关闭
    });

    //送检医生
    $("#reqDoctor").autocomplete({
        source: function (request, response) {
            $.ajax({
                url: "../basadata/ajax/item",
                dataType: "json",
                data: {
                    name: request.term,//名称
                    bddatatype: 3,//送检医生
                    bdcustomerid: $("#customerId").val()//账号所属医院
                },
                success: function (data) {
                    response($.map(data, function (result) {
                        return {
                            label: result.id + " : " + result.name,
                            value: result.name,
                            id: result.id
                        }
                    }));
                }
            });
        },
        minLength: 0,
        select: function (event, ui) {
            $("#reqDoctorId").val(ui.item.id);
            $("#reqDoctor").val(ui.item.value);
            //return false;
        }
    })
        .data("ui-autocomplete")._renderItem = function (ul, item) {
        return $("<li>")
            .append("<a style='font-size:12px;font-family: 微软雅黑;'>" + item.id + "," + item.value + "</a>")
            .appendTo(ul);
    };

    //检查项目名称
    $("#itemName").autocomplete({
        source: function (request, response) {
            $.ajax({
                url: "../estitem/querytestitem",
                dataType: "json",
                data: {
                    query: $("#itemName").val()//项目中文名称
                },
                success: function (data) {
                    response($.map(data, function (result) {
                        return {
                            label: result.teschinesename,
                            value: result.tesenglishname,
                            id: result.testitemid,
                            tesischarge: result.tesischarge
                        }
                    }));
                }
            });
        },
        minLength: 0,
        select: function (event, ui) {
            var row = {};
            row.teschinesename = ui.item.label;
            row.testitemid = ui.item.id;
            row.tesischarge = ui.item.tesischarge;
            row.tesenglishname = ui.item.value;
            appendItem(row);
            /*$("#chinesename").val(ui.item.label);
             $("#chienglishname").val(ui.item.value);
             $("#testitemid").val(ui.item.id);*/
        }
    })
        .data("ui-autocomplete")._renderItem = function (ul, item) {
        return $("<li>")
            .append("<a style='font-size:12px;font-family: 微软雅黑;'>" + item.id + "," + item.value + "</a>")
            .appendTo(ul);
    };

    getOrderItems();
})