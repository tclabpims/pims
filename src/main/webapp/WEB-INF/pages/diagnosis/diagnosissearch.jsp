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
    <script type="text/javascript" src="<c:url value='/scripts/pspathology/diagnosis1.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/pspathology/yjxbx.js'/>"></script>

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

        #pager_left {
            display: none;
        }
        input{
            padding-top: 0px!important;
            padding-bottom: 0px!important;
        }
    </style>

</head>
<SCRIPT LANGUAGE="JavaScript">

    var OsObject = navigator.userAgent;

</SCRIPT>
<body  style="font-family:'Microsoft YaHei',微软雅黑,'MicrosoftJhengHei'!important;">
<div class="row" id="toolbar">
    <div id="mainTable" class="col-xs-12">
        <div style="padding-top: 5px;">
            <div class="col-xs-12">
                <button type="button" class="btn btn-sm btn-info" title="预览" onclick="reportView(1, null, null)">
                    <i class="ace-icon fa fa-file-image-o "></i>
                    预览
                </button>
                <button type="button" class="btn btn-sm btn-warning" title="打印" onclick="printList()">
                    <i class="ace-icon fa fa-print "></i>
                    打印
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
                        <div class="content" id="searchcotent">
                            <div style="display:inline-block;"><label style="display:inline-block;width:64px;text-align:right;font-size:12px">病种类别：</label>
                                <input type="hidden" id ="user_id" value="${local_userid}">
                                <input type="hidden" id ="reqsts" value="">
                                <select onchange="" id="sampathologyid" style="height:24px;width:150px;">
                                    <%=StringEscapeUtils.unescapeHtml4((String) request.getAttribute("logyids"))%>
                                </select>
                            </div>
                            <div><label style="display:inline-block;width:64px;text-align:right;font-size:12px">登记年月：</label><input type="text" id="samplesectionfrom" class="form_datetime" value="${sevenday}"
                                                            style="width: 150px;height:24px;">~<input type="text" class="form_datetime" value="${receivetime}"
                                                                                         style="height:24px;width: 150px"
                                                                                         id="samplesectionto"></div>
                            <div style="display:block;"><label style="display:inline-block;width:64px;text-align:right;font-size:12px">病理状态：</label>
                                <%--<input type="text" id="saminspectionidq" style="height:24px;width: 150px">--%>
                                <select id="saminspectionidq" style="height:24px;width:150px;">
                                    <option value="5">已签发</option>
                                    <option value="4">已打印</option>
                                </select>
                            </div>
                            <div style="display:inline-block;"><label style="display:inline-block;width:64px;text-align:right;font-size:12px;font-size:12px">病理编号：</label><input type="text" id="sampathologycodeq" value="${code}"
                                                                                   style="height:24px;width: 150px"></div>
                            <div><label style="display:inline-block;width:64px;text-align:right;font-size:12px">患者姓名：</label><input type="text" id="sampatientnameq" style="height:24px;width: 150px">
                            </div>
                            <div style="display:inline;">
                                <div style="text-align: right;margin-top:3px;">
                                    <div style="display:inline;text-align: right;">
                                    <button onclick="query()" style="background:#4190f7!important;padding:0 16px;border-radius:3px;color:white;font-size:12px;border:1px solid #2274e4;height:25px!important;margin-right: 10px"> 查询</button>
                                    </div>
                                </div>
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
                                                                    style="width:65%; height: 24px" disabled
                                                                    id="sampathologycode"></div>
                            <input type="hidden" id="sampleid"/>
                            <input type="hidden" id="customerId"/>
                            <input type="hidden" id="pathologyCode"/>
                            <input type="hidden" id="sampathologyid1"/>

                            <div style="display: inline-block;width:33%"><label style="width:35% ; text-align: right;font-size: 12px">条形码：</label><input type="text" disabled
                                                                                                                                                         style="width:65%;height: 24px" id="saminspectionid"></div>
                            <div style="display: inline-block;width:33%"><label style="width:35%; text-align: right;font-size: 12px">年龄：</label><input type="text" disabled
                                                                   style="width:65%;height: 24px"
                                                                   id="sampatientage"></div>
                        </div>
                        <div style="margin-bottom: 5px">
                            <input type="hidden" id="sampatientid">
                            <div style="display: inline-block;width:30%"><label style="width:35%;;font-size: 12px;text-align:right">病人姓名：</label><input type="text" disabled
                                                                     style="width:40%;height: 24px"
                                                                     id="sampatientname"><a href="#" onclick="viewDetail()"><button style="background: #4190f7!important;border-radius: 3px;color:#fff;border:1px solid #2274e4;margin-left: 3px;height: 25px">详细</button></a>
                            </div>
                            <div style="display: inline-block;width:33%"><label style="width:35%; text-align: right;font-size: 12px">性别：</label><input type="text" disabled
                                                                   style="width:65%;height: 24px"
                                                                   id="sampatientgender"></div>
                            <div style="display: inline-block;width:33%"><label style="width:35%; text-align: right;font-size: 12px">送检医生：</label><input type="text" disabled
                                                                     style="width:65%;height: 24px"
                                                                     id="samsenddoctorid"></div>
                        </div>
                        <div style="margin-bottom: 5px">
                            <div style="display: inline-block;width:30%"><label style="width:35%; text-align: right;font-size: 12px">住院号：</label><input type="text" disabled
                                                                    style="width:65%;height: 24px"
                                                                    id="sampatientnumber"></div>
                            <div style="display: inline-block;width:33%"><label style="width:35%; text-align: right;font-size: 12px">常规收费：</label><input type="text" disabled
                                                                     style="width:65%;height: 24px"
                                                                     id=""></div>
                            <div style="display: inline-block;width:33%"><label style="width:35%; text-align: right;font-size: 12px">送检科室：</label><input type="text" disabled
                                                                     style="width:65%;height: 24px"
                                                                     id="samdeptname">
                            </div>
                        </div>
                        <div style="margin-bottom: 5px">
                            <div style="display: inline-block;width:30%"><label style="width:35%; text-align: right;font-size: 12px">床号：</label><input type="text" disabled
                                                                   style="width:65%;height: 24px"
                                                                   id="sampatientbed">
                            </div>
                            <div style="display: inline-block;width:33%"><label style="width:35%; text-align: right;font-size: 12px">末次月经：</label><input type="text" disabled
                                                                     style="width:65%;height: 24px"
                                                                     id="reqlastmenstruation"></div>
                            <div style="display: inline-block;width:33%"><label style="width:35%; text-align: right;font-size: 12px">送检医院：</label><input type="text" disabled
                                                                     style="width:65%;height: 24px"
                                                                     id="samsendhospital"></div>
                        </div>
                        <div style="margin-bottom: 5px">
                            <div style="display: inline-block;width:30%"><label style="width:35%; text-align: right;font-size: 12px">绝经：</label><input type="checkbox" disabled></div>
                            <div style="display: inline-block;width:33%"><label style="width:35%; text-align: right;font-size: 12px">送检材料：</label><input type="text" id="samsamplename" disabled
                                                                        style="width:65%;height: 24px">
                            </div>
                            <div style="display: inline-block;width:33%"><label style="width:35%; text-align: right;font-size: 12px">临床诊断：</label><input type="text" id="sampatientdignoses" disabled
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
            </div>
            <div>
                <hr style="border: 1px solid #49a7fe">
                <div>签发日期：<input type="text"  id="samreportedtime" onchange="doctorSign(0)" readonly
                                 name="samreportedtime">
                </div>
                <div>初查医生：<input type="text" readonly id="saminitiallyusername" name="saminitiallyusername">
                </div>
                <div>审核医生：<input type="text" readonly id="samauditer" name="samauditer">
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
                    <a href="#" data-action="collapse" onclick="showandhiden(this)">
                        <i class="ace-icon fa fa-chevron-up">隐藏</i>
                    </a>
                </div>
            </div>
        </div>
        <div id="imgContainer"></div>
    </div>
    <div id="reportTemplateList" style="display:none;alignment: center">
        <div class="mainContent" style="text-align:center;">
            <select id="reportTemplateSelect">
            </select>
        </div>
    </div>
</div>
</body>
</html>
