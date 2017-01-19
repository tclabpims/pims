var STATES = "-1";
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

function getOrderInfo(orderId, orderType) {
    $.get("../order/orderchildandcheckitem", {orderId:orderId,orderType:orderType}, function (data) {
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

        hideOrShow(orderType);

        if(orderType == "MYZH" || orderType == "FZBL" || orderType == "TSRS") {
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
        } else {
            showPieceGrid(orderId, orderType);
        }
    })
}

function showPieceGrid(orderId, orderType) {
    jQuery("#materialPieceList").jqGrid('setGridParam', {
        url:"../order/getchildlist",
        postData:{orderId:orderId}
    }).trigger('reloadGrid');
}

function hideOrShow(orderType) {
    if(orderType == "MYZH" || orderType == "FZBL" || orderType == "TSRS") {
        $("#checkItemListContainer").css('display', 'block');
        $("#checkItemCalContainer").css('display', 'block');
        $("#chargeItemListContainer").css('display', 'block');
        $("#materialPieceListContainer").css('display', 'none');
    } else {
        $("#checkItemListContainer").css('display', 'none');
        $("#checkItemCalContainer").css('display', 'none');
        $("#chargeItemListContainer").css('display', 'none');
        $("#materialPieceListContainer").css('display', 'block');
    }
}

function updateState(state) {
    var checkItems = $('#checkItemList').jqGrid('getDataIDs');
    var unchecked = false;
    for(var i = 0; i < checkItems.length; i++) {
        var r = $("#checkItemList").jqGrid('getRowData', checkItems[i]);
        if(state == 2 && r.finishStatus == 0) {
            // unchecked = true;
            // layer.alert("项目："+r.chenamech+" 还没有完成检测");
            // break;
            if(state == 2){
                var order = $("#sectionList").jqGrid('getRowData', crno);
                var orderType = order.tesenglishname,orderId = order.orderId;
                var selected, items = [],j=0;
                if(orderType == "MYZH" || orderType == "FZBL" || orderType == "TSRS") {
                    selected = $("#checkItemList").jqGrid("getDataIDs");
                    for(var i = 0; i < selected.length; i++) {
                        var rowData = $("#checkItemList").jqGrid("getRowData",selected[i]);
                        if(rowData.finishStatus == 1) continue;
                        items[j] = rowData.checkid;
                        j++;
                    }
                } else {
                    selected = $("#materialPieceList").jqGrid("getDataIDs");
                    for(var i = 0; i < selected.length; i++) {
                        var rowData = $("#materialPieceList").jqGrid("getRowData",selected[i]);
                        if(rowData.finishStatus == 1) continue;
                        items[j] = rowData.childorderid;
                        j++;
                    }
                }
                if(items.length == 0) {
                    return;
                }
                $.get("../order/updateitemstatus", {items:items.join(","), orderType:orderType, orderId:orderId}, function (data)
                    {
                        onRowSelect(crno);
                    }
                )
            }

        }
    }
    if(unchecked) return;

    var rowData = $("#sectionList").jqGrid('getRowData', crno);
    var wp = $("#chinullslidenum").val();
    if(wp > 0) {
        layer.confirm('这个医嘱需要切：'+wp + " 个白片，是否继续？", {
            btn: ['继续','取消'] //按钮
        }, function(){
            doUpdate(rowData, state);
        }, function(){

        });
    }else{doUpdate(rowData, state)};


}

function doUpdate(rowData, state) {
    $.get("../order/updateorderstate", {orderState:state, orderId:rowData.orderId}, function(data){
        rowData.chiOrderState = state;
        layer.msg("操作成功！",{icon:1,time:1000});
        query();
    });
}

