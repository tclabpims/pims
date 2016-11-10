<%@ page import="org.apache.commons.lang3.StringEscapeUtils" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<html>
<head>
    <title>病理诊断</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0"/>
    <link rel="stylesheet" type="text/css" href="<c:url value='/styles/ui.jqgrid.css'/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value='/styles/jquery-ui.css'/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value='/styles/bootstrap-datetimepicker.min.css'/>"/>
    <script type="text/javascript" src="<c:url value="/scripts/ace.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/ace-elements.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/bootstrap-tag.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/jquery-2.1.4.min.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/jquery-ui.min.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/bootstrap-datetimepicker.min.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/i18n/grid.locale-cn.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/jquery.jqGrid.js'/>"></script>
    <script src="<c:url value='/scripts/ajaxfileupload.js'/>"></script>
    <script src="<c:url value='/scripts/LodopFuncs.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/validform/Validform.min.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/layer/layer.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/pspathology/diagnosis.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/consultation/cons1.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/task/task1.js'/>"></script>
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

    ul.tabs {
        margin: 0px;
        padding: 0px;
        list-style: none;
    }

    ul.tabs li {
        background: none;
        color: #222;
        display: inline-block;
        padding: 10px 15px;
        cursor: pointer;
    }

    ul.tabs li.current {
        background: #ededed;
        color: #222;
    }

    .tab-content {
        display: none;
        background: #ededed;
        padding: 15px;
    }

    .tab-content.current {
        display: inherit;
    }

    .ui-timepicker-div .ui-widget-header {
        margin-bottom: 8px;
    }

    .ui-timepicker-div dl {
        text-align: left;
    }

    .ui-timepicker-div dl dt {
        height: 25px;
        margin-bottom: -25px;
    }

    .ui-timepicker-div dl dd {
        margin: 0 10px 10px 65px;
    }

    .ui-timepicker-div td {
        font-size: 90%;
    }
    object:focus { outline:none; }
</style>
<SCRIPT LANGUAGE="JavaScript">
    var GRID_SELECTED_ROW_SAMPLEID;
    var GRID_SELECTED_ROW_SAMPCUSTOMERID;
    var OsObject = navigator.userAgent;

</SCRIPT>
<body>
<div class="row" id="toolbar">
    <div class="row" id="userGrid" style="display: none;">
        <div class="col-xs-12">
            <table id="sectionList3"></table>
            <div id="pager3"></div>
        </div>
    </div>
    <div class="row" id="userGrid1" style="display: none;">
        <div class="col-xs-12">
            <table id="sectionList4"></table>
            <div id="pager4"></div>
        </div>
    </div>
    <div id="mainTable" class="col-xs-12">
        <div style="padding-top: 5px;">
            <div class="col-xs-12">
                <button type="button" class="btn btn-sm btn-primary " title="保存" onclick="saveSign()">
                    保存
                </button>
                <button type="button" class="btn btn-sm btn-primary" title="上一个" onclick="setSelect(0)">
                    上一个
                </button>
                <button type="button" class="btn btn-sm btn-primary" title="下一个" onclick="setSelect(1)">
                    下一个
                </button>
                <button type="button" class="btn btn-sm btn-primary" title="计费调整" onclick="deleteSection()">
                    计费调整
                </button>
                <button type="button" class="btn btn-sm btn-primary" title="图像采集" onclick="takingPicture()">
                    图像采集
                </button>
                <button type="button" class="btn btn-sm btn-primary" title="图像导入" onclick="importImg()">
                    图像导入
                </button>
                <button type="button" class="btn btn-sm btn-primary" title="预览" onclick="reportOperate(1)">
                    预览
                </button>
                <button type="button" class="btn btn-sm btn-primary" title="打印" onclick="reportOperate(2)">
                    打印
                </button>
                <button type="button" class="btn btn-sm btn-primary" title="发送" onclick="deleteSection()">
                    发送
                </button>
                <button type="button" class="btn btn-sm btn-primary" title="转送">转送</button>
                <button type="button" class="btn btn-sm btn-primary" title="导入" onclick="deleteSection()">
                    导入
                </button>
                <button type="button" class="btn btn-sm btn-primary" title="导出" onclick="deleteSection()">
                    导出
                </button>
                <button type="button" class="btn btn-sm btn-primary" title="抄送管理" onclick="csMarage()">
                    抄送管理
                </button>
                <button type="button" class="btn btn-sm btn-primary" title="发起会诊" onclick="consMarage()">
                    发起会诊
                </button>
                <button type="button" class="btn btn-sm btn-primary" title="加入随访病例" onclick="deleteSection()">
                    加入随访病例
                </button>
                <button type="button" class="btn btn-sm btn-primary" title="加入我的收藏" onclick="deleteSection()">
                    加入我的收藏
                </button>
            </div>
        </div>
    </div>
