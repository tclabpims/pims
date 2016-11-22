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
var PIC_TAKING_FROM = 2;
var targetTextareaId;
var GRID_SELECTED_ROW_SAMPLEID;
var GRID_SELECTED_ROW_SAMPCUSTOMERID;

function showTemplate(v, target) {
    targetTextareaId = target;
    var a = $(function () {
        jQuery("#templateList").jqGrid('setGridParam', {
            url: "../diagnosis/getpathologytemp",
            mtype: "GET",
            datatype: "json",
            postData: {
                "type": v
            },
            page: 1
        }).trigger('reloadGrid');//重新载入
    })
}

function createImgElement(src) {
    var ret = jQuery.parseJSON(src);
    var container = $("#imgContainer");
    var c = ret.continuous;
    if (c == "false") {
        container.empty();
    }
    var objNewDiv = $('<div>', {'id': 'mydiv', 'style': 'padding-bottom:5px'});
    objNewDiv.html("<img src='" + ret.src + "' width='220' onclick='removePicture(\"" + ret.name + "\")' height='150'>");
    container.append(objNewDiv);
}

function saveAsTemplate(v, obj) {
    $('#temkey').val('');
    $('#tempinyin').val('');
    $('#temfivestroke').val('');
    $('#temspellcode').val('');
    $('#temsort').val('');
    $("#FN").val('0');
    $("#SN").val('0');
    $("#TN").val('0');
    $("#temcontent").val(document.getElementById(obj).value);
    layer.open({
        type: 1,
        area: ['800px', '500px'],
        fix: false, //不固定
        maxmin: true,
        shade: 0.5,
        title: "模板",
        content: $('#templateForm'),
        btn: ["确定", "取消"],
        yes: function (index, layero) {
            $.post('../diagnosis/edit', {
                temcontent: $('#temcontent').val(),
                temtype: $('#owner').attr("checked") == true ? 1 : 0, temclass: v,
                temkey: $('#temkey').val(),
                temfivestroke: $('#temfivestroke').val(), temspellcode: $('#temspellcode').val(),
                tempinyin: $('#tempinyin').val(), temsort: "A" + $("#FN").val() + $("#SN").val() + $("#TN").val()
            }, function (data) {
                layer.close(index);
            });
        }
    })

}

function saveDiagnosisInfo() {
    var x = document.getElementById("diagnosisInfoForm");
    var rowData = $("#sectionList").jqGrid('getRowData', crno);
    var result = [];
    var j = 0;
    for (var i = 0; i < x.length; i++) {
        if (x.elements[i].type == 'textarea') {
            var e = x.elements[i];
            var data = {};
            data.resultid = $("#" + e.id).attr("hiddenValue");
            data.resviewtype = e.type;
            data.restestresult = e.value;
            data.restestitemid = $("#" + e.id).attr("rptItemId");
            data.resviewtitle = e.placeholder;
            data.resviewsort = $("#" + e.id).attr("printOrder");
            data.resinputsort = $("#" + e.id).attr("showOrder");
            data.ressampleid = rowData.sampleid;
            result[j] = data;
            j++;
        }
    }
    $.post('../diagnosis/saveResult', {result: JSON.stringify(result)}, function (data) {
        for (var i = 0; i < x.length; i++) {
            if (x.elements[i].type == 'textarea') {
                var e = x.elements[i];
                var restestitemid = $("#" + e.id).attr("rptItemId");
                if (data[restestitemid]) {
                    $("#" + e.id).attr("hiddenValue", data[restestitemid]);
                }
            }
        }
        layer.msg('保存成功！', {icon: 2, time: 1000});
    });
}

function takingPicture() {
    layer.open({
        type: 2,
        title: '病理诊断>图像采集',
        shadeClose: false,
        shade: 0.5,
        closeBtn: false,
        maxmin: false, //开启最大化最小化按钮
        area: ['320px', '360px'],
        content: ["../diagnosis/camera"],
        btn: ["关闭"],
        yes: function (index, layero) {
            //swfobject.removeSWF("Main");
            layer.close(index); //如果设定了yes回调，需进行手工关闭
            // console.log(layero)
        }
    });
}

function importImg() {
    layer.open({
        type: 1,
        title: '病理诊断>图像导入',
        shadeClose: true,
        shade: false,
        maxmin: true, //开启最大化最小化按钮
        area: ['260px', '300px'],
        content: $('#uploadDialog'),
        btn: ["确定", "取消"],
        yes: function (index, layero) {
            if ($("#imgFile").val().length > 0) {
                ajaxFileUpload();
                layer.close(index);
            }
            else {
                layer.msg('请先选择图片', {icon: 2, time: 1000});
                return false;
            }
        }
    });
}

