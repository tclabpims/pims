<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<html>
<head>
    <title>套餐项目列表</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0"/>
    <link rel="stylesheet" type="text/css" href="<c:url value='/styles/ui.jqgrid.css'/>"/>
    <!--script type="name/javascript" src="../scripts/bootstrap.min.js"></script-->
    <script type="text/javascript" src="../scripts/jquery-ui.min.js"></script>
    <script type="text/javascript" src="../scripts/i18n/grid.locale-cn.js"></script>
    <script type="text/javascript" src="../scripts/jquery.jqGrid.js"></script>
    <script type="text/javascript" src="../scripts/validform/Validform.min.js"></script>
    <script type="text/javascript" src="../scripts/layer/layer.js"></script>
    <script type="text/javascript" src="../scripts/pspathology/package.js"></script>
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
    <div id="mainTable" class="col-xs-12" style="padding-left:0px!important;">
        <div style="padding-top: 5px;">
            <div class="col-xs-12" style="padding-left:0px!important;">
                <button type="button" class="btn btn-sm btn-primary " title="添加" onclick="AddSection()">
                    <i class="ace-icon fa fa-fire bigger-110"></i>
                    添加
                </button>
                <button type="button" class="btn btn-sm  btn-success" title="修改" onclick="editSection()">
                    <i class="ace-icon fa fa-pencil-square bigger-110"></i>
                    修改
                </button>
                <button type="button" class="btn btn-sm btn-danger" title="删除" onclick="deleteSection()">
                    <i class="ace-icon fa fa-times bigger-110"></i>
                    删除
                </button>
                <div class="input-group col-sm-3 " style="float: right;">
                    <input type="text" id="query" class="form-control search-query" placeholder="输入项目名称或病种类别"/>
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

<div class="row" id="hospitalGrid" style="display: none;">
    <div class="col-xs-12 leftContent">
        <table id="sectionList2"></table>
        <div id="pager2"></div>
    </div>
</div>

<div class="row" id="itemGrid" style="display: none;">
    <div>
        <table id="itemList"></table>
        <div id="itemListPager"></div>
    </div>
</div>

<div id="addDialog" style="display: none;" class="col-xs-12">
    <div class="form-horizontal" id="addSectionForm">
        <input type="hidden" name="packageId" id="packageId">
        <div style="padding-top: 5px;">
            <div style="">
                <div class="form-group" style="margin-left:0px;margin-right:0px;">
                    <label class="col-xs-3 control-label no-padding-right" for="packageName"> 套餐名称 </label>
                    <div class="col-xs-8" style="">
                        <input type="text" id="packageName" name="packageName" placeholder="套餐名称" class="col-xs-8"
                               datatype="*2-255"/>
                    </div>
                    <label class="col-xs-3 control-label no-padding-right" for="pathologySelect"> 选择病种 </label>
                    <div class="col-xs-8" style="">
                        <select type="text" id="pathologySelect" name="pathologySelect" onchange="clearChoosedItem()" placeholder="选择病种" class="col-xs-8">

                        </select>
                    </div>
                </div>
                <div class="form-group" style="margin-left:0px;margin-right:0px;">
                    <button onclick="chooseTestItem()">选择套餐项目</button>
                    <div class="col-xs-8" style="">
                        <select id="itemSelect" multiple size="12" style="width: 300px"></select>
                    </div>
                    <button onclick="removeTestItem()">删除套餐项目</button>
                </div>
            </div>
        </div>
        <div style="padding-top: 5px;padding-bottom: 5px;border-bottom: 1px solid black;">
        </div>
        <div style="padding-top: 5px;">
            <div style="text-align: center;margin-left:0px;margin-right:0px;width:100%">
                <div class="col-xs-4" style="padding-top: 5px;display:inline;" id="enableSelect">

                </div>
            </div>
        </div>
    </div>
</div>
</html>