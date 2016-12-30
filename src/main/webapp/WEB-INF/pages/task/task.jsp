<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>

<head>
    <title><fmt:message key="ElectronicApplyManage.title"/></title>
    <link rel="stylesheet" type="text/css"  href="<c:url value='/styles/ui.jqgrid.css'/>" />
	<link rel="stylesheet" type="text/css"  href="<c:url value='/styles/bootstrap-datetimepicker.min.css'/>" />
	<script type="text/javascript" src="../scripts/jquery-2.1.4.min.js"></script>
	<script type="text/javascript" src="../scripts/jquery-ui.min.js"></script>
	<script type="text/javascript" src="../scripts/bootstrap.min.js"></script>
	<script type="text/javascript" src="../scripts/i18n/grid.locale-cn.js"></script>
	<script type="text/javascript" src="../scripts/jquery.jqGrid.js"></script>
	<script type="text/javascript" src="<c:url value="/scripts/ace.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/ace-elements.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/bootstrap-tag.min.js"/>"></script>
    <script type="text/javascript" src="../scripts/layer/layer.js"></script>
    <script type="text/javascript" src="../scripts/task/task.js"></script>
	<%--<script type="text/javascript" src="../scripts/validform/Validform.min.js"></script>--%>
	<script type="text/javascript" src="../scripts/bootstrap-datetimepicker.min.js"></script>
	<style>
		.ui-autocomplete {
			z-index: 99999999 !important;
			max-height: 200px;
			overflow-y: auto;
			/* 防止水平滚动条 */
			overflow-x: hidden;
		}
		.form_datetime{
			z-index: 99999999 !important;
		}
		.div_div {float:left;margin:20px 35px 11px 8px;text-align:center;color: #808080;font-size: 12px;  }
		.div_img{cursor:pointer;display: block;margin-bottom:11px;}
		.div_1{background-color: #F9F9F9;height: 106px;border:1px solid #E0E0E0}
		.img_style{width: 18px;height: 23px}
		.label_style{font-size: 12px;color: #323232;height: 24px;text-align:right;}
		.input_style{height: 24px;font-size: 12px!important;}
		.ui-jqgrid-sortable{text-align: center;}
		.ui-jqgrid-hbox{padding-right: 0px!important;}
	</style>
</head>
<body style="font-family:'Microsoft YaHei',微软雅黑,'MicrosoftJhengHei'!important;">
	<h5 style="font-size: 14px;"><strong>&nbsp;基本信息</strong></h5>
	<div class="row widget-main" style="background-color: #E8E8E8;border:1px solid #E0E0E0;" >
		<div style="margin-top: 10px;">
			<table style="margin-bottom: 5px">
				<span class="input_style">病种类别:</span>
				<select id="logyid" class="input_style">
					<%out.println(request.getAttribute("logyids"));%>
				</select>
				<span class="input_style">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;病理号:</span>
				<input type="text" class="input_style" id="req_code" onkeypress="receive(this,event)"/>
			</table>
			<table>
				<span class="input_style">申请年月:</span>
				<input type="text" class="input_style form_datetime" value="${sevenday}" id="req_bf_time"/>
				<span class="input_style">-</span>
				<input type="text" class="input_style form_datetime" value="${receivetime}"  id="req_af_time"/>
				<span style="float: right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
				<span>
					<button type="button" style="border-radius:3px;border:1px solid #2274E4;background-color: #4190f7;padding:0 16px;float: right" onclick="searchList()">
						<span style="color: white;">查询</span>
					</button>
				</span>
			</table>
		</div>
	</div>
	<h5 style="font-size: 14px;"><strong>&nbsp;工作安排一览</strong></h5>
	<div class="row">
		<div>
			<div class="widget-body" style="overflow:auto;">
				<div class="widget-main no-padding">
					<table id="new" class="table">
					</table>
					<div id="pager"></div>
				</div>
			</div>
		</div>
	</div>
	<div id="formDialog" style="display:none;" class="col-sm-12">
		<h1>打开了吧！</h1>
	</div>
</body>