function delayReport(){
    $("#chipathologycode").val($("#sampathologycode").val());
    $("#testItemChName").val($("#saminspectionid").val());
    $("#chireqtime").val($("#samsenddoctorid").val());
    $("#chiordercode").val($("#sampatientname").val());
    $("#chirequsername").val($("#samdeptname").val());
    $("#chinullslidenum").val($("#samsendhospital").val());
    $("#patientAgent").val($("#sampatientgender").val());
    $("#patientAge1").val($("#sampatientage").val());
    layer.open({
        type: 1,
        title: '病理诊断>延迟报告申请',
        shadeClose: false,
        shade: 0.5,
        maxmin: false, //开启最大化最小化按钮
        area: ['800px', '300px'],
        content: $('#delayReportForm'),
        btn: ["保存并发送","取消"],
        success: function () {

        },
        yes: function (index, layero) {
            var delreasonid = $("#delreasonid").val();
            var deldays = $("#deldays").val();
            var delreporttime = $("#delreporttime").val();
            var deldiagnosis = $("#deldiagnosis").val();
            var delreason = $("#delreason").val();
            if(delreasonid == "") return layer.alert("请选择一个延迟原因");
            if(delreporttime == "") return layer.alert("请选择报告日期");
            if(jQuery.trim(deldiagnosis) == "") return layer.alert("请填写初步诊断");
            if(jQuery.trim(delreason) == "") return layer.alert("请填写延迟原因");
            if(jQuery.trim(deldays) == "" || (/\d/.test(jQuery.trim(deldays))) == false) return layer.alert("请填写正确的延迟天数");
            var rowData = $("#sectionList").jqGrid("getRowData", crno);
            $.get("../reportdelay/save", {delreasonid:delreasonid, deldays:deldays, delreporttime:delreporttime,
                deldiagnosis:deldiagnosis,delreason:delreason, delsampleid:GRID_SELECTED_ROW_SAMPLEID,
                delcustomerid:GRID_SELECTED_ROW_SAMPCUSTOMERID, delpathologycode:rowData.sampathologycode }, function(data){
                layer.alert("延迟报告申请已发送！");
                layer.close(index);
            });
        }
    });
}

