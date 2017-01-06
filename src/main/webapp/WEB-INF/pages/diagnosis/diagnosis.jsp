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
    <script src="<c:url value='/scripts/ajaxfileupload-new.js'/>"></script>
    <script src="<c:url value='/scripts/LodopFuncs.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/validform/Validform.min.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/layer/layer.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/pspathology/diagnosis.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/consultation/cons1.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/task/task1.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/pathologysample/fee.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/pspathology/yjxbx.js'/>"></script>
    <%--<script type="text/javascript" src="<c:url value='/scripts/jquery.webcam.min.js'/>"></script>--%>


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
        .tabs-3 table tr td {
            padding-top:2px;
            padding-bottom:2px;
        }
        object:focus { outline:none; }
        .ui-jqgrid-sortable{text-align: center;}
        #maincontent .ui-jqgrid-htable{
            width:490px!important;
        }
        #maincontent .ui-jqgrid-btable{
            width:490px!important;
        }
        #imgContainer{
            border:1px #c5c5c5 solid;
            min-height: 300px;
        }
    </style>

</head>
<SCRIPT LANGUAGE="JavaScript">

    var OsObject = navigator.userAgent;

</SCRIPT>
<body  style="font-family:'Microsoft YaHei',微软雅黑,'MicrosoftJhengHei'!important;">
<div  class="row" id="feeGrid" style="display: none;">
    <h5 style="float: left;"><strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;收费项目一览&nbsp;&nbsp;&nbsp;&nbsp;</strong>
        <button type="button" style="border-radius:3px;border:1px solid #2274E4;background-color: #108CCF;height: 25px;"  onclick="addfeeRow()">
            <span style="color: white">追加行</span>
        </button>
        <button type="button" style="border-radius:3px;border:1px solid #2274E4;background-color: #108CCF;height: 25px;"  onclick="delfeeRow()">
            <span style="color: white">删除行</span>
        </button>
        <button type="button" style="border-radius:3px;border:1px solid #2274E4;background-color: #108CCF;height: 25px;"  onclick="savefeeRow('0')">
            <span style="color: white">保存</span>
        </button>
        <button type="button" style="border-radius:3px;border:1px solid #2274E4;background-color: #108CCF;height: 25px;"  onclick="savefeeRow('1')">
            <span style="color: white">保存并发送</span>
        </button>
    </h5>
    <div class="col-xs-12">
        <table id="feediv"></table>
    </div>
</div>
<div id="delayReportForm" style="display: none">
    <div style="padding-left:10px">
        <div style="display: inline;float: left">病理号：<input id="chipathologycode" style="border-width: 0px 0px 1px 0px"></div>
        <div style="display: inline;">条形码：<input id="testItemChName" style="border-width: 0px 0px 1px 0px"></div>
        <div style="display: inline;float: right">送检医生：<input id="chireqtime" style="border-width: 0px 0px 1px 0px"></div>
    </div>
    <div style="padding-left:10px">
        <div style="display: inline;float: left">病人姓名：<input id="chiordercode" style="border-width: 0px 0px 1px 0px"></div>
        <div style="display: inline;">送检科室：<input id="chirequsername" style="border-width: 0px 0px 1px 0px"></div>
        <div style="display: inline;float: right">送检医院：<input id="chinullslidenum" style="border-width: 0px 0px 1px 0px"></div>
    </div>
    <div style="padding-left:10px">
        <div style="display: inline;float: left">性别：<input id="patientAgent" style="border-width: 0px 0px 1px 0px"></div>
        <div style="display: inline;">年龄：<input id="patientAge1" style="border-width: 0px 0px 1px 0px"></div>
    </div>
    <div style="border:0px solid black;width:100%;border-width:0px 0px 1px 0px;"></div>
    <div style="border:0px solid black;width:100%;padding-top:5px;padding-left:10px">
        <div style="display: inline;float: left;z-index: 99999999 !important;" >
            患者的病例诊断因：<select onchange="" id="delreasonid">
            <option value="">--请选择--</option>
            <%=StringEscapeUtils.unescapeHtml4((String) request.getAttribute("logyids"))%>
        </select>延迟<input id="deldays" onchange="workdayafter(0)"> 工作日发送</div>
        <div style="display: inline;padding-left: 10px">预计报告日期：<input type="text" class="form_datetime2" id="delreporttime" onchange="workdayafter(1)" ></div>
    </div>
    <div style="border:0px solid black;width:100%;padding-top:5px;padding-left:10px">
        <div style="display: inline;border:0px solid black;width:50%;float: left">初步诊断：<textarea id="deldiagnosis"></textarea></div>
        <div style="display: inline;border:0px solid black;width:50%;">延迟原因：<textarea id="delreason"></textarea></div>
    </div>
