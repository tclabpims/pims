<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>

<head>
    <title><fmt:message key="menu.LoanManagement"/></title>
    <meta name="menu" content="LoanManagement"/>
    <link rel="stylesheet" type="text/css" href="<c:url value='/styles/ui.jqgrid.css'/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value='/styles/bootstrap-datetimepicker.min.css'/>"/>
    <link rel="stylesheet" type="text/css"  href="<c:url value='/styles/bootstrap.min.css'/>" />
    <script type="text/javascript" src="../scripts/jquery-2.1.4.min.js"></script>
    <script type="text/javascript" src="../scripts/jquery-ui.min.js"></script>
    <script type="text/javascript" src="../scripts/bootstrap.min.js"></script>
    <script type="text/javascript" src="../scripts/i18n/grid.locale-cn.js"></script>
    <script type="text/javascript" src="../scripts/jquery.jqGrid.js"></script>
    <script type="text/javascript" src="<c:url value="/scripts/ace.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/ace-elements.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/bootstrap-tag.min.js"/>"></script>
    <script type="text/javascript" src="../scripts/layer/layer.js"></script>
    <script type="text/javascript" src="../scripts/othermanage/loanmanagement.js"></script>
    <script type="text/javascript" src="../scripts/validform/Validform.min.js"></script>
    <script type="text/javascript" src="../scripts/bootstrap-datetimepicker.min.js"></script>
    <%--<script type="text/javascript" src="../scripts/consultation/cons1.js"></script>--%>
    <script src="<c:url value='/scripts/LodopFuncs.js'/>"></script>
    <style>
        /*select {*/
        /*height:34px;*/
        /*}*/
        .ui-autocomplete {
            z-index: 99999998;
        }
        .datetimepicker{
            z-index:1000000000;
        }
        /*label {*/
        /*font-size: 12px;*/
        /*}*/
        /*span{*/
        /*font-size: 12px;*/
        /*}*/
        /*.input_style{*/
        /*height: 28px;*/
        /*}*/
        .ui-jqgrid-sortable {
            text-align: center;
        }

        td {
            border-right: 1px solid #E0E0E0;
        }
        .form_datetime{
            z-index:99999999 !important;
        }
        .input_style {
            height: 24px;
            font-size: 12px !important;
        }
        #newsTable {
            border: 1px solid #E0E0E0;
            width: 100%;
            margin-top: 10px
        }
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
                z-index: 99999998;
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
            #deptandresult {
                width:100%;
                height:300px;
                overflow:scroll;
            }
            .result{
            position: relative;
                width:100%;
                height:100px;
            }
            .ta{
            resize:none;
            width:100%;
            height:100px;
            }
            #tabs-1 div{
                margin-left: 10px;
            }
        #maincontent{
            width:100%
        }
        .button2{
            background-color: #4190f7;color:#ffffff;border-radius:3px;border:1px solid #2274E4;width: 58px
        }
        .datetimepicker{
            z-index: 9999999999!important;
        }
        .row{
            margin-left: 0px!important;
            margin-right: 0px!important;
        }
    </style>
</head>
<script>
    $(window).resize(function () {          //当浏览器大小变化时
        alert($(window).height());          //浏览器时下窗口可视区域高度
        alert($(document).height());        //浏览器时下窗口文档的高度
        alert($(document.body).height());   //浏览器时下窗口文档body的高度
        alert($(document.body).outerHeight(true)); //浏览器时下窗口文档body的总高度 包括border padding margin
    });
