<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: 909436637@qq.com
  Date: 2016/10/6
  Time: 10:01
--%>
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
<style>
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
<div class="row" id="toolbar">
    <div id="mainTable" class="col-xs-12">
        <div style="padding-top: 5px;">
            <div class="col-xs-12">
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
                    <input type="text" id="colmodule" name="colmodule" placeholder="模块" class="col-xs-8"
                           datatype="*2-255"/>
                </div>
            </div>
            <div class="form-group" style="margin-left:0px;margin-right:0px;">
                <label class="col-xs-3 control-label no-padding-right" for="colobjectstate">对象状态 </label>
                <div class="col-xs-8">
                    <select type="text" id="colobjectstate" name="colobjectstate" class="col-xs-3">
                        <option value="1">已登记</option>
                        <option value="2">已审核</option>
                        <option value="3">已打印</option>
                    </select>
                </div>
            </div>
        </div>
</div>
</form>
</div>
</html>