function ajaxFileUpload() {
    $.ajaxFileUpload({
        url: '../diagnosis/uploadimg',
        secureuri: false,
        fileElementId: ["imgFile"],
        dataType: 'json',
        data: {sampleid: GRID_SELECTED_ROW_SAMPLEID, samcustomerid: GRID_SELECTED_ROW_SAMPCUSTOMERID,picpictureclass: PIC_TAKING_FROM},
        success: function (data, status) {
            layer.alert('上传成功！');
            onRowSelect(crno);
        },
        error: function (data, status, e) {

        }
    });
    return false;
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
function clearData() {
    $('#fieelementtype').val('input');
    $('#fieelementid').val('');
    $('#fieelementname').val('');
    $('#fieshowlevel').val('');
    $('#fiepelementid').val('');
    $('#fiedefaultvalue').val('');
    $('#fieshoworder').val('');
    $('#fieuseflag').val('1');
    $('#fieremark').val('');
}

function doctorSign(f) {
    var reportTime = $("#samreportedtime").val();
    if (f == 1 || f == 3 || (f == 0 && reportTime != '')) {
        $.get("../diagnosis/signdoctor", {}, function (data) {
            if (f == 1) {
                $("#saminitiallyusername").val(data.name);
                $("#saminitiallytime").val(data.time);
                $("#saminitiallyuserid").val(data.id);
            } else if (f == 3) {
                $("#samauditer").val(data.name);
                $("#samauditedtime").val(data.time);
                $("#samauditerid").val(data.id);
            } else {
                $("#samreportor").val(data.name);
                $("#samreportorid").val(data.id);
            }
        });
    }

    if (f == 2 || f == 4) {
        if (f == 2) {
            $("#saminitiallyusername").val('');
            $("#saminitiallytime").val('');
            $("#saminitiallyuserid").val('');
        } else {
            $("#samauditer").val('');
            $("#samauditedtime").val('');
            $("#samauditerid").val('');
        }
    }
}

function saveSign() {
    var rowData = $("#sectionList").jqGrid('getRowData', crno);
    $.post('../diagnosis/saveSign', {
        sampleid: rowData.sampleid,
        saminitiallytime: $("#saminitiallytime").val(),
        saminitiallyuserid: $("#saminitiallyuserid").val(),
        saminitiallyusername: $("#saminitiallyusername").val(),
        samauditedtime: $("#samauditedtime").val(),
        samauditerid: $("#samauditerid").val(),
        samauditer: $("#samauditer").val(),
        samreportedtime: $("#samreportedtime").val(),
        samreportorid: $("#samreportorid").val(),
        samreportor: $("#samreportor").val()
    }, function (data) {
        layer.msg('保存成功！', {icon: 2, time: 1000});
    })
}

function createOptions(pathologyid, patIsSampling, specialCheck) {
    $("#yizhugl").empty();
    $.get("../estitem/orderitem", {
        pathologyId: pathologyid,
        patIsSampling: patIsSampling,
        specialCheck: specialCheck
    }, function (data) {
        if (data != null && data.length > 0) {
            for (var i = 0; i < data.length; i++) {
                var opt_ = "<option testitemid='" + data[i].testitemid + "' value='" + data[i].tesenglishname + "'>" + data[i].teschinesename + "</option>";
                $("#yizhugl").append(opt_);
            }
        }
    });
}

function reqyizhu() {
    var yizhutype = $("#yizhugl").val();
    if (yizhutype == null) {
        layer.msg("请先选择医嘱类型！", {icon: 0, time: 1000});
        return false;
    }
    var rowData = $("#sectionList").jqGrid('getRowData', crno);
    layer.open({
        type: 1,
        title: '病理诊断>病理医嘱申请',
        shadeClose: false,
        shade: 0.5,
        maxmin: false, //开启最大化最小化按钮
        area: ['800px', '620px'],
        content: $('#specialCheckDialog'),
        btn: ["保存", "取消"],
        success: function () {
            $("#patientZyh").val($("#sampatientnumber").val());//住院卡号/门诊卡号 11
            $("#patientName").val($("#sampatientname").val());//姓名 11
            $("#patientGender").val($("#sampatientgender").val());//患者性别(1男,2女,3未知)
            $("#patientDiagnosisNote").val($("#sampatientdignoses").val());//临床诊断 11
            $("#patientBed").val($("#sampatientbed").val());//患者床号
            $("#patientAge").val($("#sampatientage").val());//年龄
            $("#yblNo").val(rowData.sampathologycode);//病理号
            $("#ytxm").val($("#saminspectionid").val());//条码号
            $("#reqType").val($("#yizhugl").find("option:selected").text());//检查类型

            $("#sampleid").val(rowData.sampleid);
            $("#customerId").val(rowData.samcustomerid);
            $("#pathologyCode").val(rowData.sampathologycode);

            $("#reqDate").val('');
            $("#reqDoctor").val('');
            $("#reqDoctorId").val('');

            var code = new Date().Format("yyyyMMdd") + "-" + $("#customerId").val() + "-" + $("#sampleid").val();
            code = code + "-" + parseInt((Math.random() * 9000 + 1000), 10);
            $("#ordercode").val(code);

            initOrderForm(yizhutype, rowData.sampleid, rowData.sampathologyid);
        },
        yes: function (index, layero) {
            requestOrder(index);
        }
    });

}

function initOrderForm(type, sampleid, sampathologyid) {
    if (type == 'MYZH' || type == 'TSRS' || type == 'FZBL' || type == 'CHONGQIE' || type == 'SHENQIE') {
        $("#lkxz").empty();
        $("#lkItemList").jqGrid('clearGridData');
        $.get("../diagnosis/report/paraffin", {sampleId: sampleid}, function (data) {
            var ret = data.rows;
            if (ret != null && ret.length > 0) {
                $("#lkxz").append("<option value=''></option>");
                for (var i = 0; i < ret.length; i++) {
                    $("#lkxz").append("<option value='" + ret[i].paraffinid + "' parnullslidenum='" + ret[i].parnullslidenum + "'>" + ret[i].parparaffincode + "</option>");
                }
            }
        });
        $("#leftBottomContainer").css('display', 'block');
        $("#lakuaiListContainer").css('display', 'block');
        $("#whitePieceTitle").css('display', 'block');
        if (type == 'MYZH' || type == 'TSRS' || type == 'FZBL') {
            getPackageItems(sampathologyid);
            $("#itemListContainer").css('display', 'block');
            $("#packageContainer").css('display', 'block');
            $("#rightListContainer").css('display', 'block');
            $("#pieceListContainer").css('display', 'none');
            $("#addMaterial").css('display', 'none');
            $("#ckItemList").jqGrid('clearGridData');
            $("#itemList").jqGrid('clearGridData');
            $("#lkItemList").jqGrid('clearGridData');
        } else if (type == 'CHONGQIE' || type == "SHENQIE") {
            $("#pieceListContainer").css('display', 'block');
            $("#itemListContainer").css('display', 'none');
            $("#packageContainer").css('display', 'none');
            $("#rightListContainer").css('display', 'none');
            $("#addMaterial").css('display', 'none');
            $("#pieceList").jqGrid('clearGridData');
            initPieceList();
        }
    } else {
        $("#pieceListContainer").css('display', 'none');
        $("#itemListContainer").css('display', 'none');
        $("#packageContainer").css('display', 'none');
        $("#rightListContainer").css('display', 'none');
        $("#leftBottomContainer").css('display', 'none');
        $("#lakuaiListContainer").css('display', 'none');
        $("#whitePieceTitle").css('display', 'none');
        $("#addMaterial").css('display', 'block');
        $("#new1").jqGrid('clearGridData');
    }
}

function initPieceList() {
    $("#pieceList").jqGrid({
        datatype: "json",
        cellEdit: true,
        cellsubmit: 'clientArray',
        afterSaveCell: function (rowid, name, val, iRow, iCol) {
            var row = $("#pieceList").jqGrid("getRowData", rowid);
            setRemark(row.parparaffincode, val);
        },
        colNames: ['蜡块编号', '取材医生', '备注'],
        colModel: [
            {name: 'parparaffincode', index: 'parparaffincode', width: 90},
            {name: 'piedoctorname', index: 'piedoctorname', width: 120},
            {
                name: 'chicontent',
                index: 'chicontent',
                width: 120,
                editable: true,
                edittype: 'text',
                editrules: {edithidden: true}
            }
        ],
        shrinkToFit: true,
        scrollOffset: 2
    });
}

function requestOrder(lindex) {
    var reqDoctor = $("#reqDoctor").val();
    var reqDoctorId = $("#reqDoctorId").val();
    var reqDate = $("#reqDate").val();
    var paraffinCode = $("#lkxz").find("option:selected").text();
    var sampleId = $("#sampleid").val();
    var customerId = $("#customerId").val();
    var pathologyCode = $("#pathologyCode").val();
    var ordercode = $("#ordercode").val();
    var paraffinId = $("#lkxz").val();
    var testItemId = $("#yizhugl").find("option:selected").attr("testitemid");
    var orderType = $("#yizhugl").val();

    if ($.trim(sampleId) == "") {
        layer.alert("标本号不存在", {icon: 1, title: "提示"});
        return false;
    }
    if ($.trim(customerId) == "") {
        layer.alert("客户号不存在", {icon: 1, title: "提示"});
        return false;
    }
    if ($.trim(pathologyCode) == "") {
        layer.alert("病理号不存在", {icon: 1, title: "提示"});
        return false;
    }
    if ($.trim(reqDoctor) == "" || $.trim(reqDoctorId) == "") {
        layer.alert("请选择申请医生", {icon: 1, title: "提示"});
        return false;
    }
    if ($.trim(reqDate) == "") {
        layer.alert("请选择申请日期", {icon: 1, title: "提示"});
        return false;
    }
    if (orderType != "BUQU") {
        if ($.trim(paraffinCode) == "") {
            layer.alert("请选择蜡块", {icon: 1, title: "提示"});
            return false;
        }
    }
    var items = [];
    var rowIds;
    var paraffinItems = [];
    if (orderType == 'MYZH' || orderType == 'TSRS' || orderType == 'FZBL') {
        rowIds = $("#itemList").jqGrid("getDataIDs")
        if (rowIds.length == 0) {
            layer.alert("请选择检查项目", {icon: 1, title: "提示"});
            return false;
        }
        for (var i = 0; i < rowIds.length; i++) {
            items[i] = $("#itemList").jqGrid("getRowData", rowIds[i]);
        }
    } else if (orderType == 'SHENQIE' || orderType == 'CHONGQIE') {
        rowIds = $("#pieceList").jqGrid("getDataIDs")
        if (rowIds.length == 0) {
            layer.alert("请先添加蜡块", {icon: 1, title: "提示"});
            return false;
        }
        for (var i = 0; i < rowIds.length; i++) {
            items[i] = $("#pieceList").jqGrid("getRowData", rowIds[i]);
        }
    } else {
        rowIds = $("#new1").jqGrid("getDataIDs")
        if (rowIds.length == 0) {
            layer.alert("请先增加材块", {icon: 1, title: "提示"});
            return false;
        }
        for (var i = 0; i < rowIds.length; i++) {
            items[i] = $("#new1").jqGrid("getRowData", rowIds[i]);
        }
    }
    if (orderType != "BUQU") {
        var lkrows = $("#lkItemList").jqGrid("getDataIDs");
        for (var i = 0; i < lkrows.length; i++) {
            paraffinItems[i] = $("#lkItemList").jqGrid("getRowData", lkrows[i]);
        }
    }

    var dataRow = $("#sectionList").jqGrid("getRowData", crno);

    //alert("JSON.stringify(items):" + JSON.stringify(items));
    /*alert("JSON.stringify(paraffinItems):" + JSON.stringify(paraffinItems));
     return;*/
    $.get("../order/save", {
        reqDoctor: reqDoctor, reqDoctorId: reqDoctorId,
        reqDate: reqDate,pathologyId:dataRow.sampathologyid,
        paraffinCode: paraffinCode, sampleId: sampleId,
        customerId: customerId, pathologyCode: pathologyCode,
        orderCode: ordercode, testItemId: testItemId,
        paraffinId: paraffinId, items: JSON.stringify(items),
        orderType: orderType, paraffinItems: JSON.stringify(paraffinItems)
    }, function (data) {
        layer.close(lindex);
    })
}

function getItemInfo(v) {
    $("#ckItemList").jqGrid('clearGridData');
    if (v == null || v == "") return;
    $.get("../package/testitems", {packageId: v}, function (data) {
        var ret = data.rows;
        if (ret != null && ret.length > 0) {
            for (var i = 0; i < ret.length; i++) {
                $("#ckItemList").jqGrid("addRowData", i, ret[i]);
            }
        }
    })
}

function getPackageItems(pid) {
    $("#itemPackage").empty();
    if (pid != null && pid != "") {
        $.get("../package/packageitems", {pathologyId: pid}, function (data) {
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

function getWhitePiece() {
    var paraffinId = $("#lkxz").val();
    if (paraffinId == "") return;
    var v = $("#lkxz").find("option:selected").text();
    var parnullslidenum = $("#lkxz").find("option:selected").attr("parnullslidenum");
    var dataId = $("#lkItemList").jqGrid("getDataIDs");
    var rowId = 1;
    var d1 = {lkno: v, kucun: parnullslidenum, yuliu: 0, lkid: paraffinId};
    if (dataId.length > 0) rowId += dataId.length;
    if (!isAdded(d1, dataId)) $("#lkItemList").jqGrid('addRowData', rowId, d1);
    var orderType_ = $("#yizhugl").val();
    if (orderType_ == 'CHONGQIE' || orderType_ == 'SHENQIE') {
        $.get("../order/getparaffinmaterial", {paraffinId: paraffinId}, function (ret) {
            var dataIDs = $("#pieceList").jqGrid("getDataIDs");
            var rows = ret.rows;
            if (rows.length > 0) {
                var rowId_ = 1;
                if (dataIDs.length > 0) {
                    rowId_ += dataIDs.length;
                }
                for (var i = 0; i < rows.length; i++) {
                    var exsits = false;
                    for (var j = 0; j < dataIDs.length; j++) {
                        var erow = $("#pieceList").jqGrid("getRowData", dataIDs[j]);
                        if (erow.parparaffincode == rows[i].parparaffincode) {
                            exsits = true;
                            break;
                        }
                    }
                    if (!exsits) {
                        $("#pieceList").jqGrid("addRowData", rowId_, rows[i], "last");
                    }
                    setTotalNumValue(v);
                }
            }
        });
    }
}

function isAdded(rows, dataIDs) {
    var exsits = false;
    for (var j = 0; j < dataIDs.length; j++) {
        var erow = $("#lkItemList").jqGrid("getRowData", dataIDs[j]);
        if (erow.lkno == rows.lkno) {
            exsits = true;
            break;
        }
    }
    return exsits;
}


function getOrderTabs(sampleId) {
    var ul = $("#tabPanel");
    $.get("../order/getrequestedorder", {sampleId: sampleId}, function (data) {
        var ret = data.rows;
        var myzh = document.getElementById("order_MYZH");
        var fzbl = document.getElementById("order_FZBL");
        var tsrs = document.getElementById("order_TSRS");
        if (myzh != null)myzh.parentNode.removeChild(myzh);
        if (fzbl != null)fzbl.parentNode.removeChild(fzbl);
        if (tsrs != null)tsrs.parentNode.removeChild(tsrs);
        if (ret.length > 0) {
            for (var i = 0; i < ret.length; i++) {
                if (ret[i].tesenglishname == "MYZH" || ret[i].tesenglishname == "FZBL" || ret[i].tesenglishname == "TSRS") {
                    var li_ = "<li itemId=\"order_" + ret[i].testitemid + "\" id=\"order_" + ret[i].tesenglishname + "\" tesischarge=\"" + ret[i].tesischarge + "\"><a href=\"#tabs-" + ret[i].tesenglishname + "\">" + ret[i].teschinesename + "</a></li>";
                    //alert(li_);
                    ul.append(li_);
                    //$("#"+ret[i].tesenglishname+"Item").jqGrid('GridUnload');
                    initItemList(ret[i].tesenglishname, sampleId, ret[i].testitemid);
                }
            }
        }
        $("#ctabs").tabs("refresh");
    });
    getSampleData1(sampleId);
}

function initItemList(n, sampleId, testItemId) {
    $("#" + n + "Item").jqGrid({
        mtype: "GET",
        url: "../order/getcheckitems?sampleId=" + sampleId + "&testItemId=" + testItemId,
        datatype: "json",
        cellEdit: true,
        cellsubmit: 'clientArray',
        colNames: ['checkid', '蜡块编号', '项目名称', '结果', '申请医生', '状态', '申请时间', 'chenameen', 'cheischarge'],
        colModel: [
            {name: 'checkid', index: 'checkid', hidden: true},
            {name: 'chiparaffincode', index: 'chiparaffincode', width: 90},
            {name: 'chenamech', index: 'chenamech', width: 120},
            {
                name: 'chetestresult',
                index: 'chetestresult',
                width: 120,
                editable: true,
                edittype: 'text',
                editrules: {edithidden: true, required: true}
            },
            {name: 'checreateuser', index: 'checreateuser', width: 80},
            {
                name: 'finishstatus',
                index: 'finishstatus',
                width: 60,
                formatter: "select",
                editoptions: {value: "0:未完成;1:已完成;"}
            },
            {name: 'checreatetime', index: 'checreatetime', width: 100},
            {name: 'chenameen', index: 'chenameen', hidden: true},
            {name: 'cheischarge', index: 'cheischarge', hidden: true}
        ],
        multiselect: true,
        shrinkToFit: true,
        scrollOffset: 2,
        rownumbers: true
    });
}

function saveResult(n) {
    var selrows = $("#" + n).jqGrid('getGridParam', 'selarrrow');
    if (selrows == null || selrows.length == 0) {
        layer.alert("请先选择要保存的数据！");
        return;
    }
    var rows = [];
    for (var a = 0; a < selrows.length; a++) {
        rows[a] = $("#" + n).jqGrid('getRowData', selrows[a]);
    }
    var items = [];
    var j = 0;
    var k = 0;
    var itemName = [];
    var ingoreName = [];
    for (var i = 0; i < rows.length; i++) {
        if (rows[i].finishstatus == 1) {
            var data = {};
            data.checkid = rows[i].checkid;
            data.chetestresult = rows[i].chetestresult;
            items[j] = data;
            itemName[j] = rows[i].chenamech;
            j++;
        } else {
            ingoreName[k] = rows[i].chenamech;
            k++;
        }
    }
    if (items.length > 0) {
        $.get("../order/saveresult", {items: JSON.stringify(items)}, function () {
            layer.alert("项目：" + JSON.stringify(itemName) + "保存成功！");
        })
    }
    if (ingoreName.length > 0) {
        layer.alert("技师端还没有完成项目：" + JSON.stringify(ingoreName) + " ，不能保存结果！");
    }
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

            createOptions(data.pathologyid, data.patIsSampling, data.specialCheck);

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

            $.get("../diagnosis/sampleresult", {sampleid: id}, function (data) {
                var x = document.getElementById("diagnosisInfoForm");
                for (itm in data) {
                    var resultid = data[itm].resultid;
                    var restestitemid = data[itm].restestitemid;
                    for (var i = 0; i < x.length; i++) {
                        var e = x.elements[i];
                        if ($("#" + e.id).attr("rptItemId") == restestitemid) {
                            $("#" + e.id).val(data[itm].restestresult);
                            $("#" + e.id).attr("hiddenValue", resultid);
                        }
                    }
                }
                var emptyResult = jQuery.isEmptyObject(data);
                if (emptyResult) {
                    for (var i = 0; i < x.length; i++) {
                        var e = x.elements[i];
                        if ($("#" + e.id).attr("rptItemId")) {
                            $("#" + e.id).val('');
                            $("#" + e.id).attr("hiddenValue", "");
                        }
                    }
                }
                getSamplePicures(id);
            })

        } else {
            layer.msg("该申请单不存在！", {icon: 0, time: 1000});
        }
    });
}

function getSamplePicures(sampleId) {
    $.get("../diagnosis/pictures", {sampleid: sampleId, picpictureclass: PIC_TAKING_FROM}, function (data) {
        var ret = data;
        var container = $("#imgContainer");
        container.empty();
        for (var item in ret) {
            var objNewDiv = $('<div>', {'id': new Date().getTime(), 'style': 'padding-bottom:5px'});
            objNewDiv.html("<img src='" + ret[item] + "' name='" + item + "' onclick='removePicture(\"" + item + "\")' width='220' height='150'>");
            container.append(objNewDiv);
        }
        //重新加载取材信息列表
        jQuery("#materialList").jqGrid("clearGridData");
        jQuery("#materialList").jqGrid('setGridParam', {
            url: "../pathologysample/pieces/ajax/getItem",
            datatype: 'json',
            postData: {"reqId": sampleId}
        }).trigger('reloadGrid');//重新载入
    });
}

function removePicture(pictureName) {
    layer.msg('需要删除图片吗？', {
        time: 3000, //自动关闭
        btn: ['是', '否'],
        yes: function (index) {
            $.get("../diagnosis/removePicture", {
                name: pictureName,
                sampleid: GRID_SELECTED_ROW_SAMPLEID
            }, function (data) {
                var es = document.getElementsByName(pictureName);
                var pn = es[0].parentNode;
                pn.parentNode.removeChild(pn);
            });
            layer.close(index);
        }
    });
}

function query() {
    var sampathologyid = $("#sampathologyid").val();
    var samplesectionfrom = $("#samplesectionfrom").val();
    var samplesectionto = $("#samplesectionto").val();
    var saminspectionid = $("#saminspectionidq").val();
    var sampathologycode = $("#sampathologycodeq").val();
    var sampatientname = $("#sampatientnameq").val();
    jQuery("#sectionList").jqGrid('setGridParam', {
        datatype: 'json',
        postData: {
            "sampathologyid": sampathologyid,
            "samplesectionfrom": samplesectionfrom,
            "samplesectionto": samplesectionto,
            "saminspectionid": saminspectionid,
            "sampathologycode": sampathologycode,
            "sampatientname": sampatientname
        },
        page: 1
    }).trigger('reloadGrid');//重新载入
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

function print(url) {
    LODOP = getLodop();
    LODOP.PRINT_INIT("报告单打印");
    LODOP.ADD_PRINT_URL(10, 10, 794, 1123, url);
    LODOP.SET_PRINT_STYLEA(0, "HOrient", 3);
    LODOP.SET_PRINT_STYLEA(0, "VOrient", 3);
    //LODOP.PREVIEW();
    LODOP.PRINT();
}

function reportOperate(v) {
    var id = $('#sectionList').jqGrid('getGridParam', 'selrow');
    if (id == null || id.length == 0) {
        layer.msg('请先选择标本', {icon: 2, time: 1000});
        return false;
    }
    layer.open({
        type: 1,
        area: ['300px', '150px'],
        fix: false, //不固定
        maxmin: true,
        shade: 0.5,
        title: "选择报告打印模板",
        content: $('#reportTemplateList'),
        btn: ["确定", "取消"],
        yes: function (index, layero) {
            var template = $("#reportTemplateSelect").val();
            if (template == null || template == "") {
                layer.msg("报告模板不存在，请先配置", {icon: 0, time: 3000});
                layer.close(index);
            } else {
                var picNum = $("#reportTemplateSelect").find("option:selected").attr("picNum");
                $.get("../diagnosis/report/print", {
                    "sampleid": GRID_SELECTED_ROW_SAMPLEID,
                    "templateUrl": template,
                    "type": v,
                    "picpictureclass": PIC_TAKING_FROM,
                    "picNum": picNum
                }, function (data) {
                    if (v == 1) {
                        layer.open({
                            type: 2,
                            title: "报告单预览",
                            area: ['854px', '600px'],
                            btn: ["打印", "关闭"],
                            maxmin: true,
                            shade: 0.5,
                            content: data.url,
                            yes: function (index1, layero1) {
                                print(data.url);
                                layer.close(index1);
                            }
                        });
                    } else {
                        print(data.url);
                    }
                });//重新载入
            }
            layer.close(index);
        },
        success: function () {
            $.get("../diagnosis/report/getTemplate", {"sampleid": GRID_SELECTED_ROW_SAMPLEID}, function (data) {
                    var rows = data.rows;
                    $("#reportTemplateSelect").empty();
                    if (rows.length > 0) {
                        for (var i = 0; i < rows.length; i++) {
                            $("#reportTemplateSelect").append("<option value='" + rows[i].formweburl + "' picNum='" + rows[i].formpicturenum + "'>" + rows[i].formname + "</option>");
                        }
                    }
                }
            );//重新载入
        }
    })
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

function buttonFormat(cellval, options, pos, cnt) {

    return "<button onclick=\"appendItem()\">追加</button>";
}

function appendItem(v) {
    var lakuai = $("#lkxz").find("option:selected").text();
    if (lakuai == "") {
        layer.alert("请先选择蜡块", {icon: 1, title: "提示"});
        return false;
    }

    var row = null;
    if (v != null) row = v;
    else {
        var selrow = $("#ckItemList").jqGrid('getGridParam', 'selrow');
        if ((selrow == null || selrow.length == 0) && v == null) return;
        row = $("#ckItemList").jqGrid('getRowData', selrow);
    }
    row.lkno = lakuai;
    var itemNo = $("#itemList").jqGrid("getDataIDs").length;
    var insertedId = $("#itemList").getCol("testitemid");
    var e = false;
    for (var j = 0; j < insertedId.length; j++) {
        if (insertedId[j] == row.testitemid) e = true;
    }
    if (!e) {
        $("#itemList").jqGrid("addRowData", (itemNo + 1), row);
        setTotalNumValue(lakuai);
    }
}

function removeItems() {
    var orderType = $("#yizhugl").val();
    var gridName;
    if (orderType == "CHONGQIE" || orderType == "SHENQIE")
        gridName = "#pieceList";
    else if (orderType == 'MYZH' || orderType == 'TSRS' || orderType == 'FZBL')
        gridName = "#itemList";
    var selectedRowIds = $(gridName).jqGrid("getGridParam", "selrow");
    for (var i = 0; i < selectedRowIds.length; i++) {
        $(gridName).jqGrid("delRowData", selectedRowIds[i]);
    }
}

function appendAll() {
    var rows = $("#ckItemList").jqGrid("getDataIDs");
    var lakuai = $("#lkxz").find("option:selected").text();
    if (lakuai == "") {
        layer.alert("请先选择蜡块", {icon: 1, title: "提示"});
        return false;
    }
    if (rows.length > 0) {
        for (var i = 0; i < rows.length; i++) {
            var row = $("#ckItemList").jqGrid("getRowData", rows[i]);
            row.lkno = lakuai;
            var itemNo = $("#itemList").jqGrid("getDataIDs").length;
            var insertedId = $("#itemList").getCol("testitemid");
            var e = false;
            for (var j = 0; j < insertedId.length; j++) {
                if (insertedId[j] == row.testitemid) e = true;
            }
            if (!e) {
                $("#itemList").jqGrid("addRowData", (itemNo + 1), row);
                setTotalNumValue(lakuai)
            }
        }
    }
}

function setRemark(pcode, r) {
    var lkrows = $("#lkItemList").jqGrid("getDataIDs");
    for (var i = 0; i < lkrows.length; i++) {
        var row = $("#lkItemList").jqGrid("getRowData", lkrows[i]);
        if (pcode == row.lkno) {
            row.chicontent = r;
            $("#lkItemList").jqGrid("setRowData", lkrows[i], row);
            break;
        }
    }
}

function setTotalNumValue(pcode) {
    var lkrows = $("#lkItemList").jqGrid("getDataIDs");
    var orderType_ = $("#yizhugl").val();
    for (var i = 0; i < lkrows.length; i++) {
        var row = $("#lkItemList").jqGrid("getRowData", lkrows[i]);
        if (pcode == row.lkno) {
            if(orderType_ == "CHONGQIE" ||orderType_ == "SHENQIE")row.totalItem=0;
            else {
            if (row.totalItem == "")
                    row.totalItem = 1;
                else
                    row.totalItem = parseInt(row.totalItem) + 1;
            }
            $("#lkItemList").jqGrid("setRowData", lkrows[i], row);
            break;
        }
    }
}

function addRow() {
    var jjinfo = "";
    var ids = $("#new1").jqGrid('getDataIDs');
    var reqDoctorId = $("#reqDoctorId").val();
    var reqDoctor = $("#reqDoctor").val();
    if (reqDoctorId == "" || reqDoctor == "") {
        layer.alert("请先选择申请医生");
        return;
    }
    var maxId = 1;
    if (ids == null || ids == "") {
        $.get("../order/getmaxpieceno", {sampleId: $("#sampleid").val()}, function (data) {
            var pno = data.userdata.maxPieceNo;
            if (pno != null && pno != "") {
                maxId = parseInt(pno) + 1;
            }
            doAddRow(maxId, jjinfo);
        })
    } else {
        var rowData = $("#new1").jqGrid('getRowData', Math.max.apply(Math, ids));
        jjinfo = rowData.pieparts;
        maxId = parseInt(rowData.piesamplingno) + 1;
        doAddRow(maxId, jjinfo);
    }
}

function doAddRow(maxId, jjinfo) {
    var sampathologycode = $('#yblNo').val();
    var reqDoctorId = $("#reqDoctorId").val();
    var reqDoctor = $("#reqDoctor").val();
    var dataRow = {
        piecode: sampathologycode + "-" + maxId,
        piesampleid: $("#sampleid").val(),
        pieunit: "1",
        piepathologycode: sampathologycode,
        piesamplingno: maxId,
        piecounts: 1,
        pienullslidenum: 0,
        pieparts: jjinfo,
        piedoctorid: reqDoctorId,
        pierecorderid: reqDoctorId,
        piedoctorname: reqDoctor,
        pierecordername: reqDoctor,
        piesamplingtime: new Date(),
        piespecial: "",
        piestate: 0,
        pieisembed: 0//是否包埋
    };
    $("#new1").jqGrid("addRowData", parseInt(reqDoctorId) + maxId, dataRow, "last");
}

function delRow() {
    var selectedIds = $("#new1").jqGrid("getGridParam", "selarrrow");
    var ids = $("#new1").jqGrid('getDataIDs');
    var maxId = Math.max.apply(Math, ids);
    if (selectedIds == null || selectedIds.length == 0) {
        layer.alert("请先选择要删除的数据!");
        return;
    } else {
        var maxSelectId = Math.max.apply(Math, selectedIds);
        if (maxId > maxSelectId) {
            layer.alert("请从最后一条数据开始删除");
            return;
        } else {
            $(selectedIds).each(function () {
                    var delrow = this.toString();
                    $("#new1").jqGrid("delRowData", delrow);
                }
            );
        }

    }
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
    if (rowData != null && rowData.sampleid != null && rowData.sampleid != "")
        GRID_SELECTED_ROW_SAMPLEID = rowData.sampleid;
        GRID_SELECTED_ROW_SAMPCUSTOMERID = rowData.samcustomerid;
    getOrderTabs(rowData.sampleid);
    setcolor(id);
    crno = id;
}

$(function () {
    $(window).on('resize.jqGrid', function () {
        $('#sectionList').jqGrid('setGridWidth', $(".leftContent").width(), false);
    });
    var clientHeight = $(window).innerHeight();
    var height = clientHeight - $('#head').height() - $('#toolbar').height() - $('.footer-content').height() - 150;

    $("#sectionList").jqGrid({
        caption: "",
        url: "../diagnosis/query",
        mtype: "GET",
        datatype: "json",
        width: $('.leftContent').width(),
        colNames: [ '病理状态', '病理号', '送检医生', 'id', 'samcustomerid', 'sampathologyid'],
        colModel: [
            {
                name: 'sampathologystatus',
                index: 'sampathologystatus',
                width: 30,
                formatter: "select",
                editoptions: {value: "1:未报告;2:已签发;3:已审核;4:已打印"}
            },
            {name: 'sampathologycode', index: 'sampathologycode', width: 40},
            {name: 'samsenddoctorname', index: 'samsenddoctorname', width: 40},
            {name: 'sampleid', index: 'sampleid', hidden: true},
            {name: 'samcustomerid', index: 'samcustomerid', hidden: true},
            {name: 'sampathologyid', index: 'sampathologyid', hidden: true}
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
        beforeSelectRow: function (rowid, e) {
            return $(e.target).is('input[type=checkbox]');
        },
        multiselect:true,
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

        },
        onCellSelect:function(id){
            onRowSelect(id);
        }
    });

    jQuery("#sectionList").jqGrid('bindKeys', {
            "onEnter": function (rowid) {
                onRowSelect(rowid);
            }
        }
    );

    $("#new1").jqGrid({
        datatype: "json",
        mtype: "GET",
        height: 130,
        width: 700,
        colNames: ['材块条码编号', '客户ID', '取材单位', '病理号', '取材序号', '材块数', '白片数', '取材部位', '取材医生ID', '取材医生', '录入员ID',
            '录入员', '取材时间', '特殊要求', '取材状态', 'pieisembed'],
        colModel: [
            {name: 'piecode', hidden: true},//材块条码编号
            {name: 'piesampleid', hidden: true},//客户ID
            {name: 'pieunit', hidden: true},//取材单位
            {name: 'piepathologycode', index: 'piepathologycode', width: 75},//病理号
            {name: 'piesamplingno', index: 'piesamplingno', width: 65},//取材序号
            {
                name: 'piecounts',
                index: 'piecounts',
                editable: true,
                width: 60,
                editrules: {edithidden: true, required: true, number: true, minValue: 1, maxValue: 100}
            },//材块数
            {
                name: 'pienullslidenum',
                index: 'pienullslidenum',
                width: 60,
                editable: true,
                editrules: {edithidden: true, required: true, number: true, minValue: 0, maxValue: 100}
            },//白片数
            {name: 'pieparts', index: 'pieparts', width: 65, editable: true},//取材部位
            {name: 'piedoctorid', hidden: true},//取材医生ID
            {name: 'piedoctorname', index: 'piedoctorname', width: 65},//取材医生
            {name: 'pierecorderid', hidden: true},//录入员ID
            {name: 'pierecordername', index: 'pierecordername', width: 60},//录入员
            {
                name: 'piesamplingtime',
                index: 'piesamplingtime',
                width: 90,
                formatter: function (cellvalue, options, row) {
                    return new Date().Format("yyyy-MM-dd hh:mm:ss")
                }
            },//取材时间
            {name: 'piespecial', index: 'piespecial', width: 65, editable: true},//特殊要求
            {
                name: 'piestate',
                index: 'piestate',
                width: 65,
                formatter: "select",
                editoptions: {value: "0:未取材;1:已取材;2:已包埋;3:已切片;4:已初诊;5:已审核"}
            },//取材状态
            {name: 'pieisembed', index: 'pieisembed', hidden: true}
        ],
        viewrecords: true,
        multiselect: true,
        cellsubmit: "clientArray",
        //autowidth: true,
        //shrinkToFit:false,
        //autoScroll: true,
        cellEdit: true,
        rownumbers: true
    });

    $("#lkItemList").jqGrid({
        datatype: "json",
        width: 300,
        cellEdit: true,
        cellsubmit: 'clientArray',
        colNames: ['lkid', '蜡块编号', '库存', '预留', 'totalItem', 'chicontent'],
        afterEditCell: function (rowid, name, val, iRow, iCol) {
            $('#lkItemList').jqGrid('saveCell', $("#lkItemList").jqGrid.editrow, $("#lkItemList").jqGrid.editcol);

        },
        colModel: [
            {
                name: 'lkid',
                index: 'lkid',
                hidden: true
            },
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
                name: 'yuliu',
                index: 'yuliu',
                width: 40,
                editable: true,
                edittype: 'text',
                editrules: {edithidden: true, required: true, number: true}
            },
            {
                name: 'totalItem',
                index: 'totalItem',
                hidden: true
            },
            {
                name: 'chicontent',
                index: 'chicontent',
                hidden: true
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
        width: 300,
        colNames: ['追加', 'testitemid', '项目名称', '英文名称', 'tesischarge'],
        colModel: [
            {name: 'append', index: 'append', formatter: buttonFormat},
            {name: 'testitemid', index: 'testitemid', hidden: true},
            {name: 'teschinesename', index: 'teschinesename', width: 35},
            {name: 'tesenglishname', index: 'tesenglishname', width: 35},
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


    $("#itemList").jqGrid({
        mtype: "GET",
        datatype: "json",
        width: 400,
        multiselect: true,
        colNames: ['testitemid', '蜡块编号', '项目名称', '英文名称', 'tesischarge'],
        colModel: [
            {
                name: 'testitemid', index: 'testitemid', hidden: true
            },
            {
                name: 'lkno',
                index: 'lkno',
                width: 30
            },
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
        height: 180,
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

    $("#templateList").jqGrid({
        caption: "病理模板",
        width: 790,
        colNames: ['关键字', '内容'],
        colModel: [
            {name: 'temkey', index: 'temkey', width: 50},
            {name: 'temcontent', index: 'temcontent', width: 300}
        ],
        loadComplete: function () {
            var table = this;
            setTimeout(function () {
                updatePagerIcons(table);
            }, 0);
            layer.open({
                type: 1,
                area: ['800px', '500px'],
                fix: false, //不固定
                maxmin: true,
                shade: 0.5,
                title: "模板",
                content: $('#templateGrid'),
                btn: ["确定", "取消"],
                yes: function (index, layero) {
                    var id = $('#templateList').jqGrid('getGridParam', 'selrow');
                    if (id == null || id.length == 0) {
                        layer.msg('请先选择病种模板', {icon: 2, time: 1000});
                        return false;
                    }
                    var rowData = $("#templateList").jqGrid('getRowData', id);
                    document.getElementById(targetTextareaId).value = rowData.temcontent;
                    layer.close(index);
                }
            })
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
        pager: "#templatePager",
        onSelectRow: function (id) {

        }
    });

    $("#tabs").tabs();
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

    $('#delreporttime').datepicker({
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
})