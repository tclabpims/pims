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
    <title>系统报告项目信息维护</title>
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
    <script type="text/javascript" src="../scripts/pspathology/reportitem.js"></script>
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
<SCRIPT LANGUAGE="JavaScript">
    var zTreeObj;
    // zTree 的参数配置，深入使用请参考 API 文档（setting 配置详解）
    /*******************************
     * ztree 设置参数
     ********************************/
    var setting = {
        data: {
            simpleData: {
                enable: true
            }
        },
        async: {
            enable: true,
            url: "../reqfield/treequery",
            dataType: "json",//默认text
            contentType: "application/json",
            type: "get"//默认post
        },
        check: {
            enable: true,
            chkStyle: "checkbox"
        },
        callback: {
            onClick: function (event, treeId, treeNode, clickFlag) {
                if(treeNode.id == 0){clearData();return false;}
                $("#rptelementname").val(treeNode.name);
                $("#rptelementid").val(treeNode.id);
            }
        },
        view: {}
    };
    // zTree 的数据属性，深入使用请参考 API 文档（zTreeNode 节点数据详解）
    var zNodes = [];
    $(document).ready(function () {
        zTreeObj = $.fn.zTree.init($("#tree"), setting, zNodes);
    });
</SCRIPT>
<div class="row" id="toolbar">
    <div id="mainTable" class="col-xs-12" style="padding-left:0px!important;">
        <div style="padding-top: 5px;">
            <div class="col-xs-12" style="padding-left:0px!important;">
                <button type="button" class="btn btn-sm btn-primary " title="添加报告项目" onclick="AddSection()">
                    <i class="ace-icon fa fa-fire bigger-110"></i>
                    添加报告项目
                </button>
                <button type="button" class="btn btn-sm  btn-success" title="修改报告项目" onclick="editSection()">
                    <i class="ace-icon fa fa-pencil-square bigger-110"></i>
                    修改报告项目
                </button>
                <button type="button" class="btn btn-sm btn-danger" title="删除报告项目" onclick="deleteSection()">
                    <i class="ace-icon fa fa-times bigger-110"></i>
                    删除报告项目
                </button>
                <div class="input-group col-sm-3 " style="float: right;">
                    <input type="text" id="query" class="form-control search-query" placeholder="输入项目中文名称英文名称"/>
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
    <div class="widget-main padding-4 scrollable ace-scroll" style="position: relative;">
        <div class="scroll-content">
            <div class="content">
                <ul id="tree" class="ztree"></ul>
            </div>
        </div>
    </div>
</div>

