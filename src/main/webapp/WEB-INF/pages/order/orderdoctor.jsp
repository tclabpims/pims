<%@ page import="org.apache.commons.lang3.StringEscapeUtils" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<html>
<head>
    <title>医嘱处理管理-医师</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0"/>
    <link rel="stylesheet" type="text/css" href="<c:url value='/styles/ui.jqgrid.css'/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value='/styles/jquery-ui.css'/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value='/styles/bootstrap-datetimepicker.min.css'/>"/>
    <script type="text/javascript" src="<c:url value="/scripts/ace.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/ace-elements.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/bootstrap-tag.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/jquery-2.1.4.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/jquery-ui.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/bootstrap.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/bootstrap-datetimepicker.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/i18n/grid.locale-cn.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/jquery.jqGrid.js"/>"></script>
    <script src="<c:url value='/scripts/LodopFuncs.js'/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/validform/Validform.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/layer/layer.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/pspathology/orderdoctor.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/consultation/cons1.js"/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/pathologysample/fee.js'/>"></script>
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
        .ui-timepicker-div .ui-widget-header { margin-bottom: 8px; }
        .ui-timepicker-div dl { text-align: left; }
        .ui-timepicker-div dl dt { height: 25px; margin-bottom: -25px; }
        .ui-timepicker-div dl dd { margin: 0 10px 10px 65px; }
        .ui-timepicker-div td { font-size: 90%; }
        .ui-jqgrid-sortable{text-align: center;}
        #maincontent .ui-jqgrid-htable{
            width:493px!important;
        }
        #maincontent .ui-jqgrid-btable{
            width:493px!important;
        }
    </style>

</head>
<SCRIPT LANGUAGE="JavaScript">
    var OsObject = navigator.userAgent;
</SCRIPT>
<body>
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
<div class="row" id="toolbar">
    <div  class="row" id="userGrid" style="display: none;">
        <div class="col-xs-12">
            <table id="sectionList3"></table>
            <div id="pager3"></div>
        </div>
    </div>
    <div id="mainTable" class="col-xs-12">
        <div style="padding-top: 5px;">
            <div class="col-xs-12">
                <button type="button" class="btn btn-sm btn-primary" title="上一个" onclick="setSelect(0)">
                    <i class="ace-icon fa fa-arrow-left"></i>
                    上一个
                </button>
                <button type="button" class="btn btn-sm btn-info" title="下一个" onclick="setSelect(1)">
                    <i class="ace-icon fa fa-arrow-right"></i>
                    下一个
                </button>
                <button type="button" class="btn btn-sm btn-warning" title="取消医嘱" id="btCancel" onclick="updateState(4)">
                    <i class="ace-icon glyphicon glyphicon-remove"></i>
                    取消医嘱
                </button>
                <button type="button" class="btn btn-sm btn-success" title="保存" id="btFinish" onclick="saveOrder()">
                    <i class="ace-icon fa fa-floppy-o"></i>
                    保存
                </button>
                <button type="button" class="btn btn-sm btn-info" title="计费调整" id="btChagreAdjust" onclick="hisChange()">
                    <i class="ace-icon glyphicon glyphicon-pencil"></i>
                    计费调整
                </button>
                <button type="button" class="btn btn-sm btn-primary" title="签收" id="btReceive" onclick="updateState(3)">
                    <i class="ace-icon fa fa-folder-open-o"></i>
                    签收
                </button>
            </div>
        </div>
    </div>
</div>

