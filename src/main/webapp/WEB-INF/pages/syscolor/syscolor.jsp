<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<html>
<head>
    <title>系统颜色设置</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0"/>
    <link rel="stylesheet" type="text/css" href="<c:url value='/styles/ui.jqgrid.css'/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value='/styles/jquery-ui.min.css'/>"/>
    <link rel="stylesheet" href="<c:url value='/styles/ztree/zTreeStyle.css'/>"/>
    <link rel="stylesheet" href="<c:url value='/styles/evol-colorpicker.min.css'/>"/>
    <link rel="stylesheet" href="<c:url value='/styles/demo.css'/>"/>
    <!--script type="name/javascript" src="../scripts/bootstrap.min.js"></script-->
    <script src="<c:url value='/scripts/jquery-2.1.4.min.js'/>"></script>
    <script type="text/javascript" src="../scripts/jquery-ui.min.js"></script>
    <script type="text/javascript" src="../scripts/i18n/grid.locale-cn.js"></script>
    <script type="text/javascript" src="../scripts/jquery.jqGrid.js"></script>
    <script src="<c:url value='/scripts/jquery.ztree.all-3.5.js'/>"></script>
    <script src="<c:url value='/scripts/evol-colorpicker.min.js'/>"></script>
    <script type="text/javascript" src="../scripts/validform/Validform.min.js"></script>
    <script type="text/javascript" src="../scripts/layer/layer.js"></script>
    <script type="text/javascript" src="../scripts/pspathology/syscolor.js"></script>

</head>

<!-- Include CSS to eliminate any default margins/padding and set the height of the html element and
     the body element to 100%, because Firefox, or any Gecko based browser, interprets percentage as
     the percentage of the height of its parent container, which has to be set explicitly.  Fix for
     Firefox 3.6 focus border issues.  Initially, don't display flashContent div so it won't show
     if JavaScript disabled.
