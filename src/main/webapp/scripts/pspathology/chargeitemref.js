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
                layer.msg('请先选择客户', {icon: 2,time: 1000});
                return false;
            }
            var rowData = $("#sectionList2").jqGrid('getRowData',id);
            $("#customercode").val(rowData.id);
            $("#customerName").val(rowData.name);
            layer.close(index);
        }
    })
}

function showItem() {
    layer.open({
        type: 1,
        area: ['800px','500px'],
        fix: false, //不固定
        maxmin: true,
        shade:0.5,
        title: "收费项目列表",
        content: $('#chargeItemGrid'),
        btn:["保存","取消"],
        yes: function(index, layero){
            var id = $('#sectionList3').jqGrid('getGridParam','selrow');
            if(id==null || id.length==0){
                layer.msg('请先选择收费项目', {icon: 2,time: 1000});
                return false;
            }
            var rowData = $("#sectionList3").jqGrid('getRowData',id);
            $("#chargeItemName").val(rowData.chinesename);
            $("#chargeitemid").val(rowData.chargeitemid);
            layer.close(index);
        }
    })
}

function showHisCharge() {
    layer.open({
        type: 1,
        area: ['800px','500px'],
        fix: false, //不固定
        maxmin: true,
        shade:0.5,
        title: "HIS收费项目列表",
        content: $('#hisChargeItemGrid'),
        btn:["保存","取消"],
        yes: function(index, layero){
            var id = $('#sectionList4').jqGrid('getGridParam','selrow');
            if(id==null || id.length==0){
                layer.msg('请先选择收费项目', {icon: 2,time: 1000});
                return false;
            }
            var rowData = $("#sectionList4").jqGrid('getRowData',id);
            $("#refhischargeid").val(rowData.sfxmid);
            $("#refhischargename").val(rowData.sfxmmc);
            $("#refhisprice").val(rowData.sfxmdj);
            layer.close(index);
        }
    })
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
        title: "添加收费项目对照信息",
        content: $("#addDialog"),
        btn:["保存","取消"],
        yes: function(index, layero){
            if($('#chargeitemid').val()=='' || $('#customercode').val()=='' || $('#refhischargeid').val() =='') {
                layer.msg('请先完善信息', {icon: 2,time: 1000});
                return false;
            }
            $.post('../chargeitemref/edit', {chargeitemid:$('#chargeitemid').val(),customercode:$('#customercode').val(),
                refhischargeid : $('#refhischargeid').val(),
                refhischargename : $('#refhischargename').val(), refhisprice : $('#refhisprice').val(),
                refsendhis : $('#refsendhis').val(),refremark : $('#refremark').val()
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
        $.post('../chargeitemref/remove',{referenceid:rowData.referenceid},function(data) {
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
        url: "../chargeitemref/query",
        //发送数据
        postData : {"query":query,"sidx":"referenceid"},
        page : 1
    }).trigger('reloadGrid');//重新载入
}

function searchHisChargeItem(){
    var query = $('#hissfxmmc').val()||'';
    jQuery("#sectionList4").jqGrid('setGridParam',{
        url: "../chargeitemref/query_his_charge",
        //发送数据
        postData : {"query":query,"sidx":"referenceid"},
        page : 1
    }).trigger('reloadGrid');//重新载入
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
        url: "../chargeitemref/data",
        data: {
            referenceid : rowData.referenceid
        },
        success:function( msg ) {
            //设置数据
            $('#referenceid').val(rowData.referenceid);
            $('#chargeitemid').val(msg.chargeitemid);
            $('#customercode').val(msg.customercode);
            $('#refhischargeid').val(msg.refhischargeid);
            $('#refhischargename').val(msg.refhischargename);
            $('#refhisprice').val(msg.refhisprice);
            $('#refsendhis').val(msg.refsendhis);
            $('#refremark').val(msg.refremark);
            $('#chargeItemName').val(rowData.chargeItemName);
            $('#customerName').val(rowData.customerName);
            layer.open({
                type: 1,
                area: ['800px','500px'],
                fix: false, //不固定
                maxmin: false,
                shade:0.6,
                title: "编辑收费项目对照信息",
                content: $("#addDialog"),
                btn:["保存","取消"],
                yes: function(index, layero){
                    if($('#chargeitemid').val()=='' || $('#customercode').val()=='' || $('#refhischargeid').val() =='') {
                        layer.msg('请先完善信息', {icon: 2,time: 1000});
                        return false;
                    }
                    $.post('../chargeitemref/edit', {referenceid:$('#referenceid').val(),chargeitemid:$('#chargeitemid').val(),customercode:$('#customercode').val(),
                        refhischargeid : $('#refhischargeid').val(),
                        refhischargename : $('#refhischargename').val(), refhisprice : $('#refhisprice').val(),
                        refsendhis : $('#refsendhis').val(),refremark : $('#refremark').val()
                    },function(data){
                        layer.close(index);
                        $("#sectionList").trigger('reloadGrid');
                    });
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


    $("#sectionList").jqGrid({
        caption: "收费项目对照信息列表",
        url: "../chargeitemref/query",
        mtype: "GET",
        datatype: "json",
        width:$('.leftContent').width(),
        colNames: ['referenceid','chargeitemid','customercode','refhischargeid','收费项目名称','客户','HIS收费名称','HIS单价', '是否发送至HIS'],
        colModel: [
            { name: 'referenceid', index: 'referenceid', width: 30,hidden:true},
            { name: 'chargeitemid', index: 'chargeitemid', width: 30, hidden: true },
            { name: 'customercode', index: 'customercode', width: 30, hidden: true },
            { name: 'refhischargeid', index: 'refhischargeid', width: 30, hidden: true },
            { name: 'chargeItemName', index: 'chargeItemName', width: 30},
            { name: 'customerName', index: 'customerName', width: 30},
            { name: 'refhischargename', index: 'refhischargename', width: 30},
            { name: 'refhisprice', index: 'refhisprice', width: 30},
            { name: 'refsendhis', index: 'refsendhis', width: 30,formatter: "select", editoptions:{value:"1:已发送;0:未发送"}}
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
        rowList:[10,20,30,40,50],
        rownumbers: true, // 显示行号
        rownumWidth: 35, // the width of the row numbers columns
        pager: "#pager",
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
        rowList:[10,20,30,40,50],
        rownumbers: true, // 显示行号
        rownumWidth: 35, // the width of the row numbers columns
        pager: "#pager2",
        onSelectRow: function(id){

        }
    });

    $("#sectionList3").jqGrid({
        caption: "收费项目列表",
        url: "../chargeitem/query",
        mtype: "GET",
        datatype: "json",
        width:$('.leftContent').width(),
        colNames: ['排序号','chargeitemid','中文名称','英文名称','单价','使用状态', '所属统计类'],
        colModel: [
            { name: 'chiitemsort', index: 'chiitemsort', width: 30},
            { name: 'chargeitemid', index: 'chargeitemid', width: 30, hidden: true },
            { name: 'chinesename', index: 'chinesename', width: 30},
            { name: 'chienglishname', index: 'chienglishname', width: 30},
            { name: 'chiprice', index: 'chiprice', width: 30},
            { name: 'chiuseflag', index: 'chiuseflag', width: 30,formatter: "select", editoptions:{value:"1:启用;0:停用"}},
            { name: 'chicategory', index: 'chicategory', width: 30}
        ],
        loadComplete : function() {
            var table = this;
            setTimeout(function(){
                updatePagerIcons(table);
            }, 0);
        },
        ondblClickRow: function (id) {
            //editSection();
        },
        viewrecords: true,
        shrinkToFit: true,
        altRows:true,
        height: height,
        rowNum: 10,
        rowList:[10,20,30,40,50],
        rownumbers: true, // 显示行号
        rownumWidth: 35, // the width of the row numbers columns
        pager: "#pager3",
        onSelectRow: function(id){

        }
    });

    $("#sectionList4").jqGrid({
        caption: "HIS收费项目列表",
        url: "../chargeitemref/query_his_charge",
        mtype: "GET",
        datatype: "json",
        width:770,
        colNames: ['HIS收费ID','HIS收费名称','HIS收费价格'],
        colModel: [
            { name: 'sfxmid', index: 'sfxmid', width: 30},
            { name: 'sfxmmc', index: 'sfxmmc', width: 150},
            { name: 'sfxmdj', index: 'sfxmdj', width: 100}
        ],
        loadComplete : function() {
            var table = this;
            setTimeout(function(){
                updatePagerIcons(table);
            }, 0);
        },
        ondblClickRow: function (id) {
            //editSection();
        },
        loadError:function(){
            layer.msg('连接HIS数据库服务器异常，请先检查网络连接', {icon: 2,time: 5000});
            return false;
        },
        viewrecords: true,
        shrinkToFit: true,
        altRows:true,
        height: height,
        rowNum: 100,
        rowList:[100,200,300],
        rownumbers: true, // 显示行号
        rownumWidth: 35, // the width of the row numbers columns
        pager: "#pager4",
        onSelectRow: function(id){

        }
    });

});
function  clearData(){
    $('#chargeitemid').val('');
    $('#customercode').val('');
    $('#refhischargeid').val('');
    $('#refhischargename').val('');
    $('#refhisprice').val(0.0);
    $('#refsendhis').val('0');
    $('#refremark').val('');
    $('#chargeItemName').val('');
    $('#customerName').val('');

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