<div class="row" id="maincontent">
    <div class="col-xs-4">
        <div class="widget-box widget-color-green ui-sortable-handle">
            <div class="widget-header">
                <h6 class="widget-title">医嘱列表</h6>
                <%--<div class="widget-toolbar">--%>
                    <%--<a href="#" data-action="collapse">--%>
                        <%--<i class="ace-icon fa fa-chevron-up">隐藏</i>--%>
                    <%--</a>--%>
                <%--</div>--%>
            </div>

            <div class="widget-body" style="display: block;">
                <div class="widget-main padding-4 scrollable ace-scroll" style="position: relative;" id="yizhuleixin">
                    <div class="scroll-content">
                        <div class="content">
                            <div style="display:inline;"><label>医嘱类型：</label>
                                <select id="q_specialCheck" style="height:24px">
                                    <option value="">--请选择--</option>
                                </select>
                            </div>
                            <div><label>申请年月：</label><input type="text" id="q_startDate"
                                                            style="width: 100px;height:24px" class="inputstyle">-<input type="text"
                                                                                         style="width: 100px;height:24px"
                                                                                         id="q_endDate"></div>
                            <div style="display:inline;"><label>源病理号：</label><input type="text" id="q_pathologyCode"
                                                                                    style="width:100px;height:24px"></div>
                            <div style="display:inline;"><label>病人名称：</label><input type="text" id="q_patientName" style="width: 100px;height:24px">
                                <button onclick="query()" style="border-radius: 3px;border:1px solid #2274E4;background-color: #4190f7;color: #ffffff;padding:0 16px;"> 查询</button></div>
                            <div style="display:block;">
                                <ul class="nav nav-tabs">
                                    <li class="active">
                                        <a href="#" data-toggle="tab" onclick="query(-1)">全部</a>
                                    </li>
                                    <li>
                                        <a href="#" data-toggle="tab" onclick="query(0)">已申请</a>
                                    </li>
                                    <li>
                                        <a href="#" data-toggle="tab" onclick="query(1)">已接收</a>
                                    </li>
                                    <li>
                                        <a href="#" data-toggle="tab" onclick="query(2)">已完成</a>
                                    </li>
                                    <li>
                                        <a href="#" data-toggle="tab" onclick="query(3)">已签收</a>
                                    </li>
                                </ul>
                                <!--<div style="display:inline;padding-right: 5px">
                                    <a href="#" onclick="query(-1)">全部</a>
                                </div>
                                <div style="display:inline;padding-right: 5px"><a href="#" onclick="query(0)">已申请</a></div>
                                <div style="display:inline;padding-right: 5px"><a href="#" onclick="query(1)">已接收</a></div>
                                <div style="display:inline;padding-right: 5px"><a href="#" onclick="query(2)">已完成</a></div>
                                <div style="display:inline;padding-right: 5px"><a href="#" onclick="query(3)">已签收</a></div>-->
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
                <h6 class="widget-title">医嘱内容</h6>
                <%--<div class="widget-toolbar">--%>
                    <%--<a href="#" data-action="collapse">--%>
                        <%--<i class="ace-icon fa fa-chevron-up">隐藏</i>--%>
                    <%--</a>--%>
                <%--</div>--%>
            </div>
            <div style="display: block;">
                <div id="tabs" style="margin: 0 auto;">
                    <ul class="nav nav-tabs">
                        <li class="active"><a href="#tabs-1" data-toggle="tab">病人基本信息</a></li>
                        <li><a href="#tabs-2"  data-toggle="tab">取材信息</a></li>
                    </ul>
                    <div id="tabs-1">
                        <div style="margin-bottom: 10px">
                            <div style="display: inline">&nbsp;&nbsp;&nbsp;&nbsp;病理号：<input type="text" style="width:120px;border-width: 0px 0px 1px 0px;height: 24px;" disabled
                                                                    id="sampathologycode"></div>
                            <input type="hidden" id="sampleid" style="height: 24px"/>
                            <input type="hidden" id="customerId" style="height: 24px"/>
                            <input type="hidden" id="pathologyCode" style="height: 24px"/>
                            <div style="display: inline">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;条形码：<input type="text" style="width:120px;border-width: 0px 0px 1px 0px;height: 24px;"disabled
                                                                    id="saminspectionid"></div>
                            <div style="display: inline">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年龄：<input type="text" style="width:120px;border-width: 0px 0px 1px 0px;height: 24px;"disabled
                                                                   id="sampatientage"></div>
                        </div>
                        <div style="margin-bottom: 10px">
                            <div style="display: inline">病人姓名：<input type="text" style="width:120px;border-width: 0px 0px 1px 0px;height: 24px;"disabled
                                                                     id="sampatientname"><a href="#">详细</a>
                            </div>
                            <div style="display: inline">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;性别：<input type="text" style="width:120px;border-width: 0px 0px 1px 0px;height: 24px;"disabled
                                                                   id="sampatientgender"></div>
                            <div style="display: inline">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;送检医生：<input type="text" style="width:120px;border-width: 0px 0px 1px 0px;height: 24px;"disabled
                                                                     id="samsenddoctorid"></div>
                        </div>
                        <div style="margin-bottom: 10px">
                            <div style="display: inline">&nbsp;&nbsp;&nbsp;&nbsp;住院号：<input type="text" style="width:120px;border-width: 0px 0px 1px 0px;height: 24px;"disabled
                                                                    id="sampatientnumber"></div>
                            <div style="display: inline">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;常规收费：<input type="text" style="width:120px;border-width: 0px 0px 1px 0px;height: 24px;"disabled id=""></div>
                            <div style="display: inline">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;送检科室：<input type="text" style="width:120px;border-width: 0px 0px 1px 0px;height: 24px;"disabled id="samdeptname">
                            </div>
                        </div>
                        <div style="margin-bottom: 10px">
                            <div style="display: inline">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;床号：<input type="text" style="width:120px;border-width: 0px 0px 1px 0px;height: 24px;"disabled id="sampatientbed">
                            </div>
                            <div style="display: inline">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;末次月经：<input type="text" style="width:120px;border-width: 0px 0px 1px 0px;height: 24px;"disabled
                                                                     id="reqlastmenstruation"></div>
                            <div style="display: inline">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;送检医院：<input type="text" style="width:120px;border-width: 0px 0px 1px 0px;height: 24px;"disabled
                                                                     id="samsendhospital"></div>
                        </div>
                        <div style="margin-bottom: 10px">
                            <div style="display: inline">绝经：<input type="checkbox"></div>
                            <div style="display: inline">送检材料：<input type="text" id="samsamplename" style="border-width: 0px 0px 1px 0px"disabled></input></div>
                            <div style="display: inline">临床诊断：<input type="text" id="sampatientdignoses" style="border-width: 0px 0px 1px 0px"disabled></input>
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
        <div><h6>申请信息</h6></div>
        <div>
            <div style="display: inline;float: left">病理号：<input id="chipathologycode" style="border-width: 0px 0px 1px 0px" disabled></div>
            <div style="display: inline;">医嘱类型：<input id="testItemChName" style="border-width: 0px 0px 1px 0px"disabled></div>
            <div style="display: inline;float: right">申请时间：<input id="chireqtime" style="border-width: 0px 0px 1px 0px"disabled></div>
        </div>
        <div>
            <div style="display: inline;float: left">医嘱号：<input id="chiordercode" style="border-width: 0px 0px 1px 0px"disabled></div>
            <div style="display: inline;">申请医生：<input id="chirequsername" style="border-width: 0px 0px 1px 0px"disabled></div>
            <div style="display: inline;float: right"><font color="red">总切片数</font>：<input id="chinullslidenum" style="border-width: 0px 0px 1px 0px"disabled></div>
        </div>
        <div>
            <div style="float: left">
                <div id="tabs99">
                    <ul class="nav nav-tabs">
                        <li class="active"><a href="#T1" data-toggle="tab">项目一览</a></li>
                        <li><a href="#T2" data-toggle="tab">医嘱开单费用列表</a></li>
                    </ul>
                    <div>
                        <div id="T1">
                            <div style="height:210px" id="checkItemListContainer">
                                <div><table id="checkItemList"></table></div>
                            </div>
                            <div id="checkItemCalContainer" style="height: 26px">
                                <div>检测项目合计：<span id="itemCal"></span></div>
                            </div>
                        </div>
                        <div  id="T2">
                            <div style=""  id="chargeItemListContainer">
                                <div style="height:210px"><table id="childChargeList"></table></div>
                            </div>
                        </div>
                    </div>
                </div>
                <div style="height:200px;display: none;" id="materialPieceListContainer">
                    <div><table id="materialPieceList"></table></div>
                </div>
                <div style="height:200px;display: none;" id="pieceListContainer">
                    <div><table id="new1"></table></div>
                </div>
            </div>
        </div>
    </div>
    <div id="takingPicture" class="col-xs-2">
        <div class="widget-box widget-color-green ui-sortable-handle">
            <div class="widget-header">
                <h6 class="widget-title">申请项目</h6>
                <%--<div class="widget-toolbar">--%>
                    <%--<a href="#" data-action="collapse">--%>
                        <%--<i class="ace-icon fa fa-chevron-up">隐藏</i>--%>
                    <%--</a>--%>
                <%--</div>--%>
            </div>
        </div>
        <div>
            <div style="width: 100%;height: 20px;;font-weight:bold;">白片信息</div>
            <div style="width: 100%;padding-top:5px;" id="lakuaiListContainer">
                <table id="lkItemList"></table>
            </div>
            <div style="width: 100%;height: 25px;">
                项目套餐：<select id="itemPackage" onchange="getItemInfo(this.value)"></select>
            </div>
            <div style="width: 100%;padding-top:5px"><button onclick="appendAll()" style="background:#e9e9e9;border-radius:3px;border:1px solid #c2c2c2">全部追加</button></div>
            <div style="width: 100%;padding-top:5px">项目名称：<input id="itemName"></div>
            <div style="width: 100%;padding-top:5px;">
                <table id="ckItemList"></table>
            </div>
        </div>
    </div>
    <div style="width: 780px;height: 500px;display: none;" id="specialCheckDialog">
        <div style="float:left;width: 60%;height: 100%;padding-left:10px;padding-right: 10px;display: inline">
            <div style="width: 100%;padding-top:5px;height: 20px;font-weight:bold;">基本信息</div>
            <div style="width: 100%;height: 20%;">
                <div style="padding-top:5px;">性别：<input id="patientGender" style="width: 60px;border-width: 0px 0px 1px 0px">住院号：<input id="patientZyh" style="width: 120px;border-width: 0px 0px 1px 0px"></div>
                <div style="padding-top:5px;">病人姓名：<input id="patientName" style="width: 120px;border-width: 0px 0px 1px 0px">年龄：<input id="patientAge" style="width: 60px;border-width: 0px 0px 1px 0px">床号：<input id="patientBed" style="width: 120px;border-width: 0px 0px 1px 0px"></div>
                <div style="padding-top:5px;">临床诊断：<input id="patientDiagnosisNote" style="width: 300px;border-width: 0px 0px 1px 0px"></div>
            </div>
            <div style="width: 100%;height: 20px;font-weight:bold;">特殊检查</div>
            <div style="width: 100%;height: 23%;">
                <div style="padding-top:5px;">医嘱号：<input id="ordercode" value="" readonly style="width: 120px;border-width: 0px 0px 1px 0px">检查类型：
                    <select name="" id="reqType" style="width: 120px;border-width: 0px 0px 1px 0px">
                        <option value="1">免疫组化</option>
                        <option value="2">特殊染色</option>
                        <option value="3">分子病理</option>
                    </select>
                </div>
                <div style="padding-top:5px;">
                    源病理号：<input id="yblNo" readonly style="width: 120px;border-width: 0px 0px 1px 0px">
                    申请医生：
                    <input id="reqDoctor" style="width: 120px;border-width: 0px 0px 1px 0px">
                    <input type="hidden" id="lcal_hosptail" value="${send_hosptail}"/>
                    <input type="hidden" id="reqDoctorId" value=""/>
                    <input type="hidden" id="orderType" value=""/>
                    <input type="hidden" id="childItemId" value=""/>
                </div>
                <div style="padding-top:5px;">
                    源条形码：<input id="ytxm" readonly style="width: 120px;border-width: 0px 0px 1px 0px">
                    申请日期：<input id="reqDate" class="" style="width: 120px;border-width: 0px 0px 1px 0px">
                </div>
                <input id="whitePieceNo" type="hidden" style="width: 120px;border-width: 0px 0px 1px 0px">

            </div>
            <div style="width: 100%;height: 25px;font-weight:bold;">项目一览  <button onclick="removeItems()">删除</button>  蜡块选择<select id="lkxz" onchange="getWhitePiece()"></select></div>
            <div style="width: 100%;padding-top:5px;" id="itemListContainer">
                <table id="itemList"></table>
            </div>
            <div style="width: 100%;" id="itemSummary">
                <%--癌基因蛋白：3 项，199元；单克隆抗体：3 项，199元；合计：398元--%>
            </div>
        </div>

    </div>
</body>
</html>