<div id="addDialog" style="display: none;" class="col-xs-12">
    <form class="form-horizontal" id="addSectionForm" action="#" method="post">
        <input type="hidden" name="reportitemid" id="reportitemid">
        <div style="padding-top: 5px;">
            <div style="">
                <div class="form-group" style="margin-left:0px;margin-right:0px;">
                    <label class="col-xs-3 control-label no-padding-right" for="rptitemsort"> 排序号 </label>
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
                        <input type="hidden" id="rptitemsort" name="rptitemsort"/>
                    </div>
                </div>
                <div class="form-group" style="margin-left:0px;margin-right:0px;">
                    <label class="col-xs-3 control-label no-padding-right" for="rptname"> 项目名称(中文) </label>
                    <div class="col-xs-8" style="">
                        <input type="text" id="rptname" name="rptname" placeholder="项目名称(中文)" class="col-xs-8"
                               datatype="*2-255"/>
                    </div>
                </div>
                <div class="form-group" style="margin-left:0px;margin-right:0px;">
                    <label class="col-xs-3 control-label no-padding-right" for="rptenglishname"> 项目名称(英文) </label>
                    <div class="col-xs-8" style="">
                        <input type="text" id="rptenglishname" name="rptenglishname" placeholder="项目名称(英文)" class="col-xs-8"
                               datatype="*2-255"/>
                    </div>
                </div>
                <div class="form-group" style="margin-left:0px;margin-right:0px;">
                    <label class="col-xs-3 control-label no-padding-right" for="rpttemplatetype"> 引用模板类型 </label>
                    <div class="col-xs-8" style="">
                        <select id="rpttemplatetype">
                            <option value="-1">--请选择--</option>
                            <option value="0">大体所见模板</option>
                            <option value="1">病理所见模板</option>
                            <option value="2">病理诊断模板</option>
                        </select>
                    </div>
                </div>
                <div class="form-group" style="margin-left:0px;margin-right:0px;">
                    <label class="col-xs-3 control-label no-padding-right" for="rptelementname"> Web元素标题 </label>
                    <div class="col-xs-8">
                        <input type="text" id="rptelementname" onclick="showFieldConfig()" name="rptelementname"
                               placeholder="Web元素标题" class="col-xs-8" datatype="*2-255"/>
                        <input type="hidden" id="rptelementid" name="rptelementid">
                    </div>
                </div>
                <div class="form-group" style="margin-left:0px;margin-right:0px;">
                    <label class="col-xs-3 control-label no-padding-right" for="rptitemtype">类型</label>
                    <div class="col-xs-8">
                        <select type="text" id="rptitemtype" name="rptitemtype" onchange="controlItemHandle()" class="col-xs-3">
                            <option value="1">输入框</option>
                            <option value="2">复选框</option>
                            <option value="3">下拉列表框</option>
                        </select>
                    </div>
                </div>

            </div>
            <div class="form-group" style="margin-left:0px;margin-right:0px;">
                <label class="col-xs-3 control-label no-padding-right" for="rptuseflag">使用状态</label>
                <div class="col-xs-8">
                    <select type="text" id="rptuseflag" name="rptuseflag" class="col-xs-3">
                        <option value="1">启用</option>
                        <option value="0">停用</option>
                    </select>
                </div>
            </div>
            <div class="form-group" style="margin-left:0px;margin-right:0px;">
                <label class="col-xs-3 control-label no-padding-right" for="rptrefvalue1">参考值1 </label>
                <div class="col-xs-8">
                    <input type="text" id="rptrefvalue1" name="rptrefvalue1" placeholder="参考值1" class="col-xs-8"
                           datatype="*2-255"/>
                </div>
            </div>
            <div class="form-group" style="margin-left:0px;margin-right:0px;">
                <label class="col-xs-3 control-label no-padding-right" for="rptavgvalue1">平均值1 </label>
                <div class="col-xs-8">
                    <input type="text" id="rptavgvalue1" name="rptavgvalue1" placeholder="平均值1" class="col-xs-8"
                           datatype="*2-255"/>
                </div>
            </div>
            <div class="form-group" style="margin-left:0px;margin-right:0px;">
                <label class="col-xs-3 control-label no-padding-right" for="rptrefvalue2">参考值2 </label>
                <div class="col-xs-8">
                    <input type="text" id="rptrefvalue2" name="rptrefvalue2" placeholder="参考值2" class="col-xs-8"
                           datatype="*2-255"/>
                </div>
            </div>
            <div class="form-group" style="margin-left:0px;margin-right:0px;">
                <label class="col-xs-3 control-label no-padding-right" for="rptavgvalue2">平均值2 </label>
                <div class="col-xs-8">
                    <input type="text" id="rptavgvalue2" name="rptavgvalue2" placeholder="平均值2" class="col-xs-8"
                           datatype="*2-255"/>
                </div>
            </div>
            <div class="form-group" style="margin-left:0px;margin-right:0px;">
                <label class="col-xs-3 control-label no-padding-right" for="rptpinyincode">拼音码 </label>
                <div class="col-xs-8">
                    <input type="text" id="rptpinyincode" name="rptpinyincode" placeholder="拼音码" class="col-xs-8"
                           datatype="*2-255"/>
                </div>
            </div>
            <div class="form-group" style="margin-left:0px;margin-right:0px;">
                <label class="col-xs-3 control-label no-padding-right" for="rptfivestroke">五笔码 </label>
                <div class="col-xs-8">
                    <input type="text" id="rptfivestroke" name="rptfivestroke" placeholder="五笔码" class="col-xs-8"
                           datatype="*2-255"/>
                </div>
            </div>
            <div class="form-group" style="margin-left:0px;margin-right:0px;">
                <label class="col-xs-3 control-label no-padding-right" for="rptdefaultvalue">默认值 </label>
                <div class="col-xs-8">
                    <textarea type="text" id="rptdefaultvalue" name="rptdefaultvalue" placeholder="默认值" class="col-xs-8"
                              datatype="*2-255"></textarea>
                </div>
            </div>
        </div>
</div>
</form>
</div>
</html>