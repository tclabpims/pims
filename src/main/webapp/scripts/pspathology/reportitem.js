/**
 * Created by lenovo on 2016/10/6.
 */
function showFieldConfig() {
    layer.open({
        type: 1,
        area: ['800px', '500px'],
        fix: false, //不固定
        maxmin: true,
        shade: 0.5,
        title: "系统申请字段配置",
        content: $('#hospitalGrid'),
        btn: ["保存", "取消"],
        yes: function (index, layero) {
            var treeObj = $.fn.zTree.getZTreeObj("tree");
            var nodes = treeObj.getSelectedNodes();
            if (!nodes || nodes.length == 0 || nodes[0].id == 0) {
                layer.msg('请先选择节点', {icon: 2, time: 1000});
                return false;
            }
            layer.close(index);
        }
    })
}

/************************************
 *  添加病种
 *  add by zcw 2015-05-16
 * **********************************/
function AddSection() {
    clearData();
    layer.open({
        type: 1,
        area: ['800px', '500px'],
        fix: false, //不固定
        maxmin: false,
        shade: 0.6,
        title: "添加系统报告项目",
        content: $("#addDialog"),
        btn: ["保存", "取消"],
        yes: function (index, layero) {
            if($('#rptelementid').val() == '') {
                layer.msg('请填写Web元素标题', {icon: 2, time: 1000});
                return false;
            }
            $.post('../reportitem/edit', {
                rptname: $('#rptname').val(), rptenglishname: $('#rptenglishname').val(),
                rptelementid: $('#rptelementid').val(),
                rptelementname: $('#rptelementname').val(), rptpinyincode: $('#rptpinyincode').val(),
                rptfivestroke: $('#rptfivestroke').val(), rptitemtype: $('#rptitemtype').val(),
                rptuseflag: $('#rptuseflag').val(), rptdefaultvalue: $('#rptdefaultvalue').val(),
                rptrefvalue1: $('#rptrefvalue1').val(), rptavgvalue1: $('#rptavgvalue1').val(),
                rptrefvalue2: $('#rptrefvalue2').val(), rptavgvalue2: $('#rptavgvalue2').val(),
                rptitemsort: "A" + $("#FN").val() + $("#SN").val() + $("#TN").val()
            }, function (data) {
                layer.close(index);
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
        $.post('../reportitem/remove', {reportitemid: rowData.reportitemid}, function (data) {
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
        url: "../reportitem/query",
        //发送数据
        postData: {"query": query, "sidx": "reportitemid"},
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
        url: "../reportitem/data",
        data: {
            reportitemid: rowData.reportitemid
        },
        success: function (msg) {
            //设置数据
            $('#reportitemid').val(rowData.reportitemid);
            $('#rptname').val(msg.rptname);
            $('#rptenglishname').val(msg.rptenglishname);
            $('#rptelementid').val(msg.rptelementid);
            $('#rptelementname').val(msg.rptelementname);
            $('#rptitemsort').val(msg.rptitemsort);
            $('#rptpinyincode').val(msg.rptpinyincode);
            $('#rptfivestroke').val(msg.rptfivestroke);
            $('#rptitemtype').val(msg.rptitemtype);
            $('#rptuseflag').val(msg.rptuseflag);
            $("#rptdefaultvalue").val(msg.rptdefaultvalue);
            $("#rptrefvalue1").val(msg.rptrefvalue1);
            $("#rptavgvalue1").val(msg.rptavgvalue1);
            $("#rptrefvalue2").val(msg.rptrefvalue2);
            $("#rptavgvalue2").val(msg.rptavgvalue2);
            var sortNo = msg.rptitemsort;
            $("#FN").val(sortNo.charAt(1));
            $("#SN").val(sortNo.charAt(2));
            $("#TN").val(sortNo.charAt(3));
            layer.open({
                type: 1,
                area: ['800px', '500px'],
                fix: false, //不固定
                maxmin: false,
                shade: 0.6,
                title: "编辑申请检查项目",
                content: $("#addDialog"),
                btn: ["保存", "取消"],
                yes: function (index, layero) {
                    $.post('../reportitem/edit', {
                        reportitemid: $('#reportitemid').val(),
                        rptname: $('#rptname').val(),
                        rptenglishname: $('#rptenglishname').val(),
                        rptelementid: $('#rptelementid').val(),
                        rptelementname: $('#rptelementname').val(),
                        rptpinyincode: $('#rptpinyincode').val(),
                        rptfivestroke: $('#rptfivestroke').val(),
                        rptitemtype: $('#rptitemtype').val(),
                        rptuseflag: $('#rptuseflag').val(),
                        rptdefaultvalue: $('#rptdefaultvalue').val(),
                        rptrefvalue1: $('#rptrefvalue1').val(),
                        rptavgvalue1: $('#rptavgvalue1').val(),
                        rptrefvalue2: $('#rptrefvalue2').val(),
                        rptavgvalue2: $('#rptavgvalue2').val(),
                        rptitemsort: "A" + $("#FN").val() + $("#SN").val() + $("#TN").val()
                    }, function (data) {
                        layer.close(index);
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
        caption: "系统报告项目",
        url: "../reportitem/query",
        mtype: "GET",
        datatype: "json",
        width: $('.leftContent').width(),
        colNames: ['排序号', 'reportitemid', '项目名称(中文)', '项目名称(英文)', 'rptelementid', 'Web元素标题', '使用状态', '类型', '参考值1', '平均值1', '参考值2', '平均值2'],
        colModel: [
            {name: 'rptitemsort', index: 'rptitemsort', width: 30},
            {name: 'reportitemid', index: 'reportitemid', width: 30, hidden: true},
            {name: 'rptname', index: 'rptname', width: 40},
            {name: 'rptenglishname', index: 'rptenglishname', width: 40},
            {name: 'rptelementid', index: 'rptelementid', width: 30, hidden: true},
            {name: 'rptelementname', index: 'rptelementname', width: 30},
            {
                name: 'rptuseflag',
                index: 'rptuseflag',
                width: 30,
                formatter: "select",
                editoptions: {value: "1:启用;0:停用"}
            },
            {
                name: 'rptitemtype',
                index: 'rptitemtype',
                width: 30,
                formatter: "select",
                editoptions: {value: "1:输入框;2:复选框;3:下拉列表框"}
            },
            {name: 'rptrefvalue1', index: 'rptrefvalue1', width: 30},
            {name: 'rptavgvalue1', index: 'rptavgvalue1', width: 30},
            {name: 'rptrefvalue2', index: 'rptrefvalue2', width: 30},
            {name: 'rptavgvalue2', index: 'rptavgvalue2', width: 30}
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

});
function clearData() {
    $('#rptname').val('');
    $('#rptenglishname').val('');
    $('#rptelementid').val('');
    $('#rptelementname').val('');
    $('#rptuseflag').val('1');
    $('#rptitemtype').val('1');
    $('#rptrefvalue1').val('');
    $('#rptavgvalue1').val('');
    $('#rptrefvalue2').val('');
    $('#rptavgvalue2').val('');
    $('#rptitemsort').val('');
    $('#rptdefaultvalue').val('');
    $('#rptpinyincode').val('');
    $('#rptfivestroke').val('');
    $("#FN").val('0');
    $("#SN").val('0');
    $("#TN").val('0');
    var treeObj = $.fn.zTree.getZTreeObj("tree");
    var nodes = treeObj.getSelectedNodes();
    if(nodes != null && nodes.length != 0) {
        treeObj.cancelSelectedNode(nodes[0]);
    }

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