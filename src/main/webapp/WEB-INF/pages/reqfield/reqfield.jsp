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
    <title>申请字段信息维护</title>
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
    <script type="text/javascript" src="../scripts/pspathology/reqfield.js"></script>
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
                if(treeNode.id == 0){clearData();$('#saveButton').attr("disabled", true);return false;}
                $.post('../reqfield/fieldconfig', {
                    fieldid: treeNode.id
                }, function (data) {
                    $('#fieelementtype').val(data.fieelementtype);
                    $('#fieelementid').val(data.fieelementid);
                    $('#fieelementname').val(data.fieelementname);
                    $('#fieshowlevel').val(data.fieshowlevel);
                    $('#fiepelementid').val(data.fiepelementid);
                    $('#fiedefaultvalue').val(data.fiedefaultvalue);
                    $('#fieshoworder').val(data.fieshoworder);
                    $('#fieuseflag').val(data.fieuseflag);
                    $('#fieremark').val(data.fieremark);
                    $('#fieldid').val(treeNode.id);
                    $('#saveButton').attr("disabled", false);
                });
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
    <div id="mainTable" class="col-xs-12">
        <div style="padding-top: 5px;">
            <div class="col-xs-12">
                <button type="button" class="btn btn-sm btn-primary " title="添加配置" onclick="AddSection()">
                    <i class="ace-icon fa fa-fire bigger-110"></i>
                    添加配置
                </button>
                <button type="button" class="btn btn-sm btn-danger" title="删除配置" onclick="deleteSection()">
                    <i class="ace-icon fa fa-times bigger-110"></i>
                    删除配置
                </button>
                <div class="input-group col-sm-3 " style="float: right;">
                    <input type="text" id="query" class="form-control search-query" placeholder="输入名称或类型"/>
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
    <div class="col-xs-4">
        <div class="widget-box widget-color-green ui-sortable-handle">
            <div class="widget-header">
                <h6 class="widget-title">字段配置列表</h6>
                <div class="widget-toolbar">
                    <a href="#" data-action="collapse">
                        <i class="ace-icon fa fa-chevron-up"></i>
                    </a>
                </div>
            </div>

            <div class="widget-body" style="display: block;">
                <div class="widget-main padding-4 scrollable ace-scroll" style="position: relative;">
                    <div class="scroll-content">
                        <div class="content">
                            <ul id="tree" class="ztree"></ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>


    </div>
    <div id="addDialog" class="col-xs-8 leftContent">
        <form class="form-horizontal" id="addSectionForm" action="#" method="post">
            <input type="hidden" name="fieldid" id="fieldid">
            <div style="padding-top: 5px;">
                <div style="">
                    <div class="form-group" style="margin-left:0px;margin-right:0px;">
                        <label class="col-xs-3 control-label no-padding-right" for="fieelementtype"> 对象类型 </label>
                        <div class="col-xs-8">
                            <select id="fieelementtype" name="fieelementtype">
                                <option value="input">单行输入框-input</option>
                                <option value="textarea">多行输入框-textarea</option>
                                <option value="select">下拉列表-select</option>
                                <option value="radio">单选框-radio</option>
                                <option value="checkbox">复选框-checkbox</option>
                                <option value="label">标签-label</option>
                                <option value="form">表单-form</option>
                                <option value="div">布局-div</option>
                                <option value="span">布局-span</option>
                                <option value="table">表格-table</option>
                                <option value="tr">表格行-tr</option>
                                <option value="td">表格列-td</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group" style="margin-left:0px;margin-right:0px;">
                        <label class="col-xs-3 control-label no-padding-right" for="fieelementid"> 对象ID </label>
                        <div class="col-xs-8" style="">
                            <input type="text" id="fieelementid" name="fieelementid" placeholder="对象ID" class="col-xs-8"
                                   datatype="*1-255"/>
                        </div>
                    </div>
                    <div class="form-group" style="margin-left:0px;margin-right:0px;">
                        <label class="col-xs-3 control-label no-padding-right" for="fieelementname"> 对象名称 </label>
                        <div class="col-xs-8">
                            <input type="text" id="fieelementname" name="fieelementname" placeholder="对象名称"
                                   class="col-xs-8" datatype="*1-255">
                        </div>
                    </div>
                    <div class="form-group" style="margin-left:0px;margin-right:0px;">
                        <label class="col-xs-3 control-label no-padding-right" for="fieshowlevel"> 显示级别 </label>
                        <div class="col-xs-8">
                            <input type="text" id="fieshowlevel" name="fieshowlevel" placeholder="显示级别" class="col-xs-8"
                                   datatype="*1-255"/>
                        </div>
                    </div>

                    <input type="hidden" id="fiepelementid" name="fiepelementid"/>

                    <div class="form-group" style="margin-left:0px;margin-right:0px;">
                        <label class="col-xs-3 control-label no-padding-right" for="fiedefaultvalue">默认值 </label>
                        <div class="col-xs-8">
                            <input type="text" id="fiedefaultvalue" name="fiedefaultvalue" placeholder="默认值"
                                   class="col-xs-8" datatype="*2-255"/>
                        </div>
                    </div>
                    <div class="form-group" style="margin-left:0px;margin-right:0px;">
                        <label class="col-xs-3 control-label no-padding-right" for="fieshoworder">显示顺序 </label>
                        <div class="col-xs-8">
                            <input type="text" id="fieshoworder" name="fieshoworder" placeholder="显示顺序" class="col-xs-8"
                                   datatype="*1-255"/>
                        </div>
                    </div>
                    <div class="form-group" style="margin-left:0px;margin-right:0px;">
                        <label class="col-xs-3 control-label no-padding-right" for="fieuseflag">使用状态</label>
                        <div class="col-xs-8">
                            <select type="text" id="fieuseflag" name="fieuseflag" class="col-xs-3">
                                <option value="1">启用</option>
                                <option value="0">停用</option>
                            </select>
                        </div>
                    </div>

                    <div class="form-group" style="margin-left:0px;margin-right:0px;">
                        <label class="col-xs-3 control-label no-padding-right" for="fieremark">备注 </label>
                        <div class="col-xs-8">
                            <input type="text" id="fieremark" name="fieremark" placeholder="备注" class="col-xs-8"/>
                        </div>
                    </div>
                    <div class="form-group" style="margin-left:0px;margin-right:0px;">
                        <button type="button" class="btn btn-sm btn-primary" title="保存" id="saveButton" disabled="true" onclick="saveFieldData()">
                            <i class="ace-icon fa fa-fire bigger-110"></i>
                            保存
                        </button>
                        <button type="button" class="btn btn-sm btn-danger" title="清空" onclick="clearData()">
                            <i class="ace-icon fa fa-times bigger-110"></i>
                            清空
                        </button>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>
</html>