</script>
<body>
 <div class="col-xs-12">
                <button type="button" class="btn btn-sm btn-primary" title="上一个" onclick="upSlide()">
                    <i class="ace-icon fa fa-arrow-left bigger-110"></i>
                    上一个
                </button>
                <button type="button" class="btn btn-sm btn-info" title="下一个" onclick="downSlide()">
                    <i class="ace-icon fa fa-arrow-right bigger-110"></i>
                    下一个
                </button>
                <button type="button" class="btn btn-sm btn-warning" title="列表打印" id="print" onclick="printCode()">
                    <i class="ace-icon fa fa-print bigger-110"></i>
                    列表打印
                </button>
                <button type="button" class="btn btn-sm btn-success" title="借阅" id="loan" onclick="loanSlide()">
                    <i class="ace-icon fa fa-book bigger-110"></i>
                    借阅
                </button>
                <button type="button" class="btn btn-sm btn-primary" title="归还" id="return" onclick="returnSlide()">
                    <i class="ace-icon fa fa-paperclip bigger-110"></i>
                    归还
                </button>

</div>
<div  class="row" id="loanSlidePage" style="display: none;z-index:1000000000;">
    <h5 style="margin-left: 20px">当前位置：借阅信息录入</h5>
    <div class="row widget-main" style="background-color: #E8E8E8;border:1px solid #E0E0E0;" id="div_0">
        <div style="margin-left: 30px"><label>身份证号:</label><input type="text" id="sliloancustomerid" style="width:160px;height: 24px" value="" datatype="*" autocomplete="off"/></div>
        <div style="margin-left: 45px"><label>借阅者:</label><input type="text" style="width:160px;height: 24px" id="sliloancustomername" value="" autocomplete="off"/></div>
        <div  style="margin-left: 21px">预计归还日:<input style="width:160px" type="text" id="sliintime" class="form_datetime input_style" placeholder="" autocomplete="off"/>
        <button type="button" style="border-radius:3px;border:1px solid #2274E4;background-color: #108CCF;margin-left:60px;" onclick="loanbtn()">
            <span style="color: white;">保存并关闭</span>
        </button>
        <button type="button" style="border-radius:3px;border:1px solid #2274E4;background-color: #108CCF;margin-left:10px;" onclick="cancellayer()">
                <span style="color: white;">取消</span>
        </button>
    </div>
    </div>
</div>

<div  class="row" id="returnSlidePage" style="display: none;z-index:1000000000;">
    <h5 style="margin-left: 20px">当前位置：归还信息录入</h5>
    <div class="col-xs-6 leftContent" style="padding-top: 1px">
        <label style="margin-left: 20px">借阅信息确认</label>
        <div class="row widget-main" style="background-color: #E8E8E8;border:1px solid #E0E0E0;" id="div_0">
            <div style="margin-left: 30px"><label>身份证号:</label><input type="text" id="slireturncustomerid" style="width:160px;height:24px" value="" datatype="*" autocomplete="off"/></div>
            <div style="margin-left: 45px"><label>借阅者:</label><input disabled="disabled" type="text" style="width:160px;height:24px" id="slireturncustomername" value="" autocomplete="off"/></div>
            <button type="button" class="button2" onclick="searchList2()" style="float:right">
                查询
            </button>
        </div>
        <div class="col-xs-12 leftContent">
            <table id="new3"></table>
        </div>
    </div>
    <div class="col-xs-6 rightContent">
        <label  style="margin-left:10px">诊断信息录入</label>
        <div  style="margin-left:10px">
        <div>
            <label>诊断机构</label><input type="text" id="slidept" style="width:160px;height: 24px" value="" autocomplete="off"/>
        </div>
        <div>
            <label>诊断结果</label><textarea id='sliresult' style="resize: none; vertical-align: text-top;width: 70%;height:150px" value="" autocomplete="off"></textarea>
        </div>
        </div>
        <button type="button" style="border-radius:3px;border:1px solid #2274E4;background-color: #108CCF;margin-left:200px;margin-top:10px" onclick="returnbtn()">
            <span style="color: white;">保存并关闭</span>
        </button>
        <button type="button" style="border-radius:3px;border:1px solid #2274E4;background-color: #108CCF;margin-left:10px;" onclick="cancellayer()">
                <span style="color: white;">取消</span>
        </button>
    </div>
</div>

