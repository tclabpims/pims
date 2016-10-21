<%--
  Created by IntelliJ IDEA.
  User: 909436637@qq.com
  Date: 2016/10/6
  Time: 10:01
--%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<html>
<head>
    <title>客户病种信息设置</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0"/>
    <link rel="stylesheet" type="text/css" href="<c:url value='/styles/ui.jqgrid.css'/>"/>
    <!--script type="name/javascript" src="../scripts/bootstrap.min.js"></script-->
    <script type="text/javascript" src="../scripts/jquery-ui.min.js"></script>
    <script type="text/javascript" src="../scripts/i18n/grid.locale-cn.js"></script>
    <script type="text/javascript" src="../scripts/jquery.jqGrid.js"></script>
    <script type="text/javascript" src="../scripts/validform/Validform.min.js"></script>
    <script type="text/javascript" src="../scripts/layer/layer.js"></script>
    <script type="text/javascript" src="../scripts/pspathology/view.js"></script>
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

<div class="row" id="pathologyGrid" style="display: none;">
    <div class="col-xs-12 leftContent">
        <table id="sectionList1"></table>
        <div id="pager1"></div>
    </div>
</div>

<div class="row" id="hospitalGrid" style="display: none;">
    <div class="col-xs-12 leftContent">
        <table id="sectionList2"></table>
        <div id="pager2"></div>
    </div>
</div>

<div class="row" id="userGrid" style="display: none;">
    <div class="col-xs-12 leftContent">
        <table id="sectionList3"></table>
        <div id="pager3"></div>
    </div>
</div>

<div id="addDialog" style="display: none;" class="col-xs-12">
    <form class="form-horizontal" id="addSectionForm" action="#" method="post">
        <input type="hidden" name="id" id="id">
        <div style="padding-top: 5px;">
            <div style="">
                <div class="form-group" style="margin-left:0px;margin-right:0px;">
                    <label class="col-xs-3 control-label no-padding-right" for="sortNo"> 排序号 </label>
                    <div class="col-xs-8">
                        A<select id="FN" name="FN">
                        <option value="0">0</option>
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                        <option value="6">6</option>
                        <option value="7">7</option>
                        <option value="8">8</option>
                        <option value="9">9</option>
                    </select>
                        <select id="SN" name="SN">
                            <option value="0">0</option>
                            <option value="1">1</option>
                            <option value="2">2</option>
                            <option value="3">3</option>
                            <option value="4">4</option>
                            <option value="5">5</option>
                            <option value="6">6</option>
                            <option value="7">7</option>
                            <option value="8">8</option>
                            <option value="9">9</option>
                        </select>
                        <select id="TN" name="TN">
                            <option value="0">0</option>
                            <option value="1">1</option>
                            <option value="2">2</option>
                            <option value="3">3</option>
                            <option value="4">4</option>
                            <option value="5">5</option>
                            <option value="6">6</option>
                            <option value="7">7</option>
                            <option value="8">8</option>
                            <option value="9">9</option>
                        </select>
                        <input type="hidden" id="sortNo" name="sortNo"/>
                    </div>
                </div>
                <div class="form-group" style="margin-left:0px;margin-right:0px;">
                    <label class="col-xs-3 control-label no-padding-right" for="hospitalName"> 客户 </label>
                    <div class="col-xs-8">
                        <input type="text" id="hospitalName" onclick="showHospital()" name="hospitalName"
                               placeholder="客户" class="col-xs-8"/>
                        <input type="hidden" id="hospitalId" name="hospitalId">
                    </div>
                </div>
                <div class="form-group" style="margin-left:0px;margin-right:0px;">
                    <label class="col-xs-3 control-label no-padding-right" for="pathologyName"> 病种类别 </label>
                    <div class="col-xs-8" style="">
                        <input type="text" id="pathologyName" onclick="showPathology()" name="pathologyName"
                               placeholder="病种类别" class="col-xs-8"/>
                        <input type="hidden" id="pathologyId" name="pathologyId">
                    </div>
                </div>
                <div class="form-group" style="margin-left:0px;margin-right:0px;">
                    <label class="col-xs-3 control-label no-padding-right" for="theAlias"> 病种别名 </label>
                    <div class="col-xs-8" style="">
                        <input type="text" id="theAlias" name="theAlias" placeholder="病种别名" class="col-xs-8"/>
                    </div>
                </div>
                <div class="form-group" style="margin-left:0px;margin-right:0px;">
                    <label class="col-xs-3 control-label no-padding-right" for="numberPrefix"> 病理号前缀 </label>
                    <div class="col-xs-8" style="">
                        <input type="text" id="numberPrefix" name= "numberPrefix" placeholder="病理号前缀" class="col-xs-8" />
                    </div>
                </div>
                <div class="form-group" style="margin-left:0px;margin-right:0px;">
                    <label class="col-xs-3 control-label no-padding-right" for="regularExpression"> 生成规则 </label>
                    <div class="col-xs-8" style="">
                        <select id="regularExpression">
                            <option value="yyyyMMdd|D3">年月日+3位数字（共11位）</option>
                            <option value="yyyyMMdd|D4">年月日+4位数字（共12位）</option>
                        </select>
                    </div>
                </div>

                <div class="form-group" style="margin-left:0px;margin-right:0px;">
                    <label class="col-xs-3 control-label no-padding-right" for="useFlag">使用状态</label>
                    <div class="col-xs-8">
                        <select type="text" id="useFlag" name="useFlag" class="col-xs-3">
                            <option value="1">使用</option>
                            <option value="0">停用</option>
                        </select>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>
</html>