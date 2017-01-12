<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>

<head>
    <title><fmt:message key="menu.MedicalDisposableMaterial"/></title>
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
    <script type="text/javascript" src="../scripts/othermanage/materialManagement.js"></script>
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
            input[type="text"]{
                height:26px;
            }
            select{
                height:26px!important;
            }
        #tabs-1 td{
            border-right: 0px;



        }
        #tabs-1 tbody tr td{
            padding-bottom: 10px!important;
            padding-top: 5px;
            margin-right: 5px;
            padding-left: 10px;
        }
        #gbox_new{
            width: 101.4%!important;
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
                <button type="button" class="btn btn-sm btn-primary" title="新增" onclick="newAdd()">
                    <i class="ace-icon glyphicon glyphicon-plus"></i>
                    新增
                </button>
                <button type="button" class="btn btn-sm btn-info" title="删除" onclick="deletemar()">
                    <i class="ace-icon glyphicon glyphicon-remove"></i>
                    删除
                </button>
                <button type="button" disabled="disabled" class="btn btn-sm btn-warning" title="保存" id="savemar" onclick="save()">
                    <i class="ace-icon fa fa-floppy-o"></i>
                    保存
                </button>
                <button type="button" class="btn btn-sm btn-success" title="列表打印" id="print" onclick="printCode()">
                    <i class="ace-icon fa fa-print bigger-110"></i>
                    列表打印
                </button>

</div>

<div class="row" id="maincontent">
    <div id="checkSlide" class="col-xs-8">
        <div class="widget-box widget-color-green ui-sortable-handle" style="margin-left: 10px">
            <div class="widget-header">

                <h6 class="widget-title">耗材一览</h6>
                <div class="widget-toolbar">
                    <a href="#"  data-action="collapse" onclick="showandhiden(this)">
                        <i class="ace-icon fa fa-chevron-up">隐藏</i>
                    </a>
                </div>
            </div>
            <div class="widget-body" style="display: block;background-color: #F9F9F9" >
                <div class="widget-main padding-4 scrollable ace-scroll" style="position: relative;">
                    <div class="scroll-content">
                        <div class="content">
                            <div style="display:inline;">
                                <label style="margin-left:10px">耗材名称：</label><input type="text" id="marname" style="width: 120px;height: 24px" autocomplete="off">
                            </div>
                            <div>
                                <label style="margin-left:10px;height:24px">在库状态：</label>
                                <select id="current" style="width:50px;height:24px;margin-left:-4px">
                                <option value="1">有</option>
                                <option value="0">无</option>
                                </select>
                                <button type="button" class="btn-sm btn-info" onclick="searchList()" style="float:right;background-color: #4190f7!important;color:#ffffff;border-radius:3px;border:1px solid #2274E4;padding:0 16px;">
                                    查询
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
                <div class="col-xs-12 leftContent" style="margin-top: 5px;margin-left: -0.7%">
                    <table id="new"></table>
                    <div id="pager"></div>
                </div>
        </div>
    </div>

    <div id="checkSlide2" class="col-xs-4">
        <div class="widget-box widget-color-green ui-sortable-handle">
            <div class="widget-header">
                <h6 class="widget-title">耗材详细信息</h6>
                <div class="widget-toolbar">
                    <a href="#"  data-action="collapse" onclick="showandhiden(this)">
                        <i class="ace-icon fa fa-chevron-up">隐藏</i>
                    </a>
                </div>
            </div>
            <div class="widget-body" style="display: block;background-color: #F9F9F9">
                <div id="tabs" style="margin: 0 auto;">

                    <table id="tabs-1">
                        <tr style="margin-top:10px ">


                            <td style="padding-left: 10px">耗材ID：<input type="text" style="width:120px;margin-left: 12px"
                                id="marid" disabled="disabled" autocomplete="off" value=""></td>
                            <td >登录者：<input type="text" style="width:120px;margin-left: 12px"
                                id="loginuser" disabled="disabled" value="" autocomplete="off" value=""/></td>
                                <input id="loginuser2" style="display:none" value="${user}" autocomplete="off"/>
                        </tr>
                        <tr style="margin-top:10px ">
                            <td style="padding-left: 10px">耗材名称：<input type="text" disabled="disabled" style="width:120px;"
                                                                     id="marnamea" autocomplete="off" value=""/>
                            </td>
                            <td >登录日期：<input type="text" style="width:150px;"
                                                                   id="loginintime" disabled="disabled" autocomplete="off" value=""></td>
                                 <input id="loginintime2" style="display:none" value="${logintime}" autocomplete="off"/>
                        </tr>
                        <tr style="margin-bottom:10px ">
                            <td style="padding-left: 10px">库存状态：<input type="radio" disabled="disabled" name="has" value="1" id="marishas" onclick="checkedhas()">有<input style="margin-left:10px" disabled="disabled" type="radio" name="has" value="0" id="marnothave" onclick="checkedhas2()">无</td>
                            <td >制造商：<input type="text" disabled="disabled" style="width:120px;margin-left: 12px"
                                                                                               id="manufacter" autocomplete="off" value=""></td>
                        </tr>

                    </table>
                </div>
                <div style="padding-bottom: 20px;padding-left: 10px;">
                    备注：<textarea id='remarks' disabled="disabled" style="resize: none; vertical-align: text-top;width: 70%;height:150px" value="" autocomplete="off"></textarea>
                </div>
            </div>
        </div>
    </div>
</div>
</body>

