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

var targetTextareaId;
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

function appendIFileElement(data) {

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
    var id = $('#sectionList').jqGrid('getGridParam', 'selrow');
    if (id == null || id.length == 0) {
        layer.msg('请先选择标本', {icon: 2, time: 1000});
        return false;
    }
    var rowData = $("#sectionList").jqGrid('getRowData', id);
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
    var id = $('#sectionList').jqGrid('getGridParam', 'selrow');
    if (id == null || id.length == 0) {
        layer.msg('请先选择标本', {icon: 2, time: 1000});
        return false;
    }
    layer.open({
        type: 1,
        title: '病理诊断>图像采集',
        shadeClose: true,
        shade: false,
        maxmin: true, //开启最大化最小化按钮
        area: ['320px', '360px'],
        content: $('#flashContent'),
        success: function (layero, index) {

    }
    });
}

function importImg() {
    var id = $('#sectionList').jqGrid('getGridParam', 'selrow');
    if (id == null || id.length == 0) {
        layer.msg('请先选择标本', {icon: 2, time: 1000});
        return false;
    }
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

function ajaxFileUpload() {
    $.ajaxFileUpload({
        url: '../diagnosis/uploadimg',
        secureuri: false,
        fileElementId: ["imgFile"],
        dataType: 'json',
        data: {sampleid: GRID_SELECTED_ROW_SAMPLEID, samcustomerid: GRID_SELECTED_ROW_SAMPCUSTOMERID},
        success: function (data) {
            var container = $("#imgContainer");
            var objNewDiv = $('<div>', {'id': 'mydiv', 'style': 'padding-bottom:5px'});
            objNewDiv.html("<img src='" + data.src + "' width='220' onclick='removePicture(\"" + data.name + "\")' height='150'>");
            container.append(objNewDiv);
            layer.msg('上传成功！', {icon: 2, time: 1000});
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
    var id = $('#sectionList').jqGrid('getGridParam', 'selrow');
    if (id == null || id.length == 0) {
        layer.msg('请先选择标本', {icon: 2, time: 1000});
        return false;
    }
    var rowData = $("#sectionList").jqGrid('getRowData', id);
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
//如果病种要取材，医嘱里面允许以下操作
var commonDiagnosis = "<option value='1'>补取</option><option value='2'>重切</option><option value='3'>深切</option>";

//如果病种是特殊检验，医嘱里面允许以下操作
var specialDiagnosis = "<option value='4'>免疫组化</option><option value='5'>特殊染色</option><option value='6'>分子病理</option>";

function createOptions(patIsSampling, specialCheck) {
    $("#yizhugl").empty();
    if(patIsSampling == 0) {
        $("#yizhugl").append(commonDiagnosis);
    }
    if(specialCheck == 0) {
        $("#yizhugl").append(specialDiagnosis);
    }
}

function reqyizhu() {
    var yizhutype = $("#yizhugl").val();
    if(yizhutype == null) {
        layer.msg("请先选择医嘱类型！", {icon: 0, time: 1000});
        return false;
    }
    //如果是特检医嘱 打开特检相应的页面
    if(yizhutype == 4 || yizhutype == 5 || yizhutype == 6) {
        layer.open({
            type: 1,
            title: '病理诊断>病理医嘱申请',
            shadeClose: false,
            shade: 0.5,
            maxmin: false, //开启最大化最小化按钮
            area: ['800px', '620px'],
            content: $('#specialCheckDialog'),
            btn: ["保存", "取消"],
            success:function() {
                $("#patientZyh").val($("#sampatientnumber").val());//住院卡号/门诊卡号 11
                $("#patientName").val($("#sampatientname").val());//姓名 11
                $("#patientGender").val($("#sampatientsex").val());//患者性别(1男,2女,3未知)
                $("#patientDiagnosisNote").val($("#sampatientdignoses").val());//临床诊断 11
                $("#patientBed").val($("#sampatientbed").val());//患者床号
            },
            yes: function (index, layero) {

            }
        });
    } else if(yizhutype == 2 || yizhutype == 3) {
        alert(2)
    } else if(yizhutype == 1) {
        alert(3)
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
            $("#sampatientsex").val(data.sampatientsex);//患者性别(1男,2女,3未知) 11
            $("#samsamplename").val(data.samsamplename);//标本名称(,多个检查项目名称之间用逗号隔开) 11
            $("#samsenddoctorid").val(data.samsenddoctorid);//送检医生id 11
            $("#samsendhospital").val(data.samsendhospital);//送检单位名称 11
            $("#samdeptname").val(data.samdeptname);//科室名称 11
            $("#sampatientdignoses").val(data.sampatientdignoses);//临床诊断 11
            $("#samismenopause").val(data.samismenopause);//是否绝经 11
            $("#reqlastmenstruation").val(data.reqlastmenstruation);//末次月经时间 11
            $("#sampatientbed").val(data.sampatientbed);//患者床号

            createOptions(data.patIsSampling, data.specialCheck);

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
    $.get("../diagnosis/pictures", {sampleid: sampleId}, function (data) {
        var ret = data;
        var container = $("#imgContainer");
        container.empty();
        for (var item in ret) {
            var objNewDiv = $('<div>', {'id': new Date().getTime(), 'style': 'padding-bottom:5px'});
            objNewDiv.html("<img src='" + ret[item] + "' name='" + item + "' onclick='removePicture(\"" + item + "\")' width='220' height='150'>");
            container.append(objNewDiv);
        }
    });
}

function removePicture(pictureName) {
    layer.msg('需要删除图片吗？', {
        time: 3000, //自动关闭
        btn: ['是', '否'],
        yes: function (index) {
            $.get("../diagnosis/removePicture", {
                name: pictureName,
                smapleid: GRID_SELECTED_ROW_SAMPLEID
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
    LODOP=getLodop();
    LODOP.PRINT_INIT("报告单打印");
    LODOP.ADD_PRINT_URL(10,10,794,1123,url);
    LODOP.SET_PRINT_STYLEA(0,"HOrient",3);
    LODOP.SET_PRINT_STYLEA(0,"VOrient",3);
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
            if(template == null || template == "") {
                layer.msg("报告模板不存在，请先配置", {icon: 0, time: 3000});
                layer.close(index);
            } else {
                var picNum = $("#reportTemplateSelect").find("option:selected").attr("picNum");
                $.get("../diagnosis/report/print", {"sampleid": GRID_SELECTED_ROW_SAMPLEID,"templateUrl":template,"type":v, "picNum":picNum}, function (data) {
                    if (v == 1) {
                        layer.open({
                            type: 2,
                            title: "报告单预览",
                            area: ['854px', '600px'],
                            btn: ["打印", "关闭"],
                            maxmin: true,
                            shade: 0.5,
                            content: data.url,
                            yes:function (index1, layero1) {
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
                    var select = $("#reportTemplateSelect");
                    if (rows.length > 0) {
                        for(var i =0; i < rows.length; i++) {
                            select.append("<option value='"+rows[i].formweburl+"' picNum='"+rows[i].formpicturenum+"'>"+rows[i].formname+"</option>");
                        }
                    } else {
                        select.empty();
                    }
                }
            );//重新载入
        }
    })
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
        url: "../diagnosis/query",
        mtype: "GET",
        datatype: "json",
        width: $('.leftContent').width(),
        colNames: ['选择', '病理状态', '病理号', '送检医生', 'id', 'samcustomerid'],
        colModel: [
            {
                name: 'matsort', index: 'matsort', width: 20, align: "center",
                formatter: "checkbox", formatoptions: {disabled: false}
            },
            {
                name: 'samsamplestatus',
                index: 'samsamplestatus',
                width: 30,
                formatter: "select",
                editoptions: {value: "0:已登记;1:已取材;2:包埋;3:已切片;4:已初诊;5:已审核;6:已发送;7:会诊中;8:报告已打印"}
            },
            {name: 'saminspectionid', index: 'saminspectionid', width: 40},
            {name: 'samsenddoctorname', index: 'samsenddoctorname', width: 40},
            {name: 'sampleid', index: 'sampleid', hidden: true},
            {name: 'samcustomerid', index: 'samcustomerid', hidden: true}
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
            if (rowData != null && rowData.sampleid != null && rowData.sampleid != "")
                getSampleData1(rowData.sampleid);
            GRID_SELECTED_ROW_SAMPLEID = rowData.sampleid;
            GRID_SELECTED_ROW_SAMPCUSTOMERID = rowData.samcustomerid;
        }
    });

    jQuery("#sectionList").jqGrid('bindKeys', {
            "onEnter": function (rowid) {
                $("#sectionList").jqGrid('setSelection', rowid);
                alert(rowid);
            }
        }
    );

    $("#lkItemList").jqGrid({
        mtype: "GET",
        datatype: "json",
        width: 300,
        colNames: ['蜡块编号', '库存', '预留'],
        colModel: [
            {
                name: 'samsamplestatus',
                index: 'samsamplestatus',
                width: 30,
                formatter: "select",
                editoptions: {value: "0:已登记;1:已取材;2:包埋;3:已切片;4:已初诊;5:已审核;6:已发送;7:会诊中;8:报告已打印"}
            },
            {name: 'saminspectionid', index: 'saminspectionid', width: 40},
            {name: 'samsenddoctorname', index: 'samsenddoctorname', width: 40}
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
        height: 100,
        rowNum: 5,
        onSelectRow: function (id) {

        }
    });

    $("#ckItemList").jqGrid({
        mtype: "GET",
        datatype: "json",
        width: 300,
        colNames: ['追加', '项目名称'],
        colModel: [
            {name: 'saminspectionid', index: 'saminspectionid', width: 40},
            {name: 'samsenddoctorname', index: 'samsenddoctorname', width: 40}
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
        colNames: ['选择', '蜡块编号', '项目名称', '结果', '备注', '申请医生'],
        colModel: [
            {
                name: 'matsort', index: 'matsort', width: 20, align: "center",
                formatter: "checkbox", formatoptions: {disabled: false}
            },
            {
                name: 'samsamplestatus',
                index: 'samsamplestatus',
                width: 30,
                formatter: "select",
                editoptions: {value: "0:已登记;1:已取材;2:包埋;3:已切片;4:已初诊;5:已审核;6:已发送;7:会诊中;8:报告已打印"}
            },
            {name: 'saminspectionid', index: 'saminspectionid', width: 40},
            {name: 'samsenddoctorname', index: 'samsenddoctorname', width: 40},
            {name: 'sampleid', index: 'sampleid', hidden: true},
            {name: 'samcustomerid', index: 'samcustomerid', hidden: true}
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

    $("#tabs").tabs(
        {
            activate: function (event, ui) {
                var active = $('#tabs').tabs('option', 'active');
                var href = $("#tabs ul>li a").eq(active).attr("href");
                var selrow = $("#sectionList").jqGrid('getGridParam', 'selrow');
                if (selrow == null || selrow.length == 0) return;
                var rowData = $("#sectionList").jqGrid('getRowData', selrow);
                if (href == '#tabs-2') {
                    jQuery("#materialList").jqGrid("clearGridData");
                    jQuery("#materialList").jqGrid('setGridParam', {
                        url: "../pathologysample/pieces/ajax/getItem",
                        datatype: 'json',
                        postData: {"reqId": rowData.sampleid}
                    }).trigger('reloadGrid');//重新载入
                }
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
})