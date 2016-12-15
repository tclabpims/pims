<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 909436637@qq.com
  Date: 2016/10/6
  Time: 10:01
--%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<html>
<head>
    <title>系统申请材料维护</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
    <link rel="stylesheet" type="text/css"  href="<c:url value='/styles/ui.jqgrid.css'/>" />
    <!--script type="name/javascript" src="../scripts/bootstrap.min.js"></script-->
    <script type="text/javascript" src="../scripts/jquery-ui.min.js"></script>
    <script type="text/javascript" src="../scripts/i18n/grid.locale-cn.js"></script>
    <script type="text/javascript" src="../scripts/jquery.jqGrid.js"></script>
    <script type="text/javascript" src="../scripts/validform/Validform.min.js"></script>
    <script type="text/javascript" src="../scripts/layer/layer.js"></script>
    <script type="text/javascript" src="../scripts/pspathology/reqmaterial.js"></script>
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
    .table-bordered>thead>tr>td, .table-bordered>thead>tr>th{
        background-color: #F5F5F6;
    }
    .ui-autocomplete {
        z-index: 99999999;
    }
</style>
<div class="row" id="toolbar">
    <div id="mainTable" class="col-xs-12">
        <div  style="padding-top: 5px;">
            <div class="col-xs-12">
                <button type="button" class="btn btn-sm btn-primary " title="添加申请材料" onclick="AddSection()">
                    <i class="ace-icon fa fa-fire bigger-110"></i>
                    添加申请材料
                </button>
                <button type="button" class="btn btn-sm  btn-success" title="修改申请材料" onclick="editSection()">
                    <i class="ace-icon fa fa-pencil-square bigger-110"></i>
                    修改申请材料
                </button>
                <button type="button" class="btn btn-sm btn-danger" title="删除申请材料" onclick="deleteSection()">
                    <i class="ace-icon fa fa-times bigger-110"></i>
                    删除申请材料
                </button>
                <div class="input-group col-sm-3 " style="float: right;">
                    <input type="text" id="query" class="form-control search-query" placeholder="输入材料名称或类型" />
                    <span class="input-group-btn">
					<button type="button" class="btn btn-purple btn-sm" onclick="search()">
						<fmt:message key="button.search"/>
						搜索
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

<div id="addDialog" style="display: none;" class="col-xs-12">
    <form class="form-horizontal" id="addSectionForm" action="#" method="post">
        <input type="hidden" name="materialid" id="materialid">
        <div style="padding-top: 5px;">
            <div style="">
                <div class="form-group" style="margin-left:0px;margin-right:0px;">
                    <label class="col-xs-3 control-label no-padding-right" for="matsort"> 排序号 </label>
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
                        <input type="hidden" id="matsort" name="matsort"/>
                    </div>
                </div>
                <div class="form-group" style="margin-left:0px;margin-right:0px;">
                    <label class="col-xs-3 control-label no-padding-right" for="matname"> 材料名称 </label>
                    <div class="col-xs-8" style="">
                        <input type="text" id="matname"  name= "matname" placeholder="材料名称" class="col-xs-8" datatype="*2-255" />
                    </div>
                </div>
                <div class="form-group" style="margin-left:0px;margin-right:0px;">
                    <label class="col-xs-3 control-label no-padding-right" for="mattypename"> 材料类型 </label>
                    <div class="col-xs-8">
                        <input type="text" id="mattypename" onclick="showMatTYpe()"  name="mattypename" placeholder="材料类型" class="col-xs-8" datatype="*2-255"/>
                        <input type="hidden" id="mattype" name="mattype">
                    </div>
                </div>
                <div class="form-group" style="margin-left:0px;margin-right:0px;">
                    <label class="col-xs-3 control-label no-padding-right" for="matspecial"> 特殊要求 </label>
                    <div class="col-xs-8">
                        <input type="text" id="matspecial"  name="matspecial" placeholder="特殊要求" class="col-xs-8" datatype="*2-255"/>

                    </div>
                </div>
                <div class="form-group" style="margin-left:0px;margin-right:0px;">
                    <label class="col-xs-3 control-label no-padding-right" for="matuseflag">使用状态</label>
                    <div class="col-xs-8">
                        <select type="text" id="matuseflag" name="matuseflag" class="col-xs-3">
                            <option value="1">启用</option>
                            <option value="0">停用</option>
                        </select>
                    </div>
                </div>
                <div class="form-group" style="margin-left:0px;margin-right:0px;">
                    <label class="col-xs-3 control-label no-padding-right" for="matpinyincode">拼音码 </label>
                    <div class="col-xs-8">
                        <input type="text" id="matpinyincode" name="matpinyincode" placeholder="拼音码" class="col-xs-8" datatype="*2-255"/>
                    </div>
                </div>
                <div class="form-group" style="margin-left:0px;margin-right:0px;">
                    <label class="col-xs-3 control-label no-padding-right" for="matfivestrokecode">五笔码 </label>
                    <div class="col-xs-8">
                        <input type="text" id="matfivestrokecode" name="matfivestrokecode" placeholder="五笔码" class="col-xs-8" datatype="*2-255"/>
                    </div>
                </div>
                <div class="form-group" style="margin-left:0px;margin-right:0px;">
                    <label class="col-xs-3 control-label no-padding-right" for="matreamrk">备注 </label>
                    <div class="col-xs-8">
                        <textarea type="text" id="matreamrk" name="matreamrk" placeholder="备注" class="col-xs-8" datatype="*2-255"></textarea>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>
</html>
