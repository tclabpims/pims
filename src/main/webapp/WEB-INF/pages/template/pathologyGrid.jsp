<%--
  Created by IntelliJ IDEA.
  User: zhou
  Date: 2016/6/14
  Time: 11:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<head>
    <script type="text/javascript" src="<c:url value="/scripts/jquery-2.1.4.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/jquery-ui.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/bootstrap.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/ace.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/ace-elements.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/bootstrap-tag.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/i18n/grid.locale-cn.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/jquery.form.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/jquery.jqGrid.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/layer/layer.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/validform/Validform.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/jquery.bootstrap-duallistbox.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/bootstrap-datetimepicker.min.js"/>"></script>

    <link rel="stylesheet" type="text/css"  href="<c:url value='/styles/ui.jqgrid.css'/>" />
</head>
<style>
    body{
        overflow: hidden;
    }
    .tab-content{
        padding: 2px 12px;
        min-height: 510px;
    }
    .widget-toolbar{
        z-index: 999;
        line-height:34px;
    }
    .bootstrap-duallistbox-container .buttons{
        display: none;
    }
    .lazy_header .input-icon, .nav-search-input{
        width: 100%;
    }
    .form-horizontal .form-group{
        margin-right: 0px;
        margin-left: 0px;
    }
    div.treeList{
        overflow: auto;
        min-height: 510px;
        overflow: auto;
        border: 1px solid #eeeeee;
    }
    .info-container{
        display: none;
    }
    ::-webkit-scrollbar-track {
        background-color: #F5F5F5
    }
    ::-webkit-scrollbar {
        width: 6px;
        background-color: #F5F5F5
    }

    ::-webkit-scrollbar-thumb {
        background-color: #999
    }
</style>
    <div class="row">
        <div class="col-xs-12" id="leftContent">
            <table id="leftGrid"></table>
            <div id="leftPager"></div>
        </div>
    </div>
    <div class="row">
        <div class="space-12"></div>
        <div class="layui-layer-btn">
            <a class="layui-layer-btn0" onclick="jQuery.Custom.Save()">保存</a>
            <a class="layui-layer-btn1" onclick="jQuery.Custom.Cancel()">取消</a>
        </div>
    </div>
<script>
    jQuery.Custom =(function(){
        var public ={
            indexGrid:$("#leftGrid"),
            updatePagerIcons:function(table){
                var replacement ={
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
            },
            initGrid:function(){
                $(window).on('resize.jqGrid', function () {
                    public.indexGrid.jqGrid('setGridWidth', $("#leftContent").width(),false);
                });
                public.indexGrid.jqGrid({
                    url: "<%=request.getContextPath()%>/pspathology/dcm/query",
                    datatype: "json",
                    height: 200,
                    //shrinkToFit: false,
                    regional : 'cn',
                    autoWidth:true,
                    /*width: $('.leftContent').width(),*/
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
                    loadComplete: function () {
                        var table = this;
                        setTimeout(function () {
                            public.updatePagerIcons(table);
                        }, 0);
                    },
                    onSelectRow: function (id) {
                        // var rowData = $("#rightGrid").jqGrid('getRowData',id);
                    },
                    ondblClickRow: function (id) {
                        var rowData = public.indexGrid.jqGrid('getRowData',id);
                    },
                    //multiselect : true,
                    altRows:true,
                    rowNum: 10,
                    //viewrecords: true,
                    rowList: [10, 20, 40],
                    rownumbers: true, // 显示行号
                    rownumWidth: 35, // the width of the row numbers columns
                    pager: "#leftPager"
                });
                //$('#leftPager_left').append('<label><input type="search" id="query" style="display:inline;height: 25px;width: 100px;" class="form-control input-sm" placeholder="输入名称" aria-controls="dynamic-table"></label>');
                $(window).triggerHandler('resize.jqGrid');
            },
            search:function(){
            },
            /*保存数据*/
            Save:function(){

            },
            Cancel:function(){
                var index = parent.layer.getFrameIndex(window.name);
                parent.layer.close(index);
            }
        };
        return public;
    })();
</script>
