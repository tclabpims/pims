/**
 * Created by lenovo on 2016/10/6.
 */
function showPathology() {
    layer.open({
        type: 1,
        area: ['800px','500px'],
        fix: false, //不固定
        maxmin: true,
        shade:0.5,
        title: "病种类型列表",
        content: $('#hospitalGrid'),
        btn:["保存","取消"],
        yes: function(index, layero){
            var id = $('#sectionList2').jqGrid('getGridParam','selrow');
            if(id==null || id.length==0){
                layer.msg('请先选择材料类型', {icon: 2,time: 1000});
                return false;
            }
            var rowData = $("#sectionList2").jqGrid('getRowData',id);
            $("#tespathologyid").val(rowData.pathologyid);
            $("#tespathologyname").val(rowData.patnamech);
            layer.close(index);
        }
    })
}

function controlItemHandle(){
    if($("#tesitemtype").val() == 3) {
        $("#tesitemhandle").attr("disabled", false);
    } else {
        $("#tesitemhandle").attr("disabled", true);
    }
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
        title: "添加申请检查项目",
        content: $("#addDialog"),
        btn:["保存","取消"],
        yes: function(index, layero){
        var tespathologyid = $('#tespathologyid').val();
        var teschinesename = $('#teschinesename').val();
        if(tespathologyid==''){
            layer.msg('请填写病种类别', {icon: 2,time: 1000});
            return false;
        }
        if(teschinesename==''){
            layer.msg('请填写中文名称', {icon: 2,time: 1000});
            return false;
        }
            if($("#tesitemhandle").attr("disabled")=="disabled"){
                $.post('../estitem/edit', {teschinesename:$('#teschinesename').val(),tesenglishname:$('#tesenglishname').val(),
                    tesischarge : $('#tesischarge').val(),tesitemproperty:$('#tesitemproperty').val(),
                    tesitemtype : $('#tesitemtype').val(), tespathologyid : $('#tespathologyid').val(),
                    tesuseflag : $('#tesuseflag').val(), tesreamrk : $('#tesreamrk').val(),
                    tespinyincode : $('#tespinyincode').val(), tesfivestroke : $('#tesfivestroke').val(),
                    tesitemsort : "A"+$("#FN").val()+$("#SN").val()+$("#TN").val(),tesisorder:$("#tesisorder").val()
                },function(data){
                    layer.close(index);
                    $("#sectionList").trigger('reloadGrid');
                });
            } else {
                $.post('../estitem/edit', {teschinesename:$('#teschinesename').val(),tesenglishname:$('#tesenglishname').val(),
                    tesitemhandle : $('#tesitemhandle').val(), tesischarge : $('#tesischarge').val(),
                    tesitemtype : $('#tesitemtype').val(), tespathologyid : $('#tespathologyid').val(),
                    tesuseflag : $('#tesuseflag').val(), tesreamrk : $('#tesreamrk').val(),tesitemproperty:$('#tesitemproperty').val(),
                    tespinyincode : $('#tespinyincode').val(), tesfivestroke : $('#tesfivestroke').val(),
                    tesitemsort : "A"+$("#FN").val()+$("#SN").val()+$("#TN").val(),tesisorder:$("#tesisorder").val()
                },function(data){
                    layer.close(index);
                    $("#sectionList").trigger('reloadGrid');
                });
            }
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
        $.post('../estitem/remove',{testitemid:rowData.testitemid},function(data) {
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
        url: "../estitem/query",
        //发送数据
        postData : {"query":query,"sidx":"testitemid"},
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
        url: "../estitem/data",
        data: {
            testitemid : rowData.testitemid
        },
        success:function( msg ) {
            //设置数据
            $('#testitemid').val(rowData.testitemid);
            $('#teschinesename').val(msg.teschinesename);
            $('#tespathologyname').val(rowData.tespathologyname);
            $('#tesenglishname').val(msg.tesenglishname);
            $('#tespinyincode').val(msg.tespinyincode);
            $('#tesfivestroke').val(msg.tesfivestroke);
            $('#tesitemtype').val(msg.tesitemtype);
            $('#tespathologyid').val(msg.tespathologyid);
            $('#tesitemhandle').val(msg.tesitemhandle);
            $('#tesitemsort').val(msg.tesitemsort);
            $("#tesischarge").val(msg.tesischarge);
            $("#tesuseflag").val(msg.tesuseflag);
            $("#tesreamrk").val(msg.tesreamrk);
            $('#tesitemproperty').val(msg.tesitemproperty);
            $('#tesisorder').val(msg.tesisorder);
            var sortNo = msg.tesitemsort;
            $("#FN").val(sortNo.charAt(1));
            $("#SN").val(sortNo.charAt(2));
            $("#TN").val(sortNo.charAt(3));
            layer.open({
                type: 1,
                area: ['800px','500px'],
                fix: false, //不固定
                maxmin: false,
                shade:0.6,
                title: "编辑申请检查项目",
                content: $("#addDialog"),
                btn:["保存","取消"],
                yes: function(index, layero){
                var tespathologyid = $('#tespathologyid').val();
                var teschinesename = $('#teschinesename').val();
                if(tespathologyid==''){
                    layer.msg('请填写病种类别', {icon: 2,time: 1000});
                    return false;
                }
                if(teschinesename==''){
                    layer.msg('请填写中文名称', {icon: 2,time: 1000});
                    return false;
                }
                    if($("#tesitemhandle").attr("disabled")=="disabled"){
                        $.post('../estitem/edit', {testitemid:$('#testitemid').val(),teschinesename:$('#teschinesename').val(),tesenglishname:$('#tesenglishname').val(),
                            tesischarge : $('#tesischarge').val(),
                            tesitemtype : $('#tesitemtype').val(), tespathologyid : $('#tespathologyid').val(),
                            tesuseflag : $('#tesuseflag').val(), tesreamrk : $('#tesreamrk').val(),tesitemproperty:$('#tesitemproperty').val(),
                            tespinyincode : $('#tespinyincode').val(), tesfivestroke : $('#tesfivestroke').val(),
                            tesitemsort : "A"+$("#FN").val()+$("#SN").val()+$("#TN").val(),tesisorder:$("#tesisorder").val()
                        },function(data){
                            layer.close(index);
                            $("#sectionList").trigger('reloadGrid');
                        });
                    } else {
                        $.post('../estitem/edit', {testitemid:$('#testitemid').val(),teschinesename:$('#teschinesename').val(),tesenglishname:$('#tesenglishname').val(),
                            tesitemhandle : $('#tesitemhandle').val(), tesischarge : $('#tesischarge').val(),
                            tesitemtype : $('#tesitemtype').val(), tespathologyid : $('#tespathologyid').val(),
                            tesuseflag : $('#tesuseflag').val(), tesreamrk : $('#tesreamrk').val(),tesitemproperty:$('#tesitemproperty').val(),
                            tespinyincode : $('#tespinyincode').val(), tesfivestroke : $('#tesfivestroke').val(),
                            tesitemsort : "A"+$("#FN").val()+$("#SN").val()+$("#TN").val(),tesisorder:$("#tesisorder").val()
                        },function(data){
                            layer.close(index);
                            $("#sectionList").trigger('reloadGrid');
                        });
                    }
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

    $("#sectionList2").jqGrid({
        caption: "病种类别列表",
        url: "../pspathology/dcm/query",
        mtype: "GET",
        datatype: "json",
        postData:{"type":1},
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
        rowList:[10,20,30,40,50],
        rownumbers: true, // 显示行号
        rownumWidth: 35, // the width of the row numbers columns
        pager: "#pager2",
        onSelectRow: function(id){

        }
    });


    $("#sectionList").jqGrid({
        caption: "申请检查项目",
        url: "../estitem/query",
        mtype: "GET",
        datatype: "json",
        width:$('.leftContent').width(),
        colNames: ['排序号','testitemid','中文名称','英文名称','tespathologyid','病种类别','项目类型','项目属性','医嘱项目','内部遗嘱处理','是否需要计费','使用状态', '拼音码','五笔码'],
        colModel: [
            { name: 'tesitemsort', index: 'tesitemsort', width: 30},
            { name: 'testitemid', index: 'testitemid', width: 30, hidden: true },
            { name: 'teschinesename', index: 'teschinesename', width: 30},
            { name: 'tesenglishname', index: 'tesenglishname', width: 30},
            { name: 'tespathologyid', index: 'tespathologyid', width: 30, hidden: true },
            { name: 'tespathologyname', index: 'tespathologyname', width: 30},
            { name: 'tesitemtype', index: 'tesitemtype', width: 30,formatter: "select", editoptions:{value:"1:医嘱开单项目;2:内部医嘱检测项目;3:内部医嘱技术处理项目;4:标本登记/电子申请检查项目;"}},
            { name: 'tesitemproperty', index: 'tesitemproperty', width: 30,formatter: "select", editoptions:{value:"0:癌基因蛋白;1:单克隆抗体;"}},
            { name: 'tesisorder', index: 'tesisorder', width: 30,formatter: "select", editoptions:{value:"1:是;0:否"}},
            { name: 'tesitemhandle', index: 'tesitemhandle', width: 50,formatter: "select", editoptions:{value:"1:取材处理;2:切片处理"}},
            { name: 'tesischarge', index: 'tesischarge', width: 30,formatter: "select", editoptions:{value:"1:是;0:否"}},
            { name: 'tesuseflag', index: 'tesuseflag', width: 30,formatter: "select", editoptions:{value:"1:启用;0:停用"}},
            { name: 'tespinyincode', index: 'tespinyincode', width: 30},
            { name: 'tesfivestroke', index: 'tesfivestroke', width: 30}
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

});
function  clearData(){
    $('#teschinesename').val('');
    $('#tesenglishname').val('');
    $('#tespinyincode').val('');
    $('#tesfivestroke').val('');
    $('#tesitemtype').val('1');
    $('#tespathologyid').val('');
    $('#tespathologyname').val('');
    $('#tesitemhandle').val('1');
    $('#tesischarge').val('1');
    $('#tesuseflag').val('1');
    $('#tesreamrk').val('');
    $('#tesitemproperty').val('100');
    $('#tesisorder').val('100');
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