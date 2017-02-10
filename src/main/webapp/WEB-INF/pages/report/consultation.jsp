<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>

<head>
    <title><fmt:message key="menu.PathologicalConsultationQuery"/></title>
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
    <script type="text/javascript" src="../scripts/report/consultation.js"></script>
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
 <div class="col-sm-12">
                <button type="button" class="btn btn-sm btn-primary" title="上一个" onclick="">
                    上一个
                </button>
                <button type="button" class="btn btn-sm btn-primary" title="下一个" onclick="">
                    下一个
                </button>
                <button type="button" class="btn btn-sm btn-primary" title="列表打印" onclick="">
                    列表打印
                </button>
                <button type="button" class="btn btn-sm btn-primary" title="打印设置" onclick="setrow()">
                    打印设置
                </button>
                <button type="button" class="btn btn-sm btn-primary" title="导出" id="savemar" onclick="daochu()">
                    导出
                </button>
</div>
 <div  class="row col-xs-12" id="setrow" style="display: none;z-index:1000000000;">
     <h5 style="margin-left: 20px">当前位置：打印列设置</h5>
     <div class="row widget-main" style="background-color: #E8E8E8;border:1px solid #E0E0E0;" id="div_0">
         <div style="margin-bottom: 5px;margin-left: 70px">
             <input type="checkbox" class="input_check" name="xx" value="1" checked><span class="input_style">&nbsp;会诊状态&nbsp;</span>
             <input type="checkbox" class="input_check" name="xx" value="11" checked><span class="input_style">&nbsp;送检日期&nbsp;</span>
         </div>
         <div style="margin-bottom: 5px;margin-left: 70px">
             <input type="checkbox" class="input_check" name="xx" value="2"   checked><span class="input_style">&nbsp;病种类别&nbsp;</span>
             <input type="checkbox" class="input_check" name="xx" value="12" checked><span class="input_style">&nbsp;发起医生&nbsp;</span>
         </div>
         <div style="margin-bottom: 5px;margin-left: 70px">
             <input type="checkbox" class="input_check" name="xx" value="3" checked><span class="input_style">&nbsp;病理编号&nbsp;</span>
             <input type="checkbox" class="input_check" name="xx" value="13" checked><span class="input_style">&nbsp;发起日期&nbsp;</span>
         </div>
         <div style="margin-bottom: 5px;margin-left: 70px">
             <input type="checkbox" class="input_check" name="xx" value="4" checked><span class="input_style">&nbsp;患者姓名&nbsp;</span>
             <input type="checkbox" class="input_check" name="xx" value="14" checked><span class="input_style">&nbsp;结束日期&nbsp;</span>
         </div>
         <div style="margin-bottom: 5px;margin-left: 70px">
             <input type="checkbox" class="input_check" name="xx" value="5" checked><span class="input_style">&nbsp;年龄&nbsp;</span>
         </div>
         <div style="margin-bottom: 5px;margin-left: 70px">
             <input type="checkbox" class="input_check" name="xx" value="6" checked><span class="input_style">&nbsp;性别&nbsp;</span>
         </div>
         <div style="margin-bottom: 5px;margin-left: 70px">
             <input type="checkbox" class="input_check" name="xx" value="7" checked><span class="input_style">&nbsp;病理状态&nbsp;</span>
         </div>
         <div style="margin-bottom: 5px;margin-left: 70px">
             <input type="checkbox" class="input_check" name="xx" value="8" checked><span class="input_style">&nbsp;送检单位&nbsp;</span>
         </div>
         <div style="margin-bottom: 5px;margin-left: 70px">
             <input type="checkbox" class="input_check" name="xx" value="9" checked><span class="input_style">&nbsp;送检科室&nbsp;</span>
         </div>
         <div style="margin-bottom: 5px;margin-left: 70px">
             <input type="checkbox" class="input_check" name="xx" value="10" checked><span class="input_style">&nbsp;送检医生&nbsp;</span>
         </div>
             <%--<button type="button" style="border-radius:3px;border:1px solid #2274E4;background-color: #108CCF;margin-left:150px;" onclick="loanbtn()">--%>
                 <%--<span style="color: white;">保存并关闭</span>--%>
             <%--</button>--%>
             <%--<button type="button" style="border-radius:3px;border:1px solid #2274E4;background-color: #108CCF;margin-left:10px;" onclick="cancellayer()">--%>
                 <%--<span style="color: white;">取消</span>--%>
             <%--</button>--%>
         </div>
     </div>
 </div>