</div>

<div class="row" id="maincontent">
    <div class="col-xs-4">
        <div class="widget-box widget-color-green ui-sortable-handle">
            <div class="widget-header">
                <h6 class="widget-title">工作列表</h6>
                <div class="widget-toolbar">
                    <a href="#" data-action="collapse">
                        <i class="ace-icon fa fa-chevron-up">隐藏</i>
                    </a>
                </div>
            </div>

            <div class="widget-body" style="display: block;">
                <div class="widget-main padding-4 scrollable ace-scroll" style="position: relative;">
                    <div class="scroll-content">
                        <div class="content">
                            <div style="display:inline;"><label>病种类别：</label>
                                <select onchange="" id="sampathologyid">
                                    <option value="">--请选择--</option>
                                    <%=StringEscapeUtils.unescapeHtml4((String) request.getAttribute("options"))%>
                                </select>
                            </div>
                            <div><label>切片年月：</label><input type="text" id="samplesectionfrom"
                                                            style="width: 120px">~<input type="text"
                                                                                         style="width: 120px"
                                                                                         id="samplesectionto"></div>
                            <div style="display:inline;"><label>条形码：</label><input type="text" id="saminspectionidq"
                                                                                   style="width: 120px"></div>
                            <div style="display:inline;"><label>病理号：</label><input type="text" id="sampathologycodeq"
                                                                                   style="width: 120px"></div>
                            <div><label>病人名称：</label><input type="text" id="sampatientnameq" style="width: 120px">
                                <button onclick="query()"> 查询</button>
                            </div>
                            <div style="display:inline;">
                                <div style="text-align: left">
                                    <label for="selectAll">全选</label>
                                    <input type="checkbox" name="selectAll" id="selectAll">
                                </div>
                                <div style="text-align: right">
                                    <div style="display:inline;text-align: right;">
                                        <button>抄送接收</button>
                                    </div>
                                    <div style="display:inline;text-align: right;">
                                        <button>抄送取消</button>
                                    </div>
                                </div>
                            </div>
                            <div style="display:inline;">
                                <div style="display:inline;padding-right: 5px">
                                    <a href="#">个人</a>
                                </div>
                                <div style="display:inline;padding-right: 5px">
                                    <a href="#">全部</a>
                                </div>
                                <div style="display:inline;padding-right: 5px"><a href="#">已发抄送</a></div>
                                <div style="display:inline;padding-right: 5px"><a href="#">待收抄送</a></div>
                                <div style="display:inline;padding-right: 5px"><a href="#">待审抄送</a></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div style="display: block;">
                <div class="col-xs-12 leftContent">
                    <table id="sectionList"></table>
                    <div id="pager"></div>
                </div>
            </div>

        </div>
    </div>

    <div id="diagnosis" class="col-xs-6">
        <div class="widget-box widget-color-green ui-sortable-handle">
            <div class="widget-header">
                <h6 class="widget-title">病理诊断管理</h6>
                <div class="widget-toolbar">
                    <a href="#" data-action="collapse">
                        <i class="ace-icon fa fa-chevron-up">隐藏</i>
                    </a>
                </div>
            </div>
            <div style="display: block;">
                <div class="widget-main padding-4 scrollable ace-scroll" style="position: relative;">
                    <div style="display:inline;"><label>医嘱管理：</label>
                        <select id="yizhugl"></select>
                        <button onclick="reqyizhu()">申请</button>
                    </div>
                </div>
                <div id="tabs" style="margin: 0 auto;">
                    <ul class="tabs">
                        <li class="tab-link"><a href="#tabs-1">病人基本信息</a></li>
                        <li class="tab-link"><a href="#tabs-2">取材信息</a></li>
                    </ul>
                    <div id="tabs-1">
                        <div>
                            <div style="display: inline">病理号：<input type="text"
                                                                    style="width:120px;border-width: 0px 0px 1px 0px"
                                                                    id="sampathologycode"></div>
                            <input type="hidden" id="sampleid"/>
                            <input type="hidden" id="customerId"/>
                            <input type="hidden" id="pathologyCode"/>
                            <div style="display: inline">条形码：<input type="text"
                                                                    style="width:120px;border-width: 0px 0px 1px 0px"
                                                                    id="saminspectionid"></div>
                            <div style="display: inline">年龄：<input type="text"
                                                                   style="width:120px;border-width: 0px 0px 1px 0px"
                                                                   id="sampatientage"></div>
                        </div>
                        <div>
                            <div style="display: inline">病人姓名：<input type="text"
                                                                     style="width:120px;border-width: 0px 0px 1px 0px"
                                                                     id="sampatientname">详细
                            </div>
                            <div style="display: inline">性别：<input type="text"
                                                                   style="width:120px;border-width: 0px 0px 1px 0px"
                                                                   id="sampatientgender"></div>
                            <div style="display: inline">送检医生：<input type="text"
                                                                     style="width:120px;border-width: 0px 0px 1px 0px"
                                                                     id="samsenddoctorid"></div>
                        </div>
                        <div>
                            <div style="display: inline">住院号：<input type="text"
                                                                    style="width:120px;border-width: 0px 0px 1px 0px"
                                                                    id="sampatientnumber"></div>
                            <div style="display: inline">常规收费：<input type="text"
                                                                     style="width:120px;border-width: 0px 0px 1px 0px"
                                                                     id=""></div>
                            <div style="display: inline">送检科室：<input type="text"
                                                                     style="width:120px;border-width: 0px 0px 1px 0px"
                                                                     id="samdeptname">
                            </div>
                        </div>
                        <div>
                            <div style="display: inline">床号：<input type="text"
                                                                   style="width:120px;border-width: 0px 0px 1px 0px"
                                                                   id="sampatientbed">
                            </div>
                            <div style="display: inline">末次月经：<input type="text"
                                                                     style="width:120px;border-width: 0px 0px 1px 0px"
                                                                     id="reqlastmenstruation"></div>
                            <div style="display: inline">送检医院：<input type="text"
                                                                     style="width:120px;border-width: 0px 0px 1px 0px"
                                                                     id="samsendhospital"></div>
                        </div>
                        <div>
                            <div style="display: inline">绝经：<input type="checkbox"></div>
                            <div style="display: inline">送检材料：<textarea type="text" id="samsamplename"
                                                                        style="border-width: 0px 0px 1px 0px"></textarea>
                            </div>
                            <div style="display: inline">临床诊断：<textarea type="text" id="sampatientdignoses"
                                                                        style="border-width: 0px 0px 1px 0px"></textarea>
                            </div>
                        </div>

                    </div>
                    <div id="tabs-2">
                        <div class="row" id="materialGrid">
                            <div class="col-xs-12 leftContent">
                                <table id="materialList"></table>
                                <div id="pager2"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="ctabs">
            <ul>
                <li><a href="#tabs-3">报告诊断</a></li>
                <li><a href="#tabs-4">免疫组化</a></li>
                <li><a href="#tabs-5">特殊染色</a></li>
                <li><a href="#tabs-6">分子病理</a></li>
            </ul>
            <div id="tabs-3">
                <form id="diagnosisInfoForm" onsubmit="return false;">
                    <%=StringEscapeUtils.unescapeHtml4((String) request.getAttribute("diagnosisItems")) == null ? "请先配置当前病种相关的病理诊断字段、报告项目以及客户相关数据" : StringEscapeUtils.unescapeHtml4((String) request.getAttribute("diagnosisItems"))%>
                </form>
                <div style="alignment: center">
                    <button onclick="saveDiagnosisInfo()">保存</button>
                </div>

            </div>
            <div id="tabs-4">
                <div>
                    <div style="display: inline">诊断报告：<select>
                        <option>病理所见模板</option>
                    </select>
                        <button> 模板保存</button>
                    </div>
                    <div style="display: inline"><select>
                        <option>病理报告模板</option>
                    </select>
                        <button> 模板保存</button>
                    </div>
                </div>
                <div><label>巨检描述：</label><textarea cols="80" rows="3"></textarea></div>
                <div><label>病理所见：</label><textarea cols="80" rows="3"></textarea></div>
                <div><label>病理诊断：</label><textarea cols="80" rows="3"></textarea></div>
                <div><label>免疫结果：</label><textarea cols="80" rows="3"></textarea></div>
                <div style="alignment: center">
                    <button>保存</button>
                </div>
            </div>
            <div id="tabs-5">
                <div>
                    <div style="display: inline">诊断报告：<select>
                        <option>病理所见模板</option>
                    </select>
                        <button> 模板保存</button>
                    </div>
                    <div style="display: inline"><select>
                        <option>病理报告模板</option>
                    </select>
                        <button> 模板保存</button>
                    </div>
                </div>
                <div><label>巨检描述：</label><textarea cols="80" rows="3"></textarea></div>
                <div><label>病理所见：</label><textarea cols="80" rows="3"></textarea></div>
                <div><label>病理诊断：</label><textarea cols="80" rows="3"></textarea></div>
                <div><label>特殊染色：</label><textarea cols="80" rows="3"></textarea></div>
                <div style="alignment: center">
                    <button>保存</button>
                </div>
            </div>
            <div id="tabs-6">
                <div>
                    <div style="display: inline">诊断报告：<select>
                        <option>病理所见模板</option>
                    </select>
                        <button> 模板保存</button>
                    </div>
                    <div style="display: inline"><select>
                        <option>病理报告模板</option>
                    </select>
                        <button> 模板保存</button>
                    </div>
                </div>
                <div><label>巨检描述：</label><textarea cols="80" rows="3"></textarea></div>
                <div><label>病理所见：</label><textarea cols="80" rows="3"></textarea></div>
                <div><label>病理诊断：</label><textarea cols="80" rows="3"></textarea></div>
                <div><label>分子病理：</label><textarea cols="80" rows="3"></textarea></div>
                <div style="alignment: center">
                    <button>保存</button>
                </div>
            </div>
            <div>
                <div>报告日期：<input type="text" class="form_datetime1" id="samreportedtime" onchange="doctorSign(0)"
                                 name="samreportedtime">
                    <button>延迟报告</button>
                </div>
                <div>初查医生：<input type="text" readonly id="saminitiallyusername" name="saminitiallyusername">
                    <button onclick="doctorSign(1)">初查签名</button>
                    <button onclick="doctorSign(2)">取消</button>
                </div>
                <div>审核医生：<input type="text" readonly id="samauditer" name="samauditer">
                    <button onclick="doctorSign(3)">审核签名</button>
                    <button onclick="doctorSign(4)">取消</button>
                </div>
                <div>
                    <div style="display:none">
                        <input type="hidden" id="samreportorid">
                        <input type="hidden" id="samreportor">
                        <input type="hidden" id="samauditedtime">
                        <input type="hidden" id="samauditerid">
                        <input type="hidden" id="saminitiallytime">
                        <input type="hidden" id="saminitiallyuserid">
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div id="takingPicture" class="col-xs-2">
        <div class="widget-box widget-color-green ui-sortable-handle">
            <div class="widget-header">
                <h6 class="widget-title">图像采集</h6>
                <div class="widget-toolbar">
                    <a href="#" data-action="collapse">
                        <i class="ace-icon fa fa-chevron-up">隐藏</i>
                    </a>
                </div>
            </div>
        </div>
        <div id="imgContainer"></div>
    </div>
    <div class="row" style="display:none" id="templateGrid">
        <div class="col-xs-12 leftContent">
            <table id="templateList"></table>
            <div id="templatePager"></div>
        </div>
    </div>
    <div id="reportTemplateList" style="display:none;alignment: center">
        <div class="mainContent" style="text-align:center;">
            <select id="reportTemplateSelect">
            </select>
        </div>
    </div>
    <div style="text-align: left;margin-left:5px;display:none" id="templateForm">
        <div style="text-align: left">
            <div>
                <div class="form-group" style="margin-top:5px;display:inline">
                    <label for="temkey">关键字：</label>
                    <input id="temkey" type="text">
                </div>
                <div class="form-group" style="margin-top:5px;display:inline">
                    <label for="owner">私有模板:</label>
                    <input type="checkbox" checked id="owner">
                </div>
            </div>
            <div>
                <div class="form-group" style="margin-top:5px;display:inline">
                    <label for="tempinyin">拼音码：</label>
                    <input id="tempinyin" type="text">
                </div>
                <div class="form-group" style="margin-top:5px;display:inline">
                    <label for="temfivestroke">五笔码:</label>
                    <input type="text" checked id="temfivestroke">
                </div>
            </div>
            <div>
                <div class="form-group" style="margin-top:5px;display:inline">
                    <label for="temspellcode">简码：</label>
                    <input id="temspellcode" type="text">
                </div>
                <div class="form-group" style="margin-top:5px;display:inline">
                    <label for="temsort">排序号:</label>
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
            <div class="form-group" style="margin-top:5px;">
                <label for="temcontent">模板内容：</label>
                <textarea id="temcontent" cols="80" rows="3"></textarea>
            </div>
        </div>
    </div>
    <div id="uploadDialog" style="text-align:left;display:none">
        <fieldset style="width:95%; margin-left:4px;">
            <div>
                <div id="more" style="float:left;">
                    <div class="col-sm-12" style="margin-top:5px;">
                        <input type="file" id="imgFile" name="imgFile" class="col-sm-10"/>
                    </div>
                </div>
            </div>
        </fieldset>
    </div>
    <div style="width: 780px;height: 500px;display: none;" id="specialCheckDialog">
        <div style="float:left;width: 60%;height: 100%;padding-left:10px;padding-right: 10px;display: inline">
            <div style="width: 100%;padding-top:5px;height: 20px;font-weight:bold;">基本信息</div>
            <div style="width: 100%;height: 20%;">
                <div style="padding-top:5px;">性别：<input id="patientGender"
                                                        style="width: 60px;border-width: 0px 0px 1px 0px">住院号：<input
                        id="patientZyh" style="width: 120px;border-width: 0px 0px 1px 0px"></div>
                <div style="padding-top:5px;">病人姓名：<input id="patientName"
                                                          style="width: 120px;border-width: 0px 0px 1px 0px">年龄：<input
                        id="patientAge" style="width: 60px;border-width: 0px 0px 1px 0px">床号：<input id="patientBed"
                                                                                                    style="width: 120px;border-width: 0px 0px 1px 0px">
                </div>
                <div style="padding-top:5px;">临床诊断：<input id="patientDiagnosisNote"
                                                          style="width: 300px;border-width: 0px 0px 1px 0px"></div>
            </div>
            <div style="width: 100%;height: 20px;font-weight:bold;">特殊检查</div>
            <div style="width: 100%;height: 23%;">
                <div style="padding-top:5px;">医嘱号：<input id="ordercode" value="" readonly
                                                         style="width: 120px;border-width: 0px 0px 1px 0px">检查类型：
                    <input name="reqType" id="reqType" readonly style="width: 120px;border-width: 0px 0px 1px 0px">
                </div>
                <div style="padding-top:5px;">
                    源病理号：<input id="yblNo" readonly style="width: 120px;border-width: 0px 0px 1px 0px">
                    申请医生：
                    <input id="reqDoctor" style="width: 120px;border-width: 0px 0px 1px 0px">
                    <input type="hidden" id="lcal_hosptail" value="${send_hosptail}"/>
                    <input type="hidden" id="reqDoctorId" value=""/>
                </div>
                <div style="padding-top:5px;">
                    源条形码：<input id="ytxm" readonly style="width: 120px;border-width: 0px 0px 1px 0px">
                    申请日期：<input id="reqDate" class="" style="width: 120px;border-width: 0px 0px 1px 0px">
                </div>
                <input id="whitePieceNo" type="hidden" style="width: 120px;border-width: 0px 0px 1px 0px">

            </div>
            <div style="width: 100%;height: 25px;font-weight:bold;">项目一览
                <button onclick="removeItems()">删除</button>
                蜡块选择<select id="lkxz" onchange="getWhitePiece()"></select></div>
            <div style="width: 100%;padding-top:5px;" id="itemListContainer">
                <table id="itemList"></table>
            </div>
            <div style="width: 100%;" id="itemSummary">
                <%--癌基因蛋白：3 项，199元；单克隆抗体：3 项，199元；合计：398元--%>
            </div>
        </div>
        <div style="float:right;width: 40%;height: 100%;padding-left:10px;padding-right: 10px;display: inline">
            <div style="width: 100%;height: 20px;;font-weight:bold;">白片信息</div>
            <div style="width: 100%;padding-top:5px;" id="lakuaiListContainer">
                <table id="lkItemList"></table>
            </div>
            <div style="width: 100%;height: 25px;">项目套餐：<select id="itemPackage"
                                                                onchange="getItemInfo(this.value)"></select>
                <button onclick="appendAll()">全部追加</button>
            </div>
            <div style="width: 100%;height: 25px;">项目名称：<input id="itemName"></div>
            <div style="width: 100%;height: 35%;">
                <table id="ckItemList"></table>
            </div>
        </div>
    </div>
    <div id="flashContent" style="display: none;">
        <p>
            To view this page ensure that Adobe Flash Player version
            11.1.0 or greater is installed.
        </p>
        <script type="text/javascript">
            var pageHost = ((document.location.protocol == "https:") ? "https://" : "http://");
            document.write("<a href='http://www.adobe.com/go/getflashplayer'><img src='"
                    + pageHost + "www.adobe.com/images/shared/download_buttons/get_flash_player.gif' alt='Get Adobe Flash player' /></a>" );
        </script>
    </div>
        <%
                String swfPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() +"/scripts/picture/";
                String swf = swfPath+"Main.swf";
                String install_ = swfPath + "playerProductInstall.swf";
            %>
    <script type="text/javascript">
        function initVideo() {
            // For version detection, set to min. required Flash Player version, or 0 (or 0.0.0), for no version detection.
            var swfVersionStr = "11.1.0";
            // To use express install, set to playerProductInstall.swf, otherwise the empty string.
            var xiSwfUrlStr = "<%=install_%>";
            var flashvars = {};
            var params = {};
            params.quality = "high";
            params.bgcolor = "#ffffff";
            params.allowscriptaccess = "sameDomain";
            params.allowfullscreen = "true";
            params.wmode = "transparent";
            var attributes = {};
            attributes.id = "Main";
            attributes.name = "Main";
            attributes.align = "middle";
            swfobject.embedSWF(
                    "<%=swf%>", "flashContent",
                    "100%", "100%",
                    swfVersionStr, xiSwfUrlStr,
                    flashvars, params, attributes, function(){
                        layer.msg('视频控件已加载！', {
                        time: 1500 //自动关闭
                    });});
            // JavaScript enabled so display the flashContent div in case it is not replaced with a swf object.
            swfobject.createCSS("#flashContent", "display:block;text-align:left;");
        }
    </script>
    <noscript>
        <object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="100%" height="100%" id="Main">
            <param name="movie" value="<%=swf%>" />
            <param name="quality" value="high" />
            <param name="bgcolor" value="#ffffff" />
            <param name="allowScriptAccess" value="sameDomain" />
            <param name="allowFullScreen" value="true" />
            <!--[if !IE]>-->
            <object type="application/x-shockwave-flash" data="<%=swf%>" width="100%" height="100%">
                <param name="quality" value="high" />
                <param name="bgcolor" value="#ffffff" />
                <param name="allowScriptAccess" value="sameDomain" />
                <param name="allowFullScreen" value="true" />
                <!--<![endif]-->
                <!--[if gte IE 6]>-->
                <p>
                    Either scripts and active content are not permitted to run or Adobe Flash Player version
                    11.1.0 or greater is not installed.
                </p>
                <!--<![endif]-->
                <a href="http://www.adobe.com/go/getflashplayer">
                    <img src="http://www.adobe.com/images/shared/download_buttons/get_flash_player.gif" alt="Get Adobe Flash Player" />
                </a>
                <!--[if !IE]>-->
            </object>
            <!--<![endif]-->
        </object>
    </noscript>
</body>
</html>
