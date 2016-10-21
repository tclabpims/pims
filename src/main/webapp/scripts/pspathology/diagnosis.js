/**
 * Created by lenovo on 2016/10/6.
 */
function takingPicture() {
    layer.open({
        type: 2,
        title: '图像采集',
        shadeClose: true,
        shade: false,
        maxmin: true, //开启最大化最小化按钮
        area: ['320px', '360px'],
        content: ['http://localhost:8080/scripts/picture/Main.html', 'no']
    });
}
/************************************
 *  添加病种
 *  add by zcw 2015-05-16
 * **********************************/
function AddSection() {
    clearData();
    var treeObj = $.fn.zTree.getZTreeObj("tree");
    var nodes = treeObj.getSelectedNodes();
    if (!nodes || nodes.length == 0) {
        layer.msg('请先选择节点', {icon: 2, time: 1000});
        return false;
    }
    $('#fiepelementid').val(nodes[0].id);
    $('#fieldid').val('');
}

function saveFieldData() {
    var treeObj = $.fn.zTree.getZTreeObj("tree");
    var nodes = treeObj.getSelectedNodes();
    if (!nodes || nodes.length == 0) {
        layer.msg('请先选择节点', {icon: 2, time: 1000});
        return false;
    }
    $('#fiepelementid').val(nodes[0].id);
    if ($('#fieelementid').val() == '' || $('#fieelementname').val() == '' || $('#fieshowlevel').val() == ''
        || $('#fiepelementid').val() == '' || $('#fieshoworder').val() == '') {
        layer.msg('信息填写不完整', {icon: 2, time: 1000});
        return false;
    }
    if ($('#fieldid').val() == '') {
        $.post('../reqfield/edit', {
            fieelementtype: $('#fieelementtype').val(), fieelementid: $('#fieelementid').val(),
            fieelementname: $('#fieelementname').val(), fieshowlevel: $('#fieshowlevel').val(),
            fiepelementid: $('#fiepelementid').val(), fiedefaultvalue: $('#fiedefaultvalue').val(),
            fieshoworder: $('#fieshoworder').val(), fieuseflag: $('#fieuseflag').val(),
            fieremark: $('#fieremark').val()
        }, function (data) {
            treeObj.addNodes(nodes[0], data, true);
            layer.msg('添加成功', {icon: 2, time: 1000});

        });
    } else {
        $.post('../reqfield/edit', {
            fieelementtype: $('#fieelementtype').val(), fieelementid: $('#fieelementid').val(),
            fieelementname: $('#fieelementname').val(), fieshowlevel: $('#fieshowlevel').val(),
            fiepelementid: $('#fiepelementid').val(), fiedefaultvalue: $('#fiedefaultvalue').val(),
            fieshoworder: $('#fieshoworder').val(), fieuseflag: $('#fieuseflag').val(),
            fieremark: $('#fieremark').val(), fieldid: $('#fieldid').val()
        }, function (data) {
            layer.msg('修改成功', {icon: 2, time: 1000});
        });
    }
}

function deleteSection() {
    var treeObj = $.fn.zTree.getZTreeObj("tree");
    var nodes = treeObj.getCheckedNodes(true);
    if (nodes == null || nodes.length == 0) {
        layer.msg('请先选择要删除的数据', {icon: 2, time: 1000});
        return false;
    }
    var param = [];
    var j = 0;
    for (var i = 0; i < nodes.length; i++) {
        if (nodes[i].id == 0) continue;
        param[j] = nodes[i].id;
        j++;
    }
    if (param.length == 0) return false;
    layer.confirm('确定删除选择的数据？', {icon: 2, title: '警告'}, function (index) {
        $.post('../reqfield/remove', {id: param.toString()}, function (data) {
            for (var i = 0; i < nodes.length; i++) {
                if (nodes[i].id == 0) continue;
                treeObj.removeNode(nodes[i], false);
            }
            layer.close(index);
            clearData();
        });
    });
}
/**
 * 查询科室
 */
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
    //表单校验
    $("#addSectionForm").Validform({
        tiptype: 4,
        callback: function () {

        }
    });
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
        } else {
            layer.msg("该申请单不存在！", {icon: 0, time: 1000});
        }
    });
}

function query(){
    var sampathologyid = $("#sampathologyid").val();
    var samsamplestatus = $("#samsamplestatus").val();
    var samplesectionfrom = $("#samplesectionfrom").val();
    var samplesectionto = $("#samplesectionto").val();
    var saminspectionid = $("#saminspectionidq").val();
    var sampathologycode = $("#sampathologycodeq").val();
    var sampatientname = $("#sampatientnameq").val();
    jQuery("#sectionList").jqGrid('setGridParam',{
        datatype:'json',
        postData : {
            "sampathologyid":sampathologyid,
            "samsamplestatus":samsamplestatus,
            "samplesectionfrom":samplesectionfrom,
            "samplesectionto":samplesectionto,
            "saminspectionid":saminspectionid,
            "sampathologycode":sampathologycode,
            "sampatientname":sampatientname
        },
        page : 1
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
    if(month < 10)
        clock += "0";
    clock += month + "-";
    if(day < 10)
        clock += "0";
    clock += day + " ";
    if(hh < 10)
        clock += "0";
    clock += hh + ":";
    if (mm < 10) clock += '0';
    clock += mm + ":";
    if(ss < 10) clock +='0';
    clock += ss;
    return(clock);
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
        colNames: ['选择', '病理状态', '条形码', '病理号', 'id'],
        colModel: [
            {name: 'matsort', index: 'matsort', width: 20, edittype: 'checkbox'},
            {
                name: 'samsamplestatus',
                index: 'samsamplestatus',
                width: 30,
                formatter: "select",
                editoptions: {value: "0:已登记;1:已取材;2:包埋;3:已切片;4:已初诊;5:已审核;6:已发送;7:会诊中;8:报告已打印"}
            },
            {name: 'saminspectionid', index: 'saminspectionid', width: 40},
            {name: 'sampathologycode', index: 'sampathologycode', width: 40},
            {name: 'sampleid', index: 'sampleid', hidden: true}
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
        rownumbers: false, // 显示行号
        rownumWidth: 35, // the width of the row numbers columns
        pager: "#pager",
        onSelectRow: function (id) {
            var rowData = $("#sectionList").jqGrid('getRowData', id);
            getSampleData1(rowData.sampleid);
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
        loadComplete : function() {
            /*var table = this;
            setTimeout(function(){
                updatePagerIcons(table);
            }, 0);*/
        },
        shrinkToFit:false,
        scrollOffset:2,
        rowNum: 10,
        rowList:[10,20,40],
        rownumbers: true // 显示行号
    });

    $("#tabs").tabs(
        {
            activate: function (event, ui) {
                var active = $('#tabs').tabs('option', 'active');
                var href = $("#tabs ul>li a").eq(active).attr("href");
                var selrow = $("#sectionList").jqGrid('getGridParam','selrow');
                if(selrow == null || selrow.length == 0) return;
                var rowData = $("#sectionList").jqGrid('getRowData',selrow);
                if(href=='#tabs-2'){
                    jQuery("#materialList").jqGrid("clearGridData");
                    jQuery("#materialList").jqGrid('setGridParam',{
                        url: "../pathologysample/pieces/ajax/getItem",
                        datatype:'json',
                        postData : {"reqId":rowData.sampleid}
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
            $("#datepickere").datepicker("option", "minDate", selectedDate);
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
})