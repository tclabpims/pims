/**
 * Created by lenovo on 2016/10/6.
 */

var SYSTEM_DEFAULT_ID = 9999999999;

function initValue() {
    var selectedValue = $("#temtype").val();
    if(selectedValue == 0) {
        $("#temownername").val("System");
        $("#temownerid").val(SYSTEM_DEFAULT_ID);
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
                $("#temownername").val(rowData.name);
                $("#temownerid").val(rowData.id);
                layer.close(index);
            }
        })
    }
}

function changeOwner() {
    var selectedValue = $("#temtype").val();
    if(selectedValue == 0) {
        $("#temownername").val("System");
        $("#temownerid").val(SYSTEM_DEFAULT_ID);
    } else {
        var name = $("#temownername").val();
        var id = $("#temownerid").val();

        if(name == "System" && id == SYSTEM_DEFAULT_ID) {
            $("#temownername").val("");
            $("#temownerid").val();
        }
    }
}

function showPathology() {
    layer.open({
        type: 1,
        area: ['800px','500px'],
        fix: false, //不固定
        maxmin: true,
        shade:0.5,
        title: "病种列表",
        content: $('#pathologyGrid'),
        btn:["保存","取消"],
        yes: function(index, layero){
            var id = $('#sectionList1').jqGrid('getGridParam','selrow');
            if(id==null || id.length==0){
                layer.msg('请先选择病种类别', {icon: 2,time: 1000});
                return false;
            }
            var rowData = $("#sectionList1").jqGrid('getRowData',id);
            $("#tempathologyname").val(rowData.patnamech);
            $("#tempathologyid").val(rowData.pathologyid);
            layer.close(index);
        }
    })
}

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
                layer.msg('请先选择病种类别', {icon: 2,time: 1000});
                return false;
            }
            var rowData = $("#sectionList2").jqGrid('getRowData',id);
            $("#temcustomerid").val(rowData.id);
            $("#temcustomername").val(rowData.name);
            layer.close(index);
        }
    })
}

function reportFormat(op) {
    var id = $('#sectionList').jqGrid('getGridParam','selrow');
    var fid = $('#sectionCode').jqGrid('getGridParam','selrow');
    var rowData = $("#sectionList").jqGrid('getRowData',id);
    var rfid = $("#sectionCode").jqGrid('getRowData',fid);
    if(op == 1){//新增
        if(id==null || id.length==0){
            layer.msg('请先选择病种类别', {icon: 2,time: 1000});
            return false;
        }
        AddCode();
    } else if(op ==2){//修改
        if(fid==null || fid.length==0){
            layer.msg('请先选择报告单格式', {icon: 2,time: 1000});
            return false;
        }
        editReportFormat();
    } else if(op==3) {//删除
        if(fid==null || fid.length==0){
            layer.msg('请先选择要删除的数据', {icon: 2,time: 1000});
            return false;
        }
        layer.confirm('确定删除选择数据？', {icon: 2, title:'警告'}, function(index){
            $.post('../pspathology/dcm/removeReport',{formateid:rfid.formateid},function(data) {});
            $("#sectionCode").trigger('reloadGrid');
            layer.close(index);
        });
    }
}

/**
 * 编辑报告格式
 */
