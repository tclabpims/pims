/**
 * Created by lenovo on 2016/10/6.
 */
function showHospital() {
    layer.open({
        type: 1,
        area: ['800px','500px'],
        fix: false, //不固定
        maxmin: true,
        shade:0.5,
        title: "医院列表",
        content: $('#hospitalGrid'),
        btn:["保存","取消"],
        yes: function(index, layero){
            var id = $('#sectionList2').jqGrid('getGridParam','selrow');
            if(id==null || id.length==0){
                layer.msg('请先选择医院', {icon: 2,time: 1000});
                return false;
            }
            var rowData = $("#sectionList2").jqGrid('getRowData',id);
            $("#colcustomercode").val(rowData.id);
            $("#colcustomername").val(rowData.name);
            layer.close(index);
        }
    })
}

var SYSTEM_DEFAULT_ID = 9999999999;

function initValue() {
    var selectedValue = $("#coltype").val();
    if(selectedValue == 0) {
        $("#colownername").val("System");
        $("#colowner").val(SYSTEM_DEFAULT_ID);
    } else {
        //弹出用户选择窗口选择用户
        layer.open({
            type: 1,
            area: ['800px','500px'],
            fix: false, //不固定
            maxmin: true,
            shade:0.5,
            title: "用户列表",
            content: $('#userGrid'),
            btn:["保存","取消"],
            yes: function(index, layero){
                var id = $('#sectionList3').jqGrid('getGridParam','selrow');
                if(id==null || id.length==0){
                    layer.msg('请先选择用户', {icon: 2,time: 1000});
                    return false;
                }
                var rowData = $("#sectionList3").jqGrid('getRowData',id);
                $("#colownername").val(rowData.name);
                $("#colowner").val(rowData.id);
                layer.close(index);
            }
        })
    }
}

function myformat(cellval,options,pos,cnt) {

    return "<div style='background-color: " + cellval + "'>"+cellval+"</div>";
}

function changeOwner() {
    var selectedValue = $("#coltype").val();
    if(selectedValue == 0) {
        $("#colownername").val("System");
        $("#colowner").val(SYSTEM_DEFAULT_ID);
    } else {
        var name = $("#colownername").val();
        var id = $("#colowner").val();

        if(name == "System" && id == SYSTEM_DEFAULT_ID) {
            $("#colownername").val("");
            $("#colowner").val();
        }
    }
}

/************************************
 *  添加病种
 *  add by zcw 2015-05-16
 * **********************************/
function AddSection() {

    clearData();
    $('#colvalue').colorpicker({color:"#ffffff"});
    layer.open({
        type: 1,
        area: ['800px', '500px'],
        fix: false, //不固定
        maxmin: false,
        shade: 0.6,
        title: "添加颜色设置",
        content: $("#addDialog"),
        btn: ["保存", "取消"],
        yes: function (index, layero) {
            if($('#colvalue').val() == '') {
                layer.msg('选择颜色', {icon: 2, time: 1000});
                return false;
            }
            $.post('../syscolor/save', {
                colcustomercode: $('#colcustomercode').val(), coltype: $('#coltype').val(),
                colobject: $('#colobject').val(), colmodule: $('#colmodule').val(),
                colobjectstate: $('#colobjectstate'+$('#colmodule').val()).val(), colvalue: $('#colvalue').val(),colowner:$('#colowner').val()
            }, function (data) {
                layer.close(index);
                layer.msg(data.message, {icon: 0, time: 3000});
                $("#sectionList").trigger('reloadGrid');
            });

        }
    });
}
/************************************
 *  删除病种
 *  add by zcw 2015-05-16
 * **********************************/
function deleteSection() {
    var id = $('#sectionList').jqGrid('getGridParam', 'selrow');
    var rowData = $("#sectionList").jqGrid('getRowData', id);
    if (id == null || id.length == 0) {
        layer.msg('请先选择要删除的数据', {icon: 2, time: 1000});
        return false;
    }
    layer.confirm('确定删除选择的数据？', {icon: 2, title: '警告'}, function (index) {
        $.post('../syscolor/remove', {colorid: rowData.colorid}, function (data) {
            layer.close(index);
            $("#sectionList").trigger('reloadGrid');
        });
    });
}
/**
 * 查询科室
 */
function search() {
    var query = $('#query').val() || '';
    jQuery("#sectionList").jqGrid('setGridParam', {
        url: "../syscolor/query",
        //发送数据
        postData: {"query": query, "sidx": "colorid"},
        page: 1
    }).trigger('reloadGrid');//重新载入
}

/**
 * 编辑病种
 */
