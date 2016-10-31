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
</head>
<style>
	select {
		height:34px;
	}
	.ui-autocomplete {
		z-index: 99999999;
	}
</style>
<body>
	<h5><strong>&nbsp;基本信息</strong></h5>
	<div class="row widget-main" style="height: 85px;background-color: #E8E8E8" id="div_2">
		<table>
			<span>病种类别:</span>
			<select id="logyid">
				<%out.println(request.getAttribute("logyids"));%>
			</select>
			<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;病理号:</span>
			<input type="text" style="height: 28px" id="req_code" onkeypress="receive(this,event)"/>
		</table>
		<table>
			<span>申请年月:</span>
			<input type="text" class="form_datetime" style="height: 28px" value="${sevenday}" id="req_bf_time"/>
			<span>-</span>
			<input type="text" class="form_datetime" style="height: 28px" value="${receivetime}"  id="req_af_time"/>
			<button type="submit" class="btn-sm btn-success" style="float: right" onclick="searchList()">
				<i class="ace-icon fa fa-print bigger-110"></i>
				查询
			</button>
		</table>
	</div>
	<h5><strong>&nbsp;工作安排一览</strong></h5>
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