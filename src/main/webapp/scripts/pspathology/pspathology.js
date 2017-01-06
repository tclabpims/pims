
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
    clearData(1);
    layer.open({
        type: 1,
        area: ['800px','500px'],
        fix: false, //不固定
        maxmin: false,
        shade:0.6,
        title: "添加病种",
        content: $("#addDialog"),
        btn:["保存","取消"],
        yes: function(index, layero){
        var patnamech = $('#patnamech').val();
        var patcoddingprechar = $('#patcoddingprechar').val();
        var patreporttitle = $('#patreporttitle').val();
        if(patnamech==''){
            layer.msg('请填写病种名称', {icon: 2,time: 1000});
            return false;
        }
        if(patcoddingprechar==''){
            layer.msg('请填写病理编号前缀', {icon: 2,time: 1000});
            return false;
        }
        if(patreporttitle==''){
            layer.msg('请填写报告单抬头名称', {icon: 2,time: 1000});
            return false;
        }
            $.post('../pspathology/dcm/edit', {patnamech:$('#patnamech').val(),
                patnameen : $('#patnameen').val(), patreporttitle : $('#patreporttitle').val(),
                patreportremark : $('#patreportremark').val(), patdefaultdiagnosis : $('#patdefaultdiagnosis').val(),
                patcoddingprechar : $('#patcoddingprechar').val(), patcoddinglength : $('#patcoddinglength').val(),
                patuseflag : $('#patuseflag').val(), patissampling : $('#patissampling').val(),
                patisspecialcheck : $('#patisspecialcheck').val(), patclass : $('#patclass').val(),
                patstartcodding : $('#patstartcodding').val(), patsort : "A"+$("#FN").val()+$("#SN").val()+$("#TN").val()
            },function(data){
                layer.close(index);
                $("#sectionList").trigger('reloadGrid');
                jQuery("#sectionCode").jqGrid("clearGridData");
            });
            //layer.close(index); //如果设定了yes回调，需进行手工关闭
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
    layer.confirm('确定删除选择数据？', {icon: 2, title:'警告'}, function(index){
        $.post('../pspathology/dcm/remove',{pathologyid:rowData.pathologyid},function(data) {
            layer.close(index);
            $("#sectionList").trigger('reloadGrid');
            jQuery("#sectionCode").jqGrid("clearGridData");
        });
    });
}
/**
 * 查询科室
 */
function search(){
    var query = $('#query').val()||'';
    jQuery("#sectionList").jqGrid('setGridParam',{
        url: "../pspathology/dcm/query",
        //发送数据
        postData : {"query":query,"sidx":"patsort"},
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
        url: "../pspathology/dcm/data",
        data: {
             pathologyid : rowData.pathologyid
        },
        success:function( msg ) {
            //设置数据
            $('#pathologyid').val(rowData.pathologyid);
            $('#patnamech').val(msg.patnamech);
            $('#patnameen').val(msg.patnameen);
            $('#patreporttitle').val(msg.patreporttitle);
            $('#patreportremark').val(msg.patreportremark);
            $('#patdefaultdiagnosis').val(msg.patdefaultdiagnosis);
            $('#patcoddingprechar').val(msg.patcoddingprechar);
            $('#patcoddinglength').val(msg.patcoddinglength);
            $('#patstartcodding').val(msg.patstartcodding);
            $("#patuseflag").val(msg.patuseflag);
            $("#patissampling").val(msg.patissampling);
            $("#patisspecialcheck").val(msg.patisspecialcheck);
            $("#patclass").val(msg.patclass);
            var sortNo = msg.patsort;
            $("#FN").val(sortNo.charAt(1));
            $("#SN").val(sortNo.charAt(2));
            $("#TN").val(sortNo.charAt(3));
            layer.open({
                type: 1,
                area: ['800px','500px'],
                fix: false, //不固定
                maxmin: false,
                shade:0.6,
                title: "编辑病种",
                content: $("#addDialog"),
                btn:["保存","取消"],
                yes: function(index, layero){
                var patnamech = $('#patnamech').val();
                var patcoddingprechar = $('#patcoddingprechar').val();
                var patreporttitle = $('#patreporttitle').val();
                if(patnamech==''){
                    layer.msg('请填写病种名称', {icon: 2,time: 1000});
                    return false;
                }
                if(patcoddingprechar==''){
                    layer.msg('请填写病理编号前缀', {icon: 2,time: 1000});
                    return false;
                }
                if(patreporttitle==''){
                    layer.msg('请填写报告单抬头名称', {icon: 2,time: 1000});
                    return false;
                }
                    $.post('../pspathology/dcm/edit', {pathologyid:rowData.pathologyid,patnamech:$('#patnamech').val(),
                        patnameen : $('#patnameen').val(), patreporttitle : $('#patreporttitle').val(),
                        patreportremark : $('#patreportremark').val(), patdefaultdiagnosis : $('#patdefaultdiagnosis').val(),
                        patcoddingprechar : $('#patcoddingprechar').val(), patcoddinglength : $('#patcoddinglength').val(),
                        patuseflag : $('#patuseflag').val(), patissampling : $('#patissampling').val(),
                        patisspecialcheck : $('#patisspecialcheck').val(), patclass : $('#patclass').val(),
                        patstartcodding : $('#patstartcodding').val(), patsort : "A"+$("#FN").val()+$("#SN").val()+$("#TN").val()
                    },function(data){
                        layer.close(index);
                        $("#sectionList").trigger('reloadGrid');
                        jQuery("#sectionCode").jqGrid("clearGridData");
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

    $("#sectionList").jqGrid({
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

    jQuery("#sectionCode").jqGrid( {
        //datatype : "local",
        height :height,
        width:$('#maincontent .rightContent').width(),
        colNames : [ 'formateid', '格式名称', '是否启用', '是否默认', '图片数量','报告格式URL'],
        colModel : [
            {name : 'formateid',index : 'formateid',width : 30,hidden : true},
            {name : 'formname',index : 'formname',width : 120},
            {name : 'formuseflag',index : 'formuseflag',width : 60,formatter: "select", editoptions:{value:"0:是;1:否"}},
            {name : 'formisdefault',index : 'formisdefault',width : 60,formatter: "select", editoptions:{value:"0:是;1:否"}},
            {name : 'formpicturenum',index : 'formpicturenum',width : 60},
            {name : 'formweburl',index : 'formweburl',width : 240}
        ],
        loadComplete : function() {
            var table = this;
            setTimeout(function(){
                updatePagerIcons(table);
            }, 0);
        },
        shrinkToFit:false,
        scrollOffset:2,
        rowNum: 100,
        rowList:[100,200,300,400,500],
        rownumbers: true, // 显示行号
        rownumWidth: 35, // the width of the row numbers columns
        pager: "#rightPager",//分页控件的id
        caption : "报告单格式"
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
function  clearData(flag){
     if(flag ==1){
        $('#pathologyid').val('');
        $('#patnamech').val('');
        $('#patnameen').val('');
        $('#patreporttitle').val('');
        $('#patreportremark').val('');
        $('#patdefaultdiagnosis').val('');
        $('#patcoddingprechar').val('');
        $('#patcoddinglength').val('');
        $('#patuseflag').val('0');
        $('#patissampling').val('0');
        $('#patisspecialcheck').val('0');
        $('#patclass').val('1');
        $('#patstartcodding').val('');
        $("#FN").val('0');$("#SN").val('0');$("#TN").val('0');
     } else if(flag == 2) {
         $('#formateid').val('');
         $('#formname').val('');
         $('#formweburl').val('');
         $('#formpicturenum').val('');
         $('#formremark').val('');
         $('#formuseflag').val('0');
         $('#formisdefault').val('0');
         $('#formsort').val();
         $("#FN1").val('0');$("#SN1").val('0');$("#TN1").val('0');
     }
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