function getWhitePiece(ordSampleId, orderId) {
    $.get("../diagnosis/report/paraffin", {sampleId: ordSampleId, orderId:orderId}, function (data) {
        var ret = data.rows;
        if (ret != null && ret.length > 0) {
            for(var i = 0; i < ret.length; i++) {
                var lkno = ret[i].parparaffincode;
                var chinullslidenum = ret[i].parnullslidenum;
                var chiparaffinid = ret[i].paraffinid;
                var slideNum = ret[i].slidenum;
                $("#lkItemList").jqGrid('addRowData',new Date().getTime(), {slideNum:slideNum,lkno:lkno,kucun:chinullslidenum,yuliu:ret[i].obligateslidenum, childorderid:orderId,chiparaffinid:chiparaffinid});
            }
        }
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

function query() {
    query(STATES);

}

function query(state) {
    STATES = state;
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
    if(state != -1)
        param.orderState = state;
    else param.orderState = "";
    jQuery("#sectionList").jqGrid('setGridParam', {
        datatype: 'json',
        postData: param,
        page: 1
    }).trigger('reloadGrid');//重新载入
}

var crno = 0;

function setSelect(c) {
    var o = jQuery("#sectionList");
    var total = o.jqGrid('getGridParam', 'reccount'); //获取当前页面的总记录数量
    if (total == 0) return ;
    c = parseInt(c);
    if (c == 0) {
        if(crno == 1) return ;
        if (crno > 1) {
            crno = crno - 1;
        }
    } else {
        if (parseInt(crno) + 1 > total) crno = total;
        else crno = parseInt(crno) + 1;
    }
    onRowSelect(crno);
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

function finishItem() {
    var order = $("#sectionList").jqGrid('getRowData', crno);

    /*if(order.chiOrderState == 0) {
        layer.msg('请先接收医嘱', {icon: 2, time: 1000});
        return false;
    }*/

    var orderType = order.tesenglishname,orderId = order.orderId;
    var selected, items = [],j=0;
    if(orderType == "MYZH" || orderType == "FZBL" || orderType == "TSRS") {
        selected = $("#checkItemList").jqGrid("getGridParam", "selarrrow");
        if(selected == null || selected.length == 0) {
            layer.alert("请先选择已经完成检测的项目！");
            return;
        }
        for(var i = 0; i < selected.length; i++) {
            var rowData = $("#checkItemList").jqGrid("getRowData",selected[i]);
            if(rowData.finishStatus == 1) continue;
            items[j] = rowData.checkid;
            j++;
        }
    } else {
        selected = $("#materialPieceList").jqGrid("getGridParam", "selarrrow");
        if(selected == null || selected.length == 0) {
            layer.alert("请先选择已经完成切片的蜡块！");
            return;
        }
        for(var i = 0; i < selected.length; i++) {
            var rowData = $("#materialPieceList").jqGrid("getRowData",selected[i]);
            if(rowData.finishStatus == 1) continue;
            items[j] = rowData.childorderid;
            j++;
        }
    }
    if(items.length == 0) {
        return;
    }
    $.get("../order/updateitemstatus", {items:items.join(","), orderType:orderType, orderId:orderId}, function (data)
        {
            layer.alert("设置成功！");
            onRowSelect(crno);
        }
    )
}
function setcolor(id){
    var ids = $("#sectionList").getDataIDs();
    $.each(ids, function (key, val) {
        $("#sectionList").children().children("tr[id='"+ids[key]+"']").removeClass("ui-state-highlight");
    });
    $("#sectionList").children().children("tr[id='"+id+"']").addClass("ui-state-highlight");
}

function onRowSelect(id) {
    var rowData = $("#sectionList").jqGrid('getRowData', id);
    getSampleData1(rowData.ordSampleId);
    getOrderInfo(rowData.orderId, rowData.tesenglishname);
    $("#lkItemList").jqGrid('clearGridData');
    getWhitePiece(rowData.ordSampleId,rowData.orderId);
    var state = rowData.chiOrderState;
    if(state >= 1 ) {
        $("#btAccept").attr("disabled", "disabled");
        if(state == 1)
            $("#btFinish").removeAttr("disabled");
        else if(state >= 2)
            $("#btFinish").attr("disabled", "disabled");
    } else {
        $("#btFinish").attr("disabled", "disabled");
        $("#btAccept").removeAttr("disabled");
    }
    setcolor(id);
    crno = id;
}
$(function () {
    $(window).on('resize.jqGrid', function () {
        $('#sectionList').jqGrid('setGridWidth', $(".leftContent").width(), false);
    });
//    var clientHeight = $(window).innerHeight();
//    var height = clientHeight - $('#head').height() - $('#toolbar').height() - $('.footer-content').height() - 150;
    var listwidth = $("#searchcontent").width();

    var height =$("#diagnosis").height()  - $(".widget-box.widget-color-green.ui-sortable-handle").height()-41-35;
           if(height < 340){
               height = 340;
           }
        $("body").click(function(){
            height = $("#diagnosis").height() - $(".widget-box.widget-color-green.ui-sortable-handle").height()-35-41;
            $("#sectionList").setGridHeight(height);
            if(height < 340){
                height = 340;
            }
         })
    $("#sectionList").jqGrid({
        caption: "",
        url: "../order/getorders?ingore=BUQU",
        mtype: "GET",
        datatype: "json",
        width: $('.leftContent').width(),
        postData: {
            specialCheck: $("#q_specialCheck").val(),
            pathologyCode: $("#q_pathologyCode").val(),
            startDate: $("#q_startDate").val(),
            endDate: $("#q_endDate").val(),
            patientName: $("#q_patientName").val()
        },
        colNames: ['tesenglishname','特检类型', '医嘱号', '申请医生', 'orderId', 'ordSampleId', 'ordCustomerId', 'ordPathologyCode', 'chiOrderState', 'samPathologyId'],
        colModel: [
            {
                name: 'tesenglishname',
                index: 'tesenglishname',
                hidden:true
            },
            {
                name: 'chiOrderType',
                index: 'chiOrderType',
                width: 30,align:"center"
            },
            {name: 'orderCode', index: 'orderCode', width: 40,align:"center"},
            {name: 'ordOrderUser', index: 'ordOrderUser', width: 40,align:"center"},
            {name: 'orderId', index: 'orderId', hidden: true},
            {name: 'ordSampleId', index: 'ordSampleId', hidden: true},
            {name: 'ordCustomerId', index: 'ordCustomerId', hidden: true},
            {name: 'ordPathologyCode', index: 'ordPathologyCode', hidden: true},
            {name: 'chiOrderState', index: 'chiOrderState', hidden: true},
            {name: 'samPathologyId', index: 'samPathologyId', hidden: true}
        ],
        loadComplete: function () {
            var table = this;
            setTimeout(function () {
                updatePagerIcons(table);
            }, 0);
            var ids = $("#sectionList").jqGrid('getDataIDs');
            if(ids != null && ids != ""){
                crno = 1;
                onRowSelect(1);
            }
        },
        ondblClickRow: function (id) {
        },
        viewrecords: true,
        shrinkToFit: true,
        altRows: true,
        height: height,
        width:listwidth,
        rowNum: 10,
        multiselect:true,
        rowList: [10, 20, 30],
        rownumbers: true, // 显示行号
        rownumWidth: 35, // the width of the row numbers columns
        pager: "#pager",
        beforeSelectRow: function (rowid, e) {
            return $(e.target).is('input[type=checkbox]');
        },
        onCellSelect:function(id){
            onRowSelect(id);
        },
        onSelectRow: function (id) {
        }
    });

    jQuery("#sectionList").jqGrid('bindKeys', {
            "onEnter": function (rowid) {
                $("#sectionList").jqGrid('setSelection', rowid);
            }
        }
    );

    $("#materialPieceList").jqGrid({
        datatype: "json",
        mtype: "GET",
        colNames: ['childorderid','蜡块编号', '常规片数', '白片数', '备注','状态'],
        colModel: [
            {name: 'childorderid', index: 'childorderid', hidden: true},
            {name: 'chiparaffincode', index: 'chiparaffincode', width: 85},
            {name: 'chislidenum', index: 'chislidenum', hidden: true},
            {name: 'chinullslidenum', index: 'chinullslidenum', width: 80},
            {name: 'chicontent', index: 'chicontent', width: 60},
            {name: 'finishStatus', index: 'finishStatus', width: 60,formatter:"select",editoptions: {value: "0:未完成;1:已完成"}}
        ],
        loadComplete: function () {

        },
        multiselect:true,
        shrinkToFit: true,
        scrollOffset: 2,
        rowNum: 10,
        rownumbers: true // 显示行号
    });

    $("#checkItemList").jqGrid({
        datatype: "json",
        mtype: "GET",
        colNames: ['checkid','蜡块编号', '项目名称', '申请医生', '状态'],
        colModel: [
            {name: 'checkid', index: 'checkid', hidden: true},
            {name: 'paraffincode', index: 'paraffincode', width: 85},
            {name: 'chenamech', index: 'chenamech', width: 80},
            {name: 'chirequsername', index: 'chirequsername', width: 80},
            {name: 'finishStatus', index: 'finishStatus', width: 60, formatter:"select",editoptions: {value: "0:未完成;1:已完成"}}
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
    var width=$("#takingPicture").width();
    $("#lkItemList").jqGrid({
        datatype: "json",
        width: width,
        cellEdit: true,
        cellsubmit:'clientArray',
        colNames: ['蜡块编号','库存', '预留','切片数','childorderid','chiparaffinid'],
        afterEditCell:function(rowid,name,val,iRow,iCol){
            //$("#lkItemList").jqGrid('setSelection',rowid);
            $('#lkItemList').jqGrid('saveCell',$("#lkItemList").jqGrid.editrow,$("#lkItemList").jqGrid.editcol);

        },
        colModel: [
            {
                name: 'lkno',
                index: 'lkno',
                width: 55
            },
            {
                name: 'kucun',
                index: 'kucun',
                width: 40
            },
            {
                name: 'yuliu', index: 'yuliu', width: 45,editable:true,edittype:'text',editrules: {edithidden:true,required:true,number:true}
            }
            ,
            {
                name: 'slideNum',
                index: 'slideNum',
                width: 50
            },
            {
                name: 'childorderid',
                index: 'childorderid',
                hidden: true
            },
            {
                name: 'chiparaffinid',
                index: 'chiparaffinid',
                hidden: true
            }
        ],
        shrinkToFit: true,
        altRows: true,
        height: 100,
        rowNum: 5
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

    $("#q_startDate").datepicker({
        changeMonth: true,
        dateFormat: "yy-mm-dd",
        monthNamesShort: ['1\u6708', '2\u6708', '3\u6708', '4\u6708', '5\u6708', '6\u6708', '7\u6708', '8\u6708', '9\u6708', '10\u6708', '11\u6708', '12\u6708'],
        dayNamesMin: ['\u65e5', '\u4e00', '\u4e8c', '\u4e09', '\u56db', '\u4e94', '\u516d'],
        onClose: function (selectedDate) {
            $("#samplesectionfrom").datepicker("option", "minDate", selectedDate);
        }
    });

    $("#q_endDate").datepicker({
        changeMonth: true,
        dateFormat: "yy-mm-dd",
        monthNamesShort: ['1\u6708', '2\u6708', '3\u6708', '4\u6708', '5\u6708', '6\u6708', '7\u6708', '8\u6708', '9\u6708', '10\u6708', '11\u6708', '12\u6708'],
        dayNamesMin: ['\u65e5', '\u4e00', '\u4e8c', '\u4e09', '\u56db', '\u4e94', '\u516d'],
        onClose: function (selectedDate) {
            $("#samplesectionfrom").datepicker("option", "minDate", selectedDate);
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
    // $("#itemName").autocomplete({
    //     source: function (request, response) {
    //         $.ajax({
    //             url: "../estitem/querytestitem",
    //             dataType: "json",
    //             data: {
    //                 query: $("#itemName").val()//项目中文名称
    //             },
    //             success: function (data) {
    //                 response($.map(data, function (result) {
    //                     return {
    //                         label: result.teschinesename,
    //                         value: result.tesenglishname,
    //                         id: result.testitemid,
    //                         tesischarge: result.tesischarge
    //                     }
    //                 }));
    //             }
    //         });
    //     },
    //     minLength: 0,
    //     select: function (event, ui) {
    //         var row = {};
    //         row.tesenglishname = ui.item.value;
    //         row.teschinesename = ui.item.label;
    //         row.testitemid = ui.item.id;
    //         row.tesischarge = ui.item.tesischarge;
    //         appendItem(row);
    //         /*$("#chinesename").val(ui.item.label);
    //          $("#chienglishname").val(ui.item.value);
    //          $("#testitemid").val(ui.item.id);*/
    //     }
    // })
    //     .data("ui-autocomplete")._renderItem = function (ul, item) {
    //     return $("<li>")
    //         .append("<a style='font-size:12px;font-family: 微软雅黑;'>" + item.id + "," + item.value + "</a>")
    //         .appendTo(ul);
    // };

    getOrderItems();
})

function printCode() {
    //打印标本条码号
    var ids = $("#sectionList").jqGrid('getGridParam', 'selarrrow');
    if(ids == null || ids == ""){
        layer.msg("请选择打印数据!", {icon:2, time: 1000});
        return;
    }
    var saveDatas = [];
    $.each(ids, function (key, val) {
        var rowData = $("#sectionList").jqGrid("getRowData", ids[key]);
        saveDatas.push(rowData);
    });
    $.post("../pathologysample/slide/yzprintcode",{samples:JSON.stringify(saveDatas)},function(data){
        data = jQuery.parseJSON(data);
        startPrint(data);
        // }
    });
}

var LODOP; //声明为全局变量

function Preview() {//打印预览
    LODOP = getLodop();
    CreateDataBill(data)
    LODOP.PREVIEW();
}
function Setup() {//打印维护
    LODOP = getLodop();
    LODOP.PRINT_SETUP();
}
function CreateDataBill(datas) {
    LODOP = getLodop();
    LODOP.PRINT_INIT("");
    LODOP.SET_PRINT_PAGESIZE(1,"80mm","24mm","A4");
    for(i=0;i<datas.labOrders.length;i++){
        var data = datas.labOrders[i];
        // var topheight1 = Math.floor(i/3)*24+ 3;
        // var topheight2 = Math.floor(i/3)*24+ 8;
        var leftwidth1 = 3;
        // if(i<3){
        // 	if(i%3 == 0){
        // 		leftwidth1 = 3;
        // 	}else if(i%3 == 1){
        // 		leftwidth1 = 30;
        // 	}else if(i%3 == 2){
        // 		leftwidth1 = 57;
        // 	}
        // }else{
        // 	if(i%3 == 0){
        // 		leftwidth1 = 1;
        // 	}else if(i%3 == 1){
        // 		leftwidth1 = 28;
        // 	}else if(i%3 == 2){
        // 		leftwidth1 = 55;
        // 	}
        // }
        if(i%3 == 0){
            leftwidth1 = 3;
        }else if(i%3 == 1){
            leftwidth1 = 30;
        }else if(i%3 == 2){
            leftwidth1 = 57;
        }

        LODOP.ADD_PRINT_TEXT("3mm",leftwidth1+"mm","27mm","5mm","浙大国际医院");
        LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
        LODOP.SET_PRINT_STYLEA(0,"Bold",1);
        // LODOP.ADD_PRINT_BARCODEA("patientCode","21.98mm","27.01mm","46.57mm",40,"128B",data.sampathologycode); slisamplingparts
        // LODOP.SET_PRINT_STYLEA(0,"Horient",2);
        if(data.barcode.length >15){
            LODOP.ADD_PRINT_TEXT("8mm",leftwidth1+"mm","27mm","8mm",data.barcode);
            LODOP.SET_PRINT_STYLEA(0,"FontSize",7);
            LODOP.SET_PRINT_STYLEA(0,"Bold",1);
            if(data.slisamplingparts != null && data.slisamplingparts != ""){
                LODOP.ADD_PRINT_TEXT("16mm",leftwidth1+"mm","24mm","10mm",data.slisamplingparts);
                LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
                LODOP.SET_PRINT_STYLEA(0,"Bold",1);
            }
        }else{
            LODOP.ADD_PRINT_TEXT("8mm",leftwidth1+"mm","27mm","4mm",data.barcode);
            LODOP.SET_PRINT_STYLEA(0,"FontSize",7);
            LODOP.SET_PRINT_STYLEA(0,"Bold",1);
            if(data.slisamplingparts != null && data.slisamplingparts != ""){
                LODOP.ADD_PRINT_TEXT("12mm",leftwidth1+"mm","24mm","10mm",data.slisamplingparts);
                LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
                LODOP.SET_PRINT_STYLEA(0,"Bold",1);
            }
        }

        if(i%3 == 2 || i == datas.labOrders.length -1){
            LODOP.PRINT();
            // LODOP.PREVIEW();
        }
    }

}
function startPrint(data) {
    CreateDataBill(data);
    //开始打印
    // LODOP.PRINT();
    // LODOP.PREVIEW();
    // LODOP.PRINT_SETUP();
}

