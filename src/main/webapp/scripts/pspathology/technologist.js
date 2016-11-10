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

            //createOptions(data.patIsSampling, data.specialCheck);

            var mills = data.saminitiallytime;
            var t1;
            if (mills != null && mills != "") {
                t1 = new Date(parseInt(mills)).Format("yyyy-MM-dd hh:mm:ss")
                $("#saminitiallytime").val(t1);
            } else {
                $("#saminitiallytime").val('');
            }//初诊时间
            $("#saminitiallyuserid").val(data.saminitiallyuserid);//初诊医生编号
            $("#saminitiallyusername").val(data.saminitiallyusername);//初诊医生姓名
            if (mills != null && mills != "") {
                t1 = new Date(parseInt(mills)).Format("yyyy-MM-dd hh:mm:ss")
                $("#samauditedtime").val(t1);
            } else {
                $("#samauditedtime").val('');
            }//审核时间
            $("#samauditerid").val(data.samauditerid);//审核医生编号
            $("#samauditer").val(data.samauditer);//审核医生姓名
            mills = data.samreportedtime;
            if (mills != null && mills != "") {
                t1 = new Date(parseInt(mills)).Format("yyyy-MM-dd hh:mm:ss")
                $("#samreportedtime").val(t1);//报告时间
            } else {
                $("#samreportedtime").val('');//报告时间
            }
            $("#samreportorid").val(data.samreportorid);//报告医生编号
            $("#samreportor").val(data.samreportor);//报告医生姓名

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
    var specialCheck = $("#q_specialCheck").val();
    var startDate = $("#q_startDate").val();
    var endDate = $("#q_endDate").val();
    var pathologyCode = $("#q_pathologyCode").val();
    var patientName = $("#q_patientName").val();
    jQuery("#sectionList").jqGrid('setGridParam', {
        datatype: 'json',
        postData: {
            "specialCheck": specialCheck,
            "startDate": startDate,
            "endDate": endDate,
            "pathologyCode": pathologyCode,
            "patientName": patientName
        },
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
        colNames: ['特检类型', '医嘱号', '申请医生', 'orderId', 'ordSampleId', 'ordCustomerId', 'ordPathologyCode', 'chiOrderState', 'samPathologyId'],
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
            {name: 'samPathologyId', index: 'samPathologyId', hidden: true}
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
            if (rowData != null && rowData.ordSampleId != null && rowData.ordSampleId != "")
                getSampleData1(rowData.ordSampleId);
            GRID_SELECTED_ROW_SAMPLEID = rowData.ordSampleId;
            GRID_SELECTED_ROW_SAMPCUSTOMERID = rowData.ordSampleId;
        }
    });

    jQuery("#sectionList").jqGrid('bindKeys', {
            "onEnter": function (rowid) {
                $("#sectionList").jqGrid('setSelection', rowid);
            }
        }
    );

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
            row.tesenglishname = ui.item.value;
            row.teschinesename = ui.item.label;
            row.testitemid = ui.item.id;
            row.tesischarge = ui.item.tesischarge;
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