<div class="row" id="maincontent">
    <div id="checkSlide" class="col-xs-3">
        <div class="widget-box widget-color-green ui-sortable-handle">
            <div class="widget-header">
                <h6 class="widget-title">会诊条件查询</h6>
                <button type="button" class="btn btn-sm btn-primary" title="查询" onclick="getSearchList()">
                    查询
                </button>
            </div>
            <div>发起年月:<input style="width:160px" type="text" id="consponsoredtime" class="form_datetime input_style" placeholder="" autocomplete="off"></div>
            <div>结束年月:<input style="width:160px" type="text" id="confinishedtime" class="form_datetime input_style" placeholder="" autocomplete="off"></div>
            <div><label>病种类别:</label>
              <select id="logyids" style="width:100px">
                   <%out.print(request.getAttribute("logyids"));%>
              </select>
            </div>
            <div>病理编号:<input style="width:160px" type="text" id="sampathologycode"  placeholder="" autocomplete="off"></div>
            <div>患者姓名:<input style="width:160px" type="text" id="sampatientname"  placeholder="" autocomplete="off"></div>
            <div>
            <label>送检单位:</label>
            <input id="samsendhospitalid" name="samsendhospitalid" type="hidden"/>
            <select id="hospitalname" class="input_style" onchange="fillval('samsendhospitalid','samsendhospital','hospitalname')">
                    <%out.print(request.getAttribute("samsendhospital"));%>
            </select>
            <input id="samsendhospital" name="samsendhospital" datatype="*" style="position:absolute;left:70px;width: 36.5%;height:24px" class="input_style">
            </div>
            <div>
                <label>送检科室:</label>
                <input id="samdeptcode" name="samdeptcode" type="hidden"/>
                <select id="samdeptname1" style="width:200px" onchange="fillval('samdeptcode','samdeptname','samdeptname1')">
                    <%out.print(request.getAttribute("samdeptname"));%>
                </select>
                <input id="samdeptname" name="samdeptname" datatype="*" style="position:absolute;left:70px;width: 54%;height:30px" class="input_style">
            </div>
            <div>
                <label>送检医生:</label>
                <input id="samsenddoctorid1" name="samsenddoctorid1" type="hidden"/>
                <select id="samsenddoctorname" style="width:100px" onchange="fillval('samsenddoctorid1','samsenddoctorname1','samsenddoctorname')">
                    <%out.print(request.getAttribute("samsenddoctorname"));%>
                </select>
                <input id="samsenddoctorname1" name="samsenddoctorname1" datatype="*" style="position:absolute;left:70px;width: 24%;height:30px" class="input_style">
            </div>
            <div>
                <label>会诊状态：</label>
                <select id="conconsultationstate" style="width:90px">
                <option value="">全部</option>
                <option value="0">会诊中</option>
                <option value="1">会诊终了</option>
                <option value="2">会诊取消</option>
                </select>
            </div>
            <div>
                <label>发起医生:</label>
                <input id="samsenddoctorid2" name="samsenddoctorid2" type="hidden"/>
                <select id="consponsoredusername" style="width:100px" onchange="fillval('samsenddoctorid2','samsenddoctorname2','consponsoredusername')">
                    <%out.print(request.getAttribute("samsenddoctorname"));%>
                </select>
                <input id="samsenddoctorname2" name="samsenddoctorname2" datatype="*" style="position:absolute;left:70px;width: 24%;height:30px" class="input_style">
            </div>
            <div>
                <label >参与医生:</label>
                <input id="samsenddoctorid3" name="samsenddoctorid3" type="hidden"/>
                <select id="detdoctorname" style="width:100px" onchange="fillval('samsenddoctorid3','samsenddoctorname3','detdoctorname')">
                    <%out.print(request.getAttribute("samsenddoctorname"));%>
                </select>
                <input id="samsenddoctorname3" name="samsenddoctorname3" datatype="*" style="position:absolute;left:70px;width: 24%;height:30px" class="input_style">
            </div>
            <div>
                    病理诊断:<textarea id='remarks' style="resize: none; vertical-align: text-top;width: 70%;height:150px" value="" autocomplete="off"></textarea>
            </div>
        </div>
    </div>

    <div id="checkSlide2" class="col-xs-9">
        <div class="widget-box widget-color-green ui-sortable-handle">
            <div class="widget-header">
                <h6 class="widget-title">结果一览</h6>
            </div>
            <div style="display: block;">
                <div class="col-xs-12 leftContent">
                    <table id="new"></table>
                    <div id="pager"></div>
                </div>
            </div>
        </div>
        <div style="display: block;">
            <div class="col-xs-12 rightContent">
                <div style="display:inline;">
                    <ul class="nav nav-tabs">
                        <li class="active">
                            <a href="#page1" onclick="show('1')" data-toggle="tab">会诊信息</a>
                        </li>
                        <li>
                            <a href="#page2" onclick="show('2')" data-toggle="tab">诊断信息</a>
                        </li>
                        <li>
                            <a href="#page3" onclick="show('3')" data-toggle="tab">取材信息</a>
                        </li>
                        <li id="ap4">
                            <a href="#page4" onclick="show('4')" data-toggle="tab">免疫组化</a>
                        </li>
                        <li>
                            <a href="#page4" onclick="show('5')" data-toggle="tab">特殊染色</a>
                        </li>
                        <li id="ap6">
                            <a href="#page4" onclick="show('6')" data-toggle="tab">分子病理</a>
                        </li>
                    </ul>
                </div>
                <div style="height:200px">
                    <div id="page1" style="display:block"></div>
                    <div id="page2" style="display:none">
                        <form class="form-horizontal" action="#" method="post" id="sampleForm" >
                            <div class="form-group" style="margin-top:10px;margin-bottom: 5px;">
                                <label class="col-sm-1 label_style">基本信息:</label>
                                <div class="col-sm-11">
                                    <textarea id="jbxx1" style="height: 50px;width: 90%"></textarea>
                                </div>
                            </div>
                            <div class="form-group" style="margin-bottom: 5px;">
                                <label class="col-sm-1 label_style" >病理诊断:</label>
                                <div class="col-sm-5 ">
                                    <textarea id="blzd1" style="height: 50px;width: 80%"></textarea>
                                </div>
                                <label class="col-sm-1 label_style" >免疫组化:</label>
                                <div class="col-sm-5 ">
                                    <textarea id="item37" style="height: 50px;width: 80%"></textarea>
                                </div>
                            </div>
                            <div class="form-group" style="margin-bottom: 5px;">
                                <label class="col-sm-1 label_style" >特殊染色:</label>
                                <div class="col-sm-5 ">
                                    <textarea id="item39" style="height: 50px;width: 80%"></textarea>
                                </div>
                                <label class="col-sm-1 label_style" >分子病理:</label>
                                <div class="col-sm-5 ">
                                    <textarea id="item38" style="height: 50px;width: 80%"></textarea>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div id="page3" style="display:none">
                    <table id="new1"></table>
                    <div id="pager2"></div>
                    </div>
                    <div id="page4" style="display:none"><table id="Item37"></table></div>
                    <div id="page5" style="display:none"><table id="Item39"></table></div>
                    <div id="page6" style="display:none"><table id="Item38"></table></div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>


