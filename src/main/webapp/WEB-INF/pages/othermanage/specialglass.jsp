<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>

<head>
    <title><fmt:message key="menu.SpecialGlassPrint"/></title>
    <meta name="menu" content="SpecialGlassPrint"/>
    <link rel="stylesheet" type="text/css" href="<c:url value='/styles/ui.jqgrid.css'/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value='/styles/bootstrap-datetimepicker.min.css'/>"/>
    <script type="text/javascript" src="../scripts/jquery-2.1.4.min.js"></script>
    <script type="text/javascript" src="../scripts/jquery-ui.min.js"></script>
    <script type="text/javascript" src="../scripts/bootstrap.min.js"></script>
    <script type="text/javascript" src="../scripts/i18n/grid.locale-cn.js"></script>
    <script type="text/javascript" src="../scripts/jquery.jqGrid.js"></script>
    <script type="text/javascript" src="<c:url value="/scripts/ace.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/ace-elements.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/bootstrap-tag.min.js"/>"></script>
    <script type="text/javascript" src="../scripts/layer/layer.js"></script>
    <script type="text/javascript" src="../scripts/othermanage/specialglass.js"></script>
    <script type="text/javascript" src="../scripts/validform/Validform.min.js"></script>
    <script type="text/javascript" src="../scripts/bootstrap-datetimepicker.min.js"></script>
    <%--<script type="text/javascript" src="../scripts/consultation/cons1.js"></script>--%>
    <script src="<c:url value='/scripts/LodopFuncs.js'/>"></script>


    <style>
        /*select {*/
        /*height:34px;*/
        /*}*/
        .ui-autocomplete {
            z-index: 99999999;
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

        #newsTable {
            border: 1px solid #E0E0E0;
            width: 100%;
            margin-top: 10px
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

<div id="div_main">
    <h5><strong>&nbsp;特殊玻片打印</strong></h5>
    <div style="margin-left:40%;margin-top:10%">
    <span style="width: 100%;">蜡块号:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
    <input type="text" id="slideid" class="input_style" value=""/>
    <div  style="margin-top:10px">
        自定义文字:<textarea id="slideinfo" style="vertical-align: text-top;width: 30%;height:100px" value=""></textarea>
    </div>
    <button type="button" class="btn-sm btn-info" onclick="printCode()" style="margin-left:10%;margin-top:10px">
        打印
    </button>
    </div>
    </div>
</body>

