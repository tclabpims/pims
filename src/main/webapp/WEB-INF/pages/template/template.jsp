<%--
  Created by IntelliJ IDEA.
  User: 909436637@qq.com
  Date: 2016/10/6
  Time: 10:01
--%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<html>
<head>
    <title>病理模板信息维护</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
    <link rel="stylesheet" type="text/css"  href="<c:url value='/styles/ui.jqgrid.css'/>" />
    <!--script type="name/javascript" src="../scripts/bootstrap.min.js"></script-->
    <script type="text/javascript" src="../scripts/jquery-ui.min.js"></script>
    <script type="text/javascript" src="../scripts/i18n/grid.locale-cn.js"></script>
    <script type="text/javascript" src="../scripts/jquery.jqGrid.js"></script>
    <script type="text/javascript" src="../scripts/validform/Validform.min.js"></script>
    <script type="text/javascript" src="../scripts/layer/layer.js"></script>
    <script type="text/javascript" src="../scripts/pspathology/template.js"></script>
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
                <button type="button" class="btn btn-sm btn-primary " title="添加模板" onclick="AddSection()">
                    <i class="ace-icon fa fa-fire bigger-110"></i>
                    添加模板
                </button>
                <button type="button" class="btn btn-sm  btn-success" title="修改模板" onclick="editSection()">
                    <i class="ace-icon fa fa-pencil-square bigger-110"></i>
                    修改模板
                </button>
                <button type="button" class="btn btn-sm btn-danger" title="删除模板" onclick="deleteSection()">
                    <i class="ace-icon fa fa-times bigger-110"></i>
                    删除模板
                </button>
                <div class="input-group col-sm-3 " style="float: right;">
                    <input type="text" id="query" class="form-control search-query" placeholder="输入归属用户或客户" />
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

<div  class="row" id="pathologyGrid" style="display: none;">
    <div class="col-xs-12 leftContent">
        <table id="sectionList1"></table>
        <div id="pager1"></div>
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
        <input type="hidden" name="templateid" id="templateid">
        <div style="padding-top: 5px;">
            <div style="">
                <div class="form-group" style="margin-left:0px;margin-right:0px;">
                    <label class="col-xs-3 control-label no-padding-right" for="temsort"> 排序号 </label>
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
                        <input type="hidden" id="temsort" name="temsort"/>
                    </div>
                </div>
                <div class="form-group" style="margin-left:0px;margin-right:0px;">
                    <label class="col-xs-3 control-label no-padding-right" for="tempathologyname"> 病种类别 </label>
                    <div class="col-xs-8" style="">
                        <input type="text" id="tempathologyname" onclick="showPathology()"  name= "tempathologyname" placeholder="病种类别" class="col-xs-8" datatype="*2-255" />
                        <input type="hidden" id="tempathologyid" name="tempathologyid">
                    </div>
                </div>
                <div class="form-group" style="margin-left:0px;margin-right:0px;">
                    <label class="col-xs-3 control-label no-padding-right" for="temcontent"> 模板内容 </label>
                    <div class="col-xs-8">
                        <textarea type="text" cols="10" id="temcontent" name="temcontent" placeholder="模板内容" class="col-xs-8" datatype="*2-255"></textarea>
                    </div>
                </div>
                <div class="form-group" style="margin-left:0px;margin-right:0px;">
                    <label class="col-xs-3 control-label no-padding-right" for="temcustomername"> 客户 </label>
                    <div class="col-xs-8">
                        <input type="text" id="temcustomername" onclick="showHospital()"  name="temcustomername" placeholder="客户" class="col-xs-8" datatype="*2-255"/>
                        <input type="hidden" id="temcustomerid" name="temcustomerid">
                    </div>
                </div>
                <div class="form-group" style="margin-left:0px;margin-right:0px;">
                    <label class="col-xs-3 control-label no-padding-right" for="temtype">模板类型</label>
                    <div class="col-xs-8">
                        <select type="text" id="temtype" name="temtype" onchange="changeOwner()" class="col-xs-3">
                            <option value="0">系统公用</option>
                            <option value="1">用户私有</option>
                        </select>
                    </div>
                </div>

                <div class="form-group" style="margin-left:0px;margin-right:0px;">
                    <label class="col-xs-3 control-label no-padding-right" for="temownername">归所用户 </label>
                    <div class="col-xs-8">
                        <input type="text" id="temownername" name="temownername"  onclick="initValue()" placeholder="归所用户" class="col-xs-8" datatype="*2-255"/>
                        <input type="hidden" id="temownerid" name="temownerid">
                    </div>
                </div>

                <div class="form-group" style="margin-left:0px;margin-right:0px;">
                    <label class="col-xs-3 control-label no-padding-right" for="temclass">模板分类</label>
                    <div class="col-xs-8">
                        <select type="text" id="temclass" name="temclass" class="col-xs-3">
                            <option value="0">大体所见模板</option>
                            <option value="1">病理所见模板</option>
                            <option value="2">病理诊断模板</option>
                        </select>
                    </div>
                </div>

                <div class="form-group" style="margin-left:0px;margin-right:0px;">
                    <label class="col-xs-3 control-label no-padding-right" for="temkey">关键字 </label>
                    <div class="col-xs-8">
                        <input type="text" id="temkey" name="temkey" placeholder="关键字" class="col-xs-8" datatype="*2-255"/>
                    </div>
                </div>
                <div class="form-group" style="margin-left:0px;margin-right:0px;">
                    <label class="col-xs-3 control-label no-padding-right" for="tempinyin">拼音码 </label>
                    <div class="col-xs-8">
                        <input type="text" id="tempinyin" name="tempinyin" placeholder="拼音码" class="col-xs-8" datatype="*2-255"/>
                    </div>
                </div>
                <div class="form-group" style="margin-left:0px;margin-right:0px;">
                    <label class="col-xs-3 control-label no-padding-right" for="temfivestroke">五笔码 </label>
                    <div class="col-xs-8">
                        <input type="text" id="temfivestroke" name="temfivestroke" placeholder="五笔码" class="col-xs-8" datatype="*2-255"/>
                    </div>
                </div>
                <div class="form-group" style="margin-left:0px;margin-right:0px;">
                    <label class="col-xs-3 control-label no-padding-right" for="temspellcode">简码 </label>
                    <div class="col-xs-8">
                        <input type="text" id="temspellcode" name="temspellcode" placeholder="简码" class="col-xs-8" datatype="*2-255"/>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>
</html>
