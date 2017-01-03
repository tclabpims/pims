/**
 * Created by lenovo on 2016/10/6.
 */

function removeRefData() {
    $('#basrefdataid').val('');
    $('#basrefdataname').val('');
}

function showDataGrid() {
    var dataType = parseInt($('#bastype').val());
    switch (dataType) {
        case 1:
            layer.open({
                type: 1,
                area: ['800px', '500px'],
                fix: false, //不固定
                maxmin: true,
                shade: 0.5,
                title: "申请材料列表",
                content: $('#reqMaterialGrid'),
                btn: ["保存", "取消"],
                yes: function (index, layero) {
                    var id = $('#sectionList3').jqGrid('getGridParam', 'selrow');
                    if (id == null || id.length == 0) {
                        layer.msg('请先选择申请材料', {icon: 2, time: 1000});
                        return false;
                    }
                    var rowData = $("#sectionList3").jqGrid('getRowData', id);
                    $('#basrefdataid').val(rowData.materialid);
                    $('#basrefdataname').val(rowData.matname);
                    layer.close(index);
                }
            });
            break;
        case 2:
            layer.open({
                type: 1,
                area: ['800px', '500px'],
                fix: false, //不固定
                maxmin: true,
                shade: 0.5,
                title: "申请字段列表",
                content: $('#treeArea'),
                btn: ["保存", "取消"],
                yes: function (index, layero) {
                    var treeObj = $.fn.zTree.getZTreeObj("tree");
                    var nodes = treeObj.getSelectedNodes();
                    if (!nodes || nodes.length == 0 || nodes[0].id == 0) {
                        layer.msg('请先选择节点', {icon: 2, time: 1000});
                        return false;
                    }
                    $('#basrefdataid').val(nodes[0].id);
                    $('#basrefdataname').val(nodes[0].name);
                    layer.close(index);
                }
            });
            break;
        case 3:
            layer.open({
                type: 1,
                area: ['800px', '500px'],
                fix: false, //不固定
                maxmin: true,
                shade: 0.5,
                title: "报告项目列表",
                content: $('#reportItemGrid'),
                btn: ["保存", "取消"],
                yes: function (index, layero) {
                    var id = $('#sectionList5').jqGrid('getGridParam', 'selrow');
                    if (id == null || id.length == 0) {
                        layer.msg('请先选择报告项目', {icon: 2, time: 1000});
                        return false;
                    }
                    var rowData = $("#sectionList5").jqGrid('getRowData', id);
                    $('#basrefdataid').val(rowData.reportitemid);
                    $('#basrefdataname').val(rowData.rptname);
                    layer.close(index);
                }
            });
            break;
        case 4:
            layer.open({
                type: 1,
                area: ['800px', '500px'],
                fix: false, //不固定
                maxmin: true,
                shade: 0.5,
                title: "检查项目列表",
                content: $('#testItemGrid'),
                btn: ["保存", "取消"],
                yes: function (index, layero) {
                    var id = $('#sectionList6').jqGrid('getGridParam', 'selrow');
                    if (id == null || id.length == 0) {
                        layer.msg('请先选择报告项目', {icon: 2, time: 1000});
                        return false;
                    }
                    var rowData = $("#sectionList6").jqGrid('getRowData', id);
                    $('#basrefdataid').val(rowData.testitemid);
                    $('#basrefdataname').val(rowData.teschinesename);
                    layer.close(index);
                }
            });
            break;
        case 5:
            layer.open({
                type: 1,
                area: ['800px', '500px'],
                fix: false, //不固定
                maxmin: true,
                shade: 0.5,
                title: "申请送检材料",
                content: $('#treeArea'),
                btn: ["保存", "取消"],
                yes: function (index, layero) {
                    var treeObj = $.fn.zTree.getZTreeObj("tree");
                    var nodes = treeObj.getSelectedNodes();
                    if (!nodes || nodes.length == 0 || nodes[0].id == 0) {
                        layer.msg('请先选择节点', {icon: 2, time: 1000});
                        return false;
                    }
                    $('#basrefdataid').val(nodes[0].id);
                    $('#basrefdataname').val(nodes[0].name);
                    layer.close(index);
                }
            });
            break;
    }
}


