/**
 * Created by lenovo on 2016/10/6.
 */
function showMatTYpe() {
    layer.open({
        type: 1,
        area: ['800px','500px'],
        fix: false, //不固定
        maxmin: true,
        shade:0.5,
        title: "材料类型列表",
        content: $('#hospitalGrid'),
        btn:["保存","取消"],
        yes: function(index, layero){
            var id = $('#sectionList2').jqGrid('getGridParam','selrow');
            if(id==null || id.length==0){
                layer.msg('请先选择材料类型', {icon: 2,time: 1000});
                return false;
            }
            var rowData = $("#sectionList2").jqGrid('getRowData',id);
            $("#mattype").val(rowData.id);
            $("#mattypename").val(rowData.value);
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
        title: "添加申请材料",
        content: $("#addDialog"),
        btn:["保存","取消"],
        yes: function(index, layero){
            var matname = $('#matname').val();
            var mattype = $('#mattype').val();
            if(matname==''){
                layer.msg('请填写材料名称', {icon: 2,time: 1000});
                return false;
            }
            if(mattype==''){
                layer.msg('请填写材料类型', {icon: 2,time: 1000});
                return false;
            }
            $.post('../reqmaterial/edit', {matname:$('#matname').val(),mattype:$('#mattype').val(),
                matspecial : $('#matspecial').val(), matuseflag : $('#matuseflag').val(),
                tempathologyname : $('#tempathologyname').val(), temcustomername : $('#temcustomername').val(),
                matpinyincode : $('#matpinyincode').val(), matfivestrokecode : $('#matfivestrokecode').val(),
                matsort : "A"+$("#FN").val()+$("#SN").val()+$("#TN").val(),matreamrk:$('#matreamrk').val()
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
        $.post('../reqmaterial/remove',{materialid:rowData.materialid},function(data) {
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
        url: "../reqmaterial/query",
        //发送数据
        postData : {"query":query,"sidx":"materialid"},
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
        url: "../reqmaterial/data",
        data: {
            materialid : rowData.materialid
        },
        success:function( msg ) {
            //设置数据
            $('#materialid').val(rowData.materialid);
            $('#matname').val(msg.matname);
            $('#mattypename').val(rowData.mattypename);
            $('#mattype').val(msg.mattype);
            $('#matspecial').val(msg.matspecial);
            $('#matuseflag').val(msg.matuseflag);
            $('#matpinyincode').val(msg.matpinyincode);
            $('#matfivestrokecode').val(msg.matfivestrokecode);
            $('#matsort').val(msg.matsort);
            $("#matreamrk").val(msg.matreamrk);
            var sortNo = msg.matsort;
            $("#FN").val(sortNo.charAt(1));
            $("#SN").val(sortNo.charAt(2));
            $("#TN").val(sortNo.charAt(3));
            layer.open({
                type: 1,
                area: ['800px','500px'],
                fix: false, //不固定
                maxmin: false,
                shade:0.6,
                title: "编辑申请材料",
                content: $("#addDialog"),
                btn:["保存","取消"],
                yes: function(index, layero){
                    var matname = $('#matname').val();
                    var mattype = $('#mattype').val();
                    if(matname==''){
                        layer.msg('请填写材料名称', {icon: 2,time: 1000});
                        return false;
                    }
                    if(mattype==''){
                        layer.msg('请填写材料类型', {icon: 2,time: 1000});
                        return false;
                    }
                    $.post('../reqmaterial/edit', {materialid:rowData.materialid,
                        matname : $('#matname').val(), mattypename : $('#mattypename').val(),
                        mattype : $('#mattype').val(), matspecial : $('#matspecial').val(),
                        matuseflag : $('#matuseflag').val(), matpinyincode : $('#matpinyincode').val(),
                        matfivestrokecode : $('#matfivestrokecode').val(), matreamrk : $('#matreamrk').val(),
                        matsort : "A"+$("#FN").val()+$("#SN").val()+$("#TN").val()
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
    });
    var clientHeight= $(window).innerHeight();
    var height =clientHeight-$('#head').height()- $('#toolbar').height()-$('.footer-content').height()-150;

    $("#sectionList2").jqGrid({
        caption: "材料类型列表",
        url: "../set/dictionary/getList",
        mtype: "GET",
        datatype: "json",
        postData:{"type":1},
        width:$('.leftContent').width(),
        colNames: ['id','名称','sign','type'],
        colModel: [
            { name: 'id', index: 'id', width: 30},
            { name: 'value', index: 'value', width: 60},
            { name: 'sign', index: 'sign', width: 30},
            { name: 'type', index: 'type', width: 50 }
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


    $("#sectionList").jqGrid({
        caption: "申请材料",
        url: "../reqmaterial/query",
        mtype: "GET",
        datatype: "json",
        width:$('.leftContent').width(),
        colNames: ['排序号','materialid','材料名称','mattype','材料类型','特殊类型','使用状态', '拼音码','五笔码'],
        colModel: [
            { name: 'matsort', index: 'matsort', width: 30},
            { name: 'materialid', index: 'materialid', width: 30, hidden: true },
            { name: 'matname', index: 'matname', width: 30},
            { name: 'mattype', index: 'mattype', width: 30, hidden: true },
            { name: 'mattypename', index: 'mattypename', width: 50},
            { name: 'matspecial', index: 'matspecial', width: 30},
            { name: 'matuseflag', index: 'matuseflag', width: 30,formatter: "select", editoptions:{value:"1:启用;0:停用"}},
            { name: 'matpinyincode', index: 'matpinyincode', width: 30},
            { name: 'matfivestrokecode', index: 'matfivestrokecode', width: 30}
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
    $('#matname').val('');
    $('#mattype').val('');
    $('#mattypename').val('');
    $('#matspecial').val('');
    $('#matuseflag').val('1');
    $('#matsort').val('');
    $('#matpinyincode').val('');
    $('#matfivestrokecode').val('');
    $('#matreamrk').val('');
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