function editReportFormat(){
    var rowId = $("#sectionCode").jqGrid('getGridParam','selrow');
    var rowData = $("#sectionCode").jqGrid('getRowData',rowId);

    var rowId1 = $("#sectionList").jqGrid('getGridParam','selrow');
    var rowData1 = $("#sectionList").jqGrid('getRowData',rowId1);

    if(!rowId || rowId =='' || rowId==null){
        layer.alert("请先选择要编辑的数据",{icon:1,title:"提示"});
        return false;
    }
    var bindData = $.ajax({
        type : "GET",
        url: "../pspathology/dcm/reportData",
        data: {
            formateid : rowData.formateid
        },
        success:function( msg ) {
            //设置数据
            $('#formateid').val(rowData.formateid);
            $('#formname').val(msg.formname);
            $('#formweburl').val(msg.formweburl);
            $('#formpicturenum').val(msg.formpicturenum);
            $('#formremark').val(msg.formremark);
            $('#formuseflag').val(msg.formuseflag);
            $('#formisdefault').val(msg.formisdefault);
            var sortNo = msg.formsort;
            $("#FN1").val(sortNo.charAt(1));
            $("#SN1").val(sortNo.charAt(2));
            $("#TN1").val(sortNo.charAt(3));
            layer.open({
                type: 1,
                area: ['480px','520px'],
                fix: false, //不固定
                maxmin: false,
                shade:0.6,
                title: "编辑报告单格式",
                content: $("#addFormatDialog"),
                btn:["保存","取消"],
                yes: function(index, layero){
                    $.post('../pspathology/dcm/editFormat', {formateid:rowData.formateid,formname:$('#formname').val(),
                        formweburl : $('#formweburl').val(), formpicturenum : $('#formpicturenum').val(),
                        formremark : $('#formremark').val(), formuseflag : $('#formuseflag').val(),
                        formisdefault : $('#formisdefault').val(),formpathologyid : rowData1.pathologyid,
                        formsort : "A"+$("#FN1").val()+$("#SN1").val()+$("#TN1").val()
                    },function(data){
                        layer.close(index);
                        $("#sectionCode").trigger('reloadGrid');
                    })
                }
            });
        }
    });
}

/************************************
 *  新增报告单格式
 *  add by 909436637@qq.com 2016-09-29
 * **********************************/
function  AddCode(){
    clearData(2);
    var id = $('#sectionList').jqGrid('getGridParam','selrow');
    var rowData = $("#sectionList").jqGrid('getRowData',id);
    layer.open({
        type: 1,
        area: ['480px','520px'],
        fix: false, //不固定
        maxmin: false,
        shade:0.6,
        title: "添加报告单格式",
        content: $("#addFormatDialog"),
        btn:["保存","取消"],
        yes: function(index, layero){
            $.post('../pspathology/dcm/editFormat', {
                formname : $('#formname').val(), formweburl : $('#formweburl').val(),
                formpicturenum : $('#formpicturenum').val(), formremark : $('#formremark').val(),
                formuseflag : $('#formuseflag').val(), formisdefault : $('#formisdefault').val(),
                formpathologyid : rowData.pathologyid, formsort : "A"+$("#FN1").val()+$("#SN1").val()+$("#TN1").val()
            },function(data){
                layer.close(index);
                jQuery("#sectionCode").jqGrid("clearGridData");
                jQuery("#sectionCode").jqGrid('setGridParam',{
                    url: "../pspathology/dcm/queryReportFormat",
                    datatype : 'json',
                    postData : {"query":rowData.pathologyid},
                    page : 1
                }).trigger('reloadGrid');//重新载入
            });
            //layer.close(index); //如果设定了yes回调，需进行手工关闭
        }
    });
}

/**
 * 删除检验段
 * */
function Delete(id){
    //var id = $('#rightGrid').jqGrid('getGridParam', 'selrow');
    if (id == null || id == 0) {
        layer.msg('请先选择要删除的数据', {icon: 2, time: 1000});
        return false;
    }
    $("#segment").val($("#segment").val().replace($("#codeEdit").jqGrid("getRowData", id).code + ",", ""));
    $("#codeEdit").jqGrid('delRowData',id );
}
/************************************
 *  添加病种
 *  add by zcw 2015-05-16
 * **********************************/