function editSection() {
    var rowId = $("#sectionList").jqGrid('getGridParam', 'selrow');
    var rowData = $("#sectionList").jqGrid('getRowData', rowId);
    if (!rowId || rowId == '' || rowId == null) {
        layer.alert("请先选择要编辑的数据", {icon: 1, title: "提示"});
        return false;
    }
    var bindData = $.ajax({
        type: "GET",
        url: "../syscolor/data",
        data: {
            colorid: rowData.colorid
        },
        success: function (msg) {
            //设置数据
            $('#colcustomername').val(rowData.colcustomername);
            $('#colownername').val(rowData.colownername);
            $('#colcustomercode').val(msg.colcustomercode);
            $('#coltype').val(msg.coltype);
            $('#colobject').val(msg.colobject);
            $('#colmodule').val(msg.colmodule);
            setSecond(msg.colmodule);
            $('#colobjectstate'+msg.colmodule).val(msg.colobjectstate);
            $('#colvalue').val(msg.colvalue);
            $('#colowner').val(msg.colowner);
            $('#colorid').val(msg.colorid);
            $('#colvalue').colorpicker({color: msg.colvalue});
            layer.open({
                type: 1,
                area: ['800px', '500px'],
                fix: false, //不固定
                maxmin: false,
                shade: 0.6,
                title: "编辑系统颜色设置",
                content: $("#addDialog"),
                btn: ["保存", "取消"],
                yes: function (index, layero) {
                    $.post('../syscolor/update', {
                        colcustomercode: $('#colcustomercode').val(), coltype: $('#coltype').val(),
                        colobject: $('#colobject').val(), colmodule: $('#colmodule').val(),
                        colobjectstate: $('#colobjectstate'+$('#colmodule').val()).val(), colvalue: $('#colvalue').val(),colowner:$('#colowner').val(),
                        colorid:$('#colorid').val()
                    }, function (data) {
                        layer.close(index);
                        layer.msg(data.message, {icon: 0, time: 3000});
                        $("#sectionList").trigger('reloadGrid');
                    });

                }
            });
        }
    });
}

