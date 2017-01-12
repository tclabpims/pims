function chooseTestItem() {
    if($("#pathologySelect").val() == null || $("#pathologySelect").val() == ""){
        layer.msg("请先选择病种!",{icon:2,time:1000});
        return;
    }
    layer.open({
        type: 1,
        area: ['850px', '500px'],
        fix: false, //不固定
        maxmin: false,
        shade: 0.6,
        title: "检查项目列表",
        content: $("#itemGrid"),
        btn: ["确定", "取消"],
        success:function() {
            jQuery("#itemList").jqGrid('setGridParam', {
                url: "../estitem/query",
                //发送数据
                postData: {"pathologyId": $("#pathologySelect").val(), "sidx": "testitemid","tesitemtype":2},
                page: 1
            }).trigger('reloadGrid');//重新载入
        },
        yes: function (index, layero) {
            var id = $("#itemList").jqGrid('getGridParam', 'selarrrow');

            if (id == null || id.length == 0) {
                layer.msg('请先选择检查项目', {icon: 2, time: 1000});
                return false;
            }
            var optArray = $("#itemSelect option").map(function () {
                return $(this).val();
            }).get();
            for (var i = 0; i < id.length; i++) {
                var rowData = $("#itemList").jqGrid('getRowData', id[i]);
                var index1 = $.inArray(rowData.testitemid, optArray);
                if (index1 == -1) {
                    $("#itemSelect").append("<option value='" + rowData.testitemid + "'>" + rowData.teschinesename + "</option>");
                }
            }
            layer.close(index);
        }
    });
}

function removeTestItem() {
    $("#itemSelect option:selected").remove();
}

function clearChoosedItem() {
    $("#itemSelect").empty();
}

function getPathologyType() {
    $.get("../pspathology/dcm/querytype", {}, function (data) {
        if(data != null && data.length > 0) {
            for(var i = 0; i < data.length; i++) {
                $("#pathologySelect").append("<option value='" + data[i].pathologyLibId + "'>" + data[i].pathologyLib + "</option>");
            }
            var id = $('#sectionList').jqGrid('getGridParam', 'selrow');
            var rowData = $("#sectionList").jqGrid('getRowData', id);
            if (id == null || id.length == 0) {
                $("#pathologySelect").val(rowData.pathologyId);
            }
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
        title: "添加套餐信息",
        content: $("#addDialog"),
        btn: ["保存", "取消"],
        success:function () {
            getPathologyType();
        },
        yes: function (index, layero) {
            var packageName = $.trim($("#packageName").val());
            if (packageName == "") {
                layer.msg('请填写套餐名', {icon: 2, time: 1000});
                return false;
            }
            if ($("#itemSelect option").length == 0) {
                layer.msg('请选择检查项目', {icon: 2, time: 1000});
                return false;
            }
            var items = $("#itemSelect option").map(function () {
                return $(this).val();
            }).get();
            if (items != null && items.length > 0) items = items.join(",");
            $.get('../package/edit', {
                selectedItems: items,
                packageName: $("#packageName").val(),
                pathologyId: $("#pathologySelect").val(),
                packageId: $("#packageId").val()
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
        $.get('../package/remove', {packageId: rowData.packageId}, function (data) {
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
        url: "../package/query",
        //发送数据
        postData: {"query": query, "sidx": "packageId"},
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
    $("#itemSelect").empty();
    $.get('../package/testitems', {packageId: rowData.packageId}, function (msg) {
            var itemData = msg.rows;
            for(var i = 0; i < itemData.length; i++) {
                    $("#itemSelect").append("<option value='" + itemData[i].testitemid + "'>" + itemData[i].teschinesename + "</option>");
            }
            layer.open({
                type: 1,
                area: ['800px', '500px'],
                fix: false, //不固定
                maxmin: false,
                shade: 0.6,
                title: "编辑套餐信息",
                content: $("#addDialog"),
                btn: ["保存", "取消"],
                success: function (layero, index) {
                    getPathologyType();
                    $("#packageName").val(rowData.packageName);
                    $("#packageId").val(rowData.packageId);
                },
                yes: function (index, layero) {
                    var packageName = $.trim($("#packageName").val());
                    if (packageName == "") {
                        layer.msg('请填写套餐名', {icon: 2, time: 1000});
                        return false;
                    }
                    if ($("#itemSelect option").length == 0) {
                        layer.msg('请选择检查项目', {icon: 2, time: 1000});
                        return false;
                    }
                    var items = $("#itemSelect option").map(function () {
                        return $(this).val();
                    }).get();
                    if (items != null && items.length > 0) items = items.join(",");
                    $.get('../package/edit', {
                        selectedItems: items,
                        packageName: $("#packageName").val(),
                        pathologyId: $("#pathologySelect").val(),
                        packageId: $("#packageId").val()
                    }, function (data) {
                        layer.close(index);
                        $("#sectionList").trigger('reloadGrid');
                    });
                }
            });
        }
    );
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

    $("#itemList").jqGrid({
        //url: "../estitem/query",
        mtype: "GET",
        datatype: "json",
        width: 800,
        multiselect: true,
        colNames: ['id', '项目中文名称', '项目英文名称'],
        colModel: [
            {
                name: 'testitemid', index: 'testitemid', width: 20, hidden: true
            },
            {name: 'teschinesename', index: 'teschinesename', width: 30},
            {name: 'tesenglishname', index: 'tesenglishname', width: 30}
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
        height: 450,
        rowNum: 10,
        rowList: [100, 200, 300],
        rownumbers: true, // 显示行号
        rownumWidth: 35, // the width of the row numbers columns
        pager: "#itemListPager",
        onclick: function (id) {
            //$("#itemGrid").jqGrid('setCell',id,'choose', "True");
        }
    });


    $("#sectionList").jqGrid({
        caption: "套餐项目列表",
        url: "../package/query",
        mtype: "GET",
        datatype: "json",
        width: $('.leftContent').width(),
        colNames: ['套餐编号', '套餐名称', '使用次数', '套餐项目数','pathologyId'],
        colModel: [
            {name: 'packageId', index: 'packageId', width: 30},
            {name: 'packageName', index: 'packageName', width: 30},
            {name: 'packageUseTimes', index: 'packageUseTimes', width: 30},
            {name: 'packageItems', index: 'packageItems', width: 30},
            {name: 'pathologyId', index: 'pathologyId', hidden: true}
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
    $('#packageId').val('');
    $('#packageName').val('');
    $("#itemSelect").empty();
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