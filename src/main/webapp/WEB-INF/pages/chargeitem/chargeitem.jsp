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
    <title>系统收费项目信息</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0"/>
    <link rel="stylesheet" type="text/css" href="<c:url value='/styles/ui.jqgrid.css'/>"/>
    <!--script type="name/javascript" src="../scripts/bootstrap.min.js"></script-->
    <script type="text/javascript" src="../scripts/jquery-ui.min.js"></script>
    <script type="text/javascript" src="../scripts/i18n/grid.locale-cn.js"></script>
    <script type="text/javascript" src="../scripts/jquery.jqGrid.js"></script>
    <script type="text/javascript" src="../scripts/validform/Validform.min.js"></script>
    <script type="text/javascript" src="../scripts/layer/layer.js"></script>
    <script type="text/javascript" src="../scripts/pspathology/chargeitem.js"></script>
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
                <button type="button" class="btn btn-sm btn-primary " title="添加收费项目" onclick="AddSection()">
                    <i class="ace-icon fa fa-fire bigger-110"></i>
                    添加收费项目
                </button>
                <button type="button" class="btn btn-sm  btn-success" title="修改收费项目" onclick="editSection()">
                    <i class="ace-icon fa fa-pencil-square bigger-110"></i>
                    修改收费项目
                </button>
                <button type="button" class="btn btn-sm btn-danger" title="删除收费项目" onclick="deleteSection()">
                    <i class="ace-icon fa fa-times bigger-110"></i>
                    删除收费项目
                </button>
                <div class="input-group col-sm-3 " style="float: right;">
                    <input type="text" id="query" class="form-control search-query" placeholder="输入项目名称"/>
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

<div id="addDialog" style="display: none;" class="col-xs-12">
    <form class="form-horizontal" id="addSectionForm" action="#" method="post">
        <button type="submit"id="saveButton1" style="display:none;">保存</button>
        <input type="hidden" name="chargeitemid" id="chargeitemid">
        <div style="padding-top: 5px;">
            <div style="">
                <div class="form-group" style="margin-left:0px;margin-right:0px;">
                    <label class="col-xs-3 control-label no-padding-right" for="chiitemsort"> 排序号 </label>
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
                        <input type="hidden" id="chiitemsort" name="chiitemsort"/>
                    </div>
                </div>
                <div class="form-group" style="margin-left:0px;margin-right:0px;">
                    <label class="col-xs-3 control-label no-padding-right" for="chinesename"> 中文名称 </label>
                    <div class="col-xs-8" style="">
                        <input type="hidden" id="testitemid">
                        <input type="text" id="chinesename" name="chinesename" placeholder="中文名称" class="col-xs-8"
                               datatype="*2-255"/>
                    </div>
                </div>
                <div class="form-group" style="margin-left:0px;margin-right:0px;">
                    <label class="col-xs-3 control-label no-padding-right" for="chienglishname"> 英文名称 </label>
                    <div class="col-xs-8" style="">
                        <input type="text" id="chienglishname" name="chienglishname" readonly placeholder="英文名称" class="col-xs-8"
                               datatype="*2-255"/>
                    </div>
                </div>
                <div class="form-group" style="margin-left:0px;margin-right:0px;">
                    <label class="col-xs-3 control-label no-padding-right" for="chicategory">所属统计类</label>
                    <div class="col-xs-8">
                        <input type="text" id="chicategory" name="chicategory" placeholder="所属统计类" class="col-xs-8"
                               datatype="*2-255"/>
                    </div>
                </div>
                <div class="form-group" style="margin-left:0px;margin-right:0px;">
                    <label class="col-xs-3 control-label no-padding-right" for="chiprice">单价</label>
                    <div class="col-xs-8">
                        <input type="text" id="chiprice" name="chiprice" placeholder="单价" class="col-xs-8"
                               datatype="*2-255"/>
                    </div>
                </div>

            <div class="form-group" style="margin-left:0px;margin-right:0px;">
                <label class="col-xs-3 control-label no-padding-right" for="chiuseflag">使用状态</label>
                <div class="col-xs-8">
                    <select type="text" id="chiuseflag" name="chiuseflag" class="col-xs-3">
                        <option value="1">启用</option>
                        <option value="0">停用</option>
                    </select>
                </div>
            </div>
            <div class="form-group" style="margin-left:0px;margin-right:0px;">
                <label class="col-xs-3 control-label no-padding-right" for="chiremark">备注 </label>
                <div class="col-xs-8">
                    <textarea type="text" id="chiremark" name="chiremark" placeholder="备注" class="col-xs-8"
                              datatype="*2-255"></textarea>
                </div>
            </div>
        </div>
</div>
</form>
</div>
</html>