</div>
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
                    <i class="ace-icon fa fa-floppy-o "></i>
                    保存
                </button>
                <button type="button" class="btn btn-sm btn-info" title="上一个" onclick="setSelect(0)">
                    <i class="ace-icon fa fa-arrow-left "></i>
                    上一个
                </button>
                <button type="button" class="btn btn-sm btn-warning" title="下一个" onclick="setSelect(1)">
                    <i class="ace-icon fa fa-arrow-right "></i>
                    下一个
                </button>
                <button type="button" class="btn btn-sm btn-success" title="计费调整" onclick="hisChange()">
                    <i class="ace-icon glyphicon glyphicon-pencil "></i>
                    计费调整
                </button>
                <button type="button" class="btn btn-sm btn-info" title="图像采集" onclick="takingPicture()">
                    <i class="ace-icon fa fa-picture-o "></i>
                    图像采集
                </button>
                <button type="button" class="btn btn-sm btn-primary" title="图像导入" onclick="importImg()">
                    <i class="ace-icon fa fa-camera "></i>
                    图像导入
                </button>
                <button type="button" class="btn btn-sm btn-info" title="预览" onclick="reportView(1, null, null)">
                    <i class="ace-icon fa fa-file-image-o "></i>
                    预览
                </button>
                <button type="button" class="btn btn-sm btn-warning" title="打印" onclick="reportView(2, null, null)">
                    <i class="ace-icon fa fa-print "></i>
                    打印
                </button>
                <button type="button" class="btn btn-sm btn-success" title="发送" onclick="sendDoctor()">
                    <i class="ace-icon fa fa-paper-plane-o smaller-60"></i>
                    发送
                </button>
                <button type="button" class="btn btn-sm btn-info" title="转送" onclick="csMarage(1)">
                    <i class="ace-icon fa fa-external-link "></i>
                    转送</button>
                <button type="button" class="btn btn-sm btn-primary" title="抄送管理" onclick="csMarage(0)">
                    <i class="ace-icon fa fa-cogs"></i>
                    抄送管理
                </button>
                <button type="button" class="btn btn-sm btn-info" title="发起会诊" onclick="consMarage()">
                    <i class="ace-icon fa fa-puzzle-piece "></i>
                    发起会诊
                </button>
                <button type="button" class="btn btn-sm btn-warning" title="加入随访病例" onclick="addFavorite(1)">
                    <i class="ace-icon fa fa-bookmark "></i>
                    加入随访病例
                </button>
                <button type="button" class="btn btn-sm btn-success" title="加入我的收藏" onclick="addFavorite(0)">
                    <i class="ace-icon fa fa-heart "></i>
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
                    <a href="#" data-action="collapse" onclick="showandhiden(this)">
                        <i class="ace-icon fa fa-chevron-up">隐藏</i>
                    </a>
                </div>
            </div>

            <div class="widget-body" style="display: block;background-color: #F9F9F9">
                <div class="widget-main padding-4 scrollable ace-scroll" style="position: relative;">
                    <div class="scroll-content">
                        <div class="content">
                            <div style="display:inline-block;"><label style="display:inline-block;width:64px;text-align:right;font-size:12px">病种类别：</label>
                                <input type="hidden" id ="user_id" value="${local_userid}">
                                <input type="hidden" id ="reqsts" value="">
                                <select onchange="" id="sampathologyid" style="height:24px;width:150px;">
                                    <%--<option value="" <c:if test="${code != ''}"> selected</c:if>>--请选择--</option>--%>
                                    <%=StringEscapeUtils.unescapeHtml4((String) request.getAttribute("logyids"))%>
                                </select>
                            </div>
                            <div><label style="display:inline-block;width:64px;text-align:right;font-size:12px">切片年月：</label><input type="text" id="samplesectionfrom" class="form_datetime"
                                                            style="width: 150px;height:24px;">~<input type="text" class="form_datetime"
                                                                                         style="height:24px;width: 150px"
                                                                                         id="samplesectionto"></div>
                            <div style="display:block;"><label style="display:inline-block;width:64px;text-align:right;font-size:12px">条形码：</label><input type="text" id="saminspectionidq"
                                                                                   style="height:24px;width: 150px"></div>
                            <div style="display:inline-block;"><label style="display:inline-block;width:64px;text-align:right;font-size:12px;font-size:12px">病理号：</label><input type="text" id="sampathologycodeq" value="${code}"
                                                                                   style="height:24px;width: 150px"></div>
                            <div><label style="display:inline-block;width:64px;text-align:right;font-size:12px">病人名称：</label><input type="text" id="sampatientnameq" style="height:24px;width: 150px">
                            </div>
                            <div style="display:inline;">
                                <div style="text-align: right;margin-top:3px;">
                                    <div style="display:inline;text-align: right;">
                                    <button onclick="query()" style="background:#4190f7!important;padding:0 16px;border-radius:3px;color:white;font-size:12px;border:1px solid #2274e4;height:25px!important;margin-right: 10px"> 查询</button>
                                    </div>
                                        <div style="display:inline;text-align: right;">
                                        <button onclick="receivecs(1)" style="border:1px solid #c2c2c2!important;border-radius:3px;background:#e9e9e9!important;margin:3px 8px 0 0;">抄送接收</button>
                                    </div>
                                    <div style="display:inline;text-align: right;">
                                        <button onclick="receivecs(0)" style="border:1px solid #c2c2c2!important;border-radius:3px;background:#e9e9e9!important;margin-right:3px;">抄送取消</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div style="display: block;">
                <div class="col-xs-12 leftContent">
                    <div style="display:inline;">
                        <ul class="nav nav-tabs">
                            <li class="active">
                                <a href="#" onclick="queryList1('${local_userid}','')" data-toggle="tab">个人</a>
                            </li>
                            <li>
                                <a href="#" onclick="queryList1('','')" data-toggle="tab">全部</a>
                            </li>
                            <li>
                                <a href="#" onclick="queryList1('','0')" data-toggle="tab">已发抄送</a>
                            </li>
                            <li>
                                <a href="#" onclick="queryList1('','1')" data-toggle="tab">待收抄送</a>
                            </li>
                            <li>
                                <a href="#" onclick="queryList1('','2')" data-toggle="tab">待审抄送</a>
                            </li>

                            <%--<li>--%>
                            <%--<a href="#" onclick="query()" data-toggle="tab">全部</a>--%>
                            <%--</li>--%>
                            <%--<li>--%>
                            <%--<a href="#" onclick="queryList(0)" data-toggle="tab">已发抄送</a>--%>
                            <%--</li>--%>
                            <%--<li>--%>
                            <%--<a href="#" onclick="queryList(1)" data-toggle="tab">待收抄送</a>--%>
                            <%--</li>--%>
                            <%--<li>--%>
                            <%--<a href="#" onclick="queryList(2)" data-toggle="tab">待审抄送</a>--%>
                            <%--</li>--%>
                        </ul>
                        <!--<div style="display:inline;padding-right: 5px">
                            <a href="#">个人</a>
                        </div>
                        <div style="display:inline;padding-right: 5px">
                            <a href="#">全部</a>
                        </div>
                        <div style="display:inline;padding-right: 5px"><a href="#" onclick="queryList(0)">已发抄送</a></div>
                        <div style="display:inline;padding-right: 5px"><a href="#" onclick="queryList(1)">待收抄送</a></div>
                        <div style="display:inline;padding-right: 5px"><a href="#" onclick="queryList(2)">待审抄送</a></div>-->
                    </div>
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
                <div style="display:inline;margin-left: 20px"><label>医嘱管理：</label>
                    <select id="yizhugl" style="padding:0 0!important;height: 24px!important;"></select>
                    <button onclick="reqyizhu()" style="background:#4190f7!important;color:white;border-radius:3px;padding:0 16px;font-size:12px;border:1px solid #2274e4;">申请</button>
                </div>
                <div class="widget-toolbar">
                    <a href="#" data-action="collapse" onclick="showandhiden(this)">
                        <i class="ace-icon fa fa-chevron-up">隐藏</i>
                    </a>
                </div>
            </div>
            <div class="widget-body" style="display: block;">
                <div class="widget-main padding-4 scrollable ace-scroll" style="position: relative;background-color: #f9f9f9">

                </div>
                <div id="tabs" style="margin: 0 auto;border: 0px;background-color: #f9f9f9">
                    <ul class="nav nav-tabs" style="background-color: #f9f9f9">
                        <li class="active"><a href="#tabs-1" data-toggle="tab">病人基本信息</a></li>
                        <li><a href="#tabs-2" data-toggle="tab">取材信息</a></li>
                    </ul>
                    <div id="tabs-1" style="border:0;color: #666666;background-color: #f9f9f9">
                        <div style="margin-bottom: 5px">
                            <div style="display: inline-block;width:30%"><label style="width:35%; text-align: right;font-size: 12px">病理号：</label><input type="text"
                                                                    style="width:65%; height: 24px"
                                                                    id="sampathologycode"></div>
                            <input type="hidden" id="sampleid"/>
                            <input type="hidden" id="customerId"/>
                            <input type="hidden" id="pathologyCode"/>
                            <input type="hidden" id="sampathologyid1"/>

                            <div style="display: inline-block;width:33%"><label style="width:35% ; text-align: right;font-size: 12px">条形码：</label><input type="text"
                                                                                                                                                         style="width:65%;height: 24px" id="saminspectionid"></div>
                            <div style="display: inline-block;width:33%"><label style="width:35%; text-align: right;font-size: 12px">年龄：</label><input type="text"
                                                                   style="width:65%;height: 24px"
                                                                   id="sampatientage"></div>
                        </div>
                        <div style="margin-bottom: 5px">
                            <div style="display: inline-block;width:30%"><label style="width:45%;;font-size: 12px">病人姓名：</label><input type="text"
                                                                     style="width:40%;height: 24px"
                                                                     id="sampatientname"><a href="#" onclick="viewDetail()">详细</a>
                            </div>
                            <div style="display: inline-block;width:33%"><label style="width:35%; text-align: right;font-size: 12px">性别：</label><input type="text"
                                                                   style="width:65%;height: 24px"
                                                                   id="sampatientgender"></div>
                            <div style="display: inline-block;width:33%"><label style="width:35%; text-align: right;font-size: 12px">送检医生：</label><input type="text"
                                                                     style="width:65%;height: 24px"
                                                                     id="samsenddoctorid"></div>
                        </div>
                        <div style="margin-bottom: 5px">
                            <div style="display: inline-block;width:30%"><label style="width:35%; text-align: right;font-size: 12px">住院号：</label><input type="text"
                                                                    style="width:65%;height: 24px"
                                                                    id="sampatientnumber"></div>
                            <div style="display: inline-block;width:33%"><label style="width:35%; text-align: right;font-size: 12px">常规收费：</label><input type="text"
                                                                     style="width:65%;height: 24px"
                                                                     id=""></div>
                            <div style="display: inline-block;width:33%"><label style="width:35%; text-align: right;font-size: 12px">送检科室：</label><input type="text"
                                                                     style="width:65%;height: 24px"
                                                                     id="samdeptname">
                            </div>
                        </div>
                        <div style="margin-bottom: 5px">
                            <div style="display: inline-block;width:30%"><label style="width:35%; text-align: right;font-size: 12px">床号：</label><input type="text"
                                                                   style="width:65%;height: 24px"
                                                                   id="sampatientbed">
                            </div>
                            <div style="display: inline-block;width:33%"><label style="width:35%; text-align: right;font-size: 12px">末次月经：</label><input type="text"
                                                                     style="width:65%;height: 24px"
                                                                     id="reqlastmenstruation"></div>
                            <div style="display: inline-block;width:33%"><label style="width:35%; text-align: right;font-size: 12px">送检医院：</label><input type="text"
                                                                     style="width:65%;height: 24px"
                                                                     id="samsendhospital"></div>
                        </div>
                        <div style="margin-bottom: 5px">
                            <div style="display: inline-block;width:30%"><label style="width:35%; text-align: right;font-size: 12px">绝经：</label><input type="checkbox"></div>
                            <div style="display: inline-block;width:33%"><label style="width:35%; text-align: right;font-size: 12px">送检材料：</label><input type="text" id="samsamplename"
                                                                        style="width:65%;height: 24px">
                            </div>
                            <div style="display: inline-block;width:33%"><label style="width:35%; text-align: right;font-size: 12px">临床诊断：</label><input type="text" id="sampatientdignoses"
                                                                        style="width:65%;height: 24px">
                            </div>
                        </div>

                    </div>
                    <div id="tabs-2" style="border:0">
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
            <ul id="tabPanel" class="nav nav-tabs">
                <li id="commonPathology" class="active"><a href="#tabs-3" data-toggle="tab">报告诊断</a></li>
            </ul>
            <div id="tabs-3" style="overflow: auto;border: 0px">
                <form id="diagnosisInfoForm" style="" onsubmit="return false;">
                    <%=StringEscapeUtils.unescapeHtml4((String) request.getAttribute("diagnosisItems")) == null ? "请先配置当前病种相关的病理诊断字段、报告项目以及客户相关数据" : StringEscapeUtils.unescapeHtml4((String) request.getAttribute("diagnosisItems"))%>
                </form>
                <div style="display: none;" id="fullScreen"><button onclick="yjfullScreen()">全屏模式</button></div>
                <div id="yjcell" style="display: none;font-size:12px;width:750px;overflow: auto;padding-left:5px">
                      <form id="radioForm">
                      <table width="86%" border="0" cellspacing="0" style="font-size: 12px">
                         <tr>
                             <td colspan="3" align="left" height="14px"><strong>标本质量：</strong></td>
                             <td align="left">&nbsp;</td>
                             <td colspan="3"><strong>生物性病原体：</strong></td>
                             <td>&nbsp;</td>
                        </tr>
                        <tr>
                            <td  height="14px" colspan="3" align="left"><input type="radio" name="sampleEvaluation" id="c1"  onchange="setCheckBoxStatus()"/>
                                <label for="c1">满意</label>
                            </td>
                            <td width="26%" align="left"><input type="radio" name="sampleEvaluation"  onchange="setCheckBoxStatus()" id="c2"/>
                                <label for="c2">不满意</label></td>
                            <td colspan="2">&nbsp;</td>
                            <td colspan="2"><input type="checkbox" name="notSee1" id="c3" onchange="setCheckboxStatus3()" />
                                <label for="c3">未见</label></td>
                        </tr>
                        <tr>
                            <td colspan="3" height="14px" align="left"><input type="radio" name="sampleWell" class="c1" onchange="setParentCheckboxStatus1()" id="c4" />
                                <label for="c4">见宫颈管/移行区成份</label></td>
                            <td colspan="2" align="left"><input type="radio" onchange="setParentCheckboxStatus2()" name="sampleNotGood" id="c6" class="c2" />
                                <label for="c6">上皮细胞数量偏低</label></td>
                            <td width="4%">&nbsp;</td>
                            <td width="20%"><input type="checkbox" name="c9" id="c9" class="g33" onchange="setCheckboxStatus3()" />
                                <label for="c9">提示HPV感染</label></td>
                            <td width="12%"><input type="checkbox" name="c14" id="c14" class="g33" onchange="setCheckboxStatus3()"  />
                                <label for="c14">放线菌</label></td>
                        </tr>
                        <tr>
                            <td colspan="3" height="14px" align="left" width="20%"><input type="radio" name="sampleWell" class="c1" onchange="setParentCheckboxStatus1()" id="c5" />
                                <label for="c5">未见宫颈管/移行区成份</label></td>
                            <td colspan="3" align="left"><input type="radio" name="sampleNotGood" onchange="setParentCheckboxStatus2()" id="c7" class="c2"  />
                                <label for="c7">炎性渗出物遮盖超过75%的鳞状细胞</label></td>
                            <td><input type="checkbox" name="c10" id="c10" class="g33" onchange="setCheckboxStatus3()"  />
                                <label for="c10">提示疱疹病毒感染</label></td>
                            <td><input type="checkbox" name="c13" id="c13" class="g33"  onchange="setCheckboxStatus3()" />
                                <label for="c13">线索细胞</label></td>
                        </tr>
                        <tr>
                            <td width="9%" height="14px">&nbsp;</td>
                            <td width="8%">&nbsp;</td>
                            <td width="12%">&nbsp;</td>
                            <td colspan="3"><input type="radio" name="sampleNotGood" onchange="setParentCheckboxStatus2()" id="c8" class="c2"  />
                                <label for="c8">血液遮盖超过75%的鳞状细胞</label></td>
                            <td><label for="c11">
                                <input type="checkbox" name="c12" id="c12" class="g33"  onchange="setCheckboxStatus3()" />
                                真菌(念珠菌等)</label></td>
                            <td><input type="checkbox" name="c11" id="c11" class="g33"  onchange="setCheckboxStatus3()" />
                                <label for="c11">滴虫</label></td>
                        </tr>
                      </table>
                      <table width="100%" border="0" cellspacing="0">
                        <tr>
                            <td colspan="5" height="14px"><label for="c15"><strong>上皮细胞异常：</strong></label>
                                <input type="checkbox" name="c15" id="c15"  onchange="changeCellSee()" />
                                <label for="c15">未见</label></td>
                            <td width="1%">&nbsp;</td>
                            <td colspan="2">&nbsp;</td>
                            <td width="7%">&nbsp;</td>
                            <td colspan="4"><strong>非肿瘤性所见：</strong></td>
                        </tr>
                        <tr>
                            <td width="10%" height="14px">腺细胞所见：</td>
                            <td colspan="2">
                                <input type="radio" name="xcell" id="c16" class="g1"/>
                                <label for="c16">不典型的</label>
                            </td>
                            <td width="3%">&nbsp;</td>
                            <td colspan="2">鳞状细胞：</td>
                            <td colspan="5"><input type="radio" name="xcell4" onclick="makeResult();" id="c28" class="g5" />
                                <label for="c28">不典型鳞状上皮细胞(ASC)</label></td>
                            <td colspan="2"><input type="checkbox" name="c34" onclick="makeResult();" id="c34" />
                                <label for="c34">反应性的细胞变化</label></td>
                        </tr>
                        <tr>
                            <td height="14px">&nbsp;</td>
                            <td width="2%">&nbsp;</td>
                            <td colspan="4"><input type="radio" name="xcell1" onchange="setStatus1()" id="c17" class="g111" />
                                <label for="c17">子宫颈内膜细胞，非特异</label></td>
                            <td width="2%">&nbsp;</td>
                            <td colspan="3"><input type="radio" onclick="makeResult();" name="xcell5" id="c29" class="g555" />
                                <label for="c29">属意义不明者(ASC-US)</label></td>
                            <td width="2%">&nbsp;</td>
                            <td width="6%">&nbsp;</td>
                            <td width="23%"><input type="checkbox" onclick="makeResult();" name="c35" id="c35" />
                                <label for="c35">与炎症有关</label>
                                <label for="s1"></label>
                                <select name="s1" id="s1">
                                    <option value="轻度">轻度</option>
                                    <option value="中度">中度</option>
                                    <option value="重度">重度</option>
                                </select></td>
                        </tr>
                        <tr>
                            <td height="14px">&nbsp;</td>
                            <td>&nbsp;</td>
                            <td colspan="3"><input type="radio" name="xcell1" id="c18" onchange="setStatus1()" class="g111" />
                                <label for="c18">子宫内膜细胞，非特异</label></td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td colspan="4"><input type="radio"  onclick="makeResult();" name="xcell5" id="c30" class="g555" />
                                <label for="c30">不能排除高级别鳞状上皮内病变(ASC-H)</label></td>
                            <td>&nbsp;</td>
                            <td><input type="checkbox" name="c36"  onclick="makeResult();" id="c36" />
                                <label for="c36">与放射有关</label></td>
                        </tr>
                        <tr>
                            <td height="14px">&nbsp;</td>
                            <td>&nbsp;</td>
                            <td colspan="3"><input type="radio" name="xcell1" id="c19" onchange="setStatus1()" class="g111" />
                                <label for="c19">腺细胞，非特异</label></td>
                            <td>&nbsp;</td>
                            <td colspan="5"><input type="radio" onclick="makeResult();" name="xcell4" id="c31" class="g6" />
                                <label for="c31">低级别鳞状上皮内病变(L-SIL)</label></td>
                            <td>&nbsp;</td>
                            <td><input type="checkbox" onclick="makeResult();" name="c37" id="c37" />
                                <label for="c37">与宫内节育器（IUD）有关</label></td>
                        </tr>
                        <tr>
                            <td>&nbsp;</td>
                            <td colspan="4"><input type="radio" onclick="makeResult();" name="xcell" id="c20" class="g2" />
                                <label for="c20">不典型的</label></td>
                            <td>&nbsp;</td>
                            <td colspan="4">（包括HPV/轻度异型性CIN I）</td>
                            <td>&nbsp;</td>
                            <td colspan="2"><input type="checkbox" onclick="makeResult();" name="c38" id="c38" />
                                <label for="c38">萎缩</label></td>
                        </tr>
                        <tr>
                            <td height="14px">&nbsp;</td>
                            <td>&nbsp;</td>
                            <td colspan="4"><input type="radio" name="xcell2" id="c21" onchange="setStatus2()" class="g222" />
                                <label for="c21">子宫颈内膜细胞,偏向肿瘤性</label></td>
                            <td colspan="4"><input type="radio" onclick="makeResult();" name="xcell4" id="c32" class="g7" />
                                <label for="c32">高级别鳞状上皮内病变(H-SIL)</label></td>
                            <td>&nbsp;</td>
                            <td colspan="2">其它：
                                <input type="checkbox" onclick="makeResult();" name="c39" id="c39" />
                                <label for="c39">子宫内膜细胞出现在≥40岁妇女中（见注释）</label></td>
                        </tr>
                        <tr>
                            <td height="14px">&nbsp;</td>
                            <td>&nbsp;</td>
                            <td colspan="3"><input type="radio" name="xcell2" id="c22" onchange="setStatus2()" class="g222" />
                                <label for="c22">腺细胞,偏向肿瘤性</label></td>
                            <td>&nbsp;</td>
                            <td colspan="7">（包括中度和重度异型性/CIS/CINⅡ和CIN III）</td>
                        </tr>
                        <tr>
                            <td height="14px">&nbsp;</td>
                            <td colspan="4">
                                <input type="radio" name="xcell" id="c23" onchange="setStatus3()" class="g3" />
                                <label for="c23">子宫颈管原位癌</label>
                            </td>
                            <td>&nbsp;</td>
                            <td colspan="2"><input type="radio" name="xcell4" onclick="makeResult();" id="c33" class="g8" />
                                <label for="c33">鳞状细胞癌</label></td>
                            <td>&nbsp;</td>
                            <td width="6%">&nbsp;</td>
                            <td>&nbsp;</td>
                            <td colspan="2">&nbsp;</td>
                        </tr>
                        <tr>
                            <td height="14px">&nbsp;</td>
                            <td colspan="4"><input type="radio" onclick="makeResult();" name="xcell" id="c24" class="g4" />
                                <label for="c24">腺癌</label></td>
                            <td>&nbsp;</td>
                            <td colspan="2">&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td colspan="2">&nbsp;</td>
                        </tr>
                        <tr>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td colspan="3"><input type="radio" name="xcell3" id="c25" onchange="setStatus4()" class="g444" />
                                <label for="c25">子宫内膜腺癌</label></td>
                            <td>&nbsp;</td>
                            <td colspan="2">&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td colspan="2">&nbsp;</td>
                        </tr>
                        <tr>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td colspan="3"><input type="radio" name="xcell3" id="c26" onchange="setStatus4()" class="g444" />
                                <label for="c26">子宫颈内膜腺癌</label></td>
                            <td>&nbsp;</td>
                            <td colspan="2">&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td colspan="2">&nbsp;</td>
                        </tr>
                        <tr>
                            <td height="14px">&nbsp;</td>
                            <td>&nbsp;</td>
                            <td colspan="3"><input type="radio" name="xcell3" id="c27" onchange="setStatus4()" class="g444" />
                                <label for="c27">子宫外腺癌</label></td>
                            <td>&nbsp;</td>
                            <td colspan="2">&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td colspan="2">&nbsp;</td>
                        </tr>
                    </table>
                    <div> <label for="textarea1">判读意见/结果：</label></div>
                    <div>
                        <textarea name="textarea1" id="textarea1" hiddenValue="" placeholder="判读意见/结果" cols="80" rows="2"></textarea>
                        <div>
                            <input type="checkbox" name="c40" id="c40" onchange="setBtEnable()" />
                            <label for="c40">Cervista高危型HPV DNA检测结果</label>
                            <input type="button" disabled name="bt1" id="bt1" value="阴性" onclick="$('#textarea2').val('阴性')" class="btclass"/>
                            <input type="button" disabled name="bt2" id="bt2" value="A5/A6阳性"  class="btclass" onclick="setHPVResult('A5/A6(51,56,66)阳性')"/>
                            <input type="button" disabled name="bt3" id="bt3" value="A7阳性"  class="btclass" onclick="setHPVResult('A7(18,39,45,59,68)阳性')"/>
                            <input type="button" disabled name="bt4" id="bt4" value="A9阳性"  class="btclass" onclick="setHPVResult('A9(16,31,33,35,52,58)阳性')"/>
                        </div>
                        <div>
                            <textarea name="textarea2" id="textarea2" hiddenValue="" placeholder="Cervista高危型HPV DNA检测结果" cols="80" rows="2"></textarea>
                        </div>
                        <div><label for="textarea3">附注/建议：</label></div>
                        <div>
                            <input type="hidden" id="textarea0" hiddenValue="">
                            <textarea name="textarea3" id="textarea3" hiddenValue="" placeholder="附注/建议" cols="80" rows="2"></textarea>
                        </div>
                    </div>
                    </form>
                </div>
                <div style="alignment: center">
                    <button onclick="saveDiagnosisInfo()">保存</button>
                </div>

            </div>
            <div id="tabs-MYZH" style="display: none">
                <div style="padding-bottom:2px;"><button onclick="saveResult('MYZHItem')">保存结果</button></div>
                <div><table id="MYZHItem"></table></div>
            </div>
            <div id="tabs-TSRS"  style="display: none">
                <div style="padding-bottom:2px;"><button onclick="saveResult('TSRSItem')">保存结果</button></div>
                <div><table id="TSRSItem"></table></div>
            </div>
            <div id="tabs-FZBL" style="display: none">
                    <div style="padding-bottom:2px;"><button onclick="saveResult('FZBLItem')">保存结果</button></div>
                    <div><table id="FZBLItem"></table></div>
            </div>
            <div>
                <hr style="border: 1px solid #49a7fe">
                <div>报告日期：<input type="text"  id="samreportedtime" onchange="doctorSign(0)" readonly
                                 name="samreportedtime">
                    <button onclick="delayReport()">延迟报告</button>
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
                                                        style="width: 60px;border-width: 0px 0px 1px 0px" disabled>住院号：<input
                        id="patientZyh" style="width: 120px;border-width: 0px 0px 1px 0px" disabled></div>
                <div style="padding-top:5px;">病人姓名：<input id="patientName"
                                                          style="width: 120px;border-width: 0px 0px 1px 0px" disabled>年龄：<input
                        id="patientAge" style="width: 60px;border-width: 0px 0px 1px 0px" disabled>床号：<input id="patientBed"
                                                                                                    style="width: 120px;border-width: 0px 0px 1px 0px" disabled>
                </div>
                <div style="padding-top:5px;">临床诊断：<input id="patientDiagnosisNote"
                                                          style="width: 300px;border-width: 0px 0px 1px 0px" disabled></div>
            </div>
            <div style="width: 100%;height: 20px;font-weight:bold;">特殊检查</div>
            <div style="width: 100%;height: 23%;">
                <div style="padding-top:5px;">医嘱号：<input id="ordercode" value="" disabled
                                                         style="width: 120px;border-width: 0px 0px 1px 0px">检查类型：
                    <input name="reqType" id="reqType" disabled style="width: 120px;border-width: 0px 0px 1px 0px">
                </div>
                <div style="padding-top:5px;">
                    源病理号：<input id="yblNo" disabled style="width: 120px;border-width: 0px 0px 1px 0px">
                    申请医生：
                    <input id="reqDoctor" style="width: 120px;border-width: 0px 0px 1px 0px" value="${local_username}">
                    <input type="hidden" id="local_userid" value="${local_userid}"/><!--当前登录用户id-->
                    <input type="hidden" id="local_username" value="${local_username}"/><!--当前登录用户名称-->
                    <input type="hidden" id="reqDoctorId" value="${local_userid}"/>
                </div>
                <div style="padding-top:5px;">
                    源条形码：<input id="ytxm" readonly style="width: 120px;border-width: 0px 0px 1px 0px">
                    申请日期：<input id="reqDate" class="" style="width: 120px;border-width: 0px 0px 1px 0px">
                </div>
                <input id="whitePieceNo" type="hidden" style="width: 120px;border-width: 0px 0px 1px 0px">

            </div>
            <div style="display: none" id="addMaterial">
                <div class="widget-main no-padding">
                    <h5><strong style="font-size: 14px;">&nbsp;材块列表</strong>
                        <button type="button" style="border-radius:3px;border:1px solid #2274E4;background-color: #108CCF;height: 25px;" id="addrow1" onclick="addRow()">
                            <span style="color: white">追加行</span>
                        </button>
                        <button type="button"  style="border-radius:3px;border:1px solid #2274E4;background-color: #108CCF;height: 25px;" onclick="delRow()">
                            <span style="color: white">删除行</span>
                        </button>
                    </h5>
                </div>
                <div class="widget-main no-padding">
                    <table id="new1" class="table-striped">
                    </table>
                </div>
            </div>
            <div id="leftBottomContainer" style="width: 100%;height: 25px;font-weight:bold;">项目一览
                <button onclick="removeItems()">删除</button>
                蜡块选择<select id="lkxz" onchange="getWhitePiece()"></select>
            </div>
            <div style="width: 100%;padding-top:5px;display: none" id="itemListContainer">
                <table id="itemList"></table>
            </div>
            <div style="width: 100%;" id="itemSummary">
                <%--癌基因蛋白：3 项，199元；单克隆抗体：3 项，199元；合计：398元--%>
            </div>
            <div style="width: 100%;padding-top:5px;display: none" id="pieceListContainer">
                <table id="pieceList"></table>
            </div>
        </div>
        <div style="float:right;width: 40%;height: 100%;padding-left:10px;padding-right: 10px;display: inline">
            <div style="width: 100%;height: 20px;;font-weight:bold;" id="whitePieceTitle">白片信息</div>
            <div style="width: 100%;padding-top:5px;" id="lakuaiListContainer">
                <table id="lkItemList"></table>
            </div>
            <div style="width: 100%;height: 25px;display: none" id="packageContainer">
                项目套餐：<select id="itemPackage" onchange="getItemInfo(this.value)"></select>
                <button onclick="appendAll()">全部追加</button>
            </div>
            <div id="rightListContainer" style="display: none">
                <div style="width: 100%;height: 25px;">项目名称：<input id="itemName"></div>
                <div style="width: 100%;height: 35%;">
                    <table id="ckItemList"></table>
                </div>
            </div>
        </div>
    </div>

    <div id="myFavorite" style="display: none">
        <div style="padding-left: 5px;padding-top:5px"><label for="favtitle">收藏标题：</label><input type="text" size="35" id="favtitle"></div>
        <div style="padding-left: 5px;padding-top:5px">
            <label for="favdescription">收藏描述：</label>
            <textarea type="text" cols="40" rows="6" id="favdescription"></textarea>
        </div>
    </div>
</div>
</body>
</html>