<div class="row" id="maincontent">
    <div id="checkSlide" class="col-xs-8">
        <div class="widget-box widget-color-green ui-sortable-handle">
            <div class="widget-header">
                <h6 class="widget-title">玻片一览</h6>
            </div>

            <div class="widget-body" style="display: block;">
                <div class="widget-main padding-4 scrollable ace-scroll" style="position: relative;">
                    <div class="scroll-content">
                        <div class="content">
                            <div style="display:inline;">
                                <label style="margin-left:20px">病种类别：</label>
                                <select id="slipathologyid" style="width: 100px;height: 24px">
                                    <%out.println(request.getAttribute("logyids"));%>
                                </select>
                                <label style="margin-left:20px">病理编号：</label><input type="text" id="logyid" style="width: 120px;height: 24px;">
                                <label style="margin-left:20px">患者姓名：</label><input type="text" id="patient_name" style="width: 120px;height: 24px;">
                            </div>
                            <div>
                                <label style="margin-left:20px">在库状态：</label>
                                <select id="current"  style="width: 100px;height: 24px">
                                <option value="">全部</option>
                                <option value="">借阅中</option>
                                <option value="">在库</option>
                                </select>
                                <label style="margin-left:20px">玻片编号：</label><input type="text" id="sliid" style="width: 120px; height: 24px;">
                                <button type="button" class="button2" onclick="searchList()" style="float:right;">
                                    查询
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div style="display: block;">
                <div class="col-xs-12 leftContent">
                    <table id="new"></table>
                    <div id="pager"></div>
                </div>
            </div>

        </div>
    </div>

    <div id="checkSlide2" class="col-xs-4">
        <div class="widget-box widget-color-green ui-sortable-handle">
            <div class="widget-header">
                <h6 class="widget-title">玻片详细信息</h6>
            </div>
            <div style="display: block;">
                <div id="tabs" style="margin: 0 auto;">

                    <div id="tabs-1">
                        <div>
                            <div style="display: inline">在库状态：<input type="text" style="width:120px;border-width: 0px 0px 1px 0px"
                                                                    id="slicurrenta"></div>
                            <div style="display: inline">患者姓名：<input type="text" style="width:120px;border-width: 0px 0px 1px 0px"
                                                                    id="patientnamea"></div>
                        </div>
                        <div>
                            <div style="display: inline">病种类别：<input type="text" style="width:120px;border-width: 0px 0px 1px 0px"
                                                                     id="slipathologyida">
                            </div>
                            <div style="display: inline">年龄：<input type="text" style="width:120px;border-width: 0px 0px 1px 0px"
                                                                   id="patientagea"></div>
                        </div>
                        <div>
                            <div style="display: inline">病理编号：<input type="text" style="width:120px;border-width: 0px 0px 1px 0px"
                                                                     id="pathologyida"></div>
                            <div style="display: inline">性别：<input type="text" style="width:120px;border-width: 0px 0px 1px 0px"
                                                                     id="patientsexa"></div>
                        </div>
                    </div>
                    <div id="tabs-2">
                        <div class="row" id="materialGrid">
                            <div class="col-xs-12 leftContent" style="margin-top: 15px">
                                <table id="new2"></table>
                                <div id="pager2"></div>
                            </div>
                        </div>
                    </div>

                    <div class="col-xs-12">
                        <div id="deptandresult" class="widget-box widget-color-green ui-sortable-handle">
                            <div  class="widget-header">
                                <h6 class="widget-title">诊断机构</h6>
                            </div>
                            <div class="widget-body result widget-main padding-4 scrollable ace-scroll">
                                <textarea class="ta"></textarea>
                            </div>
                            <div  class="widget-header">
                                <h6 class="widget-title">诊断机构</h6>
                            </div>
                            <div class="widget-body">
                                <div class="result widget-main padding-4 scrollable ace-scroll">
                                    <div class="scroll-content">
                                        <div class="content">
                                            <textarea class="ta"></textarea>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>


</div>
</body>