$(function () {
    //表单校验
    $("#addSectionForm").Validform({
        tiptype: 4,
        callback: function () {
        }
    });

    $("#formateform").Validform({
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
    //$(window).on('resize.jqGrid', function () {
    //	$("#sectionList").jqGrid('setGridWidth', $("#mainTable").width());
    //	$("#sectionList").jqGrid( 'setGridHeight', $("#mainTable").height() );
    //})

    $(window).on('resize.jqGrid', function () {
        $('#sectionList').jqGrid('setGridWidth', $(".leftContent").width(), false);
        $('#sectionCode').jqGrid('setGridWidth', $(".rightContent").width(), false);
    });
    var clientHeight = $(window).innerHeight();
    var height = clientHeight - $('#head').height() - $('#toolbar').height() - $('.footer-content').height() - 150;

    $("#sectionList").jqGrid({
        caption: "系统颜色设置列表",
        url: "../syscolor/query",
        mtype: "GET",
        datatype: "json",
        width: $('.leftContent').width(),
        colNames: ['colorid', '客户', 'colcustomercode', '设置归属','colowner','归属用户', '设置的颜色', '颜色对象', '对象状态', '模块'],
        colModel: [
            {name: 'colorid', index: 'colorid', width: 30, hidden: true},
            {name: 'colcustomername', index: 'colcustomername', width: 30},
            {name: 'colcustomercode', index: 'colcustomercode', width: 40, hidden: true},
            {name: 'coltype', index: 'coltype', width: 40,formatter: "select",editoptions: {value: "1:用户私有;0:系统公用"}},
            {name: 'colowner', index: 'colowner', width: 30, hidden:true},
            {name: 'colownername', index: 'colownername', width: 30},
            {name: 'colvalue', index: 'colvalue', width: 30,formatter:myformat},
            {
                name: 'colobject',
                index: 'colobject',
                width: 30,
                formatter: "select",
                editoptions: {value: "Requisition:申请;Sample:标本;SamplingBlock:材块;ParaffinBlock:蜡块;Slide:玻片;Order:医嘱"}
            },

            {
                name: 'colobjectstate',
                index: 'colobjectstate',
                width: 30,
                formatter: "select",
                editoptions: {value: "0:已登记;1:已取材;2:已包埋;3:已切片;4:已初诊;5:已审核;6:已发送;7:会诊中;8:报告已打印;9:未取材;10:待包埋;11:待切片;12:未制作;13:已制作;14:已打印;"}
            },
            {name: 'colmodule', index: 'colmodule', width: 30}
        ],
        loadComplete: function () {
            var table = this;
            setTimeout(function () {
                updatePagerIcons(table);
            }, 0);
        },
        ondblClickRow: function (id) {
            editSection();
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

        }
    });

    $("#sectionList2").jqGrid({
        caption: "医院列表",
        url: "../set/hospital/queryHospital",
        mtype: "GET",
        datatype: "json",
        width:$('.leftContent').width(),
        colNames: ['id','医院名称', '联系电话', '组织代码', '地址'],
        colModel: [
            { name: 'id', index: 'id', width: 30, hidden: true },
            { name: 'name', index: 'name', width: 30},
            { name: 'phone', index: 'phone', width: 60},
            { name: 'idCard', index: 'idCard', width: 50 },
            { name: 'address', index: 'address', width: 50}
        ],
        loadComplete : function() {
            var table = this;
            setTimeout(function(){
                updatePagerIcons(table);
            }, 0);
        },
        ondblClickRow: function (id) {
        },
        viewrecords: true,
        shrinkToFit: true,
        altRows:true,
        height: 'auto',
        rowNum: 10,
        rowList:[10,20,30],
        rownumbers: true, // 显示行号
        rownumWidth: 35, // the width of the row numbers columns
        pager: "#pager2",
        onSelectRow: function(id){

        }
    });

    $("#sectionList3").jqGrid({
        caption: "用户列表",
        url: "../pimsuser/userlist",
        mtype: "GET",
        datatype: "json",
        width:$('.leftContent').width(),
        colNames: ['id','姓名', '登录账号'],
        colModel: [
            { name: 'id', index: 'id', width: 30, hidden: true },
            { name: 'name', index: 'name', width: 30},
            { name: 'username', index: 'phone', width: 60}
        ],
        loadComplete : function() {
            var table = this;
            setTimeout(function(){
                updatePagerIcons(table);
            }, 0);
        },
        ondblClickRow: function (id) {
        },
        viewrecords: true,
        shrinkToFit: true,
        altRows:true,
        height: 'auto',
        rowNum: 10,
        rowList:[10,20,30],
        rownumbers: true, // 显示行号
        rownumWidth: 35, // the width of the row numbers columns
        pager: "#pager3",
        onSelectRow: function(id){

        }
    });
});
function clearData() {
    $('#colcustomercode').val('');
    $('#colcustomername').val('');
    $('#colownername').val('');
    $('#colowner').val('');
    $('#coltype').val('1');
    $('#colobject').val('Requisition');
    $('#colmodule').val('0');
    for(var i=0;i<6;i++){
    $('#colobjectstate'+i).val('0');}
    $('#colvalue').val('');
}
function updatePagerIcons(table) {
    var replacement =
    {
        'ui-icon-seek-first': 'ace-icon fa fa-angle-double-left bigger-140',
        'ui-icon-seek-prev': 'ace-icon fa fa-angle-left bigger-140',
        'ui-icon-seek-next': 'ace-icon fa fa-angle-right bigger-140',
        'ui-icon-seek-end': 'ace-icon fa fa-angle-double-right bigger-140'
    };
    $('.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon').each(function () {
        var icon = $(this);
        var $class = $.trim(icon.attr('class').replace('ui-icon', ''));
        if ($class in replacement) icon.attr('class', 'ui-icon ' + replacement[$class]);
    })
}

function setSecond(obj){
    var val = obj.value;
    switch(val){
        case ("0"):
            for(var i = 0;i<6;i++){
                $("#colobjectstate"+i).css("display","none");
            }
            $("#colobjectstate0").css("display","block");
            break;

        case ("1"):
            for(var i = 0;i<6;i++){
                $("#colobjectstate"+i).css("display","none");
            }
            $("#colobjectstate1").css("display","block");
            break;

        case ("2"):
            for(var i = 0;i<6;i++){
                $("#colobjectstate"+i).css("display","none");
            }
            $("#colobjectstate2").css("display","block");
            break;

        case ("3"):
            for(var i = 0;i<6;i++){
                $("#colobjectstate"+i).css("display","none");
            }
            $("#colobjectstate3").css("display","block");
            break;

        case ("4"):
            for(var i = 0;i<6;i++){
                $("#colobjectstate"+i).css("display","none");
            }
            $("#colobjectstate4").css("display","block");
            break;

        case ("5"):
            for(var i = 0;i<6;i++){
                $("#colobjectstate"+i).css("display","none");
            }
            $("#colobjectstate5").css("display","block");
            break;
        }
}