function showPathology() {
    if($('#bascustomercode').val() == "") {
        layer.alert("请先选择客户",{icon:1,title:"提示"});
        return false;
    }
    jQuery("#sectionList7").jqGrid('setGridParam', {
        url: "../pspathology/dcm/query",
        mtype: "GET",
        datatype: "json",
        postData: {"type": 1, "hospitalId": $('#bascustomercode').val()}
    }).trigger('reloadGrid');//重新载入
    layer.open({
        type: 1,
        area: ['800px', '500px'],
        fix: false, //不固定
        maxmin: true,
        shade: 0.5,
        title: "病种列表",
        content: $('#pathologyGrid'),
        btn: ["保存", "取消"],
        yes: function (index, layero) {
            var id = $('#sectionList7').jqGrid('getGridParam', 'selrow');
            if (id == null || id.length == 0) {
                layer.msg('请先选择病种', {icon: 2, time: 1000});
                return false;
            }
            var rowData = $("#sectionList7").jqGrid('getRowData', id);
            $("#baspathologyid").val(rowData.pathologyid);
            $("#baspathologyname").val(rowData.patnamech);
            layer.close(index);
        }
    })
}

function showHospital(query) {
    layer.open({
        type: 1,
        area: ['800px', '500px'],
        fix: false, //不固定
        maxmin: true,
        shade: 0.5,
        title: "医院列表",
        content: $('#hospitalGrid'),
        btn: ["保存", "取消"],
        yes: function (index, layero) {
            var id = $('#sectionList2').jqGrid('getGridParam', 'selrow');
            if (id == null || id.length == 0) {
                layer.msg('请先选择客户', {icon: 2, time: 1000});
                return false;
            }
            var rowData = $("#sectionList2").jqGrid('getRowData', id);
            if (query) {
                $("#queryName").val(rowData.name);
                $("#query").val(rowData.id);
            } else {
                $("#bascustomercode").val(rowData.id);
                $("#bascustomername").val(rowData.name);
            }

            $("#baspathologyid").val('');
            $("#baspathologyname").val('');
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
        title: "添加客户基础数据设置",
        content: $("#addDialog"),
        btn: ["保存", "取消"],
        yes: function (index, layero) {
            if ($('#baspathologyid').val() == '' || $('#bascustomercode').val() == '' || $('#basrefdataid').val() == '') {
                layer.msg('请先完善信息', {icon: 2, time: 1000});
                return false;
            }
            $.post('../customerdata/edit', {
                bascustomercode: $('#bascustomercode').val(), baspathologyid: $('#baspathologyid').val(),
                basrefdataalias: $('#basrefdataalias').val(), basrptItemSort: getPrintOptionValue(),
                bastype: $('#bastype').val(),
                basrefdataid: $('#basrefdataid').val(), basuseflag: $('#basuseflag').val()
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
        $.post('../customerdata/remove', {basedataid: rowData.basedataid}, function (data) {
            layer.close(index);
            $("#sectionList").trigger('reloadGrid');
        });
    });
}

function clearQuery() {
    $("#queryName").val('');
    $("#query").val('');
    search();
}

function setPrintOption() {
    if(document.getElementById("basrptItemSort").checked) {
        $("#basrptItemSort").val(1);
    } else {
        $("#basrptItemSort").val(0);
    }
}

function getPrintOptionValue() {
    return document.getElementById("basrptItemSort").checked?1:0;
}

/**
 * 查询科室
 */
function search() {
    var query = $('#query').val() || '';
    jQuery("#sectionList").jqGrid('setGridParam', {
        url: "../customerdata/query",
        //发送数据
        postData: {"query": query, "sidx": "basedataid"},
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
        url: "../customerdata/data",
        data: {
            basedataid: rowData.basedataid
        },
        success: function (msg) {
            //设置数据
            $('#basedataid').val(rowData.basedataid);
            $('#bascustomercode').val(msg.bascustomercode);
            $('#baspathologyid').val(msg.baspathologyid);
            $('#bastype').val(msg.bastype);
            $('#basrefdataid').val(msg.basrefdataid);
            $('#basuseflag').val(msg.basuseflag);
            if(msg.basrptItemSort == 1)
                $("#basrptItemSort").attr("checked", true);
            else
                $("#basrptItemSort").attr("checked", false);
            $('#basrefdataalias').val(msg.basrefdataalias);
            $('#baspathologyname').val(rowData.baspathologyname);
            $('#bascustomername').val(rowData.bascustomername);
            $('#basrefdataname').val(rowData.basrefdataname);
            layer.open({
                type: 1,
                area: ['800px', '500px'],
                fix: false, //不固定
                maxmin: false,
                shade: 0.6,
                title: "编辑客户基础数据设置",
                content: $("#addDialog"),
                btn: ["保存", "取消"],
                yes: function (index, layero) {
                    if ($('#baspathologyid').val() == '' || $('#bascustomercode').val() == '' || $('#basrefdataid').val() == '') {
                        layer.msg('请先完善信息', {icon: 2, time: 1000});
                        return false;
                    }
                    $.post('../customerdata/edit', {
                        bascustomercode: $('#bascustomercode').val(), baspathologyid: $('#baspathologyid').val(),
                        bastype: $('#bastype').val(), basedataid: $('#basedataid').val(),
                        basrptItemSort: getPrintOptionValue(), basrefdataalias: $('#basrefdataalias').val(),
                        basrefdataid: $('#basrefdataid').val(), basuseflag: $('#basuseflag').val()
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
        caption: "客户基础数据设置列表",
        url: "../customerdata/query",
        mtype: "GET",
        datatype: "json",
        width: $('.leftContent').width(),
        colNames: ['basedataid', 'baspathologyid', 'bascustomercode', 'basrefdataid', '病种名称', '客户', '数据类型', '关联数据名称','别名' ,'报告打印字段','使用状态', '创建时间'],
        colModel: [
            {name: 'basedataid', index: 'basedataid', width: 30, hidden: true},
            {name: 'baspathologyid', index: 'baspathologyid', width: 30, hidden: true},
            {name: 'bascustomercode', index: 'bascustomercode', width: 30, hidden: true},
            {name: 'basrefdataid', index: 'basrefdataid', width: 30, hidden: true},
            {name: 'baspathologyname', index: 'baspathologyname', width: 30},
            {name: 'bascustomername', index: 'bascustomername', width: 30},
            {
                name: 'bastype',
                index: 'bastype',
                width: 30,
                formatter: "select",
                editoptions: {value: "1:申请材料数据;2:申请字段数据;3:报告项目数据;4:申请检查项目数据;5:申请送检材料"}
            },
            {name: 'basrefdataname', index: 'basrefdataname', width: 30},
            {name: 'basrefdataalias', index: 'basrefdataalias', width: 30},
            {name: 'basrptItemSort', index: 'basrptItemSort', width: 30,formatter: "select",editoptions: {value: "1:是;0:否"}},
            {
                name: 'basuseflag',
                index: 'basuseflag',
                width: 30,
                formatter: "select",
                editoptions: {value: "1:启用;0:停用"}
            },
            {name: 'bascreatetime', index: 'bascreatetime', width: 30}
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
        rowList: [10, 20,30,40,50],
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
        width: $('.leftContent').width(),
        colNames: ['id', '医院名称', '联系电话', '组织代码', '地址'],
        colModel: [
            {name: 'id', index: 'id', width: 30, hidden: true},
            {name: 'name', index: 'name', width: 30},
            {name: 'phone', index: 'phone', width: 60},
            {name: 'idCard', index: 'idCard', width: 50},
            {name: 'address', index: 'address', width: 50}
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
        height: 'auto',
        rowNum: 10,
        rowList: [10, 20,30,40,50],
        rownumbers: true, // 显示行号
        rownumWidth: 35, // the width of the row numbers columns
        pager: "#pager2",
        onSelectRow: function (id) {

        }
    });

    $("#sectionList3").jqGrid({
        caption: "申请材料",
        url: "../reqmaterial/query",
        mtype: "GET",
        datatype: "json",
        width: $('.leftContent').width(),
        colNames: ['排序号', 'materialid', '材料名称', 'mattype', '材料类型', '特殊类型', '使用状态', '拼音码', '五笔码'],
        colModel: [
            {name: 'matsort', index: 'matsort', width: 30},
            {name: 'materialid', index: 'materialid', width: 30, hidden: true},
            {name: 'matname', index: 'matname', width: 30},
            {name: 'mattype', index: 'mattype', width: 30, hidden: true},
            {name: 'mattypename', index: 'mattypename', width: 50},
            {name: 'matspecial', index: 'matspecial', width: 30},
            {
                name: 'matuseflag',
                index: 'matuseflag',
                width: 30,
                formatter: "select",
                editoptions: {value: "1:启用;0:停用"}
            },
            {name: 'matpinyincode', index: 'matpinyincode', width: 30},
            {name: 'matfivestrokecode', index: 'matfivestrokecode', width: 30}
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
        rowList: [10, 20,30,40,50],
        rownumbers: true, // 显示行号
        rownumWidth: 35, // the width of the row numbers columns
        pager: "#pager3",
        onSelectRow: function (id) {

        }
    });

    var zTreeObj;
    // zTree 的参数配置，深入使用请参考 API 文档（setting 配置详解）
    /*******************************
     * ztree 设置参数
     ********************************/
    var setting = {
        data: {
            simpleData: {
                enable: true
            }
        },
        async: {
            enable: true,
            url: "../reqfield/treequery",
            dataType: "json",//默认text
            contentType: "application/json",
            type: "get"//默认post
        },
        check: {
            enable: true,
            chkStyle: "checkbox"
        },
        callback: {
            onClick: function (event, treeId, treeNode, clickFlag) {

            }
        },
        view: {}
    };
    // zTree 的数据属性，深入使用请参考 API 文档（zTreeNode 节点数据详解）
    var zNodes = [];
    $(document).ready(function () {
        zTreeObj = $.fn.zTree.init($("#tree"), setting, zNodes);
    });

    $("#sectionList5").jqGrid({
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

        },
        viewrecords: true,
        shrinkToFit: true,
        altRows: true,
        height: height,
        rowNum: 10,
        rowList: [10, 20,30,40,50],
        rownumbers: true, // 显示行号
        rownumWidth: 35, // the width of the row numbers columns
        pager: "#pager5",
        onSelectRow: function (id) {

        }
    });

    $("#sectionList6").jqGrid({
        caption: "申请检查项目",
        url: "../estitem/query",
        mtype: "GET",
        datatype: "json",
        width: $('.leftContent').width(),
        colNames: ['排序号', 'testitemid', '中文名称', '英文名称', 'tespathologyid', '病种类别', '项目类型', '内部遗嘱处理', '是否需要计费', '使用状态', '拼音码', '五笔码'],
        colModel: [
            {name: 'tesitemsort', index: 'tesitemsort', width: 30},
            {name: 'testitemid', index: 'testitemid', width: 30, hidden: true},
            {name: 'teschinesename', index: 'teschinesename', width: 30},
            {name: 'tesenglishname', index: 'tesenglishname', width: 30},
            {name: 'tespathologyid', index: 'tespathologyid', width: 30, hidden: true},
            {name: 'tespathologyname', index: 'tespathologyname', width: 30},
            {
                name: 'tesitemtype',
                index: 'tesitemtype',
                width: 30,
                formatter: "select",
                editoptions: {value: "1:申请开单项目;2:内部医嘱检测项目;3:内部医嘱技术处理项目"}
            },
            {
                name: 'tesitemhandle',
                index: 'tesitemhandle',
                width: 50,
                formatter: "select",
                editoptions: {value: "1:取材处理;2:切片处理"}
            },
            {
                name: 'tesischarge',
                index: 'tesischarge',
                width: 30,
                formatter: "select",
                editoptions: {value: "1:是;0:否"}
            },
            {
                name: 'tesuseflag',
                index: 'tesuseflag',
                width: 30,
                formatter: "select",
                editoptions: {value: "1:启用;0:停用"}
            },
            {name: 'tespinyincode', index: 'tespinyincode', width: 30},
            {name: 'tesfivestroke', index: 'tesfivestroke', width: 30}
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
        rowList: [10, 20,30,40,50],
        rownumbers: true, // 显示行号
        rownumWidth: 35, // the width of the row numbers columns
        pager: "#pager6",
        onSelectRow: function (id) {

        }
    });

    $("#sectionList7").jqGrid({
        caption: "病种类别列表",
        width: $('.leftContent').width(),
        colNames: ['pathologyid', '排序号', '病种名称', '病种名称（英文）', '病种分类', '使用状态', '是否取材', '是否特检'],
        colModel: [
            {name: 'pathologyid', index: 'pathologyid', width: 30, hidden: true},
            {name: 'patsort', index: 'patsort', width: 30},
            {name: 'patnamech', index: 'patnamech', width: 60},
            {name: 'patnameen', index: 'patnameen', width: 50},
            {
                name: 'patclass',
                index: 'patclass',
                width: 50,
                formatter: "select",
                editoptions: {value: "1:常规细胞学;2:液基细胞学;3:免疫组化;4:病理会诊;5:常规病理;6:术中冰冻;7:HPV;8:外周血细胞;9:骨髓细胞学"}
            },
            {
                name: 'patuseflag',
                index: 'patuseflag',
                width: 30,
                formatter: "select",
                editoptions: {value: "0:使用;1:停用"}
            },
            {
                name: 'patissampling',
                index: 'patissampling',
                width: 30,
                formatter: "select",
                editoptions: {value: "0:是;1:否"}
            },
            {
                name: 'patisspecialcheck',
                index: 'patisspecialcheck',
                width: 30,
                formatter: "select",
                editoptions: {value: "0:是;1:否"}
            }
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
        height: 'auto',
        rowNum: 10,
        rowList: [10, 20,30,40,50],
        rownumbers: true, // 显示行号
        rownumWidth: 35, // the width of the row numbers columns
        pager: "#pager7",
        onSelectRow: function (id) {

        }
    });

});
function clearData() {
    $('#bascustomercode').val('');
    $('#bascustomername').val('');
    $('#baspathologyid').val('');
    $('#baspathologyname').val('');
    $('#bastype').val('1');
    $('#basrefdataid').val('');
    $('#basrefdataname').val('');
    $('#basuseflag').val(1);
    $('#basedataid').val('');
    $("#basrptItemSort").attr("checked",false);
    $('#basrefdataalias').val('');

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