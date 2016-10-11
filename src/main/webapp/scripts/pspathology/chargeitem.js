/**
 * Created by lenovo on 2016/10/6.
 */

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
        title: "添加收费项目",
        content: $("#addDialog"),
        btn:["保存","取消"],
        yes: function(index, layero){
                $.post('../chargeitem/edit', {chinesename:$('#chinesename').val(),chienglishname:$('#chienglishname').val(),
                    chicategory : $('#chicategory').val(),
                    chiprice : $('#chiprice').val(), chiuseflag : $('#chiuseflag').val(),
                    chiremark : $('#chiremark').val(),
                    chiitemsort : "A"+$("#FN").val()+$("#SN").val()+$("#TN").val()
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
        $.post('../chargeitem/remove',{chargeitemid:rowData.chargeitemid},function(data) {
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
        url: "../chargeitem/query",
        //发送数据
        postData : {"query":query,"sidx":"chargeitemid"},
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
        url: "../chargeitem/data",
        data: {
            chargeitemid : rowData.chargeitemid
        },
        success:function( msg ) {
            //设置数据
            $('#chargeitemid').val(rowData.chargeitemid);
            $('#chinesename').val(msg.chinesename);
            $('#chienglishname').val(msg.chienglishname);
            $('#chiitemsort').val(msg.chiitemsort);
            $('#chicategory').val(msg.chicategory);
            $('#chiprice').val(msg.chiprice);
            $('#chiuseflag').val(msg.chiuseflag);
            $('#chiremark').val(msg.chiremark);
            var chiitemsort = msg.chiitemsort;
            $("#FN").val(chiitemsort.charAt(1));
            $("#SN").val(chiitemsort.charAt(2));
            $("#TN").val(chiitemsort.charAt(3));
            layer.open({
                type: 1,
                area: ['800px','500px'],
                fix: false, //不固定
                maxmin: false,
                shade:0.6,
                title: "编辑收费项目",
                content: $("#addDialog"),
                btn:["保存","取消"],
                yes: function(index, layero){
                        $.post('../chargeitem/edit', {chargeitemid:$('#chargeitemid').val(),chinesename:$('#chinesename').val(),chienglishname:$('#chienglishname').val(),
                            chicategory : $('#chicategory').val(),
                            chiprice : $('#chiprice').val(), chiuseflag : $('#chiuseflag').val(),
                            chiremark : $('#chiremark').val(),
                            chiitemsort : "A"+$("#FN").val()+$("#SN").val()+$("#TN").val()
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

        }
    });

});
function  clearData(){
    $('#chinesename').val('');
    $('#chienglishname').val('');
    $('#chiitemsort').val('');
    $('#chicategory').val('');
    $('#chiprice').val(0.0);
    $('#chiuseflag').val('1');
    $('#chiremark').val('');
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