function  AddSection(){
    clearData();
    layer.open({
        type: 1,
        area: ['800px','500px'],
        fix: false, //不固定
        maxmin: false,
        shade:0.6,
        title: "添加病理模板",
        content: $("#addDialog"),
        btn:["保存","取消"],
        yes: function(index, layero){
            $.post('../template/edit', {temcustomerid:$('#temcustomerid').val(),temcontent:$('#temcontent').val(),
                temownerid : $('#temownerid').val(), tempathologyid : $('#tempathologyid').val(),
                tempathologyname : $('#tempathologyname').val(), temcustomername : $('#temcustomername').val(),
                temtype : $('#temtype').val(), temclass : $('#temclass').val(),
                temownername : $('#temownername').val(), temkey : $('#temkey').val(),
                temfivestroke : $('#temfivestroke').val(), temspellcode : $('#temspellcode').val(),
                tempinyin : $('#tempinyin').val(), temsort : "A"+$("#FN").val()+$("#SN").val()+$("#TN").val()
            },function(data){
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
function deleteSection(){
    var id = $('#sectionList').jqGrid('getGridParam','selrow');
    var rowData = $("#sectionList").jqGrid('getRowData',id);
    if(id==null || id.length==0){
        layer.msg('请先选择要删除的数据', {icon: 2,time: 1000});
        return false;
    }
    layer.confirm('确定删除选择的数据？', {icon: 2, title:'警告'}, function(index){
        $.post('../template/remove',{templateid:rowData.templateid},function(data) {
            layer.close(index);
            $("#sectionList").trigger('reloadGrid');
        });
    });
}
/**
 * 查询科室
 */
function search(){
    var query = $('#query').val()||'';
    jQuery("#sectionList").jqGrid('setGridParam',{
        url: "../template/query",
        //发送数据
        postData : {"query":query,"sidx":"temownerid"},
        page : 1
    }).trigger('reloadGrid');//重新载入
    jQuery("#sectionCode").jqGrid("clearGridData");
}

/**
 * 编辑病种
 */
function editSection(){
    var rowId = $("#sectionList").jqGrid('getGridParam','selrow');
    var rowData = $("#sectionList").jqGrid('getRowData',rowId);
    if(!rowId || rowId =='' || rowId==null){
        layer.alert("请先选择要编辑的数据",{icon:1,title:"提示"});
        return false;
    }
    var bindData = $.ajax({
        type : "GET",
        url: "../template/data",
        data: {
            templateid : rowData.templateid
        },
        success:function( msg ) {
            //设置数据
            $('#templateid').val(rowData.templateid);
            $('#temsort').val(msg.temsort);
            $('#tempathologyname').val(rowData.tempathologyname);
            $('#tempathologyid').val(msg.tempathologyid);
            $('#temcontent').val(msg.temcontent);
            $('#temcustomername').val(rowData.temcustomername);
            $('#temcustomerid').val(msg.temcustomerid);
            $('#temtype').val(msg.temtype);
            $('#temownername').val(msg.temownername);
            $("#temownerid").val(msg.temownerid);
            $("#temclass").val(msg.temclass);
            $("#temkey").val(msg.temkey);
            $("#tempinyin").val(msg.tempinyin);
            $("#temfivestroke").val(msg.temfivestroke);
            $("#temspellcode").val(msg.temspellcode);
            var sortNo = msg.temsort;
            $("#FN").val(sortNo.charAt(1));
            $("#SN").val(sortNo.charAt(2));
            $("#TN").val(sortNo.charAt(3));
            layer.open({
                type: 1,
                area: ['800px','500px'],
                fix: false, //不固定
                maxmin: false,
                shade:0.6,
                title: "编辑病理模板",
                content: $("#addDialog"),
                btn:["保存","取消"],
                yes: function(index, layero){
                    $.post('../template/edit', {templateid:rowData.templateid,
                        tempathologyname : $('#tempathologyname').val(), tempathologyid : $('#tempathologyid').val(),
                        temcontent : $('#temcontent').val(), temcustomername : $('#temcustomername').val(),
                        temcustomerid : $('#temcustomerid').val(), temtype : $('#temtype').val(),
                        temownername : $('#temownername').val(), temownerid : $('#temownerid').val(),
                        temclass : $('#temclass').val(), temkey : $('#temkey').val(),
                        tempinyin : $('#tempinyin').val(), temfivestroke : $('#temfivestroke').val(),
                        temspellcode : $('#temspellcode').val(), temsort : "A"+$("#FN").val()+$("#SN").val()+$("#TN").val()
                    },function(data){
                        layer.close(index);
                        $("#sectionList").trigger('reloadGrid');
                    })
                }
            });
        }
    });
}

$(function(){
    //表单校验
    $("#addSectionForm").Validform({
        tiptype:4,
        callback:function(){
        }
    });

    $("#formateform").Validform({
        tiptype:4,
        callback:function(){

        }
    });
    //keyPress 回车检索
    $("#query").keypress(function(e){
        if (e.keyCode == 13){
            search();
        }
    });
    //$(window).on('resize.jqGrid', function () {
    //	$("#sectionList").jqGrid('setGridWidth', $("#mainTable").width());
    //	$("#sectionList").jqGrid( 'setGridHeight', $("#mainTable").height() );
    //})

    $(window).on('resize.jqGrid', function () {
        $('#sectionList').jqGrid('setGridWidth', $(".leftContent").width(),false);
        $('#sectionCode').jqGrid('setGridWidth', $(".rightContent").width(),false);
    });
    var clientHeight= $(window).innerHeight();
    var height =clientHeight-$('#head').height()- $('#toolbar').height()-$('.footer-content').height()-150;

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

    $("#sectionList1").jqGrid({
        caption: "病种类别",
        url: "../pspathology/dcm/query",
        mtype: "GET",
        datatype: "json",
        width:$('.leftContent').width(),
        colNames: ['pathologyid','排序号', '病种名称', '病种名称（英文）', '病种分类','使用状态','是否取材','是否特检'],
        colModel: [
            { name: 'pathologyid', index: 'pathologyid', width: 30, hidden: true },
            { name: 'patsort', index: 'patsort', width: 30},
            { name: 'patnamech', index: 'patnamech', width: 60},
            { name: 'patnameen', index: 'patnameen', width: 50 },
            { name: 'patclass', index: 'patclass', width: 50,formatter: "select", editoptions:{value:"1:常规细胞学;2:液基细胞学;3:免疫组化;4:病理会诊;5:常规病理;6:术中冰冻;7:HPV;8:外周血细胞;9:骨髓细胞学"}},
            { name: 'patuseflag', index: 'patuseflag', width: 30,formatter: "select", editoptions:{value:"0:使用;1:停用"}},
            { name: 'patissampling', index: 'patissampling', width: 30,formatter: "select", editoptions:{value:"0:是;1:否"}},
            { name: 'patisspecialcheck', index: 'patisspecialcheck', width: 30,formatter: "select", editoptions:{value:"0:是;1:否"}}
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
        pager: "#pager1",
        onSelectRow: function(id){

        }
    });

    $("#sectionList").jqGrid({
        caption: "病理模板",
        url: "../template/query",
        mtype: "GET",
        datatype: "json",
        width:$('.leftContent').width(),
        colNames: ['templateid','temcustomerid','temownerid','tempathologyid','排序号', '病种类别', '客户', '模板类型','模板分类','归所用户','关键字','拼音码','五笔码','简码'],
        colModel: [
            { name: 'templateid', index: 'templateid', width: 30, hidden: true },
            { name: 'temcustomerid', index: 'temcustomerid', width: 30, hidden: true },
            { name: 'temownerid', index: 'temownerid', width: 30, hidden: true },
            { name: 'tempathologyid', index: 'tempathologyid', width: 50, hidden: true },
            { name: 'temsort', index: 'temsort', width: 30},
            { name: 'tempathologyname', index: 'tempathologyname', width: 30},
            { name: 'temcustomername', index: 'temcustomername', width: 30},
            { name: 'temtype', index: 'temtype', width: 30,formatter: "select", editoptions:{value:"0:系统公用;1:用户私有"}},
            { name: 'temclass', index: 'temclass', width: 30,formatter: "select", editoptions:{value:"0:大体所见模板;1:病理所见模板;2:病理诊断模板"}},
            { name: 'temownername', index: 'temownername', width: 60},
            { name: 'temkey', index: 'temkey', width: 50 },
            { name: 'tempinyin', index: 'tempinyin', width: 30},
            { name: 'temfivestroke', index: 'temfivestroke', width: 30},
            { name: 'temspellcode', index: 'temspellcode', width: 30}
        ],
        loadComplete : function() {
            var table = this;
            setTimeout(function(){
                updatePagerIcons(table);
            }, 0);
        },
        ondblClickRow: function (id) {
            editSection();
        },
        viewrecords: true,
        shrinkToFit: true,
        altRows:true,
        height: height,
        rowNum: 10,
        rowList:[10,20,30],
        rownumbers: true, // 显示行号
        rownumWidth: 35, // the width of the row numbers columns
        pager: "#pager",
        onSelectRow: function(id){
            jQuery("#sectionCode").jqGrid("clearGridData");
            var rowData = $("#sectionList").jqGrid('getRowData',id);
            jQuery("#sectionCode").jqGrid('setGridParam',{
                url: "../pspathology/dcm/queryReportFormat",
                datatype : 'json',
                postData : {"query":rowData.pathologyid},
                page : 1
            }).trigger('reloadGrid');//重新载入
        }
    });
    //$(window).triggerHandler('resize.jqGrid');

    //搜索检验段
    $("#searchCode").autocomplete({
        source: function( request, response ) {
            $.ajax({
                url: "../ajax/searchCode",
                dataType: "json",
                data: {
                    name : request.term
                },
                success: function( data ) {
                    response( $.map( data, function( result ) {
                        return {
                            label: result.code + " : " + result.describe,
                            value: result.code,
                            describe: result.describe,
                            id : result.id
                        }
                    }));
                    $("#searchCode").removeClass("ui-autocomplete-loading");
                }
            });
        },
        minLength: 1,
        select : function(event, ui) {
            //$("#addIndexId").val(ui.item.id);
            var obj = $("#codeEdit").jqGrid("getRowData");
            var datas = [];
            var flag = true;
            jQuery(obj).each(function(){
                if(ui.item.value == this.code){
                    flag =  false;
                    layer.msg("数据已存在");
                }

            });
            if(!flag) return ;
            var ids = $('#codeEdit').jqGrid('getDataIDs');
            //console.log(ids)
            var newId = parseInt(ids[ids.length - 1] || 0) + 1;
            var rowData = {
                id: ui.item.id,
                code: ui.item.value,
                describe: ui.item.describe
            };
            $("#segment").val($("#segment").val() + ui.item.value + ",");
            $("#codeEdit").jqGrid('addRowData', newId, rowData);
            $('#codeEdit').saveRow(newId, false, 'clientArray');

        },
        close: function( event, ui ) {
            $("#searchCode").val('');
        }
    });

});
function  clearData(){
        $('#tempathologyname').val('');
        $('#tempathologyid').val('0');
        $('#temcustomername').val('');
        $('#temcustomerid').val('0');
        $('#temtype').val('0');
        $('#temownername').val('System');
        $('#temownerid').val(SYSTEM_DEFAULT_ID);
        $('#temclass').val('0');
        $('#temkey').val('');
        $('#tempinyin').val('');
        $('#temfivestroke').val('');
        $('#temspellcode').val('');
        $('#temsort').val('');
        $('#temcontent').val('');
        $("#FN").val('0');$("#SN").val('0');$("#TN").val('0');

}
function updatePagerIcons(table) {
    var replacement =
    {
        'ui-icon-seek-first' : 'ace-icon fa fa-angle-double-left bigger-140',
        'ui-icon-seek-prev' : 'ace-icon fa fa-angle-left bigger-140',
        'ui-icon-seek-next' : 'ace-icon fa fa-angle-right bigger-140',
        'ui-icon-seek-end' : 'ace-icon fa fa-angle-double-right bigger-140'
    };
    $('.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon').each(function(){
        var icon = $(this);
        var $class = $.trim(icon.attr('class').replace('ui-icon', ''));
        if($class in replacement) icon.attr('class', 'ui-icon '+replacement[$class]);
    })
}