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
    <title>客户基础数据设置</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0"/>
    <link rel="stylesheet" type="text/css" href="<c:url value='/styles/ui.jqgrid.css'/>"/>
    <link rel="stylesheet" href="<c:url value='/styles/ztree/zTreeStyle.css'/>"/>
    <!--script type="name/javascript" src="../scripts/bootstrap.min.js"></script-->
    <script type="text/javascript" src="../scripts/jquery-ui.min.js"></script>
    <script type="text/javascript" src="../scripts/i18n/grid.locale-cn.js"></script>
    <script type="text/javascript" src="../scripts/jquery.jqGrid.js"></script>
    <script src="<c:url value='/scripts/jquery.ztree.all-3.5.js'/>"></script>
    <script type="text/javascript" src="../scripts/validform/Validform.min.js"></script>
    <script type="text/javascript" src="../scripts/layer/layer.js"></script>
    <script type="text/javascript" src="../scripts/pspathology/customerdata.js"></script>
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
                <button type="button" class="btn btn-sm btn-primary " title="添加客户基础数据" onclick="AddSection()">
                    <i class="ace-icon fa fa-fire bigger-110"></i>
                    添加客户基础数据
                </button>
                <button type="button" class="btn btn-sm  btn-success" title="修改客户基础数据" onclick="editSection()">
                    <i class="ace-icon fa fa-pencil-square bigger-110"></i>
                    修改客户基础数据
                </button>
                <button type="button" class="btn btn-sm btn-danger" title="删除客户基础数据" onclick="deleteSection()">
                    <i class="ace-icon fa fa-times bigger-110"></i>
                    删除客户基础数据
                </button>
                <div class="input-group col-sm-3 " style="float: right;">
                    <input type="text" id="queryName" class="form-control search-query" onclick="showHospital(true)" placeholder="选择客户"/>
                    <input type="hidden" id="query" name="query">
                    <span class="input-group-btn">
					<button type="button" class="btn btn-purple btn-sm" onclick="search()">
						<fmt:message key="button.search"/>
						<i class="ace-icon fa fa-search icon-on-right bigger-110"></i>
					</button>
                        <button type="button" class="btn btn-purple btn-sm" onclick="clearQuery()">
						清空
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

<div  class="row" id="reqMaterialGrid" style="display: none;">
    <div class="col-xs-12 leftContent">
        <table id="sectionList3"></table>
        <div id="pager3"></div>
    </div>
</div>

<div class="widget-body" style="display: none;" id="treeArea">
    <div class="widget-main padding-4 scrollable ace-scroll" style="position: relative;">
        <div class="scroll-content">
            <div class="content">
                <ul id="tree" class="ztree"></ul>
            </div>
        </div>
    </div>
</div>

<div  class="row" id="reportItemGrid" style="display: none;">
    <div class="col-xs-12 leftContent">
        <table id="sectionList5"></table>
        <div id="pager5"></div>
    </div>
</div>

<div  class="row" id="testItemGrid" style="display: none;">
    <div class="col-xs-12 leftContent">
        <table id="sectionList6"></table>
        <div id="pager6"></div>
    </div>
</div>

<div  class="row" id="pathologyGrid" style="display: none;">
    <div class="col-xs-12 leftContent">
        <table id="sectionList7"></table>
        <div id="pager7"></div>
    </div>
</div>

<div id="addDialog" style="display: none;" class="col-xs-12">
    <form class="form-horizontal" id="addSectionForm" action="#" method="post">
        <div style="padding-top: 5px;">
            <div style="">
                <div class="form-group" style="margin-left:0px;margin-right:0px;">
                    <label class="col-xs-3 control-label no-padding-right" for="baspathologyname"> 病种名称 </label>
                    <div class="col-xs-8" style="">
                        <input type="text" id="baspathologyname" name="baspathologyname" onclick="showPathology()" placeholder="病种名称" class="col-xs-8"
                               datatype="*2-255"/>
                        <input type="hidden" id="baspathologyid" name="baspathologyid">
                        <input type="hidden" id="basedataid" name="basedataid">
                    </div>
                </div>
                <div class="form-group" style="margin-left:0px;margin-right:0px;">
                    <label class="col-xs-3 control-label no-padding-right" for="bascustomername"> 客户名称 </label>
                    <div class="col-xs-8" style="">
                        <input type="text" id="bascustomername" name="bascustomername" onclick="showHospital()" placeholder="客户名称" class="col-xs-8"
                               datatype="*2-255"/>
                        <input type="hidden" id="bascustomercode" name="bascustomercode">
                    </div>
                </div>
                <div class="form-group" style="margin-left:0px;margin-right:0px;">
                    <label class="col-xs-3 control-label no-padding-right" for="bastype"> 数据类型 </label>
                    <div class="col-xs-8" style="">
                        <select name="bastype" id="bastype" onchange="removeRefData()">
                            <option value="1">申请材料数据</option>
                            <option value="2">申请字段数据</option>
                            <option value="3">报告项目数据</option>
                            <option value="4">申请检查项目数据</option>
                        </select>
                    </div>
                </div>
                <div class="form-group" style="margin-left:0px;margin-right:0px;">
                    <label class="col-xs-3 control-label no-padding-right" for="basrefdataname"> 关联数据名称 </label>
                    <div class="col-xs-8" style="">
                        <input type="text" id="basrefdataname" name="basrefdataname" onclick="showDataGrid()" placeholder="关联数据名称" class="col-xs-8"
                               datatype="*2-255"/>
                        <input type="hidden" name="basrefdataid" id="basrefdataid">
                    </div>
                </div>

                <div class="form-group" style="margin-left:0px;margin-right:0px;">
                    <label class="col-xs-3 control-label no-padding-right" for="basuseflag">使用状态</label>
                    <div class="col-xs-8">
                        <select type="text" id="basuseflag" name="basuseflag" class="col-xs-3">
                            <option value="1">启用</option>
                            <option value="0">停用</option>
                        </select>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>
</html>