-->
<style type="text/css">
    .ui-jqgrid {
        border: 1px solid #ddd;
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

    .table-bordered > thead > tr > td, .table-bordered > thead > tr > th {
        background-color: #F5F5F6;
    }

    .ui-autocomplete {
        z-index: 99999999;
    }

</style>
<body>
<div class="row" id="toolbar">
    <div id="mainTable" class="col-xs-12" style="padding-left:0px!important;">
        <div style="padding-top: 5px;">
            <div class="col-xs-12"  style="padding-left:0px!important;">
                <button type="button" class="btn btn-sm btn-primary " title="添加颜色设置" onclick="AddSection()">
                    <i class="ace-icon fa fa-fire bigger-110"></i>
                    添加颜色设置
                </button>
                <button type="button" class="btn btn-sm  btn-success" title="修改颜色设置" onclick="editSection()">
                    <i class="ace-icon fa fa-pencil-square bigger-110"></i>
                    修改颜色设置
                </button>
                <button type="button" class="btn btn-sm btn-danger" title="删除颜色设置" onclick="deleteSection()">
                    <i class="ace-icon fa fa-times bigger-110"></i>
                    删除颜色设置
                </button>

                <div class="input-group col-sm-3 " style="float: right;">
                    <input type="text" id="query" class="form-control search-query" placeholder="输入客户名"/>
                    <span class="input-group-btn">
					<button type="button" class="btn btn-purple btn-sm" onclick="search()">
						<fmt:message key="button.search"/>
						<i class="ace-icon fa fa-search icon-on-right bigger-110"></i>
					</button>
				</span>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="row" id="maincontent">
    <div class="col-xs-12 leftContent">
        <table id="sectionList"></table>
        <div id="pager"></div>
    </div>
</div>

<div  class="row" id="hospitalGrid" style="display: none;">
    <div class="col-xs-12 leftContent">
        <table id="sectionList2"></table>
        <div id="pager2"></div>
    </div>
</div>

<div  class="row" id="userGrid" style="display: none;">
    <div class="col-xs-12 leftContent">
        <table id="sectionList3"></table>
        <div id="pager3"></div>
    </div>
</div>

<div id="addDialog" style="display: none;" class="col-xs-12">
    <form class="form-horizontal" id="addSectionForm" action="#" method="post">
        <input type="hidden" name="colorid" id="colorid">
        <div style="padding-top: 5px;">
            <div style="">
                <div class="form-group" style="margin-left:0px;margin-right:0px;">
                    <label class="col-xs-3 control-label no-padding-right" for="colcustomername"> 客户 </label>
                    <div class="col-xs-8" style="">
                        <input type="text" id="colcustomername" onclick="showHospital()"  name="colcustomername" placeholder="客户" class="col-xs-8"
                               datatype="*2-255"/>
                        <input type="hidden" name="colcustomercode" id="colcustomercode">
                    </div>
                </div>
                <div class="form-group" style="margin-left:0px;margin-right:0px;">
                    <label class="col-xs-3 control-label no-padding-right" for="coltype">设置归属</label>
                    <div class="col-xs-8">
                        <select type="text" id="coltype" name="coltype" onchange="changeOwner()" class="col-xs-3">
                            <option value="1">用户私有</option>
                            <option value="0">系统公用</option>
                        </select>
                    </div>
                </div>
                <div class="form-group" style="margin-left:0px;margin-right:0px;">
                    <label class="col-xs-3 control-label no-padding-right" for="colownername"> 归属用户 </label>
                    <div class="col-xs-8" style="">
                        <input type="text" id="colownername" onclick="initValue()" name="colownername" placeholder="归属用户" class="col-xs-8"
                               datatype="*2-255"/>
                        <input type="hidden" name="colowner" id="colowner">
                    </div>
                </div>
                <div class="form-group" style="margin-left:0px;margin-right:0px;">
                    <label class="col-xs-3 control-label no-padding-right" for="colvalue"> 设置的颜色 </label>
                    <div class="col-xs-8">
                        <input type="text" id="colvalue" name="colvalue"
                               placeholder="设置的颜色" class="col-xs-8" datatype="*2-255"/>
                    </div>
                </div>
                <div class="form-group" style="margin-left:0px;margin-right:0px;">
                    <label class="col-xs-3 control-label no-padding-right" for="colobject">颜色对象</label>
                    <div class="col-xs-8">
                        <select type="text" id="colobject" name="colobject" class="col-xs-3">
                            <option value="Requisition">申请</option>
                            <option value="Sample">标本</option>
                            <option value="SamplingBlock">材块</option>
                            <option value="ParaffinBlock">蜡块</option>
                            <option value="Slide">玻片</option>
                            <option value="Order">医嘱</option>
                        </select>
                    </div>
                </div>

            </div>

            <div class="form-group" style="margin-left:0px;margin-right:0px;">
                <label class="col-xs-3 control-label no-padding-right" for="colmodule">模块 </label>
                <div class="col-xs-8">
                    <select type="text" id="colmodule" name="colmodule" class="col-xs-3" onchange="setSecond(this)">
                        <option value="0" selected="selected" >标本登记</option>
                        <option value="1">取材管理</option>
                        <option value="2">包埋管理</option>
                        <option value="3">切片管理</option>
                        <option value="4">制片管理</option>
                        <option value="5">病理诊断</option>
                    </select>
                </div>
            </div>
            <div class="form-group" style="margin-left:0px;margin-right:0px;">
                <label class="col-xs-3 control-label no-padding-right" for="colobjectstate">对象状态 </label>
                <div class="col-xs-8">
                    <select type="text" id="colobjectstate0" style="display:block" name="colobjectstate0" class="col-xs-3" >
                        <option value="0">已登记</option>
                        <option value="1">已取材</option>
                        <option value="2">已包埋</option>
                        <option value="3">已切片</option>
                        <option value="4">已初诊</option>
                        <option value="5">已审核</option>
                        <option value="6">已发送</option>
                        <option value="7">会诊中</option>
                        <option value="8">报告已打印</option>
                    </select>
                    <select type="text" id="colobjectstate1" style="display:none" name="colobjectstate1" class="col-xs-3">
                        <option value="9">未取材</option>
                        <option value="1">已取材</option>
                    </select>
                    <select type="text" id="colobjectstate2" style="display:none" name="colobjectstate2" class="col-xs-3">
                        <option value="10">待包埋</option>
                        <option value="2">已包埋</option>
                    </select>
                    <select type="text" id="colobjectstate3" style="display:none" name="colobjectstate3" class="col-xs-3">
                        <option value="11">待切片</option>
                        <option value="3">已切片</option>
                    </select>
                    <select type="text" id="colobjectstate4" style="display:none" name="colobjectstate4" class="col-xs-3">
                        <option value="12">未制作</option>
                        <option value="13">已制作</option>
                    </select>
                    <select type="text" id="colobjectstate5" style="display:none" name="colobjectstate5" class="col-xs-3">
                        <option value="0">已登记</option>
                        <option value="5">已审核</option>
                        <option value="14">已打印</option>
                    </select>
                </div>
            </div>
        </div>
</div>
</form>
</div>
</body>
</html>